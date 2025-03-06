package it.eng.core.business;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di supporto per la paginazione e gli ordinamenti di una entity
 * @author Rigo Michele
 *
 */
@XmlRootElement
public class TFilterFetch<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Oggetto che rappresenta il Filtro di ricerca
	 */
	private E filter;

	/**
	 * Record iniziale su cui iniziare a recuperare i dati
	 */
	private Integer startRow;
	
	/**
	 * Record finale di recupero dei dati
	 */
	private Integer endRow;
	
	/**
	 * Lista degli ordinamenti da effettuare sul filtro di ricerca
	 * @return
	 */
	private List<TOrderBy> orders; 
		
	public E getFilter() {
		return filter;
	}

	public void setFilter(E filter) {
		this.filter = filter;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public List<TOrderBy> getOrders() {
		return orders;
	}

	public void setOrders(List<TOrderBy> orders) {
		this.orders = orders;
	}
}