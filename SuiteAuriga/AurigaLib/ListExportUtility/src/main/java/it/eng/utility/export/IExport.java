/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.utility.ui.module.core.server.bean.ExportBean;

public interface IExport {
	
	public void export(File file, ExportBean bean)throws Exception;
	
}
