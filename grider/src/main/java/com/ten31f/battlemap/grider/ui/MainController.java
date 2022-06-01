package com.ten31f.battlemap.grider.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.ten31f.battlemap.grider.actions.Carver;
import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.domain.TileState;

public class MainController implements WindowListener, MouseListener, ActionListener {

	private static Logger log = Logger.getLogger(MainController.class.getName());

	private String filePath = null;
	private Grid grid = null;

	private ScrollPane presnterScrollPane = null;

	private List<Frame> frames = null;

	private Map<TileComponent, TileComponent> tileMap = null;

	public MainController(String filePath) {
		setFilePath(filePath);
		setGrid(new Grid(50, 50));
		prepareGUI();
	}

	private void prepareGUI() {

		Frame controllerframe = new Frame();

		controllerframe.setExtendedState(Frame.MAXIMIZED_BOTH);
		controllerframe.setVisible(true);
		controllerframe.setSize(500, 500);
		controllerframe.addWindowListener(this);
		controllerframe.setTitle("Controller");

		ScrollPane scrollPane = new ScrollPane();
		controllerframe.add(scrollPane);

		ViewPortHighlightPanel viewPortHighlightPanel = new ViewPortHighlightPanel();

		viewPortHighlightPanel.setBackground(Color.darkGray);
		viewPortHighlightPanel.setLayout(new GridLayout(getGrid().getyCells(), getGrid().getxCells(), 2, 2));
		scrollPane.add(viewPortHighlightPanel);

		addFrames(controllerframe);

		Frame presenterFrame = new Frame();

		presenterFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		presenterFrame.setVisible(true);
		presenterFrame.setSize(500, 500);
		presenterFrame.addWindowListener(this);
		presenterFrame.setTitle("presenter");

		setPresnterScrollPane(new ScrollPane(ScrollPane.SCROLLBARS_NEVER));
		presenterFrame.add(getPresnterScrollPane());
		viewPortHighlightPanel.setScrollPane(getPresnterScrollPane());

		Panel panel = new Panel();
		panel.setBackground(Color.darkGray);
		panel.setLayout(new GridLayout(getGrid().getyCells(), getGrid().getxCells(), 2, 2));
		getPresnterScrollPane().add(panel);

		addFrames(presenterFrame);

		setTileMap(new HashMap<>());

		List<TileComponent> controllerTileComponents = null;
		List<TileComponent> presenterImageComponents = null;
		try {
			controllerTileComponents = Carver.carve(ImageIO.read(new File(getFilePath())), getGrid());

			for (TileComponent contorllerTileComponent : controllerTileComponents) {

				contorllerTileComponent.addMouseListener(this);
				viewPortHighlightPanel.add(contorllerTileComponent);
				contorllerTileComponent.setController(true);
			}

			presenterImageComponents = Carver.carve(ImageIO.read(new File(getFilePath())), getGrid());

			presenterImageComponents.stream().forEach(panel::add);

			for (int index = 0; index < presenterImageComponents.size(); index++) {
				getTileMap().put(controllerTileComponents.get(index), presenterImageComponents.get(index));
			}

		} catch (IOException ioException) {
			log.log(Level.SEVERE, "Error loading image", ioException);
		}

	}

	private void repaint() {
		if (getFrames() != null)
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

	private String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		if (log.isLoggable(Level.FINE)) {
			log.fine(String.format("image:(%s) exists: %s", filePath, new File(filePath).exists()));
		}
		this.filePath = filePath;
	}

	private Grid getGrid() {
		return grid;
	}

	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	private Map<TileComponent, TileComponent> getTileMap() {
		return tileMap;
	}

	private void setTileMap(Map<TileComponent, TileComponent> tileMap) {
		this.tileMap = tileMap;
	}

	private ScrollPane getPresnterScrollPane() {
		return presnterScrollPane;
	}

