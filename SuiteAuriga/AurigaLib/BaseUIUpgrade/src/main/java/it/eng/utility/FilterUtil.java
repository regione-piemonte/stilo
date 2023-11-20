/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.user.UserUtil;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class FilterUtil {

	// Metodo che restituisce il filtro tradotto dalla sessione
	public static FilterConfigurator getFilterConfiguratorTradotto(String nomeFiltroTradotto, HttpSession session) {
		if (session != null) {
			return (FilterConfigurator) session.getAttribute(nomeFiltroTradotto);
		} else {
			return null;
		}
	}

	// Metodo che salva il filtro tradotto in sessione
	public static void saveFilterConfiguratorTradotto(String nomeFiltroTradotto, FilterConfigurator filterConfiguratorTradotto,
			HttpSession session) {
		if (session != null) {
			session.setAttribute(nomeFiltroTradotto, filterConfiguratorTradotto);
		}
	}

	// Metodo che rimuove il filtro tradotto dalla sessione
	public static void removeFilterConfiguratorTradotto(String nomeFiltroTradotto, HttpSession session) {
		if (session != null) {
			session.removeAttribute(nomeFiltroTradotto);
		}
	}
	
	// Restituisce il nome del filtro tradotto
	public static String getNomeFiltroTradotto(HttpSession session) {
		String nomeFiltroTradotto = "FilterConfigurator_tradotto";
		String userLanguage = UserUtil.getLocale(session).getLanguage();
		// Ottengo la locale completa, che comprende lingua utente e dominio di specializzione
		String userLocale = UserUtil.getUserLocaleFromUserLanguage(userLanguage);
		if (StringUtils.isNotBlank(userLocale)) {
			nomeFiltroTradotto = nomeFiltroTradotto + "_" + userLocale;
		}
		return nomeFiltroTradotto;
	}
}
