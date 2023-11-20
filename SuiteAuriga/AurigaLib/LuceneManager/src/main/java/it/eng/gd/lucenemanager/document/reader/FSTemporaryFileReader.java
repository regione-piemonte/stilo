/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.persistence.Entity;

@Entity
public class FSTemporaryFileReader extends FileReader {
	
    private java.io.File f_in = null;

	public FSTemporaryFileReader(File arg0) throws FileNotFoundException {
		super(arg0);
		f_in = arg0;
	}

	public FSTemporaryFileReader(String arg0) throws FileNotFoundException {
		super(arg0);
		f_in = new File(arg0);
	}

	public void close() throws IOException {
		super.close();
		if (f_in != null) 
			try { f_in.delete(); } catch (Exception e) {}
	}
    
}
