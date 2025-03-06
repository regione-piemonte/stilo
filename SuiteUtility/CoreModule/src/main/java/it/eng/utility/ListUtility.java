package it.eng.utility;

import java.util.ArrayList;
import java.util.List;

public class ListUtility {

	/**
	 * Rimuove gli oggetti duplicati presenti nella lista in ingresso (utiliza il metodo equals)
	 * 
	 * @param inputList
	 *            Lista in ingresso da cui rimuovere i duplicati
	 * @param resultType
	 *            La classe con cui implementare la lista che verrà retituita
	 * @return Una lista contenente tutti gli oggetti della lista in ingresso, ma senza i duplicati
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static List<Object> removeDuplicateObject(List<? extends Object> inputList, Class<? extends List<Object>> resultType) throws InstantiationException,
			IllegalAccessException {
		if (inputList == null)
			return null;
		List<Object> outputList = resultType.newInstance();
		for (Object object : inputList) {
			if (!outputList.contains(object)) {
				outputList.add(object);
			}
		}
		return outputList;
	}

	/**
	 * Rimuove gli oggetti duplicati presenti nella lista in ingresso (utiliza il metodo equals)
	 * 
	 * @param inputList
	 *            Lista in ingresso da cui rimuovere i duplicati
	 * @return Una lista contenente tutti gli oggetti della lista in ingresso, ma senza i duplicati
	 */
	public static List<Object> removeDuplicateObject(List<? extends Object> inputList) {
		if (inputList == null)
			return null;
		List<Object> outputList = new ArrayList<Object>(inputList.size());
		for (Object object : inputList) {
			if (!outputList.contains(object)) {
				outputList.add(object);
			}
		}
		return outputList;
	}

}
