/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.auriga.compiler.docx.CompositeCompiler;
import it.eng.auriga.compiler.docx.DocxCompiler;
import it.eng.auriga.compiler.docx.FormDocxCompiler;
import it.eng.auriga.compiler.odt.OdtCompiler;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpdverdocImpl;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCollegaregtoemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.store.Collegaregtoemail;
import it.eng.auriga.database.store.dmpk_int_portale_crm.bean.DmpkIntPortaleCrmRichiestaagibilitaBean;
import it.eng.auriga.database.store.dmpk_int_portale_crm.store.Richiestaagibilita;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.RicercaAgibilitaWSConfigBean;
import it.eng.document.function.AllegatoStoreBean;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.ValueBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.restrepresentation.ElencoAgibilita;
import it.eng.document.function.bean.restrepresentation.Errore;
import it.eng.document.function.bean.restrepresentation.FileAgibilitaOutBean;
import it.eng.document.function.bean.restrepresentation.FileAgibilitaXmlInBean;
import it.eng.document.function.bean.restrepresentation.RicercaAgibilitaStoreOutBean;
import it.eng.document.function.bean.restrepresentation.XmlRicercaAgibilitaOutBean;
import it.eng.document.function.bean.restrepresentation.input.RicercaAgibilitaRequest;
import it.eng.document.function.bean.restrepresentation.output.RicercaAgibilitaResponse;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.util.BarcodeUtility;
import it.eng.util.ImpostazioniBarcodeBean;
import it.eng.util.ModelliUtil;
import it.eng.utility.TimbraUtil;
import it.eng.utility.crypto.CryptoUtility;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

/**
 *  WS per la ricerca dei documenti di agibilita dell'urbanistica
 * 
 * @author Antonio Peluso
 * 
 */

@Singleton
@Api
@Path("/agibilita")
public class AurigaAgibilitaResource {

	@Context
	ServletContext context;

	private static final long DEFAULT_VALIDATE_HOURS = 24;
	private static final String DEFAULT_SPLIT_CHAR_EMAIL = ",";
	
	protected static RicercaAgibilitaWSConfigBean lRicercaAgibilitaWSConfigBean = null;
	private static AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
	private static final Logger logger = Logger.getLogger(AurigaAgibilitaResource.class);

	@POST
	@Path("/ricerca") 
	@Produces ({ MediaType.APPLICATION_XML})
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public RicercaAgibilitaResponse ricercaAgibilita(
			@FormDataParam("ricercaAgibilitaRequest") RicercaAgibilitaRequest ricercaAgibilitaRequest,
			@FormDataParam("file") List<FormDataBodyPart> files, 
			@Context HttpHeaders headers) throws Exception {

		RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean;

		RicercaAgibilitaResponse response = new RicercaAgibilitaResponse();
		logger.debug("---------- INIZIO SERVIZIO: RicercaAgibilita ----------");

		// logica e implementazione del servizio
		try {

			/* 1) --------------- RECUPERO I DATI DI CONFIGURAZIONI DEL SERVIZIO */

			lRicercaAgibilitaWSConfigBean = (RicercaAgibilitaWSConfigBean) SpringAppContext.getContext()
					.getBean("RicercaAgibilitaWSConfigBean");

			if (lRicercaAgibilitaWSConfigBean == null
					|| StringUtils.isBlank(lRicercaAgibilitaWSConfigBean.getDefaultSchema())
					
					/*|| StringUtils.isBlank(lRicercaAgibilitaWSConfigBean.getCodIdConnectionToken())*/) {
				logger.error(
						"Errore nella configurazione del servizio: Bean RicercaAgibilitaWSConfigBean non configurato " 
						
								/*- token: " + lRicercaAgibilitaWSConfigBean.getCodIdConnectionToken()*/ + " schema: "
								+ lRicercaAgibilitaWSConfigBean.getDefaultSchema());
				throw new Exception("Errore nella configurazione del servizio");
			}
			logger.debug("RicercaAgibilitaWSConfigBean recuperato (DefaultSchema: "
					+ lRicercaAgibilitaWSConfigBean.getDefaultSchema() +/* "token :"*
					
					+ lRicercaAgibilitaWSConfigBean.getCodIdConnectionToken() +*/ ")");
			lAurigaLoginBean.setSchema(lRicercaAgibilitaWSConfigBean.getDefaultSchema());
			
//			lAurigaLoginBean.setToken(lRicercaAgibilitaWSConfigBean.getCodIdConnectionToken());
			
			

			/* 2) --------------- CREO L'XML DA DARE IN INPUT ALLA STORE */

			/* Creo xml dei dati da dare in input alla store */
			String xmlInputBean = convertBeanToXml(ricercaAgibilitaRequest);

			/* Creo xml dei file da dare in input alla store */
			String xmlFileAgibilita = null;
			List<FileAgibilitaXmlInBean> listaFilesPortale = null;
			if (files != null) {
				String nomeFiles = files.size() + " file in input al servizio: ";
				for (FormDataBodyPart file : files) {
					String realName = file.getFormDataContentDisposition().getFileName() != null
							? file.getFormDataContentDisposition().getFileName()
							: "";
					nomeFiles = nomeFiles + realName + ", ";
				}
				logger.debug(nomeFiles);
				logger.debug("Salvo i file in input");

				// Salvo i file e li confeziono per convertirli in xml da passare alla store
				listaFilesPortale = salvaFilePortale(files);

				// poi li trasformo in lista
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				xmlFileAgibilita = lXmlUtilitySerializer.bindXmlList(listaFilesPortale);
			} else {
				logger.debug("File in input non presenti!");
			}

			/* 3) --------------- INVOCO LA STORE DI RICERCA DEI DOCUMENTI DELL'AGIBILITA */
			logger.debug("Effettuo chiamata alla store Richiestaagibilita");

			 lRicercaAgibilitaStoreOutBean = callStoreRicercaAgibilita(lRicercaAgibilitaWSConfigBean, xmlInputBean, xmlFileAgibilita);


			/* 4) --------------- INIZIALIZZO AURIGALOGINBEAN --------------------- */
			SpecializzazioneBean lSpecBean = new SpecializzazioneBean();
			lSpecBean.setIdDominio(lRicercaAgibilitaStoreOutBean.getIdDominio());
			lAurigaLoginBean.setSpecializzazioneBean(lSpecBean);
			lAurigaLoginBean.setToken(lRicercaAgibilitaStoreOutBean.getConnectionToken());

		} catch (Exception e) {

//			logger.error(e.getMessage(), e);
			throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_SERVICE + ": " + e.getMessage(),
					Status.INTERNAL_SERVER_ERROR);
		}

