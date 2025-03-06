package it.eng.server;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

public class FileElaborate {

	private File unsigned;
	private File signed;
	private PdfSignatureAppearance pdfsignature;
	private ByteArrayOutputStream outStream;
	private String id;
	
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
	
}
