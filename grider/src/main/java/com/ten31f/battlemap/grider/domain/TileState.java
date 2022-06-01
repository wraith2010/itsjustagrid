package com.ten31f.battlemap.grider.domain;

import java.awt.Color;
import java.awt.Graphics;

public enum TileState {

	HIDDEN, FOGGED, REVEALED;

	public void paint(Graphics graphics, int width, int height, boolean isController) {
		switch (this) {
		case FOGGED:
			shade(graphics, width, height, Color.gray, 150);
			break;
		case HIDDEN:
			shade(graphics, width, height, Color.black, (isController) ? 150 : 255);
			break;
		case REVEALED:
			break;
		default:
			break;

		}
	}

	private void shade(Graphics graphics, int width, int height, Color color, int alpha) {
		graphics.setColor(new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha));
		graphics.fillRect(0, 0, width, height);
	}

}