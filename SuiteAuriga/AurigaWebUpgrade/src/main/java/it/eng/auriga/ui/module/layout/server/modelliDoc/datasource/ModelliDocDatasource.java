/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.pkg.OdfPackage;

import it.eng.auriga.compiler.FreeMarkerModelliUtil;
import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.compiler.TemplateWithValuesBean;
import it.eng.auriga.compiler.exeption.FreeMarkerCreateDocumentException;
import it.eng.auriga.compiler.exeption.FreeMarkerFixMergedCellException;
import it.eng.auriga.compiler.exeption.FreeMarkerMergeHtmlSectionsException;
import it.eng.auriga.compiler.exeption.FreeMarkerRetriveStyleException;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocDmodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocIumodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocLoaddettmodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocTrovamodelliBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.InputModuliCompBean;
import it.eng.auriga.module.business.dao.beans.TSerializableList;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.AttributiDinamiciDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoXRicercaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.AssociazioniAttributiCustomBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocXmlBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.GeneraDaModelloBean;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.client.DaoDmtInputModuliComp;
import it.eng.client.DmpkModelliDocDmodello;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocIumodello;
import it.eng.client.DmpkModelliDocLoaddettmodello;
import it.eng.client.DmpkModelliDocTrovamodelli;
import it.eng.client.GestioneDocumenti;
import it.eng.core.business.FileUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ModelliDocDatasource")
public class ModelliDocDatasource extends AbstractFetchDataSource<ModelliDocBean>{
	
	private static Logger logger = Logger.getLogger(ModelliDocDatasource.class);
	
	private AurigaLoginBean loginBean;
	
	public static final char NUL = (char) 0; // Codice ASCII NUL (Nullo)
	public static final char EOT = (char) 4; // Codice ASCII EOT (End of transmission)
	public static final char ENQ = (char) 5; // Codice ASCII ENQ (Enquiry)	 
	
	public static final String FREEMARKER_INIZIO = "${";
	public static final String FREEMARKER_FINE = "}";
	public static final String FREEMARKER_IMAGE_INIZIO = "JOOSCRIPT.IMAGE(";
	public static final String FREEMARKER_IMAGE_FINE = ")";
	public static final String TAG_APERTURA_SCRIPT_INIZIO = "<SCRIPT";
	public static final String TAG_APERTURA_SCRIPT_FINE = ">";
	public static final String TAG_CHIUSURA_SCRIPT = "</SCRIPT>";
	public static final String TAG_APERTURA_IMAGE_INIZIO = "<IMG";
	public static final String TAG_APERTURA_IMAGE_FINE = ">";
	
	public static final char CHECK = '\u2611'; // Codice ASCII CHECK
	public static final char NOT_CHECK = '\u2610'; // Codice ASCII NO CHECK	
	
	public static final String HTML_VALUE_PREFIX = "|*|HTML|*|";
	public static final String TAG_APERTURA_DATI_SENSIBILI = "<s>";
	public static final String TAG_CHIUSURA_DATI_SENSIBILI = "</s>";
	public static final String OMISSIS = "<i>omissis</i>";
	public static final String HTML_SPACE = "&nbsp;";
	
//	private static int nItemListsModello;
//	private static int nOrderedListsModello;
//	private static int nUnorderedListsModello;
	
