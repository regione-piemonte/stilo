/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlFolderProcesso implements Serializable {

	private static final long serialVersionUID = 6982329975817339608L;

	@XmlVariabile(nome="#FlgFascTitolario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFascTitolario = Flag.SETTED;
	
	@XmlVariabile(nome="#IdProcessFrom", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idProcessFrom;
	
	@XmlVariabile(nome="#@Assegnatari", tipo = TipoVariabile.LISTA)
	private List<Assegnatario> listaAssegnatari;

	public Flag getFlgFascTitolario() {
		return flgFascTitolario;
	}

	public void setFlgFascTitolario(Flag flgFascTitolario) {
		this.flgFascTitolario = flgFascTitolario;
	}

	public BigDecimal getIdProcessFrom() {
		return idProcessFrom;
	}

	public void setIdProcessFrom(BigDecimal idProcessFrom) {
		this.idProcessFrom = idProcessFrom;
	}

	public List<Assegnatario> getListaAssegnatari() {
		return listaAssegnatari;
	}

	public void setListaAssegnatari(List<Assegnatario> listaAssegnatari) {
		this.listaAssegnatari = listaAssegnatari;
	}

}