		/* 5) ------------ STORE IN ERRORE RITORNO RESPONSE */
		if (lRicercaAgibilitaStoreOutBean.getErrMsg() != null) {
			Errore errore = new Errore();
			response.setEsito("KO");
			errore.setCodice(lRicercaAgibilitaStoreOutBean.getErrorCode().toString());
			errore.setMessaggio(lRicercaAgibilitaStoreOutBean.getErrMsg());
			response.setErrore(errore);

			String responseString = convertBeanToXml(response);
			logger.error("Esito richiesta: KO - Response restituita: \n" + responseString);
		} else {

			/*
			 * 6) ------------ COSTRUZIONE DEL PATH DEL SERVIZIO CON I DOCUMENTI RESTITUITI DALLA STORE
			 */
			
			
			/* TIMBO I FILE DELL'AGIBILITA RITORNATI DALLA STORE, CON IL TIMBRO DI COPIA CONFORME AL CARTACEO*/
			logger.debug("Timbro i file di agbilita ritornati dalla store con il timbro di copia conforme al cartaceo");
			timbraFileAgibilita(lRicercaAgibilitaStoreOutBean);

			logger.debug("Creo path del servizio che scarica il file");
			String pathServiceFile = createPathForService(lRicercaAgibilitaStoreOutBean);

			/* 7) ------------ CONFEZIONAMENTO DELLA RISPOSTA DEL SERVIZIO */
			// !!!!! TODO: DA VERIFICARE IN CHE CASI LA STORE MI DA ERRORE, in caso togliere
			// if else, lasciare solo if
			if (lRicercaAgibilitaStoreOutBean.getEstremiProtRichiesta() != null
					&& lRicercaAgibilitaStoreOutBean.getListaCertificati() != null && pathServiceFile != null
					&& !"".equalsIgnoreCase(pathServiceFile)) {
				response.setEsito("OK");
				response.setProtocollo(lRicercaAgibilitaStoreOutBean.getEstremiProtRichiesta());

				ElencoAgibilita elencoAgibilita = new ElencoAgibilita();
				elencoAgibilita.setAgibilita(lRicercaAgibilitaStoreOutBean.getListaCertificati());

				response.setElencoAgibilita(elencoAgibilita);
				response.setPathServiceFile(pathServiceFile);

				String responseString = convertBeanToXml(response);
				logger.debug("Esito richiesta: OK - Response restituita: \n" + responseString);
			} else {
				Errore errore = new Errore();
				response.setEsito("KO");
				errore.setCodice(lRicercaAgibilitaStoreOutBean.getErrorCode().toString());
				errore.setMessaggio(lRicercaAgibilitaStoreOutBean.getErrMsg());
				response.setErrore(errore);

				String responseString = convertBeanToXml(response);
				logger.error("Esito richiesta: KO- Response restituita: \n" + responseString);

				return response;
			}

			/* 8) ------------ SALVO NEI TEMPORANEI I FILE RICEVUTI DAL PORTALE */
			/*
			 * List<FileAgibilitaOutBean> listaFilesPortale = null; if(files != null) {
			 * logger.debug("Salvo nei temporanei i file in input"); listaFilesPortale =
			 * creaTempFilePortale(files); }else {
			 * logger.debug("File in input non presenti!"); }
			 */

			Session session = null;
			File pdfDaModello = null;
			File pdfTimbrato = null;
			String uriPdfTimbratoVersionato = null;

			/**
			 * INIZIO TRANSAZIONE
			 */

			try {

				SubjectBean subject = new SubjectBean();
				subject.setIdDominio(lAurigaLoginBean.getSchema());
				SubjectUtil.subject.set(subject);

				session = HibernateUtil.begin();
				Transaction lTransaction = session.beginTransaction();

				/**ORA LA PROTOCOLLAZIONE LA FA LA STORE QUINDI I METODI SOTTO NON SERVONO MA LASCIO COMMENTATI*/
				/*
				 * 9) --- CARICO I FILE SALVATI NEI TEMPORANEI COME ALLEGATI DELL'UD CREATA
				 * DALLA STORE
				 */
				/*
				 * if (listaFilesPortale != null && !listaFilesPortale.isEmpty()) { logger.
				 * debug("Carico i file in input al servizio come allegati dell ud creata dala store"
				 * ); aggiungiAllegatiUD(lRicercaAgibilitaStoreOutBean, listaFilesPortale,
				 * session); logger.
				 * debug("Sono stati allegati i file in input all'UD con identificativo: " +
				 * lRicercaAgibilitaStoreOutBean.getIdUDRichiesta()); }
				 */

				/*
				 * 10) --- INIEZIONE NEL MODELLO DEI DATI RICEVUTI DALA STORE E CONVERSIONE PDF
				 */
				logger.debug("Innietto i dati nel modello");
				pdfDaModello = creaPdfDaModello(lRicercaAgibilitaStoreOutBean);
				logger.debug("Creato pdf: " + pdfDaModello.getName() + " con i dati inniettati nel modello");

				
				/*
				 * 11) --- TIMBRO IL FILE PDF CON I DATI DI CONFIGURAZIONE E QUELLI RITORNATI DALLA STORE
				 */
				try {
					logger.debug("Timbro il file generato da modello");
					pdfTimbrato = timbraFile(pdfDaModello, lRicercaAgibilitaStoreOutBean.getTestoInChiaroBarcode(), lRicercaAgibilitaStoreOutBean.getContenutoBarcode(),
							lRicercaAgibilitaWSConfigBean.getOpzioniTimbro());
					logger.debug("File timbrato");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				/** PREPARO IL FILE DA VERSIONARE */
				RebuildedFile lRebuildedFile = new RebuildedFile();
				lRebuildedFile.setIdDocumento(lRicercaAgibilitaStoreOutBean.getIdDocRisposta());

				// Se il file e stato timbrato correttamente lo versiono
				if (pdfTimbrato != null) {
					lRebuildedFile.setFile(pdfTimbrato);
					lRebuildedFile.setInfo(getFileInfoBean(pdfTimbrato));
				}
				// Se c'e stato un errore durante la timbratura versiono il file non timbrato
				else if (pdfDaModello != null) {
					lRebuildedFile.setFile(pdfDaModello);
					lRebuildedFile.setInfo(getFileInfoBean(pdfDaModello));
				}

				/* 12) --- VERSIONO IL FILE */
				logger.debug("Versiono il file");
				uriPdfTimbratoVersionato = versionaDocumento(getVersionaDocumentoInBean(lRebuildedFile), session);

				if (uriPdfTimbratoVersionato != null) {
					logger.debug("Il File e' stato versionato");
				} else {
					throw new Exception("Errore durante il versionamento del file timbrato: uriPdfTimbratoVersionato = null");
				}

				/**
				 * SE TUTTO E ANDATO A BUON FINE, COMMIT
				 */
				session.flush();
				lTransaction.commit();

			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
			} finally {

				/**
				 * CHIUSURA DELLA SESSIONE HIBERNATE : SE ERRORI, ROLLBACK
				 */
				HibernateUtil.release(session);
			}

			/*
			 * 13) --- INVIO LA MAIL AL RICHIEDENTE CON I DATI DELL'AGIBILITA E IL PDF GENERATO
			 */
			EmailSentReferenceBean referenceBeanMail = null;
			FileAgibilitaOutBean fileModello = new FileAgibilitaOutBean();
			try {
				// Creo la lista di file da allegare alla mail
				List<FileAgibilitaOutBean> listaAllegatiMail = new ArrayList<FileAgibilitaOutBean>();
				
				
				String nomeFileModello = StringUtils.isNotBlank(lRicercaAgibilitaWSConfigBean.getNomeModello()) ? lRicercaAgibilitaWSConfigBean.getNomeModello() : "Esito_richiesta_certificati_agibilità.pdf";

				// Se il file creato da modello e stato timbrato e versionato
				if (uriPdfTimbratoVersionato != null) {
					fileModello.setUri(uriPdfTimbratoVersionato);
					fileModello.setNomeFile(nomeFileModello);
					fileModello.setDimensione(new BigDecimal(pdfTimbrato.length()));
					fileModello.setMimetype("application\\pdf");

					listaAllegatiMail.add(fileModello);
				}
				// Se invece il file non e stato versionato
				else if (pdfTimbrato != null) {
					logger.error("C'e stato un errore nel versionamento del file lo allego comunque alla mail");
					String uriPdfTimbrato = DocumentStorage.store(pdfTimbrato, null);
					fileModello.setUri(uriPdfTimbrato);
					fileModello.setNomeFile(nomeFileModello);
					fileModello.setDimensione(new BigDecimal(pdfTimbrato.length()));
					fileModello.setMimetype("application\\pdf");

					listaAllegatiMail.add(fileModello);
				}
				// Se invece il file non e stato ne timbrato ne versionato
				else if (pdfDaModello != null) {
					logger.error("C'e stato un errore nel versionamento e nella timbratura del file lo allego comunque alla mail");
					String uriPdfDaModello = DocumentStorage.store(pdfDaModello, null);
					fileModello.setUri(uriPdfDaModello);
					fileModello.setNomeFile(nomeFileModello);
					fileModello.setDimensione(new BigDecimal(pdfDaModello.length()));
					fileModello.setMimetype("application\\pdf");

					listaAllegatiMail.add(fileModello);
				}
				// Se c'e stato un errore nela generazione del file da modello
				else {
					logger.error("C'e stato un errore durante la generazione del file, non lo allego alla mail");
				}

				if (lRicercaAgibilitaStoreOutBean.getFlgFileAllegatiMail()) {
					listaAllegatiMail.addAll(lRicercaAgibilitaStoreOutBean.getFilesAgibilita());
				}

				logger.debug("Invio mail");
				referenceBeanMail = invioMailModello(lRicercaAgibilitaStoreOutBean, listaAllegatiMail, nomeFileModello);
				logger.debug("Mail inviata a: " + lRicercaAgibilitaStoreOutBean.getDestinatariMail() +" da casella mail: " + lRicercaAgibilitaStoreOutBean.getAccountMittenteMail());

			} catch (Exception e) {
				logger.error("Errore durante l'invio della mail: " + e.getMessage(), e);
			}

			/* 14) --- ASSOCIO LE MAIL INVIATE ALL'ID_UD TORNATO DALLA STORE */
			try {
				logger.debug("Associo le mails all'ID_UD"); 
				if (referenceBeanMail.getIdEmails() != null) {
					for (String idMail : referenceBeanMail.getIdEmails()) {
						associaMailToIdud(idMail, lRicercaAgibilitaStoreOutBean.getIdUDRisposta());
						logger.debug("Mail con id:" + idMail + " associata all ID_UD: " + lRicercaAgibilitaStoreOutBean.getIdUDRisposta());
					}
				}

			} catch (Exception e) {
				logger.error("Errore durante l'associazione delle mail al id_ud: "
						+ lRicercaAgibilitaStoreOutBean.getIdUDRisposta() + "\n" + e.getMessage(), e);
			}

		}

		logger.debug("Fine servizio: RicercaAgibilita");

		return response;

	}

	
	/*
	 * 
	 *      METODO ORIGINALE DA ANDARE IN PRODUZIONE, QUELLO SOTTO E' UTILIZZATO PER DARE DATI CABLATI PER I TEST
	 * 
	 * 
	 * */
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/getFile")
	public Response getFile(@Context HttpServletRequest request) throws Exception {
		String filePath = request.getParameter("uriFile");
		String token = request.getParameter("token");
		String fileName = request.getParameter("fileName");
		
		StreamingOutput stream = null;
		
		logger.debug("----------- Inizio servizio: Scarico file Agibilita ---------");
		logger.debug("uriFile input: " + filePath + ", nome file input: " + fileName + ", token: " + token);
		
		/*
		if(!validateToken(token, filePath)) {
			return Response.status(Status.UNAUTHORIZED).entity("Accesso negato! Token non valido").type("text/plain").build();
		}
		*/
			
		if (filePath != null && !"".equals(filePath)) {
			try {
				File fileExtract = DocumentStorage.extract(filePath, null);
				
			
				final InputStream in = new FileInputStream(fileExtract);
				stream = new StreamingOutput() {
					public void write(OutputStream out) throws IOException, WebApplicationException {
						try {
							int read = 0;
							byte[] bytes = new byte[1024];

							while ((read = in.read(bytes)) != -1) {
								out.write(bytes, 0, read);
							}
						} catch (Exception e) {
							throw new WebApplicationException(e);
						}
					}
				};
			} catch (FileNotFoundException e) {
				logger.error("Errore durante lo scarico del file Agibilita: " + e.getMessage(), e);
				throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_SERVICE + ": " + e.getMessage(),
					Status.INTERNAL_SERVER_ERROR);
			}
			return Response
					.ok(stream)
					.header("content-disposition", "attachment; filename = " + fileName)
					.build();
		}
		return Response.status(500).build();
	}
	

	/*
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/getFile")
	public Response getFile(@Context HttpServletRequest request) throws Exception {
		String filePath = request.getParameter("uriFile");
		String token = request.getParameter("token");
		String fileName = request.getParameter("fileName");
		
		logger.debug("----------- Inizio servizio: Scarico file Agibilita ---------");
		logger.debug("uriFile input: " + filePath + ", nome file input: " + fileName + ", token: " + token);

		// validateToken(token);

		if (filePath != null && !"".equals(filePath)) {

			File fileResponse;

			// File cablati di ritorno
			if (fileName.equalsIgnoreCase("certificati.zip")) {
				fileResponse = new File(context.getRealPath("/WEB-INF/resources/certificati.zip"));
			} else {
				fileResponse = new File(context.getRealPath("/WEB-INF/resources/CMMI.D.I.NI.2018.0076310..._20180720132206356.pdf"));
			}

			StreamingOutput stream = null;

			final InputStream in = new FileInputStream(fileResponse);
			stream = new StreamingOutput() {
				public void write(OutputStream out) throws IOException, WebApplicationException {
					try {
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = in.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
					} catch (Exception e) {
						throw new WebApplicationException(e);
					}
				}
			};
			return Response.ok(stream).header("content-disposition", "attachment; filename = " + fileName).build();
		}
		return Response.status(500).build();
	}
	 */

	private RicercaAgibilitaResponse datiCablati(RicercaAgibilitaRequest ricercaAgibilitaRequest) throws MalformedURLException {
		RicercaAgibilitaResponse response = new RicercaAgibilitaResponse();
		    
	    Errore errore = new Errore();
	    ElencoAgibilita elencoAgibilita = new ElencoAgibilita();
	    List<String> agibilita = new ArrayList();
	    if ((ricercaAgibilitaRequest.getViaImmobile() == null) || (ricercaAgibilitaRequest.getNumeroCivico() == null))
	    {
	      response.setEsito("KO");
	      errore.setCodice("2");
	      errore.setMessaggio("Indirizzo e civico sono dati entrambi obbligatori");
	      response.setErrore(errore);
	    }
	    else if ("TLLNHS71P08F205K".equalsIgnoreCase(ricercaAgibilitaRequest.getCodiceFiscaleRichiedente()))
	    {
	      response.setEsito("KO");
	      errore.setCodice("1");
	      errore.setMessaggio("Hai superato il limite di 100 richieste annuali consentite da questo servizio. \r\nPuoi eventualmente richiedere lo sblocco di questo limite per motivate ragioni");
	      
	      response.setErrore(errore);
	    }
	    else if ((ricercaAgibilitaRequest.getViaImmobile().equalsIgnoreCase("VIA SANGRO")) && 
		  	      (ricercaAgibilitaRequest.getNumeroCivico().intValue() == 31))
	  	    {
//	      DatiCertificato agibilita = new DatiCertificato();
//	      agibilita.setAgibilita("78170/1997");
	      
	      agibilita.add("41.005.176/2000");
	      
	      elencoAgibilita.setAgibilita(agibilita);
	      
	      response.setEsito("OK");
	      response.setProtocollo("PG 988/2019");
	      response.setElencoAgibilita(elencoAgibilita);
//	      response.setPathServiceFile("https://auriga-pre.comune.milano.it/AurigaBusiness/rest/agibilita/getFile?uriFile=[FS@AURIGAREP]/2019/3/25/1/2019032517290161760360076229960058&fileName=CMMI.D.I.NI.2018.0076310..._20180720132206356.pdf&token=12345");
	      response.setPathServiceFile("http://10.63.10.111:8080/AurigaBusiness/rest/agibilita/getFile?uriFile=[FS@AURIGAREP]/2019/3/25/1/2019032517290161760360076229960058&fileName=CMMI.D.I.NI.2018.0076310..._20180720132206356.pdf&token=12345");
//	      response.setPathServiceFile("http://localhost:8088/AurigaBusiness/rest/agibilita/getFile?uriFile=[FS@AURIGAREP]/2019/3/25/1/2019032517290161760360059029960058&fileName=CMMI.D.I.NI.2018.0076310..._20180720132206356.pdf&token=12345");
	    }
	    else if (((ricercaAgibilitaRequest.getViaImmobile().equalsIgnoreCase("VICOLO GIOVANNI SUL MURO (SAN)")) && 
	  	      (ricercaAgibilitaRequest.getNumeroCivico().intValue() == 5) && 
	  	      (ricercaAgibilitaRequest.getAppendiceCivico().equalsIgnoreCase("9"))) 
	    		
	    		|| 	
	    		
	    		((ricercaAgibilitaRequest.getViaImmobile().equalsIgnoreCase("VIA TORINO")) && 
	  		  	      (ricercaAgibilitaRequest.getNumeroCivico().intValue() == 19)))
	  	    {
//	      DatiCertificato agibilita2 = new DatiCertificato();
//	      agibilita2.setAgibilita("160/1969");
//	      
//	      DatiCertificato agibilita3 = new DatiCertificato();
//	      agibilita3.setAgibilita("659/1929");
	      
	      agibilita.add("160/1969");
	      agibilita.add("659/1929");
	      
	      elencoAgibilita.setAgibilita(agibilita);
	      
	      response.setEsito("OK");
	      response.setProtocollo("PG 992/2019");
	      response.setElencoAgibilita(elencoAgibilita);
//	      response.setPathServiceFile("https://auriga-pre.comune.milano.it/AurigaBusiness/rest/agibilita/getFile?uriFile=[FS@AURIGAREP]/2019/3/25/1/2019032517290001760360076229960058&fileName=certificati.zip&token=12345");
	      response.setPathServiceFile("http://10.63.10.111:8080/AurigaBusiness/rest/agibilita/getFile?uriFile=[FS@AURIGAREP]/2019/3/25/1/2019032517290161760360059029960058&fileName=certificati.zip&token=12345");
//	      response.setPathServiceFile("http://localhost:8088/AurigaBusiness/rest/agibilita/getFile?uriFile=[FS@AURIGAREP]/2019/3/25/1/2019032517290161760360059029960058&fileName=certificati.zip&token=12345");
	    }
	    else
	    {
	      response.setEsito("KO");
	      errore.setCodice("0");
	      errore.setMessaggio("Nessun risultato trovato!");
	      response.setErrore(errore);
	    }
	    return response;
	}

	private RicercaAgibilitaStoreOutBean creatBeanForTest() {
		
		RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean = new RicercaAgibilitaStoreOutBean();
//		lRicercaAgibilitaStoreOutBean.setToken("#RESERVED_WS");  /*per 139*/
//		lRicercaAgibilitaStoreOutBean.setToken("#RESERVED_14");  /*per collaudo vicenza 14*/
		lRicercaAgibilitaStoreOutBean.setIdUDRisposta(new BigDecimal("48930"));  /*per 139*/
//		lRicercaAgibilitaStoreOutBean.setIdUDRisposta(new BigDecimal("7849"));  /*per collaudo vicenza 14*/
		lRicercaAgibilitaStoreOutBean.setIdDominio(new BigDecimal("2"));
		lRicercaAgibilitaStoreOutBean.setTestoInChiaroBarcode("BARCODE PROVA");
		lRicercaAgibilitaStoreOutBean.setContenutoBarcode("BARCODE PROVA AGIBILITA");
		lRicercaAgibilitaStoreOutBean.setNomeTemplate("template.pdf");
		lRicercaAgibilitaStoreOutBean.setUriTemplate("C:\\robe.odt");
		lRicercaAgibilitaStoreOutBean.setIdDocRisposta(new BigDecimal("76244"));
		lRicercaAgibilitaStoreOutBean.setTipoTemplate("ODT_FREEMARKERS");
		String sezioneChace_4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<SezioneCache>" + "<Variabile>"
				+ "<Nome>name</Nome>" + "<ValoreSemplice>ANTONIO PELUSO</ValoreSemplice>" + "</Variabile>"
				+ "</SezioneCache>";
//		lRicercaAgibilitaStoreOutBean.setSezioneCacheDatiModello(sezioneChace_4);
		List<String> listaCert = new ArrayList<String>();
		listaCert.add("cert1");
		listaCert.add("cert2");
		listaCert.add("cert3");
		listaCert.add("cert4");
		lRicercaAgibilitaStoreOutBean.setListaCertificati(listaCert);
		lRicercaAgibilitaStoreOutBean.setEstremiProtRichiesta("PG 0000391 / 2019 - TEST");
		FileAgibilitaOutBean file1 = new FileAgibilitaOutBean();
		file1.setUri("[FS@AURIGAREP]/2019/3/25/1/2019032517290161760360076229960058");
		file1.setNomeFile("ManualePraticoJava.pdf");
		file1.setMimetype("application\\pdf");

		FileAgibilitaOutBean file2 = new FileAgibilitaOutBean();
		file2.setUri("[FS@AURIGAREP]/2019/3/25/1/2019032517285994019103785657805329");
		file2.setNomeFile("OD_Essentials.pdf");
		file2.setMimetype("application\\pdf");

		List<FileAgibilitaOutBean> listafilesagibilita = new ArrayList<FileAgibilitaOutBean>();
		listafilesagibilita.add(file2);
		listafilesagibilita.add(file1);
		lRicercaAgibilitaStoreOutBean.setFilesAgibilita(listafilesagibilita);
		lRicercaAgibilitaStoreOutBean.setAccountMittenteMail("denisbragato@libero.it");
		lRicercaAgibilitaStoreOutBean.setOggettoMail("TEST AGIBILITA");
		lRicercaAgibilitaStoreOutBean.setCorpoMail("TEST AGIBILITA - mi leggi?");
		lRicercaAgibilitaStoreOutBean.setFlgFileAllegatiMail(true);
		lRicercaAgibilitaStoreOutBean.setDestinatariMail("antonypeluso@gmail.com");
		
		return lRicercaAgibilitaStoreOutBean;
	}

	// crea un pdf partendo da un modello e dei dati che mi restituisce la store
	private File creaPdfDaModello(RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean) throws Exception {

		File modelloWithValues = creaModello(lRicercaAgibilitaStoreOutBean);
		return convertToPdf(modelloWithValues, lRicercaAgibilitaStoreOutBean.getNomeTemplate());
	}

	private File convertToPdf(File modelloWithValues, String nomeModello) throws Exception {
		logger.debug("Converto il modello in PDF");
		File pdfDaModello = null;
		try {
			InputStream targetStream = TimbraUtil.converti(modelloWithValues,nomeModello);
			pdfDaModello = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(targetStream, pdfDaModello);
		}catch (Exception e) {
			logger.error("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
			throw new Exception("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
		}

		return pdfDaModello;
	}

	private File creaModello(RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean) throws Exception {
		String tipoModello = lRicercaAgibilitaStoreOutBean.getTipoTemplate();
		String uriModello = lRicercaAgibilitaStoreOutBean.getUriTemplate();
		
		File filledTemplate = null;
		
		try {
			/* RECUPERO I DATI (SEZIONE CACHE DA INNIETTARE NEL MODELLO) */
			
			logger.debug("Recupero i dati da inniettare nel modello");
			DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
			lGetdatixgendamodelloInput.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
			lGetdatixgendamodelloInput.setIdobjrifin(String.valueOf(lRicercaAgibilitaStoreOutBean.getIdUDRisposta()));
			lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
			lGetdatixgendamodelloInput.setNomemodelloin(lRicercaAgibilitaStoreOutBean.getNomeTemplate());

			DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello
					.execute(new Locale("it"), lAurigaLoginBean, lGetdatixgendamodelloInput);

			if (lGetdatixgendamodelloOutput.isInError()) {
				logger.error("Errore durante il recupero della sezione cahce da inniettare nel modello");
				throw new StoreException("Errore durante il recupero della sezione cahce da inniettare nel modello");
			}
			

			String sezionCacheModello = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
			
			/*
			//Stringa cablata per i TEST, usare la stored di sopra
			String sezionCacheModello = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<SezioneCache>" + "<Variabile>"
					+ "<Nome>name</Nome>" + "<ValoreSemplice>ANTONIO PELUSO</ValoreSemplice>" + "</Variabile>"
					+ "</SezioneCache>";
			*/

			logger.debug("uriModello: " + lRicercaAgibilitaStoreOutBean.getUriTemplate());
			logger.debug("tipoModello: " + tipoModello);
			logger.debug("templateValues: " + sezionCacheModello);

			if (StringUtils.isNotBlank(uriModello)) {

				File templateOdt = DocumentStorage.extract(uriModello,lRicercaAgibilitaStoreOutBean.getIdDominio());
//				File templateOdt = new File(uriModello);   /**DA USARE PER I TEST*/

				if (tipoModello != null) {
					try {
						switch (tipoModello) {

						case "odt_con_freemarkers":
							DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
							InputStream templateInputStream = FileUtils.openInputStream(templateOdt);
							DocumentTemplate template = documentTemplateFactory.getTemplate(templateInputStream);
							
							Map<String, Object> mappaValori = ModelliUtil
									.createMapToFillTemplateFromSezioneCache(sezionCacheModello, true);
							
							//converto le impronte dei file ritornati dalla store in timbri
							inserisciTimbro(mappaValori);

							File templateOdtWithValues = File.createTempFile("temp", ".odt");
							FileOutputStream odtOutputStream = new FileOutputStream(templateOdtWithValues);
							template.createDocument(mappaValori, odtOutputStream);

							filledTemplate = templateOdtWithValues;
							break;
						case "docx":
							filledTemplate = new DocxCompiler(sezionCacheModello, uriModello).fillDocument();
							break;
						case "docx_form":
							filledTemplate = new FormDocxCompiler(sezionCacheModello, uriModello).fillDocument();
							break;
						case "odt":
							filledTemplate = new OdtCompiler().fillOdtDocument(sezionCacheModello, uriModello);
							break;
						case "composite":
							filledTemplate = new CompositeCompiler(sezionCacheModello, uriModello).injectData();
							break;
						default:
							break;
						}
					} catch (Throwable e) {
						logger.error(
								"Durante l'iniezione dei dati si e verificato il seguente errore: " + e.getMessage(),e);
						throw new Exception("Durante l'iniezione dei dati si e verificato il seguente errore: " + e.getMessage(),e);
					}

				}
			} else {
				logger.error("URI del modello associato al task inesistente");
				throw new Exception("URI del modello associato al task inesistente");
			}
			if (filledTemplate == null) {
				logger.error("Si e verificato un errore durante la generazione del modello");
				throw new Exception("Si e verificato un errore durante la generazione del modello");
			}

		}catch(Exception e) {
			logger.error("Errore durante la creazione del modello: " + e.getMessage(), e);
			throw new Exception("Errore durante la creazione del modello: " + e.getMessage(), e);
		}

		return filledTemplate;
	}

	/*
	 * Metodo che salva i file ricevuti in input dal portale
	 */
	private List<FileAgibilitaXmlInBean> salvaFilePortale(List<FormDataBodyPart> files)throws Exception {
		List<FileAgibilitaXmlInBean> listaFiles = new ArrayList<>();

		try {
			for (FormDataBodyPart field : files) {
				InputStream is = field.getEntityAs(InputStream.class);
				String fileName = field.getFormDataContentDisposition().getFileName();
	
				File tempFile = File.createTempFile(FilenameUtils.getBaseName(fileName) + "_", "." + FilenameUtils.getExtension(fileName));
	
				FileUtils.copyInputStreamToFile(is, tempFile);
	
				String uriFile = DocumentStorage.store(tempFile, null);
				File fileExtract = DocumentStorage.extract(uriFile, null);
				
				MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileExtract.toURI().toString(),fileName, false, null);
				
				if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
					logger.error("Errore durante il calcolo del mymetype del file in input: " + tempFile.getName());
					throw new Exception("Errore durante il calcolo delle informazione del file");
				}
	
				FileAgibilitaXmlInBean file = new FileAgibilitaXmlInBean();
				file.setUri(uriFile);
				file.setNome(lMimeTypeFirmaBean.getCorrectFileName());
				file.setDimensione(new BigDecimal(tempFile.length()));
				file.setMimetype(lMimeTypeFirmaBean.getMimetype());
				file.setImpronta(lMimeTypeFirmaBean.getImpronta());
				file.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
				file.setEncoding(lMimeTypeFirmaBean.getEncoding());
				file.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? 1 : 0);
				
				String firmatari = "";
				if(lMimeTypeFirmaBean.getFirmatari()!=null) {
					for(String firmatario : lMimeTypeFirmaBean.getFirmatari()){
						firmatari = firmatari + firmatario +";";
					}
					file.setFirmatari(firmatari);
				}
	
				listaFiles.add(file);
			}
		}catch (Exception e) {
			logger.error("Errore durante il salvataggio dei file in input sullo storage: " + e.getMessage(), e);
			throw new Exception("Errore durante il salvataggio dei file in input sullo storage");
		}
		
		return listaFiles;
	}

	
	/**
	 * Questo metodo costriusce il path per richiamare il servizio che ritorna lo stream del file: 
	 * 
	 * N.B il path e' strutturato con i seguenti queryParam: 
	 * 		uri del file + nome del file + token
	 * @throws Exception 
	 */
	private String createPathForService(RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean) throws Exception {

		String pathServiceFile = "";
		String uriFile;

		try {
			if (lRicercaAgibilitaStoreOutBean.getFilesAgibilita()!=null && !lRicercaAgibilitaStoreOutBean.getFilesAgibilita().isEmpty()) {
				if (lRicercaAgibilitaStoreOutBean.getFilesAgibilita().size() == 1) {

					FileAgibilitaOutBean file = lRicercaAgibilitaStoreOutBean.getFilesAgibilita().get(0);
					uriFile = file.getUri();
					String nomeFile = file.getNomeFile();

					String queryParam = "uriFile=" + uriFile + "&fileName=" + nomeFile;

					pathServiceFile = lRicercaAgibilitaWSConfigBean.getPathServiceFile() + queryParam;
				} else {
					File zipFile = createZip(lRicercaAgibilitaStoreOutBean.getFilesAgibilita());

					uriFile = DocumentStorage.store(zipFile, null);
					logger.debug("Uri dello zip storato: " + uriFile);

					String queryParam = "uriFile=" + uriFile + "&fileName=certificati.zip";

					pathServiceFile = lRicercaAgibilitaWSConfigBean.getPathServiceFile() + queryParam;
				}

				// creazione e inserimento del token nella path del servizio.
//				Long time = System.currentTimeMillis();
				
				String idFile = uriFile.substring(uriFile.lastIndexOf("/")+1);
				
				String encrypt = CryptoUtility.encrypt64FromString(idFile);
				pathServiceFile += "&token=" + encrypt;
				logger.debug("Path Service: " + pathServiceFile);

				return pathServiceFile;
			}
		}catch(Exception e) {
			logger.error("Errore durante la creazione del path del servizio di download: " + e.getMessage());
			throw new Exception("Errore durante la creazione del path del servizio di download");
		}
		return pathServiceFile;
	}
	
	private String createStreamFile(RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean) throws Exception {
		String streamFile="";
		
		if(lRicercaAgibilitaStoreOutBean.getFilesAgibilita() != null && lRicercaAgibilitaStoreOutBean.getFilesAgibilita().size()>0 ) {
			
			//La store ha ritornato un solo file
			if(lRicercaAgibilitaStoreOutBean.getFilesAgibilita().size() == 1) {
				FileAgibilitaOutBean file = lRicercaAgibilitaStoreOutBean.getFilesAgibilita().get(0);
				String uriFile = file.getUri();
				
				File fileExtract = DocumentStorage.extract(uriFile, lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				
				streamFile = fromFileToBytesString(fileExtract);
			//La store ha ritornato piu file e creo uno zip
			}else {
				File fileZip = createZip(lRicercaAgibilitaStoreOutBean.getFilesAgibilita());
				
				streamFile = fromFileToBytesString(fileZip);
			}
		}
		
		return streamFile;
		
	}
	
	private String fromFileToBytesString(File file) throws IOException {
		byte[] bytesFile = FileUtils.readFileToByteArray(file);
	    String bytesFileString = new String(bytesFile, "UTF-8");

		return bytesFileString;
	}

	private File createZip(List<FileAgibilitaOutBean> listaFiles) throws Exception {
		try {

			logger.debug("Creo lo zip per il servizio di scarico");
			File fileToZip = File.createTempFile("zipFile", ".zip");

			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(fileToZip.getPath()));
			
			/*rinomino i file con lo stesso nome, senno va in errore l'insermento nel file zip*/
			listaFiles = manageNameFileToZip(listaFiles);

			for (FileAgibilitaOutBean file : listaFiles) {

				File fileExtract = DocumentStorage.extract(file.getUri(), lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				InputStream is = FileUtils.openInputStream(fileExtract);
				zip.putNextEntry(new ZipEntry(file.getNomeFile()));
				int length;

				byte[] b = new byte[1024 * 10];

				while ((length = is.read(b)) > 0) {
					zip.write(b, 0, length);
				}
				zip.closeEntry();
				is.close();
			}
			zip.close();
			
			return fileToZip;
			
		} catch (Exception e) {
			logger.error("Errore durante la generazione del file.zip");
			throw new Exception("Errore durante la generazione del file zip: " + e.getMessage());
		}

	}
	
	
	/*  
	*   Questo metodo mi rinomina i file in caso di file con lo stesso nome
	*
	*  * lo utilizzo perchè per creare il file zip non posso avere file con lo stesso nome, và in errore *
	*/
	private static List<FileAgibilitaOutBean> manageNameFileToZip(List<FileAgibilitaOutBean> listaFiles) {
		List<FileAgibilitaOutBean> listFilesResult = new ArrayList<>();
		
		Map<String,List<FileAgibilitaOutBean>> mappaNamesFile = new LinkedHashMap<>();

		/*Creo mappa avente chiave: nome del file e valore: la lista dei file avente quel nome*/
		for (FileAgibilitaOutBean file : listaFiles) {

			if(mappaNamesFile.containsKey(file.getNomeFile())) {
				List<FileAgibilitaOutBean> fileList = mappaNamesFile.get(file.getNomeFile());
				fileList.add(file);
			}else {
				List<FileAgibilitaOutBean> listFile = new LinkedList<>();
				listFile.add(file);
				mappaNamesFile.put(file.getNomeFile(), listFile);
			}
		}
		
		/*Scorro la lista di file per ogni nome file, e se ne trovo piu di uno li rinomino con _1,_2 e cosi via*/
		for(String name : mappaNamesFile.keySet()) {
			List<FileAgibilitaOutBean> fileList = mappaNamesFile.get(name);
			if(fileList.size()>1) {
				int count = 1;
				for(FileAgibilitaOutBean file : fileList) {
					String namefile = FilenameUtils.getBaseName(file.getNomeFile()) + "_" + count + "." + FilenameUtils.getExtension(file.getNomeFile());
					file.setNomeFile(namefile);
					count++;
					
					listFilesResult.add(file);
				}
			}else {
				listFilesResult.add(fileList.get(0));
			}
		}
		
		return listFilesResult;
		
	}
	
	

	// Metodo che converte il bean in input al servizio in xml
	/*
	private String convertBeanToXml(RicercaAgibilitaRequest ricercaAgibilitaRequest) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RicercaAgibilitaRequest.class);
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(ricercaAgibilitaRequest, sw);
			// Verify XML Content
			String xmlContent = sw.toString();

			return xmlContent;
		} catch (Exception e) {
			logger.error("Errore nella conversione dell' Bean di input in Xml: " + e.getMessage(),e);
			throw new Exception("Errore nella conversione dell' Bean di input");
		}
	}*/

	/*
	 * Metodo che invia una mail all indirizzo del richiedente allego alla mail i
	 * file dell agibilita ricevuti dalla store e il pdf creato da modello
	 */
	private EmailSentReferenceBean invioMailModello(RicercaAgibilitaStoreOutBean ricercaAgibilitaStoreOutBean, List<FileAgibilitaOutBean> pListaAllegatiMail, String nomeFileModello)
			throws Exception {

		SenderBean senderBean = new SenderBean();
		ResultBean<EmailSentReferenceBean> output = null;

		senderBean.setFlgInvioSeparato(false);
		senderBean.setIsPec(true);

		// Mittente
		// testeng@serviziopec.eng.it
		senderBean.setAccount(ricercaAgibilitaStoreOutBean.getAccountMittenteMail());
		senderBean.setAddressFrom(ricercaAgibilitaStoreOutBean.getAccountMittenteMail());
//		senderBean.setAccount("testeng@serviziopec.eng.it");
//		senderBean.setAddressFrom("testeng@serviziopec.eng.it");
//		senderBean.setAliasAddressFrom(ricercaAgibilitaStoreOutBean.getAccountMittenteMail());

		// Destinatari principali
		if(ricercaAgibilitaStoreOutBean.getDestinatariMail() != null) {
			String[] destinatariTo = ricercaAgibilitaStoreOutBean.getDestinatariMail().split(DEFAULT_SPLIT_CHAR_EMAIL);
			senderBean.setAddressTo(Arrays.asList(destinatariTo));
		} else {
			//TODO: throw una bella eccezione!
		}
		

		// Oggetto
		senderBean.setSubject(ricercaAgibilitaStoreOutBean.getOggettoMail());

		// CORPO
		senderBean.setBody(ricercaAgibilitaStoreOutBean.getCorpoMail());
		senderBean.setIsHtml(true);

		// Conferma di lettura
		senderBean.setReturnReceipt(false);

		// Attach
		List<SenderAttachmentsBean> listaSenderAttachmentsBean = new ArrayList<SenderAttachmentsBean>();
		for (FileAgibilitaOutBean file : pListaAllegatiMail) {

			String uriFile = file.getUri();
			String nomeFile = file.getNomeFile();

			File fileOut; 
			try {
//				fileOut = DocumentStorage.extract(uriFile, null); //TODO da usare in locale
				fileOut = DocumentStorage.extract(uriFile, lAurigaLoginBean.getSpecializzazioneBean().getIdDominio()); //TODO da usare in preproduzione
			} catch (StorageException e) {
				logger.error("Errore durante il recupero del file per l'invio mail : " + e.getMessage(),e);
				throw new StorageException(e.getMessage(), e);
			}
			// Se il servizio e' andato in errore restituisco il messaggio di errore
			if (fileOut == null) {
				logger.error("Errore durante invio mail: Errore nel recupero del file");
				throw new Exception("Errore durante invio mail: Errore nel recupero del file");
			}

			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileOut.toURI().toString(),
					fileOut.getName(), false, null);
			if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
				logger.error(
						"Errore durante invio mail: Si e verificato un errore durante il controllo del file allegato: "
								+ fileOut.getName());
				throw new Exception(
						"Errore durante invio mail: Si e verificato un errore durante il controllo del file allegato "
								+ fileOut.getName());
			}

			SenderAttachmentsBean senderAttachmentsBean = new SenderAttachmentsBean();
			senderAttachmentsBean.setFile(fileOut);
			senderAttachmentsBean.setFirmato(lMimeTypeFirmaBean.isFirmato());
			senderAttachmentsBean.setFirmaValida(lMimeTypeFirmaBean.isFirmaValida());
			senderAttachmentsBean.setMimetype(lMimeTypeFirmaBean.getMimetype());
			senderAttachmentsBean.setOriginalName(lMimeTypeFirmaBean.getCorrectFileName());
			senderAttachmentsBean.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));
			senderAttachmentsBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
			senderAttachmentsBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
			senderAttachmentsBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
			if (!nomeFile.equals(nomeFileModello)) {
				senderAttachmentsBean.setFilename("certificato_agibilita_" + nomeFile);
			}else {
				senderAttachmentsBean.setFilename(nomeFile);
			}
			listaSenderAttachmentsBean.add(senderAttachmentsBean);
		}
		senderBean.setAttachments(listaSenderAttachmentsBean);

		try {
			output = AurigaMailService.getMailSenderService().sendandsave(new Locale("it"), senderBean);
			
			return output.getResultBean();
		} catch (Exception e) {
			logger.error("Errore durante invio mail: " +e.getMessage(),e);
			throw new Exception(e.getMessage(), e);
		}
