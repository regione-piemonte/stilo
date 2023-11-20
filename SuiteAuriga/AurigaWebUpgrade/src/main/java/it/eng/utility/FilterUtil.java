/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;

/**
 * 
 * @author Federico Cacco
 *
 * Questa classe sovrascrive quella presente in BaseUI, consentendo di personalizzare
 * i metodi di salvataggio dei filtri tradotti (in questo caso essi vengono piazzati a livello
 * applicativo, e non in sessione come va l'implementazione presente in BaseUI)
 */

public class FilterUtil {
	
	private static Logger mLogger = Logger.getLogger(FilterUtil.class);
	
	static GenericConfigBean genericConfig = null;

	// Metodo che restituisce il filtro tradotto a livello applicazione
	// Nei prodotti Auriga e derivati salvo il filtro tradotto a livello
	// applicativo, in modo che possa essere condiviso
	// tra più utenti. 
	// In questo modo solamanete il primo utente che necessita
	// di un determninato filtro tradotto deve farsi carico della sua traduzione
	public static FilterConfigurator getFilterConfiguratorTradotto(String nomeFiltroTradotto, HttpSession session) {
		if (session != null) {
			return (FilterConfigurator) session.getServletContext().getAttribute(nomeFiltroTradotto);
		} else {
			return null;
		}
	}

	// Metodo che salva il filtro tradotto a livello applicazione
	// Nei prodotti Auriga e derivati salvo il filtro tradotto a livello
	// applicativo, in modo che possa essere condiviso
	// tra più utenti. In questo modo solamanete il primo utente che necessita
	// di un determninato
	// filtro tradotto deve farsi carico della sua traduzione
	public static void saveFilterConfiguratorTradotto(String nomeFiltroTradotto, FilterConfigurator filterConfiguratorTradotto,
			HttpSession session) {
		if (session != null) {
			session.getServletContext().setAttribute(nomeFiltroTradotto, filterConfiguratorTradotto);
		}
	}

	// Metodo che rimuove il filtro tradotto a livello applicazione
	// Nei prodotti Auriga e derivati salvo il filtro tradotto a livello
	// applicativo, in modo che possa essere condiviso
	// tra più utenti. 
	// In questo modo solamanete il primo utente che necessita
	// di un determninato filtro tradotto deve farsi carico della sua traduzione
	// Questa implementazione non effettua nessuna rimozione, in quanto il
	// filtro si trova a livello applicativo ed è
	// condiviso tra più utenti.
	public static void removeFilterConfiguratorTradotto(String nomeFiltroTradotto, HttpSession session) {
	}
	
	// Restituisce il nome del filtro tradotto
	public static String getNomeFiltroTradotto(HttpSession session) {
		String nomeFiltroTradotto = "FilterConfigurator_tradotto";
		String userLanguage = UserUtil.getLocale(session).getLanguage();
		String dominioSpecGUI = "";
		//IMPORTANTE: bisogna recuperare l'applicationName direttamente dalle configurazioni, solo se qui non è settato provo a recuperarmelo dalla sessione
		String applicationName = getGenericConfigBean().getApplicationName();
		if(StringUtils.isBlank(applicationName)) {
			applicationName = session != null ? (String) session.getAttribute("APPLICATION_NAME") : null;				
		}		
		try {
			dominioSpecGUI = ParametriDBUtil.getParametroDB(session, "SPEC_LABEL_GUI");
		} catch (NullPointerException e) {
			mLogger.debug("Errore durante la lettura del parametro SPEC_LABEL_GUI, restituisco stringa vuota", e);
			dominioSpecGUI = "";
		}
		// Ottengo la locale completa, che comprende lingua utente e dominio di specializzione
		String userLocale = UserUtil.getUserLocaleFromUserLanguageAndDominioSpecGUI(userLanguage, dominioSpecGUI);
		if (StringUtils.isNotBlank(applicationName)) {
			nomeFiltroTradotto = nomeFiltroTradotto + "_" + applicationName;
		}
		if (StringUtils.isNotBlank(userLocale)) {
			nomeFiltroTradotto = nomeFiltroTradotto + "_" + userLocale;
		}
		return nomeFiltroTradotto;
	}
	
	private static GenericConfigBean getGenericConfigBean() {
		if (genericConfig != null) {
			return genericConfig;
		} else {
			genericConfig = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
			return genericConfig;
		}
	}
}
