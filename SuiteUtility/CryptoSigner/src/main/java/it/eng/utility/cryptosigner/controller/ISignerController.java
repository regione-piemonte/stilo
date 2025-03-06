package it.eng.utility.cryptosigner.controller;

import it.eng.utility.cryptosigner.controller.bean.InputBean;
import it.eng.utility.cryptosigner.controller.bean.OutputBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;

/**
 * Interfaccia da implementare per i controller di firma di file firmati
 * @author Rigo Michele
 *
 */
public interface ISignerController {

	/**
	 * Metodo che consente di verificare se il controllo
	 * puo essere eseguito a partire dalla attuale configurazione del bean di input
	 * @param input bean contenente le informazioni necessarie all'esecuzione
	 * @return true se il controllo puo essere effettuato
	 */
	public boolean canExecute(InputBean input);
	
	/**
	 * Esegue il controllo a partire dalle informazioni di input, popolando 
	 * il bean di output
	 * @param input bean contenente le informazioni necessarie all'esecuzione
	 * @param output bean contenente le proprieta valorizzate dal controller in seguito all'esecuzione 
	 * @return true se il controllo e stato superato
	 * @throws ExceptionController
	 */
	public boolean execute(InputBean input,OutputBean output, boolean childValidation)throws ExceptionController;

	/**
	 * Restituisce true se il controllo attuale e critico
	 * @return boolean
	 */
	public boolean isCritical();

}
