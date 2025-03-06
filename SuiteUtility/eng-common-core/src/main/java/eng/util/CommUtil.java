// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import java.io.IOException;
import it.eng.utility.FileUtil;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Calendar;

public class CommUtil
{
    private static final int BUFFER_SIZE = 65536;
    
    public static String getStrDate() {
        final Calendar c = Calendar.getInstance();
        final int m = c.get(2);
        final int d = c.get(5);
        final int h = c.get(11);
        final int mi = c.get(12);
        final int ms = c.get(14);
        final String mm = Integer.toString(m);
        final String dd = Integer.toString(d);
        final String hh = Integer.toString(h);
        final String mmi = Integer.toString(mi);
        final String mms = Integer.toString(ms);
        final String repDt = c.get(1) + ((m < 10) ? ("0" + mm) : mm) + ((d < 10) ? ("0" + dd) : dd);
        final String repOra = ((h < 10) ? ("0" + hh) : hh) + ((mi < 10) ? ("0" + mmi) : mmi) + ((ms < 10) ? ("0" + mms) : mms);
        return repDt + repOra;
    }
    
    public static void copyFile(final File from, final File to) throws IOException {
        final FileInputStream fis = new FileInputStream(from);
        final FileOutputStream fos = new FileOutputStream(to);
        try {
            final byte[] buffer = new byte[65536];
            int byte_count = 0;
            while ((byte_count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, byte_count);
            }
        }
        catch (IOException ex) {
            try {
                fos.close();
            }
            catch (Exception ex2) {}
            FileUtil.deleteFile(to);
            throw ex;
        }
        finally {
            try {
                fos.close();
            }
            catch (Exception ex3) {}
            try {
                fis.close();
            }
            catch (Exception ex4) {}
        }
    }
}
