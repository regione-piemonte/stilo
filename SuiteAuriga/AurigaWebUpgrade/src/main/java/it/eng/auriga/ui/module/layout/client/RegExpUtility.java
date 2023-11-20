/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

/**
 * C'è la classe analoga nel package it.eng.auriga.ui.module.layout.server; *****TENERLE ALLINEATE*******
 *
 */


public class RegExpUtility {
	
	public static String codiceFiscalePIVARegExp(){
//		return "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$|^[0-9]{11}$";
		String regExp = UserInterfaceFactory.getParametroDB("REGEXP_PIVA_CF");
		return (regExp != null && !"".equals(regExp)) ? regExp : "^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})|([0-9]{11})$";
	}
	
	public static String codiceFiscaleRegExp(){
//		return "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
		String regExp = UserInterfaceFactory.getParametroDB("REGEXP_CF_PERS_FISICA");
		return (regExp != null && !"".equals(regExp)) ? regExp : "^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})$";
	}
	
	public static String partitaIvaRegExp(){
		String regExp = UserInterfaceFactory.getParametroDB("REGEXP_PIVA");
		return (regExp != null && !"".equals(regExp)) ? regExp : "^([0-9]{11})$";
	}
	
	public static String indirizzoEmailRegExp(){
		String regExp = UserInterfaceFactory.getParametroDB("REGEXP_EMAIL");
		return (regExp != null && !"".equals(regExp)) ? regExp : "^([_.\\]*[0-9a-zA-Z]([-.&\\w]*[0-9a-zA-Z-_])*@(([0-9a-zA-Z])+([-\\w]*[0-9a-zA-Z])*\\.)+[a-zA-Z]{2,9})$";
	}
	
	public static String htmlRegExp(){
		String regExp = UserInterfaceFactory.getParametroDB("REGEXP_HTML");
		return (regExp != null && !"".equals(regExp)) ? regExp : "^<([A-Za-z][A-Za-z0-9]*)\b[^>]*>(.*?)</\1>$";
	}
}