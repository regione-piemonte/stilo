/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.DatiContabiliXmlBean;

public class DatiContabiliBean extends DatiContabiliXmlBean {

	private String id;
	private String liv1234PdC;;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLiv1234PdC() {
		return liv1234PdC;
	}
	public void setLiv1234PdC(String liv1234PdC) {
		this.liv1234PdC = liv1234PdC;
	}
		
}
