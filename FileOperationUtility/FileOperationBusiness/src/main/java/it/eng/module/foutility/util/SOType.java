/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.module.foutility.util;


public enum SOType {

	LINUX("LINUX"),
	WINDOWS("WINDOWS");
	
	private final String name;
	
	SOType(String v) {
		name = v;
    }
    
}
