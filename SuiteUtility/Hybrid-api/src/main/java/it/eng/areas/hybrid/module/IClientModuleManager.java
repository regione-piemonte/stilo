/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public interface IClientModuleManager {
	
	void registerModule(IClientModule module) throws Exception;
	
	public List<IClientModuleInfo> getRegisteredModulesInfo();
	
	public void setSharedProperty(String name, String value);
	
	public String getSharedProperty(String name);

}
