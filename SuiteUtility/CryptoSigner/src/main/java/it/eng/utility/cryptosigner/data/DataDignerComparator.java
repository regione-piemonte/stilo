/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Comparator;

public class DataDignerComparator implements Comparator<DataSignerType> {

	@Override
	public int compare(DataSignerType o1, DataSignerType o2) {
		if( o1.getOrdine()>o2.getOrdine() )
			return 1;
		if( o1.getOrdine()<o2.getOrdine() )
			return -1;
		if( o1.getOrdine()==o2.getOrdine() )
			return 0;
		return 0;
	}

	
}
