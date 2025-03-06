package it.eng.core.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di supporto per la paginazione di una entity
 * @author Rigo Michele
 *
 */
@XmlRootElement
public class TPagingList<E> implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Record iniziale su cui iniziare a recuperare i dati
	 */
	private Integer startRow;
	
	//Indica se la query ha ecceduto il limite di overflow
	private boolean overflow = false;
	
	/**
	 * Record finale di recupero dei dati
	 */
	private Integer endRow;
	
	/**
	 * Record totali trovati
	 */
	private Integer totalRows;
	
	/**
	 * Lista con i record paginati
	 */
	private List<E> data = new ArrayList<E>();	 
	
	public void addData(E record){
		if(data==null){
			data = new ArrayList<E>();
		}
		data.add(record);
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TPagingList [startRow=" + startRow + ", endRow=" + endRow
				+ ", totalRows=" + totalRows + ", data=" + data + "]";
	}

	public void setOverflow(boolean overflow) {
		this.overflow = overflow;
	}

	public boolean getOverflow() {
		return overflow;
	}
	
	
}