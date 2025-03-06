package it.eng.core.business;

import it.eng.core.business.beans.EntityConstraint;
import it.eng.core.business.beans.PropValue;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Classe centralizzata per la verifica di unique constraints violation di una entity.
 * <br>
 * E' da utilizzare all'interno della logica di business che gestisce eccezioni dovute
 * ad unique constraints violations.
 * @author upescato
 *
 */

public class UniqueConstraintsUtil {

	/*
	 * Metodo che, data una classe passata in input, restituisce una struttura dati contenente la lista di tutte le property
	 * che costituiscono unique constraints.
	 * @param clazz - Classe di cui si vogliono individuare le unique constraints
	 * @throws Exception
	 */
	public static List<EntityConstraint> getEntityUniqueContraintFields(Class<?> clazz) throws Exception {

		//Data la classe passata in input, verifica se è annotata con @Table
		Annotation[] annotations = clazz.getAnnotations();
		List<EntityConstraint> listaConstraints = new ArrayList<EntityConstraint>();
		Table table = null;
		for(Annotation annotation:annotations) {
			if(annotation.annotationType().equals(Table.class)) {
				table = (Table) annotation;
				break;
			}
		}

		//Se esiste l'annotazione @Table, recupera tutte le uniqueConstraints e i nomi delle property che le compongono
		if(table!=null) {
			for(UniqueConstraint uc:table.uniqueConstraints()) {
				EntityConstraint ec = new EntityConstraint();
				List<PropValue> props = new ArrayList<PropValue>(uc.columnNames().length);
				for(String columnName:uc.columnNames()) {
					props.add(getPropertyName(columnName, clazz));
				}
				ec.setPropertyNames(props);			//Aggiungo le property all'oggetto EntityConstraint
				listaConstraints.add(ec);			//Aggiungo l'EntityConstraint alla lista
			}
		}

		return listaConstraints;

	}

