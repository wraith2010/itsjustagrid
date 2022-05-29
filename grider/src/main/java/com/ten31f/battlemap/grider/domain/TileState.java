package com.ten31f.battlemap.grider.domain;

import java.awt.Color;

public enum TileState {

	HIDDEN(Color.black, 255), REVEALED(null, 0), FOGGED(Color.gray, 150);

	private Color color = null;
	private int alpha = 255;

	private TileState(Color color, int alpha) {
		this.color = color;
		this.alpha = alpha;
	}

	public int getAlpha() {
		return alpha;
	}

	public Color getColor() {
		return color;
	}

}