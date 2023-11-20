/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TSerializableList<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista con i record paginati
	 */
	private List<E> data;	 
		
	public void addData(E record){
		if(data==null){
			data = new ArrayList<E>();
		}
		data.add(record);
	}
	
	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}
}
