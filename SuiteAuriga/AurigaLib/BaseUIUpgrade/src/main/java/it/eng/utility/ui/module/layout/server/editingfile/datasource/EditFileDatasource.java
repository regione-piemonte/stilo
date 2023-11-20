/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.server.editingfile.datasource.bean.EditFileBean;

@Datasource(id = "EditFileDatasource")
public class EditFileDatasource extends AbstractServiceDataSource<EditFileBean, EditFileBean>{

	@Override
	public EditFileBean call(EditFileBean pInBean) throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		pInBean.setAlgoritmoImpronta(lDocumentConfiguration.getAlgoritmo().value());
		pInBean.setEncoding(lDocumentConfiguration.getEncoding().value());
		return pInBean;
	}

}
