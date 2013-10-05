package de.org.lmu.othello.service;

import ki.Global;
import ki.PlayerMove;
import szte.mi.Move;
import szte.mi.Player;

public class PlayerWhiteState extends State {

	@Override
	public State nextMovePlayerBlack(GameEngine engine, Player playerBlack) {

		return this;
	}

	@Override
	public State nextMovePlayerWhite(GameEngine engine, Player playerWhite) {
		Move move = playerWhite.nextMove(engine.getPreviousMove(), 0, 0);
		PlayerMove returnMove;
		if(move==null){
			returnMove=null;
		}else {
			returnMove=new PlayerMove(move.x, move.y,
					Global.WHITE);
		}
		if (engine.makeMovePlayer(returnMove))
			return new PlayerBlackState();
		else
			return this;

	}

	@Override
	public int getCurrentPlayer() {
		return Global.WHITE;
	}

	@Override
	public int getOpponent() {
		return Global.BLACK;
	}

}
