package it.eng.dm.sira.service.ost.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hyperborea.sira.ws.CaratterizzazioniOst;

public class OstUtils {
	
	public static CaratterizzazioniOst chooseCaratterizzazione(CaratterizzazioniOst[] caratterizzazioni) {
		CaratterizzazioniOst ret = null;
		List<CaratterizzazioniOst> listToOrder = Arrays.asList(caratterizzazioni);
		if (listToOrder.size() > 1) {
			Collections.sort(listToOrder, new Comparator<CaratterizzazioniOst>() {
				@Override
				public int compare(CaratterizzazioniOst o1, CaratterizzazioniOst o2) {
					long time1 = o1.getDataInizio().getTimeInMillis();
					long time2 = o2.getDataInizio().getTimeInMillis();
					if (time1 > time2) {
						return -1;
					} else {
						if (time1 < time2) {
							return 1;
						}
					}
					return 0;
				}
			});
		}
		ret = listToOrder.get(0);
		return ret;
	}

}
