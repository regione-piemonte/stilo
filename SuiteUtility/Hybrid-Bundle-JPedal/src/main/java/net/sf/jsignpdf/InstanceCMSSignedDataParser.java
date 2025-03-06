package net.sf.jsignpdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class InstanceCMSSignedDataParser 
{
	
	
	
	public static CMSSignedDataParser getCMSSignedDataParser(File file,boolean isBase64) throws IOException, OperatorCreationException, CMSException
	{
		
		FileInputStream fis =  FileUtils.openInputStream(file);
		
		return getCMSSignedDataParser(fis, isBase64);
	}
	
	public static CMSSignedDataParser getCMSSignedDataParser(InputStream is,boolean isBase64) throws OperatorCreationException, CMSException
	{
		
		CMSSignedDataParser cmsSignedData = null;
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null){
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		
		if(isBase64)
		{
			Base64InputStream streambase64 = new Base64InputStream(is);
			
			
			 cmsSignedData= new CMSSignedDataParser(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(),streambase64);	
			
		}
		else
		{
			cmsSignedData= new CMSSignedDataParser(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build(),is);	
			
		}
		
		return cmsSignedData;
	}
	
	
	/*
	public static CMSSignedDataParser getCMSSignedDataParser(File file)
	{
		Base64InputStream streambase64 = new Base64InputStream(FileUtils.openInputStream(file));
		
		
		cmsSignedData= new CMSSignedDataParser(streambase64);	
		
		CMSSignedDataParser cmsSignedData= new CMSSignedDataParser(streambase64);	
		
		return cmsSignedData
	}
		*/

}
