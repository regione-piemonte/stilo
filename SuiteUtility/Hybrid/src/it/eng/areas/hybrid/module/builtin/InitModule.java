package it.eng.areas.hybrid.module.builtin;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleInfo;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.osgi.OSGiManager;

public class InitModule implements IClientWebSocketModule, IClientHttpModule, IClientModuleInfo {
	Logger logger = Logger.getLogger(InitModule.class);
	
	private static final String RESPONSE_PONG = "Pong";
	
	OSGiManager osgiManager;
	IClientModuleContainer container;
	
	
	public InitModule(OSGiManager manager) {
		this.osgiManager = manager;
	}

	@Override
	public void initModule(IClientModuleContainer container) throws Exception {
		this.container = container;
	}

	@Override
	public void onMessage(String message) throws IOException {
		container.sendMessage(RESPONSE_PONG);
	}

	@Override
	public void onClose(int code, String reason, boolean initiatedByRemote) {
		//Nop
		
	}

	@Override
	public void onException(Exception e) {
	}

	@Override
	public Map<String, Object> processHttpRequest(Map<String, Object> request) throws Exception {
		//Ritorno le risorse 
		String uri = (String) request.get(IClientHttpModule.REQUEST_URI);
		if (uri.equals("/init/ping")) {
			return HttpModuleUtils.returnResource("pong", HttpModuleUtils.MIMETYPE_TXT); 
		} else if (uri.equals("/init/modules")) {
			@SuppressWarnings("unchecked")
			Map<String,String> params = (Map<String, String>) request.get(IClientHttpModule.REQUEST_PARAMETERS);
			String session = params.get("session");
			logger.info("session::::: " + session);
			if (session != null) {
				container.setSharedParameter(IClientModuleContainer.PARAMETER_SERVERSESSION, session);
			}
			
			String modules = params.get("modules");
			String[] modulesToken = modules.split(",");
			
			StringBuffer json = new StringBuffer();
			json.append("{modules:[");

			for (int idx = 0; idx < modulesToken.length; idx++) {
				if (idx > 0) {
					json.append(",");
				}
				
				String[] tokens = modulesToken[idx].split(":");
				String moduleName = tokens[0];
				String moduleVersionRequest = tokens.length > 1 ? tokens[1] : null;
				
				
				//Verifico la presenza del plugin ed, eventualmente, lo carico
				IClientModuleInfo moduleInfo = findModule(moduleName, moduleVersionRequest);
				
				if (moduleInfo == null) {
					logger.info("Modulo "+moduleName+" non presente, installazione da server");
					//Provo ad installarlo dal server
					String serverUrlParameter = container.getParameter(IClientModuleContainer.PARAMETER_SERVERURL);
					String moduleUrl = serverUrlParameter + "module/" + moduleName;
					if (moduleVersionRequest != null) {
						moduleUrl += "/" + moduleVersionRequest;
					}
					logger.info("Scarico da " + moduleUrl);
					URL url = new URL(moduleUrl);
					osgiManager.startBundle(moduleName, url.openStream());
					
					logger.debug("Modulo "+moduleName+" installato, in avvio...");
					//Aspetto che sia completamente avviato (per evitare lock ed incaprettamenti faccio un polling old economy...)
					long tick = System.currentTimeMillis();
					while (System.currentTimeMillis() - tick < 30000) {
						moduleInfo = findModule(moduleName, moduleVersionRequest);
						if (moduleInfo == null) {
							Thread.sleep(100);
						} else {
							break;
						}
					}
				}
				
				if (moduleInfo != null) {
					logger.debug("Modulo "+moduleName+" avviato");
					json.append("{name:\"").append(moduleInfo.getModuleName()).append("\",version:\"").append(moduleInfo.getModuleVersion()).append("\", uri:\"").append(moduleInfo.getModuleUri()).append("/stub\"}");
				} else {
					logger.error("Modulo "+moduleName+" NON avviato");
					json.append("{name:\"").append(moduleName).append("\",error:\"Modulo non presente\"}");
				}
				
			}
			json.append("]}");
			
			
			return HttpModuleUtils.returnResource(json.toString(), HttpModuleUtils.MIMETYPE_JSON);
		} else if (uri.endsWith("handshake.js")) {
			return HttpModuleUtils.returnResource(this.getClass().getResourceAsStream("assets/handshakemodule.js"),HttpModuleUtils.MIMETYPE_JS);
		}
		
		return null;
	}

	private IClientModuleInfo findModule(String moduleName, String moduleVersionRequest) {
		for (IClientModuleInfo info : osgiManager.getClientModuleManager().getRegisteredModulesInfo()) {
			if (info.getModuleName().equals(moduleName)) {
				if (info.getModuleVersion().equals(moduleVersionRequest != null ? moduleVersionRequest : info.getModuleVersion())) {
					return info;
				}
			}
		}
		return null;
	}

	@Override
	public String getModuleUri() {
		return "/init";
	}
	
	@Override
	public String getModuleName() {
		return "init";
	}

	@Override
	public String getModuleVersion() {
		return "1.0.0";
	}

}
