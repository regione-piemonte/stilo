/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * Classe per la mappatura del campo XMLRequestIn (DMPK_GAE.GetFileEventiToSend)
 * 
 * @author denbraga
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFileEventiToSendRequest {

	@XmlVariabile(nome = "#CodPbProduttore", tipo = TipoVariabile.SEMPLICE)
	private String codPbProduttore;

	@XmlVariabile(nome = "#CodSistemaDestinatario", tipo = TipoVariabile.SEMPLICE)
	private String codSistemaDestinatario;

	@XmlElementWrapper(nillable = true)
	@XmlVariabile(nome = "#@CodTipiEventi", tipo = TipoVariabile.LISTA)
	private List<TipoEvento> codTipiEventi;

	public String getCodPbProduttore() {
		return codPbProduttore;
	}

	public void setCodPbProduttore(String codPbProduttore) {
		this.codPbProduttore = codPbProduttore;
	}

	public String getCodSistemaDestinatario() {
		return codSistemaDestinatario;
	}

	public void setCodSistemaDestinatario(String codSistemaDestinatario) {
		this.codSistemaDestinatario = codSistemaDestinatario;
	}

	public List<TipoEvento> getCodTipiEventi() {
		return codTipiEventi;
	}

	public void setCodTipiEventi(List<TipoEvento> codTipiEventi) {
		this.codTipiEventi = codTipiEventi;
	}
}
