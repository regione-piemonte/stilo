package it.eng.suiteutility.dynamiccodedetector.pdf;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDJavascriptNameTreeNode;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.PDDestinationOrAction;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDDocumentCatalogAdditionalActions;
//import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;

public class PDFExecutableDetector implements DynamicCodeDetector {

	private static Logger log = LogManager.getLogger(PDFExecutableDetector.class);

	public enum DETECT_TYPE {
		PDF_BOX, PDF_ID;
	}

	public static final String[] PREVENTED_KEYWORDS = { "/JavaScript", "/JS", "/Launch" };
	public static final String[] PREVENTED_KEYWORDS_EXTENDED = { "/JavaScript", "/JS", "/Launch", "/AA", "/OpenAction" };

	private static final Collection<String> preventedKeyWordsSet = new HashSet<String>(Arrays.asList(PREVENTED_KEYWORDS));
	private static final Collection<String> preventedKeyWordsExtendedSet = new HashSet<String>(Arrays.asList(PREVENTED_KEYWORDS_EXTENDED));

	private static final String KEYWORD_TAG = "Keyword";
	private static final String KEYWORD_ATTRIBUTE_NAME = "Name";
	private static final String KEYWORD_ATTRIBUTE_COUNT = "Count";

	private DETECT_TYPE detectMethod = DETECT_TYPE.PDF_ID;

	private DocumentBuilder documentBuilder = null;

	private PyCode compiledScript;
	private PythonInterpreter scriptInterpreter;
	private PySystemState sys;

	// Percorso delle librerie jython
	public static String JYTHON_LIB = null;

