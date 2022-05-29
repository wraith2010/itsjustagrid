package com.ten31f.battlemap.grider.domain;

public class Grid {

	private int xCells = 10;
	private int yCells = 10;

	public Grid() {

	}

	public Grid(int xCells, int yCells) {
		setxCells(xCells);
		setyCells(yCells);
	}

	public int getxCells() {
		return xCells;
	}

	public void setxCells(int xCells) {
		this.xCells = xCells;
	}

	public int getyCells() {
		return yCells;
	}

	public void setyCells(int yCells) {
		this.yCells = yCells;
	}

	public String print() {
		return String.format("Grid(%s,%s)", getxCells(), getyCells());
	}

}
