package eng.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import it.eng.utility.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class CommUtil {

	private static final int BUFFER_SIZE = 65536;
	/*
	Metodo che restituisce la data e l'ora corretente formattato
	nella maniera seguente: YYYYMMDDHHMMSSmmm
	Tipicamente usato per generare un nome di file univoco
*/
	public static String getStrDate()
	{
		Calendar c = Calendar.getInstance();

		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DATE);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int mi = c.get(Calendar.MINUTE);
		int ms = c.get(Calendar.MILLISECOND);
		String mm = Integer.toString(m);
		String dd = Integer.toString(d);
		String hh = Integer.toString(h);
		String mmi = Integer.toString(mi);
		String mms = Integer.toString(ms);

		String repDt = c.get(Calendar.YEAR) + (m < 10 ? "0" + mm : mm) + (d < 10 ? "0" + dd : dd);
		String repOra = (h < 10 ? "0" + hh : hh) + (mi < 10 ? "0" + mmi : mmi) + (ms < 10 ? "0" + mms : mms);

		return repDt + repOra;
	}
	
    public static void copyFile(File from, File to) throws IOException
    {
        FileInputStream fis = new FileInputStream( from );
        FileOutputStream fos = new FileOutputStream( to );
        try
        {
        	byte[] buffer = new byte[BUFFER_SIZE];
        	int byte_count = 0;
        	while ( (byte_count = fis.read(buffer)) > 0)
        	{
                fos.write(buffer,0,byte_count);
        	}
        } 
        catch (IOException ex)
        {
            // chiudo tutto e poi cancello l'eventuale file
            try { fos.close(); } catch (Exception ex1){}
           
            FileUtil.deleteFile(to);
            
            throw ex;
        }
        finally
        {
            try { fos.close(); } catch (Exception ex){}
            try { fis.close(); } catch (Exception ex){}
        }
    }
}
