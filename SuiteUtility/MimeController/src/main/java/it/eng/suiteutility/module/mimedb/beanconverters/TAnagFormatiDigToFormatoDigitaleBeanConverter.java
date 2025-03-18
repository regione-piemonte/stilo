/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.core.business.converter.IBeanPopulate;
import it.eng.suiteutility.module.mimedb.beans.FormatoDigitaleBean;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;

public class TAnagFormatiDigToFormatoDigitaleBeanConverter implements IBeanPopulate<TAnagFormatiDig, FormatoDigitaleBean> {

	public void populate(TAnagFormatiDig src, FormatoDigitaleBean dest) throws Exception {
		//BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, src);

//		//Setto i valori delle due foreign key nel bean FormatoDigitaleBean
//		if(src.getTValDizionarioByIdRecDizRegistroFmt()!=null) {
//			dest.setIdRecDizRegistroFmt(src.getTValDizionarioByIdRecDizRegistroFmt().getIdRecDiz());
//		}
//		
//		if(src.getTValDizionarioByIdRecDizTipoFmt()!=null) {
//			dest.setIdRecDizTipoFmt(src.getTValDizionarioByIdRecDizTipoFmt().getIdRecDiz());
//		}
	}
	

	public void populateForUpdate(TAnagFormatiDig src, FormatoDigitaleBean dest)
			throws Exception {
		// TODO Auto-generated method stub
	}
}
