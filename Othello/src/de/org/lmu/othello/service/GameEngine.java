package de.org.lmu.othello.service;

import java.util.ArrayList;
import java.util.Random;

import ki.Analyse;
import ki.Global;
import ki.OthelloBoard;
import ki.PlayerMove;
import szte.mi.Move;
import szte.mi.Player;
import de.org.lmu.othello.gui.MoveCallback;

public class GameEngine implements Cloneable {

	private State currentState;

	private int currentStepNumber = 0;
	private int passCounter = 0;

	private Move previousMove = null;
	private OthelloBoard board = new OthelloBoard();
	private MoveCallback callBack;
	private Player playerBlack;
	private Player playerWhite;
	private boolean automgame;
	private static GameEngine instance = new GameEngine();

	private GameEngine() {
		initMatrix();
	}

	public void setCallBack(MoveCallback back) {
		callBack = back;
	}

	public void initGui(final State startState, final Player playerBlack,
			final Player playerWhite) {
		automgame = true;
		currentStepNumber = 0;
		passCounter = 0;
		this.currentState = startState;
		this.playerBlack = playerBlack;
		playerBlack.init(0, 0, new Random());
		this.playerWhite = playerWhite;
		playerWhite.init(1, 0, new Random());
		this.board.init();
		refreshGuiMatrix(currentState.getCurrentPlayer());
	}

	public void startAutomaticGame() {
		while (automgame) {
			nextMovePlayerBlack();

			nextMovePlayerWhite();
		}
	}
	
	public int getCurrentPlayer(){
		return currentState.getCurrentPlayer();
	}

	
	public int getOppnentPlayer(){
		return currentState.getOpponent();
	}
	
	public boolean nextMovePlayerBlack() {
		State tmpStat = this.currentState;
		this.currentState = currentState.nextMovePlayerBlack(this, playerBlack);
		return (tmpStat != currentState) ? true : false;
	}

	public boolean nextMovePlayerWhite() {
		State tmpStat = this.currentState;
		this.currentState = currentState.nextMovePlayerWhite(this, playerWhite);
		return (tmpStat != currentState) ? true : false;
	}

	public boolean makeMovePlayer(final PlayerMove move) {
		if (move == null) {
			passCounter++;
			if (passCounter == 2) {
				gameOver();
			}
			refreshGuiMatrix(currentState.getOpponent());
			return true;
		}
		if (board.validateMove(move, move.getPlayerType()) == true) {
			passCounter = 0;

			currentStepNumber++;
			board.doStep(move, move.getPlayerType());
			// set the previousMove
			this.previousMove = move.getMove();

			refreshGuiMatrix(move.getOpponent());
			if (board.hasNextSteps() == false) {
				callBack.playHasToPass();
			}

			if (currentStepNumber == 60) {
				gameOver();
			}

			return true;
		}
		return false;
	}

	private void refreshGuiMatrix(int player) {
		ArrayList<Move> nextSteps = board.getNextPossibleSteps(player);
		int[][] field = board.mapGameFieldToIntArray();
		for (Move nextMove : nextSteps) {
			field[nextMove.y][nextMove.x] = Global.NEXTSTEP;
		}
		if (callBack != null)
			this.callBack.refreshMatrix(field);
		for (Move nextMove : nextSteps) {
			field[nextMove.y][nextMove.x] = Global.UNSET;
		}
	}

	public static int winner(OthelloBoard board) {
		Analyse.countMatrix(board);
		if (Analyse.getCounterByKey(Global.BLACK) > Analyse
				.getCounterByKey(Global.WHITE))
			return Global.BLACK;
		else if (Analyse.getCounterByKey(Global.WHITE) > Analyse
				.getCounterByKey(Global.BLACK))
			return Global.WHITE;
		else
			return -1;
	}

	private void gameOver() {
		automgame = false;
		this.callBack.gameOver(winner(board));
	}

	public Move getPreviousMove() {
		return previousMove;
	}

	public static int[][] copy2d(final int[][] nums) {
		int[][] copy = new int[nums.length][];

		for (int i = 0; i < copy.length; i++) {
			int[] member = new int[nums[i].length];
			System.arraycopy(nums[i], 0, member, 0, nums[i].length);
			copy[i] = member;
		}

		return copy;
	}

	public ArrayList<Move> getNextPossibleMoves() {
		return board.getNextPossibleSteps(currentState.getCurrentPlayer());
	}

	public static GameEngine getInstance() {
		return instance;
	}

	@Override
	public GameEngine clone() throws CloneNotSupportedException {
		return (GameEngine) super.clone();
	}

	public void initMatrix() {
		board.init();
		refreshGuiMatrix(Global.BLACK);
	}
}
