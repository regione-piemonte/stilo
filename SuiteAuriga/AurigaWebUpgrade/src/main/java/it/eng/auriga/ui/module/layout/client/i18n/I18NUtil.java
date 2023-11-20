/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.defaults.Messages;

import com.google.gwt.core.client.GWT;

/**
 * Classe di utilità per il recupero dei messaggi internazionalizzati
 * @author michele
 *
 */
public class I18NUtil {

	private static final Messages MESSAGES = ((I18NMessagesFactory)GWT.create(I18NMessagesFactory.class)).getMessages();
	
	/**
	 * Ritorna i messaggi internazionalizzati
	 * @return
	 */
	public static Messages getMessages(){
		return MESSAGES;
	}
	
}
