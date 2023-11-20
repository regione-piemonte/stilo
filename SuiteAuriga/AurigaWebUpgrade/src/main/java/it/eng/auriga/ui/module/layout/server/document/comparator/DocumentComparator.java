/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.document.bean.UniqueIdentificatorDocument;

public class DocumentComparator {

	public boolean compare(UniqueIdentificatorDocument oldDocument, UniqueIdentificatorDocument newDocument){
		if (oldDocument.getPrimario().equals(newDocument.getPrimario())){
			if (oldDocument.getAllegati()==null && newDocument.getAllegati() == null) return true;
			if (oldDocument.getAllegati()!=null && newDocument.getAllegati() == null) return false;
			if (oldDocument.getAllegati()==null && newDocument.getAllegati() != null) return false;
			for (Integer posizione : oldDocument.getAllegati().keySet()){
				if (oldDocument.getAllegati().get(posizione).equals(newDocument.getAllegati().get(posizione))){
					//tutto ok
				} else return false;
			}
			return true;
		} else return false;
	}
}
