/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityDmodellooggettoBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityIumodellooggettoBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityTrovamodoggettiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.oggettario.datasource.bean.OggettarioBean;
import it.eng.client.DmpkUtilityDmodellooggetto;
import it.eng.client.DmpkUtilityIumodellooggetto;
import it.eng.client.DmpkUtilityTrovamodoggetti;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "OggettarioDataSource")
public class OggettarioDataSource extends AbstractFetchDataSource<OggettarioBean> {
	
	@Override
	public String getNomeEntita() {
		return "oggettario";
	}
		
	@Override
	public PaginatorBean<OggettarioBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
				
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String oggetto = null;
		String nome = null;		
		String flgVersoRegistrazione = null;		
		Integer flgSoloModUtente = null;
		Integer flgIncludiAnnullati = null;
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if("oggettoFulltext".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						oggetto = String.valueOf(crit.getValue());									
					}
				}	
				else if("nome".equals(crit.getFieldName())) {
					nome = getTextFilterValue(crit);
				} 
				else if("flgVersoRegistrazione".equals(crit.getFieldName())) {
					flgVersoRegistrazione = getTextFilterValue(crit);
				}
				else if("flgSoloModUtente".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {	
						flgSoloModUtente = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}
				}
				else if("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						flgIncludiAnnullati = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}
				} 
			}			
		}
		
		DmpkUtilityTrovamodoggettiBean input = new DmpkUtilityTrovamodoggettiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOggettoin(oggetto);
		input.setCinomemodelloin(nome);
		input.setFlgversoregistrazionein(flgVersoRegistrazione);
		input.setFlgsolomodutentein(flgSoloModUtente);
		input.setFlgincludiannullatiin(flgIncludiAnnullati);		
		input.setCategoriaregin("PG");
		input.setColorderbyio(null);			
		input.setFlgsenzapaginazionein(new Integer(1));
		
		DmpkUtilityTrovamodoggetti trovamodoggetti = new DmpkUtilityTrovamodoggetti();
		StoreResultBean<DmpkUtilityTrovamodoggettiBean> output = trovamodoggetti.execute(getLocale(), loginBean, input);			
			
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		List<OggettarioBean> data = new ArrayList<OggettarioBean>();
		if (output.getResultBean().getListaxmlout() != null) {
			String xml = output.getResultBean().getListaxmlout();
			data = XmlListaUtility.recuperaLista(xml, OggettarioBean.class);
		}				
						
		PaginatorBean<OggettarioBean> lPaginatorBean = new PaginatorBean<OggettarioBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;		
	}
	
	@Override
	public OggettarioBean add(OggettarioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		DmpkUtilityIumodellooggettoBean input = new DmpkUtilityIumodellooggettoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCinomemodelloin(bean.getNome());
		input.setOggettoin(bean.getOggetto());
		input.setFlgxdocinentratain((bean.getFlgXRegInEntrata() != null && bean.getFlgXRegInEntrata()) ? new Integer("1") : new Integer("0"));
		input.setFlgxdocinuscitain((bean.getFlgXRegInUscita() != null && bean.getFlgXRegInUscita()) ? new Integer("1") : new Integer("0"));
		input.setFlgxdocinterniin((bean.getFlgXRegInterne() != null && bean.getFlgXRegInterne()) ? new Integer("1") : new Integer("0"));
		input.setFlgmodtipiregin(null);
		input.setFlgpubblicoin(bean.getFlgTipoModello() != null && "PB".equalsIgnoreCase(bean.getFlgTipoModello()) ? new Integer("1") : new Integer("0"));
		input.setNotemodelloin(bean.getNote());
		if (bean.getFlgTipoModello() != null && "UO".equalsIgnoreCase(bean.getFlgTipoModello())) {
			input.setIduoin(bean.getIdUoAssociata() != null && !"".equals(bean.getIdUoAssociata()) ? new BigDecimal(bean.getIdUoAssociata()) : null);
		}
		input.setFlgvisibsottouoin(bean.getFlgVisibileDaSottoUo() != null && bean.getFlgVisibileDaSottoUo() ? new Integer(1) : null);
		input.setFlggestsottouoin(bean.getFlgModificabileDaSottoUo() != null && bean.getFlgModificabileDaSottoUo() ? new Integer(1) : null);
		
		DmpkUtilityIumodellooggetto iumodellooggetto = new DmpkUtilityIumodellooggetto();
		StoreResultBean<DmpkUtilityIumodellooggettoBean> output = iumodellooggetto.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.setIdModello((String.valueOf(output.getResultBean().getIdmodoggettoio())));		
		return bean;
	}
	
	@Override
	public OggettarioBean update(OggettarioBean bean, OggettarioBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkUtilityIumodellooggettoBean input = new DmpkUtilityIumodellooggettoBean();
		input.setIdmodoggettoio(new BigDecimal(bean.getIdModello()));
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCinomemodelloin(bean.getNome());		
		input.setOggettoin(bean.getOggetto());
		input.setFlgxdocinentratain((bean.getFlgXRegInEntrata() != null && bean.getFlgXRegInEntrata()) ? new Integer("1") : new Integer("0"));
		input.setFlgxdocinuscitain((bean.getFlgXRegInUscita() != null && bean.getFlgXRegInUscita()) ? new Integer("1") : new Integer("0"));
		input.setFlgxdocinterniin((bean.getFlgXRegInterne() != null && bean.getFlgXRegInterne()) ? new Integer("1") : new Integer("0"));
		input.setFlgmodtipiregin(null);
		input.setFlgpubblicoin(bean.getFlgTipoModello() != null && "PB".equalsIgnoreCase(bean.getFlgTipoModello()) ? new Integer("1") : new Integer("0"));
		input.setNotemodelloin(bean.getNote());
		if (bean.getFlgTipoModello() != null && "UO".equalsIgnoreCase(bean.getFlgTipoModello())) {
			input.setIduoin(bean.getIdUoAssociata() != null && !"".equals(bean.getIdUoAssociata()) ? new BigDecimal(bean.getIdUoAssociata()) : null);
		}
		input.setFlgvisibsottouoin(bean.getFlgVisibileDaSottoUo() != null && bean.getFlgVisibileDaSottoUo() ? new Integer(1) :  new Integer("0"));
		input.setFlggestsottouoin(bean.getFlgModificabileDaSottoUo() != null && bean.getFlgModificabileDaSottoUo() ? new Integer(1) :  new Integer("0"));
	
		DmpkUtilityIumodellooggetto iumodellooggetto = new DmpkUtilityIumodellooggetto();
		StoreResultBean<DmpkUtilityIumodellooggettoBean> output = iumodellooggetto.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}
	
	@Override
	public OggettarioBean get(OggettarioBean bean)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkUtilityTrovamodoggettiBean input = new DmpkUtilityTrovamodoggettiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIdmodelloin(new BigDecimal(bean.getIdModello()));
		
		DmpkUtilityTrovamodoggetti trovamodoggetti = new DmpkUtilityTrovamodoggetti();
		StoreResultBean<DmpkUtilityTrovamodoggettiBean> output = trovamodoggetti.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		OggettarioBean result = null;	 
		if (output.getResultBean().getListaxmlout() != null) {
			String xml = output.getResultBean().getListaxmlout();
			List<OggettarioBean> listResult = XmlListaUtility.recuperaLista(xml, OggettarioBean.class);
			if(listResult.size() == 1) {
				result = listResult.get(0);
			}
		}
		
		return result;
	}
	
	@Override
	public OggettarioBean remove(OggettarioBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkUtilityDmodellooggettoBean input = new DmpkUtilityDmodellooggettoBean();
		input.setIdmodoggettoin(new BigDecimal(bean.getIdModello()));
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		DmpkUtilityDmodellooggetto dmodellooggetto = new DmpkUtilityDmodellooggetto();
		StoreResultBean<DmpkUtilityDmodellooggettoBean> output = dmodellooggetto.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}

}