// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GsubTable implements Table, LookupSubtableFactory
{
    private ScriptList scriptList;
    private FeatureList featureList;
    private LookupList lookupList;
    
    protected GsubTable(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(directoryEntry.getOffset());
        randomAccessFile.readInt();
        final int unsignedShort = randomAccessFile.readUnsignedShort();
        final int unsignedShort2 = randomAccessFile.readUnsignedShort();
        final int unsignedShort3 = randomAccessFile.readUnsignedShort();
        this.scriptList = new ScriptList(randomAccessFile, directoryEntry.getOffset() + unsignedShort);
        this.featureList = new FeatureList(randomAccessFile, directoryEntry.getOffset() + unsignedShort2);
        this.lookupList = new LookupList(randomAccessFile, directoryEntry.getOffset() + unsignedShort3, this);
    }
    
    public LookupSubtable read(final int n, final RandomAccessFile randomAccessFile, final int n2) throws IOException {
        LookupSubtable lookupSubtable = null;
        switch (n) {
            case 1: {
                lookupSubtable = SingleSubst.read(randomAccessFile, n2);
            }
            case 2: {}
            case 4: {
                lookupSubtable = LigatureSubst.read(randomAccessFile, n2);
            }
        }
        return lookupSubtable;
    }
    
    public int getType() {
        return 1196643650;
    }
    
    public ScriptList getScriptList() {
        return this.scriptList;
    }
    
    public FeatureList getFeatureList() {
        return this.featureList;
    }
    
    public LookupList getLookupList() {
        return this.lookupList;
    }
    
    public String toString() {
        return "GSUB";
    }
}
