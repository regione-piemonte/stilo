/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AllegatiMailBean extends AllegatiBean {

	private List<Integer> posizione;
	private List<String> idAttach;
	public List<Integer> getPosizione() {
		return posizione;
	}
	public void setPosizione(List<Integer> posizione) {
		this.posizione = posizione;
	}
	public List<String> getIdAttach() {
		return idAttach;
	}
	public void setIdAttach(List<String> idAttach) {
		this.idAttach = idAttach;
	}
}
