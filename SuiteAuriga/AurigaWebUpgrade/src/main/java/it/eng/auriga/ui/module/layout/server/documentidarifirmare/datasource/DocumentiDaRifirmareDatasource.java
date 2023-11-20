/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DocDaRifirmareBean;
import it.eng.auriga.ui.module.layout.server.common.P7MDetector;
import it.eng.auriga.ui.module.layout.server.documentidarifirmare.datasource.bean.OperazioneMassivaDocDaRifirmareBean;
import it.eng.auriga.ui.module.layout.server.firmamultipla.bean.FirmaMassivaFilesBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCoreExtractverdoc;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

@Datasource(id="DocumentiDaRifirmareDatasource")
public class DocumentiDaRifirmareDatasource extends AbstractFetchDataSource<DocDaRifirmareBean>{

	private static Logger mLogger = Logger.getLogger(DocumentiDaRifirmareDatasource.class);
		
	@Override
	public PaginatorBean<DocDaRifirmareBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DocDaRifirmareBean lFilterBean = new DocDaRifirmareBean();
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("firmatario".equals(crit.getFieldName())) {
					lFilterBean.setFirmatario(getTextFilterValue(crit));
				}
			}			
		}
				
		TFilterFetch<DocDaRifirmareBean> lTFilterFetch = new TFilterFetch<DocDaRifirmareBean>();
		lTFilterFetch.setFilter(lFilterBean);
		
		TPagingList<DocDaRifirmareBean> lTPagingList = AurigaService.getDaoDocDaRifirmare().search(getLocale(), lAurigaLoginBean, lTFilterFetch);
		
		PaginatorBean<DocDaRifirmareBean> lPaginatorBean = new PaginatorBean<DocDaRifirmareBean>();
		lPaginatorBean.setData(lTPagingList.getData());
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? lTPagingList.getData().size() - 1 : endRow);
		lPaginatorBean.setTotalRows(lTPagingList.getData().size());
		
		return lPaginatorBean;		
	}
	
	public FileDaFirmareBean getFile(DocDaRifirmareBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();
		
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();			
		lFileDaFirmareBean.setIdFile("" + bean.getIdDoc().longValue());
		
		DmpkCoreExtractverdocBean lDmpkCoreExtractverdocBean = new DmpkCoreExtractverdocBean();
		lDmpkCoreExtractverdocBean.setCodidconnectiontokenin(token);
		lDmpkCoreExtractverdocBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		lDmpkCoreExtractverdocBean.setIddocin(new BigDecimal(lFileDaFirmareBean.getIdFile()));
		
		DmpkCoreExtractverdoc lDmpkCoreExtractverdoc = new DmpkCoreExtractverdoc();
		StoreResultBean<DmpkCoreExtractverdocBean> lStoreResultBean = lDmpkCoreExtractverdoc.execute(getLocale(), lAurigaLoginBean, lDmpkCoreExtractverdocBean);
					
		if (lStoreResultBean.getDefaultMessage()!=null){
			throw new Exception(lStoreResultBean.getErrorContext() + " - " + lStoreResultBean.getErrorCode() + " - " + lStoreResultBean.getDefaultMessage());
		}
		
		String nomeFile = lStoreResultBean.getResultBean().getDisplayfilenameverout();
		String uri = lStoreResultBean.getResultBean().getUriverout();
		String mimetype = lStoreResultBean.getResultBean().getMimetypeverout();
		String impronta = lStoreResultBean.getResultBean().getImprontaverout();
		String improntaSbustato = null;
		boolean firmaPresente = true; 
		boolean firmaValida = false;

		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(uri);
		FileExtractedOut lFileExtractedOut = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
		File lFile = lFileExtractedOut.getExtracted();
		
		BigDecimal nroProgrVer = lStoreResultBean.getResultBean().getNroprogrverio();		
		if(nroProgrVer != null && bean.getNroVerLast() != null && nroProgrVer.intValue() == bean.getNroVerLast().intValue()) {					
			if (nomeFile.toLowerCase().endsWith(".p7m")){
				nomeFile = nomeFile.substring(0, nomeFile.length()-4);
			} 
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream lInputStream = lInfoFileUtility.sbusta(lFile, nomeFile);					
			uri = StorageImplementation.getStorage().storeStream(lInputStream);			
			File lRealFile = StorageImplementation.getStorage().getRealFile(uri);
			impronta = lInfoFileUtility.calcolaImpronta(lRealFile.toURI().toString(), nomeFile);			
			firmaPresente = false; 
			firmaValida = false; 
		} else {
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
			//Calcolo l'impronta del file sbustato che mi serve nel caso di firma congiunta				
			try {						
				P7MDetector lP7MDetector= new P7MDetector();
				byte[] hash = null;		
				if(lDocumentConfiguration.getAlgoritmo().equals(DigestAlgID.SHA_256)) {
					hash = DigestUtils.sha256(lP7MDetector.getContent(lFile));
				} else {
					hash = DigestUtils.sha(lP7MDetector.getContent(lFile));
				}
				String encodedHash = null;
				if(lDocumentConfiguration.getEncoding().equals(DigestEncID.BASE_64)) {
					encodedHash = Base64.encodeBase64String(hash);
				} else if(lDocumentConfiguration.getEncoding().equals(DigestEncID.HEX)) {
					encodedHash = Hex.encodeHexString(hash);
				}
				improntaSbustato = encodedHash;					
			} catch(Exception e) {
				mLogger.warn(e);
			}			
			firmaPresente = true; 
			firmaValida = true;		
		}
		
		lFileDaFirmareBean.setNomeFile(nomeFile);			
		lFileDaFirmareBean.setUri(uri);
		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setMimetype(mimetype);
		lMimeTypeFirmaBean.setImpronta(impronta);		
		lMimeTypeFirmaBean.setImprontaSbustato(improntaSbustato);
		lMimeTypeFirmaBean.setFirmato(firmaPresente);
		lMimeTypeFirmaBean.setFirmaValida(firmaValida);
		lFileDaFirmareBean.setInfoFile(lMimeTypeFirmaBean);
				
		return lFileDaFirmareBean;
	}

	public FirmaMassivaFilesBean getListaPerFirmaMassiva(OperazioneMassivaDocDaRifirmareBean bean) throws Exception {
		
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListFiles = new ArrayList<FileDaFirmareBean>();
		
		for(int i = 0; i < bean.getListaRecord().size(); i++) {
			
			DocDaRifirmareBean lDocDaRifirmareBean = bean.getListaRecord().get(i);
			
			try {
				
				FileDaFirmareBean lFileDaFirmareBean = getFile(lDocDaRifirmareBean);
				lListFiles.add(lFileDaFirmareBean);
				
			} catch(Exception e) {
				mLogger.error(e.getMessage());
				if(bean.getListaRecord().size() == 1) {
					throw new Exception(e.getMessage());
				}
			}					
			
		}	
		
		if(bean.getListaRecord().size() > 1) {
			if(lListFiles.size() == 0) {
				throw new Exception("Tutti i documenti selezionati per la firma massiva sono andati in errore");
			} else if(bean.getListaRecord().size() > lListFiles.size()) {			
				addMessage("Alcuni dei documenti selezionati per la firma massiva sono andati in errore", "", MessageType.WARNING);
			}
		}
		
		lFirmaMassivaFilesBean.setFiles(lListFiles);
		
		return lFirmaMassivaFilesBean;
	}
	
	public FirmaMassivaFilesBean verificaDocumentoFirmato(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception{
		
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		
		List<FileDaFirmareBean> filesInError = new ArrayList<FileDaFirmareBean>();		
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()){
			try {
				if(!lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
					lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");			
				}				
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				if (!firmaEseguita || !lFileDaFirmareBean.getInfoFile().isFirmato() || !lFileDaFirmareBean.getInfoFile().isFirmaValida()){ //non rifaccio il controllo con FileOp perchè viene già fatto in AppletDatasource al ritorno dall'applet di firma
					throw new Exception("Firma non presente o non valida");
				}	
			} catch (Exception e) {								
				filesInError.add(lFileDaFirmareBean);
			}
		}
		
		lFirmaMassivaFilesBean.setFiles(filesInError);
		
		return lFirmaMassivaFilesBean;
	}
	
	public FirmaMassivaFilesBean aggiornaDocumentoFirmato(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception{
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		
		List<FileDaFirmareBean> filesInError = new ArrayList<FileDaFirmareBean>();		
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()){
			try {
				if(!lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
					lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");			
				}				
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				if (!firmaEseguita || !lFileDaFirmareBean.getInfoFile().isFirmato() || !lFileDaFirmareBean.getInfoFile().isFirmaValida()){ //non rifaccio il controllo con FileOp perchè viene già fatto in AppletDatasource al ritorno dall'applet di firma
					throw new Exception("Firma non presente o non valida");
				}
				versionaDocumento(lFileDaFirmareBean);		
				DocDaRifirmareBean lDocDaRifirmareBean = new DocDaRifirmareBean();
				lDocDaRifirmareBean.setIdDoc(new BigDecimal(lFileDaFirmareBean.getIdFile()));
				lDocDaRifirmareBean.setFirmatario(pFirmaMassivaFilesBean.getCommonName());
				lDocDaRifirmareBean = AurigaService.getDaoDocDaRifirmare().get(getLocale(), lAurigaLoginBean, lDocDaRifirmareBean);
				lDocDaRifirmareBean.setTsFirmaApposta(new Date());
				AurigaService.getDaoDocDaRifirmare().update(getLocale(), lAurigaLoginBean, lDocDaRifirmareBean);
			} catch (Exception e) {								
				filesInError.add(lFileDaFirmareBean);
			}
		}
		
		lFirmaMassivaFilesBean.setFiles(filesInError);
		
		return lFirmaMassivaFilesBean;
	}
	
	private void versionaDocumento(IdFileBean bean) throws Exception {	
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));				
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}		
		FileInfoBean lFileInfoBean = new FileInfoBean();		
		GenericFile lGenericFile = new GenericFile();
		if (lMimeTypeFirmaBean.getFirmatari()!=null){
			List<Firmatario> lList = new ArrayList<Firmatario>();
			for (String lString : lMimeTypeFirmaBean.getFirmatari()){
				Firmatario lFirmatario = new Firmatario();
				lFirmatario.setCommonName(lString);
				lList.add(lFirmatario);
			}
			lGenericFile.setFirmatari(lList);
			lGenericFile.setFirmato(Flag.SETTED);
		} else if (lMimeTypeFirmaBean.isFirmato()) {
			lGenericFile.setFirmato(Flag.SETTED);
		}
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFile());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		if (lMimeTypeFirmaBean.isDaScansione()){
			lGenericFile.setDaScansione(Flag.SETTED);
			lGenericFile.setDataScansione(new Date());
			lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser()+"");
		}  
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);		
		lRebuildedFile.setInfo(lFileInfoBean);
		
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 
		
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);	
		if (lVersionaDocumentoOutBean.getDefaultMessage()!=null){
			throw new Exception(lVersionaDocumentoOutBean.getErrorContext() + " - " + lVersionaDocumentoOutBean.getErrorCode() + " - " + lVersionaDocumentoOutBean.getDefaultMessage());
		}		
	}	
	
}
