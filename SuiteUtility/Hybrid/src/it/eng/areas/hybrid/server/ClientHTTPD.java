package it.eng.areas.hybrid.server;

import java.awt.TrayIcon.MessageType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.function.Consumer;

import org.apache.log4j.Logger;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;
import fi.iki.elonen.NanoWSD.WebSocketFrame.CloseCode;
import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleInfo;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.util.TrayIconUtils;
import javafx.scene.Scene;

public class ClientHTTPD extends NanoWSD implements IClientModuleManager {

	private static final Logger logger = Logger.getLogger(ClientHTTPD.class);
	
	protected Timer pingTimer;
	protected Map<String, ClientModuleWrapper> modules = new HashMap<>();
	protected Map<String, String> sharedProperties = new HashMap<>();
	protected final boolean useSSL;
	protected final int port;
	protected final String serverUrl;
	protected final String context;
	
	protected class ClientModuleWrapper implements IClientModuleInfo {
		
		public class ClientWebSocketModuleWrapper extends WebSocket {
			private final IHTTPSession httpSession;

			public ClientWebSocketModuleWrapper(IHTTPSession handshakeRequest) {
				super(handshakeRequest);
				this.httpSession = handshakeRequest;
				websocket = this;
			}
			

			@Override
			protected void onPong(WebSocketFrame pongFrame) {
			}

			@Override
			protected void onMessage(WebSocketFrame messageFrame) {
				try {
					String payload = messageFrame.getTextPayload();
					((IClientWebSocketModule)clientModule).onMessage(payload);
				} catch (Exception e) {
					//TODO Loggare
				}
			}

			@Override
			protected void onClose(CloseCode code, String reason, boolean initiatedByRemote) {
				try {
					if (code == null) {
						code = CloseCode.AbnormalClosure;
					}
					((IClientWebSocketModule)clientModule).onClose(code.getValue(), reason, initiatedByRemote);
				} finally {
					websocket = null;
				}
				
			}

			@Override
			protected void onException(IOException e) {
				((IClientWebSocketModule)clientModule).onException(e);
			}

			public IHTTPSession getHttpSession() {
				return httpSession;
			}


			@Override
			protected void onOpen() {
				// TODO Auto-generated method stub
				
			}

		}
		
		protected final String path;
		protected final IClientModule clientModule;
		
		protected ClientWebSocketModuleWrapper websocket;
		
		protected ClientModuleWrapper(String path, IClientModule clientModule) throws Exception {
			this.path = path;
			this.clientModule = clientModule;
			this.clientModule.initModule(new IClientModuleContainer() {
				
				@Override
				public String getParameter(String parameterName) {
					return sharedProperties.get(parameterName);
				}
				
				@Override
				public void setSharedParameter(String parameterName, String value) {
					sharedProperties.put(parameterName, value);
				}
				
				@Override
				public void showMainPanel() {
					throw new IllegalAccessError("ToDo");
				}
				
				@Override
				public void sendMessage(String message) throws IOException {
					websocket.send(message);
				}
				
				@Override
				public Scene getMainPanel() {
					return null;
				}

				@Override
				public void displayTrayIconMessage(String caption, String text, MessageType messageType) {
					TrayIconUtils.displayMessage(caption, text, messageType);
				}
			});
		}
		
		@Override
		public String getModuleUri() {
			return clientModule.getModuleUri();
		}
		
		@Override
		public String getModuleName() {
			return clientModule.getModuleName();
		}
		
		@Override
		public String getModuleVersion() {
			if (clientModule instanceof IClientModuleInfo) {
				return ((IClientModuleInfo) clientModule).getModuleVersion();
			}
			return "N.D.";
		}
		
		public String getPath() {
			return path;
		}
		
		public IClientModule getClientModule() {
			return clientModule;
		}
		
