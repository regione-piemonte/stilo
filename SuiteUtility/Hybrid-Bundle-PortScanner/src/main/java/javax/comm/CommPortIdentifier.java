// 
// Decompiled by Procyon v0.5.36
// 

package javax.comm;

import java.io.FileDescriptor;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.net.URL;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sun.comm.Win32Driver;

import it.eng.hybrid.module.portScanner.JarLib;
import it.eng.hybrid.module.portScanner.ui.PortScannerApplication;

import java.io.InputStream;
import java.util.Enumeration;


public class CommPortIdentifier
{
	public final static Logger logger = Logger.getLogger(CommPortIdentifier.class);
	
    String name;
    private int portType;
    public static final int PORT_SERIAL = 1;
    public static final int PORT_PARALLEL = 2;
    private boolean nativeObjectsCreated;
    private int ownedEvent;
    private int unownedEvent;
    private int ownershipRequestedEvent;
    private int shmem;
    private String shname;
    private boolean maskOwnershipEvents;
    OwnershipEventThread oeThread;
    CpoList cpoList;
    CommPortIdentifier next;
    private CommPort port;
    private CommDriver driver;
    static Object lock;
    static String propfilename;
    static CommPortIdentifier masterIdList;
    boolean owned;
    String owner;
    
    public static Enumeration getPortIdentifiers() {
        final SecurityManager securityManager = System.getSecurityManager();
//        if (securityManager != null) {
//            securityManager.checkDelete(CommPortIdentifier.propfilename);
//        }
        return new CommPortEnumerator();
    }
    
    public static CommPortIdentifier getPortIdentifier(final String anObject) throws NoSuchPortException {
        final SecurityManager securityManager = System.getSecurityManager();
//        if (securityManager != null) {
//            securityManager.checkDelete(CommPortIdentifier.propfilename);
//        }
        CommPortIdentifier commPortIdentifier = null;
        synchronized (CommPortIdentifier.lock) {
            for (commPortIdentifier = CommPortIdentifier.masterIdList; commPortIdentifier != null && !commPortIdentifier.name.equals(anObject); commPortIdentifier = commPortIdentifier.next) {}
        }
        // monitorexit(CommPortIdentifier.lock)
        if (commPortIdentifier != null) {
            return commPortIdentifier;
        }
        throw new NoSuchPortException();
    }
    
    public static CommPortIdentifier getPortIdentifier(final CommPort commPort) throws NoSuchPortException {
        final SecurityManager securityManager = System.getSecurityManager();
//        if (securityManager != null) {
//            securityManager.checkDelete(CommPortIdentifier.propfilename);
//        }
        CommPortIdentifier commPortIdentifier = null;
        synchronized (CommPortIdentifier.lock) {
            for (commPortIdentifier = CommPortIdentifier.masterIdList; commPortIdentifier != null && commPortIdentifier.port != commPort; commPortIdentifier = commPortIdentifier.next) {}
        }
        // monitorexit(CommPortIdentifier.lock)
        if (commPortIdentifier != null) {
            return commPortIdentifier;
        }
        throw new NoSuchPortException();
    }
    
    private static void addPort(final CommPort commPort, final int n) {
        final SecurityManager securityManager = System.getSecurityManager();
//        if (securityManager != null) {
//            securityManager.checkDelete(CommPortIdentifier.propfilename);
//        }
        final CommPortIdentifier commPortIdentifier = new CommPortIdentifier(commPort.getName(), commPort, n, null);
        CommPortIdentifier commPortIdentifier2 = CommPortIdentifier.masterIdList;
        CommPortIdentifier commPortIdentifier3 = null;
        synchronized (CommPortIdentifier.lock) {
            while (commPortIdentifier2 != null) {
                commPortIdentifier3 = commPortIdentifier2;
                commPortIdentifier2 = commPortIdentifier2.next;
            }
            if (commPortIdentifier3 != null) {
                commPortIdentifier3.next = commPortIdentifier;
            }
            else {
                CommPortIdentifier.masterIdList = commPortIdentifier;
            }
        }
        // monitorexit(CommPortIdentifier.lock)
    }
    
