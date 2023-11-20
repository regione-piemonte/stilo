/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

/**
 * Definisce gli stati che può assumere un job
 * @author massimo malvestio
 *
 */
public enum JobExecutionStatusEnum implements Serializable {

	RUNNING,
	COMPLETED,
	STOPPED
	
}
