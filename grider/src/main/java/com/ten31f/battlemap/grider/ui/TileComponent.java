package com.ten31f.battlemap.grider.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import com.ten31f.battlemap.grider.domain.TileState;

@SuppressWarnings("serial")
public class TileComponent extends Component {

	private transient BufferedImage orginalImage = null;
	private transient BufferedImage presentedImage = null;

	private TileState tileState = TileState.REVEALED;

	private boolean selected = false;
	private boolean controller = false;

	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(getPresentedImage(), 0, 0, null);

		getTileState().paint(graphics, getOrginalImage().getWidth(), getOrginalImage().getHeight(), isController());

		if (isSelected()) {
			graphics.setColor(Color.RED);
			graphics.drawRect(2, 2, getOrginalImage().getWidth() - 4, getOrginalImage().getHeight() - 4);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getOrginalImage().getWidth(), getOrginalImage().getHeight());
	}

	public BufferedImage getOrginalImage() {
		ColorModel colorModel = orginalImage.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster writableRaster = orginalImage
				.copyData(orginalImage.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(colorModel, writableRaster, isAlphaPremultiplied, null);
	}

	public void setOrginalImage(BufferedImage bufferedImage) {
		this.orginalImage = bufferedImage;
		setPresentedImage(getOrginalImage());
	}

	public BufferedImage getPresentedImage() {
		return presentedImage;
	}

	public void setPresentedImage(BufferedImage presentedImage) {
		this.presentedImage = presentedImage;
	}

	public TileState getTileState() {
		return tileState;
	}

	public void setTileState(TileState tileState) {
		this.tileState = tileState;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isController() {
		return controller;
	}

	public void setController(boolean controller) {
		this.controller = controller;
	}

}
