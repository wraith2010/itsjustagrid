package com.ten31f.battlemap.grider.domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public enum TileState {

	HIDDEN, FOGGED, SELECTED;

	public void paint(BufferedImage bufferedImage) {
		switch (this) {
		case FOGGED:
			shade(bufferedImage, Color.gray, 150);
			break;
		case HIDDEN:
			shade(bufferedImage, Color.black, 255);
			break;
		case SELECTED:
			ring(bufferedImage, Color.red);
			break;
		default:
			break;

		}
	}

	private void shade(BufferedImage bufferedImage, Color color, int alpha) {

		Graphics graphics = bufferedImage.getGraphics();

		graphics.setColor(new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha));

		graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
	}

	private void ring(BufferedImage bufferedImage, Color color) {

		Graphics graphics = bufferedImage.getGraphics();

		graphics.setColor(color);

		graphics.drawRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
	}

}