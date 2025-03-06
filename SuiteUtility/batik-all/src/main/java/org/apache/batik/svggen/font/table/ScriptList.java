// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ScriptList
{
    private int scriptCount;
    private ScriptRecord[] scriptRecords;
    private Script[] scripts;
    
    protected ScriptList(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        this.scriptCount = 0;
        randomAccessFile.seek(n);
        this.scriptCount = randomAccessFile.readUnsignedShort();
        this.scriptRecords = new ScriptRecord[this.scriptCount];
        this.scripts = new Script[this.scriptCount];
        for (int i = 0; i < this.scriptCount; ++i) {
            this.scriptRecords[i] = new ScriptRecord(randomAccessFile);
        }
        for (int j = 0; j < this.scriptCount; ++j) {
            this.scripts[j] = new Script(randomAccessFile, n + this.scriptRecords[j].getOffset());
        }
    }
    
    public int getScriptCount() {
        return this.scriptCount;
    }
    
    public ScriptRecord getScriptRecord(final int n) {
        return this.scriptRecords[n];
    }
    
    public Script findScript(final String s) {
        if (s.length() != 4) {
            return null;
        }
        final int n = s.charAt(0) << 24 | s.charAt(1) << 16 | s.charAt(2) << 8 | s.charAt(3);
        for (int i = 0; i < this.scriptCount; ++i) {
            if (this.scriptRecords[i].getTag() == n) {
                return this.scripts[i];
            }
        }
        return null;
    }
}
