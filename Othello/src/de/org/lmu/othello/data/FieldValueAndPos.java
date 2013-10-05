package de.org.lmu.othello.data;

import szte.mi.Move;

public class FieldValueAndPos {

	final public int fieldValue;
	final public int x;
	final public int y;

	public FieldValueAndPos(int pfieldValue, Move move) {
		super();
		this.fieldValue = pfieldValue;
		this.x = move.x;
		this.y = move.y;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Value: ").append(fieldValue).append(" x: ").append(x).append(" y: ").append(y);
		return builder.toString();
	}
}
