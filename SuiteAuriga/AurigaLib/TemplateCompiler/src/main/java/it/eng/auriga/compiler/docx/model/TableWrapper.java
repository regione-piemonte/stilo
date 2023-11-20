/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;

/**
 * Raccoglie tutte le informazioni relative ad una tabella, per favorire
 * l'iniezione dei dati
 * 
 * @author massimo malvestio
 *
 */
public class TableWrapper {

	private XWPFTable table;

	// gli eventuali placeholder presenti, rappresentano i dati presenti nelle
	// colonne
	private List<PlaceholderWrapper> variables;

	private PlaceholderWrapper tableId;

	public TableWrapper(XWPFTable table, List<PlaceholderWrapper> variables, PlaceholderWrapper tableId) {
		this.table = table;
		this.variables = variables;
		this.tableId = tableId;
	}

	public Set<DirectiveEnum> getDirectives() {
		return tableId.getDirectives();
	}

	public List<PlaceholderWrapper> getVariables() {
		return this.variables;
	}

	public XWPFTable getTable() {
		return table;
	}

	public PlaceholderWrapper getTableId() {
		return tableId;
	}

	public XWPFTableRow addRow() {

		return table.createRow();

	}

	public XWPFTableRow injectNewRow(Riga riga) {

		XWPFTableRow newRow = table.createRow();

		// le variabili sono in ordine di definizione nella tabella, quindi la
		// prima variabile corrisponde alla prima colonna, etc
		for (int varIndex = 0; varIndex < variables.size(); varIndex++) {

			PlaceholderWrapper variable = variables.get(varIndex);

			XWPFTableCell cell = newRow.getCell(varIndex);

			String identifier = variable.getPlaceholderIdentifier();

			found: {

				for (Colonna currentColonna : riga.getColonna()) {

					String colonnaId = "$" + currentColonna.getNro().toString() + "$";

					String nomeColonna = "$" + currentColonna.getNome() + "$";

					// provo a verificare se è stato specificato come variabile
					// il nome della colonna oppure il numero di colonna
					if (nomeColonna.equals(identifier) || colonnaId.equals(identifier)) {
						cell.setText(currentColonna.getContent());
						break found;
					}
				}
			}
		}

		return newRow;
	}
}
