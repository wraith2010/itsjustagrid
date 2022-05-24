package com.ten31f.battlemap.grider.ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.ten31f.battlemap.grider.actions.Drawer;
import com.ten31f.battlemap.grider.domain.Grid;

public class MainController implements WindowListener {

	private static Logger LOGGER = Logger.getLogger(MainController.class.getName());

	private Frame imageFrame = null;
	private Frame controlpanelFrame = null;
	private ImageComponent imageComponent = null;

	private String filePath = null;
	private Grid grid = null;

	private static final int COLOR_CHANGE = 500;
	
	public MainController(String filePath) {
		prepareGUI();
		setFilePath(filePath);
		setGrid(new Grid(3, 3));
		render();
	}

	private void prepareGUI() {

		setImageFrame(new Frame());
		getImageFrame().setSize(800, 800);
		getImageFrame().addWindowListener(this);

		setImageComponent(new ImageComponent());

		getImageFrame().add(getImageComponent());

		setControlpanelFrame(new Frame());
		getControlpanelFrame().setSize(400, 400);

		getControlpanelFrame().addWindowListener(this);

		getControlpanelFrame().setVisible(true);
		getControlpanelFrame().setLayout(new GridLayout(7, 2));

		Button button = (Button) getControlpanelFrame().add(new Button("X++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().setxCells(getGrid().getxCells() + 1);
				render();
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("X--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getxCells() > 1) {
					getGrid().setxCells(getGrid().getxCells() - 1);
					render();
				}
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Y++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().setyCells(getGrid().getyCells() + 1);
				render();
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Y--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getyCells() > 1) {
					getGrid().setyCells(getGrid().getyCells() - 1);
					render();
				}
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Vertical++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().setVerticalBorder(getGrid().getVerticalBorder() + 1f);
				render();
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Vertical--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getVerticalBorder() >= 0f) {
					getGrid().setVerticalBorder(getGrid().getVerticalBorder() - 1f);
					render();
				}
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Horizontal++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().setHorizantalBorder(getGrid().getHorizantalBorder() + 1f);
				render();
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Horizontal--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getHorizantalBorder() >= 0f) {
					getGrid().setHorizantalBorder(getGrid().getHorizantalBorder() - 1f);
					render();
				}
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("LowerThreshhold++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				getGrid().setLuminosityThresholdLowerLimit(getGrid().getLuminosityThresholdLowerLimit() + 0.05f);
				render();

			}
		});

		button = (Button) getControlpanelFrame().add(new Button("LowerThreshhold--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getLuminosityThresholdLowerLimit() >= 0f) {
					getGrid().setLuminosityThresholdLowerLimit(getGrid().getLuminosityThresholdLowerLimit() - 0.05f);
					render();
				}

			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Threshhold++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				getGrid().setLuminosityThresholdUpperLimit(getGrid().getLuminosityThresholdUpperLimit() + 0.05f);
				render();

			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Threshhold--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getLuminosityThresholdUpperLimit() >= 0f) {
					getGrid().setLuminosityThresholdUpperLimit(getGrid().getLuminosityThresholdUpperLimit() - 0.05f);
					render();
				}

			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Color++"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getTargetColor() < Color.WHITE.getRGB()) {
					getGrid().setTargetColor(getGrid().getTargetColor() + COLOR_CHANGE);
					render();
				}
			}
		});

		button = (Button) getControlpanelFrame().add(new Button("Color--"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getGrid().getTargetColor() > Color.BLACK.getRGB()) {
					getGrid().setTargetColor(getGrid().getTargetColor() - COLOR_CHANGE);
					render();
				}

			}
		});

		getControlpanelFrame().setVisible(true);
		getImageFrame().setVisible(true);
	}

	private void render() {

		long startTime = -System.currentTimeMillis();

		try {
			getImageComponent().setImage(new Drawer(new File(getFilePath()), getGrid()).lineField());
		} catch (IOException ioException) {
			System.err.format("IO exception: %s", ioException.getMessage());
		}

		LOGGER.info(String.format("Rendered in %s mills", startTime + System.currentTimeMillis()));
		LOGGER.info(getGrid().print());
		repaint();
	}

	private void repaint() {
		getImageFrame().repaint();
		getControlpanelFrame().repaint();
	}

	private Frame getImageFrame() {
		return imageFrame;
	}

	private void setImageFrame(Frame imageFrame) {
		this.imageFrame = imageFrame;
	}

	private Frame getControlpanelFrame() {
		return controlpanelFrame;
	}

	private void setControlpanelFrame(Frame controlpanelFrame) {
		this.controlpanelFrame = controlpanelFrame;
	}

	private ImageComponent getImageComponent() {
		return imageComponent;
	}

	private void setImageComponent(ImageComponent imageComponent) {
		this.imageComponent = imageComponent;
	}

	private String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		LOGGER.info(String.format("image: %s", filePath));
		this.filePath = filePath;
	}

	private Grid getGrid() {
		return grid;
	}

	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);

	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {
		System.exit(0);

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {
		repaint();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
