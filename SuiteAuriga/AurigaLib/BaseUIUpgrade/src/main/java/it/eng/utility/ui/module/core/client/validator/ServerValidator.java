/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.ValidatorType;
import com.smartgwt.client.widgets.form.validator.Validator;

public class ServerValidator extends Validator{
	
	public ServerValidator() {
		setType(ValidatorType.SERVERCUSTOM);
		setClientOnly(false);
	}

	
}