		public Response serve(IHTTPSession session) throws Exception {
			Response result = null;
			
			//Altrimenti la gestisco come HTTP se il client implementa l'opportuna interfaccia
			if (result == null && clientModule instanceof IClientHttpModule) {
				//Preparo la mappa con i parametri
				Map<String, Object> httpRequest = new HashMap<>();
				httpRequest.put(IClientHttpModule.REQUEST_URI, session.getUri());
				httpRequest.put(IClientHttpModule.REQUEST_QUERYSTRING, session.getQueryParameterString());
				httpRequest.put(IClientHttpModule.REQUEST_METHOD, session.getMethod());
				//httpRequest.put(IClientHttpModule.REQUEST_PARAMETERS, session.getParms());
				httpRequest.put(IClientHttpModule.REQUEST_HEADERS, session.getHeaders());
				
				//Semplifico i parametri (lo stesso nome parametro non puï¿½ essere usato piï¿½ volte)
	            Map<String, String> params = new HashMap<String, String>();
	            for (String key : session.getParameters().keySet()) { 
	                params.put(key, session.getParameters().get(key).get(0));
	            }
				httpRequest.put(IClientHttpModule.REQUEST_PARAMETERS, params);
				
				

				//Decodifico il post
				if (Method.POST == session.getMethod()) {
					StringBuffer outBuffer = new StringBuffer();
					int ch = -10;
					try {
						InputStream bis = session.getInputStream();
						logger.debug("Content-length vale: " + Integer.parseInt(session.getHeaders().get( "content-length" )));
						logger.debug("Available vale: " + bis.available());
						ch = bis.read();
						logger.debug("Primo carattere letto: " + ch);
						while(ch > -1){
							outBuffer.append((char)ch);	
			                ch = bis.read();
						}
					} catch (Exception e) {
						logger.debug("Ultimo carattere letto: " + ch);
						logger.debug("Non ci sono più dati nell'input stream, probabilmente l'eccezione può essere ignorata", e);
					}
					
					httpRequest.put(IClientHttpModule.REQUEST_DATA, new String(outBuffer));					
				}				
				
				//Decodifico i cookie
				final Map<String,String> cookies = new HashMap<>();
				httpRequest.put(IClientHttpModule.REQUEST_COOKIES, cookies);
				final CookieHandler cookieHandler = session.getCookies();
//				cookieHandler.forEach(new Consumer<String>() {
//
//					@Override
//					public void accept(String cookieName) {
//						cookies.put(cookieName, cookieHandler.read(cookieName));
//					}
//				});
				
				//Converto la rischiesta
				Map<String, Object> httpResult = ((IClientHttpModule)clientModule).processHttpRequest(httpRequest);
				
				if (httpResult != null) {
					String httpResultMimetype = (String) httpResult.get(IClientHttpModule.RESPONSE_MIMETYPE);
					Object httpResultContent = httpResult.get(IClientHttpModule.RESPONSE_CONTENT);
					if (httpResultContent instanceof String) {
						result = NanoHTTPD.newFixedLengthResponse((String) httpResultContent);
					} else if (httpResultContent instanceof InputStream) {
						result = NanoHTTPD.newChunkedResponse(Response.Status.OK,httpResultMimetype,(InputStream)httpResultContent);
					} else if (httpResultContent instanceof byte[]) {
						result = NanoHTTPD.newFixedLengthResponse(Response.Status.OK,httpResultMimetype,new ByteArrayInputStream((byte[]) httpResultContent), ((byte[]) httpResultContent).length);
					} else {
						throw new IllegalArgumentException("Module "+getModuleName() + ", illegal Response Type for uri "+session.getUri());
					}
					result.addHeader("Access-Control-Allow-Origin","*");
				}
			}
			
			
			return result;
		}
		
		protected void ping() {
			if (websocket != null) {
				try {
					websocket.ping("ping".getBytes());
				} catch (Exception e1) {
					try {
						this.websocket.close(CloseCode.GoingAway, e1.getMessage(), true);
					} catch (Exception e2) {
						//NOP
					}
				}
			}
		}

		public WebSocket createClientWebSocketModuleWrapper(IHTTPSession handshake) {
			return new ClientWebSocketModuleWrapper(handshake);
		}

	}

	public ClientHTTPD(int port, boolean useSSL, String serverUrl, String context) {
		super(port);
		this.port = port;
		this.useSSL = useSSL;
		this.serverUrl = serverUrl;
		this.context= context;
	}
	
	
	@Override
	public void start() throws IOException {
		logger.info("start ClientHTTPD con port: " + port);
		
		if (useSSL) {
			logger.debug("Hybrid verra' avviato in SSL");
			this.makeSecure(NanoHTTPD.makeSSLSocketFactory("/it/eng/areas/hybrid/server/server.jks", "12345678".toCharArray()), null);
		}
		super.start();
		TimerTask pingTask = new TimerTask() {

			@Override
			public void run() {
				for (ClientModuleWrapper wrapper : new ArrayList<ClientModuleWrapper>(modules.values())) {
					wrapper.ping();
				}
			}
		};
		this.pingTimer = new Timer(true);
		pingTimer.scheduleAtFixedRate(pingTask, 0, 1000);
		
	}
	
	@Override
	public void stop() {
		try {
			pingTimer.cancel();
		} finally {
			super.stop();
		}
	}
	
	public void registerModule(IClientModule module) throws Exception {
		String path = module.getModuleUri();
		//Il modulo deve essere giï¿½ inizializzato
		if (!path.startsWith("/") && path.length() > 0) {
			path = "/"+path;
		}
		modules.put(path, new ClientModuleWrapper(path, module));
	}
	
