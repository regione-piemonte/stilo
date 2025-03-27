/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.core.server.service;

import java.util.Date;

import com.google.gson.GsonBuilder;

public class GsonBuilderFactory {
	
	public static GsonBuilder getIstance() {
		
		GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter());
		
		return builder;
	}
	
}
