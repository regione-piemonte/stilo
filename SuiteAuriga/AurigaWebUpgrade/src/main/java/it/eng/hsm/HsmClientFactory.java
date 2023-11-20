/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.config.ArubaConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.MedasConfig;
import it.eng.hsm.client.config.PkBoxConfig;
import it.eng.hsm.client.config.TypeOtp;
import it.eng.hsm.client.impl.HsmImpl;
import it.eng.hsm.client.option.MedasOption;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

public class HsmClientFactory {

	private static Logger mLogger = Logger.getLogger(HsmClientFactory.class);
	
	public static Hsm getHsmClient(HttpSession session, FirmaHsmBean firmaHsmBean)
			throws JAXBException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		HsmConfig hsmConfig = new HsmConfig();
		HsmType hsmType = HsmType.valueOf(firmaHsmBean.getHsmType());
		if (hsmType.equals(HsmType.ARUBA)) {
			hsmConfig = getArubaHsmConfig(session, firmaHsmBean);
		} else if (hsmType.equals(HsmType.MEDAS)) {
			hsmConfig = getMedasHsmConfig(session, firmaHsmBean);
		} else if (hsmType.equals(HsmType.INFOCERT)) {
			hsmConfig = getInfoCertHsmConfig(session, firmaHsmBean);
		} else if (hsmType.equals(HsmType.PKBOX)) {
			hsmConfig = getPkBoxHsmConfig(session, firmaHsmBean);
		}
		Hsm hsm = HsmImpl.getNewInstance(hsmConfig);
		return hsm;
	}

	protected static HsmConfig getArubaHsmConfig(HttpSession session, FirmaHsmBean firmaHsmBean)
			throws JAXBException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		
		// Flag che indica se sto firmando in delega
		boolean firmaInDelega = false;

		String username = firmaHsmBean.getUsername();
		String usernameDelegante = firmaHsmBean.getUsernameDelegante();
		String password = firmaHsmBean.getPassword();
		String otp = firmaHsmBean.getCodiceOtp();
		boolean parametriHSMFromGui = firmaHsmBean.getParametriHSMFromGui() != null ? firmaHsmBean.getParametriHSMFromGui() : false; 
		
		String userFromDB = ParametriDBUtil.getParametroDB(session, "HSM_USERID");
		String delegatedUserFromDB = ParametriDBUtil.getParametroDB(session, "HSM_DELEGATE_USERID");
		String delegatedPasswordFromDB = ParametriDBUtil.getParametroDB(session, "HSM_DELEGATE_PWD");
