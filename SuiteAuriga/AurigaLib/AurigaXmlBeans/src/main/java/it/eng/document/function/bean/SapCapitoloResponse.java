/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SapCapitoloResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<SapZstCapitoloResponse> item;
	
	public List<SapZstCapitoloResponse> getItem() {
		return item;
	}
	
	public void setItem(List<SapZstCapitoloResponse> item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "SapCapitoloResponse [item=" + item + "]";
	}
	
}
