/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheReportdoc_groupbysp_uoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.DmpkLoginLogin;
import it.eng.client.DmpkStatisticheReportdoc_groupbysp_uo;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;

@Controller
@RequestMapping("/percentageServlet")
public class PercentageServlet {

	Logger logger = Logger.getLogger(PercentageServlet.class);
	private static String TITLE = "GRAFICO PRINCIPALE";

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> download(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam(required = false, value = "idUtente") String pStrIdUtente, @RequestParam(required = false, value = "idSchema") String pStrSchema,
			@RequestParam(required = false, value = "idDominio") String pStrIdDominio,
			@RequestParam(required = false, value = "richiesta") String pIntRichiesta, @RequestParam(required = false, value = "level") Integer pIntLevel,
			@RequestParam(required = false, value = "da") String pStrDa, @RequestParam(required = false, value = "a") String pStrA,
			@RequestParam(required = false, value = "tipoReport") String pStrTipoReport,
			@RequestParam(required = false, value = "tipoDiRegistrazione") String pStrTipoRegistrazione) throws Exception {
		
		InputStream is = null;
		try {
			logger.debug("Richiesto da " + pStrIdUtente);
			logger.debug("idSchema " + pStrSchema);
			logger.debug("idDominio " + pStrIdDominio);
			logger.debug("richiesta " + pIntRichiesta);
			logger.debug("level " + pIntLevel);
			DmpkLoginLogin login = new DmpkLoginLogin();
			DmpkLoginLoginBean input = new DmpkLoginLoginBean();
			input.setUsernamein(pStrIdUtente);
			input.setFlgtpdominioautio(1);
			try {
				BigDecimal pBigDecIdDominio = new BigDecimal(pStrIdDominio);
				input.setIddominioautio(pBigDecIdDominio);
			} catch (NumberFormatException e) {
				logger.error("Errore nella conversione del dominio " + pStrIdDominio + e.getMessage(), e);
			}
			AurigaLoginBean pAurigaLoginBean = new AurigaLoginBean();
			// Inserisco la lingua di default
			pAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			pAurigaLoginBean.setSchema(pStrSchema);
			pAurigaLoginBean.setDominio(pStrIdDominio);
			input.setFlgnoctrlpasswordin(1);
			StoreResultBean<DmpkLoginLoginBean> output = login.execute(UserUtil.getLocale(req.getSession()), pAurigaLoginBean, input);
			DmpkStatisticheReportdoc_groupbysp_uoBean lDmpkStatisticheReportdoc_groupbysp_uoBean = new DmpkStatisticheReportdoc_groupbysp_uoBean();
			lDmpkStatisticheReportdoc_groupbysp_uoBean.setDatadain(pStrDa);
			lDmpkStatisticheReportdoc_groupbysp_uoBean.setDataain(pStrA);
			// Se è diverso da -1 allora è una richiesta, altrimenti l'idsp viene ricavato dal token
			if (!pIntRichiesta.equals("-1")) {
				lDmpkStatisticheReportdoc_groupbysp_uoBean.setIdspaoouoreportonin(Integer.valueOf(pIntRichiesta));
			}
			lDmpkStatisticheReportdoc_groupbysp_uoBean.setLivellodettreportin(pIntLevel);
			if (StringUtils.isNotEmpty(pStrTipoRegistrazione) && !pStrTipoRegistrazione.equals("null"))
				lDmpkStatisticheReportdoc_groupbysp_uoBean.setTipiregistrazioniin(pStrTipoRegistrazione);
			lDmpkStatisticheReportdoc_groupbysp_uoBean.setTiporeportin(pStrTipoReport);
			DmpkStatisticheReportdoc_groupbysp_uo lDmpkStatisticheReportdoc_groupbysp_uo = new DmpkStatisticheReportdoc_groupbysp_uo();
			AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
			// Inserisco la lingua di default
			lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			lAurigaLoginBean.setDominio(pStrIdDominio);
			lAurigaLoginBean.setIdUtente(pStrIdUtente);
			lAurigaLoginBean.setSchema(pStrSchema);
			lAurigaLoginBean.setToken(output.getResultBean().getCodidconnectiontokenout());
			List<String> lListLabel = new ArrayList<String>();
			String title = null;
			try {
				StoreResultBean<DmpkStatisticheReportdoc_groupbysp_uoBean> result = lDmpkStatisticheReportdoc_groupbysp_uo
						.execute(UserUtil.getLocale(req.getSession()), lAurigaLoginBean, lDmpkStatisticheReportdoc_groupbysp_uoBean);
				if (result.isInError()) {
					throw new Exception(result.getDefaultMessage());
				} else {
					lDmpkStatisticheReportdoc_groupbysp_uoBean = result.getResultBean();
				}
				title = lDmpkStatisticheReportdoc_groupbysp_uoBean.getReporttitleout();
				String xmlOut = lDmpkStatisticheReportdoc_groupbysp_uoBean.getReportcontentsxmlout();
	
				List<ReportResultBean> lList = XmlListaUtility.recuperaLista(xmlOut, ReportResultBean.class);
	
				for (ReportResultBean bean : lList) {
					StringBuffer lStringBuffer = new StringBuffer();
					lStringBuffer.append(bean.getLabel() + ";");
					lStringBuffer.append(bean.getValore() + ";");
					lStringBuffer.append(bean.getPerc() + ";");
					lStringBuffer.append(bean.getPercArrotondata() + ";");
					String idSoggetto = bean.getIdSoggetto() != null ? bean.getIdSoggetto() : "";
					lListLabel.add(idSoggetto.replaceAll("\\.", "") + "=" + lStringBuffer.toString());
				}
			} catch (Exception e) {
				lListLabel = new ArrayList<String>();
				title = "Errore";
				logger.error("Errore " + e.getMessage(), e);
			}
	
			if (title == null)
				title = "";
			lListLabel.add("title=" + title.replace('\n', '#'));
			if (pIntLevel != null) {
				if (pStrTipoReport.equals("tempi_medi_tra_assegnazione_e_presa_in_carico")) {
					lListLabel.add("asseX=" + "Chi");
					lListLabel.add("asseY=" + "Ore");
				}
			}
			File lFile = File.createTempFile("perc", "");
			FileUtils.writeLines(lFile, lListLabel);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("octet", "stream"));
			is = new FileInputStream(lFile);
			byte[] documentBody = IOUtils.toByteArray(is);
			header.set("Content-Disposition", "attachment");
			header.setContentLength(documentBody.length);
			return new HttpEntity<byte[]>(documentBody, header);
		} finally {
			try {
				is.close();
			} catch (Exception e) { }
		}
	}
}
