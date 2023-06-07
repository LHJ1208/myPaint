package myPaint;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class myPaintMain extends JFrame {

	JPanel p_back, p_south;

	JMenuBar jmb;
	JMenu m_file, m_edit;
	JMenuItem i_newFile, i_openFile, i_saveFile, i_undo;

	JLabel l_cursorPoint, l_rectPoint;

	myPaintCanvas canvas = null;
	private CopyOnWriteArrayList<Rectangle> btnList = null;
	private LinkedList<Rectangle> undoList = null;
	Image backImg = null;

	String pathFile = "D:/njm/SeatList.ser";
	String nameFile = null;

	OIS_Rectangle ois_SeatChart = null;
	OOS_Rectangle oos_SeatChart = null;

	static final int btnSize = 50;
	static final int frameWidth = 500;
	static final int frameHeight = 500;

	public myPaintMain() {
		super("사각 찍기");

		ois_SeatChart = new OIS_Rectangle();
		oos_SeatChart = new OOS_Rectangle();

		jmb = new JMenuBar();
		m_file = new JMenu("File");
		i_newFile = new JMenuItem("New File");
		i_openFile = new JMenuItem("Open ...");
		i_saveFile = new JMenuItem("Save");

		m_file.add(i_newFile);
		m_file.add(i_openFile);
		m_file.addSeparator();
		m_file.add(i_saveFile);

		m_edit = new JMenu("Edit");
		i_undo = new JMenuItem("Undo");

		m_edit.add(i_undo);

		jmb.add(m_file);
		jmb.add(m_edit);
		setJMenuBar(jmb);

		btnList = new CopyOnWriteArrayList<>();
		undoList = new LinkedList<>();
		backImg = Toolkit.getDefaultToolkit().getImage("src/images/back.png");
		canvas = new myPaintCanvas(this);

		p_back = new JPanel(new BorderLayout()) {
			@Override
			public void paint(Graphics g) {
				Image image = Toolkit.getDefaultToolkit().getImage("src/images/back.png");

				g.drawImage(image, 0, 0, frameWidth, frameHeight, this); // 원래크기
			}
		};

		p_back.add(canvas);

		add(p_back);

		p_south = new JPanel();

		l_rectPoint = new JLabel("");
		l_cursorPoint = new JLabel("X,Y : (?,?)");

		p_south.add(l_rectPoint);
		p_south.add(l_cursorPoint);

		add(p_south, "South");

		setSize(frameWidth, frameHeight);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setResizable(false);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				boolean intersect = false;
				Rectangle inputPoint = new Rectangle(e.getX(), e.getY(), btnSize, btnSize);
				for (Rectangle btn : btnList) {
					if (btn.intersects(inputPoint)) {
						intersect = true;
						break;
					}
				}
				if (!intersect) {
					btnList.add(inputPoint);
					undoList.push(inputPoint);
					canvas.repaint();
				}
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				printPoint(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				printPoint(e);
			}
		});

		i_newFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnList.clear();
			}
		});

		i_saveFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnList.size() > 0) {
					if (oos_SeatChart.writeRectangleList(btnList, pathFile)) {
						JOptionPane.showMessageDialog(getParent(), "저장 성공");
					} else {
						JOptionPane.showMessageDialog(getParent(), "저장 실패");
					}
				}
			}
		});

		i_openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((btnList = ois_SeatChart.readRectangleList(pathFile)) != null) {
					JOptionPane.showMessageDialog(getParent(), "열기 성공");
					canvas.setBtnList(btnList);
					undoList.clear();
					canvas.repaint();
				} else {
					JOptionPane.showMessageDialog(getParent(), "열기 실패");
				}
			}
		});

		i_undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoList.size() > 0) {
					Rectangle peekRect = undoList.peek();
					if (btnList.contains(peekRect)) {
						btnList.remove(peekRect);
						undoList.pop();
						canvas.repaint();
					} else {
						btnList.add(peekRect);
						undoList.pop();
						canvas.repaint();
					}
				}
			}
		});
	}

	public void printPoint(MouseEvent e) {
		Point curPoint = new Point(e.getX(), e.getY());
		boolean intersect = false;
		canvas.setXY(curPoint, btnSize);
		for (Rectangle btn : btnList) {
			if (btn.contains(curPoint)) {
				l_rectPoint.setText("Rect(X,Y) : (" + btn.x + "," + btn.y + ")");
				intersect = true;
				break;
			}
		}
		if (!intersect) {
			l_rectPoint.setText("");
		}
		l_cursorPoint.setText("X,Y : (" + e.getX() + "," + e.getY() + ")");
		canvas.repaint();
	}

	public CopyOnWriteArrayList<Rectangle> getBtnList() {
		return btnList;
	}

	public void setBtnList(CopyOnWriteArrayList<Rectangle> btnList) {
		this.btnList = btnList;
	}

	public Image getBackImg() {
		return backImg;
	}

	public void setBackImg(Image backImg) {
		this.backImg = backImg;
	}

	public static void main(String[] args) {
		new myPaintMain();
	}
}
