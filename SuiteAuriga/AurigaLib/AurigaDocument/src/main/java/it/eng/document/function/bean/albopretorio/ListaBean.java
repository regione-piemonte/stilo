/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaBean<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<T> data = new ArrayList<T>();
	
	public ListaBean(List<T> data){
		if(data==null){
			data = new ArrayList<T>();
		}
		setData(data);
	}
	
	public ListaBean(){
	}
	
	public void addRecord(T record){
		data.add(record);
	}
	
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}

}