/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoContiCreditoDebitoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<AmcoContoCreditoDebito> contoCreditoDebito;
	private String descrizioneErrore;
	
	public List<AmcoContoCreditoDebito> getContoCreditoDebito() {
		return contoCreditoDebito;
	}
	
	public void setContoCreditoDebito(List<AmcoContoCreditoDebito> contoCreditoDebito) {
		this.contoCreditoDebito = contoCreditoDebito;
	}
	
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	@Override
	public String toString() {
		return "AmcoContiCreditoDebitoResponse [contoCreditoDebito=" + contoCreditoDebito + ", descrizioneErrore="
				+ descrizioneErrore + "]";
	}
	
}
