/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.DaoGenericOperations;
import it.eng.spring.utility.SpringAppContext;

/**
 * factory di creazione dei dao
 * @author jravagnan
 *
 */
public class DaoFactory {
	
	@SuppressWarnings("rawtypes")
	public static DaoGenericOperations getDao(String nomeSpringDao){
		return (DaoGenericOperations) SpringAppContext.getContext().getBean(nomeSpringDao);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static DaoGenericOperations getDao(Class classeDao){
		return (DaoGenericOperations) SpringAppContext.getContext().getBean(classeDao);
	}
	

}
