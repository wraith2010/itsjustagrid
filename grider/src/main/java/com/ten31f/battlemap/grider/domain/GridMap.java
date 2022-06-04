package com.ten31f.battlemap.grider.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

public class GridMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private BufferedImage bufferedImage = null;

	private Grid grid = null;

	private TileState[][] tileStates = null;

	private transient File file = null;

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public TileState[][] getTileStates() {
		return tileStates;
	}

	public void setTileStates(TileState[][] tileStates) {
		this.tileStates = tileStates;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
