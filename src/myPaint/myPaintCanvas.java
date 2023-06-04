package myPaint;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class myPaintCanvas extends JPanel {

private int x = -50, y = -50, diameter = 50;	
	
	@Override
	public void paint(Graphics g) {
		int r1 = (int)(Math.random() * 256);
		int r2 = (int)(Math.random() * 256);
		int r3 = (int)(Math.random() * 256);
		
		g.setColor(new Color(r1, r2, r3));
		g.fillRect(x - diameter/2, y - diameter/2, diameter, diameter);

		setOpaque(true);		
	}
	
	//기존 내용 그대로 남겨두고 추가하자
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	public void setXY(int x, int y, int diameter) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		
		repaint();
	}
}
