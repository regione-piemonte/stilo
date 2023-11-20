/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityDprofiloBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIuprofiloBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettprofiloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.DettaglioProfiloBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.ProfiloUtenteBean;
import it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean.XmlDatiProfiloOutBean;
import it.eng.client.DmpkDefSecurityDprofilo;
import it.eng.client.DmpkDefSecurityIuprofilo;
import it.eng.client.DmpkDefSecurityLoaddettprofilo;
import it.eng.utility.ui.module.core.server.datasource.AbstractFormDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


@Datasource(id="AurigaDettaglioProfiloDataSource")
public class AurigaDettaglioProfiloDataSource extends AbstractFormDataSource<DettaglioProfiloBean>{

	private static final Logger log = Logger.getLogger(AurigaDettaglioProfiloDataSource.class);

	@Override
	public DettaglioProfiloBean get(DettaglioProfiloBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityLoaddettprofiloBean input = new DmpkDefSecurityLoaddettprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprofiloio(new BigDecimal(bean.getIdProfilo()));
		
		DmpkDefSecurityLoaddettprofilo dmpkDefSecurityLoaddettprofilo = new DmpkDefSecurityLoaddettprofilo();
		StoreResultBean<DmpkDefSecurityLoaddettprofiloBean> output = dmpkDefSecurityLoaddettprofilo.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		bean.setNomeProfilo(output.getResultBean().getNomeprofiloio());
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmldatiprofiloout())) {
			XmlDatiProfiloOutBean scXmlDatiProfilo = new XmlDatiProfiloOutBean();
		    XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		    scXmlDatiProfilo = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmldatiprofiloout(), XmlDatiProfiloOutBean.class);      	       
		    if(scXmlDatiProfilo != null) {		    	   
		    	    bean.setLivMaxRiservatezza(StringUtils.isNotBlank(scXmlDatiProfilo.getLivMaxRiservatezza()) ? "1".equals(scXmlDatiProfilo.getLivMaxRiservatezza()) : null);
					bean.setFlgVisibTuttiRiservati(StringUtils.isNotBlank(scXmlDatiProfilo.getFlgVisibTuttiRiservati()) ? "1".equals(scXmlDatiProfilo.getFlgVisibTuttiRiservati()) : null);
					bean.setAccessoDocIndipACL(scXmlDatiProfilo.getAccessoDocIndipACL());
					bean.setAccessoFolderIndipACL(scXmlDatiProfilo.getAccessoFolderIndipACL());
					bean.setAccessoWorkspaceIndipACL(scXmlDatiProfilo.getAccessoWorkspaceIndipACL());
					bean.setAccessoDocIndipUserAbil(scXmlDatiProfilo.getAccessoDocIndipUserAbil());
					bean.setAccessoFolderIndipUserAbil(scXmlDatiProfilo.getAccessoFolderIndipUserAbil());
					bean.setPresentiPrivSuFunzioni(StringUtils.isNotBlank(scXmlDatiProfilo.getPresentiPrivSuFunzioni()) ? "1".equals(scXmlDatiProfilo.getPresentiPrivSuFunzioni()) : false);		    	
			}					
		}								
		
		return bean;
	}

	@Override
	public DettaglioProfiloBean add(DettaglioProfiloBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkDefSecurityIuprofiloBean input = new DmpkDefSecurityIuprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomeprofiloin(bean.getNomeProfilo());
		
		XmlDatiProfiloOutBean scXmlDatiProfilo = new XmlDatiProfiloOutBean();
		scXmlDatiProfilo.setLivMaxRiservatezza((bean.getLivMaxRiservatezza() != null && bean.getLivMaxRiservatezza()) ? "1" : "");
		scXmlDatiProfilo.setFlgVisibTuttiRiservati((bean.getFlgVisibTuttiRiservati() != null && bean.getFlgVisibTuttiRiservati()) ? "1" : "");
		scXmlDatiProfilo.setAccessoDocIndipACL(bean.getAccessoDocIndipACL() != null ? bean.getAccessoDocIndipACL() : "");
		scXmlDatiProfilo.setAccessoFolderIndipACL(bean.getAccessoFolderIndipACL() != null ? bean.getAccessoFolderIndipACL() : "");
		scXmlDatiProfilo.setAccessoWorkspaceIndipACL(bean.getAccessoWorkspaceIndipACL() != null ? bean.getAccessoWorkspaceIndipACL() : "");
		scXmlDatiProfilo.setAccessoDocIndipUserAbil(bean.getAccessoDocIndipUserAbil() != null ? bean.getAccessoDocIndipUserAbil() : "");
		scXmlDatiProfilo.setAccessoFolderIndipUserAbil(bean.getAccessoFolderIndipUserAbil() != null ? bean.getAccessoFolderIndipUserAbil() : "");
		scXmlDatiProfilo.setPresentiPrivSuFunzioni((bean.getPresentiPrivSuFunzioni() != null && bean.getPresentiPrivSuFunzioni()) ? "1" : "");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
	    input.setXmldatiprofiloin(lXmlUtilitySerializer.bindXml(scXmlDatiProfilo));
		
		DmpkDefSecurityIuprofilo dmpkDefSecurityIuprofilo = new DmpkDefSecurityIuprofilo();
		StoreResultBean<DmpkDefSecurityIuprofiloBean> output = dmpkDefSecurityIuprofilo.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdProfilo(output.getResultBean().getIdprofiloio().toString());

		return bean;
	}

	@Override
	public DettaglioProfiloBean update(DettaglioProfiloBean bean,
			DettaglioProfiloBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();		

		DmpkDefSecurityIuprofiloBean input = new DmpkDefSecurityIuprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprofiloio(new BigDecimal(bean.getIdProfilo()));
		input.setNomeprofiloin(bean.getNomeProfilo());

		XmlDatiProfiloOutBean scXmlDatiProfilo = new XmlDatiProfiloOutBean();
		scXmlDatiProfilo.setLivMaxRiservatezza((bean.getLivMaxRiservatezza() != null && bean.getLivMaxRiservatezza()) ? "1" : "");
		scXmlDatiProfilo.setFlgVisibTuttiRiservati((bean.getFlgVisibTuttiRiservati() != null && bean.getFlgVisibTuttiRiservati()) ? "1" : "");
		scXmlDatiProfilo.setAccessoDocIndipACL(bean.getAccessoDocIndipACL() != null ? bean.getAccessoDocIndipACL() : "");
		scXmlDatiProfilo.setAccessoFolderIndipACL(bean.getAccessoFolderIndipACL() != null ? bean.getAccessoFolderIndipACL() : "");
		scXmlDatiProfilo.setAccessoWorkspaceIndipACL(bean.getAccessoWorkspaceIndipACL() != null ? bean.getAccessoWorkspaceIndipACL() : "");
		scXmlDatiProfilo.setAccessoDocIndipUserAbil(bean.getAccessoDocIndipUserAbil() != null ? bean.getAccessoDocIndipUserAbil() : "");
		scXmlDatiProfilo.setAccessoFolderIndipUserAbil(bean.getAccessoFolderIndipUserAbil() != null ? bean.getAccessoFolderIndipUserAbil() : "");
		scXmlDatiProfilo.setPresentiPrivSuFunzioni((bean.getPresentiPrivSuFunzioni() != null && bean.getPresentiPrivSuFunzioni()) ? "1" : "");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
	    input.setXmldatiprofiloin(lXmlUtilitySerializer.bindXml(scXmlDatiProfilo));
		
		DmpkDefSecurityIuprofilo dmpkDefSecurityIuprofilo = new DmpkDefSecurityIuprofilo();
		StoreResultBean<DmpkDefSecurityIuprofiloBean> output = dmpkDefSecurityIuprofilo.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;

	}
	
	@Override
	public DettaglioProfiloBean remove(DettaglioProfiloBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();	
		
		DmpkDefSecurityDprofiloBean input = new DmpkDefSecurityDprofiloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprofiloin(new BigDecimal(bean.getIdProfilo()));
		input.setNomeprofiloin(bean.getNomeProfilo());
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
		
		if(StringUtils.isNotBlank(getExtraparams().get("flgIgnoreWarning"))) {
			input.setFlgignorewarningin(new Integer(getExtraparams().get("flgIgnoreWarning")));
		}

		DmpkDefSecurityDprofilo dmpkDefSecurityDprofilo = new DmpkDefSecurityDprofilo();
		StoreResultBean<DmpkDefSecurityDprofiloBean> output = dmpkDefSecurityDprofilo.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setWarning(output.getResultBean().getWarningmsgout());
		
		return bean;
	}
	
	@Override
	public Map<String, ErrorBean> validate(DettaglioProfiloBean bean)
			throws Exception {
		
		return null;
	}
}
