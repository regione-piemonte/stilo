/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocVerificaregduplicataBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneDuplicataSezioneCache;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.RegistrazioneDuplicataDestinatariBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.RegistrazioneDuplicataFileBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VerificaRegDuplicataBean;
import it.eng.client.DmpkRegistrazionedocVerificaregduplicata;
import it.eng.document.function.bean.MittentiDocumentoBean;
import it.eng.document.function.bean.TipoMittente;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "VerificaRegDuplicataDataSource")
public class VerificaRegDuplicataDataSource extends AbstractDataSource<VerificaRegDuplicataBean, VerificaRegDuplicataBean>{	

	@Override
	public VerificaRegDuplicataBean add(VerificaRegDuplicataBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		if(bean.getDatiReg() != null) {
			ProtocollazioneBean lProtocollazioneBean = bean.getDatiReg();
			ProtocollazioneDuplicataSezioneCache lProtocollazioneDuplicataSezioneCache =
				new ProtocollazioneDuplicataSezioneCache();
			lProtocollazioneDuplicataSezioneCache.setFlgTipoProv(lProtocollazioneBean.getFlgTipoProv());
			lProtocollazioneDuplicataSezioneCache.setOggetto(lProtocollazioneBean.getOggetto());
			lProtocollazioneDuplicataSezioneCache.setDtRaccomandata(lProtocollazioneBean.getDataRaccomandata());
			lProtocollazioneDuplicataSezioneCache.setNroRaccomandata(lProtocollazioneBean.getNroRaccomandata());			
			lProtocollazioneDuplicataSezioneCache.setRifOrigineProtRicevuto(lProtocollazioneBean.getRifOrigineProtRicevuto());
			lProtocollazioneDuplicataSezioneCache.setNroProtRicevuto(lProtocollazioneBean.getNroProtRicevuto());
			lProtocollazioneDuplicataSezioneCache.setAnnoProtRicevuto(lProtocollazioneBean.getAnnoProtRicevuto());
			lProtocollazioneDuplicataSezioneCache.setDtDocRicevuto(lProtocollazioneBean.getDataProtRicevuto());	
			if (bean.getDatiReg().getListaMittenti() != null) {
				List<MittentiDocumentoBean> mittentiEsterni = new ArrayList<MittentiDocumentoBean>();
				for(MittenteProtBean mittenteBean: bean.getDatiReg().getListaMittenti()) {
					MittentiDocumentoBean lMittentiDocumentoBean = new MittentiDocumentoBean();
					lMittentiDocumentoBean.setIdRubrica(mittenteBean.getIdSoggetto());
	        		lMittentiDocumentoBean.setTipo(isPersonaFisica(mittenteBean.getTipoMittente()) ? TipoMittente.PERSONA_FISICA : TipoMittente.PERSONA_GIURIDICA);
	        		String denominazioneCognome = "";
	        		String nome = "";	        			        	
	        		if("AOOI".equals(mittenteBean.getTipoMittente())) {	    
	        			lMittentiDocumentoBean.setIdRubrica(mittenteBean.getAoomdgMittente());
	        		} else if(isPersonaFisica(mittenteBean.getTipoMittente())) {
	        			denominazioneCognome = mittenteBean.getCognomeMittente();
	        			nome = mittenteBean.getNomeMittente();
	        		} else {
	        			denominazioneCognome = mittenteBean.getDenominazioneMittente();
	        		}	
	        		String codiceFiscale = mittenteBean.getCodfiscaleMittente() != null ? mittenteBean.getCodfiscaleMittente() : "";
	        		lMittentiDocumentoBean.setCodiceFiscale(codiceFiscale);
	        		lMittentiDocumentoBean.setDenominazioneCognome(denominazioneCognome);
	        		lMittentiDocumentoBean.setNome(nome);
	        		mittentiEsterni.add(lMittentiDocumentoBean);
				}
				lProtocollazioneDuplicataSezioneCache.setMittentiEsterni(mittentiEsterni);
			}
			if (bean.getDatiReg().getListaDestinatari() != null) {
				List<RegistrazioneDuplicataDestinatariBean> destinatari = new ArrayList<RegistrazioneDuplicataDestinatariBean>();
	        	for(DestinatarioProtBean destinatarioBean: bean.getDatiReg().getListaDestinatari()) {	    
	        		RegistrazioneDuplicataDestinatariBean lRegistrazioneDuplicataDestinatariBean =
	        			new RegistrazioneDuplicataDestinatariBean();
	        		lRegistrazioneDuplicataDestinatariBean.setIdRubrica(destinatarioBean.getIdSoggetto());
	        		lRegistrazioneDuplicataDestinatariBean.setTipoSoggetto(isPersonaFisica(destinatarioBean.getTipoDestinatario()) ? 
	        				TipoMittente.PERSONA_FISICA: TipoMittente.PERSONA_GIURIDICA);
	        		if("AOOI".equals(destinatarioBean.getTipoDestinatario())) {
	        			lRegistrazioneDuplicataDestinatariBean.setIdRubrica(destinatarioBean.getAoomdgDestinatario());
	        		} else if("LD".equals(destinatarioBean.getTipoDestinatario())) {
	        			lRegistrazioneDuplicataDestinatariBean.setGruppo(destinatarioBean.getGruppiDestinatario());
	        		} else if(isPersonaFisica(destinatarioBean.getTipoDestinatario())) {	        		
	        			lRegistrazioneDuplicataDestinatariBean.setDenominazioneCognome(destinatarioBean.getCognomeDestinatario());
	        			lRegistrazioneDuplicataDestinatariBean.setNome(destinatarioBean.getNomeDestinatario());
	        		} else {
	        			lRegistrazioneDuplicataDestinatariBean.setDenominazioneCognome(destinatarioBean.getDenominazioneDestinatario());
	        		}	
	        		lRegistrazioneDuplicataDestinatariBean.setCodiceFiscale(destinatarioBean.getCodfiscaleDestinatario());
	        		destinatari.add(lRegistrazioneDuplicataDestinatariBean);
	        	}
	        	lProtocollazioneDuplicataSezioneCache.setDestinatari(destinatari);
			}	
			List<RegistrazioneDuplicataFileBean> files = new ArrayList<RegistrazioneDuplicataFileBean>();
			// #@File: lista con i dati dei file della registrazione
			// Ciscuna riga della lista deve contenere le seguenti colonne:
			// 1: Nro di allegato del file. Se è il file primario va specificato 0
			// 2: Impronta del file
			// 3: Algoritmo di calcolo dell'impronta del file (SHA-1, SHA-256 ecc)
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");			
			if(StringUtils.isNotBlank(bean.getDatiReg().getUriFilePrimario())) {
				RegistrazioneDuplicataFileBean lRegistrazioneDuplicataFileBean = new RegistrazioneDuplicataFileBean();
				lRegistrazioneDuplicataFileBean.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().toString());
				lRegistrazioneDuplicataFileBean.setEncoding(lDocumentConfiguration.getEncoding().toString());
				lRegistrazioneDuplicataFileBean.setImpronta(bean.getDatiReg().getInfoFile().getImpronta());
				lRegistrazioneDuplicataFileBean.setNroAllegato(0);
				files.add(lRegistrazioneDuplicataFileBean);
			}
			if (bean.getDatiReg().getListaAllegati() != null && bean.getDatiReg().getListaAllegati().size()>0) {
				int count = 1;
	        	for(AllegatoProtocolloBean allegatoBean : bean.getDatiReg().getListaAllegati()) {
	        		RegistrazioneDuplicataFileBean lRegistrazioneDuplicataFileBean = new RegistrazioneDuplicataFileBean();
					lRegistrazioneDuplicataFileBean.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().toString());
					lRegistrazioneDuplicataFileBean.setEncoding(lDocumentConfiguration.getEncoding().toString());
					if(allegatoBean.getInfoFile()!=null)
						   lRegistrazioneDuplicataFileBean.setImpronta(allegatoBean.getInfoFile().getImpronta());	
					lRegistrazioneDuplicataFileBean.setNroAllegato(count);
					files.add(lRegistrazioneDuplicataFileBean);								
					count++;
	        	}
	        }
			lProtocollazioneDuplicataSezioneCache.setFiles(files);
	        
