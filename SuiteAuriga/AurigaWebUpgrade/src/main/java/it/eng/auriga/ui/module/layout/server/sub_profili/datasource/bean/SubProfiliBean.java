/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;																					

public class SubProfiliBean extends SubProfiliXmlBean {
  
  private String ciProvGruppoPriv;
  private Integer flglockedPriv;
  private List<DettGruppoPrivPrivilegiXmlBean> privilegiXml;
  

  private List<PrivilegiFunzioneBean>  listaFunzionalitaSistema;
  private List<PrivilegiFunzioneBean>  listaTipoProcessoProcedimento;
  private List<PrivilegiFunzioneBean>  listaTipoDocumento;
  private List<PrivilegiFunzioneBean>  listaTipoFolder;
  private List<PrivilegiFunzioneBean>  listaClassificazione;
  private List<PrivilegiFunzioneBean>  listaTipoRegistrazione;
  
  public String getCiProvGruppoPriv() {
	return ciProvGruppoPriv;
  }
  
  public void setCiProvGruppoPriv(String ciProvGruppoPriv) {
	this.ciProvGruppoPriv = ciProvGruppoPriv;
  }
  
  public Integer getFlglockedPriv() {
	return flglockedPriv;
  }
  
  public void setFlglockedPriv(Integer flglockedPriv) {
	this.flglockedPriv = flglockedPriv;
  }
  
  public List<DettGruppoPrivPrivilegiXmlBean> getPrivilegiXml() {
	return privilegiXml;
  }
  
  public void setPrivilegiXml(List<DettGruppoPrivPrivilegiXmlBean> privilegiXml) {
	this.privilegiXml = privilegiXml;
  }
  
  public List<PrivilegiFunzioneBean> getListaFunzionalitaSistema() {
	return listaFunzionalitaSistema;
  }
  
  public void setListaFunzionalitaSistema(List<PrivilegiFunzioneBean> listaFunzionalitaSistema) {
	this.listaFunzionalitaSistema = listaFunzionalitaSistema;
  }
  
  public List<PrivilegiFunzioneBean> getListaTipoProcessoProcedimento() {
	return listaTipoProcessoProcedimento;
  }
  
  public void setListaTipoProcessoProcedimento(List<PrivilegiFunzioneBean> listaTipoProcessoProcedimento) {
	this.listaTipoProcessoProcedimento = listaTipoProcessoProcedimento;
  }
  
  public List<PrivilegiFunzioneBean> getListaTipoDocumento() {
	return listaTipoDocumento;
  }
  
  public void setListaTipoDocumento(List<PrivilegiFunzioneBean> listaTipoDocumento) {
	this.listaTipoDocumento = listaTipoDocumento;
  }
  
  public List<PrivilegiFunzioneBean> getListaTipoFolder() {
	return listaTipoFolder;
  }
  
  public void setListaTipoFolder(List<PrivilegiFunzioneBean> listaTipoFolder) {
	this.listaTipoFolder = listaTipoFolder;
  }
  
  public List<PrivilegiFunzioneBean> getListaClassificazione() {
	return listaClassificazione;
  }
  
  public void setListaClassificazione(List<PrivilegiFunzioneBean> listaClassificazione) {
	this.listaClassificazione = listaClassificazione;
  }
  
  public List<PrivilegiFunzioneBean> getListaTipoRegistrazione() {
	return listaTipoRegistrazione;
  }
  
  public void setListaTipoRegistrazione(List<PrivilegiFunzioneBean> listaTipoRegistrazione) {
	this.listaTipoRegistrazione = listaTipoRegistrazione;
  }
    
}