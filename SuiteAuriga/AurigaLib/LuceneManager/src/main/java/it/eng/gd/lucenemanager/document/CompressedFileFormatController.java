/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

@Entity
public class CompressedFileFormatController {

	private File archiveFile = null;
	private InputStream archiveInputStream = null;
	private String tempDir = null;
	private String mimeType = null;
	private int maxFileToCheck = Integer.MAX_VALUE;
	private Map<String, String> formatiAmmessi = null;
	// private static final String TOO_MANY_FILES_MSG = "L'archivio compresso contiene un numero di file superiore al massimo consentito";
	// private static final String COMPRESSED_FILE_INSIDE_MSG = "Non sono ammessi file archivio contenenti a loro volta altri file archivio";
	// private static final String UNAUTHORIZED_FILE_INSIDE_MSG = "Il file archivio da caricare contiene file di formato non ammesso: ";

	private static HashMap<String, String> internalFormatsHashMap;
	static {
		internalFormatsHashMap = new HashMap<String, String>();
		internalFormatsHashMap.put("application/x-tar", "tarCheck");
		internalFormatsHashMap.put("application/x-gzip", "tgzCheck");
		internalFormatsHashMap.put("application/gnutar", "tgzCheck");
		internalFormatsHashMap.put("application/x-compressed", "tgzCheck");
		internalFormatsHashMap.put("application/zip", "zipCheck");
		internalFormatsHashMap.put("application/x-compressed", "zipCheck");
		internalFormatsHashMap.put("application/x-zip-compressed", "zipCheck");
		internalFormatsHashMap.put("multipart/x-zip", "zipCheck");
		internalFormatsHashMap.put("application/x-rar", "rarCheck");
		internalFormatsHashMap.put("application/x-rar-compressed", "rarCheck");
	}

	protected CompressedFileFormatController() {
	}

	public CompressedFileFormatController(File archiveFile, String tempDir, String mimeType, int maxFileToCheck, Map<String, String> formatiAmmessi) {
		this.archiveFile = archiveFile;
		this.tempDir = tempDir;
		this.mimeType = mimeType;
		this.maxFileToCheck = maxFileToCheck;
		this.formatiAmmessi = formatiAmmessi;
	}

	public CompressedFileFormatController(InputStream archiveInputStream, String tempDir, String mimeType, int maxFileToCheck,
			Map<String, String> formatiAmmessi) throws Exception {
		this.archiveInputStream = archiveInputStream;
		this.tempDir = tempDir;
		this.mimeType = mimeType;
		this.maxFileToCheck = maxFileToCheck;
		this.formatiAmmessi = formatiAmmessi;
		writeInputStreamToFile(archiveInputStream, this.archiveFile, 4096);
	}

	public boolean operate() throws Exception {
		try {
			String methodStr = (String) internalFormatsHashMap.get(mimeType);
			Method mthd = this.getClass().getMethod(methodStr, new Class[] {});
			Boolean ret = ((Boolean) mthd.invoke(this, new Object[] {}));
			return (ret == null) ? false : ret.booleanValue();
		} catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException().getMessage());
		} catch (Throwable e) {
			throw new Exception(e.getMessage());
		}
	}

	public static boolean isCompressedFile(String mimetype) {
		return internalFormatsHashMap.containsKey(mimetype);
	}

	public Boolean zipCheck() throws Exception {
		return null;
	}

	public Boolean tarCheck() throws Exception {
		return null;
	}

	public Boolean rarCheck() throws Exception {
		return null;
	}

	public Boolean tgzCheck() throws Exception {
		return null;
	}

	protected Boolean doOperation(File file, String rootpath) throws Exception {
		return null;
	}

	protected void scanDirectoryForDelete(File file) throws Exception {
		File[] files = file.listFiles();
		List<File> listaFile = new ArrayList<File>();
		List<File> listaDir = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				listaDir.add(files[i]);
			} else {
				listaFile.add(files[i]);
			}
		}

		Iterator<File> dirit = listaDir.iterator();
		while (dirit.hasNext()) {
			File f = (File) dirit.next();
			scanDirectoryForDelete(f);
			f.delete();
		}

		Iterator<File> fileit = listaFile.iterator();
		while (fileit.hasNext()) {
			File f = (File) fileit.next();
			try {
				f.delete();
			} catch (Exception e) {
			}
		}
	}

	private void writeInputStreamToFile(InputStream inputStream, File f, int buffer) {
		try {
			java.io.OutputStream out = new FileOutputStream(f);
			byte buf[] = new byte[buffer];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();
		} catch (IOException e) {
		}
	}

	public File getArchiveFile() {
		return archiveFile;
	}

	public void setArchiveFile(File archiveFile) {
		this.archiveFile = archiveFile;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public int getMaxFileToCheck() {
		return maxFileToCheck;
	}

	public void setMaxFileToCheck(int maxFileToCheck) {
		this.maxFileToCheck = maxFileToCheck;
	}

	public Map<String, String> getFormatiAmmessi() {
		return formatiAmmessi;
	}

	public void setFormatiAmmessi(Map<String, String> formatiAmmessi) {
		this.formatiAmmessi = formatiAmmessi;
	}

}
