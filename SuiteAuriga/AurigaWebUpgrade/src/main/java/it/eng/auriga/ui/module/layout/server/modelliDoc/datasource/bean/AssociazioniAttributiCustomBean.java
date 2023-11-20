/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

public class AssociazioniAttributiCustomBean {

	private String nomeVariabileModello;
	private String aliasVariabileModello;
	private Boolean flgComplex;
	private Boolean flgRipetibile;
	private List<AssociazioniAttributiCustomBean> listaAssociazioniSottoAttributiComplex;
	private String nomeAttributoCustom;
	private String nomeAttributoLibero;
	private Boolean flgBarcode;
	private String tipoBarcode;
	private Boolean flgImage;
	private Integer count = 0;
	private String tipoAttributo;
	private BigDecimal nroColonna;
	private Integer numeroColonnaAttributoLibero;
	private String tipoAssociazioneVariabileModello;

	public String getNomeVariabileModello() {
		return nomeVariabileModello;
	}

	public void setNomeVariabileModello(String nomeVariabileModello) {
		this.nomeVariabileModello = nomeVariabileModello;
	}

	public String getAliasVariabileModello() {
		return aliasVariabileModello;
	}

	public void setAliasVariabileModello(String aliasVariabileModello) {
		this.aliasVariabileModello = aliasVariabileModello;
	}

	public Boolean getFlgComplex() {
		return flgComplex;
	}

	public void setFlgComplex(Boolean flgComplex) {
		this.flgComplex = flgComplex;
	}

	public Boolean getFlgRipetibile() {
		return flgRipetibile;
	}

	public void setFlgRipetibile(Boolean flgRipetibile) {
		this.flgRipetibile = flgRipetibile;
	}

	public List<AssociazioniAttributiCustomBean> getListaAssociazioniSottoAttributiComplex() {
		return listaAssociazioniSottoAttributiComplex;
	}

	public void setListaAssociazioniSottoAttributiComplex(List<AssociazioniAttributiCustomBean> listaAssociazioniSottoAttributiComplex) {
		this.listaAssociazioniSottoAttributiComplex = listaAssociazioniSottoAttributiComplex;
	}

	public String getNomeAttributoCustom() {
		return nomeAttributoCustom;
	}

	public void setNomeAttributoCustom(String nomeAttributoCustom) {
		this.nomeAttributoCustom = nomeAttributoCustom;
	}
	
	public String getNomeAttributoLibero() {
		return nomeAttributoLibero;
	}
	
	public void setNomeAttributoLibero(String nomeAttributoLibero) {
		this.nomeAttributoLibero = nomeAttributoLibero;
	}

	public Boolean getFlgBarcode() {
		return flgBarcode;
	}

	public void setFlgBarcode(Boolean flgBarcode) {
		this.flgBarcode = flgBarcode;
	}

	public String getTipoBarcode() {
		return tipoBarcode;
	}

	public void setTipoBarcode(String tipoBarcode) {
		this.tipoBarcode = tipoBarcode;
	}

	public Boolean getFlgImage() {
		return flgImage;
	}

	public void setFlgImage(Boolean flgImage) {
		this.flgImage = flgImage;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getTipoAttributo() {
		return tipoAttributo;
	}

	public void setTipoAttributo(String tipoAttributo) {
		this.tipoAttributo = tipoAttributo;
	}

	public BigDecimal getNroColonna() {
		return nroColonna;
	}

	public void setNroColonna(BigDecimal nroColonna) {
		this.nroColonna = nroColonna;
	}
	
	public Integer getNumeroColonnaAttributoLibero() {
		return numeroColonnaAttributoLibero;
	}

	public void setNumeroColonnaAttributoLibero(Integer numeroColonnaAttributoLibero) {
		this.numeroColonnaAttributoLibero = numeroColonnaAttributoLibero;
	}

	public String getTipoAssociazioneVariabileModello() {
		return tipoAssociazioneVariabileModello;
	}

	public void setTipoAssociazioneVariabileModello(String tipoAssociazioneVariabileModello) {
		this.tipoAssociazioneVariabileModello = tipoAssociazioneVariabileModello;
	}
	
}
