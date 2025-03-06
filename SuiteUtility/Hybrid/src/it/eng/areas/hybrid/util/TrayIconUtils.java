package it.eng.areas.hybrid.util;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.Version;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleInfo;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.osgi.OSGiManager;
import it.eng.areas.hybrid.res.Assets;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class TrayIconUtils {
	private final static Logger logger = Logger.getLogger(LoggerUtils.class);
	private static TrayIcon trayIcon;

	public static void setup(final String serverUrl) {
		
			
			Image image = null;
			if (serverUrl != null && !"".equalsIgnoreCase(serverUrl) && serverUrl.indexOf("hybrid") != -1) {
				try {
					URL url = new URL(serverUrl.substring(0, serverUrl.indexOf("hybrid")) + "images/hybrid/hybrid-logo_16.png");
					image = ImageIO.read(url);
				} catch (Exception e) {
					logger.error("Impossibile leggere immagine dalla URL " + serverUrl.substring(0, serverUrl.indexOf("hybrid")) + "images/hybrid/hybrid-logo_16.png", e);
					image = null;
				}
			}
			if (image == null) {
				image = Toolkit.getDefaultToolkit().getImage(Assets.class.getResource("logo_16.png"));
			}
			PopupMenu popup = new PopupMenu();
			MenuItem aboutItem = new MenuItem("Informazioni...");
			aboutItem.setActionCommand("ABOUT");
			popup.add(aboutItem);
			popup.addSeparator();
			MenuItem chiudiItem = new MenuItem("Chiudi");
			chiudiItem.setActionCommand("EXIT");
			popup.add(chiudiItem);

			trayIcon = new TrayIcon(image, "Hybrid", popup);
			trayIcon.setToolTip("Hybrid\nVersione: " + Version.VERSION);

			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent event) {
					if ("EXIT".equals(event.getActionCommand())) {
						logger.info("Invocato evento EXIT della trayIcon");
						System.exit(0);
					} else if ("ABOUT".equals(event.getActionCommand())) {
						logger.debug("ABOUT");
						Platform.runLater(new Runnable(){

							@Override
							public void run() {
								logger.debug("Creo Alert Informazioni");
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Informazioni");
								alert.setHeaderText("Hybrid Client v. "+Version.VERSION+" build:"+Version.BUILD);
								
								IClientModuleManager clientModuleManager = OSGiManager.getInstance().getClientModuleManager();
								final String localUri = clientModuleManager.getSharedProperty(IClientModuleContainer.PARAMETER_LOCALURL);
								final String serverUri = clientModuleManager.getSharedProperty(IClientModuleContainer.PARAMETER_SERVERURL);

								BorderPane borderPane = new BorderPane();
								borderPane.setTop(new Label("Moduli installati:"));
								Hyperlink detailHyperLink = new Hyperlink("Maggiori dettagli...");
								detailHyperLink.setOnAction(new EventHandler<ActionEvent>() {
									
									@Override
									public void handle(ActionEvent event) {
										Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
									    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
									        try {
									            desktop.browse(new URI(localUri+"/info"));
									        } catch (Exception e) {
									        	logger.error(e);
									        }
									    }									}
								});
								
								Hyperlink sslHyperLink = new Hyperlink("Istruzioni per il funzionamento in https...");
								sslHyperLink.setOnAction(new EventHandler<ActionEvent>() {
									
									@Override
									public void handle(ActionEvent event) {
										Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
									    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
									        try {
									        	desktop.browse(new URI(serverUri+"/client-info-ssl.html"));
									        } catch (Exception e) {
									            logger.error(e);
									        }
									    }									}
								});
								
								
								FlowPane linkPane = new FlowPane(Orientation.VERTICAL);
								linkPane.setPrefHeight(50);
								linkPane.getChildren().add(detailHyperLink);
								linkPane.getChildren().add(sslHyperLink);
								
								borderPane.setBottom(linkPane);
								
								FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
								flowPane.setPrefHeight(200);
								flowPane.setPrefWidth(500);
								borderPane.setCenter(flowPane);
								
								 
								StringBuffer modulesInfo = new StringBuffer(); 
								for (IClientModuleInfo moduleInfo : OSGiManager.getInstance().getClientModuleManager().getRegisteredModulesInfo()) {
									String label = moduleInfo.getModuleName()+" versione:"+moduleInfo.getModuleVersion();
									modulesInfo.append("\n").append(label);
									flowPane.getChildren().add(new Label(label));
								};
								flowPane.getChildren().add(new Label(""));
								
								//alert.setContentText("Moduli installati:"+modulesInfo.toString());
								alert.getDialogPane().contentProperty().set(borderPane);
								
								String imagePath = null;
								if (serverUrl != null && !"".equalsIgnoreCase(serverUrl) && serverUrl.indexOf("hybrid") != -1) {
									try {
										URL url = new URL(serverUrl.substring(0, serverUrl.indexOf("hybrid")) + "images/hybrid/hybrid-logo_48.png");
										imagePath = url.toString();
									} catch (Exception e) {
										logger.error("Impossibile leggere immagine dalla URL " + serverUrl.substring(0, serverUrl.indexOf("hybrid")) + "images/hybrid/hybrid-logo_48.png", e);
										imagePath = null;
									}
								}
								if (imagePath == null) {
									imagePath = Assets.class.getResource("logo_48.png").toString();
								}
								
								
								alert.setGraphic(new ImageView(imagePath));
								alert.show();	
							}
						});
					}
				};
			};

			ActionListener listenerTray = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent arg0) {
					// TODO: Manage double click!
					
				}
			};

			trayIcon.addActionListener(listenerTray);
			chiudiItem.addActionListener(listener);
			aboutItem.addActionListener(listener);

			
			logger.info("Tray icon inited");
		
	}
	
	public static void show(){
		if (SystemTray.isSupported()) {
			try {
				SystemTray tray = SystemTray.getSystemTray();
				tray.add(trayIcon);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			throw new RuntimeException("Tray Icon is not available");
		}
	}

	public static TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public static void displayMessage(String caption, String text, MessageType messageType) {
		trayIcon.displayMessage(caption, text, messageType);
	}

}
