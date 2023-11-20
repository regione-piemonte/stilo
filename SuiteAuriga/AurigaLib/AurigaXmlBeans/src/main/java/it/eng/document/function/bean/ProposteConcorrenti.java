/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ProposteConcorrenti {
	
	// Id. UD della proposta
	@NumeroColonna(numero = "1")
	private String idUdProposta;
	
	// ID. process della proposta
	@NumeroColonna(numero = "2")
	private String idProcessProposta;
	
	// Estremi della proposta
	@NumeroColonna(numero = "3")
	private String estremiProposta;
	
	// Oggetto della proposta
	@NumeroColonna(numero = "4")
	private String oggettoProposta;
	
	// Importo allocato su cdc/capitolo/conto da parte della proposta (in notazione italiana con , per i decimali)
	@NumeroColonna(numero = "5")
	private String importoProposta;
	
	@NumeroColonna(numero = "6")
	private String keyCapitolo;

	private String capitoloProposta;
	
	private String contoProposta;
	
	public String getIdUdProposta() {
		return idUdProposta;
	}

	public String getIdProcessProposta() {
		return idProcessProposta;
	}

	public String getEstremiProposta() {
		return estremiProposta;
	}

	public String getOggettoProposta() {
		return oggettoProposta;
	}

	public String getImportoProposta() {
		return importoProposta;
	}

	public void setIdUdProposta(String idUdProposta) {
		this.idUdProposta = idUdProposta;
	}

	public void setIdProcessProposta(String idProcessProposta) {
		this.idProcessProposta = idProcessProposta;
	}

	public void setEstremiProposta(String estremiProposta) {
		this.estremiProposta = estremiProposta;
	}

	public void setOggettoProposta(String oggettoProposta) {
		this.oggettoProposta = oggettoProposta;
	}

	public void setImportoProposta(String importoProposta) {
		this.importoProposta = importoProposta;
	}
	public String getKeyCapitolo() {
		return keyCapitolo;
	}

	public void setKeyCapitolo(String keyCapitolo) {
		this.keyCapitolo = keyCapitolo;
	}

	public String getCapitoloProposta() {
		return capitoloProposta;
	}

	public String getContoProposta() {
		return contoProposta;
	}

	public void setCapitoloProposta(String capitoloProposta) {
		this.capitoloProposta = capitoloProposta;
	}

	public void setContoProposta(String contoProposta) {
		this.contoProposta = contoProposta;
	}
	
	
}
