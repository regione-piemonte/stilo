/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpSession;


public interface UserPrivilegiUtil {

	public String[] getPrivilegi(HttpSession lSession);
}
