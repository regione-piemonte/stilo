/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.FillPdfBean;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.core.business.FileUtil;
import it.eng.pdfmodeler.FillPdfEditable;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ConvertToPdfUtil;
import it.eng.utility.FormatConverterInterface;
import it.eng.utility.SbustaUtil;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Datasource(id = "FillPdfService")
public class FillPdfService extends AbstractServiceDataSource<FillPdfBean, FillPdfBean> {

	private static Logger mLogger = Logger.getLogger(FillPdfService.class);

	private StorageService storageService;

	public enum TIPI_PDF {

		QUADRO_PROGRAMMATICO("quadro_programmatico.pdf", "quadro_programmatico"), QUADRO_AMMINISTRATIVO("quadro_amministrativo.pdf", "quadro_amministrativo");

		private String nomeFile;
		private String nomeFileFinale;

		private TIPI_PDF(String nomeFile, String nomeFileFinale) {
			this.nomeFile = nomeFile;
			this.nomeFileFinale = nomeFileFinale;
		}

		public String getNomeFile() {
			return nomeFile;
		}

		public void setNomeFile(String nomeFile) {
			this.nomeFile = nomeFile;
		}

		public String getNomeFileFinale() {
			return nomeFileFinale;
		}

		public void setNomeFileFinale(String nomeFileFinale) {
			this.nomeFileFinale = nomeFileFinale;
		}
	}

