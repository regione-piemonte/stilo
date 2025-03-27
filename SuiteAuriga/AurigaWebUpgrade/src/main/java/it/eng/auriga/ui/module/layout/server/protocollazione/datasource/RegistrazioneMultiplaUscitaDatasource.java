/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.CampoCaricamentoBean;
import it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean.XlsColumnRemapping;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ErroreRigaExcelBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.CaricamentoDestinatariExcelBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatariRegistrazioneMultiplaUscitaXmlBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatariXFileXlsRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.EsitoValidazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileXlsDestinatariRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ImportaDestinatariFromXlsRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MezzoTrasmissioneDestinatarioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.RegistrazioneMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.XmlColonneContenutiBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.client.GestioneInserimentoRichXRegMultiplaUscita;
import it.eng.document.function.bean.CreaDocWithFileBean;
import it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean;
import it.eng.document.function.bean.CreaFoglioXImportInBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.GestioneInserimentoRichXRegMultiplaUscitaInBean;
import it.eng.document.function.bean.GestioneInserimentoRichXRegMultiplaUscitaOutBean;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.FileUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "RegistrazioneMultiplaUscitaDatasource")
public class RegistrazioneMultiplaUscitaDatasource extends AbstractFetchDataSource<RegistrazioneMultiplaUscitaBean>{
	
	private static final Logger logger = Logger.getLogger(RegistrazioneMultiplaUscitaDatasource.class);
	
	public static final String _TIPO_REG_PG = "Prot. generale";
	public static final String _TIPO_REG_R = "Repertorio";
	
	public static final String _FLG_SI = "SI";
	public static final String _FLG_NO = "NO";

