/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;

import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementa la gestione dei controller.
 * L'analisi viene innescata dalla chiamata al metodo {@link FileOperationController#executeControll(InputFileBean)} 
 * e iterata su tutti i controller definiti nell'attributo controllers
 *
 */
public class FileOperationController {
	
	private static Logger log = LogManager.getLogger(FileOperationController.class);
	private String sistemaOperativo;
	
	//Directory temporanea di appoggio per la gestione dei file temporanei
	//private File temporarydirectroy = new File(System.getProperty("java.io.tmpdir"));

	// Controller da invocare per l'analisi
	//private List<IFileController> controllers= new ArrayList<IFileController>();

	// Mappa dei flag indicanti i controlli da effettuare
	private Map<String,Boolean> checks;
	
 
	
	// Indica se uno dei controlli bloccanti non e' andato a buon fine
	private boolean interrupted = false;
	
	// Ausiliario
	//private SignerUtil signerUtil = SignerUtil.newInstance();
	
	private Map<String,AbstractInputOperationType> customInputs= new HashMap<String, AbstractInputOperationType>();
	
//	public File getTemporarydirectroy() {
//		return temporarydirectroy;
//	}
//
//	public void setTemporarydirectroy(File temporarydirectroy) {
//		this.temporarydirectroy = temporarydirectroy;
//	}
	
	/**
	 * Directory temporanea dedicata per la gestione dei file di appoggio
	 */
	private File dedicatedTemporyDirectory;
	
	public FileOperationController() {
		
	}
	
	//call after initialization
	public void init(){
//		String dedicated = StringUtils.remove(UUID.randomUUID().toString(),"-");
//		dedicatedTemporyDirectory = new File(temporarydirectroy,dedicated);
//		dedicatedTemporyDirectory.mkdirs();
	}
	
	
//	public File getDedicatedTemporyDirectory() {
//		return dedicatedTemporyDirectory;
//	}

	/**
	 * Recupera i controller configurati
	 * @return i controller configurati
	 */
	/*public List<IFileController> getControllers() {
		return controllers;
	}*/

	/**
	 * Definisce i controller su cui effettuare l'analisi
	 * @param controllers la lista dei controlli cui cui iterare l'analisi
	 */
	/*public void setControllers(List<IFileController> controllers) {
		this.controllers = controllers;
	}*/
	
	
	/**
	 * Effettua l'analisi richiamando l'esecuzione di ciascun 
	 * controller configurato. 
	 * @param input bean contenente le informazioni in input per eseguire i controlli
	 * @return bean
	 * @throws ExceptionController
	 */
	public OutputOperations executeControll(InputFileBean input, Operations operations, String requestKey) {
		//imposto la directory temporanea
		//input.setTemporaryDir(FileOpConfigHelper.makeTempDir());
		OutputOperations output = new OutputOperations();
		List<IFileController> controllers = buildCtrl(operations, requestKey);//getControllers();
		//riordino i controllers definitivi da eseguire  in base alla priority
 
		Collections.sort(controllers/*getControllers()*/,new PriorityComparator());
		log.info( requestKey + " - Eseguo i controllers:"+controllers);
		this.execute(input, output, controllers, requestKey);
		
		return output;
	}
	
	public List<IFileController> buildCtrl(Operations operations, String requestKey){
		//read operation and build relative controller
		//crea la lista ordinata delle op richieste in base alla priorita'
		List<IFileController> ctrls=new ArrayList<IFileController>();
		
		List<AbstractInputOperationType> ops=operations.getOperation();
		CtrlBuilder builder=FileCtrlFactory.getCtrlBuilder();
		for (AbstractInputOperationType abstractInputOperationType : ops) {
			IFileController ctrl=builder.build(abstractInputOperationType);
			if(ctrl!=null){
				ctrls.add(ctrl);
				//conservo l'input custom per il controller
				customInputs.put(ctrl.getClass().getName(), abstractInputOperationType);
			}
			
		}
		 Collections.sort(ctrls,new PriorityComparator());
		//aggiungi i ctrl alla lista di quelli da eseguire considerendo le dipendenze
		 Iterator<IFileController> ctrlItr = ctrls.iterator();
		 //for (IFileController ctrl : ctrls) {
		 
		 List<IFileController> ctrlsPred=new ArrayList<IFileController>();
		 while(ctrlItr.hasNext()){
			 IFileController ctrl = ctrlItr.next();
			 addCtrl(ctrl, ctrlsPred, requestKey);
		}
		 ctrls.addAll(ctrlsPred);
		 
		 return ctrls;
	}
	
