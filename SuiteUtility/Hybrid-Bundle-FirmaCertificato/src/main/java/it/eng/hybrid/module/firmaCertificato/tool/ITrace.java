/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface ITrace {
  enum Level {
    TRACE, INFO, ERROR
  }
  
  public void trace(Level level, String message);

}
