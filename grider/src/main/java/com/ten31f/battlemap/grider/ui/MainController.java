package com.ten31f.battlemap.grider.ui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JScrollPane;

import com.ten31f.battlemap.grider.actions.Drawer;
import com.ten31f.battlemap.grider.domain.Grid;

public class MainController implements WindowListener {

	private static Logger LOGGER = Logger.getLogger(MainController.class.getName());

	private Frame presentorFrame = null;
	private Frame dmFrame = null;

	private ImageComponent imageComponent = null;

	private String filePath = null;
	private Grid grid = null;

	private List<Frame> frames = null;

	public MainController(String filePath) {
		prepareGUI();
		setFilePath(filePath);
		setGrid(new Grid(50, 50));
		render();
	}

	private void prepareGUI() {

		Frame frame = new Frame();
		setPresentorFrame(frame);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.addWindowListener(this);
		addFrames(frame);

		frame = new Frame();
		setDmFrame(frame);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.addWindowListener(this);
		
		setImageComponent(new ImageComponent());
		frame.add(new JScrollPane(getImageComponent()));
		
		
		addFrames(frame);

//		Button button = (Button) getControlpanelFrame().add(new Button("X++"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				getGrid().setxCells(getGrid().getxCells() + 1);
//				render();
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("X--"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (getGrid().getxCells() > 1) {
//					getGrid().setxCells(getGrid().getxCells() - 1);
//					render();
//				}
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("Y++"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				getGrid().setyCells(getGrid().getyCells() + 1);
//				render();
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("Y--"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (getGrid().getyCells() > 1) {
//					getGrid().setyCells(getGrid().getyCells() - 1);
//					render();
//				}
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("Vertical++"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				getGrid().setVerticalBorder(getGrid().getVerticalBorder() + 1f);
//				render();
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("Vertical--"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (getGrid().getVerticalBorder() >= 0f) {
//					getGrid().setVerticalBorder(getGrid().getVerticalBorder() - 1f);
//					render();
//				}
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("Horizontal++"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				getGrid().setHorizantalBorder(getGrid().getHorizantalBorder() + 1f);
//				render();
//			}
//		});
//
//		button = (Button) getControlpanelFrame().add(new Button("Horizontal--"));
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (getGrid().getHorizantalBorder() >= 0f) {
//					getGrid().setHorizantalBorder(getGrid().getHorizantalBorder() - 1f);
//					render();
//				}
//			}
//		});

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
		getFrames().stream().forEach(Frame::repaint);
	}

	private void setFrames(List<Frame> frames) {
		this.frames = frames;
	}

	private List<Frame> getFrames() {
		return frames;
	}

	private void addFrames(Frame frame) {
		if (getFrames() == null)
			setFrames(new ArrayList<>());

		getFrames().add(frame);
	}

	private Frame getDmFrame() {
		return dmFrame;
	}

	private void setDmFrame(Frame dmFrame) {
		this.dmFrame = dmFrame;
	}

	private Frame getPresentorFrame() {
		return presentorFrame;
	}

	private void setPresentorFrame(Frame presentorFrame) {
		this.presentorFrame = presentorFrame;
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
