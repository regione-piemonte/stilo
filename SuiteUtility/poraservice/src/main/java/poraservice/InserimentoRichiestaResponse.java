// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import pora.types.prontoweb.eng.it.RESULTMSG;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "inserimentoRichiestaResult" })
@XmlRootElement(name = "InserimentoRichiestaResponse")
public class InserimentoRichiestaResponse
{
    @XmlElement(name = "InserimentoRichiestaResult", nillable = true)
    protected RESULTMSG inserimentoRichiestaResult;
    
    public RESULTMSG getInserimentoRichiestaResult() {
        return this.inserimentoRichiestaResult;
    }
    
    public void setInserimentoRichiestaResult(final RESULTMSG value) {
        this.inserimentoRichiestaResult = value;
    }
}
