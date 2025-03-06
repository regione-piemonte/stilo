package it.eng.core.business;

import java.io.Serializable;

/**
 * Bean di supporto per l'ordinamento di una entity
 * @author Rigo Michele
 *
 */
public class TOrderBy implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nome della proprietà dell'entity da ordinare 
	 */
	private String propname;
		
	/**
	 * Verso di ordinamento della proprietà dell'entity
	 */
	private OrderByType type;

	public void setPropname(String propname) {
		this.propname = propname;
	}

	public String getPropname() {
		return propname;
	}

	public void setType(OrderByType type) {
		this.type = type;
	}

	public OrderByType getType() {
		return type;
	}
		
	public enum OrderByType {
		ASCENDING,
		DESCENDING
	} 
	
}