//		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
//			if (output.isInError()) {
//				logger.error("Errore durante invio mail: " + output.getDefaultMessage());
//				throw new Exception(output.getDefaultMessage());
//			}
//		}
	}



	// Metodo che invoca la store Ricercaagibilitadaportale
	private RicercaAgibilitaStoreOutBean callStoreRicercaAgibilita(
			RicercaAgibilitaWSConfigBean lRicercaAgibilitaWSConfigBean, String xmlInputBean, String XmlListaFilesPortale) throws Exception {

		RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean = new RicercaAgibilitaStoreOutBean();
		
		logger.debug("Dati in input alla store: " + xmlInputBean);
		logger.debug("File in input alla store: " + XmlListaFilesPortale);

		
		try {
			// creo bean connessione
	
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(lRicercaAgibilitaWSConfigBean.getDefaultSchema());
			SubjectUtil.subject.set(subject);
			
			SchemaBean schemaBean = new SchemaBean();
			schemaBean.setSchema(lAurigaLoginBean.getSchema());
	
			/* Setto i parametri in input alla store */
			DmpkIntPortaleCrmRichiestaagibilitaBean lDmpkIntPortaleCrmRichiestaagibilitaBean = new DmpkIntPortaleCrmRichiestaagibilitaBean();
			lDmpkIntPortaleCrmRichiestaagibilitaBean.setDatirichiestaxmlin(xmlInputBean);
			lDmpkIntPortaleCrmRichiestaagibilitaBean.setFileallegatirichiestain(XmlListaFilesPortale);
	
			// effettuo la chiamata alla store
			final Richiestaagibilita service = new Richiestaagibilita();
			service.setBean(lDmpkIntPortaleCrmRichiestaagibilitaBean);
	
			StoreResultBean<DmpkIntPortaleCrmRichiestaagibilitaBean> lResultStore = service.execute(schemaBean,
					lDmpkIntPortaleCrmRichiestaagibilitaBean);
	
			AnalyzeResult.analyze(lDmpkIntPortaleCrmRichiestaagibilitaBean, lResultStore);
			lResultStore.setResultBean(lDmpkIntPortaleCrmRichiestaagibilitaBean);
	
			// si E verificato un errore
			if (lResultStore.isInError()) {
				logger.error("La store ha restituito un errore");
				logger.error(lResultStore.getDefaultMessage());
				logger.error(lResultStore.getErrorContext());
				logger.error(lResultStore.getErrorCode());
	
				lRicercaAgibilitaStoreOutBean.setErrMsg(lResultStore.getDefaultMessage());
				lRicercaAgibilitaStoreOutBean.setErrorContext(lResultStore.getErrorContext());
				lRicercaAgibilitaStoreOutBean.setErrorCode(lResultStore.getErrorCode());
			} else {
	
	
				if (lResultStore.getResultBean() != null) {
					// costruisco il bean con i dati restituiti dalla store
					XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
					XmlRicercaAgibilitaOutBean lXmlRicercaAgibilitaOutBean = lXmlUtility.unbindXml(lResultStore.getResultBean().getDatirispostaxmlout(),
							XmlRicercaAgibilitaOutBean.class);
					
	
					lRicercaAgibilitaStoreOutBean.setAccountMittenteMail(lXmlRicercaAgibilitaOutBean.getIndirizzoMittenteEmailRisposta());
					lRicercaAgibilitaStoreOutBean.setContenutoBarcode(lXmlRicercaAgibilitaOutBean.getContenutoTimbroRisposta());
					lRicercaAgibilitaStoreOutBean.setCorpoMail(lXmlRicercaAgibilitaOutBean.getCorpoEmailRisposta());
					lRicercaAgibilitaStoreOutBean.setEstremiProtRichiesta(lXmlRicercaAgibilitaOutBean.getNroProtocolloRichiesta());
					if("1".equalsIgnoreCase(lXmlRicercaAgibilitaOutBean.getAgibilitaAllegateEmail())){
						lRicercaAgibilitaStoreOutBean.setFlgFileAllegatiMail(true);
					}else{
						lRicercaAgibilitaStoreOutBean.setFlgFileAllegatiMail(false);
					}
					lRicercaAgibilitaStoreOutBean.setIdDocRisposta(lXmlRicercaAgibilitaOutBean.getIdDocPrimarioProtocolloRisposta() != null ? new BigDecimal(lXmlRicercaAgibilitaOutBean.getIdDocPrimarioProtocolloRisposta()) : null);
					lRicercaAgibilitaStoreOutBean.setIdDominio(lXmlRicercaAgibilitaOutBean.getIdDominio()!= null ? new BigDecimal(lXmlRicercaAgibilitaOutBean.getIdDominio()) : null);
					lRicercaAgibilitaStoreOutBean.setIdUDRisposta(lXmlRicercaAgibilitaOutBean.getIdUDProtocolloRisposta() != null ? new BigDecimal(lXmlRicercaAgibilitaOutBean.getIdUDProtocolloRisposta()): null);
					lRicercaAgibilitaStoreOutBean.setIdUtenteInvioMail(lXmlRicercaAgibilitaOutBean.getIdCasellaMittenteEmailRisposta());
					lRicercaAgibilitaStoreOutBean.setNomeTemplate(lXmlRicercaAgibilitaOutBean.getNomeModelloRisposta());
					lRicercaAgibilitaStoreOutBean.setOggettoMail(lXmlRicercaAgibilitaOutBean.getOggettoEmailRisposta());
					lRicercaAgibilitaStoreOutBean.setTestoInChiaroBarcode(lXmlRicercaAgibilitaOutBean.getTestoInChiaroPerTimbroRisposta());
					lRicercaAgibilitaStoreOutBean.setTipoTemplate(lXmlRicercaAgibilitaOutBean.getTipoModelloRisposta());
					lRicercaAgibilitaStoreOutBean.setIdTemplate(lXmlRicercaAgibilitaOutBean.getIdModelloRisposta()!= null ? new BigDecimal(lXmlRicercaAgibilitaOutBean.getIdModelloRisposta()): null);
					lRicercaAgibilitaStoreOutBean.setUriTemplate(lXmlRicercaAgibilitaOutBean.getUriModelloRisposta());
					lRicercaAgibilitaStoreOutBean.setDestinatariMail(lXmlRicercaAgibilitaOutBean.getDestinatariEmailRisposta());
					lRicercaAgibilitaStoreOutBean.setConnectionToken(lXmlRicercaAgibilitaOutBean.getConnectionToken());
	
					// recupero la lista dei certificati
					if (lXmlRicercaAgibilitaOutBean.getListaAgibilita() != null) {
						List<String> listaCertificati = new ArrayList<String>();
							for (ValueBean numeroAgibilita : lXmlRicercaAgibilitaOutBean.getListaAgibilita() ) {
								listaCertificati.add(numeroAgibilita.getValue());
							}
							lRicercaAgibilitaStoreOutBean.setListaCertificati(listaCertificati);
						}
						
					// recupero la lista dei file da restituire
					if (lXmlRicercaAgibilitaOutBean.getListaFileAgibilita() != null) {
						lRicercaAgibilitaStoreOutBean.setFilesAgibilita(lXmlRicercaAgibilitaOutBean.getListaFileAgibilita());
					}
	
				} else {
					logger.debug("Nessun risultato restituito dalla store");
					logger.debug(lResultStore.getDefaultMessage());
					logger.debug(lResultStore.getErrorContext());
					logger.debug(lResultStore.getErrorCode());
	
					lRicercaAgibilitaStoreOutBean.setErrMsg(lResultStore.getDefaultMessage());
					lRicercaAgibilitaStoreOutBean.setErrorContext(lResultStore.getErrorContext());
					lRicercaAgibilitaStoreOutBean.setErrorCode(lResultStore.getErrorCode());
				}
			}

		}catch(Exception e){
			logger.error("Errore durante l'invocazione della store di Ricerca: " + e.getMessage(), e);
			throw new Exception("Errore durante l'invocazione della store di Ricerca");
		}
		
		return lRicercaAgibilitaStoreOutBean;
	}

	public Vector<String> getValoriRiga(Riga r) {
		Vector<String> v = new Vector<String>();
		int oldNumCol = 0;
		for (int j = 0; j < r.getColonna().size(); j++) {
			// Aggiungo le colonne vuote
			for (int k = (oldNumCol + 1); k < r.getColonna().get(j).getNro().intValue(); k++)
				v.add(null);
			String content = r.getColonna().get(j).getContent();
			// aggiungo la colonna
			if (StringUtils.isNotBlank(content)) {
				try {
					SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));
					v.add(content);
				} catch (JAXBException e) {
					v.add(content.replace("\n", "<br/>"));
				}
			} else {
				v.add(null);
			}
			oldNumCol = r.getColonna().get(j).getNro().intValue(); // aggiorno l'ultimo numero di colonna
		}
		return v;
	}

	/**
	 * 
	 * @param lRicercaAgibilitaStoreOutBean
	 * @param listaFile
	 * @return Mappa di errori
	 * @throws Exception 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws JAXBException
	 */
	public void aggiungiAllegatiUD(RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean,
			List<FileAgibilitaOutBean> listaFile, Session session) throws Exception {

		// idUd Richiesta Agibilita
		BigDecimal idUdRichiesta = lRicercaAgibilitaStoreOutBean.getIdUDRisposta();

		// lista dei file da versionare
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		// mappa degli errori di versionamento
		Map<String, String> fileErrors = new HashMap<>();

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();

		try {

			int count = 1;
			if (listaFile != null) {
				for (int i = 0; i < listaFile.size(); i++) {
					if (listaFile.get(i).getUri() != null) {
						AllegatoStoreBean lAllegatoStoreBean = new AllegatoStoreBean();
						lAllegatoStoreBean.setIdUd(idUdRichiesta);
						lAllegatoStoreBean.setDescrizione(null);
						lAllegatoStoreBean.setIdDocType(null);

						String attributi = lXmlUtilitySerializer.bindXmlCompleta(lAllegatoStoreBean);
						DmpkCoreAdddocBean lAdddocBeanAllegato = new DmpkCoreAdddocBean();
						lAdddocBeanAllegato.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
						lAdddocBeanAllegato.setIduserlavoroin(null);
						lAdddocBeanAllegato.setAttributiuddocxmlin(attributi);
						final AdddocImpl storeAllegato = new AdddocImpl();
						storeAllegato.setBean(lAdddocBeanAllegato);
						session.doWork(new Work() {

							@Override
							public void execute(Connection paramConnection) throws SQLException {
								paramConnection.setAutoCommit(false);
								storeAllegato.execute(paramConnection);
							}
						});
						StoreResultBean<DmpkCoreAdddocBean> resultAllegato = new StoreResultBean<DmpkCoreAdddocBean>();
						AnalyzeResult.analyze(lAdddocBeanAllegato, resultAllegato);
						resultAllegato.setResultBean(lAdddocBeanAllegato);

						if (resultAllegato.isInError()) {
							throw new StoreException(resultAllegato);
						} else {
							if (StringUtils.isNotBlank(listaFile.get(i).getUri())) {
								File file = new File(listaFile.get(i).getUri());
								FileInfoBean info = getFileInfoBean(file);
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setFile(file);
								lRebuildedFile.setInfo(info);
								lRebuildedFile.setIdDocumento(resultAllegato.getResultBean().getIddocout());
								lRebuildedFile.setPosizione(count);
								// lRebuildedFile.setUpdateVersion(true);
								versioni.add(lRebuildedFile);
							}
						}
						count++;
					}
				}
			}

			// Parte di versionamento
			fileErrors = versionamentiInTransaction(versioni, session);

			if (!fileErrors.isEmpty()) {
				logger.error("Mappa errori versionamento : \n "+ fileErrors.toString());
				throw new Exception("Errore durante il versionamento dei file di attacch"); //TODO : riscrivere
			}

		} catch (Exception e) {
			logger.error("Errore durante l'attacch dei file all UD: " + e.getMessage(), e);
			throw new Exception("Errore durante l'attacch dei file all' UD");
			
		}

	}

	protected FileInfoBean getFileInfoBean(File file) throws Exception {
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		try {
		lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(file.toURI().toString(), file.getName(), false,
				null);
		} catch (Exception e) {
			logger.error("Errore durante il recupero delle informazioni del file " + file.getName() + " - path : " + file.getPath());
			throw new Exception(e.getMessage(), e);
		}
		GenericFile allegatoRiferimento = new GenericFile();
		allegatoRiferimento.setDisplayFilename(file.getName());
		allegatoRiferimento.setImpronta(lMimeTypeFirmaBean.getImpronta());
		allegatoRiferimento.setMimetype(lMimeTypeFirmaBean.getMimetype());
		allegatoRiferimento.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
		allegatoRiferimento.setEncoding(lMimeTypeFirmaBean.getEncoding());
		allegatoRiferimento.setIdFormato(lMimeTypeFirmaBean.getIdFormato());

		FileInfoBean info = new FileInfoBean();
		info.setTipo(TipoFile.ALLEGATO);
		info.setAllegatoRiferimento(allegatoRiferimento);
		return info;
	}

	private Map<String, String> versionamentiInTransaction(List<RebuildedFile> versioni, Session session) {
		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = getVersionaDocumentoInBean(lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoInTransaction(
						lVersionaDocumentoInBean, session);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				logger.error("Errore " + e.getMessage(), e);
				fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(),
						"Il file allegato " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
								+ " in posizione " + lRebuildedFile.getInfo().getPosizione()
								+ " non E stato salvato correttamente."
								+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));

			}
		}

		return fileErrors;
	}

	protected VersionaDocumentoInBean getVersionaDocumentoInBean(RebuildedFile lRebuildedFile)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
		return lVersionaDocumentoInBean;
	}

	private VersionaDocumentoOutBean versionaDocumentoInTransaction(VersionaDocumentoInBean lVersionaDocumentoInBean,
			Session session) {

		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {

			String uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(),
					lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			logger.debug("Salvato " + uriVer);

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());

			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean,
						lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				logger.warn(e);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				logger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				logger.warn(e);
			}

			if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {

				DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
				lUpdverdocBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(null);
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setNroprogrverio(null);
				lUpdverdocBean.setAttributixmlin(lStringXml);

				final UpdverdocImpl store = new UpdverdocImpl();
				store.setBean(lUpdverdocBean);
				logger.debug("Chiamo la updverdoc " + lUpdverdocBean);

				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreUpdverdocBean>();
				AnalyzeResult.analyze(lUpdverdocBean, lUpdverdocStoreResultBean);
				lUpdverdocStoreResultBean.setResultBean(lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {
					logger.error("Default message: " + lUpdverdocStoreResultBean.getDefaultMessage());
					logger.error("Error context: " + lUpdverdocStoreResultBean.getErrorContext());
					logger.error("Error code: " + lUpdverdocStoreResultBean.getErrorCode());
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lUpdverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			} else {

				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
				lAddverdocBean.setIduserlavoroin(null);
				lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lAddverdocBean.setAttributiverxmlin(lStringXml);

				final AddverdocImpl store = new AddverdocImpl();
				store.setBean(lAddverdocBean);
				logger.debug("Chiamo la addVerdoc " + lAddverdocBean);

				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
				AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
				lAddverdocStoreResultBean.setResultBean(lAddverdocBean);

				if (lAddverdocStoreResultBean.isInError()) {
					logger.error("Default message: " + lAddverdocStoreResultBean.getDefaultMessage());
					logger.error("Error context: " + lAddverdocStoreResultBean.getErrorContext());
					logger.error("Error code: " + lAddverdocStoreResultBean.getErrorCode());
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lAddverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			}

		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				logger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				logger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;
		}

		return lVersionaDocumentoOutBean;
	}
	
	private String versionaDocumento(VersionaDocumentoInBean lVersionaDocumentoInBean,Session session) throws Exception {
		
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		String uriVer = null;
		FileStoreBean lFileStoreBean = new FileStoreBean();
		
		try {
			
			uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(),
					lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			logger.debug("Salvato " + uriVer);
			
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());
			
			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean,
						lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				logger.warn(e);
			}
			
			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				logger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				logger.warn(e);
			}
				
			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
			lAddverdocBean.setIduserlavoroin(null);
			lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
			lAddverdocBean.setAttributiverxmlin(lStringXml);
			
			final AddverdocImpl store = new AddverdocImpl();
			store.setBean(lAddverdocBean);
			logger.debug("Chiamo la addVerdoc " + lAddverdocBean);
			
			session.doWork(new Work() {
				
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});
			
			StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
			AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
			lAddverdocStoreResultBean.setResultBean(lAddverdocBean);
			
			if (lAddverdocStoreResultBean.isInError()) {
				logger.error("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename()  + "\n" + lAddverdocStoreResultBean.getDefaultMessage());
				throw new Exception("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename() + "\n" + lAddverdocStoreResultBean.getDefaultMessage());
			}
		} catch (Throwable e) {
			logger.error("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename() + "\n" + e.getMessage(), e);
			throw new Exception("Errore durante il versionamento del file: " + lFileStoreBean.getDisplayFilename() + "\n" + e.getMessage(), e);
		}
		
		return uriVer;
	}

	// Metodo che appone il timbro sul file
	private File timbraFile(File filedaTimbrare, String testoInChiaroBarcode, String contenutoBarcode,
			OpzioniCopertinaTimbroBean opzioniTimbro) throws Exception {
		try {
			TimbraUtil.impostaTestoOpzioniTimbro(opzioniTimbro, testoInChiaroBarcode, contenutoBarcode);
			InputStream fileTimbratoStream = TimbraUtil.timbra(filedaTimbrare, filedaTimbrare.getName(), opzioniTimbro,
					false);
			File fileTimbrato = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(fileTimbratoStream, fileTimbrato);
			return fileTimbrato;
		} catch (Exception e) {
			logger.error("Errore durante la timbratura del file: " + filedaTimbrare.getName() + "\n" + e.getMessage(), e);
			throw new Exception("Errore durante la timbratura del file: " + filedaTimbrare.getName() + "\n" + e.getMessage(), e);
		}

	}
	
	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	private boolean validateToken(String token, String uriFile) throws Exception {
		if (!StringUtils.isNotBlank(token)) {
			logger.error("Token mancante");
			return false;
		} else {			
			String idFile = uriFile.substring(uriFile.lastIndexOf("/")+1);
			
			String encrypt = CryptoUtility.encrypt64FromString(idFile);
			
			if(!token.equals(encrypt)) {
				logger.error("Token non valido");
				return false;
			}
		}
		logger.debug("Token valido");
		return true;
	}
	
	private void validateToken2(String token, String uriFile) throws Exception {

		if (token.length() == 0) {
			logger.error("Campo Token non presente.");
//			throw new AccessoNegatoAgibilitaException("Campo Token non presente.");
			throw new Exception("Campo Token non presente");
		} else if(StringUtils.isBlank(token)) {
			logger.error("Token di accesso non presente.");
//			throw new AccessoNegatoAgibilitaException("Token di accesso non presente.");
			throw new Exception("Token di accesso non presente.");
		} else {
			String decoded = CryptoUtility.decrypt64FromString(token);
			
			
			
			Long timeGenLink = Long.parseLong(decoded);
			Long currentTime = System.currentTimeMillis();
			long hours = TimeUnit.MILLISECONDS.toHours(currentTime - timeGenLink);
			if (hours > retrieveValidateHours()) {
				logger.error("Il link risulta scaduto.");
//				throw new AccessoNegatoAgibilitaException("Il link risulta scaduto.");
				throw new Exception("Il link risulta scaduto.");
			}
		}
		logger.debug("Token valido");
	
	}
	
	
	/**Associa la mail inviata all id_ud ritornato dalla store per la protocollazione*/
	private void associaMailToIdud(String idMail, BigDecimal idUd) throws Exception {
		
		final DmpkIntMgoEmailCollegaregtoemailBean lDmpkIntMgoEmailCollegaregtoemailBean = new DmpkIntMgoEmailCollegaregtoemailBean();
		lDmpkIntMgoEmailCollegaregtoemailBean.setFlgautocommitin(1);

		lDmpkIntMgoEmailCollegaregtoemailBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		
		//TODO: capire se conviene mettere nella Request direttamente i campi in BigDecimal
		lDmpkIntMgoEmailCollegaregtoemailBean.setIdemailin(idMail);
		lDmpkIntMgoEmailCollegaregtoemailBean.setIdudin(idUd);
		
		Collegaregtoemail service = new Collegaregtoemail();
		StoreResultBean<DmpkIntMgoEmailCollegaregtoemailBean> storeResultBean = service.execute(lAurigaLoginBean, lDmpkIntMgoEmailCollegaregtoemailBean);

		
	}
	
	/**
	 * 
	 * @param ricercaAgibilitaRequest
	 * @throws Exception
	 * 
	 * metodo creato per testare una richiesta di invio con dati cablati. Da eliminare una volta che il servizio funziona a regime.
	 */
	private void testInvio(RicercaAgibilitaRequest ricercaAgibilitaRequest) throws Exception {
		//-----------------
		String mittente = "test.01@postacert.comune.milano.it";
		String corpo = "Che bel test";
		String oggetto = "TEST INVIO";
		
		String uri_1 = "[FS@AURIGAREP]/2019/11/4/1/20191104125618861147637966698089399";
		String nome_1 = "file1.pdf";
		String mimetype_1 = "application/pdf";
		int dimension_1 = 110592;
		
		String uri_2 = "[FS@AURIGAREP]/2019/11/4/1/20191104125622634161732208409973349";
		String nome_2 = "file2.pdf";
		String mimetype_2 = "application/pdf";
		int dimension_2 = 94208;
		//-----------------
		
		RicercaAgibilitaStoreOutBean ricercaAgibilitaStoreOutBean =  new RicercaAgibilitaStoreOutBean();
		ricercaAgibilitaStoreOutBean.setAccountMittenteMail(mittente);
		ricercaAgibilitaStoreOutBean.setCorpoMail(corpo);
		ricercaAgibilitaStoreOutBean.setOggettoMail(oggetto);
		
		
		List<FileAgibilitaOutBean> pListaAllegatiMail = new ArrayList<FileAgibilitaOutBean>();
		
		FileAgibilitaOutBean bean1 = new FileAgibilitaOutBean();
		bean1.setUri(uri_1);
		bean1.setNomeFile(nome_1);
		bean1.setMimetype(mimetype_1);
		bean1.setDimensione(new BigDecimal(dimension_1));
		
		FileAgibilitaOutBean bean2 = new FileAgibilitaOutBean();
		bean2.setUri(uri_2);
		bean2.setNomeFile(nome_2);
		bean2.setMimetype(mimetype_2);
		bean2.setDimensione(new BigDecimal(dimension_2));
		
		pListaAllegatiMail.add(bean1);
		pListaAllegatiMail.add(bean2);
		
		invioMailModello(ricercaAgibilitaStoreOutBean, pListaAllegatiMail, "Modello.pdf");
	}
	
	// Metodo che converte il bean in input al servizio in xml
	private String convertBeanToXml(Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(object, sw);
			// Verify XML Content
			String xmlContent = sw.toString();

			return xmlContent;
		} catch (Exception e) {
			logger.error("Errore nella conversione dell' Bean di input in Xml: " + e.getMessage(),e);
			throw new Exception("Errore nella conversione dell' Bean di input");
		}
	}
	
	
	private void timbraFileAgibilita(RicercaAgibilitaStoreOutBean lRicercaAgibilitaStoreOutBean) {

		for (FileAgibilitaOutBean fileDaTimbrare : lRicercaAgibilitaStoreOutBean.getFilesAgibilita()) {
			if (fileDaTimbrare.getIdUD() != null) {
				try {
					//recupero la segnatura del timbro da apporre sul file
					DmpkRegistrazionedocGettimbrodigregBean result = getSegnaturaStore(fileDaTimbrare.getIdUD());
					if (result != null) {
						String contenutoBarcode = result.getContenutobarcodeout();
						String testoInChiaroBarcode = result.getTestoinchiaroout();
						String nomeFile = fileDaTimbrare.getNomeFile();

						File fileExtractDaTimbrare = DocumentStorage.extract(fileDaTimbrare.getUri(),
								lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
						
						//se ho file immagini li converto in pdf per poterli timbrare
						if(fileDaTimbrare.getMimetype().startsWith("image")) {
							InputStream targetStream = TimbraUtil.converti(fileExtractDaTimbrare,fileDaTimbrare.getNomeFile());
							fileExtractDaTimbrare = File.createTempFile("tmp", ".pdf");
							FileUtils.copyInputStreamToFile(targetStream, fileExtractDaTimbrare);
							
							nomeFile = FilenameUtils.getBaseName(nomeFile) + ".pdf";
						}

						File fileAgibilitaTimbrato = timbraFile(fileExtractDaTimbrare, testoInChiaroBarcode, contenutoBarcode,
								lRicercaAgibilitaWSConfigBean.getOpzioniTimbro());

						String uriFileTimbrato = DocumentStorage.store(fileAgibilitaTimbrato, null);

						fileDaTimbrare.setUri(uriFileTimbrato);
						fileDaTimbrare.setNomeFile(nomeFile);
					}
				} catch (Exception ex) {
					logger.error("Errore durante l'apposizione del timbro al file: " + fileDaTimbrare.getNomeFile() + " error: " + ex.getMessage() + "\n" + ex);
				}
			}
		}

	}



	private DmpkRegistrazionedocGettimbrodigregBean getSegnaturaStore(String idUD) throws Exception {
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		input.setIdudio(new BigDecimal(idUD));
		input.setFinalitain("CONFORMITA_ORIG_CARTACEO");

		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(null, lAurigaLoginBean, input);

		if (result.isInError()) {
			logger.error("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
			throw new Exception("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
		}	
		
		return result.getResultBean();
	}


	//Recupero le impronte dei file e li converto in immagini timbro dainniettare poi nel modello
	private static void inserisciTimbro(Map<String, Object> mappaValori) throws Exception {
		List<Map<String, Object>> mappaValoriFileAgibilita = (List<Map<String, Object>>) mappaValori.get("FILE_AGIBILITA");
		
		try {
			for(Map<String, Object> rigaFile : mappaValoriFileAgibilita) {
				String impronta = (String) rigaFile.get("col3");
				
				Object timbroImage = getImageTimbro(impronta);
				
				rigaFile.put("col3", timbroImage);			
			}
		}catch(Exception e) {
			logger.error("Errore durante la conversione dell'impronta nel timbro: " + e.getMessage(),e);
//			throw new Exception("Errore durante la conversione dell'impronta nel timbro");
		}
	}

	private static Object getImageTimbro(String impronta) throws Exception{
		Object timbroImage = null;
		
		String tipoBarcode = StringUtils.isNotBlank(lRicercaAgibilitaWSConfigBean.getTipoBarcodeModello()) ? lRicercaAgibilitaWSConfigBean.getTipoBarcodeModello() : "QRCODE";
		ImpostazioniBarcodeBean impostazioniBarcodeBean = BarcodeUtility.getImpostazioniImmagineBarCode(tipoBarcode);
		timbroImage = BarcodeUtility.getImageProvider(impronta, impostazioniBarcodeBean);
		
		return timbroImage;
	}
	
	
	private long retrieveValidateHours() {
		// TODO: da vedere come/dove recuperare. Valore che mi indica la validita del token espressa in ore
		return DEFAULT_VALIDATE_HOURS;
	}
	
}