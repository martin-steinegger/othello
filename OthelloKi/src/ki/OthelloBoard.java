package ki;

import java.util.ArrayList;
import java.util.HashMap;

import szte.mi.Move;

public class OthelloBoard {
	final public static int GAME_SIZE = 8;
	private long[] fields = new long[2];
	private final long whiteStart = 0x1008000000L;
	private final long blackStart = 0x810000000L;
	private final boolean[] validArray = { false, false, false, false, false,
			false, false, false, false, false, false, true, true, true, true,
			true, true, true, true, false, false, true, true, true, true, true,
			true, true, true, false, false, true, true, true, true, true, true,
			true, true, false, false, true, true, true, true, true, true, true,
			true, false, false, true, true, true, true, true, true, true, true,
			false, false, true, true, true, true, true, true, true, true,
			false, false, true, true, true, true, true, true, true, true,
			false, false, true, true, true, true, true, true, true, true,
			false, false, false, false, false, false, false, false, false,
			false, false };
	// final int[] posCheck = { -1, 1, -9, -8, -7, 7, 8, 9 };
	private final int[] posCheck10x10 = { -1, 1, -11, -10, -9, 9, 10, 11 };
	private final int[] posCheck8x8 = { -1, 1, -9, -8, -7, 7, 8, 9 };
	private final int[] map8x8To10x10 = { 11, 12, 13, 14, 15, 16, 17, 18, 21,
			22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 41, 42,
			43, 44, 45, 46, 47, 48, 51, 52, 53, 54, 55, 56, 57, 58, 61, 62, 63,
			64, 65, 66, 67, 68, 71, 72, 73, 74, 75, 76, 77, 78, 81, 82, 83, 84,
			85, 86, 87, 88 };
	private int countNextSteps;
	private HashMap<PlayerMove, long[]> redoStepMap;
	private ArrayList<Move> nextSteps = null;

	public OthelloBoard() {
		redoStepMap = new HashMap<PlayerMove, long[]>();
		fields[0] = whiteStart;
		fields[1] = blackStart;
	}

	public void init() {
		fields[0] = whiteStart;
		fields[1] = blackStart;
	}

	public void setStep(final int posLine, final int player) {
		// shift um x positionen nach links
		fields[player - 1] = fields[player - 1] | (1L << posLine);
	}

	private void unsetStep(final int posLine, final int player) {
		fields[player - 1] = fields[player - 1] ^ (1L << posLine);
	}

	public boolean isSet(final int posLine, final long field) {
		return ((field & (1L << posLine)) != 0);
	}

	private boolean isSet(final int posLine, final int player) {
		return isSet(posLine, fields[player - 1]);
	}

	private boolean isValidMove(final int pos, final int posCheckIndex) {
		int index;
		try {
			index = map8x8To10x10[pos] + posCheck10x10[posCheckIndex];
		} catch (ArrayIndexOutOfBoundsException bound) {
			return false;
		}
		return validArray[index];
	}

	public int lineRunner(final int startPos, final int posCheckIndex,
			final int player, final int opponent, final long playerBoard,
			final long opponentBoard, final boolean rotate) {
		int moveLength = posCheck8x8[posCheckIndex];
		int tmpPos = startPos + moveLength;
		int flips = 0;

		if (isValidMove(startPos, posCheckIndex) == false)
			return 0;
		while (isSet(tmpPos, opponentBoard) == true) {
			if (isValidMove(tmpPos, posCheckIndex) == false) {
				setGameField(opponentBoard, opponent);
				setGameField(playerBoard, player);
				return 0;
			}
			if (rotate == true) {
				setStep(tmpPos, player);
				unsetStep(tmpPos, opponent);
			}
			tmpPos += moveLength;
			flips++;
		}

		if (isSet(tmpPos, playerBoard) == false) {
			setGameField(opponentBoard, opponent);
			setGameField(playerBoard, player);
			return 0;
		} else {
			return flips;
		}
	}

	public boolean validateMove(final Move move, final int player) {
		int ret = validation(convertMoveToLineNumber(move), player,
				inversPlayer(player), false);
		return (ret == 0) ? false : true;
	}

	private boolean validateMove(final int posLine, final int player) {
		int ret = validation(posLine, player, inversPlayer(player), false);
		return (ret == 0) ? false : true;
	}

	private int validation(final int posLine, final int player,
			final int opponent, final boolean rotate) {
		if (isSet(posLine, fields[player - 1] | fields[opponent - 1]))
			return 0;
		int check = 0;
		for (int i = 0; i < posCheck8x8.length; i++) {
			long playerBoard = fields[player - 1];
			long opponentBoard = fields[2 - (player - 1) - 1];

			check += lineRunner(posLine, i, player, opponent, playerBoard,
					opponentBoard, rotate);

		}
		return check;

	}

	public void setGameField(long field, int player) {
		fields[player - 1] = field;
	}

	private int rotateFields(final int posLine, final int player) {
		return validation(posLine, player, inversPlayer(player), true);

	}

	public void doStep(final Move move, final int player) {
		int stepLinePos = convertMoveToLineNumber(move);
		redoStepMap.put(new PlayerMove(move.x, move.y, player), new long[] {
				fields[0], fields[1] });
		rotateFields(stepLinePos, player);
		setStep(stepLinePos, player);
	}

	private int convertMoveToLineNumber(final Move move) {
		return (move.y * GAME_SIZE) + move.x;
	}

	public boolean hasNextSteps() {
		return (countNextSteps == 0) ? false : true;
	}

	public ArrayList<Move> calcNextPossibleSteps(final int player) {
		nextSteps = new ArrayList<Move>(64);
		this.countNextSteps = 0;
		for (int index = 0; index < GAME_SIZE * GAME_SIZE; index++) {
			// checks if the index is set in the or of black and white
			if (isSet(index, fields[0] | fields[1]) == false) {
				if (validateMove(index, player)) {
					nextSteps.add(new Move((index % GAME_SIZE),
							(index / GAME_SIZE)));
					this.countNextSteps++;
				}
			}
		}

		return nextSteps;
	}

	public int[][] mapGameFieldToIntArray() {
		int[][] array = new int[GAME_SIZE][GAME_SIZE];
		int row = -1;
		for (int i = 0; i < GAME_SIZE * GAME_SIZE; i++) {
			if (i % 8 == 0) {
				row++;
			}
			int arrayindex = i - row * 8;
			if (isSet(i, Global.BLACK)) {
				array[row][arrayindex] = Global.BLACK;
			} else if (isSet(i, Global.WHITE)) {
				array[row][arrayindex] = Global.WHITE;
			}
		}
		return array;
	}

	public int getValue(int x, int y) {
		return isSet(convertMoveToLineNumber(new Move(x, y)), Global.BLACK) ? Global.BLACK
				: isSet(convertMoveToLineNumber(new Move(x, y)), Global.WHITE) ? Global.WHITE
						: Global.UNSET;
	}

	public long getBoard(int player) {
		return fields[player - 1];
	}

	public void redoStepGameMatrix(PlayerMove playerMove) {
		fields = redoStepMap.get(playerMove);
	}

	public static int inversPlayer(int playerType) {
		return (playerType == Global.WHITE) ? Global.BLACK : Global.WHITE;
	}

	public ArrayList<Move> getNextPossibleSteps(final int player) {
		calcNextPossibleSteps(player);
		return nextSteps;
	}

}
