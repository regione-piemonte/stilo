/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OpzioniTimbroDocBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="LoadOpzioniTimbroDocDefault")
public class LoadOpzioniTimbroDocDefault extends AbstractServiceDataSource<OpzioniTimbroDocBean, OpzioniTimbroDocBean> {
	
	private static final Logger logger = Logger.getLogger(LoadOpzioniTimbroDocDefault.class);

	@Override
	public OpzioniTimbroDocBean call(OpzioniTimbroDocBean pInBean) throws Exception {
		
		OpzioniTimbroDocBean output = new OpzioniTimbroDocBean();
		OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = null;
		
		try{
			 lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			logger.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}
		
		String rotazioneTimbroBean = lOpzTimbroAutoDocRegBean != null && lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
				!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale";
		String posizioneTimbroBean =  lOpzTimbroAutoDocRegBean != null && lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
				!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn";
		String tipoPaginaTimbroBean = lOpzTimbroAutoDocRegBean != null && lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null ? 
				lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value() : "TUTTE";
		
		String rotazione = StringUtils.isNotBlank(pInBean.getRotazioneTimbro()) ? pInBean.getRotazioneTimbro() : rotazioneTimbroBean;
		String posizione = StringUtils.isNotBlank(pInBean.getPosizioneTimbro()) ? pInBean.getPosizioneTimbro() : posizioneTimbroBean;
		
		if(StringUtils.isNotBlank(pInBean.getTipoPaginaTimbro())){
			output.setTipoPaginaTimbro(pInBean.getTipoPaginaTimbro());
			if("intervallo".equals(pInBean.getTipoPaginaTimbro())){
				output.setPaginaDa(pInBean.getPaginaDa());
				output.setPaginaA(pInBean.getPaginaA());
			}
		} else{
			output.setTipoPaginaTimbro(tipoPaginaTimbroBean);
		}
		
		output.setRotazioneTimbro(rotazione);
		output.setPosizioneTimbro(posizione);
		
		return output;
	}

}