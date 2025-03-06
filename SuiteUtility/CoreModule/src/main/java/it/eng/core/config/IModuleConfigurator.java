package it.eng.core.config;

/**
 * Interfaccia che espone i metodi di utilizzo dei moduli per essere configuarti da IRIS
 * @author michele
 *
 */
public interface IModuleConfigurator {
	
	/**
	 * Metodo che viene chiamato all'inizializzazione di IRIS 
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	
	/**
	 * Metodo che viene chiamato allo shutdown della jvm
	 * @throws Exception
	 */
	public void destroy() throws Exception;
}
