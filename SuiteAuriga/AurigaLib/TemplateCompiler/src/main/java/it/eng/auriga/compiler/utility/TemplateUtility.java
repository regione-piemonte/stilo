/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import it.eng.auriga.compiler.docx.model.CellPlaceholderWrapper;
import it.eng.auriga.compiler.docx.model.ParagraphPlaceholderWrapper;
import it.eng.auriga.compiler.docx.model.PlaceholderWrapper;
import it.eng.bean.ExecutionResultBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;

/**
 * Raccoglie metodi di utilità per la gestione dei template
 * 
 * @author massimo malvestio
 *
 */
public class TemplateUtility {

	private static Logger logger = Logger.getLogger(TemplateUtility.class);

	public static XWPFDocument generateXWPFDocument(String docStorageUri) {

		XWPFDocument template = null;

		try {
			TemplateStorage templateStorage = TemplateStorageFactory.getTemplateStorageImpl();
			
			InputStream inputStream = templateStorage.extract(docStorageUri);
			BufferedInputStream fis = new BufferedInputStream(inputStream);
			template = new XWPFDocument(fis);

			fis.close();

		} catch (Exception e) {

			logger.error(
					String.format("Durante il recupero del file avente uri %1$s, si è verificata la seguente eccezione",
							docStorageUri),
					e);

		}

		return template;
	}

	public static XWPFDocument generateXWPFDocument(File templateFile) {

		XWPFDocument template = null;

		try {

			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(templateFile));
			template = new XWPFDocument(fis);
			fis.close();

		} catch (Exception e) {

			logger.error(
					String.format("Durante il recupero del file avente uri %1$s, si è verificata la seguente eccezione",
							templateFile.getAbsolutePath()),
					e);

		}

