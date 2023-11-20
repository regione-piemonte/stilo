/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocPrepararegdefregemergenzaBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocSetregdefregemergenzaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.registrazioniEmergenza.datasource.bean.ProtocollazioneRegEmergenzaBean;
import it.eng.client.DmpkRegistrazionedocPrepararegdefregemergenza;
import it.eng.client.DmpkRegistrazionedocSetregdefregemergenza;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id="ProtocollazioneRegEmergenzaDatasource")
public class ProtocollazioneRegEmergenzaDatasource extends AbstractFetchDataSource<ProtocollazioneRegEmergenzaBean>{	
	
	@Override
	public ProtocollazioneRegEmergenzaBean get(ProtocollazioneRegEmergenzaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocPrepararegdefregemergenzaBean input = new DmpkRegistrazionedocPrepararegdefregemergenzaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdregemergenzain(bean.getIdRegEmergenza());
		
		DmpkRegistrazionedocPrepararegdefregemergenza dmpkRegistrazionedocPrepararegdefregemergenza = new DmpkRegistrazionedocPrepararegdefregemergenza();
		
		StoreResultBean<DmpkRegistrazionedocPrepararegdefregemergenzaBean> output = dmpkRegistrazionedocPrepararegdefregemergenza.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotEmpty(output.getDefaultMessage())){
			throw new StoreException(output);
		}
		
		if(output.getResultBean().getDatiudxmlout() != null) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			DocumentoXmlOutBean lDocumentoXmlOutBean = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getDatiudxmlout(), DocumentoXmlOutBean.class);	
			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
			ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(bean, lProtocollazioneBean);			
		}
	
		return bean;			
	}	
		
	@Override
	public ProtocollazioneRegEmergenzaBean add(ProtocollazioneRegEmergenzaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocSetregdefregemergenzaBean input = new DmpkRegistrazionedocSetregdefregemergenzaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdregemergenzain(bean.getIdRegEmergenza());
		input.setIdudin(bean.getIdUd());
		
		DmpkRegistrazionedocSetregdefregemergenza dmpkRegistrazionedocSetregdefregemergenza = new DmpkRegistrazionedocSetregdefregemergenza();
		
		StoreResultBean<DmpkRegistrazionedocSetregdefregemergenzaBean> output = dmpkRegistrazionedocSetregdefregemergenza.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotEmpty(output.getDefaultMessage())){
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}
		
		return bean;					
	}	
	
	@Override
	public PaginatorBean<ProtocollazioneRegEmergenzaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		return null;
	}	

	@Override
	public ProtocollazioneRegEmergenzaBean update(ProtocollazioneRegEmergenzaBean bean, ProtocollazioneRegEmergenzaBean oldvalue) throws Exception {
	
		return null;	
	}
	
	@Override
	public ProtocollazioneRegEmergenzaBean remove(ProtocollazioneRegEmergenzaBean bean) throws Exception {

		return null;
	}	
	
}