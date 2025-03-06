package it.eng.core.config;

import it.eng.core.annotation.Module;
import it.eng.core.business.HibernateUtil;

import org.apache.commons.beanutils.BeanUtilsBean2;

/**
 * <h3> Core module</h3> <br>
 * Questo modulo è una dipendenza comune a tutti moduli che hanno necessità di accedere al database del sistema IRIS <br>
 * Al suo interno sono implementate: 
 * <ul>
 * 	<li>Entity di Hibernate 3.6</li>
 *  <li>Utilità di accesso alla SessionFactory {@link HibernateUtil}</li>
 * </ul>
 * @author Rigo Michele
 * 
 */
@Module(name=CoreConfig.modulename, version="0.0.1-SNAPSHOT")
public class CoreConfig implements IModuleConfigurator{
public static final String modulename="Core";
	public void init() throws Exception {
		//comportamento di default dei converter beanutils
		BeanUtilsBean2.getInstance().getConvertUtils().register(false,false, 0);
		//TODO inizializzazione dei serializer
		
	}

	public void destroy() throws Exception {
		//TODO DA IMPLEMENTARE
	}	
}