	public void addCtrl(IFileController ctrl, List<IFileController> controllers, String requestKey){
		//verifico se è già presente
		log.debug(requestKey + " - Verifico se il controller " + ctrl + " ha predecessori, nel caso li aggiungo");
		log.debug("ctrl.getPredecessors() " + ctrl.getPredecessors());
		if(ctrl.getPredecessors()!=null && ctrl.getPredecessors().size()>0){
			for (IFileController iFileController : ctrl.getPredecessors()) {
				log.debug("iFileController " + iFileController);
				//if(!controllers.contains(iFileController)) {
				if( !containsCtrl(iFileController.getClass(), controllers)){
					controllers.add(iFileController );
					addCtrl(iFileController, controllers, requestKey);
				}
			}
		}
		/*if(!containsCtrl(ctrl.getClass(), controllers)){
			controllers.add(ctrl);
		}*/
		 
	}
	private boolean containsCtrl(Class clazz, List<IFileController> controllers){
		boolean ret=false;
		if(controllers==null){
			controllers=new ArrayList<IFileController>();
		}
		for (IFileController iFileController : controllers/*getControllers()*/) {
			if(iFileController.getClass().equals(clazz)){
				ret=true;
				break;
			}
		}
		return ret;
	}
	/**
	 * Esegue la sequenza di controlli sul bean di input, 
	 *
	 */
	private void execute(InputFileBean input,OutputOperations output, List<IFileController> controllers, String requestKey){
		boolean result;
//		input.setChecks(checks);
		 
		for (IFileController controller:controllers) {
//			if (controller.canExecute(input)) {
//				try {
					long start = System.currentTimeMillis();
					log.info(">>>>>> " + requestKey + " Inizio Controllo: " + controller.getClass().getSimpleName() + " " + controller  + " input " + input + " output "+ output);
					result = controller.execute(input,customInputs.get(controller.getClass().getName()), output, requestKey);
					
					if (!result && controller.isCritical() && customInputs.get(controller.getClass().getName()).isBreakOnError() ) {
						//output.setProperty(OutputFileBean.MASTER_SIGNER_EXCEPTION_PROPERTY, controller.getClass().getName());
						interrupted = true;
						break;
					}
					long elapsedTimeMillis = System.currentTimeMillis()-start;
					log.info("<<<<<<< " + requestKey + " - Controllo: " + controller.getClass().getSimpleName() + " eseguito in " + elapsedTimeMillis + "ms");
//				}catch(Exception e) {//
//					if (controller.isCritical()) {
//						interrupted = true;
//						//output.setProperty(OutputFileBean.MASTER_SIGNER_EXCEPTION_PROPERTY, controller.getClass().getName());
//						//throw new ExceptionController(e);
//					}
//				}
				
//			}
		}
	}
	
 

	/**
	 * Recupera la mappa dei flag dei controlli
	 * @return la mappa dei flag
	 */
	public Map<String, Boolean> getChecks() {
		return checks;
	}

	/**
	 * Definisce i flag dei controlli da effettuare
	 * @param checks la mappa contenente i flag dei controlli e il loro valore (true/false)
	 */
	public void setChecks(Map<String, Boolean> checks) {
		this.checks = checks;
	}
 

	/**
	 * Restituisce lo stato di esecuzione dei controller
	 * @return true se uno dei controller ha generato un errore bloccante
	 */
	public boolean isInterrupted() {
		return interrupted;
	}

	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public void setSistemaOperativo(String sistemaOperativo) {
		this.sistemaOperativo = sistemaOperativo;
	}
		
	
}