	@Override
	public PaginatorBean<ModelliDocBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = getLoginBean();
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String nomeModello = null;
		String desModello = null;
		String note = null;
		String idTyObjRelated= null;
		String tyObjRelated = null;
					
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("nomeModello".equals(crit.getFieldName())) {
					nomeModello = getTextFilterValue(crit);
				} else if("desModello".equals(crit.getFieldName())) {
					desModello = getTextFilterValue(crit);
				} else if("note".equals(crit.getFieldName())) {
					note = getTextFilterValue(crit);
				} else if("entitaAssociata".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						if("TD".equals((String) map.get("tipoEntitaAssociata"))){
							tyObjRelated = (String) map.get("tipoEntitaAssociata");						
							idTyObjRelated = (String) map.get("tipoDocumento");
						} else if("TF".equals((String) map.get("tipoEntitaAssociata"))){
							tyObjRelated = (String) map.get("tipoEntitaAssociata");						
							idTyObjRelated = (String) map.get("tipoFolder");
						} else if("TP".equals((String) map.get("tipoEntitaAssociata"))){
							tyObjRelated = (String) map.get("tipoEntitaAssociata");						
							idTyObjRelated = (String) map.get("tipoProcedimento");
						}
					}	
				}
			}
		}                	
		     
		DmpkModelliDocTrovamodelliBean input = new DmpkModelliDocTrovamodelliBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomemodelloio(nomeModello);		
		input.setColorderbyio(null);
		input.setFlgdescorderbyio(null);
		input.setFlgsenzapaginazionein(new Integer(1));
		input.setBachsizeio(null);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setNomemodelloio(nomeModello);
		input.setDescrizionemodelloio(desModello);
		input.setNotemodelloio(note);
		input.setIdtyobjrelatedio(StringUtils.isNotBlank(idTyObjRelated) ? new BigDecimal(idTyObjRelated) : null);
		input.setTyobjrelatedio(tyObjRelated);
		
		DmpkModelliDocTrovamodelli dmpkModelliDocTrovamodelli = new DmpkModelliDocTrovamodelli();
		
		StoreResultBean<DmpkModelliDocTrovamodelliBean> output = dmpkModelliDocTrovamodelli.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		List<ModelliDocBean> data = new ArrayList<ModelliDocBean>();
		if(output.getResultBean().getListaxmlout() != null) {
			List<ModelliDocXmlBean> lista = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), ModelliDocXmlBean.class);
			if(lista != null) {
				for(ModelliDocXmlBean lModelliDocXmlBean : lista) {
					ModelliDocBean lModelliDocBean = new ModelliDocBean();
					lModelliDocBean.setIdModello(lModelliDocXmlBean.getIdModello());
					lModelliDocBean.setNomeModello(lModelliDocXmlBean.getNome());
					lModelliDocBean.setDesModello(lModelliDocXmlBean.getDescrizione());
					lModelliDocBean.setFormatoFile(lModelliDocXmlBean.getNomeFormatoFile());
					lModelliDocBean.setTipoEntitaAssociata(lModelliDocXmlBean.getTipoEntitaAssociata());
					lModelliDocBean.setIdEntitaAssociata(lModelliDocXmlBean.getIdEntitaAssociata());
					lModelliDocBean.setNomeEntitaAssociata(lModelliDocXmlBean.getNomeEntitaAssociata());
					lModelliDocBean.setNomeTabella(lModelliDocXmlBean.getNomeTabella());
					lModelliDocBean.setIdDoc(lModelliDocXmlBean.getIdDoc());
					lModelliDocBean.setNote(lModelliDocXmlBean.getAnnotazioni());
					lModelliDocBean.setFlgProfCompleta(lModelliDocXmlBean.getFlgProfCompleta() != null && "1".equals(lModelliDocXmlBean.getFlgProfCompleta()));	
					lModelliDocBean.setUriFileModello(lModelliDocXmlBean.getUriFile());	
					lModelliDocBean.setNomeFileModello(lModelliDocXmlBean.getNomeFile());	
					lModelliDocBean.setFlgGeneraPdf(lModelliDocXmlBean.getFlgGeneraPdf() != null && "1".equals(lModelliDocXmlBean.getFlgGeneraPdf()));	
					lModelliDocBean.setTipoModello(lModelliDocXmlBean.getTipoModello());					
					lModelliDocBean.setTsCreazione(lModelliDocXmlBean.getTsCreazione() != null ? lModelliDocXmlBean.getTsCreazione() : null);
					lModelliDocBean.setCreatoDa(lModelliDocXmlBean.getDesUteCreazione());
					lModelliDocBean.setTsUltimoAgg(lModelliDocXmlBean.getDesUltimoAgg() != null ? lModelliDocXmlBean.getDesUltimoAgg() : null);
					lModelliDocBean.setUltimoAggEffDa(lModelliDocXmlBean.getIdUteUltimoAgg());
					lModelliDocBean.setFlgValido(lModelliDocXmlBean.getFlgAnnLogico() != null && "0".equals(lModelliDocXmlBean.getFlgAnnLogico()));
					data.add(lModelliDocBean);
				}
			}
		}				
					
		PaginatorBean<ModelliDocBean> lPaginatorBean = new PaginatorBean<ModelliDocBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}	
	
	@Override
	public ModelliDocBean get(ModelliDocBean bean) throws Exception {	
		
		AurigaLoginBean loginBean = getLoginBean();
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DmpkModelliDocLoaddettmodelloBean input = new DmpkModelliDocLoaddettmodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdmodelloio(StringUtils.isNotBlank(bean.getIdModello()) ? new BigDecimal(bean.getIdModello()) : null);		
			
		DmpkModelliDocLoaddettmodello dmpkModelliDocLoaddettmodello = new DmpkModelliDocLoaddettmodello();
		
		StoreResultBean<DmpkModelliDocLoaddettmodelloBean> output = dmpkModelliDocLoaddettmodello.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		bean.setNomeModello(output.getResultBean().getNomeout());
		bean.setDesModello(output.getResultBean().getDescrizioneout());		
		bean.setTipoEntitaAssociata(output.getResultBean().getTipoenitaassociataout());
		bean.setIdEntitaAssociata(output.getResultBean().getIdentitaassociataout() != null ? String.valueOf(output.getResultBean().getIdentitaassociataout().longValue()) : null);
		bean.setNomeEntitaAssociata(output.getResultBean().getNomeentitaassociataout());
		bean.setNomeTabella(output.getResultBean().getNometabellaentitaassout());
		bean.setIdDoc(output.getResultBean().getIddocout() != null ? String.valueOf(output.getResultBean().getIddocout().longValue()) : null);
		bean.setIdDocHtml(output.getResultBean().getIddochtmlout() != null ? String.valueOf(output.getResultBean().getIddochtmlout().longValue()) : null);
		bean.setNote(output.getResultBean().getAnnotazioniout());
		bean.setFlgProfCompleta(output.getResultBean().getFlgprofcompletaout() != null && output.getResultBean().getFlgprofcompletaout().intValue() == 1);	
		bean.setFlgGeneraPdf(output.getResultBean().getFlggeneraformatopdfout() != null && output.getResultBean().getFlggeneraformatopdfout().intValue() == 1);	
		bean.setTipoModello(output.getResultBean().getTipomodelloout()); 							
		/*
		if(StringUtils.isNotBlank(output.getResultBean().getAttributiaddout())) {
			List<AttributoBean> listaAttributiAdd = XmlListaUtility.recuperaLista(output.getResultBean().getAttributiaddout(), AttributoBean.class);
			if (listaAttributiAdd != null) {
				for (AttributoBean lAttributoBean : listaAttributiAdd) {					
					
				}
			}
		}
		*/
		logger.debug("#######INIZIO extractVerModello#######");
		extractVerModello(bean);	
		logger.debug("#######FINE extractVerModello#######");
		List<AllegatoProtocolloBean> listaModelli = new ArrayList<AllegatoProtocolloBean>();
		AllegatoProtocolloBean fileModello = new AllegatoProtocolloBean();
		fileModello.setIdDocAllegato(StringUtils.isNotBlank(bean.getIdDoc()) ? new BigDecimal(bean.getIdDoc()) : null);
		fileModello.setNomeFileAllegato(bean.getNomeFileModello());
		fileModello.setUriFileAllegato(bean.getUriFileModello());
		fileModello.setInfoFile(bean.getInfoFileModello());
		listaModelli.add(fileModello);
		bean.setListaModelli(listaModelli);
		
		return bean;
	}
	
	// Fa l'estrazione del documento del modello
	public ModelliDocBean extractVerModello(ModelliDocBean bean) throws Exception {
		
		AurigaLoginBean loginBean = getLoginBean();
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DmpkModelliDocExtractvermodelloBean input = new DmpkModelliDocExtractvermodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdmodelloin(StringUtils.isNotBlank(bean.getIdModello()) ? new BigDecimal(bean.getIdModello()) : null);		
		input.setNomemodelloin(bean.getNomeModello());		
			
		DmpkModelliDocExtractvermodello dmpkModelliDocExtractvermodello = new DmpkModelliDocExtractvermodello();
		
		StoreResultBean<DmpkModelliDocExtractvermodelloBean> output = dmpkModelliDocExtractvermodello.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		bean.setNomeFileModello(output.getResultBean().getDisplayfilenameverout());
		bean.setUriFileModello(output.getResultBean().getUriverout());
		
		if (bean.getTipoModello() != null && bean.getTipoModello().equalsIgnoreCase("odt_con_freemarkers")) {
			logger.debug("#######INIZIO (in extractVerModello) caricamento manuale mimeTypeModello#######");
			logger.debug("#######INIZIO (in extractVerModello) estrazione file per caricamento manuale mimeTypeModello#######");
			File fileModello = StorageImplementation.getStorage().extractFile(output.getResultBean().getUriverout());
			logger.debug("#######FINE (in extractVerModello) estrazione file per caricamento manuale mimeTypeModello#######");
			MimeTypeFirmaBean mimeTypeModelloBean = new MimeTypeFirmaBean();
			mimeTypeModelloBean.setMimetype("application/vnd.oasis.opendocument.text");
			mimeTypeModelloBean.setCorrectFileName(output.getResultBean().getDisplayfilenameverout());
			mimeTypeModelloBean.setConvertibile(true);
			mimeTypeModelloBean.setAlgoritmo("SHA-256");
			mimeTypeModelloBean.setEncoding("base64");
			mimeTypeModelloBean.setImpronta((new CalcolaImpronteService()).calcolaImprontaWithoutFileOp(fileModello, "SHA-256", "base64"));
			mimeTypeModelloBean.setBytes(fileModello.length());
			bean.setInfoFileModello(mimeTypeModelloBean);
			logger.debug("#######FINE (in extractVerModello) caricamento manuale mimeTypeModello#######");
		} else {
			logger.debug("#######INIZIO (in extractVerModello) fileModello#######");
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			logger.debug("#######INIZIO (in extractVerModello) extractFile#######");
			File fileModello = StorageImplementation.getStorage().extractFile(output.getResultBean().getUriverout());
			logger.debug("#######FINE (in extractVerModello) extractFile#######");
			logger.debug("#######INIZIO (in extractVerModello) getInfoFromFile#######");
			MimeTypeFirmaBean mimeTypeModelloBean = lInfoFileUtility.getInfoFromFile(fileModello.toURI().toString(), output.getResultBean().getDisplayfilenameverout(), false, null);
			logger.debug("#######FINE (in extractVerModello) getInfoFromFile#######");
			bean.setInfoFileModello(mimeTypeModelloBean);
			logger.debug("#######FINE (in extractVerModello) fileModello#######");
		}
		
		return bean;
	}
	
	@Override
	public ModelliDocBean add(ModelliDocBean bean) throws Exception {       		

		AurigaLoginBean loginBean = getLoginBean();
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DmpkModelliDocIumodelloBean input = new DmpkModelliDocIumodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setNomein(bean.getNomeModello());
		input.setDescrizionein(bean.getDesModello());		
		input.setTipoenitaassociatain(bean.getTipoEntitaAssociata());
		input.setIdentitaassociatain(StringUtils.isNotBlank(bean.getIdEntitaAssociata()) ? new BigDecimal(bean.getIdEntitaAssociata()) : null);
		input.setAnnotazioniin(bean.getNote());
		input.setFlggeneraformatopdfin(bean.getFlgGeneraPdf() != null && bean.getFlgGeneraPdf() ? new BigDecimal("1") : new BigDecimal("0"));	
		input.setTipomodelloin(bean.getTipoModello()); 		
//		input.setAttributiaddin(null);
		
		DmpkModelliDocIumodello dmpkModelliDocIumodelloBean = new DmpkModelliDocIumodello();
		
		StoreResultBean<DmpkModelliDocIumodelloBean> output = dmpkModelliDocIumodelloBean.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		bean.setIdModello(output.getResultBean().getIdmodelloio() != null ? String.valueOf(output.getResultBean().getIdmodelloio().longValue()) : null);
		bean.setIdDoc(output.getResultBean().getIddocout() != null ? String.valueOf(output.getResultBean().getIddocout().longValue()) : null);
		bean.setIdDocHtml(output.getResultBean().getIddochtmlout() != null ? String.valueOf(output.getResultBean().getIddochtmlout().longValue()) : null);
		
		AllegatoProtocolloBean fileModello = getFileModello(bean);
		
		if(fileModello != null && StringUtils.isNotBlank(fileModello.getUriFileAllegato())) {
			if (bean.getTipoModello() != null && TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equalsIgnoreCase(bean.getTipoModello())) {
				versionaDocModello(bean, fileModello);
				// Se va in errore la creazione del file html non blocco il salvataggio del modello, ma do un avviso che la profilatura non è stata creata correttamente
				try {
					LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustom = new LinkedHashMap<String, AssociazioniAttributiCustomBean>(); 
					String uriFileHtml = storeFileHtmlFromOdt(fileModello.getUriFileAllegato(), mappaAssociazioniAttributiCustom);						
					String nomeFileHtml = FilenameUtils.getBaseName(fileModello.getNomeFileAllegato()) + ".html";
					versionaDocHtml(bean, uriFileHtml, nomeFileHtml); //TODO Da togliere perchè l'html dev'essere comunque rigenerato ad ogni profilatura			
					salvaInputFileModello(bean, mappaAssociazioniAttributiCustom);
				} catch (Exception e) {
					logger.error("Errore non bloccante durante la creazione della profilatura", e);
					addMessage("Non è stato possibile creare la profilatura del modello", "", MessageType.WARNING);
				}
			} else {
				versionaDocModello(bean, fileModello);
			}
		}

		return bean;				
	}
	
	@Override
	public ModelliDocBean update(ModelliDocBean bean, ModelliDocBean oldvalue) throws Exception {

		AurigaLoginBean loginBean =  getLoginBean();
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DmpkModelliDocIumodelloBean input = new DmpkModelliDocIumodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdmodelloio(StringUtils.isNotBlank(bean.getIdModello()) ? new BigDecimal(bean.getIdModello()) : null);		
			
		input.setNomein(bean.getNomeModello());
		input.setDescrizionein(bean.getDesModello());		
		input.setTipoenitaassociatain(bean.getTipoEntitaAssociata());
		input.setIdentitaassociatain(StringUtils.isNotBlank(bean.getIdEntitaAssociata()) ? new BigDecimal(bean.getIdEntitaAssociata()) : null);
		input.setAnnotazioniin(bean.getNote());
		input.setFlggeneraformatopdfin(bean.getFlgGeneraPdf() != null && bean.getFlgGeneraPdf() ? new BigDecimal("1") : new BigDecimal("0"));	
		input.setTipomodelloin(bean.getTipoModello()); 							
//		input.setAttributiaddin(null);
		
		DmpkModelliDocIumodello dmpkModelliDocIumodelloBean = new DmpkModelliDocIumodello();
		
		StoreResultBean<DmpkModelliDocIumodelloBean> output = dmpkModelliDocIumodelloBean.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		bean.setIdDoc(output.getResultBean().getIddocout() != null ? String.valueOf(output.getResultBean().getIddocout().longValue()) : null);
		bean.setIdDocHtml(output.getResultBean().getIddochtmlout() != null ? String.valueOf(output.getResultBean().getIddochtmlout().longValue()) : null);
		
		AllegatoProtocolloBean fileModello = getFileModello(bean);
		if(fileModello != null && StringUtils.isNotBlank(fileModello.getUriFileAllegato())) {
			if(fileModello.getIsChanged() != null && fileModello.getIsChanged()) {				
				if (bean.getTipoModello() != null && TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equalsIgnoreCase(bean.getTipoModello())) {
					LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustom = new LinkedHashMap<String, AssociazioniAttributiCustomBean>(); 
					String uriFileHtml = storeFileHtmlFromOdt(fileModello.getUriFileAllegato(), mappaAssociazioniAttributiCustom);							
					String nomeFileHtml = FilenameUtils.getBaseName(fileModello.getNomeFileAllegato()) + ".html";					
					versionaDocModello(bean, fileModello);					
					versionaDocHtml(bean, uriFileHtml, nomeFileHtml); //TODO Da togliere perchè l'html dev'essere comunque rigenerato ad ogni profilatura
					salvaInputFileModello(bean, mappaAssociazioniAttributiCustom);
				} else {
					versionaDocModello(bean, fileModello);										
				}
			}
		}
		
		return bean;
	}

	@Override
	public ModelliDocBean remove(ModelliDocBean bean) throws Exception {

		AurigaLoginBean loginBean = getLoginBean();
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DmpkModelliDocDmodelloBean input = new DmpkModelliDocDmodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdmodelloin(new BigDecimal(bean.getIdModello()));		
		input.setFlgcancfisicain(0);
		
		//TODO passare i motivi dell'annullamento alla store
		input.setMotiviin(null);
			
		DmpkModelliDocDmodello dmpkModelliDocDmodello = new DmpkModelliDocDmodello();
		
		StoreResultBean<DmpkModelliDocDmodelloBean> output = dmpkModelliDocDmodello.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		return bean;
	}	
	
	private AllegatoProtocolloBean getFileModello(ModelliDocBean bean) {
		
		if(bean.getListaModelli() != null && bean.getListaModelli().size() > 0) {			
			return bean.getListaModelli().get(0);
		}		
		
		return null;
	}	
	
	// Verifica se la profilatura del modello è completa
	// Le variabili semplici sono profilate se hanno un tipo
	// Le variabili complesse sono profilate se hanno un tipo e un numero colonna
	private boolean isProfilaturaCompleta(List<InputModuliCompBean> listaInputModuliCompBean) throws Exception {
		if(listaInputModuliCompBean != null) {
			
			//Creo una lista degli attributi complessi
			List<String> listaAttributiComplessi = new ArrayList<>();
			for(InputModuliCompBean lInputModuliCompBean : listaInputModuliCompBean) {
				if(lInputModuliCompBean.getAttrType() != null && "COMPLEX".equalsIgnoreCase(lInputModuliCompBean.getAttrType())) {
					String nomeVariabile = lInputModuliCompBean.getNomeInput();
					String aliasVariabileModello = nomeVariabile.substring(nomeVariabile.indexOf("|") + 1).trim();
					listaAttributiComplessi.add(aliasVariabileModello);			
				}
			}
			
			// Verifico che tutti gli attributi siano profilati
			for(InputModuliCompBean lInputModuliCompBean : listaInputModuliCompBean) {
				// Tutte le associazioni devono avere un tipo
				String attrType = lInputModuliCompBean.getAttrType();
				if (StringUtils.isNotBlank(lInputModuliCompBean.getAttrName()) && StringUtils.isBlank(attrType)) {
					return false;
				}
				String nomeInput = lInputModuliCompBean.getNomeInput();
				for (String nomeAttributoComplesso : listaAttributiComplessi) {
					if (nomeInput != null && nomeInput.startsWith(nomeAttributoComplesso + ".")) {
						if (StringUtils.isNotBlank(lInputModuliCompBean.getAttrName()) && lInputModuliCompBean.getNroColonna() == null) {
							return false;
						}
					}
				}
			}
		
		}
		
		return true;	
	}
	
	// Versiona il documento del modello	
	private void versionaDocModello(ModelliDocBean bean, AllegatoProtocolloBean fileModello) throws Exception {					
		
		AurigaLoginBean loginBean = getLoginBean();
		
		if(StringUtils.isNotBlank(bean.getIdDoc()) && !"0".equals(bean.getIdDoc())) {		
			GenericFile lGenericFile = new GenericFile();	
			setProprietaGenericFile(lGenericFile, fileModello.getInfoFile());
			lGenericFile.setMimetype(fileModello.getInfoFile().getMimetype());
			lGenericFile.setDisplayFilename(fileModello.getNomeFileAllegato());
			lGenericFile.setImpronta(fileModello.getInfoFile().getImpronta());
			lGenericFile.setImprontaFilePreFirma(fileModello.getInfoFile().getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(fileModello.getInfoFile().getAlgoritmo());
			lGenericFile.setEncoding(fileModello.getInfoFile().getEncoding());		
			
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);					
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);	
			
			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdDoc()));
			lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(fileModello.getUriFileAllegato()));					
			lRebuildedFile.setInfo(lFileInfoBean);	
			
			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 					
			
			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);					
			if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
				logger.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutput);
			}	
		}
	}
	
	// Versiona il documento con l'html generato a partire dall'odt		
	private void versionaDocHtml(ModelliDocBean bean, String uriFileHtml, String nomeFileHtml) throws Exception {	
		
		AurigaLoginBean loginBean = getLoginBean();
		
		if(StringUtils.isNotBlank(bean.getIdDocHtml()) && !"0".equals(bean.getIdDocHtml())) {		
			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdDocHtml()));
			lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriFileHtml));
			
			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);		
			
			GenericFile lGenericFile = new GenericFile();		
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(nomeFileHtml);
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
			lGenericFile.setEncoding(lMimeTypeFirmaBean.getEncoding());	
			
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);					
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);									
			lRebuildedFile.setInfo(lFileInfoBean);		
			
			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 		
			
			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);		
			if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
				logger.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutput);
			}		
		}
	}
	
	// Salva le associazioni in DmtInputModuliComp facendo il match con quelle già presenti (in caso di modifica)
	private void salvaInputFileModello(ModelliDocBean bean, LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustom) throws Exception {		
				
		AurigaLoginBean loginBean = getLoginBean();		
		List<AttributoBean> attributiAdd = new ArrayList<AttributoBean>();
		HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista = new HashMap<String, List<DettColonnaAttributoListaBean>>();
		
		if (StringUtils.isNotBlank(bean.getNomeTabella()) || StringUtils.isNotBlank(bean.getIdEntitaAssociata())) {
			// Recupero gli attributi custom associati
			AttributiDinamiciInputBean input = new AttributiDinamiciInputBean();
			input.setNomeTabella(bean.getNomeTabella());
			input.setTipoEntita(bean.getIdEntitaAssociata());
			
			AttributiDinamiciDatasource attributiDS = new AttributiDinamiciDatasource();
			attributiDS.setSession(getSession());
			attributiDS.setLoginBean(getLoginBean());
			attributiDS.setExtraparams(new LinkedHashMap<String, String>());
			
			AttributiDinamiciOutputBean output = attributiDS.call(input);	
			attributiAdd = output.getAttributiAdd() != null ? output.getAttributiAdd() : new ArrayList<AttributoBean>();
			mappaDettAttrLista = output.getMappaDettAttrLista() != null ? output.getMappaDettAttrLista() : new HashMap<String, List<DettColonnaAttributoListaBean>>();
		}
		
		try {
			
			List<InputModuliCompBean> listaInputModuliCompBean = new ArrayList<InputModuliCompBean>();
			if(mappaAssociazioniAttributiCustom != null) {
				for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : mappaAssociazioniAttributiCustom.values()) {
					listaInputModuliCompBean.add(buildInputModuliCompBeanFromAssociazioniAttributiCustomBean(bean.getIdModello(), lAssociazioniAttributiCustomBean, false, "",  attributiAdd, mappaDettAttrLista));
					if (lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()){
						for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
							listaInputModuliCompBean.add(buildInputModuliCompBeanFromAssociazioniAttributiCustomBean(bean.getIdModello(), lAssociazioniSottoAttributiComplexBean, true, lAssociazioniAttributiCustomBean.getNomeAttributoCustom(), attributiAdd, mappaDettAttrLista));							
						}
					}
				}
			}
			
			DaoDmtInputModuliComp lDaoDmtInputModuliComp = new DaoDmtInputModuliComp();		
			
			TSerializableList<InputModuliCompBean> lTSerializableList = new TSerializableList<InputModuliCompBean>();
			lTSerializableList.setData(getSerializableList(listaInputModuliCompBean));
			lDaoDmtInputModuliComp.salvainputfilemodello(getLocale(), loginBean, bean.getIdModello(), lTSerializableList);

			TFilterFetch<InputModuliCompBean> filter = new TFilterFetch<InputModuliCompBean>();
			InputModuliCompBean filterBean = new InputModuliCompBean();
			filterBean.setIdModello(StringUtils.isNotBlank(bean.getIdModello()) ? new BigDecimal(bean.getIdModello()) : null);
			filter.setFilter(filterBean);				
			TPagingList<InputModuliCompBean> lTPagingListInputModuliComp = lDaoDmtInputModuliComp.search(getLocale(), loginBean, filter);			
			bean.setFlgProfCompleta(isProfilaturaCompleta(lTPagingListInputModuliComp.getData()));
			
			aggiornaFlgProfCompletaModello(bean);			
			
		} catch(Exception e) {
			throw new Exception("Si è verificato un errore durante il salvataggio delle variabili input del modello", e);
		}			
	}
	
	private AssociazioniAttributiCustomBean buildAssociazioniAttributiCustomBeanFromInputModuliCompBean(InputModuliCompBean lInputModuliCompBean) {
		
		String nomeVariabile = lInputModuliCompBean.getNomeInput().trim();
		AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = new AssociazioniAttributiCustomBean();
		if(nomeVariabile != null && nomeVariabile.contains("|")) {
			lAssociazioniAttributiCustomBean.setNomeVariabileModello(nomeVariabile.substring(0, nomeVariabile.indexOf("|")).trim());
			lAssociazioniAttributiCustomBean.setAliasVariabileModello(nomeVariabile.substring(nomeVariabile.indexOf("|") + 1).trim());
		} else {
			lAssociazioniAttributiCustomBean.setNomeVariabileModello(nomeVariabile);
			lAssociazioniAttributiCustomBean.setAliasVariabileModello(null);
		}
		if (StringUtils.isNotBlank(lInputModuliCompBean.getAttrName())) {
			lAssociazioniAttributiCustomBean.setTipoAssociazioneVariabileModello("attributoCustom");
			lAssociazioniAttributiCustomBean.setNomeAttributoCustom(lInputModuliCompBean.getAttrName());
			lAssociazioniAttributiCustomBean.setTipoAttributo(lInputModuliCompBean.getAttrType());
			lAssociazioniAttributiCustomBean.setNroColonna(lInputModuliCompBean.getNroColonna());
		} else {
			lAssociazioniAttributiCustomBean.setTipoAssociazioneVariabileModello("attributoLibero");
			if(nomeVariabile != null && nomeVariabile.contains("|")) {
				lAssociazioniAttributiCustomBean.setNomeAttributoLibero(nomeVariabile.substring(0, nomeVariabile.indexOf("|")).trim());
			} else {
				lAssociazioniAttributiCustomBean.setNomeAttributoLibero(nomeVariabile);
			}
			lAssociazioniAttributiCustomBean.setTipoAttributo(StringUtils.isNotBlank(lInputModuliCompBean.getAttrType()) ? lInputModuliCompBean.getAttrType() : "TEXT-BOX");
			// Setto il numero di colonna, se non è presente lo ricavo dal nome dell'attributo
			if (lInputModuliCompBean.getNroColonna() != null) {
				lAssociazioniAttributiCustomBean.setNumeroColonnaAttributoLibero(lInputModuliCompBean.getNroColonna().intValue());
				lAssociazioniAttributiCustomBean.setNroColonna(lInputModuliCompBean.getNroColonna());
			} else {
				// Ricavo l'eventuale numero di colonna dal nome del campo
				String nomeAttributoLibero = lAssociazioniAttributiCustomBean.getNomeAttributoLibero().toUpperCase();
				if (StringUtils.isNotBlank(nomeAttributoLibero) && nomeAttributoLibero.contains(".COL")) {
					try {
						int numColAttributo = Integer.parseInt(nomeAttributoLibero.substring(nomeAttributoLibero.indexOf(".COL") + 4, nomeAttributoLibero.length()));
						lAssociazioniAttributiCustomBean.setNumeroColonnaAttributoLibero(numColAttributo);
						lAssociazioniAttributiCustomBean.setNroColonna(BigDecimal.valueOf(numColAttributo));
					} catch (Exception e) {
						logger.error("Errore nella conversione del numero di colonna: " + nomeAttributoLibero + " -> " + nomeAttributoLibero.substring(nomeAttributoLibero.indexOf(".COL")));
					}					
				}
			}
		}
		lAssociazioniAttributiCustomBean.setFlgRipetibile(lInputModuliCompBean.getFlgMultivalore());
		lAssociazioniAttributiCustomBean.setFlgComplex(lInputModuliCompBean.getAttrType() != null && "COMPLEX".equalsIgnoreCase(lInputModuliCompBean.getAttrType()));
		lAssociazioniAttributiCustomBean.setFlgImage(lInputModuliCompBean.getAttrType() != null && "IMAGE".equalsIgnoreCase(lInputModuliCompBean.getAttrType()));
		if(lAssociazioniAttributiCustomBean.getFlgImage() != null && lAssociazioniAttributiCustomBean.getFlgImage()) {
			lAssociazioniAttributiCustomBean.setFlgBarcode(lInputModuliCompBean.getFlgBarcode());
			lAssociazioniAttributiCustomBean.setTipoBarcode(lInputModuliCompBean.getTipoBarcode());							
		}
		
		return lAssociazioniAttributiCustomBean;
	}
	
	private InputModuliCompBean buildInputModuliCompBeanFromAssociazioniAttributiCustomBean(String idModello, AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean, boolean isSottoAttributo, String nomeAttributoPadre, List<AttributoBean> attributiAdd, HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista) {
		
		InputModuliCompBean lInputModuliCompBean = new InputModuliCompBean();
		lInputModuliCompBean.setIdModello(new BigDecimal(idModello));				
		// Verifico se è una variabile con alias (ad esempio le liste)
		if(StringUtils.isNotBlank(lAssociazioniAttributiCustomBean.getAliasVariabileModello())) {
			lInputModuliCompBean.setNomeInput(lAssociazioniAttributiCustomBean.getNomeVariabileModello() + " | " + lAssociazioniAttributiCustomBean.getAliasVariabileModello());
		} else {
			lInputModuliCompBean.setNomeInput(lAssociazioniAttributiCustomBean.getNomeVariabileModello());
		}
		
		// Setto attrName
		if ("attributoCustom".equalsIgnoreCase(lAssociazioniAttributiCustomBean.getTipoAssociazioneVariabileModello())) {
			// Sono associato ad un attributo custom
			lInputModuliCompBean.setAttrName(lAssociazioniAttributiCustomBean.getNomeAttributoCustom());
			// Se è un sotto attributo recupero anche il numero della colonna dell'attributo custom
			if (isSottoAttributo) {
				// Ottengo il dettaglio colonne dell'attributo custom associato
				List<DettColonnaAttributoListaBean> listaDettaglioColonne = mappaDettAttrLista.get(nomeAttributoPadre);
				// Scorro tutte le colonne fino a trovare quella relativa all'attributo associato
				for (DettColonnaAttributoListaBean dettaglioColonna : listaDettaglioColonne) {
					if (dettaglioColonna.getNome() != null && dettaglioColonna.getNome().equalsIgnoreCase(lAssociazioniAttributiCustomBean.getNomeAttributoCustom())) {
						lInputModuliCompBean.setNroColonna(dettaglioColonna.getNumero() != null ? new BigDecimal(dettaglioColonna.getNumero()) : null);
					}
				}
			}
		} else {
			// sono associato ad un attributo libero
			lInputModuliCompBean.setAttrName("");
			// Se è un sotto attributo recupero il numero della colonna dai dati a maschera
			if (isSottoAttributo) {
				lInputModuliCompBean.setNroColonna(lAssociazioniAttributiCustomBean.getNumeroColonnaAttributoLibero() != null ? new BigDecimal(lAssociazioniAttributiCustomBean.getNumeroColonnaAttributoLibero()) : null);
			}
		}
		
		// Setto i dati sul tipo
		lInputModuliCompBean.setFlgMultivalore(lAssociazioniAttributiCustomBean.getFlgRipetibile());
		if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {	 							
			lInputModuliCompBean.setAttrType("COMPLEX");
			lInputModuliCompBean.setFlgBarcode(false);
			lInputModuliCompBean.setTipoBarcode(null);
		} else if(lAssociazioniAttributiCustomBean.getFlgImage() != null && lAssociazioniAttributiCustomBean.getFlgImage()) {
			lInputModuliCompBean.setAttrType("IMAGE");
			lInputModuliCompBean.setFlgBarcode(lAssociazioniAttributiCustomBean.getFlgBarcode() != null ? lAssociazioniAttributiCustomBean.getFlgBarcode() : false);
			lInputModuliCompBean.setTipoBarcode(lAssociazioniAttributiCustomBean.getTipoBarcode());								
		}  else {
			// Se è un attributo custom leggo il tipo da attributiAdd, altrimenti tengo quello di lAssociazioniAttributiCustomBean
			lInputModuliCompBean.setAttrType(lAssociazioniAttributiCustomBean.getTipoAttributo());
			if ("attributoCustom".equalsIgnoreCase(lAssociazioniAttributiCustomBean.getTipoAssociazioneVariabileModello())) {
				if (!isSottoAttributo && (lAssociazioniAttributiCustomBean.getFlgRipetibile() == null || !Boolean.valueOf(lAssociazioniAttributiCustomBean.getFlgRipetibile()))) {
					// Se non si tratta di un sotto attributo o di una lista leggo il tipo da attributiAdd
					for (AttributoBean attributoBean : attributiAdd) {
						if (attributoBean.getNome() != null && attributoBean.getNome().equalsIgnoreCase(lAssociazioniAttributiCustomBean.getNomeAttributoCustom())) {
							lInputModuliCompBean.setAttrType(attributoBean.getTipo());
						}
					}
				} else {
					// Se si tratta di un sotto attributo o di una lista leggo il tipo da mappaDettAttrLista
					List<DettColonnaAttributoListaBean> listaDettaglioColonne;
					if (lAssociazioniAttributiCustomBean.getFlgRipetibile()) {
						listaDettaglioColonne = mappaDettAttrLista.get(lAssociazioniAttributiCustomBean.getNomeAttributoCustom());
					} else {
						listaDettaglioColonne = mappaDettAttrLista.get(nomeAttributoPadre);
					}
					for (DettColonnaAttributoListaBean dettaglioColonna : listaDettaglioColonne) {
						if (dettaglioColonna.getNome() != null && dettaglioColonna.getNome().equalsIgnoreCase(lAssociazioniAttributiCustomBean.getNomeAttributoCustom())) {
							lInputModuliCompBean.setAttrType(dettaglioColonna.getTipo());
						}
					}
				}
			} 
			lInputModuliCompBean.setFlgBarcode(false);
			lInputModuliCompBean.setTipoBarcode(null);
		}
		
		return lInputModuliCompBean;
	}
	
	private String storeFileHtmlFromOdt(String uriFileModello, LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustom) throws Exception {
		
		if(mappaAssociazioniAttributiCustom == null) {
			mappaAssociazioniAttributiCustom = new LinkedHashMap<String, AssociazioniAttributiCustomBean>(); 
		}
		
		File templateOdt = File.createTempFile("temp", ".odt");
		FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(uriFileModello), templateOdt);
		recuperaImmaginiOdt(templateOdt);
		File templateHtml = File.createTempFile("temp", ".html");						
		
		try {
			OpenOfficeConverter.newInstance().convertByOutExt(templateOdt, "application/vnd.oasis.opendocument.text", templateHtml, "html");
		} catch (Exception e) {
			logger.error("Errore durante la conversione con OpenOffice", e);
			throw new StoreException("Il servizio di conversione da odt in html è momentaneamente non disponibile. Se il problema persiste contattare l'assistenza");
		}	
		
		String html = FileUtils.readFileToString(templateHtml);
		html = html.replace(EOT, NUL);
		html = html.replace(ENQ, NUL);		
		html = replaceAllFreemarkersAndScripts(html, mappaAssociazioniAttributiCustom);
		
		Document htmlDocument = Jsoup.parse(html);
		String charset = getCharsetFromHtmlDocument(htmlDocument);		
		
		Elements divElements = htmlDocument.getElementsByTag("div");
		for(Element div : divElements){					
			if(div.attr("type").toLowerCase().contains("header")) {
				div.before(getHtmlSeparatorLineWithText("intestazione"));
				div.after(getHtmlSeparatorLine());
			} 
			if(div.attr("type").toLowerCase().contains("footer")) {
				div.before(getHtmlSeparatorLineWithText("piè di pagina"));
				div.after(getHtmlSeparatorLine());
			}
		}
		
		Elements pElements = htmlDocument.getElementsByTag("p");
		for(Element p : pElements){					
			if(p.attr("style").toLowerCase().replace(" ", "").contains("page-break-before:always")) {
				p.before(getHtmlSeparatorLine());
			} 
			if(p.attr("style").toLowerCase().replace(" ", "").contains("page-break-after:always")) {
				p.after(getHtmlSeparatorLine());
			}
		}
		
		html = htmlDocument.html();			
		FileUtils.writeStringToFile(templateHtml, html, charset); //TODO bisogna mettere il charset corretto
		
		String uriFileHtml = StorageImplementation.getStorage().store(templateHtml);
		
		return uriFileHtml;
	}
	
	private String getHtmlSeparatorLine() {
		return getHtmlSeparatorLineWithText(null);
	}
	
	private String getHtmlSeparatorLineWithText(String text) {
		
		//TODO togliere bordi della tabella, che non è perfettamente allineata con il separatore senza testo
		if(StringUtils.isNotBlank(text)) {		
			return 	"<table>" +
					"<tr>" +
					"<td style=\"width: 50%\"><hr align=\"center\" size=\"1\" noshade></td>" +
					"<td style=\"vertical-align:middle;text-align:center\" rowspan=\"2\"><font size=\"2\" color=\"grey\"><i>" + (text != null ? text.replace(" ", "&nbsp;") : "") + "</i></font></td>" +
					"<td style=\"width: 50%\"><hr align=\"center\" size=\"1\" noshade></td>" +
					"</tr>" +
					"</table>"; 	
		}
		
		return 	"<hr align=\"center\" size=\"1\" noshade>"; 
	}
	
	private void recuperaImmaginiOdt(File fileOdt) throws Exception {
		
		OdfPackage odfPackage = null;
		OdfDocument odfDocument = null;			
        
		try {	        	
        	odfPackage = OdfPackage.loadPackage(fileOdt);  
        	odfDocument = OdfTextDocument.loadDocument(odfPackage);		
        	List<OdfDrawImage> odfDrawImages = OdfDrawImage.getImages(odfDocument);	// org.odftoolkit.odfdom.dom.attribute.fo.FoBreakBeforeAttribute; fo:break-before="page"		
			for(int i = 0; i < odfDrawImages.size(); i++) {
				String fileName = StringUtils.remove(odfDrawImages.get(i).getImageUri().getPath(), "Pictures/");							
				byte[] data = odfPackage.getBytes(odfDrawImages.get(i).getImageUri().getPath());								
				String mimetype = null;
				try {
					mimetype = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));										
				} catch(Exception e) {}							
				if(StringUtils.isBlank(mimetype)) {
					mimetype = "image/*";
				}							
				odfDrawImages.get(i).setImagePath("data:" + mimetype + ";base64," + Base64.encodeBase64String(data));													
			}
			odfDocument.save(fileOdt);
        } finally {
        	if(odfDocument != null) {
        		odfDocument.close();
        	}        	
        	if(odfPackage != null) {
        		odfPackage.close();
        	}        	
        }		
	}
	
	private boolean isTrue(Boolean value) {
		return value != null && value;
	}
	
	private String replaceAllFreemarkersAndScripts(String html, LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustom) throws Exception {		
		
		int pos = 0;
		String str = ""; 
		
		if(StringUtils.isNotBlank(html)) {		
			
			boolean variabiliDiverseConStessoNomeError = false;
			boolean operatoreNonGestitoError = false;
			boolean listeDiverseConStessoAliasError = false;
			boolean listaAliasUgualeNomeVariabileError = false;
			
			List<String> nomiVariabiliLista = new ArrayList<String>();
			HashMap<String, String> mappaAliasVariabiliLista = new HashMap<String, String>();
			while(pos < html.length()) {
				int startFreemarker = html.indexOf(FREEMARKER_INIZIO, pos);
				int startScript = html.toUpperCase().indexOf(TAG_APERTURA_SCRIPT_INIZIO, pos);
				int startImage = html.toUpperCase().indexOf(TAG_APERTURA_IMAGE_INIZIO, pos);
				if(startFreemarker == -1 && startScript == -1 && startImage == -1) {
					str += html.substring(pos);
					break;
				}
				
				// Controllo se il primo attributo è un startFreemarker
				if (controlloSePrimo(startFreemarker, startScript, startImage)){
					int endFreemarker = html.indexOf(FREEMARKER_FINE, startFreemarker);
					if(endFreemarker != -1) {
						String nomeVariabile = html.substring(startFreemarker + FREEMARKER_INIZIO.length(), endFreemarker).trim();
						// Aggiungo la variabile alla lista delle associazioni
						if(!mappaAssociazioniAttributiCustom.keySet().contains(nomeVariabile)) {
							AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = new AssociazioniAttributiCustomBean();
							lAssociazioniAttributiCustomBean.setNomeVariabileModello(nomeVariabile);
							lAssociazioniAttributiCustomBean.setFlgComplex(false);
							lAssociazioniAttributiCustomBean.setFlgRipetibile(false);
							mappaAssociazioniAttributiCustom.put(nomeVariabile, lAssociazioniAttributiCustomBean);	
						} else {
							// Se la variabile esiste già ma è di tipo diverso allora devo dare errore
							AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = mappaAssociazioniAttributiCustom.get(nomeVariabile);
							if(isTrue(lAssociazioniAttributiCustomBean.getFlgComplex()) || isTrue(lAssociazioniAttributiCustomBean.getFlgRipetibile()) || isTrue(lAssociazioniAttributiCustomBean.getFlgImage())) {
								variabiliDiverseConStessoNomeError = true;								 
							}
						}
						int count = mappaAssociazioniAttributiCustom.get(nomeVariabile).getCount();
						mappaAssociazioniAttributiCustom.get(nomeVariabile).setCount(count + 1);						
						str += html.substring(pos, startFreemarker);
						if(StringUtils.isNotBlank(getExtraparams().get("profilaVariabileFunctionName"))) {				
//							if(str.toUpperCase().endsWith("<DIV ID=\"")) { 
//								int endDiv = html.toUpperCase().indexOf(">", endFreemarker + FREEMARKER_FINE.length());
//								str += html.substring(startFreemarker, endDiv + 1);
//								str += String.format("<span style=\"cursor: pointer;\" onclick=\"%s('%s');\">%s</span>", getExtraparams().get("profilaVariabileFunctionName"), nomeVariabile, FREEMARKER_INIZIO + "<u>" + nomeVariabile + "</u>" + FREEMARKER_FINE);
//								pos = endDiv + 1;
//							} else {							
								str += String.format("<span style=\"cursor: pointer;\" onclick=\"%s('%s');\">%s</span>", getExtraparams().get("profilaVariabileFunctionName"), nomeVariabile, FREEMARKER_INIZIO + "<u>" + nomeVariabile + "</u>" + FREEMARKER_FINE);
								pos = endFreemarker + FREEMARKER_FINE.length();
//							}
						} else {
							str += FREEMARKER_INIZIO + nomeVariabile + FREEMARKER_FINE;
							pos = endFreemarker + FREEMARKER_FINE.length();
						}						
					} else {
						pos = startFreemarker + FREEMARKER_INIZIO.length();
					}
				}
				
				// Controllo se il primo attributo è un startScript
				if (controlloSePrimo(startScript, startImage, startFreemarker)){
					int tagAperturaScriptLength = (html.indexOf(TAG_APERTURA_SCRIPT_FINE, startScript) + TAG_APERTURA_SCRIPT_FINE.length()) - startScript;
					int endScript = html.toUpperCase().indexOf(TAG_CHIUSURA_SCRIPT, startScript);
					if(endScript != -1) {						
						String script = html.substring(startScript + tagAperturaScriptLength, endScript).trim();
						script = script.replace("\"", "&quot;");
						script = script.replace("[#if", "****[#if").replace("[/#if", "****[/#if").replace("[#list", "****[#list");
						str += html.substring(pos, endScript + TAG_CHIUSURA_SCRIPT.length());	
						String img = "";
						String[] istruzioni = script.split("\\*\\*\\*\\*");
						for (int i = 0; i < istruzioni.length; i++) {
							script = istruzioni[i];
							boolean scriptRiconosciuto = false;
							if(script.toLowerCase().contains("[#if")) {
								scriptRiconosciuto = true;
								// Estraggo lo script delle condizioni
								int indexInizioCondizione = script.toLowerCase().indexOf("[#if");
								int indexFineCondizione = script.indexOf("]", indexInizioCondizione);
								String scriptCondizione = script.substring(indexInizioCondizione, indexFineCondizione + 1);
								// Splitto la condizione per avere i vari token.
								// Mi serve perchè potrei avere un if composto da più condizioni in && o in ||
								String[] condizioni = scriptCondizione.split("\\s*[&|]+");
								boolean condizioniMultiple = condizioni != null && condizioni.length > 1;
								// Per ogni condizione estreggo il nome della variabile
								for (String condizione : condizioni) {
									condizione = condizione.replace("(", "").replace(")", "").replace("[#if", "").replace("]", "").trim();
									int posOperatore = condizione.indexOf("!=");
									if(posOperatore == -1) {
										posOperatore = condizione.indexOf("=");
										if(posOperatore == -1) {
											operatoreNonGestitoError = true;
										}
									}
									String nomeVariabile = condizione.substring(0, posOperatore).trim();
									// Aggiungo la variabile alla lista delle associazioni
									if(!mappaAssociazioniAttributiCustom.keySet().contains(nomeVariabile)) {
										AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = new AssociazioniAttributiCustomBean();
										lAssociazioniAttributiCustomBean.setNomeVariabileModello(nomeVariabile);
										lAssociazioniAttributiCustomBean.setFlgComplex(false);
										lAssociazioniAttributiCustomBean.setFlgRipetibile(false);
										mappaAssociazioniAttributiCustom.put(nomeVariabile, lAssociazioniAttributiCustomBean);	
									} else {
										// Se la variabile esiste già ma è di tipo diverso allora devo dare errore
										AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = mappaAssociazioniAttributiCustom.get(nomeVariabile);
										if(isTrue(lAssociazioniAttributiCustomBean.getFlgComplex()) || isTrue(lAssociazioniAttributiCustomBean.getFlgRipetibile()) || isTrue(lAssociazioniAttributiCustomBean.getFlgImage())) {
											variabiliDiverseConStessoNomeError = true;									 
										}
									} 
									int count = mappaAssociazioniAttributiCustom.get(nomeVariabile).getCount();
									mappaAssociazioniAttributiCustom.get(nomeVariabile).setCount(count + 1);
									String scriptToInsert = scriptCondizione;
									if (condizioniMultiple) {
										scriptToInsert = scriptCondizione + "   [" + nomeVariabile + "]";
									}
									if(StringUtils.isNotBlank(getExtraparams().get("profilaVariabileFunctionName"))) {								
										img += String.format("<img src=\"images/modelli/scriptModello.png\" style=\"cursor: pointer; " + (condizioniMultiple ? "background:PaleGreen" : "") + "\" title=\"%s\" alt=\"%s\" onclick=\"%s('%s');\" />", scriptToInsert, scriptToInsert, getExtraparams().get("profilaVariabileFunctionName"), nomeVariabile);
									} else {
										img += String.format("<img src=\"images/modelli/scriptModello.png\" style=\"" +  (condizioniMultiple ? "background:PaleGreen" : "") + "\" title=\"%s\" alt=\"%s\" />", scriptToInsert, scriptToInsert);
									}
								}
								script = script.substring(indexFineCondizione);
							}
							if(script.toLowerCase().contains("[#list")) {
								scriptRiconosciuto = true;
								// Estraggo lo script della lista
								int indexInizioList = script.toLowerCase().indexOf("[#list");
								int indexFineList = script.indexOf("]", indexInizioList);
								String scriptList = script.substring(indexInizioList, indexFineList + 1);
								String nomeVariabileLista = null;
								String aliasVariabileLista = null;
								if(scriptList.toLowerCase().contains("as")) {
									nomeVariabileLista = scriptList.substring(scriptList.indexOf("[#list") + "[#list".length(), scriptList.indexOf("as")).trim();
									aliasVariabileLista = scriptList.substring(scriptList.indexOf("as") + "as".length(), scriptList.indexOf("]")).trim();									
								} else if(script.toLowerCase().contains("]")) {
									nomeVariabileLista = script.substring(scriptList.indexOf("[#list") + "[#list".length(), scriptList.indexOf("]")).trim();
								}
								// se è una lista sovrascrivo sempre l'associazione nella mappa
								// se nella mappa c'era una variabile con lo stesso nome della variabileLista (senza il punto) allora vuol dire che era il freemarker relativo all'unica colonna della lista, quindi va sovrascritto
								// IMPORTANTE: nel modello l'alias deve sempre corrispondere con il nome della variabileLista, perchè nel parsing non viene considerato il suo scope
								//             ci potrebbe essere una variabile esterna alla lista che si chiama come l'alias, oppure un'altra lista che utilizza lo stesso alias
	//							if(!mappaAssociazioniAttributiCustom.keySet().contains(nomeVariabileLista)) {
									AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = new AssociazioniAttributiCustomBean();
									lAssociazioniAttributiCustomBean.setNomeVariabileModello(nomeVariabileLista);
									if(StringUtils.isNotBlank(aliasVariabileLista) /** && !aliasVariabileLista.equals(nomeVariabileLista)*/) {
										if(!mappaAliasVariabiliLista.keySet().contains(aliasVariabileLista)) {
											mappaAliasVariabiliLista.put(aliasVariabileLista, nomeVariabileLista);
										} else if(!mappaAliasVariabiliLista.get(aliasVariabileLista).equals(nomeVariabileLista)) {
											listeDiverseConStessoAliasError = true;										
										}
										lAssociazioniAttributiCustomBean.setAliasVariabileModello(aliasVariabileLista);																		
									} else {
										lAssociazioniAttributiCustomBean.setAliasVariabileModello(null);
									}
									lAssociazioniAttributiCustomBean.setFlgComplex(true);
									lAssociazioniAttributiCustomBean.setFlgRipetibile(true);
									mappaAssociazioniAttributiCustom.put(nomeVariabileLista, lAssociazioniAttributiCustomBean);
									nomiVariabiliLista.add(nomeVariabileLista);
	//							} 																	
								if(StringUtils.isNotBlank(getExtraparams().get("profilaVariabileFunctionName"))) {								
									img += String.format("<img src=\"images/modelli/scriptModello.png\" style=\"cursor: pointer;\" title=\"%s\" alt=\"%s\" onclick=\"%s('%s');\" />", scriptList, scriptList, getExtraparams().get("profilaVariabileFunctionName"), nomeVariabileLista);
								} else {
									img += String.format("<img src=\"images/modelli/scriptModello.png\" title=\"%s\" alt=\"%s\" />", scriptList, scriptList);
								}
								script = script.substring(indexFineList);
							}
							if(script.toLowerCase().contains("@table:table-row")) {
								// Script che non devono essere visualizzati nell'anteprima html perchè fanno confusione
								// Si tratta di porzioni non rilevanti
								scriptRiconosciuto = true;
							}
							if (StringUtils.isNotBlank(script) && !scriptRiconosciuto){	
								// Si tratta di uno script generico non riconosciuto (ad esempio la chiusira di un if)
								// che voglio comunque visualizzare nell'anteprima
								img += String.format("<img src=\"images/modelli/scriptModello.png\" title=\"%s\" alt=\"%s\" />", script, script);
							}
						}
						if(img != null) {
							str += img;	
						}
						pos = endScript + TAG_CHIUSURA_SCRIPT.length();		
					} else {
						pos = startScript + tagAperturaScriptLength;
					}
				} 	
				
				// Controllo se il primo attributo è un image
				if (controlloSePrimo(startImage, startFreemarker, startScript)){
					int endImage = html.toUpperCase().indexOf(TAG_APERTURA_IMAGE_FINE, startImage);
					String imageAttribute = html.substring(startImage, endImage).trim();
					// Verifico se all'interno drgli attributi di img ho un jooscriptImage
					int jooscriptImageStart = imageAttribute.toUpperCase().indexOf(FREEMARKER_IMAGE_INIZIO); 
					int jooscriptImageEnd = imageAttribute.toUpperCase().indexOf(FREEMARKER_IMAGE_FINE, jooscriptImageStart);
					if(jooscriptImageStart != -1 && jooscriptImageEnd != -1) { 
						String nomeVariabile = imageAttribute.substring(jooscriptImageStart + FREEMARKER_IMAGE_INIZIO.length(), jooscriptImageEnd).trim();
						String immagineAlt = FREEMARKER_IMAGE_INIZIO.toLowerCase() + nomeVariabile + FREEMARKER_IMAGE_FINE.toLowerCase();
						if(!mappaAssociazioniAttributiCustom.keySet().contains(nomeVariabile)) {
							AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = new AssociazioniAttributiCustomBean();
							lAssociazioniAttributiCustomBean.setNomeVariabileModello(nomeVariabile);
							lAssociazioniAttributiCustomBean.setFlgComplex(false);
							lAssociazioniAttributiCustomBean.setFlgRipetibile(false);
							lAssociazioniAttributiCustomBean.setFlgImage(true);
							mappaAssociazioniAttributiCustom.put(nomeVariabile, lAssociazioniAttributiCustomBean);	
						} else {
							//TODO se esiste già ed è di tipo diverso allora devo dare errore
							AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = mappaAssociazioniAttributiCustom.get(nomeVariabile);
							if(isTrue(lAssociazioniAttributiCustomBean.getFlgComplex()) || isTrue(lAssociazioniAttributiCustomBean.getFlgRipetibile()) || !isTrue(lAssociazioniAttributiCustomBean.getFlgImage())) {
								variabiliDiverseConStessoNomeError = true;
							}
						}
						int count = mappaAssociazioniAttributiCustom.get(nomeVariabile).getCount();
						mappaAssociazioniAttributiCustom.get(nomeVariabile).setCount(count + 1);						
						String imageAttrToAdd = "";
						if(StringUtils.isNotBlank(getExtraparams().get("profilaVariabileFunctionName"))) {
							imageAttrToAdd = String.format("title=\"%s\" alt=\"%s\" onclick=\"%s('%s');\" style=\"cursor:pointer;cursor:hand;\"", nomeVariabile, immagineAlt, getExtraparams().get("profilaVariabileFunctionName"), nomeVariabile);
						} else {
							imageAttrToAdd = String.format("title=\"%s\" alt=\"%s\"", nomeVariabile, immagineAlt);
						}
						str += html.substring(pos, endImage) + imageAttrToAdd + TAG_APERTURA_IMAGE_FINE;
					} 
					pos = endImage + 1;
				}
			}
						
			List<String> nomiSottoVariabiliListaDaRimuovere = new ArrayList<String>();
			for(String nomeVariabileLista : nomiVariabiliLista) {
				AssociazioniAttributiCustomBean variabileLista = mappaAssociazioniAttributiCustom.get(nomeVariabileLista);
				String aliasVariabileLista = variabileLista.getAliasVariabileModello();
				if(variabileLista.getListaAssociazioniSottoAttributiComplex() == null) {
					variabileLista.setListaAssociazioniSottoAttributiComplex(new ArrayList<AssociazioniAttributiCustomBean>());	
				}
				for(String nomeVariabile : mappaAssociazioniAttributiCustom.keySet()) {
					if(StringUtils.isNotBlank(aliasVariabileLista) && nomeVariabile.startsWith(aliasVariabileLista + ".")) {
						variabileLista.getListaAssociazioniSottoAttributiComplex().add(mappaAssociazioniAttributiCustom.get(nomeVariabile));
						nomiSottoVariabiliListaDaRimuovere.add(nomeVariabile);
					} else if(nomeVariabile.startsWith(nomeVariabileLista + ".")) {
						variabileLista.getListaAssociazioniSottoAttributiComplex().add(mappaAssociazioniAttributiCustom.get(nomeVariabile));
						nomiSottoVariabiliListaDaRimuovere.add(nomeVariabile);
					}
				}				
				// se non ho sotto-attributi vuol dire che quella variabile lista non è di tipo COMPLEX ma un attributo semplice ripetibile 
				if(variabileLista.getListaAssociazioniSottoAttributiComplex().size() == 0) {
					variabileLista.setFlgComplex(false);
					if(StringUtils.isNotBlank(aliasVariabileLista) && mappaAssociazioniAttributiCustom.keySet().contains(aliasVariabileLista)) {
						// se il count sulla variabile che ha il nome uguale all'alias della variabile lista è uguale a 1 allora il freemarker è quello relativo all'unica colonna della lista perciò lo devo rimuovere dalla associazioni
						// altrimenti vuol dire che ho una variabili esterna alla lista con il nome uguale all'alias della lista, quindi la devo tenere in mappa per profilarla
						if(mappaAssociazioniAttributiCustom.get(aliasVariabileLista).getCount() == 1) {
							nomiSottoVariabiliListaDaRimuovere.add(aliasVariabileLista);	
						} else if(mappaAssociazioniAttributiCustom.get(aliasVariabileLista).getCount() > 1) {
							listaAliasUgualeNomeVariabileError = true;																
						}
					}
				} else {
					if(StringUtils.isNotBlank(aliasVariabileLista) && mappaAssociazioniAttributiCustom.keySet().contains(aliasVariabileLista)) {
						if(mappaAssociazioniAttributiCustom.get(aliasVariabileLista).getCount() > 0) {
							listaAliasUgualeNomeVariabileError = true;	
						}
					}
				}
			}	
			
			for(String nomeSottoVariabileLista : nomiSottoVariabiliListaDaRimuovere) {
				mappaAssociazioniAttributiCustom.remove(nomeSottoVariabileLista);
			}
			
			//Gestione errori profilatura (non cambiare l'ordine di questi controlli in quanto alcuni errori potrebbero ricadere anche in altri indi per cui dev'essere rispettata la gerarchia)
			if(listeDiverseConStessoAliasError) {
				throw new Exception("Non posso utilizzare lo stesso alias (della riga) su due liste diverse");				
			} else if(listaAliasUgualeNomeVariabileError) {
				throw new Exception("Non posso utilizzare come alias (della riga) di una lista un nome già utilizzato da un''altra variabile");
			} else if(variabiliDiverseConStessoNomeError) {
				throw new Exception("Non posso inserire nel modello due variabili di tipo diverso con lo stesso nome");
			} else if(operatoreNonGestitoError) {
				throw new Exception("L''operatore utilizzato nella if condition non è gestito");				
			}
						
		}
		
		return str;
	}
	
	/**
	 * Verifica se posizione è l'intero più piccolo degli interi contenuti in posizioniDiConfronto maggiori di -1
	 * @param posizione: la posizione da verificare
	 * @param posizioniDiConfronto: elenco delle posizioni con cui confrontare posizione
	 * @return true se posizione è l'intero più piccolo rispetto a quelli contenuti in posizioniDiConfronto maggiori di -1
	 */
	private boolean controlloSePrimo(int posizione, int... posizioniDiConfronto) {
		
		if (posizione < 0) {
			return false;
		} else if (posizioniDiConfronto == null || posizioniDiConfronto.length == 0) {
			return true;
		}
		
		boolean result = true;
		for (int i = 0; i < posizioniDiConfronto.length; i++) {
			if (posizioniDiConfronto[i] > 0 && posizione > posizioniDiConfronto[i]) {
				result = false;
			}
		}
		
		return result;
	}
	
	public ModelliDocBean caricaProfilatura(ModelliDocBean bean) throws Exception {	
		
		AurigaLoginBean loginBean = getLoginBean();		
		
		try {	
			// se carico l'html a partire dall'idDocHtml che avevo salvato insieme al modello perdo i riferimenti alle funzioni per profilare le variabili, quindi lo ricreo ogni volta che apro la maschera di profilatura
//			if(StringUtils.isNotBlank(bean.getIdDocHtml()) && !"0".equals(bean.getIdDocHtml())) {
//				DmpkCoreExtractverdocBean lDmpkCoreExtractverdocBean = new DmpkCoreExtractverdocBean();
//				lDmpkCoreExtractverdocBean.setCodidconnectiontokenin(loginBean.getToken());
//				lDmpkCoreExtractverdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
//				lDmpkCoreExtractverdocBean.setIddocin(new BigDecimal(bean.getIdDocHtml()));			
//				DmpkCoreExtractverdoc lDmpkCoreExtractverdoc = new DmpkCoreExtractverdoc();
//				StoreResultBean<DmpkCoreExtractverdocBean> lStoreResultBean = lDmpkCoreExtractverdoc.execute(getLocale(), loginBean, lDmpkCoreExtractverdocBean);						
//				if (lStoreResultBean.getDefaultMessage()!=null){
//					throw new StoreException(lStoreResultBean);
//				}
//				
//				RecuperoFile lRecuperoFile = new RecuperoFile();
//				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
//				lFileExtractedIn.setUri(lStoreResultBean.getResultBean().getUriverout());
//				FileExtractedOut lFileExtractedOut = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
//				File lFileHtml = lFileExtractedOut.getExtracted();
//				bean.setHtml(FileUtils.readFileToString(lFileHtml));
//			} else {
			if(!(StringUtils.isNotBlank(getExtraparams().get("skipGenerazioneHtml")) && Boolean.parseBoolean(getExtraparams().get("skipGenerazioneHtml")))) {
				AllegatoProtocolloBean fileModello = getFileModello(bean);
				LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustom = new LinkedHashMap<String, AssociazioniAttributiCustomBean>(); 
				String uriFileHtml = storeFileHtmlFromOdt(fileModello.getUriFileAllegato(), mappaAssociazioniAttributiCustom);										
				bean.setHtml(FileUtils.readFileToString(StorageImplementation.getStorage().extractFile(uriFileHtml)));
			}
//			}
			
			DaoDmtInputModuliComp lDaoDmtInputModuliComp = new DaoDmtInputModuliComp();		
			
			TFilterFetch<InputModuliCompBean> filter = new TFilterFetch<InputModuliCompBean>();
			InputModuliCompBean filterBean = new InputModuliCompBean();
			filterBean.setIdModello(StringUtils.isNotBlank(bean.getIdModello()) ? new BigDecimal(bean.getIdModello()) : null);
			filter.setFilter(filterBean);				
			TPagingList<InputModuliCompBean> lTPagingListInputModuliComp = lDaoDmtInputModuliComp.search(getLocale(), loginBean, filter);			
			bean.setFlgProfCompleta(isProfilaturaCompleta(lTPagingListInputModuliComp.getData()));				
			
			List<String> nomiVariabiliLista = new ArrayList<String>();
			LinkedHashMap<String, AssociazioniAttributiCustomBean> mappaAssociazioniAttributiCustomSalvate = new LinkedHashMap<String, AssociazioniAttributiCustomBean>();
			if(lTPagingListInputModuliComp != null && lTPagingListInputModuliComp.getData() != null) {
				for(int i = 0; i < lTPagingListInputModuliComp.getData().size(); i++) {							
					AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean = buildAssociazioniAttributiCustomBeanFromInputModuliCompBean(lTPagingListInputModuliComp.getData().get(i));							
					mappaAssociazioniAttributiCustomSalvate.put(lAssociazioniAttributiCustomBean.getNomeVariabileModello(), lAssociazioniAttributiCustomBean);
					if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {
						nomiVariabiliLista.add(lAssociazioniAttributiCustomBean.getNomeVariabileModello());
					}
				}
			}	
			List<String> nomiSottoVariabiliLista = new ArrayList<String>();
			for(String nomeVariabileLista : nomiVariabiliLista) {
				AssociazioniAttributiCustomBean variabileLista = mappaAssociazioniAttributiCustomSalvate.get(nomeVariabileLista);
				String aliasVariabileLista = variabileLista.getAliasVariabileModello();
				if(variabileLista.getListaAssociazioniSottoAttributiComplex() == null) {
					variabileLista.setListaAssociazioniSottoAttributiComplex(new ArrayList<AssociazioniAttributiCustomBean>());	
				}
				for(String nomeVariabile : mappaAssociazioniAttributiCustomSalvate.keySet()) {
					if(StringUtils.isNotBlank(aliasVariabileLista) && nomeVariabile.startsWith(aliasVariabileLista + ".")) {
						AssociazioniAttributiCustomBean sottoAttributo = mappaAssociazioniAttributiCustomSalvate.get(nomeVariabile);
						// Se è un sotto attributo libero devo cambiare il nome secondo la convenzione col + numero colonna
						if ("attributoLibero".equalsIgnoreCase(sottoAttributo.getTipoAssociazioneVariabileModello()) && sottoAttributo.getNroColonna() != null) {
							sottoAttributo.setNomeAttributoLibero("col" + sottoAttributo.getNroColonna());
						}
						variabileLista.getListaAssociazioniSottoAttributiComplex().add(mappaAssociazioniAttributiCustomSalvate.get(nomeVariabile));
						nomiSottoVariabiliLista.add(nomeVariabile);
					} else if(nomeVariabile.startsWith(nomeVariabileLista + ".")) {
						variabileLista.getListaAssociazioniSottoAttributiComplex().add(mappaAssociazioniAttributiCustomSalvate.get(nomeVariabile));
						nomiSottoVariabiliLista.add(nomeVariabile);
					}
				}
				// se non ho sotto-attributi vuol dire che quella variabile lista non è di tipo COMPLEX ma un attributo semplice ripetibile 
				if(variabileLista.getListaAssociazioniSottoAttributiComplex().size() == 0) {
					variabileLista.setFlgComplex(false);
				}
			}	
			
			for(String nomeSottoVariabileLista : nomiSottoVariabiliLista) {
				mappaAssociazioniAttributiCustomSalvate.remove(nomeSottoVariabileLista);
			}
			
			bean.setListaAssociazioniAttributiCustom(new ArrayList<AssociazioniAttributiCustomBean>(mappaAssociazioniAttributiCustomSalvate.values()));				
			
			return bean;				
		} catch(Exception e) {
			throw new Exception("Si è verificato un errore durante il caricamento della profilatura del modello", e);
		}	
	}
	
	public ModelliDocBean salvaProfilatura(ModelliDocBean bean) throws Exception {								
		
		AurigaLoginBean loginBean = getLoginBean();
		HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista = new HashMap<String, List<DettColonnaAttributoListaBean>>();
		List<AttributoBean> attributiAdd = new ArrayList<AttributoBean>();
		
		// Recupero gli attributi custom associati solamente se il modello è associato ad una tipologia documentale, 
		// e quindi ho nome tabella e identita Associata
		if (StringUtils.isNotBlank(bean.getIdEntitaAssociata()) || StringUtils.isNotBlank(bean.getNomeTabella())) {
			AttributiDinamiciInputBean input = new AttributiDinamiciInputBean();
			input.setNomeTabella(bean.getNomeTabella());
			input.setTipoEntita(bean.getIdEntitaAssociata());
			
			AttributiDinamiciDatasource attributiDS = new AttributiDinamiciDatasource();
			attributiDS.setSession(getSession());
			attributiDS.setLoginBean(getLoginBean());
			attributiDS.setExtraparams(new LinkedHashMap<String, String>());
			
			AttributiDinamiciOutputBean output = attributiDS.call(input);	
			attributiAdd = output.getAttributiAdd() != null ? output.getAttributiAdd() : new ArrayList<AttributoBean>();
			mappaDettAttrLista = output.getMappaDettAttrLista() != null ? output.getMappaDettAttrLista() : new HashMap<String, List<DettColonnaAttributoListaBean>>();
		}
		
		
		try {					
			List<InputModuliCompBean> listaInputModuliCompBean = new ArrayList<InputModuliCompBean>();
			if(bean.getListaAssociazioniAttributiCustom() != null && bean.getListaAssociazioniAttributiCustom().size() > 0) {
				for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : bean.getListaAssociazioniAttributiCustom()) {
					listaInputModuliCompBean.add(buildInputModuliCompBeanFromAssociazioniAttributiCustomBean(bean.getIdModello(), lAssociazioniAttributiCustomBean, false, "", attributiAdd, mappaDettAttrLista));
					if (lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()){
						if(lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex() != null) {
							for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
								listaInputModuliCompBean.add(buildInputModuliCompBeanFromAssociazioniAttributiCustomBean(bean.getIdModello(), lAssociazioniSottoAttributiComplexBean, true, lAssociazioniAttributiCustomBean.getNomeAttributoCustom(), attributiAdd, mappaDettAttrLista));							
							}
						}
					}
				}
			}
			
			DaoDmtInputModuliComp lDaoDmtInputModuliComp = new DaoDmtInputModuliComp();		
			
			TSerializableList<InputModuliCompBean> lTSerializableList = new TSerializableList<InputModuliCompBean>();
			lTSerializableList.setData(getSerializableList(listaInputModuliCompBean));
			lDaoDmtInputModuliComp.salvaprofilatura(getLocale(), loginBean, bean.getIdModello(), lTSerializableList);
			
			TFilterFetch<InputModuliCompBean> filter = new TFilterFetch<InputModuliCompBean>();
			InputModuliCompBean filterBean = new InputModuliCompBean();
			filterBean.setIdModello(StringUtils.isNotBlank(bean.getIdModello()) ? new BigDecimal(bean.getIdModello()) : null);
			filter.setFilter(filterBean);				
			TPagingList<InputModuliCompBean> lTPagingListInputModuliComp = lDaoDmtInputModuliComp.search(getLocale(), loginBean, filter);			
			bean.setFlgProfCompleta(isProfilaturaCompleta(lTPagingListInputModuliComp.getData()));
			
			aggiornaFlgProfCompletaModello(bean);
			
			return bean;			
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore durante il salvataggio della profilatura del modello", e);
		}	
	}
	
	public void aggiornaFlgProfCompletaModello(ModelliDocBean bean) throws Exception {
		
		// salvo flgProfCompleta sul modello
		ModelliDocBean lModelliDocBean = new ModelliDocBean();
		lModelliDocBean.setIdModello(bean.getIdModello());
		lModelliDocBean = get(lModelliDocBean);
		lModelliDocBean.setFlgProfCompleta(bean.getFlgProfCompleta());
		
		update(lModelliDocBean, null);		
	}
	
