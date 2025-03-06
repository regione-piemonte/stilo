package it.eng.wordOpener.configuration;

import it.eng.wordOpener.exception.FileNotSupportedException;
import it.eng.wordOpener.exception.InitException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JApplet;

import org.apache.commons.lang.StringUtils;

public class WordOpenerConfigManager {
	
	public enum TIPO_IMPRONTA{
		
		SHA1("sha1"), SHA256("sha-256");
		
		String value;
		
		private TIPO_IMPRONTA(String tipo){
			value = tipo;
		}
		
		public String getRealValue(){
			return value;
		}
		
		public static TIPO_IMPRONTA getTipoImpronta(String tipo){
			for (TIPO_IMPRONTA lImpronta : values()){
				if (lImpronta.getRealValue().equals(tipo)){
					return lImpronta;
				}
			}
			return null;
		}
	}
	
	public enum TIPO_ENCODING{
		
		HEX("hex"), BASE64("base64");
		
		String value;
		
		private TIPO_ENCODING(String tipo){
			value = tipo;
		}
		
		public String getRealValue(){
			return value;
		}
		
		public static TIPO_ENCODING getTipoEncoding(String tipo){
			for (TIPO_ENCODING lTipoEncoding : values()){
				if (lTipoEncoding.getRealValue().equals(tipo)){
					return lTipoEncoding;
				}
			}
			return null;
		}
		
	}
	
	public enum TIPO_FILE {
		XLS("xls"), XLSX("xlsx"), DOC("doc"), DOCX("docx"), ODT("odt"),
		TXT("txt");
		
		String estensione;
		
		private TIPO_FILE(String estensione){
			this.estensione = estensione;
		}
		
		public String getEstensione(){
			return estensione;
		}
		
		public static TIPO_FILE getTipoFile(String estensione){
			for (TIPO_FILE pTipoFile : values()){
				if (pTipoFile.getEstensione().equals(estensione)){
					return pTipoFile;
				}
			}
			return null;
		}
	}
	
	private static String FILE_URL = "fileUrl";
	private static String FILE_NAME = "fileName";
	private static String OUTPUT_URL = "outputUrl";
	private static String CALLBACK = "callBack";
	private static String TIPO_FILE_PARAM = "tipoFile";
	private static String IMPRONTA = "impronta";
	private static String TIPO_IMPRONTA_PARAM = "tipoImpronta";
	private static String CALLBACK_CANCEL = "callBackCancel";
	private static String TIPO_ENCODING_PARAM = "tipoEncoding";
	private static String TEST_URL = "testUrl";
	
	private static String fileName;
	private static String fileUrl;
	private static String outputUrl;
	private static String callBack;
	private static TIPO_FILE tipoFile;
	private static String impronta;
	private static TIPO_IMPRONTA tipoImpronta;
	private static String callBackCancel;
	private static TIPO_ENCODING tipoEncoding;
	private static String testUrl;
	
	
	public static void init(JApplet pJApplet) throws FileNotSupportedException{
		fileName = pJApplet.getParameter(FILE_NAME);
		fileUrl = pJApplet.getParameter(FILE_URL);
		outputUrl = pJApplet.getParameter(OUTPUT_URL);
		callBack = pJApplet.getParameter(CALLBACK);
		tipoFile = TIPO_FILE.getTipoFile(pJApplet.getParameter(TIPO_FILE_PARAM));
		if (tipoFile == null) throw new FileNotSupportedException(pJApplet.getParameter(TIPO_FILE_PARAM));
		tipoImpronta = TIPO_IMPRONTA.getTipoImpronta(pJApplet.getParameter(TIPO_IMPRONTA_PARAM));
		impronta = pJApplet.getParameter(IMPRONTA);
		callBackCancel = pJApplet.getParameter(CALLBACK_CANCEL);
		tipoEncoding = TIPO_ENCODING.getTipoEncoding(pJApplet.getParameter(TIPO_ENCODING_PARAM));
		testUrl = pJApplet.getParameter(TEST_URL);
	}

	public static String getFileName() throws InitException {
		if (StringUtils.isEmpty(fileName)) { throw new InitException(FILE_NAME); }; 
		return fileName;//"Open.doc";
	}
	
	public static String getFileUrl() throws InitException {
		if (StringUtils.isEmpty(fileUrl)) { throw new InitException(FILE_URL); };
		return fileUrl;//"http://localhost/ciao.doc";
	}

	public static URI getOutputURL() throws MalformedURLException, URISyntaxException, InitException {
		if (StringUtils.isEmpty(outputUrl)) { throw new InitException(OUTPUT_URL); };
		return new URL(outputUrl).toURI();//new URL("http://localhost:8080/AurigaUI/springdispatcher/UploadSignerApplet/").toURI();
	}

	public static String getCallback() throws InitException {
		if (StringUtils.isEmpty(callBack)) { throw new InitException(CALLBACK); };
		return callBack;//"alert";
	}
	
	public static TIPO_FILE getTipoFile() throws InitException {
		if (tipoFile == null) { throw new InitException(TIPO_FILE_PARAM); };
		return tipoFile;
	}
	
	public static TIPO_IMPRONTA getTipoImpronta() throws InitException {
		if (tipoImpronta == null) { throw new InitException(TIPO_IMPRONTA_PARAM); };
		return tipoImpronta;
	}
	
	public static String getImpronta() throws InitException {
		if (StringUtils.isEmpty(impronta)) { throw new InitException(IMPRONTA); };
		return impronta;//"alert";
	}

	public static String getCallBackCancel() throws InitException {
		if (StringUtils.isEmpty(callBackCancel)) { throw new InitException(CALLBACK_CANCEL); };
		return callBackCancel;//"alert";
	} 
	
	public static TIPO_ENCODING getTipoEncoding() throws InitException {
		if (tipoEncoding == null) { throw new InitException(TIPO_ENCODING_PARAM); };
		return tipoEncoding;
	}
	
	public static String getTestUrl() throws InitException{
		if (testUrl == null) { throw new InitException(TEST_URL); };
		return testUrl;
	}
	
}
