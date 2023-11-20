/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArrayBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6561162376749858210L;
	private List<T> list;

	public void setList(List<T> list) {
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}
	
	public ArrayBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayBean(List<T> list){
		this.list = list;
	}
}
