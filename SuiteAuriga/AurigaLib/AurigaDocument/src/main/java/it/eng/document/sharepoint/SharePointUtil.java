/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetparamforstoreinsharepointBean;
import it.eng.auriga.database.store.dmpk_core.store.Getparamforstoreinsharepoint;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.document.function.RecuperoDocumenti;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.AssegnatariOutBean;
import it.eng.document.function.bean.ClassFascTitolarioOutBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DestInvioCCOutBean;
import it.eng.document.function.bean.DestinatariOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.MittentiDocumentoOutBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.UrlVersione;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.sharepointclient.util.SharePointFileUtils;
import it.eng.sharepointclient.util.SharePointVersionFileUtils;
import it.eng.utility.storageutil.impl.sharePoint.SharePointStorageConfig;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;

public class SharePointUtil {
	
	private static Logger mLogger = Logger.getLogger(SharePointUtil.class);
	
	private String propertySocieta;
	private String propertyTipoProtocollo;
	private String propertyDataProtocollo;
	private String propertyAnnoProtocollo;
	private String propertyNumeroProtocollo;
	private String propertyUtenteProtocollo;
	private String propertyUfficioProtocollo;
	private String propertyDocumentoPrimario;
	private String propertyNumeroAllegato;
	private String propertyAltraNumerazioneRegistro;
	private String propertyAltraNumerazioneData;
	private String propertyAltraNumerazioneAnno;
	private String propertyAltraNumerazioneNumero;
	private String propertyMittenti;
	private String propertyDestinatari;
	private String propertyOggettoRegistrazione;
	private String propertyDescrizione;
	private String propertyProtRicevutoRifOriginale;
	private String propertyProtRicevutoNumero;
	private String propertyProtRicevutoAnno;
	private String propertyProtRicevutoData;
	private String propertyAssegnatari;
	private String propertyInConoscenzaA;
	private String propertyClassificazione;
	private String propertyRiservatezza;
	private String propertyTermineRiservatezza;
	private String propertyTipologia;
	private String propertyDataDocumento;
	private String propertyMezzoEstremiTrasmissione;
	private String propertyNote;
	private String propertyDataOraAnnullamento;
	private String propertyAnnullamentoEffettuatoDa;
	private String propertyMotivoAnnullamento;
	private String propertyAnnullamentoAutorizzatoConAtto;
	private String propertyNomeFile;
	private String propertyFirmato;
	private String propertyFirmatoDa;
	private String propertyAnnullato;
	
	private String formatoDate;
	
	public static boolean isIntegratoConSharePoint(AurigaLoginBean pAurigaLoginBean) throws Exception {
		
		mLogger.debug("Su questo dominio (" + pAurigaLoginBean.getSpecializzazioneBean().getIdDominio() + ") e' attiva l'integrazione con SHAREPOINT");	
		return "SHAREPOINT".equals(DocumentStorage.getTipoStorage(pAurigaLoginBean.getSpecializzazioneBean().getIdDominio()));
		
	}
	
