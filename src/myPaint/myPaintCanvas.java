package myPaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class myPaintCanvas extends JPanel {
	private CopyOnWriteArrayList<Rectangle> btnList = null;

	private Image backImg = null;
	private int panelWidth = 0;
	private int panelHeight = 0;

	private int x = -1 * myPaintMain.btnSize;
	private int y = -1 * myPaintMain.btnSize;
	private int diameter = myPaintMain.btnSize;

	int r1 = (int) (Math.random() * 256);
	int r2 = (int) (Math.random() * 256);
	int r3 = (int) (Math.random() * 256);

	public myPaintCanvas(myPaintMain source) {
		this.btnList = source.getBtnList();
		this.backImg = source.getBackImg();
		
		panelWidth = source.frameWidth;
		panelHeight = source.frameHeight;
		// setOpaque(true); //투명하게
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backImg, 0, 0, panelWidth, panelHeight, null);

		g.setColor(new Color(r1, r2, r3));
		g.drawRect(x, y, diameter, diameter);

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

	public void setXY(int x, int y, int diameter) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;

		repaint();
	}

	public void setXY(Point xy, int diameter) {
		this.x = xy.x;
		this.y = xy.y;
		this.diameter = diameter;

		repaint();
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
}
