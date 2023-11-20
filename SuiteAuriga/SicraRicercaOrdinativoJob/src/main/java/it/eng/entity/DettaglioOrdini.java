/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class DettaglioOrdini  implements java.io.Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idSpAoo;
	private String codApplOwner;
    private String codIstApplOwner;
    private BigDecimal idDoc;
    private Date tsIns;
    private Timestamp pDataIni;
	private Timestamp pDataFin;
    private String numOrdine;
    private String dataOrdine;
    private String ordTrasmNSO;
	private String displayFileName;
	private String uri;
	private String ordSellerDes;
	private ArrayList<DmtDettOrdini> dmtDettOrdini;
    
	public int getIdSpAoo() {
		return idSpAoo;
	}
	public void setIdSpAoo(int idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	public String getCodApplOwner() {
		return codApplOwner;
	}
	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}
	public String getCodIstApplOwner() {
		return codIstApplOwner;
	}
	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	public Timestamp getpDataIni() {
		return pDataIni;
	}
	public void setpDataIni(Timestamp pDataIni) {
		this.pDataIni = pDataIni;
	}
	public Timestamp getpDataFin() {
		return pDataFin;
	}
	public void setpDataFin(Timestamp pDataFin) {
		this.pDataFin = pDataFin;
	}
	public String getNumOrdine() {
		return numOrdine;
	}
	public void setNumOrdine(String numOrdine) {
		this.numOrdine = numOrdine;
	}
	public String getDisplayFileName() {
		return displayFileName;
	}
	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getOrdSellerDes() {
		return ordSellerDes;
	}
	public void setOrdSellerDes(String ordSellerDes) {
		this.ordSellerDes = ordSellerDes;
	}
	public String getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(String dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	public String getOrdTrasmNSO() {
		return ordTrasmNSO;
	}
	public void setOrdTrasmNSO(String ordTrasmNSO) {
		this.ordTrasmNSO = ordTrasmNSO;
	}
	public ArrayList<DmtDettOrdini> getDmtDettOrdini() {
		return dmtDettOrdini;
	}
	public void setDmtDettOrdini(ArrayList<DmtDettOrdini> dmtDettOrdini) {
		this.dmtDettOrdini = dmtDettOrdini;
	}
	@Override
	public String toString() {
		return "DettaglioOrdini [idSpAoo=" + idSpAoo + ", codApplOwner=" + codApplOwner + ", codIstApplOwner="
				+ codIstApplOwner + ", idDoc=" + idDoc + ", tsIns=" + tsIns + ", pDataIni=" + pDataIni + ", pDataFin="
				+ pDataFin + ", numOrdine=" + numOrdine + ", dataOrdine=" + dataOrdine + ", ordTrasmNSO=" + ordTrasmNSO
				+ ", displayFileName=" + displayFileName + ", uri=" + uri + ", ordSellerDes=" + ordSellerDes
				+ ", dmtDettOrdini=" + dmtDettOrdini + "]";
	}
    
	
	
	
}	
