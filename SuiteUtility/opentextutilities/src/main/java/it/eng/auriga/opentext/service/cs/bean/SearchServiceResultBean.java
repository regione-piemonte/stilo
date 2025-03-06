package it.eng.auriga.opentext.service.cs.bean;

import java.util.HashMap;
import java.util.Map;

public class SearchServiceResultBean {
	
	private Map<String, Object> resultSetMap = new HashMap<String, Object>();

	public Map<String, Object> getResultSetMap() {
		return resultSetMap;
	}

	public void setResultSetMap(Map<String, Object> resultSetMap) {
		this.resultSetMap = resultSetMap;
	}
	
	private Long otDataId ;

	public Long getOtDataId() {
		return otDataId;
	}

	public void setOtDataId(Long otDataId) {
		this.otDataId = otDataId;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SearchServiceResultBean ){
			SearchServiceResultBean currentObj = (SearchServiceResultBean) obj;
			return currentObj.getOtDataId().equals(this.getOtDataId()) ;
		}

		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	

	
	
}
