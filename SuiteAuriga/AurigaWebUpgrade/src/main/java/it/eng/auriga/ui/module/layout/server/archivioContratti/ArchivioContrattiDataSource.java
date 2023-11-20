/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil.TipoFiltro;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.archivioContratti.bean.ArchivioContrattiBean;
import it.eng.auriga.ui.module.layout.server.archivioContratti.bean.ContraenteBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.AurigaService;
import it.eng.client.GestioneContratti;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AttachAndPosizioneBean;
import it.eng.document.function.bean.ContraentiOutBean;
import it.eng.document.function.bean.CreaModContrattoInBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.SoggettoEsternoBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FormatiAmmessiUtil;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ArchivioContrattiDataSource")
public class ArchivioContrattiDataSource extends AbstractFetchDataSource<ArchivioContrattiBean>{
	
	@Override
	public PaginatorBean<ArchivioContrattiBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String filtroFullText = null;
		String[] checkAttributes = null;	
		Integer searchAllTerms = null;
		String advancedFilters = null;
		String customFilters = null;
		
		String nroProtDa = null;
		String nroProtA = null;
		String annoProtDa = null;
		String annoProtA = null;
		Date dataProtDa = null;
		Date dataProtA = null;
		
		String colsToReturn = "2,8,9,18,219,223,224,DATA_STIPULA,NRO_CONTRATTO";
		
		List<ArchivioContrattiBean> data = new ArrayList<ArchivioContrattiBean>();
		
