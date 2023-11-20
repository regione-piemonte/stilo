/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * restituisce l'intersezione di due liste 
 * @author jravagnan
 *
 */
public class ListUtilities {
	
	public static List<String> intersect(List<String> a,List<String> b){
			  List<String> lstIntersectAB = a;
			  lstIntersectAB.retainAll(b);
			  return lstIntersectAB;
			}
	

}
