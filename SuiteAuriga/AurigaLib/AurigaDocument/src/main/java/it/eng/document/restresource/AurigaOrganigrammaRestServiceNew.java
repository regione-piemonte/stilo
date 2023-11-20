/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.dom.attribute.text.TextDescriptionAttribute;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.springframework.beans.BeanWrapperImpl;
import org.w3c.dom.Node;

import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.json.JSONObject;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import io.swagger.annotations.Api;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Extractverdoc;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.Del_ud_doc_verImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpdverdocImpl;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityExplodetreenodeBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGetlivpathexuoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoadorganigrammaxmodgraficoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovausersBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkDefSecurityExplodetreenode;
import it.eng.client.DmpkDefSecurityGetlivpathexuo;
import it.eng.client.DmpkDefSecurityLoadorganigrammaxmodgrafico;
import it.eng.client.DmpkDefSecurityTrovausers;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.ExtractVerDocOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.KeyValueBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.editororganigramma.EditorOrganigrammaTreeNodeBean;
import it.eng.document.function.bean.editororganigramma.MapsFunzionigrammaBean;
import it.eng.document.function.bean.editororganigramma.RuoloOrganigrammaXmlBean;
import it.eng.document.function.bean.editororganigramma.TextInputElement;
import it.eng.document.function.bean.editororganigramma.TipoUOXmlBean;
import it.eng.document.function.bean.editororganigramma.UtenteXOrganigrammaBean;
import it.eng.document.function.bean.editororganigramma.ValuesTabellaFunzionigrammaBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.TimbraUtil;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.ReflectionUtility;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

/**
 * @author Antonio Peluso
 * 
 * SERVIZI Business utilizzati dal tool grafico EDITORORGANIGRAMMA
 */

@Singleton
@Api
@Path("/editorOrganigrammaNew")
public class AurigaOrganigrammaRestServiceNew {

	@Context
	ServletContext context;

	@Context
	HttpServletRequest request;

	private static final Logger logger = Logger.getLogger(AurigaOrganigrammaRestServiceNew.class);

	private static final String KEY_COMPETENZA_FREEMARKER = "competenza";
	private static final String MODELLO_ORGANIGRAMMA_FRONTESPIZIO = "/WEB-INF/resources/template_frontespizio.odt";
	private static final String MODELLO_ORGANIGRAMMA_FUNZIONIGRAMMA = "/WEB-INF/resources/template_funzionigramma.odt";
	private static final String TOKEN_AURIGALOGIN = "token";
	private static final String SCHEMA_AURIGALOGIN  = "schema";
	private static final String ID_USER_LAVORO_AURIGALOGIN  = "idUserLavoro";

	private static String displayFileNamePdf = "";
	private static boolean flgWarning = false;

	@POST
	@Path("/getTree")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTree(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String idUoRoot = doc.select("idUoRoot").text();
		String livelloRevisione = doc.select("livelloMaxRevisione").text();
		String tipiUORevisione = doc.select("tipiUORevisione").text();

		logger.debug("call service EditorOrganigramma - getTree");

		// ResponseBean response = new ResponseBean();
		List<EditorOrganigrammaTreeNodeBean> data = new ArrayList<EditorOrganigrammaTreeNodeBean>();
		Map<String, List<UtenteXOrganigrammaBean>> mappaUtenti = new HashMap();
		EditorOrganigrammaTreeNodeBean organigrammaTree = null;
		try {

			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			String idRootNode = null;
			DmpkDefSecurityLoadorganigrammaxmodgraficoBean input = new DmpkDefSecurityLoadorganigrammaxmodgraficoBean();
			
			input.setIduorootin(idUoRoot);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			input.setLivellomaxin(StringUtils.isNotBlank(livelloRevisione) ? Integer.parseInt(livelloRevisione) : null);
			input.setCodtipiuoin(tipiUORevisione);
			input.setFlgincludiutentiin(1);

			DmpkDefSecurityLoadorganigrammaxmodgrafico loadorganigrammaxmodgrafico = new DmpkDefSecurityLoadorganigrammaxmodgrafico();
			StoreResultBean<DmpkDefSecurityLoadorganigrammaxmodgraficoBean> output = loadorganigrammaxmodgrafico.execute(null,
					loginBean, input);


			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				logger.error("ErrorStore: " + output.getDefaultMessage());
				throw new StoreException(output.getDefaultMessage());
			}

			if (output.getResultBean().getOrganigrammaxmlout() != null) {
				HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
				StringReader sr = new StringReader(output.getResultBean().getOrganigrammaxmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						 
						Vector<String> v = getValoriRiga(lista.getRiga().get(i));
						if (v.get(0) != null && v.get(0).equals("UO")) {
							if (v.get(2).equals(idUoRoot)) {
								idRootNode = v.get(1);
							}
							BigDecimal nroLivelloProgressivo = v.get(6) != null ? new BigDecimal(v.get(6)) : null; // colonna 7 dell'xml
							BigDecimal nroProgr = null;
							if (nroProgrXLivello.containsKey(nroLivelloProgressivo)) {
								nroProgr = nroProgrXLivello.get(nroLivelloProgressivo).add(BigDecimal.ONE);
							} else {
								nroProgr = BigDecimal.ONE;
							}
							nroProgrXLivello.put(nroLivelloProgressivo, nroProgr);
							EditorOrganigrammaTreeNodeBean node = buildNodeFromValoriRiga(v, nroProgr);
							data.add(node);
						} else {
							UtenteXOrganigrammaBean user = buildUtenteFromValoriRiga(v);
							if (!mappaUtenti.containsKey(user.getUoAppartenenza())) {
								mappaUtenti.put(user.getUoAppartenenza(), new ArrayList<UtenteXOrganigrammaBean>());
							}
							UtenteXOrganigrammaBean userAdd = new UtenteXOrganigrammaBean();
							userAdd.setDesUser(user.getDesUser());
							userAdd.setIdUser(user.getIdUser());
							userAdd.setIdRuolo(user.getIdRuolo());
							userAdd.setDescrizioneRuolo(user.getDescrizioneRuolo());
							mappaUtenti.get(user.getUoAppartenenza()).add(userAdd);
						}
						
					}
				}
			}

			Map<String, EditorOrganigrammaTreeNodeBean> dataMap = new TreeMap<>();
			for (EditorOrganigrammaTreeNodeBean lNode : data) {
				if (mappaUtenti.containsKey(lNode.getIdNode())) {
					lNode.setListaUtentiAssociati(mappaUtenti.get(lNode.getIdNode()));
				} else {
					lNode.setListaUtentiAssociati(new ArrayList<UtenteXOrganigrammaBean>());
				}
				dataMap.put(lNode.getIdNode(), lNode);
			}

