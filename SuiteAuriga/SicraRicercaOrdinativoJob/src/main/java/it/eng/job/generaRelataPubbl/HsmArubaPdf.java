/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ArubaConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.impl.HsmImpl;
import it.eng.hsm.client.option.SignOption;
import it.eng.job.SpringHelper;

public class HsmArubaPdf {
	
	private Logger logger = Logger.getLogger(HsmArubaPdf.class);

	public HsmArubaPdf() {
	}
	public static void main(String[] args) {
       
		byte[] fileFirmato = null;
		File f = new File("");
		HsmArubaPdf pades = new HsmArubaPdf();
		fileFirmato = pades.firmaPades(f);
		
		try {
			FileUtils.writeByteArrayToFile(
					new File("C:/Users/Bcsoft/Downloads/firmatoArubaPades.pdf"),
					fileFirmato);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public byte[] firmaPades(File input)
	{
		byte[] fileFirmato = null;
		
		HsmType hsmType = HsmType.ARUBA;

		SignOption signOption = new SignOption();
		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);

		if (hsmType.equals(HsmType.ARUBA)) {
			
			ArubaHsmBean arubaHsmBean = (ArubaHsmBean)  SpringHelper.getMainApplicationContext().getBean("ArubaHSM");
			
			logger.info("arubaHsmBean: "+arubaHsmBean.getServiceName());
			
			WSConfig wsSignConfig = new WSConfig();
			wsSignConfig.setWsdlEndpoint(arubaHsmBean.getWsdlArubaHsm());
			wsSignConfig.setServiceNS(arubaHsmBean.getServiceNS());
			wsSignConfig.setServiceName(arubaHsmBean.getServiceName());

			ArubaConfig arubaConfig = new ArubaConfig();
			arubaConfig.setWsSignConfig(wsSignConfig);
			arubaConfig.setWsCertConfig(wsSignConfig);
			arubaConfig.setUseDelegate(true);
			arubaConfig.setUser(arubaHsmBean.getUser());
			arubaConfig.setDelegatedUser(arubaHsmBean.getDelegatedUser());
			arubaConfig.setDelegatedPassword(arubaHsmBean.getDelegatedPassword());
			arubaConfig.setDelegatedDomain(arubaHsmBean.getDelegatedDomain());
			arubaConfig.setOtpPwd(arubaHsmBean.getOtpPwd());
			arubaConfig.setTypeOtpAuth(arubaHsmBean.getTypeOtpAuth());
			ProxyConfig proxyConfig = (ProxyConfig)  SpringHelper.getMainApplicationContext().getBean("ProxyConfig");
			if (proxyConfig.getProxyHost()!=null &&
				!"".equals(proxyConfig.getProxyHost()))
			{
			 logger.info("proxyConfig.getProxyHost(): "+proxyConfig.getProxyHost());	
			 arubaConfig.setProxyConfig(proxyConfig);
			}
			logger.info("arubaConfig: "+arubaConfig.getUser());
			
			/*PadesConfig padesConfig = new PadesConfig();
			padesConfig.setLeftX("10");
			padesConfig.setLeftY("10");
			padesConfig.setRightX("60");
			padesConfig.setRightY("60");
			padesConfig.setTesto("Firmato da "+arubaConfig.getUser() +" in data " + new Date());
			padesConfig.setNumPagina("1");
			arubaConfig.setPadesConfig(padesConfig);*/
			PadesConfig padesConfig = new PadesConfig();
			arubaConfig.setPadesConfig(padesConfig);
			hsmConfig.setClientConfig(arubaConfig);

		}

		Hsm hsm = HsmImpl.getNewInstance(hsmConfig);
		InputStream fileDaFirmare= null;
		logger.info("hsm: "+hsm.getHsmConfig().getClientConfig().getHsmType());
		try {
			//fileDaFirmare = new FileInputStream("C:/Users/Bcsoft/eclipse-workspace/HSMClient/src/test/java/test/pades/Chiusure collettive 2020[2226].pdf");
			fileDaFirmare = new FileInputStream(input);
			logger.info("fileDaFirmare: "+fileDaFirmare.toString());
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] bytesFileDaFirmare = null;
		try {
			bytesFileDaFirmare = IOUtils.toByteArray(fileDaFirmare);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("bytesFileDaFirmare: "+bytesFileDaFirmare.length);
		if (bytesFileDaFirmare != null) {
			try {
				SignResponseBean response = hsm.firmaPades(bytesFileDaFirmare, signOption);
				logger.info("response: "+response.getFileResponseBean());
				MessageBean message = response.getMessage();
				if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
					System.out.println("Errore: - " + message.getCode() + " " + message.getDescription());
				}
				FileResponseBean fileResponseBean = response.getFileResponseBean();
				if (fileResponseBean != null) {
					fileFirmato = fileResponseBean.getFileFirmato();
					if (fileFirmato != null)
						
						return fileFirmato;
					
				}

			} catch (HsmClientConfigException e) {
				logger.error("HsmClientConfigException: "+e.getMessage());
				e.printStackTrace();
			} catch (HsmClientSignatureException e) {
				logger.error("HsmClientSignatureException: "+e.getMessage());
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				logger.error("UnsupportedOperationException: "+e.getMessage());
				e.printStackTrace();
			}catch (Exception e) {
				logger.error("Exception: "+e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("File non specificato");
		}
		
		return null;
	}
	
}
