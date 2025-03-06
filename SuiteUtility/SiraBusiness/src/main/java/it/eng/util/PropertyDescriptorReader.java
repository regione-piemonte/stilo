package it.eng.util;

import com.hyperborea.sira.ws.WsPropertyDescriptor;

public class PropertyDescriptorReader {

	public String toXml(WsPropertyDescriptor property) {
		String xml = null;
		String name = property.getPropertyName();
		String type = property.getPropertyType();
		boolean mandatory = property.isMandatory();
		boolean multiple = property.isMultiple();
		xml = "<xs:element name=\"" + name + "\" ";
		xml = xml + "type=\"xs:" + type + "\" ";
		xml = xml + (mandatory ? "nillable=\" true\" " : "nillable=\" false\" ");
		xml = xml + (multiple ? "minoccurs=\"1\"" : "minoccurs=\"0\"");
		xml = xml + ">";
		if(property.getWsPropertyDescriptors()!=null){
			for(WsPropertyDescriptor child : property.getWsPropertyDescriptors()){
				xml = xml + toXml(child);
			}
		}
		xml = xml + "\n"+"</xs:element>";
		return xml;
	}

}
