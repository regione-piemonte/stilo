//
// Decompiled by Procyon v0.5.36
//

package com.sun.comm;

import java.io.IOException;
import javax.comm.CommPort;
import java.util.Enumeration;
import javax.comm.CommPortIdentifier;

import it.eng.hybrid.module.portScanner.JarLib;

import java.util.Vector;
import javax.comm.CommDriver;

public class Win32Driver implements CommDriver {

	public void initialize() {
		try {
			JarLib.load(Win32Driver.class, "win32comPortScanner");
		} catch (SecurityException obj) {
			System.err.println("Security Exception win32com: " + obj);
			return;
		} catch (UnsatisfiedLinkError obj2) {
			System.err.println("Error loading win32com: " + obj2);
			return;
		}
		final Vector<String> vector = new Vector<String>();
		readRegistrySerial(vector);
		final Enumeration<String> elements = vector.elements();
		while (elements.hasMoreElements()) {
			CommPortIdentifier.addPortName(elements.nextElement(), 1, this);
		}
		final Vector<String> vector2 = new Vector<String>();
		readRegistryParallel(vector2);
		final Enumeration<String> elements2 = vector2.elements();
		while (elements2.hasMoreElements()) {
			CommPortIdentifier.addPortName(elements2.nextElement(), 2, this);
		}
	}

	public CommPort getCommPort(final String s, final int n) {
		CommPort commPort = null;
		try {
			switch (n) {
			case 1: {
				commPort = new Win32SerialPort(s);
				break;
			}
			case 2: {
				commPort = new Win32ParallelPort(s);
				break;
			}
			}
		} catch (IOException ex) {
		}
		return commPort;
	}

	private static native int readRegistrySerial(final Vector p0);

	private static native int readRegistryParallel(final Vector p0);
}
