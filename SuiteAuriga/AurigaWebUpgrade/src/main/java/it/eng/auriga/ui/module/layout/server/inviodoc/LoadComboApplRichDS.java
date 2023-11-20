/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovarichinvioemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.inviodoc.bean.InvioDocViaMailFilterSezioneCache;
import it.eng.client.DmpkIntMgoEmailTrovarichinvioemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Datasource(id = "LoadComboApplRichDS")
public class LoadComboApplRichDS extends OptionFetchDataSource<SimpleKeyValueBean> {

	private List<SimpleKeyValueBean> lListResult;

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		lListResult = new ArrayList<SimpleKeyValueBean>();

		DmpkIntMgoEmailTrovarichinvioemailBean lDmpkIntMgoEmailTrovarichinvioemailBean = new DmpkIntMgoEmailTrovarichinvioemailBean();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		InvioDocViaMailFilterSezioneCache lInvioDocViaMailFilterSezioneCache = new InvioDocViaMailFilterSezioneCache();
		String lStrXml = lXmlUtilitySerializer.bindXml(lInvioDocViaMailFilterSezioneCache);
		lDmpkIntMgoEmailTrovarichinvioemailBean.setFiltriio(lStrXml);

		// Inizializzo l'INPUT
		DmpkIntMgoEmailTrovarichinvioemail lDmpkIntMgoEmailTrovarichinvioemail = new DmpkIntMgoEmailTrovarichinvioemail();
		StoreResultBean<DmpkIntMgoEmailTrovarichinvioemailBean> lStoreResultBean = lDmpkIntMgoEmailTrovarichinvioemail.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkIntMgoEmailTrovarichinvioemailBean);

		if (lStoreResultBean.getResultBean().getResultout() != null) {
			StringReader sr = new StringReader(lStoreResultBean.getResultBean().getResultout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();

					lSimpleKeyValueBean.setKey(v.get(2));
					lSimpleKeyValueBean.setValue(v.get(2));

					if (!isPresente(lSimpleKeyValueBean)) {
						lListResult.add(lSimpleKeyValueBean);
					}
				}
			}
		}

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

	private boolean isPresente(SimpleKeyValueBean lSimpleKeyValueBean) {
		boolean verify = false;
		if (lListResult.size() > 0) {
			for (int i = 0; i < lListResult.size(); i++) {
				if (lListResult.get(i).getKey().equalsIgnoreCase(lSimpleKeyValueBean.getKey())) {
					verify = true;
					break;
				}
			}
		}
		return verify;
	}
}
