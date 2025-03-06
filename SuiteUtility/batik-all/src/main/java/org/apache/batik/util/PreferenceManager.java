// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.util.StringTokenizer;
import java.awt.Rectangle;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.security.AccessControlException;
import java.util.Map;
import java.util.Properties;

public class PreferenceManager
{
    protected Properties internal;
    protected Map defaults;
    protected String prefFileName;
    protected String fullName;
    protected static final String USER_HOME;
    protected static final String USER_DIR;
    protected static final String FILE_SEP;
    private static String PREF_DIR;
    
    protected static String getSystemProperty(final String key) {
        try {
            return System.getProperty(key);
        }
        catch (AccessControlException ex) {
            return "";
        }
    }
    
    public PreferenceManager(final String s) {
        this(s, null);
    }
    
    public PreferenceManager(final String prefFileName, final Map defaults) {
        this.internal = null;
        this.defaults = null;
        this.prefFileName = null;
        this.fullName = null;
        this.prefFileName = prefFileName;
        this.defaults = defaults;
        this.internal = new Properties();
    }
    
    public static void setPreferenceDirectory(final String pref_DIR) {
        PreferenceManager.PREF_DIR = pref_DIR;
    }
    
    public static String getPreferenceDirectory() {
        return PreferenceManager.PREF_DIR;
    }
    
    public void load() throws IOException {
        InputStream inStream = null;
        if (this.fullName != null) {
            try {
                inStream = new FileInputStream(this.fullName);
            }
            catch (IOException ex) {
                this.fullName = null;
            }
        }
        if (this.fullName == null) {
            if (PreferenceManager.PREF_DIR != null) {
                try {
                    final String string = PreferenceManager.PREF_DIR + PreferenceManager.FILE_SEP + this.prefFileName;
                    this.fullName = string;
                    inStream = new FileInputStream(string);
                }
                catch (IOException ex2) {
                    this.fullName = null;
                }
            }
            if (this.fullName == null) {
                try {
                    final String string2 = PreferenceManager.USER_HOME + PreferenceManager.FILE_SEP + this.prefFileName;
                    this.fullName = string2;
                    inStream = new FileInputStream(string2);
                }
                catch (IOException ex3) {
                    try {
                        final String string3 = PreferenceManager.USER_DIR + PreferenceManager.FILE_SEP + this.prefFileName;
                        this.fullName = string3;
                        inStream = new FileInputStream(string3);
                    }
                    catch (IOException ex4) {
                        this.fullName = null;
                    }
                }
            }
        }
        if (this.fullName != null) {
            try {
                this.internal.load(inStream);
            }
            finally {
                ((FileInputStream)inStream).close();
            }
        }
    }
    
    public void save() throws IOException {
        OutputStream out = null;
        if (this.fullName != null) {
            try {
                out = new FileOutputStream(this.fullName);
            }
            catch (IOException ex2) {
                this.fullName = null;
            }
        }
        if (this.fullName == null) {
            if (PreferenceManager.PREF_DIR != null) {
                try {
                    final String string = PreferenceManager.PREF_DIR + PreferenceManager.FILE_SEP + this.prefFileName;
                    this.fullName = string;
                    out = new FileOutputStream(string);
                }
                catch (IOException ex3) {
                    this.fullName = null;
                }
            }
            if (this.fullName == null) {
                try {
                    final String string2 = PreferenceManager.USER_HOME + PreferenceManager.FILE_SEP + this.prefFileName;
                    this.fullName = string2;
                    out = new FileOutputStream(string2);
                }
                catch (IOException ex) {
                    this.fullName = null;
                    throw ex;
                }
            }
        }
        try {
            this.internal.store(out, this.prefFileName);
        }
        finally {
            ((FileOutputStream)out).close();
        }
    }
    
    private Object getDefault(final String s) {
        if (this.defaults != null) {
            return this.defaults.get(s);
        }
        return null;
    }
    
