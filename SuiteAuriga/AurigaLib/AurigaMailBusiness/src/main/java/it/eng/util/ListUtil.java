/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	/**
	 * Pulisce la lista di eventuali doppioni. Gli oggetti vengono confrontati con equals
	 * @param lList
	 * @return
	 */
	public static List<?> distinct(List<?> lList){
		List result = new ArrayList();
		for (Object lObject : lList){
			if (!contains(result, lObject)){
				result.add(lObject);
			}
		}
		return result;
	}

	private static boolean contains(List result, Object lObjectToFind) {
		for (Object lObject : result){
			if (lObject.equals(lObjectToFind)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Dice se è vuota
	 * @param lList
	 * @return
	 */
	public static boolean isEmpty(List<?> lList){
		return lList == null || lList.size() == 0;
	}
	
	/**
	 * Dice se non è vuota
	 * @param lList
	 * @return
	 */
	public static boolean isNotEmpty(List<?> lList){
		return !isEmpty(lList);
	}

	public static String print(List<?> pListToPrint) {
		if (pListToPrint==null) return null;
		boolean first = true;
		StringBuffer lStringBuffer = new StringBuffer();
		for (Object lString : pListToPrint){
			if (first) first = false;
			else lStringBuffer.append(", ");
			lStringBuffer.append(lString);
		}
		return lStringBuffer.toString();
	}
	
	/**
	 * metodo per fare l'intersezione di valori fra due liste di stringhe
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static List<String> intersect(List<String> a, List<String> b) {
		List<String> lstIntersectAB = a;
		lstIntersectAB.retainAll(b);
		return lstIntersectAB;
	}
}
