package com.ten31f.battlemap.grider.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.logging.Logger;

public class ViewPortHighlightPanel extends Panel implements AdjustmentListener {

	private static Logger LOGGER = Logger.getLogger(ViewPortHighlightPanel.class.getName());

	private ScrollPane scrollPane = null;

	private ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
		getScrollPane().getHAdjustable().addAdjustmentListener(this);
		getScrollPane().getVAdjustable().addAdjustmentListener(this);
	}

	@Override
	public void paint(Graphics graphics) {

		super.paint(graphics);

		if (getScrollPane() != null) {

			graphics.setColor(Color.red);

			LOGGER.info(String.format("(%S,%S,%S,%S,)", getScrollPane().getHAdjustable().getValue(),
					getScrollPane().getVAdjustable().getValue(), getScrollPane().getWidth(),
					getScrollPane().getHeight()));
			graphics.drawRect(getScrollPane().getHAdjustable().getValue(), getScrollPane().getVAdjustable().getValue(),
					getScrollPane().getWidth(), getScrollPane().getHeight());

		}

	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
		this.repaint();
	}

}
