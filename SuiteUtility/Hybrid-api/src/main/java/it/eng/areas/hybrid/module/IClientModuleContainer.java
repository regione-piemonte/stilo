package it.eng.areas.hybrid.module;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;


public interface IClientModuleContainer {
	
	public static final String PARAMETER_SERVERURL = "serverUrl";
	public static final String PARAMETER_SERVERSESSION = "serverSession";
	public static final String PARAMETER_LOCALURL = "localUrl";
	
	
	/**
	 * @param parameterName 
	 * @return il parametro legato al modulo o condiviso
	 */
	String getParameter(String parameterName);
	
	/**
	 * Assegna il parametro condiviso
	 * @param parameterName
	 * @param value
	 */
	void setSharedParameter(String parameterName, String value);

	/**
	 * Invia un messagio al browser tramite WebSocket
	 * @param message
	 */
	void sendMessage(String message) throws IOException;

	/**
	 * Richiedi il pannello associato al modulo (lo crea se non esiste)
	 */
	Object getMainPanel();
	
	
	/**
	 * Forza la visualizzazione del pannello
	 */
	void showMainPanel();
	
	
	/**
	 * Mostro un messaggio attraverso la TrayIcon
	 * @param caption
	 * @param text
	 * @param messageType
	 */
	void displayTrayIconMessage(String caption, String text, MessageType messageType);
	
}