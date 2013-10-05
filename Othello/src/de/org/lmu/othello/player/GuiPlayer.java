package de.org.lmu.othello.player;

import java.util.Random;

import szte.mi.Move;
import szte.mi.Player;
import de.org.lmu.othello.gui.JOthelloGui;

public class GuiPlayer implements Player {
	@Override
	public void init(int order, long t, Random rnd) {

	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		return JOthelloGui.getCurrentMove();
	}

}
