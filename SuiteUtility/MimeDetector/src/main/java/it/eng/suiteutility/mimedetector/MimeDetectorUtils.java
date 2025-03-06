package it.eng.suiteutility.mimedetector;

import it.eng.suiteutility.mimedetector.implementations.mimeutils.MimeUtilsAdapter;

import java.util.Enumeration;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParameterList;

public class MimeDetectorUtils {

	public static MimeType getBestMimeType(Map<MimeType, Integer> mimeTypes) throws MimeDetectorException {
		MimeType result = null;
		double maxScore = -1;
        for (MimeType mimeType: mimeTypes.keySet()) {
        	int role = mimeTypes.get(mimeType).intValue();
        	int specificity = 1;
        	try {
        		specificity   = Integer.parseInt(mimeType.getParameter(MimeDetectorConfig.MIME_SPECIFICITY_ATTRIBUTE));
        	}catch(Exception e) {;}
        	double score = calculateScore(role, specificity);
        	if (score >maxScore) {
/*        		try {
					result = new MimeType(mimeType.getPrimaryType(), mimeType.getSubType());
				} catch (MimeTypeParseException e) {
					throw new MimeDetectorException(e.getLocalizedMessage(),e);
				}*/
        		result = mimeType;
        		maxScore = score;
        	}
        }
		return result;
	}
	
	public static double calculateScore ( int role, int specificity) {
		return role + specificity * 0.01;
	}
	
	public static void copyMimeParameters ( MimeType dest, MimeType source) {
		MimeTypeParameterList parameters = source.getParameters();
		Enumeration<String> names = parameters.getNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value= parameters.get(name);
			dest.setParameter(name,value);
		}
	}
	
	public static MimeDetector getMimeDetector(){
		return new MimeUtilsAdapter();
	}
	
}
