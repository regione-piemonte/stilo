/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BeanString implements Serializable{
	private ArrayList<String> stringhe;
	private HashSet<String> sets= new HashSet<String>();
	
	public ArrayList<String> getStringhe() {
		return stringhe;
	}

	public void setStringhe(ArrayList<String> stringhe) {
		this.stringhe = stringhe;
	}

	public HashSet<String> getSets() {
		return sets;
	}

	public void setSets(HashSet<String> sets) {
		this.sets = sets;
	}
	
}
