/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

public class UserUtil {
	
	private static Logger mLogger = Logger.getLogger(UserUtil.class);
	
	public static void setLoginInfo(HttpSession session, LoginBean loginInfo){	
		session.setAttribute("LOGIN_INFO", loginInfo);
		mLogger.debug("Messo in sessione "+session.getId() + " "+loginInfo);
	}	

	public static LoginBean getLoginInfo(HttpSession session){		
		return (LoginBean) session.getAttribute("LOGIN_INFO");
	}
	
	@Deprecated
	/**
	 * Deprecato
	 * Utilizzare getLocale(HttpSession session)
	 */
	public static Locale getLocale(){
		Locale locale = new Locale("it", "IT");
		return locale;
	}
	
	public static Locale getLocale(HttpSession session){
		if (session != null){
			LoginBean sessionLoginBean = (LoginBean) session.getAttribute("LOGIN_INFO");
			if ((sessionLoginBean != null) && (sessionLoginBean.getLinguaApplicazione() != null) && (!sessionLoginBean.getLinguaApplicazione().trim().equalsIgnoreCase(""))){
				return new Locale(sessionLoginBean.getLinguaApplicazione());
			}else{
				return new Locale("it", "IT");
			}
		}else{
			return new Locale("it", "IT");
		}
	}
	
	public static String getUserLocaleFromUserLanguage(String userLanguage){
		// La userlanguage è nel formato language_STATE (ad esempio it_IT)
		// -> language_STATE lo ricavo da userLanguage
		String userLocale = "";
		if ((userLanguage != null) && (!"".equalsIgnoreCase(userLanguage.trim()))) {
			userLocale = userLanguage + "_" + userLanguage.toUpperCase(Locale.ITALIAN);
		} 
		return userLocale;
	}
	
	public static String getUserLocaleFromUserLanguageAndDominioSpecGUI(String userLanguage, String dominioSpecGUI){
		// Concateno userLanguage con dominio nel formato userLanguage_dominio
		// Il caso generale è che user language sia del tipo language_STATE_DOMINIO (ad esempio it_IT_dominio1)		
		// -> language_STATE lo ricavo da userLanguage
		// -> DOMINIO lo ricavo da dominioSpecGUI
		// Non è detto che userLanguage e dominioSpecGUI siano entrambi presenti
		String userLocale = "";
		if ((userLanguage != null) && (!"".equalsIgnoreCase(userLanguage.trim())) && (dominioSpecGUI != null)
				&& (!"".equalsIgnoreCase(dominioSpecGUI.trim()))) {
			userLocale = userLanguage + "_" + userLanguage.toUpperCase(Locale.ITALIAN) + "_" + dominioSpecGUI;
		} else if ((userLanguage != null) && (!"".equalsIgnoreCase(userLanguage.trim()))) {
			userLocale = userLanguage + "_" + userLanguage.toUpperCase(Locale.ITALIAN);
		} else if ((dominioSpecGUI != null) && (!"".equalsIgnoreCase(dominioSpecGUI.trim()))) {
			userLocale = dominioSpecGUI;
		}
		return userLocale;
	}
}
