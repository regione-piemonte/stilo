/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlDocProposta implements Serializable {

	private static final long serialVersionUID = 6982329975817339608L;

	@XmlVariabile(nome="#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idDocType;
	
	@XmlVariabile(nome="#@NumerazioniDaDare", tipo = TipoVariabile.LISTA)
	private List<NumerazioneDaDare> listaNumerazioniDaDare;

	public BigDecimal getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(BigDecimal idDocType) {
		this.idDocType = idDocType;
	}

	public List<NumerazioneDaDare> getListaNumerazioniDaDare() {
		return listaNumerazioniDaDare;
	}

	public void setListaNumerazioniDaDare(List<NumerazioneDaDare> listaNumerazioniDaDare) {
		this.listaNumerazioniDaDare = listaNumerazioniDaDare;
	}

}
