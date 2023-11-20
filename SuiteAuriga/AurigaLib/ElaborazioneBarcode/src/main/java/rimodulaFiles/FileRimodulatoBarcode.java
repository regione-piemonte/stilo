/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class FileRimodulatoBarcode {
	
	private List<PagineDaUnireBarcode> listaPagineDaUnire = new ArrayList<>();
	private int numFirmeAttese;
	private int numFirmeCompilate;
	private String barcode;
	private String tipoBarcode;
	private String nomeFileOriginale;
	
	public List<PagineDaUnireBarcode> getListaPagineDaUnire() {
		return listaPagineDaUnire;
	}
	public int getNumFirmeAttese() {
		return numFirmeAttese;
	}
	public int getNumFirmeCompilate() {
		return numFirmeCompilate;
	}
	public void setListaPagineDaUnire(List<PagineDaUnireBarcode> listaPagineDaUnire) {
		this.listaPagineDaUnire = listaPagineDaUnire;
	}
	public void setNumFirmeAttese(int numFirmeAttese) {
		this.numFirmeAttese = numFirmeAttese;
	}
	public void setNumFirmeCompilate(int numFirmeCompilate) {
		this.numFirmeCompilate = numFirmeCompilate;
	}
	public String getBarcode() {
		return barcode;
	}
	public String getTipoBarcode() {
		return tipoBarcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public void setTipoBarcode(String tipoBarcode) {
		this.tipoBarcode = tipoBarcode;
	}	
	public String getNomeFileOriginale() {
		return nomeFileOriginale;
	}
	public void setNomeFileOriginale(String nomeFileOriginale) {
		this.nomeFileOriginale = nomeFileOriginale;
	}
}
