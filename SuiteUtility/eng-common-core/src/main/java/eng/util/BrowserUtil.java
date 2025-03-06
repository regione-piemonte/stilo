// 
// Decompiled by Procyon v0.5.36
// 

package eng.util;

import java.util.StringTokenizer;

public class BrowserUtil
{
    public static final String[] TextBrowsers;
    public static final String[] PalmSO;
    
    public static boolean IsTextBrowser(final String userAgent) {
        final StringTokenizer token = new StringTokenizer(userAgent, "/");
        String brow_name = "";
        if (token.countTokens() >= 1) {
            brow_name = token.nextToken();
            for (int i = 0; i < BrowserUtil.TextBrowsers.length; ++i) {
                if (BrowserUtil.TextBrowsers[i].equalsIgnoreCase(brow_name)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean IsPalmSO(final String userAgent) {
        for (int i = 0; i < BrowserUtil.PalmSO.length; ++i) {
            if (userAgent.toLowerCase().indexOf(BrowserUtil.PalmSO[i].toLowerCase()) >= 0) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean IsIEBrowser(final String userAgent) {
        return userAgent.toLowerCase().indexOf("MSIE".toLowerCase()) >= 0;
    }
    
    static {
        TextBrowsers = new String[] { "Lynx" };
        PalmSO = new String[] { "WinCE", "Windows CE" };
    }
}
