/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspRichiesteContabiliRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;	
	private Integer rigaAttim;
	private String es;
	private Integer progkeyvb;
	private Integer tipoImp;
	private String desImp;
	private String ragioneSociale;
	private String progSogg;
	private String codCup;
	private String desCup;
	private String cig;
	private String desCig;
	private String motivoNoCig;
	private String codiceOpera;
	private Double importo;
	private Integer annoEse;
	private Integer provenImporto;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getRigaAttim() {
		return rigaAttim;
	}
	
	public void setRigaAttim(Integer rigaAttim) {
		this.rigaAttim = rigaAttim;
	}
	
	public String getEs() {
		return es;
	}
	
	public void setEs(String es) {
		this.es = es;
	}
	
	public Integer getProgkeyvb() {
		return progkeyvb;
	}
	
	public void setProgkeyvb(Integer progkeyvb) {
		this.progkeyvb = progkeyvb;
	}
	
	public Integer getTipoImp() {
		return tipoImp;
	}
	
	public void setTipoImp(Integer tipoImp) {
		this.tipoImp = tipoImp;
	}
	
	public String getDesImp() {
		return desImp;
	}
	
	public void setDesImp(String desImp) {
		this.desImp = desImp;
	}
	
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	public String getProgSogg() {
		return progSogg;
	}
	
	public void setProgSogg(String progSogg) {
		this.progSogg = progSogg;
	}
	
	public String getCodCup() {
		return codCup;
	}
	
	public void setCodCup(String codCup) {
		this.codCup = codCup;
	}
	
	public String getDesCup() {
		return desCup;
	}
	
	public void setDesCup(String desCup) {
		this.desCup = desCup;
	}
	
	public String getCig() {
		return cig;
	}
	
	public void setCig(String cig) {
		this.cig = cig;
	}
	
	public String getDesCig() {
		return desCig;
	}
	
	public void setDesCig(String desCig) {
		this.desCig = desCig;
	}
	
	public String getMotivoNoCig() {
		return motivoNoCig;
	}
	
	public void setMotivoNoCig(String motivoNoCig) {
		this.motivoNoCig = motivoNoCig;
	}
	
	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Integer getAnnoEse() {
		return annoEse;
	}
	
	public void setAnnoEse(Integer annoEse) {
		this.annoEse = annoEse;
	}
	
	public Integer getProvenImporto() {
		return provenImporto;
	}
	
	public void setProvenImporto(Integer provenImporto) {
		this.provenImporto = provenImporto;
	}
	
	public String getCodiceOpera() {
		return codiceOpera;
	}

	public void setCodiceOpera(String codiceOpera) {
		this.codiceOpera = codiceOpera;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspRichiesteContabiliRequest [token=" + token + ", rigaAttim=" + rigaAttim + ", es=" + es
				+ ", progkeyvb=" + progkeyvb + ", tipoImp=" + tipoImp + ", desImp=" + desImp + ", ragioneSociale="
				+ ragioneSociale + ", progSogg=" + progSogg + ", codCup=" + codCup + ", desCup=" + desCup + ", cig="
				+ cig + ", desCig=" + desCig + ", motivoNoCig=" + motivoNoCig + ", importo=" + importo + ", annoEse="
				+ annoEse + ", provenImporto=" + provenImporto + "]";
	}

}
