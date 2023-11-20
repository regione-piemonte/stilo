/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Operation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.entity.TRelAlboAvbVsAuriga;
import it.eng.core.business.DaoGenericOperations;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import it.eng.core.annotation.Service;

@Service(name = "DaoTRelAlboAvbVsAuriga")
public class DaoTRelAlboAvbVsAuriga extends DaoGenericOperations<TRelAlboAvbVsAuriga>
{
    private static final Logger log = Logger.getLogger(DaoTRelAlboAvbVsAuriga.class);
    
    public DaoTRelAlboAvbVsAuriga() {}
    
    @Operation(name = "getIdAttoAvb")
    public BigDecimal getIdAttoAvb(AurigaLoginBean pAurigaLoginBean, BigDecimal idAttoAuriga) throws Exception {
        DaoTRelAlboAvbVsAuriga.log.debug("Effettuo la query sulla tabella T_REL_ALBO_AVB_VS_AURIGA");
        TRelAlboAvbVsAuriga result = null;
        SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
        Session session = null;
        try {
            session = HibernateUtil.begin();
            final Criteria criteria = session.createCriteria(TRelAlboAvbVsAuriga.class).add(Restrictions.eq("idTipoAttoAuriga", idAttoAuriga));
            result = (TRelAlboAvbVsAuriga) criteria.uniqueResult();
            DaoTRelAlboAvbVsAuriga.log.debug(("result : " + (result != null)));
        }
        catch (Exception e) {
            DaoTRelAlboAvbVsAuriga.log.error("Errore in getIdAttoAvb: ", e);
            throw e;
        }
        finally {
            HibernateUtil.release(session);
        }
        return result.getIdTipoAttoAvb();
    }

	@Override
	public TRelAlboAvbVsAuriga save(TRelAlboAvbVsAuriga bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TPagingList<TRelAlboAvbVsAuriga> search(TFilterFetch<TRelAlboAvbVsAuriga> filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TRelAlboAvbVsAuriga update(TRelAlboAvbVsAuriga bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(TRelAlboAvbVsAuriga bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forcedelete(TRelAlboAvbVsAuriga bean) throws Exception {
		// TODO Auto-generated method stub
		
	}

}