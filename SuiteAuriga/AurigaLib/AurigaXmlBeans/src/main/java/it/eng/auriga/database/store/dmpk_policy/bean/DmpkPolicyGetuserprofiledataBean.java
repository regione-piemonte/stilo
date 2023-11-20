/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyGetuserprofiledataBean")
public class DmpkPolicyGetuserprofiledataBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_GETUSERPROFILEDATA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioin;
	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idprofiloout;
	private java.lang.String flgvisibindipaclout;
	private java.lang.String flgvisibindipuserabilout;
	private java.lang.Integer maxlivriservatezzaout;
	private java.lang.String flggestindipaclout;
	private java.lang.String flggestindipuserabilout;
	public Integer getParametro_1() { return 1; }
    public java.lang.Integer getFlgtpdominioin(){return flgtpdominioin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdprofiloout(){return idprofiloout;}
    public java.lang.String getFlgvisibindipaclout(){return flgvisibindipaclout;}
    public java.lang.String getFlgvisibindipuserabilout(){return flgvisibindipuserabilout;}
    public java.lang.Integer getMaxlivriservatezzaout(){return maxlivriservatezzaout;}
    public java.lang.String getFlggestindipaclout(){return flggestindipaclout;}
    public java.lang.String getFlggestindipuserabilout(){return flggestindipuserabilout;}
    
    public void setFlgtpdominioin(java.lang.Integer value){this.flgtpdominioin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdprofiloout(java.math.BigDecimal value){this.idprofiloout=value;}
    public void setFlgvisibindipaclout(java.lang.String value){this.flgvisibindipaclout=value;}
    public void setFlgvisibindipuserabilout(java.lang.String value){this.flgvisibindipuserabilout=value;}
    public void setMaxlivriservatezzaout(java.lang.Integer value){this.maxlivriservatezzaout=value;}
    public void setFlggestindipaclout(java.lang.String value){this.flggestindipaclout=value;}
    public void setFlggestindipuserabilout(java.lang.String value){this.flggestindipuserabilout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    