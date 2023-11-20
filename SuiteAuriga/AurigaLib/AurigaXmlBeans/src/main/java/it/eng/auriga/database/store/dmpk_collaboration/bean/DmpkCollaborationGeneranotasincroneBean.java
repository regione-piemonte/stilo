/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCollaborationGeneranotasincroneBean")
public class DmpkCollaborationGeneranotasincroneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_COLLABORATION_GENERANOTASINCRONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	public Integer getParametro_1() { return 1; }
    
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    