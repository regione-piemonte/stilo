/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

public class FilterPrivilegiContainer {

	public Map<String, List<String>> configMap;

	public Map<String, List<String>> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, List<String>> pMap) {
		this.configMap = pMap;
	}
	
	
}
