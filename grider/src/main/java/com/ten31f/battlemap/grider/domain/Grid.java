package com.ten31f.battlemap.grider.domain;

import java.awt.Color;

public class Grid {

	private int xCells = 3;
	private int yCells = 3;
	private float horizantalBorder = 0;
	private float verticalBorder = 0;
	private float luminosityThresholdUpperLimit = 0.5f;
	private float luminosityThresholdLowerLimit = 0.4f;
	private int targetColor = Color.BLACK.getRGB();

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

	public float getLuminosityThresholdUpperLimit() {
		return luminosityThresholdUpperLimit;
	}

	public void setLuminosityThresholdUpperLimit(float luminosityThresholdUpperLimit) {
		this.luminosityThresholdUpperLimit = luminosityThresholdUpperLimit;
	}

	public float getLuminosityThresholdLowerLimit() {
		return luminosityThresholdLowerLimit;
	}

	public void setLuminosityThresholdLowerLimit(float luminosityThresholdLowerLimit) {
		this.luminosityThresholdLowerLimit = luminosityThresholdLowerLimit;
	}

	public int getTargetColor() {
		return targetColor;
	}

	public void setTargetColor(int targetColor) {
		this.targetColor = targetColor;
	}

	public String print() {
		return String.format("Grid(%s,%s,%s,%s,%s - %s,(White=%s Black=%s)%s)", getxCells(), getyCells(),
				getHorizantalBorder(), getVerticalBorder(), getLuminosityThresholdLowerLimit(),
				getLuminosityThresholdUpperLimit(), Color.WHITE.getRGB(), Color.BLACK.getRGB(), getTargetColor());
	}

}
