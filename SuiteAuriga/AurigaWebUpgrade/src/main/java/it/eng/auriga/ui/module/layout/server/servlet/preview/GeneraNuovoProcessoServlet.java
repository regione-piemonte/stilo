/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Locale;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.AvvioProcedimentoService;
import it.eng.client.DmpkLoginLogin;
import it.eng.config.WorkflowBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.core.service.client.config.Configuration;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceInBean;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/nuovoProcesso")
public class GeneraNuovoProcessoServlet {


	private static Logger logger = Logger.getLogger(GeneraNuovoProcessoServlet.class);

	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody

	public  ResponseEntity<String> download(@RequestParam("idProcessType") Integer idProcessType, 
			@RequestParam("flowTypeId") String flowTypeId,
			@RequestParam("oggettoProc") String oggettoProc,
			@RequestParam("noteProc") String noteProc,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException{
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		try {
			
			Locale locale = Locale.ITALY;
			AurigaLoginBean loginBean;
			DmpkLoginLogin lDmpkLoginLogin = new DmpkLoginLogin();

			loginBean = new AurigaLoginBean();
			// Inserisco la lingua di default
			loginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			loginBean.setIdUtente("VALE");
			loginBean.setPassword("test1234");
			loginBean.setSchema("OWNER_1");

			DmpkLoginLoginBean lLBean = new DmpkLoginLoginBean();
			lLBean.setUsernamein("VALE");
			lLBean.setPasswordin("test1234");
			StoreResultBean<DmpkLoginLoginBean> result1 = lDmpkLoginLogin.execute(locale, loginBean, lLBean);

			String token = result1.getResultBean().getCodidconnectiontokenout();

			loginBean.setToken(token);

			String desUser = result1.getResultBean().getDesuserout();
			String codFisUser = null;
			if(StringUtils.isNotBlank(desUser) && desUser.contains(";CF:")) {
				int pos = desUser.indexOf(";CF:");					
				codFisUser = desUser.substring(pos + 4);		
				desUser = desUser.substring(0, pos);
			}
			SpecializzazioneBean spec = new SpecializzazioneBean();
			spec.setCodIdConnectionToken(result1.getResultBean().getCodidconnectiontokenout());
			spec.setDesDominioOut(result1.getResultBean().getDesdominioout());
			spec.setDesUserOut(desUser);
			spec.setCodFiscaleUserOut(codFisUser);
			spec.setIdDominio(result1.getResultBean().getIddominioautio());
			spec.setParametriConfigOut(result1.getResultBean().getParametriconfigout());
			spec.setTipoDominio(result1.getResultBean().getFlgtpdominioautio());
			loginBean.setSpecializzazioneBean(spec);
			logger.debug("Start test client");
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");					
			lStringBuffer.append("<body>");
			AvvioProcedimentoServiceInBean lAvvioProcedimentoServiceInBean =
				new AvvioProcedimentoServiceInBean();
			lAvvioProcedimentoServiceInBean.setFlowTypeId(flowTypeId);
			lAvvioProcedimentoServiceInBean.setIdProcessType(idProcessType);
			lAvvioProcedimentoServiceInBean.setNoteProc(noteProc);
			lAvvioProcedimentoServiceInBean.setOggettoProc(oggettoProc);

			AvvioProcedimentoService lAvvioProcedimentoService = new AvvioProcedimentoService();
			AvvioProcedimentoServiceOutBean lAvvioProcedimentoServiceOutBean = 
				lAvvioProcedimentoService.avviaprocesso(locale, loginBean, lAvvioProcedimentoServiceInBean);
			logger.debug(lAvvioProcedimentoServiceOutBean.getEsito());
			if (lAvvioProcedimentoServiceOutBean.getEsito()){
				logger.debug("Id processo " + lAvvioProcedimentoServiceOutBean.getIdProcesso());
				logger.debug("Process instance " + lAvvioProcedimentoServiceOutBean.getProcessInstanceId());
				logger.debug("Id folder " + lAvvioProcedimentoServiceOutBean.getIdFolder());
				logger.debug("Process definition id " + lAvvioProcedimentoServiceOutBean.getProcessDefinitionId());
			} else {
				logger.debug(lAvvioProcedimentoServiceOutBean.getError());
			}
			lStringBuffer.append("<h1>" + "idProcesso " + lAvvioProcedimentoServiceOutBean.getIdProcesso() + "<h1>");
			lStringBuffer.append("<h1>" + "processInstanceId " + lAvvioProcedimentoServiceOutBean.getProcessInstanceId() + "<h1>");
			lStringBuffer.append("<h1>" + "idFolder " + lAvvioProcedimentoServiceOutBean.getIdFolder() + "<h1>");
			lStringBuffer.append("<h1>" + "processDefinitionId " + lAvvioProcedimentoServiceOutBean.getProcessDefinitionId() + "<h1>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");	
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);	
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");					
			lStringBuffer.append("<body>");
			lStringBuffer.append("<h1>Errore " + e.getMessage() + "</h1>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");	
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);	
		}


	}
}