	public static SharePointStorageConfig getConfigurazioniSharePoint(AurigaLoginBean pAurigaLoginBean) throws Exception {
		String xmlConfig = DocumentStorage.getConfigurazioniStorage(pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
		SharePointStorageConfig config = (SharePointStorageConfig)XMLUtil.newInstance().deserializeIgnoringDtd(xmlConfig, SharePointStorageConfig.class);
		return config;
		
	}
	
	public String salvaFile(AurigaLoginBean pAurigaLoginBean, RebuildedFile pRebuildedFile) throws Exception {
		
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(pAurigaLoginBean.getSchema());
		
		DmpkCoreGetparamforstoreinsharepointBean lInputBean = new DmpkCoreGetparamforstoreinsharepointBean();
		lInputBean.setIddocin(pRebuildedFile.getIdDocumento());
		lInputBean.setDisplayfilenamein(pRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename());
		lInputBean.setMimetypein(pRebuildedFile.getInfo().getAllegatoRiferimento().getMimetype());
		
		Getparamforstoreinsharepoint lGetparamforstoreinsharepoint = new Getparamforstoreinsharepoint();		
		StoreResultBean<DmpkCoreGetparamforstoreinsharepointBean> lStoreResultBean = lGetparamforstoreinsharepoint.execute(lSchemaBean, lInputBean);		
		
		if (lStoreResultBean.isInError()){
			mLogger.debug(lStoreResultBean.getDefaultMessage());
			mLogger.debug(lStoreResultBean.getErrorContext());
			mLogger.debug(lStoreResultBean.getErrorCode());
			throw new StoreException(lStoreResultBean);
		}		
		
		List<String> params = new ArrayList<String>();
		if(lStoreResultBean.getResultBean().getParametrixmlout() != null) {
			StringReader sr = new StringReader(lStoreResultBean.getResultBean().getParametrixmlout());			
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {		
					String content = lista.getRiga().get(i).getColonna().get(0).getContent();
					mLogger.error("******************** XML Getparamforstoreinsharepoint: "+ lStoreResultBean.getResultBean().getParametrixmlout());
					mLogger.error("******************** PARAMETRI Getparamforstoreinsharepoint: "+ content);
					if(StringUtils.isNotBlank(content)) {
						try {
							SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));					
						} catch (JAXBException e) {
							content = content.replace("\n","<br/>");
						}
						params.add(content);	
		   			} 
				}
			}
		}
		
		return DocumentStorage.store(pRebuildedFile.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio(), params.toArray(new String[params.size()]));		
	}
	
	public void aggiornaMetadati(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoOutBean pCreaModDocumentoOutBean) throws Exception {
		mLogger.debug("Metodo aggiornaMetadati");
		try {
			
			SharePointStorageConfig mSharePointConfig = getConfigurazioniSharePoint(pAurigaLoginBean);
			mLogger.debug("mSharePointConfig " + mSharePointConfig);
			
			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
			lRecuperaDocumentoInBean.setIdUd(pCreaModDocumentoOutBean.getIdUd());				
			
			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();			
			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);	
			mLogger.debug("lRecuperaDocumentoOutBean " + lRecuperaDocumentoOutBean);

			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			mLogger.debug("lDocumentoXmlOutBean " + lDocumentoXmlOutBean);

//			DocumentoXmlOutBean lDocumentoXmlOutBean = getDocumento(pAurigaLoginBean);
							
			SharePointFileUtils shUtils = null;
			String libraryName = null;
			if( mSharePointConfig!=null ) {
				String wsdlLocation = mSharePointConfig.getWsdlLocationList();
				String serviceName = mSharePointConfig.getServiceNameList();
				String serviceNamespace = mSharePointConfig.getServiceNamespaceList();
				String username = mSharePointConfig.getUsername();
				String password = mSharePointConfig.getPassword();
				libraryName = mSharePointConfig.getLibraryName();
				shUtils = new SharePointFileUtils(wsdlLocation, serviceName, serviceNamespace, username, password);
			} else {
				throw new Exception("Configurazioni storage non trovate");
			}
			
						
			if(lDocumentoXmlOutBean.getFilePrimario() != null) {
				FilePrimarioOutBean filePrimario = lDocumentoXmlOutBean.getFilePrimario();
				
				HashMap<String,String> metadati = getMetadati(pAurigaLoginBean, lDocumentoXmlOutBean,filePrimario, null, 0);
				
				if( shUtils!=null){
					String[] sTmp = StorageUtil.resolveStorageUri(filePrimario.getUri());
					if( sTmp!=null && sTmp.length>1){
						String fileRefFilePrimario = sTmp[1];
						String idDocument = shUtils.getIdDocument(libraryName, fileRefFilePrimario );
						mLogger.debug("idDocument " + idDocument );
						if( idDocument!=null )
							shUtils.updateFile(libraryName, idDocument, null, metadati );
						else
							throw new Exception("Id documento non trovato");
					} else
						throw new Exception("Documento non trovato");
				}
			} 
			
			if(lDocumentoXmlOutBean.getAllegati().size() > 0) {
//				for(int i = 0; i < lRecuperaDocumentoOutBean.getDocumento().getAllegati().size(); i++) {
				for(int i = 0; i < lDocumentoXmlOutBean.getAllegati().size(); i++) {
					AllegatiOutBean allegato = lDocumentoXmlOutBean.getAllegati().get(i);
					HashMap<String,String> metadati = getMetadati(pAurigaLoginBean, lDocumentoXmlOutBean,null, allegato, i+1);
					
					if( shUtils!=null){
						String[] sTmp = StorageUtil.resolveStorageUri(allegato.getUri());
						if( sTmp!=null && sTmp.length>1){
							String fileRefFileAllegato = sTmp[1];
							String idDocument = shUtils.getIdDocument(libraryName, fileRefFileAllegato );
							mLogger.debug("idDocument " + idDocument );
							if( idDocument!=null )
								shUtils.updateFile(libraryName, idDocument, null, metadati );
							else
								throw new Exception("Id documento non trovato");
						} else
							throw new Exception("Documento non trovato");
					}
				}
			}
			
		} catch(Exception e) {			
			//se l'aggiornamento dei metadati non va a buon fine setto questa variabile sull'output per dare un warning
			pCreaModDocumentoOutBean.setSalvataggioMetadatiError(true);				
		}
		
	}
	
	public List<UrlVersione> getUriPrecentiVersioni(AurigaLoginBean pAurigaLoginBean, String fileUrl){
		List<UrlVersione> uri = new ArrayList<UrlVersione>();
		
		try {
			SharePointStorageConfig mSharePointConfig = getConfigurazioniSharePoint(pAurigaLoginBean);
			String wsdlLocation = mSharePointConfig.getWsdlLocationVersion();
			String nameSpaceService = mSharePointConfig.getServiceNamespaceVersion();
			String serviceName = mSharePointConfig.getServiceNameVersion();
			String username = mSharePointConfig.getUsername();
			String password = mSharePointConfig.getPassword();
			String rootPath = mSharePointConfig.getRootPath();
			SharePointVersionFileUtils shUtils = new SharePointVersionFileUtils(wsdlLocation, serviceName, nameSpaceService, username, password);
			
			String[] sTmp = StorageUtil.resolveStorageUri(fileUrl);
			if( sTmp!=null && sTmp.length>1){
				String fileRefFileAllegato = sTmp[1];
				LinkedHashMap<String, String> mapVersion = shUtils.getVersions(fileRefFileAllegato);
				Iterator<String> versioni = mapVersion.keySet().iterator();
				while( versioni.hasNext() ){
					String versione = versioni.next();
					if( !versione.startsWith("@")){
						String uriFile = mapVersion.get( versione );
						UrlVersione urlVersione = new UrlVersione();
						urlVersione.setUrl( uriFile.substring( rootPath.length() ));
						uri.add( urlVersione );
					}
				}
			}
		} catch (Exception e) {
			mLogger.warn(e);
		}
		
		mLogger.info("Uri Versioni precedenti " + uri);
		return uri;
	}
	
	
	public HashMap<String,String> getMetadati(AurigaLoginBean pAurigaLoginBean, DocumentoXmlOutBean lDocumentoXmlOutBean, FilePrimarioOutBean filePrimario, 
			AllegatiOutBean allegato, int numeroAllegato){
		mLogger.debug("Metodo getMetadati");
		
		HashMap<String, String> metadati = new HashMap<String, String>();
		SimpleDateFormat formatter = new SimpleDateFormat( getFormatoDate() );
		
		metadati.put( getPropertySocieta(), pAurigaLoginBean.getSpecializzazioneBean().getDesDominioOut() );
		
		if( lDocumentoXmlOutBean.getFlgTipoProv()!=null )
			metadati.put( getPropertyTipoProtocollo(), lDocumentoXmlOutBean.getFlgTipoProv().getDescrizione() );
		
		if( lDocumentoXmlOutBean.getEstremiRegistrazione()!=null ){
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione()!=null )
				metadati.put( getPropertyDataProtocollo(), formatter.format( lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione() ));
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getAnno()!=null){
				metadati.put( getPropertyAnnoProtocollo(), lDocumentoXmlOutBean.getEstremiRegistrazione().getAnno() );
			}
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getNro()!=null){
				if( verificaLunghezzaCampo(lDocumentoXmlOutBean.getEstremiRegistrazione().getNro(),7) )
					metadati.put( getPropertyNumeroProtocollo(), lDocumentoXmlOutBean.getEstremiRegistrazione().getNro() );
				else {
					mLogger.warn("E' stata superata la lunghezza massima per il numero di protocollo, ne setto il valore troncandolo");
					metadati.put( getPropertyNumeroProtocollo(), lDocumentoXmlOutBean.getEstremiRegistrazione().getNro().substring(0, 7) );
				}
			}
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getDesUO()!=null)
				metadati.put( getPropertyUfficioProtocollo(), lDocumentoXmlOutBean.getEstremiRegistrazione().getDesUO() );
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getDesUser()!=null)
				metadati.put( getPropertyUtenteProtocollo(), lDocumentoXmlOutBean.getEstremiRegistrazione().getDesUser() );
		}
		
		if( filePrimario!=null )
			metadati.put( getPropertyDocumentoPrimario(), "1" );
		else 
			metadati.put( getPropertyDocumentoPrimario(), "0" );
		
		if( numeroAllegato>0)
			metadati.put( getPropertyNumeroAllegato(), ""+numeroAllegato);
		
		if( lDocumentoXmlOutBean.getRegEmergenza()!=null){
			if( lDocumentoXmlOutBean.getRegEmergenza().getRegistro()!=null)
				metadati.put( getPropertyAltraNumerazioneRegistro(), lDocumentoXmlOutBean.getRegEmergenza().getRegistro() );
			if( lDocumentoXmlOutBean.getRegEmergenza().getTsRegistrazione()!=null)
				metadati.put( getPropertyAltraNumerazioneData(), formatter.format( lDocumentoXmlOutBean.getRegEmergenza().getTsRegistrazione() ) );
			if( lDocumentoXmlOutBean.getRegEmergenza().getAnno()!=null)
				metadati.put( getPropertyAltraNumerazioneAnno(), lDocumentoXmlOutBean.getRegEmergenza().getAnno() );
			if( lDocumentoXmlOutBean.getRegEmergenza().getNro()!=null)
				metadati.put( getPropertyAltraNumerazioneNumero(), lDocumentoXmlOutBean.getRegEmergenza().getNro() );
		}

		
		String mittenti = "";
		List<MittentiDocumentoOutBean> mittentiList = lDocumentoXmlOutBean.getMittenti();
		for(MittentiDocumentoOutBean mittente : mittentiList){
			if( mittente.getDenominazioneCognome()!=null)
				mittenti += mittente.getDenominazioneCognome() ;
			if( mittente.getNome()!=null)
				mittenti += " " + mittente.getNome();
			mittenti = mittenti.trim();
			if( mittentiList.size()>1)
				mittenti += ";";
		}
