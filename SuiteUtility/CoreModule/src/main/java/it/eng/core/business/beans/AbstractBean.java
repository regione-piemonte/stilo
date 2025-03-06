package it.eng.core.business.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModelProperty;

/**
 * Classe padre di tutti i bean d'interfaccia.
 * <br>
 * Al suo interno è contenuta una variabile Set, nella quale, 
 * ad ogni modifica di un field di un proprio sottotipo, 
 * andrà inserito il nome della property modificata.
 * 
 * Per un esempio, si vedano i setter di una qualsiasi delle classi che estendono questa. 
 * @author upescato
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AbstractBean implements Serializable {
	
	private static final long serialVersionUID = -6781464110893855859L;

	/**
	 * Variabile contenente al proprio interno gli operatori da utilizzare (per ogni campo) quando il bean viene usato come filtro
	 */
	@ApiModelProperty(hidden=true)
	private HashMap<String,String> operators = new HashMap<String,String>();
	
	public void addOperator(String fieldName, String operator) {
		if(operators == null) {
			operators = new HashMap<String,String>();
		}
		operators.put(fieldName, operator);
	}
	
	public HashMap<String,String> getOperators() {
		return operators;
	}

	public void setOperators(HashMap<String,String> operators) {
		this.operators = operators;
	}
	
	/**
	 * Variabile contenente al proprio interno i nomi delle property correntemente aggiunte/modificate nella presente istanza.
	 */
	@ApiModelProperty(hidden=true)
	private Set<String> updatedProperties = new HashSet<String>();

	public Set<String> getUpdatedProperties() {
		return updatedProperties;
	}

	public void setUpdatedProperties(Set<String> updatedProperties) {
		this.updatedProperties = updatedProperties;
	}

	/**
	 * Metodo generale che verifica se la property il cui nome corrisponde alla stringa in input è stata modificata 
	 * (i.e.: aggiunta al set {@link AbstractBean#updatedProperties})
	 * @param propertyName - nome della property su cui effettuare la verifica
	 * @return
	 */
	public boolean hasPropertyBeenModified(String propertyName) {
		return this.getUpdatedProperties().contains(propertyName);
	}
}
