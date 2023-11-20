/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import static org.jodconverter.office.LocalOfficeUtils.toUrl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.CSS;
import javax.swing.text.html.StyleSheet;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xerces.dom.TextImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableColumn;
import org.odftoolkit.odfdom.dom.attribute.table.TableAlignAttribute;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeBodyElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeMasterStylesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleMasterPageElement;
import org.odftoolkit.odfdom.dom.element.style.StyleParagraphPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTableCellPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTablePropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTextPropertiesElement;
import org.odftoolkit.odfdom.dom.element.text.TextLineBreakElement;
import org.odftoolkit.odfdom.dom.element.text.TextParagraphElementBase;
import org.odftoolkit.odfdom.dom.element.text.TextSElement;
import org.odftoolkit.odfdom.dom.element.text.TextSectionElement;
import org.odftoolkit.odfdom.dom.element.text.TextTabElement;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.dom.style.props.OdfParagraphProperties;
import org.odftoolkit.odfdom.dom.style.props.OdfStylePropertiesSet;
import org.odftoolkit.odfdom.dom.style.props.OdfStyleProperty;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStylePageLayout;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;
import org.odftoolkit.odfdom.pkg.OdfNamespace;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.PageLayoutProperties;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;
import org.odftoolkit.simple.text.list.CircleDecorator;
import org.odftoolkit.simple.text.list.DiscDecorator;
import org.odftoolkit.simple.text.list.ListDecorator.ListType;
import org.odftoolkit.simple.text.list.ListItem;
import org.odftoolkit.simple.text.list.NumberDecorator;
import org.odftoolkit.simple.text.list.NumberedAlphaLowerDecorator;
import org.odftoolkit.simple.text.list.NumberedAlphaUpperDecorator;
import org.odftoolkit.simple.text.list.NumberedGreekLowerDecorator;
import org.odftoolkit.simple.text.list.NumberedRomanLowerDecorator;
import org.odftoolkit.simple.text.list.NumberedRomanUpperDecorator;
import org.odftoolkit.simple.text.list.SquareDecorator;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.google.gson.Gson;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Utilities;
import com.itextpdf.tool.xml.css.CSS.Value;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XReplaceable;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;

import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import it.eng.auriga.compiler.exeption.FreeMarkerCreateDocumentException;
import it.eng.auriga.compiler.exeption.FreeMarkerFixMergedCellException;
import it.eng.auriga.compiler.exeption.FreeMarkerMergeHtmlSectionsException;
import it.eng.auriga.compiler.exeption.FreeMarkerRetriveStyleException;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.ModelliDocDatasource;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.AssociazioniAttributiCustomBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.PdfUtility;
import it.eng.utility.barcode.BarcodeUtility;
import it.eng.utility.barcode.ImpostazioniBarcodeBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.config.OpenOfficeInstance;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import net.sf.jooreports.templates.image.RenderedImageSource;

/**
 * Centralizza la generazione di template con dati iniettati
 * 
 * @author mattia zanin
 * 
 *         IMPORTANTE: SE LA CLASSE VIENE MODIFICATA VERIFICARE SE DEVE ESSERE MODIFICATA ANCHE L'ANALOGA CLASSE IN AURIGADOCUMENT
 *
 */
public class FreeMarkerModelliUtil {
	
	private static Logger logger = Logger.getLogger(FreeMarkerModelliUtil.class);
	
	private final static String SEPARATORE_FILE_DA_INIETTARE = "##@@FILE_DA_INIETTARE@@##";
	
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
	public static final String ESCAPE_BR = "" + '\u200E'; // Codice ASCII ESCAPEBR
	
	public static final String HTML_VALUE_PREFIX = "|*|HTML|*|";
	public static final String TAG_APERTURA_DATI_SENSIBILI = "<s>";
	public static final String TAG_CHIUSURA_DATI_SENSIBILI = "</s>";
	public static final String OMISSIS = "<i>omissis</i>";
	public static final String HTML_SPACE = "&nbsp;";
	public static final String PAGE_BREAK = "PPaGeBrEaKK";
	public static String VARIABILE_FONT_NAME;
	public static String VARIABILE_FONT_SIZE;
	
	public static TemplateWithValuesBean createTemplateWithValues(File templateOdt, ModelliDocBean bean, HttpSession session) throws Exception  {
		logger.debug("#######INIZIO createTemplateWithValues#######");
		// Devo togliere tutti i numeri colonna e sostituirli con col<n>
		Map<String, Object> model;
		if ((bean.getFlgProfCompleta() != null && bean.getFlgProfCompleta())) {
			logger.debug("#######INIZIO createMapToFillTemplate(bean, session)#######");
			model = createMapToFillTemplate(bean, session);
			logger.debug("#######FINE createMapToFillTemplate(bean, session)#######");
		} else {
			// Se il modello non è profilato lancio una eccezione
			logger.error("Impossibile generare il file da modello. La profilatura del modello non è completa per il modello con id: " + bean.getIdModello());
			throw new Exception("Impossibile generare il file da modello. La profilatura del modello non è completa");
		}
		
		//Gestisco gli omisses
		Map<String, Object> htmlSectionsModel = new HashMap<String, Object>();
		Map<String, String> fileSectionsModel = new HashMap<String, String>();
		List<String> elencoCampiConGestioneOmissisDaIgnorare =  bean.getElencoCampiConGestioneOmissisDaIgnorare();
		logger.debug("#######INIZIO for(String nomeVariabile : model.keySet())#######");
		for(String nomeVariabile : model.keySet()) {
			if (model.get(nomeVariabile) instanceof String && presenteFileAlternativoTestoCkeditor(bean.getTipiValori(), nomeVariabile, bean.getValori())) {
				String jsonFileDaIniettare = getFileAlternativoTestoCkeditor(nomeVariabile, bean.getValori());
				Gson lGson = new Gson();  
				FileDaIniettareBean fileDaIniettare = lGson.fromJson(jsonFileDaIniettare, FileDaIniettareBean.class);
				// Se sono qua sicuramente fileDaIniettare.getFileIniezioneFile() è diverso da null (lo verifico in isIniezioneTramiteFile())
				if(bean.getFlgMostraDatiSensibili() == null || bean.getFlgMostraDatiSensibili()) {
					fileSectionsModel.put(nomeVariabile, fileDaIniettare.getFileIniezioneFile().getUriFile());
				} else {
					fileSectionsModel.put(nomeVariabile, fileDaIniettare.getFileIniezioneFile().getUriFile());
				}
				model.put(nomeVariabile, "");
			} else if(model.get(nomeVariabile) instanceof String) {
				String value = "";
				value = (String) model.get(nomeVariabile);
				String html = "";
				String regex = "<([A-Za-z][A-Za-z0-9]*)\\b[^>]*>(.*?)</\\1>";
				boolean isHtml = false;
				if(value != null && value.startsWith(HTML_VALUE_PREFIX)) {
					html = value.substring(HTML_VALUE_PREFIX.length());
					isHtml = true;
				} else if (StringUtils.isNotBlank(value) && value.replaceAll("\n", "").matches(regex)) {
					html = value;
					isHtml = true;
				}
				if (isHtml) {
					//TODO Perchè viene fatto l'unescapeHtml? 
					// intanto lo commento e faccio solo i replaceAll, perchè mi crea problemi quando ho dei caratteri speciali: &amp; ecc...					
//					html = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(html).replaceAll("\n", "").replaceAll("\t", ""));
					html = html.replaceAll("\n", "").replaceAll("\t", "");
					// sostituisco tutti i dati sensibili (quelli tra <s> e </s>)
					html = html.replaceAll("<br />", ESCAPE_BR);
					html = html.replaceAll("<br/>", ESCAPE_BR);
					html = html.replaceAll("<br>", ESCAPE_BR);	
					html = html.replaceAll("\\$\\{", "\\$ \\{");
					// Sostituzione tag di interruzione pagina, metto dei paragrfi come placeholder (questi paragrafi poi verranno tolti o gestiti per creare le minime differenze possibili)
					// Ckeditor inserisce i page break come "<div style=\"page-break-after:always\"><span style=\"display:none\">&nbsp;</span></div>"
//					html = html.replaceAll("<div style=\"page-break-after:always\"><span style=\"display:none\">&nbsp;</span></div>", "<p>" + PAGE_BREAK + "</p>");
					html = html.replaceAll("<(\\s)*div(.*?)page-break-after(\\s)*:(\\s)*always(.*?)<(\\s)*\\/(\\s)*div(\\s)*>", "<p>" + PAGE_BREAK + "</p>");
					if (elencoCampiConGestioneOmissisDaIgnorare != null && elencoCampiConGestioneOmissisDaIgnorare.contains(nomeVariabile)) {
						htmlSectionsModel.put(nomeVariabile, html);
						model.put(nomeVariabile, "");
					} else {
						if(bean.getFlgMostraDatiSensibili() == null || bean.getFlgMostraDatiSensibili()) {
							String htmlFull = "" + html; //Concateno per creare un nuovo riferimento
							if(bean.getFlgMostraOmissisBarrati() == null || !bean.getFlgMostraOmissisBarrati()) {
								htmlFull = htmlFull.replaceAll(TAG_APERTURA_DATI_SENSIBILI, "");
								htmlFull = htmlFull.replaceAll(TAG_CHIUSURA_DATI_SENSIBILI, "");
							}
							htmlFull = htmlFull.replaceAll(HTML_SPACE, "&#160;");
							// htmlFull = htmlFull.replaceAll(HTML_SPACE, "&#13;");
							htmlSectionsModel.put(nomeVariabile, htmlFull);
						} else {
							String htmlOmissis = replaceOmissisInHtml(html);
							htmlSectionsModel.put(nomeVariabile, htmlOmissis);
						}					
						model.put(nomeVariabile, "");	
					}
				}
			}
		}
		logger.debug("#######FINE for(String nomeVariabile : model.keySet())#######");
		// Pulisco i nbsp dalle variabili Freemarker
		// Lo faccio dopo aver creato htmlSectionModel, in modo da non modificare le varialibi ckeditor che verranno iniettate nel secondo passaggio
		logger.debug("#######INIZIO cleanNbspFromModel(model)#######");
		cleanNbspFromModel(model);
		logger.debug("#######FINE cleanNbspFromModel(model)#######");
		
		// font e size di default del modello nel caso in cui sia fatto male
		VARIABILE_FONT_SIZE = ParametriDBUtil.getParametroDB(session, "MODELLO_FONT_SIZE");
		VARIABILE_FONT_NAME = ParametriDBUtil.getParametroDB(session, "MODELLO_FONT_NAME");
		
		// Inietto i valori nel modello.
		// Questa iniezione non consente una corretta gestione dei valori ckeditor, che andranno quiandi iniettati in un secondo momento
		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory(); 
		DocumentTemplate template = documentTemplateFactory.getTemplate(templateOdt);    
       
		File templateOdtWithValues = File.createTempFile("temp", ".odt");
		FileOutputStream odtOutputStream = new FileOutputStream(templateOdtWithValues);
		try {
			logger.debug("#######INIZIO template.createDocument#######");
			template.createDocument(model, odtOutputStream);
			logger.debug("#######FINE template.createDocument#######");
		} catch (Exception e) {
			logger.error("Errore nell'esecuzione del metodo createDocument: " + e.getMessage());
			throw new FreeMarkerCreateDocumentException(e.getMessage(), e);
		}
		logger.debug("Ho terminato la prima iniezione, ora gestisco i valori ckeditor");	
		// Ho terminato la prima iniezione, ora gestisco i valori ckeditor
		
		TemplateWithValuesBean beanToReturn = new TemplateWithValuesBean();
		
		// Ripristino le section destinate a contenere i valori ckeditor e file, rimettendo nel file ottenuto dalla prima iniezione quelle presenti nel modello.
		// Lo devo fare perchè la prima iniezione ha iniettato anche i valori ckeditor, ma dovendo rifare l'inizione devo ripristinare le section del modello
		if (htmlSectionsModel != null && !htmlSectionsModel.isEmpty()) {
			// Ripristino le section destinate a contenere i valori ckeditor, rimettendo nel file ottenuto dalla prima iniezione quelle presenti nel modello.
			// Lo devo fare perchè la prima iniezione ha iniettato anche i valori ckeditor, ma dovendo rifare l'inizione devo ripristinare le section del modello
			logger.debug("#######INIZIO for(String nomeVariabile : htmlSectionsModel.keySet())#######");
			for(String nomeVariabile : htmlSectionsModel.keySet()) {
				mergeHtmlSections(nomeVariabile, templateOdt, templateOdtWithValues);
			}
			logger.debug("#######FINE for(String nomeVariabile : htmlSectionsModel.keySet())#######");
		}
		if (fileSectionsModel != null && !fileSectionsModel.isEmpty()) {
			// Ripristino le section destinate a contenere i valori ckeditor, rimettendo nel file ottenuto dalla prima iniezione quelle presenti nel modello.
			// Lo devo fare perchè la prima iniezione ha iniettato anche i valori ckeditor, ma dovendo rifare l'inizione devo ripristinare le section del modello
			logger.debug("#######INIZIO for(String nomeVariabile : fileSectionsModel.keySet())#######");
			for(String nomeVariabile : fileSectionsModel.keySet()) {
				mergeHtmlSections(nomeVariabile, templateOdt, templateOdtWithValues);
			}
			logger.debug("#######FINE for(String nomeVariabile : fileSectionsModel.keySet())#######");
		}
		
		//Inietto i contenuti selle sezioni html ricevuti come file odt
		if (fileSectionsModel != null && !fileSectionsModel.isEmpty()) {
			logger.debug("#######INIZIO iniettaDocxInHtmlSections#######");
			iniettaDocxInHtmlSections(fileSectionsModel, templateOdtWithValues, session);
			logger.debug("#######FINE iniettaDocxInHtmlSections#######");
		}
		
		// Inietto l'html dei campi CKEditor nelle sezioni del modello odt
		if (htmlSectionsModel != null && !htmlSectionsModel.isEmpty()) {
			// Creo una mappa dove salvo l'eventuale testo statico presente nelle sezioni html priam e dopo il placeholder
			// Map<String, String[]> mappaTestoStaticoHtmlSection = new HashMap<>();
			
			// 5) Gestione tabelle con celle unite
			// Non è supportata l'inizione html di tabelle con celle unite, devo quandi seguire questi passaggi per ogni tabella presente:
			// 1- Trasformare la tabella html con celle unite in una tabella senza celle unite
			// 2- Iniettare la tabella senza celle unite
			// 3- Generare il file odt
			// 4- Per ogni tabella nel file odt eseguore i raggruppamenti delle celle per ripristinare lo stato originale della tabella
			logger.debug("#######INIZIO fixMergedCell#######");
			Map<String, TableStyleBean> mappaTablesStyle = fixMergedCell(htmlSectionsModel);
			logger.debug("#######FINE fixMergedCell#######");

			//TODO PER AGGIUNGERE IL CONTENUTO NELLE SEZIONI HTML
			// Load ODT file by filling Velocity template engine and cache
			// it to the registry
			FileInputStream fisReport = new FileInputStream(templateOdtWithValues);
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(fisReport, TemplateEngineKind.Freemarker);
			
			// Create fields metadata to manage text styling
			FieldsMetadata metadata = report.createFieldsMetadata();
			for(String nomeVariabile : htmlSectionsModel.keySet()) {
//				if (mappaTestoStaticoHtmlSection.containsKey(nomeVariabile)) {
//					String testoHtml = (String) htmlSectionsModel.get(nomeVariabile);
//					if (testoHtml != null && testoHtml.startsWith("<p")) {
//						int endTag = testoHtml.indexOf(">");
//						testoHtml = testoHtml.substring(0, endTag + 1) + mappaTestoStaticoHtmlSection.get(nomeVariabile)[0] + testoHtml.substring(endTag + 1);
//					}
//					if (testoHtml != null && testoHtml.endsWith("</p>")) {
//						testoHtml = testoHtml.substring(0, testoHtml.length() - 4) + mappaTestoStaticoHtmlSection.get(nomeVariabile)[1] + "</p>";
//					}
//					htmlSectionsModel.put(nomeVariabile, testoHtml);
//					
//				}
				metadata.addFieldAsTextStyling(nomeVariabile, SyntaxKind.Html, true);		
			}
			
			// Create context Java model
			IContext context = report.createContext();
			
			// Retrive paragraph style
			Map<String, AttributeSet> paragraphsStyle = null;
			if (isAttivaIndentazione(session)) {
				paragraphsStyle = retriveParagraphsStyle(htmlSectionsModel);
			}
			
			// Save the map
			context.putMap(htmlSectionsModel);
			
			// Generate report by merging Java model with the ODT
			odtOutputStream = new FileOutputStream(templateOdtWithValues);
			
			Map<String, Object> listsStyle = null;
			
			try {
						
				// Retrive tables style
				logger.debug("#######INIZIO retriveCellsTableStyle#######");
				mappaTablesStyle = retriveCellsTableStyle(htmlSectionsModel, mappaTablesStyle);
				logger.debug("#######FINE retriveCellsTableStyle#######");
				
				// Retrive list style
				logger.debug("#######INIZIO retriveListsStyle#######");
				listsStyle = retriveListsStyle(htmlSectionsModel);
				logger.debug("#######FINE retriveListsStyle#######");
				
			} catch (Exception e) {
				logger.error("Errore nel retrive degli stili: " + e.getMessage());
				throw new FreeMarkerRetriveStyleException(e.getMessage(), e);
			}
			logger.debug("#######INIZIO report.process#######");
			report.process(context, odtOutputStream);
			logger.debug("#######FINE report.process#######");
			
			// Funzione che prende i caratteri delle sezioni dal modello ODT e li applica sull ODT generato che dovrà  essere convertito
			// TODO controllare il cambio caratteri, per ora gestisco gli errori con una eccezione
			try {
				logger.debug("#######INIZIO cambiaCarattereSezioni#######");
				templateOdtWithValues = cambiaCarattereSezioni(templateOdtWithValues, templateOdt, mappaTablesStyle, listsStyle, paragraphsStyle, bean, htmlSectionsModel, session);
				logger.debug("#######FINE cambiaCarattereSezioni#######");
				if (mappaTablesStyle != null && !mappaTablesStyle.isEmpty()) {
					logger.debug("#######INIZIO applicaStiliTabelle#######");
					templateOdtWithValues = applicaStiliTabelle(templateOdtWithValues, mappaTablesStyle);
					logger.debug("#######FINE applicaStiliTabelle#######");
					// Riprisitino l'unione solo se si sono tabelle con celle unite
					if (isPresentiTabelleConCelleUnite(mappaTablesStyle)) {
						logger.debug("#######INIZIO applicaUnioniCelle#######");
						templateOdtWithValues = applicaUnioniCelle(templateOdtWithValues, mappaTablesStyle);
						logger.debug("#######FINE applicaUnioniCelle#######");
					}
				}				
//			} catch (StoreException e) {
//				// errori gestiti
//				logger.error(e.getMessage(), e);
//				// per gestire eventualmente un warning specifico in futuro
//				beanToReturn.setInError(true);
//				beanToReturn.setErrorMessage(e.getMessage());
//			} catch (Exception e) {
//				// errore non gestito durante l'applicazione degli stili o l'unione delle celle nelle tabelle 
//				logger.error(e.getMessage(), e);
//				// per gestire eventualmente un warning specifico in futuro
//				beanToReturn.setInError(true);
//				beanToReturn.setErrorMessage("");
			} finally {
				if(fisReport != null) {
					try {
						fisReport.close(); 
					} catch (Exception e) {}
				}
				if(odtOutputStream != null) {
					try {
					    odtOutputStream.close();
					} catch (Exception e) {}
				}
			}
		}	 
		logger.debug("#######INIZIO setfilegenerato#######");
		beanToReturn.setFileOdtGenerato(templateOdtWithValues);
		if(bean.getFlgGeneraPdf()) {
			logger.debug("#######INIZIO createPdfFromOdt#######");
			beanToReturn.setFileGenerato(createPdfFromOdt(templateOdtWithValues, session));
			logger.debug("#######FINE createPdfFromOdt#######");
		} else {
			logger.debug("#######INIZIO ModelliUtil.convertToDoc#######");
			beanToReturn.setFileGenerato(ModelliUtil.convertToDoc(templateOdtWithValues));
			logger.debug("#######INIZIO ModelliUtil.convertToDoc#######");
		}
		logger.debug("#######FINE setfilegenerato#######");
		logger.debug("#######FINE createTemplateWithValues#######");
		return beanToReturn;
	}
	
