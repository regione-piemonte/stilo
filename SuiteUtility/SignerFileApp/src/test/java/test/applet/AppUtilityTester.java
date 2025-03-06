package test.applet;

import java.io.File;
import java.io.InputStream;

import it.eng.common.bean.HashFileBean;
import it.eng.common.type.SignerType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

public class AppUtilityTester {
	
	@Test
	public void generateData() {
		String result="";
		try{
			File file=new File("c:/test.txt");
			HashFileBean bean = new HashFileBean();
			bean.setFileName(file.getName());
			bean.setId(file.getName());
			bean.setHash(getHash(file));
			bean.setFileType(SignerType.P7M);
			result=Base64.encodeBase64String(SerializationUtils.serialize(bean));
			System.out.println("result:"+result);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public byte[] getHash(File file) throws Exception{
		InputStream stream = FileUtils.openInputStream(file);
		byte[] hash = DigestUtils.sha256(stream);
		stream.close();	
		return hash;
	}
}
