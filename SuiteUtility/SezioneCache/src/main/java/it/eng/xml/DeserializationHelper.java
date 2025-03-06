package it.eng.xml;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;

import java.util.Map;

/**
 * Rimappa i valori restituiti dal db in campi diversi a seconda delle
 * condizioni fornite. In genere la deserializzazione riga -> bean dovrebbe
 * essere fatta in maniera automatica tramite XmlListaUtility, e se un bean è
 * utilizzato in due datasource distinti con semantiche diverse, dovrebbero
 * essere scissi in due entità distinte. Possono esistere dei casi però in cui
 * il bean viene utilizzato da un solo datasource per gestire casistiche
 * leggermente diverse, o che a causa di personalizzazioni, vengano rimappati i
 * valori di alcuni campi. Questa classe ha quindi il compito di racchiudere la
 * logica di rimappatura in maniera tale sia invocabile in maniera generica,
 * senza cioè che il codice invocante sia a consocienza dell'implementazione
 * 
 * @author massimo malvestio
 *
 */
public abstract class DeserializationHelper {

	protected static final String  FMT_STD_DATA = "dd/MM/yyyy";
	protected static final String  FMT_STD_TIMESTAMP = "dd/MM/yyyy HH:mm";
	protected static final String  FMT_STD_TIMESTAMP_WITH_SEC = "dd/MM/yyyy HH:mm:ss";
	protected static final String  DATETIME_ATTR_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		
	protected Map<String, String> remapConditions;
	
	/**
	 * 
	 * @param remapConditions contiene le condizioni di rimappatura dei campi ed eventuali valori da utilizzare
	 */
	public DeserializationHelper(Map<String, String> remapConditions) {
		this.remapConditions = remapConditions;
	}
	
	public abstract void remapValues(Object obj, Riga riga) throws Exception;

	public String getColonnaContent(Riga riga, int nroCol) {
		for (Colonna col : riga.getColonna()) {
	        if (col.getNro().intValue() == nroCol) {
	        	return col.getContent();
	        }
		}
		return null;
	}
	
}
