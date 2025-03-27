/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.aurigamailbusiness.utility;

import java.util.Date;

/**
 * 
 * classe di utilità per il confronto fra date
 * @author jravagnan
 *
 */
public class DateUtils {

	public static Integer getDifferenceInMinute(Date currentDate, Date referenceDate) {
		Long difference = currentDate.getTime() - referenceDate.getTime();
		Long minuti = difference / 60000;
		return minuti.intValue();
	}

}
