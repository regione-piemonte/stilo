/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperazioneMassivaArchivioBean;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.DmpkCoreUpdfolder;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FlagArchivio;
import it.eng.document.function.bean.XmlFascicoloIn;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;


@Datasource(id = "OperazioneMassivaArchivioDataSource")
public class OperazioneMassivaArchivioDataSource extends AbstractDataSource<OperazioneMassivaArchivioBean, OperazioneMassivaArchivioBean>{
	
	@Override
	public OperazioneMassivaArchivioBean add(OperazioneMassivaArchivioBean bean) throws Exception {	
	
		return null;
	}
	
	@Override
	public OperazioneMassivaArchivioBean get(OperazioneMassivaArchivioBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public OperazioneMassivaArchivioBean remove(OperazioneMassivaArchivioBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public OperazioneMassivaArchivioBean update(OperazioneMassivaArchivioBean bean,
			OperazioneMassivaArchivioBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<OperazioneMassivaArchivioBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(OperazioneMassivaArchivioBean bean)
	throws Exception {
		
		return null;
	}
	
	public OperazioneMassivaArchivioBean versaInArchivioStoricoFascicolo(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		String token = loginBean.getToken();
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			
			// creo l'input			
			DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
			
			// ID FOLDER
			input.setIdfolderin(StringUtils.isNotEmpty(udFolder.getIdUdFolder()) ? new BigDecimal(udFolder.getIdUdFolder()) : null);
					
			// creo l'XML 
			XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
			xmlFascicoloIn.setFlgSottoFascInserto(udFolder.getFlgSottoFascInserto());
			xmlFascicoloIn.setIdFolderType(StringUtils.isNotEmpty(udFolder.getFolderType()) ? new BigDecimal(udFolder.getFolderType()) : null);
			xmlFascicoloIn.setNomeFolder(udFolder.getNomeFascicolo());
			
			if(udFolder.getFlgFascTitolario() != null && !udFolder.getFlgFascTitolario()) {
				xmlFascicoloIn.setFlgFascTitolario(Flag.NOT_SETTED);
			}
			
			// #FlgArchivio = S
		    xmlFascicoloIn.setFlgArchivio(FlagArchivio.ARCHIVIO_STORICO);
		   
		    XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributixmlin(lXmlUtilitySerializer.bindXml(xmlFascicoloIn));
			
			DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
			StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(getLocale(),loginBean, input);
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();				
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		bean.setErrorMessages(errorMessages);
		
		return bean;		
	}
	
	public OperazioneMassivaArchivioBean chiudiFascicolo(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		String token = loginBean.getToken();
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
	
			// creo l'input			
			DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
			
			// ID FOLDER
			input.setIdfolderin(StringUtils.isNotEmpty(udFolder.getIdUdFolder()) ? new BigDecimal(udFolder.getIdUdFolder()) : null);
					
			// creo l'XML 
			XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
			xmlFascicoloIn.setFlgSottoFascInserto(udFolder.getFlgSottoFascInserto());
			xmlFascicoloIn.setIdFolderType(StringUtils.isNotEmpty(udFolder.getFolderType()) ? new BigDecimal(udFolder.getFolderType()) : null);
			xmlFascicoloIn.setNomeFolder(udFolder.getNomeFascicolo());
			
			if(udFolder.getFlgFascTitolario() != null && !udFolder.getFlgFascTitolario()) {
				xmlFascicoloIn.setFlgFascTitolario(Flag.NOT_SETTED);
			}
			
		    // #FlgArchivio = D
		    xmlFascicoloIn.setFlgArchivio(FlagArchivio.ARCHIVIO_DEPOSITO);
		    
		    // id. utente loggato o delegante se si sta lavorando in delega 
		    xmlFascicoloIn.setIdUserChiusura(idUserLavoro);
		    
		     //data e ora correnti nel formato standard
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
			String oggi = sdf.format(new Date());
			Date dataOggi = sdf.parse(oggi);
			xmlFascicoloIn.setTsChiusura(dataOggi);
			
			
		    XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributixmlin(lXmlUtilitySerializer.bindXml(xmlFascicoloIn));
			
			DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
			StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(getLocale(),loginBean, input);
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();				
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		bean.setErrorMessages(errorMessages);
		
		return bean;		
	}
	
	public OperazioneMassivaArchivioBean riapriFascicolo(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		String token = loginBean.getToken();
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
	
			// creo l'input			
			DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
			
			// ID FOLDER
			input.setIdfolderin(StringUtils.isNotEmpty(udFolder.getIdUdFolder()) ? new BigDecimal(udFolder.getIdUdFolder()) : null);
					
			// creo l'XML 
			XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
			
			Date dataChiusura = null;
			xmlFascicoloIn.setTsChiusura(dataChiusura); 
			
			List<String> lListNomiVariabiliToSet = new ArrayList<String>();
			lListNomiVariabiliToSet.add("#TsChiusura");
		    XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributixmlin(lXmlUtilitySerializer.bindXmlParziale(xmlFascicoloIn, lListNomiVariabiliToSet));
			
			DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
			StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(getLocale(),loginBean, input);
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();				
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		bean.setErrorMessages(errorMessages);
		
		return bean;		
	}
	
	
	public OperazioneMassivaArchivioBean cancellaScansioni(OperazioneMassivaArchivioBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		String token = loginBean.getToken();
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
	
			// creo l'input			
			DmpkCoreDel_ud_doc_verBean input = new DmpkCoreDel_ud_doc_verBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
			
			input.setIduddocin(StringUtils.isNotEmpty(udFolder.getIdUdFolder()) ? new BigDecimal(udFolder.getIdUdFolder()) : null);
			input.setFlgtipotargetin("U");
			input.setFlgcancfisicain(null);
			
			DmpkCoreDel_ud_doc_ver lstore = new DmpkCoreDel_ud_doc_ver();
			StoreResultBean<DmpkCoreDel_ud_doc_verBean> output = lstore.execute(getLocale(),loginBean, input);
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();				
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		bean.setErrorMessages(errorMessages);
		
		return bean;		
	}	
}
