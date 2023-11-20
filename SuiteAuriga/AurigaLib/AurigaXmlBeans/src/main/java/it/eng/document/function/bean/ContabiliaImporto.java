/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaImporto extends ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoCompetenza;
	
	public Integer getAnnoCompetenza() {
		return annoCompetenza;
	}
	
	public void setAnnoCompetenza(Integer annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}
	
	@Override
	public String toString() {
		return "ContabiliaImporto [annoCompetenza=" + annoCompetenza + "]";
	}
	
}
