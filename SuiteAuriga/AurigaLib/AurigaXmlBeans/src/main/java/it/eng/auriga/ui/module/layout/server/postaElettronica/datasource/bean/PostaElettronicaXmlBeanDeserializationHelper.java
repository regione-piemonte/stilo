/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.xml.DeserializationHelper;

public class PostaElettronicaXmlBeanDeserializationHelper extends DeserializationHelper {

	public PostaElettronicaXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		PostaElettronicaXmlBean bean = (PostaElettronicaXmlBean)obj; 
		
		bean.setCorpo(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(bean.getCorpo()));
		
		if(StringUtils.isNotBlank(bean.getListEstremiRegProtAssociati()) && 
				StringUtils.isNotBlank(bean.getListIdUdProtAssociati())) {							
			String [] splitEstremiRegProtAssociati = String.valueOf(bean.getListEstremiRegProtAssociati()).split(";"); //colonna 46
			String [] splitIdUdProtAssociati = String.valueOf(bean.getListIdUdProtAssociati()).split(";"); //colonna 47
			Map<String, String> estremiRegProtAssociati = new HashMap<String, String>();						
			for(int k=0; k<splitIdUdProtAssociati.length;k++) {
				estremiRegProtAssociati.put(splitIdUdProtAssociati[k], splitEstremiRegProtAssociati[k]);
			}						
			if(bean.getFlgStatoProt() != null && !"".equals(bean.getFlgStatoProt())) {										
				bean.setEstremiProtCollegati(bean.getListEstremiRegProtAssociati());													
			}

		}
		
		if(StringUtils.isNotBlank(bean.getListEstremiRegProtAssociati()) 
				&& StringUtils.isNotBlank(bean.getListIdUdProtAssociati())) {							
			String [] splitEstremiRegProtAssociati = String.valueOf(bean.getListEstremiRegProtAssociati()).split(";");
			String [] splitIdUdProtAssociati = String.valueOf(bean.getListIdUdProtAssociati()).split(";");
			Map<String, String> estremiRegProtAssociati = new HashMap<String, String>();													
			for(int k=0; k<splitIdUdProtAssociati.length;k++) {
				estremiRegProtAssociati.put(splitIdUdProtAssociati[k], splitEstremiRegProtAssociati[k]);
			}
			bean.setEstremiDocInviato(bean.getListEstremiRegProtAssociati());														
		}
		
	}
}
