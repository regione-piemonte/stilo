package it.eng.dm.sira.dao.converter;

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.ComuniItalia;
import it.eng.dm.sira.service.bean.SiraComuniItaliaBean;

public class ComuniItaliaToComuniItaliaBean implements IBeanPopulate<ComuniItalia, SiraComuniItaliaBean> {

	@Override
	public void populate(ComuniItalia src, SiraComuniItaliaBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIstatCom(src.getId().getIstatCom());
			dest.setIstatProv(src.getId().getIstatProv());
			dest.setIstatReg(src.getId().getIstatReg());
		}
	}

	@Override
	public void populateForUpdate(ComuniItalia src, SiraComuniItaliaBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

}