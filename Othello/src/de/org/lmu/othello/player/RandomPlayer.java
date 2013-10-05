package de.org.lmu.othello.player;

import java.util.ArrayList;
import java.util.Random;

import szte.mi.Move;
import szte.mi.Player;
import de.org.lmu.othello.service.GameEngine;

public class RandomPlayer implements Player {
	private Random rnd;

	@Override
	public void init(int order, long t, Random rnd) {
		this.rnd = rnd;
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		GameEngine engine = GameEngine.getInstance();
		final ArrayList<Move> nextSteps = engine.getNextPossibleMoves();
		return (nextSteps.size() > 0) ? nextSteps.get(rnd.nextInt(nextSteps
				.size())) : null;

	}

}
