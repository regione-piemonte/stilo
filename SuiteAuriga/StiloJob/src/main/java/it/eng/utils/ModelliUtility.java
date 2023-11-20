/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.office.OfficeBodyElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeMasterStylesElement;
import org.odftoolkit.odfdom.dom.element.text.TextSectionElement;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfPackage;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.itext.extension.IPdfWriterConfiguration;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;


import it.eng.auriga.compiler.docx.CompositeCompiler;
import it.eng.auriga.compiler.docx.DocxCompiler;
import it.eng.auriga.compiler.docx.FormDocxCompiler;
import it.eng.auriga.compiler.odt.OdtCompiler;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.Marktokenusage;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.store.impl.GetdatixgendamodelloImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.bean.DocumentConfiguration;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.RecuperoFile;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.storage.DocumentStorage;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.InputConversionType;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.ResponsePdfConvResultType;
import it.eng.job.SpringHelper;
import it.eng.job.registri.StampaRegProt;
import it.eng.services.fileop.FileOpUtility;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.barcode.BarcodeUtility;
import it.eng.utility.barcode.ImpostazioniBarcodeBean;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.springBeanWrapper.bean.SpringBeanWrapperConfigBean;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import net.sf.jooreports.templates.image.RenderedImageSource;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import it.eng.fileOperation.clientws.FileOperationRequest;
import it.eng.fileOperation.clientws.FileOperationWS;

/**
 * Centralizza la generazione di template con dati iniettati
 *
 */
