/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoadfirmevistiattoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.DettaglioFirmeVistiAttoBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.FirmeVistiAttoXmlBean;
import it.eng.client.DmpkRepositoryGuiLoadfirmevistiatto;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author dbe4235
 *
 */

@Datasource(id="DettaglioFirmeVisteAttoDataSource")
public class DettaglioFirmeVisteAttoDataSource extends AbstractServiceDataSource<DettaglioFirmeVistiAttoBean, DettaglioFirmeVistiAttoBean> {
	
	private static final Logger log = Logger.getLogger(DettaglioFirmeVisteAttoDataSource.class);

	@Override
	public DettaglioFirmeVistiAttoBean call(DettaglioFirmeVistiAttoBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
	
		DettaglioFirmeVistiAttoBean result = new DettaglioFirmeVistiAttoBean();
		
		DmpkRepositoryGuiLoadfirmevistiattoBean input = new DmpkRepositoryGuiLoadfirmevistiattoBean();
		String idUdAtto = StringUtils.isNotBlank(getExtraparams().get("idUdAtto")) ? getExtraparams().get("idUdAtto") : null;
		input.setIdudin(idUdAtto != null && !"".equalsIgnoreCase(idUdAtto) ? new BigDecimal(idUdAtto) : null);
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		DmpkRepositoryGuiLoadfirmevistiatto dmpkRepositoryGuiLoadfirmevistiatto = new DmpkRepositoryGuiLoadfirmevistiatto();
		StoreResultBean<DmpkRepositoryGuiLoadfirmevistiattoBean> output = dmpkRepositoryGuiLoadfirmevistiatto.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getListafirmexmlout() != null) {
			
			result.setEstremiAtto(output.getResultBean().getEstremiattoout());

			List<FirmeVistiAttoXmlBean> data = new ArrayList<FirmeVistiAttoXmlBean>();
			StringReader sr = new StringReader(output.getResultBean().getListafirmexmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																
					FirmeVistiAttoXmlBean lFirmeVistiAttoXmlBean = new FirmeVistiAttoXmlBean();
					lFirmeVistiAttoXmlBean.setIdUd(idUdAtto);
					lFirmeVistiAttoXmlBean.setDataFirma(v.get(0) != null ?  new SimpleDateFormat(FMT_STD_DATA).parse(v.get(0)) : null);
					lFirmeVistiAttoXmlBean.setTipoFirma(v.get(1));
					lFirmeVistiAttoXmlBean.setFirmatario(v.get(2));
					lFirmeVistiAttoXmlBean.setRuolo(v.get(3));
					
					data.add(lFirmeVistiAttoXmlBean);
		   		}			
				result.setListaFirmeVisti(data);
			}					
		}
		
		//test(result);
		
		return result;
	}

	private void test(DettaglioFirmeVistiAttoBean result) {
		result.setEstremiAtto("test daniele");
		FirmeVistiAttoXmlBean lFirmeVistiAttoXmlBean = new FirmeVistiAttoXmlBean();
		lFirmeVistiAttoXmlBean.setIdUd("1684");
		lFirmeVistiAttoXmlBean.setDataFirma(null);
		lFirmeVistiAttoXmlBean.setTipoFirma("D");
		lFirmeVistiAttoXmlBean.setFirmatario("Daniele");
		lFirmeVistiAttoXmlBean.setRuolo("A");
		List<FirmeVistiAttoXmlBean> data = new ArrayList<FirmeVistiAttoXmlBean>();
		data.add(lFirmeVistiAttoXmlBean);
		result.setListaFirmeVisti(data);
	}	

}
