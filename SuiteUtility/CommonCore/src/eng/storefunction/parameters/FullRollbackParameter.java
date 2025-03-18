/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import eng.storefunction.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/

import java.sql.*;


public class FullRollbackParameter extends StoreParameter {
			
	public FullRollbackParameter(int index, String rollback_type)
	{
		super(index, "FlgRollBckFullIn", ""+rollback_type, java.sql.Types.VARCHAR, IN );
	}
	
	public StoreParameter cloneMe()
	{
		return new FullRollbackParameter(index, getValue()+"");
	}
}
