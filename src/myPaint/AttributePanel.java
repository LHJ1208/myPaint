package myPaint;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class AttributePanel extends JPanel implements KeyListener {
	DrawFrame parent = null;

	JLabel l_pointX, l_pointY, l_btnWidth, l_btnHeight, l_btnColNum, l_btnRowNum, l_btnColGap, l_btnRowGap;
	JFormattedTextField ftf_pointX, ftf_pointY, ftf_btnWidth, ftf_btnHeight, ftf_btnColNum, ftf_btnRowNum,
			ftf_btnColGap, ftf_btnRowGap;

	NumberFormatter numFormat = null;

	final int DEFAULT_COLUMNS = 2;

	public AttributePanel(DrawFrame source) {

		parent = source;
		l_pointX = new JLabel("X");
		l_pointY = new JLabel("Y");
		l_btnWidth = new JLabel("Width");
		l_btnHeight = new JLabel("Height");
		l_btnColNum = new JLabel("ColNum");
		l_btnRowNum = new JLabel("RowNum");
		l_btnColGap = new JLabel("ColGap");
		l_btnRowGap = new JLabel("RowGap");

		numFormat = new NumberFormatter();

		ftf_pointX = new JFormattedTextField(numFormat);
		ftf_pointY = new JFormattedTextField(numFormat);
		ftf_btnWidth = new JFormattedTextField(numFormat);
		ftf_btnHeight = new JFormattedTextField(numFormat);
		ftf_btnColNum = new JFormattedTextField(numFormat);
		ftf_btnRowNum = new JFormattedTextField(numFormat);
		ftf_btnColGap = new JFormattedTextField(numFormat);
		ftf_btnRowGap = new JFormattedTextField(numFormat);

		ftf_pointX.setColumns(DEFAULT_COLUMNS + 1);
		ftf_pointY.setColumns(DEFAULT_COLUMNS + 1);
		ftf_btnWidth.setColumns(DEFAULT_COLUMNS + 1);
		ftf_btnHeight.setColumns(DEFAULT_COLUMNS + 1);
		ftf_btnColNum.setColumns(DEFAULT_COLUMNS);
		ftf_btnRowNum.setColumns(DEFAULT_COLUMNS);
		ftf_btnColGap.setColumns(DEFAULT_COLUMNS);
		ftf_btnRowGap.setColumns(DEFAULT_COLUMNS);

		setAttChild();

		ftf_pointX.addKeyListener(this);
		ftf_pointY.addKeyListener(this);
		ftf_btnWidth.addKeyListener(this);
		ftf_btnHeight.addKeyListener(this);
		ftf_btnColNum.addKeyListener(this);
		ftf_btnRowNum.addKeyListener(this);
		ftf_btnColGap.addKeyListener(this);
		ftf_btnRowGap.addKeyListener(this);

		add(l_pointX);
		add(ftf_pointX);
		add(l_pointY);
		add(ftf_pointY);
		add(l_btnWidth);
		add(ftf_btnWidth);
		add(l_btnHeight);
		add(ftf_btnHeight);
		add(l_btnColNum);
		add(ftf_btnColNum);
		add(l_btnRowNum);
		add(ftf_btnRowNum);
		add(l_btnColGap);
		add(ftf_btnColGap);
		add(l_btnRowGap);
		add(ftf_btnRowGap);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char input = e.getKeyChar();
		if (!Character.isDigit(input)) {
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void setEditable(boolean editable) {
		ftf_pointX.setEditable(editable);
		ftf_pointY.setEditable(editable);
		ftf_btnWidth.setEditable(editable);
		ftf_btnHeight.setEditable(editable);
		ftf_btnColNum.setEditable(editable);
		ftf_btnRowNum.setEditable(editable);
		ftf_btnColGap.setEditable(editable);
		ftf_btnRowGap.setEditable(editable);
	}

	public void setAttChild() {
		ftf_pointX.setText(Integer.toString(parent.getBtnPoint().x));
		ftf_pointY.setText(Integer.toString(parent.getBtnPoint().y));
		ftf_btnWidth.setText(Integer.toString(parent.getBtnWidth()));
		ftf_btnHeight.setText(Integer.toString(parent.getBtnHeight()));
		ftf_btnColNum.setText(Integer.toString(parent.getBtnColNum()));
		ftf_btnRowNum.setText(Integer.toString(parent.getBtnRowNum()));
		ftf_btnColGap.setText(Integer.toString(parent.getBtnColGap()));
		ftf_btnRowGap.setText(Integer.toString(parent.getBtnRowGap()));
	}

	public boolean setAttParent() {
		boolean result = true;

		try {
			int x = Integer.parseInt(ftf_pointX.getText());
			int y = Integer.parseInt(ftf_pointY.getText());
			int width = Integer.parseInt(ftf_btnWidth.getText());
			int height = Integer.parseInt(ftf_btnHeight.getText());
			int colNum = Integer.parseInt(ftf_btnColNum.getText());
			int rowNum = Integer.parseInt(ftf_btnRowNum.getText());
			int colGap = Integer.parseInt(ftf_btnColGap.getText());
			int rowGap = Integer.parseInt(ftf_btnRowGap.getText());

			int drawWidth = width * colNum + colGap * (colNum - 1);
			int drawHeight = height * rowNum + rowGap * (rowNum - 1);

			if (drawWidth > parent.getBackImgWidth() || drawHeight > parent.getBackImgHeight())
				return false;
			if (x < 0 || x > parent.getBackImgWidth() - drawWidth)
				return false;
			if (y < 0 || y > parent.getBackImgHeight() - drawHeight)
				return false;
			if (width < 1 || height < 1 || colNum < 1 || rowNum < 1 || colGap < 1 || rowGap < 1)
				return false;

			parent.setBtnPoint(new Point(x, y));
			parent.setBtnWidth(width);
			parent.setBtnHeight(height);
			parent.setBtnColNum(colNum);
			parent.setBtnRowNum(rowNum);
			parent.setBtnColGap(colGap);
			parent.setBtnRowGap(rowGap);
			parent.setDrawWidth(drawWidth);
			parent.setDrawHeight(drawHeight);
			result = true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return result;
	}
}
