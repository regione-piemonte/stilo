/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface IClientModule extends IClientModuleInfo {
	
	void initModule(IClientModuleContainer container) throws Exception;
	
}
