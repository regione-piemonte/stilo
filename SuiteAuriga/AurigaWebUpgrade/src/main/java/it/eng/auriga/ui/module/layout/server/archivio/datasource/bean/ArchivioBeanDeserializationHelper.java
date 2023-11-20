/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;


public class ArchivioBeanDeserializationHelper extends ArchivioXmlBeanDeserializationHelper {

	public ArchivioBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
	}
	
	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {

		super.remapValues(obj, objValues);
		
		ArchivioBean bean = (ArchivioBean) obj;

		if(StringUtils.isNotBlank(bean.getInfo())) {
    		StringSplitterServer st = new StringSplitterServer(bean.getInfo(), "**");
    		int n = 0;
    		while(st.hasMoreTokens()) {
    			if(n == 0) {
    				bean.setNomeFascicolo(st.nextToken().toString());	        						
    			} else if(n == 1) {
    				bean.setAnnoFascicolo(new Integer(st.nextToken().toString()));				        			
    			} else if(n == 2) {
    				bean.setIdClassifica(st.nextToken().toString());
    			} else if(n == 3) {
    				bean.setIndiceClassifica(st.nextToken().toString());
    			} else if(n == 4) {
    				bean.setNroFascicolo(st.nextToken().toString());
    			} else if(n == 5) {
    				bean.setNroSottofascicolo(st.nextToken().toString());
    			} else if(n == 6) {
    				bean.setNroInserto(st.nextToken().toString());
    			} 
    			n++;
    		}			        				        		
		}
		if(bean.getEstremiInvioNotifiche() != null && !"".equals(bean.getEstremiInvioNotifiche())){
			bean.setEstremiInvioNotifiche(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(bean.getEstremiInvioNotifiche()));
		}
		bean.setFlgFascTitolario(StringUtils.isNotBlank(bean.getSegnatura()));
	}
}
