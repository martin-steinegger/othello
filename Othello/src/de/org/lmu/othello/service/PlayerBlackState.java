package de.org.lmu.othello.service;

import ki.Global;
import ki.PlayerMove;
import szte.mi.Move;
import szte.mi.Player;

public class PlayerBlackState extends State {

	@Override
	public State nextMovePlayerBlack(GameEngine engine, Player playerBlack) {
		Move move = playerBlack.nextMove(engine.getPreviousMove(), 0, 0);
		PlayerMove returnMove;
		if(move==null){
			returnMove=null;
		}else {
			returnMove=new PlayerMove(move.x, move.y,
					Global.BLACK);
		}
		if (engine.makeMovePlayer(returnMove))
			return new PlayerWhiteState();
		else
			return this;

	}

	@Override
	public State nextMovePlayerWhite(GameEngine engine, Player playerWhite) {
		return this;
	}

	@Override
	public int getCurrentPlayer() {
		return Global.BLACK;
	}

	@Override
	public int getOpponent() {
		return Global.WHITE;
	}

}
