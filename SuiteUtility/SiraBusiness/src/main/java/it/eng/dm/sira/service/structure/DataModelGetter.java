package it.eng.dm.sira.service.structure;

import it.eng.dm.sira.dao.DaoAttributiOst;
import it.eng.dm.sira.entity.AttributiOst;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.util.PopulationUtils;
import it.eng.dm.sira.service.util.PropertyDescriptorReader;
import it.eng.spring.utility.SpringAppContext;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.WsCcostDescriptor;
import com.hyperborea.sira.ws.WsCostNostRef;
import com.hyperborea.sira.ws.WsGetAvailableCategoriaListRequest;
import com.hyperborea.sira.ws.WsGetCategoriaInfoRequest;
import com.hyperborea.sira.ws.WsGetCategoriaInfoResponse;
import com.hyperborea.sira.ws.WsGetCategoriaMetadataRequest;
import com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse;
import com.hyperborea.sira.ws.WsPropertyDescriptor;

/**
 * classe che restituisce la struttura xml di un oggetto da inserire
 * 
 * @author jravagnan
 * 
 */
public class DataModelGetter {
	
	private List<String> excludedAttributes;

	private static Logger log = Logger.getLogger(DataModelGetter.class);
	public void writeDataModel(CostNostId input) throws Exception {
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			WsGetCategoriaMetadataRequest request = new WsGetCategoriaMetadataRequest();
			request.setCostNostId(input);
			WsGetCategoriaMetadataResponse response;
			response = service.getCatastiProxy().getCategoriaMetadata(request);
			WsCcostDescriptor descriptor = response.getWsCcostDescriptor();
			write(descriptor, input, null);
		} catch (RemoteException e) {
			log.error("Impossibile ottenere la struttura per l'oggetto con natura: " + input.getCodNost() + " e categoria: "
					+ input.getCodCost());
			throw new SiraBusinessException("Impossibile ottenere la struttura per l'oggetto con natura: " + input.getCodNost()
					+ " e categoria: " + input.getCodCost(), e);
		}
	}

	public String getDataModel(CostNostId input) throws SiraBusinessException {
		String ret = "";
		try {
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			WsGetCategoriaMetadataRequest request = new WsGetCategoriaMetadataRequest();
			request.setCostNostId(input);
			WsGetCategoriaMetadataResponse response;
			response = service.getCatastiProxy().getCategoriaMetadata(request);
			WsCcostDescriptor descriptor = response.getWsCcostDescriptor();
			return toXml(descriptor);
		} catch (RemoteException e) {
			log.error("Impossibile ottenere la struttura per l'oggetto con natura: " + input.getCodNost() + " e categoria: "
					+ input.getCodCost());
			throw new SiraBusinessException("Impossibile ottenere la struttura per l'oggeto con natura: " + input.getCodNost()
					+ " e categoria: " + input.getCodCost(), e);
		}
	}

	public WsCcostDescriptor getDataDescriptor(CostNostId input) throws SiraBusinessException, InterruptedException {
		String ret = "";
		try {
			// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			WsGetCategoriaMetadataRequest request = new WsGetCategoriaMetadataRequest();
			request.setCostNostId(input);
			WsGetCategoriaMetadataResponse response;
			response = service.getCatastiProxy().getCategoriaMetadata(request);
			log.info("\n\n\nSONO QUI\n\n\n");
			WsCcostDescriptor descriptor = response.getWsCcostDescriptor();
			return descriptor;
		} catch (RemoteException e) {
			log.error("Impossibile ottenere la struttura per l'oggetto con natura: " + input.getCodNost() + " e categoria: " + input.getCodCost());
			
			Thread.sleep(3000);
			
			throw new SiraBusinessException("Impossibile ottenere la struttura per l'oggeto con natura: " + input.getCodNost() + " e categoria: " + input.getCodCost(), e);
		}
	}	

	public void write(WsCcostDescriptor descriptor, CostNostId id, String nomePadre) throws Exception {
		DaoAttributiOst daoAttributi = new DaoAttributiOst();
		AttributiOst attributoPadre = new AttributiOst();
		attributoPadre.setCategoria(String.valueOf(id.getCodCost()));
		attributoPadre.setNatura(String.valueOf(id.getCodNost()));
		attributoPadre.setAttributo(descriptor.getPropertyType().equals("CaratterizzazioniOst") ? "caratterizzazioniOst" : descriptor.getPropertyName());
		String nomeComplesso = nomePadre != null ? nomePadre + "." + attributoPadre.getAttributo() : attributoPadre.getAttributo();
		attributoPadre.setNomeComplesso(nomeComplesso);
		attributoPadre.setFlgMultiplo("0");
		attributoPadre.setFlgObbl("1");
		attributoPadre.setLabel(PopulationUtils.createLabel(descriptor.getPropertyName()));
		attributoPadre.setTipo(descriptor.getPropertyType());
		attributoPadre.setIdPadre(null);
		AttributiOst padre = daoAttributi.save(attributoPadre);
		PropertyDescriptorReader reader = new PropertyDescriptorReader();
		if (descriptor.getWsPropertyDescriptors() != null) {
			for (WsPropertyDescriptor prop : descriptor.getWsPropertyDescriptors()) {
				reader.write(prop, id, padre.getIdAttributo(), padre.getNomeComplesso());
			}
		}
		if (descriptor.getChildCcostDescriptor() != null) {

			write(descriptor.getChildCcostDescriptor(), id, attributoPadre.getNomeComplesso());
		}
	}

	/*
	 * Metodo ricorsivo per il popolamento degli attributi custom (dinamici)
	 * 
	 * @param	descriptor Il singolo nodo della navigazione, ricorsivamente il metodo sarà richiamato sugli eventuali figli.
	 * @param	id 	Struttura contentente la natura e la categoria OST oggetto del popolamento
	 * @param	parent Nelle chiamate ricorsive contiene il nodo padre nella gerarchia "appiattita"
	 * @param	nameParentHierarchy Mantiene il nodo padre della gerarchia originale a discapito dell'"appiattimento". Necessario per mantenere i riferimenti rispetto ai medodi del CBDA.
	 * @param	levelHierarchy Il livello di ricorsione e navigazione della gerarchia metadati originale
	 * @param	root La root della gerarchia metadati originale. NULL alla prima invocazione del metodo, popolato intrinsecamente alla chiamata stessa.
	 */
	public void writePlainHierarchy(WsCcostDescriptor descriptor, CostNostId id, AttributiOst parent, String nameParentHierarchy, int levelHierarchy, AttributiOst root) throws Exception {
		String currentNomeComplesso = "";

		if(parent == null)
			currentNomeComplesso = "caratterizzazioniOst"; 
		else{				
			currentNomeComplesso = nameParentHierarchy  + "." + descriptor.getPropertyName(); 
		}

		if (parent == null || levelHierarchy == 1) {				
			DaoAttributiOst daoAttributi = new DaoAttributiOst();
			AttributiOst attributoPadre = new AttributiOst();
			attributoPadre.setCategoria(String.valueOf(id.getCodCost()));
			attributoPadre.setNatura(String.valueOf(id.getCodNost()));
			attributoPadre.setAttributo(descriptor.getPropertyType().equals("CaratterizzazioniOst") ? "caratterizzazioniOst" : descriptor.getPropertyName());
			//String nomeComplesso = attributoPadre.getAttributo() != null ? parent.getAttributo() + "." + attributoPadre.getAttributo() : attributoPadre.getAttributo();
			//attributoPadre.setNomeComplesso(nomeComplesso);
			attributoPadre.setNomeComplesso(currentNomeComplesso);

			attributoPadre.setFlgMultiplo("0");
			attributoPadre.setFlgObbl("1");
			
			if(levelHierarchy > 0) {
				if(descriptor.getPropertyName() != null && descriptor.getPropertyName().equals("ccostElementiSignificativi")) {
					attributoPadre.setLabel("Caratterizzazione");
				} else if(descriptor.getPropertyName() != null && descriptor.getPropertyName().equals("ccostAmbitoTerritoriale")) {
					attributoPadre.setLabel("Dati Specifici");
				} else {
					attributoPadre.setLabel(PopulationUtils.createLabel(descriptor.getPropertyName()));
				}
				
			} else {			
				attributoPadre.setLabel(PopulationUtils.createLabel(descriptor.getPropertyName()));
			}
			
			
			attributoPadre.setTipo(descriptor.getPropertyType());
			attributoPadre.setIdPadre( (parent == null) ? null : new BigDecimal(parent.getIdAttributo()) );				
			
			System.out.println("Saving Parent-> Nome Complesso: " + currentNomeComplesso + " Level: " + levelHierarchy + " Parent " + nameParentHierarchy);
			parent = daoAttributi.save(attributoPadre); 	
			
			// Set root attribute
			if(attributoPadre.getAttributo().equals("caratterizzazioniOst")) {
				root = parent;
			}
			
			System.out.println("Parent: "+ parent.getIdAttributo());				
		}
		
		System.out.println("--->using parent: "+ parent.getIdAttributo());
		
		if (descriptor.getWsPropertyDescriptors() != null) {

			for (WsPropertyDescriptor prop : descriptor.getWsPropertyDescriptors()) {
				if(excludeFields(prop.getPropertyName())) {
					
					if(prop.isMultiple()) { //Recursive invoke for multiple properties
						if ( excludeGenericFieldsByNomeComplesso(currentNomeComplesso + "." + prop.getPropertyName()) ) {
							saveMultiple(prop, id, root, currentNomeComplesso, levelHierarchy + 1);
						}
					} else { //Recursive invoke for complex/primitives
						if (prop.getWsPropertyDescriptors() == null) {
							if ( excludeGenericFieldsByNomeComplesso(currentNomeComplesso + "." + prop.getPropertyName()) ) {
								saveAttributiOst(prop, id, parent, currentNomeComplesso, levelHierarchy + 1);
							}					
						}
						else{	
							if( levelHierarchy <= 1 ) { // Save parent level 1
								String levelNomeComplesso = "caratterizzazioniOst." + prop.getPropertyName(); 
								
								DaoAttributiOst daoAttributi = new DaoAttributiOst();
								AttributiOst attributoPadre = new AttributiOst();
								attributoPadre.setCategoria(String.valueOf(id.getCodCost()));
								attributoPadre.setNatura(String.valueOf(id.getCodNost()));
								attributoPadre.setAttributo(prop.getPropertyName());
								attributoPadre.setNomeComplesso(levelNomeComplesso);
	
								attributoPadre.setFlgMultiplo("0");
								attributoPadre.setFlgObbl("1");
								
								if(levelHierarchy>0 && descriptor.getPropertyName().equals("ccostElementiSignificativi")) {
									attributoPadre.setLabel("Caratterizzazione");
								} else {			
									String label = prop.getPropertyLabel();
									if (label.length() > 100){
										label = prop.getPropertyLabel().substring(0, 99);
									}		
									attributoPadre.setLabel( label.contains("'") ? label.replace("'", "''") : label);
								}
								attributoPadre.setTipo(prop.getPropertyType());
								attributoPadre.setIdPadre( (parent == null) ? null : new BigDecimal(parent.getIdAttributo()) );									
								
								System.err.println("-->Saving Parent Lv.1 --> Nome Complesso: " + levelNomeComplesso + " Parent " + nameParentHierarchy);
								AttributiOst localParent = daoAttributi.save(attributoPadre); 		
								System.err.println("-->Parent Lv.1: "+ localParent.getIdAttributo());		
								
								for(WsPropertyDescriptor innerProp : prop.getWsPropertyDescriptors()) {
									savePrimitives(innerProp, id, localParent, levelNomeComplesso, levelHierarchy + 2);
								}
								
							} else {
								for(WsPropertyDescriptor innerProp : prop.getWsPropertyDescriptors()) {
									savePrimitives(innerProp, id, parent, currentNomeComplesso + "." + prop.getPropertyName(), levelHierarchy + 1);
								}
							}
						}
					}
				}
			}
		}

		if (descriptor.getChildCcostDescriptor() != null ) {
			writePlainHierarchy(descriptor.getChildCcostDescriptor(), id, parent, currentNomeComplesso, levelHierarchy + 1, root);
		}
	}

	/*
	 * Metodo ricorsivo per il popolamento degli attributi custom ripetibili. 
	 * Pur mantenendo il riferimento alla gerarchia metadati originale, le proprietà ripetibili sono "appiattite" al nodo radice. 
	 * 
	 * @param	prop Il singolo nodo della navigazione, ricorsivamente il metodo sarà richiamato sugli eventuali figli.
	 * @param	id 	Struttura contentente la natura e la categoria OST oggetto del popolamento
	 * @param	root Nelle chiamate ricorsive contiene il nodo root nella gerarchia "appiattita"
	 * @param	nameParentHierarchy Mantiene il nodo padre della gerarchia originale a discapito dell'"appiattimento". Necessario per mantenere i riferimenti rispetto ai medodi del CBDA.
	 * @param	levelHierarchy Il livello di ricorsione e navigazione della gerarchia metadati originale
	 */
	private void saveMultiple(WsPropertyDescriptor prop, CostNostId id, AttributiOst root, String nameParentHierarchy, int levelHierarchy) throws Exception {
		
		if(getExcludedAttributes() != null) {
			if(getExcludedAttributes().contains(prop.getPropertyName())) {
				return;
			}
		}
		
		String multipleNomeComplesso = nameParentHierarchy + "."; 
		multipleNomeComplesso += prop.getPropertyName();
		
		DaoAttributiOst daoAttributi = new DaoAttributiOst();
		AttributiOst attributoMultiple = new AttributiOst();
		attributoMultiple.setCategoria(String.valueOf(id.getCodCost()));
		attributoMultiple.setNatura(String.valueOf(id.getCodNost()));
		attributoMultiple.setAttributo(prop.getPropertyName());
		attributoMultiple.setNomeComplesso(multipleNomeComplesso);
		
		if (prop.getVocabularyDescription() != null){
			attributoMultiple.setClasseVocabolario(prop.getVocabularyDescription().getClassName());
		}

		attributoMultiple.setFlgMultiplo("1");
		attributoMultiple.setFlgObbl((prop.isMandatory() ? "1" : "0"));	
					
		//Set section header label
		//TODO CBDA must send explicit labels!
		String label = (prop.getPropertyLabel() != null) ? prop.getPropertyLabel() : prop.getPropertyName();
		if (label.length() > 100){
			label = prop.getPropertyLabel().substring(0, 99);
		}		
		attributoMultiple.setLabel( label.contains("'") ? label.replace("'", "''") : label);
		
		attributoMultiple.setTipo(prop.getPropertyType());
		attributoMultiple.setIdPadre( new BigDecimal(root.getIdAttributo()) );									
		
		System.err.println("-->Saving Parent Ripetibile: " + multipleNomeComplesso + " Parent " + nameParentHierarchy);
		AttributiOst multipleParent = daoAttributi.save(attributoMultiple); 		
		System.err.println("-->Parent Ripetibile: "+ multipleParent.getIdAttributo());		
		
		if(prop.getWsPropertyDescriptors() != null) {
			for(WsPropertyDescriptor innerProp : prop.getWsPropertyDescriptors()) {
				if(innerProp.isMultiple()) {
					saveMultiple(innerProp, id, root, multipleNomeComplesso, levelHierarchy + 2);
				} else {
					savePrimitives(innerProp, id, multipleParent, multipleNomeComplesso, levelHierarchy + 2);
				}
			}
		}
	}

	/*
	 * Metodo ricorsivo per il popolamento degli attributi custom NON ripetibili. 
	 * Pur mantenendo il riferimento alla gerarchia metadati originale, le proprietà complesse sono "appiattite" al primo livello. 
	 * 
	 * @param	prop Il singolo nodo della navigazione, ricorsivamente il metodo sarà richiamato sugli eventuali figli.
	 * @param	id 	Struttura contentente la natura e la categoria OST oggetto del popolamento
	 * @param	parent Nelle chiamate ricorsive contiene il nodo padre nella gerarchia "appiattita"
	 * @param	nameParentHierarchy Mantiene il nodo padre della gerarchia originale a discapito dell'"appiattimento". Necessario per mantenere i riferimenti rispetto ai medodi del CBDA.
	 * @param	levelHierarchy Il livello di ricorsione e navigazione della gerarchia metadati originale
	 */
	private void savePrimitives(WsPropertyDescriptor prop, CostNostId id, AttributiOst parent, String nameParentHierarchy, int levelHierarchy) throws Exception { 
		//		if((prop.isMandatory() && !(prop.isMultiple())) || isInUbicazioniOst(prop.getPropertyName())) {
		
		if(getExcludedAttributes() != null) {
			if(getExcludedAttributes().contains(prop.getPropertyName())) {
				return;
			}
		}
		
		if( !(prop.isMultiple()) ) {

			if (prop.getWsPropertyDescriptors() == null ){					
				saveAttributiOst(prop, id, parent, nameParentHierarchy, levelHierarchy + 1);				
			}
			else{
				if(levelHierarchy <= 1) {

					AttributiOst attributoPadre = saveAttributiOst(prop, id, parent, nameParentHierarchy, levelHierarchy + 1);

					for(WsPropertyDescriptor innerProp : prop.getWsPropertyDescriptors()) {
						savePrimitives(innerProp, id, attributoPadre, nameParentHierarchy + "." + prop.getPropertyName(), levelHierarchy + 1);
					}

				} else {
					for(WsPropertyDescriptor innerProp : prop.getWsPropertyDescriptors()) {
						savePrimitives(innerProp, id, parent, nameParentHierarchy + "." + prop.getPropertyName(), levelHierarchy + 1);
					}
				}
			}
		}
	}

	/*
	 * Metodo per il salvataggio di un singolo attributo. 
	 * 
	 * @param	prop Il singolo nodo da salvare, che rappresenta una proprietà primitiva.
	 * @param	id 	Struttura contentente la natura e la categoria OST oggetto del popolamento
	 * @param	parent Contiene il nodo padre nella gerarchia "appiattita"
	 * @param	nameParentHierarchy Contiene il nodo padre nella gerarchia originale a discapito dell'"appiattimento".
	 * @param	levelHierarchy Il livello di ricorsione e navigazione della gerarchia metadati originale
	 */
	public AttributiOst saveAttributiOst(WsPropertyDescriptor prop, CostNostId id, AttributiOst parent, String nameParentHierarchy, int levelHierarchy) throws Exception { 
		//		System.out.println("\n\n-------------------------------------------------- Parent Hierarchy: " + nameParentHierarchy);
		System.out.println("\t\tSaving Nodo -> Nome: " + prop.getPropertyName() + " Level: " + levelHierarchy);
//		System.out.println("\t\t\t Nodo Label: " + prop.getPropertyLabel());
//		System.out.println("\t\t\t Parent " + nameParentHierarchy);
		
		DaoAttributiOst daoAttributi = new DaoAttributiOst();
		AttributiOst newAttributo = new AttributiOst();
		newAttributo.setCategoria(String.valueOf(id.getCodCost()));
		newAttributo.setNatura(String.valueOf(id.getCodNost()));
		newAttributo.setAttributo(prop.getPropertyName());
		if (prop.getVocabularyDescription() != null){
			newAttributo.setClasseVocabolario(prop.getVocabularyDescription().getClassName());
		}
		
		//String nomeComplesso = newAttributo.getAttributo() != null ? parent.getAttributo() + "." + newAttributo.getAttributo() : newAttributo.getAttributo();
		//newAttributo.setNomeComplesso(nomeComplesso);
		newAttributo.setNomeComplesso(nameParentHierarchy + "." + prop.getPropertyName());

		newAttributo.setFlgMultiplo((prop.isMultiple() ? "1" : "0"));
		newAttributo.setFlgObbl((prop.isMandatory() ? "1" : "0"));			
		String label = prop.getPropertyLabel();
		if (prop.getPropertyLabel().length() > 100){
			label = prop.getPropertyLabel().substring(0, 99);
		}		
		newAttributo.setLabel( label.contains("'") ? label.replace("'", "''") : label);
		
		newAttributo.setTipo(prop.getPropertyType());	
		newAttributo.setIdPadre(new BigDecimal(parent.getIdAttributo()) );	
		System.out.println(" ");
		for(int i = 0; i < levelHierarchy; i++) {
			System.out.print("\t");
		}		
		return daoAttributi.save(newAttributo); 
	}