		List<CriteriPersonalizzati> listCustomFilters = new ArrayList<CriteriPersonalizzati>(); 

		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();		
		scCriteriAvanzati.setNomeDocType("Contratto");	

		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("searchFulltext".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");												
						checkAttributes = new String[] {"#FILE"};
						String operator = crit.getOperator();
						if(StringUtils.isNotBlank(operator)) {
							if("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							} 
						}
					}
				} 
				else if("contraente".equals(crit.getFieldName())) {
					scCriteriAvanzati.setContraenteUD(getTextFilterValue(crit));
				}
				else if("pIvaCfContraente".equals(crit.getFieldName())) {
					scCriteriAvanzati.setpIvaCfContraenteUD(getTextFilterValue(crit));
				}
				else if("nroProtocollo".equals(crit.getFieldName())) {
					String[] estremiNroProt = getNumberFilterValue(crit);
					nroProtDa = estremiNroProt[0];
					nroProtA  = estremiNroProt[1];					
				} 				
				else if("dataProtocollo".equals(crit.getFieldName())) {
					Date[] estremiDataProt = getDateFilterValue(crit);
					dataProtDa = estremiDataProt[0];
					dataProtA  = estremiDataProt[1];					
				}
				else if("annoProtocollo".equals(crit.getFieldName())) {
					String[] estremiAnnoProt = getNumberFilterValue(crit);
					annoProtDa = estremiAnnoProt[0];
					annoProtA  = estremiAnnoProt[1];
				} 											
				else if("oggetto".equals(crit.getFieldName())) {
					scCriteriAvanzati.setOggettoUD(getTextFilterValue(crit));
				}
				else if("dataStipulaContratto".equals(crit.getFieldName())) {		
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("DATA_STIPULA", crit, TipoFiltro.DATA_SENZA_ORA)); 					
				}
				else if("nroContratto".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("NRO_CONTRATTO", crit));
				}							
			}
		}       
		
		 List<Registrazione> listaRegistrazioni = new ArrayList<Registrazione>(); 
	    
		 if(StringUtils.isNotBlank(nroProtDa) || StringUtils.isNotBlank(nroProtA) || StringUtils.isNotBlank(annoProtDa) || StringUtils.isNotBlank(annoProtA) || dataProtDa != null || dataProtA != null) {			 
        	Registrazione registrazione = new Registrazione();
        	registrazione.setCategoria("PG");
        	registrazione.setSigla(null);
        	registrazione.setAnno(null);
        	registrazione.setNumeroDa(nroProtDa);
        	registrazione.setNumeroA(nroProtA);
        	registrazione.setDataDa(dataProtDa);
        	registrazione.setDataA(dataProtA);
        	registrazione.setAnnoDa(annoProtDa);
        	registrazione.setAnnoA(annoProtA);    
	        listaRegistrazioni.add(registrazione);   
		}
		 
		scCriteriAvanzati.setRegistrazioni(listaRegistrazioni);  
	        
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);  
		
		List<CriteriPersonalizzati> listCustomFiltersSkipNullValues = new ArrayList<CriteriPersonalizzati>();
		for(int i = 0; i < listCustomFilters.size(); i++) {
			if(listCustomFilters.get(i) != null) {
				listCustomFiltersSkipNullValues.add(listCustomFilters.get(i));
			}
		}
		customFilters = lXmlUtilitySerializer.bindXmlList(listCustomFiltersSkipNullValues);
		
		if(StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);			
		} else {

			FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();	
			lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
			lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
			lFindRepositoryObjectBean.setFormatoEstremiReg(null);
			lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);
			lFindRepositoryObjectBean.setFlgUdFolder("U");
			lFindRepositoryObjectBean.setIdFolderSearchIn(null);		
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(null);
			lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
			lFindRepositoryObjectBean.setCustomFilters(customFilters);
			lFindRepositoryObjectBean.setColsOrderBy(null);
			lFindRepositoryObjectBean.setFlgDescOrderBy(null);
			lFindRepositoryObjectBean.setFlgSenzaPaginazione(new Integer(1));
			lFindRepositoryObjectBean.setNumPagina(null);
			lFindRepositoryObjectBean.setNumRighePagina(null);
			lFindRepositoryObjectBean.setOnline(null);
			lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
			lFindRepositoryObjectBean.setPercorsoRicerca(null);
			lFindRepositoryObjectBean.setFlagTipoRicerca(null);
			lFindRepositoryObjectBean.setFinalita(null);
			lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());			
			
			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(
						getLocale(), 
						loginBean, 
						lFindRepositoryObjectBean
				).getList();
			} catch(Exception vhe) {
				throw new StoreException(vhe.getMessage());
			}

			String xmlResultSetOut = (String) resFinder.get(0);			
			String errorMessageOut = null;			
			if (resFinder.size() > 5){ 
				errorMessageOut = (String) resFinder.get(5);
			}

			if(errorMessageOut != null && !"".equals(errorMessageOut)) {
				addMessage(errorMessageOut, "", MessageType.WARNING);
			}

			if (xmlResultSetOut != null){
				StringReader sr = new StringReader(xmlResultSetOut);
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				if(lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) 
					{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
						ArchivioContrattiBean bean = new ArchivioContrattiBean();	        		
						bean.setIdUd(v.get(1) != null ? new BigDecimal(v.get(1)) : null);                                               //col. 2   - Id. unità documentaria
						bean.setEstremiProtocollo(v.get(7));   																			//col. 8   - Estremi di registrazione a Prot. Gen. 
						bean.setDataProtocollo(v.get(8) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(8)) : null); 			//col. 9   - Data di protocollazione (è un data e ora, ma mostriamo solo la data)  
						bean.setDataStipulaContratto(v.get(100) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(100)) : null); //col. 101 - Data stipula del contratto 
						bean.setNroContratto(v.get(101));                                                                            	//col. 102 - Numero contratto 
						bean.setOggetto(v.get(17));   																					//col. 18  - Oggetto/descrizione
						bean.setContraente(v.get(222));   																				//col. 223 - Contraente
						bean.setpIvaCfContraente(v.get(223));   																		//col. 224 - CF/PIVA del contraente
						bean.setStatoConservazione(v.get(218));
						
						if (v.get(218)!=null && v.get(218).equalsIgnoreCase("inviato in CS")){
							bean.setInvioInConservazione(false);	
						}
						else{
							bean.setInvioInConservazione(true);
						}
							
							
						data.add(bean);
					}
				}							
			}			
		}		
		
		PaginatorBean<ArchivioContrattiBean> lPaginatorBean = new PaginatorBean<ArchivioContrattiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;		
	}
	
	@Override
	public ArchivioContrattiBean get(ArchivioContrattiBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(bean.getIdUd());
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
			
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
		lProtocolloUtility.recuperaFilesFromDoc(lDocumentoXmlOutBean, lProtocollazioneBean, null);

		ArchivioContrattiBean lArchivioContrattiBean = new ArchivioContrattiBean();

		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lArchivioContrattiBean, bean);
		
		ContraenteBean contraenteBean;
		List<ContraenteBean> listaContraenti = new ArrayList<ContraenteBean>();
		
		if(lDocumentoXmlOutBean.getContraenti() != null){
		
			for (ContraentiOutBean contraenteXmlOutBean : lDocumentoXmlOutBean.getContraenti()) {
				
				contraenteBean = new ContraenteBean();
				contraenteBean.setDenominazione(contraenteXmlOutBean.getDenominazione());
				contraenteBean.setCodiceFiscale(contraenteXmlOutBean.getCodiceFiscale());
				contraenteBean.setPartitaIva(contraenteXmlOutBean.getPartitaIVA());
				
				listaContraenti.add(contraenteBean);
				
			}
		}
		
		lArchivioContrattiBean.setIdDocPrimario(lProtocollazioneBean.getIdDocPrimario());
		lArchivioContrattiBean.setNomeFilePrimario(lProtocollazioneBean.getNomeFilePrimario());
		lArchivioContrattiBean.setUriFilePrimario(lProtocollazioneBean.getUriFilePrimario());
		lArchivioContrattiBean.setRemoteUriFilePrimario(lProtocollazioneBean.getRemoteUriFilePrimario());
		lArchivioContrattiBean.setInfoFile(lProtocollazioneBean.getInfoFile());		
		lArchivioContrattiBean.setListaAllegati(lProtocollazioneBean.getListaAllegati());
		lArchivioContrattiBean.setStatoConservazione(lDocumentoXmlOutBean.getStatoConservazione());
		lArchivioContrattiBean.setErroreInInvio(lDocumentoXmlOutBean.getErroreInInvio());
		lArchivioContrattiBean.setDataInvioInConservazione(lDocumentoXmlOutBean.getDataInvioInConservazione());
		lArchivioContrattiBean.setModificaDati(lDocumentoXmlOutBean.getAbilModificaDati());
		lArchivioContrattiBean.setInvioInConservazione(lDocumentoXmlOutBean.getAbilInvioInConservazione());
		lArchivioContrattiBean.setListaContraenti(listaContraenti);
		if(lDocumentoXmlOutBean.getEstremiRegistrazione().getTipo().equalsIgnoreCase("A")){
			lArchivioContrattiBean.setSiglaProtocollo(lDocumentoXmlOutBean.getEstremiRegistrazione().getSigla());
			lArchivioContrattiBean.setAnnoProtocollo(lDocumentoXmlOutBean.getEstremiRegistrazione().getAnno());
			lArchivioContrattiBean.setNroProtocollo(lDocumentoXmlOutBean.getEstremiRegistrazione().getNro());
			lArchivioContrattiBean.setDataProtocollo(lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione());
		}
		return lArchivioContrattiBean;
	}
	
	@Override
	public ArchivioContrattiBean add(ArchivioContrattiBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		FormatiAmmessiUtil lFormatiAmmessiUtil = new FormatiAmmessiUtil();		
		
		boolean formatiNonAmmessi = false;
		boolean primarioNonAmmesso = false;		
		List<String> fileConFormatiNonAmmessi = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())){
			if (bean.getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), bean.getInfoFile())) {
				primarioNonAmmesso = true;
			}			
		}
		
		if (bean.getListaAllegati()!=null){
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()){
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())){
					if (allegato.getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), allegato.getInfoFile())) {
						formatiNonAmmessi = true;
						fileConFormatiNonAmmessi.add(allegato.getNomeFileAllegato());
					}					
				}
			}
		}
		
		if (primarioNonAmmesso || formatiNonAmmessi){
			StringBuilder msg = new StringBuilder();
			if (primarioNonAmmesso && !formatiNonAmmessi){
				msg.append("Attenzione! Il file primario risulta in un formato non ammesso: Impossibile archiviare.");
			} else if (primarioNonAmmesso && formatiNonAmmessi){
				msg.append("Attenzione! Il file primario ");
				if (fileConFormatiNonAmmessi.size() == 1) msg.append("e l'allegato " + fileConFormatiNonAmmessi.get(0));
				else {
					msg.append("e gli allegati ");
					boolean first = true;
					for (String lString : fileConFormatiNonAmmessi){
						if (first) first = !first;
						else msg.append(", ");
						msg.append(lString);
					} 
				}
				msg.append(" risultano in un formato non ammesso: Impossibile archiviare.");
			} else if (!primarioNonAmmesso && formatiNonAmmessi){
				if (fileConFormatiNonAmmessi.size()==1)
					msg.append("Attenzione! L'allegato " + fileConFormatiNonAmmessi.get(0) + " risulta in un formato non ammesso: Impossibile archiviare.");
				else {
					msg.append("Attenzione! Gli allegati ");
					boolean first = true;
					for (String lString : fileConFormatiNonAmmessi){
						if (first) first = !first;
						else msg.append(" - ");
						msg.append(lString);
					} 
					msg.append(" risultano in un formato non ammesso: Impossibile archiviare.");
				}
			}
			throw new StoreException(msg.toString());
		}
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");

		CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
		
		// Salvo l'OGGETTO
		lCreaDocumentoInBean.setOggetto(bean.getOggetto());
		
		// Salvo i file ALLEGATI
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();

		AllegatiBean lAllegatiBean = null;

		if (bean.getListaAllegati()!=null){
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> infos = new ArrayList<FileInfoBean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			int i = 1;
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()){
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				if (allegato.getUriFileAllegato()!=null  && StringUtils.isNotEmpty(allegato.getUriFileAllegato())){
					isNull.add(false);
					File lFile = null;
					if (allegato.getRemoteUri()){
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						//File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					fileAllegati.add(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

					if (lMimeTypeFirmaBean.isDaScansione()){
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser()+"");
					}  
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					infos.add(lFileInfoBean);
				} else{
					infos.add(new FileInfoBean());
					isNull.add(true);
				}
				if (StringUtils.isNotEmpty(allegato.getIdAttachEmail())){
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				i++;
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(infos);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
		}
				
		// Salvo il file PRIMARIO
		FilePrimarioBean lFilePrimarioBean = null;
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())){
			lFilePrimarioBean = new FilePrimarioBean();
			File lFile = null;
			//Il file è esterno
			if (bean.getRemoteUriFilePrimario()){
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFilePrimario());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
				lFile = out.getExtracted();
			} else {
				//File locale
				lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
			}
			lFilePrimarioBean.setFile(lFile);
			MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
			if (lMimeTypeFirmaBean.isDaScansione()){
				lGenericFile.setDaScansione(Flag.SETTED);
				lGenericFile.setDataScansione(new Date());
				lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser()+"");
			}  

			lFileInfoBean.setAllegatoRiferimento(lGenericFile);
			lFilePrimarioBean.setInfo(lFileInfoBean);
			if (StringUtils.isNotEmpty(bean.getIdAttachEmailPrimario())){
				AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
				lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
				lAttachAndPosizioneBean.setPosizione(0);
				list.add(lAttachAndPosizioneBean);
			}
		}
		
		CreaModContrattoInBean creaContratto = new CreaModContrattoInBean();	
		
		creaContratto.setNroContrattoDoc(bean.getNroContratto());
		
		String dataStipulaDoc = new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataStipulaContratto());		
		creaContratto.setDataStipulaDoc(new SimpleDateFormat("dd/MM/yyyy").parse(dataStipulaDoc));
		
		creaContratto.setOggetto(bean.getOggetto());
		
		List<SoggettoEsternoBean> listaSoggettoEsterno = new ArrayList<SoggettoEsternoBean>();
		
		if(bean.getListaContraenti() != null){
		
			for (ContraenteBean contraente : bean.getListaContraenti()) {
				
				SoggettoEsternoBean soggettoEsterno = new SoggettoEsternoBean();				
				soggettoEsterno.setDenominazioneCognome(contraente.getDenominazione());
				soggettoEsterno.setCodFiscale(contraente.getCodiceFiscale());
				soggettoEsterno.setPartitaIva(contraente.getPartitaIva());
				soggettoEsterno.setCodNaturaRel("CO");
				
				listaSoggettoEsterno.add(soggettoEsterno);
				
			}
		}
		
		creaContratto.setAltriSoggettiEsterni(listaSoggettoEsterno);				
		
		List<RegistroEmergenza> listaRegistrazioneData = new ArrayList<RegistroEmergenza>();
		
		if(bean.getNroProtocollo() != null || StringUtils.isNotBlank(bean.getAnnoProtocollo()) || bean.getDataProtocollo() != null) {
			
			RegistroEmergenza registrazioneData = new RegistroEmergenza();
			registrazioneData.setFisso("A");
			registrazioneData.setRegistro(StringUtils.isNotBlank(bean.getSiglaProtocollo()) ? bean.getSiglaProtocollo() : "PROT");			
			registrazioneData.setNro(bean.getNroProtocollo().toString());
			registrazioneData.setAnno(bean.getAnnoProtocollo());
			registrazioneData.setDataRegistrazione(bean.getDataProtocollo());
			
			listaRegistrazioneData.add(registrazioneData);
			
		} 
		
		creaContratto.setRegistroEmergenza(listaRegistrazioneData);
		
		creaContratto.setNomeDocType("Contratto");
		
		GestioneDocumenti gestioneContratti = new GestioneDocumenti();
		CreaModDocumentoOutBean creaDocumentoOutBean = gestioneContratti.creadocumento(getLocale(), lAurigaLoginBean, creaContratto, lFilePrimarioBean, lAllegatiBean);

		if (creaDocumentoOutBean.getDefaultMessage()!=null){
			throw new StoreException(creaDocumentoOutBean);
		} 
		
		bean.setIdUd(creaDocumentoOutBean.getIdUd());
		
		addMessage("Contratto archiviato con successo", "", MessageType.INFO);

		return bean;
		
	}
	
	@Override
	public ArchivioContrattiBean update(ArchivioContrattiBean bean, ArchivioContrattiBean oldvalue) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		FormatiAmmessiUtil lFormatiAmmessiUtil = new FormatiAmmessiUtil();		
		
		boolean formatiNonAmmessi = false;
		boolean primarioNonAmmesso = false;
		List<String> fileConFormatiNonAmmessi = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())){
			if (bean.getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), bean.getInfoFile())) {
				primarioNonAmmesso = true;
			}
		}
		
		if (bean.getListaAllegati()!=null){
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()){
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())){
					if (allegato.getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), allegato.getInfoFile())) {
						formatiNonAmmessi = true;
						fileConFormatiNonAmmessi.add(allegato.getNomeFileAllegato());
					}
				}
			}
		}
		
		if (primarioNonAmmesso || formatiNonAmmessi){
			StringBuilder msg = new StringBuilder();
			if (primarioNonAmmesso && !formatiNonAmmessi){
				msg.append("Attenzione! Il file primario risulta in un formato non ammesso: Impossibile archiviare.");
			} else if (primarioNonAmmesso && formatiNonAmmessi){
				msg.append("Attenzione! Il file primario ");
				if (fileConFormatiNonAmmessi.size() == 1) msg.append("e l'allegato " + fileConFormatiNonAmmessi.get(0));
				else {
					msg.append("e gli allegati ");
					boolean first = true;
					for (String lString : fileConFormatiNonAmmessi){
						if (first) first = !first;
						else msg.append(", ");
						msg.append(lString);
					} 
				}
				msg.append(" risultano in un formato non ammesso: Impossibile archiviare.");
			} else if (!primarioNonAmmesso && formatiNonAmmessi){
				if (fileConFormatiNonAmmessi.size()==1)
					msg.append("Attenzione! L'allegato " + fileConFormatiNonAmmessi.get(0) + " risulta in un formato non ammesso: Impossibile archiviare.");
				else {
					msg.append("Attenzione! Gli allegati ");
					boolean first = true;
					for (String lString : fileConFormatiNonAmmessi){
						if (first) first = !first;
						else msg.append(" - ");
						msg.append(lString);
					} 
					msg.append(" risultano in un formato non ammesso: Impossibile archiviare.");
				}
			}
			throw new StoreException(msg.toString());
		}
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		
		// Salvo i file ALLEGATI
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();

		AllegatiBean lAllegatiBean = null;

		if (bean.getListaAllegati()!=null){
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> infos = new ArrayList<FileInfoBean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			int i = 1;
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()){
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				if (allegato.getUriFileAllegato()!=null  && StringUtils.isNotEmpty(allegato.getUriFileAllegato())){
					isNull.add(false);
					File lFile = null;
					if (allegato.getRemoteUri()){
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						//File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					fileAllegati.add(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

					if (lMimeTypeFirmaBean.isDaScansione()){
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser()+"");
					}  
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					infos.add(lFileInfoBean);
				} else{
					infos.add(new FileInfoBean());
					isNull.add(true);
				}
				if (StringUtils.isNotEmpty(allegato.getIdAttachEmail())){
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				i++;
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(infos);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
		}
				
		// Salvo il file PRIMARIO
		FilePrimarioBean lFilePrimarioBean = null;
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())){
			lFilePrimarioBean = new FilePrimarioBean();
			File lFile = null;
			//Il file è esterno
			if (bean.getRemoteUriFilePrimario()){
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFilePrimario());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
				lFile = out.getExtracted();
			} else {
				//File locale
				lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
			}
			lFilePrimarioBean.setFile(lFile);
			MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
			if (lMimeTypeFirmaBean.isDaScansione()){
				lGenericFile.setDaScansione(Flag.SETTED);
				lGenericFile.setDataScansione(new Date());
				lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser()+"");
			}  

			lFileInfoBean.setAllegatoRiferimento(lGenericFile);
			lFilePrimarioBean.setInfo(lFileInfoBean);
			if (StringUtils.isNotEmpty(bean.getIdAttachEmailPrimario())){
				AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
				lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
				lAttachAndPosizioneBean.setPosizione(0);
				list.add(lAttachAndPosizioneBean);
			}
		}
		
		lFilePrimarioBean.setIdDocumento(bean.getIdDocPrimario() != null ? bean.getIdDocPrimario() : null );
		
		lFilePrimarioBean.setIsNewOrChanged(bean.getIdDocPrimario() == null || bean.getIsDocPrimarioChanged());
		CreaModContrattoInBean modificaContratto = new CreaModContrattoInBean();	
		
		modificaContratto.setNroContrattoDoc(bean.getNroContratto());
		
		String dataStipulaDoc = new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataStipulaContratto());		
		modificaContratto.setDataStipulaDoc(new SimpleDateFormat("dd/MM/yyyy").parse(dataStipulaDoc));
		
		modificaContratto.setOggetto(bean.getOggetto());
		
		modificaContratto.setFlgTipoProv(TipoProvenienza.INTERNA);
		
		List<SoggettoEsternoBean> listaSoggettoEsterno = new ArrayList<SoggettoEsternoBean>();
		
		if(bean.getListaContraenti() != null){
		
			for (ContraenteBean contraente : bean.getListaContraenti()) {
				
				SoggettoEsternoBean soggettoEsterno = new SoggettoEsternoBean();
				
				soggettoEsterno.setDenominazioneCognome(contraente.getDenominazione());
				soggettoEsterno.setCodFiscale(contraente.getCodiceFiscale());
				soggettoEsterno.setPartitaIva(contraente.getPartitaIva());
				soggettoEsterno.setCodNaturaRel("CO");
				
				listaSoggettoEsterno.add(soggettoEsterno);
				
			}
		}
		
		modificaContratto.setAltriSoggettiEsterni(listaSoggettoEsterno);
		
		List<RegistroEmergenza> listaRegistrazioneData = new ArrayList<RegistroEmergenza>();
		
		if(bean.getNroProtocollo() != null || StringUtils.isNotBlank(bean.getAnnoProtocollo()) || bean.getDataProtocollo() != null) {
			
			RegistroEmergenza registrazioneData = new RegistroEmergenza();
			registrazioneData.setFisso("A");
			registrazioneData.setRegistro(StringUtils.isNotBlank(bean.getSiglaProtocollo()) ? bean.getSiglaProtocollo() : "PROT");			
			registrazioneData.setNro(bean.getNroProtocollo().toString());
			registrazioneData.setAnno(bean.getAnnoProtocollo() != null ? bean.getAnnoProtocollo() : "" );
			registrazioneData.setDataRegistrazione(bean.getDataProtocollo());
			
			listaRegistrazioneData.add(registrazioneData);
			
		} 
		
		// se la lista è vuota in modifica non passo nulla altrimenti annullo le registrazioni precedenti
		if(listaRegistrazioneData.size() > 0) {
			modificaContratto.setRegistroEmergenza(listaRegistrazioneData);
		}
		
		modificaContratto.setNomeDocType("Contratto");
		
		GestioneDocumenti gestioneContratti = new GestioneDocumenti();
		CreaModDocumentoOutBean creaDocumentoOutBean = gestioneContratti.modificadocumento(getLocale(), lAurigaLoginBean, bean.getIdUd(), bean.getIdDocPrimario(), modificaContratto, lFilePrimarioBean, lAllegatiBean);

		if (creaDocumentoOutBean.getDefaultMessage()!=null){
			throw new StoreException(creaDocumentoOutBean);
		} 
				
		addMessage("Contratto modificato con successo", "", MessageType.INFO);

		return bean;
		
	}
		
	@Override
	public ArchivioContrattiBean remove(ArchivioContrattiBean bean)
			throws Exception {
		
		return bean;
	}
	
	public ArchivioContrattiBean invioInConservazione(ArchivioContrattiBean contratto) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		CreaModDocumentoInBean modificaDocumentoInBean = new CreaModDocumentoInBean();
		
		Calendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = sdf.parse(sdf.format(cal.getTime()));
		
		modificaDocumentoInBean.setTsInvioInConservazione(date);
		modificaDocumentoInBean.setStatoConservazione("inviato");
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		
		FilePrimarioBean filePrimario = null;
		
		if (StringUtils.isNotEmpty(contratto.getUriFilePrimario())){
			filePrimario = new FilePrimarioBean();
			filePrimario.setFile(StorageImplementation.getStorage().extractFile(contratto.getUriFilePrimario()));
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			MimeTypeFirmaBean lMimeTypeFirmaBean = contratto.getInfoFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(contratto.getNomeFilePrimario());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
			if (lMimeTypeFirmaBean.isDaScansione()){
				lGenericFile.setDaScansione(Flag.SETTED);
				lGenericFile.setDataScansione(new Date());
				lGenericFile.setIdUserScansione(AurigaUserUtil.getLoginInfo(getSession()).getIdUser()+"");
			}  
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);
			filePrimario.setInfo(lFileInfoBean);
			filePrimario.setIsNewOrChanged(true);
			filePrimario.setIdDocumento(contratto.getIdDocPrimario());
		}
		
		List<String> lListDescrizioni = new ArrayList<String>();
		List<String> lListDisplay = new ArrayList<String>();
		List<Integer> lListDocType = new ArrayList<Integer>();
		List<File> lListFile = new ArrayList<File>();
		List<FileInfoBean> lListInfo = new ArrayList<FileInfoBean>();
		List<Boolean> lListBoolean = new ArrayList<Boolean>();
		List<Boolean> lListNull = new ArrayList<Boolean>();
		List<BigDecimal> lListIdDocs = new ArrayList<BigDecimal>();
		if (contratto.getListaAllegati()!=null){
			int i = 0;
			for (AllegatoProtocolloBean allegato : contratto.getListaAllegati()){
				lListDescrizioni.add(allegato.getDescrizioneFileAllegato());
				lListDocType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				lListDisplay.add(allegato.getNomeFileAllegato());
				lListBoolean.add(allegato.getIsChanged());
				lListIdDocs.add(allegato.getIdDocAllegato()!=null?new BigDecimal(allegato.getIdDocAllegato().longValue()):null);
				if (allegato.getUriFileAllegato()!=null  && StringUtils.isNotEmpty(allegato.getUriFileAllegato())){
					lListNull.add(false);
					File lFile = null;
					if (allegato.getRemoteUri()){
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						//File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					lListFile.add(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					// ATTENZIONE !!! Verificare se si puo' usare GenericFile() di auriga ??!!!
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

					if (lMimeTypeFirmaBean.isDaScansione()){
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(AurigaUserUtil.getLoginInfo(getSession()).getIdUser()+"");
					}  
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					lListInfo.add(lFileInfoBean);

				} else{
					lListInfo.add(new FileInfoBean());
					lListNull.add(true);
				}
				i++;
			}
		}
		
		AllegatiBean listaAllegatiOut = new AllegatiBean();
		listaAllegatiOut.setDescrizione(lListDescrizioni);
		listaAllegatiOut.setDisplayFilename(lListDisplay);
		listaAllegatiOut.setDocType(lListDocType);
		listaAllegatiOut.setIsNull(lListNull);
		listaAllegatiOut.setFileAllegati(lListFile);
		listaAllegatiOut.setInfo(lListInfo);
		listaAllegatiOut.setIdDocumento(lListIdDocs);
		listaAllegatiOut.setIsNewOrChanged(lListBoolean);
		
		CreaModDocumentoOutBean creaDocumentoOutBean = null;
		GestioneContratti gestioneContratti = new GestioneContratti();
		
		creaDocumentoOutBean = gestioneContratti.modificadocumento(getLocale(), lAurigaLoginBean, contratto.getIdUd(), contratto.getIdDocPrimario(), modificaDocumentoInBean, filePrimario, listaAllegatiOut);
		
		if (creaDocumentoOutBean.getDefaultMessage()!=null){
			throw new StoreException(creaDocumentoOutBean);
		} 
		
		return contratto;
		
	}
	
	@Override
	public Map<String, ErrorBean> validate(ArchivioContrattiBean bean)
			throws Exception {
		
		return null;
	}

}