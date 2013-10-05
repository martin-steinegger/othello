package de.org.lmu.othello.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ki.Global;
import ki.KiPlayer;
import szte.mi.Move;
import de.org.lmu.othello.player.GuiPlayer;
import de.org.lmu.othello.service.GameEngine;
import de.org.lmu.othello.service.PlayerBlackState;

public class JOthelloGui extends JFrame implements ActionListener, MoveCallback {

	private static final long serialVersionUID = 1L;
	private GameEngine gameEngine;
	final JPanel gameMap = new JPanel();
	final JPanel infobar = new JPanel();
	final JLabel statusBar = new JLabel();
	final JButton passButton = new JButton();
	final int rowCount = Global.ROW;
	final int cellCount = Global.COL;
	final int fieldCount = cellCount * rowCount;
	final GameField gameField[] = new GameField[fieldCount];
	private static Move currentMove;

	public JOthelloGui() {
		init();
	}

	public void init() {
		setLayout(new BorderLayout());
		this.add(gameMap, BorderLayout.CENTER);

		passButton.setEnabled(false);
		passButton.setText("Pass");
		passButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentMove = null;
				if (gameEngine.nextMovePlayerBlack() == false) {
					gameEngine.nextMovePlayerWhite();
				}
				passButton.setEnabled(false);
				gameEngine.nextMovePlayerWhite();
			}
		});
		passButton.setBackground(new Color(240, 110, 10));
		infobar.setBackground(new Color(110, 50, 0));
		
		infobar.add(statusBar);
		infobar.add(passButton);
		this.add(infobar, BorderLayout.SOUTH);
		gameMap.setLayout(new GridLayout(8, 8));
		gameMap.setPreferredSize(new Dimension(650, 650));
		gameMap.setBackground(new Color(0, 160, 0));
		for (int i = 0; i < fieldCount; i++) {
			gameField[i] = new GameField(i);
			gameField[i].addActionListener(this);
			gameMap.add(gameField[i]);
		}

		// Init GameEngine
		gameEngine = GameEngine.getInstance();
		gameEngine.setCallBack(this);
		gameEngine.initGui(new PlayerBlackState(), new GuiPlayer(),
				new KiPlayer());
		
		setLabel(Global.BLACK);
	}

	public static void main(String[] args) {
		JOthelloGui othGui = new JOthelloGui();
		othGui.setTitle("Othello");
		othGui.setLocation(100, 100);

		othGui.pack();
		othGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		othGui.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() instanceof GameField) {
			GameField fieldCmd = (GameField) e.getSource();
			int index = fieldCmd.getIndexOfGameField();
			currentMove = new Move((index % rowCount), (index / rowCount));
			if (gameEngine.nextMovePlayerBlack() == false) {
				//gameEngine.nextMovePlayerWhite();
			}

			gameEngine.nextMovePlayerWhite();
		}
	}

	@Override
	public void refreshMatrix(int[][] matrix) {
		setLabel(gameEngine.getOppnentPlayer());
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				GameField fieldToChange = gameField[(x * rowCount) + y];
				fieldToChange.setFieldType(matrix[x][y]);
			}
		}
	}

	public static Move getCurrentMove() {
		return currentMove;
	}

	@Override
	public void playHasToPass() {
		statusBar.setText("Player has to pass");
		passButton.setEnabled(true);

	}

	@Override
	public void gameOver(int player) {
		statusBar.setText("Game Over. Player "+player+" wins");
	}

	private void setLabel(int player) {
		if (Global.BLACK == player) {
			statusBar.setText("Black");
		} else if (Global.WHITE == player) {
			statusBar.setText("White");
		} else {
			statusBar.setText("NaN");
		}
	}

}
