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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.ten31f.battlemap.grider.actions.Carver;
import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.domain.GridMap;
import com.ten31f.battlemap.grider.domain.TileState;

public class MainController implements MouseListener, ActionListener {

	private static Logger log = Logger.getLogger(MainController.class.getName());

	private List<GridMap> gridMaps = null;
	private List<Frame> frames = new ArrayList<>();

	private ScrollPane presnterScrollPane = null;
	private ScrollPane contorllerScrollPane = null;

	private Map<TileComponent, TileComponent> tileMap = null;
	private ViewPortHighlightPanel viewPortHighlightPanel = null;

	public MainController() {
		prepareGUI();
	}

	private void prepareGUI() {

		WindowListner windowListner = new WindowListner();

		Frame controllerframe = new Frame();
		getFrames().add(controllerframe);

		controllerframe.setExtendedState(Frame.MAXIMIZED_BOTH);
		controllerframe.setVisible(true);
		controllerframe.setSize(500, 500);
		controllerframe.addWindowListener(windowListner);
		controllerframe.setTitle("Controller");

		setContorllerScrollPane(new ScrollPane());
		controllerframe.add(getContorllerScrollPane());

		setViewPortHighlightPanel(new ViewPortHighlightPanel());

		getViewPortHighlightPanel().setBackground(Color.darkGray);
		getContorllerScrollPane().add(getViewPortHighlightPanel());

		Frame presenterFrame = new Frame();
		getFrames().add(presenterFrame);

		presenterFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		presenterFrame.setVisible(true);
		presenterFrame.setSize(500, 500);
		presenterFrame.addWindowListener(windowListner);
		presenterFrame.setTitle("Presenter");

		setPresnterScrollPane(new ScrollPane(ScrollPane.SCROLLBARS_NEVER));
		presenterFrame.add(getPresnterScrollPane());
		getViewPortHighlightPanel().setScrollPane(getPresnterScrollPane());

		new MenuHandler(this, controllerframe);
	}

	private List<Frame> getFrames() {
		return frames;
	}

	public List<GridMap> getGridMaps() {
		return gridMaps;
	}

	public void setGridMaps(List<GridMap> gridMaps) {
		this.gridMaps = gridMaps;
	}

	public void addGridMap(GridMap gridMap) {
		if (getGridMaps() == null)
			setGridMaps(new ArrayList<>());

		getGridMaps().add(0, gridMap);

		loadGridMap(gridMap);
	}

	private void loadGridMap(GridMap gridMap) {

		Grid grid = gridMap.getGrid();

		setTileMap(new HashMap<>());

		getViewPortHighlightPanel().removeAll();
		getViewPortHighlightPanel().setLayout(new GridLayout(grid.getyCells(), grid.getxCells(), 2, 2));

		getPresnterScrollPane().removeAll();
		Panel panel = new Panel();
		panel.setBackground(Color.darkGray);
		panel.setLayout(new GridLayout(grid.getyCells(), grid.getxCells(), 2, 2));
		getPresnterScrollPane().add(panel);

		List<TileComponent> presenterImageComponents = null;
		List<TileComponent> controllerTileComponents = Carver.carve(gridMap.getBufferedImage(), grid);

		for (TileComponent contorllerTileComponent : controllerTileComponents) {
			contorllerTileComponent.addMouseListener(this);
			getViewPortHighlightPanel().add(contorllerTileComponent);
			contorllerTileComponent.setController(true);
		}

		presenterImageComponents = Carver.carve(gridMap.getBufferedImage(), grid);
		presenterImageComponents.stream().forEach(panel::add);

		for (int index = 0; index < presenterImageComponents.size(); index++) {
			getTileMap().put(controllerTileComponents.get(index), presenterImageComponents.get(index));
		}

		repaint();
	}

	private ViewPortHighlightPanel getViewPortHighlightPanel() {
		return viewPortHighlightPanel;
	}

	private void setViewPortHighlightPanel(ViewPortHighlightPanel viewPortHighlightPanel) {
		this.viewPortHighlightPanel = viewPortHighlightPanel;
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

	private ScrollPane getContorllerScrollPane() {
		return contorllerScrollPane;
	}

	private void setContorllerScrollPane(ScrollPane contorllerScrollPane) {
		this.contorllerScrollPane = contorllerScrollPane;
	}

	private void repaint() {
		getFrames().stream().forEach(Frame::repaint);
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