	@Override
	public PaginatorBean<RegistrazioneMultiplaUscitaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}
	
	@Override
	public RegistrazioneMultiplaUscitaBean add(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<CreaDocWithFileBean> listaDocRegMultiplaUscita = new ArrayList<CreaDocWithFileBean>();
		
		if(bean.getListaDestinatariDiversiXReg() != null) {
			String folderJob = ParametriDBUtil.getParametroDB(getSession(), "FOLDER_TEMP_ELABORAZIONI_FILE_REG_MULTI_USCITA");
			HashMap<Integer, List<DestinatariRegistrazioneMultiplaUscitaXmlBean>> mappaRegistrazioniDest = new LinkedHashMap<Integer, List<DestinatariRegistrazioneMultiplaUscitaXmlBean>>();
			int nroRegistrazione = 0;
			for (DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean : bean.getListaDestinatariDiversiXReg()) {
				// per tutti i destinatari che hanno colonna "stessa reg. dest. precedente" vuota corrisponde una registrazione
				// tutte le righe successive che hanno colonna "stessa reg. dest. precedente" settata a 1 sono destinatari che andranno nella stessa registrazione del primo (quello con colonna vuota)
				// leggo i file solo sulla riga del primo destinatario della registrazione (quello con colonna vuota)
				boolean isStessaRegDestPrec = lDestinatariDiversiXRegBean.getFlgStessaRegDestPrec() != null && "1".equals(lDestinatariDiversiXRegBean.getFlgStessaRegDestPrec());
				if(isStessaRegDestPrec) {
					if(mappaRegistrazioniDest.get(nroRegistrazione) != null) {
						mappaRegistrazioniDest.get(nroRegistrazione).add(lDestinatariDiversiXRegBean);
					}
				} else {
					nroRegistrazione++;
					mappaRegistrazioniDest.put(nroRegistrazione, new ArrayList<DestinatariRegistrazioneMultiplaUscitaXmlBean>());
					mappaRegistrazioniDest.get(nroRegistrazione).add(lDestinatariDiversiXRegBean);
				}
			}
			for (Integer nroProgrReg : mappaRegistrazioniDest.keySet()) {
				// Copio i dati a maschera nel bean di salvataggio
				ProtocollazioneBean lProtocollazioneBean = createProtocollazioneBeanFromRegistrazioneMultiplaUscitaBean(bean, mappaRegistrazioniDest.get(nroProgrReg), folderJob);
				CreaDocWithFileBean lCreaDocWithFileBean = getProtocolloDataSource(bean).buildCreaDocWithFileBean(lProtocollazioneBean);
				if(lProtocollazioneBean.getErroriFile() != null && !lProtocollazioneBean.getErroriFile().isEmpty()) {
					bean.setErroriFile(lProtocollazioneBean.getErroriFile());
					return bean;
				}
				listaDocRegMultiplaUscita.add(lCreaDocWithFileBean);
				
			}
		}
		
		CreaDocumentiRegMultiplaUscitaBean lCreaDocumentiRegMultiplaUscitaBean = new CreaDocumentiRegMultiplaUscitaBean();
		lCreaDocumentiRegMultiplaUscitaBean.setListaDocRegMultiplaUscita(listaDocRegMultiplaUscita);
		
//		lCreaDocumentiRegMultiplaUscitaBean = new GestioneDocumenti().creadocumentiregistrazionemultiplauscita(getLocale(), lAurigaLoginBean, lCreaDocumentiRegMultiplaUscitaBean); //TODO come salvo lCreaDocumentoInBean, lFilePrimarioBean, lAllegatiBean in DB per il job di registrazione multipla in uscita?
		
		// INIZIO NUOVA GESTIONE
		GestioneInserimentoRichXRegMultiplaUscitaInBean input = new GestioneInserimentoRichXRegMultiplaUscitaInBean();
		
		CreaFoglioXImportInBean importBean = new CreaFoglioXImportInBean();
		
		File extractFile = StorageImplementation.getStorage().extractFile(bean.getUriFileXlsDestinatariDiversiXReg());
		MimeTypeFirmaBean infoFromFile = new InfoFileUtility().getInfoFromFile(extractFile.toURI().toString(), bean.getNomeFileXlsDestinatariDiversiXReg(), false, null);
//		MimeTypeFirmaBean infoFromFile = new InfoFileUtility().getInfoFromFile(bean.getUriFileXlsDestinatariDiversiXReg(), bean.getNomeFileXlsDestinatariDiversiXReg(), false, null);
		String algoritmo = infoFromFile.getAlgoritmo();
		String encoding = infoFromFile.getEncoding();
		String impronta = infoFromFile.getImpronta();
		FileInfoBean fib = new FileInfoBean();
		GenericFile gf = new GenericFile();
		gf.setAlgoritmo(algoritmo);
		gf.setEncoding(encoding);
		gf.setImpronta(impronta);
		fib.setAllegatoRiferimento(gf);
		
		importBean.setTipoContenuto("OP_AURIGA_REG_MULTIPLA_USCITA");
		importBean.setUriFileExcel(bean.getUriFileXlsDestinatariDiversiXReg());
		importBean.setInfo(fib);
//		importBean.setFile(extractFile);
		input.setXlsXImport(importBean);
		
		XmlColonneContenutiBean data = new XmlColonneContenutiBean();
		data.setUri(bean.getUriFileXlsDestinatariDiversiXReg());
		data.setMimetype(bean.getMimeFileXlsDestinatariDiversiXReg());
		XmlColonneContenutiBean xmlFromDocumentRows = getXmlFromDocumentRows(data);
		
		input.setDettagliColonneXImportContentFoglio(xmlFromDocumentRows.getDettagliColonne());
		input.setXmlContenutiXImportContentFoglio(xmlFromDocumentRows.getXmlContenuti());
		
		input.setpCreaDocumentiRegMultiplaUscitaBean(lCreaDocumentiRegMultiplaUscitaBean);
		
		GestioneInserimentoRichXRegMultiplaUscita service = new GestioneInserimentoRichXRegMultiplaUscita();
		GestioneInserimentoRichXRegMultiplaUscitaOutBean outResult = service.creafoglioximport(getLocale(), lAurigaLoginBean, input);
		// FINE NUOVA GESTIONE
		if (outResult.getDefaultMessage() != null) {
			logger.error("RegistrazioneMultiplaUscitaDatasource - creafoglioximport: " + outResult.getDefaultMessage());
			throw new StoreException(outResult);
		}
		addMessage("Registrazione massiva in uscita effettuata con successo con id " + outResult.getIdJob(), "", MessageType.INFO);

		return bean;
	}
	
	private ProtocollazioneBean createProtocollazioneBeanFromRegistrazioneMultiplaUscitaBean(RegistrazioneMultiplaUscitaBean pRegistrazioneMultiplaUscitaBean, List<DestinatariRegistrazioneMultiplaUscitaXmlBean> pListaDestinatariDiversiXRegBean, String folderJob) throws Exception {

		if(pRegistrazioneMultiplaUscitaBean != null) {			
			
			DestinatariRegistrazioneMultiplaUscitaXmlBean lFirstDestinatariDiversiXRegBean = pListaDestinatariDiversiXRegBean.get(0);
			
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
			
			BeanUtilsBean2.getInstance().copyProperties(lProtocollazioneBean, pRegistrazioneMultiplaUscitaBean);
			
			lProtocollazioneBean.setFlgTipoProv("U");
			
			lProtocollazioneBean.setOggetto(generaOggettoWithPlaceholder(lProtocollazioneBean.getOggetto(), null, lFirstDestinatariDiversiXRegBean));
			
			// Aggiungo i valori dei tab dinamici, tutti con il suffisso _Doc				
			lProtocollazioneBean.setValori(new HashMap<String, Object>());		
			if (pRegistrazioneMultiplaUscitaBean.getValori() != null) {
				for (String attrName : pRegistrazioneMultiplaUscitaBean.getValori().keySet()) {
					lProtocollazioneBean.getValori().put(attrName + "_Doc", pRegistrazioneMultiplaUscitaBean.getValori().get(attrName));
				}
			}						
			lProtocollazioneBean.setTipiValori(new HashMap<String, String>());
			if (pRegistrazioneMultiplaUscitaBean.getTipiValori() != null) {
				for (String attrName : pRegistrazioneMultiplaUscitaBean.getTipiValori().keySet()) {
					if(!attrName.contains(".")) {
						lProtocollazioneBean.getTipiValori().put(attrName + "_Doc", pRegistrazioneMultiplaUscitaBean.getTipiValori().get(attrName));
					} else {
						// se contiene il punto è l'attributo relativo alla colonna di un attributo lista
						lProtocollazioneBean.getTipiValori().put(attrName.substring(0, attrName.indexOf(".")) + "_Doc" + attrName.substring(attrName.indexOf(".")), pRegistrazioneMultiplaUscitaBean.getTipiValori().get(attrName));
					}
				}
			}
			
			// Numerazioni da dare
			if(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla() != null) {
				List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();				
				if(_TIPO_REG_PG.equals(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla())) {					
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setCategoria("PG");
					lTipoNumerazioneBean.setSigla(null);		
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getUoProtocollante()) ? pRegistrazioneMultiplaUscitaBean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);		
				} else if(_TIPO_REG_R.equals(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla())) {
					lProtocollazioneBean.setIsRepertorio(true);
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setCategoria("R");
					lTipoNumerazioneBean.setSigla(pRegistrazioneMultiplaUscitaBean.getRepertorio());
					int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					lTipoNumerazioneBean.setAnno(String.valueOf(annoCorrente));					
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getUoProtocollante()) ? pRegistrazioneMultiplaUscitaBean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);					
				}
				lProtocollazioneBean.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
			}
			
			// Destinatari
			List<DestinatarioProtBean> listaDestinatari = new ArrayList<DestinatarioProtBean>();
			if(pListaDestinatariDiversiXRegBean != null) {
				for(DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean : pListaDestinatariDiversiXRegBean) {
//					DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
//					lDestinatarioProtBean.setTipoDestinatario("XLS");
//					lDestinatarioProtBean.setIdFoglioExcelDestinatari(pRegistrazioneMultiplaUscitaBean.getIdFoglioXlsDestinatariDiversiXReg());
//					lDestinatarioProtBean.setDisplayFileNameExcel(pRegistrazioneMultiplaUscitaBean.getNomeFileXlsDestinatariDiversiXReg());
//					listaDestinatari.add(lDestinatarioProtBean);
					listaDestinatari.add(createDestinatarioProtBeanFromDestinatariDiversiXRegBean(lDestinatariDiversiXRegBean));
				}
			}
			if(pRegistrazioneMultiplaUscitaBean.getListaDestinatari() != null) {
				listaDestinatari.addAll(pRegistrazioneMultiplaUscitaBean.getListaDestinatari());
			}
			lProtocollazioneBean.setListaDestinatari(listaDestinatari);
			
			// Mittenti
			for (MittenteProtBean mittente : lProtocollazioneBean.getListaMittenti()) {
				String casellaMittente = pRegistrazioneMultiplaUscitaBean.getCasellaMittente();
				mittente.setEmailMittente(casellaMittente);
			}
			
			String pathDirJobs = "";
			if ((pRegistrazioneMultiplaUscitaBean.getFlgFilePrincipaleUgualeXTutteReg() != null)
					|| (lFirstDestinatariDiversiXRegBean != null && StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati()))
					|| (pRegistrazioneMultiplaUscitaBean.getListaAllegati() != null && pRegistrazioneMultiplaUscitaBean.getListaAllegati().size() > 0)) {
				long currentTimeMillis = System.currentTimeMillis();
				pathDirJobs = folderJob + File.separator + currentTimeMillis;
				File dir = new File(pathDirJobs);
				if (!dir.exists()) {
					dir.mkdir();
				}
			}
			
			// File primario
			if(pRegistrazioneMultiplaUscitaBean.getFlgFilePrincipaleUgualeXTutteReg() != null) {
				if (_FLG_NO.equalsIgnoreCase(pRegistrazioneMultiplaUscitaBean.getFlgFilePrincipaleUgualeXTutteReg())) {
					if(lFirstDestinatariDiversiXRegBean != null && StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getPercorsoFilePrimari())) {					
						File filePrimario = recuperaFilePrimarioDestinatario(pRegistrazioneMultiplaUscitaBean.getPercorsoFilePrimari(), lFirstDestinatariDiversiXRegBean.getNomeFilePrimario());
						if(filePrimario != null) {
							File filePrimarioCopiato = new File(pathDirJobs + File.separator + lFirstDestinatariDiversiXRegBean.getNomeFilePrimario());
							if (!filePrimarioCopiato.exists()) {
								FileUtils.copyFile(filePrimario, filePrimarioCopiato);
							}
							lProtocollazioneBean.setFilePrimario(filePrimarioCopiato);
							lProtocollazioneBean.setPercorsoFilePrimari(pRegistrazioneMultiplaUscitaBean.getPercorsoFilePrimari());
							lProtocollazioneBean.setNomeFilePrimario(lFirstDestinatariDiversiXRegBean.getNomeFilePrimario());
							lProtocollazioneBean.setUriFilePrimario(filePrimarioCopiato.getPath()); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
//							lProtocollazioneBean.setUriFilePrimario(StorageImplementation.getStorage().storeStream(new FileInputStream(filePrimario))); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
							lProtocollazioneBean.setRemoteUriFilePrimario(false);
//							MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(filePrimario.toURI().toString(), filePrimario.getName(), false, null); //TODO non posso fare una chiamata per ogni file, se sono migliaia diventa lentissimo
//							lProtocollazioneBean.setInfoFile(lMimeTypeFirmaBean);
						}
					}
				} else {
					if (StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getUriFilePrimario())) {
						File filePrimario = StorageImplementation.getStorage().getRealFile(pRegistrazioneMultiplaUscitaBean.getUriFilePrimario());
						if (filePrimario != null) {
							File filePrimarioCopiato = new File(pathDirJobs + File.separator + "file_primario_condiviso_per_registrazioni" + File.separator + pRegistrazioneMultiplaUscitaBean.getInfoFile().getCorrectFileName());
							if (!filePrimarioCopiato.exists()) {
								FileUtils.copyFile(filePrimario, filePrimarioCopiato);
							}
							lProtocollazioneBean.setFilePrimario(filePrimarioCopiato);
							lProtocollazioneBean.setPercorsoFilePrimari(null);
							lProtocollazioneBean.setUriFilePrimario(filePrimarioCopiato.getPath());
							lProtocollazioneBean.setRemoteUriFilePrimario(false);
							lProtocollazioneBean.setInfoFile(pRegistrazioneMultiplaUscitaBean.getInfoFile()); // qui ho già calcolato l'infoFile quindi me lo passo avanti
						}
					}
				}
			}
			
			// File allegati
			List<AllegatoProtocolloBean> listaAllegati = new ArrayList<AllegatoProtocolloBean>();
			if(lFirstDestinatariDiversiXRegBean != null && StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati())) {
				if (lFirstDestinatariDiversiXRegBean.getNomiFileAllegati() != null && lFirstDestinatariDiversiXRegBean.getNomiFileAllegati().contains(";")) {
					StringSplitterServer st = new StringSplitterServer(lFirstDestinatariDiversiXRegBean.getNomiFileAllegati(), ";");
					while(st.hasMoreTokens()) {
						String nomeFileAllegato = st.nextToken().trim();
						File fileAllegato = recuperaFileAllegatoDestinatario(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati(), lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati(), nomeFileAllegato);					
						if(fileAllegato != null) {
							File fileAllegatoCopiato = lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati() != null && !"".equals(lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati()) 
									? new File(pathDirJobs + File.separator + lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati() + File.separator + nomeFileAllegato) 
									: new File(pathDirJobs + File.separator + nomeFileAllegato);
							if (!fileAllegatoCopiato.exists()) {
								FileUtils.copyFile(fileAllegato, fileAllegatoCopiato);
							}
							AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
							lAllegatoProtocolloBean.setFileAllegato(fileAllegatoCopiato);
							lAllegatoProtocolloBean.setPercorsoRelFileAllegati(lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati());
							lAllegatoProtocolloBean.setNomeFileAllegato(nomeFileAllegato);
							lAllegatoProtocolloBean.setUriFileAllegato(fileAllegatoCopiato.getPath()); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
//							lAllegatoProtocolloBean.setUriFileAllegato(StorageImplementation.getStorage().storeStream(new FileInputStream(fileAllegato))); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
							lAllegatoProtocolloBean.setRemoteUri(false);
//							MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileAllegato.toURI().toString(), fileAllegato.getName(), false, null); //TODO non posso fare una chiamata per ogni file, se sono migliaia diventa lentissimo
//							lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
							listaAllegati.add(lAllegatoProtocolloBean);						
						}
					}
				} else {
					String nomeFileAllegato = lFirstDestinatariDiversiXRegBean.getNomiFileAllegati();
					File fileAllegato = recuperaFileAllegatoDestinatario(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati(), lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati(), nomeFileAllegato);					
					if(fileAllegato != null) {
						File fileAllegatoCopiato = lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati() != null && !"".equals(lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati()) 
								? new File(pathDirJobs + File.separator + lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati() + File.separator + nomeFileAllegato) 
								: new File(pathDirJobs + File.separator + nomeFileAllegato);
						if (!fileAllegatoCopiato.exists()) {
							FileUtils.copyFile(fileAllegato, fileAllegatoCopiato);
						}
						AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
						lAllegatoProtocolloBean.setFileAllegato(fileAllegatoCopiato);
						lAllegatoProtocolloBean.setPercorsoRelFileAllegati(lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati());
						lAllegatoProtocolloBean.setNomeFileAllegato(nomeFileAllegato);
						lAllegatoProtocolloBean.setUriFileAllegato(pathDirJobs + File.separator + (StringUtils.isNotBlank(lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati()) ? lFirstDestinatariDiversiXRegBean.getPercorsoRelFileAllegati() : "")); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
//						lAllegatoProtocolloBean.setUriFileAllegato(StorageImplementation.getStorage().storeStream(new FileInputStream(fileAllegato))); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
						lAllegatoProtocolloBean.setRemoteUri(false);
//						MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileAllegato.toURI().toString(), fileAllegato.getName(), false, null); //TODO non posso fare una chiamata per ogni file, se sono migliaia diventa lentissimo
//						lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
						listaAllegati.add(lAllegatoProtocolloBean);						
					}
				}
			}
			if(pRegistrazioneMultiplaUscitaBean.getListaAllegati() != null) {
				List<AllegatoProtocolloBean> listaAllegatiComuniAlleRegistrazioni = pRegistrazioneMultiplaUscitaBean.getListaAllegati();
				for (AllegatoProtocolloBean allegato : listaAllegatiComuniAlleRegistrazioni) {
					File fileAllegato = StorageImplementation.getStorage().getRealFile(allegato.getUriFileAllegato());
					if (fileAllegato != null) {
						File fileAllegatoCopiato = new File(pathDirJobs + File.separator + "file_allegati_condivisi_per_registrazioni" + File.separator + allegato.getNomeFileAllegato());
						if (!fileAllegatoCopiato.exists()) {
							FileUtils.copyFile(fileAllegato, fileAllegatoCopiato);
						}
						AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
						lAllegatoProtocolloBean.setFileAllegato(fileAllegatoCopiato);
						lAllegatoProtocolloBean.setPercorsoRelFileAllegati(null);
						lAllegatoProtocolloBean.setNomeFileAllegato(allegato.getNomeFileAllegato());
						lAllegatoProtocolloBean.setUriFileAllegato(fileAllegatoCopiato.getPath()); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
//						lAllegatoProtocolloBean.setUriFileAllegato(StorageImplementation.getStorage().storeStream(new FileInputStream(fileAllegato))); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
						lAllegatoProtocolloBean.setRemoteUri(false);
						lAllegatoProtocolloBean.setInfoFile(allegato.getInfoFile()); // qui ho già calcolato l'infoFile quindi me lo passo avanti
						listaAllegati.add(lAllegatoProtocolloBean);		
					}
				}
//				listaAllegati.addAll(pRegistrazioneMultiplaUscitaBean.getListaAllegati());
			}
			lProtocollazioneBean.setListaAllegati(listaAllegati);
			lProtocollazioneBean.setPercorsoFileAllegati(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati());
			lProtocollazioneBean.setNumRigaInTabContFoglio(lFirstDestinatariDiversiXRegBean.getNumRigaInTabContFoglio());
			
			return lProtocollazioneBean;
		}
		
		return null;
	}
	
	private DestinatarioProtBean createDestinatarioProtBeanFromDestinatariDiversiXRegBean(DestinatariRegistrazioneMultiplaUscitaXmlBean pDestinatariDiversiXRegBean) throws Exception {
		
		if(pDestinatariDiversiXRegBean != null) {
						
			DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
			
			if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getTipo())) {
				if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.PF.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("PF");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.PG.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("PG");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.PA.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("PA");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.UOI.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("UOI");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.UP.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("UP");
				}
			}
				
			lDestinatarioProtBean.setCodRapidoDestinatario(pDestinatariDiversiXRegBean.getCodRapido());
			lDestinatarioProtBean.setDenominazioneDestinatario(pDestinatariDiversiXRegBean.getDenominazioneCognome());
			lDestinatarioProtBean.setCognomeDestinatario(pDestinatariDiversiXRegBean.getDenominazioneCognome());
			lDestinatarioProtBean.setNomeDestinatario(pDestinatariDiversiXRegBean.getNome());
			lDestinatarioProtBean.setCodfiscaleDestinatario(pDestinatariDiversiXRegBean.getCodiceFiscale());
