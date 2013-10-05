package de.org.lmu.othello.gui;

import java.util.Random;

import ki.KiPlayer;
import szte.mi.Player;
import de.org.lmu.othello.player.RandomPlayer;
import de.org.lmu.othello.service.GameEngine;
import de.org.lmu.othello.service.PlayerBlackState;
import de.org.lmu.othello.service.State;

public class DebugGui implements MoveCallback {
	private static GameEngine gameEngine = GameEngine.getInstance();;

	public DebugGui(final State startState, final Player playerBlack,
			final Player playerWhite) {
		gameEngine.setCallBack(this);
		gameEngine.initGui(startState, playerBlack, playerWhite);
		gameEngine.startAutomaticGame();
	}

	@Override
	public void playHasToPass() {
		System.out.println("Pass");
	}

	@Override
	public void refreshMatrix(int[][] matrix) {
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix.length; x++) {

				System.out.print(matrix[y][x]);
				System.out.print("\t");
			}

			System.out.println();

		}
		System.out
				.println("---------------------------------------------------------");
	}

	public static void main(String[] args) {
		Player player1 = new RandomPlayer();
		player1.init(0, 4534534, new Random());
		Player player2 = new KiPlayer();
		player2.init(1, 4534534, new Random());
		new DebugGui(new PlayerBlackState(), player1, player2);
	}

	@Override
	public void gameOver(int player) {
		System.out.println("Player " + player + " win");
		System.out.println("Game Over");
		System.exit(1);
	}

}
