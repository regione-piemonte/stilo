/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.MessageUtil;
import it.eng.utility.ui.config.MenuConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.server.common.ServiceRestUserUtil;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "AurigaMenuDataSource")
public class ServiceRestMenu extends AbstractServiceDataSource<LoginBean, MenuBean> {

	private static Logger log = Logger.getLogger(ServiceRestMenu.class);

	private List<String> privilegi = null;

	@Override
	public MenuBean call(LoginBean bean) throws Exception {
		it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
		privilegi = loginInfo != null && loginInfo.getSpecializzazioneBean() != null ? loginInfo.getSpecializzazioneBean().getPrivilegi() : null;
		if (privilegi == null || privilegi.size() == 0) {
			privilegi = ServiceRestUserUtil.getPrivilegi() != null ? Arrays.asList(ServiceRestUserUtil.getPrivilegi().getPrivilegi(getSession())) : null;
		}
		log.debug("Privilegi utente " + loginInfo.getUserid() + ": " + Arrays.toString(privilegi.toArray()));
		// Creo un menu statico
		MenuBean menu = new MenuBean();
		MenuConfigurator config = (MenuConfigurator) SpringAppContext.getContext().getBean("MenuConfigurator");
		for (MenuBean vocemenu : config.getVociMenu()) {
			menu.addSubMenu(creaCopiaVoceMenu(vocemenu));
		}
		return profilaMenu(menu);
	}

	public MenuBean profilaMenu(MenuBean menu) throws Exception {
		if (menu.getSubmenu() != null) {
			List<MenuBean> submenu = new ArrayList<MenuBean>();
			for (MenuBean vocemenu : menu.getSubmenu()) {
				if (isMenuAbilitato(vocemenu)) {
					MenuBean subvocemenu = profilaMenu(vocemenu);
					if (subvocemenu != null) {
						submenu.add(subvocemenu);
					}
				}
			}
			if (submenu.size() > 0) {
				menu.setSubmenu(submenu);
			} else {
				return null;
			}
		}
		return menu;
	}

	private Boolean isMenuAbilitato(MenuBean vocemenu) throws Exception {
		if (vocemenu.getPrivilegi() == null || vocemenu.getPrivilegi().size() == 0) {
			return true;
		} else if (privilegi == null || privilegi.size() == 0) {
			return false;
		} else {
			for (String priv : privilegi) {
				if (vocemenu.getPrivilegi().contains(priv)) {
					return true;
				}
			}
		}
		return false;
	}

	private MenuBean creaCopiaVoceMenu(MenuBean menu) {
		if (menu == null) return null;
		MenuBean ret = new MenuBean();
		ret.setNomeEntita(menu.getNomeEntita());

		String userLanguage = getLocale().getLanguage();

		String title = MessageUtil.getValue(userLanguage, getSession(), menu.getTitle());
		if (StringUtils.isNotBlank(title)) {
			ret.setTitle(title);
		} else {
			ret.setTitle(menu.getTitle());
		}
		String prompt = MessageUtil.getValue(userLanguage, getSession(), menu.getPrompt());
		if (StringUtils.isNotBlank(prompt)) {
			ret.setPrompt(prompt);
		} else {
			ret.setPrompt(menu.getPrompt());
		}
		ret.setIcon(menu.getIcon());
		ret.setPortalDesktopIcon(menu.getPortalDesktopIcon());		
		ret.setPrivilegi(menu.getPrivilegi());
		if (menu.getSubmenu() != null) {
			List<MenuBean> submenu = new ArrayList<MenuBean>();
			for (MenuBean subvocemenu : menu.getSubmenu()) {
				submenu.add(creaCopiaVoceMenu(subvocemenu));
			}
			ret.setSubmenu(submenu);
		}
		return ret;
	}

}