			organigrammaTree = getResponseData(dataMap, idRootNode);
			fixIdForOrganigrammaClient(idRootNode, organigrammaTree);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		GenericEntity<EditorOrganigrammaTreeNodeBean> list = new GenericEntity<EditorOrganigrammaTreeNodeBean>(organigrammaTree) {};

		return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();

	}


	//	@POST
	//	@Path("/testGenerale")
	//	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	//	@Produces({ MediaType.APPLICATION_JSON })
	//	public Response testGenerale(
	//			@FormDataParam("aurigaLoginBean") AurigaLoginBean aurigaLoginBean,
	//			@FormDataParam("dataInput") ServiceGeneraleInputBean serviceGeneraleInputBean) throws Exception {
	//
	//		String nroVersione;
	//
	//		File tempFile = File.createTempFile("tmp", ".xml");
	//
	//		FileUtils.writeStringToFile(tempFile, serviceGeneraleInputBean.getOrganigrammaXml());
	//		
	//		SubjectBean subject = new SubjectBean();
	//		subject.setIdDominio(aurigaLoginBean.getSchema());
	//		SubjectUtil.subject.set(subject);
	//
	//		Session session = HibernateUtil.begin();
	//		Transaction lTransaction = session.beginTransaction();
	//
	//		try {
	//			
	//			nroVersione = versionaOrganigramma(serviceGeneraleInputBean.getOrganigrammaXml(), serviceGeneraleInputBean.getIdDoc(), "false", session);
	//			
	//			session.flush();
	//			lTransaction.commit();
	//			
	//		} catch (Exception e) {
	//			logger.error(e.getMessage(), e);
	//			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
	//		} 
	//		finally {
	//			HibernateUtil.release(session);
	//		}
	//		
	//		logger.debug("service saveOrganigramma - Response 200");
	//
	//		return Response.ok(nroVersione).header("Access-Control-Allow-Origin", "*").build();
	//	}


	private UtenteXOrganigrammaBean buildUtenteFromValoriRiga(Vector<String> v) {
		UtenteXOrganigrammaBean user = new UtenteXOrganigrammaBean();
		user.setIdUser(v.get(2)); // colonna 3
		user.setUoAppartenenza(v.get(4)); //colonna 5
		user.setCognomeNome(v.get(5)); // colonna 6
		user.setNroMatricola(v.get(11)); // colonna 12
		if (StringUtils.isNotBlank(user.getNroMatricola())) {
			user.setDesUser(user.getCognomeNome() + " - " + user.getNroMatricola());
		} else {
			user.setDesUser(user.getCognomeNome());
		}
		user.setIdRuolo(v.get(12)); // colonna 13
		user.setDescrizioneRuolo(v.get(13)); // colonna 14
		user.setIdScrivania(v.get(14)); // colonna 15
		user.setDesScrivania(v.get(15)); // colonna 16
		
		return user;
	}


	@POST
	@Path("/versionaFileOrganigramma")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response versionaFileOrganigramma(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String filePdf = doc.select("filePdf").text();
		String idDoc = doc.select("idDoc").text();
		String nomePdf = doc.select("nomePdf").text();

		displayFileNamePdf = nomePdf;
		String nroVersione;
		File tempFilePdf = File.createTempFile("tmp", ".pdf");

		byte [] bytesFile = Base64.decodeBase64(filePdf.substring(28));

		FileUtils.writeByteArrayToFile(tempFilePdf, bytesFile);

		AurigaLoginBean loginBean = getAurigaLoginBean(request);

		SubjectBean subject = new SubjectBean();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);

		Session session = HibernateUtil.begin();
		Transaction lTransaction = session.beginTransaction();

		try {
			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setFile(tempFilePdf);
			lRebuildedFile.setInfo(getFileInfoBean(tempFilePdf));
			lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));

			logger.debug("Versiono il file");
			nroVersione = versionaDocumento(getVersionaDocumentoInBean(lRebuildedFile), session, loginBean);

			session.flush();
			lTransaction.commit();


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		} finally {
			HibernateUtil.release(session);
		}
		GenericEntity<String> result = new GenericEntity<String>(nroVersione) {
		};

		return Response.ok(result).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/saveOrganigramma")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveOrganigramma(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String flgUpdate = doc.select("flgUpdate").text();
		String idDoc = doc.select("idDoc").text();
		String organigrammaXml = doc.select("organigrammaXml").text();

		String nroVersione;

		if(StringUtils.isNotBlank(flgUpdate) && "true".equalsIgnoreCase(flgUpdate)) {
			logger.debug("call service EditorOrganigramma - saveOrganigramma (updateVersion)");		
		}else {
			logger.debug("call service EditorOrganigramma - saveOrganigramma (addNewVersion)");
		}

		File tempFile = File.createTempFile("tmp", ".xml");

		FileUtils.writeStringToFile(tempFile, organigrammaXml);

		AurigaLoginBean loginBean = getAurigaLoginBean(request);

		SubjectBean subject = new SubjectBean();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);

		Session session = HibernateUtil.begin();
		Transaction lTransaction = session.beginTransaction();

		try {

			nroVersione = versionaOrganigramma(organigrammaXml, idDoc, flgUpdate, loginBean, session);

			session.flush();
			lTransaction.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		} 
		finally {
			HibernateUtil.release(session);
		}

		GenericEntity<String> result = new GenericEntity<String>(nroVersione) {
		};

		return Response.ok(result).header("Access-Control-Allow-Origin", "*").build();

	}

	@POST
	@Path("/annullaModifiche")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response annullaModifiche(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String idDoc = doc.select("idDoc").text();
		String nroVersLavoro = doc.select("nroVersLavoro").text();

		logger.debug("call service EditorOrganigramma - annullaModifiche");

		String organigrammaXml;
		EditorOrganigrammaTreeNodeBean organigrammaBean;

		AurigaLoginBean loginBean = getAurigaLoginBean(request);

		SubjectBean subject = new SubjectBean();
		subject.setIdDominio(loginBean.getSchema());
		SubjectUtil.subject.set(subject);

		Session session = HibernateUtil.begin();
		Transaction lTransaction = session.beginTransaction();

		try {

			//cancello l'ultima versione
			DmpkCoreDel_ud_doc_verBean delUdDocResultStoreBean = deleteLastVersion(loginBean, idDoc, session);

			String idDocVersionePrecedente = String.valueOf((delUdDocResultStoreBean.getNroprogrverio().subtract(new BigDecimal(1))));

			//estraggo la versione precendete (ultima valida)
			ExtractVerDocOutBean lExtractVerDocOutBean = getVersionStore(loginBean, idDoc, idDocVersionePrecedente);

			File fileExtract = DocumentStorage.extract(lExtractVerDocOutBean.getUriOut(), null);

			organigrammaXml = FileUtils.readFileToString(fileExtract);

			JAXBContext contextUn = JAXBContext.newInstance(EditorOrganigrammaTreeNodeBean.class);
			Unmarshaller unmarshaller = contextUn.createUnmarshaller();
			organigrammaBean = (EditorOrganigrammaTreeNodeBean) unmarshaller
					.unmarshal(new StringReader(organigrammaXml));

			if (nroVersLavoro != null
					&& lExtractVerDocOutBean.getNroProgrVerOut().compareTo(new BigDecimal(nroVersLavoro)) == 0) {
				versionaOrganigramma(organigrammaXml, idDoc, "false", loginBean, session);
			}

			session.flush();
			lTransaction.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		} finally {
			HibernateUtil.release(session);
		}

		GenericEntity<EditorOrganigrammaTreeNodeBean> result = new GenericEntity<EditorOrganigrammaTreeNodeBean>(organigrammaBean) {
		};

		return Response.ok(result).header("Access-Control-Allow-Origin", "*").build();
	}


	@POST
	@Path("/getVersion")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVersion(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String idDoc = doc.select("idDoc").text();
		String nroVers = doc.select("nroVers").text();

		logger.debug("call service EditorOrganigramma - getVersion");

		String organigrammaXml;
		EditorOrganigrammaTreeNodeBean organigrammaBean;

		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(loginBean.getSchema());
			SubjectUtil.subject.set(subject);

			ExtractVerDocOutBean lExtractVerDocOutBean = getVersionStore(loginBean, idDoc, nroVers);

			File fileExtract = DocumentStorage.extract(lExtractVerDocOutBean.getUriOut(), null);

			organigrammaXml = FileUtils.readFileToString(fileExtract);

			JAXBContext contextUn = JAXBContext.newInstance(EditorOrganigrammaTreeNodeBean.class);
			Unmarshaller unmarshaller = contextUn.createUnmarshaller();
			organigrammaBean = (EditorOrganigrammaTreeNodeBean) unmarshaller.unmarshal(new StringReader(organigrammaXml));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(
					Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		GenericEntity<EditorOrganigrammaTreeNodeBean> result = new GenericEntity<EditorOrganigrammaTreeNodeBean>(organigrammaBean) {
		};

		return Response.ok(result).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/funzionigramma")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response funzionigramma(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String image = doc.select("image").text();
		String organigrammaXml = doc.select("organigrammaXml").text();
		String azione = doc.select("azione").text();

		logger.debug("call service EditorOrganigramma - funzionigramma");

		File fileOrganigramma = null;
		JSONObject jsonResponse = new JSONObject();

		try {
			if ("PDF_COMPLETO".equalsIgnoreCase(azione)) {
				// crea PDF completo
				fileOrganigramma = createPDOrganigramma(organigrammaXml, image, "completo");
			} else if ("PDF_FUNZIONIGRAMMA".equalsIgnoreCase(azione)) {
				// crea PDF solo con le funzioni
				fileOrganigramma = createPDOrganigramma(organigrammaXml, image, "funzioni");
			} else if ("PDF_GRAFO".equalsIgnoreCase(azione)) {
				// crea PDF soolo con il grafo
				fileOrganigramma = createPDOrganigramma(organigrammaXml, image, "grafo");

			}

			String base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(fileOrganigramma));

			jsonResponse.put("base64", base64File);
			jsonResponse.put("warning", flgWarning);
			flgWarning = false;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(
					Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		String jsonResponseString = jsonResponse.toString();

		// GenericEntity<JSONObject> result = new GenericEntity<JSONObject>(json) {
		// };
		return Response.ok(jsonResponseString).header("Access-Control-Allow-Origin", "*").build();

	}

	@POST
	@Path("/getTipiUO")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTipiUO(@FormDataParam("dataInput") String dataInputXml ) throws Exception {

		logger.debug("call service EditorOrganigramma - getTipiUO");
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		String tipiUORevisione = doc.select("tipiUORevisione").text();

		List<TipoUOXmlBean> listaTipiUO = new ArrayList<>();
		List<TipoUOXmlBean> listaTipiUODef = new ArrayList<>();

		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPI_UO_IN_ORGANIGRAMMA");
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo
					.execute(null, loginBean, lDmpkLoadComboDmfn_load_comboBean);

			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				logger.error("ErrorStore: " + lStoreResultBean.getDefaultMessage());
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}
			
			if (lStoreResultBean.getResultBean().getListaxmlout() != null) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				listaTipiUO = recuperaLista(xmlLista, TipoUOXmlBean.class);
			}

			if (StringUtils.isNotBlank(tipiUORevisione)) {
				for (TipoUOXmlBean tipoUOXmlBean : listaTipiUO) {
					if (tipiUORevisione.contains(tipoUOXmlBean.getCodTipo())) {
						listaTipiUODef.add(tipoUOXmlBean);
					}
				}
			} else listaTipiUODef.addAll(listaTipiUO);
			
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		GenericEntity<List<TipoUOXmlBean>> list = new GenericEntity<List<TipoUOXmlBean>>(listaTipiUODef) {
		};
		return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/getRuoli")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRuoli(@FormDataParam("dataInput") String dataInputXml ) throws Exception {

		logger.debug("call service EditorOrganigramma - getRuoli");

		List<RuoloOrganigrammaXmlBean> resultList=null;

		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkLoadComboDmfn_load_comboBean inputParamComboBean = new DmpkLoadComboDmfn_load_comboBean();
			inputParamComboBean.setTipocomboin("RUOLO_AMM");

			inputParamComboBean.setAltriparametriin("INTERNAL_VALUE|*|CID|*|CI_TO_ADD|*|");
			inputParamComboBean.setFlgsolovldin(new BigDecimal("1"));

			// Output
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = lDmpkLoadComboDmfn_load_combo.execute(null,
					loginBean, inputParamComboBean);

			if (StringUtils.isNotBlank(storeResult.getDefaultMessage())) {
				logger.error("ErrorStore: " + storeResult.getDefaultMessage());
				throw new StoreException(storeResult.getDefaultMessage());
			}

			if (storeResult.getResultBean().getListaxmlout() != null) {
				String xmlLista = storeResult.getResultBean().getListaxmlout();

				List<KeyValueBean> listaXML = XmlListaUtility.recuperaLista(xmlLista, KeyValueBean.class);
				resultList = new ArrayList<RuoloOrganigrammaXmlBean>();

				for (KeyValueBean lXmlListaSimpleBean : listaXML) {
					RuoloOrganigrammaXmlBean lBean = new RuoloOrganigrammaXmlBean();
					lBean.setKey(lXmlListaSimpleBean.getKey());
					lBean.setValue(lXmlListaSimpleBean.getValue());
					resultList.add(lBean);
				}
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		GenericEntity<List<RuoloOrganigrammaXmlBean>> list = new GenericEntity<List<RuoloOrganigrammaXmlBean>>(resultList) {
		};
		return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();

	}

	@POST
	@Path("/fetchUsers")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response fetchUsers(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String filter = doc.select("filter").text();

		logger.debug("call service EditorOrganigramma - fetchUsers");

		List<UtenteXOrganigrammaBean> data = new ArrayList<UtenteXOrganigrammaBean>();

		try {

			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkDefSecurityTrovausersBean input = new DmpkDefSecurityTrovausersBean();
			input.setFlgsenzapaginazionein(1);
			input.setFlgsolovldio(new BigDecimal(1));
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			input.setDescrizioneio("%" + filter + "%");

			DmpkDefSecurityTrovausers dmpkDefSecurityTrovausers = new DmpkDefSecurityTrovausers();
			StoreResultBean<DmpkDefSecurityTrovausersBean> output = dmpkDefSecurityTrovausers.execute(null, loginBean,
					input);

			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					logger.error("ErrorStore: " + output.getDefaultMessage());
					throw new StoreException(output.getDefaultMessage());
				}
			}

			if (output.getResultBean().getListaxmlout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = getValoriRiga(lista.getRiga().get(i));
						UtenteXOrganigrammaBean bean = new UtenteXOrganigrammaBean();

						// Setto i valori dell'XML nel bean
						bean.setIdUser(v.get(0)); // colonna 1: Id. univoco dell'utente (ID_USER)
						bean.setDesUser(v.get(1)); // colonna 2: Cognome e nome, descrizione (DES_USER)
						String[] tokens = bean.getDesUser().split(Pattern.quote("|"));
						String cognome = tokens[0];
						String nome = tokens[1];
						bean.setCognome(cognome);
						bean.setNome(nome);
						String cognomeNome = "";
						if (cognome != null)
							cognomeNome = cognomeNome + cognome.trim() + " ";
						if (nome != null)
							cognomeNome = cognomeNome + nome.trim();

						bean.setCognomeNome(cognomeNome);
						bean.setNroMatricola(v.get(3)); // colonna 4: N° matricola
						bean.setIdSoggRubrica(v.get(9)); // colonna 10:Id. del soggetto di rubrica corrispondente
						bean.setNomeProfilo(v.get(28)); // colonna 29: Nome del profilo
						bean.setCodiceUtenteMansione(v.get(32)); // colonna 33: Codice mansione
						bean.setCodiceUO(v.get(34)); // colonna 35: Cod. UO appartenenza
						bean.setNomeAppUO(v.get(35)); // colonna 36: Nome UO appartenenza

						data.add(bean);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		GenericEntity<List<UtenteXOrganigrammaBean>> list = new GenericEntity<List<UtenteXOrganigrammaBean>>(data) {
		};
		return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();
	}


	@POST
	@Path("/explodeNode")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response explodeNode(@FormDataParam("dataInput") String dataInputXml ) throws Exception {
		
		Document doc = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		BigDecimal nroProgrRootNode = new BigDecimal(doc.select("nroProgrRootNode").text());
		String idNodeToExplode = doc.select("idNodeToExplode").text();
		String idRootNode = doc.select("idRootNode").text();

		logger.debug("call service EditorOrganigramma - explodeNode");

		EditorOrganigrammaTreeNodeBean organigrammaTree = null;
		List<EditorOrganigrammaTreeNodeBean> data = new ArrayList<>();

		// String idNodeToExplode = explodeNodeRequestBean.getIdNodeToExplode();
		// BigDecimal nroProgrRootNode = explodeNodeRequestBean.getNroProgrRootNode();

		try {

			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkDefSecurityExplodetreenodeBean input = new DmpkDefSecurityExplodetreenodeBean();
			String idNodeToExplodeReplace = idNodeToExplode.replaceAll("_", "/");
			input.setIdnodetoexplodein(idNodeToExplodeReplace);
			input.setFlgincludiutentiin(0);
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			input.setTiporelutenticonuoin("A;D");
			//			input.setIdorganigrammain(new BigDecimal(2));
			input.setFlgsoloattivein(1);

			DmpkDefSecurityExplodetreenode explodetreenode = new DmpkDefSecurityExplodetreenode();
			StoreResultBean<DmpkDefSecurityExplodetreenodeBean> output = explodetreenode.execute(null, loginBean,
					input);

			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				logger.error("ErrorStore: " + output.getDefaultMessage());
				throw new StoreException(output.getDefaultMessage());
			}

			if (output.getResultBean().getTreexmlout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = getValoriRiga(lista.getRiga().get(i));
						BigDecimal nroLivello = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1
						// dell'xml
						BigDecimal nroProgr = null;
						if (nroProgrXLivello.containsKey(nroLivello)) {
							nroProgr = nroProgrXLivello.get(nroLivello).add(BigDecimal.ONE);
						} else if (i == 0) {
							nroProgr = nroProgrRootNode;
						} else {
							nroProgr = BigDecimal.ONE;
						}
						nroProgrXLivello.put(nroLivello, nroProgr);
						EditorOrganigrammaTreeNodeBean node = buildNodeFromValoriRiga(v, nroProgr);
						if (i == 0 && "2".equals(node.getFlgEsplodiNodo())) {
							node.setFlgEsplodiNodo("1");
						}
						if (!node.getIdNode().equals(idNodeToExplode)) {
							data.add(node);
						}
					}
				}
			}

			Map<String, EditorOrganigrammaTreeNodeBean> dataMap = new TreeMap<>();
			for (EditorOrganigrammaTreeNodeBean lNode : data) {
				dataMap.put(lNode.getIdNode(), lNode);
			}

			organigrammaTree = getResponseData(dataMap, idNodeToExplodeReplace);
			fixIdForOrganigrammaClient(idRootNode.replaceAll("_", "/"), organigrammaTree);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		GenericEntity<EditorOrganigrammaTreeNodeBean> list = new GenericEntity<EditorOrganigrammaTreeNodeBean>(
				organigrammaTree) {
		};
		return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();

	}


	private AurigaLoginBean getAurigaLoginBean(HttpServletRequest request) {

		AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();

		aurigaLoginBean.setToken(request.getHeader(TOKEN_AURIGALOGIN));
		aurigaLoginBean.setSchema(request.getHeader(SCHEMA_AURIGALOGIN));
		aurigaLoginBean.setIdUserLavoro(StringUtils.isNotBlank(request.getHeader(ID_USER_LAVORO_AURIGALOGIN))? request.getHeader(ID_USER_LAVORO_AURIGALOGIN) : null);

		logger.debug("Recuperati i seguenti parametri di autenticazione: Token: " + aurigaLoginBean.getToken()+ " Schema: " + aurigaLoginBean.getSchema() + " IdUserLavoro: " + aurigaLoginBean.getIdUserLavoro());

		return aurigaLoginBean;
	}

	private DmpkCoreDel_ud_doc_verBean deleteLastVersion(AurigaLoginBean loginBean, String idDoc, Session session) throws Exception {

		DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
		lDel_ud_doc_verBean.setCodidconnectiontokenin(loginBean.getToken());
		lDel_ud_doc_verBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDel_ud_doc_verBean.setFlgtipotargetin("V");
		lDel_ud_doc_verBean.setFlgcancfisicain(1);
		lDel_ud_doc_verBean.setFlgautocommitin(0);
		lDel_ud_doc_verBean.setIduddocin(new BigDecimal(idDoc));

		final Del_ud_doc_verImpl lDel_ud_doc_verImp = new Del_ud_doc_verImpl();
		lDel_ud_doc_verImp.setBean(lDel_ud_doc_verBean);

		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lDel_ud_doc_verImp.execute(paramConnection);
			}
		});

		StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
		AnalyzeResult.analyze(lDel_ud_doc_verBean, lStoreResultBean);
		lStoreResultBean.setResultBean(lDel_ud_doc_verBean);

		if (lStoreResultBean.isInError()) {
			logger.error("ErrorStore: " + lStoreResultBean.getDefaultMessage());
			throw new StoreException(lStoreResultBean);
		}	

		return lStoreResultBean.getResultBean();

	}

	private String versionaOrganigramma(String organigrammaXml, String idDoc, String flgUpdate, AurigaLoginBean loginBean, Session session) throws Exception {

		String nroVersione;

		File tempFile = File.createTempFile("tmp", ".xml");

		FileUtils.writeStringToFile(tempFile, organigrammaXml);

		try {

//			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setFile(tempFile);
			lRebuildedFile.setInfo(getFileInfoBean(tempFile));
			lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));
			if (StringUtils.isNotBlank(flgUpdate) && "true".equalsIgnoreCase(flgUpdate)) {
				lRebuildedFile.setUpdateVersion(true);
			}

			logger.debug("Versiono il file");
			nroVersione = versionaDocumento(getVersionaDocumentoInBean(lRebuildedFile), session, loginBean);


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain").build());
		}

		return nroVersione;

	}

	private static File generatePdfFromSvg(String imageSvg) throws Exception {
		String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + imageSvg;

		byte[] bytes = svg.getBytes(Charset.forName ("UTF-8"));
		File svgPdf = File.createTempFile("svgPdfTmp", ".pdf");
		try(FileOutputStream pdfOutputStream = new FileOutputStream(svgPdf)){
			Transcoder transcoder = new PDFTranscoder();
			transcoder.getTranscodingHints();
			TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(bytes));
			TranscoderOutput transcoderOutput = new TranscoderOutput(pdfOutputStream);
			int dpi = 95;
			transcoder.addTranscodingHint(PDFTranscoder.KEY_WIDTH, new Float(dpi * 8.5));
			transcoder.addTranscodingHint(PDFTranscoder.KEY_HEIGHT, new Float(dpi * 11));
			// transcoder.addTranscodingHint(PDFTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER,(25.4f
			// / 72f));
			transcoder.transcode(transcoderInput, transcoderOutput);
		}
		return svgPdf;
	}
	
	private File createPDOrganigramma(String organigrammaXml, String image, String tipoModello) throws Exception {

		logger.debug("call service EditorOrganigramma - funzionigramma");
		JAXBContext contextUn = JAXBContext.newInstance(EditorOrganigrammaTreeNodeBean.class);
		Unmarshaller unmarshaller = contextUn.createUnmarshaller();
		EditorOrganigrammaTreeNodeBean organigrammaBean = (EditorOrganigrammaTreeNodeBean) unmarshaller
				.unmarshal(new StringReader(organigrammaXml));

		File frontespizioPdf = null;
		File organigrammaSvgPdf = null;
		File funzionigrammaPdf = null;

		File templateFrontespizio = new File(context.getRealPath(MODELLO_ORGANIGRAMMA_FRONTESPIZIO));

		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
		DocumentTemplate template = documentTemplateFactory.getTemplate(templateFrontespizio);
		Map<String, Object> mappaFrontespizio = new HashMap<>();
		mappaFrontespizio.put("titoloPdf", organigrammaBean.getDescrUoSvUt());

		File templateOdtFrontespizio = File.createTempFile("tmp", ".odt");

		try (FileOutputStream odtOutputStream = new FileOutputStream(templateOdtFrontespizio)) {

			template.createDocument(mappaFrontespizio, odtOutputStream);
			frontespizioPdf = convertToPdf(templateOdtFrontespizio, "Frontespizio");

			File templateFunzionigramma = null;
			MapsFunzionigrammaBean mapsBean = new MapsFunzionigrammaBean();

			if ("completo".equals(tipoModello)) {
				templateFunzionigramma = new File(context.getRealPath(MODELLO_ORGANIGRAMMA_FUNZIONIGRAMMA));
				organigrammaSvgPdf = generatePdfFromSvg(image);
			} else if ("funzioni".equals(tipoModello)) {
				templateFunzionigramma = new File(context.getRealPath(MODELLO_ORGANIGRAMMA_FUNZIONIGRAMMA));
			} else if ("grafo".equals(tipoModello)) {
				organigrammaSvgPdf = generatePdfFromSvg(image);
			}
			
			//Creo la mappa delle funzioni/competenze da inniettare nel modello
			if ("completo".equals(tipoModello) || "funzioni".equals(tipoModello)) {
				DocumentTemplate templateFunzioni = documentTemplateFactory.getTemplate(templateFunzionigramma);
				// Map<String, List<ValuesTabellaFunzionigrammaBean>> mappa = new HashMap<>();
				Map<String, Object> mappa = new HashMap<>();	
				mappa.put("titoloTabella", organigrammaBean.getDescrUoSvUt());
				
				createMapsFunzionigramma(organigrammaBean, mapsBean, 1);			
				mappa.put("nodes", mapsBean.getListTabellaOrganigramma());

				File templateOdtFunzionigramma = File.createTempFile("tmp", ".odt");

				try (FileOutputStream funzionigrammaOutStrem = new FileOutputStream(templateOdtFunzionigramma)) {

					templateFunzioni.createDocument(mappa, funzionigrammaOutStrem);

					//Nelle funzioni/competenze innietto il valore del CKEditor
					if ("completo".equals(tipoModello) || "funzioni".equals(tipoModello)) {

						//Funzione che mi crea lo sript nell odt per poter inniettare l'html nei placehorder
						createScriptForInjection(templateOdtFunzionigramma);

						IXDocReport report = XDocReportRegistry.getRegistry()
								.loadReport(new FileInputStream(templateOdtFunzionigramma), TemplateEngineKind.Freemarker);

						// 2) Create fields metadata to manage text styling
						FieldsMetadata metadata = report.createFieldsMetadata();
						for (String nomeVariabile : mapsBean.getMapCompetenze().keySet()) {
							metadata.addFieldAsTextStyling(nomeVariabile, SyntaxKind.Html, true);
						}

						// 3) Create context Java model
						IContext context = report.createContext();
						Map<String, Object> htmlSectionsModel = new HashMap<String, Object>();

						for (String key : mapsBean.getMapCompetenze().keySet()) {
							htmlSectionsModel.put(key, mapsBean.getMapCompetenze().get(key));
						}
						context.putMap(htmlSectionsModel);

						// 4) Generate report by merging Java model with the ODT
						try (FileOutputStream odtOutputStream2 = new FileOutputStream(templateOdtFunzionigramma)) {

							report.process(context, odtOutputStream2);

						} catch (Exception ex) {
							throw new Exception();
						}

						funzionigrammaPdf = convertToPdf(templateOdtFunzionigramma, "Funzionigramma");

					}

				} catch (Exception ex) {
					logger.error("Errore durante la creazione del modello: " + ex.getMessage(), ex);
					throw new Exception(ex.getMessage(), ex);
				}
			}
		} catch (Exception ex) {
			logger.error("Errore durante la creazione del modello del frontespizio: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		
		File pdfUnione = new File("Unione.pdf");
		PDFMergerUtility pdf = new PDFMergerUtility();
		pdf.setDestinationFileName(pdfUnione.getPath());
		pdf.addSource(frontespizioPdf);
		if (organigrammaSvgPdf != null) {
			pdf.addSource(organigrammaSvgPdf);
		}
		if (funzionigrammaPdf != null) {
			pdf.addSource(funzionigrammaPdf);
		}
		pdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

		return pdfUnione;
	}

	private ExtractVerDocOutBean getVersionStore(AurigaLoginBean loginBean, String idDoc, String nroVers) throws Exception {

		DmpkCoreExtractverdocBean lDmpkCoreExtractverdocBean = new DmpkCoreExtractverdocBean();
		lDmpkCoreExtractverdocBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkCoreExtractverdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkCoreExtractverdocBean.setIddocin(new BigDecimal(idDoc));
		lDmpkCoreExtractverdocBean.setNroprogrverio(StringUtils.isNotBlank(nroVers) ? new BigDecimal(nroVers) : null);

		// eseguo il servizio
		Extractverdoc lExtractverdoc = new Extractverdoc();

		StoreResultBean<DmpkCoreExtractverdocBean> lStoreResultBean2 = lExtractverdoc.execute(loginBean,lDmpkCoreExtractverdocBean);

		if (lStoreResultBean2.isInError()) {
			logger.error("ErrorStore: " + lStoreResultBean2.getDefaultMessage());
			throw new StoreException(lStoreResultBean2.getDefaultMessage());
		}

		// leggo output
		ExtractVerDocOutBean lExtractVerDocOutBean = new ExtractVerDocOutBean();
		lExtractVerDocOutBean.setNroProgrVerOut(lStoreResultBean2.getResultBean().getNroprogrverio());
		lExtractVerDocOutBean.setDisplayFilenameVerOut(lStoreResultBean2.getResultBean().getDisplayfilenameverout());
		lExtractVerDocOutBean.setUriOut(lStoreResultBean2.getResultBean().getUriverout());
		lExtractVerDocOutBean.setImprontaVerOut(lStoreResultBean2.getResultBean().getImprontaverout());
		lExtractVerDocOutBean.setAlgoritmoImprontaVerOut(lStoreResultBean2.getResultBean().getAlgoritmoimprontaverout());
		lExtractVerDocOutBean.setEncodingImprontaVerOut(lStoreResultBean2.getResultBean().getEncodingimprontaverout());
		lExtractVerDocOutBean.setDimensioneVerOut(lStoreResultBean2.getResultBean().getDimensioneverout());
		lExtractVerDocOutBean.setMimetypeVerOut(lStoreResultBean2.getResultBean().getMimetypeverout());
		lExtractVerDocOutBean.setFlgFirmataVerOut(lStoreResultBean2.getResultBean().getFlgfirmataverout());

		return lExtractVerDocOutBean;
	}

	private EditorOrganigrammaTreeNodeBean buildNodeFromValoriRiga(Vector<String> v, BigDecimal nroProgr) {
		EditorOrganigrammaTreeNodeBean node = new EditorOrganigrammaTreeNodeBean();
		node.setNroProgr(nroProgr);
		node.setIdNode(v.get(1)); // colonna 2 dell'xml
		node.setIdNodeDB(v.get(1));
		node.setIdUo(v.get(2)); // colonna 3 dell'xml
		node.setTipo("UO_" + v.get(3)); // colonna 4 dell'xml
		node.setCodRapidoUo(v.get(7)); // colonna 8 dell'xml
		node.setDescrUoSvUt(v.get(5)); // colonna 6 dell'xml
		node.setFlgEsplodiNodo(v.get(8)); // colonna 9 dell'xml
				
		String parentId = node.getIdNode().substring(0, node.getIdNode().lastIndexOf("/"));
		if (parentId == null || "".equals(parentId)) {
			if (!"/".equals(node.getIdNode())) {
				parentId = "/";
			}
		}
		node.setParentIdDB(parentId);
		node.setParentId(v.get(4)); // colonna 5 dell'xml
		node.setCompetenze(v.get(9)); // colonna 10 dell'xml
		node.setLevel(v.get(10) != null ? new BigDecimal(v.get(10)) : null); // colonna 11 dell'xml
		
		return node;
	}

	private EditorOrganigrammaTreeNodeBean getResponseData(Map<String, EditorOrganigrammaTreeNodeBean> pNodes,
			String idRootNode) {
		if (pNodes.isEmpty()) {
			return new EditorOrganigrammaTreeNodeBean();
		}

		buildHierarchy(idRootNode, buildRelations(pNodes, idRootNode), pNodes);
		return pNodes.get(idRootNode);
	}

	protected Map<Integer, List<EditorOrganigrammaTreeNodeBean>> buildRelations(
			Map<String, EditorOrganigrammaTreeNodeBean> pNodes, String idRootNode) {
		Map<Integer, List<EditorOrganigrammaTreeNodeBean>> relation = new TreeMap<>();

		for (EditorOrganigrammaTreeNodeBean lEditorNode : pNodes.values()) {
			if (lEditorNode.getParentIdDB().equals("") || lEditorNode.getIdNode().equals(idRootNode)) {
				ArrayList<EditorOrganigrammaTreeNodeBean> rootNode = new ArrayList<EditorOrganigrammaTreeNodeBean>();
				rootNode.add(lEditorNode);
				relation.put(lEditorNode.getLevel().intValue(), rootNode);
			} else {
				if (!relation.containsKey(lEditorNode.getLevel().intValue())) {
					relation.put(lEditorNode.getLevel().intValue(), new ArrayList<EditorOrganigrammaTreeNodeBean>());
				}
				relation.get(lEditorNode.getLevel().intValue()).add(lEditorNode);
			}
		}
		return relation;
	}

	private void buildHierarchy(String parentId, Map<Integer, List<EditorOrganigrammaTreeNodeBean>> relationship,
			Map<String, EditorOrganigrammaTreeNodeBean> nodes) {
		Integer rootLevel = nodes.get(parentId).getLevel().intValue();
		Integer maxLevel = Collections.max(relationship.keySet());
		
		for (Integer iLvl = maxLevel; iLvl > rootLevel; iLvl--) {
			for (EditorOrganigrammaTreeNodeBean childNode : relationship.get(iLvl)) {
				nodes.get(childNode.getParentIdDB()).getChildren().add(childNode);
			}
		}
	}

	/**
	 * 
	 * @param r
	 *            la riga di cui si vogliono estrarre i valori
	 * @return Vector contenente in formato stringa i valori dei campi della riga
	 *         specificata.<br/>
	 *         ATTENZIONE: il Vector è una struttura dati sincronizzata, quindi più
	 *         lenta nell'accesso ai dati.<br/>
	 *         Se non si devono gestire problematicità relative alla concorrenza,
	 *         per questioni di performance sarbbe meglo utilizzare il corrispettivo
	 *         metodo che restituisce una lista
	 */
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
					// TODO Auto-generated catch block
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
	 * @param riga
	 *            la riga di cui si vogliono estrarre i valori
	 * @return lista contenente in formato stringa i valori dei campi della riga
	 *         specificata
	 */
	public List<String> getValoriRigaAsList(Riga riga) {

		List<String> retValue = new ArrayList<String>();

		int oldNumCol = 0;

		for (int j = 0; j < riga.getColonna().size(); j++) {

			// Aggiungo le colonne vuote
			for (int k = (oldNumCol + 1); k < riga.getColonna().get(j).getNro().intValue(); k++)
				retValue.add(null);

			String content = riga.getColonna().get(j).getContent();

			// aggiungo la colonna
			if (StringUtils.isNotBlank(content)) {

				try {
					SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(content));
					retValue.add(content);
				} catch (JAXBException e) {
					retValue.add(content.replace("\n", "<br/>"));
				}

			} else {
				retValue.add(null);
			}

			// aggiorno l'ultimo numero di colonna
			oldNumCol = riga.getColonna().get(j).getNro().intValue();
		}
		return retValue;
	}

	public static <T> List<T> recuperaLista(String xmlIn, Class<T> lClass) throws Exception {
		Lista lLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		Field[] lFields = ReflectionUtility.getFields(lClass);
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		List<T> lList = new ArrayList<T>();

		for (Riga lRiga : lLista.getRiga()) {
			Object lObject = lClass.newInstance();
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null) {
					int index = Integer.valueOf(lNumeroColonna.numero());
					for (Colonna lColonna : lRiga.getColonna()) {
						if (lColonna.getNro().intValue() == index) {
							String value = lColonna.getContent();
							if (lTipoData != null) {
								if (StringUtils.isNotBlank(value)) {
									SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
											lTipoData.tipo().getPattern());
									Date lDateValue = lSimpleDateFormat.parse(value);
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(),
											lDateValue);
									// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(),
									// lDateValue);
								}
							} else
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), value);
							// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), value);
						}
					}
				}
			}
			lList.add((T) lObject);
		}
		return lList;
	}

	private static void createScriptForInjection(File templateOdtWithValues) throws Exception {
		OdfPackage odfPackage = OdfPackage.loadPackage(templateOdtWithValues);
		OdfDocument odfDocument = OdfTextDocument.loadDocument(odfPackage);
		for (OdfTable table : odfDocument.getTableList()) {
			for (int i = 1; i < table.getColumnByIndex(1).getCellCount(); i++) {
				OdfTableCell cell = table.getColumnByIndex(1).getCellByIndex(i);
				Node ele = cell.getOdfElement().getFirstChild();
				if (StringUtils.isNotBlank(ele.getTextContent())) {
					TextInputElement textinput = new TextInputElement(odfDocument.getContentDom());
					textinput.setOdfAttributeValue(TextDescriptionAttribute.ATTRIBUTE_NAME, "jooscript");
					textinput.setTextContent(ele.getTextContent());
					ele.setTextContent("");
					ele.appendChild(textinput);
				}
			}
		}
		odfDocument.save(templateOdtWithValues);
	}

	private static int createMapsFunzionigramma(EditorOrganigrammaTreeNodeBean organigrammaBean,
			MapsFunzionigrammaBean mapsBean, int cont) {

		if (organigrammaBean != null) {
			List<ValuesTabellaFunzionigrammaBean> listTabellaOrganigramma = mapsBean.getListTabellaOrganigramma();
			Map<String, String> mapCompetenzeWithValue = mapsBean.getMapCompetenze();

			if (StringUtils.isNotBlank(organigrammaBean.getDescrUoSvUt())) {
				ValuesTabellaFunzionigrammaBean bean = new ValuesTabellaFunzionigrammaBean();
				bean.setStruttura(organigrammaBean.getDescrUoSvUt());
				bean.setCompetenza("${" + KEY_COMPETENZA_FREEMARKER + cont + "}");
				listTabellaOrganigramma.add(bean);

				String competenze = "";

				if(StringUtils.isNotBlank(organigrammaBean.getCompetenze())) {
					competenze = organigrammaBean.getCompetenze().replaceAll("\n", "").replaceAll("\t", "");
				}else {
					flgWarning = true;
				}

				mapCompetenzeWithValue.put(KEY_COMPETENZA_FREEMARKER + cont, competenze);

				cont++;
			}

			for (EditorOrganigrammaTreeNodeBean children : organigrammaBean.getChildren()) {
				cont = createMapsFunzionigramma(children, mapsBean, cont);
			}
		}

		return cont;

	}

	private File convertToPdf(File modelloWithValues, String nomeFile) throws Exception {
		File pdfDaModello;
		try {
			InputStream targetStream = TimbraUtil.converti(modelloWithValues, nomeFile);
			pdfDaModello = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(targetStream, pdfDaModello);
		} catch (Exception e) {
			logger.error("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
			throw new Exception("Errore durante la conversione in pdf del modello");
		}

		return pdfDaModello;
	}

	protected VersionaDocumentoInBean getVersionaDocumentoInBean(RebuildedFile lRebuildedFile)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
		return lVersionaDocumentoInBean;
	}

	private String versionaDocumento(VersionaDocumentoInBean lVersionaDocumentoInBean, Session session, AurigaLoginBean loginBean)
			throws Exception {


		String nroVerisone = null;

		try {

			String uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), null);
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
				lUpdverdocBean.setCodidconnectiontokenin(loginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setAttributixmlin(lStringXml);

				final UpdverdocImpl storeUpd = new UpdverdocImpl();
				storeUpd.setBean(lUpdverdocBean);

				logger.debug("Chiamo la updverdoc " + lUpdverdocBean);

				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						storeUpd.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreUpdverdocBean>();
				AnalyzeResult.analyze(lUpdverdocBean, lUpdverdocStoreResultBean);
				lUpdverdocStoreResultBean.setResultBean(lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {
					logger.error("Errore durante il versionamento del file: "
							+ lUpdverdocStoreResultBean.getDefaultMessage());
					throw new Exception("Errore durante il versionamento del file");
				}

				nroVerisone = (lUpdverdocStoreResultBean.getResultBean().getNroprogrverio()).toString();

			} else {
				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(loginBean.getToken());
				lAddverdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
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
					logger.error("Errore durante il versionamento del file: "
							+ lAddverdocStoreResultBean.getDefaultMessage());
					throw new Exception("Errore durante il versionamento del file");
				}

				nroVerisone = (lAddverdocStoreResultBean.getResultBean().getNroprogrverio()).toString();
			}
		} catch (Throwable e) {
			logger.error("Errore durante il versionamento del file: " + e.getMessage(), e);
			throw new Exception("Errore durante il versionamento del file");
		}

		return nroVerisone;
	}

	protected FileInfoBean getFileInfoBean(File file) throws Exception {
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		try {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(file.toURI().toString(), file.getName(), false,
					null);
		} catch (Exception e) {
			logger.error("Errore durante il recupero delle informazioni del file " + file.getName() + " - path : "
					+ file.getPath());
			throw new Exception(e.getMessage(), e);
		}

		GenericFile allegatoRiferimento = new GenericFile();
		allegatoRiferimento.setImpronta(lMimeTypeFirmaBean.getImpronta());
		allegatoRiferimento.setMimetype(lMimeTypeFirmaBean.getMimetype());
		allegatoRiferimento.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
		allegatoRiferimento.setEncoding(lMimeTypeFirmaBean.getEncoding());
		allegatoRiferimento.setIdFormato(lMimeTypeFirmaBean.getIdFormato());
		if(StringUtils.isNotBlank(displayFileNamePdf)) {
			allegatoRiferimento.setDisplayFilename(displayFileNamePdf);
			displayFileNamePdf = "";
		} else {
			allegatoRiferimento.setDisplayFilename(file.getName());
		}
		FileInfoBean info = new FileInfoBean();
		info.setTipo(TipoFile.ALLEGATO);
		info.setAllegatoRiferimento(allegatoRiferimento);
		return info;
	}

	private String calculateIdNodeFromIdUO(String idUo, AurigaLoginBean loginBean) throws Exception {
		DmpkDefSecurityGetlivpathexuoBean input = new DmpkDefSecurityGetlivpathexuoBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setIduoin(new BigDecimal(idUo));
		input.setFlgincludiutentiin(0);
		input.setTiporelutenticonuoin("A;D");
		input.setFlgsoloattivein(1);
		//		input.setIdorganigrammain(new BigDecimal(2));

		DmpkDefSecurityGetlivpathexuo getlivpathexuo = new DmpkDefSecurityGetlivpathexuo();
		StoreResultBean<DmpkDefSecurityGetlivpathexuoBean> output = getlivpathexuo.execute(null, loginBean, input);

		String idNode = "";
		if (output.getResultBean().getPercorsoxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getPercorsoxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = getValoriRiga(lista.getRiga().get(i));
					if (i == 0) {
						idNode = "/";
					} else {
						if (!idNode.endsWith("/")) {
							idNode += "/";
						}
						idNode += v.get(0);
					}
				}
			}
		}

		return idNode;
	}

	private void fixIdForOrganigrammaClient(String idRootNode, EditorOrganigrammaTreeNodeBean organigrammaTreeNodeBean) {

		if(organigrammaTreeNodeBean != null) {
			String newId = organigrammaTreeNodeBean.getIdNode().replaceAll(idRootNode, "_").replaceAll("/", "_");
			organigrammaTreeNodeBean.setIdNode(newId);
			organigrammaTreeNodeBean.setIdNodeOrig(newId);
			organigrammaTreeNodeBean.setIdNodeDB(organigrammaTreeNodeBean.getIdNodeDB().replaceAll("/", "_"));
			String newParentId; 
			if(newId.equalsIgnoreCase("_")) {
				newParentId = "";
			} else if (newId.substring(0, newId.lastIndexOf('_')).equals("")) {
				newParentId = "_";  
			} else {
				newParentId = newId.substring(0, newId.lastIndexOf('_'));
			}

			organigrammaTreeNodeBean.setParentId(newParentId);
			organigrammaTreeNodeBean.setParentIdDB(organigrammaTreeNodeBean.getParentIdDB().replaceAll("/", "_"));
			for(EditorOrganigrammaTreeNodeBean lOrganigrammaTreeNodeBean : organigrammaTreeNodeBean.getChildren()) {
				fixIdForOrganigrammaClient(idRootNode.lastIndexOf("/") != 0 ? idRootNode : idRootNode+"/", lOrganigrammaTreeNodeBean);
			}
		}	
	}

}