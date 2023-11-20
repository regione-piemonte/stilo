/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

public interface TaskFlussoInterface extends LoadDetailInterface, BackDetailInterface {
	
	public Record getRecordEvento();
	public void salvaDatiProvvisorio();
	public void salvaDatiDefinitivo();
	public String getNomeTastoSalvaProvvisorio();
	public String getNomeTastoSalvaDefinitivo();
	public String getNomeTastoSalvaDefinitivo_2();
	public boolean hasDocumento();

}
