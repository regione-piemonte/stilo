// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik;

public final class Version
{
    public static String getVersion() {
        final Package package1 = Version.class.getPackage();
        String str = null;
        if (package1 != null) {
            str = package1.getImplementationVersion();
        }
        final String s = "$HeadURL: https://svn.apache.org/repos/asf/xmlgraphics/batik/tags/batik-1_7/sources/org/apache/batik/Version.java $";
        final String prefix = "$HeadURL: ";
        final String suffix = "/sources/org/apache/batik/Version.java $";
        if (s.startsWith(prefix) && s.endsWith(suffix)) {
            final String substring = s.substring(prefix.length(), s.length() - suffix.length());
            if (!substring.endsWith("/trunk")) {
                final int lastIndex = substring.lastIndexOf(47);
                final int lastIndex2 = substring.lastIndexOf(47, lastIndex - 1);
                final String substring2 = substring.substring(lastIndex + 1);
                final String substring3 = substring.substring(lastIndex2 + 1, lastIndex);
                final String prefix2 = "batik-";
                if (substring3.equals("tags") && substring2.startsWith(prefix2)) {
                    str = substring2.substring(prefix2.length()).replace('_', '.');
                }
                else if (substring3.equals("branches")) {
                    str = str + "; " + substring2;
                }
            }
        }
        if (str == null) {
            str = "development version";
        }
        return str;
    }
}
