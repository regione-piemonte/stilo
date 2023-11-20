/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.select.Elements;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailAllegatoBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailDestinatarioBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DownloadEmlFileBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ListaDettaglioEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaRetriewAttachBean;
import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsInfoBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.client.AurigaMailService;
import it.eng.client.MailProcessorService;
import it.eng.config.AurigaMailBusinessClientConfig;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.formati.FormatiUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFormDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Datasource(id = "AurigaLoadDettaglioEmailDataSource")
public class AurigaLoadDettaglioEmailDataSource extends AbstractFormDataSource<DettaglioEmailBean> {

	private static Logger mLogger = Logger.getLogger(AurigaLoadDettaglioEmailDataSource.class);

	@Override
	public PaginatorBean<DettaglioEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		PaginatorBean<DettaglioEmailBean> result = new PaginatorBean<DettaglioEmailBean>();
		result.setStartRow(0);
		result.setEndRow(1);
		List<DettaglioEmailBean> lista = new ArrayList<DettaglioEmailBean>();
		result.setData(lista);
		result.setTotalRows(1);
		return result;
	}

	@Override
	public DettaglioEmailBean get(DettaglioEmailBean bean) throws Exception {

		MailProcessorService lMailProcessorService = new MailProcessorService();

		String lStringInoltro = getExtraparams() != null ? getExtraparams().get("Inoltro") : "";
		TFilterFetch<TDestinatariEmailMgoBean> filter = new TFilterFetch<TDestinatariEmailMgoBean>();
		TDestinatariEmailMgoBean lTDestinatariEmailMgoBean = new TDestinatariEmailMgoBean();
		lTDestinatariEmailMgoBean.setIdEmail(bean.getIdEmail());
		filter.setFilter(lTDestinatariEmailMgoBean);
		List<DettaglioEmailDestinatarioBean> lList = new ArrayList<DettaglioEmailDestinatarioBean>();
		List<DettaglioEmailDestinatarioBean> lListCC = new ArrayList<DettaglioEmailDestinatarioBean>();
		List<DettaglioEmailDestinatarioBean> lListCCn = new ArrayList<DettaglioEmailDestinatarioBean>();
		TPagingList<TDestinatariEmailMgoBean> result = AurigaMailService.getDaoTDestinatariEmailMgo().search(getLocale(), filter);
		if (result.getData() != null && !result.getData().isEmpty()) {
			for (TDestinatariEmailMgoBean lTDestinatariEmailMgoBeanRes : result.getData()) {
				mLogger.debug("Stato consolidamento " + lTDestinatariEmailMgoBeanRes.getStatoConsolidamento());
				mLogger.debug("Tipo destinatario " + lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario());
				DettaglioEmailDestinatarioBean lDestinatarioBean = new DettaglioEmailDestinatarioBean();
				lDestinatarioBean.setIdDestinatario(lTDestinatariEmailMgoBeanRes.getIdDestinatarioEmail());
				lDestinatarioBean.setStatoConsolidamento(lTDestinatariEmailMgoBeanRes.getStatoConsolidamento());
				lDestinatarioBean.setAccount(lTDestinatariEmailMgoBeanRes.getAccountDestinatario());
				if (lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario().equals("P")) {
					lList.add(lDestinatarioBean);
				} else if (lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario().equals("CC")) {
					lListCC.add(lDestinatarioBean);
				} else if (lTDestinatariEmailMgoBeanRes.getFlgTipoDestinatario().equals("CCN")) {
					lListCCn.add(lDestinatarioBean);
				}
			}
		}
		if (!lListCCn.isEmpty()) {
			bean.setDestinatariCCn(lListCCn);
		}
		if (!lListCC.isEmpty()) {
			bean.setDestinatariCC(lListCC);
		}
		bean.setDestinatariPrincipali(lList);

		TEmailMgoBean lTEmailMgo = AurigaMailService.getDaoTEmailMgo().get(getLocale(), bean.getIdEmail());
		mLogger.debug("Stato consolidamento globale " + lTEmailMgo.getStatoConsolidamento());
		bean.setFlgIo(lTEmailMgo.getFlgIo());
		bean.setStatoConsolidamento(lTEmailMgo.getStatoConsolidamento());
		bean.setInviataRispostaPresente(lTEmailMgo.getFlgInviataRisposta() + "");
		bean.setInoltrataPresente(lTEmailMgo.getFlgInoltrata() + "");
		bean.setNotificaEccezionePresente(lTEmailMgo.getFlgNotifInteropEcc() + "");
		bean.setNotificaAggiornamentoPresente(lTEmailMgo.getFlgNotifInteropAgg() + "");
		bean.setNotificaConfermaPresente(lTEmailMgo.getFlgNotifInteropConf() + "");
		bean.setNotificaAnnullamentoPresente(lTEmailMgo.getFlgNotifInteropAnn() + "");
		bean.setAvvertimenti(lTEmailMgo.getAvvertimenti());

		List<DettaglioEmailAllegatoBean> lListAllegati = new ArrayList<DettaglioEmailAllegatoBean>();
		String uriEmail = lTEmailMgo.getUriEmail();
		bean.setUriEmail(uriEmail);
		TFilterFetch<TAttachEmailMgoBean> filterAttach = new TFilterFetch<TAttachEmailMgoBean>();
		TAttachEmailMgoBean lTAttachEmailMgoBean = new TAttachEmailMgoBean();
		lTAttachEmailMgoBean.setIdEmail(bean.getIdEmail());
		filterAttach.setFilter(lTAttachEmailMgoBean);
		TPagingList<TAttachEmailMgoBean> resultAttach = AurigaMailService.getDaoTAttachEmailMgo().search(getLocale(), filterAttach);
		if (resultAttach.getData() != null && !resultAttach.getData().isEmpty()) {
			int numeroProgrAllegato = 0;
			for (TAttachEmailMgoBean lTAttach : resultAttach.getData()) {
				if (lTAttach.getIdEmail().equals(bean.getIdEmail())) {
					numeroProgrAllegato += 1;
					DettaglioEmailAllegatoBean lAllegatoProtocolloBean = new DettaglioEmailAllegatoBean();
					lAllegatoProtocolloBean.setNomeFileAllegato(lTAttach.getNomeOriginale());
					lAllegatoProtocolloBean.setDisplayFileNameAllegato(lTAttach.getDisplayFilename());
					lAllegatoProtocolloBean.setNumeroProgAllegato(numeroProgrAllegato + "");
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					lMimeTypeFirmaBean.setBytes(lTAttach.getDimensione());
					lMimeTypeFirmaBean.setFirmato(lTAttach.getFlgFirmato());
					lMimeTypeFirmaBean.setFirmaValida(lTAttach.isFlgFirmaValida());
					if (lMimeTypeFirmaBean.isFirmato()) {
						lMimeTypeFirmaBean.setTipoFirma(
								lTAttach.getNomeOriginale().toUpperCase().endsWith("P7M") || lTAttach.getNomeOriginale().toUpperCase().endsWith("TSD")
										? "CAdES_BES" : "PDF");
					}
					lMimeTypeFirmaBean.setCorrectFileName(lTAttach.getNomeOriginale());
					lMimeTypeFirmaBean.setMimetype(lTAttach.getMimetype());
					lMimeTypeFirmaBean.setConvertibile(FormatiUtil.isConvertibile(getSession(), lTAttach.getMimetype()));
					lAllegatoProtocolloBean.setUriFileAllegato("_noUri");
					lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
					if (StringUtils.isNotBlank(lTAttach.getIdRegProtEmail())) {
						TFilterFetch<TRegProtVsEmailBean> lTRegProtVsEmailFilterFetch = new TFilterFetch<TRegProtVsEmailBean>();
						TRegProtVsEmailBean lTRegProtVsEmailFilter = new TRegProtVsEmailBean();
						lTRegProtVsEmailFilter.setIdRegProtEmail(lTAttach.getIdRegProtEmail());
						lTRegProtVsEmailFilterFetch.setFilter(lTRegProtVsEmailFilter);
						TPagingList<TRegProtVsEmailBean> resultRegProtVsEmail = AurigaMailService.getDaoTRegProtVsEmail().search(getLocale(),
								lTRegProtVsEmailFilterFetch);
						if (resultRegProtVsEmail.getData() != null) {
							for (TRegProtVsEmailBean lTRegProtVsEmailBean : resultRegProtVsEmail.getData()) {
								if (lTRegProtVsEmailBean.getIdRegProtEmail().equals(lTAttach.getIdRegProtEmail())) {
									lAllegatoProtocolloBean.setIdProvReg(lTRegProtVsEmailBean.getIdProvReg());
									String estremi = "";
									if (lTRegProtVsEmailBean.getSiglaRegistro() != null && !"".equals(lTRegProtVsEmailBean.getSiglaRegistro())) {
										estremi += lTRegProtVsEmailBean.getSiglaRegistro() + " ";
									} else {
										estremi += "Prot. ";
									}
									if (lTRegProtVsEmailBean.getNumReg() != null && !"".equals(lTRegProtVsEmailBean.getNumReg())) {
										estremi += lTRegProtVsEmailBean.getNumReg() + " ";
									}
									if (lTRegProtVsEmailBean.getTsReg() != null) {
										estremi += "del " + new SimpleDateFormat(FMT_STD_TIMESTAMP).format(lTRegProtVsEmailBean.getTsReg());
									}
									lAllegatoProtocolloBean.setEstremiReg(estremi);
								}
							}
						}
					}
					lListAllegati.add(lAllegatoProtocolloBean);
				}
			}
		}
		bean.setAllegati(lListAllegati);
		bean.setTsInvio(lTEmailMgo.getTsInvio() != null ? lTEmailMgo.getTsInvio() : null);
		bean.setDateRif(lTEmailMgo.getTsInvio() != null ? lTEmailMgo.getTsInvio() : null);
		bean.setAccountMittente(lTEmailMgo.getAccountMittente());
		bean.setReplyTo(lTEmailMgo.getReplyTo());

		String nuovoOggetto = lTEmailMgo.getOggetto();

		SimpleDateFormat dtGiorni = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dtOre = new SimpleDateFormat("hh:mm");
		if (nuovoOggetto == null) {
			if (lTEmailMgo.getTsInvio() != null) {
				nuovoOggetto = "E-mail inviata il " + dtGiorni.format(lTEmailMgo.getTsInvio()) + " alle " + dtOre.format(lTEmailMgo.getTsInvio()) + " da "
						+ lTEmailMgo.getAccountMittente();
			}
		}
		bean.setOggetto(nuovoOggetto);

		bean.setCorpo(lTEmailMgo.getCorpo());
		bean.setCorpoHtml(bean.getCorpo());

		EmailInfoBean lEmailInfoBean = lMailProcessorService.getbodyhtmlbyidemail(getLocale(), lTEmailMgo.getIdEmail());

		String html = lEmailInfoBean.getMessaggio();

		if (html != null && !html.isEmpty()) {

			final Map<String, String> lMapImageBase64 = new HashMap<String, String>();
			final Map<String, String> lMapMimetype = new HashMap<String, String>();

			try {
				EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(getLocale(), lTEmailMgo.getIdEmail());
				int count = 0;

				if (lEmailAttachsBean.getMailAttachments() != null) {
					for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {
						if (StringUtils.contains(info.getMimetype(), "image")
								&& (info.getDisposition() == null || !"attachment".equals(info.getDisposition()))) {
							String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));

							InputStream in = (FileInputStream) StorageImplementation.getStorage().extract(uriAttach);

							String extension = FilenameUtils.getExtension(info.getFilename());

							final File tempFile = File.createTempFile("attachImg", "." + extension);
							tempFile.deleteOnExit();

							FileOutputStream fos = new FileOutputStream(tempFile);

							try {
								IOUtils.copy(in, fos);
							} finally {
								try {
									in.close();
								} catch (Exception ex) {
								}
							}
							String cid = info.getContentID();

							if (cid != null && cid.startsWith("<") && cid.endsWith(">")) {
								cid = cid.substring(1, cid.length() - 1);
								lMapImageBase64.put(cid, encodeToBase64String(tempFile));
								String[] infoMimetype = info.getMimetype().split(";");
								lMapMimetype.put(cid, infoMimetype[0]);
							}
						}
						count++;
					}
				}
			} catch (Exception e) {
				mLogger.warn("Eccezione recupero allegati", e);
			}
			if (html.contains("&nbsp;")) {
				html = html.replace("&nbsp;", " ");
			}
			org.jsoup.nodes.Document emailBody = Jsoup.parse(html);
			// normalizza la sintassi del documento rispettando le direttive xml, la libreria di generazione del pdf si aspetta html valido
			emailBody.outputSettings().syntax(Syntax.xml);
			try {
				// estraggo tutte le immagini, per rimpiazzare l'attributo src originale con il path relativo al server in cui si trova la mail
				Elements images = emailBody.select("img");
				Iterator<org.jsoup.nodes.Element> imagesIterator = images.iterator();

				while (imagesIterator.hasNext()) {

					org.jsoup.nodes.Element currentImage = imagesIterator.next();

					String originalSrc = currentImage.attr("src");

					String[] splittedSrc = originalSrc.split(":");

					String mimetype = lMapMimetype.get(splittedSrc[1]);
					if (StringUtils.isBlank(mimetype)) {
						mimetype = "unknown";
					}
					String imageBase64 = lMapImageBase64.get(splittedSrc[1]);
					if (StringUtils.isNotBlank(mimetype) && StringUtils.isNotBlank(imageBase64)) {
						currentImage.attr("src", "data:" + mimetype + ";base64," + imageBase64);
					}
				}
			} catch (Exception e) {
				// rimuovo le immagini
				emailBody.select("img").remove();
			}
			bean.setCorpoHtml(emailBody.outerHtml());
		}

		// fine

		if (lStringInoltro != null && lStringInoltro.equals("true")) {
			retrieveAttach(bean);
		}

		return bean;
	}

	public DettaglioEmailBean preparaFirmaEmailHtml(DettaglioEmailBean bean) {

		String firmaEmailHtml = bean.getFirmaEmailHtml();

		if (firmaEmailHtml != null && !firmaEmailHtml.isEmpty()) {

			if (firmaEmailHtml.contains("&nbsp;")) {
				bean.setFirmaEmailHtml(firmaEmailHtml.replace("&nbsp;", " "));
			}
		}
		return bean;
	}

	public DettaglioEmailBean caricaLogoFirmaEmailHtml(DettaglioEmailBean bean) {

		String firmaEmailHtml = bean.getFirmaEmailHtml();
		if (firmaEmailHtml != null && !firmaEmailHtml.isEmpty()) {
			org.jsoup.nodes.Document document = Jsoup.parse(firmaEmailHtml);
			try {
				// estraggo tutte le immagini, per rimpiazzare l'attributo src originale con il path relativo al server in cui si trova la mail
				Elements images = document.select("img");
				Iterator<org.jsoup.nodes.Element> imagesIterator = images.iterator();

				while (imagesIterator.hasNext()) {

					org.jsoup.nodes.Element currentImage = imagesIterator.next();

					String originalSrc = currentImage.attr("src");

					if (originalSrc != null && !originalSrc.toLowerCase().startsWith("data:")) {
						String[] splittedSrc = originalSrc.split(":");
						String displayFileName = splittedSrc[0];
						String uri = splittedSrc[1];

						InfoFileUtility lInfoFileUtility = new InfoFileUtility();
						MimeTypeFirmaBean infoFile = lInfoFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(uri).toURI().toString(),
								displayFileName, false, null);

						InputStream in = (FileInputStream) StorageImplementation.getStorage().extract(uri);
						String extension = FilenameUtils.getExtension(infoFile.getCorrectFileName());

						final File tempFile = File.createTempFile("attachImg", "." + extension);
						tempFile.deleteOnExit();

						String mimetype = infoFile.getMimetype();
						if (StringUtils.isBlank(mimetype)) {
							mimetype = "unknown";
						}

						FileOutputStream fos = new FileOutputStream(tempFile);

						try {
							IOUtils.copy(in, fos);
						} catch (Exception e) {
						} finally {
							try {
								in.close();
							} catch (Exception ex) {
							}
						}

						String imageBase64 = encodeToBase64String(tempFile);
						if (StringUtils.isNotBlank(mimetype) && StringUtils.isNotBlank(imageBase64)) {
							currentImage.attr("src", "data:" + mimetype + ";base64," + imageBase64);
						}
					}
				}
			} catch (Exception e) {
				addMessage("Si è verificato un errore durante il caricamento dell'immagine", "", MessageType.ERROR);
				// rimuovo le immagini
				document.select("img").remove();
			}

			bean.setFirmaEmailHtml(document.outerHtml());
		}

		return bean;
	}

	public String encodeToBase64String(File file) throws Exception {

		BufferedImage image = ImageIO.read(file);

		String extension = FilenameUtils.getExtension(file.getName());

		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, extension, bos);
			byte[] imageBytes = bos.toByteArray();
			imageString = Base64.encodeBase64String(imageBytes);
			bos.close();
		} catch (IOException e) {
			mLogger.warn(e);
		}

		return imageString;
	}

	@Override
	public DettaglioEmailBean update(DettaglioEmailBean bean, DettaglioEmailBean oldvalue) throws Exception {
		return null;
	}

	public DettaglioEmailBean retrieveAttach(DettaglioEmailBean bean) throws Exception {
		
		CalcolaImpronteService calcolaImpronteService = new CalcolaImpronteService();
		Map<String, String> mapFileUnivoci = new HashMap<String, String>();
		MailProcessorService lMailProcessorService = new MailProcessorService();
		EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(getLocale(), bean.getIdEmail());

		/**
		 * Viene costruita la mappa contenente le informazioni per gli allegati recuperati da MailProcessorService con valori { key = nome_file + dim_file -
		 * value = uri_file }
		 */
		int count = 0;
		if (lEmailAttachsBean.getMailAttachments() != null) {
			for (MailAttachmentsInfoBean info : lEmailAttachsBean.getMailAttachments()) {

				String uriAttach = StorageImplementation.getStorage().store(lEmailAttachsBean.getFiles().get(count));
				String fileName = Normalizer.normalize(info.getFilename(), Normalizer.Form.NFC);
				Long currentFileSize = info.getSize();

				String key = fileName.concat(";").concat(currentFileSize.toString());
				mapFileUnivoci.put(key, uriAttach);

				count++;
			}
		}

		/**
		 * Viene costruita una mappa ( mapFileUnivoci ) con chiave la coppia nome_file + dimensione_file e value l'uri del file Viene valorizzato il campo uri
		 * per ogni allegato presente nell'oggetto bean.getListaAllegati(), recuperato dalla mappa costruita in precedenza.
		 */
		if (bean.getAllegati() != null && bean.getAllegati().size() > 0) {
			for (DettaglioEmailAllegatoBean lDettaglioEmailAllegatoBean : bean.getAllegati()) {

				Long dimAllegato = lDettaglioEmailAllegatoBean.getInfoFile().getBytes();
				String size = dimAllegato.toString();
				String key = lDettaglioEmailAllegatoBean.getNomeFileAllegato().concat(";").concat(size);
				String impronta = lDettaglioEmailAllegatoBean.getInfoFile().getImpronta();
				String uriFileAllegato = mapFileUnivoci.get(key);

				/**
				 * La coppia NOME_FILE+DIMENSIONE FILE RECUPERATA DA MAIL_PROCESSOR_SERVICE coincide con quelli recuperati dalla store, quindi procedo a
				 * valorizzare l'uri dell'allegato
				 */
				if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
					lDettaglioEmailAllegatoBean.setUriFileAllegato(uriFileAllegato);
				} else {

					String algoritmoAttach = lDettaglioEmailAllegatoBean.getInfoFile().getAlgoritmo();
					String encodingAttach = lDettaglioEmailAllegatoBean.getInfoFile().getEncoding();
					if ((algoritmoAttach != null && !"".equals(algoritmoAttach)) && (encodingAttach != null && !"".equals(encodingAttach))) {

						Map<String, String> mapImprontaFile = recuperaUriAllegatoWithoutFOP(calcolaImpronteService, mapFileUnivoci,
								lDettaglioEmailAllegatoBean);
						String keyMapFileUnivoci = mapImprontaFile.get(impronta);
						if (mapFileUnivoci.get(keyMapFileUnivoci) != null && !"".equals(mapFileUnivoci.get(keyMapFileUnivoci))) {
							lDettaglioEmailAllegatoBean.setUriFileAllegato(mapFileUnivoci.get(keyMapFileUnivoci));
						}
					} else {
						Map<String, PostaElettronicaRetriewAttachBean> mapFiles = recuperaInfoAllegatoWithFOP(mapFileUnivoci);
						Iterator<String> ite = mapFiles.keySet().iterator();
						while (ite.hasNext()) {
							String keyMapFiles = ite.next();
							String sizeFileTemp[] = keyMapFiles.split(";");

							PostaElettronicaRetriewAttachBean uriFileAllegato2 = mapFiles.get(keyMapFiles);
							PostaElettronicaRetriewAttachBean valueMapFiles = mapFiles.get(keyMapFiles);
							String uriFileAllegatoTemp = valueMapFiles.getUriFile();
							Long sizeDettEmailAlleBean = lDettaglioEmailAllegatoBean.getInfoFile().getBytes();
							if (uriFileAllegato2.getUriFile().equals(uriFileAllegatoTemp) && sizeFileTemp[1].equals(sizeDettEmailAlleBean.toString())) {
								String uriFileTemp = valueMapFiles.getUriFile();
								lDettaglioEmailAllegatoBean.setUriFileAllegato(uriFileTemp);
							}
						}
					}
				}
			}
		} else {
			List<DettaglioEmailAllegatoBean> allegati = new ArrayList<DettaglioEmailAllegatoBean>();
			Map<String, PostaElettronicaRetriewAttachBean> mapFiles = recuperaInfoAllegatoWithFOP(mapFileUnivoci);
			Iterator<String> ite = mapFiles.keySet().iterator();
			while (ite.hasNext()) {
				String keyMapFiles = ite.next();
				PostaElettronicaRetriewAttachBean uriFileAllegato = mapFiles.get(keyMapFiles);
				PostaElettronicaRetriewAttachBean valueMapFiles = mapFiles.get(keyMapFiles);
				String uriFileAllegatoTemp = valueMapFiles.getUriFile();
				if (uriFileAllegato.getUriFile().equals(uriFileAllegatoTemp)) {
					DettaglioEmailAllegatoBean dettaglioEmailAllegatoBean = setValuesToAllegato(keyMapFiles, valueMapFiles);
					allegati.add(dettaglioEmailAllegatoBean);
				}
			}
			bean.setAllegati(allegati);
		}
		return bean;
	}

	private DettaglioEmailAllegatoBean setValuesToAllegato(String key, PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean) {

		DettaglioEmailAllegatoBean lDettaglioEmailAllegatoBean = new DettaglioEmailAllegatoBean();

		MimeTypeFirmaBean lMimeTypeFirmaBean = lPostaElettronicaRetriewAttachBean.getMimeTypeFirmaBean();
		String uriFileAllegato = lPostaElettronicaRetriewAttachBean.getUriFile();

		String[] fileName = key.split(";");// nome_file;dim_file
		lDettaglioEmailAllegatoBean.setNomeFileAllegato(fileName[0]);
		lDettaglioEmailAllegatoBean.setDisplayFileNameAllegato(lMimeTypeFirmaBean.getCorrectFileName());
		lDettaglioEmailAllegatoBean.setUriFileAllegato(uriFileAllegato);
		lDettaglioEmailAllegatoBean.setInfoFile(lMimeTypeFirmaBean);

		return lDettaglioEmailAllegatoBean;
	}

	private Map<String, String> recuperaUriAllegatoWithoutFOP(CalcolaImpronteService calcolaImpronteService, Map<String, String> mapFileUnivoci,
			DettaglioEmailAllegatoBean lDettaglioEmailAllegatoBean) throws StorageException, Exception {
		String algoritmoAttach = lDettaglioEmailAllegatoBean.getInfoFile().getAlgoritmo();
		String encodingAttach = lDettaglioEmailAllegatoBean.getInfoFile().getEncoding();

		Map<String, String> mapImprontaFile = new HashMap<String, String>();
		Iterator<String> ite = mapFileUnivoci.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String uriFileAllegato = mapFileUnivoci.get(key);
			if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
				// Viene risolto l'uri del file allegato tramite storage_util
				File fileTemp = StorageImplementation.getStorage().extractFile(uriFileAllegato);
				String improntaFile = calcolaImpronteService.calcolaImprontaWithoutFileOp(fileTemp, algoritmoAttach, encodingAttach);
				mapImprontaFile.put(improntaFile, key);
			}
		}
		return mapImprontaFile;
	}

	private Map<String, PostaElettronicaRetriewAttachBean> recuperaInfoAllegatoWithFOP(Map<String, String> mapFileUnivoci) throws Exception {

		Map<String, PostaElettronicaRetriewAttachBean> mapFiles = new HashMap<String, PostaElettronicaRetriewAttachBean>();

		Iterator<String> ite = mapFileUnivoci.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String uriFileAllegato = mapFileUnivoci.get(key);
			String nomeFile[] = key.split(";");
			File fileTemp = StorageImplementation.getStorage().extractFile(uriFileAllegato);

			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileTemp.toURI().toString(), nomeFile[0], false, null);
			if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
				throw new Exception("Si è verificato un errore durante il controllo del file allegato " + nomeFile[0]);
			}
			PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean = new PostaElettronicaRetriewAttachBean();
			lPostaElettronicaRetriewAttachBean.setMimeTypeFirmaBean(lMimeTypeFirmaBean);
			lPostaElettronicaRetriewAttachBean.setUriFile(uriFileAllegato);

			mapFiles.put(key, lPostaElettronicaRetriewAttachBean);
		}
		return mapFiles;
	}

	@Override
	public Map<String, ErrorBean> validate(DettaglioEmailBean bean) throws Exception {
		return null;
	}

	/**
	 * Per Inoltro Massivo di più email; fatto per email che vogliono in allegato l'email originale
	 * 
	 * @param listaDettaglioEmailBean
	 * @return
	 * @throws Exception
	 */
	public ListaDettaglioEmailBean retrieveMailMassivoDaInoltrare(ListaDettaglioEmailBean listaDettaglioEmailBean) throws Exception {

		List<DettaglioEmailBean> output = new ArrayList<DettaglioEmailBean>();

		List<DettaglioEmailBean> ld = listaDettaglioEmailBean.getListaDettagli();

		for (DettaglioEmailBean dettaglioEmailBean : ld) {
			dettaglioEmailBean.setAllegaEmlSbustato(listaDettaglioEmailBean.getEmlSbustato());
			dettaglioEmailBean = retrieveMailDaInoltrare(dettaglioEmailBean);

			output.add(dettaglioEmailBean);
		}

		listaDettaglioEmailBean.setListaDettagli(output);

		return listaDettaglioEmailBean;
	}
	
	public DettaglioEmailBean retrieveMailDaInoltrare(DettaglioEmailBean lDettaglioEmailBean) throws Exception {
		
		DettaglioEmailDestinatarioBean lDettaglioEmailDestinatarioBean = new DettaglioEmailDestinatarioBean();
		String account = lDettaglioEmailBean.getAccountMittente();
		lDettaglioEmailDestinatarioBean.setAccount(account);
		lDettaglioEmailBean.setDestinatariCC(Arrays.asList(new DettaglioEmailDestinatarioBean[] { lDettaglioEmailDestinatarioBean }));
		String nomeFileAllegato = lDettaglioEmailBean.getProgrDownloadStampa();


		if (nomeFileAllegato != null && !nomeFileAllegato.equalsIgnoreCase("")) {
			nomeFileAllegato = normalize(nomeFileAllegato);
		}
		if (nomeFileAllegato != null && nomeFileAllegato.length() > 20) {
			nomeFileAllegato = nomeFileAllegato.substring(0, nomeFileAllegato.length() - 1) + ".eml";
		} else {
			nomeFileAllegato = nomeFileAllegato + ".eml";
		}

		String uri = null;
		DownloadEmlFileBean downloadEmlFileBean = new DownloadEmlFileBean();
		downloadEmlFileBean.setIdEmail(lDettaglioEmailBean.getIdEmail());
		MailProcessorService lMailProcessorService = new MailProcessorService();
		if ("true".equals(lDettaglioEmailBean.getAllegaEmlSbustato())) {
			try {
				MailAttachmentsBean mab = lMailProcessorService.getpostacertbyidemail(getLocale(), lDettaglioEmailBean.getIdEmail());
				uri = StorageImplementation.getStorage().store(mab.getFile());
			} catch (Exception e) {
				// Non è email PEC
				if(lDettaglioEmailBean.getUriEmail() != null && !"".equals(lDettaglioEmailBean.getUriEmail())){
					uri = lDettaglioEmailBean.getUriEmail();
				} else {
					downloadEmlFileBean = getUriFromIdEmail(downloadEmlFileBean);
					uri = downloadEmlFileBean.getUriFile();
				}
			}
		} else {
			// Non è email PEC
			if(lDettaglioEmailBean.getUriEmail() != null && !"".equals(lDettaglioEmailBean.getUriEmail())){
				uri = lDettaglioEmailBean.getUriEmail();
			} else {
				downloadEmlFileBean = getUriFromIdEmail(downloadEmlFileBean);
				uri = downloadEmlFileBean.getUriFile();
			}
		}

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setBytes(StorageImplementation.getStorage().getRealFile(uri).length());
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setCorrectFileName(nomeFileAllegato);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setMimetype("message/rfc822");
		DettaglioEmailAllegatoBean lDettaglioEmailAllegatoBean = new DettaglioEmailAllegatoBean();
		lDettaglioEmailAllegatoBean.setInfoFile(lMimeTypeFirmaBean);
		lDettaglioEmailAllegatoBean.setNomeFileAllegato(nomeFileAllegato);
		lDettaglioEmailAllegatoBean.setUriFileAllegato(uri);
		List<DettaglioEmailAllegatoBean> lList = new ArrayList<DettaglioEmailAllegatoBean>();
		lList.add(lDettaglioEmailAllegatoBean);
		lDettaglioEmailBean.setAllegati(lList);

		return lDettaglioEmailBean;
	}

	public static String normalize(String fileName) {
		String newFileName = fileName;
		newFileName = newFileName.replace(" ", "_");
		newFileName = newFileName.replace("\\", "_");
		newFileName = newFileName.replace("/", "_");
		newFileName = newFileName.replace(":", "_");
		newFileName = newFileName.replace("*", "_");
		newFileName = newFileName.replace("?", "_");
		newFileName = newFileName.replace("<", "_");
		newFileName = newFileName.replace(">", "_");
		newFileName = newFileName.replace("|", "_");
		newFileName = newFileName.replace("\"", "_");
		return newFileName;
	}
	
	private TrustManager trustManager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateExpiredException, CertificateNotYetValidException {
			chain[0].checkValidity();
			chain[0].getIssuerUniqueID();
			chain[0].getSubjectDN();
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {

		}
	};
	
	public DownloadEmlFileBean getUriFromIdEmail(DownloadEmlFileBean input){
		
		AurigaMailBusinessClientConfig config = (AurigaMailBusinessClientConfig) SpringAppContext.getContext().getBean("MailConfigurator");
		DownloadEmlFileBean downloadEmlFileBean = new DownloadEmlFileBean();
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			DefaultClientConfig defaultClientConfig = new com.sun.jersey.api.client.config.DefaultClientConfig();
			defaultClientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));
			Client client = Client.create(defaultClientConfig);
			WebResource webResource = client.resource(config.getUrl().endsWith("/") ? config.getUrl()+"/rest/email/"+input.getIdEmail()+"/downloading"
						 : config.getUrl()+"/rest/email/"+input.getIdEmail()+"/downloading");
			ClientResponse response = webResource.accept("application/octet-stream").get(ClientResponse.class);
			File filerRespJson = response.getEntity(File.class);
			downloadEmlFileBean.setUriFile(StorageImplementation.getStorage().store(filerRespJson));
			
			return downloadEmlFileBean;
		} catch (Exception e) {
			addMessage(
					"Errore nella chiamata al WS "+config.getUrl()+"/swagger/#!/Interazione/downloadEmlFile"
							+ e.getMessage(), "", MessageType.ERROR);
			mLogger.warn(e);
		}
		return downloadEmlFileBean;
	}

}