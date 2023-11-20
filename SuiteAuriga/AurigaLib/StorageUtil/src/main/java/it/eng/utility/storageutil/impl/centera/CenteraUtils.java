/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.filepool.fplibrary.FPClip;
import com.filepool.fplibrary.FPLibraryException;
import com.filepool.fplibrary.FPTag;

public class CenteraUtils {
	
//	public static final String separator = "/";
	public static final String separator = ""+File.separator;
	public static final String separatorEscape = "::";
	
	public static final String escapeCenteraTag(String str){
		return str.replace(separator, separatorEscape);
	}
	
	public static final String unescapeCenteraTag(String str){
		return str.replace(separatorEscape, separator);
	}

	public static final List<FPTag> getTagInTreeByIdPrefixAndDepth(FPTag root, String tagName, int currentDepth) throws FPLibraryException{
		if (root==null)
			return null;
		List<FPTag> result = new ArrayList<FPTag>();
		boolean match = false;
		//System.out.println("cerco il tag:"+tagName+" da:"+root.getTagName());
		if (root.getTagName()!=null && root.getTagName().startsWith(tagName)){
			match = true;
			result.add(root);
		}
		if (currentDepth>0){
			FPTag child = root.getFirstChild();
			if (child!=null){
				List<FPTag> childrenResult = getTagInTreeByIdPrefixAndDepth(child, tagName, currentDepth-1);
				if (childrenResult!=null && childrenResult.size()!=0){
					result.addAll(childrenResult);
				}
			}
		}

		FPTag brother = root.getSibling();
		if (brother!=null){
			List<FPTag> brotherResult = getTagInTreeByIdPrefixAndDepth(brother, tagName, currentDepth);
			if (brotherResult!= null && brotherResult.size()!=0)
				result.addAll(brotherResult);
		}
		if (!match)
			root.Close();
		return result;
	}
		
	public static final List<FPTag> getFirstLevelTagsByIdPrefix(FPTag root, String tagName)throws FPLibraryException{
		return getTagInTreeByIdPrefixAndDepth(root, tagName, 0);
	}
	
	public static final FPTag getFPTagByCenteraPath(FPClip clip, String centeraPath) throws FPLibraryException, StorageException{
		FPTag currentTag = clip.getTopTag();
		for (StringTokenizer strTokenizer = new StringTokenizer(centeraPath, "/"); strTokenizer.hasMoreTokens();){
			String partialpath = strTokenizer.nextToken();
			List<FPTag> currentTags = getFirstLevelTagsByIdPrefix(currentTag.getFirstChild(), partialpath);
			currentTag.Close();
			if (currentTags==null ||currentTags.size()!=1)
				throw new StorageException("Cannnot find element with path: " + centeraPath);
			currentTag = currentTags.get(0);
		}
		return currentTag;
	}	
	
}