	private static boolean presenteFileAlternativoTestoCkeditor(Map<String, String> tipiValore, String nomeVariabile,  Map<String, Object> valori) {
		// Verifico se la variabile è di tipo Ckeditor
		if (tipiValore != null && tipiValore.containsKey(nomeVariabile) && tipiValore.get(nomeVariabile) instanceof String && "CKEDITOR".equalsIgnoreCase(tipiValore.get(nomeVariabile))) {
			// Controllo se ho un file altrenativo al testo Ckeditor
			if (valori != null && valori.get(nomeVariabile + "_FILE") != null && valori.get(nomeVariabile + "_FILE") instanceof String) {
				try {
					Gson lGson = new Gson();  
					FileDaIniettareBean fileDaIniettare = lGson.fromJson((String) (valori.get(nomeVariabile + "_FILE")), FileDaIniettareBean.class);
					return fileDaIniettare.isSelezionaIniezioneFile() && fileDaIniettare.getFileIniezioneFile() != null && StringUtils.isNotBlank(fileDaIniettare.getFileIniezioneFile().getUriFile());
				} catch (Exception e) {
					logger.error("Errore in presenteFileAlternativoTestoCkeditor: " + e.getMessage(), e);
				}
			} 
		}
		return false;
	}
	
	private static String getFileAlternativoTestoCkeditor(String nomeVariabile,  Map<String, Object> valori) {
		// Ho già eseguito presenteFileAlternativoTestoCkeditor(), quindi so che la variabile esiste ed è di tipo stringa
		return (String) valori.get(nomeVariabile + "_FILE");
	}
	
