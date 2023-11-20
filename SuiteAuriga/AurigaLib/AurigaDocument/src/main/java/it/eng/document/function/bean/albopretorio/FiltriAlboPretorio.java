/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antonio Peluso
 */

@XmlRootElement(name = "filtriAlboPretorio")
@XmlAccessorType(XmlAccessType.FIELD)
public class FiltriAlboPretorio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "filtroAlbo")
	List<FiltroAlbo> filtriAlbo;
	
	public List<FiltroAlbo> getFiltriAlbo() {
		return filtriAlbo;
	}

	public void setFiltriAlbo(List<FiltroAlbo> filtriAlbo) {
		this.filtriAlbo = filtriAlbo;
	}
	
	
}
