package com.ten31f.battlemap.grider.actions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.ui.TileComponent;

public class Carver {

	private Carver() {
	}

	public static List<TileComponent> carve(BufferedImage bufferedImage, Grid grid) {

		List<TileComponent> imageComponents = new ArrayList<>();

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		int subWidth = width / grid.getxCells();
		int subHeight = height / grid.getxCells();

		for (int yIndex = 0; yIndex < grid.getxCells(); yIndex++) {
			for (int xIndex = 0; xIndex < grid.getxCells(); xIndex++) {

				BufferedImage subBufferedImage = bufferedImage.getSubimage(xIndex * subWidth, yIndex * subHeight,
						subWidth, subHeight);

				TileComponent imageComponent = new TileComponent();
				imageComponent.setOrginalImage(subBufferedImage);
				imageComponent.setSize(width, height);

				imageComponents.add(imageComponent);
			}
		}

		return imageComponents;
	}

}
