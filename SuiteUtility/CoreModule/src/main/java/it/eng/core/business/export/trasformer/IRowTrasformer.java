package it.eng.core.business.export.trasformer;

import it.eng.core.business.export.DataExtractor;

/**
 * trasforma i dati di una riga in un oggetto specifico.
 * Un trasformer può  essere applicato ad un DataExtractor {@link DataExtractor} occorre sapere 
 * cosa ci sia aspetta in output dalla query e di conseguenza implementare 
 * la trasformazione in un nuovo oggetto
 * @author Russo
 *
 */
public interface IRowTrasformer {
	
	public Object trasform(Object[] row) throws Exception;
}
