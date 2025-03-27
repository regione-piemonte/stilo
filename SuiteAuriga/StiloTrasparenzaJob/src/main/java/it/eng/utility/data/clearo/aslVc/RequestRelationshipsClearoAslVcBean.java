/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data.clearo.aslVc;

import java.io.Serializable;

public class RequestRelationshipsClearoAslVcBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RequestFieldPaginaAlberaturaClearoAslVcBean field_pagina_alberatura;

	public RequestFieldPaginaAlberaturaClearoAslVcBean getField_pagina_alberatura() {
		return field_pagina_alberatura;
	}

	public void setField_pagina_alberatura(RequestFieldPaginaAlberaturaClearoAslVcBean field_pagina_alberatura) {
		this.field_pagina_alberatura = field_pagina_alberatura;
	}

	@Override
	public String toString() {
		return "RequestRelationshipsClearoBean [field_pagina_alberatura=" + field_pagina_alberatura + "]";
	}
	
}
