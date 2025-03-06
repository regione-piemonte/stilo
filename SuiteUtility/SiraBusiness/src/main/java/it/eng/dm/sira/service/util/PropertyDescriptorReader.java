package it.eng.dm.sira.service.util;

import java.math.BigDecimal;

import it.eng.dm.sira.dao.DaoAttributiOst;
import it.eng.dm.sira.entity.AttributiOst;

import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.VocabularyDescription;
import com.hyperborea.sira.ws.WsPropertyDescriptor;

public class PropertyDescriptorReader {

	public String toXml(WsPropertyDescriptor property) {
		String xml = null;
		String name = property.getPropertyName();
		String type = property.getPropertyType();
		String label = property.getPropertyLabel();
		boolean mandatory = property.isMandatory();
		boolean multiple = property.isMultiple();
		xml = "<" + name + " ";
		xml = xml + "label=\"" + label + "\" ";
		xml = xml + "type=\"xs:" + type + "\" ";
		xml = xml + (mandatory ? "nillable=\" false\" " : "nillable=\" true\" ");
		xml = xml + (multiple ? "minoccurs=\"1\"" : "minoccurs=\"0\"");
		xml = xml + ">";
		if (property.getWsPropertyDescriptors() != null) {
			for (WsPropertyDescriptor child : property.getWsPropertyDescriptors()) {
				xml = xml + toXml(child);
			}
		}
		xml = xml + "</" + name + ">" + "\n";
		return xml;
	}	
	

	public void write(WsPropertyDescriptor property, CostNostId id, Integer idPadre, String nomeAttributoPadre) throws Exception {
		DaoAttributiOst daoAttributi = new DaoAttributiOst();
		String categoria = String.valueOf(id.getCodCost());
		String natura = String.valueOf(id.getCodNost());
		String name = property.getPropertyName();
		String label = property.getPropertyLabel();
		String type = property.getPropertyType();
		boolean mandatory = property.isMandatory();
		boolean multiple = property.isMultiple();
		VocabularyDescription description = property.getVocabularyDescription();
		AttributiOst attributo = new AttributiOst();
		attributo.setNomeComplesso(nomeAttributoPadre + "." + name);
		attributo.setCategoria(categoria);
		attributo.setNatura(natura);
		attributo.setFlgMultiplo(multiple == true ? "1" : "0");
		attributo.setFlgObbl(mandatory == true ? "1" : "0");
		attributo.setAttributo(name);
		attributo.setTipo(type);
		attributo.setClasseVocabolario(description != null ? type : null);
		attributo.setIdPadre(new BigDecimal(idPadre));
		attributo.setLabel(label != null ? label : PopulationUtils.createLabel(name));
		AttributiOst saved = daoAttributi.save(attributo);
		Integer idOstSaved = saved.getIdAttributo();
		
//		if (property.getWsPropertyDescriptors() != null) {
//			for (WsPropertyDescriptor child : property.getWsPropertyDescriptors()) {
//				write(child, id, idOstSaved, attributo.getNomeComplesso());
//			}
//		}

	}

}
