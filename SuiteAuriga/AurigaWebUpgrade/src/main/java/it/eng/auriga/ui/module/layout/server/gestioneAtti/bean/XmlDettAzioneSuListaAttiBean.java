/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.KeyValueBean;

public class XmlDettAzioneSuListaAttiBean {
	
	@XmlVariabile(nome="Nota", tipo=TipoVariabile.SEMPLICE)
	private String nota;
	
	@XmlVariabile(nome="@Assessori", tipo=TipoVariabile.LISTA)
	private List<AssegnazioneAssessoreXmlBean> listaAssessori;	
	
	@XmlVariabile(nome="@Tag", tipo=TipoVariabile.LISTA)
	private List<KeyValueBean> listaTag;
	
	@XmlVariabile(nome="BloccoDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataBloccoDal;
	
	@XmlVariabile(nome="Motivo", tipo=TipoVariabile.SEMPLICE)
	private String motivo;
	
	@XmlVariabile(nome="Osservazioni", tipo=TipoVariabile.SEMPLICE)
	private String osservazioni;

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public List<AssegnazioneAssessoreXmlBean> getListaAssessori() {
		return listaAssessori;
	}

	public void setListaAssessori(List<AssegnazioneAssessoreXmlBean> listaAssessori) {
		this.listaAssessori = listaAssessori;
	}

	public List<KeyValueBean> getListaTag() {
		return listaTag;
	}

	public void setListaTag(List<KeyValueBean> listaTag) {
		this.listaTag = listaTag;
	}

	public Date getDataBloccoDal() {
		return dataBloccoDal;
	}

	public void setDataBloccoDal(Date dataBloccoDal) {
		this.dataBloccoDal = dataBloccoDal;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getOsservazioni() {
		return osservazioni;
	}

	public void setOsservazioni(String osservazioni) {
		this.osservazioni = osservazioni;
	}
	
}
