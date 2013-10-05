import junit.framework.Assert;
import ki.Analyse;
import ki.Global;
import ki.OthelloBoard;

import org.junit.Before;
import org.junit.Test;


public class TestAnalyse {
	
	private OthelloBoard board = new OthelloBoard();

	@Before
	public void setUp() {

		board.init();
	}
	
	@Test
	public void testCountBlack(){
		Analyse.countMatrix(board);
		Assert.assertEquals(2, Analyse.getCounterByKey(Global.BLACK));
	}
	
	
	@Test
	public void testCountWhite(){
		Analyse.countMatrix(board);
		Assert.assertEquals(2, Analyse.getCounterByKey(Global.WHITE));
		Assert.assertEquals(60,Analyse.getCounterByKey(Global.UNSET));
	}
	
	
	@Test
	public void testCountBlackCorner(){
		Analyse.countMatrix(board);
		Assert.assertEquals(0, Analyse.getBlackCountCorner());
		board.setStep(0, Global.BLACK);
		board.setStep(7, Global.BLACK);

		board.setStep(56, Global.BLACK);
		board.setStep(63, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(4, Analyse.getBlackCountCorner());
		
		
	}
	
	@Test
	public void testCountWhiteCorner(){
		Analyse.countMatrix(board);
		Assert.assertEquals(0, Analyse.getWhiteCountCorner());
		board.setStep(0, Global.WHITE);
		board.setStep(7, Global.WHITE);
		board.setStep(56, Global.WHITE);
		board.setStep(63, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(4, Analyse.getWhiteCountCorner());
		
	}
	
	
	@Test
	public void testCountBlackCornerClosenes(){
		Analyse.countMatrix(board);
		Assert.assertEquals(0, Analyse.getBlackCornerClosenes());
		board.setStep(1, Global.BLACK);
		board.setStep(8, Global.BLACK);
		board.setStep(9, Global.BLACK);
		board.setStep(6, Global.BLACK);
		board.setStep(14, Global.BLACK);
		board.setStep(15, Global.BLACK);
		board.setStep(57, Global.BLACK);
		board.setStep(48, Global.BLACK);
		board.setStep(49, Global.BLACK);
		board.setStep(62, Global.BLACK);
		board.setStep(55, Global.BLACK);
		board.setStep(54, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(12, Analyse.getBlackCornerClosenes());
		board.setStep(63, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(9, Analyse.getBlackCornerClosenes());
		
	}
	
	
	@Test
	public void testCountWhiteCornerClosenes(){
		Analyse.countMatrix(board);
		Assert.assertEquals(0, Analyse.getWhiteCornerClosenes());
		board.setStep(1, Global.WHITE);
		board.setStep(8, Global.WHITE);
		board.setStep(9, Global.WHITE);
		board.setStep(6, Global.WHITE);
		board.setStep(14, Global.WHITE);
		board.setStep(15, Global.WHITE);
		board.setStep(57, Global.WHITE);
		board.setStep(48, Global.WHITE);
		board.setStep(49, Global.WHITE);
		board.setStep(62, Global.WHITE);
		board.setStep(55, Global.WHITE);
		board.setStep(54, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(12, Analyse.getWhiteCornerClosenes());
		board.setStep(63, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(9, Analyse.getWhiteCornerClosenes());
		
	}
	
//	{   { 100, -10, 11, 6, 6, 11, -10, 100 },
//		{ -10, -20, 1, 2, 2, 1, -20, -10 }, 
//	    { 10, 1, 5, 4, 4, 5, 1, 10 },
//		{ 6, 2, 4, 2, 2, 4, 2, 6 }, 
//      { 6, 2, 4, 2, 2, 4, 2, 6 },
//		{ 10, 1, 5, 4, 4, 5, 1, 10 }, 
//      { -10, -20, 1, 2, 2, 1, -20, -10 },
//		{ 100, -10, 11, 6, 6, 11, -10, 100 } };
	
	
	@Test
	public void testWhiteScoreFunction(){
		Analyse.countMatrix(board);
		Assert.assertEquals(4, Analyse.getWhiteMatrixScore());
		board.setStep(1, Global.WHITE);
		board.setStep(8, Global.WHITE);
		board.setStep(9, Global.WHITE);
		board.setStep(6, Global.WHITE);
		board.setStep(14, Global.WHITE);
		board.setStep(15, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
		board.setStep(0, Global.WHITE);
		board.setStep(7, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
		board.setStep(16, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
		board.setStep(17, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(1+10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
		board.setStep(18, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(5+1+10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
		board.setStep(20, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(4+5+1+10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
		board.setStep(24, Global.WHITE);
		Analyse.countMatrix(board);
		Assert.assertEquals(6+4+5+1+10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getWhiteMatrixScore());
	}
	
	@Test
	public void testBlackScoreFunction(){
		Analyse.countMatrix(board);
		Assert.assertEquals(4, Analyse.getBlackMatrixScore());
		board.setStep(1, Global.BLACK);
		board.setStep(8, Global.BLACK);
		board.setStep(9, Global.BLACK);
		board.setStep(6, Global.BLACK);
		board.setStep(14, Global.BLACK);
		board.setStep(15, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(-10+-10+-20+-10+-20+-10+4, Analyse.getBlackMatrixScore());
		board.setStep(0, Global.BLACK);
		board.setStep(7, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getBlackMatrixScore());
		board.setStep(16, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getBlackMatrixScore());
		board.setStep(17, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(1+10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getBlackMatrixScore());
		board.setStep(18, Global.BLACK);
		Analyse.countMatrix(board);
		Assert.assertEquals(5+1+10+100+100+-10+-10+-20+-10+-20+-10+4, Analyse.getBlackMatrixScore());
	
	}
	
	
//	
}
