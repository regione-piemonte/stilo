/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import it.eng.auriga.compiler.utility.TemplateUtility;

/**
 * Wrapper di un placeholder presente in un CTSdtRun, il quale potrebbe essere
 * all'interno di una cella di una tabella oppure di un paragrafo
 * 
 * @author massimo malvestio
 *
 */
public class ParagraphPlaceholderWrapper extends PlaceholderWrapper {

	private CTSdtRun run;
	private int rowIndex;
	private XWPFTable table;

	public ParagraphPlaceholderWrapper(CTSdtRun run) {
		this.run = run;
	}

	public ParagraphPlaceholderWrapper(CTSdtRun run, int rowIndex, XWPFTable table) {
		this.run = run;
		this.rowIndex = rowIndex;
		this.table = table;
	}

	@Override
	public String getPlaceholderValue() {

		if (run.getSdtPr().getTagList() == null || run.getSdtPr().getTagList().isEmpty()) {
			return null;
		} else {
			return run.getSdtPr().getTagList().get(0).getVal();
		}
	}

	@Override
	public void setPlaceholderValue(String newValue) {
		run.getSdtPr().getTagList().get(0).setVal(newValue);
	}

	@Override
	public boolean isCheckBox() {
		return run.getSdtPr().toString().contains("w14:checkbox");
	}

	@Override
	public boolean isText() {

		return !run.getSdtPr().getTextList().isEmpty();

	}

	@Override
	public boolean isRtfText() {
		return !run.getSdtPr().getRPrList().isEmpty();
	}

	@Override
	public void injectValue(String value) {

		Set<DirectiveEnum> directives = getDirectives();

		if (isText()) {

			if (directives.contains(DirectiveEnum.APPEND_CONTENT)) {

				// è stato richiesto di accodare il contenuto a quanto già
				// presente

				CTR placeholderCtr = run.getSdtContent().addNewR();
				placeholderCtr.addNewT();

				placeholderCtr.getTList().get(placeholderCtr.getTList().size() - 1).setStringValue(value);

			} else {

				List<CTR> ctrList = run.getSdtContent().getRList();

				CTR placeholderCtr = null;

				if (ctrList.isEmpty()) {

					// non ci sono contenuti, quindi posso creare l'oggetto che
					// andrà ad ospitare il valore da iniettare

					placeholderCtr = run.getSdtContent().addNewR();
					placeholderCtr.addNewT();

					// applico lo stile eventualmente specificato dall'utente
					CTString rStyle = null;

					if (!run.getSdtPr().getRPrList().isEmpty()) {
						rStyle = run.getSdtPr().getRPrList().get(0).getRStyle();
					}

					if (rStyle != null) {
						String style = rStyle.getVal();
						placeholderCtr.addNewRPr().addNewRStyle().setVal(style);
					}

				} else {

					String style = null;

					// ci sono contenuti, quindi per prima cosa devo fare piazza
					// pulita
					for (int rIndex = 0; rIndex < ctrList.size(); rIndex++) {

						if (run.getSdtContent().getRList().get(rIndex).getRPr() != null) {
							style = run.getSdtContent().getRList().get(rIndex).getRPr().getRStyle().getVal();

						}

						run.getSdtContent().removeR(rIndex);
					}

					// aggiungo l'oggetto che ospiterà il valore da iniettare
					placeholderCtr = run.getSdtContent().addNewR();
					placeholderCtr.addNewRPr().addNewRStyle().setVal(style);
					placeholderCtr.addNewT();
				}

				// inietto il valore
				placeholderCtr.getTList().get(0).setStringValue(value);

			}

		} else if (isCheckBox()) {

			CTText checkboxText = TemplateUtility.getCheckboxBox(run);

			// si tratta di un checkbox, devo quindi analizzare il valore e se è
			// pari ad 1 applicare il check
			if (value.equals("1") || value.equals("true")) {

				checkboxText.setStringValue("☑");

			} else {

				if (directives.contains(DirectiveEnum.REMOVE_ON_EMPTY)
						|| directives.contains(DirectiveEnum.REMOVE_ON_FALSE)) {
					removeFromDocument();
				} else {
					checkboxText.setStringValue("☐");
				}

			}
		}
	}

	@Override
	public void removeFromDocument() {

		// eseguito solo quando il controllo si trova all'iterno di un paragrafo
		// all'interno di una cella di una tabella
		if (table != null) {
			table.removeRow(rowIndex);
		}
	}

	@Override
	public String toString() {
		return run.toString();
	}
}