	//Metodo di utilità.
	/*
	 * Dato il nome di una colonna di una specifica classe, recupera il nome della property corrispondente alla colonna.
	 */
	private static PropValue getPropertyName(String columnName, Class<?> clazz) throws Exception {
		PropValue propValue = null;

		for (PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
			if (pd.getReadMethod() != null ) {
				Column cl = pd.getReadMethod().getAnnotation(Column.class);
				JoinColumn jcl = pd.getReadMethod().getAnnotation(JoinColumn.class);
				if((cl!=null && cl.name().equals(columnName)) || (jcl!=null && jcl.name().equals(columnName))) {
					propValue = new PropValue();
					//Se la property è una Join, di essa ritorna anche la primaryKey
					if(jcl!=null && jcl.name().equals(columnName)) {
						propValue.setPropertyPKName(HibernateUtil.getPrimaryKey(pd.getPropertyType()));
					}
					propValue.setPropertyName(pd.getName());
					break;
				}
			}
		}

		return propValue;
	}

//	/** FIXME funziona solo con l'insert
//	 * Questo metodo effettua una query sui campi corrispondenti alle unique constraint della classe passata in input.
//	 * <br>
//	 * Verifica qual è l'eventuale unique constraint violata occorsa.
//	 * @param session - Sessione del DB
//	 * @param bean - Bean corrispondente all'oggetto che si sta inserendo/modificando
//	 * @param classe - Classe corrispondente all'entity
//	 * @param e - Eccezione verificatasi a monte, volendo è ripropagabile
//	 * @return
//	 * @throws Exception
//	 */
//	public static EntityConstraint findViolatingConstraint(Session session, AbstractBean bean, Class<?> classe, Exception e) throws Exception {
//		PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();
//		
//		//Verifica solo le entry cancellate logicamente
//		SimpleExpression flgAnnTrue = Restrictions.eq("flgAnn", true);
//
//		//Recupero l'elenco delle constraints della classe
//		List<EntityConstraint> constraints = getEntityUniqueContraintFields(classe);
//
//		//Recupera l'entity in sessione che sta generando l'errore
//		Object entity = ReflectionUtil.getEntity(session, util, bean, classe);
//		if(entity==null) {
//			throw e;		//Se nella sessione non esiste un'entity, propaga l'eccezione occorsa
//		}
//
//		/* La query è una or di tutte le constraint.*/
//
//		for(EntityConstraint ec:constraints) {
//			Criteria crit = session.createCriteria(classe);
//			Junction jc = Restrictions.conjunction();		//Faccio una AND degli attributi che costituiscono una singola constraint
//			for(PropValue prop:ec.getPropertyNames()) {
//				//Se la property si riferisce ad una chiave esterna allora entro in questo if:
//				if(StringUtils.isNotBlank(prop.getPropertyPKName())) {
//					String nomeCampo = prop.getPropertyName();                //nome della property
//					String nomeProperty = prop.getPropertyPKName();			  //nome della PK della property
//					crit.createAlias(nomeCampo, nomeCampo);					  //creo l'alias	
//					String aliasCond = nomeCampo+"."+nomeProperty;			  //alias.nomeProperty
//					Object relatedBean = util.getProperty(entity, nomeCampo); //recupero l'istanza della property	
//					jc.add(Restrictions.eq(aliasCond, util.getProperty(relatedBean, nomeProperty)).ignoreCase());	//creo la condizione della select
//				}
//
//				else {
//					jc.add(Restrictions.eq(prop.getPropertyName(),util.getProperty(entity, prop.getPropertyName())).ignoreCase());
//				}
//
//			}
//			crit.add(Restrictions.and(flgAnnTrue, jc));
//			List<?> lista = crit.list();
//			if(lista!=null && lista.size()>0)
//				return ec;
//		}
//		
//		throw e;     //Se non è stata ritornata nessuna constraints, vuol dire che non ne ho violato nessuna, quindi propago l'eccezione originaria
//	}
	
	
	
//	/** FIXME funziona solo per l'insert
//	 * Questo metodo effettua una query sui campi corrispondenti alle unique constraint della classe passata in input.
//	 * <br>
//	 * Verifica che esistano entry annullate logicamente, corrispondenti ai valori delle unique violate.
//	 * @param session - Sessione del DB
//	 * @param bean - Bean corrispondente all'oggetto che si sta inserendo/modificando
//	 * @param classe - Classe corrispondente all'entity
//	 * @param e - Eccezione verificatasi a monte, volendo è ripropagabile
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<?> checkConstraintViolation(Session session, AbstractBean bean, Class<?> classe, Exception e) throws Exception {
//		PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();
//		Criteria crit = session.createCriteria(classe);
//		//Verifica solo le entry cancellate logicamente
//		SimpleExpression flgAnnTrue = Restrictions.eq("flgAnn", true);
//
//		//Recupero l'elenco delle constraints della classe
//		List<EntityConstraint> constraints = getEntityUniqueContraintFields(classe);
//
//		//Recupera l'entity in sessione che sta generando l'errore
//		Object entity = ReflectionUtil.getEntity(session, util, bean, classe);
//		if(entity==null) {
//			throw e;		//Se nella sessione non esiste un'entity, propaga l'eccezione occorsa
//		}
//
//		//Prepara la query
//		Disjunction dj = Restrictions.disjunction();		//Ogni constraint va in or
//
//		/* La query è una or di tutte le constraint.*/
//
//		for(EntityConstraint ec:constraints) {
//			Junction jc = Restrictions.conjunction();		//Faccio una AND degli attributi che costituiscono una singola constraint
//			for(PropValue prop:ec.getPropertyNames()) {
//				//Se la property si riferisce ad una chiave esterna allora entro in questo if:
//				if(StringUtils.isNotBlank(prop.getPropertyPKName())) {
//					String nomeCampo = prop.getPropertyName();                //nome della property
//					String nomeProperty = prop.getPropertyPKName();			  //nome della PK della property
//					crit.createAlias(nomeCampo, nomeCampo);					  //creo l'alias	
//					String aliasCond = nomeCampo+"."+nomeProperty;			  //alias.nomeProperty
//					Object relatedBean = util.getProperty(entity, nomeCampo); //recupero l'istanza della property	
//					jc.add(Restrictions.eq(aliasCond, util.getProperty(relatedBean, nomeProperty)).ignoreCase());	//creo la condizione della select
//				}
//
//				else {
//					jc.add(Restrictions.eq(prop.getPropertyName(),util.getProperty(entity, prop.getPropertyName())).ignoreCase());
//				}
//
//			}
//			dj.add(jc);				//Ogni constraint è messa in or con le altre
//		}
//
//		crit.add(Restrictions.and(flgAnnTrue, dj));
//		//SELECT * from classe where FLG_ANN = true AND (constraint_1 OR constraint_2 OR ... constraint_n)
//		return crit.list();
//	}
}