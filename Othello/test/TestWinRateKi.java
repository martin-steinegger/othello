import java.util.Random;

import junit.framework.Assert;
import ki.Global;
import ki.KiPlayer;

import org.junit.Before;
import org.junit.Test;

import szte.mi.Player;
import de.org.lmu.othello.gui.MoveCallback;
import de.org.lmu.othello.player.RandomPlayer;
import de.org.lmu.othello.service.GameEngine;
import de.org.lmu.othello.service.PlayerBlackState;

public class TestWinRateKi implements MoveCallback {
	private GameEngine gameEngine=GameEngine.getInstance();
	@Before
	public void setUp() {
		Player playerBlack = new RandomPlayer();
		playerBlack.init(0, 4534534, new Random());
		Player playerWhite = new KiPlayer();;
		playerWhite.init(1, 4534534, new Random());
		
		gameEngine=GameEngine.getInstance();
		gameEngine.setCallBack(this);	
		counterWhite=0;
		counterBlack=0;
	}

	private static int counterWhite;
	private static int counterBlack;
	@Test
	public void testWinrateWhite() {

		int count=100;
		for(int i = 0; i < count;i++){
			gameEngine.initMatrix();
			Player playerBlack = new RandomPlayer();
			playerBlack.init(0, 4534534, new Random());
			Player playerWhite = new KiPlayer();
			playerWhite.init(1, 4534534, new Random());
			gameEngine.initGui(new PlayerBlackState(), playerBlack, playerWhite);
			gameEngine.startAutomaticGame();
		}
		Assert.assertEquals(count,counterWhite);
		
	}
	
	
	@Test
	public void testWinrateBlack() {
		int count=100;
		for(int i = 0; i < count;i++){
			gameEngine.initMatrix();
			Player playerBlack = new KiPlayer();
			playerBlack.init(0, 4534534, new Random());
			Player playerWhite = new RandomPlayer();
			playerWhite.init(1, 4534534, new Random());
			gameEngine.initGui(new PlayerBlackState(), playerBlack, playerWhite);
			gameEngine.startAutomaticGame();
		}
		Assert.assertEquals(count,counterBlack);
		
	}

	@Override
	public void playHasToPass() {
		System.out.println("Pass");
	}

	@Override
	public void gameOver(int player) {
		System.out.println("Game over Player "+player+" winns");
		if(Global.WHITE==player)
			counterWhite++;
		if(Global.BLACK==player)
			counterBlack++;
		
	}

	@Override
	public void refreshMatrix(int[][] matrix) {
		
	}
}
