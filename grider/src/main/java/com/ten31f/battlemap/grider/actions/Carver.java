package com.ten31f.battlemap.grider.actions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.ui.ImageComponent;
import com.ten31f.battlemap.grider.ui.MainController;

public class Carver {

	private static Logger LOGGER = Logger.getLogger(MainController.class.getName());

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

				LOGGER.log(Level.INFO, String.format("sub section(x:%s y:%s width: %s height: %s)", xIndex * subWidth,
						yIndex * subHeight, subWidth, subHeight));

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
