/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import it.eng.document.function.DocumentExporterManager;
import it.eng.document.function.bean.RigaEstrazioneDocumento;

public class DDTSheetHandler extends AbstractSheetHandler {
		public static final int COL_COD_SOCIETA = 0;
		public static final int COL_TIPO_DOC = 1;
		public static final int COL_NUM_DOC = 2;
		public static final int COL_ANNO_DOC = 3;
		public static final int COL_NOME_FILE = 4;
		public static final int COL_ESITO_ESTRAZIONE = 5;
		public static final int COL_ESITO_MESSAGGIO = 6;

		private boolean atLeastOneCellOk = false; 
		private RigaEstrazioneDocumento rigaIndice;
		private DocumentExporterManager docExportManager;

		public DDTSheetHandler(SharedStringsTable sst, DocumentExporterManager docExportManager) {
			super(sst);
			this.docExportManager = docExportManager;
		}
		
		@Override
		protected void atStartRow(String name, Attributes attributes) throws SAXException {
			
			this.row = Integer.parseInt(attributes.getValue("r"));

			if (this.row > 1) {
				this.rigaIndice = new RigaEstrazioneDocumento();
				this.rigaIndice.setNumeroRiga(this.row);
			}
		}
		
		@Override
		protected void atEndRow(String name) throws SAXException {
			try {
				if (this.rigaIndice != null && atLeastOneCellOk) {
					this.docExportManager.exportDDT(rigaIndice);
				} else if (this.row == 1) {
					this.docExportManager.verificaColonne(column, atLeastOneCellOk);
					this.docExportManager.generaIntestazione();
				}
				atLeastOneCellOk = false;
			} catch (Exception e) {
				throw new SAXException("Errore alla riga " + row, e);
			}
		}
		
		@Override
		protected void atStartColumn(String name, Attributes attributes) throws SAXException {
		}
		
		@Override
		protected void atEndColumn(String name) throws SAXException {
			
			atLeastOneCellOk = StringUtils.isNotBlank(lastContents);
			if (this.row > 1) {
				switch (column) {
				case COL_COD_SOCIETA:
					rigaIndice.setSocieta(lastContents);
					break;
				case COL_TIPO_DOC:
					rigaIndice.setTipoDocumento(lastContents);
					break;
				case COL_NUM_DOC:
					rigaIndice.setNumeroDocumento(lastContents);
					break;
				case COL_ANNO_DOC:
					rigaIndice.setAnnoDocumento(lastContents);
					break;
				default:
					break;
				};
			} else {
				this.docExportManager.addColonnaTitolo(this.column, lastContents);
			}
		}
	}