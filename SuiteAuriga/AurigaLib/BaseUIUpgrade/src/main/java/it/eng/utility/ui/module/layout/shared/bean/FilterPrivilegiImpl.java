/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilterPrivilegiImpl implements FilterPrivilegiUtil{

	public static FilterPrivilegiContainer filterPrivilegiContainer;

	@Override
	public boolean isRequired(String nomeFiltro, String[] privilegi) {
		Map<String, List<String>> lMap = filterPrivilegiContainer.configMap;
		if (lMap!=null)
		if (lMap.get(nomeFiltro)!=null){
			for (String lString : privilegi){
				if (contains(lString, lMap.get(nomeFiltro)))
					return false;
			}
		}
		return true;
	}


	private boolean contains(String value, List<String> valori){
		for (String val : valori){
			if (val.equals(value)) return true;
		}
		return false;
	}

	@Override
	public void setContainer(FilterPrivilegiContainer pFilterPrivilegiContainer) {
		FilterPrivilegiImpl.filterPrivilegiContainer = pFilterPrivilegiContainer; 
		
	}


	@Override
	public Map<String, List<String>> getMap() {
		
		return filterPrivilegiContainer.configMap;
	}


	@Override
	public FilterPrivilegiContainer getContainer() {
		
		return filterPrivilegiContainer;
	}
	

}