//		if( verificaLunghezzaCampo(mittenti, 1000))
			metadati.put( getPropertyMittenti(), mittenti);
//		else {
//			metadati.put( getPropertyMittenti(), mittenti.substring(0,1000 ) );
//			mLogger.warn("Campo mittenti di lunghezza superiore al consentito");
//		}
		
		String destinatari = "";
		List<DestinatariOutBean> destinatariList = lDocumentoXmlOutBean.getDestinatari();
		for(DestinatariOutBean destinatario : destinatariList){
			if( destinatario.getDenominazioneCognome()!=null)
				destinatari += destinatario.getDenominazioneCognome() ;
			if( destinatario.getNome()!=null)
				destinatari += " " + destinatario.getNome();
			destinatari = destinatari.trim();
			if( destinatariList.size()>1)
				destinatari += ";";
		}
//		if( verificaLunghezzaCampo(destinatari, 1000))
			metadati.put( getPropertyDestinatari(), destinatari );
//		else {
//			metadati.put( getPropertyDestinatari(), destinatari.substring(0,1000 ) );
//			mLogger.warn("Campo destinatari di lunghezza superiore al consentito");
//		}
		
		if( lDocumentoXmlOutBean.getOggetto()!=null){
			if( verificaLunghezzaCampo(lDocumentoXmlOutBean.getOggetto(), 4000))
				metadati.put( getPropertyOggettoRegistrazione(), lDocumentoXmlOutBean.getOggetto() );
			else {
				metadati.put( getPropertyOggettoRegistrazione(), lDocumentoXmlOutBean.getOggetto().substring(0,4000 ) );
				mLogger.warn("Campo oggetto di lunghezza superiore al consentito");
			}
		}
