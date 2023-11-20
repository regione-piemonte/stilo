/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class StreamUtils {
  
  private StreamUtils() {
    
  }
  
  
  public static byte[] loadBytes(InputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int len;
    while ((len = inputStream.read(buffer)) > 0) {
      outputStream.write(buffer, 0, len);
    }
    
    return outputStream.toByteArray();
  }

  /**
   * Metodo inutile, aggiunto per simmetria
   * @param data
   * @param outputStream
   * @throws IOException
   */
  public static void saveBytes(byte[] data, OutputStream outputStream) throws IOException {
    outputStream.write(data);
  }
  
  
  

}