	public PDFExecutableDetector() {

		sys = Py.getSystemState();
		// scriptInterpreter = new PythonInterpreter(null, new PySystemState());
		scriptInterpreter = new PythonInterpreter(null, sys);

		// sys.path.append(new PyString("C:\\workspace_formats\\libraries\\Jython\\Lib"));
		if (JYTHON_LIB == null) {
			// JYTHON_LIB = "C:/jython2.7.0/";
			JYTHON_LIB = this.getClass().getResource("/lib/jython/").getFile();
			// JYTHON_LIB = PythonInterpreter.class.getResource("/libJython/").getFile();
		}
		log.info("JYTHON_LIB " + JYTHON_LIB);
		File jythonDir = new File(JYTHON_LIB);
		sys.path.append(new PyString(jythonDir.getAbsolutePath()));

		// sys.path.append(new PyString(jythonDir.getAbsolutePath()));

		InputStream is = this.getClass().getResourceAsStream("/it/eng/suiteutility/dynamiccodedetector/pdf/python/pdfid.py");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			String script = sb.toString();
			// log.info("Script " + script );
			compiledScript = scriptInterpreter.compile(script);
		} catch (IOException e) {
			log.error("Eccezione PDFExecutableDetector", e);
		}
	}

	public void executePDFBox(File file, ValidationInfos validationInfos) throws IOException {
		InputStream is = new FileInputStream(file);
		executePDFBox(is, validationInfos);
	}

	public void executePDFBox(InputStream is, ValidationInfos validationInfos) throws IOException {
		PDDocument document = PDDocument.load(is);
		PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
		PDDocumentNameDictionary nameDictionary = documentCatalog.getNames();

		if (nameDictionary != null) {

			/*
			 * Controllo i file embedded
			 */
			PDEmbeddedFilesNameTreeNode embeddedTreeNode = nameDictionary.getEmbeddedFiles();
			//Map<String, COSObjectable> names = embeddedTreeNode.getNames();
			Map<String, PDComplexFileSpecification> names = embeddedTreeNode.getNames();
			// Map<String, Object> names = embeddedTreeNode.getNames();
			Set<String> keys = names.keySet();
			for (String key : keys) {
				Object obj = names.get(key);
				// System.out.println("key: " + key+ " object: "+ obj);
				if (obj instanceof PDComplexFileSpecification) {
					PDComplexFileSpecification complexFile = (PDComplexFileSpecification) obj;
					PDEmbeddedFile pdEmbeddedFile = complexFile.getEmbeddedFile();
					InputStream is2 = pdEmbeddedFile.createInputStream();

					try {
						executePDFBox(is2, validationInfos);
					} catch (IOException e) {
						validationInfos.addError(e.getMessage());
					}

				}
			}

			/*
			 * Controllo la presenza di codice javascript
			 */
			PDJavascriptNameTreeNode javascriptNode = nameDictionary.getJavaScript();
			if (javascriptNode != null)
				validationInfos.addError("PDF contains untrusted javascript code");
		}

		List<COSObject> cosActionObjects = document.getDocument().getObjectsByType("Action");
		if (cosActionObjects != null) {
			for (COSObject cosActionObject : cosActionObjects) {

				COSBase baseWin = cosActionObject.getItem(COSName.getPDFName("Win"));
				COSBase baseDos = cosActionObject.getItem(COSName.getPDFName("Dos"));
				COSBase baseMac = cosActionObject.getItem(COSName.getPDFName("Mac"));
				COSBase baseUnix = cosActionObject.getItem(COSName.getPDFName("Unix"));

				if (baseWin != null || baseDos != null || baseMac != null || baseUnix != null)
					validationInfos.addError("PDF contains launch instructions");

			}
		}

		// Controllo se esiste un'azione da eseguire all'apertura del file
		PDDestinationOrAction openAction = documentCatalog.getOpenAction();
		if (openAction != null) {
			if (openAction != null)
				validationInfos.addError("PDF contains document open actions");
		}

		// Controllo se esistono ulteriori azioni eseguibili innescate da altri controlli sull'intero documento
		PDDocumentCatalogAdditionalActions additionalActions = documentCatalog.getActions();
		if (parseDocumentAdditionalActions(additionalActions))
			validationInfos.addError("PDF contains document additional actions");
		;
	}

	private boolean parseDocumentAdditionalActions(PDDocumentCatalogAdditionalActions additionalActions) {
		PDAction afterPrintAction = additionalActions.getDP();
		PDAction afterSaveAction = additionalActions.getDS();
		PDAction beforeCloseAction = additionalActions.getWC();
		PDAction beforePrintAction = additionalActions.getWP();
		PDAction beforeSaveAction = additionalActions.getWS();
		if (afterPrintAction != null || afterSaveAction != null || beforeCloseAction != null || beforePrintAction != null || beforeSaveAction != null)
			return true;
		return false;
	}

	public void executePDFId(File pdfFile, ValidationInfos validationInfos, boolean useExtendedMethod)
			throws IOException, ParserConfigurationException, SAXException {
		// PySystemState sys = Py.getSystemState();
		// log.info("pdfFile.getAbsolutePath() " + pdfFile.getAbsolutePath());
		PyString pyStringFilePath = new PyString(pdfFile.getAbsolutePath());
		sys.argv.append(pyStringFilePath);
		// log.info(PythonInterpreter.class.getProtectionDomain().getCodeSource().getLocation());
		log.debug("scriptInterpreter " + scriptInterpreter + " compiledScript " + compiledScript);

		try {
			if (scriptInterpreter != null && compiledScript != null) {
				scriptInterpreter.exec(compiledScript);
			}
		} catch (Throwable e) {
			log.error("Eccezione executePDFId", e);
		}
		PyString obj = (PyString) scriptInterpreter.eval("__javaResult__.toprettyxml()");
		log.debug(obj);
		identifyPreventedKeywords(obj, validationInfos, useExtendedMethod);
		sys.argv.remove(pyStringFilePath);
		scriptInterpreter.cleanup();

		Py.setSystemState(null);
	}

	private void identifyPreventedKeywords(PyObject obj, ValidationInfos validationInfos, boolean useExtendedMethod)
			throws ParserConfigurationException, SAXException, IOException {
		if (documentBuilder == null)
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		ByteArrayInputStream bais = new ByteArrayInputStream(obj.toString().getBytes());
		Document document = documentBuilder.parse(bais);
		Element root = document.getDocumentElement();
		NodeList keywords = root.getElementsByTagName(KEYWORD_TAG);
		// log.info("keywords" + keywords);
		if (keywords != null) {
			for (int i = 0; i < keywords.getLength(); ++i) {
				Element keyword = (Element) keywords.item(i);
				String keywordName = keyword.getAttribute(KEYWORD_ATTRIBUTE_NAME);
				// log.info("keywordName " + keywordName);
				Collection<String> comparisonCollection = useExtendedMethod ? preventedKeyWordsExtendedSet : preventedKeyWordsSet;
				// log.info("comparisonCollection "+ comparisonCollection);
				if (comparisonCollection.contains(keywordName)) {
					String keywordCount = keyword.getAttribute(KEYWORD_ATTRIBUTE_COUNT);
					// log.info("keywordCount " + keywordCount);
					if (keywordCount != null) {
						try {
							int count = new Integer(keywordCount);
							// log.info("count " + count);
							if (count > 0)
								validationInfos.addError("PDF contains untrusted " + keywordName + " instructions");
						} catch (NumberFormatException e) {
							log.error("Eccezione identifyPreventedKeywords", e);
						}
					}
				}
			}
		}
	}

	// public static void main(String args[]){
	// PDFExecutableDetector pdfDetector = new PDFExecutableDetector();
	//// try {
	//// pdfDetector.execute(pdfFile);
	//// } catch (IOException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// }
	// try {
	// ValidationInfos validationInfos = new ValidationInfos();
	// pdfDetector.executePDFId(pdfFile, validationInfos, true);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ScriptException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ParserConfigurationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SAXException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException {
		log.info("File " + file);
		ValidationInfos validationInfos = new ValidationInfos();
		log.info("detectMethod " + detectMethod);
		switch (detectMethod) {
		case PDF_BOX:
			try {
				executePDFBox(file, validationInfos);
			} catch (IOException e) {
				throw new DynamicCodeDetectorException(e.getMessage(), e);
			}
		case PDF_ID:
			try {
				executePDFId(file, validationInfos, false);
			} catch (Exception e) {
				throw new DynamicCodeDetectorException(e.getMessage(), e);
			}
		}
		return validationInfos;
	}

	public DETECT_TYPE getDetectMethod() {
		return detectMethod;
	}

	public void setDetectMethod(DETECT_TYPE detectMethod) {
		this.detectMethod = detectMethod;
	}

}
