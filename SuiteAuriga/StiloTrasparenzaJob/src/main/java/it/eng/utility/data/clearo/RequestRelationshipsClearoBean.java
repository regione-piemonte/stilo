/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestRelationshipsClearoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RequestFieldPaginaAlberaturaClearoBean field_pagina_alberatura;

	public RequestFieldPaginaAlberaturaClearoBean getField_pagina_alberatura() {
		return field_pagina_alberatura;
	}

	public void setField_pagina_alberatura(RequestFieldPaginaAlberaturaClearoBean field_pagina_alberatura) {
		this.field_pagina_alberatura = field_pagina_alberatura;
	}

	@Override
	public String toString() {
		return "RequestRelationshipsClearoBean [field_pagina_alberatura=" + field_pagina_alberatura + "]";
	}
	
}
