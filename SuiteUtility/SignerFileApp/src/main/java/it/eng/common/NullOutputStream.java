/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream
    extends OutputStream
{
    public void write(byte[] buf)
        throws IOException
    {
        // do nothing
    }

    public void write(byte[] buf, int off, int len)
        throws IOException
    {
        // do nothing
    }
    
    public void write(int b) throws IOException
    {
        // do nothing
    }
}