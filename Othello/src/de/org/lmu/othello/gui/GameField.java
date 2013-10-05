package de.org.lmu.othello.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ki.Global;

public class GameField extends JButton {

	private static final long serialVersionUID = 1L;
	private int indexOfGameField;
	final private static ImageIcon iconTransparent = new ImageIcon(
			ClassLoader.getSystemResource("resource/Transparent.gif"));
	final private static ImageIcon iconWhiteCircle = new ImageIcon(
			ClassLoader.getSystemResource("resource/WhiteCircle.gif"));
	final private static ImageIcon iconBlackCircle = new ImageIcon(
			ClassLoader.getSystemResource("resource/BlackCircle.gif"));
	final private static ImageIcon iconNextStep = new ImageIcon(
			ClassLoader.getSystemResource("resource/NextStep.gif"));

	public GameField(int index) {
		super("", iconTransparent);
		indexOfGameField = index;
		this.setBackground(new Color(0, 0, 0, 0));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void setFieldType(int type) {
		switch (type) {
		case Global.BLACK:
			this.setIcon(iconBlackCircle);
			break;
		case Global.WHITE:
			this.setIcon(iconWhiteCircle);
			break;
		case Global.UNSET:
			this.setIcon(iconTransparent);
			break;
		case Global.NEXTSTEP:
			this.setIcon(iconNextStep);
			break;
		}
	}

	public int getIndexOfGameField() {
		return indexOfGameField;
	}
}
