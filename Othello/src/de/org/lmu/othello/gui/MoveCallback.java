package de.org.lmu.othello.gui;

public interface MoveCallback {
	public void playHasToPass();

	public void gameOver(int black);

	public void refreshMatrix(int[][] matrix);
}
