/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Copyright 2008 Claudius Hauptmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.claudiushauptmann.gwt.multipage.rebind;

import java.io.PrintWriter;

import com.claudiushauptmann.gwt.multipage.client.MultipageEntryPoint;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class EntryPointFactoryImplGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		
		PrintWriter pw = context.tryCreate(logger,
				"com.claudiushauptmann.gwt.multipage.client",
				"EntryPointFactoryImpl");

		if (pw != null) {
			pw.println("package com.claudiushauptmann.gwt.multipage.client;");
			pw.println();
			pw.println("import com.google.gwt.core.client.GWT;");
			pw.println("import com.google.gwt.core.client.EntryPoint;");
			pw.println("import com.google.gwt.user.client.Window;");
			pw.println();
			
			for (JPackage pack : context.getTypeOracle().getPackages()) {
				for (JClassType classtype : pack.getTypes()) {
					if (classtype.getAnnotation(MultipageEntryPoint.class) != null) {
						pw.println("import " + classtype.getQualifiedSourceName() + ";");
					}
				}
			}

			pw.println();
			pw.println("public class EntryPointFactoryImpl implements EntryPointFactory {");
			pw.println();
			pw.println("	public EntryPoint getEntryPoint() throws MultipleEntryPointsMatchingException, NoEntryPointMatchingException {");
			pw.println();
			pw.println("		EntryPoint ep = null;");
			pw.println();
			pw.println("		String moduleBaseURL = GWT.getModuleBaseURL();");
			pw.println("		String href = Window.Location.getHref();");
			pw.println("		int endIndex = moduleBaseURL.lastIndexOf(\"/\", moduleBaseURL.length()-2);");
			pw.println("		String relativePath = href.substring(endIndex+1, href.length());");	
			pw.println("		if(relativePath != null && relativePath.indexOf(\"?\") != -1) relativePath = relativePath.substring(0, relativePath.indexOf(\"?\"));");
			pw.println("		if(relativePath != null && relativePath.indexOf(\";\") != -1) relativePath = relativePath.substring(0, relativePath.indexOf(\";\"));");
			pw.println("		if(relativePath == null || \"\".equals(relativePath) || !relativePath.endsWith(\".jsp\")) relativePath = \"index.jsp\";");													
			pw.println("		System.out.println(\"relativePath: \" + relativePath);");			
			pw.println();
			
			TypeOracle oracle = context.getTypeOracle();
			JPackage[] packages = oracle.getPackages();
			for (JPackage pack : packages) {

				JClassType[] classes = pack.getTypes();

				for (JClassType classtype : classes) {
					MultipageEntryPoint mep = classtype
							.getAnnotation(MultipageEntryPoint.class);

					if (mep != null) {
						pw.println("		if (relativePath.equals(\"" + mep.urlPattern() + "\")) {");
						pw.println("			if (ep != null) {");
						pw.println("				throw new MultipleEntryPointsMatchingException();");
						pw.println("			}");
						pw.println("			ep = new " + classtype.getQualifiedSourceName() + "();");																		
						pw.println("			System.out.println(\"ep: " + classtype.getQualifiedSourceName() + "()\");");									
						pw.println("		}");
						pw.println();						
					}
				}
			}

			pw.println("		if (ep == null) {");
			pw.println("			throw new NoEntryPointMatchingException();");
			pw.println("		}");
			pw.println();			
			pw.println("		return ep;");
			pw.println("	}");
			pw.println();
			pw.println("}");

			context.commit(logger, pw);
		}

		return "com.claudiushauptmann.gwt.multipage.client.EntryPointFactoryImpl";
	}

}
