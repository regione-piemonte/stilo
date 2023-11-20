/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaVincoliStilo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaVincoloImpegnoStilo> vincoli;
	
	public List<ContabiliaVincoloImpegnoStilo> getVincoli() {
		return vincoli;
	}
	
	public void setVincoli(List<ContabiliaVincoloImpegnoStilo> vincoli) {
		this.vincoli = vincoli;
	}
	
	@Override
	public String toString() {
		return "ContabiliaVincoliStilo [vincoli=" + vincoli + "]";
	}
	
}
