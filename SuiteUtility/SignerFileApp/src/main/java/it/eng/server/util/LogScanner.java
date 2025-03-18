/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.server.util;


public interface LogScanner {

    //passed any messages from LogWriter.writeLog
    public void message(String message);
}
