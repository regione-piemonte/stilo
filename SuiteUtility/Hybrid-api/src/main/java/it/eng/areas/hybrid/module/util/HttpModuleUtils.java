package it.eng.areas.hybrid.module.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import it.eng.areas.hybrid.module.IClientHttpModule;

public final class HttpModuleUtils {
	
	public static final String MIMETYPE_HTML = "text/html";
	public static final String MIMETYPE_TXT = "text/txt";
	public static final String MIMETYPE_JS = "application/javascript";
	public static final String MIMETYPE_JSON = "application/json";
	public static final String MIMETYPE_CSS = "text/css";
	public static final String MIMETYPE_JPG = "image/jpeg";
	public static final String MIMETYPE_PNG = "image/png";
	public static final String MIMETYPE_GIF = "image/gif";
	
	public static final  Map<String,String> MIMETYPE_MAPPING = new HashMap<>();
	
	static {
		MIMETYPE_MAPPING.put("html", MIMETYPE_HTML);
		MIMETYPE_MAPPING.put("htm", MIMETYPE_HTML);
		MIMETYPE_MAPPING.put("txt", MIMETYPE_TXT);
		MIMETYPE_MAPPING.put("js", MIMETYPE_JS);
		MIMETYPE_MAPPING.put("jpg", MIMETYPE_JPG);
		MIMETYPE_MAPPING.put("jpeg", MIMETYPE_JPG);
		MIMETYPE_MAPPING.put("png", MIMETYPE_PNG);
		MIMETYPE_MAPPING.put("gif", MIMETYPE_GIF);
		MIMETYPE_MAPPING.put("css", MIMETYPE_CSS);
	}
	
	private HttpModuleUtils() {
		//NOP
	}
	
	public static String mimeTypeByName(String filename) {
		int dotIdx = filename.lastIndexOf('.');
		if (dotIdx > -1) {
			String ext = filename.substring(dotIdx);
			return MIMETYPE_MAPPING.get(ext);
		}
		return null;
	}
	
	public static Map<String,Object> returnHtml(String html) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put(IClientHttpModule.RESPONSE_MIMETYPE, MIMETYPE_HTML);
		result.put(IClientHttpModule.RESPONSE_CONTENT, html);
		
		return result;
	}
	
	public static Map<String,Object> returnResource(InputStream inputStream, String mimetype) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put(IClientHttpModule.RESPONSE_MIMETYPE, mimetype);
		result.put(IClientHttpModule.RESPONSE_CONTENT, inputStream);
		
		return result;
	}
	
	public static Map<String,Object> returnResource(String resource, String mimetype) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put(IClientHttpModule.RESPONSE_MIMETYPE, mimetype);
		result.put(IClientHttpModule.RESPONSE_CONTENT, new ByteArrayInputStream(resource.getBytes()));
		
		return result;
	}
	
	public static void copyStream(InputStream in, OutputStream out, boolean close) throws IOException {

		byte[] buff = new byte[32768];

		while (true) {
			int lenght = in.read(buff);
			if (lenght == -1)
				break;
			out.write(buff, 0, lenght);
		}
		
		if (close) {
			in.close();
			out.close();
		}
	}
	
	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		copyStream(in, out, true);
	}
	
	public static String inputStreamToString(InputStream in) throws IOException {
		if (in == null) {
			return null;
		}
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		copyStream(in, buffer);
		return new String(buffer.toByteArray());
	}
	
	public static InputStream stringToInputStream(String s) {
		return new ByteArrayInputStream(s.getBytes());
	}
	
	public static String replaceValues(String source, Map<String, String> values) {
		for (Map.Entry<String, String> entry : values.entrySet()) {
			source = source.replaceAll("\\$\\{"+entry.getKey()+"\\}", entry.getValue());
		}
		
		return source;
	}
	
	

}
