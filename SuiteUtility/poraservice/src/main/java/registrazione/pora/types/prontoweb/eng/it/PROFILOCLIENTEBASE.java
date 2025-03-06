// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PROFILOCLIENTEBASE", propOrder = { "codicecliente", "codicefiscale", "email", "partitaIVA" })
public class PROFILOCLIENTEBASE
{
    @XmlElement(name = "CODICECLIENTE", nillable = true)
    protected String codicecliente;
    @XmlElement(name = "CODICEFISCALE", nillable = true)
    protected String codicefiscale;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "PartitaIVA", nillable = true)
    protected String partitaIVA;
    
    public String getCODICECLIENTE() {
        return this.codicecliente;
    }
    
    public void setCODICECLIENTE(final String value) {
        this.codicecliente = value;
    }
    
    public String getCODICEFISCALE() {
        return this.codicefiscale;
    }
    
    public void setCODICEFISCALE(final String value) {
        this.codicefiscale = value;
    }
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
    
    public String getPartitaIVA() {
        return this.partitaIVA;
    }
    
    public void setPartitaIVA(final String value) {
        this.partitaIVA = value;
    }
}