public class ModelliUtility extends BeanPropertyUtility {

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ModelliUtility.class);
    
	

	public static final String HTML_VALUE_PREFIX = "|*|HTML|*|";
	public static final String TAG_APERTURA_DATI_SENSIBILI = "<s>";
	public static final String TAG_CHIUSURA_DATI_SENSIBILI = "</s>";
	public static final String OMISSIS = "<i>omissis</i>";
	public static final String HTML_SPACE = "&nbsp;";
    
	
    private static ModelliUtility instance;
    
	
	public static ModelliUtility getInstance() {
		if (instance == null) {
			instance = new ModelliUtility();
		}
		return instance;
	}

	/**
	 * Elenca i tipi di modello compatibili
	 */
	public enum TipoModello {

		ODT_FREEMARKERS, DOCX, DOCX_FORM, ODT, COMPOSITE
	}

	public File generaRelataAtto(AurigaLoginBean logInBean, RelataAttoParamBean bean) throws Exception {
        
		logger.info(" generaRelataAtto - INFO ");
		File filledTemplate = null;
		File templateOdt = null;

		String idUd = bean.getIdUd();
		String nomeModello = bean.getNomeModello();
		String uriModello = bean.getUriModello();
		String tipoModello = bean.getTipoModello();
		logger.info("tipoModello: " + tipoModello);
		
		try {
			String sezionCacheModello = getDatiModello(logInBean, idUd, nomeModello);
			logger.info("sezionCacheModello: " + sezionCacheModello);
			sezionCacheModello=sezionCacheModello.replaceAll("ISO-8859-1","UTF-8");
			logger.info("sezionCacheModelloAggiornato: " + sezionCacheModello);
			if (uriModello != null && !"".equalsIgnoreCase(uriModello)) {
                
				logger.info("uriModello: " + uriModello);
				
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriModello);

				FileExtractedOut lFileExtractedOut = extractFile(logInBean,
						lFileExtractedIn);
				logger.info("lFileExtractedOut: " + lFileExtractedOut.getExtracted().getAbsolutePath());
				
				if (lFileExtractedOut.getExtracted() == null || lFileExtractedOut.getExtracted().length() == 0) {
					logger.error("Modello non trovato o di dimensione nulla");
				} else {
					templateOdt = lFileExtractedOut.getExtracted();
					logger.info("templateOdt: " + templateOdt.getAbsolutePath());
				}
				
				//Per TEST va cancellato
				//templateOdt = new File(bean.getUriModello());
                
				
				
				if (tipoModello != null) {
					try {
						switch (tipoModello) {

						case "odt_con_freemarkers":
							
							Class klass = DocumentTemplateFactory.class;
							URL location = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
							logger.info("location file: "+location.getFile());
							Class klass1 = DocumentTemplate.class;
							URL location1 = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
							logger.info("location file: "+location1.getFile());
							DocumentTemplate template = null;
							logger.info("DocumentTemplate "+location1.getFile());
							try {
								
								DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
								
								logger.info(" documentTemplateFactory ");
								InputStream templateInputStream = FileUtils.openInputStream(templateOdt);
								logger.info("templateInputStream: " + templateInputStream.toString());
								template = documentTemplateFactory.getTemplate(templateInputStream);
								logger.info("template: " + template.toString());
								
								
							} catch (Exception e) {
								logger.error("Exception: "+e.getMessage());
							}
							
							
							Map<String, Object> mappaValori = createMapToFillTemplateFromSezioneCache(sezionCacheModello, true);
							logger.info("mappaValori: " + mappaValori.size());
							// converto le impronte dei file ritornati dalla store in timbri
							// inserisciTimbro(mappaValori, tipoTimbro);

							File templateOdtWithValues = File.createTempFile("temp", ".odt");
							
							FileOutputStream odtOutputStream = new FileOutputStream(templateOdtWithValues);
							template.createDocument(mappaValori, odtOutputStream);
							logger.info("template: " + template.getConfigurations().size());
							// CONTROLLO SE CI SONO VARIABILI HTML DA INNIETTARE
							Map<String, Object> htmlSectionsModel = new HashMap<String, Object>();
							for (String nomeVariabile : mappaValori.keySet()) {
								if (mappaValori.get(nomeVariabile) instanceof String) {
									String value = (String) mappaValori.get(nomeVariabile);
									String html = "";
									String regex = "<([A-Za-z][A-Za-z0-9]*)\\b[^>]*>(.*?)</\\1>";
									boolean isHtml = false;
									if (value != null && value.startsWith(HTML_VALUE_PREFIX)) {
										html = value.substring(HTML_VALUE_PREFIX.length());
										isHtml = true;
									} else if (StringUtils.isNotBlank(value)
											&& value.replaceAll("\n", "").matches(regex)) {
										html = value;
										isHtml = true;
									}
									if (isHtml) {
										// TODO Perchè viene fatto l'unescapeHtml?
										// intanto lo commento e faccio solo i replaceAll, perchè mi crea problemi
										// quando ho dei caratteri speciali: &amp; ecc...
										// html =
										// StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(html).replaceAll("\n",
										// "").replaceAll("\t", ""));
										html = html.replaceAll("\n", "").replaceAll("\t", "");
										// sostituisco tutti i dati sensibili (quelli tra <s> e </s>)
										html = html.replaceAll("<br />", "#ESCAPE_BR#");
										html = html.replaceAll("<br/>", "#ESCAPE_BR#");
										html = html.replaceAll("<br>", "#ESCAPE_BR#");

										String htmlOmissis = replaceOmissisInHtml(html);
										htmlSectionsModel.put(nomeVariabile, htmlOmissis);

										mappaValori.put(nomeVariabile, "");
									}
								}
							}

							// Inietto l'html dei campi CKEditor nelle sezioni del modello odt (prima
							// bisogna aggiungere le dipendenze di xdocreport nel pom)
							if (htmlSectionsModel != null && !htmlSectionsModel.isEmpty()) {
								// Ripristino le section destinate a contenere i valori ckeditor, rimettendo nel
								// file ottenuto dalla prima iniezione quelle presenti nel modello.
								// Lo devo fare perchè la prima iniezione ha iniettato anche i valori ckeditor,
								// ma dovendo rifare l'inizione devo ripristinare le section del modello
								for (String nomeVariabile : htmlSectionsModel.keySet()) {
									mergeHtmlSections(nomeVariabile, templateOdt, templateOdtWithValues);
								}

								// TODO PER AGGIUNGERE IL CONTENUTO NELLE SEZIONI HTML
								IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
										new FileInputStream(templateOdtWithValues), TemplateEngineKind.Freemarker);

								// 2) Create fields metadata to manage text styling
								FieldsMetadata metadata = report.createFieldsMetadata();
								for (String nomeVariabile : htmlSectionsModel.keySet()) {
									metadata.addFieldAsTextStyling(nomeVariabile, SyntaxKind.Html, true);
								}

								// 3) Create context Java model
								IContext context = report.createContext();
								context.putMap(htmlSectionsModel);

								// 4) Generate report by merging Java model with the ODT
								odtOutputStream = new FileOutputStream(templateOdtWithValues);

								report.process(context, odtOutputStream);
							}

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
						logger.info("filledTemplate: " + filledTemplate.getAbsolutePath());
					} catch (Throwable e) {
						logger.error("Throwable: "+e.getMessage());
						throw new Exception(
								"Durante l'iniezione dei dati si e verificato il seguente errore: " + e.getMessage(),
								e);
					}

				}
			} else {
				throw new Exception("URI del modello associato al task inesistente");
			}
			if (filledTemplate == null) {
				throw new Exception("Si e verificato un errore durante la generazione del modello");
			}

		} catch (Exception e) {
			throw new Exception("Errore durante la creazione del modello: " + e.getMessage(), e);
		}
		logger.info("relataPdf FINE");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		
		logger.info("nomeModello: "+nomeModello);
		File relataPdf = File.createTempFile("relata_di_pubblicazione_"+simpleDateFormat.format(date), ".pdf");
		logger.info("relataPdf: "+relataPdf.getAbsolutePath());
