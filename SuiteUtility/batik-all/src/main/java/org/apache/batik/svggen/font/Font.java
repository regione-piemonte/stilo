// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font;

import java.io.IOException;
import org.apache.batik.svggen.font.table.TableFactory;
import java.io.RandomAccessFile;
import java.io.File;
import org.apache.batik.svggen.font.table.GlyphDescription;
import org.apache.batik.svggen.font.table.PostTable;
import org.apache.batik.svggen.font.table.NameTable;
import org.apache.batik.svggen.font.table.MaxpTable;
import org.apache.batik.svggen.font.table.LocaTable;
import org.apache.batik.svggen.font.table.HmtxTable;
import org.apache.batik.svggen.font.table.HheaTable;
import org.apache.batik.svggen.font.table.HeadTable;
import org.apache.batik.svggen.font.table.GlyfTable;
import org.apache.batik.svggen.font.table.CmapTable;
import org.apache.batik.svggen.font.table.Os2Table;
import org.apache.batik.svggen.font.table.Table;
import org.apache.batik.svggen.font.table.TableDirectory;

public class Font
{
    private String path;
    private TableDirectory tableDirectory;
    private Table[] tables;
    private Os2Table os2;
    private CmapTable cmap;
    private GlyfTable glyf;
    private HeadTable head;
    private HheaTable hhea;
    private HmtxTable hmtx;
    private LocaTable loca;
    private MaxpTable maxp;
    private NameTable name;
    private PostTable post;
    
    public Font() {
        this.tableDirectory = null;
    }
    
    public Table getTable(final int n) {
        for (int i = 0; i < this.tables.length; ++i) {
            if (this.tables[i] != null && this.tables[i].getType() == n) {
                return this.tables[i];
            }
        }
        return null;
    }
    
    public Os2Table getOS2Table() {
        return this.os2;
    }
    
    public CmapTable getCmapTable() {
        return this.cmap;
    }
    
    public HeadTable getHeadTable() {
        return this.head;
    }
    
    public HheaTable getHheaTable() {
        return this.hhea;
    }
    
    public HmtxTable getHmtxTable() {
        return this.hmtx;
    }
    
    public LocaTable getLocaTable() {
        return this.loca;
    }
    
    public MaxpTable getMaxpTable() {
        return this.maxp;
    }
    
    public NameTable getNameTable() {
        return this.name;
    }
    
    public PostTable getPostTable() {
        return this.post;
    }
    
    public int getAscent() {
        return this.hhea.getAscender();
    }
    
    public int getDescent() {
        return this.hhea.getDescender();
    }
    
    public int getNumGlyphs() {
        return this.maxp.getNumGlyphs();
    }
    
    public Glyph getGlyph(final int n) {
        return (this.glyf.getDescription(n) != null) ? new Glyph(this.glyf.getDescription(n), this.hmtx.getLeftSideBearing(n), this.hmtx.getAdvanceWidth(n)) : null;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public TableDirectory getTableDirectory() {
        return this.tableDirectory;
    }
    
    protected void read(final String s) {
        this.path = s;
        final File file = new File(s);
        if (!file.exists()) {
            return;
        }
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            this.tableDirectory = new TableDirectory(randomAccessFile);
            this.tables = new Table[this.tableDirectory.getNumTables()];
            for (short n = 0; n < this.tableDirectory.getNumTables(); ++n) {
                this.tables[n] = TableFactory.create(this.tableDirectory.getEntry(n), randomAccessFile);
            }
            randomAccessFile.close();
            this.os2 = (Os2Table)this.getTable(1330851634);
            this.cmap = (CmapTable)this.getTable(1668112752);
            this.glyf = (GlyfTable)this.getTable(1735162214);
            this.head = (HeadTable)this.getTable(1751474532);
            this.hhea = (HheaTable)this.getTable(1751672161);
            this.hmtx = (HmtxTable)this.getTable(1752003704);
            this.loca = (LocaTable)this.getTable(1819239265);
            this.maxp = (MaxpTable)this.getTable(1835104368);
            this.name = (NameTable)this.getTable(1851878757);
            this.post = (PostTable)this.getTable(1886352244);
            this.hmtx.init(this.hhea.getNumberOfHMetrics(), this.maxp.getNumGlyphs() - this.hhea.getNumberOfHMetrics());
            this.loca.init(this.maxp.getNumGlyphs(), this.head.getIndexToLocFormat() == 0);
            this.glyf.init(this.maxp.getNumGlyphs(), this.loca);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Font create() {
        return new Font();
    }
    
    public static Font create(final String s) {
        final Font font = new Font();
        font.read(s);
        return font;
    }
}
