/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocRichiestaaccessodocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoDocumento.datasource.bean.RichiestaAccessoDocBean;
import it.eng.client.DmpkRegistrazionedocRichiestaaccessodoc;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author Antonio Peluso
 *
 */

@Datasource(id = "RichiestaAccessoDocDatasource")
public class RichiestaAccessoDocDatasource extends AbstractServiceDataSource<RichiestaAccessoDocBean, RichiestaAccessoDocBean>{

	private static Logger mLogger = Logger.getLogger(RichiestaAccessoDocDatasource.class);

	@Override
	public RichiestaAccessoDocBean call(RichiestaAccessoDocBean bean) throws Exception {

		RichiestaAccessoDocBean beanOut = new RichiestaAccessoDocBean();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocRichiestaaccessodocBean input = new DmpkRegistrazionedocRichiestaaccessodocBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnno()) ? new Integer(bean.getAnno()) : null);
		input.setCodcategoriaregin(bean.getTipo());
		input.setMotivoin(bean.getMotivazioni());
		input.setNumregin(StringUtils.isNotBlank(bean.getNumero()) ? new BigDecimal(bean.getNumero()) : null);
		input.setTipoaccessoin(bean.getTipoAccesso());
		input.setSiglaregin(bean.getSiglaRegistro());
		input.setSubnumregin(bean.getSub());

		DmpkRegistrazionedocRichiestaaccessodoc service = new DmpkRegistrazionedocRichiestaaccessodoc();
		StoreResultBean<DmpkRegistrazionedocRichiestaaccessodocBean> output = service.execute(getLocale(), loginBean, input);
		
		boolean storeInError = false;
		if (output.isInError()) {
			storeInError = true;
			String storeErrorMesssage = StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "Errore durante il recupero del documento";
			mLogger.error(storeErrorMesssage);
			beanOut.setStoreErrorMessage(storeErrorMesssage);
		} 
		
		DmpkRegistrazionedocRichiestaaccessodocBean resultBean = output.getResultBean();
		
		if(resultBean.getIdudout()!=null){
			beanOut.setIdUd(resultBean.getIdudout().toString());
		}else {
			if(!storeInError) {
				mLogger.error("La store DmpkRegistrazionedocRichiestaaccessodoc ha ritornato idUd null");
				throw new Exception("Errore durante il recupero del documento");
			}
		} 
		
		return beanOut;
	}

}