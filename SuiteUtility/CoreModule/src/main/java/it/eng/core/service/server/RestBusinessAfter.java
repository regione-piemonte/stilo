/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.service.bean.rest.RestServiceBean;

public interface RestBusinessAfter {

	public void after(RestServiceBean servicebeanrest);
}
