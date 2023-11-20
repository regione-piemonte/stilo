/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.AttributeSet;

public class TableStyleBean {

	private ArrayList<ArrayList<AttributeSet>> listaStiliRow;
	private ArrayList<Float> listaWidthColonne;
	private String unitaMisuraWidthColonne;
	private CellaMappaUnioniBean[][] mappaUnioni;
	private HashMap<Integer, CoordinateRaggruppamentoBean> raggruppamenti;
	private AttributeSet stileTabella;
	private Float sommaWidthColonne;
	private String bordoTabella;

	public ArrayList<ArrayList<AttributeSet>> getListaStiliRow() {
		return listaStiliRow;
	}

	public void setListaStiliRow(ArrayList<ArrayList<AttributeSet>> listaStiliRow) {
		this.listaStiliRow = listaStiliRow;
	}

	public CellaMappaUnioniBean[][] getMappaUnioni() {
		return mappaUnioni;
	}

	public void setMappaUnioni(CellaMappaUnioniBean[][] mappaUnioni) {
		this.mappaUnioni = mappaUnioni;
	}

	public HashMap<Integer, CoordinateRaggruppamentoBean> getRaggruppamenti() {
		return raggruppamenti;
	}

	public void setRaggruppamenti(HashMap<Integer, CoordinateRaggruppamentoBean> raggruppamenti) {
		this.raggruppamenti = raggruppamenti;
	}

	public ArrayList<Float> getListaWidthColonne() {
		return listaWidthColonne;
	}

	public void setListaWidthColonne(ArrayList<Float> listaWidthColonne) {
		this.listaWidthColonne = listaWidthColonne;
	}

	public String getUnitaMisuraWidthColonne() {
		return unitaMisuraWidthColonne;
	}

	public void setUnitaMisuraWidthColonne(String unitaMisuraWidthColonne) {
		this.unitaMisuraWidthColonne = unitaMisuraWidthColonne;
	}

	public AttributeSet getStileTabella() {
		return stileTabella;
	}

	public void setStileTabella(AttributeSet stileTabella) {
		this.stileTabella = stileTabella;
	}

	public Float getSommaWidthColonne() {
		return sommaWidthColonne;
	}

	public void setSommaWidthColonne(Float sommaWidthColonne) {
		this.sommaWidthColonne = sommaWidthColonne;
	}

	public String getBordoTabella() {
		return bordoTabella;
	}

	public void setBordoTabella(String bordoTabella) {
		this.bordoTabella = bordoTabella;
	}

	public class CoordinateRaggruppamentoBean {

		private int startRow;
		private int endRow;
		private int startCol;
		private int endCol;

		public int getEndCol() {
			return endCol;
		}

		public int getEndRow() {
			return endRow;
		}

		public void setEndCol(int endCol) {
			this.endCol = endCol;
		}

		public void setEndRow(int endRow) {
			this.endRow = endRow;
		}

		public int getStartRow() {
			return startRow;
		}

		public int getStartCol() {
			return startCol;
		}

		public void setStartRow(int startRow) {
			this.startRow = startRow;
		}

		public void setStartCol(int startCol) {
			this.startCol = startCol;
		}

	}
}
