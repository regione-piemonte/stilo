/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetidutentemgoemailBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginGetuserprivsBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginStartsessioneindelegaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.ui.module.layout.server.common.PrivilegioBean;
import it.eng.auriga.ui.module.layout.server.deleghe.datasource.bean.AttivaDelegaBean;
import it.eng.client.DmpkIntMgoEmailGetidutentemgoemail;
import it.eng.client.DmpkLoginGetuserprivs;
import it.eng.client.DmpkLoginStartsessioneindelega;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AurigaAttivaDelegaDataSource")
public class AurigaAttivaDelegaDataSource extends AbstractServiceDataSource<AttivaDelegaBean, AttivaDelegaBean> {

	@Override
	public AttivaDelegaBean call(AttivaDelegaBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
		DmpkLoginStartsessioneindelegaBean lDmpkLoginStartsessioneindelegaInput = new DmpkLoginStartsessioneindelegaBean();
		lDmpkLoginStartsessioneindelegaInput.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		lDmpkLoginStartsessioneindelegaInput.setIduserdelegantein(new BigDecimal(bean.getIdUser()));
		// Ignoro gli avvertimenti
		if (bean.isIgnora()) {
			lDmpkLoginStartsessioneindelegaInput.setFlgignorewarningin(1);
		} else {
			lDmpkLoginStartsessioneindelegaInput.setFlgignorewarningin(0);
		}
		// Faccio il cambio
		DmpkLoginStartsessioneindelega lDmpkLoginStartsessioneindelega = new DmpkLoginStartsessioneindelega();
		StoreResultBean<DmpkLoginStartsessioneindelegaBean> lDmpkLoginStartsessioneindelegaOutput = lDmpkLoginStartsessioneindelega.execute(getLocale(),
				lAurigaLoginBean, lDmpkLoginStartsessioneindelegaInput);
		if (lDmpkLoginStartsessioneindelegaOutput.isInError()) {
			if (StringUtils.isNotBlank(lDmpkLoginStartsessioneindelegaOutput.getResultBean().getWarningmsgout())) {
				bean.setAvvertimenti(lDmpkLoginStartsessioneindelegaOutput.getResultBean().getWarningmsgout());
				bean.setError(true);
				return bean;
			} else {
				throw new StoreException(lDmpkLoginStartsessioneindelegaOutput);
			}
		}
		String desUserDelegante = lDmpkLoginStartsessioneindelegaOutput.getResultBean().getDesuserdeleganteout();
		String codFisUserDelegante = null;
		if(StringUtils.isNotBlank(desUserDelegante) && desUserDelegante.contains(";CF:")) {
			int pos = desUserDelegante.indexOf(";CF:");					
			codFisUserDelegante = desUserDelegante.substring(pos + 4);		
			desUserDelegante = desUserDelegante.substring(0, pos);
		}
		lAurigaLoginBean.setDelegaDenominazione(desUserDelegante);
		lAurigaLoginBean.setDelegaCodFiscale(codFisUserDelegante);
		// if (!bean.isMantieniPreference()) {
		lAurigaLoginBean.setUseridForPrefs(lDmpkLoginStartsessioneindelegaOutput.getResultBean().getUsernamedeleganteout());
		// }
		lAurigaLoginBean.setIdUserLavoro(bean.getIdUser());
		SpecializzazioneBean lSpecializzazioneBean = lAurigaLoginBean.getSpecializzazioneBean();
		DmpkLoginGetuserprivsBean lDmpkLoginGetuserprivsInput = new DmpkLoginGetuserprivsBean();
		lDmpkLoginGetuserprivsInput.setIduserin(new BigDecimal(bean.getIdUser()));
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
		lDmpkIntMgoEmailGetidutentemgoemailInput.setIduserin(new BigDecimal(bean.getIdUser()));
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
