package ki;

public class Analyse {
	private static int whiteCountCorner;
	private static int blackCountCorner;
	private static int[] map;
	private static int blackCornerClosenes;
	private static int whiteCornerClosenes;

	private static int blackMatrixScore;
	private static int whiteMatrixScore;
	private final static long CORNER_MASK = 0x8100000000000081L;
	// private final static long CORNER_RIGHT_DOWN = 0x1L;
	// private final static long CORNER_LEFT_DOWN = 0x80L;
	// private final static long CORNER_RIGHT_UP = 0x100000000000000L;
	// private final static long CORNER_LEFT_UP = 0x8000000000000000L;
	private final static int[] CORNER = { 0, 7, 56, 63 };
	private final static long CORNER_CLOSENES_LEFT_UP = 0x40C0000000000000L;
	private final static long CORNER_CLOSENES_RIGHT_UP = 0x203000000000000L;
	private final static long CORNER_CLOSENES_LEFT_DOWN = 0xC040L;
	private final static long CORNER_CLOSENES_RIGHT_DOWN = 0x302L;
	private final static long[] CORNER_CLOSENES = { CORNER_CLOSENES_LEFT_UP,
			CORNER_CLOSENES_RIGHT_UP, CORNER_CLOSENES_LEFT_DOWN,
			CORNER_CLOSENES_RIGHT_DOWN };

	private final static long[] matrixCalcMasks = { 0x4281000000008142L,
			CORNER_MASK, 0x42000000004200L, 0x2400000000000024L,
			0x1800008181000018L, 0x24420000422400L, 0x18005A5A001800L,
			0x182424180000L, 0x240000240000L,0x810000810000L };

	private final static long[] matrixCalcScore = { -10, 100, -20, 11, 6, 1,
			2, 4, 5,10 };

	final int[][] gameFieldWeight = { { 100, -10, 11, 6, 6, 11, -10, 100 },
			{ -10, -20, 1, 2, 2, 1, -20, -10 }, { 10, 1, 5, 4, 4, 5, 1, 10 },
			{ 6, 2, 4, 2, 2, 4, 2, 6 }, { 6, 2, 4, 2, 2, 4, 2, 6 },
			{ 10, 1, 5, 4, 4, 5, 1, 10 }, { -10, -20, 1, 2, 2, 1, -20, -10 },
			{ 100, -10, 11, 6, 6, 11, -10, 100 } };

	public static void countMatrix(OthelloBoard board) {
		map = new int[3];
		blackCountCorner = 0;
		whiteCountCorner = 0;
		blackCornerClosenes = 0;
		whiteCornerClosenes = 0;
		blackMatrixScore = 0;
		whiteMatrixScore = 0;
		long whiteBoard = board.getBoard(Global.WHITE);
		long blackBoard = board.getBoard(Global.BLACK);
		blackCountCorner = bitCount(blackBoard & CORNER_MASK);
		whiteCountCorner = bitCount(whiteBoard & CORNER_MASK);
		for (int i = 0; i < CORNER.length; i++) {
			if (board.isSet(CORNER[i], whiteBoard | blackBoard) == false) {
				whiteCornerClosenes += bitCount(whiteBoard & CORNER_CLOSENES[i]);
			}
			if (board.isSet(CORNER[i], whiteBoard | blackBoard) == false) {
				blackCornerClosenes += bitCount(blackBoard & CORNER_CLOSENES[i]);
			}
		}

		for (int i = 0; i < matrixCalcMasks.length; i++) {
			blackMatrixScore += bitCount(blackBoard & matrixCalcMasks[i])
					* matrixCalcScore[i];
			whiteMatrixScore += bitCount(whiteBoard & matrixCalcMasks[i])
					* matrixCalcScore[i];
		}
		map[Global.UNSET] = bitCount(~(whiteBoard | blackBoard));
		map[Global.WHITE] = bitCount(whiteBoard);
		map[Global.BLACK] = bitCount(blackBoard);

	}

	public static int getCounterByKey(int key) {
		return map[key];
	}

	public static int getWhiteCountCorner() {
		return whiteCountCorner;
	}

	public static int getBlackCountCorner() {
		return blackCountCorner;
	}

	public static int getBlackCornerClosenes() {
		return blackCornerClosenes;
	}

	public static int getWhiteCornerClosenes() {
		return whiteCornerClosenes;
	}

	public static int getBlackMatrixScore() {
		return blackMatrixScore;
	}

	public static int getWhiteMatrixScore() {
		return whiteMatrixScore;
	}

	/**
	 * Count the number of set bits in an long;
	 * 
	 * @param x
	 *            the long to have its bits counted
	 * @author Tim Tyler tt@iname.com
	 * @returns the number of bits set in x
	 */
	static int bitCount(long x) {
		long temp;

		temp = 0x5555555555555555L;
		x = (x & temp) + (x >>> 1 & temp);
		temp = 0x3333333333333333L;
		x = (x & temp) + (x >>> 2 & temp);
		temp = 0x0707070707070707L;
		x = (x & temp) + (x >>> 4 & temp);
		temp = 0x000F000F000F000FL;
		x = (x & temp) + (x >>> 8 & temp);
		temp = 0x0000001F0000001FL;
		x = (x & temp) + (x >>> 16 & temp);

		return (int) ((x & 0x3f) + (x >>> 32));
	}

}
