package ki;


import szte.mi.Move;

public class PlayerMove extends Move {
	private int playerType;

	public PlayerMove(int x, int y, int playerType) {
		super(x, y);
		this.playerType = playerType;
	}

	public Move getMove() {
		return this;
	}

	public int getPlayerType() {
		return playerType;
	}

	public int getOpponent() {
		return (playerType == Global.BLACK) ? Global.WHITE
				: Global.BLACK;
	}

	@Override
	public int hashCode() {

		int hash = 1;
		hash = hash * 17 + x;
		hash = hash * 31 + y;
		hash = hash * 13 + playerType;

		return hash;
	}

	@Override
	public boolean equals(Object arg0) {
		PlayerMove move = (PlayerMove) arg0;
		if (move.x == this.x && move.y == this.y
				&& move.playerType == this.playerType)
			return true;
		else
			return false;
	}
}