//			lDestinatarioProtBean.setPivaDestinatario(pDestinatariDiversiXRegBean.getPiva());
			
			//TODO Per il mapping serve sapere quali sono i valori che si possono inserire nel campo "Mezzo trasmissione" dell'excel dei destinatari
			MezzoTrasmissioneDestinatarioBean lMezzoTrasmissioneDestinatarioBean = new MezzoTrasmissioneDestinatarioBean();
			if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getMezzoTrasmissione())) {
				if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.R.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("R");					
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.NM.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("NM");
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.PEC.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("PEC");
					if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getEmail())) {
						lMezzoTrasmissioneDestinatarioBean.setIndirizzoPECDestinatario(pDestinatariDiversiXRegBean.getEmail());
					}
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.PEO.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("PEO");
					if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getEmail())) {
						lMezzoTrasmissioneDestinatarioBean.setIndirizzoPEODestinatario(pDestinatariDiversiXRegBean.getEmail());
					}
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.EMAIL.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("EMAIL");
					if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getEmail())) {
						lMezzoTrasmissioneDestinatarioBean.setIndirizzoMailDestinatario(pDestinatariDiversiXRegBean.getEmail());
					}
				}
			}
			/*
			 * DestinatariDiversiXRegBean => DestinatarioProtBean => DestinatariBean
			 * - Toponimo => TipoToponimo => TipoToponimo col. 49
			 * - Indirizzo => Indirizzo e Toponimo => ToponimoIndirizzo col. 25
			 * - NumCivico => Civico => Civico col. 27
			 * - AppendiceCivico => Appendici => Appendici col. 47
			 * - ComuneCittaEstera => NomeComune e Citta => NomeComuneCitta col. 33 (e cod. istat Comune col. 32 ?)
			 * - Cap => Cap => Cap col. 31
			 * - StatoEstero => NomeStato => NomeStato col. 35 (e cod. istat Stato col. 34 ?)
			 * - Localita => Frazione => Frazione col. 26
			 * - IndirizzoRubrica => ? => col. 58		
			 */
			if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INDIRIZZO_DEST_ESTESI")) {
				// dati indirizzo
				if (StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getStatoEstero())) {		
//					lDestinatarioProtBean.setStato();
					lDestinatarioProtBean.setNomeStato(pDestinatariDiversiXRegBean.getStatoEstero());					
					lDestinatarioProtBean.setIndirizzo(pDestinatariDiversiXRegBean.getIndirizzo());
					lDestinatarioProtBean.setCitta(pDestinatariDiversiXRegBean.getComuneCittaEstera());					
				} else {
					lDestinatarioProtBean.setStato(ProtocolloDataSource._COD_ISTAT_ITALIA);
					lDestinatarioProtBean.setNomeStato(ProtocolloDataSource._NOME_STATO_ITALIA);
					lDestinatarioProtBean.setTipoToponimo(pDestinatariDiversiXRegBean.getToponimo());
					lDestinatarioProtBean.setToponimo(pDestinatariDiversiXRegBean.getIndirizzo());
//					lDestinatarioProtBean.setComune();
					lDestinatarioProtBean.setNomeComune(pDestinatariDiversiXRegBean.getComuneCittaEstera());
					lDestinatarioProtBean.setFrazione(pDestinatariDiversiXRegBean.getLocalita());
					lDestinatarioProtBean.setCap(pDestinatariDiversiXRegBean.getCap());
					lDestinatarioProtBean.setIndirizzoRubrica(pDestinatariDiversiXRegBean.getIndirizzoRubrica());
				}
				lDestinatarioProtBean.setCivico(pDestinatariDiversiXRegBean.getNumCivico());