//	public void writePlainPropertyHierarchy(WsPropertyDescriptor prop, CostNostId id, AttributiOst parent, String nameParentHierarchy, int levelHierarchy) throws Exception {		
//		PropertyDescriptorReader reader = new PropertyDescriptorReader();			
//		if (prop.getWsPropertyDescriptors() != null) {
//
//			for (WsPropertyDescriptor p : prop.getWsPropertyDescriptors()) {
//				if((p.isMandatory() && !(p.isMultiple())) || isInUbicazioniOst(p.getPropertyName())) {
//
//					AttributiOst strProp = saveAttributiOst(prop, id, parent, nameParentHierarchy, levelHierarchy + 1);
//
//					reader.write(p, id, parent.getIdAttributo(), nameParentHierarchy);
//					for(int i = 0; i < levelHierarchy; i++) {
//						System.out.print("\t");
//					}		
//
//					AttributiOst toPass = (levelHierarchy <= 1) ? strProp : parent;
//
//					System.out.println("Saving "+p.getPropertyType()+" -> " + p.getPropertyName() + " Level: " + levelHierarchy + " realParent " + nameParentHierarchy+" Label:"+p.getPropertyLabel());
//
//					writePlainPropertyHierarchy(p, id, toPass, nameParentHierarchy, levelHierarchy + 1);
//				}
//			}
//		}
//	}

	private boolean excludeGenericFieldsByNomeComplesso(String nomeComplesso){
		if(nomeComplesso.equals("caratterizzazioniOst.denominazione")){
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.denoAlias")){  
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.dataInizio")){
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.dataFine")){
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.dataValUtente")){
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.dataValPFR")){
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.flagUfficiale")){ //Label: Eventuale denominazione alternativa OST presso la fonte dati di riferimento (solo da banche dati es
			return false;
		}else if(nomeComplesso.equals("caratterizzazioniOst.flagValPFR")){ 			
			return false;		
		}else if(nomeComplesso.equals("caratterizzazioniOst.flagValUtente")){ 			
			return false;	
		}else if(nomeComplesso.equals("caratterizzazioniOst.note")){ 			
			return false;}
		return true;
	}



	public boolean excludeFields(String propertyName){		 
		if(propertyName.equals("ubicazioniOst")){
			return false;
		}else if(propertyName.equals("fontiDati")){
			return false;
		}else if(propertyName.equals("wsFonteRef")){
			return false;}

		return true;
	}


	public boolean isInUbicazioniOst(String propertyName){

		if(propertyName.equals("ubicazioniOst")){
			return true;
		}else if(propertyName.equals("cap")){
			return true;
		}else if(propertyName.equals("comune")){
			return true;			
		}else if(propertyName.equals("comuneItalia")){
			return true;
		}else if(propertyName.equals("comuniItalia")){
			return true;	
		}else if(propertyName.equals("datiCatastalis")){
			return true;
		}else if(propertyName.equals("gbEst")){
			return true;
		}else if(propertyName.equals("gbNord")){
			return true;
		}else if(propertyName.equals("geocodice")){
			return true;
		}else if(propertyName.equals("localita")){
			return true;
		}else if(propertyName.equals("nomeStrada")){
			return true;
		}else if(propertyName.equals("numeroCivico")){
			return true;
		}else if(propertyName.equals("provincia")){
			return true;
		}else if(propertyName.equals("quotaSlmM")){
			return true;
		}else if(propertyName.equals("regione")){
			return true;
		}else if(propertyName.equals("tipoStrada")){
			return true;
		}else if(propertyName.equals("wgs84X")){
			return true;
		}else if(propertyName.equals("wgs84Y")){
			return true;
		}					
		return false;		
	}


	public boolean checkMandatoryPropertyExists(WsCcostDescriptor descriptor){
		boolean foundMandatoryProperty= false;
		boolean foundMandatoryPropertyChild= false;

		if (descriptor.getWsPropertyDescriptors() != null) {			
			for (WsPropertyDescriptor prop : descriptor.getWsPropertyDescriptors()) {
				if(prop.isMandatory() && !(prop.isMultiple()) ) {					
					foundMandatoryProperty = true;
					break;
				}
			}
		}

		if (descriptor.getChildCcostDescriptor() != null) {
			foundMandatoryPropertyChild = checkMandatoryPropertyExists(descriptor.getChildCcostDescriptor());
		}

		return foundMandatoryProperty | foundMandatoryPropertyChild;
	}

	public String toXml(WsCcostDescriptor descriptor) {
		String ret = "<" + descriptor.getPropertyType() + ">";
		PropertyDescriptorReader reader = new PropertyDescriptorReader();
		if (descriptor.getWsPropertyDescriptors() != null) {
			for (WsPropertyDescriptor prop : descriptor.getWsPropertyDescriptors()) {
				ret += reader.toXml(prop);
			}
		}
		if (descriptor.getChildCcostDescriptor() != null) {
			ret += toXml(descriptor.getChildCcostDescriptor());
		}
		ret += "</" + descriptor.getPropertyType() + ">";
		return ret;
	}

	public String getCCost(CostNostId id) throws RemoteException {
		// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		String caratterizzazione = "";
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		WsGetCategoriaMetadataRequest request = new WsGetCategoriaMetadataRequest();
		request.setCostNostId(id);
		WsGetCategoriaMetadataResponse response;
		response = service.getCatastiProxy().getCategoriaMetadata(request);
		WsCcostDescriptor descriptor = response.getWsCcostDescriptor();
		while (descriptor != null) {
			caratterizzazione += descriptor.getPropertyType() + ".";
			descriptor = descriptor.getChildCcostDescriptor();
		}
		return caratterizzazione.substring(0, caratterizzazione.length() - 1);

	}

	public List<String> getExcludedAttributes() {
		return excludedAttributes;
	}

	public void setExcludedAttributes(List<String> excludedAttributes) {
		this.excludedAttributes = excludedAttributes;
	}

	
	public WsCostNostRef[] getAvailableCategoriaList() throws SiraBusinessException, InterruptedException {
		WsCostNostRef[] retVal = null;
		try {
			// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			
			WsGetAvailableCategoriaListRequest request = new WsGetAvailableCategoriaListRequest();
			retVal = service.getCatastiProxy().getAvailableCategoriaList(request);
			
			log.info("\n\n\ngetAvailableCategoriaList invocato\n\n\n");
			
		} catch (RemoteException e) {
			log.error("DataModelGetter: Impossibile ottenere la lista di categorie istanziabili.");
			
			Thread.sleep(3000);
			
			throw new SiraBusinessException("DataModelGetter: Impossibile ottenere la lista di categorie istanziabili.", e);
		}
		
		return retVal;
	}
	
	
	public WsGetCategoriaInfoResponse getCategoriaInfo(CostNostId costNostId) throws SiraBusinessException {
		WsGetCategoriaInfoResponse retVal = null;
		try {
			// SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
			SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
			if (service.getProxyManager().needProxy()) {
				service.getProxySetter().initProxy();
			}
			
			WsGetCategoriaInfoRequest request = new WsGetCategoriaInfoRequest();
			request.setCostNostId(costNostId);
			retVal = service.getCatastiProxy().getCategoriaInfo(request);
//			log.info("\n\n\ngetCategoriaInfo invocato\n\n\n");
		} catch (RemoteException e) {
			log.error("DataModelGetter: Impossibile ottenere la info della categoria " 
					+ costNostId.getCodNost() + " e categoria: " + costNostId.getCodCost());
			
			throw new SiraBusinessException("DataModelGetter: Impossibile ottenere la info della categoria " 
					+ costNostId.getCodNost() + " e categoria: " + costNostId.getCodCost(), e);
		}
		return retVal;
	}
}
