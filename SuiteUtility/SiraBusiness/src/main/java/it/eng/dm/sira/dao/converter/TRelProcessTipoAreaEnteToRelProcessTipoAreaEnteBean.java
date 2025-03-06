package it.eng.dm.sira.dao.converter;

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.TAreaTematicaProcedimento;
import it.eng.dm.sira.entity.TRelProcessTipoAreaEnte;
import it.eng.dm.sira.entity.TTipologiaProcedimento;
import it.eng.dm.sira.entity.VMguOrganigramma;
import it.eng.dm.sira.service.bean.DominiVMguOrganigrammaBean;
import it.eng.dm.sira.service.bean.RelProcessTipoAreaEnteBean;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

public class TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean implements IBeanPopulate<TRelProcessTipoAreaEnte, RelProcessTipoAreaEnteBean> {

	private Session session;
	
	public TRelProcessTipoAreaEnteToRelProcessTipoAreaEnteBean(Session session) {
		this.session = session;
	}
	
	@Override
	public void populate(TRelProcessTipoAreaEnte src, RelProcessTipoAreaEnteBean dest) throws Exception {
		List<DominiVMguOrganigrammaBean> listaDomini = new ArrayList<DominiVMguOrganigrammaBean>(); 
		if(src.getIdProcess() != null) {
			dest.setNomeProcess(getNomeProcess(src.getIdProcess().longValue(), session));
		}
		if(src.getIdArea() != null) {
			TAreaTematicaProcedimento lTAreaTematicaProcedimento = (TAreaTematicaProcedimento) session.get(TAreaTematicaProcedimento.class, src.getIdArea());
			dest.setNomeArea(lTAreaTematicaProcedimento != null ? lTAreaTematicaProcedimento.getDescrizione() : null);
		}
		if(src.getIdTipologia() != null) {
			TTipologiaProcedimento lTTipologiaProcedimento = (TTipologiaProcedimento) session.get(TTipologiaProcedimento.class, src.getIdTipologia());
			dest.setNomeTipologia(lTTipologiaProcedimento != null ? lTTipologiaProcedimento.getDescrizione() : null);
		}
		if(StringUtils.isNotBlank(src.getIdDominio())) {
			StringTokenizer st = new StringTokenizer(src.getIdDominio(), ";");
			while(st.hasMoreTokens()) {
				String idDominio = st.nextToken();
				DominiVMguOrganigrammaBean dominio = new DominiVMguOrganigrammaBean();
				dominio.setIdDominioVMguOrganigramma(idDominio);
				VMguOrganigramma lVMguOrganigramma = (VMguOrganigramma) session.get(VMguOrganigramma.class, new Long(idDominio));
				dominio.setNomeDominioVMguOrganigramma(lVMguOrganigramma != null ? lVMguOrganigramma.getDescrizione() : null);
				listaDomini.add(dominio);
			}
		}
		dest.setListaDomini(listaDomini);
	}
	
	private String getNomeProcess(Long idProcess, Session session) {
		String hql = null;
		hql = "select t.nomeProcessType from DmtProcessTypes t where t.idProcessType = " + idProcess;					
		Query query = session.createQuery(hql);
		List<String> results = query.list();
		if(results != null && results.size() > 0) {
			return results.get(0);
		}		
		return null;
	}

	@Override
	public void populateForUpdate(TRelProcessTipoAreaEnte src, RelProcessTipoAreaEnteBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

}