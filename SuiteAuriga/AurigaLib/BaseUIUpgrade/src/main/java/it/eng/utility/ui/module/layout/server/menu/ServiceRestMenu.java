/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.config.MenuConfigurator;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

@Datasource(id="MenuDataSource")
public class ServiceRestMenu extends AbstractServiceDataSource<LoginBean, MenuBean>{

	@Override
	public MenuBean call(LoginBean bean) throws Exception {		
		
		//Creo un menu statico
		MenuBean menu = new MenuBean();		
						
		MenuConfigurator config = (MenuConfigurator) SpringAppContext.getContext().getBean("MenuConfigurator");
		for(MenuBean voceMenu : config.getVociMenu()) {
			menu.addSubMenu(voceMenu);
		}
		return menu;
	}

}
