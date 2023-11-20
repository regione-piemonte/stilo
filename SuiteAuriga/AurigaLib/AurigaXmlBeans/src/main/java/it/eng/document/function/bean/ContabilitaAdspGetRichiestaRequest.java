/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetRichiestaRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codiceFiscaleOp;
	private Integer progAtto;
	private Integer idraggrupp;
	private Integer rigaAttim;
	private String es;
	private String capitoloParte1;
	private String capitoloParte2;
	private String descrizioneVoce;
	private Integer progkeyvb;
	private String cup;
	private String cig;
	private String annoEse;
	private String nImpeg;
	private Float imImpPren;
	private String desImp;
	private Integer progimpacc;
	private String ragioneSociale;
	private String codiceFiscale;
	private String partitaIva;
	private String aliasAssRic;
	private String progSogg;
	private String liv1pf;
	private String liv2pf;
	private String liv3pf;
	private String liv4pf;
	private String liv5pf;
	private String missione;
	private String programma;
	private String cofog1;
	private String cofog2;
	private String datainserImp;
	private Float imRicOri;
	
	public String getCodiceFiscaleOp() {
		return codiceFiscaleOp;
	}
	
	public void setCodiceFiscaleOp(String codiceFiscaleOp) {
		this.codiceFiscaleOp = codiceFiscaleOp;
	}
	
	public Integer getProgAtto() {
		return progAtto;
	}
	
	public void setProgAtto(Integer progAtto) {
		this.progAtto = progAtto;
	}
	
	public Integer getIdraggrupp() {
		return idraggrupp;
	}
	
	public void setIdraggrupp(Integer idraggrupp) {
		this.idraggrupp = idraggrupp;
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
	
	public String getCapitoloParte1() {
		return capitoloParte1;
	}
	
	public void setCapitoloParte1(String capitoloParte1) {
		this.capitoloParte1 = capitoloParte1;
	}
	
	public String getCapitoloParte2() {
		return capitoloParte2;
	}
	
	public void setCapitoloParte2(String capitoloParte2) {
		this.capitoloParte2 = capitoloParte2;
	}
	
	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}
	
	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
	}
	
	public Integer getProgkeyvb() {
		return progkeyvb;
	}
	
	public void setProgkeyvb(Integer progkeyvb) {
		this.progkeyvb = progkeyvb;
	}
	
	public String getCup() {
		return cup;
	}
	
	public void setCup(String cup) {
		this.cup = cup;
	}
	
	public String getCig() {
		return cig;
	}
	
	public void setCig(String cig) {
		this.cig = cig;
	}
	
	public String getAnnoEse() {
		return annoEse;
	}
	
	public void setAnnoEse(String annoEse) {
		this.annoEse = annoEse;
	}
	
	public String getnImpeg() {
		return nImpeg;
	}
	
	public void setnImpeg(String nImpeg) {
		this.nImpeg = nImpeg;
	}
	
	public Float getImImpPren() {
		return imImpPren;
	}
	
	public void setImImpPren(Float imImpPren) {
		this.imImpPren = imImpPren;
	}
	
	public String getDesImp() {
		return desImp;
	}
	
	public void setDesImp(String desImp) {
		this.desImp = desImp;
	}
	
	public Integer getProgimpacc() {
		return progimpacc;
	}
	
	public void setProgimpacc(Integer progimpacc) {
		this.progimpacc = progimpacc;
	}
	
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	public String getAliasAssRic() {
		return aliasAssRic;
	}
	
	public void setAliasAssRic(String aliasAssRic) {
		this.aliasAssRic = aliasAssRic;
	}
	
	public String getProgSogg() {
		return progSogg;
	}
	
	public void setProgSogg(String progSogg) {
		this.progSogg = progSogg;
	}
	
	public String getLiv1pf() {
		return liv1pf;
	}
	
	public void setLiv1pf(String liv1pf) {
		this.liv1pf = liv1pf;
	}
	
	public String getLiv2pf() {
		return liv2pf;
	}
	
	public void setLiv2pf(String liv2pf) {
		this.liv2pf = liv2pf;
	}
	
	public String getLiv3pf() {
		return liv3pf;
	}
	
	public void setLiv3pf(String liv3pf) {
		this.liv3pf = liv3pf;
	}
	
	public String getLiv4pf() {
		return liv4pf;
	}
	
	public void setLiv4pf(String liv4pf) {
		this.liv4pf = liv4pf;
	}
	
	public String getLiv5pf() {
		return liv5pf;
	}
	
	public void setLiv5pf(String liv5pf) {
		this.liv5pf = liv5pf;
	}
	
	public String getMissione() {
		return missione;
	}
	
	public void setMissione(String missione) {
		this.missione = missione;
	}
	
	public String getProgramma() {
		return programma;
	}
	
	public void setProgramma(String programma) {
		this.programma = programma;
	}
	
	public String getCofog1() {
		return cofog1;
	}
	
	public void setCofog1(String cofog1) {
		this.cofog1 = cofog1;
	}
	
	public String getCofog2() {
		return cofog2;
	}
	
	public void setCofog2(String cofog2) {
		this.cofog2 = cofog2;
	}
	
	public String getDatainserImp() {
		return datainserImp;
	}
	
	public void setDatainserImp(String datainserImp) {
		this.datainserImp = datainserImp;
	}
	
	public Float getImRicOri() {
		return imRicOri;
	}
	
	public void setImRicOri(Float imRicOri) {
		this.imRicOri = imRicOri;
	}
	
	@Override
	public String toString() {
		return "ContabilitaAdspGetRichiestaRequest [codiceFiscaleOp=" + codiceFiscaleOp + ", progAtto=" + progAtto
				+ ", idraggrupp=" + idraggrupp + ", rigaAttim=" + rigaAttim + ", es=" + es + ", capitoloParte1="
				+ capitoloParte1 + ", capitoloParte2=" + capitoloParte2 + ", descrizioneVoce=" + descrizioneVoce
				+ ", progkeyvb=" + progkeyvb + ", cup=" + cup + ", cig=" + cig + ", annoEse=" + annoEse + ", nImpeg="
				+ nImpeg + ", imImpPren=" + imImpPren + ", desImp=" + desImp + ", progimpacc=" + progimpacc
				+ ", ragioneSociale=" + ragioneSociale + ", codiceFiscale=" + codiceFiscale + ", partitaIva="
				+ partitaIva + ", aliasAssRic=" + aliasAssRic + ", progSogg=" + progSogg + ", liv1pf=" + liv1pf
				+ ", liv2pf=" + liv2pf + ", liv3pf=" + liv3pf + ", liv4pf=" + liv4pf + ", liv5pf=" + liv5pf
				+ ", missione=" + missione + ", programma=" + programma + ", cofog1=" + cofog1 + ", cofog2=" + cofog2
				+ ", datainserImp=" + datainserImp + ", imRicOri=" + imRicOri + "]";
	}
	
}
