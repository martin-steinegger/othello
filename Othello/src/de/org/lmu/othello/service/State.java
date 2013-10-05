package de.org.lmu.othello.service;

import szte.mi.Player;

public abstract class State {

	abstract public State nextMovePlayerBlack(GameEngine engine,
			Player playerBlack);

	abstract public State nextMovePlayerWhite(GameEngine engine,
			Player playerWhite);

	abstract public int getCurrentPlayer();

	abstract public int getOpponent();
}
