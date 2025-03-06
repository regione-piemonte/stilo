package it.eng.core.business.util;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtilsBean2;

import it.eng.core.business.HibernateUtil;

public class SetUtil {

	public static <T> Set<T> union(Set<T> setA, Set<T> setB) {
		Set<T> tmp = new TreeSet<T>(setA);
		tmp.addAll(setB);
		return tmp;
	}

	public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
		Set<T> tmp = new TreeSet<T>();
		for (T x : setA)
			if (setB.contains(x))
				tmp.add(x);
		return tmp;
	}

	public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
		Set<T> tmp = new TreeSet<T>(setA);
		tmp.removeAll(setB);
		return tmp;
	}

	public static <T> Set<T> symDifference(Set<T> setA, Set<T> setB) {
		Set<T> tmpA;
		Set<T> tmpB;

		tmpA = union(setA, setB);
		tmpB = intersection(setA, setB);
		return difference(tmpA, tmpB);
	}

	public static <T> boolean isSubset(Set<T> setA, Set<T> setB) {
		return setB.containsAll(setA);
	}

	public static <T> boolean isSuperset(Set<T> setA, Set<T> setB) {
		return setA.containsAll(setB);
	}

	// make sure keyProp value is a string
	public static <T> Set<String> buildSetFromList(List<T> lista, String keyProp) throws Exception {
		LinkedHashSet<String> ret = new LinkedHashSet<String>();
		for (T t : lista) {
			String value = (String) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(t, keyProp);
			ret.add(value);
		}
		return ret;
	}

	// data una lista di entity T ritorna una Mappa in cui
	// la chiave e' il valore della proprieta' keyProp, il valore e' l'oggetto istanza di T
	// se keyprop è una entity utilizza la chiave della entity che si suppone stringa
	// FIXME funziona solo con le stringhe
	public static <T> Map<String, T> buildMapFromList(List<T> lista, String keyProp) throws Exception {
		LinkedHashMap<String, T> ret = new LinkedHashMap<String, T>();
		for (T t : lista) {
			String keyRet;
			Object value = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(t, keyProp);
			Class propType = BeanUtilsBean2.getInstance().getPropertyUtils().getPropertyType(t, keyProp);
			if ((HibernateUtil.getListEntityPackage() != null && !HibernateUtil.getListEntityPackage().isEmpty()
					&& HibernateUtil.getListEntityPackage().contains(value.getClass().getPackage().getName()))
					|| propType.getPackage().getName().equals(HibernateUtil.getEntitypackage())) {
				// prendi la chiave
				String keyName = HibernateUtil.getPrimaryKey(value.getClass());
				keyRet = (String) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(value, keyName);
			} else {
				// cerca di fare il casting a String
				keyRet = (String) value;
			}
			ret.put(keyRet, t);
		}
		return ret;
	}

	public static void main(String args[]) {
		TreeSet<Character> set1 = new TreeSet<Character>();
		TreeSet<Character> set2 = new TreeSet<Character>();

		set1.add('A');
		set1.add('B');
		set1.add('C');
		set1.add('D');

		set2.add('C');
		set2.add('D');
		set2.add('E');
		set2.add('F');

		// System.out.println("set1: " + set1);
		// System.out.println("set2: " + set2);

		// System.out.println("Union: " + union(set1, set2));
		// System.out.println("Intersection: " + intersection(set1, set2));

		// System.out.println("Difference (set1 - set2): " + difference(set1, set2));
		// System.out.println("Difference (set2 - set2): " + difference(set2, set1));
		// System.out.println("Symmetric Difference: " + symDifference(set1, set2));

		TreeSet<Character> set3 = new TreeSet<Character>(set1);

		set3.remove('D');
		// System.out.println("set3: " + set3);

		// System.out.println("Is set1 a subset of set2? " + isSubset(set1, set3));
		// System.out.println("Is set1 a superset of set2? " + isSuperset(set1, set3));
		// System.out.println("Is set3 a subset of set1? " + isSubset(set3, set1));
		// System.out.println("Is set3 a superset of set1? " + isSuperset(set3, set1));

	}
}
