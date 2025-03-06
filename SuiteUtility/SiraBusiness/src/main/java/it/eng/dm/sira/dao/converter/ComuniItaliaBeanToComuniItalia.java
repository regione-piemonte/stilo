package it.eng.dm.sira.dao.converter;

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.ComuniItalia;
import it.eng.dm.sira.entity.ComuniItaliaId;
import it.eng.dm.sira.service.bean.SiraComuniItaliaBean;

public class ComuniItaliaBeanToComuniItalia implements IBeanPopulate<SiraComuniItaliaBean, ComuniItalia> {

	@Override
	public void populate(SiraComuniItaliaBean src, ComuniItalia dest) throws Exception {
		if (src.getIstatCom() != null || src.getIstatProv() != null || src.getIstatReg() != null) {
			ComuniItaliaId id = new ComuniItaliaId();
			id.setIstatCom(src.getIstatCom());
			id.setIstatProv(src.getIstatProv());
			id.setIstatReg(src.getIstatReg());
			dest.setId(id);
		}
	}

	@Override
	public void populateForUpdate(SiraComuniItaliaBean src, ComuniItalia dest) throws Exception {
	}

}