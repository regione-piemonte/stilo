package it.eng.suiteutility.module.mimedb.beanconverters;


import it.eng.core.business.converter.IBeanPopulate;
import it.eng.suiteutility.module.mimedb.beans.FormatoDigitaleBean;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;

import org.hibernate.Session;

public class FormatoDigitaleBeanToTAnagFormatiDigConverter implements IBeanPopulate<FormatoDigitaleBean, TAnagFormatiDig> {

	@SuppressWarnings("unused")
	private Session session;
	
	public FormatoDigitaleBeanToTAnagFormatiDigConverter(Session session) {
		this.session = session;
	}
	
	public void populate(FormatoDigitaleBean src, TAnagFormatiDig dest) throws Exception {		
		//BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, src);

//		/* Foreign key
//		 * Identificativo del record di dizionario che rappresenta il registro internazionale di formati (PRONOM, UDFR...) 
//		 * da cui è presa l'identificativo del formato contenuto nel campo ID_IN_REGISTRO_FMT
//		 */
//		if(src.getIdRecDizRegistroFmt() != null) {
//			TValDizionario tvaldictByRegistro = new TValDizionario();
//			tvaldictByRegistro.setIdRecDiz(src.getIdRecDizRegistroFmt());	
//			dest.setTValDizionarioByIdRecDizRegistroFmt(tvaldictByRegistro);
//		}
//		
//		/* Foreign key
//		 * Identificativo del record di dizionario che esprime la classificazione/tipo del formato: word processor, image (vector) ecc
//		 */
//		if(src.getIdRecDizTipoFmt() != null) {
//			TValDizionario tvaldictByTipo = new TValDizionario();
//			tvaldictByTipo.setIdRecDiz(src.getIdRecDizTipoFmt());
//			dest.setTValDizionarioByIdRecDizTipoFmt(tvaldictByTipo);
//		}
	}
	
	public void populateForUpdate(FormatoDigitaleBean src, TAnagFormatiDig dest) throws Exception {
		
		/* Se è stata modificata la foreign key
		 * Identificativo del record di dizionario che rappresenta il registro internazionale di formati (PRONOM, UDFR...) 
		 * da cui è presa l'identificativo del formato contenuto nel campo ID_IN_REGISTRO_FMT
		 */
//		if(src.hasPropertyBeenModified("idRecDizRegistroFmt")) {
//			if(src.getIdRecDizRegistroFmt()!=null) {
//				TValDizionario tvaldictByRegistro = new TValDizionario();
//				tvaldictByRegistro.setIdRecDiz(src.getIdRecDizRegistroFmt());
//				dest.setTValDizionarioByIdRecDizRegistroFmt(tvaldictByRegistro);
//			}
//			else {
//				dest.setTValDizionarioByIdRecDizRegistroFmt(null);
//			}	
//		}
//		
//		/* Se è stata modificata la foreign key
//		 * Identificativo del record di dizionario che esprime la classificazione/tipo del formato: word processor, image (vector) ecc
//		 */
//		if(src.hasPropertyBeenModified("idRecDizTipoFmt")) {
//			if(src.getIdRecDizRegistroFmt()!=null) {
//				TValDizionario tvaldictByTipo = new TValDizionario();
//				tvaldictByTipo.setIdRecDiz(src.getIdRecDizTipoFmt());
//				dest.setTValDizionarioByIdRecDizTipoFmt(tvaldictByTipo);
//			}
//			else {
//				dest.setTValDizionarioByIdRecDizTipoFmt(null);
//			}	
//		}
	}

}
