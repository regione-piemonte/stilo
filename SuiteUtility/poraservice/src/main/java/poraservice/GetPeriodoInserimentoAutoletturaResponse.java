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
@XmlType(name = "", propOrder = { "getPeriodoInserimentoAutoletturaResult" })
@XmlRootElement(name = "GetPeriodoInserimentoAutoletturaResponse")
public class GetPeriodoInserimentoAutoletturaResponse
{
    @XmlElement(name = "GetPeriodoInserimentoAutoletturaResult", nillable = true)
    protected RESULTMSG getPeriodoInserimentoAutoletturaResult;
    
    public RESULTMSG getGetPeriodoInserimentoAutoletturaResult() {
        return this.getPeriodoInserimentoAutoletturaResult;
    }
    
    public void setGetPeriodoInserimentoAutoletturaResult(final RESULTMSG value) {
        this.getPeriodoInserimentoAutoletturaResult = value;
    }
}
