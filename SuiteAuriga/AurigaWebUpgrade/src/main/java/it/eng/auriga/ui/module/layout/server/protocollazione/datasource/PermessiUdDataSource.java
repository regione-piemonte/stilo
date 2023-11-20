/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreLoadacludBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreSetacludBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ACLBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ACLUdBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PermessiUdBean;
import it.eng.client.DmpkCoreLoadaclud;
import it.eng.client.DmpkCoreSetaclud;
import it.eng.document.function.bean.Flag;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "PermessiUdDataSource")
public class PermessiUdDataSource extends AbstractFetchDataSource<PermessiUdBean>{	

	
	
	@Override
	public PermessiUdBean get(PermessiUdBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		if(StringUtils.isNotBlank(bean.getIdUd())) {
				        
	        DmpkCoreLoadacludBean input = new DmpkCoreLoadacludBean();
	        input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdudin(new BigDecimal(bean.getIdUd()));
			
			DmpkCoreLoadaclud dmpkCoreLoadaclud = new DmpkCoreLoadaclud();
			StoreResultBean<DmpkCoreLoadacludBean> output = dmpkCoreLoadaclud.execute(getLocale(),loginBean, input);
			
			if(output.getResultBean().getAclxmlout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getAclxmlout());
				// Lista con le registrazioni che sono possibili duplicati della registrazione che si sta per effettuare (XML conforme a schema LISTA_STD.xsd)
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				if(lista != null) {
					List<ACLBean> listaACL = new ArrayList<ACLBean>();					
					for (int i = 0; i < lista.getRiga().size(); i++) 
					{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																						
						//	1: (obblig.) Indicatore del tipo di destinatario del permesso
						//		Valori possibili:
						//			SP	= Soggetto produttore
						// 			AOO	= AOO
						// 			UT 	= Utente
						//			SV	= Scrivania virtuale
						//			UO	= Unità organizzativa (vale a dire tutte le scrivanie virtuali ad essa afferenti)
						//			G	= Gruppo
						//			R	= Ruolo amministrativo contestualizzato ovvero i soggetti che hanno un certo ruolo amministrativo (eventualmente rispetto ad una certa UO o un dato livello della struttura organizzativa/tipo di UO o entrambi)
						//	2: Identificativo del destinatario del permesso
						//		è un ID_SP_AOO di DMT_SOGGETTI_PROD_AOO se colonna 1 è SP o AOO
						//		è un ID_UO di DMT_STRUTTURA_ORG se colonna 1=UO,
						//		è un ID_GRUPPO di DMT_GRUPPI se colonna 1 =G
						//		è un ID_USER di DMT_USERS se colonna 1 =UT
						//		è un ID_SCRIVANIA di DMT_SCRIVANIE_VIRTUALI se colonna 1 =SV
						//		è un ID_RUOLO_AMM di DMT_RUOLI_AMM se colonna 1 =R
						//	5: Nri livello della UO destinataria del permesso o a cui appartiene la scrivania destinataria (tutti o alcuni; però se si specifica un livello è obbligatorio indicare tutti i livelli superiori; vanno specificati come appaiono nella GUI, ovvero romani se previsto, 0 o -- se nulli; sono separati dal separatore dei livelli valido per il soggetto produttore/AOO di appartenenza della UO)
						//	17:(valori 1/0/NULL) Indica se la visualizzazione dei metadati è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	18:(valori 1/0/NULL) Indica se la visualizzazione dei file è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	19:(valori 1/0/NULL) Indica se la modifica dei metadati è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	20:(valori 1/0/NULL) Indica se la modifica (intendendo anche il versioning) dei file è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	21:(valori 1/0/NULL) Indica se la modifica dell'ACL è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	22:(valori 1/0/NULL) Indica se la copia dell'unità doc. è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	23:(valori 1/0/NULL) Indica se l'eliminazione dell'unità doc. è assentita (1) o negata (0) o se nulla è specificato in merito(NULL)
						//	24:(valori 1/0/NULL) Indica se il ripristino dell'unità doc. eliminata è assentito (1) o negato (0) o se nulla è specificato in merito(NULL)
						//  30:(valori 1/0) Flag di ACL ereditata
						ACLBean acl = new ACLBean();
			       		String tipo = v.get(0); //colonna 1 dell'xml
			       		if("AOO".equals(tipo)) {
			       			acl.setTipoDestinatario("AOO");
			       		} else if("UO".equals(tipo) || "SV".equals(tipo)) {
			       			acl.setTipoDestinatario("UO+SV");
			       			String idOrganigramma = v.get(1); //colonna 2 dell'xml
			       			acl.setOrganigramma(tipo + idOrganigramma); //colonna 2 dell'xml
			       			acl.setTipoOrganigramma(tipo);
			       			acl.setIdOrganigramma(idOrganigramma); //colonna 2 dell'xml
			       			acl.setCodiceUo(v.get(4)); // colonna 5 dell'xml
			       		} else if("UT".equals(tipo)) {
			       			acl.setTipoDestinatario("UT");
			       			acl.setIdUtente(v.get(1)); //colonna 2 dell'xml
			       			acl.setCodiceRapido(v.get(4)); // colonna 5 dell'xml			       			
			       		} else if("G".equals(tipo)) {
			       			acl.setTipoDestinatario("G");
			       			acl.setIdGruppo(v.get(1)); //colonna 2 dell'xml
			       			acl.setIdGruppoInterno(v.get(1)); //colonna 2 dell'xml
			       			acl.setCodiceRapido(v.get(4)); // colonna 5 dell'xml
			       		} 			       		
			       		if((v.get(19) != null && "1".equals(v.get(19))) ||
			       		   (v.get(20) != null && "1".equals(v.get(20))) ||
			       		   (v.get(22) != null && "1".equals(v.get(22))) ||
			       		   (v.get(23) != null && "1".equals(v.get(23))) ||
			       		   (v.get(24) != null && "1".equals(v.get(24)))) {
			       			acl.setOpzioniAccesso("C");
			       		} else {
			       			acl.setOpzioniAccesso("V");
			       		}				       		
			       		acl.setFlgEreditata(v.get(29) != null && "1".equals(v.get(29)));			       		
			       		acl.setDenominazione(v.get(2)); //colonna 3 dell'xml						       		
			       		acl.setDenominazioneEstesa(v.get(30)); //colonna 31 dell'xml
			       		acl.setFlgAssegnatario(v.get(31) != null && "1".equals(v.get(31))); //colonna 32 dell'xml
			       		acl.setFlgInvioPerConoscenza(v.get(32) != null && "1".equals(v.get(32))); //colonna 33 dell'xml
			       		listaACL.add(acl);
			    	}
					bean.setListaACL(listaACL);
				}					
			}
		}
				