//				lDestinatarioProtBean.setInterno();
//				lDestinatarioProtBean.setZona();
//				lDestinatarioProtBean.setComplementoIndirizzo();
				lDestinatarioProtBean.setAppendici(pDestinatariDiversiXRegBean.getAppendiceCivico());
				if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getEmail())) {
					lDestinatarioProtBean.setIndirizzoMailDestinatario(pDestinatariDiversiXRegBean.getEmail());
				}
			} else {						
				lMezzoTrasmissioneDestinatarioBean.setTipoToponimo(pDestinatariDiversiXRegBean.getToponimo());
//				lMezzoTrasmissioneDestinatarioBean.setCiToponimo();
				lMezzoTrasmissioneDestinatarioBean.setIndirizzo(pDestinatariDiversiXRegBean.getIndirizzo());
//				lMezzoTrasmissioneDestinatarioBean.setIndirizzoDestinatario();
				lMezzoTrasmissioneDestinatarioBean.setFrazione(pDestinatariDiversiXRegBean.getLocalita());
				lMezzoTrasmissioneDestinatarioBean.setCivico(pDestinatariDiversiXRegBean.getNumCivico());
//				lMezzoTrasmissioneDestinatarioBean.setInterno();
//				lMezzoTrasmissioneDestinatarioBean.setScala();
//				lMezzoTrasmissioneDestinatarioBean.setPiano();
				lMezzoTrasmissioneDestinatarioBean.setCap(pDestinatariDiversiXRegBean.getCap());
//				lMezzoTrasmissioneDestinatarioBean.setCodIstatComune();
				lMezzoTrasmissioneDestinatarioBean.setComune(pDestinatariDiversiXRegBean.getComuneCittaEstera());
				if (StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getStatoEstero())) {															
//					lMezzoTrasmissioneDestinatarioBean.setCodIstatStato();
					lMezzoTrasmissioneDestinatarioBean.setStato(pDestinatariDiversiXRegBean.getStatoEstero());
				} else {
					lMezzoTrasmissioneDestinatarioBean.setCodIstatStato(ProtocolloDataSource._COD_ISTAT_ITALIA);
					lMezzoTrasmissioneDestinatarioBean.setStato(ProtocolloDataSource._NOME_STATO_ITALIA);
				}				