//		if( lDocumentoXmlOutBean.get!=null){
//			if( verificaLunghezzaCampo(lDocumentoXmlOutBean.get, 4000))
//				metadati.put( getPropertyDescrizione(), lDocumentoXmlOutBean.get);
//			else 
//				mLogger.debug("Campo descrizione di lunghezza superiore al consentito");
//		}
		
		if( lDocumentoXmlOutBean.getRifDocRicevuto()!=null){
			if( verificaLunghezzaCampo(lDocumentoXmlOutBean.getRifDocRicevuto(), 100))
				metadati.put( getPropertyProtRicevutoRifOriginale(), lDocumentoXmlOutBean.getRifDocRicevuto() );
			else {
				metadati.put( getPropertyProtRicevutoRifOriginale(), lDocumentoXmlOutBean.getRifDocRicevuto().substring(0,100 ) );
				mLogger.warn("Campo ProtRicevutoRifOriginale di lunghezza superiore al consentito");
			}
		}
		if( lDocumentoXmlOutBean.getEstremiRegDocRicevuto()!=null){
			if( verificaLunghezzaCampo(lDocumentoXmlOutBean.getEstremiRegDocRicevuto(), 100))
				metadati.put( getPropertyProtRicevutoNumero(), lDocumentoXmlOutBean.getEstremiRegDocRicevuto()  );
			else {
				metadati.put( getPropertyProtRicevutoNumero(), lDocumentoXmlOutBean.getEstremiRegDocRicevuto().substring(0,100 ) );
				mLogger.warn("Campo ProtRicevutoNumero di lunghezza superiore al consentito");
			}
		}
		if( lDocumentoXmlOutBean.getDtDocRicevuto()!=null )
			metadati.put( getPropertyProtRicevutoData(), formatter.format(lDocumentoXmlOutBean.getDtDocRicevuto() ) );
		if( lDocumentoXmlOutBean.getAnnoDocRicevuto()!=null)
			metadati.put( getPropertyProtRicevutoAnno(), lDocumentoXmlOutBean.getAnnoDocRicevuto() );
		
		String assegnatari = "";
		List<AssegnatariOutBean> assegnatariList = lDocumentoXmlOutBean.getAssegnatari();
		if( assegnatariList!=null){
			for(AssegnatariOutBean assegnatario : assegnatariList){
				assegnatari += assegnatario.getDescr();
				assegnatari = assegnatari.trim();
				if( assegnatariList.size()>1)
					assegnatari += ";";
			}
		}
		if( verificaLunghezzaCampo(assegnatari, 1000))
			metadati.put( getPropertyAssegnatari(), assegnatari );
		else {
			metadati.put( getPropertyAssegnatari(), assegnatari.substring(0,1000 ) );
			mLogger.warn("Campo assegnatari di lunghezza superiore al consentito");
		}
		
		String inConoscenza = "";
		List<DestInvioCCOutBean> inConoscenzaList = lDocumentoXmlOutBean.getDestInvioCC();
		if( inConoscenzaList!=null ){
			for(DestInvioCCOutBean inCon : inConoscenzaList){
				inConoscenza += inCon.getDescr();
				inConoscenza = inConoscenza.trim();
				if( inConoscenzaList.size()>1)
					inConoscenza += ";";
			}
		}
		if( verificaLunghezzaCampo(inConoscenza, 1000))
			metadati.put( getPropertyInConoscenzaA(), inConoscenza );
		else { 
			metadati.put( getPropertyInConoscenzaA(), inConoscenza.substring(0,1000 ) );
			mLogger.warn("Campo inConoscenza di lunghezza superiore al consentito");
		}
		
		String classificazione = "";
		List<ClassFascTitolarioOutBean> classificazioneList = lDocumentoXmlOutBean.getClassifichefascicoli();
		if( classificazioneList!=null ){
			for(ClassFascTitolarioOutBean classif : classificazioneList){
				String indice = classif.getIndice();
				if( indice!=null ){
					classificazione += indice.replaceAll("|*|","–") ;
				}
				classificazione = classificazione.trim();
				if( classificazioneList.size()>1)
					classificazione += ";";
			}
		}
		if( verificaLunghezzaCampo(classificazione, 1000))
			metadati.put( getPropertyClassificazione(), classificazione );
		else {
			metadati.put( getPropertyClassificazione(), classificazione.substring(0,1000 ) );
			mLogger.warn("Campo classificazione di lunghezza superiore al consentito");
		}
		
		if( lDocumentoXmlOutBean.getLivelloRiservatezza()!=null )
			metadati.put( getPropertyRiservatezza(), lDocumentoXmlOutBean.getLivelloRiservatezza() );
		if( lDocumentoXmlOutBean.getTermineRiservatezza()!=null)
			metadati.put( getPropertyTermineRiservatezza(), formatter.format( lDocumentoXmlOutBean.getTermineRiservatezza() ) );
		
		if( lDocumentoXmlOutBean.getNomeTipoDocumento()!=null){
			if( verificaLunghezzaCampo(lDocumentoXmlOutBean.getNomeTipoDocumento(), 250))
				metadati.put( getPropertyTipologia(), lDocumentoXmlOutBean.getNomeTipoDocumento() );
			else {
				metadati.put( getPropertyTipologia(), lDocumentoXmlOutBean.getNomeTipoDocumento().substring(0,250 ) );
				mLogger.debug("Campo tipologia di lunghezza superiore al consentito");
			}
		}
		
		if( lDocumentoXmlOutBean.getDataStesura()!=null)
			metadati.put( getPropertyDataDocumento(), formatter.format( lDocumentoXmlOutBean.getDataStesura() ) );
		
		if( lDocumentoXmlOutBean.getEstremiTrasm()!=null)
			metadati.put( getPropertyMezzoEstremiTrasmissione(), lDocumentoXmlOutBean.getEstremiTrasm() );
		
		if( lDocumentoXmlOutBean.getNote()!=null)
			metadati.put( getPropertyNote(), lDocumentoXmlOutBean.getNote() );
			
		if( lDocumentoXmlOutBean.getEstremiRegistrazione() !=null){
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione() !=null)
				metadati.put( getPropertyDataOraAnnullamento(), formatter.format( lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione() ));
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getUserAnnullamento() !=null)
				metadati.put( getPropertyAnnullamentoEffettuatoDa(), lDocumentoXmlOutBean.getEstremiRegistrazione().getUserAnnullamento()  );
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getMotiviRichAnnullamento() !=null)
				metadati.put( getPropertyMotivoAnnullamento(), lDocumentoXmlOutBean.getEstremiRegistrazione().getMotiviRichAnnullamento() );
			if( lDocumentoXmlOutBean.getEstremiRegistrazione().getEstremiAttoAutAnnullamento() !=null){
				if( verificaLunghezzaCampo(lDocumentoXmlOutBean.getEstremiRegistrazione().getEstremiAttoAutAnnullamento(), 250))
					metadati.put( getPropertyAnnullamentoAutorizzatoConAtto(), lDocumentoXmlOutBean.getEstremiRegistrazione().getEstremiAttoAutAnnullamento() );
				else {
					metadati.put( getPropertyAnnullamentoAutorizzatoConAtto(), lDocumentoXmlOutBean.getEstremiRegistrazione().getEstremiAttoAutAnnullamento().substring(0,250 ) );
					mLogger.debug("Campo AnnullamentoAutorizzatoConAtto di lunghezza superiore al consentito");
				}
			}
		}
		
		if( filePrimario!=null ){
			if( filePrimario.getFlgFirmato().getDbValue()!=null)
				metadati.put( getPropertyFirmato(), filePrimario.getFlgFirmato().getDbValue() );
			if( filePrimario.getFirmatari()!=null)
				metadati.put( getPropertyFirmatoDa(), filePrimario.getFirmatari() );
		}
		if( allegato!=null ){
			if( allegato.getFlgFileFirmato().getDbValue()!=null)
				metadati.put( getPropertyFirmato(), allegato.getFlgFileFirmato().getDbValue() );
			if( allegato.getFirmatari()!=null)
				metadati.put( getPropertyFirmatoDa(), allegato.getFirmatari() );
		}
		
		mLogger.debug("metadati " + metadati);
		return metadati;
	}
	
	private boolean verificaLunghezzaCampo(String campo, int lunghezzaMassima){
		if(campo.length()>lunghezzaMassima)
			return false;
		else
			return true;
	}

	public String getPropertyAnnoProtocollo() {
		return propertyAnnoProtocollo;
	}

	public void setPropertyAnnoProtocollo(String propertyAnnoProtocollo) {
		this.propertyAnnoProtocollo = propertyAnnoProtocollo;
	}

	public String getPropertySocieta() {
		return propertySocieta;
	}

	public void setPropertySocieta(String propertySocieta) {
		this.propertySocieta = propertySocieta;
	}

	public String getPropertyTipoProtocollo() {
		return propertyTipoProtocollo;
	}

	public void setPropertyTipoProtocollo(String propertyTipoProtocollo) {
		this.propertyTipoProtocollo = propertyTipoProtocollo;
	}

	public String getPropertyDataProtocollo() {
		return propertyDataProtocollo;
	}

	public void setPropertyDataProtocollo(String propertyDataProtocollo) {
		this.propertyDataProtocollo = propertyDataProtocollo;
	}

	public String getPropertyNumeroProtocollo() {
		return propertyNumeroProtocollo;
	}

	public void setPropertyNumeroProtocollo(String propertyNumeroProtocollo) {
		this.propertyNumeroProtocollo = propertyNumeroProtocollo;
	}

	public String getPropertyUtenteProtocollo() {
		return propertyUtenteProtocollo;
	}

	public void setPropertyUtenteProtocollo(String propertyUtenteProtocollo) {
		this.propertyUtenteProtocollo = propertyUtenteProtocollo;
	}

	public String getPropertyUfficioProtocollo() {
		return propertyUfficioProtocollo;
	}

	public void setPropertyUfficioProtocollo(String propertyUfficioProtocollo) {
		this.propertyUfficioProtocollo = propertyUfficioProtocollo;
	}

	public String getPropertyDocumentoPrimario() {
		return propertyDocumentoPrimario;
	}

	public void setPropertyDocumentoPrimario(String propertyDocumentoPrimario) {
		this.propertyDocumentoPrimario = propertyDocumentoPrimario;
	}
	
	public String getPropertyMittenti() {
		return propertyMittenti;
	}

	public void setPropertyMittenti(String propertyMittenti) {
		this.propertyMittenti = propertyMittenti;
	}
	
	public String getPropertyDestinatari() {
		return propertyDestinatari;
	}

	public void setPropertyDestinatari(String propertyDestinatari) {
		this.propertyDestinatari = propertyDestinatari;
	}

	public String getPropertyOggettoRegistrazione() {
		return propertyOggettoRegistrazione;
	}

	public void setPropertyOggettoRegistrazione(String propertyOggettoRegistrazione) {
		this.propertyOggettoRegistrazione = propertyOggettoRegistrazione;
	}

	public String getPropertyNumeroAllegato() {
		return propertyNumeroAllegato;
	}

	public void setPropertyNumeroAllegato(String propertyNumeroAllegato) {
		this.propertyNumeroAllegato = propertyNumeroAllegato;
	}

	public String getPropertyAltraNumerazioneRegistro() {
		return propertyAltraNumerazioneRegistro;
	}

	public void setPropertyAltraNumerazioneRegistro(String propertyAltraNumerazioneRegistro) {
		this.propertyAltraNumerazioneRegistro = propertyAltraNumerazioneRegistro;
	}

	public String getPropertyAltraNumerazioneData() {
		return propertyAltraNumerazioneData;
	}

	public void setPropertyAltraNumerazioneData(String propertyAltraNumerazioneData) {
		this.propertyAltraNumerazioneData = propertyAltraNumerazioneData;
	}

	public String getPropertyAltraNumerazioneAnno() {
		return propertyAltraNumerazioneAnno;
	}

	public void setPropertyAltraNumerazioneAnno(String propertyAltraNumerazioneAnno) {
		this.propertyAltraNumerazioneAnno = propertyAltraNumerazioneAnno;
	}

	public String getPropertyAltraNumerazioneNumero() {
		return propertyAltraNumerazioneNumero;
	}

	public void setPropertyAltraNumerazioneNumero(String propertyAltraNumerazioneNumero) {
		this.propertyAltraNumerazioneNumero = propertyAltraNumerazioneNumero;
	}

	public String getPropertyDescrizione() {
		return propertyDescrizione;
	}

	public void setPropertyDescrizione(String propertyDescrizione) {
		this.propertyDescrizione = propertyDescrizione;
	}

	public String getPropertyProtRicevutoRifOriginale() {
		return propertyProtRicevutoRifOriginale;
	}

	public void setPropertyProtRicevutoRifOriginale(String propertyProtRicevutoRifOriginale) {
		this.propertyProtRicevutoRifOriginale = propertyProtRicevutoRifOriginale;
	}

	public String getPropertyProtRicevutoNumero() {
		return propertyProtRicevutoNumero;
	}

	public void setPropertyProtRicevutoNumero(String propertyProtRicevutoNumero) {
		this.propertyProtRicevutoNumero = propertyProtRicevutoNumero;
	}

	public String getPropertyProtRicevutoAnno() {
		return propertyProtRicevutoAnno;
	}

	public void setPropertyProtRicevutoAnno(String propertyProtRicevutoAnno) {
		this.propertyProtRicevutoAnno = propertyProtRicevutoAnno;
	}

	public String getPropertyAssegnatari() {
		return propertyAssegnatari;
	}

	public void setPropertyAssegnatari(String propertyAssegnatari) {
		this.propertyAssegnatari = propertyAssegnatari;
	}

	public String getPropertyInConoscenzaA() {
		return propertyInConoscenzaA;
	}

	public void setPropertyInConoscenzaA(String propertyInConoscenzaA) {
		this.propertyInConoscenzaA = propertyInConoscenzaA;
	}

	public String getPropertyClassificazione() {
		return propertyClassificazione;
	}

	public void setPropertyClassificazione(String propertyClassificazione) {
		this.propertyClassificazione = propertyClassificazione;
	}

	public String getPropertyRiservatezza() {
		return propertyRiservatezza;
	}

	public void setPropertyRiservatezza(String propertyRiservatezza) {
		this.propertyRiservatezza = propertyRiservatezza;
	}

	public String getPropertyTermineRiservatezza() {
		return propertyTermineRiservatezza;
	}

	public void setPropertyTermineRiservatezza(String propertyTermineRiservatezza) {
		this.propertyTermineRiservatezza = propertyTermineRiservatezza;
	}

	public String getPropertyTipologia() {
		return propertyTipologia;
	}

	public void setPropertyTipologia(String propertyTipologia) {
		this.propertyTipologia = propertyTipologia;
	}

	public String getPropertyDataDocumento() {
		return propertyDataDocumento;
	}

	public void setPropertyDataDocumento(String propertyDataDocumento) {
		this.propertyDataDocumento = propertyDataDocumento;
	}

	public String getPropertyMezzoEstremiTrasmissione() {
		return propertyMezzoEstremiTrasmissione;
	}

	public void setPropertyMezzoEstremiTrasmissione(String propertyMezzoEstremiTrasmissione) {
		this.propertyMezzoEstremiTrasmissione = propertyMezzoEstremiTrasmissione;
	}

	public String getPropertyNote() {
		return propertyNote;
	}

	public void setPropertyNote(String propertyNote) {
		this.propertyNote = propertyNote;
	}

	public String getPropertyDataOraAnnullamento() {
		return propertyDataOraAnnullamento;
	}

	public void setPropertyDataOraAnnullamento(String propertyDataOraAnnullamento) {
		this.propertyDataOraAnnullamento = propertyDataOraAnnullamento;
	}

	public String getPropertyAnnullamentoEffettuatoDa() {
		return propertyAnnullamentoEffettuatoDa;
	}

	public void setPropertyAnnullamentoEffettuatoDa(String propertyAnnullamentoEffettuatoDa) {
		this.propertyAnnullamentoEffettuatoDa = propertyAnnullamentoEffettuatoDa;
	}

	public String getPropertyMotivoAnnullamento() {
		return propertyMotivoAnnullamento;
	}

	public void setPropertyMotivoAnnullamento(String propertyMotivoAnnullamento) {
		this.propertyMotivoAnnullamento = propertyMotivoAnnullamento;
	}

	public String getPropertyAnnullamentoAutorizzatoConAtto() {
		return propertyAnnullamentoAutorizzatoConAtto;
	}

	public void setPropertyAnnullamentoAutorizzatoConAtto(String propertyAnnullamentoAutorizzatoConAtto) {
		this.propertyAnnullamentoAutorizzatoConAtto = propertyAnnullamentoAutorizzatoConAtto;
	}
	
	public String getPropertyProtRicevutoData() {
		return propertyProtRicevutoData;
	}

	public void setPropertyProtRicevutoData(String propertyProtRicevutoData) {
		this.propertyProtRicevutoData = propertyProtRicevutoData;
	}
	
	public String getPropertyNomeFile() {
		return propertyNomeFile;
	}

	public void setPropertyNomeFile(String propertyNomeFile) {
		this.propertyNomeFile = propertyNomeFile;
	}

	public String getPropertyFirmato() {
		return propertyFirmato;
	}

	public void setPropertyFirmato(String propertyFirmato) {
		this.propertyFirmato = propertyFirmato;
	}

	public String getPropertyFirmatoDa() {
		return propertyFirmatoDa;
	}

	public void setPropertyFirmatoDa(String propertyFirmatoDa) {
		this.propertyFirmatoDa = propertyFirmatoDa;
	}

	public String getPropertyAnnullato() {
		return propertyAnnullato;
	}

	public void setPropertyAnnullato(String propertyAnnullato) {
		this.propertyAnnullato = propertyAnnullato;
	}

	public String getFormatoDate() {
		return formatoDate;
	}

	public void setFormatoDate(String formatoDate) {
		this.formatoDate = formatoDate;
	}

	
	
}