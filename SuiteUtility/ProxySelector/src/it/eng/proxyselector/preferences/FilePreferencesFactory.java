package it.eng.proxyselector.preferences;

import it.eng.proxyselector.ProxySelector;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

import org.apache.log4j.Logger;

public class FilePreferencesFactory implements PreferencesFactory
{
	private static final Logger log = Logger.getLogger(FilePreferencesFactory.class);

	Preferences rootPreferences;
	public static final String SYSTEM_PROPERTY_FILE =
		"net.infotrek.util.prefs.FilePreferencesFactory.files";

	public Preferences systemRoot() {
		return userRoot();
	}

	public Preferences userRoot() {
		if (rootPreferences == null) {
			log.debug("Instantiating root preferences");
			rootPreferences = new FilePreferences(null, "");
		}
		return rootPreferences;
	}

	private static File preferencesFile;

	public static File getPreferencesFile() {
		if (preferencesFile == null) {
			String prefsFile = System.getProperty("user.home") + File.separator + ".fileprefs";
			preferencesFile = new File(prefsFile).getAbsoluteFile();
			System.out.println("Preferences file is " + preferencesFile);
		}
		return preferencesFile;
	}

	public static void main(String[] args) throws BackingStoreException {
		System.setProperty("java.util.prefs.PreferencesFactory", FilePreferencesFactory.class.getName());
//		System.setProperty(SYSTEM_PROPERTY_FILE, "myprefs.txt");

		Preferences p = Preferences.userNodeForPackage(ProxySelector.class);

		for (String s : p.keys()) {
			System.out.println("p[" + s + "]=" + p.get(s, null));
		}

		p.putBoolean("hi", true);
		p.put("Number", String.valueOf(System.currentTimeMillis()));
	}
}
