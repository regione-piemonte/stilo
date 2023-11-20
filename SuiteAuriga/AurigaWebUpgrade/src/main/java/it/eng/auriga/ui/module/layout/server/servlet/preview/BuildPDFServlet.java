/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sun.jersey.core.util.Base64;
import com.sun.star.uno.RuntimeException;

import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsInfoBean;
import it.eng.client.MailProcessorService;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.proxy.ProxyManager;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.servlet.fileExtractor.EmailDetailToExtractBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Controller
@RequestMapping("/createpdf")
/**
 * 
 * Genera la stampa pdf della mail specificata nella request
 *
 */
public class BuildPDFServlet {

	private static Logger logger = Logger.getLogger(BuildPDFServlet.class);

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<String> download(@RequestParam("record") String record, @RequestParam("mimetype") String mimetype, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {

		HttpHeaders header = new HttpHeaders();
		if(mimetype != null && !"".equals(mimetype)) {
			// oltre a "text/html", anche per i mimetype "application/xhtml+xml" e "image/svg+xml" restituire il content-type = "text/plain" per non farlo eseguire dal client in caso ci fossero degli script al suo interno
			if("text/html".equalsIgnoreCase(mimetype) || "application/xhtml+xml".equalsIgnoreCase(mimetype) || "image/svg+xml".equalsIgnoreCase(mimetype)) {
				header.setContentType(MediaType.parseMediaType("text/plain"));
			} else {
				header.setContentType(MediaType.parseMediaType(mimetype));	
			}
		} else {
			header.setContentType(new MediaType("octet", "stream"));
		}

		FileInputStream fis = null;
		BufferedOutputStream pdfFileStream = null;

		try {
			EmailDetailToExtractBean bean = (EmailDetailToExtractBean) getRecord(req);

			logger.info("BuildPDFServlet -> download con IdEmail: " + bean.getIdEmail());
			fis = getStreamHTMLForPDF(req.getSession(), bean);

			boolean completa = "true".equals(bean.getCompleta()) ? true : false;

			// la stampa pdf
			File pdfTempFile = File.createTempFile("generatedPdf", ".pdf");
			pdfTempFile.deleteOnExit();

			pdfFileStream = new BufferedOutputStream(new FileOutputStream(pdfTempFile));

			if (completa) {
				createPDF(pdfFileStream, bean, fis);
			} else {
				createPDFSolotesto(pdfFileStream, bean, fis);
			}

			header.setContentLength(pdfTempFile.getAbsolutePath().length());
			header.setCacheControl("public");
			header.add("Cache-Control", "public");

			return new HttpEntity<String>(pdfTempFile.getAbsolutePath());
		} catch (Exception e) {
			logger.error("Errore download " + e.getMessage(), e);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);
			responseHeaders.setCacheControl("private, no-store, no-cache, must-revalidate");
			responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
			responseHeaders.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<head>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<h1>File not found</h1>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} finally {
			IOUtils.closeQuietly(pdfFileStream);
			IOUtils.closeQuietly(fis);
		}
	}

