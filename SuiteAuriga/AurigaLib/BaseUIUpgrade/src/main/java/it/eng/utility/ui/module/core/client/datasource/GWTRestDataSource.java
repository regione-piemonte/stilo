/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Cookies;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.EscapeHtmlClient;
import it.eng.utility.ui.module.core.client.RequestValidatorClient;
import it.eng.utility.ui.module.core.client.SJCLClient;
import it.eng.utility.ui.module.core.client.ScriptCleanerClient;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.message.MessageBox;

public class GWTRestDataSource extends RestDataSource {
	
	public static final String NAME_COOKIE_CSRF_TOKEN = "Csrf-Token";
	public static final String NAME_HEADER_CSRF_TOKEN = "X-"+NAME_COOKIE_CSRF_TOKEN;
	
	private static MessageBox messagebox = null;
	private static CustomLayout customLayout = null;
	private static String datasourceId = null;
	private static Boolean flagAttivaRequestValidator = false;
	private static SJCLClient sjclClient;
	private static ScriptCleanerClient scriptCleanerClient;
	private static EscapeHtmlClient escapeHtmlClient;
	
	private static HashSet<GWTRestDataSource> allDataSourceInstances = new HashSet<GWTRestDataSource>();
	private static HashMap<Canvas, HashSet<GWTRestDataSource>> canvasDataSourceInstancesMap = new HashMap<Canvas, HashSet<GWTRestDataSource>>();
	
	protected GWTRestDataSource instance; 
	
	private String serverid = null;
	private Canvas parentCanvas = null;
	private Boolean isSelectFilter = false;
	private Boolean disableCursor = false;
	private Boolean forceToShowPrompt = null;
	private Boolean isOneCallDataSource = false;
	private Integer timeout = null;
		
	public Map<String, String> extraparam = new HashMap<String, String>();
	public Map<String, String> onlyfirstcallparam = new HashMap<String, String>();
	private LinkedHashMap<String, String> extratypeparam = new LinkedHashMap<String, String>();	
	
	public GWTRestDataSource(String serverid) {
		init(serverid, false, null, FieldType.TEXT);	
	}
	
	public GWTRestDataSource(String serverid, boolean isSelectFilter) {
		init(serverid, false, null, FieldType.TEXT);	
		this.isSelectFilter = isSelectFilter;
	}
	
	public GWTRestDataSource(String serverid, String keyproperty, FieldType keytype) {
		init(serverid, false, keyproperty, keytype);
	}
	
	public GWTRestDataSource(String serverid, String keyproperty, FieldType keytype, boolean isSelectFilter) {
		init(serverid, false, keyproperty, keytype);
		this.isSelectFilter = isSelectFilter;
	}
	
