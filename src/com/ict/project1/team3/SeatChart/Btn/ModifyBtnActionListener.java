package com.ict.project1.team3.SeatChart.Btn;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

public class ModifyBtnActionListener implements ActionListener {
	JButton btn = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		btn = (JButton) e.getSource();
//		actSetText();
//		actSetForeground();
//		actSetBackground();
	}

	void actSetText() {
		String input = (String) JOptionPane.showInputDialog(btn.getParent(), "Input Text", "Set Text", -1, null, null,
				btn.getText());
		if (input != null) {
			btn.setText(input);
		}
	}

	void actSetForeground() {
		Color choice = new JColorChooser().showDialog(btn.getParent(), "Color Chooser", btn.getForeground());

		if (choice != null) {
			btn.setForeground(choice);
		}
	}

	void actSetBackground() {
		Color choice = new JColorChooser().showDialog(btn.getParent(), "Color Chooser", btn.getForeground());

		if (choice != null) {
			btn.setBackground(choice);
		}
	}
}
