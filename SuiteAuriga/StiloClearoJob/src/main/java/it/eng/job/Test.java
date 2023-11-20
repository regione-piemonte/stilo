/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.itextpdf.text.Anchor;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.security.CodeSource;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class Test {
	
	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		Class klass = Anchor.class;
		URL location = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
		System.out.println("location file: "+location.getFile());
		System.out.println("location path: "+location.getPath());

		File jarFileIn = new File("/C:/Users/Bcsoft/.m2/repository/com/sun/media/imageio/jpedal/1.0.1/jpedal-1.0.1.jar");
		JarFile jarFile = new JarFile(jarFileIn);
		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + jarFileIn+"!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (e.hasMoreElements()) {
		    JarEntry je = e.nextElement();
		    System.out.println("je.getName(): "+je.getName());
		    if( !je.getName().equals("com/itextpdf/text/BaseColor.class")){
		        continue;
		    }
		    // -6 because of .class
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    Class c = cl.loadClass(className);
		    URL location1 = c.getResource('/' + c.getName().replace('.', '/') + ".class");
			System.out.println("location file: "+location1.getFile());
			System.out.println("location path: "+location1.getPath());
			
			CodeSource codeSource = c.getProtectionDomain().getCodeSource();

		    if ( codeSource != null) {

		        System.out.println(codeSource.getLocation());

		    }
		}
		    ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
		    MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
		    Class myObjectClass = classLoader.loadClass("reflection.MyObject");

		    URL location2 = myObjectClass.getResource('/' + myObjectClass.getName().replace('.', '/') + ".class");
			System.out.println("location file: "+location2.getFile());
			System.out.println("location path: "+location2.getPath());
		
	}
	
	
}
