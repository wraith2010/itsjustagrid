package com.ten31f.battlemap.grider.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class ImageComponent extends Component {

	private transient BufferedImage image = null;
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(getImage(), 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		if (getImage() == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(getImage().getWidth(), getImage().getHeight());
		}
	}

}
