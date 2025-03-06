// 
// Decompiled by Procyon v0.5.36
// 

package com.sun.comm;

import javax.comm.SerialPortEvent;
import java.util.TooManyListenersException;
import javax.comm.UnsupportedCommOperationException;
import java.io.IOException;
import javax.comm.SerialPortEventListener;
import java.io.OutputStream;
import java.io.InputStream;
import javax.comm.SerialPort;

class Win32SerialPort extends SerialPort
{
    int nativeHandle;
    private InputStream ins;
    private OutputStream outs;
    private int rcvThreshold;
    int rcvTimeout;
    private boolean framing;
    private int framingByte;
    boolean framingByteReceived;
    private int baudrate;
    private int parity;
    private int dataBits;
    private int stopBits;
    private int flowcontrol;
    private static int[] speeds;
    private boolean dtr;
    private boolean rts;
    NotificationThread notificationThread;
    SerialPortEventListener eventListener;
    private int notifyMask;
    static final int NOTIFY_DATA_AVAIL = 1;
    static final int NOTIFY_OUTPUT_EMPTY = 2;
    static final int NOTIFY_RI = 4;
    static final int NOTIFY_CTS = 8;
    static final int NOTIFY_DSR = 16;
    static final int NOTIFY_CD = 32;
    static final int NOTIFY_OE = 64;
    static final int NOTIFY_PE = 128;
    static final int NOTIFY_FE = 256;
    static final int NOTIFY_BI = 512;
    private boolean stateRI;
    private boolean stateCTS;
    private boolean stateDSR;
    private boolean stateCD;
    private boolean stateOE;
    private boolean statePE;
    private boolean stateFE;
    private boolean stateBI;
    Object readSignal;
    private byte[] wa;
    private static final int READ_POLL = 200;
    boolean closed;
    
    Win32SerialPort(final String s) throws IOException {
        this.rcvThreshold = -1;
        this.rcvTimeout = -1;
        this.framing = false;
        this.dtr = true;
        this.rts = true;
        this.closed = false;
        super.name = s;
        if (!this.nativeConstructor(s)) {
            throw new IOException("Unable to create port " + s);
        }
        this.outs = new Win32SerialOutputStream(this);
        this.ins = new Win32SerialInputStream(this);
        this.readSignal = new Object();
        this.wa = new byte[1];
        try {
            this.setFlowControlMode(0);
            this.setSerialPortParams(9600, 8, 1, 0);
        }
        catch (UnsupportedCommOperationException ex) {}
        (this.notificationThread = new NotificationThread("Win32SerialPort Notification thread", this)).start();
    }
    
    private native boolean nativeConstructor(final String p0);
    
