/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetcompgruppoassdestudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.MembriGruppoUdBean;
import it.eng.client.DmpkRepositoryGuiGetcompgruppoassdestud;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="MembriGruppoUdDataSource")
public class MembriGruppoUdDataSource extends AbstractFetchDataSource<MembriGruppoUdBean> {
	
	private static final Logger log = Logger.getLogger(MembriGruppoUdDataSource.class);

	@Override
	public PaginatorBean<MembriGruppoUdBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		List<MembriGruppoUdBean> data = new ArrayList<MembriGruppoUdBean>();
		
		DmpkRepositoryGuiGetcompgruppoassdestudBean input = new DmpkRepositoryGuiGetcompgruppoassdestudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setIdudin(StringUtils.isNotBlank(getExtraparams().get("idUd")) ? new BigDecimal(getExtraparams().get("idUd")) : null);
		input.setIdgrupposoggestin(StringUtils.isNotBlank(getExtraparams().get("gruppo")) ? new BigDecimal(getExtraparams().get("gruppo")) : null);	
		input.setFlgassdestin(getExtraparams().get("flgAssCondDest"));
		
		DmpkRepositoryGuiGetcompgruppoassdestud dmpkRepositoryGuiGetcompgruppoassdestud = 
				new DmpkRepositoryGuiGetcompgruppoassdestud();
		StoreResultBean<DmpkRepositoryGuiGetcompgruppoassdestudBean> output = dmpkRepositoryGuiGetcompgruppoassdestud.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getListaxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					MembriGruppoUdBean bean = new MembriGruppoUdBean();
					bean.setIdRubrica(v.get(0));
					bean.setTipoSoggetto(v.get(1));
					bean.setDenominazione(v.get(2));
					bean.setCodRapido(v.get(5));
					bean.setCodFiscalePIVA(v.get(6));
					bean.setIndirizzo(v.get(7));		
					bean.setPresaInCarico(v.get(9));
					bean.setMessAltPresaInCarico(v.get(10));
					data.add(bean);
				}
			}
		}
		
		PaginatorBean<MembriGruppoUdBean> lPaginatorBean = new PaginatorBean<MembriGruppoUdBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		
		return lPaginatorBean;
	}

}