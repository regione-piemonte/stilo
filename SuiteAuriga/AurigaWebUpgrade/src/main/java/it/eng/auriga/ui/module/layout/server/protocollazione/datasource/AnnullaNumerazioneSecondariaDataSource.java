/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AnnullaNumerazioneSecondariaDataSource")
public class AnnullaNumerazioneSecondariaDataSource extends AbstractServiceDataSource<ProtocollazioneBean, ProtocollazioneBean> {

	@Override
	public ProtocollazioneBean call(ProtocollazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if (bean.getTipoProtocollo() != null && bean.getTipoProtocollo().equalsIgnoreCase("PG") && 
				bean.getTipoNumerazioneSecondaria() != null && bean.getTipoNumerazioneSecondaria().equalsIgnoreCase("R") && 
				StringUtils.isNotBlank(bean.getMotiviRichAnnullamento())) {
				
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);

			input.setIduddocin(bean.getIdUd());
			input.setFlgtipotargetin("U");
			
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
				
			List<RegistroEmergenza> lListRegistrazioniDaAnnullare = new ArrayList<RegistroEmergenza>();
			RegistroEmergenza lRegistrazioneSecondaria = new RegistroEmergenza();
			lRegistrazioneSecondaria.setFisso(bean.getTipoNumerazioneSecondaria());
			lRegistrazioneSecondaria.setRegistro(bean.getSiglaNumerazioneSecondaria());
			lRegistrazioneSecondaria.setAnno(bean.getAnnoNumerazioneSecondaria());
			lRegistrazioneSecondaria.setNro(bean.getNumeroNumerazioneSecondaria() != null ? String.valueOf(bean.getNumeroNumerazioneSecondaria().longValue()) : null);
			lRegistrazioneSecondaria.setFlgAnnullamento("1");
			lRegistrazioneSecondaria.setMotiviAnnullamento(bean.getMotiviRichAnnullamento());
			lListRegistrazioniDaAnnullare.add(lRegistrazioneSecondaria);
			lCreaModDocumentoInBean.setRegistroEmergenza(lListRegistrazioniDaAnnullare);						
			
			// devo mettere l'append a 1 
			lCreaModDocumentoInBean.setAppendRegistroEmergenza("1");
				
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
				
			DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean, input);
			
			if(lUpddocudOutput.isInError()) {
				throw new StoreException(lUpddocudOutput);
			}	
		}
		
		return bean;
	}
}