	private void setPresnterScrollPane(ScrollPane presnterScrollPane) {
		this.presnterScrollPane = presnterScrollPane;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// do nothing for now
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// do nothing for now
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {
		System.exit(0);

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// do nothing for now
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// do nothing for now
	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {
		repaint();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// do nothing for now
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

		TileComponent controllerTileComponent = (TileComponent) mouseEvent.getComponent();

		if (controllerTileComponent == null)
			return;

		switch (mouseEvent.getButton()) {
		case MouseEvent.BUTTON1:

			controllerTileComponent.setSelected(!controllerTileComponent.isSelected());
			controllerTileComponent.repaint();

			break;
		case MouseEvent.BUTTON3:

			PopupMenu popupMenu = new PopupMenu();

			MenuItem menuItem = new MenuItem("Present");
			menuItem.addActionListener(this);
			popupMenu.add(menuItem);

			menuItem = new MenuItem("Fogged");
			menuItem.addActionListener(this);
			popupMenu.add(menuItem);

			menuItem = new MenuItem("Hidden");
			menuItem.addActionListener(this);
			popupMenu.add(menuItem);

			menuItem = new MenuItem("Revealed");
			menuItem.addActionListener(this);
			popupMenu.add(menuItem);

			controllerTileComponent.add(popupMenu);

			popupMenu.show(controllerTileComponent, mouseEvent.getX(), mouseEvent.getY());

			break;
		default:
			break;
		}

	}

	private void present(TileComponent presenterTileComponent) {
		int x = presenterTileComponent.getX() - ((int) (getPresnterScrollPane().getBounds().getWidth() / 2));
		int y = presenterTileComponent.getY() - ((int) (getPresnterScrollPane().getBounds().getHeight() / 2));

		getPresnterScrollPane().setScrollPosition(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing for now
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing for now
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing for now
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing for now
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		MenuItem menuItem = (MenuItem) actionEvent.getSource();
		PopupMenu popupMenu = (PopupMenu) menuItem.getParent();
		TileComponent controllerTileComponent = (TileComponent) popupMenu.getParent();
		TileComponent presnterTileComponent = getTileMap().get(controllerTileComponent);

		List<TileComponent> selectedTiles = getTileMap().entrySet().stream()
				.filter(entry -> entry.getKey().isSelected()).map(Entry::getKey).toList();

		List<TileComponent> selectedCounterParts = getTileMap().entrySet().stream()
				.filter(entry -> entry.getKey().isSelected()).map(Entry::getValue).toList();

		switch (actionEvent.getActionCommand()) {

		case "Present":
			present(controllerTileComponent);
			break;
		case "Fogged":
			if (!selectedTiles.isEmpty()) {

				selectedTiles.stream().forEach(tile -> tile.setTileState(TileState.FOGGED));
				selectedCounterParts.stream().forEach(tile -> tile.setTileState(TileState.FOGGED));

				selectedTiles.stream().forEach(TileComponent::repaint);
				selectedCounterParts.stream().forEach(TileComponent::repaint);
			} else {
				controllerTileComponent.setTileState(TileState.FOGGED);
				presnterTileComponent.setTileState(TileState.FOGGED);

				controllerTileComponent.repaint();
				presnterTileComponent.repaint();
			}
			break;
		case "Hidden":
			if (!selectedTiles.isEmpty()) {

				selectedTiles.stream().forEach(tile -> tile.setTileState(TileState.HIDDEN));
				selectedCounterParts.stream().forEach(tile -> tile.setTileState(TileState.HIDDEN));

				selectedTiles.stream().forEach(TileComponent::repaint);
				selectedCounterParts.stream().forEach(TileComponent::repaint);
			} else {
				controllerTileComponent.setTileState(TileState.HIDDEN);
				presnterTileComponent.setTileState(TileState.HIDDEN);

				controllerTileComponent.repaint();
				presnterTileComponent.repaint();
			}
			break;
		case "Revealed":
			if (!selectedTiles.isEmpty()) {

				selectedTiles.stream().forEach(tile -> tile.setTileState(TileState.REVEALED));
				selectedCounterParts.stream().forEach(tile -> tile.setTileState(TileState.REVEALED));

				selectedTiles.stream().forEach(TileComponent::repaint);
				selectedCounterParts.stream().forEach(TileComponent::repaint);
			} else {
				controllerTileComponent.setTileState(TileState.REVEALED);
				presnterTileComponent.setTileState(TileState.REVEALED);

				controllerTileComponent.repaint();
				presnterTileComponent.repaint();
			}
			break;
		default:
			break;

		}

	}

}
