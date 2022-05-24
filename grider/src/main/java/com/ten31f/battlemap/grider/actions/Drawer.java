package com.ten31f.battlemap.grider.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ten31f.battlemap.grider.domain.Grid;

public class Drawer {

	private File imageFile = null;
	private Grid grid = null;

	public Drawer(File imageFile, Grid grid) {
		setImageFile(imageFile);
		setGrid(grid);
	}

	public BufferedImage lineField() throws IOException {

		BufferedImage bufferedImage = ImageIO.read(getImageFile());

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		// luminosity(bufferedImage);
		colorHighLight(bufferedImage, getGrid().getTargetColor());

		Graphics2D graphics2d = bufferedImage.createGraphics();

		graphics2d.setColor(Color.RED);
		Stroke stroke = new BasicStroke(1f);
		graphics2d.setStroke(stroke);

		vertialLines(graphics2d, width, height);
		horizontalLines(graphics2d, width, height);

		return bufferedImage;
	}

	private boolean colorMatch(int rgb) {

		int targetColor = getGrid().getTargetColor();

		return (targetColor + 100 > rgb) && (targetColor - 100 < rgb);
	}

	public void luminosity(BufferedImage bufferedImage) {

		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int color = bufferedImage.getRGB(x, y);

				int red = (color >>> 16) & 0xFF;
				int green = (color >>> 8) & 0xFF;
				int blue = (color >>> 0) & 0xFF;

				float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;

				// choose brightness threshold as appropriate:
				if (luminance < getGrid().getLuminosityThresholdUpperLimit()
						&& luminance > getGrid().getLuminosityThresholdLowerLimit()) {
					bufferedImage.setRGB(x, y, Color.YELLOW.getRGB());
				} else {
					bufferedImage.setRGB(x, y, Color.GRAY.getRGB());
				}
			}
		}

	}

	public void colorHighLight(BufferedImage bufferedImage, int targetColor) {

		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int color = bufferedImage.getRGB(x, y);

				// choose brightness threshold as appropriate:
				if (colorMatch(color) ) {
					bufferedImage.setRGB(x, y, Color.YELLOW.getRGB());
				} else {
					bufferedImage.setRGB(x, y, Color.GRAY.getRGB());
				}
			}
		}

	}

	private float getWidthLimit(int width) {
		return width - (2f * getGrid().getHorizantalBorder());
	}

	private float getColumnWidth(int width) {
		return getWidthLimit(width) / getGrid().getxCells();
	}

	private float getHeightLimit(int height) {
		return height - (2f * getGrid().getVerticalBorder());
	}

	private float getRowHeight(int height) {
		return getHeightLimit(height) / getGrid().getyCells();
	}

	private void vertialLines(Graphics2D graphics2d, int width, int height) {
		float columnWidth = getColumnWidth(width);

		float limit = width - getGrid().getHorizantalBorder();
		float initial = (getGrid().getHorizantalBorder() > 0) ? getGrid().getHorizantalBorder() : columnWidth;

		float yMinOffest = 0 + getGrid().getVerticalBorder();
		float yMaxOffest = height - getGrid().getVerticalBorder();

		for (float x = initial; x <= limit; x += columnWidth) {
			Line2D line2d = new Line2D.Float(x, yMinOffest, x, yMaxOffest);
			graphics2d.draw(line2d);
		}
	}

	private void horizontalLines(Graphics2D graphics2d, int width, int height) {
		float rowWidth = getRowHeight(height);

		float initial = (getGrid().getVerticalBorder() > 0) ? getGrid().getVerticalBorder() : rowWidth;

		float xMinOffest = 0 + getGrid().getHorizantalBorder();
		float xMaxOffest = width - getGrid().getHorizantalBorder();

		for (int index = 0; index <= getGrid().getyCells(); index++) {
			float y = initial + index * rowWidth;
			Line2D line2d = new Line2D.Float(xMinOffest, y, xMaxOffest, y);
			graphics2d.draw(line2d);
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}
