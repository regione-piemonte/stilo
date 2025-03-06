package it.eng.utility.cryptosigner.data;

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
