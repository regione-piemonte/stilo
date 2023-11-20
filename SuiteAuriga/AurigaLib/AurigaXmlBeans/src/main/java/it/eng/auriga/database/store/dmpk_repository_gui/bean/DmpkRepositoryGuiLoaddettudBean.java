/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiLoaddettudBean")
public class DmpkRepositoryGuiLoaddettudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_LOADDETTUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudio;
	private java.math.BigDecimal iddocprimarioout;
	private java.lang.String listaregnumout;
	private java.lang.String stringaregnumout;
	private java.math.BigDecimal idtipodocprimarioout;
	private java.lang.String nometipodocprimarioout;
	private java.lang.Integer nrolastvisibleverprimarioout;
	private java.lang.Integer nrolastverprimarioout;
	private java.lang.String displayfilenameprimarioout;
	private java.lang.Integer flgpubblfileprimarioout;
	private java.lang.Integer flgdadigitfileprimarioout;
	private java.lang.String notefileprimarioout;
	private java.lang.String abilitazionifileprimarioout;
	private java.lang.String desoggettoprimarioout;
	private java.lang.String flgacqprodout;
	private java.lang.String tsarrivostesuraout;
	private java.lang.String flgtipoprovout;
	private java.lang.String mittentiout;
	private java.lang.Integer flgmovimentataout;
	private java.lang.String estremisoggincaricoaout;
	private java.lang.String destinatariassegnatariout;
	private java.lang.String altrisoggettiesterniout;
	private java.lang.String docnonprimariout;
	private java.lang.String contenitoriout;
	private java.lang.String parolechiaveprimarioout;
	private java.lang.String estensoriout;
	private java.lang.String listarelazionivsudout;
	private java.lang.String codstatoout;
	private java.lang.String desstatoout;
	private java.lang.String noteudout;
	private java.lang.String abilitazioniazioniout;
	private java.lang.String infosulockout;
	private java.math.BigDecimal idprocessout;
	private java.lang.Integer flgprocguidatodawfout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.Integer nomeudcreatoinautomaticoout;
	private java.lang.String nomeunitadocout;
	private java.lang.String statoconsprimarioout;
	private java.math.BigDecimal flginvioinconservazioneout;
	private java.math.BigDecimal flgurgenzainvioinconservout;
	private java.lang.String idinconservazioneout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudio(){return idudio;}
    public java.math.BigDecimal getIddocprimarioout(){return iddocprimarioout;}
    public java.lang.String getListaregnumout(){return listaregnumout;}
    public java.lang.String getStringaregnumout(){return stringaregnumout;}
    public java.math.BigDecimal getIdtipodocprimarioout(){return idtipodocprimarioout;}
    public java.lang.String getNometipodocprimarioout(){return nometipodocprimarioout;}
    public java.lang.Integer getNrolastvisibleverprimarioout(){return nrolastvisibleverprimarioout;}
    public java.lang.Integer getNrolastverprimarioout(){return nrolastverprimarioout;}
    public java.lang.String getDisplayfilenameprimarioout(){return displayfilenameprimarioout;}
    public java.lang.Integer getFlgpubblfileprimarioout(){return flgpubblfileprimarioout;}
    public java.lang.Integer getFlgdadigitfileprimarioout(){return flgdadigitfileprimarioout;}
    public java.lang.String getNotefileprimarioout(){return notefileprimarioout;}
    public java.lang.String getAbilitazionifileprimarioout(){return abilitazionifileprimarioout;}
    public java.lang.String getDesoggettoprimarioout(){return desoggettoprimarioout;}
    public java.lang.String getFlgacqprodout(){return flgacqprodout;}
    public java.lang.String getTsarrivostesuraout(){return tsarrivostesuraout;}
    public java.lang.String getFlgtipoprovout(){return flgtipoprovout;}
    public java.lang.String getMittentiout(){return mittentiout;}
    public java.lang.Integer getFlgmovimentataout(){return flgmovimentataout;}
    public java.lang.String getEstremisoggincaricoaout(){return estremisoggincaricoaout;}
    public java.lang.String getDestinatariassegnatariout(){return destinatariassegnatariout;}
    public java.lang.String getAltrisoggettiesterniout(){return altrisoggettiesterniout;}
    public java.lang.String getDocnonprimariout(){return docnonprimariout;}
    public java.lang.String getContenitoriout(){return contenitoriout;}
    public java.lang.String getParolechiaveprimarioout(){return parolechiaveprimarioout;}
    public java.lang.String getEstensoriout(){return estensoriout;}
    public java.lang.String getListarelazionivsudout(){return listarelazionivsudout;}
    public java.lang.String getCodstatoout(){return codstatoout;}
    public java.lang.String getDesstatoout(){return desstatoout;}
    public java.lang.String getNoteudout(){return noteudout;}
    public java.lang.String getAbilitazioniazioniout(){return abilitazioniazioniout;}
    public java.lang.String getInfosulockout(){return infosulockout;}
    public java.math.BigDecimal getIdprocessout(){return idprocessout;}
    public java.lang.Integer getFlgprocguidatodawfout(){return flgprocguidatodawfout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.Integer getNomeudcreatoinautomaticoout(){return nomeudcreatoinautomaticoout;}
    public java.lang.String getNomeunitadocout(){return nomeunitadocout;}
    public java.lang.String getStatoconsprimarioout(){return statoconsprimarioout;}
    public java.math.BigDecimal getFlginvioinconservazioneout(){return flginvioinconservazioneout;}
    public java.math.BigDecimal getFlgurgenzainvioinconservout(){return flgurgenzainvioinconservout;}
    public java.lang.String getIdinconservazioneout(){return idinconservazioneout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudio(java.math.BigDecimal value){this.idudio=value;}
    public void setIddocprimarioout(java.math.BigDecimal value){this.iddocprimarioout=value;}
    public void setListaregnumout(java.lang.String value){this.listaregnumout=value;}
    public void setStringaregnumout(java.lang.String value){this.stringaregnumout=value;}
    public void setIdtipodocprimarioout(java.math.BigDecimal value){this.idtipodocprimarioout=value;}
    public void setNometipodocprimarioout(java.lang.String value){this.nometipodocprimarioout=value;}
    public void setNrolastvisibleverprimarioout(java.lang.Integer value){this.nrolastvisibleverprimarioout=value;}
    public void setNrolastverprimarioout(java.lang.Integer value){this.nrolastverprimarioout=value;}
    public void setDisplayfilenameprimarioout(java.lang.String value){this.displayfilenameprimarioout=value;}
    public void setFlgpubblfileprimarioout(java.lang.Integer value){this.flgpubblfileprimarioout=value;}
    public void setFlgdadigitfileprimarioout(java.lang.Integer value){this.flgdadigitfileprimarioout=value;}
    public void setNotefileprimarioout(java.lang.String value){this.notefileprimarioout=value;}
    public void setAbilitazionifileprimarioout(java.lang.String value){this.abilitazionifileprimarioout=value;}
    public void setDesoggettoprimarioout(java.lang.String value){this.desoggettoprimarioout=value;}
    public void setFlgacqprodout(java.lang.String value){this.flgacqprodout=value;}
    public void setTsarrivostesuraout(java.lang.String value){this.tsarrivostesuraout=value;}
    public void setFlgtipoprovout(java.lang.String value){this.flgtipoprovout=value;}
    public void setMittentiout(java.lang.String value){this.mittentiout=value;}
    public void setFlgmovimentataout(java.lang.Integer value){this.flgmovimentataout=value;}
    public void setEstremisoggincaricoaout(java.lang.String value){this.estremisoggincaricoaout=value;}
    public void setDestinatariassegnatariout(java.lang.String value){this.destinatariassegnatariout=value;}
    public void setAltrisoggettiesterniout(java.lang.String value){this.altrisoggettiesterniout=value;}
    public void setDocnonprimariout(java.lang.String value){this.docnonprimariout=value;}
    public void setContenitoriout(java.lang.String value){this.contenitoriout=value;}
    public void setParolechiaveprimarioout(java.lang.String value){this.parolechiaveprimarioout=value;}
    public void setEstensoriout(java.lang.String value){this.estensoriout=value;}
    public void setListarelazionivsudout(java.lang.String value){this.listarelazionivsudout=value;}
    public void setCodstatoout(java.lang.String value){this.codstatoout=value;}
    public void setDesstatoout(java.lang.String value){this.desstatoout=value;}
    public void setNoteudout(java.lang.String value){this.noteudout=value;}
    public void setAbilitazioniazioniout(java.lang.String value){this.abilitazioniazioniout=value;}
    public void setInfosulockout(java.lang.String value){this.infosulockout=value;}
    public void setIdprocessout(java.math.BigDecimal value){this.idprocessout=value;}
    public void setFlgprocguidatodawfout(java.lang.Integer value){this.flgprocguidatodawfout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setNomeudcreatoinautomaticoout(java.lang.Integer value){this.nomeudcreatoinautomaticoout=value;}
    public void setNomeunitadocout(java.lang.String value){this.nomeunitadocout=value;}
    public void setStatoconsprimarioout(java.lang.String value){this.statoconsprimarioout=value;}
    public void setFlginvioinconservazioneout(java.math.BigDecimal value){this.flginvioinconservazioneout=value;}
    public void setFlgurgenzainvioinconservout(java.math.BigDecimal value){this.flgurgenzainvioinconservout=value;}
    public void setIdinconservazioneout(java.lang.String value){this.idinconservazioneout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    