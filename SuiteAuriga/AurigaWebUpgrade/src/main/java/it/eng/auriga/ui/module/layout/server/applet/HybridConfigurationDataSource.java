/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import it.eng.areas.hybrid.deploy.beans.HybridPropertyConfigurator;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;



@Datasource(id="HybridConfigurationDataSource")
public class HybridConfigurationDataSource extends AbstractServiceDataSource<Object, HybridPropertyConfigurator>{
	
	@Override
	public HybridPropertyConfigurator call(Object bean) throws Exception {
		
		HybridPropertyConfigurator hybridConfigBean = null;
		ApplicationContext context = SpringAppContext.getContext();
		if (context != null && context.containsBeanDefinition("HybridPropertyConfigurator") && context.getBean("HybridPropertyConfigurator") != null) {
			hybridConfigBean = (HybridPropertyConfigurator) context.getBean("HybridPropertyConfigurator");
			// Questi valori predefiniti sono presenti anche nella classe HybridServlet di HybridDeployer 
			if (StringUtils.isBlank(hybridConfigBean.getHybridPort())) {
				hybridConfigBean.setHybridPort("8181");
			}
			if (StringUtils.isBlank(hybridConfigBean.getHybridPortSSL())) {
				hybridConfigBean.setHybridPortSSL("8183");
			}
			if (StringUtils.isBlank(hybridConfigBean.getHybridWorkFolder())) {
				hybridConfigBean.setHybridWorkFolder(".areas-hybrid");
			}
		} else {
			hybridConfigBean = new HybridPropertyConfigurator();
			// Questi valori predefiniti sono presenti anche nella classe HybridServlet di HybridDeployer 
			hybridConfigBean.setHybridPort("8181");
			hybridConfigBean.setHybridPortSSL("8183");
			hybridConfigBean.setHybridWorkFolder(".areas-hybrid");
		}
		
		return hybridConfigBean;
	}

}
