package myPaint;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DrawFrame extends JFrame {

	JPanel p_north, p_south, p_west, p_east;

	JScrollPane sp_center;

	JMenuBar jmb;
	JMenu m_file, m_edit;
	JMenuItem i_newFile, i_openFile, i_saveFile, i_openImage, i_undo, i_redo;

	JLabel l_cursorPoint, l_rectPoint;

	JButton b_undo, b_redo, b_attSet, b_btnAdd;

	PaintPanel p_paint = null;
	AttributePanel p_attFixed = null, p_attEdit = null;
	private ArrayList<Rectangle> btnList = null;
	private LinkedList<Rectangle> undoList = null;
	private LinkedList<Rectangle> redoList = null;

	String pathBackImage = "src/images/SeatChart01.png";

	Image backImg = null;
	int backImgWidth = 0;
	int backImgHeight = 0;

	String pathFile = "D:/njm/SeatList.ser";
	String nameFile = null;

	OIS_SeatChart ois_SeatChart = null;
	OOS_SeatChart oos_SeatChart = null;

	final Point DEFAULT_BTN_POINT = new Point(0, 0);
	private Point btnPoint = DEFAULT_BTN_POINT;

	final int DEFAULT_BTN_SIZE = 50;
	private int btnWidth = DEFAULT_BTN_SIZE;
	private int btnHeight = DEFAULT_BTN_SIZE;

	final int DEFAULT_BTN_NUM = 1;
	private int btnColNum = DEFAULT_BTN_NUM;
	private int btnRowNum = DEFAULT_BTN_NUM;

	final int DEFAULT_BTN_GAP = 10;
	private int btnColGap = DEFAULT_BTN_GAP;
	private int btnRowGap = DEFAULT_BTN_GAP;

	private int drawWidth = DEFAULT_BTN_SIZE;
	private int drawHeight = DEFAULT_BTN_SIZE;

	public DrawFrame() {
		super("사각 찍기");

		ois_SeatChart = new OIS_SeatChart();
		oos_SeatChart = new OOS_SeatChart();

		jmb = new JMenuBar();
		m_file = new JMenu("File");
		i_newFile = new JMenuItem("New File");
		i_openFile = new JMenuItem("Open ...");
		i_saveFile = new JMenuItem("Save");
		i_openImage = new JMenuItem("Open Image ...");

		m_file.add(i_newFile);
		m_file.add(i_openFile);
		m_file.addSeparator();
		m_file.add(i_saveFile);
		m_file.addSeparator();
		m_file.add(i_openImage);

		m_edit = new JMenu("Edit");
		i_undo = new JMenuItem("Undo");
		i_redo = new JMenuItem("Redo");

		m_edit.add(i_undo);
		m_edit.add(i_redo);

		jmb.add(m_file);
		jmb.add(m_edit);
		setJMenuBar(jmb);

		m_file.setMnemonic('f');
		i_newFile.setMnemonic('n');
		i_openFile.setMnemonic('o');
		i_saveFile.setMnemonic('s');
		i_openImage.setMnemonic('i');
		m_edit.setMnemonic('e');
		i_undo.setMnemonic('u');
		i_redo.setMnemonic('r');

		btnList = new ArrayList<>();
		undoList = new LinkedList<>();
		redoList = new LinkedList<>();
		initBackImg();

		p_paint = new PaintPanel(this);
		sp_center = new JScrollPane(p_paint, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		p_north = new JPanel(new GridLayout(2, 0));
		p_north.setBorder(BorderFactory.createTitledBorder("버튼 속성"));

		p_attFixed = new AttributePanel(this);
		p_attEdit = new AttributePanel(this);
		p_attFixed.setEditable(false);

		p_north.add(p_attFixed);
		p_north.add(p_attEdit);

		p_south = new JPanel();

		l_rectPoint = new JLabel("");
		l_cursorPoint = new JLabel("X,Y : (?,?)");

		p_south.add(l_rectPoint);
		p_south.add(l_cursorPoint);

		p_west = new JPanel(new GridLayout(2, 0));
		b_undo = new JButton("Undo");
		b_redo = new JButton("Redo");
		p_west.add(b_undo);
		p_west.add(b_redo);

		b_undo.setMnemonic('u');
		b_redo.setMnemonic('r');

		p_east = new JPanel(new GridLayout(2, 0));
		b_attSet = new JButton("Set");
		b_btnAdd = new JButton("Add");
		p_east.add(b_attSet);
		p_east.add(b_btnAdd);

		b_attSet.setMnemonic('s');
		b_btnAdd.setMnemonic('a');

		add(p_north, "North");
		add(sp_center, "Center");
		add(p_south, "South");
		add(p_west, "West");
		add(p_east, "East");

		setSide();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setResizable(false);

		p_paint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				addBtn(e.getX(), e.getY());
			}
		});

		p_paint.addMouseMotionListener(new MouseMotionListener() {
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
				p_paint.repaint();
			}
		});

		i_saveFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog((JFrame) getParent(), "SeatChart Save", FileDialog.SAVE);
				fd.setVisible(true);

				String tmpPath = fd.getDirectory() + fd.getFile();

				if (tmpPath.equals("null") || tmpPath.equals("nullnull")) {
					JOptionPane.showMessageDialog(getParent(), "열기 실패");
				} else {
					if (btnList.size() > 0) {
						VO_SeatChart saveData = new VO_SeatChart(pathBackImage, btnList);
						if (oos_SeatChart.writeSeatChart(tmpPath, saveData)) {
							JOptionPane.showMessageDialog(getParent(), "저장 성공");
						} else {
							JOptionPane.showMessageDialog(getParent(), "저장 실패");
						}
					}
				}
			}
		});

		i_openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog((JFrame) getParent(), "SeatChart Load", FileDialog.LOAD);
				fd.setVisible(true);

				String tmpPath = fd.getDirectory() + fd.getFile();

				if (tmpPath.equals("null") || tmpPath.equals("nullnull")) {
					JOptionPane.showMessageDialog(getParent(), "열기 실패");
				} else { // 올바른 유형의 파일이 아닐 경우 예외처리 필요
					pathFile = tmpPath;
					VO_SeatChart loadData = null;
					if ((loadData = ois_SeatChart.readSeatChart(pathFile)) != null) {
						undoList.clear();
						redoList.clear();
						pathBackImage = loadData.getBackImgSrcPath();
						btnList = loadData.getRectangleList();
						initBackImg();
						p_paint.setInit();
						setSide();
						p_paint.repaint();
						pack();
						JOptionPane.showMessageDialog(getParent(), "열기 성공");
					} else {
						JOptionPane.showMessageDialog(getParent(), "열기 실패");
					}
				}
			}
		});

		i_openImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog((JFrame) getParent(), "Background Image Load", FileDialog.LOAD);
				fd.setVisible(true);

				String tmpPath = fd.getDirectory() + fd.getFile();

				if (tmpPath.equals("null") || tmpPath.equals("nullnull")) {
					JOptionPane.showMessageDialog(getParent(), "열기 실패");
				} else { // 올바른 유형의 파일이 아닐 경우 예외처리 필요
					undoList.clear();
					redoList.clear();
					pathBackImage = tmpPath;
					btnList.clear();
					initBackImg();
					p_paint.setInit();
					setSide();
					p_paint.repaint();
					pack();
					JOptionPane.showMessageDialog(getParent(), "열기 성공");
				}
			}
		});

		i_undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actUndo();
			}
		});

		i_redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actRedo();
			}
		});

		b_undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actUndo();
			}
		});

		b_redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actRedo();
			}
		});

		b_attSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actSet();
			}
		});

		b_btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBtn(btnPoint.x, btnPoint.y);
			}
		});
	}

	public void printPoint(MouseEvent e) {
		Point curPoint = new Point(e.getX(), e.getY());
		boolean intersect = false;
		p_paint.setXY(curPoint, drawWidth, drawHeight);
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
		p_paint.repaint();
	}

	public void actUndo() {
		if (undoList.size() > 0) {
			Rectangle peekRect = undoList.peek();
			if (btnList.contains(peekRect)) {
				redoList.push(peekRect);
				btnList.remove(peekRect);
				undoList.pop();
				p_paint.repaint();
			} else {
				redoList.push(peekRect);
				btnList.add(peekRect);
				undoList.pop();
				p_paint.repaint();
			}
		}
	}

	public void actRedo() {
		if (redoList.size() > 0) {
			Rectangle peekRect = redoList.peek();
			if (btnList.contains(peekRect)) {
				undoList.push(peekRect);
				btnList.remove(peekRect);
				redoList.pop();
				p_paint.repaint();
			} else {
				undoList.push(peekRect);
				btnList.add(peekRect);
				redoList.pop();
				p_paint.repaint();
			}
		}
	}

	public void actSet() {
		if (p_attEdit.setAttParent()) {
			JOptionPane.showMessageDialog(getParent(), "설정 완료");
			p_attFixed.setAttChild();
		} else {
			JOptionPane.showMessageDialog(getParent(), "올바른 값을 입력하시오.");
		}
	}

	public void addBtn(int x, int y) {
		boolean intersect = false;
		Rectangle inputRect = new Rectangle(x, y, drawWidth, drawHeight);
		Rectangle interRect = null;

		if (x >= 0 && x < backImgWidth - drawWidth && y >= 0 && y < backImgHeight - drawHeight) {
			for (Rectangle btn : btnList) {
				if (!intersect && btn.intersects(inputRect)) {
					intersect = true;
				}
				if (btn.contains(x, y)) {
					l_rectPoint.setText("Rect(X,Y) : (" + btn.x + "," + btn.y + ")");
					interRect = btn;
					break;
				}
			}

			if (!intersect) {
				if (btnColNum == 1 && btnRowNum == 1) {
					btnList.add(inputRect);
					undoList.push(inputRect);
					p_paint.repaint();
				} else {
					int colGap = btnWidth + btnColGap;
					int rowGap = btnHeight + btnRowGap;
					for (int i = 0; i < btnRowNum; i++) {
						for (int j = 0; j < btnColNum; j++) {
							Rectangle tmpRect = new Rectangle(x + j * (colGap), y + i * (rowGap), btnWidth, btnHeight);
							btnList.add(tmpRect);
							undoList.push(tmpRect);
						}
					}
					p_paint.repaint();
				}
			} else if (interRect != null) {
				int reply = JOptionPane.showConfirmDialog(getParent(), "선택한 버튼을 삭제할까요?", "버튼 삭제",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (reply == 0) {
					undoList.push(interRect);
					btnList.remove(interRect);
					p_paint.repaint();
				}
			}
		}

	}

	public void initBackImg() {
		backImg = new ImageIcon(pathBackImage).getImage();
		backImgWidth = backImg.getWidth(null);
		backImgHeight = backImg.getHeight(null);
	}

	public void setSide() {
		if (p_north.getPreferredSize().width > p_paint.getPreferredSize().width) {
			int over = p_north.getPreferredSize().width - p_paint.getPreferredSize().width;
			p_west.setPreferredSize(new Dimension(over / 2 + 1, 1));
			p_east.setPreferredSize(new Dimension(over / 2 + 1, 1));
		} else {
			p_west.setPreferredSize(b_undo.getPreferredSize());
			p_east.setPreferredSize(b_btnAdd.getPreferredSize());
		}
	}

	public ArrayList<Rectangle> getBtnList() {
		return btnList;
	}

	public void setBtnList(ArrayList<Rectangle> btnList) {
		this.btnList = btnList;
	}

	public Image getBackImg() {
		return backImg;
	}

	public void setBackImg(Image backImg) {
		this.backImg = backImg;
	}

	public int getBackImgWidth() {
		return backImgWidth;
	}

	public void setBackImgWidth(int backImgWidth) {
		this.backImgWidth = backImgWidth;
	}

	public int getBackImgHeight() {
		return backImgHeight;
	}

	public void setBackImgHeight(int backImgHeight) {
		this.backImgHeight = backImgHeight;
	}

	public Point getBtnPoint() {
		return btnPoint;
	}

	public void setBtnPoint(Point btnPoint) {
		this.btnPoint = btnPoint;
	}

	public int getBtnWidth() {
		return btnWidth;
	}

	public void setBtnWidth(int btnWidth) {
		this.btnWidth = btnWidth;
	}

	public int getBtnHeight() {
		return btnHeight;
	}

	public void setBtnHeight(int btnHeight) {
		this.btnHeight = btnHeight;
	}

	public int getBtnColNum() {
		return btnColNum;
	}

	public void setBtnColNum(int btnColNum) {
		this.btnColNum = btnColNum;
	}

	public int getBtnRowNum() {
		return btnRowNum;
	}

	public void setBtnRowNum(int btnRowNum) {
		this.btnRowNum = btnRowNum;
	}

	public int getBtnColGap() {
		return btnColGap;
	}

	public void setBtnColGap(int btnColGap) {
		this.btnColGap = btnColGap;
	}

	public int getBtnRowGap() {
		return btnRowGap;
	}

	public void setBtnRowGap(int btnRowGap) {
		this.btnRowGap = btnRowGap;
	}

	public int getDrawWidth() {
		return drawWidth;
	}

	public void setDrawWidth(int drawWidth) {
		this.drawWidth = drawWidth;
	}

	public int getDrawHeight() {
		return drawHeight;
	}

	public void setDrawHeight(int drawHeight) {
		this.drawHeight = drawHeight;
	}

	public static void main(String[] args) {
		new DrawFrame();
	}
}
