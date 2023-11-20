/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

public class UserUtil {

	public static void getInfoUtente(final UserUtilCallback lUserCallback){
		new OneCallGWTRestService<Record, Record>("ServiceRestUserUtil").call(new Record(), new ServiceCallback<Record>() {	    	
			public void execute(Record record) {
				LoginBean lLoginBean = new LoginBean();
				lLoginBean.setCodApplicazione(record.getAttributeAsString("codApplicazione"));
				lLoginBean.setIdApplicazione(record.getAttributeAsString("idApplicazione"));			
				lLoginBean.setUserid(record.getAttributeAsString("idUser"));
				lLoginBean.setIdUtente(record.getAttributeAsString("userid"));
				lLoginBean.setDenominazione(record.getAttributeAsString("denominazione"));
				lLoginBean.setCodFiscale(record.getAttributeAsString("codFiscale"));
				lLoginBean.setToken(record.getAttributeAsString("token"));
				lLoginBean.setIdUserLavoro(record.getAttributeAsString("idUserLavoro"));
				lLoginBean.setPrivilegi(record.getAttributeAsStringArray("privilegi"));
				String idUser = record.getAttributeAsString("idUser");
				lLoginBean.setIdUser(idUser != null && !"".equals(idUser) ? new BigDecimal(idUser) : null);
				lLoginBean.setUseridForPrefs(record.getAttributeAsString("useridForPrefs"));
				lLoginBean.setDelegaDenominazione(record.getAttributeAsString("delegaDenominazione"));
				lLoginBean.setDominio(record.getAttributeAsString("dominio"));
				lLoginBean.setSchema(record.getAttribute("schema"));
				lLoginBean.setLogoUtente(record.getAttributeAsString("logoUtente"));
				lLoginBean.setLinguaApplicazione(record.getAttributeAsString("linguaApplicazione"));				
				lUserCallback.execute(lLoginBean);
			}
		});
	}	
	
}
