/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfTrovaattflussowfBean")
public class DmpkWfTrovaattflussowfBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_TROVAATTFLUSSOWF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String citypeflussowfio;
	private java.lang.String nometypeflussowfin;
	private java.lang.Integer flglistaistanzeattin;
	private java.lang.String strinnomeattin;
	private java.lang.String ciattivitain;
	private java.lang.String strinnomefasein;
	private java.lang.String cifasein;
	private java.math.BigDecimal flgsolovldin;
	private java.lang.String tsvldin;
	private java.lang.String ciattivitatoaddin;
	private java.lang.String nomeattivitatoaddin;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgaggiungifasiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getCitypeflussowfio(){return citypeflussowfio;}
    public java.lang.String getNometypeflussowfin(){return nometypeflussowfin;}
    public java.lang.Integer getFlglistaistanzeattin(){return flglistaistanzeattin;}
    public java.lang.String getStrinnomeattin(){return strinnomeattin;}
    public java.lang.String getCiattivitain(){return ciattivitain;}
    public java.lang.String getStrinnomefasein(){return strinnomefasein;}
    public java.lang.String getCifasein(){return cifasein;}
    public java.math.BigDecimal getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsvldin(){return tsvldin;}
    public java.lang.String getCiattivitatoaddin(){return ciattivitatoaddin;}
    public java.lang.String getNomeattivitatoaddin(){return nomeattivitatoaddin;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgaggiungifasiin(){return flgaggiungifasiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setCitypeflussowfio(java.lang.String value){this.citypeflussowfio=value;}
    public void setNometypeflussowfin(java.lang.String value){this.nometypeflussowfin=value;}
    public void setFlglistaistanzeattin(java.lang.Integer value){this.flglistaistanzeattin=value;}
    public void setStrinnomeattin(java.lang.String value){this.strinnomeattin=value;}
    public void setCiattivitain(java.lang.String value){this.ciattivitain=value;}
    public void setStrinnomefasein(java.lang.String value){this.strinnomefasein=value;}
    public void setCifasein(java.lang.String value){this.cifasein=value;}
    public void setFlgsolovldin(java.math.BigDecimal value){this.flgsolovldin=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    public void setCiattivitatoaddin(java.lang.String value){this.ciattivitatoaddin=value;}
    public void setNomeattivitatoaddin(java.lang.String value){this.nomeattivitatoaddin=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgaggiungifasiin(java.lang.Integer value){this.flgaggiungifasiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    