	private static void cleanNbspFromModel (Map<String, Object> model) {
		for(String nomeVariabile : model.keySet()) {
			Object variabileModello = model.get(nomeVariabile); 
			if (variabileModello != null) {
				if (variabileModello instanceof String) {
					// Il freemarker è una stringa semplice
					model.put(nomeVariabile, replaceNbspWithSpace((String) variabileModello));
				}
				else if (variabileModello instanceof List) {
					// Il freemarker è una lista o una tabella
					List listaRighe = (List) (variabileModello);
					if (listaRighe.size() > 0 && listaRighe.get(0) instanceof String) {
						// Il freemarker è una lista di stringhe, scorro i valori e li pulisco
						ArrayList listaSenzaNbsp = new ArrayList<>();
						for (Object riga : listaRighe) {
							if (riga != null && riga instanceof String) {
								String rigaCorretta = replaceNbspWithSpace((String) riga);
								listaSenzaNbsp.add(rigaCorretta);
							} else {
								listaSenzaNbsp.add(riga);
							}	
						}
						// Inserisco la lista corretta nel modello
						model.put(nomeVariabile, listaSenzaNbsp);
					} else if (listaRighe.size() > 0 && listaRighe.get(0) instanceof Map) {
						// Il freemarker è una tabella, devo pulire tutte le celle
						for (Object riga : listaRighe) {
							if (riga != null && riga instanceof Map) {
								Map mappaColonne = (Map) riga;
								for(Object nomeColonna : mappaColonne.keySet()) {
									Object valoreColonna = mappaColonne.get(nomeColonna);
									if (valoreColonna != null && valoreColonna instanceof String){
										String valoreColonnaCorretto = replaceNbspWithSpace((String) valoreColonna);
										mappaColonne.put(nomeColonna, valoreColonnaCorretto);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	private static String replaceNbspWithSpace(String input) {
		if (StringUtils.isNotBlank(input)) {
			return input.replace('\u00A0',' ').trim();
		} else {
			return input;
		}
	}
	
	private static boolean isPresentiTabelleConCelleUnite(Map<String, TableStyleBean> mappaTablesStyle) {
		if (mappaTablesStyle != null) {
			Set<String> keySet = mappaTablesStyle.keySet();
			for (String key : keySet) {
				TableStyleBean lTableStyleBean = mappaTablesStyle.get(key);
				if (lTableStyleBean != null && isTabellaConCelleUnite(lTableStyleBean)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isTabellaConCelleUnite(TableStyleBean lTableStyleBean) {
		CellaMappaUnioniBean[][] lCellaMappaUnioniBean = lTableStyleBean.getMappaUnioni();
		if (lCellaMappaUnioniBean != null){
			for (int i = 0; i < lCellaMappaUnioniBean.length; i++) {
				for (int j = 0; j < lCellaMappaUnioniBean[i].length; j++) {
					if (lCellaMappaUnioniBean[i][j] != null && lCellaMappaUnioniBean[i][j].getNroRaggruppamento() > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static File applicaUnioniCelle(File templateOdtWithValues, Map<String, TableStyleBean> mappaTablesStyle) throws Exception {

		TextDocument odtFinale = null;
		try {
			odtFinale = TextDocument.loadDocument(templateOdtWithValues.getPath());
	
			//Prendo le varie sezioni dal modello ODT
			Iterator<Section> iteratorSezioni = odtFinale.getSectionIterator();
			while(iteratorSezioni.hasNext()) {
				String nomeSection = iteratorSezioni.next().getName();
				Section section = odtFinale.getSectionByName(nomeSection);
				Iterator<Table> iteratorTabelle = section != null ? section.getTableList().iterator() : null;
				// Ciclo sulle tabelle
				if(iteratorTabelle != null) {
					while (iteratorTabelle.hasNext()) {
						Table tabella = iteratorTabelle.next();
						String idTable = tabella.getOdfElement().getAttribute("idTableCKEDITOR");
						if (StringUtils.isNotBlank(idTable) && mappaTablesStyle.containsKey(idTable) && isTabellaConCelleUnite(mappaTablesStyle.get(idTable))) {
							// Ciclo sulle celle
							for (int posRow = tabella.getRowCount() - 1; posRow >= 0; posRow --) {
								for (int posCol = tabella.getColumnCount() - 1; posCol >= 0 ; posCol--) {
									CellaMappaUnioniBean currentCell = mappaTablesStyle.get(idTable).getMappaUnioni()[posRow][posCol];
									Integer nroRagg = currentCell.getNroRaggruppamento();
									boolean coveredCell = currentCell.isCoveredCell();
									if (!coveredCell && nroRagg > 0) {
										// E' una cella unita di partenza (quindi non è la covered)
										Integer colToAdd = currentCell.getColSpan() ;
										Integer rowToAdd = currentCell.getRowSpan() ;
										Integer startRow = posRow;
										Integer endRow = posRow + rowToAdd -1 ;
										Integer startCol = posCol;
										Integer endCol = posCol + colToAdd -1 ;
		
										tabella.getCellRangeByPosition(startCol, startRow, endCol, endRow).merge();
									} 							
								}
							}
						}
					}
				}
			}
	
			File templateOdtToconvert = File.createTempFile("temp", ".odt");
	
			odtFinale.save(templateOdtToconvert);
	
			return templateOdtToconvert;
			
		} finally {
			if(odtFinale != null) {
				try {
					odtFinale.close();
				} catch (Exception e) {}
			}
		}
	}

	private static void getWidthCols(TableStyleBean tableStyleBean) {
		
		CellaMappaUnioniBean[][] mappaUnioni = tableStyleBean.getMappaUnioni();
		Float[] listaWidthCols = new Float[mappaUnioni[0].length];
		Float sommaLarghezzeColonne = (float) 0;
		tableStyleBean.setUnitaMisuraWidthColonne(null);

		for (int i = 0; i < mappaUnioni.length; i++) {
			for (int j = 0; j < mappaUnioni[0].length; j++) {

				CellaMappaUnioniBean currentCell = mappaUnioni[i][j];

				Integer colspan = currentCell.getColSpan();
				if (colspan == 1) {
					try {
						AttributeSet cellStyle = tableStyleBean.getListaStiliRow().get(i).get(j);
						String colWidthUnit = cellStyle.getAttribute(CSS.Attribute.WIDTH) != null ? cellStyle.getAttribute(CSS.Attribute.WIDTH).toString() : null;
						if (listaWidthCols[j] == null ) {
							Pair<Float, String> colWidth = estraiValoreMisura(colWidthUnit);
							if (colWidth != null) {
								// Tengo traccia della unità di musura delle colonne solo se è coerente tra tutte le colonne
								String unitaMusiraLarghezzaColonna = colWidth.getRight();
								if (tableStyleBean.getUnitaMisuraWidthColonne() == null) {
									tableStyleBean.setUnitaMisuraWidthColonne(colWidth.getRight());
								} else if (!tableStyleBean.getUnitaMisuraWidthColonne().equalsIgnoreCase(unitaMusiraLarghezzaColonna)) {
									tableStyleBean.setUnitaMisuraWidthColonne("invalidColWidth");
								}
								Float colWidthFloat = colWidth.getLeft();
								listaWidthCols[j] = colWidthFloat;
								sommaLarghezzeColonne += colWidthFloat;
							} else if (!currentCell.isCoveredCell()) {
								tableStyleBean.setUnitaMisuraWidthColonne("invalidColWidth");
							}
						}
					} catch (Exception e) {
						logger.error("Errore in getWidthCols: " + e.getMessage(), e);
					}
				}
			}
		}
		
		ArrayList<Float> listaWidths = new ArrayList<>();
		for (int i = 0; i < listaWidthCols.length; i++) {
			listaWidths.add(listaWidthCols[i]);
		}
		
		tableStyleBean.setListaWidthColonne(listaWidths);
		tableStyleBean.setSommaWidthColonne(sommaLarghezzeColonne);
	}

	private static boolean isTrue(Boolean value) {
		return value != null && value;
	}
	
	public static String replaceOmissisInHtml(String html) {
		
		String htmlOmissis = ""; 
		int pos = 0;
		while(pos < html.length()) {
			int startDatiSensibili = html.indexOf(TAG_APERTURA_DATI_SENSIBILI, pos);
			if(startDatiSensibili == -1) {
				htmlOmissis += html.substring(pos);
				break;
			}
			htmlOmissis += html.substring(pos, startDatiSensibili);
			int endDatiSensibili = html.indexOf(TAG_CHIUSURA_DATI_SENSIBILI, startDatiSensibili);
			htmlOmissis += OMISSIS;	
			if(endDatiSensibili != -1) {
				pos = endDatiSensibili + TAG_CHIUSURA_DATI_SENSIBILI.length();
			} else {
				pos = html.length();
			}
		}
		
		htmlOmissis = htmlOmissis.replaceAll(HTML_SPACE, "&#160;");
		return htmlOmissis;
	}

	/**
	 * Metodo che per ogni tabella assegnna un bean TableStyleBean contenente le informazioni su:
	 * - lista degli stili applicati su ogni cella
	 * - larghezza di ogni colonna
	 * - mappa dei raggruppamenti (ad ogni cella unita è assegnato il numero di raggruppamento di appartenenza)
	 * - coordinnate di raggruppamenti (per ogni raggruppamento si indica la cella di inizio in alto a sinistra e quella di fine in basso a destra)
	 * 
	 * @param htmlSectionsModel Mappa delle sezioni nel documento
	 * @return mappa contenete lo TableStyleBean di ogni tabella
	 * @throws FreeMarkerFixMergedCellException 
	 */
	private static Map<String, TableStyleBean> fixMergedCell(Map<String, Object> htmlSectionsModel) throws FreeMarkerFixMergedCellException {
		
		try {
			Map<String, TableStyleBean> tablesStyle = new HashMap<String, TableStyleBean>();
			
			// ciclo su tutte le section
			for (String htmlSectionName : htmlSectionsModel.keySet()) {
				Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
				doc.outputSettings().indentAmount(0).prettyPrint(false);
				int nTab = 0;
				Elements tables = doc.select("table");
				// ciclo su tutte le tabelle sdella section
				for (Element table : tables) {	
					nTab++;
					Elements rows = table.select("tr");
					TableStyleBean tableStyle = new TableStyleBean();
					// Ricavo il numero massimo di righe e colonne, ovvero la dimensione della tabella con tutte le celle divise
					int numMaxRows = rows.size();
					int numMaxCols = 0;
					for (int posRow = 0; posRow < rows.size(); posRow++) {
						Element row = rows.get(posRow);
						Elements cols = row.select("td");
						int sumColCurrentRow = 0;
						for (int posCol = 0; posCol < cols.size(); posCol++) {
							Element currentCol = cols.get(posCol);
							int colSpanSet = StringUtils.isNotBlank(currentCol.attr("colspan")) ? Integer.parseInt(currentCol.attr("colspan")) : 1;
							sumColCurrentRow += colSpanSet;
						}
						if (sumColCurrentRow > numMaxCols) {
							numMaxCols = sumColCurrentRow;
						}
					}
					
					// Inizializzo la mappa delle celle unite
					CellaMappaUnioniBean[][] mappaUnioni = new CellaMappaUnioniBean[numMaxRows][numMaxCols];
					for (int i = 0; i < numMaxRows; i++) {
						for (int j = 0; j < numMaxCols; j++) {
							mappaUnioni[i][j] = new CellaMappaUnioniBean();
						}
					}
					
					int numeroRaggruppamento = 0;
	
					// Scorro tutte le celle per ricavare la mappa delle unioni
					for (int posRow = 0; posRow < rows.size(); posRow++) {
						Element row = rows.get(posRow);
						Elements cols = row.select("td");
						// CellaMappaUnioniBean ha la stessa dimensione della tabella, ma non ci sarà una corrispondenza 1 a 1 delle celle dato che la tabella può contenere celle unite
						// Ad esempio se la tabella ha le prime due celle della prima riga unite la cella 0,0 corrisponde alla cella 0,0 di CellaMappaUnioniBean, ma la cella 0,1 corrisponde alla
						// cella 0,2 della CellaMappaUnioniBean, in quanto in CellaMappaUnioniBean la cella 0,1 è segnata come covered
						// colCorrenteMappa serve appunto come indice per tenere conto di questa mancata corrispondenza
						int colCorrenteMappa = initColCorrenteMappa(mappaUnioni, posRow, 0);
						for (int posCol = 0; posCol < cols.size(); posCol++) {
	
							Element col = cols.get(posCol);
							int colSpanSet = StringUtils.isNotBlank(col.attr("colspan")) ? Integer.parseInt(col.attr("colspan")) : 0;
							int rowSpanSet = StringUtils.isNotBlank(col.attr("rowspan")) ? Integer.parseInt(col.attr("rowspan")) : 0;
							if (colSpanSet > 0 && rowSpanSet > 0) {
								// La cella è formata dall'unione di celle sia in verticale che orizzontale
								numeroRaggruppamento ++;
								mappaUnioni[posRow][colCorrenteMappa].setCoveredCell(false);
								mappaUnioni[posRow][colCorrenteMappa].setRowSpan(rowSpanSet);
								mappaUnioni[posRow][colCorrenteMappa].setColSpan(colSpanSet);
								// Per ogni riga trovo le celle che appartengono a quel raggruppamento
								int startColGroup = colCorrenteMappa;
								for (int  i = 0; i < rowSpanSet; i++) {
									colCorrenteMappa = startColGroup;
									for (int j = 0 ; j < colSpanSet; j++) {
										mappaUnioni[posRow + i][colCorrenteMappa].setNroRaggruppamento(numeroRaggruppamento);
										colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow + i, colCorrenteMappa);
									}
								}
							} else if (colSpanSet > 0 && rowSpanSet == 0) {
								// La cella è formata solo dall'unione di celle in orizzontale
								numeroRaggruppamento ++;
								mappaUnioni[posRow][colCorrenteMappa].setCoveredCell(false);
								mappaUnioni[posRow][colCorrenteMappa].setColSpan(colSpanSet);
								for (int i = 0 ; i < colSpanSet; i++) {
									mappaUnioni[posRow][colCorrenteMappa].setNroRaggruppamento(numeroRaggruppamento);
									colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow, colCorrenteMappa);
								}
							} else if (colSpanSet == 0 && rowSpanSet > 0) {
								// La cella è formata solo dall'unione di celle in verticale
								numeroRaggruppamento ++;
								mappaUnioni[posRow][colCorrenteMappa].setCoveredCell(false);
								mappaUnioni[posRow][colCorrenteMappa].setRowSpan(rowSpanSet);
								for (int i = 0; i < rowSpanSet; i++) {
									mappaUnioni[posRow + i][colCorrenteMappa].setNroRaggruppamento(numeroRaggruppamento);
								}
								colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow, colCorrenteMappa);
							} else if  (colSpanSet == 0 && rowSpanSet == 0) {
								// Nessun raggruppamento sulla cella
								colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow, colCorrenteMappa);
							}
						}
					}
					
					tableStyle.setMappaUnioni(mappaUnioni);
					tablesStyle.put(htmlSectionName + "_Tab" + nTab, tableStyle);
					
					// Aggiungo tutte le celle per fare in modo che la tabella diventi senza raggruppamenti
					for (int i = 0; i < mappaUnioni.length; i++) {
						int lastNotCovered = 0;
						System.out.println(mappaUnioni[i].toString());
	
						for (int j = 0; j < mappaUnioni[0].length; j++) {
							Element row = rows.get(i);
							Elements cols = row.getElementsByTag("td");
							if (mappaUnioni[i][j].isCoveredCell() && mappaUnioni[i][j].getNroRaggruppamento() > 0 ) {
								if ( j == 0 ) {
									cols.get(lastNotCovered).before("<td></td>");				
								} else {
									cols.get(lastNotCovered).after("<td></td>");				
								}
							} else if (!mappaUnioni[i][j].isCoveredCell() || mappaUnioni[i][j].getNroRaggruppamento() == 0){
								lastNotCovered = j;
							}
						}
					}
				}
				htmlSectionsModel.put(htmlSectionName, doc.select("body").html());
			}
			return tablesStyle;
		} catch (Exception e) {
			logger.error("Errore nell'esecuzione del metodo fixMergedCell: " + e.getMessage());
			throw new FreeMarkerFixMergedCellException(e.getMessage(), e);
		}
	}
	
	// Si posiziona nel primo elemento non covered della riga attuale nella mappaUnioni
	private static int initColCorrenteMappa(CellaMappaUnioniBean[][] mappaUnioni, int posRow, int posCol) {
		CellaMappaUnioniBean[] rigaUnione = mappaUnioni[posRow];
		// Devo considerare che potrebbero esserci dei rowspan
		// ad esempio se in una tabella 3x3 la cella in posizione 0,0 ha un rowspan di 2, la prima celle della riga 1 corrisponde
		/// all'elemento in posizione 1 di rigaUnione in quanto l'lemento in posizione 0 è una cella covered.
		for (int i = posCol; i < rigaUnione.length; i++) {
			if (rigaUnione[i] == null || rigaUnione[i].getNroRaggruppamento() == 0) {
				return i;
			}	
		}
		
		return -1;
	}
	
	// Si posiziona nel prossimo elemento non covered della riga attuale nella mappaUnioni
	private static int avanzaColCorrenteMappa(CellaMappaUnioniBean[][] mappaUnioni, int posRow, int colCorrenteMappa) {
		CellaMappaUnioniBean[] rigaUnione = mappaUnioni[posRow];
		
		for (int i = 1; (colCorrenteMappa + i) < rigaUnione.length; i++) {
			if (rigaUnione[colCorrenteMappa + i] == null || rigaUnione[colCorrenteMappa + i].getNroRaggruppamento() == 0) {
				return colCorrenteMappa + i;
			}	
		}
		
		return -1;
	}
	
	private static Map<String, AttributeSet> retriveParagraphsStyle(Map<String, Object> htmlSectionsModel) {
		Map<String, AttributeSet> paragraphsStyle = new HashMap<String, AttributeSet>();

		for (String htmlSectionName : htmlSectionsModel.keySet()) {
			Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
			doc.outputSettings().indentAmount(0).prettyPrint(false);
			int nItem = 0;
			Elements parItems = doc.select("p");
			for (org.jsoup.nodes.Node parItem : parItems) {
				// Non devo elaborare paragrafi che sono dentro tabelle o liste
				boolean nodoDaElaborare = true;
				org.jsoup.nodes.Node parent = parItem != null ? parItem.parent() : null;
				while(parent != null) {
					String nodeName = parent.nodeName();
					if ("table".equalsIgnoreCase(nodeName) || "ol".equalsIgnoreCase(nodeName) || "li".equalsIgnoreCase(nodeName)) {
						nodoDaElaborare = false;
						break;
					}
					parent = parent.parent();
				}
				if (nodoDaElaborare) {
					nItem++;
					TextNode firstTextNode = getFirstTextNode(parItem);
					firstTextNode.text("%#ParItem" + nItem + "#%sect_" + htmlSectionName + firstTextNode.text());
					StyleSheet styleSheet = new StyleSheet();
					AttributeSet styleSet = styleSheet.getDeclaration(parItem.attr("style"));
					paragraphsStyle.put("%#ParItem" + nItem + "#%sect_" + htmlSectionName, styleSet);
	//				if (parItem.hasText() && parItem.textNodes() != null && parItem.textNodes().size() > 0) {
	//					parItem.textNodes().get(0).text("%#ParItem" + nItem + "#%sect_" + htmlSectionName + "##" + parItem.textNodes().get(0).text());
	//					StyleSheet styleSheet = new StyleSheet();
	//					AttributeSet styleSet = styleSheet.getDeclaration(parItem.attr("style"));
	//					paragraphsStyle.put("%#ParItem" + nItem + "#%sect_" + htmlSectionName + "##", styleSet);
	//				} else if (parItem.hasText()){
	//					List<org.jsoup.nodes.Node> listNodiPar = parItem.childNodes();
	//					boolean paragrafoElaborato = false;
	//					if (listNodiPar != null && !listNodiPar.isEmpty()) {
	//						org.jsoup.nodes.Node nodoPar = listNodiPar.get(0);
	//						while (!paragrafoElaborato && nodoPar != null) {
	//							if (nodoPar instanceof TextNode) {
	//								((TextNode) nodoPar).text("%#ParItem" + nItem + "#%sect_" + htmlSectionName + "##" + ((TextNode) nodoPar).text());
	//								StyleSheet styleSheet = new StyleSheet();
	//								AttributeSet styleSet = styleSheet.getDeclaration(parItem.attr("style"));
	//								paragraphsStyle.put("%#ParItem" + nItem + "#%sect_" + htmlSectionName + "##", styleSet);
	//								paragrafoElaborato = true;
	//							} else {
	//								nodoPar = nodoPar.nextSibling();
	//							}
	//						}
	//					}
	//				}
				}
			}
			htmlSectionsModel.put(htmlSectionName, doc.select("body").html());
		}

		return paragraphsStyle;
	}
	
	private static TextNode getFirstTextNode(org.jsoup.nodes.Node parItem) {
		TextNode result = null;
		if (parItem instanceof TextNode) {
			return (TextNode) parItem;
		} else {
			if (parItem.childNodeSize() > 0) {
				for (org.jsoup.nodes.Node childNode : parItem.childNodes()) {
					if (childNode != null && childNode instanceof TextNode) {
						result = (TextNode) childNode;
						break;
					} else if (childNode != null) {
						return getFirstTextNode(childNode);
					}
				}

			}
		}
		return result;
	}
	
	private static Map<String, TableStyleBean> retriveCellsTableStyle(Map<String, Object> htmlSectionsModel, Map<String, TableStyleBean> tablesStyle) {
				
		for (String htmlSectionName : htmlSectionsModel.keySet()) {
			Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
			int nTab = 0;
			Elements tables = doc.select("table");
			for (Element table : tables) {
				nTab++;
				Elements rows = table.select("tr");
				ArrayList<ArrayList<AttributeSet>> listaStiliRow = new ArrayList<ArrayList<AttributeSet>>();
				for (int posRow = 0; posRow < rows.size(); posRow++) {
					Element row = rows.get(posRow);
					Elements cols = row.select("td");
					ArrayList<AttributeSet> listaStiliCol = new ArrayList<AttributeSet>();
					for (int posCol = 0; posCol < cols.size(); posCol++) {
						Element col = cols.get(posCol);
						StyleSheet styleSheet = new StyleSheet();
						AttributeSet styleSet = styleSheet.getDeclaration(col.attr("style"));
						listaStiliCol.add(posCol, styleSet);
					}
					listaStiliRow.add(posRow, listaStiliCol);
				}
				
				TableStyleBean tableStyle = tablesStyle.get(htmlSectionName + "_Tab" + nTab) != null ? tablesStyle.get(htmlSectionName + "_Tab" + nTab) : new TableStyleBean();
				tableStyle.setListaStiliRow(listaStiliRow);
				// Inserisco lo stile della tabella
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet stileTabella = styleSheet.getDeclaration(table.attr("style"));
				tableStyle.setStileTabella(stileTabella);
				tableStyle.setBordoTabella(table.attr("border"));
				tablesStyle.put(htmlSectionName + "_Tab" + nTab, tableStyle);
			}
		}

		return tablesStyle;
	}

	private static Map<String, Object> retriveListsStyle(Map<String, Object> htmlSectionsModel) {
		Map<String, Object> listsStyle = new HashMap<String, Object>();

		for (String htmlSectionName : htmlSectionsModel.keySet()) {
			Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
			int nItem = 0;
			Elements listItems = doc.select("li");
			for (Element listItem : listItems) {
				nItem++;
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet styleSet = styleSheet.getDeclaration(listItem.attr("style"));
				listsStyle.put("%#ListItem" + nItem + "#%sect_"+ htmlSectionName, styleSet);
			}
			
			int nOlist = 0;
			Elements oLists = doc.select("ol"); 
			for (Element oList : oLists) {
				nOlist++;
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet styleSet = styleSheet.getDeclaration(oList.attr("style"));
				listsStyle.put("%#oListStyle" + nOlist + "#%sect_"+ htmlSectionName, styleSet);
				
				Integer startSet = StringUtils.isNotBlank(oList.attr("start")) ? Integer.parseInt(oList.attr("start")) : null;
				listsStyle.put("%#oListStart" + nOlist + "#%sect_"+ htmlSectionName, startSet);
			}
			
			int nUlist = 0;
			Elements uLists = doc.select("ul"); 
			for (Element uList : uLists) {
				nUlist++;
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet styleSet = styleSheet.getDeclaration(uList.attr("style"));
				listsStyle.put("%#uListStyle" + nUlist + "#%sect_"+ htmlSectionName, styleSet);
			}
		}

		return listsStyle;
	}
	
	private static File applicaStiliTabelle(File templateOdtWithValues, Map<String, TableStyleBean> mappaTablesStyle) throws Exception {
		
		OdfDocument odtFinale = null;
		try {
		    odtFinale = OdfDocument.loadDocument(templateOdtWithValues);
		    
		    StyleMasterPageElement defaultPage = odtFinale.getOfficeMasterStyles().getMasterPage("Standard");
		    String pageLayoutName = defaultPage.getStylePageLayoutNameAttribute();        
		    OdfStylePageLayout pageLayoutStyle = defaultPage.getAutomaticStyles().getPageLayout(pageLayoutName);
		    PageLayoutProperties pageLayoutProps = PageLayoutProperties.getOrCreatePageLayoutProperties(pageLayoutStyle);
		    Double writeablePageWidth = pageLayoutProps.getPageWidth() - (pageLayoutProps.getMarginLeft() +  pageLayoutProps.getMarginRight());
		    
			for (OdfTable odfTable : odtFinale.getTableList()) {
				String idTable =  odfTable.getOdfElement().getAttribute("idTableCKEDITOR");
				if (StringUtils.isNotBlank(idTable) && mappaTablesStyle.containsKey(idTable)) {
					TableStyleBean tableStyle = mappaTablesStyle.get(idTable);
					getWidthCols(tableStyle);
					if (StringUtils.isNotBlank(tableStyle.getUnitaMisuraWidthColonne())) {
						Float larghezzaTabellaDaSettare = convertMeasureInMm(tableStyle.getSommaWidthColonne() + tableStyle.getUnitaMisuraWidthColonne());
						if (larghezzaTabellaDaSettare != null && larghezzaTabellaDaSettare < writeablePageWidth) {
							try {
								odfTable.getOdfElement().setProperty(StyleTablePropertiesElement.Align, TableAlignAttribute.Value.LEFT.toString());
								odfTable.setWidth(larghezzaTabellaDaSettare.longValue());
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
					long odfTableWidth = odfTable.getWidth() > 0 ? odfTable.getWidth() : writeablePageWidth.longValue();	
//					odfTable.getOdfElement().setProperty(StyleTablePropertiesElement.MayBreakBetweenRows, "true");
//					odfTable.getOdfElement().setProperty(StyleTablePropertiesElement.KeepWithNext, "auto");
					
					// Calcolo lo stile dei bordi
					String stileBordo = "";
					if (StringUtils.isNotBlank(tableStyle.getBordoTabella())) {
						try {
							int bordoTabella = Integer.parseInt(tableStyle.getBordoTabella());
							stileBordo = (bordoTabella / 4d) + "pt" + " solid #000000";
						} catch (Exception e) {
							stileBordo = tableStyle.getBordoTabella() + " solid #000000";
						}
					} else if (tableStyle.getStileTabella() != null) {
						stileBordo = tableStyle.getStileTabella().getAttribute("border") != null ? tableStyle.getStileTabella().getAttribute("border").toString() : "";
					}
					
					for (int posCol = 0; posCol < odfTable.getColumnCount(); posCol++) {
						OdfTableColumn colonna = odfTable.getColumnByIndex(posCol);
						// setto la larghezza della colonna posCol
						colonna.setUseOptimalWidth(false);
						
						String colWidthUnit = tableStyle.getUnitaMisuraWidthColonne();
						ArrayList<Float> colsWidth = tableStyle.getListaWidthColonne();

						if (colWidthUnit != null && !colWidthUnit.equalsIgnoreCase("invalidColWidth")) {
							if (colsWidth.get(posCol) != null) {
								Float larghezzaColonnaHtml = colsWidth.get(posCol);
								Float percentualeLarghezza = (larghezzaColonnaHtml / tableStyle.getSommaWidthColonne()) ;
								Float larghezzaColonnaOdf = odfTableWidth * percentualeLarghezza;
								colonna.setWidth(larghezzaColonnaOdf.longValue());
							}
						}
						for (int posRow = 0; posRow < colonna.getCellCount(); posRow ++) {
							AttributeSet cellStyle = tableStyle.getListaStiliRow().get(posRow).get(posCol);
							OdfTableCell cella = odfTable.getCellByPosition(posCol, posRow);
							if (cella != null) {
								// Verifico che nella cella non siano presenti interruzioni di pagina
								String testoCella = cella.getDisplayText();
								if (StringUtils.isNotBlank(testoCella) && testoCella.indexOf(PAGE_BREAK) != -1) {
									throw new FreeMarkerCreateDocumentException("Non è possibile inserire interruzioni di pagina nel contenuto di una tabella");
								}
								cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.Padding, "0.15cm");
								
								double cellFontSize = 0;
								if (cellStyle.getAttribute(CSS.Attribute.FONT_SIZE) != null) {
									try{
										cellFontSize = Double.parseDouble(cellStyle.getAttribute(CSS.Attribute.FONT_SIZE).toString().replace("pt", "").replace("px", ""));
									} catch (Exception e) {
										logger.debug("FONT_SIZE della cella non numerico " + cellStyle.getAttribute(CSS.Attribute.FONT_SIZE));
									}
								}
								
								if (cellFontSize != 0) {
									cella.getOdfElement().setProperty(StyleTextPropertiesElement.FontSize, cellFontSize +"pt");
								}
		
								if (cellStyle.getAttribute(CSS.Attribute.TEXT_ALIGN) != null) {
									cella.setHorizontalAlignment(cellStyle.getAttribute(CSS.Attribute.TEXT_ALIGN).toString());
								} else {
									cella.setHorizontalAlignment("justify");
								}
								
								if (cellStyle.getAttribute(CSS.Attribute.VERTICAL_ALIGN) != null) {
									cella.setVerticalAlignment(cellStyle.getAttribute(CSS.Attribute.VERTICAL_ALIGN).toString());
								} else {
									cella.setVerticalAlignment("middle");
								}
								
								if (cellStyle.getAttribute(CSS.Attribute.BACKGROUND_COLOR) != null ) {
									StyleSheet styleSheet = new StyleSheet();
									if (styleSheet.stringToColor(cellStyle.getAttribute(CSS.Attribute.BACKGROUND_COLOR).toString()) != null) {
										Color backgroundColor = new Color(styleSheet.stringToColor(cellStyle.getAttribute(CSS.Attribute.BACKGROUND_COLOR).toString()));
										cella.setCellBackgroundColor(backgroundColor);
									}
								}
								cella.getOdfElement().setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
								cella.getOdfElement().setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
								cella.getOdfElement().setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");
								
								// Setto il bordo delle celle
								try {
									// Ricavo il bordo sinistro
									String borderLeft = "";
									if (cellStyle.getAttribute(CSS.Attribute.BORDER_LEFT) != null && StringUtils.isNotBlank(cellStyle.getAttribute(CSS.Attribute.BORDER_LEFT).toString())) {
										borderLeft = cellStyle.getAttribute(CSS.Attribute.BORDER_LEFT).toString();
									} else {
										borderLeft = convertCssToBorderString(cellStyle.getAttribute(CSS.Attribute.BORDER_LEFT_WIDTH), cellStyle.getAttribute(CSS.Attribute.BORDER_LEFT_STYLE), cellStyle.getAttribute(CSS.Attribute.BORDER_LEFT_COLOR), stileBordo);
									}
									// Ricavo il bordo superiore
									String borderTop = "";
									if (cellStyle.getAttribute(CSS.Attribute.BORDER_TOP) != null && StringUtils.isNotBlank(cellStyle.getAttribute(CSS.Attribute.BORDER_TOP).toString())) {
										borderTop = cellStyle.getAttribute(CSS.Attribute.BORDER_TOP).toString();
									} else {
										borderTop = convertCssToBorderString(cellStyle.getAttribute(CSS.Attribute.BORDER_TOP_WIDTH), cellStyle.getAttribute(CSS.Attribute.BORDER_TOP_STYLE), cellStyle.getAttribute(CSS.Attribute.BORDER_TOP_COLOR), stileBordo);
									}
									// Ricavo il bordo destro
									String borderRight = "";
									if (cellStyle.getAttribute(CSS.Attribute.BORDER_RIGHT) != null && StringUtils.isNotBlank(cellStyle.getAttribute(CSS.Attribute.BORDER_RIGHT).toString())) {
										borderRight = cellStyle.getAttribute(CSS.Attribute.BORDER_RIGHT).toString();
									} else {
										borderRight = convertCssToBorderString(cellStyle.getAttribute(CSS.Attribute.BORDER_RIGHT_WIDTH), cellStyle.getAttribute(CSS.Attribute.BORDER_RIGHT_STYLE), cellStyle.getAttribute(CSS.Attribute.BORDER_RIGHT_COLOR), stileBordo);
									}
									// Ricavo il bordo inferiore
									String borderBottom = "";
									if (cellStyle.getAttribute(CSS.Attribute.BORDER_BOTTOM) != null && StringUtils.isNotBlank(cellStyle.getAttribute(CSS.Attribute.BORDER_BOTTOM).toString())) {
										borderBottom = cellStyle.getAttribute(CSS.Attribute.BORDER_BOTTOM).toString();
									} else {
										borderBottom = convertCssToBorderString(cellStyle.getAttribute(CSS.Attribute.BORDER_BOTTOM_WIDTH), cellStyle.getAttribute(CSS.Attribute.BORDER_BOTTOM_STYLE), cellStyle.getAttribute(CSS.Attribute.BORDER_BOTTOM_COLOR), stileBordo);
									}
									
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderLeft, borderLeft);
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderTop, borderTop);
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderRight, borderRight);
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderBottom, borderBottom);
//									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderLeft, "0.5pt solid black");
//									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderRight, "0.5pt solid black");
//									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderTop, "0.5pt solid #000000");
//									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderBottom, "0.5pt solid #000000");									
								} catch (Exception e) {
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderLeft, "0.5pt solid #000000");
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderRight, "0.5pt solid #000000");
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderTop, "0.5pt solid #000000");
									cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.BorderBottom, "0.5pt solid #000000");
								}
							}
						}
					}
				}
			}
	
			File templateOdtToconvert = File.createTempFile("temp", ".odt");
	
			odtFinale.save(templateOdtToconvert);
			
			return templateOdtToconvert;
		} finally {
			if(odtFinale != null) {
				try {
					odtFinale.close();
				} catch (Exception e) {}
			}
		}
	}

	private static Float convertMeasureInMm(String measureWithUnit) {
		
		if (StringUtils.isNotBlank(measureWithUnit) && measureWithUnit.length() >= 2) {
			String unit;
			String measure;
			if (measureWithUnit.contains("%")) {
				unit = measureWithUnit.substring(measureWithUnit.length() - 1, measureWithUnit.length());
				measure = measureWithUnit.substring(0, measureWithUnit.length() - 1);
			} else {
				unit = measureWithUnit.substring(measureWithUnit.length() - 2, measureWithUnit.length());
				measure = measureWithUnit.substring(0, measureWithUnit.length() - 2);
			}
			switch (unit) {
			case "pt":
				Float measurePtFloat = Utilities.pointsToMillimeters(Float.parseFloat(measure));
				return measurePtFloat;
			case "in":
				Float measureInFloat = Utilities.inchesToMillimeters(Float.parseFloat(measure));
				return measureInFloat;
			case "cm":
				Float measureCmFloat = Float.parseFloat(measure)*10;
				return measureCmFloat;
			case "px":
				float fattoreConversionePxMm = 3F;
				Float measurePxFloat = Float.parseFloat(measure)/fattoreConversionePxMm;
				return measurePxFloat;
			default:
				return null;
			}
		}
		return null;
	}
	
		
	private static String convertCssToBorderString(Object cssBorderWidth, Object cssBorderStyle, Object cssBorderColor, String defaulBorder) {
		
		if (cssBorderWidth == null && cssBorderStyle == null && cssBorderColor == null) {
			return defaulBorder;
		}
		String strCssBorderWidth = cssBorderWidth != null ? cssBorderWidth.toString() : "";
		String strCssBorderStyle = cssBorderStyle != null ? cssBorderStyle.toString() : "";
		String strCssBorderColor = cssBorderColor != null ? cssBorderColor.toString() : "";
		
		String strBorderWidth = "0pt";
		String strBorderStyle = "none";
		String strBorderColor = "black";
		
		if (StringUtils.isBlank(strCssBorderStyle) || "none".equalsIgnoreCase(strCssBorderStyle)) {
			// Non fare nulla
		} else {
			strBorderStyle = strCssBorderStyle;
			strBorderWidth = convertMeasureToPoint(strCssBorderWidth, "0.33pt", false);
			try {
				strBorderColor = Color.toSixDigitHexRGB(strCssBorderColor);
			} catch (Exception e) {
				strBorderColor = "#000000";
			}
		}
		
		return strBorderWidth + " " + strBorderStyle + " " + strBorderColor;
	}
	
	private static String convertMeasureToPoint(String strPixelMeasure, String defaultMeasure, boolean convertForParagraphIndent) {
		try {
			if (StringUtils.isNotBlank(strPixelMeasure) && strPixelMeasure.toUpperCase().endsWith("PX")) {
				strPixelMeasure = strPixelMeasure.substring(0, strPixelMeasure.length() - 2);
				double fattoreConversione = 0.55;
				if (convertForParagraphIndent) {
					fattoreConversione = 0.35;
					fattoreConversione = 0.55;
				}
				return ((Double.valueOf(strPixelMeasure)) * fattoreConversione) + "pt";
			} else if (StringUtils.isNotBlank(strPixelMeasure) && strPixelMeasure.toUpperCase().endsWith("PT")) {
				return strPixelMeasure;
			} else {
				return defaultMeasure;
			}
		}catch (Exception e) {
			return defaultMeasure;
		}
	}
	
	private static Pair<Float, String> estraiValoreMisura(String measureWithUnit) {
		
		if (StringUtils.isNotBlank(measureWithUnit) && measureWithUnit.length() >= 2) {
			String unit;
			String measure;
			int posUltimaCifra = -1;
			for (int pos = 0; pos < measureWithUnit.length(); pos++) {
				if (Character.isDigit(measureWithUnit.charAt(pos))) {
					posUltimaCifra = pos;
				}
			}
			
			if (posUltimaCifra > -1) {
				measure = measureWithUnit.substring(0, posUltimaCifra + 1);
				if (posUltimaCifra + 1 < measureWithUnit.length() - 1) {
					unit = measureWithUnit.substring(posUltimaCifra + 1,  measureWithUnit.length());
				} else {
					unit = "undefinied";
				}
				try {
					return new Pair<Float, String>(Float.parseFloat(measure), unit);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					return null;
				}
			}
		}
		return null;
	}

	private static File cambiaCarattereSezioni(File templateOdtWithValues,File templateOdt, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle, Map<String, AttributeSet> paragraphsStyle, ModelliDocBean modelliDocBean, Map<String, Object> htmlSectionsModel, HttpSession session) throws Exception {
		
		TextDocument odtModello = null;
		TextDocument odtFinale = null;
		try {
			odtModello = TextDocument.loadDocument(templateOdt.getPath());
			odtFinale = TextDocument.loadDocument(templateOdtWithValues.getPath());
		
			//Prendo le varie sezioni dal modello ODT
			Iterator<Section> iteratorSezioni = odtModello.getSectionIterator();
			while(iteratorSezioni.hasNext()) {
				String sezione = iteratorSezioni.next().getName();
				// Devo elaborare solamente le sezioni associate ad una iniezione html
				if (htmlSectionsModel != null && htmlSectionsModel.containsKey(sezione)) {
					// Elimino il primo paragrafo vuoto da ogni sezione
					eliminaPrimoParagrafoVuoto(odtModello, odtFinale, sezione, mappaTablesStyle, listsStyle);
					// Cambio i caratteri della sezione
					cambiaCarattereSezione(odtModello, odtFinale, sezione, mappaTablesStyle, listsStyle, paragraphsStyle, modelliDocBean, session);
				}
			}
	
			File templateOdtToconvert = File.createTempFile("temp", ".odt");
			
			odtFinale.save(templateOdtToconvert);
				
			return templateOdtToconvert;
		} finally {
			if(odtFinale != null) {
				try {
					odtFinale.close();
				} catch (Exception e) {}
			}
			if(odtModello != null) {
				try {
					odtModello.close();
				} catch (Exception e) {}
			}
		}
	}
	
	private static void eliminaPrimoParagrafoVuoto(TextDocument odtModello, TextDocument odtFinale, String nomeSection, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle) throws Exception {
		try {
			Section section = odtFinale.getSectionByName(nomeSection);
			Iterator<Paragraph> iterator = section != null ? section.getParagraphIterator() : null;
			if (iterator!= null && iterator.hasNext()) {
				Paragraph par = iterator.next();
				if (par != null){
				String content = par.getTextContent();
					if (StringUtils.isBlank(content)) {
						// Decommentare per rimuovere  il primo paragrafo vuoto che viene inserito nell'inizezione
						par.remove();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore in eliminaPrimoParagrafoVuoto: " + e.getMessage(), e); 
		}
	}
	
	private static void cambiaCarattereSezione(TextDocument odtModello, TextDocument odtFinale, String nomeSection, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle, Map<String, AttributeSet> paragraphsStyle, ModelliDocBean modelliDocBean, HttpSession session) throws Exception {
		
		String variabileFontName = null;
		String variabileFontSize = null;
		String variabileStyleName = null;
		String variabileStyleSpiazzaturaTop = null;
		String variabileStyleSpiazzaturaBottom = null;
				
		Section section = odtModello.getSectionByName(nomeSection);
		Iterator<Paragraph> iterator = section != null ? section.getParagraphIterator() : null;
		
		if (iterator != null && iterator.hasNext()) {
		
			Paragraph par = iterator.next();
			// Recupero lo span contenete la variabile su cui iniettare il testo
			StyleTextPropertiesElement styleText = null;
			
			if (par.getFrameContainerElement().getFirstChild() != null && par.getFrameContainerElement().getFirstChild() instanceof OdfTextSpan) {
				try {
					OdfTextSpan spam = (OdfTextSpan) par.getFrameContainerElement().getFirstChild();
					NodeList listaNodi = spam.getAutomaticStyle().getChildNodes();
					if (listaNodi != null) {
						 for (int i = 0; i < listaNodi.getLength(); i++) {
							 if (listaNodi.item(i) instanceof StyleTextPropertiesElement) {
								 styleText = (StyleTextPropertiesElement) listaNodi.item(i);
								 variabileFontName = styleText.getStyleFontNameAttribute();
								 variabileFontSize = styleText.getFoFontSizeAttribute();
								 variabileStyleName = spam.getAutomaticStyle().getStyleNameAttribute();
							 }
						 }
					}
				} catch (Exception e) {
					logger.error("Errore nel recupero dello style nella section " + nomeSection, e);
				}
			} 

			if (StringUtils.isBlank(variabileFontName)) {
				if (par.getOdfElement() != null && par.getOdfElement().getAutomaticStyle() != null) {
					try {
						NodeList listaNodi = par.getOdfElement().getAutomaticStyle().getChildNodes();
						if (listaNodi != null) {
							for (int i = 0; i < listaNodi.getLength(); i++) {
								if (listaNodi.item(i) instanceof StyleTextPropertiesElement) {
									styleText = (StyleTextPropertiesElement) listaNodi.item(i);
									variabileFontName = styleText.getStyleFontNameAttribute();
									variabileFontSize = styleText.getFoFontSizeAttribute();
									variabileStyleName = par.getOdfElement().getAutomaticStyle().getStyleNameAttribute();
								}
							 }
						}
					} catch (Exception e) {
						logger.error("Errore nel recupero dello style nella section " + nomeSection, e);
					}
				}
			}

			if (StringUtils.isBlank(variabileFontName)) {
				try {
					variabileFontName = par.getFont().getFamilyName();
				} catch (Exception e1) {
					// Se non sono ancora riuscito a recuperare il fontName vuol dire che viene usato quello predefinito del documento
					// Tento di recuperarlo
					try {
						OdfNamespace fontNameNameSpace = OdfNamespace.newNamespace("fo", "urn:oasis:names:tc:opendocument:xmlns:style:1.0");
			            OdfName fontNameOdfName = OdfName.newName(fontNameNameSpace, "font-name");
			            OdfStyleProperty  fontNameProp = OdfStyleProperty.get(OdfStylePropertiesSet.TextProperties, fontNameOdfName);
			            String styleFamily = par.getOdfElement().getAutomaticStyle().getStyleFamilyAttribute();
						OdfStyleFamily odfStyleFamily = OdfStyleFamily.getByName(styleFamily);
						variabileFontName = odtModello.getDocumentStyles().getDefaultStyle(odfStyleFamily).getProperty(fontNameProp);
					} catch (Exception e2) {
						logger.error("Non sono riuscito a recuperare il fontName della section" + nomeSection + " nel modello " + modelliDocBean.getIdModello() + " " + modelliDocBean.getNomeModello() + " " + modelliDocBean.getNomeFileModello());
					}
				}
			}
			
			//In alcuni casi mi restituisce gli apici e vanno tolti
			if (variabileFontName != null && variabileFontName.contains("'")) {
				try{
					variabileFontName = variabileFontName.substring(1,(variabileFontName.length() - 1));
				} catch (Exception e) {
					logger.error("Errore nel recupero di variabileFontName");
				}
			}
			
			//In alcuni casi mi restituisce 1 o 2 alla fine del nome del carattere e vanno tolti
			if (variabileFontName != null && (((variabileFontName.charAt(variabileFontName.length()-1)=='1') || (variabileFontName.charAt(variabileFontName.length()-1)=='2')) && (variabileFontName.charAt(variabileFontName.length()-2)!=' '))) {
				try {
					variabileFontName = variabileFontName.substring(0,(variabileFontName.length() - 1));
				} catch (Exception e) {
					logger.error("Errore nel recupero di variabileFontName");
				}
			}
			
			if (StringUtils.isBlank(variabileFontSize)) {
				try {
					variabileFontSize = par.getFont().getSize() + "pt";
				} catch (Exception e1) {
					// Se non sono ancora riuscito a recuperare il fontSize vuol dire che viene usato quello predefinito del documento
					try {
						// Tento di recuperarlo
						OdfNamespace fontSizeNameSpace = OdfNamespace.newNamespace("fo", "urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0");
						OdfName fontSizeOdfName = OdfName.newName(fontSizeNameSpace, "font-size");
						OdfStyleProperty  fontSizeProp = OdfStyleProperty.get(OdfStylePropertiesSet.TextProperties, fontSizeOdfName);
						String styleFamily = par.getOdfElement().getAutomaticStyle().getStyleFamilyAttribute();
						OdfStyleFamily odfStyleFamily = OdfStyleFamily.getByName(styleFamily);
						variabileFontSize = odtModello.getDocumentStyles().getDefaultStyle(odfStyleFamily).getProperty(fontSizeProp);
					} catch (Exception e2) {
						logger.error("Non sono riuscito a recuperare il fontSize della section" + nomeSection + " nel modello " + modelliDocBean.getIdModello() + " " + modelliDocBean.getNomeModello() + " " + modelliDocBean.getNomeFileModello());
					}
				}				
			}

			// Se non ho ancora recuperato il font metto quello salvato in DB o quello cablato
			if (StringUtils.isBlank(variabileFontName)) {
				variabileFontName = StringUtils.isNotBlank(VARIABILE_FONT_NAME) ? VARIABILE_FONT_NAME : "Book Antiqua";	
			}
			
			if (StringUtils.isBlank(variabileFontSize)) {
				variabileFontSize = StringUtils.isNotBlank(VARIABILE_FONT_SIZE) ? VARIABILE_FONT_SIZE : "11.0pt";
			}
			
			if (variabileStyleName == null) {
				variabileStyleName = par.getStyleName();
			}
			
			if(par.getOdfElement() != null && par.getOdfElement().getAutomaticStyle() != null) {
				String styleFamily = par.getOdfElement().getAutomaticStyle().getStyleFamilyAttribute();
				OdfStyleFamily odfStyleFamily = OdfStyleFamily.getByName(styleFamily);
				
				Map<OdfStyleProperty, String> mappaStyleParagrafo = par.getOdfElement().getAutomaticStyle().getStyleProperties();
				
				OdfNamespace marginTopNameSpace = OdfNamespace.newNamespace("fo", "urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0");
				OdfName marginTopOdfName = OdfName.newName(marginTopNameSpace, "margin-top");
				OdfStyleProperty  marginTopProp = OdfStyleProperty.get(OdfStylePropertiesSet.ParagraphProperties, marginTopOdfName);
				variabileStyleSpiazzaturaTop = mappaStyleParagrafo.get(marginTopProp);
				
				OdfNamespace marginBottomNameSpace = OdfNamespace.newNamespace("fo", "urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0");
	            OdfName marginBottomOdfName = OdfName.newName(marginBottomNameSpace, "margin-bottom");
	            OdfStyleProperty  marginBottomProp = OdfStyleProperty.get(OdfStylePropertiesSet.ParagraphProperties, marginBottomOdfName);           
				variabileStyleSpiazzaturaBottom = mappaStyleParagrafo.get(marginBottomProp);
			}
			
		}

		Section sectionFinale = odtFinale.getSectionByName(nomeSection);

		// FIXME Se section finale è vuota cosa devo fare? Una volta è capitato ed è andato in errore per null pointer
		if(sectionFinale != null) {
			settaStyleInSection(sectionFinale, variabileFontName, variabileFontSize, variabileStyleName, variabileStyleSpiazzaturaTop, variabileStyleSpiazzaturaBottom, mappaTablesStyle, listsStyle, paragraphsStyle, session);
		}
	}
	
	private static void settaStyleInSection(Section sectionFinale, String variabileFontName, String variabileFontSize, String variabileStyleName, String variabileStyleSpiazzaturaTop, String variabileStyleSpiazzaturaBottom, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle, Map<String, AttributeSet> paragraphsStyle, HttpSession session) throws FreeMarkerCreateDocumentException {
		// Setto lo stile nei paragrafi dela section
		Iterator<Paragraph> iteratorParagrafi = sectionFinale.getParagraphIterator();
		List<Paragraph> listaParagrafiDaRimuovere = new ArrayList<>();
		Paragraph precPar = null;
		while (iteratorParagrafi.hasNext()) {
			Paragraph par = iteratorParagrafi.next();
			//Controllo se il paragrafo non è una riga vuota
			String textContent = par.getTextContent();
			if (!textContent.trim().isEmpty()) {
				
				checkReplaceBR(par.getOdfElement(), session);
				Font font = par.getFont();
				font.setFamilyName(variabileFontName);
				font.setSize(Double.parseDouble(variabileFontSize.replace("pt", "")));
				par.setFont(font);
				
				TextParagraphElementBase odfParagraph = par.getOdfElement();
				odfParagraph.setProperty(StyleTextPropertiesElement.FontName, variabileFontName);
				odfParagraph.setProperty(StyleTextPropertiesElement.FontNameAsian, variabileFontName);
				odfParagraph.setProperty(StyleTextPropertiesElement.FontNameComplex, variabileFontName);
				odfParagraph.setProperty(StyleTextPropertiesElement.FontSize, variabileFontSize + "pt");
				odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, variabileFontSize + "pt");
				odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, variabileFontSize + "pt");
				odfParagraph.setProperty(StyleParagraphPropertiesElement.MarginBottom, variabileStyleSpiazzaturaBottom);
				odfParagraph.setProperty(StyleParagraphPropertiesElement.MarginTop, variabileStyleSpiazzaturaTop);
				odfParagraph.setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
				odfParagraph.setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
				odfParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");
				
				if (odfParagraph.getProperty(OdfParagraphProperties.TextAlign) == null) {
					odfParagraph.setProperty(OdfParagraphProperties.TextAlign, "justify");
				}
				
				// Gestisco l'indentazione dei paragrafi
				if (isAttivaIndentazione(session)) {
					setParagraphIndentation(odfParagraph, paragraphsStyle, sectionFinale.getName());
				}
				
				// Setto lo stile nei sotto paragrafi
				NodeList paragraphChildNodesList = par.getOdfElement().getChildNodes();
				if (paragraphChildNodesList != null) {
					for (int k = 0; k < paragraphChildNodesList.getLength(); k++) {
						try {
							if (paragraphChildNodesList.item(k) != null && paragraphChildNodesList.item(k) instanceof OdfStylableElement) {
								OdfStylableElement odfChildParagraph = (OdfStylableElement) paragraphChildNodesList.item(k);
								odfChildParagraph.setProperty(StyleTextPropertiesElement.FontName, variabileFontName);
								odfChildParagraph.setProperty(StyleTextPropertiesElement.FontNameAsian, variabileFontName);
								odfChildParagraph.setProperty(StyleTextPropertiesElement.FontNameComplex, variabileFontName);
								odfChildParagraph.setProperty(StyleTextPropertiesElement.FontSize, variabileFontSize + "pt");
								odfChildParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, variabileFontSize + "pt");
								odfChildParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, variabileFontSize + "pt");
								odfChildParagraph.setProperty(StyleParagraphPropertiesElement.MarginBottom, variabileStyleSpiazzaturaBottom);
								odfChildParagraph.setProperty(StyleParagraphPropertiesElement.MarginTop, variabileStyleSpiazzaturaTop);
								odfChildParagraph.setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
								odfChildParagraph.setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
								odfChildParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");
							}
						} catch (Exception e) {
							logger.error("Errore nel cambio font nella sezione " + sectionFinale.getName(), e);
							logger.error("nei paragrafi children del testo " + odfParagraph.getTextContent(), e);
	//						return;
						}
					}
				}
				// Inserisco le interruzioni di pagina al posto del placeholder nella variabile NUOVA_PAGINA 
				gestisciPageBreakNelParagrafo(par, precPar, listaParagrafiDaRimuovere);
			}
			precPar = par;
		}
		
		// Elimino tutti paragrafi che servivono solo come placeholder per l'interruzione di pagina
		for (Paragraph par : listaParagrafiDaRimuovere) {
			sectionFinale.removeParagraph(par);
		}
		
		// Setto l'id alle tabelle della sezione, mi serve per applicare gli stili alle tabelle
		Iterator<Table> iteratorTabelle = sectionFinale.getTableList().iterator();
		int count = 0;
		while (iteratorTabelle.hasNext()) {
			count++;
			Table tabella = iteratorTabelle.next();
			String idTable = sectionFinale.getName() + "_Tab" + count;
			tabella.getOdfElement().setAttribute("idTableCKEDITOR", idTable);
			if (StringUtils.isNotBlank(idTable) && mappaTablesStyle != null && !mappaTablesStyle.isEmpty() && mappaTablesStyle.containsKey(idTable) && mappaTablesStyle.get(idTable).getListaStiliRow() != null && !mappaTablesStyle.get(idTable).getListaStiliRow().isEmpty()) {

				for (int rowPos = 0; rowPos < tabella.getRowCount(); rowPos++) {
					AttributeSet cellStyle = mappaTablesStyle.get(idTable).getListaStiliRow().get(rowPos).get(0);

					// Setto l'altezza della riga posRow
					String rowHeightUnit = cellStyle.getAttribute(CSS.Attribute.HEIGHT) != null ? cellStyle.getAttribute(CSS.Attribute.HEIGHT).toString() : null;
					if (StringUtils.isNotBlank(rowHeightUnit) && rowHeightUnit.length() >= 2) { 
						Float rowHeightFloat = convertMeasureInMm(rowHeightUnit);
						Row riga = tabella.getRowByIndex(rowPos);
						if (rowHeightFloat != null) {
							riga.setHeight(rowHeightFloat.doubleValue(), true);
						}
					}
					
					// Setto qui un bordo di default TODO: vedere se si riesce a recuperare da cellStyle
					for (int colPos = 0; colPos < tabella.getColumnCount(); colPos++) {	
						Cell cella = tabella.getCellByPosition(colPos, rowPos);
						if (cella != null) {
							for (int i = 0; i < cella.getOdfElement().getFirstChild().getChildNodes().getLength(); i++) {
								checkReplaceBR((OdfElement)cella.getOdfElement().getFirstChild().getChildNodes().item(i), session);
							}
							Font font = cella.getFont();
							font.setFamilyName(variabileFontName);
							// La size verrà modificata in applicaStileTabelle, per mettere quello originale della tabella iniettata
							font.setSize(Double.parseDouble(variabileFontSize.replace("pt", "")));
							cella.setFont(font);
						}
					}
				}
				
			}
		}
		
		// Setto lo stile nelle liste della section
		/** FIXBUG: Antonio 10/03/2021
		 * 
		 * Bean contenente i due contatori per le liste ordinate <ol> e le non ordinate <ul>
		 * 
		 * è stato modificato come bean perche prima sfalsavano le chiavi delle liste nelle funzioni ricorsive perche passate come valore e l'incremento andava perso
		 * 
		 * */
		CountersListsBean counterListBean = new CountersListsBean();
		int nItemListsModello = 0;
		Iterator<org.odftoolkit.simple.text.list.List> iteratorListe = sectionFinale.getListIterator();
		while (iteratorListe.hasNext()) {
	    	org.odftoolkit.simple.text.list.List lista = iteratorListe.next();
	    	AttributeSet style = null;
	    	if (!lista.getItems().isEmpty()) {
	    		if (lista.getType().equals(ListType.NUMBER)) {
	    			counterListBean.incrementNOrderedListsModello();
	    			Integer oListStart = (Integer) listsStyle.get("%#oListStart" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());
	    			if (oListStart != null && lista.getItem(0) != null) {
	    				lista.getItem(0).setStartNumber(oListStart);
	    			}
	    			style = (AttributeSet) listsStyle.get("%#oListStyle" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());
	    			setStileLista(lista, style, sectionFinale.getOwnerDocument());
	    		} else {
	    			counterListBean.incrementNUnorderedListsModello();
	    			style = (AttributeSet) listsStyle.get("%#uListStyle" + counterListBean.getnUnorderedListsModello() + "#%sect_"+ sectionFinale.getName());
	    			setStileLista(lista, style, sectionFinale.getOwnerDocument());
	    		}
	    	}
	    	// Setto il margine inferiore dell'ultimo item della lista, per staccarla dal testo successivo
	    	ListItem lastListItem = getLastListItem(lista);
	    	if (lastListItem != null) {
	    		OdfTextParagraph listTextParagraph = (OdfTextParagraph) lastListItem.getOdfElement().getFirstChild();
	    		listTextParagraph.setProperty(StyleParagraphPropertiesElement.MarginBottom, variabileStyleSpiazzaturaBottom);
	    	}
	    	
	    	// Setto lo stile negli elementi della lista
	    	java.util.List<ListItem> listaItem = lista.getItems();
	    	// Se lista.getItems() è true non entro nel ciclo, quindi non mi devo preoccupare che style non sia stato inizializzato
	    	for (ListItem listItem : listaItem) {
	    		nItemListsModello++;
	    		setListItemStyleInSection(sectionFinale, listItem, variabileFontName, variabileFontSize, variabileStyleSpiazzaturaBottom, variabileStyleSpiazzaturaTop, listsStyle, counterListBean, nItemListsModello, session, style, 0, 0);
			}
	    }
		// Controllo che nelle liste le interruzioni di pagina siano presenti solamente alla fine di un item
		iteratorListe = sectionFinale.getListIterator();
		while (iteratorListe.hasNext()) {
			controllaPosizionePageBreakNelleListe(iteratorListe.next());
		}
	}
	
	private static ListItem getLastListItem (org.odftoolkit.simple.text.list.List lista) {
		if (!lista.getItems().isEmpty()) {
			ListItem item = lista.getItem(lista.getItems().size() - 1);
			Iterator<org.odftoolkit.simple.text.list.List> subListIterator = item.getListIterator();
			if (subListIterator.hasNext()) {
				org.odftoolkit.simple.text.list.List subList = null;
				while (subListIterator.hasNext()) {
					 subList = subListIterator.next();
				}
				return getLastListItem(subList);
			} else {
				return item;
			}
		} else {
			return null;
		}
	}
	
	private static void controllaPosizionePageBreakNelleListe(org.odftoolkit.simple.text.list.List lista) throws FreeMarkerCreateDocumentException {
		java.util.List<ListItem> listaItem = lista.getItems();
		for (ListItem listItem : listaItem) {
			NodeList listaNodiListItem = listItem.getOdfElement().getChildNodes();
			if (listaNodiListItem != null) {
				for (int i = 0; i < listaNodiListItem.getLength(); i++) {
					Node nodoListItem = listaNodiListItem.item(i);
					if (nodoListItem instanceof OdfTextParagraph) {
						OdfTextParagraph odfTextParagraphNodoListItem = (OdfTextParagraph) nodoListItem;
						String contenutoNodo = odfTextParagraphNodoListItem.getTextContent();
						if (StringUtils.isNotBlank(contenutoNodo) && contenutoNodo.indexOf(PAGE_BREAK) != -1) {
							if (contenutoNodo.endsWith(PAGE_BREAK)) {
								// Controllo che i nodi successivi non abbiano testo
								for (int j = i + 1; j < listaNodiListItem.getLength(); j++) {
									Node nodoSuccessivoListItem = listaNodiListItem.item(j);
									if (nodoSuccessivoListItem instanceof OdfTextParagraph) {
										String testo = ((OdfTextParagraph) nodoSuccessivoListItem).getTextContent();
										if (StringUtils.isNotBlank(testo)) {
											throw new FreeMarkerCreateDocumentException("Non è possibile inserire interruzioni di pagina all''interno di un paragrafo di un elenco puntato");
										}
									}
								}
								odfTextParagraphNodoListItem.setTextContent(contenutoNodo.replaceAll(PAGE_BREAK, ""));
								odfTextParagraphNodoListItem.setProperty(StyleParagraphPropertiesElement.BreakAfter, "page");
							} else {
								throw new FreeMarkerCreateDocumentException("Non è possibile inserire interruzioni di pagina all''interno di un paragrafo di un elenco puntato");
							}
						}
					}
					
				}
			}
			// Controllo che nelle liste le interruzioni di pagina siano presenti solamente alla fine di un item
			Iterator<org.odftoolkit.simple.text.list.List> iteratoreSottoListe = listItem.getListIterator();
			if (iteratoreSottoListe != null) {
				while  (iteratoreSottoListe.hasNext()) {
					controllaPosizionePageBreakNelleListe(iteratoreSottoListe.next());
				}
			}
		}
	}
	
	// Funzione che applica l'indentazione del paragrafo
	private static void setParagraphIndentation(TextParagraphElementBase paragraph, Map<String, AttributeSet> paragraphsStyle, String sectionName) {
		if (paragraphsStyle != null && !paragraphsStyle.isEmpty()) {
			String paragraphTextContent = paragraph.getTextContent();
			int parIdStartIndex = paragraphTextContent.indexOf("%#");
			int parIdEndIndex = -1;
			boolean termineEleaborazioneParagrafo = false;
			while (!termineEleaborazioneParagrafo) {
				if (parIdStartIndex > -1) {
					parIdEndIndex = paragraphTextContent.indexOf(sectionName, parIdStartIndex);
					if (parIdEndIndex > -1) {
						String parId = paragraphTextContent.substring(parIdStartIndex, parIdEndIndex + sectionName.length());
						if (paragraphsStyle.containsKey(parId)) {
							TextImpl textNodeWithParId = getTextImplNodeWithParagraphKey(paragraph, parId);
							if (textNodeWithParId != null) {
								textNodeWithParId.setTextContent(textNodeWithParId.getTextContent().replace(parId, ""));
							} else {
								paragraph.setTextContent(paragraphTextContent.replace(parId, ""));
							}
							AttributeSet paragraphAttributeSet = paragraphsStyle.get(parId);
							Object marginLeft = paragraphAttributeSet.getAttribute(CSS.Attribute.MARGIN_LEFT);
							if (marginLeft != null) {
								paragraph.setProperty(StyleParagraphPropertiesElement.MarginLeft, convertMeasureToPoint(marginLeft.toString(), "0pt", true));
							}
							termineEleaborazioneParagrafo = true;
						} else {
							parIdStartIndex = paragraphTextContent.indexOf("%#", parIdStartIndex + 1);
						}
					} else {
						termineEleaborazioneParagrafo = true;
					}
				} else {
					termineEleaborazioneParagrafo = true;
				}
			}
		}
	}
	
	private static org.apache.xerces.dom.TextImpl getTextImplNodeWithParagraphKey(Node nodeItem, String paragraphKey) {
		org.apache.xerces.dom.TextImpl result = null;
		if (nodeItem instanceof org.apache.xerces.dom.TextImpl && StringUtils.isNotBlank(nodeItem.getTextContent()) && nodeItem.getTextContent().indexOf(paragraphKey) != -1) {
			return (org.apache.xerces.dom.TextImpl) nodeItem;
		} else {
			if (nodeItem.getChildNodes() != null && nodeItem.getChildNodes().getLength() > 0) {
				for (int i = 0; i < nodeItem.getChildNodes().getLength(); i++) {
					Node childNode = nodeItem.getChildNodes().item(i);
					if (childNode != null && childNode instanceof org.apache.xerces.dom.TextImpl && StringUtils.isNotBlank(nodeItem.getTextContent()) && nodeItem.getTextContent().indexOf(paragraphKey) != -1) {
						result = (org.apache.xerces.dom.TextImpl) childNode;
						break;
					} else if (childNode != null) {
						return getTextImplNodeWithParagraphKey(childNode, paragraphKey);
					}
				}

			}
		}
		return result;
	}
	
	//Funzione che applica il carattere alle liste delle liste ricorsivamente
	private static void setListItemStyleInSection(Section sectionFinale, ListItem listItem, String variabileFontName, String variabileFontSize, String variabileStyleSpiazzaturaBottom, String variabileStyleSpiazzaturaTop, Map<String, Object> listsStyle, CountersListsBean counterListBean, int nItemListsModello, HttpSession session, AttributeSet stileLista, int margineSinistroListaPadre, int livelloLista) {

		if (listItem != null && listItem.getOdfElement() != null && listItem.getOdfElement().getFirstChild() != null && listItem.getOdfElement().getFirstChild() instanceof OdfTextParagraph) {
			
			for (int i = 0; i < listItem.getOdfElement().getFirstChild().getChildNodes().getLength(); i++) {
				checkReplaceBR((OdfElement)listItem.getOdfElement().getFirstChild().getChildNodes().item(i), session);
			}
			
			OdfTextParagraph listTextParagraph = (OdfTextParagraph) listItem.getOdfElement().getFirstChild();

			AttributeSet itemStyle = (AttributeSet)listsStyle.get("%#ListItem" + nItemListsModello + "#%sect_"+ sectionFinale.getName());
			if (itemStyle.getAttribute(CSS.Attribute.TEXT_ALIGN) != null) {
				listTextParagraph.setProperty(OdfParagraphProperties.TextAlign, itemStyle.getAttribute(CSS.Attribute.TEXT_ALIGN).toString());
			} else {
				listTextParagraph.setProperty(OdfParagraphProperties.TextAlign, "justify");
			}
			
			if (isAttivaIndentazione(session)) {
				// Prendo il margine della lista che sto elaborando dal suo stile html
				String margineSinistroListaCorrente = stileLista.getAttribute(CSS.Attribute.MARGIN_LEFT) != null ? stileLista.getAttribute(CSS.Attribute.MARGIN_LEFT).toString() : null;
				if (StringUtils.isNotBlank(margineSinistroListaCorrente) && margineSinistroListaCorrente.endsWith("px")) {
					// Se la lista corrente ha un margine lo sommo a quello della lista padre
					int margineSinistroListaCorrenteInt = margineSinistroListaPadre + Integer.parseInt(margineSinistroListaCorrente.substring(0, margineSinistroListaCorrente.length() - 2));
					if (livelloLista == 0) {
						margineSinistroListaCorrenteInt += 20;
					}
					margineSinistroListaCorrente = margineSinistroListaCorrenteInt + "px";
					// Aggiorno il margine della lista padre per elaborare le sottoliste
					margineSinistroListaPadre = margineSinistroListaCorrenteInt;
				} else {
					// Se la lista corrente non ha un margine uso quello della lista padre
					int margineSinistroListaCorrenteInt = margineSinistroListaPadre + 20;
					margineSinistroListaCorrente = margineSinistroListaCorrenteInt + "px";
					margineSinistroListaPadre = margineSinistroListaCorrenteInt;
				}
				// Setto il margine sinistro alla lista
				listTextParagraph.setProperty(StyleParagraphPropertiesElement.MarginLeft, convertMeasureToPoint(margineSinistroListaCorrente.toString(), "0pt", true));
			}
			
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontName, variabileFontName);
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontNameAsian, variabileFontName);
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontNameComplex, variabileFontName);
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontSize, variabileFontSize + "pt");
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, variabileFontSize + "pt");
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, variabileFontSize + "pt");
//			listTextParagraph.setProperty(StyleParagraphPropertiesElement.MarginBottom, variabileStyleSpiazzaturaBottom);
//			listTextParagraph.setProperty(StyleParagraphPropertiesElement.MarginTop, variabileStyleSpiazzaturaTop);
			listTextParagraph.setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
			listTextParagraph.setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
			listTextParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");

			Iterator<org.odftoolkit.simple.text.list.List> iteratorListe = listItem.getListIterator();
			while (iteratorListe.hasNext()) {
				org.odftoolkit.simple.text.list.List lista = iteratorListe.next();
				AttributeSet style = null;
				if (lista.getType().equals(ListType.NUMBER)) {
					counterListBean.incrementNOrderedListsModello();
					Integer oListStart = (Integer) listsStyle.get("%#oListStart" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());
					if (oListStart != null && lista.getItem(0) != null) {
						lista.getItem(0).setStartNumber(oListStart);
					}
					style = (AttributeSet) listsStyle.get("%#oListStyle" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());

					setStileLista(lista, style, sectionFinale.getOwnerDocument());
				} else {
					counterListBean.incrementNUnorderedListsModello();
					style = (AttributeSet) listsStyle.get("%#uListStyle" + counterListBean.getnUnorderedListsModello() + "#%sect_"+ sectionFinale.getName());

					setStileLista(lista, style, sectionFinale.getOwnerDocument());
				}

				java.util.List<ListItem> listaItem = lista.getItems();
				for (ListItem subListItem : listaItem) {
					nItemListsModello++;
					setListItemStyleInSection(sectionFinale, subListItem, variabileFontName, variabileFontSize, variabileStyleSpiazzaturaBottom, variabileStyleSpiazzaturaTop, listsStyle, counterListBean, nItemListsModello, session, style, margineSinistroListaPadre, livelloLista + 1);
				}
			}
		}
	}
	
	private static void setStileLista(org.odftoolkit.simple.text.list.List lista, AttributeSet styleLista, org.odftoolkit.simple.Document document) {
		String style = styleLista.getAttribute(CSS.Attribute.LIST_STYLE_TYPE) != null ? styleLista.getAttribute(CSS.Attribute.LIST_STYLE_TYPE).toString() : "";
		switch (style) {
		case Value.UPPER_ROMAN:
			lista.setDecorator(new NumberedRomanUpperDecorator(document));	
			break;
		case Value.LOWER_ROMAN:
			lista.setDecorator(new NumberedRomanLowerDecorator(document));	
			break;
		case Value.LOWER_ALPHA:
			lista.setDecorator(new NumberedAlphaLowerDecorator(document));	
			break;
		case Value.UPPER_ALPHA:
			lista.setDecorator(new NumberedAlphaUpperDecorator(document));	
			break;
		case Value.LOWER_GREEK:
			lista.setDecorator(new NumberedGreekLowerDecorator(document));	
			break;
		case Value.DISC:
			lista.setDecorator(new DiscDecorator(document));	
			break;
		case Value.CIRCLE:
			lista.setDecorator(new CircleDecorator(document));	
			break;
		case Value.SQUARE:
			lista.setDecorator(new SquareDecorator(document));	
			break;
		default:
			if (lista.getType().equals(ListType.NUMBER)) {
				lista.setDecorator(new NumberDecorator(document));
			} else {
				switch (lista.getLevel()) {
				case 1:
					lista.setDecorator(new DiscDecorator(document));
					break;
				case 2:
					lista.setDecorator(new CircleDecorator(document));
					break;
				case 3:
				default:
					lista.setDecorator(new SquareDecorator(document));
					break;
				}
			}
			break;
		}
	}
	
	private static void mergeHtmlSections(String nomeVariabile, File fileOdt, File fileOdtWithValues) throws FreeMarkerMergeHtmlSectionsException {
		OdfPackage odfPackage = null;
		OdfDocument odfDocument = null;			
		TextSectionElement sectionOrig = null;
		try {	        	
        	odfPackage = OdfPackage.loadPackage(fileOdt);  
        	odfDocument = OdfTextDocument.loadDocument(odfPackage);
        	if (getSectionByName(odfDocument, nomeVariabile) == null) {
        		// Probabilmente ho profilato per errore una variabile come testo html, e nel modello non è presente una section con quel nome
        		logger.error("Errore durante l'esecuzione del metodo mergeHtmlSections: Sezione " + nomeVariabile + " non presente nel modello");
        		throw new Exception("La variabile " + nomeVariabile + " è profilata come testo html, ma nel modello non è presente la relativa sezione");
        	}
        	sectionOrig = (TextSectionElement) getSectionByName(odfDocument, nomeVariabile).cloneNode(true);
        } catch(Exception e){
        	logger.error("Errore durante l'esecuzione del metodo mergeHtmlSections: " + e.getMessage(), e);
        	throw new FreeMarkerMergeHtmlSectionsException(e.getMessage(), e);
        } finally {
        	if(odfDocument != null) {
        		try {
        			odfDocument.close();
        		} catch (Exception e) {}
        	}        	
        	if(odfPackage != null) {
        		try {
        			odfPackage.close();
        		} catch (Exception e) {}    		
        	}        	
        }			
		try {	        
			odfPackage = OdfPackage.loadPackage(fileOdtWithValues);  
        	odfDocument = OdfTextDocument.loadDocument(odfPackage);		
        	TextSectionElement section = getSectionByName(odfDocument, nomeVariabile);
        	// Se section è null vuol dire che nel primo step di iniezione è stata tolta (ad esempio si trova dentro un blocco if che non
        	// deve essere visualizzato)
        	if (section != null) {
        		Node importedNode = odfDocument.getContentDom().importNode(sectionOrig, true);
        		section.getParentNode().replaceChild(importedNode, section);
        	}
			odfDocument.save(fileOdtWithValues);
        } catch(Exception e){
        	logger.error("Errore durante l'esecuzione del metodo mergeHtmlSections: " + e.getMessage(), e);
        	throw new FreeMarkerMergeHtmlSectionsException(e.getMessage(), e);
        } finally {
        	if(odfDocument != null) {
        		try {
        			odfDocument.close();
        		} catch (Exception e) {}    		
        	}        	
        	if(odfPackage != null) {
        		try {
        			odfPackage.close();
        		} catch (Exception e) {}         		
        	}        	
        }		
	}
	
	private static void iniettaDocxInHtmlSections(Map<String, String> fileSectionsModel, File fileOdtWithValues, HttpSession session) throws Exception {
		// Pulisco le sezioni dove inittare i dati
		OdfPackage odfPackage = null;
		OdfDocument odfDocument = null;			
		TextDocument target = TextDocument.loadDocument(fileOdtWithValues);
		try {	    
			for(String nomeVariabile : fileSectionsModel.keySet()) {
				Section sezione = target.getSectionByName(nomeVariabile);
				// Se section è null vuol dire che nel primo step di iniezione è stata tolta (ad esempio si trova dentro un blocco if che non
	        	// deve essere visualizzato)
	        	if (sezione != null) {
	        		Iterator<Paragraph> iteratore = sezione.getParagraphIterator();
					if (iteratore != null) {
						while (iteratore.hasNext()) {
							sezione.removeParagraph(iteratore.next());
						}
					}
					sezione.addParagraph(nomeVariabile + "_ODTInjection");
	        	}
        	}
			target.save(fileOdtWithValues);
        } catch(Exception e){
        	logger.error("Errore durante l'esecuzione del metodo iniettaOdtInHtmlSections: " + e.getMessage(), e);
        	throw new FreeMarkerMergeHtmlSectionsException(e.getMessage(), e);
        } finally {
        	if(odfDocument != null) {
        		try {
        			odfDocument.close();
        		} catch (Exception e) {}    		
        	}        	
        	if(odfPackage != null) {
        		try {
        			odfPackage.close();
        		} catch (Exception e) {}         		
        	}        	
        }
		
		// Inietto i file odt
		XComponentContext lXLocalContext = com.sun.star.comp.helper.Bootstrap.createInitialComponentContext(null);
        XMultiComponentFactory lXLocalServiceManager = lXLocalContext.getServiceManager();
        Object lUrlResolver  = lXLocalServiceManager.createInstanceWithContext("com.sun.star.bridge.UnoUrlResolver", lXLocalContext);
        XUnoUrlResolver xUnoUrlResolver = (XUnoUrlResolver) UnoRuntime.queryInterface(XUnoUrlResolver.class,lUrlResolver);
        boolean openOfficeConnected = false;
        Object initialObject = null;
        OpenOfficeConfiguration lOpenOfficeConfiguration = (OpenOfficeConfiguration)SpringAppContext.getContext().getBean("officemanager");
        Iterator<OpenOfficeInstance> openOfficeInstanceIterator = lOpenOfficeConfiguration.getInstances().iterator();
        while (openOfficeInstanceIterator.hasNext() && !openOfficeConnected) {
        	OpenOfficeInstance lOpenOfficeInstance = openOfficeInstanceIterator.next();
        	String openOfficeHost = lOpenOfficeInstance.getHost();
        	List<String> openOfficePortList = lOpenOfficeInstance.getPortList();
        	for (String openOfficePort : openOfficePortList) {
		        try {
		        	initialObject = xUnoUrlResolver.resolve("uno:socket,host=" + openOfficeHost + ",port=" + openOfficePort + ";urp;StarOffice.ServiceManager");
		        	openOfficeConnected = true;
		        	break;
		        } catch (Exception e) {
		        	logger.error("Conessione openoffice fallita su host " + openOfficeHost + " e porta " + openOfficePort, e);
		        }
			}
		}
        if (!openOfficeConnected || initialObject == null) {
        	throw new FreeMarkerCreateDocumentException("Errore nell''iniezione del documento nel modello");
        }
        XPropertySet lXPropertySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, initialObject);
        XComponentContext lXComponentContext = (XComponentContext) UnoRuntime.queryInterface(XComponentContext.class, lXPropertySet.getPropertyValue("DefaultContext"));

        XMultiComponentFactory lXMultiComponentFactory = lXComponentContext.getServiceManager();
        Object lDesktop = lXMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", lXComponentContext);
        
        XComponentLoader lXComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, lDesktop);
        
//		XMultiServiceFactory xmultiservicefactoryServiceManager = com.sun.star.comp.helper.Bootstrap.createSimpleServiceManager();
//
//		Object objectConnector = xmultiservicefactoryServiceManager.createInstance("com.sun.star.connection.Connector");
//		XConnector xconnector = (XConnector) UnoRuntime.queryInterface(XConnector.class, objectConnector);
//		XConnection xconnection = xconnector.connect("socket, host=localhost,port=8100");
//		String stringRootOid = "StarOffice.NamingService";
//		com.sun.star.uno.IBridge ibridge = UnoRuntime.getBridgeByName("java", null, "remote", null, new Object[] { "urp", xconnection, null });
//		Object objectInitial = ibridge.mapInterfaceFrom(stringRootOid, new com.sun.star.uno.Type(XInterface.class));
//		XNamingService xnamingservice = (XNamingService) UnoRuntime.queryInterface(XNamingService.class, objectInitial);
//		if (xnamingservice != null) {
//			Object objectServiceManager = xnamingservice.getRegisteredObject("StarOffice.ServiceManager");
//			XMultiServiceFactory xmultiservicefactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, objectServiceManager);
//		}
        	
