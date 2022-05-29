package com.ten31f.battlemap.grider.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.ten31f.battlemap.grider.actions.Carver;
import com.ten31f.battlemap.grider.actions.Shader;
import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.domain.TileState;

public class MainController implements WindowListener, MouseListener {

	private static Logger LOGGER = Logger.getLogger(MainController.class.getName());

	private Frame presentorFrame = null;
	private Frame dmFrame = null;

	private String filePath = null;
	private Grid grid = null;

	private List<Frame> frames = null;

	private Map<ImageComponent, ImageComponent> imageComponentMap = null;

	public MainController(String filePath) {
		setFilePath(filePath);
		setGrid(new Grid(50, 50));
		prepareGUI();
		repaint();
	}

	private void prepareGUI() {

		Frame frame = new Frame();
		setPresentorFrame(frame);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.addWindowListener(this);
		frame.setTitle("Controller");

		ScrollPane scrollPane = new ScrollPane();
		frame.add(scrollPane);

		Panel panel = new Panel();
		panel.setBackground(Color.darkGray);
		panel.setLayout(new GridLayout(getGrid().getyCells(), getGrid().getxCells(), 2, 2));
		scrollPane.add(panel);

		List<ImageComponent> contorllerImageComponents = null;
		try {
			contorllerImageComponents = Carver.carve(ImageIO.read(new File(getFilePath())), getGrid());

			for (ImageComponent imageComponent : contorllerImageComponents) {

				panel.add(imageComponent);
			}

		} catch (IOException ioException) {
			LOGGER.log(Level.SEVERE, "Error loading image", ioException);
		}

		addFrames(frame);

		frame = new Frame();
		setDmFrame(frame);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.addWindowListener(this);
		frame.setTitle("presenter");

		scrollPane = new ScrollPane();
		frame.add(scrollPane);

		panel = new Panel();
		panel.setBackground(Color.darkGray);
		panel.setLayout(new GridLayout(getGrid().getyCells(), getGrid().getxCells(), 2, 2));
		scrollPane.add(panel);

		List<ImageComponent> presenterImageComponents = null;
		try {
			presenterImageComponents = Carver.carve(ImageIO.read(new File(getFilePath())), getGrid());

			for (ImageComponent imageComponent : presenterImageComponents) {

				panel.add(imageComponent);
			}

		} catch (IOException ioException) {
			LOGGER.log(Level.SEVERE, "Error loading image", ioException);
		}

		setImageComponentMap(new HashMap<>());

		for (int index = 0; index < presenterImageComponents.size(); index++) {

			contorllerImageComponents.get(index).addMouseListener(this);

			getImageComponentMap().put(contorllerImageComponents.get(index), presenterImageComponents.get(index));
		}

		addFrames(frame);

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

	private String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		LOGGER.info(String.format("image:(%s) exists: %s", filePath, new File(filePath).exists()));
		this.filePath = filePath;
	}

	private Grid getGrid() {
		return grid;
	}

	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	private Map<ImageComponent, ImageComponent> getImageComponentMap() {
		return imageComponentMap;
	}

	private void setImageComponentMap(Map<ImageComponent, ImageComponent> imageComponentMap) {
		this.imageComponentMap = imageComponentMap;
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

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

		ImageComponent controllerImageComponent = (ImageComponent) mouseEvent.getComponent();

		controllerImageComponent
				.setPresentedImage(Shader.shade(controllerImageComponent.getOrginalImage(), TileState.FOGGED));

		ImageComponent presenterImageComponent = getImageComponentMap().get(controllerImageComponent);

		presenterImageComponent
				.setPresentedImage(Shader.shade(presenterImageComponent.getOrginalImage(), TileState.FOGGED));

		controllerImageComponent.repaint();
		presenterImageComponent.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
