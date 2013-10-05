import junit.framework.Assert;
import ki.Global;
import ki.OthelloBoard;
import ki.PlayerMove;

import org.junit.Before;
import org.junit.Test;

import szte.mi.Move;

public class TestRuleEngine2 {
	private int[][] gameMatrix;
	private OthelloBoard gameRule = new OthelloBoard();

	@Before
	public void setUp() {
		gameMatrix = new int[8][8];
		gameMatrix[3][3] = Global.WHITE;
		gameMatrix[4][4] = Global.WHITE;
		gameMatrix[3][4] = Global.BLACK;
		gameMatrix[4][3] = Global.BLACK;
	}

	@Test
	public void testVerticalTrue1() {
		Move move = new Move(4, 5);

		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);

	}

	@Test
	public void testVerticalTrue2() {
		Move move = new Move(4, 2);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, true);
	}

	@Test
	public void testVerticalFalse1() {
		Move move = new Move(4, 5);

		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testVerticalFalse2() {
		Move move = new Move(4, 2);

		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testVerticalFlow() {
		Move move = new Move(4, 5);
		OthelloBoard rule = gameRule;
		boolean test = rule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);

		rule.doStep(move, Global.BLACK);
		gameMatrix = rule.mapGameFieldToIntArray();
		int[][] tmpMatrix = new int[8][8];
		tmpMatrix[3][3] = Global.WHITE;
		tmpMatrix[4][4] = Global.BLACK;
		tmpMatrix[3][4] = Global.BLACK;
		tmpMatrix[4][3] = Global.BLACK;
		tmpMatrix[5][4] = Global.BLACK;
		for (int y = 0; y < gameMatrix.length; y++)
			for (int x = 0; x < gameMatrix.length; x++)
				Assert.assertTrue(gameMatrix[y][x] == tmpMatrix[y][x]);
		move = new Move(5, 5);

		test = rule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, true);

	}

	@Test
	public void testHorizontalTrue1() {
		Move move = new Move(2, 3);

		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);

	}

	@Test
	public void testHorizontalTrue2() {
		Move move = new Move(5, 3);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, true);
	}

	@Test
	public void testHorizontalFalse1() {
		Move move = new Move(2, 3);

		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testHorizontalFalse2() {
		Move move = new Move(5, 3);

		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testDiagnoalUpperLeftTrue1() {
		Move move = new Move(2, 2);
		gameRule.setStep(18, Global.BLACK);
		move = new Move(5, 5);
		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);
	}

	@Test
	public void testDiagnoalUpperLeftFalse1() {
		Move move = new Move(2, 2);
		gameRule.setStep(18, Global.BLACK);
		move = new Move(5, 5);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testDiagnoalUpperRightTrue1() {
		Move move = new Move(5, 2);
		gameRule.setStep(21, Global.WHITE);
		move = new Move(2, 5);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, true);
	}

	@Test
	public void testDiagnoalUpperRightFalse1() {
		Move move = new Move(5, 2);
		gameRule.setStep(21, Global.WHITE);
		move = new Move(2, 5);
		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testDiagnoalDownRightTrue1() {
		Move move = new Move(5, 5);

		gameRule.setStep(45, Global.BLACK);
		move = new Move(2, 2);
		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);
	}

	@Test
	public void testDiagnoalDownRightFalse1() {
		Move move = new Move(5, 5);
		gameRule.setStep(45, Global.BLACK);
		move = new Move(2, 2);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testDiagnoalDownLeftTrue1() {
		Move move = new Move(2, 5);
		gameRule.setStep(42, Global.WHITE);
		move = new Move(5, 2);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, true);
	}

	@Test
	public void testDiagnoalDownLeftFalse1() {
		Move move = new Move(2, 5);
		gameRule.setStep(42, Global.WHITE);
		move = new Move(5, 2);
		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testBoarder() {
		Move move = new Move(0, 0);
		gameMatrix[move.y][move.x] = Global.WHITE;
		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
		move = new Move(7, 7);
		gameMatrix[move.y][move.x] = Global.WHITE;
		test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
		move = new Move(0, 7);
		gameMatrix[move.y][move.x] = Global.WHITE;
		test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
		move = new Move(7, 0);
		gameMatrix[move.y][move.x] = Global.WHITE;
		test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, false);
	}

	@Test
	public void testVerticalFlowRedo() {
		Move move = new Move(4, 5);
		OthelloBoard rule = gameRule;
		boolean test = rule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);

		rule.doStep(move, Global.BLACK);

		int[][] tmpMatrix = copy2d(rule.mapGameFieldToIntArray());
		rule = gameRule;
		move = new Move(5, 5);

		test = rule.validateMove(move, Global.WHITE);

		rule.doStep(move, Global.WHITE);

		Assert.assertEquals(test, true);

		move = new Move(5, 3);

		test = rule.validateMove(move, Global.WHITE);

		rule.doStep(move, Global.WHITE);
		Assert.assertEquals(test, true);

		rule.redoStepGameMatrix(new PlayerMove(5, 3, Global.WHITE));

		rule.redoStepGameMatrix(new PlayerMove(5, 5, Global.WHITE));
		gameMatrix = rule.mapGameFieldToIntArray();
		for (int y = 0; y < gameMatrix.length; y++)
			for (int x = 0; x < gameMatrix.length; x++)
				Assert.assertTrue(gameMatrix[y][x] == tmpMatrix[y][x]);

	}

	@Test
	public void testBugRule() {
		// [0, 0, 0, 0, 0, 0, 0, 0],
		// [0, 0, 0, 0, 0, 0, 0, 0],
		// [0, 0, 1, 2, 0, 0, 0, 0],
		// [0, 0, 0, 1, 1, 2, 0, 0],
		// [0, 0, 0, 2, 2, 2, 0, 0],
		// [0, 0, 0, 0, 0, 0, 0, 0],
		// [0, 0, 0, 0, 0, 0, 0, 0],
		// [0, 0, 0, 0, 0, 0, 0, 0]]

		gameRule.setGameField(0, Global.WHITE);
		gameRule.setGameField(0, Global.BLACK);

		gameRule.setStep(18, Global.WHITE);
		gameRule.setStep(19, Global.BLACK);
		gameRule.setStep(27, Global.WHITE);
		gameRule.setStep(28, Global.WHITE);
		gameRule.setStep(29, Global.BLACK);
		gameRule.setStep(35, Global.BLACK);
		gameRule.setStep(36, Global.BLACK);
		gameRule.setStep(37, Global.BLACK);

		for (Move move : gameRule.getNextPossibleSteps(Global.BLACK)) {
			if (move.x == 6 && move.y == 2)
				Assert.assertTrue(false);
		}
	}

	@Test
	public void testBug2Rule() {
		// // [0, 0, 0, 0, 0, 0, 0, 0],
		// // [0, 0, 0, 0, 0, 0, 0, 0],
		// // [0, 0, 1, 2, 0, 0, 0, 0],
		// // [0, 0, 0, 1, 1, 2, 0, 0],
		// // [0, 0, 0, 2, 2, 2, 0, 0],
		// // [0, 0, 0, 0, 0, 0, 0, 0],
		// // [0, 0, 0, 0, 0, 0, 0, 0],
		// // [0, 0, 0, 0, 0, 0, 0, 0]]
		// // Redo 1 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 2 0 0 0 0 0
		// // 0 0 1 1 1 1 0 0
		// // 0 0 0 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // ---------------------------------------------------------
		// // Do 3 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 2 2 0 0 0 0
		// // 0 0 1 2 2 1 0 0
		// // 0 0 0 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // ---------------------------------------------------------
		// // Redo 3 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 2 0 0 0 0 0
		// // 0 0 1 2 1 1 0 0
		// // 0 0 0 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0

		gameRule.setStep(18, Global.BLACK);
		gameRule.setStep(26, Global.WHITE);
		gameRule.setStep(27, Global.WHITE);
		gameRule.setStep(28, Global.WHITE);
		gameRule.setStep(29, Global.WHITE);
		gameRule.setStep(35, Global.BLACK);
		gameRule.setStep(36, Global.BLACK);
		gameRule.setStep(37, Global.BLACK);
		// gameMatrix[2][2] = GameEngine.BLACK;
		// gameMatrix[3][2] = GameEngine.WHITE;
		// gameMatrix[3][3] = GameEngine.WHITE;
		// gameMatrix[3][4] = GameEngine.WHITE;
		// gameMatrix[3][5] = GameEngine.WHITE;
		// gameMatrix[4][3] = GameEngine.BLACK;
		// gameMatrix[4][4] = GameEngine.BLACK;
		// gameMatrix[4][5] = GameEngine.BLACK;
		// gameRule.init(gameMatrix);
		gameMatrix = gameRule.mapGameFieldToIntArray();
		int[][] tmpMatrix = copy2d(gameMatrix);
		Move move1 = new Move(3, 2);

		boolean test = gameRule.validateMove(move1, Global.BLACK);
		Assert.assertEquals(test, true);
		gameRule.doStep(move1, Global.BLACK);
		gameMatrix = gameRule.mapGameFieldToIntArray();
		Move move2 = new Move(2, 1);

		test = gameRule.validateMove(move2, Global.WHITE);
		Assert.assertEquals(test, true);
		gameRule.doStep(move2, Global.WHITE);
		gameMatrix = gameRule.mapGameFieldToIntArray();
		gameRule.redoStepGameMatrix(new PlayerMove(move2.x, move2.y,
				Global.WHITE));
		gameRule.redoStepGameMatrix(new PlayerMove(move1.x, move1.y,
				Global.BLACK));
		gameMatrix = gameRule.mapGameFieldToIntArray();
		for (int y = 0; y < gameMatrix.length; y++)
			for (int x = 0; x < gameMatrix.length; x++)
				Assert.assertTrue(gameMatrix[y][x] == tmpMatrix[y][x]);
	}

	@Test
	public void testBug3Rule() {
		// // Do 1 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 2 1 0 0 0 0 0
		// // 0 0 2 1 2 0 0 0
		// // 0 0 1 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // ---------------------------------------------------------
		// // Redo 1 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 1 0 0 0 0 0
		// // 0 0 1 1 2 0 0 0
		// // 0 0 1 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // ---------------------------------------------------------
		// // Do 3 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 1 2 0 0 0 0
		// // 0 0 1 2 2 0 0 0
		// // 0 0 1 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // ---------------------------------------------------------
		// // Redo 3 2
		// // ######## Refreshmatrix Player: 1
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 1 0 0 0 0 0
		// // 0 0 1 1 1 0 0 0
		// // 0 0 1 2 2 2 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 0 0 0 0 0
		// // ---------------------------------------------------------
		//
		gameMatrix = new int[8][8];

		// gameMatrix[2][2] = GameEngine.WHITE;
		// gameMatrix[3][2] = GameEngine.WHITE;
		// gameMatrix[3][3] = GameEngine.WHITE;
		// gameMatrix[3][4] = GameEngine.BLACK;
		// gameMatrix[4][2] = GameEngine.WHITE;
		// gameMatrix[4][3] = GameEngine.BLACK;
		// gameMatrix[4][4] = GameEngine.BLACK;
		// gameMatrix[4][5] = GameEngine.BLACK;
		gameRule.setStep(18, Global.WHITE);
		gameRule.setStep(26, Global.WHITE);
		gameRule.setStep(27, Global.WHITE);
		gameRule.setStep(28, Global.BLACK);
		gameRule.setStep(34, Global.WHITE);
		gameRule.setStep(35, Global.BLACK);
		gameRule.setStep(36, Global.BLACK);
		gameMatrix = gameRule.mapGameFieldToIntArray();

		int[][] tmpMatrix = copy2d(gameMatrix);
		Move move = new Move(1, 2);

		boolean test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);
		gameRule.doStep(move, Global.BLACK);

		gameRule.redoStepGameMatrix(new PlayerMove(move.x, move.y,
				Global.BLACK));
		gameMatrix = gameRule.mapGameFieldToIntArray();
		for (int y = 0; y < gameMatrix.length; y++)
			for (int x = 0; x < gameMatrix.length; x++)
				Assert.assertTrue(gameMatrix[y][x] == tmpMatrix[y][x]);

		tmpMatrix = copy2d(gameMatrix);
		move = new Move(3, 2);

		test = gameRule.validateMove(move, Global.BLACK);
		Assert.assertEquals(test, true);
		gameRule.doStep(move, Global.BLACK);

		gameRule.redoStepGameMatrix(new PlayerMove(move.x, move.y,
				Global.BLACK));
		gameMatrix = gameRule.mapGameFieldToIntArray();

		for (int y = 0; y < gameMatrix.length; y++)
			for (int x = 0; x < gameMatrix.length; x++)
				Assert.assertTrue(gameMatrix[y][x] == tmpMatrix[y][x]);
	}

	@Test
	public void testBug4Rule() {
		// Do 1 2

		// [0, 0, 0, 0, 0, 0, 0, 0],
		// [0, 0, 0, 0, 0, 0, 0, 0],
		// [0, 2, 1, 2, 1, 0, 0, 0],
		// [0, 0, 2, 1, 1, 0, 0, 0],
		// [0, 2, 2, 2, 1, 0, 0, 0],
		// [0, 0, 0, 2, 2, 2, 0, 0],
		// [0, 0, 0, 0, 1, 2, 0, 0],
		// [0, 0, 0, 0, 0, 0, 2, 0]
		//
		gameMatrix = new int[8][8];

//		gameMatrix[2][1] = GameEngine.BLACK;
//		gameMatrix[2][2] = GameEngine.WHITE;
//		gameMatrix[2][3] = GameEngine.BLACK;
//		gameMatrix[2][4] = GameEngine.WHITE;
//		gameMatrix[3][2] = GameEngine.BLACK;
//		gameMatrix[3][3] = GameEngine.WHITE;
//		gameMatrix[3][4] = GameEngine.WHITE;
//		gameMatrix[4][1] = GameEngine.BLACK;
//		gameMatrix[4][2] = GameEngine.BLACK;
//		gameMatrix[4][3] = GameEngine.BLACK;
//		gameMatrix[4][4] = GameEngine.WHITE;
//		gameMatrix[5][3] = GameEngine.BLACK;
//		gameMatrix[5][4] = GameEngine.BLACK;
//		gameMatrix[5][5] = GameEngine.BLACK;
//		gameMatrix[6][4] = GameEngine.WHITE;
//		gameMatrix[6][5] = GameEngine.BLACK;
//		gameMatrix[7][6] = GameEngine.BLACK;
		gameRule.setGameField(0L, Global.BLACK);
		gameRule.setGameField(0L, Global.WHITE);
		gameRule.setStep(17, Global.BLACK);
		gameRule.setStep(18, Global.WHITE);
		gameRule.setStep(19, Global.BLACK);
		gameRule.setStep(20, Global.WHITE);
		gameRule.setStep(26, Global.BLACK);
		gameRule.setStep(27, Global.WHITE);
		gameRule.setStep(28, Global.WHITE);
		gameRule.setStep(33, Global.BLACK);
		gameRule.setStep(34, Global.BLACK);
		gameRule.setStep(35, Global.BLACK);
		gameRule.setStep(36, Global.WHITE);
		gameRule.setStep(43, Global.BLACK);
		gameRule.setStep(44, Global.BLACK);
		gameRule.setStep(45, Global.BLACK);
		gameRule.setStep(52, Global.WHITE);
		gameRule.setStep(53, Global.BLACK);
		gameRule.setStep(62, Global.BLACK);
		gameMatrix = gameRule.mapGameFieldToIntArray();
		int[][] tmpMatrix = copy2d(gameMatrix);
		Move move = new Move(2, 1);
		boolean test = gameRule.validateMove(move, Global.WHITE);
		Assert.assertEquals(test, true);
		gameRule.doStep(move, Global.WHITE);

		Move move2 = new Move(5, 3);
		gameRule.doStep(move2, Global.BLACK);
		gameRule.redoStepGameMatrix(new PlayerMove(move2.x, move2.y,
				Global.BLACK));
//		gameRule.doStep(move, GameEngine.WHITE);

		gameRule.redoStepGameMatrix(new PlayerMove(move.x, move.y,
				Global.WHITE));
		gameMatrix = gameRule.mapGameFieldToIntArray();
		for (int y = 0; y < gameMatrix.length; y++)
			for (int x = 0; x < gameMatrix.length; x++)
				Assert.assertTrue(gameMatrix[y][x] == tmpMatrix[y][x]);

	}

	@Test
	public void testBug5Rule() {
		//
		// // 0 0 0 0 0 0 0 0
		// // 0 0 0 2 0 2 0 0
		// // 0 0 2 0 2 0 1 0
		// // 2 1 2 1 1 1 1 0
		// // 1 2 2 1 2 1 1 1
		// // 2 0 2 2 2 2 1 2
		// // 0 0 0 1 1 1 0 0
		// // 0 0 0 2 0 1 0 0
		// gameMatrix = new int[8][8];
		//
		// gameMatrix[1][3] = GameEngine.BLACK;
		// gameMatrix[1][5] = GameEngine.BLACK;
		// gameMatrix[2][2] = GameEngine.BLACK;
		// gameMatrix[2][4] = GameEngine.BLACK;
		// gameMatrix[2][6] = GameEngine.WHITE;
		// gameMatrix[3][0] = GameEngine.BLACK;
		// gameMatrix[3][1] = GameEngine.WHITE;
		// gameMatrix[3][2] = GameEngine.BLACK;
		// gameMatrix[3][3] = GameEngine.WHITE;
		// gameMatrix[3][4] = GameEngine.WHITE;
		// gameMatrix[3][5] = GameEngine.WHITE;
		// gameMatrix[3][6] = GameEngine.WHITE;
		//
		// gameMatrix[4][0] = GameEngine.WHITE;
		// gameMatrix[4][1] = GameEngine.BLACK;
		// gameMatrix[4][2] = GameEngine.BLACK;
		// gameMatrix[4][3] = GameEngine.WHITE;
		// gameMatrix[4][4] = GameEngine.BLACK;
		// gameMatrix[4][5] = GameEngine.WHITE;
		// gameMatrix[4][6] = GameEngine.WHITE;
		// gameMatrix[4][7] = GameEngine.WHITE;
		//
		// gameMatrix[5][0] = GameEngine.BLACK;
		// gameMatrix[5][2] = GameEngine.BLACK;
		// gameMatrix[5][3] = GameEngine.BLACK;
		// gameMatrix[5][4] = GameEngine.BLACK;
		// gameMatrix[5][5] = GameEngine.BLACK;
		// gameMatrix[5][6] = GameEngine.WHITE;
		// gameMatrix[5][7] = GameEngine.BLACK;
		// gameMatrix[6][3] = GameEngine.WHITE;
		// gameMatrix[6][4] = GameEngine.WHITE;
		// gameMatrix[6][5] = GameEngine.WHITE;
		//
		// gameMatrix[7][3] = GameEngine.BLACK;
		// gameMatrix[7][5] = GameEngine.WHITE;
		// gameRule.init(gameMatrix);
		// Move move = new Move(2, 6);
		// // boolean test = gameRule.validateMove(move, GameEngine.WHITE);
		// // Assert.assertEquals(test, true);
		// gameMatrix = gameRule.calculateNewGameMatrix(new PlayerMove(move.x,
		// move.y, GameEngine.WHITE));
		// System.out.println(gameMatrix);
	}

	private static int[][] copy2d(final int[][] nums) {
		int[][] copy = new int[nums.length][];

		for (int i = 0; i < copy.length; i++) {
			int[] member = new int[nums[i].length];
			System.arraycopy(nums[i], 0, member, 0, nums[i].length);
			copy[i] = member;
		}

		return copy;
	}
}
