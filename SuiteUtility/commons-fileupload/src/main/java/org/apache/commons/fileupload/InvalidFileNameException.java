// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.fileupload;

public class InvalidFileNameException extends RuntimeException
{
    private static final long serialVersionUID = 7922042602454350470L;
    private final String name;
    
    public InvalidFileNameException(final String pName, final String pMessage) {
        super(pMessage);
        this.name = pName;
    }
    
    public String getName() {
        return this.name;
    }
}
