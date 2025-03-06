package it.eng.areas.hybrid.module.builtin;

import java.util.Map;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleInfo;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;

public class RootModule implements IClientHttpModule, IClientModuleInfo{

	@Override
	public void initModule(IClientModuleContainer container) throws Exception {
	}

	@Override
	public Map<String, Object> processHttpRequest(Map<String, Object> request) throws Exception {
		return HttpModuleUtils.returnHtml("<html><body>Hello! Hybrid is running...</body></html>");
	}
	
	@Override
	public String getModuleUri() {
		return "";
	}

	@Override
	public String getModuleName() {
		return "core";
	}

	@Override
	public String getModuleVersion() {
		return "1.0.0";
	}

}
