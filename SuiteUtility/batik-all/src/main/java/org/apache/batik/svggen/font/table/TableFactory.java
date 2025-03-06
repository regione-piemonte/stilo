// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TableFactory
{
    public static Table create(final DirectoryEntry directoryEntry, final RandomAccessFile randomAccessFile) throws IOException {
        Table table = null;
        switch (directoryEntry.getTag()) {
            case 1111577413: {}
            case 1128678944: {}
            case 1146308935: {}
            case 1161970772: {}
            case 1161972803: {}
            case 1161974595: {}
            case 1196445523: {
                table = new GposTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1196643650: {
                table = new GsubTable(directoryEntry, randomAccessFile);
            }
            case 1246975046: {}
            case 1280594760: {}
            case 1296909912: {}
            case 1330851634: {
                table = new Os2Table(directoryEntry, randomAccessFile);
            }
            case 1346587732: {}
            case 1668112752: {
                table = new CmapTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1668707360: {
                table = new CvtTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1718642541: {
                table = new FpgmTable(directoryEntry, randomAccessFile);
            }
            case 1719034226: {}
            case 1735162214: {
                table = new GlyfTable(directoryEntry, randomAccessFile);
            }
            case 1751474532: {
                table = new HeadTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1751672161: {
                table = new HheaTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1752003704: {
                table = new HmtxTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1801810542: {
                table = new KernTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1819239265: {
                table = new LocaTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1835104368: {
                table = new MaxpTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1851878757: {
                table = new NameTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1886545264: {
                table = new PrepTable(directoryEntry, randomAccessFile);
                break;
            }
            case 1886352244: {
                table = new PostTable(directoryEntry, randomAccessFile);
            }
        }
        return table;
    }
}
