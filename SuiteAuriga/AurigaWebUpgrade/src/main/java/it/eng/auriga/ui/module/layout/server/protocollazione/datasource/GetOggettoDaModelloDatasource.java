/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetoggettodamodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkUtilityGetoggettodamodello;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "GetOggettoDaModelloDatasource")
public class GetOggettoDaModelloDatasource extends AbstractServiceDataSource<ProtocollazioneBean, ProtocollazioneBean>{

	@Override
	public ProtocollazioneBean call(ProtocollazioneBean bean) throws Exception {		
		ProtocollazioneBean result = new ProtocollazioneBean();		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
					
		DmpkUtilityGetoggettodamodelloBean input = new DmpkUtilityGetoggettodamodelloBean();				
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setCinomemodelloin(bean.getCodRapidoOggetto());			
		input.setProvcimodelloin(null);
		input.setIdmodelloin(null);
		input.setFlgversoregistrazionein(bean.getFlgTipoProv());
		
		DmpkUtilityGetoggettodamodello lDmpkUtilityGetoggettodamodello = new DmpkUtilityGetoggettodamodello();
		StoreResultBean<DmpkUtilityGetoggettodamodelloBean> output = lDmpkUtilityGetoggettodamodello.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
			
		result.setOggetto(output.getResultBean().getOggettoout());
		
		return result;
	}
	
}
