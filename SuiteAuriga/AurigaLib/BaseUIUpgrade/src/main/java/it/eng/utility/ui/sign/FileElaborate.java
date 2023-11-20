/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

public class FileElaborate implements Serializable{

	private File unsigned;
	private File signed;
	private PdfSignatureAppearance pdfsignature;
	private ByteArrayOutputStream outStream;
	private String id;
	private byte[] hash;
	private String encodedHash;
	
	public String getEncodedHash() {
		return encodedHash;
	}
	public void setEncodedHash(String encodedHash) {
		this.encodedHash = encodedHash;
	}
	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	public ByteArrayOutputStream getOutStream() {
		return outStream;
	}
	public void setOutStream(ByteArrayOutputStream outStream) {
		this.outStream = outStream;
	}

	
	public PdfSignatureAppearance getPdfsignature() {
		return pdfsignature;
	}
	public void setPdfsignature(PdfSignatureAppearance pdfsignature) {
		this.pdfsignature = pdfsignature;
	}

	
	public File getUnsigned() {
		return unsigned;
	}
	public void setUnsigned(File unsigned) {
		this.unsigned = unsigned;
	}
	public File getSigned() {
		return signed;
	}
	public void setSigned(File signed) {
		this.signed = signed;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "FileElaborate [unsigned=" + unsigned + ", signed=" + signed
				+ ", pdfsignature=" + pdfsignature + ", outStream=" + outStream
				+ ", id=" + id + ", hash=" + Arrays.toString(hash)
				+ ", encodedHash=" + encodedHash + "]";
	}
	
	
}