//	public FileDaFirmareBean generaDocDaModello(ModelliDocBean bean) throws Exception {			
//		return generaDocDaModello(bean, null);		
//	}
	
//	public FileDaFirmareBean generaDocDaModello(ModelliDocBean modello, Map<String, Object> mapToFillTemplate) throws Exception {			
	
	public FileDaFirmareBean generaDocDaModello(ModelliDocBean modello) throws Exception {
		// Queste variabili mi servono per i log, potrebbero essere sovrascritte in seguito
		Boolean profilaturaCompletaPerlog = modello.getFlgProfCompleta();
		String valoriPerLog = modello.getValori() != null ? modello.getValori().toString() : "null";
		
		try {	
			logger.debug("#######INIZIO scrittura temp modello#######");
			AllegatoProtocolloBean fileModello = getFileModello(modello);
			File templateOdt = File.createTempFile("temp", ".odt");
			FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(fileModello.getUriFileAllegato()), templateOdt);
			logger.debug("#######FINE scrittura temp modello#######");
			// Carico la profilatura del modello se non è già presente
			ModelliDocDatasource lModelliDocDatasource = new ModelliDocDatasource();
			lModelliDocDatasource.setLoginBean(getLoginBean());
			lModelliDocDatasource.setSession(getSession());
			// Salto la generazione del file html
			Map<String, String> extraParams = new LinkedHashMap<String, String>();
			extraParams.put("skipGenerazioneHtml", "true");
			lModelliDocDatasource.setExtraparams(extraParams);
			
			// Carico la profilatura per salvare ListaAssociazioniAttributiCustom
			modello = lModelliDocDatasource.caricaProfilatura(modello);
			
			Map<String, String> mappaTipiValori = new LinkedHashMap<String, String>();
			Map<String, String> mappaColonneListe = new LinkedHashMap<String, String>();;
			// Verifico se il modello è completamente profilato
			 if (modello.getFlgProfCompleta() != null && modello.getFlgProfCompleta()) {
				// Se il modello è profilato calcolo mappaTipiValori e mappaColonneListe dalla profilatura
				mappaTipiValori = ModelliUtil.getMappaTipiValoriFromModello(modello);
				mappaColonneListe = ModelliUtil.getMappaColonneListeFromModello(modello);
			} else if (FreeMarkerModelliUtil.contieneSoloAssociazioniAttributiCustom(modello)){
				// Se non è completamente profilato ma ho solamente associazioni con attributi custom 
				// ricavo mappaTipiValori e mappaColonneListe dagli attributi dinamici
				// Creo il datasource settando la login bean
				AttributiDinamiciDatasource lAttributiDinamiciDatasource = new AttributiDinamiciDatasource();
				lAttributiDinamiciDatasource.setLoginBean(getLoginBean());
				// Creo il bean per invocare la chiamata
				AttributiDinamiciInputBean lAttributiDinamiciInputBean = new AttributiDinamiciInputBean();
				lAttributiDinamiciInputBean.setNomeTabella(modello.getNomeTabella());
				lAttributiDinamiciInputBean.setTipoEntita(modello.getIdEntitaAssociata());
				// Eseguo la chiamata
				AttributiDinamiciOutputBean lAttributiDinamiciOutputBean = lAttributiDinamiciDatasource.call(lAttributiDinamiciInputBean);
				mappaTipiValori = ModelliUtil.getMappaTipiValoriFromAttrDinamici(lAttributiDinamiciOutputBean);
				mappaColonneListe = ModelliUtil.getMappaColonneListeFromAttrDinamici(lAttributiDinamiciOutputBean);
				modello.setFlgProfCompleta(true);
			} 
			
			modello.setTipiValori(mappaTipiValori);
			modello.setColonneListe(mappaColonneListe);
			
			TemplateWithValuesBean lTemplateWithValuesBean = FreeMarkerModelliUtil.createTemplateWithValues(templateOdt, modello, getSession());
//
//			addMessage PERCHè MODELLIDOCDATASOURCE VIENE CHIAMATO DA ALTRI DATASOURCE E NON è COLLEGATO AL LAYOUT
//
//			if (lTemplateWithValuesBean.isInError()) {
//				if (StringUtils.isNotBlank(lTemplateWithValuesBean.getErrorMessage())) {
//					addMessage(lTemplateWithValuesBean.getErrorMessage(), "", MessageType.WARNING);
//				} else {
//					addMessage("Il contenuto dei campi testo è tale che non è possibile generare il pdf dell'atto "
//							+ "in una forma fedele all'originale. Riverifica i testi, in particolare le tabelle con celle unite.", "", MessageType.WARNING);
//				}
//			}
			
			
			File templateWithValues = lTemplateWithValuesBean.getFileGenerato();
			File templateOdtWithValues = lTemplateWithValuesBean.getFileOdtGenerato();
			
			// In templateWithValues ho un odt o un pdf a seconda del valore di bean.getFlgGeneraPdf()
			logger.debug("#######INIZIO dopo createTemplateWithValues#######");
			String nomeFile = FilenameUtils.getBaseName(fileModello.getNomeFileAllegato()) + (modello.getFlgGeneraPdf() ? ".pdf" : ".doc");
			logger.debug("#######PRIMA DI getStorage().store#######");
			String uriFile = StorageImplementation.getStorage().store(templateWithValues);
			logger.debug("#######DOPO DI getStorage().store#######");
			logger.debug("#######PRIMA DI getInfoFromFile#######");
			MimeTypeFirmaBean lMimeTypeFirmaBean;
			if (templateWithValues.getName().endsWith(".pdf")) {
				lMimeTypeFirmaBean = new MimeTypeFirmaBean();
				lMimeTypeFirmaBean.setMimetype("application/pdf");
				lMimeTypeFirmaBean.setCorrectFileName(nomeFile);
				lMimeTypeFirmaBean.setConvertibile(true);
				lMimeTypeFirmaBean.setAlgoritmo("SHA-256");
				lMimeTypeFirmaBean.setEncoding("base64");
				lMimeTypeFirmaBean.setImpronta((new CalcolaImpronteService()).calcolaImprontaWithoutFileOp(templateWithValues, "SHA-256", "base64"));
				lMimeTypeFirmaBean.setBytes(templateWithValues.length());
				lMimeTypeFirmaBean.setFirmato(false);
				lMimeTypeFirmaBean.setTipoFirma("");
			} else if (templateWithValues.getName().endsWith(".doc")) {
				lMimeTypeFirmaBean = new MimeTypeFirmaBean();
				lMimeTypeFirmaBean.setMimetype("application/msword");
				lMimeTypeFirmaBean.setCorrectFileName(nomeFile);
				lMimeTypeFirmaBean.setConvertibile(true);
				lMimeTypeFirmaBean.setAlgoritmo("SHA-256");
				lMimeTypeFirmaBean.setEncoding("base64");
				lMimeTypeFirmaBean.setImpronta((new CalcolaImpronteService()).calcolaImprontaWithoutFileOp(templateWithValues, "SHA-256", "base64"));
				lMimeTypeFirmaBean.setBytes(templateWithValues.length());
				lMimeTypeFirmaBean.setFirmato(false);
				lMimeTypeFirmaBean.setTipoFirma("");
			} else {
				lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString(), nomeFile, false, null);
			}
			logger.debug("#######DOPO DI getInfoFromFile#######");
			FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
			lFileDaFirmareBean.setNomeFile(nomeFile);
			lFileDaFirmareBean.setUri(uriFile);
			lFileDaFirmareBean.setInfoFile(lMimeTypeFirmaBean);
			logger.debug("#######PRIMA DI getStorage().store#######");
			String uriFileOdt = StorageImplementation.getStorage().store(templateOdtWithValues);
			logger.debug("#######DOPO DI getStorage().store#######");
			lFileDaFirmareBean.setUriFileOdtGenerato(uriFileOdt);
			logger.debug("#######FINE dopo createTemplateWithValues#######");
			return lFileDaFirmareBean;
		} catch (StoreException specificError) {
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw specificError;
		} catch (FreeMarkerCreateDocumentException e) {
			String errorMessage = "Errore nella generazione del modello" + (StringUtils.isNotBlank(e.getMessage()) ? " - " + e.getMessage() : "");
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw new Exception(errorMessage, e);
		} catch (FreeMarkerMergeHtmlSectionsException e) {
			String errorMessage = "Errore nella generazione del modello" + (StringUtils.isNotBlank(e.getMessage()) ? " - " + e.getMessage() : "");
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw new Exception(errorMessage, e);
		} catch (FreeMarkerFixMergedCellException e) {
			String errorMessage = "Errore nella generazione del modello" + (StringUtils.isNotBlank(e.getMessage()) ? " - " + e.getMessage() : "");
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw new Exception(errorMessage, e);
		} catch (FreeMarkerRetriveStyleException e) {
			String errorMessage = "Errore nella generazione del modello" + (StringUtils.isNotBlank(e.getMessage()) ? " - " + e.getMessage() : "");
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw new Exception(errorMessage, e);
		} catch (IOException e) {
			String errorMessage = "Errore nella generazione del modello" + (StringUtils.isNotBlank(e.getMessage()) ? " - " + e.getMessage() : "");
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw new Exception(errorMessage, e);
		} catch (Exception e) {
			String errorMessage = "Il contenuto dei campi testo è tale che non è possibile generare il pdf dell&apos;atto in una forma fedele all&apos;originale.<br> Riverifica i testi, in particolare le tabelle con celle unite, e cambiali in una forma tale che possano essere correttamente rappresentati nel pdf dell&apos;atto";
			scriviLogEccezione(modello, valoriPerLog, profilaturaCompletaPerlog);
			throw new Exception(errorMessage, e);
		}
	}

	private void scriviLogEccezione(ModelliDocBean modello, String valoriPerLog, Boolean profilaturaCompletaPerlog) throws Exception {
		logger.error("Errore nella generazione del modello");
		logger.error("idModello: " + modello.getIdModello() + " Nome modello: " + modello.getNomeModello() + " ProfilaturaCompleta: " + profilaturaCompletaPerlog + " Contiene solo attributi custom: " + FreeMarkerModelliUtil.contieneSoloAssociazioniAttributiCustom(modello));
		logger.error("Valori in ingresso: " + valoriPerLog);
		logger.error("TipiValori: " + modello.getTipiValori());
		logger.error("ColonneListe: " + modello.getColonneListe());
		logger.error("Valori iniettati: " + modello.getValori());
	}
	
	public String getCharsetFromHtmlDocument(Document htmlDocument) {
		for(Element meta : htmlDocument.head().getElementsByTag("meta")) {
			if(StringUtils.isNotBlank(meta.attr("content"))) {
				int pos = meta.attr("content").indexOf("charset=");
				if(pos != -1) {
					return meta.attr("content").substring(pos + 8);					
				}				
			}
		}
		return null;
	}

	public AttributiDinamiciOutputBean caricaAttributiCustom(ModelliDocBean bean) throws Exception {	
		
		
		AttributiDinamiciOutputBean output = new AttributiDinamiciOutputBean();
		output.setAttributiAdd(new ArrayList<AttributoBean>());
		output.setAttributiXRicerca(new ArrayList<AttributoXRicercaBean>());
		output.setMappaDettAttrLista(new HashMap<String, List<DettColonnaAttributoListaBean>>());
		output.setMappaDocumenti(new LinkedHashMap<String, DocumentBean>());
		output.setMappaValoriAttrLista(new HashMap<String, List<HashMap<String, String>>>());
		output.setMappaVariazioniAttrLista(new HashMap<String, List<HashMap<String, String>>>());
		
		HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista = output.getMappaDettAttrLista();
		List<AttributoBean> attributiAdd = output.getAttributiAdd();
		
		int numeroRiquadro = 0;
		int numeroRiga = 0;
		
		// Carico gli attributi custom associati solamente se il modello è associato ad una tipologia documentale, 
		// e quindi ho nome tabella e identita Associata
		if (StringUtils.isNotBlank(bean.getIdEntitaAssociata()) || StringUtils.isNotBlank(bean.getNomeTabella())) {
		
			LinkedHashMap<String, String> associazioniNomiAttributiCustomMap = getAssociazioniNomiAttributiCustomMap(bean);
			AttributiDinamiciInputBean input = new AttributiDinamiciInputBean();
			input.setNomeTabella(bean.getNomeTabella());
			input.setTipoEntita(bean.getIdEntitaAssociata());
			
			AttributiDinamiciDatasource attributiDS = new AttributiDinamiciDatasource();
			attributiDS.setSession(getSession());
			attributiDS.setLoginBean(getLoginBean());
			attributiDS.setExtraparams(new LinkedHashMap<String, String>());
			
			output = attributiDS.call(input);	
			
			if(output != null && output.getAttributiAdd() != null) {
				List<AttributoBean> lAttributoBean = new ArrayList<AttributoBean>();
				for(int i = 0; i < output.getAttributiAdd().size(); i++) {
					if(associazioniNomiAttributiCustomMap.containsValue(output.getAttributiAdd().get(i).getNome())) {
						lAttributoBean.add(output.getAttributiAdd().get(i));
						String numeroRiquadroAttributo = output.getAttributiAdd().get(i).getNumero();
						numeroRiquadro = StringUtils.isNotBlank(numeroRiquadroAttributo) ? Math.max(numeroRiquadro, Integer.parseInt(numeroRiquadroAttributo)) : numeroRiquadro;
					}
				}
				output.setAttributiAdd(lAttributoBean);
			}	
			
			numeroRiquadro = numeroRiquadro > 0 ? numeroRiquadro + 1 : numeroRiquadro;
			
			if(output != null && output.getMappaDettAttrLista() != null) {
				for(String key : output.getMappaDettAttrLista().keySet()) {
					List<DettColonnaAttributoListaBean> listaDettColonneAttributoLista = new ArrayList<DettColonnaAttributoListaBean>();
					for(DettColonnaAttributoListaBean lDettColonnaAttributoListaBean : output.getMappaDettAttrLista().get(key)) {
						if(associazioniNomiAttributiCustomMap.containsValue(lDettColonnaAttributoListaBean.getNome())) {
							listaDettColonneAttributoLista.add(lDettColonnaAttributoListaBean);
						}
					}
					output.getMappaDettAttrLista().put(key, listaDettColonneAttributoLista);
				}			
			}			
			
			attributiAdd = output.getAttributiAdd() != null ? output.getAttributiAdd() : new ArrayList<AttributoBean>();
			mappaDettAttrLista = output.getMappaDettAttrLista() != null ? output.getMappaDettAttrLista() : new HashMap<String, List<DettColonnaAttributoListaBean>>();		
		}
				
		// Creo gli attributi liberi del modello
		if(bean.getListaAssociazioniAttributiCustom() != null && !bean.getListaAssociazioniAttributiCustom().isEmpty()) {
			for (AssociazioniAttributiCustomBean associazione : bean.getListaAssociazioniAttributiCustom()) {
				if ("attributoLibero".equalsIgnoreCase(associazione.getTipoAssociazioneVariabileModello())){
					String nome = associazione.getNomeAttributoLibero();
					String labelRiquadro = "ATTRIBUTI LIBERI";
					AttributoBean lAttributoBean;
					if ("COMPLEX".equalsIgnoreCase(associazione.getTipoAttributo())){
						lAttributoBean = createAttributoLibero("LISTA", nome, numeroRiquadro + "", labelRiquadro, numeroRiga + "");
						List<AssociazioniAttributiCustomBean> listaAssociazioniSottoAttributi = associazione.getListaAssociazioniSottoAttributiComplex() != null ? associazione.getListaAssociazioniSottoAttributiComplex() : new ArrayList<AssociazioniAttributiCustomBean>();
						List<DettColonnaAttributoListaBean> listaDettColonnaAttributoListaBean = new ArrayList<DettColonnaAttributoListaBean>(); 
						for (AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : listaAssociazioniSottoAttributi) {
							String tipoSottoAttributo = lAssociazioniAttributiCustomBean.getTipoAttributo();
							Integer numero = lAssociazioniAttributiCustomBean.getNumeroColonnaAttributoLibero();
							String nomeSottoAttributo = "col" + numero;
							Boolean ripetibileSottoAttributo = lAssociazioniAttributiCustomBean.getFlgRipetibile();
							DettColonnaAttributoListaBean lDettColonnaAttributoListaBean = createSottoAttributoLibero(tipoSottoAttributo, nomeSottoAttributo, numero + "");
							listaDettColonnaAttributoListaBean.add(lDettColonnaAttributoListaBean);
						}
						mappaDettAttrLista.put(nome, listaDettColonnaAttributoListaBean);
					} else if (associazione.getFlgRipetibile() != null && associazione.getFlgRipetibile()) {
						lAttributoBean = createAttributoLibero("LISTA", nome, numeroRiquadro + "", labelRiquadro, numeroRiga + "");
						List<DettColonnaAttributoListaBean> listaDettColonnaAttributoListaBean = new ArrayList<DettColonnaAttributoListaBean>();
						String tipoSottoAttributo = associazione.getTipoAttributo();
						Integer numero = 1;
						String nomeSottoAttributo = nome;
						DettColonnaAttributoListaBean lDettColonnaAttributoListaBean = createSottoAttributoLibero(tipoSottoAttributo, nomeSottoAttributo, numero + "");
						listaDettColonnaAttributoListaBean.add(lDettColonnaAttributoListaBean);
						mappaDettAttrLista.put(nome, listaDettColonnaAttributoListaBean);
					} else {
						lAttributoBean = createAttributoLibero(associazione.getTipoAttributo(), nome, numeroRiquadro + "", labelRiquadro, numeroRiga + "");
					}
					attributiAdd.add(lAttributoBean);
					numeroRiga ++;
					// numeroRiquadro ++;
				}
			}
		}
		
		output.setMappaDettAttrLista(mappaDettAttrLista);
		output.setAttributiAdd(attributiAdd);
	
		return output;
	}
	
	private AttributoBean createAttributoLibero(String tipo, String nome, String numeroRiquadro, String labelRiquadro, String riga) {
		AttributoBean lAttributoBean;
		if ("CHECK".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoCheck();
		}else if ("DATE".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoDate();
		}else if ("DATETIME".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoDateTime();
		} else if ("NUMBER".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoDecimal();
		} else if ("EURO".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoEuro();
		} else if ("TEXT-BOX".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoText();
		} else if ("TEXT-AREA".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoTextArea();
		} else if ("CKEDITOR".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoCKEditor();
		} else if ("COMBO-BOX".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoComboBox();
		} else if ("RADIO".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoRadio();
		} else if ("LISTA".equalsIgnoreCase(tipo)){
			lAttributoBean = createAttributoLiberoLista();
		} else {
			lAttributoBean = createAttributoLiberoText();
		}
		lAttributoBean.setNome(nome);
		lAttributoBean.setLabel(setTitleAlign(nome));
		lAttributoBean.setModificabile("1");
		lAttributoBean.setNumero(numeroRiquadro);
		lAttributoBean.setLabelRiquadro(labelRiquadro);
		lAttributoBean.setRiga(riga);
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoLista() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("LISTA");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoDate() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("DATE");
		return lAttributoBean;
	}
	
	
	private AttributoBean createAttributoLiberoDateTime() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("DATETIME");
		return lAttributoBean;
	}
	
	
	private AttributoBean createAttributoLiberoText() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("TEXT");
		return lAttributoBean;
	}
	
	
	private AttributoBean createAttributoLiberoTextArea() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("TEXT-AREA");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoComboBox() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("COMBO-BOX");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoEuro() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("EURO");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoDecimal() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("DECIMAL");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoRadio() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("RADIO");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoCKEditor() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("CKEDITOR");
		return lAttributoBean;
	}
	
	private AttributoBean createAttributoLiberoCheck() {
		AttributoBean lAttributoBean = new AttributoBean();
		lAttributoBean.setTipo("CHECK");
		return lAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLibero(String tipo, String nome, String numero) {
		DettColonnaAttributoListaBean lSottoAttributoBean;
		if ("CHECK".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoCheck();
		}else if ("DATE".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoDate();
		}else if ("DATETIME".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoDateTime();
		} else if ("NUMBER".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoDecimal();
		} else if ("EURO".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoEuro();
		} else if ("TEXT-BOX".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoText();
		} else if ("TEXT-AREA".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoTextArea();
		} else if ("CKEDITOR".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoCKEditor();
		} else if ("COMBO-BOX".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoComboBox();
		} else if ("RADIO".equalsIgnoreCase(tipo)){
			lSottoAttributoBean = createSottoAttributoLiberoRadio();
		} else {
			lSottoAttributoBean = createSottoAttributoLiberoText();
		}
		lSottoAttributoBean.setNome(nome);
		lSottoAttributoBean.setLabel(setTitleAlign(nome));
		lSottoAttributoBean.setModificabile("1");
		lSottoAttributoBean.setNumero(numero);
		lSottoAttributoBean.setRiga(numero);
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoLista() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("LISTA");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoDate() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("DATE");
		return lSottoAttributoBean;
	}
	
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoDateTime() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("DATETIME");
		return lSottoAttributoBean;
	}
	
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoText() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("TEXT");
		lSottoAttributoBean.setLarghezza("800");
		return lSottoAttributoBean;
	}
	
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoTextArea() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("TEXT-AREA");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoComboBox() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("COMBO-BOX");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoEuro() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("EURO");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoDecimal() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("DECIMAL");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoRadio() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("RADIO");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoCKEditor() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("CKEDITOR");
		return lSottoAttributoBean;
	}
	
	private DettColonnaAttributoListaBean createSottoAttributoLiberoCheck() {
		DettColonnaAttributoListaBean lSottoAttributoBean = new DettColonnaAttributoListaBean();
		lSottoAttributoBean.setTipo("CHECK");
		return lSottoAttributoBean;
	}
	
	private String setTitleAlign(String title) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (200) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	private DettColonnaAttributoListaBean createAttributoLiberoColonna(String tipo) {
		DettColonnaAttributoListaBean lDettColonnaAttributoListaBean = new DettColonnaAttributoListaBean();
		return lDettColonnaAttributoListaBean;
	}
	
	public FileDaFirmareBean creaModelloDaAttributiDinamici(GeneraDaModelloBean bean) throws Exception {
		
		SezioneCache lSezioneCache = null;
		if (bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
			lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(),
					getSession());
		} else {
			lSezioneCache = new SezioneCache();
		}
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(lSezioneCache);
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciXmlBean);
		
		return ModelliUtil.fillTemplate(bean.getIdProcess(), bean.getIdModello(), xmlAttributiDinamici, null, bean.getFlgMostraDatiSensibili(), getSession());
		
	}
	
	private LinkedHashMap<String, String> getAssociazioniNomiAttributiCustomMap(ModelliDocBean bean) {
		
		LinkedHashMap<String, String> associazioniNomiAttributiCustomMap = new LinkedHashMap<String, String>();
		
		if(bean.getListaAssociazioniAttributiCustom() != null && bean.getListaAssociazioniAttributiCustom().size() > 0) {
			for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : bean.getListaAssociazioniAttributiCustom()) {
				if ("attributoCustom".equalsIgnoreCase(lAssociazioniAttributiCustomBean.getTipoAssociazioneVariabileModello())) {
					if(StringUtils.isNotBlank(lAssociazioniAttributiCustomBean.getNomeVariabileModello())) {
						associazioniNomiAttributiCustomMap.put(lAssociazioniAttributiCustomBean.getNomeVariabileModello(), lAssociazioniAttributiCustomBean.getNomeAttributoCustom());
					}
					if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {
						for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
							associazioniNomiAttributiCustomMap.put(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello(), lAssociazioniSottoAttributiComplexBean.getNomeAttributoCustom());
						}
					}
				}
			}
		}
		
		return associazioniNomiAttributiCustomMap;
	}
	
	private static List<InputModuliCompBean> getSerializableList(List<InputModuliCompBean> inputList){
		ListIterator<InputModuliCompBean> iter = inputList.listIterator();
		while(iter.hasNext()){
			InputModuliCompBean inputModuliCompBean = iter.next();
			if (inputModuliCompBean != null && StringUtils.isBlank(inputModuliCompBean.getNomeInput())){ 
		        iter.remove();
		    }
		}
		
		return inputList != null && inputList.size() > 0 ? inputList : null;
	}
	
//	private ImpostazioniBarcodeBean getImpostazioniImmagineBarCode(String tipoBarcode) {
//		
//		if (StringUtils.isBlank(tipoBarcode)) {
//			tipoBarcode = "CODE128";
//		}
//		
//		ImpostazioniBarcodeBean impostazioniBarcodeBean = new ImpostazioniBarcodeBean();
//		impostazioniBarcodeBean.setBarcodeEncoding(tipoBarcode);
//		return impostazioniBarcodeBean;
//	}
	
	public AurigaLoginBean getLoginBean() {
		if (loginBean == null && getSession() != null) {
			loginBean = AurigaUserUtil.getLoginInfo(getSession());
		}
		return loginBean;
	}

	// Questo metodo è stato inserito per poter chiamare il datasource da una classe esterna, settando il bean di login
	// in quanto non sarebbe recuperatible dalla session
	public void setLoginBean(AurigaLoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
}