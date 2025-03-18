/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import uk.co.mmscomputing.device.scanner.*;

public class TwainIOException extends ScannerIOException implements TwainConstants{
  public TwainIOException(String msg){ // Need this. JNI wouldn't find IOException constructor.
    super(msg);
  }
}