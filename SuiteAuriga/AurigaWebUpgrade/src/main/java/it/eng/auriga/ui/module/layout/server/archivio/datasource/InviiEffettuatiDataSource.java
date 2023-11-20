/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationGetinviinotificheannullabiliBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.InvioEffettuatoBean;
import it.eng.client.DmpkCollaborationGetinviinotificheannullabili;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "InviiEffettuatiDataSource")
public class InviiEffettuatiDataSource extends AbstractFetchDataSource<InvioEffettuatoBean> {

	@Override
	public PaginatorBean<InvioEffettuatoBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
						
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// leggo il paramatro di input
		String flgUdFolder = getExtraparams().get("flgUdFolder");		
		String idUdFolder = getExtraparams().get("idUdFolder");
		
		String flgInvioNotifica = null;
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("flgInvioNotifica".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						flgInvioNotifica = String.valueOf(crit.getValue());
					}
				} 
			}
		}			

		DmpkCollaborationGetinviinotificheannullabiliBean input = new DmpkCollaborationGetinviinotificheannullabiliBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtypeobjsentin(StringUtils.isNotBlank(flgUdFolder) ? flgUdFolder : null);
		input.setIdobjsentin(StringUtils.isNotBlank(idUdFolder) ? new BigDecimal(idUdFolder) : null);
		input.setFlginvionotificain(flgInvioNotifica);
		
		DmpkCollaborationGetinviinotificheannullabili dmpkCollaborationGetinviinotificheannullabili = new DmpkCollaborationGetinviinotificheannullabili();
		StoreResultBean<DmpkCollaborationGetinviinotificheannullabiliBean> output = dmpkCollaborationGetinviinotificheannullabili.execute(getLocale(),loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException(output);						
		}
		
		List<InvioEffettuatoBean> listaInviiEffettuati = new ArrayList<InvioEffettuatoBean>();					
		if(output.getResultBean().getInviinotifichexmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getInviinotifichexmlout());
			// Lista con le registrazioni che sono possibili duplicati della registrazione che si sta per effettuare (XML conforme a schema LISTA_STD.xsd)
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       		InvioEffettuatoBean invioEffettuato = new InvioEffettuatoBean();
			       		invioEffettuato.setFlgUdFolder(flgUdFolder);
			       		invioEffettuato.setIdUdFolder(idUdFolder);
			       		invioEffettuato.setFlgInvioNotifica(v.get(0)); //colonna 1 dell'xml	
			       		invioEffettuato.setIdInvioNotifica(v.get(1)); //colonna 2 dell'xml	 	
			       		invioEffettuato.setTipoMittente(v.get(2)); //colonna 3 dell'xml
			       		invioEffettuato.setIdMittente(v.get(3)); //colonna 4 dell'xml	
			       		invioEffettuato.setDesMittente(v.get(4)); //colonna 5 dell'xml
			       		invioEffettuato.setDesUteInvioNotifica(v.get(5)); //colonna 6 dell'xml
			       		invioEffettuato.setTsInvioNotifica(StringUtils.isNotBlank(v.get(6)) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(6)) : null); //colonna 7 dell'xml			       		
			       		invioEffettuato.setTipoDestinatario(v.get(7)); //colonna 8 dell'xml
			       		invioEffettuato.setIdDestinatario(v.get(8)); //colonna 9 dell'xml
			       		invioEffettuato.setFlgInclSottoUoDest(v.get(9)); //colonna 10 dell'xml
			       		invioEffettuato.setVsLivelloSoDest(v.get(10)); //colonna 11 dell'xml	
			       		invioEffettuato.setVsIdUoDest(v.get(11)); //colonna 12 dell'xml
			       		invioEffettuato.setVsCodTipoUoDest(v.get(12)); //colonna 13 dell'xml
			       		invioEffettuato.setDesDestinatario(v.get(13)); //colonna 14 dell'xml
			       		invioEffettuato.setCodMotivo(v.get(14)); //colonna 15 dell'xml
			       		invioEffettuato.setDesMotivo(v.get(15)); //colonna 16 dell'xml
			       		invioEffettuato.setLivPriorita(StringUtils.isNotBlank(v.get(16)) ? new Integer(v.get(16)) : null); //colonna 17 dell'xml
			       		invioEffettuato.setMessaggio(v.get(17)); //colonna 18 dell'xml
//			       		invioEffettuato.setTsDecorrenzaInvio(StringUtils.isNotBlank(v.get(18)) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(18)) : null); //colonna 19 dell'xml			       		
			       		invioEffettuato.setPermessiXDest(v.get(19)); //colonna 20 dell'xml
			       		invioEffettuato.setCodRuoloDest(v.get(20)); //colonna 21 dell'xml
			       		invioEffettuato.setDesRuoloDest(v.get(21)); //colonna 22 dell'xml
			       		invioEffettuato.setIdFolderApp(v.get(22)); //colonna 23 dell'xml
			       		invioEffettuato.setFlgConContenuti(v.get(23)); //colonna 24 dell'xml
			       		invioEffettuato.setFlgAnnullabile(v.get(24)); //colonna 25 dell'xml
			       		invioEffettuato.setMotivoNonAnnullabile(v.get(25)); //colonna 26 dell'xml
			       		invioEffettuato.setCodiceRapido(v.get(26)); //colonna 27 dell'xml
			       		listaInviiEffettuati.add(invioEffettuato);		        		
			   		}							
			}	
		}
		
		PaginatorBean<InvioEffettuatoBean> lPaginatorBean = new PaginatorBean<InvioEffettuatoBean>();
		lPaginatorBean.setData(listaInviiEffettuati);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaInviiEffettuati.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaInviiEffettuati.size());
		
		return lPaginatorBean;
	}	
	
	@Override
	public InvioEffettuatoBean get(InvioEffettuatoBean bean) throws Exception {		
		return null;
	}
	
	@Override
	public InvioEffettuatoBean remove(InvioEffettuatoBean bean)
	throws Exception {
		return null;
	}
	
	@Override
	public InvioEffettuatoBean add(InvioEffettuatoBean bean) 
	throws Exception {
		return bean;
	}

	@Override
	public InvioEffettuatoBean update(InvioEffettuatoBean bean,
			InvioEffettuatoBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(InvioEffettuatoBean bean)
	throws Exception {
		return null;
	}

}
