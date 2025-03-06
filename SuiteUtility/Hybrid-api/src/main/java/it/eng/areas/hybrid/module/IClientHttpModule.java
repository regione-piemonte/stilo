package it.eng.areas.hybrid.module;

import java.util.Map;

public interface IClientHttpModule extends IClientModule {
	public static final String REQUEST_URI = "uri";
	public static final String REQUEST_QUERYSTRING = "querystring";
	public static final String REQUEST_METHOD = "method";
	public static final String REQUEST_PARAMETERS = "parameters";
	public static final String REQUEST_DATA = "data";
	public static final String REQUEST_COOKIES = "cookies";
	public static final String REQUEST_HEADERS = "headers";

	
	public static final String RESPONSE_MIMETYPE = "mimetype";
	public static final String RESPONSE_CONTENT = "content";

	Map<String,Object> processHttpRequest(Map<String,Object> request) throws Exception;
	
}
