package com.ten31f.battlemap.grider.domain;

public class Grid {

	private int xCells = 3;
	private int yCells = 3;
	private float horizantalBorder = 0;
	private float verticalBorder = 0;

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

	public float getHorizantalBorder() {
		return horizantalBorder;
	}

	public void setHorizantalBorder(float horizantalBorder) {
		this.horizantalBorder = horizantalBorder;
	}

	public float getVerticalBorder() {
		return verticalBorder;
	}

	public void setVerticalBorder(float verticalBorder) {
		this.verticalBorder = verticalBorder;
	}

	public String print() {
		return String.format("Grid(%s,%s,%s,%s)", getxCells(), getyCells(), getHorizantalBorder(), getVerticalBorder());
	}

}
