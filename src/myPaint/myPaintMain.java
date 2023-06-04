package myPaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class myPaintMain extends JFrame {

	JPanel p_back;
	myPaintCanvas canvas = new myPaintCanvas();
	
	Image backImg;

	public myPaintMain() {
		super("사각 찍기");
		
		p_back = new JPanel(new BorderLayout()) {
			@Override
            public void paint(Graphics g) {  
				setBackground(new Color(123, 123, 123, 123));
				
				Image image = Toolkit.getDefaultToolkit().getImage("src/images/back.png");

				g.drawImage(image, 0, 0, 500, 500, this); // 원래크기
            }
        };
        
		p_back.add(canvas);
		
		
		add(p_back);

		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setResizable(false);


		canvas.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setXY(e.getX(), e.getY(), 50);
			}
		});
	}

	public static void main(String[] args) {
		new myPaintMain();
	}
}
