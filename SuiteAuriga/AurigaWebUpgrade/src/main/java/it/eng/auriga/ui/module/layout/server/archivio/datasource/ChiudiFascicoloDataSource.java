/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperazioneMassivaArchivioBean;
import it.eng.client.GestioneFascicoli;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FlagArchivio;
import it.eng.document.function.bean.FolderAppartenenzaBean;
import it.eng.document.function.bean.ModificaFascicoloIn;
import it.eng.document.function.bean.ModificaFascicoloOut;
import it.eng.document.function.bean.XmlFascicoloIn;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "ChiudiFascicoloDataSource")
public class ChiudiFascicoloDataSource extends AbstractDataSource<OperazioneMassivaArchivioBean, OperazioneMassivaArchivioBean>{
	
	
	@Override
	public OperazioneMassivaArchivioBean add(OperazioneMassivaArchivioBean bean) throws Exception {		
	
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String idUser =  (loginBean.getIdUser() != null ? loginBean.getIdUser().toString() : null);
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			
			// creo l'input
			ModificaFascicoloIn  input = new ModificaFascicoloIn();
			
			// ID FOLDER
			input.setIdFolderIn(StringUtils.isNotEmpty(udFolder.getIdUdFolder()) ? new BigDecimal(udFolder.getIdUdFolder()) : null);
					
			// creo l'XML 
			XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
			xmlFascicoloIn.setFlgSottoFascInserto(udFolder.getFlgSottoFascInserto());
			xmlFascicoloIn.setIdFolderType(StringUtils.isNotEmpty(udFolder.getFolderType()) ? new BigDecimal(udFolder.getFolderType()) : null);
			xmlFascicoloIn.setNomeFolder(udFolder.getNomeFascicolo());									
			xmlFascicoloIn.setIdClassificazione(null);		
			
			if(udFolder.getFlgFascTitolario() != null && !udFolder.getFlgFascTitolario()) {
				xmlFascicoloIn.setFlgFascTitolario(Flag.NOT_SETTED);
			}
			
			// FOLDER APPARTENENZA
			if(StringUtils.isNotBlank(udFolder.getIdFolderApp())) {
				xmlFascicoloIn.setFolderAppartenenza(new ArrayList<FolderAppartenenzaBean>());
			} else {			
				List<FolderAppartenenzaBean> folderAppartenenza = new ArrayList<FolderAppartenenzaBean>();
				FolderAppartenenzaBean lFolderAppartenenzaBean = new FolderAppartenenzaBean();
				lFolderAppartenenzaBean.setIdFolder(new BigDecimal(udFolder.getIdFolderApp()));
				folderAppartenenza.add(lFolderAppartenenzaBean);
				xmlFascicoloIn.setFolderAppartenenza(folderAppartenenza);	
			}
			
			// #FlgArchivio = D
		    xmlFascicoloIn.setFlgArchivio(FlagArchivio.ARCHIVIO_DEPOSITO);
		    
		    // id. utente loggato o delegante se si sta lavorando in delega 
		    xmlFascicoloIn.setIdUserChiusura(idUser);
		    
		     //data e ora correnti nel formato standard
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
			String oggi = sdf.format(new Date());
			Date dataOggi = sdf.parse(oggi);
			xmlFascicoloIn.setTsChiusura(dataOggi);
			input.setModificaFascicolo(xmlFascicoloIn);
			
			GestioneFascicoli lGestioneFascicoli = new GestioneFascicoli();		
			ModificaFascicoloOut output = new ModificaFascicoloOut();
			
			try {
				output = lGestioneFascicoli.modificafascicolo(getLocale(), loginBean, input);								
			} catch (Exception e) {
				throw new StoreException(" Errore : " + e.getMessage());
			}
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();				
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		bean.setErrorMessages(errorMessages);
		return bean;
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
}
