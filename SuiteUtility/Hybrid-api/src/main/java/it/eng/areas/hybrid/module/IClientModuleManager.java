package it.eng.areas.hybrid.module;

import java.util.List;

public interface IClientModuleManager {
	
	void registerModule(IClientModule module) throws Exception;
	
	public List<IClientModuleInfo> getRegisteredModulesInfo();
	
	public void setSharedProperty(String name, String value);
	
	public String getSharedProperty(String name);

}
