/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocAnnullaregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocEliminazionerichannregBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocRichiestaannregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.RegistrazioneDocBean;
import it.eng.client.DmpkRegistrazionedocAnnullareg;
import it.eng.client.DmpkRegistrazionedocEliminazionerichannreg;
import it.eng.client.DmpkRegistrazionedocRichiestaannreg;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AurigaRegistrazioneDocDataSource")
public class RegistrazioneDocDataSource extends AbstractDataSource<RegistrazioneDocBean, RegistrazioneDocBean> {	
	
	public RegistrazioneDocBean richAnnullamentoReg(RegistrazioneDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkRegistrazionedocRichiestaannregBean input = new DmpkRegistrazionedocRichiestaannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCodCategoria());
		input.setSiglaregin(bean.getSigla());
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnno()) ? new Integer(bean.getAnno()) : null);
		input.setNumregin(StringUtils.isNotBlank(bean.getNro()) ? new Integer(bean.getNro()) : null);
		input.setMotiviannin(bean.getMotiviRichAnnullamento());
		DmpkRegistrazionedocRichiestaannreg dmpkRegistrazionedocRichiestaannreg = new DmpkRegistrazionedocRichiestaannreg();
		StoreResultBean<DmpkRegistrazionedocRichiestaannregBean> output = dmpkRegistrazionedocRichiestaannreg.execute(getLocale(), loginBean, input);
		if(output.getDefaultMessage() != null) {
			bean.setErrorMessage(output.getDefaultMessage());
		}
		return bean;
	}

	public RegistrazioneDocBean modificaRichAnnullamentoReg(RegistrazioneDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkRegistrazionedocRichiestaannregBean input = new DmpkRegistrazionedocRichiestaannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCodCategoria());
		input.setSiglaregin(bean.getSigla());
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnno()) ? new Integer(bean.getAnno()) : null);
		input.setNumregin(StringUtils.isNotBlank(bean.getNro()) ? new Integer(bean.getNro()) : null);
		input.setMotiviannin(bean.getMotiviRichAnnullamento());
		DmpkRegistrazionedocRichiestaannreg dmpkRegistrazionedocRichiestaannreg = new DmpkRegistrazionedocRichiestaannreg();		
		StoreResultBean<DmpkRegistrazionedocRichiestaannregBean> output = dmpkRegistrazionedocRichiestaannreg.execute(getLocale(), loginBean, input);
		if(output.getDefaultMessage() != null) {
			bean.setErrorMessage(output.getDefaultMessage());
		}
		return bean;
	}
	
	public RegistrazioneDocBean eliminaRichAnnullamentoReg(RegistrazioneDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkRegistrazionedocEliminazionerichannregBean input = new DmpkRegistrazionedocEliminazionerichannregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCodCategoria());
		input.setSiglaregin(bean.getSigla());
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnno()) ? new Integer(bean.getAnno()) : null);
		input.setNumregin(StringUtils.isNotBlank(bean.getNro()) ? new Integer(bean.getNro()) : null);		
		DmpkRegistrazionedocEliminazionerichannreg dmpkRegistrazionedocEliminazionerichannreg = new DmpkRegistrazionedocEliminazionerichannreg();
		StoreResultBean<DmpkRegistrazionedocEliminazionerichannregBean> output = dmpkRegistrazionedocEliminazionerichannreg.execute(getLocale(), loginBean, input);
		if(output.getDefaultMessage() != null) {
			bean.setErrorMessage(output.getDefaultMessage());
		}
		return bean;
	}
	
	public RegistrazioneDocBean annullamentoReg(RegistrazioneDocBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkRegistrazionedocAnnullaregBean input = new DmpkRegistrazionedocAnnullaregBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCodCategoria());
		input.setSiglaregin(bean.getSigla());
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnno()) ? new Integer(bean.getAnno()) : null);
		input.setNumregin(StringUtils.isNotBlank(bean.getNro()) ? new Integer(bean.getNro()) : null);		
		DmpkRegistrazionedocAnnullareg dmpkRegistrazionedocAnnullareg = new DmpkRegistrazionedocAnnullareg();
		StoreResultBean<DmpkRegistrazionedocAnnullaregBean> output = dmpkRegistrazionedocAnnullareg.execute(getLocale() ,loginBean, input);
		if(output.getDefaultMessage() != null) {
			bean.setErrorMessage(output.getDefaultMessage());
		}
		return bean;
	}

	@Override
	public RegistrazioneDocBean add(RegistrazioneDocBean bean) 
	throws Exception {			
		
		return null;
	}

	@Override
	public RegistrazioneDocBean get(RegistrazioneDocBean bean) 
	throws Exception {		
		
		return null;
	}
	
	@Override
	public RegistrazioneDocBean remove(RegistrazioneDocBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public RegistrazioneDocBean update(RegistrazioneDocBean bean,
			RegistrazioneDocBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<RegistrazioneDocBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(RegistrazioneDocBean bean)
	throws Exception {
		
		return null;
	}

}
