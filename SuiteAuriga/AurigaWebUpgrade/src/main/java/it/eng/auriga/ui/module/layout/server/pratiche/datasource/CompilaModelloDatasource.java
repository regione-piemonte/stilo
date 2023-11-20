/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.io.Files;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.compiler.odt.OdtConverter;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * Compila il modello specificato utilizzando i dati forniti, generando poi una versione del documento pdf compilato
 *
 */
@Datasource(id = "CompilaModelloDatasource")
public class CompilaModelloDatasource extends AbstractServiceDataSource<CompilaModelloBean, CompilaModelloBean> {

	@Override
	public CompilaModelloBean call(CompilaModelloBean modelloBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String templateValues = null;
		if(StringUtils.isNotBlank(modelloBean.getIdUd()) && StringUtils.isNotBlank(modelloBean.getNomeModello())) {
			
			DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
			DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
			lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(modelloBean.getIdUd());
			lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
			lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(modelloBean.getNomeModello());
			// Creo gli attributi addizionali
			Map<String, Object> valori = modelloBean.getValori() != null ? modelloBean.getValori() : new HashMap<String, Object>();
			Map<String, String> tipiValori = modelloBean.getTipiValori() != null ? modelloBean.getTipiValori() : new HashMap<String, String>();
			SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
			AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
			lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
			lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
					lDmpkModelliDocGetdatixgendamodelloInput);
			if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
				throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
			}
			
			templateValues = lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
		}

		if(StringUtils.isNotBlank(modelloBean.getIdModello())) {
			FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(modelloBean.getProcessId(), modelloBean.getIdModello(), templateValues, true, getSession());			
			modelloBean.setUri(lFileDaFirmareBean.getUri());
			modelloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
		} else {
			File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModello(), modelloBean.getTipoModello(), templateValues, getSession());
			String storageUri = StorageImplementation.getStorage().store(fileModelloPdf);
			modelloBean.setUri(storageUri);
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModelloPdf));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModelloPdf.length());
			infoFile.setCorrectFileName(modelloBean.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
			modelloBean.setInfoFile(infoFile);
		}

		return modelloBean;
	}
	
	public CompilaModelloBean compilaDispositivoAttoConCopertina(CompilaModelloBean modelloBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		
		ArrayList<File> fileToMerge = new ArrayList<File>();

		if (StringUtils.isNotBlank(modelloBean.getUriModCopertina())) {
			DmpkProcessesGetdatixmodellipratica getDatiXModelliPratica = new DmpkProcessesGetdatixmodellipratica();
			DmpkProcessesGetdatixmodellipraticaBean getDatiXModelliPraticaInput = new DmpkProcessesGetdatixmodellipraticaBean();
			getDatiXModelliPraticaInput.setCodidconnectiontokenin(loginBean.getToken());
			getDatiXModelliPraticaInput.setIdprocessin(BigDecimal.valueOf(Long.valueOf(modelloBean.getProcessId())));
			StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> getDatiXModelliPraticaOutput = getDatiXModelliPratica.execute(getLocale(), loginBean,
					getDatiXModelliPraticaInput);
			if (getDatiXModelliPraticaOutput.isInError()) {
				throw new StoreException(getDatiXModelliPraticaOutput);
			}
			String templateValues = getDatiXModelliPraticaOutput.getResultBean().getDatimodellixmlout();
			File fileCopertina = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModCopertina(), modelloBean.getTipoModCopertina(), templateValues, getSession());
			fileToMerge.add(fileCopertina);
		} 
