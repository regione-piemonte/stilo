// 
// Decompiled by Procyon v0.5.36
// 

package com.sun.comm;

import java.io.IOException;

class NotificationThread extends Thread
{
    Win32SerialPort port;
    private static int EV_RXCHAR;
    private static int EV_RXFLAG;
    private static int EV_TXEMPTY;
    private static int EV_CTS;
    private static int EV_DSR;
    private static int EV_RLSD;
    private static int EV_BREAK;
    private static int EV_ERR;
    private static int EV_RING;
    private static int CE_OVERRUN;
    private static int CE_RXPARITY;
    private static int CE_FRAME;
    private static int CE_BREAK;
    
    NotificationThread(final String name, final Win32SerialPort port) {
        super(name);
        this.port = port;
    }
    
    public void run() {
        while (!this.port.closed && this.port.eventListener != null) {
            final int waitForEvent = this.port.waitForEvent();
            if (this.port.closed || this.port.eventListener == null) {
                this.port.notificationThread = null;
                return;
            }
            if ((waitForEvent & NotificationThread.EV_CTS) == NotificationThread.EV_CTS) {
                this.port.sendCTSevent();
            }
            if ((waitForEvent & NotificationThread.EV_DSR) == NotificationThread.EV_DSR) {
                this.port.sendDSRevent();
            }
            if ((waitForEvent & NotificationThread.EV_RLSD) == NotificationThread.EV_RLSD) {
                this.port.sendCDevent();
            }
            if ((waitForEvent & NotificationThread.EV_RING) == NotificationThread.EV_RING) {
                this.port.sendRIevent();
            }
            if ((waitForEvent & NotificationThread.EV_BREAK) == NotificationThread.EV_BREAK) {
                this.port.sendBIevent();
            }
            if ((waitForEvent & NotificationThread.EV_ERR) == NotificationThread.EV_ERR) {
                final int n = waitForEvent >> 16;
                if ((n & NotificationThread.CE_OVERRUN) == NotificationThread.CE_OVERRUN) {
                    this.port.sendOEevent();
                }
                if ((n & NotificationThread.CE_RXPARITY) == NotificationThread.CE_RXPARITY) {
                    this.port.sendPEevent();
                }
                if ((n & NotificationThread.CE_FRAME) == NotificationThread.CE_FRAME) {
                    this.port.sendFEevent();
                }
            }
            if ((waitForEvent & NotificationThread.EV_TXEMPTY) == NotificationThread.EV_TXEMPTY) {
                this.port.sendOutputEmptyEvent();
            }
            if ((waitForEvent & NotificationThread.EV_RXCHAR) == NotificationThread.EV_RXCHAR) {
                synchronized (this.port.readSignal) {
                    this.port.readSignal.notifyAll();
                }
                // monitorexit(this.port.readSignal)
                try {
                    if (this.port.available() > 0) {
                        this.port.sendDataAvailEvent();
                    }
                }
                catch (IOException ex) {}
            }
            if ((waitForEvent & NotificationThread.EV_RXFLAG) != NotificationThread.EV_RXFLAG) {
                continue;
            }
            this.port.framingByteReceived = true;
            synchronized (this.port.readSignal) {
                this.port.readSignal.notifyAll();
            }
            // monitorexit(this.port.readSignal)
        }
        this.port.notificationThread = null;
    }
    
    static {
        NotificationThread.EV_RXCHAR = 1;
        NotificationThread.EV_RXFLAG = 2;
        NotificationThread.EV_TXEMPTY = 4;
        NotificationThread.EV_CTS = 8;
        NotificationThread.EV_DSR = 16;
        NotificationThread.EV_RLSD = 32;
        NotificationThread.EV_BREAK = 64;
        NotificationThread.EV_ERR = 128;
        NotificationThread.EV_RING = 256;
        NotificationThread.CE_OVERRUN = 2;
        NotificationThread.CE_RXPARITY = 4;
        NotificationThread.CE_FRAME = 8;
        NotificationThread.CE_BREAK = 16;
    }
}
