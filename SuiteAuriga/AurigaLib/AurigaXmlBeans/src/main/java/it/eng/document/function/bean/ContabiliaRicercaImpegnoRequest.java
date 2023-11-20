/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaImpegnoRequest extends ContabiliaRicercaMovimentoGestioneRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoImpegno;
	private Integer numeroImpegno;
	private String idSpAoo;
	
	public Integer getAnnoImpegno() {
		return annoImpegno;
	}
	
	public void setAnnoImpegno(Integer annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	
	public Integer getNumeroImpegno() {
		return numeroImpegno;
	}
	
	public void setNumeroImpegno(Integer numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Override
	public String toString() {
		return "ContabiliaRicercaImpegnoRequest [annoImpegno=" + annoImpegno + ", numeroImpegno=" + numeroImpegno
				+ ", idSpAoo=" + idSpAoo + "]";
	}
	
}
