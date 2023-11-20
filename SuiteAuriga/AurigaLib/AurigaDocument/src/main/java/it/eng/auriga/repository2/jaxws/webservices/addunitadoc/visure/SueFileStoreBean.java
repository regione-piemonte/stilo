/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.FileStoreBean;

public class SueFileStoreBean extends FileStoreBean {
	
	@XmlVariabile(nome="#ProcessToStart.IdBPMProcessInst", tipo = TipoVariabile.SEMPLICE)
	 private String idBPMProcessInst;
	 
	 @XmlVariabile(nome="#ProcessToStart.IdBPMProcessDef", tipo = TipoVariabile.SEMPLICE)
	 private String idBPMProcessDef;
	 
	 public String getIdBPMProcessInst() {
			return idBPMProcessInst;
		}

		public void setIdBPMProcessInst(String idBPMProcessInst) {
			this.idBPMProcessInst = idBPMProcessInst;
		}

		public String getIdBPMProcessDef() {
			return idBPMProcessDef;
		}

		public void setIdBPMProcessDef(String idBPMProcessDef) {
			this.idBPMProcessDef = idBPMProcessDef;
		}

}
