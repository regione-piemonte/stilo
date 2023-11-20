/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaSubImpegno extends ContabiliaMovimentoGestione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoSubImpegno;
	private BigDecimal disponibilitaLiquidare;
	private Integer numeroSubImpegno;
	
	public Integer getAnnoSubImpegno() {
		return annoSubImpegno;
	}
	
	public void setAnnoSubImpegno(Integer annoSubImpegno) {
		this.annoSubImpegno = annoSubImpegno;
	}
	
	public BigDecimal getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}
	
	public void setDisponibilitaLiquidare(BigDecimal disponibilitaLiquidare) {
		this.disponibilitaLiquidare = disponibilitaLiquidare;
	}
	
	public Integer getNumeroSubImpegno() {
		return numeroSubImpegno;
	}
	
	public void setNumeroSubImpegno(Integer numeroSubImpegno) {
		this.numeroSubImpegno = numeroSubImpegno;
	}
	
	@Override
	public String toString() {
		return "ContabiliaSubImpegno [annoSubImpegno=" + annoSubImpegno + ", disponibilitaLiquidare="
				+ disponibilitaLiquidare + ", numeroSubImpegno=" + numeroSubImpegno + "]";
	}
	
}