	public GWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields) {
		init(serverid, false, keyproperty, keytype);
		setFields(fields.toArray(new DataSourceField[0]));
	}
	
	public GWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, boolean isSelectFilter) {
		init(serverid, false, keyproperty, keytype);
		setFields(fields.toArray(new DataSourceField[0]));
		this.isSelectFilter = isSelectFilter;
	}
	
	public GWTRestDataSource(String serverid, boolean hierarchical, String keyproperty, FieldType keytype) {
		init(serverid, hierarchical, keyproperty, keytype);
	}
	
	public GWTRestDataSource(String serverid, boolean hierarchical, String keyproperty, FieldType keytype, List<DataSourceField> fields) {
		init(serverid, hierarchical, keyproperty, keytype);
		setFields(fields.toArray(new DataSourceField[0]));
	}

	private void init(String serverid, boolean hierarchical, String keyproperty, FieldType keytype){
		
		 instance = this;
		 this.serverid = serverid;
		 allDataSourceInstances.add(this);

		 // System.out.println(this.toString() + ": sjclClient " + String.valueOf(sjclClient));
		
		 OperationBinding fetch = new OperationBinding();  
	     fetch.setOperationType(DSOperationType.FETCH);  
	     fetch.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding add = new OperationBinding();  
	     add.setOperationType(DSOperationType.ADD);  
	     add.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding update = new OperationBinding();  
	     update.setOperationType(DSOperationType.UPDATE);  
	     update.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding remove = new OperationBinding();  
	     remove.setOperationType(DSOperationType.REMOVE);  
	     remove.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding validate = new OperationBinding();  
	     validate.setOperationType(DSOperationType.VALIDATE);  
	     validate.setDataProtocol(DSProtocol.POSTMESSAGE); 
	     OperationBinding custom = new OperationBinding();  
	     custom.setOperationType(DSOperationType.CUSTOM);  
	     custom.setDataProtocol(DSProtocol.POSTMESSAGE); 
	          
	     setOperationBindings(fetch, add, update, remove, validate, custom);
	     setDataFormat(DSDataFormat.JSON);
	     setJsonPrefix(""); //TODO da gestire
	     setJsonSuffix(""); //TODO da gestire
//	     setSkipJSONValidation(SkipJSONValidation.FULL);
//	     setUseStrictJSON(true);
		 setDropExtraFields(false);
	     setDataProtocol(DSProtocol.POSTPARAMS);
	     setDisableQueuing(true);
	     setAttribute("showPrompt", true, true);	         
	     
	     setDataURL("rest/datasourceservice/all");
	          
	     extraparam.put("sourceidobject", serverid);
	     if(keyproperty!=null){
	    	 extraparam.put("keyproperty",keyproperty);
	     }   
	     
	     if(keyproperty!=null){
	    	DataSourceField pkField = new DataSourceField(keyproperty,keytype);
	    	pkField.setPrimaryKey(true);
	       	addField(pkField);
	     }
	     
	     if(hierarchical){
	    	DataSourceField parentField = new DataSourceField("parentId",keytype);
	    	parentField.setForeignKey(this.getID() + "." + keyproperty);
	       	addField(parentField);
	     }
	}		
	
	@Override
	protected Object transformRequest(DSRequest dsRequest) {    	
		dsRequest.setUseSimpleHttp(true);
		dsRequest.setExportResults(true);
		if(timeout != null && timeout.intValue() > 0) {
			dsRequest.setTimeout(timeout);
		}
//		dsRequest.setShowPrompt(false);
		dsRequest.setContentType("application/json; charset=utf-8");	
		Map<String,String> requestextraparams = new HashMap<String,String>();
		requestextraparams.putAll(extraparam);
		requestextraparams.putAll(onlyfirstcallparam);
		onlyfirstcallparam.clear();
		if(!requestextraparams.isEmpty()){
			if(dsRequest.getData() == null) {
				dsRequest.setData(new HashMap());
			}
			//Setto gli attributi					
			JSOHelper.setAttribute(dsRequest.getData(), "EXTRA_PARAM", requestextraparams);				
		}		
		String serverid = extraparam.get("sourceidobject");
		if (serverid!=null && (serverid.equals("FilterConfiguratorService") ||
							   serverid.equals("FiltriObbligatoriDatasource") || 
							   serverid.equals("ListFieldDataSource") || 
							   serverid.equals("ListConfiguratorService") || 
							   serverid.equals("UploadStatusDataSource"))){
			forceToShowPrompt = false;
		}
		if(forceToShowPrompt != null) {
			setShowPrompt(forceToShowPrompt);
			dsRequest.setShowPrompt(forceToShowPrompt);
		} else {
			switch(dsRequest.getOperationType()){
				case FETCH:
					setShowPrompt(false);
					dsRequest.setShowPrompt(false);
					break;			
				case VALIDATE:			
				case ADD:			
				case REMOVE:
				case UPDATE:			
				case CUSTOM:
					setShowPrompt(true);
					dsRequest.setShowPrompt(true);
					break;			
			}
		}
		
		//Setto anche il fatto che viene da un filtro
		if(dsRequest.getData() == null) {
			dsRequest.setData(new HashMap());
		}
		//Setto l'attributo	
		JSOHelper.setAttribute(dsRequest.getData(), "selectFilter", isSelectFilter);			
		if (disableCursor){
//			System.out.println(dsRequest.getPromptCursor());
			dsRequest.setPromptCursor(null);
		}			
        
//	    Collection<String> cookies = Cookies.getCookieNames();
//	    for (String cookie : cookies) {
//	         final String value = Cookies.getCookie(cookie);
//	         System.out.println("cookie: ("+cookie+", "+value+")");
//	    }

		final String cookieNameToken = determineCookieName(false);
//		System.out.println("cookieNameToken: "+cookieNameToken);
		final String cookieValueToken = Cookies.getCookie(cookieNameToken);
//		System.out.println("cookieValueToken: "+String.valueOf(cookieValueToken));
		if (cookieValueToken != null) {
		   Map<String, String> httpHeaders = new HashMap<String, String>();
		   httpHeaders.put(NAME_HEADER_CSRF_TOKEN, cookieValueToken);
		   dsRequest.setHttpHeaders(httpHeaders);
		}
		
		Object strRequest = super.transformRequest(dsRequest); //è una stringa
		
		if(flagAttivaRequestValidator && RequestValidatorClient.containsScript(String.valueOf(strRequest))) {
			return null;
		}
		
		strRequest = clearFromScript(String.valueOf(strRequest));
		strRequest = escapeHtml(String.valueOf(strRequest));
		strRequest = encrypt(String.valueOf(strRequest));
//		System.out.println("INVIO RICHIESTA: \n"+strRequest);
	
		return strRequest; 
	}
	
	@Override
	protected void transformResponse(DSResponse response, DSRequest request, Object data) {
		
		boolean isStringData = false;
		JavaScriptObject dataobj = null;
		if(data instanceof String) {
			isStringData = true;
			dataobj = JSOHelper.eval((String) data);
			
		} else {
			dataobj = (JavaScriptObject) data;	
		}

		if (isEncryptionEnabled()) {
		    final String jsonEncrypted = JSON.encode(dataobj);
//		    System.out.println("RICEVUTA RISPOSTA: \n"+jsonEncrypted);
		    final String jsonDecrypted = decryptIfNeeded(jsonEncrypted);
//		    System.out.println("RISPOSTA DECRIPTATA: \n"+jsonDecrypted);
		    dataobj = JSOHelper.eval(jsonDecrypted);		    
		} 
		
		final JavaScriptObject responseobj = JSOHelper.getAttributeAsJavaScriptObject(dataobj, "response");

//	    System.out.println("dataobj: "+String.valueOf(JSOHelper.convertToJava(dataobj)));
//	    System.out.println("responseobj: "+String.valueOf(JSOHelper.convertToJava(responseobj)));

		//Setto i messsaggi di errore
		JavaScriptObject[] messagesobj = JSOHelper.getAttributeAsJavaScriptObjectArray(responseobj, "messages");
		
		List<MessageBean> messages = new ArrayList<MessageBean>();
		if(messagesobj != null){
			//Converto e salvo i valori sulla messagebox;
			for(JavaScriptObject messageobj : messagesobj){
//				MessageBean bean = JSONUtil.MessageBeanJsonReader.read(JSON.encode(messageobj));				
//				messages.add(bean);				
				Record record = new Record(messageobj);
				String message = record.getAttributeAsString("message");					
//				String detailmessage = record.getAttributeAsString("detailmessage");
				String lStrType = record.getAttributeAsString("type");
				MessageType type = MessageType.valueOf(MessageType.class, lStrType);
//				MessageType type = (MessageType)record.getAttributeAsObject("type");				
				MessageBean bean = new MessageBean(message, "", type);				
				messages.add(bean);
			}
		}
		
		//Verifico dove devo gestire la visualizzazione dei messaggi
		boolean gestisciMessaggiInExceuteCallback = false;
		try {
			JavaScriptObject originalDataJSO = request.getAttributeAsJavaScriptObject("originalData");
			if (originalDataJSO != null) {
				Record originalDataRecord = new Record(originalDataJSO);
				gestisciMessaggiInExceuteCallback = originalDataRecord.getAttributeAsBoolean("GWTRDSGestisciMessaggiInExceuteCallbackGWTRDS");
			}
		} catch (Exception e) {
			// Proseguo con gestisciMessaggiInExceuteCallback a false
		}
		if (gestisciMessaggiInExceuteCallback) {
			// Gestisco la visualizzazione dei messaggi nel metodo executeCallback
			response.setAttribute("responseMessageList", messages);
		} else {
			// Gestisco i messaggi qui
			if(messagebox != null && messages.size() > 0) {
				messagebox.addMessages(messages);
			}
		}
		if(request.getOperationType().equals(DSOperationType.FETCH) && datasourceId != null && serverid != null && datasourceId.equals(serverid) && customLayout !=null) {
			boolean overflowData = JSOHelper.getAttributeAsBoolean(responseobj, "overflowData");
			String overflowSortField = JSOHelper.getAttribute(responseobj, "overflowSortField");
			boolean overflowSortDesc = JSOHelper.getAttributeAsBoolean(responseobj, "overflowSortDesc");
			Integer rowsForPage = JSOHelper.getAttributeAsInt(responseobj, "rowsForPage");
			Integer totalRows = JSOHelper.getAttributeAsInt(responseobj, "totalRows");
			customLayout.refreshNroRecord(overflowData, overflowSortField, overflowSortDesc, rowsForPage, totalRows);
			customLayout = null;
		}
		
		if (isEncryptionEnabled()) {
//			System.out.println("ELABORAZIONE RISPOSTA ...");
			final JavaScriptObject dataJS = JSOHelper.getAttributeAsJavaScriptObject(responseobj, "data");
//		    System.out.println("dataJS: "+String.valueOf(JSOHelper.convertToJava(dataJS)));
			final Record[] ret = Record.convertToRecordArray(dataJS);
			response.setData(ret);	
		}
		
		if(isStringData) {
			super.transformResponse(response, request, JSON.encode(dataobj));
		} else {
			super.transformResponse(response, request, dataobj);
		}
	}
		
	/**
	 * Esegue un metodo custom sul datasource
	 * @param record
	 * @param callback
	 */
	public void executecustom(String operationname,Record record,DSCallback callback) {
		setShowPrompt(true);
		performCustomOperation(operationname, record, callback, new DSRequest());
	}
		
	public void getData(Record lRecordToLoad, DSCallback dsCallback) {
		performCustomOperation("get", lRecordToLoad, dsCallback, new DSRequest());		
	}
	
	public String getServerid() {
		return serverid;
	}
	
	public void setParentCanvas(Canvas pParentCanvas) {
		this.parentCanvas = pParentCanvas;
		if(pParentCanvas != null) {
			if(canvasDataSourceInstancesMap.get(pParentCanvas) == null) {
				canvasDataSourceInstancesMap.put(pParentCanvas, new HashSet<GWTRestDataSource>());
			}
			canvasDataSourceInstancesMap.get(pParentCanvas).add(this);
		}
	}
	
	public Canvas getParentCanvas() {
		return parentCanvas;
	}
	
	public void setFilter(boolean isFilter){
		this.isSelectFilter = isFilter;
	}	
	
	public void disableCursor(){
		disableCursor = true;
	}
	
	public void setForceToShowPrompt(Boolean showPrompt) {
		forceToShowPrompt = showPrompt;
	}	
	
	public void addParam(String key,String value) {
		if(extraparam.containsKey(key)) {
			extraparam.remove(key);
		}
		extraparam.put(key, value);
	}	
   
	public void addOnlyFirstCallParam(String key,String value) {
		if(onlyfirstcallparam.containsKey(key)) {
			onlyfirstcallparam.remove(key);
		}
		onlyfirstcallparam.put(key, value);
	}

	/**
	 * Effettua l'ovveride di un tipo definito nel datasource
	 * @param nama
	 * @param type
	 */
	public void addFieldType(String name,String type){
		extratypeparam.put(name, type);
	}
	
	private String encrypt(String data) {
		return sjclClient == null ? data : sjclClient.encrypt(data);
	}
	
	private boolean isEncryptionEnabled() {
		return sjclClient == null ? false : sjclClient.isEnabled();
	}
	
	private String decryptIfNeeded(String data) {
		return sjclClient == null ? data : sjclClient.decryptIfNeeded(data);
	}
	
	/**
     * ATTENZIONE: si suppone che il contesto dell'applicazione sia privo di caratteri slash
     */
    public static String getContextPath() {
	    final String hostPageBaseURL = GWT.getHostPageBaseURL();
	    final String[] array = hostPageBaseURL.split("/");
	    final String contextPath = "/" + array[array.length-1];
	    return contextPath;
    }
    
    public static void destroyAllDataSources() {
    	for(GWTRestDataSource datasource : allDataSourceInstances) {
			if(datasource != null) {
				datasource.destroy();
			}
		}
    	allDataSourceInstances.clear();		
    }
    
    public static void destroyAllCanvasDataSources(Canvas parentCanvas) {
    	if(parentCanvas != null && canvasDataSourceInstancesMap.get(parentCanvas) != null) {
    		for(GWTRestDataSource datasource : canvasDataSourceInstancesMap.get(parentCanvas)) {
    			if(datasource != null) {
    				datasource.destroy();
    			}
    		}
    		canvasDataSourceInstancesMap.remove(parentCanvas);
    	}    	
    }
    
    public static void printMessage(MessageBean bean){
		List<MessageBean> messages = new ArrayList<MessageBean>();
		messages.add(bean);
		messagebox.addMessages(messages);
	}	
    
    public static String determineCookieName(boolean flagOnlyName) {
    	String backendServiceName = "";
    	if (!flagOnlyName) {
    		final String contextPath = getContextPath();
    		backendServiceName = "-" + contextPath.replaceAll("/", "-");
    	}
        return NAME_COOKIE_CSRF_TOKEN + backendServiceName;
    }
    
    public static void settingMessageBox(MessageBox pMessagebox){
		messagebox = pMessagebox;
	}
	
	public static void settingFetchReferences(CustomLayout pCustomLayout, String pDatasourceId){
		customLayout = pCustomLayout;
		datasourceId = pDatasourceId;
	}
	
	public static void setFlagAttivaRequestValidator(boolean flagAttivaRequestValidator) {
		GWTRestDataSource.flagAttivaRequestValidator = flagAttivaRequestValidator;
	}

	public static void setSjclClient(SJCLClient sjcl) {
		GWTRestDataSource.sjclClient = sjcl;
	}
	
	public static void setScriptCleanerClient(ScriptCleanerClient scriptCleanerClient) {
		GWTRestDataSource.scriptCleanerClient = scriptCleanerClient;
	}

	public static void setEscapeHtmlClient(EscapeHtmlClient escapeHtmlClient) {
		GWTRestDataSource.escapeHtmlClient = escapeHtmlClient;
	}
	
    public static String clearFromScript(String data) {
		return scriptCleanerClient == null ? data : scriptCleanerClient.clearFromScript(data);
	}
    
    public static String escapeHtml(String data) {
		return escapeHtmlClient == null ? data : escapeHtmlClient.escapeHtml(data);
	}
    
    public void setOneCallDataSource(boolean isOneCallDataSource) {
    	this.isOneCallDataSource = isOneCallDataSource;
    }
    
    public boolean isOneCallDataSource() {
    	return isOneCallDataSource;
    }
    
    public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public void fetchData(Criteria criteria, final DSCallback callback) {
		super.fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				executeCallback(dsResponse, data, dsRequest, callback, false);
			}
		});
	}
	
	@Override
	public void addData(Record newRecord, final DSCallback callback) {
		final boolean gestisciMessaggiInExceuteCallback = newRecord != null;
		if (newRecord != null) {
			newRecord.setAttribute("GWTRDSGestisciMessaggiInExceuteCallbackGWTRDS", true);
		}
		super.addData(newRecord, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				executeCallback(dsResponse, data, dsRequest, callback, gestisciMessaggiInExceuteCallback);
			}
		});
	}
	
	public void updateData(Record updatedRecord, final DSCallback callback) {
		final boolean gestisciMessaggiInExceuteCallback = updatedRecord != null;
		if (updatedRecord != null) {
			updatedRecord.setAttribute("GWTRDSGestisciMessaggiInExceuteCallbackGWTRDS", true);
		}
		super.updateData(updatedRecord, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				executeCallback(dsResponse, data, dsRequest, callback, gestisciMessaggiInExceuteCallback);
			}
		});
	}
	
	@Override
	public void removeData(Record removedRecord, final DSCallback callback) {
		final boolean gestisciMessaggiInExceuteCallback = removedRecord != null;
		if (removedRecord != null) {
			removedRecord.setAttribute("GWTRDSGestisciMessaggiInExceuteCallbackGWTRDS", true);
		}
		super.removeData(removedRecord, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				executeCallback(dsResponse, data, dsRequest, callback, gestisciMessaggiInExceuteCallback);
			}
		});
	}
	
	@Override
	public void performCustomOperation(String operationId, Record data, final DSCallback callback, DSRequest requestProperties) {
		final boolean gestisciMessaggiInExceuteCallback = data != null;
		if (data != null) {
			data.setAttribute("GWTRDSGestisciMessaggiInExceuteCallbackGWTRDS", true);
		}
		super.performCustomOperation(operationId, data, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				executeCallback(dsResponse, data, dsRequest, callback, gestisciMessaggiInExceuteCallback);
			}
		}, requestProperties);
	}
	
	protected void executeCallback(DSResponse dsResponse, Object data, DSRequest dsRequest, DSCallback callback, boolean gestisciMessaggiInExceuteCallback) {
		try {	
			if (gestisciMessaggiInExceuteCallback) {
				Object messagesObj = dsResponse.getAttributeAsJavaScriptObject("responseMessageList");
				if (messagesObj != null) {
					try {
						List messages = JSOHelper.convertToList(dsResponse.getAttributeAsJavaScriptObject("responseMessageList"));
						if(messagebox != null && messages.size() > 0) {
							messagebox.addMessages(messages, callback, dsResponse, data, dsRequest);
							return;
						}
					} catch (Exception e) {
						// Vado avanti ed eseguo la callback
					}
				}
			}
			if(callback != null) {
				callback.execute(dsResponse, data, dsRequest);
			}
		} finally {
			if(isOneCallDataSource()) {
				instance.destroy();
			}
		}
	}
	
}