	        Writer wr = null;
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String datiRegXml = lXmlUtilitySerializer.bindXml(lProtocollazioneDuplicataSezioneCache);
			
			bean.setDatiRegXml(datiRegXml);
	        
	        DmpkRegistrazionedocVerificaregduplicataBean input = new DmpkRegistrazionedocVerificaregduplicataBean();
	        input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setXmldatiregin(datiRegXml);
			
			DmpkRegistrazionedocVerificaregduplicata dmpkRegistrazionedocVerificaregduplicata = new DmpkRegistrazionedocVerificaregduplicata();
			StoreResultBean<DmpkRegistrazionedocVerificaregduplicataBean> output = dmpkRegistrazionedocVerificaregduplicata.execute(getLocale(),loginBean, input);
			
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				throw new StoreException(output);						
			}
			
			bean.setStatoDuplicazione(output.getResultBean().getStatoduplicazioneout());
			
			bean.setWarningMessage(output.getResultBean().getWarningmsgout());
			
//			if(output.getResultBean().getPossibiliregduplicatexmlout() != null) {
//				StringReader sr = new StringReader(output.getResultBean().getPossibiliregduplicatexmlout());
//				// Lista con le registrazioni che sono possibili duplicati della registrazione che si sta per effettuare (XML conforme a schema LISTA_STD.xsd)
//				Lista lista = (Lista) JAXBContext.newInstance(Lista.class).createUnmarshaller().unmarshal(sr);			
//				if(lista != null) {
//					List<RegDuplicataBean> listaRegDuplicate = new ArrayList<RegDuplicataBean>();					
//					for (int i = 0; i < lista.getRiga().size(); i++) 
//					{
//						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																						
//			       		// Ogni registrazione è un tag Riga nell'xml; i dati di ogni registrazione corrispondono ai seguenti tag Colonna:
//						// 1: Score del match con la registrazione in corso (n.ro intero)
//						// 2: Estremi di registrazione (registro, anno e n.ro)
//						// 3: Data e ora di registrazione (nel formato definito dal parametro di config. FMT_STD_TIMESTAMP)
//						// 4: (valori E/U/I) Verso della registrazione: E = Entrata, U = In uscita, I = Interna
//						// 5: Denominazione o cognome e nome del/i mittente/i (se più di uno separati da ;)
//						// 6: (valori 2/1/0) Indica se il/i mittenti della registrazione in corso rispetto a quello/i della registrazione rappresentata dal record sono tutti uguali (=2) o uguali in parte (=1) o del tutto diversi (=0). Risulta 2 anche se sono non valorizzati in entrambe le registrazioni
//						// 7: Denominazione o cognome e nome del/i destinatari (se più di uno separati da ;)
//						// 8: (valori 2/1/0) Indica se il/i destinatari della registrazione in corso rispetto a quello/i della registrazione rappresentata dal record sono tutti uguali (=2) o uguali in parte (=1) o del tutto diversi (=0). Risulta 2 anche se sono non valorizzati in entrambe le registrazioni
//						// 9: Rif. della registrazione di proveninenza (valorizzato se reg. in entrata)
//						// 10: (valori 2/0) Indica se il rif. della reg. di provenenienza della registrazione in corso è uguale (=2) o diverso (=0) da quello della registrazione rappresentata dal record. Risulta 2 anche se sono non valorizzati in entrambe le registrazioni
//						// 11: Nro della registrazione di proveninenza (valorizzato se reg. in entrata)
//						// 12: (valori 2/0) Indica se il nro della reg. di provenenienza della registrazione in corso è uguale (=2) o diverso (=0) da quello della registrazione rappresentata dal record. Risulta 2 anche se sono non valorizzati in entrambe le registrazioni
//						// 13: Data della registrazione di proveninenza (nel formato definito dal parametro di config. FMT_STD_DATA; valorizzata se reg. in entrata)
//						// 14: (valori 2/0) Indica se la data della reg. di provenenienza della registrazione in corso è uguale (=2) o diversa (=0) da quella della registrazione rappresentata dal record. Risulta 2 anche se sono non valorizzati in entrambe le registrazioni
//						// 15: Oggetto
//						// 16: (valori 2/0) Indica se l'oggetto della registrazione in corso è uguale (=2) o diverso (=0) da quello della registrazione rappresentata dal record (uguale a meno del case e di caratteri NON significativi quali spazi, carriage return e caratteri di punteggiatura)
//						// 17: (1/0) Indica se c'è (=1) o meno (=0) file primario
//						// 18: (valori 2/0/-1) Indica se l'impronta del file primario della registrazione in corso coincide (=2) o è diversa (=0) da quella del file primario della registrazione rappresentata dal record. Risulta 2 anche se NON c'è file primario in entrambe le registrazioni
//						//		Vale -1 nel caso in cui le impronte non siano comparabili perchè calcolate con algoritmi e/o encoding diversi
//						// 19: URI del file primario (valorizzato solo se il file è visibile all'utente di lavoro o se coincide con quello della reg. in corso)
//						// 20: Nome del file primario (valorizzato solo se il file è visibile all'utente di lavoro o se coincide con quello della reg. in corso)
//						// 21: (1/0) Indica se ci sono (=1) o meno (=0) file (primario e allegati)
//						// 22: (valori 2/1/0/-1) Indica se i file della registrazione in corso coincidono tutti (=2) o in parte (=1) o nessuno (=0) con quelli della registrazione rappresentata dal record. Risulta 2 anche se NON ci sono file in entrambe le registrazioni
//						//		Vale -1 nel caso in cui non risultino file coincidenti e alcuni/tutti i file non siano comparabili perchè le impronte sono calcolate con algoritmi e/o encoding diversi da quelli dei file in input
//						// 23: N.ro di allegato (0 se è il primario) dei file coincidenti con quelli della registrazione in corso
//						//	   Nel caso siano più di uno gli URI sono separati da ;
//						// 24: URI dei file coincidenti con quelli della registrazione in corso
//						//	   Nel caso siano più di uno gli URI sono separati da ;
//						// 25: Nomi dei file coincidenti con quelli della registrazione in corso
//						//	   Nel caso siano più di uno gli URI sono separati da ;
//						// 26: N.ro di raccomandata (valorizzato solo se reg. in entrata)
//						// 27: (valori 2/0) Indica se il nro di raccomandata della registrazione in corso è uguale (=2) o diverso (=0) da quello della registrazione rappresentata dal record. Risulta 2 anche se sono non valorizzati in entrambe le registrazioni
//						// 28: Data raccomandata, nel formato dato dal parametro di config. FMT_STD_DATA (valorizzata solo se reg. in entrata)
//			       		HashMap<String, String> comparators = new HashMap<String, String>();
//			       		RegDuplicataBean regDuplicata = new RegDuplicataBean();	    		        		
//			       		regDuplicata.setScore(v.get(0)); //colonna 1 dell'xml		        		
//			       		regDuplicata.setEstremiRegistrazione(v.get(1)); //colonna 2 dell'xml				       					       		 	        		
//		        		regDuplicata.setTsRegistrazione(v.get(2) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(2)) : null); //colonna 3 dell'xml	
//			       		regDuplicata.setFlgVersoRegistrazione(v.get(3)); //colonna 4 dell'xml	
//			       		regDuplicata.setMittenti(v.get(4)); //colonna 5 dell'xml	
//			       		comparators.put("mittenti", v.get(5)); //colonna 6 dell'xml
//			       		regDuplicata.setDestinatari(v.get(6)); //colonna 7 dell'xml
//			       		comparators.put("destinatari", v.get(7)); //colonna 8 dell'xml
//			       		regDuplicata.setRifRegProvenienza(v.get(8)); //colonna 9 dell'xml
//			       		comparators.put("rifRegProvenienza", v.get(9)); //colonna 10 dell'xml
//			       		regDuplicata.setNroRegProvenienza(v.get(10)); //colonna 11 dell'xml
//			       		comparators.put("nroRegProvenienza", v.get(11));	//colonna 12 dell'xml 	        		
//		        		regDuplicata.setDtRegProvenienza(v.get(12) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(12)) : null); //colonna 13 dell'xml
//		        		comparators.put("dtRegProvenienza", v.get(13)); //colonna 14 dell'xml
//			       		regDuplicata.setOggetto(v.get(14)); //colonna 15 dell'xml
//			       		comparators.put("oggetto", v.get(15)); //colonna 16 dell'xml
//			       		regDuplicata.setFlgPrimario(v.get(16)); //colonna 17 dell'xml
//			       		comparators.put("nomeFilePrimario", v.get(17)); //colonna 18 dell'xml
//			       		regDuplicata.setUriFilePrimario(v.get(18)); //colonna 19 dell'xml	
//			       		regDuplicata.setNomeFilePrimario(v.get(19)); //colonna 20 dell'xml	
//			       		regDuplicata.setFlgPrimarioAllegati(v.get(20)); //colonna 21 dell'xml			       		
//			       		comparators.put("nomiFileCoincidenti", v.get(21)); //colonna 22 dell'xml			       		
//			       		regDuplicata.setNroAllegatoFileCoincidenti(v.get(22)); //colonna 23 dell'xml	
//			       		regDuplicata.setUriFileCoincidenti(v.get(23)); //colonna 24 dell'xml	
//			       		regDuplicata.setNomiFileCoincidenti(v.get(24)); //colonna 25 dell'xml		
//			       		regDuplicata.setNroRaccomandata(v.get(25)); //colonna 26 dell'xml
//			       		comparators.put("nroRaccomandata", v.get(26)); //colonna 27 dell'xml			       		 	        				        			
//		        		regDuplicata.setDtRaccomandata(v.get(27) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(27)) : null); //colonna 28 dell'xml		        		
//		        		comparators.put("dtRaccomandata", v.get(28)); //colonna 29 dell'xml			       					       		
//		        		regDuplicata.setIdUd(v.get(29)); //colonna 30 dell'xml		        		
//		        		listaRegDuplicate.add(regDuplicata);		        		
//			   		}
//					bean.setListaRegDuplicate(listaRegDuplicate);					
//				}					
//			}
		}
				
		return bean;
	}	
	
	private boolean isPersonaFisica(String tipo) {
		return ("F".equals(tipo) || "PF".equals(tipo) || "UP".equals(tipo));
	}
	
	@Override
	public VerificaRegDuplicataBean get(VerificaRegDuplicataBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public VerificaRegDuplicataBean remove(VerificaRegDuplicataBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public VerificaRegDuplicataBean update(VerificaRegDuplicataBean bean,
			VerificaRegDuplicataBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<VerificaRegDuplicataBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(VerificaRegDuplicataBean bean)
	throws Exception {
		
		return null;
	}

}
