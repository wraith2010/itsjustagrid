package com.ten31f.battlemap.grider.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

@SuppressWarnings("serial")
public class ImageComponent extends Component {

	private transient BufferedImage orginalImage = null;
	private transient BufferedImage presentedImage = null;

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

}
