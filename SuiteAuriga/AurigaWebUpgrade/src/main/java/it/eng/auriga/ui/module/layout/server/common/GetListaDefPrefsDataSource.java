/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetlistdefprefsBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.client.DmpkUtilityGetlistdefprefs;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.GetListaDefPrefsBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "AurigaGetListaDefPrefsDataSource")
public class GetListaDefPrefsDataSource extends AbstractServiceDataSource<GetListaDefPrefsBean, GetListaDefPrefsBean> {

	@Override
	public GetListaDefPrefsBean call(GetListaDefPrefsBean bean) throws Exception {
		SchemaBean schemaBean = new SchemaBean();
		schemaBean.setSchema(AurigaUserUtil.getLoginInfo(getSession()).getSchema());

		DmpkUtilityGetlistdefprefsBean input = new DmpkUtilityGetlistdefprefsBean();
		if (bean.getPrefKeyPrefix() != null && bean.getPrefKeyPrefix().endsWith(".")) {
			bean.setPrefKeyPrefix(bean.getPrefKeyPrefix().substring(0, bean.getPrefKeyPrefix().length() - 1));
		}
		input.setListidin(bean.getPrefKeyPrefix());
		input.setUsernamein(bean.getUserId());
		if(StringUtils.isNotBlank(bean.getIdNode())) {
			input.setIdtreenodein(bean.getIdNode());
		}
		if(StringUtils.isNotBlank(bean.getFinalita())) {
			input.setFinalitain(bean.getFinalita());
		}
		
		DmpkUtilityGetlistdefprefs store = new DmpkUtilityGetlistdefprefs();
		StoreResultBean<DmpkUtilityGetlistdefprefsBean> output = store.execute(getLocale(), schemaBean, input);

		if (output.isInError()) {
			throw new StoreException(output);
		}

		bean.setLayoutLista(output.getResultBean().getGridprefout());
		bean.setFlgLayoutListaDefault(output.getResultBean().getFlggridprefdefout() != null
				? String.valueOf(output.getResultBean().getFlggridprefdefout()) : null);
		bean.setRicercaPreferita(output.getResultBean().getFilterprefout());
		bean.setFlgRicercaPreferitaDefault(output.getResultBean().getFlgfilterprefdefout() != null
				? String.valueOf(output.getResultBean().getFlgfilterprefdefout()) : null);
		bean.setLayoutFiltro(output.getResultBean().getFilterlayoutprefout());
		bean.setFlgLayoutFiltroDefault(output.getResultBean().getFlgfilterlayoutprefdefout() != null
				? String.valueOf(output.getResultBean().getFlgfilterlayoutprefdefout()) : null);
		bean.setAutosearch(output.getResultBean().getAutosearchprefout() != null
				&& output.getResultBean().getAutosearchprefout().intValue() == 1 ? "true" : "");

		return bean;
	}

}