//				lMezzoTrasmissioneDestinatarioBean.setZona();
//				lMezzoTrasmissioneDestinatarioBean.setComplementoIndirizzo();
				lMezzoTrasmissioneDestinatarioBean.setAppendici(pDestinatariDiversiXRegBean.getAppendiceCivico());
				lMezzoTrasmissioneDestinatarioBean.setIndirizzoRubrica(pDestinatariDiversiXRegBean.getIndirizzoRubrica());
			}
			lDestinatarioProtBean.setMezzoTrasmissioneDestinatario(lMezzoTrasmissioneDestinatarioBean);
			
			if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getEffettuaAssegnazioneCc())) {
				if(pDestinatariDiversiXRegBean.getEffettuaAssegnazioneCc().equalsIgnoreCase(EffettuaAssegnazioneCc.ASS.getValue())) {
					lDestinatarioProtBean.setFlgAssegnaAlDestinatario(true);					
				} else if(pDestinatariDiversiXRegBean.getEffettuaAssegnazioneCc().equalsIgnoreCase(EffettuaAssegnazioneCc.CC.getValue())) {
					lDestinatarioProtBean.setFlgPC(true);
				} 
			}			
					
			return lDestinatarioProtBean;
		}
		
		return null;
	}
	
	public ProtocolloDataSource getProtocolloDataSource(final RegistrazioneMultiplaUscitaBean pRegistrazioneMultiplaUscitaBean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				if(pRegistrazioneMultiplaUscitaBean != null) {
					salvaAttributiCustomRegistrazioneMultiplaUscita(pRegistrazioneMultiplaUscitaBean, pSezioneCacheAttributiDinamici);
				}
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		Map<String, String> extraparams = getExtraparams();
		extraparams.put("isRegMultiplaUscita", "true");
		if(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla() != null && _TIPO_REG_R.equalsIgnoreCase(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla())) {
			extraparams.put("isRepertorio", "true");
		}
		lProtocolloDataSource.setExtraparams(extraparams);					
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2CompletaDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}
	
	private void salvaAttributiCustomRegistrazioneMultiplaUscita(RegistrazioneMultiplaUscitaBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		salvaAttributiCustomRegistrazioneMultiplaUscita(bean, sezioneCacheAttributiDinamici, false);
	}
	
	private void salvaAttributiCustomRegistrazioneMultiplaUscita(RegistrazioneMultiplaUscitaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean flgGenModello) throws Exception {
		
	}
	
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}
	
	public EsitoValidazioneBean validazioneFile(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		// verifica di presenza dei file primari e allegati indicati nel file xls dei destinatari
		EsitoValidazioneBean lEsitoValidazioneFilePrimari = validazioneFilePrimari(bean);
		EsitoValidazioneBean lEsitoValidazioneFileAllegati = validazioneFileAllegati(bean);
		
		EsitoValidazioneBean lEsitoValidazioneBean = new EsitoValidazioneBean();
		lEsitoValidazioneBean.setEsitoValidazione(lEsitoValidazioneFilePrimari.getEsitoValidazione() && lEsitoValidazioneFileAllegati.getEsitoValidazione());
		lEsitoValidazioneBean.setErrorMessages(new HashMap<String, String>());
		lEsitoValidazioneBean.getErrorMessages().putAll(lEsitoValidazioneFilePrimari.getErrorMessages());
		for(String key : lEsitoValidazioneFileAllegati.getErrorMessages().keySet()) {
			String value = lEsitoValidazioneFileAllegati.getErrorMessages().get(key);
			if(lEsitoValidazioneBean.getErrorMessages().containsKey(key)) {
				lEsitoValidazioneBean.getErrorMessages().put(key, lEsitoValidazioneBean.getErrorMessages().get(key) + "\n" + value);
			} else {
				lEsitoValidazioneBean.getErrorMessages().put(key, value);
				
			}
		}
		
		return lEsitoValidazioneBean;
	}
	
	public EsitoValidazioneBean validazioneFilePrimari(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		// Verifica di presenza dei file primari indicati nel file xls dei destinatari
		EsitoValidazioneBean lEsitoValidazioneBean = new EsitoValidazioneBean();
		lEsitoValidazioneBean.setEsitoValidazione(true);
		lEsitoValidazioneBean.setErrorMessages(new HashMap<String, String>());
		if(bean.getListaDestinatariDiversiXReg() != null && bean.getListaDestinatariDiversiXReg().size() > 0) {
			if(bean.getFlgFilePrincipaleUgualeXTutteReg() != null && _FLG_NO.equalsIgnoreCase(bean.getFlgFilePrincipaleUgualeXTutteReg()) && StringUtils.isNotBlank(bean.getPercorsoFilePrimari())) {
				for(int i = 0; i < bean.getListaDestinatariDiversiXReg().size(); i++) {
					DestinatariRegistrazioneMultiplaUscitaXmlBean dest = bean.getListaDestinatariDiversiXReg().get(i);
					if (StringUtils.isBlank(dest.getFlgStessaRegDestPrec())) {
						if(StringUtils.isNotBlank(dest.getNomeFilePrimario())) {	
							File filePrimario = recuperaFilePrimarioDestinatario(bean.getPercorsoFilePrimari(), dest.getNomeFilePrimario());
							if(filePrimario == null) {
								lEsitoValidazioneBean.setEsitoValidazione(false);
								lEsitoValidazioneBean.getErrorMessages().put("" + (Integer.parseInt(dest.getNumRigaInTabContFoglio()) + 1), "In riga " + (Integer.parseInt(dest.getNumRigaInTabContFoglio()) + 1) + " il file primario " + dest.getNomeFilePrimario() + " non è presente nel percorso indicato.");							
							}
						} else {
							lEsitoValidazioneBean.setEsitoValidazione(false);
							lEsitoValidazioneBean.getErrorMessages().put("" + (Integer.parseInt(dest.getNumRigaInTabContFoglio()) + 1), "In riga " + (Integer.parseInt(dest.getNumRigaInTabContFoglio()) + 1) + " non è indicato il file primario.");							
						}
					}
				}				
			} else {
				if (StringUtils.isBlank(bean.getUriFilePrimario())) {
					lEsitoValidazioneBean.setEsitoValidazione(false);
					lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato il percorso da cui recuperare i file primari.");		
				}
			}	
		} else {
			lEsitoValidazioneBean.setEsitoValidazione(false);
			lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato nessun destinatario nel file xls.");	
		}
		
		return lEsitoValidazioneBean;
	}
	
	private File recuperaFilePrimarioDestinatario(String percorsoFilePrimari, String nomeFilePrimario) {
		
		if(StringUtils.isNotBlank(percorsoFilePrimari)) {
			File folderFilePrimari = new File(percorsoFilePrimari);
			if(folderFilePrimari.exists() && folderFilePrimari.isDirectory()) {
				if(StringUtils.isNotBlank(nomeFilePrimario)) {
					File filePrimario = new File(folderFilePrimari.getAbsolutePath() + File.separator + nomeFilePrimario);
					if(filePrimario.exists() && filePrimario.isFile()) {
						return filePrimario;				
					}
				}
			}
		}
		return null;
	}

	public EsitoValidazioneBean validazioneFileAllegati(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		// Verifica di presenza dei file allegati indicati nel file xls dei destinatari
		EsitoValidazioneBean lEsitoValidazioneBean = new EsitoValidazioneBean();
		lEsitoValidazioneBean.setEsitoValidazione(true);
		lEsitoValidazioneBean.setErrorMessages(new HashMap<String, String>());
		if(bean.getListaDestinatariDiversiXReg() != null && bean.getListaDestinatariDiversiXReg().size() > 0) {			
			// Recupero solo i destinatari che hanno allegati
			List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestDiversiXRegConAllegati = new ArrayList<DestinatariRegistrazioneMultiplaUscitaXmlBean>();
			for(int i = 0; i < bean.getListaDestinatariDiversiXReg().size(); i++) {
				DestinatariRegistrazioneMultiplaUscitaXmlBean dest = bean.getListaDestinatariDiversiXReg().get(i);
				if(StringUtils.isBlank(dest.getFlgStessaRegDestPrec()) && StringUtils.isNotBlank(dest.getNomiFileAllegati())) {
					listaDestDiversiXRegConAllegati.add(dest);
				} 
			}
			if(listaDestDiversiXRegConAllegati != null && listaDestDiversiXRegConAllegati.size() > 0) {		
				if(StringUtils.isNotBlank(bean.getPercorsoFileAllegati())) {
					for(int i = 0; i < listaDestDiversiXRegConAllegati.size(); i++) {
						DestinatariRegistrazioneMultiplaUscitaXmlBean dest = listaDestDiversiXRegConAllegati.get(i);
						StringSplitterServer st = new StringSplitterServer(dest.getNomiFileAllegati(), ";");
						while(st.hasMoreTokens()) {
							String nomeFileAllegato = st.nextToken().trim();
							File fileAllegato = recuperaFileAllegatoDestinatario(bean.getPercorsoFileAllegati(), dest.getPercorsoRelFileAllegati(), nomeFileAllegato);
							if(fileAllegato == null) {
								lEsitoValidazioneBean.setEsitoValidazione(false);
								lEsitoValidazioneBean.getErrorMessages().put("" + (Integer.parseInt(dest.getNumRigaInTabContFoglio()) + 1), "In riga " + (Integer.parseInt(dest.getNumRigaInTabContFoglio()) + 1) + " il file allegato " + nomeFileAllegato + " non è presente nel percorso indicato.");							
							}					
						}
					}
				} else {
					lEsitoValidazioneBean.setEsitoValidazione(false);
					lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato il percorso da cui recuperare i file allegati.");				
				}
			}
		} else {
			lEsitoValidazioneBean.setEsitoValidazione(false);
			lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato nessun destinatario nel file xls.");	
		}
		return lEsitoValidazioneBean;
	}
		
	private File recuperaFileAllegatoDestinatario(String percorsoFileAllegati, String percorsoRelFileAllegatiDest, String nomeFileAllegato) {
		if(StringUtils.isNotBlank(percorsoFileAllegati)) {
			File folderFileAllegati = new File(percorsoFileAllegati);
			if(folderFileAllegati.exists() && folderFileAllegati.isDirectory()) {
				if(StringUtils.isNotBlank(percorsoRelFileAllegatiDest)) {
					folderFileAllegati = new File(folderFileAllegati.getAbsolutePath() + File.separator + percorsoRelFileAllegatiDest);
					if(!folderFileAllegati.exists() || !folderFileAllegati.isDirectory()) {
						return null;
					}
				} 
				if(StringUtils.isNotBlank(nomeFileAllegato)) {
					File fileAllegato = new File(folderFileAllegati.getAbsolutePath() + File.separator + nomeFileAllegato);
					if(fileAllegato.exists() && fileAllegato.isFile()) {
						return fileAllegato;				
					}
				}
			}	
		}
		return null;
	}
	
	public FileDaFirmareBean generaAnteprimaOggetti(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		File tempFile = null;
				
		try {

			tempFile = File.createTempFile("anteprimaOggetti", "");
			
			Document document = null;
			PdfWriter writer = null;
			
			try {

				Font font_20_bold = new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD);
//				Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		
				document = new Document(PageSize.A4);
				writer = PdfWriter.getInstance(document, new FileOutputStream(tempFile));
		
				document.setMargins(20, 20, 20, 20);
		
				document.newPage();
				document.open();
				
				Paragraph title = new Paragraph("Anteprima oggetti\n\n", font_20_bold);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
				
				if(StringUtils.isNotBlank(bean.getOggetto()) && bean.getListaDestinatariDiversiXReg() != null && bean.getListaDestinatariDiversiXReg().size() > 0) {
					com.itextpdf.text.List list = new com.itextpdf.text.List();
					List<String> listaPlaceholder = getListaPlaceholderOggetto(bean.getOggetto());			
					for (DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean : bean.getListaDestinatariDiversiXReg()) {
						list.add(generaOggettoWithPlaceholder(bean.getOggetto(), listaPlaceholder, lDestinatariDiversiXRegBean)); 
					}
					document.add(list);						
				}
											
			}  catch(Exception e) {
				throw e;
			} finally {
				try { document.close(); } catch(Exception e) {}
				try { writer.close(); } catch(Exception e) {}
			}
						
			StorageService storageService = StorageImplementation.getStorage();
			lFileDaFirmareBean.setUri(storageService.store(tempFile));
			lFileDaFirmareBean.setNomeFile("Anteprima oggetti.pdf");
	
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setCorrectFileName("Anteprima oggetti.pdf");
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setFirmaValida(false);
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			lMimeTypeFirmaBean.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(tempFile));
			lFileDaFirmareBean.setInfoFile(lMimeTypeFirmaBean);
		
		} catch(Exception e) {
			throw new StoreException("Si è verificato un errore durante la generazione dell'anteprima oggetti");
		} finally {
			try { FileUtil.deleteFile(tempFile); } catch(Exception e) {}
			
		}

		return lFileDaFirmareBean;
	}
	
	private String generaOggettoWithPlaceholder(String oggetto, List<String> listaPlaceholder, DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean) {
		if(StringUtils.isNotBlank(oggetto)) {
			if(listaPlaceholder == null) {
				listaPlaceholder = getListaPlaceholderOggetto(oggetto);			
			}
			Map<String, String> mappaIntestazioniColonneValore = lDestinatariDiversiXRegBean.getMappaIntestazioniColonneValore();
			String oggettoWithPlaceholder = "" + oggetto;
			for (int i = 0; i < listaPlaceholder.size(); i++) {
				String valorePlaceholderXls = mappaIntestazioniColonneValore.get(listaPlaceholder.get(i));
				oggettoWithPlaceholder = oggettoWithPlaceholder.replace("$" + listaPlaceholder.get(i) + "$", valorePlaceholderXls);
			}
			return oggettoWithPlaceholder;
		}
		return oggetto;
	}
	
	private List<String> getListaPlaceholderOggetto(String oggetto) {
		List<String> listaPlaceholder = new LinkedList<String> ();
		if(StringUtils.isNotBlank(oggetto)) {
			String app = "" + oggetto;
			while (app.contains("$")) {
				String placeholder = "";
				app = app.substring(app.indexOf("$")+1);
				if (app.contains("$")) {
					placeholder = app.substring(0, app.indexOf("$"));
					if (StringUtils.isNotBlank(placeholder)) {
						listaPlaceholder.add(placeholder);
					}
					app = app.substring(app.indexOf("$")+1);					
				}
			}
		}
		return listaPlaceholder;
	}
	
	public ImportaDestinatariFromXlsRegMultiplaUscitaBean importaDestinatariFromXls(ImportaDestinatariFromXlsRegMultiplaUscitaBean bean) throws Exception {

		List<DestinatariXFileXlsRegMultiplaUscitaBean> listaDestinatariXls = new ArrayList<DestinatariXFileXlsRegMultiplaUscitaBean>();		

		RegistrazioneMultiplaUscitaExcelUtility protMassivaExcelUtility = new RegistrazioneMultiplaUscitaExcelUtility();
		try {
			for(FileXlsDestinatariRegMultiplaUscitaBean lFileXlsBean : bean.getListaFileXls()) {
				
				String uriExcel = lFileXlsBean.getUriExcel();
				String mimeType = lFileXlsBean.getMimeType();
				boolean isXls = mimeType.equals("application/excel");
				boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				DestinatariXFileXlsRegMultiplaUscitaBean datiDestinatari = new DestinatariXFileXlsRegMultiplaUscitaBean();
				/**
				 * Implementazione HSSF (Horrible SpreadSheet Format): indica un'API che funziona con Excel 2003 o versioni precedenti.
				 */
				if (isXls) {
					datiDestinatari = protMassivaExcelUtility.caricaDatiFromXls(uriExcel, getSession(), lFileXlsBean.getNomeFile());
					datiDestinatari.setNomeFile(lFileXlsBean.getNomeFile());
					listaDestinatariXls.add(datiDestinatari);
				}
				
				/**
				 * Implementazione XSSF (XML SpreadSheet Format): indica un'API che funziona con Excel 2007 o versioni successive.
				 */
				else if (isXlsx) { 
					datiDestinatari = protMassivaExcelUtility.caricaDatiFromXlsx(uriExcel, getSession(), lFileXlsBean.getNomeFile());
					datiDestinatari.setNomeFile(lFileXlsBean.getNomeFile());
					listaDestinatariXls.add(datiDestinatari);
				} else {
					String message = "Il formato del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi";
					logger.error(message);
					
					throw new StoreException(message);
				}
			}
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file, si è verificata la seguente eccezione: " + errorMessage;
			logger.error(message, e);

			throw new StoreException(message); 
		}

		bean.setListaDestinatariXFileXls(listaDestinatariXls);
		return bean;
	}
	
	private XmlColonneContenutiBean getXmlFromDocumentRows(XmlColonneContenutiBean data) {
		XmlColonneContenutiBean retValue = new XmlColonneContenutiBean();
		retValue.setSuccessful(true);

		String mimeType = data.getMimetype();

		boolean isXls = mimeType.equals("application/excel");
		boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		if (isXls) {

			retValue = saveXls(data);

		} else if (isXlsx) {

			retValue = saveXlsx(data);

		} else {

			String message = String.format(
					"Il formato %1$s del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi",
					data.getMimetype());
			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);

		}

		return retValue;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private XmlColonneContenutiBean saveXlsx(XmlColonneContenutiBean data) {

		int numColonne = 0;
		XmlColonneContenutiBean xmlColonneContenuti = new XmlColonneContenutiBean();
		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();

		BufferedInputStream documentStream = null;
		InputStream is = null;
		int numRigheDestinatari = 0;
		
		
		try {

			File document = StorageImplementation.getStorage().getRealFile(data.getUri());

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);

			XSSFWorkbook wb = new XSSFWorkbook(documentStream);

			ExecutionResultBean result = populateDettagliColonne(data, wb, null);

			@SuppressWarnings("unchecked")
			List<XlsColumnRemapping> cellReferences = (List<XlsColumnRemapping>) result
					.getAdditionalInformation("cellReferences");

			it.eng.jaxb.variabili.Lista dettagliColonne = (it.eng.jaxb.variabili.Lista) result.getAdditionalInformation("dettagliColonne");

			it.eng.jaxb.variabili.Lista xmlContenuti = new it.eng.jaxb.variabili.Lista();

			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

					XSSFSheet currentSheet = wb.getSheetAt(sheetIndex);

					// verifico se ci sono righe con celle valorizzate
					int nonEmptyRows = currentSheet.getPhysicalNumberOfRows();
					
					numRigheDestinatari = nonEmptyRows;
					
					numColonne = getNumberOfColumn(wb, null);

					if (nonEmptyRows > 0) {

						// la prima riga è di intestazione
						// la condizione su nonEmptyRows mi evita di scansionare
						// il numero massimo di righe che possono essere
						// presenti
						// nel foglio
						for (int rowIndex = currentSheet.getFirstRowNum(); rowIndex <= currentSheet.getLastRowNum()
								&& nonEmptyRows > 0; rowIndex++) {

							Row row = currentSheet.getRow(rowIndex);
							
							if (row.getCell(0) != null
									&& row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK
									&& row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING
									&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Stessa reg. dest. prec.")) {
								
								numRigheDestinatari = nonEmptyRows-1;
								
								continue;
							}

							if (row != null) {
								retValue = populateRiga(cellReferences, xmlContenuti, nonEmptyRows, row);

								if (retValue.isSuccessful()) {

									Riga currentRiga = (Riga) retValue.getResult();

									if (currentRiga != null) {
										Colonna colValue = new Colonna();
										colValue.setNro(BigInteger.valueOf(numColonne + 1));
										colValue.setContent("da_effettuare");
										currentRiga.getColonna().add(colValue);
										
										Colonna colValue1 = new Colonna();
										colValue1.setNro(BigInteger.valueOf(numColonne + 2));
										colValue1.setContent(retValue.getInvioMailPrevisto());
										currentRiga.getColonna().add(colValue1);
										
										Colonna colValue2 = new Colonna();
										colValue2.setNro(BigInteger.valueOf(numColonne + 3));
										colValue2.setContent("0");
										currentRiga.getColonna().add(colValue2);
										
										Colonna colValue3 = new Colonna();
										colValue3.setNro(BigInteger.valueOf(numColonne + 4));
										colValue3.setContent("0");
										currentRiga.getColonna().add(colValue3);
										
										Colonna colValue4 = new Colonna();
										colValue4.setNro(BigInteger.valueOf(numColonne + 5));
										colValue4.setContent("");
										currentRiga.getColonna().add(colValue4);
										
										Colonna colValue5 = new Colonna();
										colValue5.setNro(BigInteger.valueOf(numColonne + 6));
										colValue5.setContent("");
										currentRiga.getColonna().add(colValue5);
										
										Colonna colValue6 = new Colonna();
										colValue6.setNro(BigInteger.valueOf(numColonne + 7));
										colValue6.setContent("");
										currentRiga.getColonna().add(colValue6);
										
										Colonna colValue7 = new Colonna();
										colValue7.setNro(BigInteger.valueOf(numColonne + 8));
										colValue7.setContent("");
										currentRiga.getColonna().add(colValue7);
										
										xmlContenuti.getRiga().add(currentRiga);
									}

								} else {
									ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
									erroreRiga.setNumeroRiga(String.valueOf(rowIndex + 1));
									erroreRiga.setMotivo(retValue.getMessage());

									listaRigheInErrore.add(erroreRiga);
								}
							}
						}
					}
				}
				
				if(listaRigheInErrore!=null && listaRigheInErrore.size()>0) {
					
					xmlColonneContenuti.setMessage("Errore durante l'elaborazione del file Excel, dati in formato non valido");
					xmlColonneContenuti.setSuccessful(false);
					xmlColonneContenuti.setNumRigheDestinatari(String.valueOf(numRigheDestinatari));
					xmlColonneContenuti.setListaExcelDatiInError(listaRigheInErrore);
				} else {
					xmlColonneContenuti.setDettagliColonne(dettagliColonne);
					xmlColonneContenuti.setXmlContenuti(xmlContenuti);
				}

		} catch (Exception e) {

			String message = "Durante il caricamento delle righe del documento si è verificata la seguente eccezione %1$s";

			String exceptionMessage = e.getMessage() != null ? e.getMessage() : e.toString();

			String infoMessage = String.format(message, exceptionMessage);

			logger.error(String.format(message, ExceptionUtils.getFullStackTrace(e)));


			xmlColonneContenuti.setSuccessful(false);
			xmlColonneContenuti.setMessage(infoMessage);

		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
			if(documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error(String.format(
							"Impossibile chiudere lo stream legato al documento a causa della seguente eccezione "), e);
				}
			}
		}

		return xmlColonneContenuti;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private XmlColonneContenutiBean saveXls(XmlColonneContenutiBean data) {

		XmlColonneContenutiBean xmlColonneContenuti = new XmlColonneContenutiBean();
		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		List<ErroreRigaExcelBean> listaRigheInErrore = new ArrayList<ErroreRigaExcelBean>();
		int numColonne = 0;
		
		BufferedInputStream documentStream = null;
		InputStream is = null;
		
		int numRigheDestinatari = 0;
		try {

			File document = StorageImplementation.getStorage().getRealFile(data.getUri());

			is = new FileInputStream(document);
			documentStream = new BufferedInputStream(is);

			HSSFWorkbook wb = new HSSFWorkbook(documentStream);

			ExecutionResultBean result = populateDettagliColonne(data, null, wb);

			@SuppressWarnings("unchecked")
			List<XlsColumnRemapping> cellReferences = (List<XlsColumnRemapping>) result
					.getAdditionalInformation("cellReferences");

			it.eng.jaxb.variabili.Lista dettagliColonne = (it.eng.jaxb.variabili.Lista) result.getAdditionalInformation("dettagliColonne");

			it.eng.jaxb.variabili.Lista xmlContenuti = new it.eng.jaxb.variabili.Lista();

			badRow: {

				for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

					HSSFSheet currentSheet = wb.getSheetAt(sheetIndex);

					// verifico se ci sono righe con celle valorizzate
					int nonEmptyRows = currentSheet.getPhysicalNumberOfRows();
					
					numRigheDestinatari = nonEmptyRows;
					
					numColonne = getNumberOfColumn(null, wb);

					if (nonEmptyRows > 0) {
				
						for (int rowIndex = currentSheet.getFirstRowNum(); rowIndex <= currentSheet.getLastRowNum()
								&& nonEmptyRows > 0; rowIndex++) {

							Row row = currentSheet.getRow(rowIndex);
							
							if (row.getCell(0) != null
									&& row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK
									&& row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING
									&& row.getCell(0).getStringCellValue().equalsIgnoreCase("Stessa reg. dest. prec.")) {
								
								numRigheDestinatari = nonEmptyRows-1;
								
								continue;
							}

							if (row != null) {
								retValue = populateRiga(cellReferences, xmlContenuti, nonEmptyRows, row);

								if (retValue.isSuccessful()) {

									it.eng.jaxb.variabili.Lista.Riga currentRiga = (it.eng.jaxb.variabili.Lista.Riga) retValue.getResult();

									if (currentRiga != null) {
										Colonna colValue = new Colonna();
										colValue.setNro(BigInteger.valueOf(numColonne + 1));
										colValue.setContent("da_effettuare");
										currentRiga.getColonna().add(colValue);
										
										Colonna colValue1 = new Colonna();
										colValue1.setNro(BigInteger.valueOf(numColonne + 2));
										colValue1.setContent(retValue.getInvioMailPrevisto());
										currentRiga.getColonna().add(colValue1);
										
										Colonna colValue2 = new Colonna();
										colValue2.setNro(BigInteger.valueOf(numColonne + 3));
										colValue2.setContent("0");
										currentRiga.getColonna().add(colValue2);
										
										Colonna colValue3 = new Colonna();
										colValue3.setNro(BigInteger.valueOf(numColonne + 4));
										colValue3.setContent("0");
										currentRiga.getColonna().add(colValue3);
										
										Colonna colValue4 = new Colonna();
										colValue4.setNro(BigInteger.valueOf(numColonne + 5));
										colValue4.setContent("");
										currentRiga.getColonna().add(colValue4);
										
										Colonna colValue5 = new Colonna();
										colValue5.setNro(BigInteger.valueOf(numColonne + 6));
										colValue5.setContent("");
										currentRiga.getColonna().add(colValue5);
										
										Colonna colValue6 = new Colonna();
										colValue6.setNro(BigInteger.valueOf(numColonne + 7));
										colValue6.setContent("");
										currentRiga.getColonna().add(colValue6);
										
										Colonna colValue7 = new Colonna();
										colValue7.setNro(BigInteger.valueOf(numColonne + 8));
										colValue7.setContent("");
										currentRiga.getColonna().add(colValue7);
										
										xmlContenuti.getRiga().add(currentRiga);
									}

								} else {
									ErroreRigaExcelBean erroreRiga = new ErroreRigaExcelBean();
									erroreRiga.setNumeroRiga(String.valueOf(rowIndex + 1));
									erroreRiga.setMotivo(retValue.getMessage());

									listaRigheInErrore.add(erroreRiga);
								}
							}
						}
					}
				}

				if(listaRigheInErrore!=null && listaRigheInErrore.size()>0) {
					
					xmlColonneContenuti.setMessage("Errore durante l'elaborazione del file Excel, dati in formato non valido");
					xmlColonneContenuti.setNumRigheDestinatari(String.valueOf(numRigheDestinatari));
					xmlColonneContenuti.setSuccessful(false);
					xmlColonneContenuti.setListaExcelDatiInError(listaRigheInErrore);
					
				} else {
					xmlColonneContenuti.setDettagliColonne(dettagliColonne);
					xmlColonneContenuti.setXmlContenuti(xmlContenuti);
				}
			}
		} catch (Exception e) {

			String message = "Durante il caricamento delle righe del documento si è verificata la seguente eccezione %1$s";

			String exceptionMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			String infoMessage = String.format(message, exceptionMessage);

			logger.error(String.format(message, ExceptionUtils.getFullStackTrace(e)));

//			updateDocumentStateError(data.getMimetype(), documentId, infoMessage);

			xmlColonneContenuti.setSuccessful(false);
			xmlColonneContenuti.setMessage(infoMessage);

		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
			if(documentStream != null) {
				try {
					documentStream.close();
				} catch (Exception e) {
					logger.error(String.format(
							"Impossibile chiudere lo stream legato al documento a causa della seguente eccezione "), e);
				}
			}
		}

		return xmlColonneContenuti;
	}
	
	@SuppressWarnings("rawtypes")
	protected ExecutionResultBean populateDettagliColonne(XmlColonneContenutiBean data, XSSFWorkbook xb, HSSFWorkbook hb) {

		ExecutionResultBean result = new ExecutionResultBean();

		int colIndex = 1;
		
		it.eng.jaxb.variabili.Lista dettagliColonne = new it.eng.jaxb.variabili.Lista();

		List<XlsColumnRemapping> cellReferences = new ArrayList<XlsColumnRemapping>();
		
		List<CampoCaricamentoBean> listaCampiDestinatariCaricamento = getListaCampiDestinatariCaricamento(xb, hb);

		for (CampoCaricamentoBean currentCampoCaricamento : listaCampiDestinatariCaricamento) {

			Riga riga = new Riga();
			Colonna[] cols = new Colonna[3];

			// identificativo della colonna che permette di referenziare gli
			// oggetti presenti nelle due Lista, quella che identifica le
			// colonne e quella che contiene i dati
			Colonna index = new Colonna();
			index.setContent(String.valueOf(colIndex));
			index.setNro(new BigInteger("1"));
			cols[0] = index;
			colIndex++;
			riga.getColonna().add(index);

			// colonna associata al campo
			Colonna field = new Colonna();
			cols[1] = field;
			field.setContent(currentCampoCaricamento.getNomeCampo());
			field.setNro(new BigInteger("2"));
			riga.getColonna().add(field);

			// tipo di campo, "S"tringa, "N"umerico o "D"ata, viene popolato a
			// posteriori durante la scansione delle righe
			Colonna fieldType = new Colonna();
			cols[2] = fieldType;
			fieldType.setContent(currentCampoCaricamento.getType());
			fieldType.setNro(new BigInteger("3"));
			riga.getColonna().add(fieldType);

			dettagliColonne.getRiga().add(riga);

			cellReferences.add(new XlsColumnRemapping(currentCampoCaricamento.getNomeCampo(),
					currentCampoCaricamento.getColonnaRif(), index, fieldType));
		}

		List<CampoCaricamentoBean> listaCampiAggiuntiviDestinatariCaricamento = new LinkedList<CampoCaricamentoBean>();
		
		CampoCaricamentoBean campoCaricamentoBean = new CampoCaricamentoBean();
		campoCaricamentoBean.setType("S");
		campoCaricamentoBean.setColonnaRif("");
		campoCaricamentoBean.setNomeCampo("91-StatoRegistrazione");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean);
		
		CampoCaricamentoBean campoCaricamentoBean1 = new CampoCaricamentoBean();
		campoCaricamentoBean1.setType("S");
		campoCaricamentoBean1.setColonnaRif("");
		campoCaricamentoBean1.setNomeCampo("92-StatoInvioEmail");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean1);
		
		CampoCaricamentoBean campoCaricamentoBean2 = new CampoCaricamentoBean();
		campoCaricamentoBean2.setType("N");
		campoCaricamentoBean2.setColonnaRif("");
		campoCaricamentoBean2.setNomeCampo("93-NroTryRegistrazione");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean2);
		
		CampoCaricamentoBean campoCaricamentoBean3 = new CampoCaricamentoBean();
		campoCaricamentoBean3.setType("N");
		campoCaricamentoBean3.setColonnaRif("");
		campoCaricamentoBean3.setNomeCampo("94-NroTryInvioEmail");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean3);
		
		CampoCaricamentoBean campoCaricamentoBean4 = new CampoCaricamentoBean();
		campoCaricamentoBean4.setType("S");
		campoCaricamentoBean4.setColonnaRif("");
		campoCaricamentoBean4.setNomeCampo("95-MsgErrRegistrazione");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean4);
		
		CampoCaricamentoBean campoCaricamentoBean5 = new CampoCaricamentoBean();
		campoCaricamentoBean5.setType("S");
		campoCaricamentoBean5.setColonnaRif("");
		campoCaricamentoBean5.setNomeCampo("96-MsgErrInvioEmail");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean5);
		
		CampoCaricamentoBean campoCaricamentoBean6 = new CampoCaricamentoBean();
		campoCaricamentoBean6.setType("S");
		campoCaricamentoBean6.setColonnaRif("");
		campoCaricamentoBean6.setNomeCampo("97-IdUD");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean6);
		
		CampoCaricamentoBean campoCaricamentoBean7 = new CampoCaricamentoBean();
		campoCaricamentoBean7.setType("S");
		campoCaricamentoBean7.setColonnaRif("");
		campoCaricamentoBean7.setNomeCampo("98-IdEmail");
		listaCampiAggiuntiviDestinatariCaricamento.add(campoCaricamentoBean7);
		
		for (CampoCaricamentoBean currentCampoCaricamento : listaCampiAggiuntiviDestinatariCaricamento) {

			Riga riga = new Riga();
			Colonna[] cols = new Colonna[3];

			// identificativo della colonna che permette di referenziare gli
			// oggetti presenti nelle due Lista, quella che identifica le
			// colonne e quella che contiene i dati
			Colonna index = new Colonna();
			index.setContent(String.valueOf(colIndex));
			index.setNro(new BigInteger("1"));
			cols[0] = index;
			colIndex++;
			riga.getColonna().add(index);

			// colonna associata al campo
			Colonna field = new Colonna();
			cols[1] = field;
			field.setContent(currentCampoCaricamento.getNomeCampo());
			field.setNro(new BigInteger("2"));
			riga.getColonna().add(field);

			// tipo di campo, "S"tringa, "N"umerico o "D"ata, viene popolato a
			// posteriori durante la scansione delle righe
			Colonna fieldType = new Colonna();
			cols[2] = fieldType;
			fieldType.setContent(currentCampoCaricamento.getType());
			fieldType.setNro(new BigInteger("3"));
			riga.getColonna().add(fieldType);

			dettagliColonne.getRiga().add(riga);

		}

		result.setSuccessful(true);
		result.addAdditionalInformation("dettagliColonne", dettagliColonne);
		result.addAdditionalInformation("cellReferences", cellReferences);

		return result;
	}
	
	private List<CampoCaricamentoBean> getListaCampiDestinatariCaricamento(XSSFWorkbook xb, HSSFWorkbook hb) {
		
		List<CampoCaricamentoBean> listaCampoCaricamentoBean = new ArrayList<CampoCaricamentoBean>();
		
		if (xb == null) {
			FormulaEvaluator formulaEvaluator = hb.getCreationHelper().createFormulaEvaluator();
			HSSFSheet sheet = hb.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			int physicalNumberOfCells = row.getPhysicalNumberOfCells();
			for (int i = 0; i < physicalNumberOfCells; i++) {
				HSSFCell cell = row.getCell(i);
				if (cell != null) {
					CampoCaricamentoBean campoCaricamentoBean = new CampoCaricamentoBean();
					campoCaricamentoBean.setType("S");
					String column_letter = CellReference.convertNumToColString(cell.getColumnIndex());
					campoCaricamentoBean.setColonnaRif(column_letter);
					campoCaricamentoBean.setNomeCampo( i + 1 < 10 ? "0"+ (i + 1) +"-"+ cell.getStringCellValue() : (i + 1) +"-"+ cell.getStringCellValue());
					listaCampoCaricamentoBean.add(campoCaricamentoBean);
				}
			}
		} else {
			XSSFFormulaEvaluator formulaEvaluator = xb.getCreationHelper().createFormulaEvaluator();
			XSSFSheet sheet = xb.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);
			int physicalNumberOfCells = row.getPhysicalNumberOfCells();
			for (int i = 0; i < physicalNumberOfCells; i++) {
				XSSFCell cell = row.getCell(i);
				if (cell != null) {
					CampoCaricamentoBean campoCaricamentoBean = new CampoCaricamentoBean();
					campoCaricamentoBean.setType("S");
					String column_letter = CellReference.convertNumToColString(cell.getColumnIndex());
					campoCaricamentoBean.setColonnaRif(column_letter);
					campoCaricamentoBean.setNomeCampo( i + 1 < 10 ? "0"+ (i + 1) +"-"+ cell.getStringCellValue() : (i + 1) +"-"+ cell.getStringCellValue());
					listaCampoCaricamentoBean.add(campoCaricamentoBean);
				}
			}
		}
		
		return listaCampoCaricamentoBean;
	}
	
	private int getNumberOfColumn(XSSFWorkbook xb, HSSFWorkbook hb) {
		if (xb == null) {
			FormulaEvaluator formulaEvaluator = hb.getCreationHelper().createFormulaEvaluator();
			HSSFSheet sheet = hb.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			int physicalNumberOfCells = row.getPhysicalNumberOfCells();
			return physicalNumberOfCells;
		} else {
			XSSFFormulaEvaluator formulaEvaluator = xb.getCreationHelper().createFormulaEvaluator();
			XSSFSheet sheet = xb.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);
			int physicalNumberOfCells = row.getPhysicalNumberOfCells();
			return physicalNumberOfCells;
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected CaricamentoDestinatariExcelBean populateRiga(List<XlsColumnRemapping> cellReferences, it.eng.jaxb.variabili.Lista xmlContenuti,
			int nonEmptyRows, Row row) throws NumberFormatException, IndexOutOfBoundsException {

		CaricamentoDestinatariExcelBean retValue = new CaricamentoDestinatariExcelBean();
		retValue.setSuccessful(Boolean.TRUE);

		Riga riga = new Riga();

		// mi permette di determinare se almeno una delle
		// colonne della riga è valorizzata. Questo perchè le
		// righe
		// potrebbero essere valorizzate a scacchiera, oppure
		// valorizzate e poi cancellate (selezionando una riga e
		// premendo canc il contenuto viene valorizzzato con "")
		boolean saveRiga = false;

		// verifico se la riga ha celle valorizzate
		if (row.getPhysicalNumberOfCells() > 0) {

			for (XlsColumnRemapping cellReference : cellReferences) {

				int cellColumnIndex = Integer.valueOf(cellReference.getIndex().getContent()) - 1;
				Cell cell = row.getCell(cellColumnIndex);
				
				String value = "";

				if (cell != null) {
					String cellFieldName = cellReference.getFieldName();
					int rowNum = row.getRowNum();
					String fieldType = cellReference.getFieldType().getContent();
					
					try {
						/**
						 * IL TIPO DATA E NUMERO VENGONO RICONOSCIUTI COME CELL_TYPE_NUMERIC
						 */
						if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {
							/**
							 * VERIFICA SE E' UN NUMERO
							 */
							try {
//									value = String.valueOf(cell.getNumericCellValue());
								value = new DecimalFormat("#").format(cell.getNumericCellValue());
								value = value.replace(".", ",");
							} catch (Exception e1) {
	
								value = cell.getStringCellValue();
								value = value.replace(".", ",");
							}		
						} else if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_STRING) {							
							value = cell.getStringCellValue();
						} else if (row.getCell(cellColumnIndex).getCellType() == Cell.CELL_TYPE_BLANK) {							
							value = cell.getStringCellValue();						
						}	
						if (cellFieldName.contains("Invia e-mail")) {
							retValue.setInvioMailPrevisto("NO".equalsIgnoreCase(value) ? "non_previsto" : "da_effettuare");
						}
					} catch (Exception e) {

						String message = String.format(
								"Dato non valido per la colonna %1$s, nome campo %2$s, tipo %4$s, controllare il formato della cella",
								cellColumnIndex, cellFieldName, rowNum, fieldType);

						retValue.setMessage(message);
						retValue.setSuccessful(false);

						break;
					}

					if (StringUtils.isNotBlank(value)) {
						saveRiga = true;
					}
				}

				Colonna colValue = new Colonna();
				colValue.setNro(new BigInteger(cellReference.getIndex().getContent()));
				colValue.setContent(value);
				riga.getColonna().add(colValue);
			}
		}

		if (saveRiga) {

			retValue.setResult(riga);
		}
		--nonEmptyRows;

		return retValue;
	}
	
	public enum TipoDestinatario implements Serializable {

		PF("Persona fisica"),
		PG("Persona giuridica"),
		PA("PA o sua articolazione"),
		UOI("U.O. interna"),
		UP("Unità di personale");

		private final String value;

		TipoDestinatario(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	public enum MezzoTrasmissione implements Serializable {

		R("Raccomandata"),
		NM("Notifica"),
		PEC("PEC"),
		PEO("PEO"),
		EMAIL("E-mail");

		private final String value;

		MezzoTrasmissione(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	public enum EffettuaAssegnazioneCc implements Serializable {

		ASS("effettua assegnazione"),
		CC("c.c.");

		private final String value;

		EffettuaAssegnazioneCc(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	public enum InvioEmail implements Serializable {

		SI_CON_SEGNATURA("SI con Segnatura.xml"),
		SI_SENZA_SEGNATURA("SI senza Segnatura.xml"),
		NO("NO");

		private final String value;

		InvioEmail(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
}
