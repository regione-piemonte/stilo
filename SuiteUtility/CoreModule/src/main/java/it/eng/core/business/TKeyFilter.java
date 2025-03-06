package it.eng.core.business;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Oggetto di utilità per raggruppare eventuali chiavi da includere/escludere in una query
 *
 * @param <KEY_TYPE> Tipo della chiave da filtrare
 */
@XmlRootElement
public class TKeyFilter<E> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	public TKeyFilter() {
		// TODO Auto-generated constructor stub
	}
	
	//id da includere
	private Collection<E> includeId ;
	//id da escludere
	private Collection<E> excludeId ;
	//id da preservare in ogni caso
	private E preserveId;
	
	
	
	public TKeyFilter(Collection<E> includeId,
			Collection<E> excludeId, E preserveId) {
		super();
		this.includeId = includeId;
		this.excludeId = excludeId;
		this.preserveId = preserveId;
	}
	
	public Collection<E> getIncludeId() {
		return includeId;
	}
	public void setIncludeId(Collection<E> includeId) {
		this.includeId = includeId;
	}
	public Collection<E> getExcludeId() {
		return excludeId;
	}
	public void setExcludeId(Collection<E> excludeId) {
		this.excludeId = excludeId;
	}
	public Object getPreserveId() {
		return preserveId;
	}
	public void setPreserveId(E preserveId) {
		this.preserveId = preserveId;
	}
	
	 
	
}