		return bean;
	}	
	
	@Override
	public PermessiUdBean add(PermessiUdBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCoreSetacludBean input = new DmpkCoreSetacludBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		input.setAclxmlin(getListaACLUd(bean));
		input.setFlgmodaclin("C");
		input.setFlgcallbyguiin(new Integer(1));
		
		DmpkCoreSetaclud dmpkCoreSetaclud = new DmpkCoreSetaclud();
		StoreResultBean<DmpkCoreSetacludBean> output = dmpkCoreSetaclud.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;		
	}
	
	protected String getListaACLUd(PermessiUdBean bean)
			throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		String xmlACL;
		List<ACLUdBean> lListACLUd = new ArrayList<ACLUdBean>();
		for (ACLBean lACLBean : bean.getListaACL()){			
			ACLUdBean lACLUdBean = new ACLUdBean();			
			String tipoDestinatario = lACLBean.getTipoDestinatario();			
			 if("AOO".equals(tipoDestinatario)) {
	       		lACLUdBean.setTipoDestinatario("AOO");
	       	 } else if("UO+SV".equals(tipoDestinatario)) {
       			lACLUdBean.setTipoDestinatario(lACLBean.getTipoOrganigramma());
       			lACLUdBean.setIdDestinatario(lACLBean.getIdOrganigramma());   
       			lACLUdBean.setCodiceRapido(lACLBean.getCodiceUo());
       		} else if("UT".equals(tipoDestinatario)) {       			
       			lACLUdBean.setTipoDestinatario("UT");
       			lACLUdBean.setIdDestinatario(lACLBean.getIdUtente());   
       			lACLUdBean.setCodiceRapido(lACLBean.getCodiceRapido());
       		} else if("G".equals(tipoDestinatario)) {       			
       			lACLUdBean.setTipoDestinatario("G");
           		lACLUdBean.setIdDestinatario(lACLBean.getIdGruppoInterno());
       			lACLUdBean.setCodiceRapido(lACLBean.getCodiceRapido());
       		}
       		if("V".equals(lACLBean.getOpzioniAccesso())) {
       			lACLUdBean.setFlgVisualizzaMetadati(Flag.SETTED);
       			lACLUdBean.setFlgVisualizzaFile(Flag.SETTED);
       			lACLUdBean.setFlgCopiaUd(Flag.SETTED);       			
       		} else if("C".equals(lACLBean.getOpzioniAccesso())) {
       			lACLUdBean.setFlgVisualizzaMetadati(Flag.SETTED);
       			lACLUdBean.setFlgVisualizzaFile(Flag.SETTED);       			
       			lACLUdBean.setFlgModificaMetadati(Flag.SETTED);
       			lACLUdBean.setFlgModificaFile(Flag.SETTED);
       			lACLUdBean.setFlgModificaACL(Flag.SETTED);
       			lACLUdBean.setFlgCopiaUd(Flag.SETTED);
       			lACLUdBean.setFlgEliminaUd(Flag.SETTED);
       			lACLUdBean.setFlgRipristinoUd(Flag.SETTED);
       		} 
       		if(lACLBean.getFlgEreditata() != null) {
       			lACLUdBean.setFlgEreditata(lACLBean.getFlgEreditata() ? Flag.SETTED : Flag.NOT_SETTED);
       		}
       		lListACLUd.add(lACLUdBean);			
		}		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlACL = lXmlUtilitySerializer.bindXmlList(lListACLUd);
		return xmlACL;
	}

	@Override
	public PermessiUdBean update(PermessiUdBean bean,
			PermessiUdBean oldvalue) throws Exception {
		
		return bean;
	}
	
	@Override
	public PermessiUdBean remove(PermessiUdBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public PaginatorBean<PermessiUdBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(PermessiUdBean bean)
	throws Exception {
		
		return null;
	}

}