	@Override
	public void setSharedProperty(String name, String value) {
		if (!sharedProperties.containsKey(name)) {
			this.sharedProperties.put(name, value);
		} else {
			throw new IllegalStateException(name+" is present");
		}
		
	}
	
	@Override
	public String getSharedProperty(String name) {
		return sharedProperties.get(name);
	}
	
	@Override
	public List<IClientModuleInfo> getRegisteredModulesInfo() {
		return Collections.unmodifiableList(new ArrayList<IClientModuleInfo>(modules.values()));
	}

	
	@Override
	protected Response serveHttp(IHTTPSession session) {
		try {
			Response result = null;
			String uri = session.getUri();
			String remoteHostName = session.getRemoteHostName();
			String remoteIpAddress = session.getRemoteIpAddress();
			
			String header_referer = "";
			String header_remote_addr = "";
			String header_http_client_ip = "";
			String header_origin = "";
			String header_host = "";
			if (session != null && session.getHeaders() !=  null && session.getHeaders().get("referer") !=  null) {
				header_referer = session.getHeaders().get("referer") != null ? session.getHeaders().get("referer") : header_referer;
				header_remote_addr = session.getHeaders().get("remote-addr") != null ? session.getHeaders().get("remote-addr") : header_remote_addr;
				header_http_client_ip = session.getHeaders().get("http-client-ip") != null ? session.getHeaders().get("http-client-ip") : header_http_client_ip;
				header_origin = session.getHeaders().get("origin") != null ? session.getHeaders().get("origin") : header_origin;
				header_host = session.getHeaders().get("host") != null ? session.getHeaders().get("host") : header_host;
			}		
			logger.debug("********************* Inizio parametri per verifica provenienza *********************");
			logger.debug("uri: " + uri);
			logger.debug("serverUrl: " + serverUrl);
			logger.debug("context: " + context);
			logger.debug("remoteHostName: " + remoteHostName);
			logger.debug("remoteIpAddress: " + remoteIpAddress);
			logger.debug("header_referer: " + header_referer);
			logger.debug("header_remote_addr: " + header_remote_addr);
			logger.debug("header_http_client_ip: " + header_http_client_ip);
			logger.debug("header_origin: " + header_origin);
			logger.debug("header_host: " + header_host);
			logger.debug("********************* Fine parametri per verifica provenienza *********************");
			
			// Blocco le chiamate
			
			// Blocco tutte le chiamate che non provengono da 127.0.0.1
			if (!"127.0.0.1".equalsIgnoreCase(remoteIpAddress)) {
				logger.error("Accesso negato");
				return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.UNAUTHORIZED,null,null);
			} else {
//				// Controllo se ho rihiesto info. Per tutte le altre richieste controllo anche il contesto a cui fa riferimento al richiesta
//				if (!"/info".equalsIgnoreCase(uri) && !"/favicon.ico".equalsIgnoreCase(uri)) {
//					if (context != null && !"".equalsIgnoreCase(context)) {
//						if (serverUrl == null || "".equalsIgnoreCase(serverUrl) || !serverUrl.toUpperCase().contains(context.toUpperCase())) {
//							logger.error("Accesso negato");
//							return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.UNAUTHORIZED,null,null);
//						}
//					}
//				}
			}
			String modulePath = uri;
			boolean handled = false;
			
			while (modulePath!= null && !handled) {
				ClientModuleWrapper wrapper = modules.get(modulePath);
				if (wrapper != null) {
					result = wrapper.serve(session);
					handled = true;
				} else {
					int idx = modulePath.lastIndexOf('/');
					if (idx > -1) {
						modulePath = modulePath.substring(0,idx);
					} else {
						modulePath = "";
					}
				}
			}
			
			if (handled) {
				return result;
			} else {
				throw new IllegalStateException("No handler for request");
			}
		} catch (Exception e) {
			logger.error(e, e);
			return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR,null,null);
		}
	}
	
	@Override
	public WebSocket openWebSocket(IHTTPSession handshake) {
		String uri = handshake.getUri();
		
		String modulePath = uri;
		boolean handled = false;
		
		while (modulePath!= null && !handled) {
			ClientModuleWrapper wrapper = modules.get(modulePath);
			if (wrapper != null) {
				return wrapper.createClientWebSocketModuleWrapper(handshake);
			} else {
				int idx = modulePath.lastIndexOf('/');
				if (idx > -1) {
					modulePath = modulePath.substring(0,idx);
				} else {
					modulePath = "";
				}
			}
		}
		
		logger.error("Nessun modulo configurato per "+uri);
		
		return null;
	}
	

}
