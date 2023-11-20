/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspGetcontenutisezBean")
public class DmpkAmmTraspGetcontenutisezBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_GETCONTENUTISEZ";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal idsezionein;
	private java.lang.Integer flgpresentirifnormout;
	private java.lang.String rifnormativiout;
	private java.lang.Integer flgpresenteheaderout;
	private java.lang.String headerout;
	private java.lang.String datiaggout;
	private java.math.BigDecimal nrocontenutiout;
	private java.lang.String listacontenutiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgcontenutifuoripubblin;
	private java.lang.String datapubblstorichedain;
	private java.lang.String datapubblstoricheain;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgincludipubbleliminatein;
	private java.lang.String matchwordlistin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIdsezionein(){return idsezionein;}
    public java.lang.Integer getFlgpresentirifnormout(){return flgpresentirifnormout;}
    public java.lang.String getRifnormativiout(){return rifnormativiout;}
    public java.lang.Integer getFlgpresenteheaderout(){return flgpresenteheaderout;}
    public java.lang.String getHeaderout(){return headerout;}
    public java.lang.String getDatiaggout(){return datiaggout;}
    public java.math.BigDecimal getNrocontenutiout(){return nrocontenutiout;}
    public java.lang.String getListacontenutiout(){return listacontenutiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgcontenutifuoripubblin(){return flgcontenutifuoripubblin;}
    public java.lang.String getDatapubblstorichedain(){return datapubblstorichedain;}
    public java.lang.String getDatapubblstoricheain(){return datapubblstoricheain;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgincludipubbleliminatein(){return flgincludipubbleliminatein;}
    public java.lang.String getMatchwordlistin(){return matchwordlistin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIdsezionein(java.math.BigDecimal value){this.idsezionein=value;}
    public void setFlgpresentirifnormout(java.lang.Integer value){this.flgpresentirifnormout=value;}
    public void setRifnormativiout(java.lang.String value){this.rifnormativiout=value;}
    public void setFlgpresenteheaderout(java.lang.Integer value){this.flgpresenteheaderout=value;}
    public void setHeaderout(java.lang.String value){this.headerout=value;}
    public void setDatiaggout(java.lang.String value){this.datiaggout=value;}
    public void setNrocontenutiout(java.math.BigDecimal value){this.nrocontenutiout=value;}
    public void setListacontenutiout(java.lang.String value){this.listacontenutiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgcontenutifuoripubblin(java.lang.Integer value){this.flgcontenutifuoripubblin=value;}
    public void setDatapubblstorichedain(java.lang.String value){this.datapubblstorichedain=value;}
    public void setDatapubblstoricheain(java.lang.String value){this.datapubblstoricheain=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgincludipubbleliminatein(java.lang.Integer value){this.flgincludipubbleliminatein=value;}
    public void setMatchwordlistin(java.lang.String value){this.matchwordlistin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    