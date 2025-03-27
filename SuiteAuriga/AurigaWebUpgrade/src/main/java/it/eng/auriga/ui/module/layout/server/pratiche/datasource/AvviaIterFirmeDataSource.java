/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.datasource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationInvioBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AssegnazioneSmistamentoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FirmatariIterFirmaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkCollaborationInvio;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;


@Datasource(id = "AvviaIterFirmeDataSource")
public class AvviaIterFirmeDataSource extends AbstractServiceDataSource<ProtocollazioneBean, ProtocollazioneBean>{
	
	private static Logger logger = Logger.getLogger(AvviaIterFirmeDataSource.class);
	
	@Override
	public ProtocollazioneBean call(ProtocollazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCollaborationInvioBean input = new DmpkCollaborationInvioBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);				
		input.setFlgtypeobjtosendin("U");
		input.setIdobjtosendin(bean.getIdUd());
		input.setFlgcallbyguiin(new Integer(0));
		input.setRecipientsxmlin(getXmlFirmatari(bean));
//		input.setCodmotivoinvioin(null);
//		input.setMessaggioinvioin(null);
//		input.setLivelloprioritain(null);

		DmpkCollaborationInvio dmpkCollaborationInvio = new DmpkCollaborationInvio();
		StoreResultBean<DmpkCollaborationInvioBean> output = dmpkCollaborationInvio.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	public String getXmlFirmatari(ProtocollazioneBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaFirmatari(bean));
	}

	private List<AssegnatariBean> getListaFirmatari(ProtocollazioneBean bean) throws Exception {
		List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
		if (bean.getListaFirmatariIterFirma() != null) {
			List<FirmatariIterFirmaBean> listaFirmatariIterFirma = new ArrayList<FirmatariIterFirmaBean>();
			listaFirmatariIterFirma.addAll(bean.getListaFirmatariIterFirma());
			Collections.sort(listaFirmatariIterFirma, new Comparator<FirmatariIterFirmaBean>() {

				@Override
				public int compare(FirmatariIterFirmaBean bean1, FirmatariIterFirmaBean bean2) {
					Integer nroOrdine1 = StringUtils.isNotBlank(bean1.getNroOrdine()) ? Integer.parseInt(bean1.getNroOrdine()) : 0; 
					Integer nroOrdine2 = StringUtils.isNotBlank(bean2.getNroOrdine()) ? Integer.parseInt(bean2.getNroOrdine()) : 0; 
					return nroOrdine1.compareTo(nroOrdine2);
				}
			});
			Integer nroOrdinePiuBasso = null;
			for (FirmatariIterFirmaBean firmatario : listaFirmatariIterFirma) {				
				if(StringUtils.isNotBlank(firmatario.getIdUtente())) {
					Integer nroOrdineCorrente = StringUtils.isNotBlank(firmatario.getNroOrdine()) ? Integer.parseInt(firmatario.getNroOrdine()) : 0;
					if(nroOrdinePiuBasso == null || nroOrdineCorrente.compareTo(nroOrdinePiuBasso) == 0) {
						AssegnatariBean lAssegnatariBean = new AssegnatariBean();
						lAssegnatariBean.setTipo(TipoAssegnatario.UTENTE);				
						lAssegnatariBean.setIdSettato(firmatario.getIdUtente());		
						if (firmatario.getTipoFirma() != null) {
							if("D".equals(firmatario.getTipoFirma())) {
								lAssegnatariBean.setMotivoInvio("PAF");
							} else if("E".equals(firmatario.getTipoFirma())) {						
								lAssegnatariBean.setMotivoInvio("PAV");										
							}
						}
						listaAssegnatari.add(lAssegnatariBean);						
						nroOrdinePiuBasso = nroOrdineCorrente;
					} else {
						break;
					}
				}				
			}
		}
		return listaAssegnatari;
	}

}
