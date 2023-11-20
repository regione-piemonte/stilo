/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.KeyValueBean;

public class OpzUOInDettAttoXmlBean {
	
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_RESP_UFF.Visibilita", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoRespUffVisibilita;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_RESP_UFF.Editabile", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoRespUffEditabile;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_RESP_UFF.ValoreDefault", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoRespUffValoreDefault;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_RESP_UFF.ValoriSelectScrivanie", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> flgVistoRespUffValoriSelectScrivanie;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_RESP_UFF.ValoreDefaultSelectScrivanie", tipo = TipoVariabile.SEMPLICE)
	private String flgVistoRespUffValoreDefaultSelectScrivanie;	
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_1.Visibilita", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup1Visibilita;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_1.Editabile", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup1Editabile;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_1.ValoreDefault", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup1ValoreDefault;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_1.ValoriSelectScrivanie", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> flgVistoDirSup1ValoriSelectScrivanie;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_1.ValoreDefaultSelectScrivanie", tipo = TipoVariabile.SEMPLICE)
	private String flgVistoDirSup1ValoreDefaultSelectScrivanie;	
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_2.Visibilita", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup2Visibilita;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_2.Editabile", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup2Editabile;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_2.ValoreDefault", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup2ValoreDefault;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_2.ValoriSelectScrivanie", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> flgVistoDirSup2ValoriSelectScrivanie;
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_2.ValoreDefaultSelectScrivanie", tipo = TipoVariabile.SEMPLICE)
	private String flgVistoDirSup2ValoreDefaultSelectScrivanie;
	
	public Flag getFlgVistoRespUffVisibilita() {
		return flgVistoRespUffVisibilita;
	}
	public void setFlgVistoRespUffVisibilita(Flag flgVistoRespUffVisibilita) {
		this.flgVistoRespUffVisibilita = flgVistoRespUffVisibilita;
	}
	public Flag getFlgVistoRespUffEditabile() {
		return flgVistoRespUffEditabile;
	}
	public void setFlgVistoRespUffEditabile(Flag flgVistoRespUffEditabile) {
		this.flgVistoRespUffEditabile = flgVistoRespUffEditabile;
	}
	public Flag getFlgVistoRespUffValoreDefault() {
		return flgVistoRespUffValoreDefault;
	}
	public void setFlgVistoRespUffValoreDefault(Flag flgVistoRespUffValoreDefault) {
		this.flgVistoRespUffValoreDefault = flgVistoRespUffValoreDefault;
	}
	public List<KeyValueBean> getFlgVistoRespUffValoriSelectScrivanie() {
		return flgVistoRespUffValoriSelectScrivanie;
	}
	public void setFlgVistoRespUffValoriSelectScrivanie(List<KeyValueBean> flgVistoRespUffValoriSelectScrivanie) {
		this.flgVistoRespUffValoriSelectScrivanie = flgVistoRespUffValoriSelectScrivanie;
	}
	public String getFlgVistoRespUffValoreDefaultSelectScrivanie() {
		return flgVistoRespUffValoreDefaultSelectScrivanie;
	}
	public void setFlgVistoRespUffValoreDefaultSelectScrivanie(String flgVistoRespUffValoreDefaultSelectScrivanie) {
		this.flgVistoRespUffValoreDefaultSelectScrivanie = flgVistoRespUffValoreDefaultSelectScrivanie;
	}
	public Flag getFlgVistoDirSup1Visibilita() {
		return flgVistoDirSup1Visibilita;
	}
	public void setFlgVistoDirSup1Visibilita(Flag flgVistoDirSup1Visibilita) {
		this.flgVistoDirSup1Visibilita = flgVistoDirSup1Visibilita;
	}
	public Flag getFlgVistoDirSup1Editabile() {
		return flgVistoDirSup1Editabile;
	}
	public void setFlgVistoDirSup1Editabile(Flag flgVistoDirSup1Editabile) {
		this.flgVistoDirSup1Editabile = flgVistoDirSup1Editabile;
	}
	public Flag getFlgVistoDirSup1ValoreDefault() {
		return flgVistoDirSup1ValoreDefault;
	}
	public void setFlgVistoDirSup1ValoreDefault(Flag flgVistoDirSup1ValoreDefault) {
		this.flgVistoDirSup1ValoreDefault = flgVistoDirSup1ValoreDefault;
	}
	public List<KeyValueBean> getFlgVistoDirSup1ValoriSelectScrivanie() {
		return flgVistoDirSup1ValoriSelectScrivanie;
	}
	public void setFlgVistoDirSup1ValoriSelectScrivanie(List<KeyValueBean> flgVistoDirSup1ValoriSelectScrivanie) {
		this.flgVistoDirSup1ValoriSelectScrivanie = flgVistoDirSup1ValoriSelectScrivanie;
	}	
	public String getFlgVistoDirSup1ValoreDefaultSelectScrivanie() {
		return flgVistoDirSup1ValoreDefaultSelectScrivanie;
	}
	public void setFlgVistoDirSup1ValoreDefaultSelectScrivanie(String flgVistoDirSup1ValoreDefaultSelectScrivanie) {
		this.flgVistoDirSup1ValoreDefaultSelectScrivanie = flgVistoDirSup1ValoreDefaultSelectScrivanie;
	}
	public Flag getFlgVistoDirSup2Visibilita() {
		return flgVistoDirSup2Visibilita;
	}
	public void setFlgVistoDirSup2Visibilita(Flag flgVistoDirSup2Visibilita) {
		this.flgVistoDirSup2Visibilita = flgVistoDirSup2Visibilita;
	}
	public Flag getFlgVistoDirSup2Editabile() {
		return flgVistoDirSup2Editabile;
	}
	public void setFlgVistoDirSup2Editabile(Flag flgVistoDirSup2Editabile) {
		this.flgVistoDirSup2Editabile = flgVistoDirSup2Editabile;
	}
	public Flag getFlgVistoDirSup2ValoreDefault() {
		return flgVistoDirSup2ValoreDefault;
	}
	public void setFlgVistoDirSup2ValoreDefault(Flag flgVistoDirSup2ValoreDefault) {
		this.flgVistoDirSup2ValoreDefault = flgVistoDirSup2ValoreDefault;
	}
	public List<KeyValueBean> getFlgVistoDirSup2ValoriSelectScrivanie() {
		return flgVistoDirSup2ValoriSelectScrivanie;
	}
	public void setFlgVistoDirSup2ValoriSelectScrivanie(List<KeyValueBean> flgVistoDirSup2ValoriSelectScrivanie) {
		this.flgVistoDirSup2ValoriSelectScrivanie = flgVistoDirSup2ValoriSelectScrivanie;
	}
	public String getFlgVistoDirSup2ValoreDefaultSelectScrivanie() {
		return flgVistoDirSup2ValoreDefaultSelectScrivanie;
	}
	public void setFlgVistoDirSup2ValoreDefaultSelectScrivanie(String flgVistoDirSup2ValoreDefaultSelectScrivanie) {
		this.flgVistoDirSup2ValoreDefaultSelectScrivanie = flgVistoDirSup2ValoreDefaultSelectScrivanie;
	}

}