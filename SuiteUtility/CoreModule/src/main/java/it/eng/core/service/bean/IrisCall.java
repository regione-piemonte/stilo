package it.eng.core.service.bean;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import com.esotericsoftware.reflectasm.MethodAccess;

import it.eng.core.annotation.Operation;
import it.eng.core.audit.AuditHelper;
import it.eng.core.business.exception.ServiceException;
import it.eng.core.config.ConfigUtil;

/**
 * Classe centralizzata dalla quale passano tutte le chiamate ai servizi esposti dalla business TODO ottimizzare le chimate a Reflections
 */
public class IrisCall {

	private static Logger log = Logger.getLogger(IrisCall.class);
	private static Reflections reflections = null;

	/**
	 * Cancella i dati caricati in reflections
	 */
	public static synchronized void clearReflections() {
		reflections = null;
	}

	/**
	 * Ritorna le classi in reflection
	 * 
	 * @return
	 */
	public static synchronized Reflections getReflectionService() {
		if (reflections == null) {
			reflections = new Reflections(ConfigUtil.getServicePackage());
			// reflections = new Reflections("it.eng.iris", new
			// TypeAnnotationsScanner().filterResultsBy(new Predicate<String>()
			// {
			// @Override
			// public boolean apply(String input) {
			// System.out.println(input);
			// if(input.equals(Service.class.getName())){
			// return true;
			// }else{
			// return false;
			// }
			// }
			// }));

			// try {
			// XmlServiceSerialize.settingContext(reflections);
			// } catch (JAXBException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}
		return reflections;
	}

	/**
	 * Ritorna il metodo da invocare
	 * 
	 * @param servicename
	 * @param operationname
	 * @return
	 */
	public static ServiceBean getOperationServiceBean(final String servicename, String operationname) throws Exception {

		ServiceBean servicebean = new ServiceBean();

		// Set<Class<?>> services =
		// getReflectionService().getTypesAnnotatedWith(new Service() {
		// @Override
		// public Class<? extends Annotation> annotationType() {
		// return Service.class;
		// }
		// @Override
		// public String name() {
		// return servicename;
		// }
		// });
		Class<?> serviceclass = MetadataRegistry.getInstance().getService(servicename);
		if (serviceclass == null) {
			throw new Exception("Servizio " + servicename + " non configurato!");
		}

		// if(services.size()>1){
		// throw new Exception("Servizio "+servicename
		// +" configurato più volte!");
		// }

		// Recupero l'operazione di chiamata

		Method[] methods = serviceclass.getMethods();
		Method operation = null;
		for (Method method : methods) {
			if (method.getAnnotation(Operation.class) != null) {
				if (method.getAnnotation(Operation.class).name().equalsIgnoreCase(operationname)) {
					operation = method;
					// se ho il metodo con i tipi dei parametri a Object (che è quello che fa riferimento al tipo generico) vado avanti col ciclo e lo
					// sovrascrivo eventualmente con quello che ha i tipi dei parametri corretti (per la serializzazione JSON dei parametri mi servono le classi
					// dei bean)
					if (!hasObjectParameterTypes(method)) {
						break;
					}
				}
			}

		}

		if (operation == null) {
			throw new Exception("Operazione " + operation + " non configurata!");
		}

		servicebean.setOperation(operation);
		servicebean.setService(serviceclass);
		return servicebean;
	}

	public static boolean hasObjectParameterTypes(Method method) {
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			if (method.getParameterTypes()[i] != null && "java.lang.Object".equalsIgnoreCase(method.getParameterTypes()[i].getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Chiamata al metodo tramite reflections
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Serializable call(final String servicename, String operationname, Serializable... inputsObject) throws Exception {
		String idAuditlog = "";
		try {
			// Audit before
			idAuditlog = AuditHelper.traceStart(servicename, operationname, inputsObject);

			// Recupero il metodo
			ServiceBean servicebean = getOperationServiceBean(servicename, operationname);

			// Effettua la chiamata
			Serializable outputobject = (Serializable) MethodAccess.get(servicebean.getService()).invoke(servicebean.getService().newInstance(),
					servicebean.getOperation().getName(), inputsObject);

			// Audit after
			// all is going ok

			AuditHelper.traceEnd(idAuditlog, servicename, operationname, outputobject);

			return outputobject;
		} catch (Throwable e) {
			if (StringUtils.isNotBlank(servicename)) {
				log.error("Service name: " + servicename);
			}
			if (StringUtils.isNotBlank(operationname)) {
				log.error("Operation name: " + operationname);
			}
			log.error("Eccezione IrisCall", e);
			// Audit Error
			AuditHelper.traceError(idAuditlog, servicename, operationname, e);

			// Rilancio l'Exception
			if (e instanceof ServiceException) {
				throw ((ServiceException) e);
			} else {
				// generic message per gli errori non gestiti.
				throw new ServiceException(e);
			}
		}
	}

}
