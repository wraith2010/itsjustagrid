package com.ten31f.battlemap.grider.actions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.ui.ImageComponent;

public class Carver {

	private Carver() {
	}

	public static List<ImageComponent> carve(BufferedImage bufferedImage, Grid grid) {

		List<ImageComponent> imageComponents = new ArrayList<>();

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		int subWidth = width / grid.getxCells();
		int subHeight = height / grid.getxCells();

		for (int yIndex = 0; yIndex < grid.getxCells(); yIndex++) {
			for (int xIndex = 0; xIndex < grid.getxCells(); xIndex++) {

				BufferedImage subBufferedImage = bufferedImage.getSubimage(xIndex * subWidth, yIndex * subHeight,
						subWidth, subHeight);

				ImageComponent imageComponent = new ImageComponent();
				imageComponent.setOrginalImage(subBufferedImage);
				imageComponent.setSize(width, height);

				imageComponents.add(imageComponent);
			}
		}

		return imageComponents;
	}

}
