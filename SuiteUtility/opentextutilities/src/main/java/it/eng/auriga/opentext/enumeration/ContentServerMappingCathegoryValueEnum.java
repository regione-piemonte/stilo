package it.eng.auriga.opentext.enumeration;
//package it.eng.auriga.opentext.enumeration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import it.eng.auriga.opentext.service.cs.util.ContentServerDateValueCathegory;
//import it.eng.auriga.opentext.service.cs.util.ContentServerIntegerValueCathegory;
//import it.eng.auriga.opentext.service.cs.util.ContentServerStringValueCathegory;
//import it.eng.auriga.opentext.service.cs.util.IContentServerCathegoryType;
//
//public enum ContentServerMappingCathegoryValueEnum {
//
//	STRING_VALUE("com.opentext.livelink.service.core.StringValue", ContentServerStringValueCathegory.class),
//	INTEGER_VALUE("com.opentext.livelink.service.core.IntegerValue", ContentServerIntegerValueCathegory.class),
//	DATE_VALUE("com.opentext.livelink.service.core.DateValue", ContentServerDateValueCathegory.class);
//
//	private String classCathegory;
//	private Class<? extends IContentServerCathegoryType> typeCathegory;
//
//	private ContentServerMappingCathegoryValueEnum(String classCathegoryDescription,
//			Class<? extends IContentServerCathegoryType> typeCathegoryDescription) {
//		this.classCathegory = classCathegoryDescription;
//		this.typeCathegory = typeCathegoryDescription;
//	}
//
//	public static Map<String, Class<? extends IContentServerCathegoryType>> buildMapContentServerTypeCathegory() {
//		Map<String, Class<? extends IContentServerCathegoryType>> mapTypeCathegory = new HashMap<String, Class<? extends IContentServerCathegoryType>>();
//		mapTypeCathegory.put(ContentServerMappingCathegoryValueEnum.STRING_VALUE.getClassCathegory(),
//				ContentServerMappingCathegoryValueEnum.STRING_VALUE.getTypeCathegory());
//		mapTypeCathegory.put(ContentServerMappingCathegoryValueEnum.INTEGER_VALUE.getClassCathegory(),
//				ContentServerMappingCathegoryValueEnum.INTEGER_VALUE.getTypeCathegory());
//		mapTypeCathegory.put(ContentServerMappingCathegoryValueEnum.DATE_VALUE.getClassCathegory(),
//				ContentServerMappingCathegoryValueEnum.DATE_VALUE.getTypeCathegory());
//
//		return mapTypeCathegory;
//	}
//
//	public String getClassCathegory() {
//		return classCathegory;
//	}
//
//	public void setClassCathegory(String classCathegory) {
//		this.classCathegory = classCathegory;
//	}
//
//	public Class<? extends IContentServerCathegoryType> getTypeCathegory() {
//		return typeCathegory;
//	}
//
//	public void setTypeCathegory(Class<? extends IContentServerCathegoryType> typeCathegory) {
//		this.typeCathegory = typeCathegory;
//	}
//
//	/**
//	 * a fronte della classe restituisce il tipo della category
//	 * 
//	 * @param classCathegoryDescription
//	 * @return
//	 */
//
//	public static Class<? extends IContentServerCathegoryType> getTypeByClassCathegory(String classCathegoryDescription) {
//		for (ContentServerMappingCathegoryValueEnum csMappingCathegory : ContentServerMappingCathegoryValueEnum
//				.values()) {
//			if (csMappingCathegory.getClassCathegory().equals(classCathegoryDescription))
//				return csMappingCathegory.getTypeCathegory();
//		}
//		return null;
//	}
//
//}
