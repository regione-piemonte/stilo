/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AssegnazioneSmistamentoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ModificaPreassegnazioneDataSource")
public class ModificaPreassegnazioneDataSource extends AbstractDataSource<AssegnazioneSmistamentoBean, AssegnazioneSmistamentoBean>{	

	@Override
	public AssegnazioneSmistamentoBean add(AssegnazioneSmistamentoBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean ud : bean.getListaRecord()) {				
			
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
			input.setFlgtipotargetin("U");
			input.setIduddocin(new BigDecimal(ud.getIdUdFolder()));
			
			CreaModDocumentoInBean modDocumentoInBean = new CreaModDocumentoInBean();			
			modDocumentoInBean.setIdUserConfermaAssegnazione("");
			modDocumentoInBean.setAssegnatari(getListaAssegnatari(bean));
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(modDocumentoInBean));	
				
			DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(getLocale(),loginBean, input);
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(ud.getIdUdFolder(), output.getDefaultMessage());
			}																		
			
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}
		
	private List<AssegnatariBean> getListaAssegnatari(AssegnazioneSmistamentoBean bean) throws Exception {
		List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
		if(bean.getListaAssegnazioni()!=null){
			for(AssegnazioneBean assegnazioneBean : bean.getListaAssegnazioni()){
				AssegnatariBean lAssegnatariBean = new AssegnatariBean();
				lAssegnatariBean.setTipo(getTipoAssegnatario(assegnazioneBean));		
				if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
					lAssegnatariBean.setIdSettato(assegnazioneBean.getGruppo());
				} else {
					lAssegnatariBean.setIdSettato(assegnazioneBean.getIdUo());
					if(assegnazioneBean.getCodRapidoChanged() != null && assegnazioneBean.getCodRapidoChanged()){
						lAssegnatariBean.setCodRapido(assegnazioneBean.getCodRapido());
					}
				}				
				if(bean.getMotivoInvio() != null && "PAF".equals(bean.getMotivoInvio())) {
					if("PAF".equals(bean.getMotivoInvio())) {
						if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
							throw new StoreException("L'assegnazione per firma/approvazione deve essere fatta a persona/e e non ad una U.O.");
						}	
					}
					if ("PAV".equals(bean.getMotivoInvio())) {
						if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
							throw new StoreException("L'assegnazione per apposizione visto elettronico deve essere fatta a persona/e e non ad una U.O.");
						}	
					}
				}
				lAssegnatariBean.setPermessiAccesso("FC");
				//TODO ASSEGNAZIONE SAVE OK
				if (bean.getFlgPresaInCarico()!=null && bean.getFlgPresaInCarico()){
					lAssegnatariBean.setFeedback(Flag.SETTED);
				} 
				if (bean.getFlgMancataPresaInCarico()!=null && bean.getFlgMancataPresaInCarico()){
					lAssegnatariBean.setNumeroGiorniFeedback(bean.getGiorniTrascorsi());
				}
				if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato()) ||
						StringUtils.isNotBlank(lAssegnatariBean.getCodRapido())) {
					listaAssegnatari.add(lAssegnatariBean);
				}
			}
		}
		return listaAssegnatari;
	}

	private TipoAssegnatario getTipoAssegnatario(DestInvioBean lDestInvioBean) {
		if(StringUtils.isNotBlank(lDestInvioBean.getTypeNodo())) {
			TipoAssegnatario[] tipiAssegnatari = TipoAssegnatario.values();
			for (TipoAssegnatario tipoAssegnatario : tipiAssegnatari) {
				if (tipoAssegnatario.toString().equals(lDestInvioBean.getTypeNodo())) {
					return tipoAssegnatario;
				}
			}
		} else if(lDestInvioBean.getCodRapidoChanged() != null && lDestInvioBean.getCodRapidoChanged() && StringUtils.isNotBlank(lDestInvioBean.getCodRapido())) {
			return TipoAssegnatario.UNITA_ORGANIZZATIVA;			
		}
		return null;
	}

	@Override
	public AssegnazioneSmistamentoBean get(AssegnazioneSmistamentoBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public AssegnazioneSmistamentoBean remove(AssegnazioneSmistamentoBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public AssegnazioneSmistamentoBean update(AssegnazioneSmistamentoBean bean,
			AssegnazioneSmistamentoBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<AssegnazioneSmistamentoBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AssegnazioneSmistamentoBean bean)
	throws Exception {
		
		return null;
	}

}
