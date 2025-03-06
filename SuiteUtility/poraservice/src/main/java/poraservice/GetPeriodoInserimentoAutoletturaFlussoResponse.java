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
@XmlType(name = "", propOrder = { "getPeriodoInserimentoAutoletturaFlussoResult" })
@XmlRootElement(name = "GetPeriodoInserimentoAutoletturaFlussoResponse")
public class GetPeriodoInserimentoAutoletturaFlussoResponse
{
    @XmlElement(name = "GetPeriodoInserimentoAutoletturaFlussoResult", nillable = true)
    protected RESULTMSG getPeriodoInserimentoAutoletturaFlussoResult;
    
    public RESULTMSG getGetPeriodoInserimentoAutoletturaFlussoResult() {
        return this.getPeriodoInserimentoAutoletturaFlussoResult;
    }
    
    public void setGetPeriodoInserimentoAutoletturaFlussoResult(final RESULTMSG value) {
        this.getPeriodoInserimentoAutoletturaFlussoResult = value;
    }
}
