/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.compiler.odt.HtmlTransformer;
import it.eng.auriga.compiler.odt.OdtCompiler;
import it.eng.auriga.compiler.odt.OdtConverter;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EditorHtmlBean;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.client.DmpkProcessesIuassociazioneudvsproc;
import it.eng.client.DmpkProcessesIueventocustom;
import it.eng.client.GestioneDocumenti;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "EditorHtmlDataSource")
public class EditorHtmlDataSource extends AbstractServiceDataSource<EditorHtmlBean, EditorHtmlBean> {

	private static Logger mLogger = Logger.getLogger(EditorHtmlDataSource.class);

	@Override
	public EditorHtmlBean call(EditorHtmlBean pInBean) throws Exception {
		mLogger.debug("Start call");
		String flgIgnoreObblig = getExtraparams().get("flgIgnoreObblig");

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		addOrUpdateDocument(loginBean, pInBean);

		DmpkProcessesIueventocustomBean lBean = new DmpkProcessesIueventocustomBean();
		lBean.setCodidconnectiontokenin(token);
		lBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		lBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
		lBean.setIdeventoio(pInBean.getIdEvento() != null ? new BigDecimal(pInBean.getIdEvento()) : null);
		lBean.setIdtipoeventoin(new BigDecimal(pInBean.getIdTipoEvento()));
		lBean.setIdudassociatain(StringUtils.isNotBlank(pInBean.getIdUd()) ? new BigDecimal(pInBean.getIdUd()) : null);

		lBean.setDeseventoin(pInBean.getDesEvento());
		lBean.setMessaggioin(pInBean.getMessaggio());

		if (StringUtils.isNotBlank(flgIgnoreObblig)) {
			lBean.setFlgignoraobbligin(new Integer(flgIgnoreObblig));
		}

		if (pInBean.getValori() != null && pInBean.getValori().size() > 0) {

			SezioneCache lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(pInBean.getIdProcess(), pInBean.getValori(),
					pInBean.getTipiValori(), getSession());
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			lBean.setAttributiaddin(lStringWriter.toString());
		}

		DmpkProcessesIueventocustom store = new DmpkProcessesIueventocustom();

		StoreResultBean<DmpkProcessesIueventocustomBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()) {
			throw new StoreException(result);
		}

		addMessage("Salvataggio effettuato con successo", "", MessageType.INFO);

		pInBean.setIdEvento(String.valueOf(result.getResultBean().getIdeventoio()));

