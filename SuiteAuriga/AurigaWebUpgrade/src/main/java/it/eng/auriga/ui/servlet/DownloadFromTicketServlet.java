/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginGetparametriconfig_jBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DownloadTicketBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkLoginGetparametriconfig_j;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.xml.XmlUtilitySerializer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/downloadUriFromTicket")
public class DownloadFromTicketServlet {

	private String ATTIVA_AGG_STATO_UD_DOWNLOD_LINK = "";
	private String CONN_TOKEN_X_LINK_DOWNLOAD = "";
	private String SPEC_LABEL_GUI = "";

	private TPagingList<DownloadTicketBean> resultDownloadTicket = null;

	Logger logger = Logger.getLogger(DownloadFromTicketServlet.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> download(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String mess = "";

		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("octet", "stream"));

			String ticketId = req.getParameter("ticketId") != null ? req.getParameter("ticketId") : "";
						
			if(ticketId.equalsIgnoreCase("")){
				ticketId = req.getParameter("ticketid") != null ? req.getParameter("ticketid") : "";
			}
				
		    if (ticketId.equals("")) {
			    throw new Exception("Il parametro TicketId non è presente sulla request.");
		    }
		
			String schema = req.getParameter("schema") != null ? req.getParameter("schema") : "";
			
			if (schema.equals("")) {
				throw new Exception("Il parametro schema non è presente sulla request.");
			}
			
			String tpDominio = req.getParameter("tpDominio") != null ? req.getParameter("tpDominio") : "";
			
			if(tpDominio.equalsIgnoreCase("")){
				tpDominio = req.getParameter("tpdominio") != null ? req.getParameter("tpdominio") : "";
			}
				
			if (tpDominio.equals("")) {
				throw new Exception("Il parametro tpDominio non è presente sulla request.");
			}
			
			String idDominio = req.getParameter("idDominio") != null ? req.getParameter("idDominio") : "";

			
			if(idDominio.equalsIgnoreCase("")){
				idDominio = req.getParameter("iddominio") != null ? req.getParameter("iddominio") : "";
			}
			
			if (idDominio.equals("")) {
				throw new Exception("Il parametro idDominio non è presente sulla request.");
			}
			
			
			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setSchema(schema);

			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(loginBean.getSchema());

			DownloadTicketBean downloadTicketBean = new DownloadTicketBean();
			TFilterFetch<DownloadTicketBean> filter = new TFilterFetch<DownloadTicketBean>();
	
			setParametriLoginFromDVB(tpDominio, idDominio, lSchemaBean);
			downloadTicketBean.setTicketId(ticketId);
			filter.setFilter(downloadTicketBean);

			// Leggo i dati del ticket dalla tabella T_DOWNLOAD_TICKET
			resultDownloadTicket = AurigaService.getDaoDownloadTicket().search(new Locale("it"), loginBean, filter);

			if (resultDownloadTicket.getData() == null) {
				throw new Exception("Il file non è più disponibile a questo indirizzo.");
			}
			
			if (resultDownloadTicket.getData().size() == 0) {
				throw new Exception("Il file non è più disponibile a questo indirizzo.");
			}
			
			if (resultDownloadTicket.getData().size() > 1) {
				throw new Exception("Esistono piu' documenti con i riferimenti indicati.Non e' possibile mostrare il doumento.");
			}
								
			

			String uri = resultDownloadTicket.getData().get(0).getUri();
			if (uri == null){
					uri = "";
			}

			BigDecimal idUdFattura = new BigDecimal(0);
			idUdFattura = resultDownloadTicket.getData().get(0).getIdUd();
			String filename = resultDownloadTicket.getData().get(0).getDisplayFilename();

			if (uri.equals("")) {
				throw new Exception("Il file non è più disponibile a questo indirizzo.");
			}

			if (uri.equals("#FILE_REMOVED")) {
				throw new Exception("Il file non è più parte del documento: è stato annullato.");
			}

			if (idUdFattura == null) {
				throw new Exception("Il file non è più disponibile a questo indirizzo.");
			}

			// Estraggo il file elettornico
			byte[] documentBody = null;
			try {
				if (resultDownloadTicket.getData().get(0).isFlgDaSbustare()) {
					InfoFileUtility lInfoFileUtility = new InfoFileUtility();
					String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
					documentBody = IOUtils.toByteArray(lInfoFileUtility.sbusta(fileUrl, filename));
				} else {
					File file = StorageImplementation.getStorage().extractFile(uri);
					documentBody = IOUtils.toByteArray(FileUtils.openInputStream(file));
				}
			} catch (Exception e2) {
				throw new Exception("Il file non è più disponibile a questo indirizzo.");
			}

			header.set("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			header.setContentLength(documentBody.length);

			boolean flgAttivaAggStatoUdDownloadLink = ATTIVA_AGG_STATO_UD_DOWNLOD_LINK != null && !"".equals(ATTIVA_AGG_STATO_UD_DOWNLOD_LINK) && ("1".equals(ATTIVA_AGG_STATO_UD_DOWNLOD_LINK) || "true".equals(ATTIVA_AGG_STATO_UD_DOWNLOD_LINK)) ? true : false;
			
			// Aggiorno lo stato del documento
			if (flgAttivaAggStatoUdDownloadLink) {

					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					try {
						DmpkCoreUpddocudBean lUpddocudInput = new DmpkCoreUpddocudBean();
						lUpddocudInput.setCodidconnectiontokenin(loginBean.getToken());
						lUpddocudInput.setIduserlavoroin(null);
						lUpddocudInput.setIduddocin(idUdFattura);
						lUpddocudInput.setFlgtipotargetin("U");
						lUpddocudInput.setFlgautocommitin(1);
						CreaModDocumentoInBean attributiUdFattura = new CreaModDocumentoInBean();

						attributiUdFattura.setCodStato("CONS");

						lUpddocudInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiUdFattura));

						String tokenConnessione = CONN_TOKEN_X_LINK_DOWNLOAD;
						loginBean.setToken(tokenConnessione);

						DmpkCoreUpddocud lUpddocud = new DmpkCoreUpddocud();
						StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lUpddocud.execute(new Locale("it"), loginBean, lUpddocudInput);

						if (lUpddocudOutput.isInError()) {
							String message = "Non riesco ad aggiornare lo stato del documento. Errore nella store DMPK_CORE.UpdDocUd : " + lUpddocudOutput.getDefaultMessage();
							throw new Exception(message);
						}
					} catch (Exception e3) {
						throw new Exception(e3.getMessage());
					}
			}
			return new HttpEntity<byte[]>(documentBody, header);			
		}

		catch (Exception e) {
			mess = e.getMessage();
		}

		error(resp, mess);

		return null;
	}

	private void setParametriLoginFromDVB(String fltTpDominioInput, String idDominioInput, SchemaBean lSchemaBean) throws Exception, StoreException {
		DmpkLoginGetparametriconfig_jBean inputLogin = new DmpkLoginGetparametriconfig_jBean();
		inputLogin.setFlgtpdominioautin(new Integer(fltTpDominioInput));
		inputLogin.setIddominioautin(new BigDecimal(idDominioInput));
		DmpkLoginGetparametriconfig_j dmpkLoginGetparametriconfig_j = new DmpkLoginGetparametriconfig_j();
		StoreResultBean<DmpkLoginGetparametriconfig_jBean> resultLogin = dmpkLoginGetparametriconfig_j.execute(new Locale("it"), lSchemaBean, inputLogin);
		if (StringUtils.isNotBlank(resultLogin.getDefaultMessage())) {
			if (resultLogin.isInError()) {
				throw new StoreException(resultLogin);
			}
		}
		Map<String, String> mapValues = new HashMap<String, String>();
		if (resultLogin.getResultBean().getXmlparametriout() != null && resultLogin.getResultBean().getXmlparametriout().length() > 0) {
			StringReader sr = new StringReader(resultLogin.getResultBean().getXmlparametriout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					String key = v.get(0);
					String value = v.get(1);
					mapValues.put(key, value);
				}
			}
		}

		if (mapValues.size() > 0) {
			if (mapValues.get("ATTIVA_AGG_STATO_UD_DOWNLOD_LINK") != null && !"".equals(mapValues.get("ATTIVA_AGG_STATO_UD_DOWNLOD_LINK"))) {
				ATTIVA_AGG_STATO_UD_DOWNLOD_LINK = mapValues.get("ATTIVA_AGG_STATO_UD_DOWNLOD_LINK");
			}
			if (mapValues.get("CONN_TOKEN_X_LINK_DOWNLOAD") != null && !"".equals(mapValues.get("CONN_TOKEN_X_LINK_DOWNLOAD"))) {
				CONN_TOKEN_X_LINK_DOWNLOAD = mapValues.get("CONN_TOKEN_X_LINK_DOWNLOAD");
			}

			if (mapValues.get("SPEC_LABEL_GUI") != null && !"".equals(mapValues.get("SPEC_LABEL_GUI"))) {
				SPEC_LABEL_GUI = mapValues.get("SPEC_LABEL_GUI");
			}
		}
	}

	private void error(HttpServletResponse resp, String messaggio) throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert(' " + messaggio + " ');");
		out.println("</script>");
		out.flush();
	}
}