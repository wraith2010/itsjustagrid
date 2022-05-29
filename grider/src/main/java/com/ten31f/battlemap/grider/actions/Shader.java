package com.ten31f.battlemap.grider.actions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ten31f.battlemap.grider.domain.TileState;

public class Shader {

	private Shader() {
	}

	public static BufferedImage shade(BufferedImage bufferedImage, TileState tileState) {

		Graphics graphics = bufferedImage.getGraphics();

		graphics.setColor(new Color(tileState.getColor().getRed(), tileState.getColor().getBlue(),
				tileState.getColor().getGreen(), tileState.getAlpha()));

		graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

		return bufferedImage;
	}

}