	@RequestMapping(value = "getHtml", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<String> getHtml(@RequestParam("record") String record, @RequestParam("mimetype") String mimetype, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException {

		HttpHeaders header = new HttpHeaders();
		if(mimetype != null && !"".equals(mimetype)) {
			if (!mimetype.contains("html")) {
				throw new RuntimeException("Errore: il metodo può essere chiamato solo per content-type text/html");
			}
			// oltre a "text/html", anche per i mimetype "application/xhtml+xml" e "image/svg+xml" restituire il content-type = "text/plain" per non farlo eseguire dal client in caso ci fossero degli script al suo interno
			if("text/html".equalsIgnoreCase(mimetype) || "application/xhtml+xml".equalsIgnoreCase(mimetype) || "image/svg+xml".equalsIgnoreCase(mimetype)) {
				header.setContentType(MediaType.parseMediaType("text/plain"));
			} else {
				header.setContentType(MediaType.parseMediaType(mimetype));	
			}
		} else {
			header.setContentType(new MediaType("octet", "stream"));
		}

		BufferedWriter fileWriter = null;
		try {
			EmailDetailToExtractBean bean = (EmailDetailToExtractBean) getRecord(req);
			logger.info("BuildPDFServlet -> getHtml con IdEmail: " + bean.getIdEmail());

			String fis = getStreamHTML(req.getSession(), bean);
			
			final File tempFileHtml = File.createTempFile("Cleaner", ".html");
			tempFileHtml.deleteOnExit();
			fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFileHtml), "UTF-8"));
			fileWriter.write(fis);

			header.setContentLength(tempFileHtml.getAbsolutePath().length());
			header.setCacheControl("public");
			header.add("Cache-Control", "public");
			header.add("charset", "UTF-8");

			return new HttpEntity<String>(tempFileHtml.getAbsolutePath());
		} catch (Exception e) {
			logger.error("Errore download " + e.getMessage(), e);
			throw new RuntimeException("Errore download " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(fileWriter);
		}
	}

	private Object getRecord(HttpServletRequest req) {
		String recordString = req.getParameter("record").replaceAll("%26", "&");
		GsonBuilder builder = GsonBuilderFactory.getIstance();
		Gson gson = builder.create();
		return gson.fromJson(recordString, EmailDetailToExtractBean.class);
	}

	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

