package com.ten31f.battlemap.grider.ui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ten31f.battlemap.grider.domain.Grid;
import com.ten31f.battlemap.grider.domain.GridMap;

public class MenuHandler implements ActionListener {

	private static final String LABEL_MENU_FILE_NEW = "NEW_FILE";
	private static final String LABEL_MENU_FILE_SAVE = "SAVE_FILE";
	private static final String LABEL_MENU_FILE_OPEN = "OPEN_FILE";
	private static final String LABEL_MENU_FILE_EXIT = "EXIT_FILE";

	private MainController mainController = null;

	public MenuHandler(MainController mainController, Frame frame) {
		setMainController(mainController);
		setupMenu(frame);
	}

	private void setupMenu(Frame frame) {
		MenuBar menuBar = new MenuBar();
		frame.setMenuBar(menuBar);

		Menu fileMenu = new Menu("File");
		menuBar.add(fileMenu);

		MenuItem newMenuItem = new MenuItem("New");
		fileMenu.add(newMenuItem);
		newMenuItem.setActionCommand(LABEL_MENU_FILE_NEW);
		newMenuItem.addActionListener(this);

		MenuItem openMenuItem = new MenuItem("Open");
		fileMenu.add(openMenuItem);
		openMenuItem.setActionCommand(LABEL_MENU_FILE_OPEN);
		openMenuItem.addActionListener(this);

		MenuItem saveMenuItem = new MenuItem("Save");
		fileMenu.add(saveMenuItem);
		saveMenuItem.setActionCommand(LABEL_MENU_FILE_SAVE);
		saveMenuItem.addActionListener(this);

		MenuItem exitMenuItem = new MenuItem("Exit");
		fileMenu.add(exitMenuItem);
		exitMenuItem.setActionCommand(LABEL_MENU_FILE_EXIT);
		exitMenuItem.addActionListener(this);

		Menu gridMenu = new Menu("Grid");
		menuBar.add(gridMenu);
		gridMenu.add(new MenuItem("Edit"));
		gridMenu.add(new MenuItem("Import"));
	}

	private void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	private MainController getMainController() {
		return mainController;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		MenuItem menuItem = (MenuItem) actionEvent.getSource();

		switch (menuItem.getActionCommand()) {
		case LABEL_MENU_FILE_NEW:
			GridMap gridMap = new GridMap();
			gridMap.setGrid(new Grid(50, 50));
			try {
				gridMap.setBufferedImage(ImageIO.read(new File("src/test/resource/testmaps/farm[50x50].jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			getMainController().addGridMap(gridMap);
			break;
		case LABEL_MENU_FILE_SAVE:
			FileDialog saveFileDialog = new FileDialog(new Frame(), "Save Grid...", FileDialog.SAVE);
			saveFileDialog.setDirectory(System.getProperty("user.dir"));
			saveFileDialog.setFile("*.grid");
			saveFileDialog.setVisible(true);
			break;
		case LABEL_MENU_FILE_OPEN:
			FileDialog openFileDialog = new FileDialog(new Frame(), "Open Grid...", FileDialog.LOAD);
			openFileDialog.setDirectory(System.getProperty("user.dir"));
			openFileDialog.setFile("*.grid");
			openFileDialog.setVisible(true);
			break;
		case LABEL_MENU_FILE_EXIT:
			System.exit(0);
			break;

		default:
		}

	}

}
