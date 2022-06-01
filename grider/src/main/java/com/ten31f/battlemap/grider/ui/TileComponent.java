package com.ten31f.battlemap.grider.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

import com.ten31f.battlemap.grider.domain.TileState;

@SuppressWarnings("serial")
public class TileComponent extends Component {

	private transient BufferedImage orginalImage = null;
	private transient BufferedImage presentedImage = null;

	private List<TileState> tileStates = null;

	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(getPresentedImage(), 0, 0, null);
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

	public List<TileState> getTileStates() {
		return tileStates;
	}

	public void setTileStates(List<TileState> tileStates) {
		this.tileStates = tileStates;
	}

	public void addTileState(TileState tileState) {
		if (getTileStates() == null) {
			setTileStates(new ArrayList<>());
		}
		getTileStates().add(tileState);
	}

	public void removeTileState(TileState tileState) {
		if (getTileStates() == null)
			return;
		getTileStates().remove(tileState);
	}

}