/*		File filledTemplate = new File("C:\\\\Downloads\\\\EXPORT_CODA\\\\Rilascio_batch\\\\ComuneTorino-Modellorelatadipubblicazione.odt");
    	File relataPdf = new File("C:\\Downloads\\EXPORT_CODA\\Rilascio_batch\\ComuneTorino-Modellorelatadipubblicazione.pdf");*/
    	
    	relataPdf=ConvertODTBigToPDF.getInstance().convert(filledTemplate, relataPdf);
    	logger.info("relataPdfconvi: "+relataPdf.getAbsolutePath());
		// converto il modello in pdf
		//relataPdf = convertToPdf(filledTemplate, nomeModello);
		
		
		logger.info("relataPdf "+relataPdf.getAbsolutePath());
		return relataPdf;
	}

	private File convertToPdf(File modelloWithValues, String nomeModello) throws Exception {
		logger.info("Converto il modello in PDF INIZIO");
		File pdfDaModello = null;
		try {
			InputStream targetStream = convertiInPdf(modelloWithValues, nomeModello);
			pdfDaModello = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(targetStream, pdfDaModello);
		} catch (Exception e) {
			logger.error("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
			throw new Exception("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
		}
		logger.info("Converto il modello in PDF FINE");
		return pdfDaModello;
	}

	

	public String getDatiModello(AurigaLoginBean logInBean, String idUD, String nomeModello) throws Exception {
		/* RECUPERO I DATI (SEZIONE CACHE DA INNIETTARE NEL MODELLO) */
        try {
        logger.info(" getDatiModello - INFO ");
        logger.info(" logInBean.getToken() " +logInBean.getToken());
        logger.info(" idUD " +idUD);
        logger.info(" nomeModello " +nomeModello);
        logger.info(" logInBean.getIdUserLavoro() " +logInBean.getIdUserLavoro());
		DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lGetdatixgendamodelloInput.setCodidconnectiontokenin(logInBean.getToken());
		lGetdatixgendamodelloInput.setIdobjrifin(idUD);
		lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		lGetdatixgendamodelloInput.setIduserlavoroin(new BigDecimal(logInBean.getIdUserLavoro()));
		logger.info(" nomeModello " +nomeModello);
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = execute(logInBean, lGetdatixgendamodelloInput);
		logger.info(" lGetdatixgendamodelloOutput " +lGetdatixgendamodelloOutput);
		logger.info(" lGetdatixgendamodelloOutput " +lGetdatixgendamodelloOutput.getDefaultMessage());
		if (lGetdatixgendamodelloOutput.isInError()) {
			logger.error("Errore durante il recupero della sezione cahce da inniettare nel modello: "+lGetdatixgendamodelloOutput.getErrorContext());
			logger.error("Errore durante il recupero della sezione cahce da inniettare nel modello: "+lGetdatixgendamodelloOutput.getErrorCode());
			logger.error("Errore durante il recupero della sezione cahce da inniettare nel modello: "+lGetdatixgendamodelloOutput.getResultBean().getErrmsgout());
			throw new Exception("ID_UD "+idUD+" Errore durante il recupero della sezione cahce da inniettare nel modello: "+ lGetdatixgendamodelloOutput.getErrorContext());
		}
		logger.info("lGetdatixgendamodello "+lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout());
		logger.info(" getDatiModello - FINE ");
		return lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
        } catch (Exception e) {
			logger.error("Exception getDatiModello: "+e.getMessage());
			return e.getMessage();
		}
        
        

	}
	
	public StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> execute( AurigaLoginBean lLoginBean,DmpkModelliDocGetdatixgendamodelloBean pBean) throws Exception{
		   final GetdatixgendamodelloImpl lGetdatixgendamodello = new GetdatixgendamodelloImpl();
		   logger.info(" execute DmpkModelliDocGetdatixgendamodelloBean");
		   try
		   {
		   pBean.setCodidconnectiontokenin(lLoginBean.getToken());
		   BigDecimal lBigDecimal = new BigDecimal(lLoginBean.getIdUserLavoro());
		   pBean.setIduserlavoroin(lBigDecimal);
		   SubjectBean subject =  SubjectUtil.subject.get();
		   subject.setIdDominio(lLoginBean.getSchema());
		   subject.setUuidtransaction(lLoginBean.getUuid());
		   SubjectUtil.subject.set(subject);
		   Session session = null;
		   lGetdatixgendamodello.setBean(pBean);
		   final SpringBeanWrapperConfigBean springBeanWrapperConfig = new SpringBeanWrapperConfigBean();
		   
			springBeanWrapperConfig.setEnableSpringBeanWrapper(true);
			
			try {
				LoginService lLoginService = new LoginService();
				lLoginService.login(lLoginBean);
				session = it.eng.database.utility.HibernateUtil.begin("JobBatch");
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lGetdatixgendamodello.execute(paramConnection);
					}
				});
				StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> result = new StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean>();
				AnalyzeResult.analyze(pBean, result);
				result.setResultBean(pBean);
				
				return result;
			}catch(Exception e){
				logger.error("Exception DmpkModelliDocGetdatixgendamodelloBean "+e.getMessage());
				if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().equals("Chiusura forzata")) throw new Exception("Chiusura forzata");
				else throw e;
			}finally{
				it.eng.database.utility.HibernateUtil.release(session);
			}
		   }catch(Exception e){
				logger.error("Exception execute DmpkModelliDocGetdatixgendamodelloBean "+e.getMessage());
				if (e.getCause() != null && e.getCause().getMessage() != null && e.getCause().getMessage().equals("Chiusura forzata")) throw new Exception("Chiusura forzata");
				else throw e;
		   }
		}

	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml
	 *            Sezione cache contenente i dati da iniettare
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache(String xml) throws Exception {
		return createMapToFillTemplateFromSezioneCache(xml, false);
	}

	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml
	 *            Sezione cache contenente i dati da iniettare
	 * @param creaMappaPerContext
	 *            se è true le colonne hanno indice col1, col2, col3 invece di 1, 2,
	 *            3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache(String xml, boolean creaMappaPerContext)
			throws Exception {
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);
		document.getDocumentElement().normalize();
		Node sezioneCacheNode = document.getFirstChild();
		NodeList figliSezioneCache = sezioneCacheNode.getChildNodes();
		return createMapToFillTemplateFromNodeList(figliSezioneCache, creaMappaPerContext);
	}

	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param listaNodi
	 *            Lista dei nodi Varibile della sezione cache
	 * @param creaMappaPerContext
	 *            se è true le colonne hanno indice col1, col2, col3 invece di 1, 2,
	 *            3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 */
	private static Map<String, Object> createMapToFillTemplateFromNodeList(NodeList listaNodi,
			boolean creaMappaPerContext) {
		Map<String, Object> mappaToReturn = new LinkedHashMap<String, Object>();
		// Scorro tutti i nodi <Variaible>
		for (int i = 0; i < listaNodi.getLength(); i++) {
			Node node = listaNodi.item(i);
			// Estraggo il nome della variabile
			String nomeVariabile = togliSuffisso(getChildNodeValue(node, "Nome"));

			// Estraggo il tipo della variabile
			String tipoVariabile = getTipoVariabileSezioneCache(node);
			if ("ValoreSemplice".equalsIgnoreCase(tipoVariabile)) {
				// E' una variabile semplice, estraggo il valore e lo inserisco nella mappa
				String valoreVariabile = getChildNodeValue(node, "ValoreSemplice");
				if (creaMappaPerContext) {
					mappaToReturn.put(nomeVariabile, (getTextModelValue(valoreVariabile)));
				} else {
					mappaToReturn.put(nomeVariabile, valoreVariabile);
				}
			} else if ("Lista".equalsIgnoreCase(tipoVariabile)) {
				// E' una variabile lista, genero la lista da iniettare
				List<Object> listaValori = getListaSezioneCache(node, nomeVariabile, creaMappaPerContext);
				mappaToReturn.put(nomeVariabile, listaValori);
			}
		}
		return mappaToReturn;
	}

	/**
	 * Restituisci il tipo di variabile di un nodo Variabile
	 * 
	 * @param nodo
	 *            Il nodo Variabile da cui estrarre il tipo
	 * @return Il tipo del nodo Variabile
	 */
	private static String getTipoVariabileSezioneCache(Node nodo) {
		NodeList figli = nodo.getChildNodes();
		for (int i = 0; i < figli.getLength(); i++) {
			if ("ValoreSemplice".equalsIgnoreCase(figli.item(i).getNodeName())) {
				return "ValoreSemplice";
			} else if ("Lista".equalsIgnoreCase(figli.item(i).getNodeName())) {
				return "Lista";
			}
		}
		return "";
	}

	/**
	 * Restituisce il node value di un nodo figlio
	 * 
	 * @param nodo
	 *            Il nodo da esaminare
	 * @param childName
	 *            Il nome del figlio da cui estrarre il node value
	 * @return Il node value del figlio childName del node node
	 */
	private static String getChildNodeValue(Node nodo, String childName) {
		// Estraggo tutti i figli
		NodeList figli = nodo.getChildNodes();
		// Scorro i figli
		for (int i = 0; i < figli.getLength(); i++) {
			// Verifico se è il figlio cercato
			if (childName.equalsIgnoreCase(figli.item(i).getNodeName())) {
				if (figli.item(i).getFirstChild() != null) {
					// Estraggo il node value del nodo
					return figli.item(i).getFirstChild().getNodeValue();
				}
			}
		}
		return null;
	}

	/**
	 * Trasforma un nodo Variabile di tipo Lista nella struttura List da iniettare
	 * nel modello
	 * 
	 * @param nodo
	 *            Nodo Variabile di tipo lista
	 * @param modello
	 *            Bean del modello su cui iniettare i dati
	 * @param nomeAttributoStrutturato
	 *            Nome dell'attributo lista
	 * @param creaMappaPerContext
	 *            se è true le colonne hanno indice col1, col2, col3 invece di 1, 2,
	 *            3
	 * @return List che rapprensta i dati da iniettare
	 */
	private static List<Object> getListaSezioneCache(Node nodo, String nomeAttributoStrutturato,
			boolean creaMappaPerContext) {
		List<Object> listaToReturn = new LinkedList<Object>();
		org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ModelliUtility.class);
		// Verifico che il nodo abbia dei figli
		if (nodo.getChildNodes() != null && nodo.getChildNodes().getLength() >= 1) {
			// Estraggo il nodo Lista
			Node nodoLista = getChildNodeByName(nodo, "Lista");
			if (nodoLista != null) {
				// Estraggo Tutte le Rige della Lista
				NodeList lista = nodoLista.getChildNodes();
				if (lista != null) {
					if (lista.getLength() > 0) {
						for (int i = 0; i < lista.getLength(); i++) {
							if (lista.item(i) != null) {
								// La lista è composta da righe
								if ("Riga".equalsIgnoreCase(lista.item(i).getNodeName())) {
									// Per ogni riga creo la mappa dei valori Riga
									Map<String, Object> mappaValoriRiga = new LinkedHashMap<String, Object>();
									Node riga = lista.item(i);
									// Inserisco nella mappa tutti gli attributi della riga
									NodeList listaColonne = riga.getChildNodes();
									if (listaColonne != null && listaColonne.getLength() > 0) {
										for (int indiceColonna = 0; indiceColonna < listaColonne
												.getLength(); indiceColonna++) {
											Node nodoColonna = listaColonne.item(indiceColonna);
											String numeroColonna = getAttributeValue(nodoColonna, "Nro");
											String valoreColonna = nodoColonna.getFirstChild() != null
													? nodoColonna.getFirstChild().getNodeValue()
													: null;
											if (numeroColonna != null && !"".equalsIgnoreCase(numeroColonna)) {
												if (creaMappaPerContext) {
													if (numeroColonna.equals("3"))
													{
														ImpostazioniBarcodeBean impostazioniBarcodeBean = getImpostazioniImmagineBarCode("QRCODE");
														try {
															Object valoreVariabileColonna  = BarcodeUtility.getImageProvider(valoreColonna, impostazioniBarcodeBean);
															mappaValoriRiga.put("col" + numeroColonna,
																	valoreVariabileColonna);
														
														} catch (BadElementException e) {
															log.error("BadElementException : "+e.getMessage());
														} catch (UnsupportedEncodingException e) {
															log.error("UnsupportedEncodingException : "+e.getMessage());
														}
														
													}
													else
													{	
													mappaValoriRiga.put("col" + numeroColonna,
															getTextModelValue(valoreColonna));
													}
												} else {
													mappaValoriRiga.put(numeroColonna, valoreColonna);
												}
											}
										}
										listaToReturn.add(mappaValoriRiga);
									}
								}
								// La lista è composta da valori semplici
								else if ("ValoreSemplice".equalsIgnoreCase(lista.item(i).getNodeName())) {
									if (lista.item(i).getNodeType() == 1) {
										if (creaMappaPerContext) {
											listaToReturn.add(getTextModelValue(lista.item(i).getTextContent()));
										} else {
											listaToReturn.add(lista.item(i).getTextContent());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return listaToReturn;
	}

	/**
	 * Retituisce un nodo figlio con un determinato node name
	 * 
	 * @param nodo
	 *            Il nodo su cui ricercare il figlio
	 * @param nodeName
	 *            Il node name del figlio da ricercare
	 * @return Il nodo figlio con il node name ricercato
	 */
	private static Node getChildNodeByName(Node nodo, String nodeName) {
		if (nodo.getChildNodes() != null) {
			for (int i = 0; i < nodo.getChildNodes().getLength(); i++) {
				if (nodo.getChildNodes().item(i) != null
						&& nodeName.equalsIgnoreCase(nodo.getChildNodes().item(i).getNodeName())) {
					return nodo.getChildNodes().item(i);
				}
			}
		}
		return null;
	}

	/**
	 * Restitituisce il valore di un attributo del nodo
	 * 
	 * @param nodo
	 *            Il nodo da cui estrarre lìattributo
	 * @param attributeName
	 *            Il nome dell'attributo da estrarre
	 * @return Il valore dell'attributo cercato
	 */
	private static String getAttributeValue(Node nodo, String attributeName) {
		if (nodo != null && nodo.getAttributes() != null && nodo.getAttributes().getNamedItem(attributeName) != null) {
			return nodo.getAttributes().getNamedItem(attributeName).getNodeValue();
		}
		return null;
	}

	/**
	 * Metodo che elimina il suffisso _Doc o _Ud dal una string
	 * 
	 * @param lString
	 *            La stringa da elaborare
	 * @return La stringa ricevuta in ingresso privata del suffisso _Doc o _Ud
	 */
	private static String togliSuffisso(String lString) {
		if (lString != null && !"".equalsIgnoreCase(lString)) {
			if (lString.endsWith("_Ud")) {
				return lString.substring(0, lString.lastIndexOf("_Ud"));
			}
			if (lString.endsWith("_Doc")) {
				return lString.substring(0, lString.lastIndexOf("_Doc"));
			}
		}
		return lString;
	}

	public static String getTextModelValue(Object value) {
		return value != null ? String.valueOf(value) : " ";
	}

	/**
	 * Riceve un input stream di un file che non Ã¨ un pdf e restituisce un pdf
	 * 
	 * @param lInputStream
	 * @return
	 * @throws Exception
	 */
	public InputStream convertiInPdf(File file, String displayFileName) throws Exception {

		FileOperationResponse lFileOperationResponse = null;
		logger.info("convertiInPdf INIZIO");
		try {

			InputFileOperationType lInputFileOperationType = FileOpUtility.buildInputFileOperationType(file,
					displayFileName);

			Operations operations = new Operations();

			InputConversionType lInputConversionType = new InputConversionType();
			lInputConversionType.setPdfA(false);
			operations.getOperation().add(lInputConversionType);

			lFileOperationResponse = callFileOperation(lInputFileOperationType, operations);
        
		
			
		} catch (Exception e) {
			logger.error(String.format("Errore in timbra() del file %s: %s", displayFileName, e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			logger.error("lFileOperationResponse == null ");
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			logger.info("Risposta dal servizio di FileOperation");
			logger.info("lFileOperationResponse "+lFileOperationResponse.getFileoperationResponse().toString());
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();
			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				if (lAbstractResponseOperationType instanceof ResponsePdfConvResultType) {
					ResponsePdfConvResultType lResponsePdfConvResultType = (ResponsePdfConvResultType) lAbstractResponseOperationType;
					if (lResponsePdfConvResultType.getErrorsMessage() != null
							&& lResponsePdfConvResultType.getErrorsMessage().getErrMessage() != null
							&& lResponsePdfConvResultType.getErrorsMessage().getErrMessage().size() > 0) {
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						StringBuffer error = new StringBuffer();
						boolean first = true;
						for (MessageType message : errors) {
							logger.error(message.getDescription());
							if (first) {
								first = false;
							} else {
								error.append(";");
							}
							error.append(message.getDescription());
						}
						throw new Exception(error.toString());
					} else {
						return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();
					}
				}
			}
		}

		return null;
	}
    
public FileOperationResponse callFileOperation(InputFileOperationType inputFileOperationType, Operations operations) throws Exception {
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringHelper.getMainApplicationContext().getBean("DocumentConfiguration");
		
		FileOperationResponse lFileOperationResponse = null;

		try {
			URL url = new URL(lDocumentConfiguration.getOperationWsAddress());
			QName qname = new QName("it.eng.fileoperation.ws", "FOImplService");
			Service service = Service.create(url, qname);
			FileOperationWS fileOperationWS = service.getPort(FileOperationWS.class);
			//((org.apache.axis.client.Stub) fileOperationWS).setTimeout(lDocumentConfiguration.getTimeout());
			
			int timeout = lDocumentConfiguration != null ? lDocumentConfiguration.getTimeout() : 300000;
			setTimeout((BindingProvider) fileOperationWS, timeout);
			
			// enable mtom on client
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) fileOperationWS).getBinding();
			binding.setMTOMEnabled(true);

			FileOperationRequest lFileOperationRequest = new FileOperationRequest();
			
			lFileOperationRequest.setFileOperationInput(inputFileOperationType);

			lFileOperationRequest.setOperations(operations);

			lFileOperationResponse = fileOperationWS.execute(lFileOperationRequest);
		} catch (Exception e) {
			throw new Exception(String.format("Errore nella chiamata a FileOperation: %s", e.getMessage()));
		}

		if (lFileOperationResponse == null) {
			// C'Ã¨ stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			
			logger.debug("Risposta dal servizio di FileOperation");			
			return lFileOperationResponse;
									
		} else if (lFileOperationResponse.getGenericError() != null) {
			if (lFileOperationResponse.getGenericError().getErrorMessage() != null) {
				String errors = null;				
				for (String err : lFileOperationResponse.getGenericError().getErrorMessage()) {
					if(errors == null) { 
						errors = err;
					} else {
						errors += "; " + err;
					}					
				}
				throw new Exception(String.format("Errore generico nella chiamata a FileOperation: %s", errors));
			}
		} else {
			throw new Exception("Errore generico: nessuna risposta da FileOperation");
		}
		
		return null;
	}

public static void setTimeout(BindingProvider port, int timeout) {
	
	if (port != null) {			
		
		port.getRequestContext().put("com.sun.xml.ws.developer.JAXWSProperties.CONNECT_TIMEOUT", timeout);
		port.getRequestContext().put("com.sun.xml.ws.connect.timeout", timeout);
		port.getRequestContext().put("com.sun.xml.ws.internal.connect.timeout", timeout);		
		port.getRequestContext().put("com.sun.xml.ws.request.timeout", timeout);
		port.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", timeout);	
		
		// We don't want to use proprietary Sun code
		// port.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, timeout);
		// port.getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, timeout);
		
		// Properties for JBoss implementation of JAX-WS
		//port.getRequestContext().put("javax.xml.ws.client.connectionTimeout", timeout);
		//port.getRequestContext().put("javax.xml.ws.client.receiveTimeout", timeout); 										
	}
}

	
	public static File generaModello(AurigaLoginBean logInBean, String idUd, String nomeModello, String uriModello,
			String tipoModello, String sezionCacheModello) throws Exception {

		File filledTemplate = null;
		File templateOdt;

		try {

			if (uriModello != null && !"".equalsIgnoreCase(uriModello)) {

				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(uriModello);

				FileExtractedOut lFileExtractedOut = lRecuperoFile.extractfile(new Locale("it"), logInBean,
						lFileExtractedIn);

				if (lFileExtractedOut.getExtracted() == null || lFileExtractedOut.getExtracted().length() == 0) {
					throw new Exception("Modello non trovato o di dimensione nulla");
				} else {
					templateOdt = lFileExtractedOut.getExtracted();
				}

				if (tipoModello != null) {
					try {
						switch (tipoModello) {

						case "odt_con_freemarkers":
							DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
							InputStream templateInputStream = FileUtils.openInputStream(templateOdt);
							DocumentTemplate template = documentTemplateFactory.getTemplate(templateInputStream);

							Map<String, Object> mappaValori = ModelliUtility
									.createMapToFillTemplateFromSezioneCache(sezionCacheModello, true);

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
						throw new Exception(
								"Durante l'iniezione dei dati si e verificato il seguente errore: " + e.getMessage(),
								e);
					}

				}
			} else {
				throw new Exception("URI del modello associato al task inesistente");
			}
			if (filledTemplate == null) {
				throw new Exception("Si e verificato un errore durante la generazione del modello");
			}

		} catch (Exception e) {
			throw new Exception("Errore durante la creazione del modello: " + e.getMessage(), e);
		}

		return filledTemplate;
	}

	public static String replaceOmissisInHtml(String html) {
		String htmlOmissis = "";
		int pos = 0;
		while (pos < html.length()) {
			int startDatiSensibili = html.indexOf(TAG_APERTURA_DATI_SENSIBILI, pos);
			if (startDatiSensibili == -1) {
				htmlOmissis += html.substring(pos);
				break;
			}
			htmlOmissis += html.substring(pos, startDatiSensibili);
			int endDatiSensibili = html.indexOf(TAG_CHIUSURA_DATI_SENSIBILI, startDatiSensibili);
			htmlOmissis += OMISSIS;
			if (endDatiSensibili != -1) {
				pos = endDatiSensibili + TAG_CHIUSURA_DATI_SENSIBILI.length();
			} else {
				pos = html.length();
			}
		}
		htmlOmissis = htmlOmissis.replaceAll(HTML_SPACE, "&#160;");
		return htmlOmissis;
	}

	public static void mergeHtmlSections(String nomeVariabile, File fileOdt, File fileOdtWithValues) {
		OdfPackage odfPackage = null;
		OdfDocument odfDocument = null;
		TextSectionElement sectionOrig = null;
		try {
			odfPackage = OdfPackage.loadPackage(fileOdt);
			odfDocument = OdfTextDocument.loadDocument(odfPackage);
			sectionOrig = (TextSectionElement) getSectionByName(odfDocument, nomeVariabile).cloneNode(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (odfDocument != null) {
				odfDocument.close();
			}
			if (odfPackage != null) {
				odfPackage.close();
			}
		}
		try {
			odfPackage = OdfPackage.loadPackage(fileOdtWithValues);
			odfDocument = OdfTextDocument.loadDocument(odfPackage);
			TextSectionElement section = getSectionByName(odfDocument, nomeVariabile);
			Node importedNode = odfDocument.getContentDom().importNode(sectionOrig, true);
			section.getParentNode().replaceChild(importedNode, section);
			odfDocument.save(fileOdtWithValues);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (odfDocument != null) {
				odfDocument.close();
			}
			if (odfPackage != null) {
				odfPackage.close();
			}
		}
	}

	public static TextSectionElement getSectionByName(OdfDocument odfDocument, String name) {
		TextSectionElement element;
		try {
			OdfElement root = odfDocument.getContentDom().getRootElement();
			OfficeBodyElement officeBody = OdfElement.findFirstChildNode(OfficeBodyElement.class, root);
			XPath xpath = odfDocument.getContentDom().getXPath();
			String xpathValue = ".//text:section[@text:name=\"" + name + "\"]";
			element = (TextSectionElement) xpath.evaluate(xpathValue, officeBody, XPathConstants.NODE);
			if (element != null) {
				return element;
			}
			root = odfDocument.getStylesDom().getRootElement();
			OfficeMasterStylesElement masterStyle = OdfElement.findFirstChildNode(OfficeMasterStylesElement.class,
					root);
			xpath = odfDocument.getStylesDom().getXPath();
			element = (TextSectionElement) xpath.evaluate(".//text:section[@text:name=\"" + name + "\"]", masterStyle,
					XPathConstants.NODE);
			if (element != null) {
				return element;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public FileExtractedOut extractFile(AurigaLoginBean pAurigaLoginBean, FileExtractedIn pFileExtractedIn){
		FileExtractedOut lFileExtractedOut = new FileExtractedOut();
		try {
			lFileExtractedOut.setExtracted(DocumentStorage.extract(pFileExtractedIn.getUri(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio()));
		}
		catch (Exception e) {
			lFileExtractedOut.setErrorInExtract(e.getMessage());
		}
		return lFileExtractedOut;
	}
	private static ImpostazioniBarcodeBean getImpostazioniImmagineBarCode(String tipoBarcode) {
		
		if (StringUtils.isBlank(tipoBarcode)) {
			tipoBarcode = "CODE128";
		}
		
		ImpostazioniBarcodeBean impostazioniBarcodeBean = new ImpostazioniBarcodeBean();
		impostazioniBarcodeBean.setBarcodeEncoding(tipoBarcode);
		return impostazioniBarcodeBean;
	}
	
	public RenderedImageSource getImageProvider(String testo, ImpostazioniBarcodeBean impostazioniBarcodeBean)
			throws BadElementException, UnsupportedEncodingException {
		java.awt.Image barCode;
			barCode = getQRcode(testo, impostazioniBarcodeBean);
		
		// construct the buffered image
		BufferedImage bImage = new BufferedImage(barCode.getWidth(null), barCode.getHeight(null), BufferedImage.TYPE_INT_RGB);
		// obtain it's graphics
		Graphics2D bImageGraphics = bImage.createGraphics();
		// draw the Image (image) into the BufferedImage (bImage)
		bImageGraphics.drawImage(barCode, null, null);
		// cast it to rendered image
		RenderedImage rImage = (RenderedImage) bImage;
		return new RenderedImageSource(bImage);
	}

	private java.awt.Image getQRcode(String testo, ImpostazioniBarcodeBean impostazioniBarcodeBean)
			throws BadElementException, UnsupportedEncodingException {
		logger.debug("Aggiungo il barcode");

		// Controllo QRCodeHeight
		logger.debug("QRCodeHeight vale " + impostazioniBarcodeBean.getQrCodeHeight());
		int height = 30;
		if (impostazioniBarcodeBean.getQrCodeHeight() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getQrCodeHeight())) {
			height = Integer.valueOf(impostazioniBarcodeBean.getQrCodeHeight());
		}

		// Controllo qrCodeWidth
		logger.debug("QRCodeWidth vale " + impostazioniBarcodeBean.getQrCodeWidth());
		int width = 30;
		if (impostazioniBarcodeBean.getQrCodeWidth() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getQrCodeWidth())) {
			width = Integer.valueOf(impostazioniBarcodeBean.getQrCodeWidth());
		}

		BarcodeQRCode barcodeQRCode = new BarcodeQRCode(testo, width, height, null);

		// Ho settato il barcode. Lo converto in immagine
		java.awt.Image img = barcodeQRCode.createAwtImage(java.awt.Color.BLACK, java.awt.Color.WHITE);
		barcodeQRCode.createAwtImage(Color.BLACK, Color.WHITE);

		return img;
	}
}