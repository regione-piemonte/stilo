/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe che raccoglie le informazioni sui vincoli che un file deve soddisfare per essere incluso o escluso da una scansione <br>
 * Quindi può trattarsi di una directory, di un file compresso o di un file indice
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class VincoliFileScansione {

	/**
	 * Lista pattern del nome dei file ammessi
	 */

	private List<String> listaPatternPercorsiFileAssoluti = new ArrayList<>();

	/**
	 * Lista pattern dei percorsi assouluti dei file ammessi
	 */

	private List<String> listaPatternNomeFile = new ArrayList<>();

	/**
	 * Lista con eventuali percorsi assoluti dei file da escludere dalla scansione
	 */

	private List<String> listaPercorsiFileAssolutiDaEscludere = new ArrayList<>();

	/**
	 * Lista con eventuali nomi dei file da escludere dalla scansione
	 */

	private List<String> listaNomiFileDaEscludere = new ArrayList<>();

	/**
	 * Lista con le estensioni dei file amessi, compresi eventuali file indice e compressi
	 */

	private List<String> listaEstensioni = new ArrayList<>();

	/**
	 * Lista dei mime type ammessi
	 */

	private List<String> listaMimeType = new ArrayList<>();

	public List<String> getListaPatternNomeFile() {
		return listaPatternNomeFile;
	}

	public void setListaPatternNomeFile(List<String> listaPatternNomeFile) {
		this.listaPatternNomeFile = listaPatternNomeFile;
	}

	public List<String> getListaPercorsiFileAssolutiDaEscludere() {
		return listaPercorsiFileAssolutiDaEscludere;
	}

	public void setListaPercorsiFileAssolutiDaEscludere(List<String> listaPercorsiFileAssolutiDaEscludere) {
		this.listaPercorsiFileAssolutiDaEscludere = listaPercorsiFileAssolutiDaEscludere;
	}

	public List<String> getListaNomiFileDaEscludere() {
		return listaNomiFileDaEscludere;
	}

	public void setListaNomiFileDaEscludere(List<String> listaNomiFileDaEscludere) {
		this.listaNomiFileDaEscludere = listaNomiFileDaEscludere;
	}

	public List<String> getListaEstensioni() {
		return listaEstensioni;
	}

	public void setListaEstensioni(List<String> listaEstensioni) {
		this.listaEstensioni = listaEstensioni;
	}

	public List<String> getListaMimeType() {
		return listaMimeType;
	}

	public void setListaMimeType(List<String> listaMimeType) {
		this.listaMimeType = listaMimeType;
	}

	public List<String> getListaPatternPercorsiFileAssoluti() {
		return listaPatternPercorsiFileAssoluti;
	}

	public void setListaPatternPercorsiFileAssoluti(List<String> listaPatternPercorsiFileAssoluti) {
		this.listaPatternPercorsiFileAssoluti = listaPatternPercorsiFileAssoluti;
	}

}
