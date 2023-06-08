package myPaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintPanel extends JPanel {
	private DrawFrame parent = null;
	private ArrayList<Rectangle> btnList = null;

	private Image backImg = null;
	private int panelWidth = 0;
	private int panelHeight = 0;

	private int x = -1;
	private int y = -1;
	private int width = -1;
	private int heigth = -1;

	int r1 = (int) (Math.random() * 256);
	int r2 = (int) (Math.random() * 256);
	int r3 = (int) (Math.random() * 256);

	public PaintPanel(DrawFrame source) {
		parent = source;
		setInit();
		// setOpaque(true); //투명하게
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backImg, 0, 0, this);

		g.setColor(new Color(r1, r2, r3));
		g.drawRect(x, y, width, heigth);

		for (Rectangle btn : btnList) {
			g.fillRect(btn.x, btn.y, btn.width, btn.height);
		}

	}

	// 기존 내용 그대로 남겨두고 추가하자
	@Override
	public void update(Graphics g) {
		paint(g);
		setOpaque(true);
	}

	public void setXY(Point xy, int witdh, int height) {
		this.x = xy.x;
		this.y = xy.y;
		this.width = witdh;
		this.heigth = height;

		repaint();
	}

	public void setInit() {
		btnList = parent.getBtnList();
		backImg = parent.getBackImg();

		panelWidth = parent.getBackImgWidth();
		panelHeight = parent.getBackImgHeight();

		width = parent.getBtnWidth();
		heigth = parent.getBtnHeight();

		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}
}