		return pInBean;
	}

	private void addOrUpdateDocument(AurigaLoginBean loginBean, EditorHtmlBean pInBean) throws Exception {
		mLogger.debug("Start addOrUpdateDocument");

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		File fileHtml = File.createTempFile("editor", ".html");
		String descrizione = pInBean.getDescrizione();
		if (!pInBean.getDescrizione().startsWith("<html><body>")) {
			descrizione = "<html><body>" + descrizione;
		}
		if (!pInBean.getDescrizione().endsWith("</body></html>")) {
			descrizione = descrizione + "</body></html>";
		}
		pInBean.setDescrizione(descrizione);
		pInBean.setDescrizione(pInBean.getDescrizione().replace("><style", "/><style"));
		FileUtils.writeStringToFile(fileHtml, pInBean.getDescrizione(), "UTF-8");

		StorageService storageService = StorageImplementation.getStorage();
		String uri = storageService.store(fileHtml);

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(StringUtils.isNotBlank(pInBean.getIdDocAssTask()) ? new BigDecimal(pInBean.getIdDocAssTask()) : null);
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);

		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype("text/html");
		lGenericFile.setDisplayFilename(pInBean.getNomeFileDocTipAssTask());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());

		lFileInfoBean.setAllegatoRiferimento(lGenericFile);

		lRebuildedFile.setInfo(lFileInfoBean);

		if (StringUtils.isNotBlank(pInBean.getIdDocAssTask())) {

			mLogger.debug("idDoc presente -> versiono il documento");
			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

			VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);

			if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
				mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutBean);
			}

		} else {

			FilePrimarioBean lFilePrimarioBean = new FilePrimarioBean();
			lFilePrimarioBean.setIdDocumento(StringUtils.isNotBlank(pInBean.getIdDocAssTask()) ? new BigDecimal(pInBean.getIdDocAssTask()) : null);
			lFilePrimarioBean.setIsNewOrChanged(true);
			lFilePrimarioBean.setInfo(lFileInfoBean);

			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setTipoDocumento(pInBean.getIdDocTipDocTask());
			lCreaModDocumentoInBean.setNomeDocType(null);

			lFilePrimarioBean.setFile(fileHtml);

			CreaModDocumentoOutBean lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean,
					lFilePrimarioBean, null);

			if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaModDocumentoOutBean);
			}

			DmpkProcessesIuassociazioneudvsprocBean dmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
			dmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
			dmpkProcessesIuassociazioneudvsprocBean.setIdudin(lCreaModDocumentoOutBean.getIdUd());
			dmpkProcessesIuassociazioneudvsprocBean.setNroordinevsprocin(null);
			dmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin("A");
			dmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin("PARTEIST");

			DmpkProcessesIuassociazioneudvsproc dmpkProcessesIuassociazioneudvsproc = new DmpkProcessesIuassociazioneudvsproc();
			StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = dmpkProcessesIuassociazioneudvsproc.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), dmpkProcessesIuassociazioneudvsprocBean);

			if (result.isInError()) {
				throw new StoreException(result);
			}

			pInBean.setIdUd(lCreaModDocumentoOutBean.getIdUd() != null ? String.valueOf(lCreaModDocumentoOutBean.getIdUd().longValue()) : null);
		}

	}

	public EditorHtmlBean loadDati(EditorHtmlBean pInBean) throws Exception {
		mLogger.debug("Start loadDati");
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		// Inserisco la lingua di default
		// loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
		loginBean.setLinguaApplicazione(getLocale().getLanguage());
		EditorHtmlBean bean = new EditorHtmlBean();

		if (StringUtils.isNotBlank(pInBean.getUriUltimaVersDocTask())) {

			File fileHtml = StorageImplementation.getStorage().extractFile(pInBean.getUriUltimaVersDocTask());
			String descrizione = FileUtils.readFileToString(fileHtml, "UTF-8");
			descrizione = addContextMenuToPage(descrizione, pInBean.getSmartId());
			bean.setDescrizione(descrizione);

		} else if (StringUtils.isNotBlank(pInBean.getUriModAssDocTask())) {
			DmpkProcessesGetdatixmodellipraticaBean dmpkProcessesGetdatixmodellipraticaBean = new DmpkProcessesGetdatixmodellipraticaBean();
			dmpkProcessesGetdatixmodellipraticaBean.setCodidconnectiontokenin(token);
			dmpkProcessesGetdatixmodellipraticaBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			dmpkProcessesGetdatixmodellipraticaBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));

			DmpkProcessesGetdatixmodellipratica storeDmpkProcessesGetdatixmodellipratica = new DmpkProcessesGetdatixmodellipratica();
			StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> result = storeDmpkProcessesGetdatixmodellipratica.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), dmpkProcessesGetdatixmodellipraticaBean);

			if (result.isInError()) {
				throw new StoreException(result);
			}

			String descr = showOdtToHtml(pInBean.getUriModAssDocTask());
			String compilato = new OdtCompiler().fillHtmlDocument(result.getResultBean().getDatimodellixmlout(),
					new String(descr.getBytes(), Charset.forName("UTF-8")));
			compilato = HtmlTransformer.convertInputIntoParagraph(compilato);
			compilato = compilato
					.replace(
							"</style>",
							"</style><div id='A4div' style='width:21cm;height:29.7cm;border:1px solid grey;margin-top:40;margin-bottom:40;margin-left:auto;margin-right:auto;padding: 40px;"
									+ "-moz-box-shadow:0 0 25px #333333;-webkit-box-shadow:0 0 25px #333333;box-shadow:0 0 25px #333333;'"
									+ "oncontextmenu=\"window.top.pageContextMenu" + pInBean.getSmartId() + "('" + 0 + "', event); return false; \">");
			compilato = compilato.replace("</body>", "</div></body>");
			// FileUtils.write(new File("C:\\data\\saved.html"), compilato, "UTF-8");
			bean.setDescrizione(compilato);
		}
		return bean;
	}

	private String addContextMenuToPage(String descrizione, String smartId) {
		String contenuto = descrizione;
		int count = 0;
		while (contenuto.indexOf("A4div") != -1) {
			count++;
			contenuto = contenuto.substring(contenuto.indexOf("A4div") + "A4div".length());
		}
		// Sistemo la prima pagina
		int indicePagina = descrizione.indexOf("A4div") + "A4div".length();
		if (indicePagina != -1) {
			int fine = descrizione.substring(indicePagina).indexOf(">");
			String tagDiv = descrizione.substring(indicePagina, indicePagina + fine);
			if (!tagDiv.toLowerCase().contains("oncontextmenu")) {
				// Aggiungo lo script -- siamo in un vecchio caricamento
				descrizione = descrizione.substring(0, indicePagina + fine) + "oncontextmenu=\"window.top.pageContextMenu" + smartId + "('" + 0
						+ "', event); return false; \"" + descrizione.substring(indicePagina + fine);
			}
		}

		for (int i = 1; i < count; i++) {
			indicePagina = descrizione.indexOf("A4div" + i) + ("A4div" + i).length();
			if (indicePagina != -1) {
				int fine = descrizione.substring(indicePagina).indexOf(">");
				String tagDiv = descrizione.substring(indicePagina, indicePagina + fine);
				if (!tagDiv.toLowerCase().contains("oncontextmenu")) {
					// Aggiungo lo script -- siamo in un vecchio caricamento
					descrizione = descrizione.substring(0, indicePagina + fine) + "oncontextmenu=\"window.top.pageContextMenu" + smartId + "('" + i
							+ "', event); return false; \"" + descrizione.substring(indicePagina + fine);
				}
			}
		}
		// Sistemo le altre pagine
		return descrizione;
	}

	public EditorHtmlBean aggiungiPagina(EditorHtmlBean pInBean) throws Exception {
		String descrizione = pInBean.getDescrizione();
		String contenuto = descrizione;
		int count = 0;
		while (contenuto.indexOf("A4div") != -1) {
			count++;
			contenuto = contenuto.substring(contenuto.indexOf("A4div") + "A4div".length());
		}
		// descrizione = descrizione + "<div id='A4div" + count + "' style='width:21cm;height:29.7cm;border-style:solid' " +
		// "onmousedown=\"var rightclick;var e = window.event;if (e.which) rightclick = (e.which == 3);" +
		// "else if (e.button) rightclick = (e.button == 2);if(rightclick) window.top.pageContextMenu" + pInBean.getSmartId() + "('" + count +
		// "'); e.preventDefault(); return false; \"><p>Nuova pagina...</p></div>";
		descrizione = descrizione + "<div id='A4div" + count
				+ "' style='width:21cm;height:29.7cm;border:1px solid grey;margin-top:40;margin-bottom:40;margin-left:auto;margin-right:auto;padding: 40px;"
				+ "-moz-box-shadow:0 0 25px #333333;-webkit-box-shadow:0 0 25px #333333;box-shadow:0 0 25px #333333;'"
				+ "oncontextmenu=\"window.top.pageContextMenu" + pInBean.getSmartId() + "('" + count + "', event); return false; \"></div>";
		pInBean.setDescrizione(descrizione);
		return pInBean;
	}

	public EditorHtmlBean eliminaPagina(EditorHtmlBean pInBean) throws Exception {
		String contenuto = pInBean.getDescrizione();
		String htmlFinale = null;
		int idPagina = Integer.valueOf(getExtraparams().get("pageNumber"));
		int count = 0;
		while (contenuto.indexOf("A4div") != -1) {
			count++;
			contenuto = contenuto.substring(contenuto.indexOf("A4div") + "A4div".length());
		}

		if (count == 0)
			count = 1;

		// Sistemo la prima pagina
		boolean isLastPage = pInBean.getDescrizione().indexOf("A4div" + (idPagina + 1)) == -1;
		int indicePaginaSuccessiva = pInBean.getDescrizione().indexOf("A4div" + (idPagina + 1)) + ("A4div" + (idPagina + 1)).length();
		if (!isLastPage) {
			// non è l'ultima pagina
			int fine = 0;
			for (int i = indicePaginaSuccessiva; i > 0; i--) {
				if (pInBean.getDescrizione().charAt(i) == '>') {
					fine = i;
					break;
				}
			}
			int indicePagina = pInBean.getDescrizione().indexOf("A4div" + (idPagina)) + ("A4div" + (idPagina)).length();
			int inizioPagina = 0;
			for (int i = indicePagina; i > 0; i--) {
				if (pInBean.getDescrizione().charAt(i) == '<') {
					inizioPagina = i;
					break;
				}
			}
			htmlFinale = pInBean.getDescrizione().substring(0, inizioPagina) + pInBean.getDescrizione().substring(fine + 1, pInBean.getDescrizione().length());
			for (int k = idPagina + 1; k < count; k++) {
				htmlFinale = htmlFinale.replace("A4div" + k, "A4div" + (k - 1)).replace(k + "', event)", k - 1 + "', event)");
			}
			mLogger.debug(htmlFinale);
		} else {
			// è l'ultima pagina
			int indicePagina = pInBean.getDescrizione().indexOf("A4div" + (idPagina)) + ("A4div" + (idPagina)).length();
			int inizioPagina = 0;
			for (int i = indicePagina; i > 0; i--) {
				if (pInBean.getDescrizione().charAt(i) == '<') {
					inizioPagina = i;
					break;
				}
			}
			htmlFinale = pInBean.getDescrizione().substring(0, inizioPagina);
		}

		pInBean.setDescrizione(htmlFinale);
		return pInBean;
	}

	public EditorHtmlBean generaFileHtml(EditorHtmlBean pInBean) throws Exception {

		File fileHtml = File.createTempFile("editor", ".html");
		String descrizione = pInBean.getDescrizione();
		if (!pInBean.getDescrizione().replaceAll(" ", "").startsWith("<html><body>")) {
			descrizione = "<html><body>" + descrizione;
		}
		if (!pInBean.getDescrizione().replaceAll(" ", "").endsWith("</body></html>")) {
			descrizione = descrizione + "</body></html>";
		}
		pInBean.setDescrizione(descrizione);
		pInBean.setDescrizione(pInBean.getDescrizione().replace("><style", "/><style"));
		FileUtils.writeStringToFile(fileHtml, pInBean.getDescrizione(), "UTF-8");

		String uriHtmlGenerato = StorageImplementation.getStorage().store(fileHtml);
		pInBean.setUriHtmlGenerato(uriHtmlGenerato);
		return pInBean;
	}

	public EditorHtmlBean convertToPdf(EditorHtmlBean pInBean) throws Exception {

		String lStrHtml = pInBean.getDescrizione();

		FillPdfService lFillPdfService = new FillPdfService();
		pInBean.setUriPdfGenerato(lFillPdfService.convertHtmlToPdf(lStrHtml));

		return pInBean;
	}

	public String showOdtToHtml(String uriPathFile) throws Exception {

		File lFile = StorageImplementation.getStorage().extractFile(uriPathFile);
		// FileUtils.copyFile(lFile, new File("C:\\testOdt\\file.odt"));
		// File lFile = new File("C:\\testOdt\\file.odt");
		return OdtConverter.convertOdtToHtml(lFile);

	}

}
