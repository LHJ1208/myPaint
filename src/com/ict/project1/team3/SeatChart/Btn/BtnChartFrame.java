package com.ict.project1.team3.SeatChart.Btn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import com.ict.project1.team3.basicClass.OIS_Basic;
import com.ict.project1.team3.basicClass.OOS_Baisc;
import com.ict.project1.team3.basicClass.VO_Basic;

public class BtnChartFrame extends JFrame {
	JPanel p_center, p_south, p_west, p_east;

	JScrollPane sp_center;

	JMenuBar jmb;
	JMenu m_file, m_edit;
	JMenuItem i_openFile, i_saveFile, i_undo, i_redo;

	JLabel l_cursorPoint, l_rectPoint;

	JButton b_undo, b_redo;

	private ArrayList<JButton> btnList = null;
	private LinkedList<JButton> undoList = null;
	private LinkedList<JButton> redoList = null;

	private ArrayList<Rectangle> rectList = null;
	String pathBackImage = null;

	String pathFile = null;

	Image backImg = null;
	int backImgWidth = 0;
	int backImgHeight = 0;

	OIS_Basic<Rectangle> ois_SeatChart = null;
	OOS_Baisc<Rectangle> oos_SeatChart = null;

	ModifyBtnActionListener btbActionListener = null;

	public BtnChartFrame() {
		super("Modify BtnChart");

		btbActionListener = new ModifyBtnActionListener();

		ois_SeatChart = new OIS_Basic<Rectangle>();
		oos_SeatChart = new OOS_Baisc<Rectangle>();
		jmb = new JMenuBar();
		m_file = new JMenu("File");
		i_openFile = new JMenuItem("Open Sketch ...");
		i_saveFile = new JMenuItem("Save");

		m_file.add(i_openFile);
		m_file.addSeparator();
		m_file.add(i_saveFile);

		m_edit = new JMenu("Edit");
		i_undo = new JMenuItem("Undo");
		i_redo = new JMenuItem("Redo");

		m_edit.add(i_undo);
		m_edit.add(i_redo);

		jmb.add(m_file);
		jmb.add(m_edit);
		setJMenuBar(jmb);

		m_file.setMnemonic('f');
		m_edit.setMnemonic('e');
		i_undo.setMnemonic('u');
		i_redo.setMnemonic('r');

		btnList = new ArrayList<>();
		undoList = new LinkedList<>();
		redoList = new LinkedList<>();

		p_center = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				if (backImg != null) {
					g.drawImage(backImg, 0, 0, this);
				}
			}
		};
		p_center.setLayout(null);

		sp_center = new JScrollPane(p_center, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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

		p_east = new JPanel();

		add(sp_center, "Center");
		add(p_south, "South");
		add(p_west, "West");
		add(p_east, "East");

		// setLookAndFeel example
		// https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=javaking75&logNo=140170600514

//		   try{
//               UIManager.setLookAndFeel(
//                   "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//               SwingUtilities.updateComponentTreeUI(this);
//           //에러처리 블록
//           }catch (Exception e){
//               System.out.println(e+ "오류 발생");
//           }

//           try{
//               UIManager.setLookAndFeel(
//                   "javax.swing.plaf.metal.MetalLookAndFeel");
//               SwingUtilities.updateComponentTreeUI(this);
//           //에러처리 블록
//           }catch (Exception e){
//               System.out.println(e+ "오류 발생");
//           }

//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//			SwingUtilities.updateComponentTreeUI(this);
//
//		} catch (Exception e) {
//			System.out.println(e + "오류 발생");
//		}

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setResizable(false);

		p_center.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				printPoint(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});

		i_saveFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

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
					VO_Basic<Rectangle> loadData = null;
					if ((loadData = ois_SeatChart.readObject(pathFile)) != null) {
						undoList.clear();
						redoList.clear();
						pathBackImage = loadData.getBackImgSrcPath();
						rectList = loadData.getRectangleList();
						initBackImg();
						initBtnList();
						JOptionPane.showMessageDialog(getParent(), "열기 성공");
					} else {
						JOptionPane.showMessageDialog(getParent(), "열기 실패");
					}
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
				JButton btn = (JButton) e.getSource();
				btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
//				btn.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
//				btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//				btn.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED));
//				btn.setBorder(BorderFactory.createEmptyBorder());
//				btn.setBorder(BorderFactory.createMatteBorder(1, 5, 10, 20, Color.black));
//				btn.setBorder(BorderFactory.createEtchedBorder());
//				btn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
//				btn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
//				btn.setBorder(BorderFactory.createDashedBorder(getForeground()));
//				btn.setBorder(BorderFactory.createLineBorder(getForeground(), 2, true));
//				btn.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3)));
//				btn.setBorder(BorderFactory.createCompoundBorder());
//				btn.setBorder(BorderFactory.createTitledBorder("Title"));
				actUndo();
			}
		});

		b_redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				actRedo();
			}
		});
	}

	public void actUndo() {
	}

	public void actRedo() {
	}

	public void printPoint(MouseEvent e) {
		l_cursorPoint.setText("X,Y : (" + e.getX() + "," + e.getY() + ")");
	}

	public void initBackImg() {
		backImg = new ImageIcon(pathBackImage).getImage();
		backImgWidth = backImg.getWidth(null);
		backImgHeight = backImg.getHeight(null);

		p_center.setPreferredSize(new Dimension(backImgWidth, backImgHeight));
		p_center.repaint();
		pack();
		setLocationRelativeTo(null);
	}

	public void initBtnList() {
		if (rectList != null) {
			p_center.removeAll();

			Integer i = 1;
			for (Rectangle rect : rectList) {
				JButton btn = new JButton(pathBackImage);
				btn.setText(i.toString());
				btn.setBounds(rect);
				btn.addActionListener(btbActionListener);
				i++;
				p_center.add(btn);
			}
		}
	}

	public static void main(String[] args) {
		new BtnChartFrame();
	}
}
