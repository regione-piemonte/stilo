package it.eng.utility.pdfproducer;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;

public interface PdfConverter {

	void generatePdf(String htmlContent, Charset charset, File pdf, URL baseURL, boolean skipCleaningup) throws Exception;

	void generatePdf(String htmlContent, Charset charset, File pdf, boolean skipCleaningup) throws Exception;

	void generatePdf(String htmlContent, File pdf, URL baseURL, boolean skipCleaningup) throws Exception;

	void generatePdf(String htmlContent, File pdf, boolean skipCleaningup) throws Exception;

	Path getBaseDir();

	void generatePdfMode2(String htmlContent, Charset charset, File pdf, URL baseURL, boolean skipCleaningup) throws Exception;

	void generatePdfMode2(String htmlContent, Charset charset, File pdf, boolean skipCleaningup) throws Exception;

	void generatePdfMode2(String htmlContent, File pdf, URL baseURL, boolean skipCleaningup) throws Exception;

	void generatePdfMode2(String htmlContent, File pdf, boolean skipCleaningup) throws Exception;
}
