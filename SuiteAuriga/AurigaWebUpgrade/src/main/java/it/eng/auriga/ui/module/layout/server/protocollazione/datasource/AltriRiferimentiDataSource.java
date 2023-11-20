/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltriRiferimentiUdBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltroRifBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.RecuperoDocumenti;
import it.eng.document.function.bean.AltriRiferimentiBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AltriRiferimentiDataSource")
public class AltriRiferimentiDataSource extends AbstractFetchDataSource<AltriRiferimentiUdBean> {

	@Override
	public AltriRiferimentiUdBean get(AltriRiferimentiUdBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		if (StringUtils.isNotBlank(bean.getIdUd())) {

			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(bean.getIdUd()));
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
			if(lRecuperaDocumentoOutBean.isInError()) {
				throw new StoreException(lRecuperaDocumentoOutBean);
			}
			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
			bean.setListaAltriRiferimenti(lProtocollazioneBean.getListaAltriRiferimenti());
		}

		return bean;
	}

	@Override
	public AltriRiferimentiUdBean add(AltriRiferimentiUdBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);

		List<AltriRiferimentiBean> altriRiferimenti = new ArrayList<AltriRiferimentiBean>();
		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		if (bean.getListaAltriRiferimenti() != null) {
			for (AltroRifBean altroRif : bean.getListaAltriRiferimenti()) {
				AltriRiferimentiBean altriRifBean = new AltriRiferimentiBean();
				altriRifBean.setRegistroTipoRif(altroRif.getRegistroTipoRif());
				altriRifBean.setNumero(altroRif.getNumero());
				altriRifBean.setAnno(altroRif.getAnno());
				altriRifBean.setData(altroRif.getData());
				altriRifBean.setNote(altroRif.getNote());
				altriRiferimenti.add(altriRifBean);
			}
		}
		lModificaDocumentoInBean.setAltriRiferimenti(altriRiferimenti);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModificaDocumentoInBean));

		input.setFlgtipotargetin("U");
		input.setIduddocin(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);

		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

	@Override
	public AltriRiferimentiUdBean update(AltriRiferimentiUdBean bean, AltriRiferimentiUdBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public AltriRiferimentiUdBean remove(AltriRiferimentiUdBean bean) throws Exception {
		
		return null;
	}

	@Override
	public PaginatorBean<AltriRiferimentiUdBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AltriRiferimentiUdBean bean) throws Exception {
		
		return null;
	}

}