	@Override
	public FillPdfBean call(FillPdfBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		FillPdfEditable lFillPdfEditable = null;

		if (pInBean.getUriFile() != null && !"".equals(pInBean.getUriFile())) {
			String mimetype = pInBean.getMimetype();
			boolean firmato = false;
			boolean convertibile = true;

			MimeTypeFirmaBean lMimeTypeFirmaBean = retrieveInfo(pInBean.getUriFile(), pInBean.getNomeFile(), getSession());
			if (lMimeTypeFirmaBean != null) {
				mimetype = lMimeTypeFirmaBean.getMimetype();
				firmato = lMimeTypeFirmaBean.isFirmato();
				convertibile = lMimeTypeFirmaBean.isConvertibile();
			}

			pInBean.setFirmato(firmato);

			String uriFileModello = pInBean.getUriFile();
			String nomeFileModello = pInBean.getNomeFile();

			if (firmato) {
				File tempFile = File.createTempFile("sbustato", ".tmp");
				InputStream lInputStreamExtracted = SbustaUtil.sbusta(pInBean.getUriFile(), "");
				FileUtil.writeStreamToFile(lInputStreamExtracted, tempFile);
				uriFileModello = StorageImplementation.getStorage().store(tempFile);
				lMimeTypeFirmaBean = retrieveInfo(uriFileModello, pInBean.getNomeFile(), getSession());
				if (lMimeTypeFirmaBean != null) {
					mimetype = lMimeTypeFirmaBean.getMimetype();
					convertibile = lMimeTypeFirmaBean.isConvertibile();
					nomeFileModello = lMimeTypeFirmaBean.getCorrectFileName();
				}
				if (nomeFileModello.toLowerCase().endsWith(".p7m")) {
					nomeFileModello = nomeFileModello.substring(0, nomeFileModello.length() - 4);
				}
			}

			if (StringUtils.isNotBlank(mimetype) && mimetype.equals("text/html")) {
				File fileHtml = StorageImplementation.getStorage().extractFile(uriFileModello);
				String html = FileUtils.readFileToString(fileHtml, "UTF-8");
				pInBean.setUriFilePdf(convertHtmlToPdf(html));
			} else if (StringUtils.isNotBlank(mimetype) && mimetype.equals("application/pdf")) {
				pInBean.setUriFilePdf(uriFileModello);
			} else if (convertibile) {
				boolean generaPdfa = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "GENERAZIONE_DA_MODELLO_ABILITA_PDFA");
				InputStream lInputStream = ConvertToPdfUtil.convertToPdf(uriFileModello, false, generaPdfa);
				pInBean.setUriFilePdf(StorageImplementation.getStorage().storeStream(lInputStream));
			} else {
				throw new Exception("Formato non convertibile in pdf");
			}

			pInBean.setNomeFilePdf(FilenameUtils.getBaseName(nomeFileModello) /* + "_" + pInBean.getIdProcess() */+ ".pdf");

		} else {
			lFillPdfEditable = new FillPdfEditable(FillPdfService.class.getClassLoader().getResourceAsStream(pInBean.getTipoPdf().getNomeFile()));
			DmpkProcessesGetdatixmodellipraticaBean bean = new DmpkProcessesGetdatixmodellipraticaBean();
			bean.setCodidconnectiontokenin(token);
			bean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			bean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
			DmpkProcessesGetdatixmodellipratica store = new DmpkProcessesGetdatixmodellipratica();
			StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), bean);
			if (result.isInError()) {
				throw new StoreException(result);
			}
			String xmlOut = result.getResultBean().getDatimodellixmlout();
			String lString = new String();
			InputStream lInputStream = IOUtils.toInputStream(lString);
			String uri = StorageImplementation.getStorage().storeStream(lInputStream, new String[] {});
			FileOutputStream lFileOutputStream = new FileOutputStream(StorageImplementation.getStorage().getRealFile(uri));
			lFillPdfEditable.fillPdf(xmlOut, lFileOutputStream);
			pInBean.setNomeFilePdf(FilenameUtils.getBaseName(pInBean.getTipoPdf().getNomeFileFinale()) /* + "_" + pInBean.getIdProcess() */+ ".pdf");
			pInBean.setUriFilePdf(uri);
		}
		return pInBean;
	}

	public String convertHtmlToPdf(String html) throws Exception {

		String lStrHtml = html;
		String contenuto = lStrHtml;
		lStrHtml = lStrHtml.replace("charset=UTF-8\">", "charset=UTF-8\"/>");
		lStrHtml = lStrHtml.replace("><style", "/><style");
		lStrHtml = replaceAll(lStrHtml, "width:21cm;", "");
		lStrHtml = replaceAll(lStrHtml, "height:29.7cm;", "");
		lStrHtml = replaceAll(lStrHtml, "border:1px solid grey;", "");
		lStrHtml = replaceAll(lStrHtml, "margin-top:40;", "");
		lStrHtml = replaceAll(lStrHtml, "margin-bottom:40;", "");
		lStrHtml = replaceAll(lStrHtml, "margin-left:auto;", "");
		lStrHtml = replaceAll(lStrHtml, "margin-right:auto;", "");
		lStrHtml = replaceAll(lStrHtml, "padding: 40px;", "");
		lStrHtml = replaceAll(lStrHtml, "-moz-box-shadow:0 0 25px #333333;", "");
		lStrHtml = replaceAll(lStrHtml, "-webkit-box-shadow:0 0 25px #333333;", "");
		lStrHtml = replaceAll(lStrHtml, "box-shadow:0 0 25px #333333;", "");
		lStrHtml = replaceAll(lStrHtml, "<br>", "<br/>");

		int count = 0;
		while (contenuto.indexOf("A4div") != -1) {
			count++;
			contenuto = contenuto.substring(contenuto.indexOf("A4div") + "A4div".length());
		}

		if (count == 0)
			count = 1;

		String totalHtml = lStrHtml;
		int[] pagine = new int[count];
		pagine[0] = 0;
		for (int i = 1; i < count; i++) {
			int finePagina = getPosizionePagina(totalHtml, i);
			pagine[i] = finePagina;
			totalHtml = totalHtml.substring(finePagina);
		}

		String allHtml = lStrHtml;
		String[] lStringPagine = new String[count];

		int advancingPosition = 0;
		for (int j = 0; j < count; j++) {
			if (j + 1 < count) {
				lStringPagine[j] = allHtml.substring(advancingPosition, advancingPosition + pagine[j + 1]);
				advancingPosition = advancingPosition + pagine[j + 1];
			} else {
				lStringPagine[j] = allHtml.substring(advancingPosition);
			}
		}

		int counter = 0;
		String[] files = new String[count];
		for (String lString : lStringPagine) {

			if (!lString.replaceAll(" ", "").startsWith("<html><body>")) {
				lString = "<html><body>" + lString;
			}
			if (!lString.replaceAll(" ", "").endsWith("</body></html>")) {
				lString = lString + "</body></html>";
			}

			File lFilePage = File.createTempFile("tmp", "pdf");
			Document document = new Document();
			// step 2
			PdfWriter lPdfWriter = PdfWriter.getInstance(document, new FileOutputStream(lFilePage));
			// step 3
			document.open();
			// step 4
			XMLWorkerHelper.getInstance().parseXHtml(lPdfWriter, document, new ByteArrayInputStream(lString.getBytes("UTF-8")), Charset.forName("UTF-8"));
			// htmlWorker.parse(new StringReader(lStrHtml));
			document.close();
			files[counter] = lFilePage.getPath();
			counter++;

		}

		File totalFile = File.createTempFile("tmp", "pdf");
		Document document = new Document();
		// step 2
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(totalFile));
		// step 3
		document.open();
		// step 4
		PdfReader reader;
		int n;
		// loop over the documents you want to concatenate
		for (int pageFile = 0; pageFile < count; pageFile++) {
			reader = new PdfReader(files[pageFile]);
			// loop over the pages in that document
			n = reader.getNumberOfPages();
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			copy.freeReader(reader);
			reader.close();
		}
		// step 5
		document.close();

		String uriPdfGenerato = StorageImplementation.getStorage().store(totalFile, new String[] {});

		return uriPdfGenerato;
	}

	private int getPosizionePagina(String lStrhtml, int numeroPagina) {
		int position = lStrhtml.indexOf("A4div" + numeroPagina);
		for (int k = position; k > 0; k--) {
			if (lStrhtml.substring(k, k + 4).equals("<div")) {
				return k;
			}
		}
		return -1;
	}

	public String replaceAll(String pString, String expression, String value) {
		while (pString.indexOf(expression) != -1) {
			pString = pString.replace(expression, value);
		}
		return pString;
	}

	private MimeTypeFirmaBean retrieveInfo(String lStrPath, String pStrDisplayFileName, HttpSession session) throws Exception {
		// Inizializzo lo storage
		initStorageService();

		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setDaScansione(false);
		try {
			lMimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(storageService.getRealFile(lStrPath).toURI().toString(), pStrDisplayFileName, false, null);
			lMimeTypeFirmaBean.setConvertibile(SpringAppContext.getContext().getBean(FormatConverterInterface.class)
					.isValidFormat(lMimeTypeFirmaBean.getMimetype(), session));
			return lMimeTypeFirmaBean;
		} catch (Exception e) {
			throw e;
		}
	}

	private void initStorageService() {
		storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {
				ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
				mLogger.debug("Recuperato module config");
				if (mc != null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
					mLogger.debug("Id Storage vale " + mc.getIdDbStorage());
					return mc.getIdDbStorage();
				} else {
					mLogger.error("L'identificativo del DB di storage non è correttamente configurato, " + "controllare il file di configurazione del modulo.");
					return null;
				}
			}
		});
	}

}