    public Rectangle getRectangle(final String s) {
        final Rectangle rectangle = (Rectangle)this.getDefault(s);
        final String property = this.internal.getProperty(s);
        if (property == null) {
            return rectangle;
        }
        final Rectangle rectangle2 = new Rectangle();
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, " ", false);
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return rectangle;
            }
            final int int1 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return rectangle;
            }
            final int int2 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return rectangle;
            }
            final int int3 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return rectangle;
            }
            rectangle2.setBounds(int1, int2, int3, Integer.parseInt(stringTokenizer.nextToken()));
            return rectangle2;
        }
        catch (NumberFormatException ex) {
            this.internal.remove(s);
            return rectangle;
        }
    }
    
    public Dimension getDimension(final String s) {
        final Dimension dimension = (Dimension)this.getDefault(s);
        final String property = this.internal.getProperty(s);
        if (property == null) {
            return dimension;
        }
        final Dimension dimension2 = new Dimension();
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, " ", false);
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return dimension;
            }
            final int int1 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return dimension;
            }
            dimension2.setSize(int1, Integer.parseInt(stringTokenizer.nextToken()));
            return dimension2;
        }
        catch (NumberFormatException ex) {
            this.internal.remove(s);
            return dimension;
        }
    }
    
    public Point getPoint(final String key) {
        final Point point = (Point)this.getDefault(key);
        final String property = this.internal.getProperty(key);
        if (property == null) {
            return point;
        }
        final Point point2 = new Point();
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, " ", false);
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(key);
                return point;
            }
            final int int1 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(key);
                return point;
            }
            final int int2 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(key);
                return point;
            }
            point2.setLocation(int1, int2);
            return point2;
        }
        catch (NumberFormatException ex) {
            this.internal.remove(key);
            return point;
        }
    }
    
    public Color getColor(final String s) {
        final Color color = (Color)this.getDefault(s);
        final String property = this.internal.getProperty(s);
        if (property == null) {
            return color;
        }
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, " ", false);
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return color;
            }
            final int int1 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return color;
            }
            final int int2 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return color;
            }
            final int int3 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(s);
                return color;
            }
            return new Color(int1, int2, int3, Integer.parseInt(stringTokenizer.nextToken()));
        }
        catch (NumberFormatException ex) {
            this.internal.remove(s);
            return color;
        }
    }
    
    public Font getFont(final String key) {
        final Font font = (Font)this.getDefault(key);
        final String property = this.internal.getProperty(key);
        if (property == null) {
            return font;
        }
        try {
            final StringTokenizer stringTokenizer = new StringTokenizer(property, " ", false);
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(key);
                return font;
            }
            final String nextToken = stringTokenizer.nextToken();
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(key);
                return font;
            }
            final int int1 = Integer.parseInt(stringTokenizer.nextToken());
            if (!stringTokenizer.hasMoreTokens()) {
                this.internal.remove(key);
                return font;
            }
            return new Font(nextToken, Integer.parseInt(stringTokenizer.nextToken()), int1);
        }
        catch (NumberFormatException ex) {
            this.internal.remove(key);
            return font;
        }
    }
    
    public String getString(final String key) {
        String property = this.internal.getProperty(key);
        if (property == null) {
            property = (String)this.getDefault(key);
        }
        return property;
    }
    
    public String[] getStrings(final String str) {
        int i = 0;
        final ArrayList list = new ArrayList<String>();
        while (true) {
            final String string = this.getString(str + i);
            ++i;
            if (string == null) {
                break;
            }
            list.add(string);
        }
        if (list.size() != 0) {
            return list.toArray(new String[list.size()]);
        }
        return (String[])this.getDefault(str);
    }
    
    public URL getURL(final String s) {
        final URL url = (URL)this.getDefault(s);
        final String property = this.internal.getProperty(s);
        if (property == null) {
            return url;
        }
        URL url2;
        try {
            url2 = new URL(property);
        }
        catch (MalformedURLException ex) {
            this.internal.remove(s);
            return url;
        }
        return url2;
    }
    
    public URL[] getURLs(final String str) {
        int i = 0;
        final ArrayList list = new ArrayList<URL>();
        while (true) {
            final URL url = this.getURL(str + i);
            ++i;
            if (url == null) {
                break;
            }
            list.add(url);
        }
        if (list.size() != 0) {
            return list.toArray(new URL[list.size()]);
        }
        return (URL[])this.getDefault(str);
    }
    
    public File getFile(final String s) {
        final File file = (File)this.getDefault(s);
        final String property = this.internal.getProperty(s);
        if (property == null) {
            return file;
        }
        final File file2 = new File(property);
        if (file2.exists()) {
            return file2;
        }
        this.internal.remove(s);
        return file;
    }
    
    public File[] getFiles(final String str) {
        int i = 0;
        final ArrayList list = new ArrayList<File>();
        while (true) {
            final File file = this.getFile(str + i);
            ++i;
            if (file == null) {
                break;
            }
            list.add(file);
        }
        if (list.size() != 0) {
            return list.toArray(new File[list.size()]);
        }
        return (File[])this.getDefault(str);
    }
    
    public int getInteger(final String s) {
        int intValue = 0;
        if (this.getDefault(s) != null) {
            intValue = (int)this.getDefault(s);
        }
        final String property = this.internal.getProperty(s);
        if (property == null) {
            return intValue;
        }
        int int1;
        try {
            int1 = Integer.parseInt(property);
        }
        catch (NumberFormatException ex) {
            this.internal.remove(s);
            return intValue;
        }
        return int1;
    }
    
    public float getFloat(final String key) {
        float floatValue = 0.0f;
        if (this.getDefault(key) != null) {
            floatValue = (float)this.getDefault(key);
        }
        final String property = this.internal.getProperty(key);
        if (property == null) {
            return floatValue;
        }
        float float1;
        try {
            float1 = Float.parseFloat(property);
        }
        catch (NumberFormatException ex) {
            this.setFloat(key, floatValue);
            return floatValue;
        }
        return float1;
    }
    
    public boolean getBoolean(final String s) {
        if (this.internal.getProperty(s) != null) {
            return this.internal.getProperty(s).equals("true");
        }
        return this.getDefault(s) != null && (boolean)this.getDefault(s);
    }
    
    public void setRectangle(final String s, final Rectangle rectangle) {
        if (rectangle != null && !rectangle.equals(this.getDefault(s))) {
            this.internal.setProperty(s, rectangle.x + " " + rectangle.y + " " + rectangle.width + ' ' + rectangle.height);
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setDimension(final String s, final Dimension dimension) {
        if (dimension != null && !dimension.equals(this.getDefault(s))) {
            this.internal.setProperty(s, dimension.width + " " + dimension.height);
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setPoint(final String s, final Point point) {
        if (point != null && !point.equals(this.getDefault(s))) {
            this.internal.setProperty(s, point.x + " " + point.y);
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setColor(final String s, final Color color) {
        if (color != null && !color.equals(this.getDefault(s))) {
            this.internal.setProperty(s, color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + color.getAlpha());
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setFont(final String s, final Font font) {
        if (font != null && !font.equals(this.getDefault(s))) {
            this.internal.setProperty(s, font.getName() + " " + font.getSize() + " " + font.getStyle());
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setString(final String s, final String value) {
        if (value != null && !value.equals(this.getDefault(s))) {
            this.internal.setProperty(s, value);
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setStrings(final String str, final String[] array) {
        int i = 0;
        if (array != null) {
            for (int j = 0; j < array.length; ++j) {
                if (array[j] != null) {
                    this.setString(str + i, array[j]);
                    ++i;
                }
            }
        }
        while (this.getString(str + i) != null) {
            this.setString(str + i, null);
            ++i;
        }
    }
    
    public void setURL(final String s, final URL url) {
        if (url != null && !url.equals(this.getDefault(s))) {
            this.internal.setProperty(s, url.toString());
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setURLs(final String str, final URL[] array) {
        int i = 0;
        if (array != null) {
            for (int j = 0; j < array.length; ++j) {
                if (array[j] != null) {
                    this.setURL(str + i, array[j]);
                    ++i;
                }
            }
        }
        while (this.getString(str + i) != null) {
            this.setString(str + i, null);
            ++i;
        }
    }
    
    public void setFile(final String s, final File file) {
        if (file != null && !file.equals(this.getDefault(s))) {
            this.internal.setProperty(s, file.getAbsolutePath());
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setFiles(final String str, final File[] array) {
        int i = 0;
        if (array != null) {
            for (int j = 0; j < array.length; ++j) {
                if (array[j] != null) {
                    this.setFile(str + i, array[j]);
                    ++i;
                }
            }
        }
        while (this.getString(str + i) != null) {
            this.setString(str + i, null);
            ++i;
        }
    }
    
    public void setInteger(final String s, final int i) {
        if (this.getDefault(s) != null && (int)this.getDefault(s) != i) {
            this.internal.setProperty(s, Integer.toString(i));
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setFloat(final String s, final float f) {
        if (this.getDefault(s) != null && (float)this.getDefault(s) != f) {
            this.internal.setProperty(s, Float.toString(f));
        }
        else {
            this.internal.remove(s);
        }
    }
    
    public void setBoolean(final String s, final boolean b) {
        if (this.getDefault(s) != null && (boolean)this.getDefault(s) != b) {
            this.internal.setProperty(s, b ? "true" : "false");
        }
        else {
            this.internal.remove(s);
        }
    }
    
    static {
        USER_HOME = getSystemProperty("user.home");
        USER_DIR = getSystemProperty("user.dir");
        FILE_SEP = getSystemProperty("file.separator");
        PreferenceManager.PREF_DIR = null;
    }
}