    public static void addPortName(final String s, final int n, final CommDriver commDriver) {
        final SecurityManager securityManager = System.getSecurityManager();
        logger.info("securityManager: " + securityManager);
//        if (securityManager != null) {
//        	logger.info("CommPortIdentifier.propfilename: " + CommPortIdentifier.propfilename);
//            securityManager.checkDelete(CommPortIdentifier.propfilename);
//        }
        final CommPortIdentifier commPortIdentifier = new CommPortIdentifier(s, null, n, commDriver);
        CommPortIdentifier commPortIdentifier2 = CommPortIdentifier.masterIdList;
        CommPortIdentifier commPortIdentifier3 = null;
        synchronized (CommPortIdentifier.lock) {
            while (commPortIdentifier2 != null) {
                commPortIdentifier3 = commPortIdentifier2;
                commPortIdentifier2 = commPortIdentifier2.next;
            }
            if (commPortIdentifier3 != null) {
                commPortIdentifier3.next = commPortIdentifier;
            }
            else {
                CommPortIdentifier.masterIdList = commPortIdentifier;
            }
        }
        // monitorexit(CommPortIdentifier.lock)
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPortType() {
        return this.portType;
    }
    
    private native int nCreateMutex(final String p0);
    
    private native int nCreateEvent(final String p0);
    
    private native boolean nClaimMutex(final int p0, final int p1);
    
    private native boolean nReleaseMutex(final int p0);
    
    private native boolean nPulseEvent(final int p0);
    
    private native boolean nSetEvent(final int p0);
    
    private native boolean nCloseHandle(final int p0);
    
    private void createNativeObjects() {
        if (this.ownershipRequestedEvent == 0) {
            this.ownershipRequestedEvent = this.nCreateEvent("javax.comm." + this.name + "-ownershipRequestedEvent");
        }
        if (this.ownedEvent == 0) {
            this.ownedEvent = this.nCreateEvent("javax.comm." + this.name + "-ownedEvent");
        }
        if (this.unownedEvent == 0) {
            this.unownedEvent = this.nCreateEvent("javax.comm." + this.name + "-unownedEvent");
        }
        this.nativeObjectsCreated = true;
    }
    
    public synchronized CommPort open(final String owner, final int n) throws PortInUseException {
        if (!this.nativeObjectsCreated) {
            this.createNativeObjects();
        }
        if (this.owned) {
            this.maskOwnershipEvents = true;
            this.fireOwnershipEvent(3);
            this.maskOwnershipEvents = false;
            if (this.owned) {
                throw new PortInUseException(this.owner);
            }
        }
        this.port = this.driver.getCommPort(this.name, this.portType);
        if (this.port == null) {
            this.nSetEvent(this.ownershipRequestedEvent);
            for (int i = (n > 200) ? n : 200; i > 0; i -= 200) {
                try {
                    this.wait(200L);
                }
                catch (InterruptedException ex) {}
                this.port = this.driver.getCommPort(this.name, this.portType);
                if (this.port != null) {
                    break;
                }
            }
            if (this.port == null) {
                String nGetOwner = this.nGetOwner(this.shname);
                if (nGetOwner == null || nGetOwner.length() == 0) {
                    nGetOwner = "Unknown Windows Application";
                }
                throw new PortInUseException(nGetOwner);
            }
        }
        this.owned = true;
        this.owner = owner;
        if (owner == null || owner.length() == 0) {
            this.shmem = this.nSetOwner(this.shname, "Unspecified Java Application", true);
        }
        else {
            this.shmem = this.nSetOwner(this.shname, owner, true);
        }
        if (!this.nSetEvent(this.ownedEvent)) {
            System.err.println("Error pulsing ownedEvent");
        }
        return this.port;
    }
    
    private native int nSetOwner(final String p0, final String p1, final boolean p2);
    
    private native String nGetOwner(final String p0);
    
    private native void nUnsetOwner(final String p0);
    
    public String getCurrentOwner() {
        if (this.owned) {
            return this.owner;
        }
        final String nGetOwner = this.nGetOwner(this.shname);
        if (nGetOwner == null || nGetOwner.length() == 0) {
            return "Port currently not owned";
        }
        return nGetOwner;
    }
    
    public boolean isCurrentlyOwned() {
        if (this.owned) {
            return true;
        }
        final String nGetOwner = this.nGetOwner(this.shname);
        return nGetOwner != null && nGetOwner.length() != 0;
    }
    
    public void addPortOwnershipListener(final CommPortOwnershipListener commPortOwnershipListener) {
        this.cpoList.add(commPortOwnershipListener);
        if (this.oeThread == null) {
            (this.oeThread = new OwnershipEventThread(this)).start();
        }
    }
    
    public void removePortOwnershipListener(final CommPortOwnershipListener commPortOwnershipListener) {
        this.cpoList.remove(commPortOwnershipListener);
    }
    
    private native int nWaitForEvents(final int p0, final int p1, final int p2);
    
    void ownershipThreadWaiter() {
        final int nWaitForEvents;
        if ((nWaitForEvents = this.nWaitForEvents(this.ownedEvent, this.unownedEvent, this.ownershipRequestedEvent)) >= 0) {
            this.maskOwnershipEvents = true;
            switch (nWaitForEvents) {
                case 0: {
                    this.fireOwnershipEvent(1);
                    break;
                }
                case 1: {
                    this.fireOwnershipEvent(2);
                    break;
                }
                case 2: {
                    this.fireOwnershipEvent(3);
                    break;
                }
            }
            this.maskOwnershipEvents = false;
        }
    }
    
    synchronized void internalClosePort() {
        this.owned = false;
        this.owner = null;
        this.port = null;
        this.nUnsetOwner(this.shname);
        this.nSetEvent(this.unownedEvent);
    }
    
    CommPortIdentifier(final String s, final CommPort port, final int portType, final CommDriver driver) {
        this.cpoList = new CpoList();
        this.name = s;
        this.port = port;
        this.portType = portType;
        this.next = null;
        this.driver = driver;
        this.shname = "javax.comm-" + s;
        this.shmem = this.nSetOwner(this.shname, "", false);
    }
    
    void fireOwnershipEvent(final int n) {
        this.cpoList.clonelist().fireOwnershipEvent(n);
    }
    
    private static String[] parsePropsFile(final InputStream inputStream) {
        final Vector vector = new Vector<String>();
        try {
            final byte[] array = new byte[4096];
            int n = 0;
            boolean b = false;
            int read;
            while ((read = inputStream.read()) != -1) {
                switch (read) {
                    default: {
                        if (!b && n < 4096) {
                            array[n++] = (byte)read;
                            continue;
                        }
                        continue;
                    }
                    case 10:
                    case 13: {
                        if (n > 0) {
                            vector.addElement(new String(array, 0, 0, n));
                        }
                        n = 0;
                        b = false;
                        continue;
                    }
                    case 35: {
                        b = true;
                        if (n > 0) {
                            vector.addElement(new String(array, 0, 0, n));
                        }
                        n = 0;
                    }
                    case 9:
                    case 32: {
                        continue;
                    }
                }
            }
        }
        catch (Throwable obj) {
            System.err.println("Caught " + obj + " parsing prop file.");
        }
        if (vector.size() > 0) {
            final String[] array2 = new String[vector.size()];
            for (int i = 0; i < vector.size(); ++i) {
                array2[i] = (String) vector.elementAt(i);
            }
            return array2;
        }
        return null;
    }
    
    private static void loadDriver(final String pathname) throws IOException {
        final String[] propsFile = parsePropsFile(new BufferedInputStream(new FileInputStream(new File(pathname))));
        if (propsFile != null) {
            for (int i = 0; i < propsFile.length; ++i) {
                if (propsFile[i].regionMatches(true, 0, "driver=", 0, 7)) {
                    final String substring = propsFile[i].substring(7);
                    substring.trim();
                    try {
                        ((CommDriver)Class.forName(substring).newInstance()).initialize();
                    }
                    catch (Throwable obj) {
                        System.err.println("Caught " + obj + " while loading driver " + substring);
                        logger.error(obj.getMessage(), obj);
                    }
                }
            }
        }
    }
    
    private static String findPropFile() {
        final StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(System.getProperty("java.class.path")));
        streamTokenizer.whitespaceChars(File.pathSeparatorChar, File.pathSeparatorChar);
        streamTokenizer.wordChars(File.separatorChar, File.separatorChar);
        streamTokenizer.ordinaryChar(46);
        streamTokenizer.wordChars(46, 46);
        try {
            while (streamTokenizer.nextToken() != -1) {
                final int index;
                if (streamTokenizer.ttype == -3 && (index = streamTokenizer.sval.indexOf("comm.jar")) != -1) {
                    final String pathname = new String(streamTokenizer.sval);
                    if (!new File(pathname).exists()) {
                        continue;
                    }
                    final String substring = pathname.substring(0, index);
                    String s;
                    if (substring != null) {
                        s = String.valueOf(substring) + "." + File.separator + "javax.comm.properties";
                    }
                    else {
                        s = "." + File.separator + "javax.comm.properties";
                    }
                    if (new File(s).exists()) {
                        return new String(s);
                    }
                    return null;
                }
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    public CommPort open(final FileDescriptor fileDescriptor) throws UnsupportedCommOperationException {
        throw new UnsupportedCommOperationException();
    }
    
    static {
        CommPortIdentifier.lock = new Object();
//        final String string = String.valueOf(System.getProperty("java.home")) + File.separator + "lib" + File.separator + "javax.comm.properties";
        try {
        	final String string = JarLib.copyJavaxCommPropertiesFile(Win32Driver.class, "javax.comm.properties");
            loadDriver(string);
            logger.info("Setto CommPortIdentifier.propfilename = " + CommPortIdentifier.propfilename);
            CommPortIdentifier.propfilename = new String(string);
        }
        catch (IOException ex) {
        	logger.error(ex.getMessage(), ex);
            CommPortIdentifier.propfilename = findPropFile();
            try {
                if (CommPortIdentifier.propfilename != null) {
                    loadDriver(CommPortIdentifier.propfilename);
                }
            }
            catch (IOException x) {
                CommPortIdentifier.propfilename = new String(" ");
                System.err.println(x);
                logger.error(x.getMessage(), x);
            }
        }
    }
}
