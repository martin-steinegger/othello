package ki;

import java.util.ArrayList;
import java.util.Random;

import szte.mi.Move;
import szte.mi.Player;

public class KiPlayer implements Player {
	private int currentPlayer;
	private OthelloBoard board = new OthelloBoard();
	public static final int MIN_HAS_WON = Integer.MAX_VALUE;
	public static final int MAX_HAS_WON = Integer.MIN_VALUE;
	private static int MAXLEVEL = 7;
	private static int MAXENDGAME = 11;
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	public static Random rnd;
	int passCounter = 0;

	public KiPlayer() {

	}

	@Override
	public void init(int order, long t, Random rnd) {
		KiPlayer.rnd = rnd;
		passCounter = 0;
		board.init();
		scoreOfConfiguration = NORESULT;
		switch (order) {
		case 0:
			currentPlayer = Global.BLACK;
			break;
		case 1:
			currentPlayer = Global.WHITE;
			break;
		}
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null) {
			doStep(prevMove, inversPlayer(currentPlayer));
		} else {
			passCounter++;
		}

		Move retMove = retreiveBestMove();
		if (retMove != null)
			doStep(retMove, currentPlayer);
		return retMove;
	}

	public static int inversPlayer(int playerType) {
		return (playerType == Global.WHITE) ? Global.BLACK : Global.WHITE;
	}

	public void redoStep(Move move, int player) {
		board.redoStepGameMatrix(new PlayerMove(move.x, move.y, player));
	}

	public void doStep(Move move, int player) {
		board.doStep(move, player);
	}

	public ArrayList<Move> nextSteps(int player) {
		return board.getNextPossibleSteps(player);
	}

	public boolean isKillerMove(Move move) {
		if (move.x == 0 && move.y == 0) {
			return true;
		} else if (move.x == 0 && move.y == OthelloBoard.GAME_SIZE - 1) {
			return true;
		} else if (move.x == OthelloBoard.GAME_SIZE - 1 && move.y == 0) {
			return true;
		}
		if (move.x == OthelloBoard.GAME_SIZE - 1
				&& move.y == OthelloBoard.GAME_SIZE - 1) {
			return true;
		} else
			return false;

	}

	public int scoreOfConfiguration = NORESULT;

	public Move retreiveBestMove() {

		int bestScore = NORESULT;
		Move bestMove = null;
		int alpha = LSCORE;
		int beta = HSCORE;

		for (Move move : board.calcNextPossibleSteps(currentPlayer)) {

			board.doStep(move, currentPlayer);
			Analyse.countMatrix(board);
			int newScore;

			if (Analyse.getCounterByKey(Global.UNSET) > MAXENDGAME) {
				newScore = -alphaBeta(-beta, -Math.max(alpha, bestScore),
						inversPlayer(currentPlayer), MAXLEVEL - 1);
			} else {
				newScore = -alphaBeta(-beta, -Math.max(alpha, bestScore),
						inversPlayer(currentPlayer),
						Analyse.getCounterByKey(Global.UNSET) + 1);
			}

			if (newScore > bestScore) {
				bestScore = newScore;
				bestMove = move;

			}
			board.redoStepGameMatrix(new PlayerMove(move.x, move.y,
					currentPlayer));
		}

		return bestMove;
	}

	public static final int NORESULT = 0x80000000;
	public static final int LSCORE = 0x80000001;
	public static final int HSCORE = 0x7FFFFFFF;

	protected int alphaBeta(int alpha, int beta, int player, int depth) {
		int bestScore = NORESULT;
		final ArrayList<Move> nextSteps = board.calcNextPossibleSteps(player);

		for (Move move : nextSteps) {

			board.doStep(move, player);

			int newScore;
			if (depth <= 1) {
				newScore = evaluate(player);
			} else {
				newScore = -alphaBeta(-beta, -Math.max(alpha, bestScore),
						inversPlayer(player), depth - 1);
			}

			if (newScore > bestScore) {
				bestScore = newScore;

				if (bestScore >= beta) {
					return bestScore;
				}
			}
			board.redoStepGameMatrix(new PlayerMove(move.x, move.y, player));
		}

		if (bestScore == NORESULT) {
			if (board.calcNextPossibleSteps(inversPlayer(player)).size() != 0) {
				bestScore = -alphaBeta(-beta, -alpha, inversPlayer(player),
						depth - 1);
			} else {
				bestScore = endGameScore(player);
			}
		}

		return bestScore;
	}

	private int evaluate(int player) {
		return scoreForPlayer(player)
				- scoreForPlayer(inversPlayer(player));
	}

	private int scoreForPlayer(int player) {
		int positionScore = 0;
		int passScore = 0;
//		if (currentPlayer == Global.BLACK && player == Global.BLACK
//				&& passCounter % 2 == 1) {
//
//			if (board.calcNextPossibleSteps(inversPlayer(player)).size() == 0) {
//				passScore = 12;
//			}
//
//		}
		long playerboard = board.getBoard(player);
		int pieceScore = Analyse.bitCount(playerboard);
		positionScore += 2 * Analyse
				.bitCount(playerboard & 0x8100000000000081L); // corners
		positionScore += Analyse.bitCount(playerboard & 0xBD008181818100BDL); // edge
		positionScore -= 3 * Analyse
				.bitCount(playerboard & 0x42C300000000C342L);
		return passScore + pieceScore + positionScore;
	}

	protected int endGameScore(int player) {
		Analyse.countMatrix(board);
		int pieceDiff = Analyse.getCounterByKey(player)
				- Analyse.getCounterByKey(inversPlayer(player));

		if (pieceDiff < 0) {
			return LSCORE + 1 + Analyse.getCounterByKey(player); // LOSE
		} else if (pieceDiff == 0) {
			return -3;
		} else {
			return HSCORE - 1
					- Analyse.getCounterByKey(inversPlayer(player)); // win
		}
	}

}
