/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.bean.FilterPropertyBean;
import it.eng.utility.ui.module.core.shared.bean.ValidationBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

import java.util.List;

@Datasource(id="FiltriObbligatoriDatasource")
public class FiltriObbligatoriDatasource extends AbstractServiceDataSource<FilterPropertyBean, ValidationBean>{

	@Override
	public ValidationBean call(FilterPropertyBean bean) throws Exception {
		//Recupero tutti i filtri dal context
		// Federico Cacco 14-10-2015
		// Carico filtro tradotto
		FilterConfigurator lFilterConfigurator = getXmlFiltriTradotto();
		//FilterConfigurator lFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("FilterConfigurator");
		
		//Recupero il filtro che interessa a me
		FilterBean lFilterBean = lFilterConfigurator.getListe().get(bean.getName());
		//Ciclo tutti i sottofiltri
		AdvancedCriteria lCriteria  = bean.getCriteria();
		if(lFilterBean != null) {
			for (FilterFieldBean lFilterFieldBean : lFilterBean.getFields()){
				if (lFilterFieldBean.isRequired()){
					if (lCriteria == null || lCriteria.getCriteria()==null){
						return notValid();
					}
					List<Criterion> lList = lCriteria.getCriteria();
					Criterion lCriterion = findCriterion(lList, lFilterFieldBean.getName());
					if (lCriterion==null) return notValid();
					if (lCriterion.getValue()==null && lCriterion.getStart()==null && lCriterion.getEnd()==null){
						return notValid();
					}
				}
			}
		}
		return isValid();
	}

	private ValidationBean isValid() {
		return new ValidationBean(true);
	}

	private Criterion findCriterion(List<Criterion> lList, String name) {
		for (Criterion lCriterion : lList){
			if (lCriterion.getFieldName().equals(name)){
				return lCriterion;
			}
		}
		return null;
	}

	private ValidationBean notValid() {
		return new ValidationBean(false);
	}

	

}
