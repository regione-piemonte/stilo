package it.eng.hybrid.module.wordOpener.config;



import it.eng.hybrid.module.wordOpener.exception.FileNotSupportedException;
import it.eng.hybrid.module.wordOpener.exception.InitException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class WordOpenerConfigManager {
	
	private static String FILE_URL = "fileUrl";
	private static String FILE_NAME = "fileName";
	private static String OUTPUT_URL = "outputUrl";
	public static String CALLBACK = "callBack";
	private static String TIPO_FILE_PARAM = "tipoFile";
	private static String IMPRONTA = "impronta";
	private static String TIPO_IMPRONTA_PARAM = "tipoImpronta";
	public static String CALLBACK_CANCEL = "callBackCancel";
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
	
	
	
	
	public static void init(Map<String, String> props) throws FileNotSupportedException{
		fileName = props.get(FILE_NAME);
		fileUrl = props.get(FILE_URL);
		outputUrl = props.get(OUTPUT_URL);
		callBack = props.get(CALLBACK);
		tipoFile = TIPO_FILE.getTipoFile(props.get(TIPO_FILE_PARAM));
		if (tipoFile == null) throw new FileNotSupportedException(props.get(TIPO_FILE_PARAM));
		tipoImpronta = TIPO_IMPRONTA.getTipoImpronta(props.get(TIPO_IMPRONTA_PARAM));
		impronta = props.get(IMPRONTA);
		callBackCancel = props.get(CALLBACK_CANCEL);
		tipoEncoding = TIPO_ENCODING.getTipoEncoding(props.get(TIPO_ENCODING_PARAM));
		testUrl = props.get(TEST_URL);
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