		return template;
	}

	/**
	 * Resituisce tutti i run, pezzi, che compongono un paragraph
	 * 
	 * @param currentPar
	 * @return
	 */
	public static List<CTSdtRun> getRuns(XWPFParagraph paragraph) {
		return paragraph.getCTP().getSdtList();
	}

	/**
	 * Estrae tutti i componenti del paragrafo specificato
	 * 
	 * @param paragraph
	 * @return
	 */
	public static List<PlaceholderWrapper> getVariables(XWPFParagraph paragraph) {

		List<PlaceholderWrapper> retValue = new ArrayList<PlaceholderWrapper>();

		List<CTSdtRun> runs = paragraph.getCTP().getSdtList();

		for (CTSdtRun run : runs) {

			ParagraphPlaceholderWrapper wrapper = new ParagraphPlaceholderWrapper(run);

			String placeholderIdentifier = wrapper.getPlaceholderIdentifier();

			if (StringUtils.isNotBlank(placeholderIdentifier)) {
				retValue.add(wrapper);
			}
		}

		return retValue;

	}

	/**
	 * Estrae il contenuto del run in formato xml
	 * 
	 * @param run
	 * @return
	 */
	public static String getGraphicalControlXml(CTSdtRun run) {
		return run.getSdtPr().toString();
	}

	/**
	 * Estrae il contenuto del run in formato xml
	 * 
	 * @param run
	 * @return
	 */
	public static String getGraphicalControlXml(CTString run) {
		return run.toString();
	}

	/**
	 * Estrae tutti tag presenti nel run specificato. Se richiesto, splitta i
	 * valori mediante il separatore |*|
	 * 
	 * @param run
	 * @return
	 */
	public static List<String> getVariableContent(CTSdtRun run, boolean split) {

		List<String> retValue = new ArrayList<String>();

		if (run.getSdtPr() != null && !run.getSdtPr().getTagList().isEmpty()) {

			for (CTString content : run.getSdtPr().getTagList()) {

				retValue.addAll(getVariableContent(content, split));
			}
		}

		return retValue;
	}

	/**
	 * Ritorna tutti le variabili presenti nel run specificato
	 * 
	 * @param run
	 * @return
	 */
	public static List<CTString> getVariablesList(CTSdtRun run) {
		return run.getSdtPr().getTagList();
	}

	/**
	 * Estrae tutti tag presenti nel run specificato. Se richiesto, splitta i
	 * valori mediante il separatore |*|
	 * 
	 * @param run
	 * @return
	 */
	public static List<String> getVariableContent(CTString content, boolean split) {

		List<String> retValue = new ArrayList<String>();

		String value = content.getVal();

		if (split) {

			String[] tags = value.split("|*|");

			retValue.addAll(Arrays.asList(tags));

		} else {

			retValue.add(value);

		}

		return retValue;
	}

	/**
	 * L'identificativo presente nel tag deve essere necessariamente nella forma
	 * $valore$, non possono comparire altri $
	 * 
	 * @param run
	 * @return
	 */
	public static String getVariablePlaceholder(CTSdtRun run) {

		String retValue = null;

		if (run.getSdtPr() != null && !run.getSdtPr().getTagList().isEmpty()) {

			for (CTString ctString : run.getSdtPr().getTagList()) {

				if (ctString.getVal().contains("$")) {

					int startIndex = ctString.getVal().indexOf("$");
					int endIndex = ctString.getVal().lastIndexOf("$") + 1;

					if (startIndex != endIndex && startIndex > -1) {

						retValue = ctString.getVal().substring(startIndex, endIndex);

					}
				}
			}
		}

		return retValue;
	}

	/**
	 * Estrae tutte le variabili presenti nella tabella specificata
	 * 
	 * @param currentTable
	 * @return
	 */
	public static List<PlaceholderWrapper> getTableVariables(XWPFTable currentTable) {

		List<PlaceholderWrapper> retValue = new ArrayList<PlaceholderWrapper>();

		for (int rowIndex = 0; rowIndex < currentTable.getNumberOfRows(); rowIndex++) {

			XWPFTableRow row = currentTable.getRow(rowIndex);

			// le celle contenenti controlli di tipo form sono di tipo sdt,
			// mentre le celle contenenti testo normale sono di tipo ct
			for (CTSdtCell cellContent : row.getCtRow().getSdtList()) {

				CellPlaceholderWrapper wrapper = new CellPlaceholderWrapper(cellContent, rowIndex, currentTable);

				if (StringUtils.isNotBlank(wrapper.getPlaceholderIdentifier())) {

					retValue.add(wrapper);
				}
			}

			// se c'è del testo, le variabili potrebbero essere contenute in
			// elementi di tipo w:p. Potrebbero essere duplicati con i
			// precedenti, inserisco un controllo sull'id
			for (CTTc cttc : row.getCtRow().getTcList()) {

				for (CTSdtRun run : cttc.getPList().get(0).getSdtList()) {

					ParagraphPlaceholderWrapper wrapper = new ParagraphPlaceholderWrapper(run, rowIndex, currentTable);

					if (StringUtils.isNotBlank(wrapper.getPlaceholderIdentifier())) {
						retValue.add(wrapper);
					}
				}
			}
		}

		return retValue;
	}

	/**
	 * Restituisce l'id del run che contiene un placeholder
	 * 
	 * @param run
	 * @return
	 */
	private static BigInteger getRunPlaceholderId(CTSdtRun run) {
		return getSdtPrId(run.getSdtPr());
	}

	/**
	 * Ritorna l'i del controllo presente nella cella
	 * 
	 * @param cell
	 * @return
	 */
	public static BigInteger getCellId(CTSdtCell cell) {

		return getSdtPrId(cell.getSdtPr());

	}

	/**
	 * Restituisce l'id del run che contiene un placeholder
	 * 
	 * @param sdtPr
	 * @return
	 */
	public static BigInteger getSdtPrId(CTSdtPr sdtPr) {

		if (sdtPr != null && sdtPr.getIdList() != null && !sdtPr.getIdList().isEmpty()
				&& sdtPr.getIdList().get(0) != null) {
			return sdtPr.getIdList().get(0).getVal();
		} else {
			return null;
		}

	}

	/**
	 * Cerca nella tabella se esiste una variabile marcata come tableId.<br/>
	 * Si suppone ce ne sia una sola con questa marcatura, e che il valore del
	 * placeholder sia univoco a livello di documento
	 * 
	 * @param variables
	 * @return l
	 */
	public static PlaceholderWrapper getTableId(List<PlaceholderWrapper> variables) {

		PlaceholderWrapper retValue = null;

		found: {

			for (int index = 0; index < variables.size(); index++) {

				PlaceholderWrapper variable = variables.get(index);

				String[] variableContents = variable.getPlaceHolderValueContents();

				for (String variableContent : variableContents) {

					if (variableContent.equals("tableId")) {

						retValue = variable;

						break found;

					}
				}
			}
		}

		return retValue;
	}

	public static PlaceholderWrapper removeTableId(List<PlaceholderWrapper> variables) {

		PlaceholderWrapper retValue = null;

		found: {

			for (int index = 0; index < variables.size(); index++) {

				PlaceholderWrapper variable = variables.get(index);

				String[] variableContents = variable.getPlaceHolderValueContents();

				for (String variableContent : variableContents) {

					if (variableContent.equals("tableId")) {

						retValue = variable;

						variables.remove(index);

						break found;

					}
				}
			}
		}

		return retValue;
	}

	/**
	 * Estrae il CTText che contiene il box associato ad un checkbox, dato il
	 * run che contiene il controllo
	 * 
	 * @param run
	 *            ad un paragrafo che contiene il controllo form di tipo
	 *            checkbox
	 * @return
	 */
	public static CTText getCheckboxBox(CTSdtRun run) {

		return run.getSdtContent().getRList().get(0).getTList().get(0);

	}

	/**
	 * Estrae il CTText che contiene il box associato ad un checkbox
	 * 
	 * @param run
	 *            appartenente ad una cella di una tabella che contiene il
	 *            controllo form di tipo checkbox
	 * @return
	 */
	public static CTText getCheckboxBox(CTSdtCell run) {

		return run.getSdtContent().getTcList().get(0).getPList().get(0).getRList().get(0).getTList().get(0);

	}

	/**
	 * Restituisce tutti i paragrafi presenti nel controllo grafico di tipo
	 * testo semplice contenuto nel run specificato associato ad una cella
	 * 
	 * @return tutti i paragrafi, oppure null se il tag è vuoto
	 */
	public static List<CTText> getTextFormParagraphs(CTSdtCell run) {

		if (run.getSdtContent().getTcList().get(0).getPList().get(0).getRList().isEmpty()) {
			return null;
		} else {
			return run.getSdtContent().getTcList().get(0).getPList().get(0).getRList().get(0).getTList();
		}
	}

	/**
	 * Se il form control è vuoto allora crea un nuovo R e gli aggancia un nuovo
	 * CTText.<br/>
	 * 
	 * Se il form control ha già degli R, allora prende il primo e gli aggiunge
	 * un nuovo CTText
	 * 
	 * @param run
	 * @param style
	 * @return
	 */
	public static CTText addCellTextFormParagraph(CTSdtCell run, CTString style) {

		List<CTText> paragraphs = getTextFormParagraphs(run);

		if (paragraphs == null) {

			CTR variableParagraph = run.getSdtContent().getTcList().get(0).getPList().get(0).addNewR();

			if (style != null && style.getVal() != null) {
				CTString newStyle = variableParagraph.addNewRPr().addNewRStyle();
				newStyle.setVal(style.getVal());
			}

			return variableParagraph.addNewT();

		} else {
			return run.getSdtContent().getTcList().get(0).getPList().get(0).getRList().get(0).addNewT();
		}
	}

	/**
	 * Aggiunge una cella alla riga specificata, inserendo il contenuto fornito
	 * 
	 * @param row
	 * @param cellIndex
	 * @param content
	 */
	public static void addCell(XWPFTableRow row, int cellIndex, String content) {

		XWPFTableCell currentCell = row.getCell(cellIndex);
		currentCell.setText(content);

	}

	/**
	 * Estrae i valori complessi ed i valori semplici in due mappe, dove k ->
	 * nome del valore, v -> il valore, che può essere uno String oppure una
	 * Lista.
	 * 
	 * @param sezioneCache
	 * @return
	 *         <ul>
	 *         <li>per accedere alla mappa dei valori semplici recuperare
	 *         l'additionInformation mediante la chiave "valoriSemplici"</li>
	 *         <li>per accedere alla mappa dei valori complessi recuperare
	 *         l'additionInformation mediante la chiave "valoriComplessi"</li>
	 *         </ul>
	 */
	public static ExecutionResultBean mapSezioneCache(SezioneCache sezioneCache) {

		Map<String, String> valoriSemplici = new LinkedHashMap<String, String>();
		Map<String, Lista> valoriComplessi = new LinkedHashMap<String, Lista>();

		ExecutionResultBean retValue = new ExecutionResultBean();
		retValue.addAdditionalInformation("valoriSemplici", valoriSemplici);
		retValue.addAdditionalInformation("valoriComplessi", valoriComplessi);

		for (Variabile var : sezioneCache.getVariabile()) {

			String valoreSemplice = var.getValoreSemplice();

			String variableName = var.getNome().trim();
			if (valoreSemplice != null) {

				valoriSemplici.put("$" + variableName + "$", valoreSemplice);

			} else {

				Lista values = var.getLista();
				valoriComplessi.put("$" + variableName + "$", values);
			}
		}

		return retValue;
	}

	/**
	 * Elimina tutti i contenuti della cella e ricrea la struttura minimale TC
	 * -> P -> R -> T vuota
	 * 
	 * @param run
	 * @return l'unico campo di testo attualmente presente nella cella
	 */
	public static CTText deleteContents(CTSdtCell run) {

		for (int i = 0; i < run.getSdtContent().getTcList().size(); i++) {
			run.getSdtContent().removeTc(i);
		}

		return run.getSdtContent().addNewTc().addNewP().addNewR().addNewT();

	}
}
