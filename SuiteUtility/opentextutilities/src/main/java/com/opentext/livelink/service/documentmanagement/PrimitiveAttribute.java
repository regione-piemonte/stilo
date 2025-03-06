
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PrimitiveAttribute complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PrimitiveAttribute">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}Attribute">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrimitiveAttribute")
@XmlSeeAlso({
    BooleanAttribute.class,
    MultiLineAttribute.class,
    UserAttribute.class,
    StringAttribute.class,
    RealAttribute.class,
    ItemReferenceAttribute.class,
    DateAttribute.class,
    IntegerAttribute.class
})
public class PrimitiveAttribute
    extends Attribute
{


}