	private static Font curierFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);

	private static Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	private String getStreamHTML(HttpSession httpSession, EmailDetailToExtractBean bean) {
		// Ricavo la locale dal bean in sessione
		it.eng.utility.ui.module.layout.shared.bean.LoginBean sessionLoginBean = (it.eng.utility.ui.module.layout.shared.bean.LoginBean) httpSession
				.getAttribute("LOGIN_INFO");
		String userLanguage = "it";
		if ((sessionLoginBean != null) && (sessionLoginBean.getLinguaApplicazione() != null)
				&& (!sessionLoginBean.getLinguaApplicazione().equalsIgnoreCase(""))) {
			userLanguage = sessionLoginBean.getLinguaApplicazione();
		}
		Locale locale = new Locale(userLanguage);

		String fisHTML = null;

		try {
			MailProcessorService lMailProcessorService = new MailProcessorService();
			EmailInfoBean lEmailInfoBean = null;
			String uriFileEml = null;
			EmailAttachsBean lEmailAttachsBean = null;

			if (StringUtils.isNotBlank(bean.getIdEmail())) {

				lEmailInfoBean = lMailProcessorService.getbodyhtmlbyidemail(locale, bean.getIdEmail());
				lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(locale, bean.getIdEmail());
				if (StringUtils.isEmpty(lEmailInfoBean.getMessaggio()) && StringUtils.isEmpty(bean.getBody())) {
					EmailInfoBean ei = lMailProcessorService.getbodytextbyidemail(locale, bean.getIdEmail());
					bean.setBody(ei.getMessaggio());
					return ei.getMessaggio();
				}

			} else {

				uriFileEml = bean.getUriFileEml();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFileEml);
				FileExtractedOut out = new RecuperoFile().extractfile(locale, AurigaUserUtil.getLoginInfo(httpSession), lFileExtractedIn);

				File emlFile = out.getExtracted();
				lEmailInfoBean = lMailProcessorService.getbodyhtml(locale, emlFile);
				lEmailAttachsBean = lMailProcessorService.getattachments(locale, emlFile);

				if (StringUtils.isEmpty(lEmailInfoBean.getMessaggio()) && StringUtils.isEmpty(bean.getBody())) {
					EmailInfoBean ei = lMailProcessorService.gettextinmainbody(locale, emlFile);
					bean.setBody(ei.getMessaggio());
					return ei.getMessaggio();
				}

			}

			int count = 0;

			final Map<String, String> lMap = new HashMap<String, String>();
			final Map<String, String> lMapMimetype = new HashMap<String, String>();

			String allegati = "";

			if (lEmailAttachsBean.getMailAttachments() != null) {
				for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {
					if (StringUtils.contains(info.getMimetype(), "image") && (info.getDisposition() == null || !"attachment".equals(info.getDisposition()))) {
						String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));

						InputStream in = (FileInputStream) StorageImplementation.getStorage().extract(uriAttach);
						final File tempFile = File.createTempFile("mailAttach", ".jpg");
						tempFile.deleteOnExit();
						FileOutputStream fos = new FileOutputStream(tempFile);
						try {
							IOUtils.copy(in, fos);
						} finally {
							IOUtils.closeQuietly(fos);
						}

						String cid = info.getContentID();

						if (cid != null && cid.startsWith("<") && cid.endsWith(">")) {
							cid = cid.substring(1, cid.length() - 1);
							lMap.put(cid, encodeToBase64String(tempFile));

							String[] infoMimetype = info.getMimetype().split(";");
							lMapMimetype.put(cid, infoMimetype[0]);
						}

					}
					count++;
					allegati += info.getFilename() + ";";
				}
			}

			if (allegati.length() > 0) {
				allegati = allegati.substring(0, allegati.length() - 1);

				bean.setAllegati(allegati);
			}

			String html = lEmailInfoBean.getMessaggio();

			convertSpecialCharacters(html);

			org.jsoup.nodes.Document emailBody = Jsoup.parse(html);

			// normalizza la sintassi del documento rispettando le direttive xml, la libreria di generazione del pdf si aspetta html valido
			emailBody.outputSettings().syntax(Syntax.xml);

			// estraggo tutte le immagini, per rimpiazzare l'attributo src originale con il path relativo al server in cui si trova la mail
			Elements images = emailBody.select("img");
			Iterator<org.jsoup.nodes.Element> imagesIterator = images.iterator();

			while (imagesIterator.hasNext()) {

				org.jsoup.nodes.Element currentImage = imagesIterator.next();

				String originalSrc = currentImage.attr("src");

				String[] splittedSrc = originalSrc.split(":");

				String imageBase64 = (String) lMap.get(splittedSrc[1]);

				String mimetype = lMapMimetype.get(splittedSrc[1]);

				// rimpiazzo il nome logico con quello del file temporaneo fisico generato in locale

				// l'immagine è tra quelle salvate in auriga
				if (imageBase64 != null) {
					currentImage.attr("src", "data:" + mimetype + ";base64," + imageBase64);
				}
			}
			fisHTML = emailBody.outerHtml();
		} catch (

		Exception e) {
			logger.error("Errore download " + e.getMessage(), e);
		}
		return fisHTML;
	}

	public String encodeToBase64String(File file) throws Exception {

		BufferedImage image = ImageIO.read(file);

		String extension = FilenameUtils.getExtension(file.getName());

		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, extension, bos);
			byte[] imageBytes = bos.toByteArray();

			imageString = org.apache.commons.codec.binary.Base64.encodeBase64String(imageBytes);
		} finally {
			IOUtils.closeQuietly(bos);
		}

		return imageString;
	}

	private FileInputStream getStreamHTMLForPDF(HttpSession httpSession, EmailDetailToExtractBean bean) {
		// Ricavo la locale dal bean in sessione
		it.eng.utility.ui.module.layout.shared.bean.LoginBean sessionLoginBean = (it.eng.utility.ui.module.layout.shared.bean.LoginBean) httpSession
				.getAttribute("LOGIN_INFO");
		String userLanguage = "it";
		if ((sessionLoginBean != null) && (sessionLoginBean.getLinguaApplicazione() != null)
				&& (!sessionLoginBean.getLinguaApplicazione().equalsIgnoreCase(""))) {
			userLanguage = sessionLoginBean.getLinguaApplicazione();
		}
		Locale locale = new Locale(userLanguage);

		FileInputStream fisHTML = null;
		BufferedWriter fileWriter = null;
		try {
			MailProcessorService lMailProcessorService = new MailProcessorService();
			String uriFileEml = null;
			EmailInfoBean lEmailInfoBean = null;
			EmailAttachsBean lEmailAttachsBean = null;
			if (StringUtils.isNotBlank(bean.getIdEmail())) {
				lEmailInfoBean = lMailProcessorService.getbodyhtmlbyidemail(locale, bean.getIdEmail());
				lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(locale, bean.getIdEmail());

				if (StringUtils.isEmpty(lEmailInfoBean.getMessaggio())) {
					if (StringUtils.isEmpty(bean.getBody())) {
						EmailInfoBean ei = lMailProcessorService.getbodytextbyidemail(locale, bean.getIdEmail());
						bean.setBody(ei.getMessaggio());
					}
					return null;
				}

			} else {

				uriFileEml = bean.getUriFileEml();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriFileEml);
				FileExtractedOut out = new RecuperoFile().extractfile(locale, AurigaUserUtil.getLoginInfo(httpSession), lFileExtractedIn);
				File emlFile = out.getExtracted();
				lEmailInfoBean = lMailProcessorService.gethtmlinmainbody(locale, emlFile);
				lEmailAttachsBean = lMailProcessorService.getattachments(locale, emlFile);

				if (StringUtils.isEmpty(lEmailInfoBean.getMessaggio())) {
					if (StringUtils.isEmpty(bean.getBody())) {
						EmailInfoBean ei = lMailProcessorService.gettextinmainbody(locale, emlFile);
						bean.setBody(ei.getMessaggio());
					}
					return null;
				}

			}
			if (StringUtils.isNotBlank(uriFileEml)) {

				int count = 0;

				final Map<String, File> lMap = new HashMap<String, File>();

				String allegati = "";
				// Federico Cacco 29.09.2106
				// Estraggo gli allegati se non sono già stati caricati in precedenza
				if ((lEmailAttachsBean.getMailAttachments() != null) && (StringUtils.isBlank(bean.getAllegati()))) {
					for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {
						if (StringUtils.contains(info.getMimetype(), "image")
								&& (info.getDisposition() == null || !"attachment".equals(info.getDisposition()))) {
							String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));
							InputStream in = (FileInputStream) StorageImplementation.getStorage().extract(uriAttach);
							final File tempFile = File.createTempFile("mailAttach", "");
							tempFile.deleteOnExit();
							FileOutputStream fos = new FileOutputStream(tempFile);
							try {
								IOUtils.copy(in, fos);
							} catch (Exception e) {
								logger.error(e.getMessage() + e);

							}
							String cid = info.getContentID();

							if (cid != null && cid.startsWith("<") && cid.endsWith(">")) {
								cid = cid.substring(1, cid.length() - 1);
								lMap.put(cid, tempFile);
							}
						}
						count++;
						allegati += info.getFilename() + ";";
					}
				}

				if (allegati.length() > 0) {
					allegati = allegati.substring(0, allegati.length() - 1);
					bean.setAllegati(allegati);
				}
				String html = lEmailInfoBean.getMessaggio();
				convertSpecialCharacters(html);
				org.jsoup.nodes.Document emailBody = Jsoup.parse(html);
				// normalizza la sintassi del documento rispettando le direttive xml, la libreria di generazione del pdf si aspetta html valido
				emailBody.outputSettings().syntax(Syntax.xml);
				// estraggo tutte le immagini, per rimpiazzare l'attributo src originale con il path relativo al server in cui si trova la mail
				Elements images = emailBody.select("img");
				Iterator<org.jsoup.nodes.Element> imagesIterator = images.iterator();
				// cache delle eventuali immagini remote che sono state scaricate in locale, evita di scaricare più volte lo stesso file
				Map<String, String> downloadedRemoteImages = new HashMap<String, String>();
				// proxy necessario per recuperare le immagini remote
				ProxyManager proxyManager = null;
				try {
					proxyManager = (ProxyManager) SpringAppContext.getContext().getBean("aurigaProxyManager");
				} catch (BeansException e) {
					String message = "Il proxy manager non è stato correttamente configurato, le immagini remote eventualmente presenti nelle mail, non verranno renderizzate in fase di stampa pdf "
							+ e.getMessage();
					logger.warn(message, e);
				}
				boolean useProxy = proxyManager != null && proxyManager.needProxy();
				Proxy proxy = null;
				String encodedUserPwd = null;
				if (useProxy) {
					SocketAddress socketAddress = new InetSocketAddress(proxyManager.getProxySetter().getHost(),
							Integer.valueOf(proxyManager.getProxySetter().getPort()));
					proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
					encodedUserPwd = new String(
							Base64.encode(new String(proxyManager.getProxySetter().getUser() + ":" + proxyManager.getProxySetter().getPassword()).getBytes()));
				}
				while (imagesIterator.hasNext()) {

					org.jsoup.nodes.Element currentImage = imagesIterator.next();
					String originalSrc = currentImage.attr("src");
					String[] splittedSrc = originalSrc.split(":");
					File fi = (File) lMap.get(splittedSrc[1]);
					// rimpiazzo il nome logico con quello del file temporaneo fisico generato in locale
					// l'immagine è tra quelle salvate in auriga
					if (fi != null) {
						currentImage.attr("src", fi.getAbsolutePath());
					} else {
						// l'immagine è remota, la scarico in locale e modifico l'url
						String donwloadRemoteImage = null;
						if (downloadedRemoteImages.containsKey(originalSrc)) {
							// l'immagine è già stata scaricata quindi la recupero dalla cache locale
							donwloadRemoteImage = downloadedRemoteImages.get(originalSrc);
						} else {
							// scarico l'immagine in locale e la salvo nella cache locale
							donwloadRemoteImage = donwloadRemoteImage(originalSrc, proxy, proxyManager, encodedUserPwd);
							downloadedRemoteImages.put(originalSrc, donwloadRemoteImage);
						}
						// se sono riuscito a scaricare l'immagine remota allora la utilizzo, altrimenti metto src vuoto
						if (donwloadRemoteImage != null) {
							currentImage.attr("src", donwloadRemoteImage);
						} else {
							currentImage.attr("src", "");
						}
					}
				}

				final File tempFileHtml = File.createTempFile("Cleaner", ".html");
				tempFileHtml.deleteOnExit();
				fileWriter = new BufferedWriter(new FileWriter(tempFileHtml));
				fileWriter.write(emailBody.outerHtml());
				fisHTML = new FileInputStream(tempFileHtml);
			}
		} catch (Exception e) {
			logger.error("Errore download " + e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fileWriter);
			IOUtils.closeQuietly(fisHTML);
		}
		return fisHTML;
	}

	/**
	 * Scarica l'immagine remota in locale
	 * 
	 * @param imgRemoteUrlString
	 *            absolute path dell'immagine locale
	 * @param proxy
	 * @param proxyManager
	 * @param encodedUserPwd
	 *            username e password codificate tramite Base64, da utilizzare per l'autenticazione al server
	 * @return
	 */
	protected String donwloadRemoteImage(String imgRemoteUrlString, Proxy proxy, ProxyManager proxyManager, String encodedUserPwd) {

		String retValue = null;
		BufferedImage remoteImg = null;
		try {
			URL imgRemoteUrl = new URL(imgRemoteUrlString);
			URLConnection urlConnection = null;
			if (proxy != null) {
				urlConnection = imgRemoteUrl.openConnection(proxy);
				urlConnection.setConnectTimeout(3000);
				urlConnection.setReadTimeout(300);
				urlConnection.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			} else {
				urlConnection = imgRemoteUrl.openConnection();
			}
			// devo definire uno user agent qualsiasi altrimenti il sito remoto mi impedisce di scaricare l'immagine
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			if (urlConnection.getInputStream() != null) {
				remoteImg = ImageIO.read(urlConnection.getInputStream());
			}
			String imgFormat = imgRemoteUrlString.substring(imgRemoteUrlString.lastIndexOf(".") + 1).toUpperCase();

			File tmpImage = File.createTempFile("tempImg", "." + imgFormat.toLowerCase());
			tmpImage.deleteOnExit();

			ImageIO.write(remoteImg, imgFormat, tmpImage);

			retValue = tmpImage.getPath();
		} catch (Exception e) {
			StringBuilder builder = new StringBuilder("Errore durante il recupero dell'immagine remota ");
			builder.append(imgRemoteUrlString).append(". Se le comunicazioni verso l'esterno sono gestite da un proxy configurare il relativo bean");
			logger.warn(builder.toString(), e);
		}
		return retValue;
	}

	/**
	 * Converte eventuali caratteri speciali o codici html che non vengono poi stampati correttamente da ITextPdf.<br/>
	 * <ul>
	 * <li>&#8217; -> &#39;</li>
	 * </ul>
	 * 
	 * @param html
	 */
	protected void convertSpecialCharacters(String html) {

		if (html != null && html.contains("&#8217;")) {
			html = html.replace("&#8217;", "&#39;");
		}
	}

	private void createPDF(OutputStream baos, EmailDetailToExtractBean bean, FileInputStream fis) throws DocumentException, IOException {

		Document document = null;

		try {
			String tipo = bean.getTipo();
			String sottoTipo = bean.getSottoTipo();

			String casellaAccount = bean.getCasellaAccount();

			boolean casellaIsPEC = "true".equals(bean.getCasellaIsPEC()) ? true : false;

			String accountMittente = bean.getAccountMittente();
			String subject = bean.getSubject();
			String body = bean.getBody();

			String tsInvio = bean.getTsInvio();
			String tsRicezione = bean.getTsRicezione();

			String assegnatario = bean.getDesUOAssegnataria();

			String[] allegati = bean.getFiles();

			document = new Document(PageSize.A4, 50, 50, 50, 50);

			PdfWriter writer = PdfWriter.getInstance(document, baos);

			TableHeader event = new TableHeader();
			event.setFooter(bean.getProgressivo() + " - " + getDataStampa());
			writer.setPageEvent(event);

			document.open();

			addParagrafo(document, "Estremi");

			document.add(new Paragraph(" "));

			PdfPTable tableDG = new PdfPTable(2);
			tableDG.setWidths(new int[] { 2, 6 });
			tableDG.setWidthPercentage(100);

			addRow(tableDG, "N°", isNotNull(bean.getProgressivo()));
			addRow(tableDG, "  ", "  ");
			addRow(tableDG, "Tipo email", isNotNull(tipo) + "- " + isNotNull(sottoTipo));
			addRow(tableDG, "Email PEC", casellaIsPEC ? "SI" : "NO");

			addRow(tableDG, "Data invio", isNotNull(tsInvio));
			addRow(tableDG, "Data di registrazione", isNotNull(tsRicezione));
			addRow(tableDG, "Casella scarico", isNotNull(casellaAccount));

			if ("O".equals(bean.getFlgIO())) {
				addRow(tableDG, "Stato Invio", isNotNull(bean.getStatoInvio()));
				addRow(tableDG, "Stato Consegna", isNotNull(bean.getStatoConsegna()));
				addRow(tableDG, "Stato Accettazione", isNotNull(bean.getStatoAccettazione()));
			}

			document.add(tableDG);

			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			addParagrafo(document, "Lavorazione");

			PdfPTable tableLav = new PdfPTable(6);
			tableLav.setWidths(new int[] { 2, 3, 2, 2, 1, 1 });
			tableLav.setWidthPercentage(100);

			addRowColum4(tableLav, "Stato Lavorazione", "a partire dal", "alle", isNotNull(bean.getStatoLavorazione()),
					isNotNull(bean.getDataAggStatoLavorazione()), bean.getOrarioAggStatoLavorazione());
			addRowColum4(tableLav, "U.O competente", "dal", "alle", isNotNull(assegnatario), isNotNull(bean.getDataUltimaAssegnazione()),
					isNotNull(bean.getOrarioUltimaAssegnazione()));
			addRowColum4(tableLav, "In carico a", "dal", "alle", isNotNull(bean.getDesUtenteLock()), isNotNull(bean.getDataLock()),
					isNotNull(bean.getOrarioLock()));

			addRowColum4(tableLav, "Azioni da fare", "", "", isNotNull(bean.getCodAzioneDaFare()), "", "");
			addRowColum4(tableLav, "Dettagli azione ", "", "", isNotNull(bean.getDescrizioneAzioneDaFare()), "", "");

			document.add(tableLav);

			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			addParagrafo(document, "Contenuti");

			document.add(new Paragraph(" "));
			PdfPTable tableCorpo = new PdfPTable(2);
			tableCorpo.setWidths(new int[] { 2, 6 });
			tableCorpo.setWidthPercentage(100);
			addRow(tableCorpo, "Mittente", isNotNull(accountMittente));
			addRow(tableCorpo, "Destinatari", isNotNull(bean.getListaDestinatariPrincipali()));
			addRow(tableCorpo, " ", isNotNull(bean.getDestinatariCC()));
			addRow(tableCorpo, "  ", "  ");

			addRow(tableCorpo, "Oggetto", isNotNull(subject));
			addRow(tableCorpo, "  ", "  ");
			addRow(tableCorpo, "Testo del Messaggio", " ");
			addRow(tableCorpo, " ", " ");
			document.add(tableCorpo);

			addBodyToPdfDocument(body, document, fis, writer);

			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			addParagrafo(document, "Allegati");

			document.add(new Paragraph(" "));

			PdfPTable tableAllegati = new PdfPTable(1);

			for (int x = 0; x < allegati.length; x++) {
				addRow(tableAllegati, isNotNull(allegati[x]));
			}

			document.add(tableAllegati);

		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	public String getDataStampa() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String date = sdf.format(new Date());
		return date;
	}

	private void createPDFSolotesto(OutputStream baos, EmailDetailToExtractBean bean, FileInputStream fis) throws DocumentException, IOException {
		Document document = null;
		try {
			String body = bean.getBody();

			document = new Document(PageSize.A4, 50, 50, 50, 50);

			PdfWriter writer = PdfWriter.getInstance(document, baos);

			TableHeader event = new TableHeader();
			event.setFooter(bean.getProgressivo());
			writer.setPageEvent(event);
			document.open();

			PdfPTable tableCorpo = new PdfPTable(1);

			document.add(tableCorpo);

			addBodyToPdfDocument(body, document, fis, writer);
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * Aggiunge al documento pdf passato come parametro un paragrafo contenente il body della mail
	 * 
	 * @param body
	 *            body della mail in formato testo
	 * @param document
	 *            documento pdf a cui aggiungere il body
	 * @param fis
	 *            stream legato alla versione html, se esiste, del body della mail
	 * @param writer
	 *            scrive il body nel documento pdf
	 * @throws UnsupportedEncodingException
	 * @throws DocumentException
	 */
	protected void addBodyToPdfDocument(String body, Document document, FileInputStream fis, PdfWriter writer)
			throws UnsupportedEncodingException, DocumentException {

		if (body != null) {
			byte ptext[] = body.getBytes();
			body = new String(ptext, "UTF-8");
		}
		// il body è in formato html
		if (fis != null) {
			try {
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, fis, Charset.forName("UTF-8"));
			} catch (Exception e) {
				logger.warn("Si è verificato il seguente errore durante la generazione del pdf, per la stampa di utilizzerà il body senza formattazione", e);
				document.add(new Paragraph(isNotNull(body)));
			}
		} else {
			// se in formato testo verifico che gli a capo siano corretti sia per gli ambienti windows che per gli ambienti linux, quindi anche mac
			// se si tratta di un \n, allora aggiungo anche \r, affinchè si veda correttamente sotto windows. Sotto linux,
			// essendo un carattere non stampabile non dovrebbe dare problemi
			if (body != null && body.contains("\n")) {
				body.replace("\r\n", "\n");
				body.replace("\n", "\r\n");
			}
			document.add(new Paragraph(isNotNull(body)));
		}
	}

	private void addParagrafo(Document document, String label) throws DocumentException {
		PdfPTable tableEmail = new PdfPTable(1);
		tableEmail.setWidthPercentage(100);
		tableEmail.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tableEmail.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
		tableEmail.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		addCellWhite(tableEmail, label);

		document.add(tableEmail);
	}

	private void addCellWhite(PdfPTable tableEmail, String label) {
		Paragraph p = new Paragraph(label, titleFont);

		PdfPCell cellE = new PdfPCell(p);
		cellE.setBorder(Rectangle.BOTTOM);
		cellE.setBackgroundColor(new BaseColor(203, 200, 202));
		cellE.setBorderWidthBottom(0.3f);
		cellE.setFixedHeight(28f);
		cellE.setVerticalAlignment(Element.ALIGN_MIDDLE);
		tableEmail.addCell(cellE);
	}

	private void addRow(PdfPTable tableEmail, String label, String value) {
		tableEmail.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellLabel = new PdfPCell(new Phrase(label + "", boldFont));
		cellLabel.setBorder(Rectangle.NO_BORDER);

		cellLabel.setVerticalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, curierFont));
		cellValue.setBorder(Rectangle.NO_BORDER);

		tableEmail.addCell(cellLabel);
		tableEmail.addCell(cellValue);
	}

	private void addRowColum4(PdfPTable tableEmail, String label, String label2, String label3, String value, String value2, String value3) {
		tableEmail.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellLabel = new PdfPCell(new Phrase(label + "", boldFont));
		cellLabel.setBorder(Rectangle.NO_BORDER);
		cellLabel.setVerticalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, curierFont));
		cellValue.setBorder(Rectangle.NO_BORDER);

		PdfPCell cellLabel2 = new PdfPCell(new Phrase(label2 + "", boldFont));
		cellLabel2.setBorder(Rectangle.NO_BORDER);

		cellLabel2.setVerticalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellValue2 = new PdfPCell(new Phrase(value2, curierFont));
		cellValue2.setBorder(Rectangle.NO_BORDER);

		PdfPCell cellLabel3 = new PdfPCell(new Phrase(label3 + "", boldFont));
		cellLabel3.setBorder(Rectangle.NO_BORDER);
		cellLabel3.setVerticalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellValue3 = new PdfPCell(new Phrase(value3, curierFont));
		cellValue3.setBorder(Rectangle.NO_BORDER);

		tableEmail.addCell(cellLabel);
		tableEmail.addCell(cellValue);
		tableEmail.addCell(cellLabel2);
		tableEmail.addCell(cellValue2);
		tableEmail.addCell(cellLabel3);
		tableEmail.addCell(cellValue3);
	}

	private void addRow(PdfPTable tableEmail, String value) {
		tableEmail.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, curierFont));
		cellValue.setBorder(Rectangle.NO_BORDER);

		tableEmail.addCell(cellValue);
	}

	private String isNotNull(String s) {
		return s != null ? s : "";
	}

	private String isNotNullStati(String s) {
		return s != null ? s : " - ";
	}

	/**
	 * Inner class to add a table as header.
	 */
	class TableHeader extends PdfPageEventHelper {

		/** The header text. */
		String header;
		/** The template with the total number of pages. */
		PdfTemplate total;

		String footer;

		/**
		 * Allows us to change the content of the header.
		 * 
		 * @param header
		 *            The new header String
		 */
		public void setHeader(String header) {
			this.header = header;
		}

		public void setFooter(String progressivo) {

			this.footer = progressivo;
		}

		public String getFooter() {
			return footer;
		}

		/**
		 * Creates the PdfTemplate that will hold the total number of pages.
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(10, 16);
		}

		/**
		 * Adds a header to every page
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {
			PdfPTable table = new PdfPTable(3);
			try {
				table.setWidths(new int[] { 24, 24, 2 });
				table.setTotalWidth(527);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(10);
				table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table.addCell(getFooter());
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(String.format("Page %d of", writer.getPageNumber()));
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);

				table.writeSelectedRows(0, -1, 34, 30, writer.getDirectContent());
			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}

		/**
		 * Fills out the total number of pages before the document is closed.
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
		}
	}
}
