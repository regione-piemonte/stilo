/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import it.eng.auriga.compiler.utility.TemplateUtility;

/**
 * Wrapper di un placeholder presente nella cella di una tabella
 * 
 * @author massimo malvestio
 *
 */
public class CellPlaceholderWrapper extends PlaceholderWrapper {

	private CTSdtCell run;
	private int rowIndex;
	private XWPFTable table;

	public CellPlaceholderWrapper(CTSdtCell run, int rowIndex, XWPFTable table) {
		this.run = run;
		this.rowIndex = rowIndex;
		this.table = table;
	}

	@Override
	public String getPlaceholderValue() {

		if (run.getSdtPr() == null || run.getSdtPr().getTagList() == null || run.getSdtPr().getTagList().isEmpty()) {
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

		// metto per primo il controllo di checkbox perchè gli può essere
		// applicato uno stile, per esempio grassetto, e mi verrebbe visto come
		// rtf se mettessi questa condizione dopo
		if (isCheckBox()) {

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
		} else if (isText() || isRtfText()) {

			if (directives.contains(DirectiveEnum.REMOVE_ON_EMPTY)) {

				table.removeRow(rowIndex);

			} else if (directives.contains(DirectiveEnum.APPEND_CONTENT)) {

				List<CTText> variableParagraphs = TemplateUtility.getTextFormParagraphs(run);
				CTText lastParagraph = variableParagraphs.get(variableParagraphs.size() - 1);
				String currentValue = lastParagraph.getStringValue();
				lastParagraph.setStringValue(currentValue + value);

				lastParagraph.getStringValue();
			} else {

				CTText variableParagraph = null;

				List<CTText> variableParagraphs = TemplateUtility.getTextFormParagraphs(run);

				if (variableParagraphs == null || variableParagraphs.isEmpty()) {

					CTString style = null;

					if (!run.getSdtPr().getRPrList().isEmpty()) {

						style = run.getSdtPr().getRPrList().get(0).getRStyle();

					}

					variableParagraph = TemplateUtility.addCellTextFormParagraph(run, style);
				} else {

					String style = "";

					List<CTTc> tcList = run.getSdtContent().getTcList();

					for (int i = 0; i < tcList.size(); i++) {

						List<CTP> pList = tcList.get(i).getPList();

						for (int pIndex = 0; pIndex < pList.size(); pIndex++) {

							List<CTR> rList = pList.get(pIndex).getRList();

							for (int rIndex = 0; rIndex < rList.size(); rIndex++) {

								if (rList.get(rIndex).getRPr() != null
										&& rList.get(rIndex).getRPr().getRStyle() != null) {
									style = rList.get(rIndex).getRPr().getRStyle().getVal();
								}
							}
						}

						run.getSdtContent().removeTc(i);
					}

					CTR addNewTc = run.getSdtContent().addNewTc().addNewP().addNewR();

					variableParagraph = addNewTc.addNewT();

					CTString newStyle = addNewTc.addNewRPr().addNewRStyle();
					newStyle.setVal(style);

				}

				variableParagraph.setStringValue(value);
			}
		}
	}

	@Override
	public void removeFromDocument() {
		table.removeRow(rowIndex);
	}

	@Override
	public String toString() {
		return run.toString();
	}
}
