package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import application.actions.EnregistreurSVG;
import application.actions.EnregistreurXML;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private BorderPane root;
	public static void main(String [] args){
		launch(args);
	}

	public static FormesController controller;

	@Override
	public void start(Stage fenetre) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Formes.fxml"));
		 root = loader.load();

		controller = (FormesController)loader.getController();

		//Menu Configuration:
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(fenetre.widthProperty());
		root.setTop(menuBar);

		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu aboutMenu = new Menu("Help");
		MenuItem newMenuItem = new MenuItem("New");
		MenuItem openMenuItem = new MenuItem("Open");
		MenuItem xmlSaveMenuItem = new MenuItem("Save as an XML");
		MenuItem svgSaveMenuItem = new MenuItem("Save as an SVG");
		MenuItem exitMenuItem = new MenuItem("Exit");

		//Exit Menu Item Configuration:
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
		
		//New Menu Item Configuration:
		newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Panneau.shapes.clear();
				Panneau.formes.clear();
			}
		});

		//Open Menu Item Configuration:
		openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				
			}
		});

		//Xml Save Menu Item Configuration:
		xmlSaveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				JFileChooser dialogue = new JFileChooser(new File("."));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("FICHIER XML (*.xml)", "xml");
				dialogue.setFileFilter(filter);
				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String pathFichier = dialogue.getSelectedFile().getPath();
					if (!pathFichier.endsWith(".xml")) {
						pathFichier = pathFichier + ".xml";
					}
					EnregistreurXML enregistreurXML = new EnregistreurXML();
					try {
						enregistreurXML.enregistreDessin(pathFichier,
								Panneau.formes);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		//SVG Save Menu Item Configuration:
		svgSaveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				JFileChooser dialogue = new JFileChooser(new File("./export.svg"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("FICHIER SVG (*.svg)", "svg");
				dialogue.setFileFilter(filter);
				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String pathFichier = dialogue.getSelectedFile().getPath();
					if (!pathFichier.endsWith(".svg")) {
						pathFichier = pathFichier + ".svg";
					}
					EnregistreurSVG enregistreurSVG = new EnregistreurSVG();
					try {
						enregistreurSVG.enregistreDessin(pathFichier,
								Panneau.formes);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		fileMenu.getItems().addAll(newMenuItem, openMenuItem, new SeparatorMenuItem(), xmlSaveMenuItem, svgSaveMenuItem, 
				new SeparatorMenuItem(), exitMenuItem);

		menuBar.getMenus().addAll(fileMenu);
		menuBar.getMenus().addAll(aboutMenu);
		
		Scene scene = new Scene(root);
		fenetre.setTitle("Placement de formes");
		fenetre.setScene(scene);
		fenetre.show();
	}
}


