package it.eng.utility.cryptosigner.utils;

import it.eng.utility.cryptosigner.manager.factory.SignatureManagerFactory;
import it.eng.utility.cryptosigner.storage.ICAStorage;

import java.io.File;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import be.fedict.eid.tsl.TrustService;
import be.fedict.eid.tsl.TrustServiceList;
import be.fedict.eid.tsl.TrustServiceListFactory;
import be.fedict.eid.tsl.TrustServiceProvider;

/**
 * Utilità di confgiurazione della CA
 * @author michele
 *
 */
public class CAUtility {
	
	
	public static void updateCAFromXml(File xml,ICAStorage storage) throws Exception{
		
		InputStream stream = FileUtils.openInputStream(xml);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		
		Document doc =docBuilder.parse(stream);
		
		TrustServiceList trustServiceList = TrustServiceListFactory.newInstance(doc);
		List<TrustServiceProvider> trustServiceProviders = trustServiceList.getTrustServiceProviders();
		
		for (TrustServiceProvider trustServiceProvider: trustServiceProviders){
			List<TrustService> trustServices = trustServiceProvider.getTrustServices();
			for (TrustService trustService: trustServices){
				X509Certificate certificate = trustService.getServiceDigitalIdentity();
				//storage.insertCA(certificate);
				
				
			}
		}
		stream.close();
	}

	
	public static void main(String[] args) throws Exception {
		
		SignatureManagerFactory factory =  SignatureManagerFactory.newInstance("/home/michele/progetti/CA", "/home/michele/progetti/CRL", "/home/michele/progetti/CONF");
		
		CAUtility.updateCAFromXml(new File("/home/michele/Progetti/PEC/IT_TSL_signed.xml"),factory.getCaStorage());

		
		System.out.println("CLOSE");
	}
	
}