//		else {
//			throw new StoreException("Manca la configurazione della copertina");
//		}

		AllegatoProtocolloBean dispositivoAtto = new AllegatoProtocolloBean(); 
		dispositivoAtto.setUriFileAllegato(modelloBean.getUri());
		dispositivoAtto.setNomeFileAllegato(modelloBean.getNomeFile());
		if(modelloBean.getInfoFile() == null) {
			MimeTypeFirmaBean lMimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(modelloBean.getUri()).toURI().toString(), modelloBean.getNomeFile(), false, null);
			modelloBean.setInfoFile(lMimeTypeFirmaBean);			
		}
		dispositivoAtto.setInfoFile(modelloBean.getInfoFile());
		dispositivoAtto.setFlgParteDispositivo(true);
		dispositivoAtto.setFlgNoPubblAllegato(false);
		
		File fileDispositivoAtto = recuperaFileAllegatoToMerge(dispositivoAtto);
		if (fileDispositivoAtto != null) {
			fileToMerge.add(fileDispositivoAtto);
		}

		File fileModello = File.createTempFile("mergeFiles", ".pdf");
		FileOutputStream output = new FileOutputStream(fileModello);

		MergeDocument mergeDoc = new MergeDocument();
		mergeDoc.mergeDocument(fileToMerge.toArray(new File[fileToMerge.size()]), output);

		String storageUri = StorageImplementation.getStorage().store(fileModello);
		modelloBean.setUri(storageUri);
		modelloBean.setNomeFile(FilenameUtils.getBaseName(modelloBean.getNomeFile()) + ".pdf");

		MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
		infoFile.setMimetype("application/pdf");
		infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModello));
		infoFile.setDaScansione(false);
		infoFile.setFirmato(false);
		infoFile.setFirmaValida(false);
		infoFile.setBytes(fileModello.length());
		infoFile.setCorrectFileName(modelloBean.getNomeFile());
		File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
		infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
		modelloBean.setInfoFile(infoFile);

		return modelloBean;
	}

	public CompilaModelloBean compilaModelloAnteprimaConCopertina(CompilaModelloBean modelloBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		SezioneCache lSezioneCache = null;

		if (modelloBean.getValori() != null && modelloBean.getValori().size() > 0 && modelloBean.getTipiValori() != null
				&& modelloBean.getTipiValori().size() > 0) {
			lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, modelloBean.getValori(), modelloBean.getTipiValori(),
					getSession());
		} else {
			lSezioneCache = new SezioneCache();
		}

		if (StringUtils.isNotBlank(modelloBean.getOggetto())) {
			Variabile varDesOgg = new Variabile();
			varDesOgg.setNome("#DesOgg");
			varDesOgg.setValoreSemplice(modelloBean.getOggetto());
			lSezioneCache.getVariabile().add(varDesOgg);
		}

		Variabile varUoCoinvolte = new Variabile();
		varUoCoinvolte.setNome("#UOCoinvolte");
		Lista listaUoCoinvolte = new Lista();
		if (modelloBean.getListaUoCoinvolte() != null) {
			for (int i = 0; i < modelloBean.getListaUoCoinvolte().size(); i++) {
				Riga riga = new Riga();
				Colonna col = new Colonna();
				col.setNro(new BigInteger("1"));
				col.setContent(modelloBean.getListaUoCoinvolte().get(i).getIdUo());
				if (col.getContent() != null && col.getContent().toUpperCase().startsWith("UO")) {
					col.setContent(col.getContent().substring(2));
				}
				riga.getColonna().add(col);
				listaUoCoinvolte.getRiga().add(riga);
			}
		}
		varUoCoinvolte.setLista(listaUoCoinvolte);
		lSezioneCache.getVariabile().add(varUoCoinvolte);

		String xmlSezioneCacheAttributiAdd = null;
		if (lSezioneCache != null) {
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			xmlSezioneCacheAttributiAdd = lStringWriter.toString();
		}

		DmpkProcessesGetdatixmodellipratica getDatiXModelliPratica = new DmpkProcessesGetdatixmodellipratica();
		DmpkProcessesGetdatixmodellipraticaBean getDatiXModelliPraticaInput = new DmpkProcessesGetdatixmodellipraticaBean();
		getDatiXModelliPraticaInput.setCodidconnectiontokenin(loginBean.getToken());
		getDatiXModelliPraticaInput.setIdprocessin(BigDecimal.valueOf(Long.valueOf(modelloBean.getProcessId())));
		getDatiXModelliPraticaInput.setAttributiaddin(xmlSezioneCacheAttributiAdd);
		StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> getDatiXModelliPraticaOutput = getDatiXModelliPratica.execute(getLocale(), loginBean,
				getDatiXModelliPraticaInput);
		if (getDatiXModelliPraticaOutput.isInError()) {
			throw new StoreException(getDatiXModelliPraticaOutput);
		}
		String templateValues = getDatiXModelliPraticaOutput.getResultBean().getDatimodellixmlout();

		ArrayList<File> fileToMerge = new ArrayList<File>();

		if (StringUtils.isNotBlank(modelloBean.getUriModCopertina())) {
			File fileCopertina = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModCopertina(), modelloBean.getTipoModCopertina(), templateValues, getSession());
			fileToMerge.add(fileCopertina);
		} else {
			throw new StoreException("Manca la configurazione della copertina");
		}

		File filePrimario = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModello(), modelloBean.getTipoModello(), templateValues, getSession());
		fileToMerge.add(filePrimario);

		if (StringUtils.isNotBlank(modelloBean.getUriModAppendice())) {
			File fileAppendice = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModAppendice(), modelloBean.getTipoModAppendice(), templateValues, getSession());
			fileToMerge.add(fileAppendice);
		}

		File fileModello = File.createTempFile("mergeFiles", ".pdf");
		FileOutputStream output = new FileOutputStream(fileModello);

		MergeDocument mergeDoc = new MergeDocument();
		mergeDoc.mergeDocument(fileToMerge.toArray(new File[fileToMerge.size()]), output);

		String storageUri = StorageImplementation.getStorage().store(fileModello);
		modelloBean.setUri(storageUri);
		modelloBean.setNomeFile(FilenameUtils.getBaseName(modelloBean.getNomeFile()) + ".pdf");

		MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
		infoFile.setMimetype("application/pdf");
		infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModello));
		infoFile.setDaScansione(false);
		infoFile.setFirmato(false);
		infoFile.setFirmaValida(false);
		infoFile.setBytes(fileModello.length());
		infoFile.setCorrectFileName(modelloBean.getNomeFile());
		File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
		modelloBean.setInfoFile(infoFile);

		return modelloBean;

	}

	public CompilaModelloBean compilaVersionePerPubblicazione(CompilaModelloBean modelloBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		SezioneCache lSezioneCache = null;

		if (modelloBean.getValori() != null && modelloBean.getValori().size() > 0 && modelloBean.getTipiValori() != null
				&& modelloBean.getTipiValori().size() > 0) {
			lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, modelloBean.getValori(), modelloBean.getTipiValori(),
					getSession());
		} else {
			lSezioneCache = new SezioneCache();
		}

		String xmlSezioneCacheAttributiAdd = null;
		if (lSezioneCache != null) {
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			xmlSezioneCacheAttributiAdd = lStringWriter.toString();
		}

		DmpkProcessesGetdatixmodellipratica getDatiXModelliPratica = new DmpkProcessesGetdatixmodellipratica();
		DmpkProcessesGetdatixmodellipraticaBean getDatiXModelliPraticaInput = new DmpkProcessesGetdatixmodellipraticaBean();
		getDatiXModelliPraticaInput.setCodidconnectiontokenin(loginBean.getToken());
		getDatiXModelliPraticaInput.setIdprocessin(BigDecimal.valueOf(Long.valueOf(modelloBean.getProcessId())));
		getDatiXModelliPraticaInput.setAttributiaddin(xmlSezioneCacheAttributiAdd);
		StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> getDatiXModelliPraticaOutput = getDatiXModelliPratica.execute(getLocale(), loginBean,
				getDatiXModelliPraticaInput);
		if (getDatiXModelliPraticaOutput.isInError()) {
			throw new StoreException(getDatiXModelliPraticaOutput);
		}
		String templateValues = getDatiXModelliPraticaOutput.getResultBean().getDatimodellixmlout();

		ArrayList<File> fileToMerge = new ArrayList<File>();

		if (StringUtils.isNotBlank(modelloBean.getUriModCopertinaFinale())) {
			File fileCopertinaFinale = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModCopertinaFinale(), modelloBean.getTipoModCopertinaFinale(), templateValues, getSession());
			fileToMerge.add(fileCopertinaFinale);
		} else {
			throw new StoreException("Manca la configurazione della copertina finale");
		}

		boolean flgNoPubblPrimario = modelloBean.getFlgNoPubblPrimario() != null && modelloBean.getFlgNoPubblPrimario();
		boolean flgDatiSensibiliPrimario = modelloBean.getFlgDatiSensibili() != null && modelloBean.getFlgDatiSensibili();
		
		if (!flgNoPubblPrimario && !flgDatiSensibiliPrimario) {
			File fileAnteprimaConCopertinaEAppendice = StorageImplementation.getStorage().extractFile(modelloBean.getUri());
			fileToMerge.add(fileAnteprimaConCopertinaEAppendice);
		} else {
			File fileCopertina = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModCopertina(), modelloBean.getTipoModCopertina(), templateValues, getSession());
			fileToMerge.add(fileCopertina);
			if (flgNoPubblPrimario && modelloBean.getFilePrimarioVerPubbl() != null) {
				File filePrimarioVerPubbl = recuperaFilePrimarioVerPubblToMerge(modelloBean.getFilePrimarioVerPubbl());
				if (filePrimarioVerPubbl != null) {
					fileToMerge.add(filePrimarioVerPubbl);
				}				
			} else if (flgDatiSensibiliPrimario && modelloBean.getFilePrimarioOmissis() != null) {
				File filePrimarioOmissis = recuperaFilePrimarioOmissisToMerge(modelloBean.getFilePrimarioOmissis());
				if (filePrimarioOmissis != null) {
					fileToMerge.add(filePrimarioOmissis);
				}			
			}			
			if (StringUtils.isNotBlank(modelloBean.getUriModAppendice())) {
				File fileAppendice = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModAppendice(), modelloBean.getTipoModAppendice(), templateValues, getSession());
				fileToMerge.add(fileAppendice);
			}
		}

		// file allegati parte dispositivo
		if (modelloBean.getListaAllegati() != null && modelloBean.getListaAllegati().size() > 0) {
			for (AllegatoProtocolloBean allegato : modelloBean.getListaAllegati()) {				
				boolean flgParteDispositivoAllegato = allegato.getFlgParteDispositivo() != null && allegato.getFlgParteDispositivo();
				boolean flgNoPubblAllegato = allegato.getFlgNoPubblAllegato() != null && allegato.getFlgNoPubblAllegato();
				boolean flgDatiSensibiliAllegato = allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili();				
				if(flgParteDispositivoAllegato && !flgNoPubblAllegato) {
					if(flgDatiSensibiliAllegato) {
						File fileAllegatoOmissis = recuperaFileAllegatoOmissisToMerge(allegato);
						if (fileAllegatoOmissis != null) {
							fileToMerge.add(fileAllegatoOmissis);
						}						
					} else {
						File fileAllegato = recuperaFileAllegatoToMerge(allegato);
						if (fileAllegato != null) {
							fileToMerge.add(fileAllegato);
						}
					} 
				}				
			}
		}

		File fileModello = File.createTempFile("mergeFiles", ".pdf");
		FileOutputStream output = new FileOutputStream(fileModello);

		MergeDocument mergeDoc = new MergeDocument();
		mergeDoc.mergeDocument(fileToMerge.toArray(new File[fileToMerge.size()]), output);

		String storageUri = StorageImplementation.getStorage().store(fileModello);
		modelloBean.setUri(storageUri);
		modelloBean.setNomeFile(FilenameUtils.getBaseName(modelloBean.getNomeFile()) + ".pdf");

		MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
		infoFile.setMimetype("application/pdf");
		infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModello));
		infoFile.setDaScansione(false);
		infoFile.setFirmato(false);
		infoFile.setFirmaValida(false);
		infoFile.setBytes(fileModello.length());
		infoFile.setCorrectFileName(modelloBean.getNomeFile());
		File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
		modelloBean.setInfoFile(infoFile);

		return modelloBean;

	}
	
	private File recuperaFilePrimarioVerPubblToMerge(AllegatoProtocolloBean filePrimarioVerPubbl) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uriFileVerPubbl = filePrimarioVerPubbl.getUriFileAllegato();
		if (filePrimarioVerPubbl.getInfoFile() != null && filePrimarioVerPubbl.getInfoFile().isFirmato() && filePrimarioVerPubbl.getInfoFile().getTipoFirma().startsWith("CAdES")) {
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uriFileVerPubbl);
			FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
			File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()));
			uriFileVerPubbl = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(fileTmp.toURI().toString(), ""));
		}
		if (StringUtils.isNotBlank(uriFileVerPubbl)) {
			InputStream inputStream = null;
			if (filePrimarioVerPubbl.getInfoFile().getMimetype().equalsIgnoreCase("application/pdf")) {
				inputStream = StorageImplementation.getStorage().extract(uriFileVerPubbl);
			} else if (filePrimarioVerPubbl.getInfoFile().isConvertibile()) {
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFileVerPubbl);
				FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
				String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()))
						.toURI().toString();
				String uriFileVerPubblPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, ""));
				inputStream = StorageImplementation.getStorage().extract(uriFileVerPubblPdf);
			} else {
				addMessage("Il file primario " + filePrimarioVerPubbl.getNomeFileAllegato() + " (vers. pubblicazione) risulta in un formato non convertibile e sara quindi escluso dal documento",
						"", it.eng.utility.ui.module.core.shared.message.MessageType.WARNING);
			}
			if (inputStream != null) {
				return StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(inputStream));
			}
		}
		return null;
	}
	
	private File recuperaFilePrimarioOmissisToMerge(DocumentBean filePrimarioOmissis) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uriFileOmissis = filePrimarioOmissis.getUriFile();
		if (filePrimarioOmissis.getInfoFile() != null && filePrimarioOmissis.getInfoFile().isFirmato() && filePrimarioOmissis.getInfoFile().getTipoFirma().startsWith("CAdES")) {
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uriFileOmissis);
			FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
			File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()));
			uriFileOmissis = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(fileTmp.toURI().toString(), ""));
		}
		if (StringUtils.isNotBlank(uriFileOmissis)) {
			InputStream inputStream = null;
			if (filePrimarioOmissis.getInfoFile().getMimetype().equalsIgnoreCase("application/pdf")) {
				inputStream = StorageImplementation.getStorage().extract(uriFileOmissis);
			} else if (filePrimarioOmissis.getInfoFile().isConvertibile()) {
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFileOmissis);
				FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
				String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()))
						.toURI().toString();
				String uriFileOmissisPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, ""));
				inputStream = StorageImplementation.getStorage().extract(uriFileOmissisPdf);
			} else {
				addMessage("Il file primario " + filePrimarioOmissis.getNomeFile() + " (vers. con omissis) risulta in un formato non convertibile e sara quindi escluso dal documento",						"", it.eng.utility.ui.module.core.shared.message.MessageType.WARNING);
			}
			if (inputStream != null) {
				return StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(inputStream));
			}
		}
		return null;
	} 

	private File recuperaFileAllegatoToMerge(AllegatoProtocolloBean allegato) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uriFileAllegato = allegato.getUriFileAllegato();
		if (allegato.getInfoFile() != null && allegato.getInfoFile().isFirmato() && allegato.getInfoFile().getTipoFirma().startsWith("CAdES")) {
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uriFileAllegato);
			FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
			File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()));
			uriFileAllegato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(fileTmp.toURI().toString(), ""));
		}
		if (StringUtils.isNotBlank(uriFileAllegato)) {
			InputStream inputStream = null;
			if (allegato.getInfoFile().getMimetype().equalsIgnoreCase("application/pdf")) {
				inputStream = StorageImplementation.getStorage().extract(uriFileAllegato);
			} else if (allegato.getInfoFile().isConvertibile()) {
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFileAllegato);
				FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
				String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()))
						.toURI().toString();
				String uriFileAllegatoPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, ""));
				inputStream = StorageImplementation.getStorage().extract(uriFileAllegatoPdf);
			} else {
				addMessage("L'allegato " + allegato.getNomeFileAllegato() + " risulta in un formato non convertibile e sara quindi escluso dal documento",
						"", it.eng.utility.ui.module.core.shared.message.MessageType.WARNING);
			}
			if (inputStream != null) {
				return StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(inputStream));
			}
		}
		return null;
	}
	
	private File recuperaFileAllegatoOmissisToMerge(AllegatoProtocolloBean allegato) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uriFileAllegatoOmissis = allegato.getUriFileOmissis();
		if (allegato.getInfoFileOmissis() != null && allegato.getInfoFileOmissis().isFirmato() && allegato.getInfoFileOmissis().getTipoFirma().startsWith("CAdES")) {
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uriFileAllegatoOmissis);
			FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
			File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()));
			uriFileAllegatoOmissis = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(fileTmp.toURI().toString(), ""));
		}
		if (StringUtils.isNotBlank(uriFileAllegatoOmissis)) {
			InputStream inputStream = null;
			if (allegato.getInfoFileOmissis().getMimetype().equalsIgnoreCase("application/pdf")) {
				inputStream = StorageImplementation.getStorage().extract(uriFileAllegatoOmissis);
			} else if (allegato.getInfoFileOmissis().isConvertibile()) {
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFileAllegatoOmissis);
				FileExtractedOut lFileExtractedOut = new RecuperoFile().extractfile(getLocale(), loginBean, lFileExtractedIn);
				String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(lFileExtractedOut.getExtracted()))
						.toURI().toString();
				String uriFileAllegatoOmissisPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, ""));
				inputStream = StorageImplementation.getStorage().extract(uriFileAllegatoOmissisPdf);
			} else {
				addMessage("L'allegato " + allegato.getNomeFileOmissis() + " (vers. con omissis) risulta in un formato non convertibile e sara quindi escluso dal documento",
						"", it.eng.utility.ui.module.core.shared.message.MessageType.WARNING);
			}
			if (inputStream != null) {
				return StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(inputStream));
			}
		}
		return null;
	}

	/**
	 * Converte il file specificato in odt, ritornando il file di riferimento
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	private static File convertOdtToPdf(File file) throws Exception, IOException {
		InputStream pdfInputStream = OdtConverter.convertOdtToPdf(file);
		File filledTemplatePdf = convertInputStreamToFile(pdfInputStream, "filledTemplatePdf", ".pdf");
		return filledTemplatePdf;
	}

	/**
	 * @param pdfInputStream
	 * @return
	 * @throws IOException
	 */
	private static File convertInputStreamToFile(InputStream pdfInputStream, String filename, String extension) throws IOException {
		byte[] buffer = new byte[pdfInputStream.available()];
		pdfInputStream.read(buffer);
		File filledTemplatePdf = File.createTempFile(filename, extension);
		Files.write(buffer, filledTemplatePdf);
		return filledTemplatePdf;
	}

}
