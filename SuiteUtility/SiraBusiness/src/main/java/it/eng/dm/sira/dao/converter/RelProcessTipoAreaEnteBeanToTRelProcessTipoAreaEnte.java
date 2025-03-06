package it.eng.dm.sira.dao.converter;

import it.eng.core.business.converter.IBeanPopulate;
import it.eng.dm.sira.entity.TRelProcessTipoAreaEnte;
import it.eng.dm.sira.service.bean.RelProcessTipoAreaEnteBean;

import org.apache.commons.lang3.StringUtils;

public class RelProcessTipoAreaEnteBeanToTRelProcessTipoAreaEnte implements IBeanPopulate<RelProcessTipoAreaEnteBean, TRelProcessTipoAreaEnte> {

	@Override
	public void populate(RelProcessTipoAreaEnteBean src, TRelProcessTipoAreaEnte dest) throws Exception {
		String idDominioList = ""; 
		if(src.getListaDomini() != null) {
			for(int i = 0; i < src.getListaDomini().size(); i++) {
				String idDominio = src.getListaDomini().get(i).getIdDominioVMguOrganigramma();
				if(StringUtils.isNotBlank(idDominioList)) {
					idDominioList += ";";
				}
				idDominioList += idDominio;
				
			}
		}
		dest.setIdDominio(idDominioList);
	}

	@Override
	public void populateForUpdate(RelProcessTipoAreaEnteBean src, TRelProcessTipoAreaEnte dest) throws Exception {
		String idDominioList = ""; 
		if(src.getListaDomini() != null) {
			for(int i = 0; i < src.getListaDomini().size(); i++) {
				String idDominio = src.getListaDomini().get(i).getIdDominioVMguOrganigramma();
				if(StringUtils.isNotBlank(idDominioList)) {
					idDominioList += ";";
				}
				idDominioList += idDominio;
				
			}
		}
		dest.setIdDominio(idDominioList);
	}

}