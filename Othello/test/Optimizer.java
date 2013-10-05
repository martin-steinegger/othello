import java.util.Random;

import ki.Global;
import ki.KiPlayer;

import org.junit.Before;
import org.junit.Test;

import szte.mi.Player;
import de.org.lmu.othello.gui.MoveCallback;
import de.org.lmu.othello.player.RandomPlayer;
import de.org.lmu.othello.service.GameEngine;
import de.org.lmu.othello.service.PlayerBlackState;

public class Optimizer implements MoveCallback {
	private GameEngine gameEngine = GameEngine.getInstance();
	private static int counterWhite;
	private static int counterBlack;

	@Before
	public void setUp() {
		Player playerBlack = new RandomPlayer();
		playerBlack.init(0, 4534534, new Random());
		Player playerWhite = new KiPlayer();
		;
		playerWhite.init(1, 4534534, new Random());

		gameEngine = GameEngine.getInstance();
		gameEngine.setCallBack(this);
		counterWhite = 0;
		counterBlack = 0;
	}

	@Test
	public void optimizeValueBlack() {
		int currMax=0;
		int[] weight = { 10, 801, 382, 78 };
		for (int x = 0; x < 1000; x++) {
			counterWhite = 0;
			counterBlack = 0;
			int count = 10;
			for (int i = 0; i < count; i++) {
				gameEngine.initMatrix();
				KiPlayer playerBlack = new KiPlayer();
				playerBlack.init(0, 4534534, new Random());
//				playerBlack.setWeight(weight);
				Player playerWhite = new RandomPlayer();
				playerWhite.init(1, 4534534, new Random());
				gameEngine.initGui(new PlayerBlackState(), playerBlack,
						playerWhite);
				gameEngine.startAutomaticGame();
			}
			weight[2]+=20;
			System.out.println("Done");
			if(counterBlack>=currMax){
				currMax=counterBlack;
				System.out.println("CountBlack: "+counterBlack);
				System.out.println("Weight: "+weight[0]+" "+weight[1]);
//				if(counterBlack==count)
//					break;
			}
		}
		
	}

	@Override
	public void gameOver(int player) {
		//System.out.println("Game over Player " + player + " winns");
		if (Global.WHITE == player)
			counterWhite++;
		if (Global.BLACK == player)
			counterBlack++;

	}

	@Override
	public void playHasToPass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshMatrix(int[][] matrix) {
		// TODO Auto-generated method stub

	}

}
