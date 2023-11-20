/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetidutentemgoemailBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginEndsessioneindelegaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginGetuserprivsBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.ui.module.layout.server.common.PrivilegioBean;
import it.eng.auriga.ui.module.layout.server.deleghe.datasource.bean.TerminaDelegaBean;
import it.eng.client.DmpkIntMgoEmailGetidutentemgoemail;
import it.eng.client.DmpkLoginEndsessioneindelega;
import it.eng.client.DmpkLoginGetuserprivs;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.ArrayList;
import java.util.List;

@Datasource(id = "AurigaTerminaDelegaDataSource")
public class AurigaTerminaDelegaDataSource extends AbstractServiceDataSource<TerminaDelegaBean, TerminaDelegaBean> {

	@Override
	public TerminaDelegaBean call(TerminaDelegaBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
		DmpkLoginEndsessioneindelegaBean lDmpkLoginEndsessioneindelegaInput = new DmpkLoginEndsessioneindelegaBean();
		lDmpkLoginEndsessioneindelegaInput.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		DmpkLoginEndsessioneindelega lDmpkLoginEndsessioneindelega = new DmpkLoginEndsessioneindelega();
		StoreResultBean<DmpkLoginEndsessioneindelegaBean> lDmpkLoginEndsessioneindelegaOutput = lDmpkLoginEndsessioneindelega.execute(getLocale(),
				lAurigaLoginBean, lDmpkLoginEndsessioneindelegaInput);
		if (lDmpkLoginEndsessioneindelegaOutput.isInError()) {
			throw new StoreException(lDmpkLoginEndsessioneindelegaOutput);
		}
		lAurigaLoginBean.setDelegaDenominazione(null);
		lAurigaLoginBean.setUseridForPrefs(lAurigaLoginBean.getUserid());
		lAurigaLoginBean.setIdUserLavoro(null);
		SpecializzazioneBean lSpecializzazioneBean = lAurigaLoginBean.getSpecializzazioneBean();
		DmpkLoginGetuserprivsBean lDmpkLoginGetuserprivsInput = new DmpkLoginGetuserprivsBean();
		lDmpkLoginGetuserprivsInput.setIduserin(lAurigaLoginBean.getIdUser());
		lDmpkLoginGetuserprivsInput.setIddominioautin(lSpecializzazioneBean.getIdDominio());
		lDmpkLoginGetuserprivsInput.setFlgtpdominioautin(lSpecializzazioneBean.getTipoDominio());
		DmpkLoginGetuserprivs lDmpkLoginGetuserprivs = new DmpkLoginGetuserprivs();
		StoreResultBean<DmpkLoginGetuserprivsBean> lDmpkLoginGetuserprivsOutput = lDmpkLoginGetuserprivs.execute(getLocale(), lSchemaBean,
				lDmpkLoginGetuserprivsInput);
		if (lDmpkLoginGetuserprivsOutput.isInError()) {
			throw new StoreException(lDmpkLoginGetuserprivsOutput);
		}
		List<PrivilegioBean> lPrivilegiList = XmlListaUtility.recuperaLista(lDmpkLoginGetuserprivsOutput.getResultBean().getPrivslistout(),
				PrivilegioBean.class);
		List<String> privilegi = new ArrayList<String>(lPrivilegiList.size());
		for (PrivilegioBean lPrivilegioBean : lPrivilegiList) {
			privilegi.add(lPrivilegioBean.getPrivilegio());
		}
		lSpecializzazioneBean.setPrivilegi(privilegi);
		DmpkIntMgoEmailGetidutentemgoemailBean lDmpkIntMgoEmailGetidutentemgoemailInput = new DmpkIntMgoEmailGetidutentemgoemailBean();
		lDmpkIntMgoEmailGetidutentemgoemailInput.setIduserin(lAurigaLoginBean.getIdUser());
		DmpkIntMgoEmailGetidutentemgoemail lDmpkIntMgoEmailGetidutentemgoemail = new DmpkIntMgoEmailGetidutentemgoemail();
		StoreResultBean<DmpkIntMgoEmailGetidutentemgoemailBean> lDmpkIntMgoEmailGetidutentemgoemailOutput = lDmpkIntMgoEmailGetidutentemgoemail.execute(
				getLocale(), lSchemaBean, lDmpkIntMgoEmailGetidutentemgoemailInput);
		if (lDmpkIntMgoEmailGetidutentemgoemailOutput.isInError()) {
			throw new StoreException(lDmpkIntMgoEmailGetidutentemgoemailOutput);
		}
		lSpecializzazioneBean.setIdUtenteModPec(lDmpkIntMgoEmailGetidutentemgoemailOutput.getResultBean().getIdutentepecout());
		lAurigaLoginBean.setSpecializzazioneBean(lSpecializzazioneBean);
		AurigaUserUtil.setLoginInfo(getSession(), lAurigaLoginBean);
		return bean;
	}

}
