/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.*;

public interface ResultSelection{

  boolean next() throws eng.database.exception.EngSqlNoApplException;

  String getElement(int i) throws eng.database.exception.EngSqlNoApplException;

  void addColumn(String obj);

  void addRow();

  void addRow(Vector row);

  Vector getRow(int index);

  boolean isEmpty();

}