    public InputStream getInputStream() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.ins;
    }
    
    public OutputStream getOutputStream() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.outs;
    }
    
    public void enableReceiveThreshold(final int rcvThreshold) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (rcvThreshold < 0) {
            throw new UnsupportedCommOperationException("This threshold value is not supported");
        }
        this.rcvThreshold = rcvThreshold;
    }
    
    public void disableReceiveThreshold() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.rcvThreshold = -1;
    }
    
    public boolean isReceiveThresholdEnabled() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.rcvThreshold != -1;
    }
    
    public int getReceiveThreshold() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.rcvThreshold;
    }
    
    public void enableReceiveTimeout(final int rcvTimeout) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (rcvTimeout < 0) {
            throw new UnsupportedCommOperationException("This timeout value is not supported");
        }
        this.rcvTimeout = rcvTimeout;
    }
    
    public void disableReceiveTimeout() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.rcvTimeout = -1;
    }
    
    public int getReceiveTimeout() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.rcvTimeout;
    }
    
    public boolean isReceiveTimeoutEnabled() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.rcvTimeout != -1;
    }
    
    public void disableReceiveFraming() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.framing = false;
        this.nativeDisableFraming();
    }
    
    public void enableReceiveFraming(final int n) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.framing = true;
        this.framingByte = (n & 0xFF);
        this.nativeEnableFraming(n);
    }
    
    public int getReceiveFramingByte() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.framingByte;
    }
    
    public boolean isReceiveFramingEnabled() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.framing;
    }
    
    native void nativeEnableFraming(final int p0);
    
    native void nativeDisableFraming();
    
    public void setInputBufferSize(final int n) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.nsetInputBufferSize(n);
    }
    
    private native void nsetInputBufferSize(final int p0);
    
    public int getInputBufferSize() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.ngetInputBufferSize();
    }
    
    private native int ngetInputBufferSize();
    
    public void setOutputBufferSize(final int n) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.nsetOutputBufferSize(n);
    }
    
    private native void nsetOutputBufferSize(final int p0);
    
    public int getOutputBufferSize() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.ngetOutputBufferSize();
    }
    
    private native int ngetOutputBufferSize();
    
    public int getBaudRate() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.baudrate;
    }
    
    public int getDataBits() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.dataBits;
    }
    
    public int getStopBits() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.stopBits;
    }
    
    public int getParity() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.parity;
    }
    
    public void setFlowControlMode(final int flowcontrol) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if ((flowcontrol & 0x3) != 0x0 && (flowcontrol & 0xC) != 0x0) {
            throw new UnsupportedCommOperationException("Cannot mix hardware and software flow control");
        }
        this.nativeSetFlowcontrolMode(this.flowcontrol = flowcontrol);
        if ((flowcontrol & 0x1) == 0x0) {
            this.rts = true;
            this.dtr = true;
        }
    }
    
    native void nativeSetFlowcontrolMode(final int p0);
    
    public int getFlowControlMode() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.flowcontrol;
    }
    
    public void setSerialPortParams(final int baudrate, final int dataBits, final int stopBits, final int parity) throws UnsupportedCommOperationException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        for (int n = 0; n < Win32SerialPort.speeds.length && baudrate != Win32SerialPort.speeds[n]; ++n) {
            if (baudrate < Win32SerialPort.speeds[n]) {
                throw new UnsupportedCommOperationException("Unsupported baud rate");
            }
        }
        if (dataBits != 5 && dataBits != 6 && dataBits != 7 && dataBits != 8) {
            throw new UnsupportedCommOperationException("Unsupported num of databits");
        }
        if (stopBits != 1 && stopBits != 2 && stopBits != 3) {
            throw new UnsupportedCommOperationException("Unsupported num of stopbits");
        }
        if (parity != 2 && parity != 1 && parity != 0) {
            throw new UnsupportedCommOperationException("Unsupported parity value");
        }
        this.setCommDeviceParams(this.baudrate = baudrate, this.parity = parity, this.dataBits = dataBits, this.stopBits = stopBits);
        this.saveCommDeviceState();
        try {
            this.setFlowControlMode(this.flowcontrol);
        }
        catch (UnsupportedCommOperationException ex) {}
    }
    
    public native void setCommDeviceParams(final int p0, final int p1, final int p2, final int p3);
    
    public void sendBreak(final int n) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        synchronized (this) {
            this.setCommBreak();
            try {
                Thread.sleep(n);
            }
            catch (InterruptedException ex) {}
            this.clearCommBreak();
        }
    }
    
    private native void setCommBreak();
    
    private native void clearCommBreak();
    
    public void setDTR(final boolean dtr) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if ((this.flowcontrol & 0x1) == 0x1) {
            return;
        }
        this.nativeSetDTR(dtr);
        this.dtr = dtr;
    }
    
    private native void nativeSetDTR(final boolean p0);
    
    public boolean isDTR() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.dtr;
    }
    
    public void setRTS(final boolean rts) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if ((this.flowcontrol & 0x1) == 0x1) {
            throw new IllegalStateException("Cannot modify RTS when Hardware flowcontrol is on.");
        }
        this.nativeSetRTS(rts);
        this.rts = rts;
    }
    
    private native void nativeSetRTS(final boolean p0);
    
    public boolean isRTS() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.rts;
    }
    
    public boolean isCTS() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.nativeIsCTS();
    }
    
    private native boolean nativeIsCTS();
    
    public boolean isDSR() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.nativeIsDSR();
    }
    
    private native boolean nativeIsDSR();
    
    public boolean isRI() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.nativeIsRI();
    }
    
    private native boolean nativeIsRI();
    
    public boolean isCD() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        return this.nativeIsCD();
    }
    
    private native boolean nativeIsCD();
    
    public synchronized void addEventListener(final SerialPortEventListener eventListener) throws TooManyListenersException {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (this.eventListener != null) {
            throw new TooManyListenersException();
        }
        this.eventListener = eventListener;
        if (this.eventListener != null && this.notificationThread == null) {
            (this.notificationThread = new NotificationThread("Win32SerialPort Notification thread", this)).start();
        }
    }
    
    public synchronized void removeEventListener() {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        this.eventListener = null;
    }
    
    public synchronized void notifyOnDataAvailable(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x1;
        }
        else {
            this.notifyMask &= 0xFFFFFFFE;
        }
        this.nnotifyOnDataAvailable(b);
    }
    
    private native void nnotifyOnDataAvailable(final boolean p0);
    
    public synchronized void notifyOnOutputEmpty(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x2;
        }
        else {
            this.notifyMask &= 0xFFFFFFFD;
        }
        this.nnotifyOnOutputEmpty(b);
    }
    
    private native void nnotifyOnOutputEmpty(final boolean p0);
    
    public synchronized void notifyOnCTS(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x8;
        }
        else {
            this.notifyMask &= 0xFFFFFFF7;
        }
        this.nnotifyOnCTS(b);
    }
    
    private native void nnotifyOnCTS(final boolean p0);
    
    public synchronized void notifyOnDSR(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x10;
        }
        else {
            this.notifyMask &= 0xFFFFFFEF;
        }
        this.nnotifyOnDSR(b);
    }
    
    private native void nnotifyOnDSR(final boolean p0);
    
    public synchronized void notifyOnCarrierDetect(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x20;
        }
        else {
            this.notifyMask &= 0xFFFFFFDF;
        }
        this.nnotifyOnCarrierDetect(b);
    }
    
    private native void nnotifyOnCarrierDetect(final boolean p0);
    
    public synchronized void notifyOnRingIndicator(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x4;
        }
        else {
            this.notifyMask &= 0xFFFFFFFB;
        }
        this.nnotifyOnRingIndicator(b);
    }
    
    private native void nnotifyOnRingIndicator(final boolean p0);
    
    public synchronized void notifyOnOverrunError(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x40;
        }
        else {
            this.notifyMask &= 0xFFFFFFBF;
        }
        this.nnotifyOnOverrunError(b);
    }
    
    private native void nnotifyOnOverrunError(final boolean p0);
    
    public synchronized void notifyOnParityError(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x80;
        }
        else {
            this.notifyMask &= 0xFFFFFF7F;
        }
        this.nnotifyOnParityError(b);
    }
    
    private native void nnotifyOnParityError(final boolean p0);
    
    public synchronized void notifyOnFramingError(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x100;
        }
        else {
            this.notifyMask &= 0xFFFFFEFF;
        }
        this.nnotifyOnFramingError(b);
    }
    
    private native void nnotifyOnFramingError(final boolean p0);
    
    public synchronized void notifyOnBreakInterrupt(final boolean b) {
        if (this.closed) {
            throw new IllegalStateException("Port Closed");
        }
        if (b) {
            this.notifyMask |= 0x200;
        }
        else {
            this.notifyMask &= 0xFFFFFDFF;
        }
        this.nnotifyOnBreakInterrupt(b);
    }
    
    private native void nnotifyOnBreakInterrupt(final boolean p0);
    
    native int waitForEvent();
    
    private void saveCommDeviceState() {
        this.stateRI = this.isRI();
        this.stateCTS = this.isCTS();
        this.stateDSR = this.isDSR();
        this.stateCD = this.isCD();
    }
    
    void sendCTSevent() {
        if ((this.notifyMask & 0x8) == 0x8) {
            final boolean cts = this.isCTS();
            if (cts != this.stateCTS) {
                this.eventListener.serialEvent(new SerialPortEvent(this, 3, this.stateCTS, cts));
                this.stateCTS = cts;
            }
        }
    }
    
    void sendDSRevent() {
        if ((this.notifyMask & 0x10) == 0x10) {
            final boolean dsr = this.isDSR();
            if (dsr != this.stateDSR) {
                this.eventListener.serialEvent(new SerialPortEvent(this, 4, this.stateDSR, dsr));
                this.stateDSR = dsr;
            }
        }
    }
    
    void sendCDevent() {
        if ((this.notifyMask & 0x20) == 0x20) {
            final boolean cd = this.isCD();
            if (cd != this.stateCD) {
                this.eventListener.serialEvent(new SerialPortEvent(this, 6, this.stateCD, cd));
                this.stateCD = cd;
            }
        }
    }
    
    void sendRIevent() {
        if ((this.notifyMask & 0x4) == 0x4) {
            final boolean ri = this.isRI();
            if (ri != this.stateRI) {
                this.eventListener.serialEvent(new SerialPortEvent(this, 5, this.stateRI, ri));
                this.stateRI = ri;
            }
        }
    }
    
    void sendBIevent() {
        if ((this.notifyMask & 0x200) == 0x200) {
            this.eventListener.serialEvent(new SerialPortEvent(this, 10, false, true));
        }
    }
    
    void sendOEevent() {
        if ((this.notifyMask & 0x40) == 0x40) {
            this.eventListener.serialEvent(new SerialPortEvent(this, 7, false, true));
        }
    }
    
    void sendPEevent() {
        if ((this.notifyMask & 0x80) == 0x80) {
            this.eventListener.serialEvent(new SerialPortEvent(this, 8, false, true));
        }
    }
    
    void sendFEevent() {
        if ((this.notifyMask & 0x100) == 0x100) {
            this.eventListener.serialEvent(new SerialPortEvent(this, 9, false, true));
        }
    }
    
    void sendOutputEmptyEvent() {
        if ((this.notifyMask & 0x2) == 0x2) {
            this.eventListener.serialEvent(new SerialPortEvent(this, 2, false, true));
        }
    }
    
    void sendDataAvailEvent() {
        if ((this.notifyMask & 0x1) == 0x1) {
            this.eventListener.serialEvent(new SerialPortEvent(this, 1, false, true));
        }
    }
    
    protected void finalize() throws Throwable {
        this.nativeFinalize();
    }
    
    private native void nativeFinalize();
    
    void write(final int n) throws IOException {
        this.wa[0] = (byte)n;
        this.write(this.wa, 0, 1);
    }
    
    void write(final byte[] array, final int n, final int n2) throws IOException {
        int nwrite;
        for (int i = 0; i < n2; i += nwrite) {
            nwrite = this.nwrite(array, n + i, (n2 - i > 512) ? 512 : (n2 - i));
            if (nwrite <= 0) {
                throw new IOException("write error");
            }
        }
    }
    
    private native int nwrite(final byte[] p0, final int p1, final int p2);
    
    private native int nread(final byte[] p0, final int p1, final int p2) throws IOException;
    
    native int available() throws IOException;
    
    int read() throws IOException {
        final byte[] array = { 0 };
        switch (this.read(array, 0, 1)) {
            case 1: {
                return array[0] & 0xFF;
            }
            default: {
                return -1;
            }
        }
    }
    
    int read(final byte[] array, final int n, final int n2) throws IOException {
        int n3 = 200;
        int n4;
        if (this.rcvTimeout == 0) {
            n4 = 0;
        }
        else if (this.rcvTimeout == -1) {
            n4 = 0;
        }
        else {
            n3 = ((this.rcvTimeout < 200) ? this.rcvTimeout : 200);
            n4 = this.rcvTimeout / n3;
        }
        int i = 0;
        int n5 = 0;
        while (i < n2) {
            if (this.available() > 0) {
                final int nread = this.nread(array, n + i, n2 - i);
                if (nread < 0) {
                    return i;
                }
                i += nread;
            }
            if (this.rcvTimeout == 0) {
                return i;
            }
            if (i == n2) {
                return i;
            }
            if (this.framing && this.framingByteReceived) {
                this.framingByteReceived = false;
                return i;
            }
            if (this.rcvTimeout == -1) {
                if (this.rcvThreshold == -1) {
                    if (i > 0) {
                        return i;
                    }
                }
                else if (i >= Math.min(this.rcvThreshold, n2)) {
                    return i;
                }
            }
            else {
                if (n5 >= n4) {
                    return i;
                }
                if (this.rcvThreshold == -1) {
                    if (i > 0) {
                        return i;
                    }
                }
                else if (i >= Math.min(this.rcvThreshold, n2)) {
                    return i;
                }
            }
            synchronized (this.readSignal) {
                ++n5;
                try {
                    this.readSignal.wait(n3);
                }
                catch (InterruptedException ex) {
                    // monitorexit(this.readSignal)
                    return -1;
                }
            }
            // monitorexit(this.readSignal)
        }
        return i;
    }
    
    public void close() {
        this.eventListener = null;
        this.nativeFinalize();
        this.nativeHandle = 0;
        this.closed = true;
        super.close();
    }
    
    static {
        Win32SerialPort.speeds = new int[] { 75, 110, 134, 150, 300, 600, 1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200 };
    }
}
