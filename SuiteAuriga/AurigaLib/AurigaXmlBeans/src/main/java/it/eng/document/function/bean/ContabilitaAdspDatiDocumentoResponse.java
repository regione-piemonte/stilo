/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class ContabilitaAdspDatiDocumentoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer IdTipdoc;
	private String uuid;
	private String descrizione;
	
	private ContabilitaAdspMetadatiResponse metadati;
	
	private List<ContabilitaAdspAllegatoDocumentoResponse> allegati;

	public Integer getIdTipdoc() {
		return IdTipdoc;
	}

	public void setIdTipdoc(Integer idTipdoc) {
		IdTipdoc = idTipdoc;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public ContabilitaAdspMetadatiResponse getMetadati() {
		return metadati;
	}

	public void setMetadati(ContabilitaAdspMetadatiResponse metadati) {
		this.metadati = metadati;
	}

	public List<ContabilitaAdspAllegatoDocumentoResponse> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<ContabilitaAdspAllegatoDocumentoResponse> allegati) {
		this.allegati = allegati;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspDatiDocumentoResponse [IdTipdoc=" + IdTipdoc + ", uuid=" + uuid + ", descrizione="
				+ descrizione + ", metadati=" + metadati + ", allegati=" + allegati + "]";
	}
	
}
