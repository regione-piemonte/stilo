/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NMessagesFactory;

import com.google.gwt.core.client.GWT;

public class I18NDefaultMessagesFactory implements I18NMessagesFactory {

	 private static final Messages i18n = (Messages) GWT.create(Messages.class);
	
	 @Override
	 public Messages getMessages() {
		 return i18n;
	 }

}
