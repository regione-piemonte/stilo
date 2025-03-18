/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public interface IServiceInvokerInfo extends Serializable {
	
	public String getIdServiceInvoker();
	
	public String getNomeApplicazione();


}