		XComponent lXComponent = lXComponentLoader.loadComponentFromURL(toUrl(fileOdtWithValues), "_blank", 0, new PropertyValue[0]);
        
        XTextDocument lXTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, lXComponent);
                
        for(String nomeVariabile : fileSectionsModel.keySet()) {
        	
        	XSearchable lXReplaceable = (XSearchable) UnoRuntime.queryInterface(XSearchable.class, lXTextDocument);
			XSearchDescriptor lXSearchDescriptor = lXReplaceable.createSearchDescriptor();
			lXSearchDescriptor.setSearchString(nomeVariabile + "_ODTInjection");
			XInterface lXInterface = (XInterface) lXReplaceable.findFirst(lXSearchDescriptor);
			if (lXInterface != null) {
				// Inietto il documento ODT
				XTextRange lXTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, lXInterface);
				XTextCursor lXTextCursor = lXTextRange.getText().createTextCursorByRange(lXTextRange);
				lXTextCursor.collapseToEnd();
				XDocumentInsertable lXDocumentInsertable = (XDocumentInsertable) UnoRuntime.queryInterface(XDocumentInsertable.class, lXTextCursor);
				// Recupero il file da iniettare
				File fileDaIniettare = StorageImplementation.getStorage().extractFile(fileSectionsModel.get(nomeVariabile));
				MimeTypeFirmaBean infoFile = new InfoFileUtility().getInfoFromFile(fileDaIniettare.toURI().toString(), null, false, null);
				// Converto il file in formato odt
				File odtTempFile = File.createTempFile("temp", ".odt");
				OpenOfficeConverter.newInstance().convertByOutExt(fileDaIniettare, infoFile.getMimetype(), odtTempFile, "odt");
				lXDocumentInsertable.insertDocumentFromURL(odtTempFile.toURI().toString(), new PropertyValue[0]);
				// Elimino i bookmark inseriti per l'iniezione da documento odt 
				lXReplaceable = (XReplaceable) UnoRuntime.queryInterface(XReplaceable.class, lXTextDocument);
				lXSearchDescriptor = lXReplaceable.createSearchDescriptor();
				lXSearchDescriptor.setSearchString(nomeVariabile + "_ODTInjection");
				lXInterface = (XInterface) lXReplaceable.findFirst(lXSearchDescriptor);
				lXTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, lXInterface);
				lXTextCursor = lXTextRange.getText().createTextCursorByRange(lXTextRange);
				lXTextCursor.setString("");
			}
        }
        
        //Salvo il documento
        XStorable xStore = (XStorable) UnoRuntime.queryInterface(com.sun.star.frame.XStorable.class, lXComponent); 
        xStore.store();
        
        XModel lXModel = (com.sun.star.frame.XModel) UnoRuntime.queryInterface(XModel.class, lXComponent);
        if (lXModel != null) {
			XCloseable xCloseable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, lXModel);
			if (xCloseable != null) {
				try {
					xCloseable.close(true);
				} catch (Exception exCloseVeto) {
				}
			} else {
				try {
					XComponent lXComponent2 = (XComponent) UnoRuntime.queryInterface(XComponent.class, lXModel);
					lXComponent2.dispose();
				} catch (Exception exModifyVeto) {
				}
			}
		}

	}
	
	private static TextSectionElement getSectionByName(OdfDocument odfDocument, String name) {
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
	      OfficeMasterStylesElement masterStyle = OdfElement.findFirstChildNode(OfficeMasterStylesElement.class, root);
	      xpath = odfDocument.getStylesDom().getXPath();
	      element = (TextSectionElement) xpath.evaluate(".//text:section[@text:name=\"" + name + "\"]", masterStyle, XPathConstants.NODE);
	      if (element != null) {
	    	  return element;
	      }
	    } catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	private static File createPdfFromOdt(File templateOdtWithValues, HttpSession session) throws Exception {
		
		File templatePdfWithValues = File.createTempFile("temp", ".pdf");
		
		boolean generaPdfa = ParametriDBUtil.getParametroDBAsBoolean(session, "GENERAZIONE_DA_MODELLO_ABILITA_PDFA");

		try {
			OpenOfficeConverter.newInstance().convertByOutExt(templateOdtWithValues, "application/vnd.oasis.opendocument.text", templatePdfWithValues, "pdf");
		} catch (Exception e) {
			logger.error("Errore durante la conversione con OpenOffice", e);
 			throw new StoreException("Il servizio di conversione in pdf del testo è momentaneamente non disponibile. Se il problema persiste contattare l'assistenza");
		}
		
		if (generaPdfa) {
            templatePdfWithValues = PdfUtility.convertiPdfToPdfA3U(templatePdfWithValues);
        }

		return templatePdfWithValues;
	}
	
	public static Map<String, Object> createMapToFillTemplate(ModelliDocBean bean, HttpSession session) throws BadElementException, ParseException, IOException{
		
		LinkedHashMap<String, AssociazioniAttributiCustomBean> associazioniAttributiCustomMap = getAssociazioniAttributiCustomMap(bean);
		
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new LinkedHashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new LinkedHashMap<String, String>();
		Map<String, String> colonneListe = bean.getColonneListe() != null ? bean.getColonneListe() : new LinkedHashMap<String, String>();
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		List<String> nomiSottoVariabiliLista = new ArrayList<String>();
		
		for (String nomeVariabile : associazioniAttributiCustomMap.keySet()) {
			AssociazioniAttributiCustomBean associazione = associazioniAttributiCustomMap.get(nomeVariabile);
			String nomeAttributoCustom = associazione != null && StringUtils.isNotBlank(associazione.getNomeAttributoCustom()) && "attributoCustom".equalsIgnoreCase(associazione.getTipoAssociazioneVariabileModello()) ? associazioniAttributiCustomMap.get(nomeVariabile).getNomeAttributoCustom() : nomeVariabile;
			String tipo = (String) tipiValori.get(nomeAttributoCustom);
			if ("LISTA".equals(tipo) || (associazione.getFlgRipetibile() != null && Boolean.valueOf(associazione.getFlgRipetibile()))) {
				List<Map> valoreLista = valori.get(nomeAttributoCustom) != null ? (List<Map>) valori.get(nomeAttributoCustom) : new ArrayList<Map>();
				String prefix = nomeAttributoCustom + ".";				
				if(isTrue(associazioniAttributiCustomMap.get(nomeVariabile).getFlgComplex())) {
					List<Map<String, Object>> valoreListaModel = new ArrayList<Map<String, Object>>();
					for(int r = 0; r < valoreLista.size(); r++) {
						Map<String, Object> valoriColonneRigaModel = new LinkedHashMap<String, Object>();
						java.util.HashSet<Object> cols = new java.util.HashSet<Object>();
						for(String col : colonneListe.keySet()) {
							if(col.startsWith(prefix)) {
								cols.add(col.substring(prefix.length()));
							}
						}						
						for(Object c : cols) {
							String numeroColonna = (String) c;
							String nomeAttributoCustomColonna = colonneListe.get(nomeAttributoCustom + "." + numeroColonna);
							List<String> listaNomiVariabiliColonna = getListaNomiVariabiliFromNomeAttributoCustom(nomeAttributoCustomColonna, bean, associazione.getAliasVariabileModello());	
							if(listaNomiVariabiliColonna != null) {
								for (int n = 0; n < listaNomiVariabiliColonna.size(); n++) {
									String nomeVariabileColonna = listaNomiVariabiliColonna.get(n);
									Object valoreVariabileColonna = null; 
									String tipoColonna = (String) tipiValori.get(nomeAttributoCustom + "." + numeroColonna);
									if(valoreLista.get(r).get(c) != null) {	
										if ("DATE".equals(tipoColonna)) {
											valoreVariabileColonna = getDateModelValue(valoreLista.get(r).get(c));
										} else if ("DATETIME".equals(tipoColonna)) {
											valoreVariabileColonna = getDateTimeModelValue(valoreLista.get(r).get(c));
										} else if ("CHECK".equals(tipoColonna)) {
											valoreVariabileColonna = getCheckModelValue(valoreLista.get(r).get(c));
										} else if ("CKEDITOR".equals(tipoColonna)) {
											String htmlFixedTable = fixHTMLCKEDITOR(valoreLista.get(r).get(c), session);
											valoreVariabileColonna = getHtmlModelValue(htmlFixedTable);
//											valoreVariabileColonna = getHtmlModelValue(valoreLista.get(r).get(c));
										} else {
											valoreVariabileColonna = getTextModelValue(valoreLista.get(r).get(c));
										}
									} else {
										valoreVariabileColonna = "";
									}
									
									if(valoreVariabileColonna != null && !"".equals(valoreVariabileColonna)) {					
										Boolean flgBarcodeColonna = associazioniAttributiCustomMap.get(nomeVariabileColonna) != null ? associazioniAttributiCustomMap.get(nomeVariabileColonna).getFlgBarcode() : null;	
										Boolean flgImageColonna = associazioniAttributiCustomMap.get(nomeVariabileColonna) != null ? associazioniAttributiCustomMap.get(nomeVariabileColonna).getFlgImage() : null;
										if(flgBarcodeColonna != null && flgBarcodeColonna) {
											String tipoBarcode = associazioniAttributiCustomMap.get(nomeVariabileColonna) != null ? associazioniAttributiCustomMap.get(nomeVariabileColonna).getTipoBarcode() : null;						
											String strBarcode = (String) valoreVariabileColonna;
											ImpostazioniBarcodeBean impostazioniBarcodeBean = getImpostazioniImmagineBarCode(tipoBarcode);
											valoreVariabileColonna = BarcodeUtility.getImageProvider(strBarcode, impostazioniBarcodeBean);
										} else if (flgImageColonna != null && flgImageColonna){
											valoreVariabileColonna = getImageModelValue(valoreVariabileColonna, session);
										}
									}
									valoriColonneRigaModel.put(nomeVariabileColonna.substring(nomeVariabileColonna.indexOf(".") + 1), valoreVariabileColonna);
									
									if(!nomiSottoVariabiliLista.contains(nomeVariabileColonna)) {
										nomiSottoVariabiliLista.add(nomeVariabileColonna);
									}
								}
							}
							
						}					
						valoreListaModel.add(valoriColonneRigaModel);
					}
					model.put(nomeVariabile, valoreListaModel);
				} else {
					List<Object> valoreListaModel = new ArrayList<Object>();
					for(int r = 0; r < valoreLista.size(); r++) {
						Object valoreVariabileColonna = null; 
						String tipoColonna = (String) tipiValori.get(nomeAttributoCustom);						
						if(valoreLista.get(r).get("1") != null) {	
							if ("DATE".equals(tipoColonna)) {
								valoreVariabileColonna = getDateModelValue(valoreLista.get(r).get("1"));
							} else if ("DATETIME".equals(tipoColonna)) {
								valoreVariabileColonna = getDateTimeModelValue(valoreLista.get(r).get("1"));
							} else if ("CHECK".equals(tipoColonna)) {
								valoreVariabileColonna = getCheckModelValue(valoreLista.get(r).get("1"));
							} else if ("CKEDITOR".equals(tipoColonna)) {
								String htmlFixedTable = fixHTMLCKEDITOR(valoreLista.get(r).get("1"), session);
								valoreVariabileColonna = getHtmlModelValue(htmlFixedTable);
//								valoreVariabileColonna = getHtmlModelValue(valoreLista.get(r).get("1"));
							} else {
								valoreVariabileColonna = getTextModelValue(valoreLista.get(r).get("1"));
							}
						} else {
							valoreVariabileColonna = "";
						}
						valoreListaModel.add(valoreVariabileColonna);
					}
					model.put(nomeVariabile, valoreListaModel);
				}
			} else {
				Object valoreVariabile = null;
				if(valori.get(nomeAttributoCustom) != null) {			
					if ("DATE".equals(tipo)) {
						valoreVariabile = getDateModelValue(valori.get(nomeAttributoCustom));
					} else if ("DATETIME".equals(tipo)) {	
						valoreVariabile = getDateTimeModelValue(valori.get(nomeAttributoCustom));
					} else if ("CHECK".equals(tipo)) {
						valoreVariabile = getCheckModelValue(valori.get(nomeAttributoCustom));
					} else if ("CKEDITOR".equals(tipo)) {
						String htmlFixedTable = fixHTMLCKEDITOR(valori.get(nomeAttributoCustom), session);
						valoreVariabile = getHtmlModelValue(htmlFixedTable);
					} else {
						valoreVariabile = getTextModelValue(valori.get(nomeAttributoCustom));
					}
				} else {
					valoreVariabile = "";
				}
				Boolean flgBarcode = associazioniAttributiCustomMap.get(nomeVariabile) != null ? associazioniAttributiCustomMap.get(nomeVariabile).getFlgBarcode() : null;
				Boolean flgImage = associazioniAttributiCustomMap.get(nomeVariabile) != null ? associazioniAttributiCustomMap.get(nomeVariabile).getFlgImage() : null;
				if(flgBarcode != null && flgBarcode && valoreVariabile != null && StringUtils.isNotBlank(valoreVariabile.toString())) {
					String tipoBarcode = associazioniAttributiCustomMap.get(nomeVariabile) != null ? associazioniAttributiCustomMap.get(nomeVariabile).getTipoBarcode() : null;						;
					String strBarcode = (String) valoreVariabile;
					ImpostazioniBarcodeBean impostazioniBarcodeBean = getImpostazioniImmagineBarCode(tipoBarcode);
					valoreVariabile = BarcodeUtility.getImageProvider(strBarcode, impostazioniBarcodeBean);
				} else if (flgImage != null && flgImage){
					valoreVariabile = getImageModelValue(valoreVariabile, session);
				}
				model.put(nomeVariabile, valoreVariabile);
			}
		}	
		
		for(String nomeSottoVariabileLista : nomiSottoVariabiliLista) {
			model.remove(nomeSottoVariabileLista);
		}
		
		return model;
	}
	
	public static String fixHTMLCKEDITOR(Object stringHtml, HttpSession session) {
		
		// L'indentazione deve essere fatta con tag e non con spazi
		// Sostituisco tutti gli spazi usati per indentare i caratteri < con tab
		Object html_backup = stringHtml;
		try {
			// Scorro tutta la stringa html
			for (int i = 0; i < stringHtml.toString().length(); i++) {
				// Vedo se sono a fine riga
				if (stringHtml.toString().charAt(i) == '\n') {
					// Salvo la posizione del carattere a capo
					int posReturn = i;
					// Sostituisco tutti gli spazi dopo il carattere a capo con tab
					for (int j = posReturn + 1; (j < stringHtml.toString().length()) && (stringHtml.toString().charAt(j) == ' '); j++) {
						if ((j + 1 < stringHtml.toString().length()) && (stringHtml.toString().charAt(j + 1) == '<')) {
							int posOpenAngular = j + 1;
							for (int k = posReturn + 1; k < posOpenAngular; k++) {
								String stringToReplace = stringHtml.toString().substring(0, k) + '\t' + stringHtml.toString().substring(k + 1);
								stringHtml = stringToReplace;
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la formattazione dell'html dei CKEditor: " + e.getMessage(), e);
			stringHtml = html_backup;
		}
				
		Document doc = Jsoup.parse(stringHtml.toString());
		doc.outputSettings().indentAmount(0).prettyPrint(false);
		
		/** unwrap degli item che hanno style none e che quindi non devono essere mostrati */
		Elements listItems = doc.select("li");
		for (Element listItem : listItems) {
			StyleSheet styleSheet = new StyleSheet();
			AttributeSet styleSet = styleSheet.getDeclaration(listItem.attr("style"));
			if (styleSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE) != null && styleSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE).toString().equals(Value.NONE) && isBlankListItem(listItem)) {
				listItem.parent().unwrap();
				listItem.unwrap();
			}
		}

		/** Unwrap degli item che hanno style none e che quindi non devono essere mostrati.
		 * 
		 * Ad esempio
		 *  
		 *  <ul>
		 *		<li style="list-style-type:none">
		 *			<ul>
		 *				<li style="text-align:justify">primo item</li>
		 *				<li style="text-align:justify">secondo item</li>
		 *			</ul>
		 *		</li>
		 *	</ul>
		 *
		 * deve diventare 
		 * 
		 *	<ul>
		 *		<li style="text-align:justify">primo item</li>
		 *		<li style="text-align:justify">secondo item</li>
		 *	</ul>
		 *
		 **/
//		boolean finish = false;
//		while (!finish) {
//			boolean trovatoItemConStyleNone = false;
//			Elements listaListItems = doc.select("li");
//			if (listaListItems != null) {
//				for (Element listItem : listaListItems) {
//					StyleSheet styleSheet = new StyleSheet();
//					AttributeSet styleSet = styleSheet.getDeclaration(listItem.attr("style"));
//					// Verifico se l'item ha list-style-type:none e sistemo la lista alla quale appartiene
//					if (styleSet != null && styleSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE) != null && styleSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE).toString().equals(Value.NONE) && isBlankListItem(listItem)) {
//						trovatoItemConStyleNone = true;
//						boolean unwrapNodoPadre = true;
//						// Ricavo il nodo padre
//						org.jsoup.nodes.Node nodoPadre = listItem.parent();
//						// Ricavo i figli del padre, ovvero tutti i fratelli di listItem
//						List<org.jsoup.nodes.Node> listaNodiFiglio = listItem.parent().childNodes();
//						if (listaNodiFiglio != null && !listaNodiFiglio.isEmpty()) {
//							org.jsoup.nodes.Node[] arrayNodiFiglio = listaNodiFiglio.toArray(new org.jsoup.nodes.Node[listaNodiFiglio.size()]);
//							// Devo fare l'unwrap di tutti i figli, e poi del loro padre
//							for (int i = 0; i < arrayNodiFiglio.length; i++) {
//								org.jsoup.nodes.Node nodoFiglio = arrayNodiFiglio[i]; 
//								if (nodoFiglio != null && nodoFiglio instanceof Element) {
//									StyleSheet styleSheetFiglio = new StyleSheet();
//									AttributeSet styleSetFiglio = styleSheet.getDeclaration(nodoFiglio.attr("style"));
//									if (styleSetFiglio != null && styleSetFiglio.getAttribute(CSS.Attribute.LIST_STYLE_TYPE) != null && styleSetFiglio.getAttribute(CSS.Attribute.LIST_STYLE_TYPE).toString().equals(Value.NONE) && isBlankListItem((Element) nodoFiglio)) {
//										nodoFiglio.unwrap();
//									} else {
//										unwrapNodoPadre = false;
//									}
//								}
//							}
//						}
//						// Unwrap del padre
//						if (nodoPadre != null && unwrapNodoPadre) {
//							nodoPadre.unwrap();
//						}
//						// Esco dal ciclo, ho sistemato la lista ma devo ripetere l'analisi del codice html dall'inizio
//						break;
//					}
//				}
//			}
//			// Se analizzo tutto l'html senza trovare più item con list-style-type:none allora ho finito
//			if (!trovatoItemConStyleNone) {
//				finish = true;
//			}
//		}
		
		/** elimino tutti i thead */
		Elements theadItems = doc.select("thead");
		for (Element theadItem : theadItems) {
			theadItem.unwrap();
		}
		
		/** elimino tutti i tbody */
		Elements tbodyItems = doc.select("tbody");
		for (Element tbodyItem : tbodyItems) {
			tbodyItem.unwrap();
		}
		
		/** sostituisco tutti i th con elementi td, setto lo stile centrato e grassetto */
		Elements thItems = doc.select("th");
		for (Element thItem : thItems) {
			thItem.tagName("td");
			thItem.attributes().remove("scope");
			thItem.attr("style", "text-align:center");
			String text = thItem.text();
			thItem.empty();
			thItem.appendElement("strong");
			thItem.child(0).text(text);
		}
		
		Elements tables = doc.select("table");
		if (!tables.isEmpty()) {
			Elements cellTableElements = tables.select("td");

			for (Element cell : cellTableElements) {
				Elements pCellElements = cell.select("p");
				if (!pCellElements.isEmpty()) {
					String styleP = pCellElements.get(0).attr("style");
					if (StringUtils.isNotBlank(styleP)) {
						if ( pCellElements.get(0).parent() != null) {
							pCellElements.get(0).parent().attr("style", StringUtils.isNotBlank( pCellElements.get(0).parent().attr("style")) ?  pCellElements.get(0).parent().attr("style") + "; " + styleP : styleP);
						}
					}

					for (int pPos = 0; pPos < pCellElements.size(); pPos++) {
						Element pElement = pCellElements.get(pPos);
						if (pPos != pCellElements.size() - 1) {
							String innerHtml = "";
//							if(ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_SOST_BR_HTML_MODELLI")) {
								innerHtml = pElement.html() + "<br/>";
//							} else {
//								innerHtml = pElement.html() + " ";
//							}
							pElement.html(innerHtml);
						}
						pElement.unwrap(); 
					}
				}

				/**
				 * Per l'iniezione nel modello viene considerata solo la size del testo delle tabelle, 
				 * il restante testo avrà dimensione pari alla dimensione del carattere settata nel file modello stesso
				 * Gestito il copia incolla da Microsoft Word, per il copia incolla da openoffice è necessario sovrascrivere 
				 * direttamente dall'editor le dimensioni, per LibreOffice vengono ignorate le celle che hanno la dimensione
				 * pari a quella dello stile generale del file di origine. è sempre possibile sovrascrivere la dimensione
				 * utilizzando il tool dell'editor				
				 */
				Elements spanCellElements = cell.select("span");
				if (!spanCellElements.isEmpty()) {
					for (Element spanElem : spanCellElements) {
						
						if (spanElem.children().size() > 0 && spanElem.hasAttr("style") && spanElem.attr("style").contains("font-size")) {
							Boolean dontUnwrap = true;
							int i=-1;
							Element spanChild ;
							do {
								i++;
								spanChild = spanElem.child(i);					
								if (spanChild.toString().contains("font-size")) {
									dontUnwrap = false;
								}
								
							} while (dontUnwrap && i<spanElem.children().size()-1);
							
							if (!dontUnwrap) {
								spanElem.unwrap();
							}
						}
					}
				}
				spanCellElements = cell.select("span");
				if (!spanCellElements.isEmpty()) {

					String styleSpan = spanCellElements.get(0).attr("style");
					if (StringUtils.isNotBlank(styleSpan)) {
						cell.attr("style", StringUtils.isNotBlank(cell.attr("style")) ? cell.attr("style") + "; " + styleSpan : styleSpan);
					}
				}
				
			}
		}

		return doc.select("body").html();
	}
	
	private static boolean isBlankListItem(Element listItem) {
		for (TextNode textNode : listItem.textNodes()) {
			if (StringUtils.isNotBlank(textNode.getWholeText())) {
				return false;
			}
		}
		return true;
	}

	// Verifica se nel modello ci sono associazioni con attributi liberi
	public static boolean contieneSoloAssociazioniAttributiCustom(ModelliDocBean modello) throws Exception {
		boolean soloAttributiCustom = true;
		if(modello.getListaAssociazioniAttributiCustom() != null) {
			// Le associazioni dei sotto attributi sono dello stesso tipo di quella del padre, non serve verificarle
			for(AssociazioniAttributiCustomBean associazioneAttributiCustomBean : modello.getListaAssociazioniAttributiCustom()) {
				if (!"attributoCustom".equalsIgnoreCase(associazioneAttributiCustomBean.getTipoAssociazioneVariabileModello())){
					soloAttributiCustom = false;
				}
			}
		}
		return soloAttributiCustom;	
	}
	
	private static LinkedHashMap<String, AssociazioniAttributiCustomBean> getAssociazioniAttributiCustomMap(ModelliDocBean bean) {
		
		LinkedHashMap<String, AssociazioniAttributiCustomBean> associazioniAttributiCustomMap = new LinkedHashMap<String, AssociazioniAttributiCustomBean>();
		
		if(bean.getListaAssociazioniAttributiCustom() != null && bean.getListaAssociazioniAttributiCustom().size() > 0) {
			for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : bean.getListaAssociazioniAttributiCustom()) {
				if(StringUtils.isNotBlank(lAssociazioniAttributiCustomBean.getNomeVariabileModello())) {
					associazioniAttributiCustomMap.put(lAssociazioniAttributiCustomBean.getNomeVariabileModello(), lAssociazioniAttributiCustomBean);
				}
				if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {
					for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
						associazioniAttributiCustomMap.put(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello(), lAssociazioniSottoAttributiComplexBean);
					}
				}
			}
		}
		
		return associazioniAttributiCustomMap;
	}
	
	private static List<String> getListaNomiVariabiliFromNomeAttributoCustom(String nomeAttributoCustom, ModelliDocBean bean, String nomeVariabileLista) {
		
		List<String> listaNomiVariabili = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(nomeAttributoCustom)) {
			if(bean.getListaAssociazioniAttributiCustom() != null && bean.getListaAssociazioniAttributiCustom().size() > 0) {
				for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : bean.getListaAssociazioniAttributiCustom()) {
					if(StringUtils.isNotBlank(lAssociazioniAttributiCustomBean.getNomeAttributoCustom()) && nomeAttributoCustom.equalsIgnoreCase(lAssociazioniAttributiCustomBean.getNomeAttributoCustom())) {
						listaNomiVariabili.add(lAssociazioniAttributiCustomBean.getNomeVariabileModello());
					}
					if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {
						for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
							boolean controlloVariabileListaAttrLibero = lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello().startsWith(nomeVariabileLista + ".");
							if(StringUtils.isNotBlank(lAssociazioniSottoAttributiComplexBean.getNomeAttributoCustom()) && nomeAttributoCustom.equalsIgnoreCase(lAssociazioniSottoAttributiComplexBean.getNomeAttributoCustom())) {
								listaNomiVariabili.add(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello());
							} else if(StringUtils.isNotBlank(lAssociazioniSottoAttributiComplexBean.getNomeAttributoLibero()) && nomeAttributoCustom.equalsIgnoreCase(lAssociazioniSottoAttributiComplexBean.getNomeAttributoLibero()) && controlloVariabileListaAttrLibero) {
								listaNomiVariabili.add(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello());
							}
						}
					}
				}
			}
		}
		
		return listaNomiVariabili;
	}
	
	private static ImpostazioniBarcodeBean getImpostazioniImmagineBarCode(String tipoBarcode) {
		
		if (StringUtils.isBlank(tipoBarcode)) {
			tipoBarcode = "CODE128";
		}
		
		ImpostazioniBarcodeBean impostazioniBarcodeBean = new ImpostazioniBarcodeBean();
		impostazioniBarcodeBean.setBarcodeEncoding(tipoBarcode);
		return impostazioniBarcodeBean;
	}

	public static String getTextModelValue(Object value) {
		return value != null ? String.valueOf(value) : " ";
	}
	
	public static String getDateModelValue(Object value) {		
		Date date = getDateValueFromObject(value);
		return date != null ? formatDate(date) : " ";			
	}
	
	public static String getDateTimeModelValue(Object value) {		
		Date date = getDateValueFromObject(value);
		return date != null ? formatDateTime(date) : " ";			
	}	
	
	public static String getCheckModelValue(Object value) {
		if (value != null && value instanceof String && (((String) value).equals(CHECK + "") || ((String) value).equals(NOT_CHECK + ""))){
			return (String) value;
		} else {
			boolean checked = getBooleanValueFromObject(value);
			return checked ? CHECK + "" : NOT_CHECK + "";
		}
	}
	
	public static String getHtmlModelValue(Object value) {
		return value != null ? ModelliDocDatasource.HTML_VALUE_PREFIX + String.valueOf(value) : "";
	}
	
	private static RenderedImageSource getImageModelValue(Object value, HttpSession session) throws IOException {
		String path = "/images/pratiche/icone/";
		String nomeImmagineLogo = "blank.png";
		try {
			if (value instanceof String && StringUtils.isNotBlank((String) value)) {
				// FIXME Da cambiare quando valentina passarà tutto il path e non solo il nome file
				path = "/images/loghiXTemplateDoc/";
				nomeImmagineLogo = (String) value;
			}		
			BufferedImage imgLogo = ImageIO.read(new File(session.getServletContext().getRealPath(path + nomeImmagineLogo)));
			BufferedImage joinedImg = joinBufferedImage(session, 16.51f, imgLogo);
			// construct the buffered image
			// obtain it's graphics
			Graphics2D bImageGraphics = joinedImg.createGraphics();
			// draw the Image (image) into the BufferedImage (bImage)
			bImageGraphics.drawImage(joinedImg, null, null);
			// cast it to rendered image
			RenderedImage rImage = (RenderedImage) joinedImg;
			return new RenderedImageSource(rImage);
		} catch (IOException e) {
			throw new IOException("Impossibile leggere il file " + path + nomeImmagineLogo, e);
		}
	}
	
	private static BufferedImage joinBufferedImage(HttpSession session, float aspectRatio, BufferedImage... imgs) throws IOException {

		// do some calculate first
		int totalWidth =  Math.round(100 * aspectRatio);
		int wid = 0;
		int height =  0;
		for (BufferedImage bufferedImage : imgs) {
			wid += bufferedImage.getWidth();
			height = Math.max(height, bufferedImage.getHeight());
		}
		
		BufferedImage blankImg = ImageIO.read(new File(session.getServletContext().getRealPath("/images/pratiche/icone/blank.png")));
		Image tmp = blankImg.getScaledInstance((totalWidth - wid) / 2, height, Image.SCALE_SMOOTH);
	    BufferedImage scaledBalnkImage = new BufferedImage((totalWidth - wid) / 2, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = scaledBalnkImage.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	     
		BufferedImage newImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		java.awt.Color oldColor = g2.getColor();
		// fill background
		g2.fillRect(0, 0, totalWidth, height);
		// draw image
		g2.setColor(oldColor);
		int x = 0;
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		for (BufferedImage bufferedImage : imgs) {
			g2.drawImage(bufferedImage, null, x, 0);
			x += bufferedImage.getWidth();
		}
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		g2.dispose();
		return newImage;
	}
	
	private static String formatDate(Date date) {
		return new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(date);
	}
	
	private static String formatDateTime(Date date) {
		return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).format(date);
	}
	
	private static boolean getBooleanValueFromObject(Object value) {
		if (value != null) {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else if (value instanceof String) {
				String strValue = (String) value;
				return "true".equalsIgnoreCase(strValue) || "1".equalsIgnoreCase(strValue) || "si".equalsIgnoreCase(strValue);
			}
		} 
		return false;
	}
	
	private static Date getDateValueFromObject(Object value) {
		if (value != null) {
			if (value instanceof Date) {
				return (Date) value;
			} else {				
				String valueStr = String.valueOf(value);
				if (StringUtils.isNotBlank(valueStr)) {
					try {
						return new SimpleDateFormat(AbstractDataSource.DATETIME_ATTR_FORMAT).parse(valueStr);
					}catch (Exception e1) {
						try {
							return new SimpleDateFormat(AbstractDataSource.DATE_ATTR_FORMAT).parse(valueStr);
						}catch (Exception e2) {
							try {
								return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP_WITH_SEC).parse(valueStr);
							}catch (Exception e3) {
								try {
									return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).parse(valueStr);
								}catch (Exception e4) {
									try {
										return new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).parse(valueStr);
									}catch (Exception e5) {
									}
								}
							}						
						}
					}
				}
			}
		} 
		return null;
	}
	
	private static void checkReplaceBR(OdfElement odfElement, HttpSession session) {
		
		if (ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_GEST_ADSP_EDITOR_ATTI")) {
			manageReplaceBRWithStyles(odfElement);
		} else {
			String textContent = odfElement.getTextContent();
			
			if(StringUtils.isNotBlank(textContent) && textContent.contains(ESCAPE_BR)) {
				String newTextContent = textContent.replaceAll(ESCAPE_BR, "\n");
				removeTextContentBeforeAppend(odfElement);
				appendTextElement(odfElement, newTextContent);
			}
		}		
	}
	
	// Metodo che gestisce i page break all'interno dei paragrafi
	private static void gestisciPageBreakNelParagrafo(Paragraph paragrafo, Paragraph paragrafoPrecedente, List<Paragraph> listaParagrafiDaRimuovere) throws DOMException {
		OdfElement par = paragrafo.getOdfElement();
		NodeList childs = par.getChildNodes();
		int size = childs.getLength();
		// Cerco il paragrafo che fa da placeholder per il page break
		for (int i = 0; i < size; i++) {
			if (childs.item(i) instanceof OdfTextSpan) {
				OdfTextSpan node = (OdfTextSpan) childs.item(i);
				String textContent = node.getTextContent();
				if (StringUtils.isNotBlank(textContent) && textContent.contains(PAGE_BREAK)) {
					// Ho trovato il placeholder per il page break
					TextParagraphElementBase odfParagraph = paragrafo.getOdfElement();
					if (odfParagraph.getPreviousSibling() instanceof TextParagraphElementBase) {
						// Se l'elemento precedente è un paragrafo allora ci setto la proprietà BreakAfter, e metto il paragrafo placeholder 
						// nella lista dei paragrafi da eliminare
						((OdfStylableElement) odfParagraph.getPreviousSibling()).setProperty(StyleParagraphPropertiesElement.BreakAfter, "page");
						listaParagrafiDaRimuovere.add(paragrafo);
					} else {
						// Se l'elemento precedente non è un paragrafo allora setto la proprietà BreakAfter nel paragrafo placeholder, setto il testo a " "
						// e faccio in modo che il paragrafo placeholder occupi meno spazio possibile
						// Devo fare così perchè dato che l'elemento prima del peragrafo placeholder non è un paragrafo non vi posso settare il BreakAfter
						node.setTextContent(" ");
						odfParagraph.setProperty(StyleParagraphPropertiesElement.BreakAfter, "page");
						odfParagraph.setProperty(StyleTextPropertiesElement.FontSize, "0pt");
						odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, "0pt");
						odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, "0pt");
						odfParagraph.setProperty(StyleParagraphPropertiesElement.MarginBottom, "0");
						odfParagraph.setProperty(StyleParagraphPropertiesElement.MarginTop, "0");
						odfParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "0");
						NodeList paragraphChildNodesList = paragrafo.getOdfElement().getChildNodes();
						if (paragraphChildNodesList != null) {
							for (int k = 0; k < paragraphChildNodesList.getLength(); k++) {
								try {
									if (paragraphChildNodesList.item(k) != null && paragraphChildNodesList.item(k) instanceof OdfStylableElement) {
										OdfStylableElement odfChildParagraph = (OdfStylableElement) paragraphChildNodesList.item(k);
										odfChildParagraph.setProperty(StyleTextPropertiesElement.FontSize, "0pt");
										odfChildParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, "0pt");
										odfChildParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, "0pt");
										odfChildParagraph.setProperty(StyleParagraphPropertiesElement.MarginBottom, "0");
										odfChildParagraph.setProperty(StyleParagraphPropertiesElement.MarginTop, "0");
										odfChildParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "0");
									}
								} catch (Exception e) {
									logger.error("Errore nel cambio font nel palceholder per la nuova pagina", e);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param odfElement
	 * @throws DOMException
	 */
	private static void manageReplaceBRWithStyles(OdfElement odfElement) throws DOMException {
		
		NodeList childs = odfElement.getChildNodes();
		
		int size = childs.getLength();
		
		for (int i = 0; i < size; i++) {

			if (childs.item(0) instanceof OdfTextSpan) {

				OdfTextSpan node = (OdfTextSpan) childs.item(0);

				String textContent = node.getTextContent();

				if (StringUtils.isNotBlank(textContent) && textContent.contains(ESCAPE_BR)) {
					String styleName = node.getStyleName();
					OdfFileDom ownerDocument = (OdfFileDom) odfElement.getOwnerDocument();
					
					while(StringUtils.isNotBlank(textContent)) {
						int posBR = textContent.indexOf(ESCAPE_BR);
						
						if(posBR>=0) {
							if(posBR>0) {
								String text = textContent.substring(0, posBR);
								OdfTextSpan textnode = new OdfTextSpan(ownerDocument);
								textnode.setStyleName(styleName);
								textnode.setTextContent(text);
								odfElement.appendChild(textnode);
							}
													
							TextLineBreakElement lineBreakElement = ownerDocument.newOdfElement(TextLineBreakElement.class);
							odfElement.appendChild(lineBreakElement);
						} else {
							OdfTextSpan textnode = new OdfTextSpan(ownerDocument);
							textnode.setStyleName(styleName);
							textnode.setTextContent(textContent);
							odfElement.appendChild(textnode);
							
							break;
						}						
						
						textContent = textContent.substring(posBR + ESCAPE_BR.length(), textContent.length());
					}					
					odfElement.removeChild(childs.item(0));					
				}else {
					odfElement.appendChild(node);
				}
			}else {
				odfElement.appendChild(childs.item(0));
			}
		}
	}
	
	private static void removeTextContentBeforeAppend(OdfElement ownerElement) {
		NodeList nodeList = ownerElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node;
			node = nodeList.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) {
				ownerElement.removeChild(node);
				// element removed need reset index.
				i--;
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				String nodename = node.getNodeName();
				if (nodename.equals("text:s") || nodename.equals("text:tab") || nodename.equals("text:line-break")) {
					ownerElement.removeChild(node);
					// element removed need reset index.
					i--;
				} else if (nodename.equals("text:a") || nodename.equals("text:span") || nodename.equals("text:list-item") || nodename.equals("text:p") )
					removeTextContentBeforeAppend((OdfElement) node);
			}
		}
	}
	
	private static void appendTextElement(OdfElement parentElement, String content) {
		OdfFileDom ownerDocument = (OdfFileDom) parentElement.getOwnerDocument();
		Node ownerElement = parentElement.getLastChild() != null ? parentElement.getLastChild() : parentElement;
		if (ownerElement != null) {
			int i = 0, length = content.length();
			String str = "";
			while (i < length) {
				char ch = content.charAt(i);
				if (ch == ' ') {
					int j = 1;
					i++;
					while ((i < length) && (content.charAt(i) == ' ')) {
						j++;
						i++;
					}
					if (j == 1) {
						str += ' ';
					} else {
						str += ' ';
						Text textnode = ownerDocument.createTextNode(str);
						ownerElement.appendChild(textnode);
						str = "";
						TextSElement spaceElement = ownerDocument.newOdfElement(TextSElement.class);
						ownerElement.appendChild(spaceElement);
						spaceElement.setTextCAttribute(j - 1);
					}
				} else if (ch == '\n') {
					if (str.length() > 0) {
						Text textnode = ownerDocument.createTextNode(str);
						ownerElement.appendChild(textnode);
						str = "";
					}
					TextLineBreakElement lineBreakElement = ownerDocument.newOdfElement(TextLineBreakElement.class);
					ownerElement.appendChild(lineBreakElement);
					i++;
				} else if (ch == '\t') {
					if (str.length() > 0) {
						Text textnode = ownerElement.getOwnerDocument().createTextNode(str);
						ownerElement.appendChild(textnode);
						str = "";
					}
					TextTabElement tabElement = ownerDocument.newOdfElement(TextTabElement.class);
					ownerElement.appendChild(tabElement);
					i++;
				} else if (ch == '\r') {
					i++;
				} else {
					str += ch;
					i++;
				}
			}
			if (str.length() > 0) {
				Text textnode = ownerDocument.createTextNode(str);
				ownerElement.appendChild(textnode);
			}
		}
	}
	
	private static boolean isAttivaIndentazione(HttpSession session) {
		return ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_INDENTAZIONE_CKEDITOR");
	}

}