//		String delegatedDomain = "engineering";

		HsmType hsmType = HsmType.ARUBA;
		
		String xmlParametriHsm;
		if (StringUtils.isNotBlank(firmaHsmBean.getProviderHsmFromPreference())) {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS_" + firmaHsmBean.getProviderHsmFromPreference());
		} else {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS");
		}
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		
		ArubaConfig arubaConfig = lXmlUtility.unbindXml(xmlParametriHsm, ArubaConfig.class);

		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);
		
		// Verifico se le preferenze arrivano da GUI o da firma automatica
		if (parametriHSMFromGui) {
			// Le preferenze di firma arrivano da GUI
			
			// Setto username, delegante e delgato
			if (usernameDelegante != null && !"".equalsIgnoreCase(usernameDelegante)) {
				arubaConfig.setUser(usernameDelegante);
				arubaConfig.setDelegatedUser(username);
				arubaConfig.setUseDelegate(true);
				firmaInDelega = true;
			} else {
				arubaConfig.setUser(username);
				arubaConfig.setDelegatedUser(null);
				arubaConfig.setDelegatedDomain(null);
				arubaConfig.setUseDelegate(false);
			}
			
			// Setto le password di firma
			if (!firmaInDelega) {
				// Firma non in delega con parametri ricevuti  da GUI. Setto la password del firmatario solo se non sono in delega
				arubaConfig.setPassword(password);
				arubaConfig.setDelegatedPassword(null);
			} else {
				// Firma in delega con parametri ricevuti  da GUI. Setto la password per la firma in delega
				arubaConfig.setPassword(null);
				arubaConfig.setDelegatedPassword(password);
			} 
			
			// Setto l'otp
			if (otp != null && !"".equalsIgnoreCase(otp)){
				arubaConfig.setOtpPwd(otp);
			}
		} else {
			// La firma è automatica, devo solo controllare che a DB non ci siano impostazioni che sovrascrivono le configurazioni del bind
			if( userFromDB != null && !"".equalsIgnoreCase(userFromDB)) {
				arubaConfig.setUser(userFromDB);
			} 
			
			// Controllo l'eventuale utente delegato
			if( delegatedUserFromDB != null && !"".equalsIgnoreCase(delegatedUserFromDB)) {
				arubaConfig.setDelegatedUser(delegatedUserFromDB);
//				arubaConfig.setDelegatedDomain(delegatedDomain);
			}
			
			// Controllo la password del delegato
			if( delegatedPasswordFromDB != null && !"".equalsIgnoreCase(delegatedPasswordFromDB)) {
				arubaConfig.setDelegatedPassword(delegatedPasswordFromDB);
			}
			
			// controllo se ho parametri nelle preferenze che sovrascrivono quelli in DB
			if (username != null && !"".equalsIgnoreCase(username) && usernameDelegante != null && !"".equalsIgnoreCase(usernameDelegante)) {
				arubaConfig.setUseDelegate(true);
				arubaConfig.setUser(usernameDelegante);
				arubaConfig.setDelegatedUser(username);
				arubaConfig.setPassword(null);
				arubaConfig.setDelegatedPassword(password);
			} else if (username != null && !"".equalsIgnoreCase(username)) {
				arubaConfig.setUseDelegate(false);
				arubaConfig.setUser(username);
				arubaConfig.setPassword(password);
				arubaConfig.setDelegatedDomain(null);
				arubaConfig.setDelegatedPassword(null);
			} else if( usernameDelegante != null && !"".equalsIgnoreCase(usernameDelegante)) {
				arubaConfig.setUseDelegate(true);
				arubaConfig.setDelegatedUser(usernameDelegante);
//				arubaConfig.setDelegatedDomain(delegatedDomain);
				arubaConfig.setPassword(null);
				arubaConfig.setDelegatedPassword(password);
			}
			
		}

		hsmConfig.setClientConfig(arubaConfig);

		return hsmConfig;
	}
	
	protected static HsmConfig getPkBoxHsmConfig(HttpSession session, FirmaHsmBean firmaHsmBean)
			throws JAXBException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		
		// Flag che indica se sto firmando in delega
		boolean firmaInDelega = false;

		String username = firmaHsmBean.getUsername();
		String password = firmaHsmBean.getPassword();
		String otp = firmaHsmBean.getCodiceOtp();
		boolean parametriHSMFromGui = firmaHsmBean.getParametriHSMFromGui() != null ? firmaHsmBean.getParametriHSMFromGui() : false; 
		
		String userFromDB = ParametriDBUtil.getParametroDB(session, "HSM_USERID");
		
		HsmType hsmType = HsmType.PKBOX;
		
		String xmlParametriHsm;
		if (StringUtils.isNotBlank(firmaHsmBean.getProviderHsmFromPreference())) {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS_" + firmaHsmBean.getProviderHsmFromPreference());
		} else {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS");
		}
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		
		PkBoxConfig pkBoxConfig = lXmlUtility.unbindXml(xmlParametriHsm, PkBoxConfig.class);

		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);
		
		// Verifico se le preferenze arrivano da GUI o da firma automatica
		if (parametriHSMFromGui) {
			// Le preferenze di firma arrivano da GUI
			
			// Setto lo username
			pkBoxConfig.setAlias(username);
			
	
			// Setto le password di firma
			if (!firmaInDelega) {
				// Firma non in delega con parametri ricevuti  da GUI. Setto la password del firmatario solo se non sono in delega
				pkBoxConfig.setPin(password);
			} 
			
			// Setto le credenziali per l'otp
			pkBoxConfig.setUser(username);
			pkBoxConfig.setPassword(password);
			
			// Setto l'otp
			if (otp != null && !"".equalsIgnoreCase(otp)){
				pkBoxConfig.setOtpPwd(otp);
			}
		} else {
			// La firma è automatica, devo solo controllare che a DB non ci siano impoostazioni che sovrascrivono le configurazioni del bind
			if( userFromDB != null && !"".equalsIgnoreCase(userFromDB)) {
				pkBoxConfig.setUser(userFromDB);
				pkBoxConfig.setAlias(userFromDB);
			} 
			
			// controllo se ho parametri nelle preferenze che sovrascrivono quelli in DB
			if( username != null && !"".equalsIgnoreCase(username)) {
				pkBoxConfig.setUser(username);
				pkBoxConfig.setAlias(username);
				pkBoxConfig.setPassword(password);
				pkBoxConfig.setPin(password);
			}
			
		}

		hsmConfig.setClientConfig(pkBoxConfig);

		return hsmConfig;
	}

	protected static HsmConfig getInfoCertHsmConfig(HttpSession session, FirmaHsmBean firmaHsmBean) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException, ParseException {
		String username = firmaHsmBean.getUsername();
		String usernameDelegante = firmaHsmBean.getUsernameDelegante();
		String password = firmaHsmBean.getPassword();
		String otp = firmaHsmBean.getCodiceOtp();
		boolean parametriHSMFromGui = firmaHsmBean.getParametriHSMFromGui() != null ? firmaHsmBean.getParametriHSMFromGui() : false; 
		
		String userFromDB = ParametriDBUtil.getParametroDB(session, "HSM_USERID");
		String delegatedUserFromDB = ParametriDBUtil.getParametroDB(session, "HSM_DELEGATE_USERID");
		String delegatedPasswordFromDB = ParametriDBUtil.getParametroDB(session, "HSM_DELEGATE_PWD");
//				String delegatedDomain = "engineering";

		HsmType hsmType = HsmType.INFOCERT;
		
		String xmlParametriHsm;
		if (StringUtils.isNotBlank(firmaHsmBean.getProviderHsmFromPreference())) {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS_" + firmaHsmBean.getProviderHsmFromPreference());
		} else {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS");
		}
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		
		InfoCertConfig infoCertConfig = lXmlUtility.unbindXml(xmlParametriHsm, InfoCertConfig.class);

		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);
		
		// Verifico se le preferenze arrivano da GUI o da firma automatica
		if (parametriHSMFromGui) {
			// Le preferenze di firma arrivano da GUI
			
			// Setto username
			infoCertConfig.setAlias(username);
			infoCertConfig.setPin(password);
			infoCertConfig.setOtp(otp);
			
		}
		hsmConfig.setClientConfig(infoCertConfig);

		return hsmConfig;
	}

	protected static HsmConfig getMedasHsmConfig(HttpSession session, FirmaHsmBean firmaHsmBean)
			throws JAXBException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		
		String username = firmaHsmBean.getUsername();
		String password = firmaHsmBean.getPassword();
		String certId = firmaHsmBean.getCertId();
		String certificateSerialNumber = firmaHsmBean.getCertSerialNumber();
		String potereDiFirma = firmaHsmBean.getPotereDiFirma();
		String strTypeOtp = firmaHsmBean.getTypeOtp();
		String otp = firmaHsmBean.getCodiceOtp();
		
		HsmType hsmType = HsmType.MEDAS;
		
		MedasOption medasOption = (MedasOption) HsmClientOptionFactory.getMedasClientOption();
		
		String xmlParametriHsm;
		if (StringUtils.isNotBlank(firmaHsmBean.getProviderHsmFromPreference())) {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS_" + firmaHsmBean.getProviderHsmFromPreference());
		} else {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(session, "HSM_PARAMETERS");
		}
		
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();

		MedasConfig medasConfig = lXmlUtility.unbindXml(xmlParametriHsm, MedasConfig.class);

		medasConfig.setCodiceFiscale(username);
		medasConfig.setPassword(password);

		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);

		if (otp != null){
			medasConfig.setOtp(otp);
		}

		if (certificateSerialNumber != null){
			medasConfig.setCertificateSerialNumber(certificateSerialNumber);
		}
		
		if (certId != null){
			medasConfig.setCertificateId(certId);
		}
		
		if (potereDiFirma != null){
			medasConfig.setSignaturePower(potereDiFirma);
		}
		
		if (strTypeOtp != null){
			TypeOtp typeOtp = TypeOtp.valueOf(strTypeOtp);
			medasConfig.setTypeOtp(typeOtp);
		}
		
		if (medasOption != null && medasOption.getDocType() != null){
			medasConfig.setCertificateDocType( medasOption.getDocType());
		}

		hsmConfig.setClientConfig(medasConfig);

		return hsmConfig;
	}

}
