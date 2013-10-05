package ki;


import java.util.Random;

import szte.mi.Move;

public class OrderMove extends Move implements Comparable<OrderMove> {
	public final Integer score;
	final static Random rgen = KiPlayer.rnd;
	final int[] num= {-1,1};
	public OrderMove(Integer score, int x, int y) {
		super(x, y);
		this.score = score;
	}

	@Override
	public int compareTo(OrderMove o) {
		if (score.compareTo(o.score) == 0) {
			if (o.x == this.x && o.y == this.y) {
				return 0;
			} else {
				return num[OrderMove.rgen.nextInt(1)];
			}
		} else
			return o.score.compareTo(score);
	}
}
