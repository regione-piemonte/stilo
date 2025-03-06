// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.ClockHandler;
import org.apache.batik.parser.ClockParser;
import java.util.StringTokenizer;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import org.apache.batik.transcoder.Transcoder;
import java.util.Iterator;
import java.io.FileFilter;
import java.io.File;
import org.apache.batik.util.ApplicationSecurityEnforcer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main implements SVGConverterController
{
    public static final String RASTERIZER_SECURITY_POLICY = "org/apache/batik/apps/rasterizer/resources/rasterizer.policy";
    public static String USAGE;
    public static String CL_OPTION_OUTPUT;
    public static String CL_OPTION_OUTPUT_DESCRIPTION;
    public static String CL_OPTION_MIME_TYPE;
    public static String CL_OPTION_MIME_TYPE_DESCRIPTION;
    public static String CL_OPTION_WIDTH;
    public static String CL_OPTION_WIDTH_DESCRIPTION;
    public static String CL_OPTION_HEIGHT;
    public static String CL_OPTION_HEIGHT_DESCRIPTION;
    public static String CL_OPTION_MAX_WIDTH;
    public static String CL_OPTION_MAX_WIDTH_DESCRIPTION;
    public static String CL_OPTION_MAX_HEIGHT;
    public static String CL_OPTION_MAX_HEIGHT_DESCRIPTION;
    public static String CL_OPTION_AOI;
    public static String CL_OPTION_AOI_DESCRIPTION;
    public static String CL_OPTION_BACKGROUND_COLOR;
    public static String CL_OPTION_BACKGROUND_COLOR_DESCRIPTION;
    public static String CL_OPTION_MEDIA_TYPE;
    public static String CL_OPTION_MEDIA_TYPE_DESCRIPTION;
    public static String CL_OPTION_DEFAULT_FONT_FAMILY;
    public static String CL_OPTION_DEFAULT_FONT_FAMILY_DESCRIPTION;
    public static String CL_OPTION_ALTERNATE_STYLESHEET;
    public static String CL_OPTION_ALTERNATE_STYLESHEET_DESCRIPTION;
    public static String CL_OPTION_VALIDATE;
    public static String CL_OPTION_VALIDATE_DESCRIPTION;
    public static String CL_OPTION_ONLOAD;
    public static String CL_OPTION_ONLOAD_DESCRIPTION;
    public static String CL_OPTION_SNAPSHOT_TIME;
    public static String CL_OPTION_SNAPSHOT_TIME_DESCRIPTION;
    public static String CL_OPTION_LANGUAGE;
    public static String CL_OPTION_LANGUAGE_DESCRIPTION;
    public static String CL_OPTION_USER_STYLESHEET;
    public static String CL_OPTION_USER_STYLESHEET_DESCRIPTION;
    public static String CL_OPTION_DPI;
    public static String CL_OPTION_DPI_DESCRIPTION;
    public static String CL_OPTION_QUALITY;
    public static String CL_OPTION_QUALITY_DESCRIPTION;
    public static String CL_OPTION_INDEXED;
    public static String CL_OPTION_INDEXED_DESCRIPTION;
    public static String CL_OPTION_ALLOWED_SCRIPTS;
    public static String CL_OPTION_ALLOWED_SCRIPTS_DESCRIPTION;
    public static String CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN;
    public static String CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN_DESCRIPTION;
    public static String CL_OPTION_SECURITY_OFF;
    public static String CL_OPTION_SECURITY_OFF_DESCRIPTION;
    protected static Map optionMap;
    protected static Map mimeTypeMap;
    protected List args;
    public static final String ERROR_NOT_ENOUGH_OPTION_VALUES = "Main.error.not.enough.option.values";
    public static final String ERROR_ILLEGAL_ARGUMENT = "Main.error.illegal.argument";
    public static final String ERROR_WHILE_CONVERTING_FILES = "Main.error.while.converting.files";
    public static final String MESSAGE_ABOUT_TO_TRANSCODE = "Main.message.about.to.transcode";
    public static final String MESSAGE_ABOUT_TO_TRANSCODE_SOURCE = "Main.message.about.to.transcode.source";
    public static final String MESSAGE_CONVERSION_FAILED = "Main.message.conversion.failed";
    public static final String MESSAGE_CONVERSION_SUCCESS = "Main.message.conversion.success";
    
    public Main(final String[] array) {
        this.args = new ArrayList();
        for (int i = 0; i < array.length; ++i) {
            this.args.add(array[i]);
        }
    }
    
    protected void error(final String s, final Object[] array) {
        System.err.println(Messages.formatMessage(s, array));
    }
    
    public void execute() {
        final SVGConverter svgConverter = new SVGConverter(this);
        final ArrayList<String> list = new ArrayList<String>();
        for (int size = this.args.size(), i = 0; i < size; ++i) {
            final String s = this.args.get(i);
            final OptionHandler optionHandler = Main.optionMap.get(s);
            if (optionHandler == null) {
                list.add(s);
            }
            else {
                final int optionValuesLength = optionHandler.getOptionValuesLength();
                if (i + optionValuesLength >= size) {
                    this.error("Main.error.not.enough.option.values", new Object[] { s, optionHandler.getOptionDescription() });
                    return;
                }
                final String[] array = new String[optionValuesLength];
                for (int j = 0; j < optionValuesLength; ++j) {
                    array[j] = (String)this.args.get(1 + i + j);
                }
                i += optionValuesLength;
                try {
                    optionHandler.handleOption(array, svgConverter);
                }
                catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                    this.error("Main.error.illegal.argument", new Object[] { s, optionHandler.getOptionDescription(), this.toString(array) });
                    return;
                }
            }
        }
        final ApplicationSecurityEnforcer applicationSecurityEnforcer = new ApplicationSecurityEnforcer(this.getClass(), "org/apache/batik/apps/rasterizer/resources/rasterizer.policy");
        applicationSecurityEnforcer.enforceSecurity(!svgConverter.getSecurityOff());
        final String[] expandSources = this.expandSources(list);
        svgConverter.setSources(expandSources);
        this.validateConverterConfig(svgConverter);
        if (expandSources == null || expandSources.length < 1) {
            System.out.println(Main.USAGE);
            System.out.flush();
            applicationSecurityEnforcer.enforceSecurity(false);
            return;
        }
        try {
            svgConverter.execute();
        }
        catch (SVGConverterException ex2) {
            this.error("Main.error.while.converting.files", new Object[] { ex2.getMessage() });
        }
        finally {
            System.out.flush();
            applicationSecurityEnforcer.enforceSecurity(false);
        }
    }
    
    protected String toString(final String[] array) {
        final StringBuffer sb = new StringBuffer();
        for (int n = (array != null) ? array.length : 0, i = 0; i < n; ++i) {
            sb.append(array[i]);
            sb.append(' ');
        }
        return sb.toString();
    }
    
    public void validateConverterConfig(final SVGConverter svgConverter) {
    }
    
    protected String[] expandSources(final List list) {
        final ArrayList list2 = new ArrayList<String>();
        for (final String pathname : list) {
            final File file = new File(pathname);
            if (file.exists() && file.isDirectory()) {
                final File[] listFiles = file.listFiles(new SVGConverter.SVGFileFilter());
                for (int i = 0; i < listFiles.length; ++i) {
                    list2.add(listFiles[i].getPath());
                }
            }
            else {
                list2.add(pathname);
            }
        }
        final String[] array = new String[list2.size()];
        list2.toArray(array);
        return array;
    }
    
    public static void main(final String[] array) {
        new Main(array).execute();
        System.exit(0);
    }
    
    public boolean proceedWithComputedTask(final Transcoder transcoder, final Map map, final List list, final List list2) {
        System.out.println(Messages.formatMessage("Main.message.about.to.transcode", new Object[] { "" + list.size() }));
        return true;
    }
    
    public boolean proceedWithSourceTranscoding(final SVGConverterSource svgConverterSource, final File file) {
        System.out.print(Messages.formatMessage("Main.message.about.to.transcode.source", new Object[] { svgConverterSource.toString(), file.toString() }));
        return true;
    }
    
    public boolean proceedOnSourceTranscodingFailure(final SVGConverterSource svgConverterSource, final File file, final String s) {
        System.out.println(Messages.formatMessage("Main.message.conversion.failed", new Object[] { s }));
        return true;
    }
    
    public void onSourceTranscodingSuccess(final SVGConverterSource svgConverterSource, final File file) {
        System.out.println(Messages.formatMessage("Main.message.conversion.success", null));
    }
    
    static {
        Main.USAGE = Messages.formatMessage("Main.usage", null);
        Main.CL_OPTION_OUTPUT = Messages.get("Main.cl.option.output", "-d");
        Main.CL_OPTION_OUTPUT_DESCRIPTION = Messages.get("Main.cl.option.output.description", "No description");
        Main.CL_OPTION_MIME_TYPE = Messages.get("Main.cl.option.mime.type", "-m");
        Main.CL_OPTION_MIME_TYPE_DESCRIPTION = Messages.get("Main.cl.option.mime.type.description", "No description");
        Main.CL_OPTION_WIDTH = Messages.get("Main.cl.option.width", "-w");
        Main.CL_OPTION_WIDTH_DESCRIPTION = Messages.get("Main.cl.option.width.description", "No description");
        Main.CL_OPTION_HEIGHT = Messages.get("Main.cl.option.height", "-h");
        Main.CL_OPTION_HEIGHT_DESCRIPTION = Messages.get("Main.cl.option.height.description", "No description");
        Main.CL_OPTION_MAX_WIDTH = Messages.get("Main.cl.option.max.width", "-maxw");
        Main.CL_OPTION_MAX_WIDTH_DESCRIPTION = Messages.get("Main.cl.option.max.width.description", "No description");
        Main.CL_OPTION_MAX_HEIGHT = Messages.get("Main.cl.option.max.height", "-maxh");
        Main.CL_OPTION_MAX_HEIGHT_DESCRIPTION = Messages.get("Main.cl.option.max.height.description", "No description");
        Main.CL_OPTION_AOI = Messages.get("Main.cl.option.aoi", "-a");
        Main.CL_OPTION_AOI_DESCRIPTION = Messages.get("Main.cl.option.aoi.description", "No description");
        Main.CL_OPTION_BACKGROUND_COLOR = Messages.get("Main.cl.option.background.color", "-bg");
        Main.CL_OPTION_BACKGROUND_COLOR_DESCRIPTION = Messages.get("Main.cl.option.background.color.description", "No description");
        Main.CL_OPTION_MEDIA_TYPE = Messages.get("Main.cl.option.media.type", "-cssMedia");
        Main.CL_OPTION_MEDIA_TYPE_DESCRIPTION = Messages.get("Main.cl.option.media.type.description", "No description");
        Main.CL_OPTION_DEFAULT_FONT_FAMILY = Messages.get("Main.cl.option.default.font.family", "-font-family");
        Main.CL_OPTION_DEFAULT_FONT_FAMILY_DESCRIPTION = Messages.get("Main.cl.option.default.font.family.description", "No description");
        Main.CL_OPTION_ALTERNATE_STYLESHEET = Messages.get("Main.cl.option.alternate.stylesheet", "-cssAlternate");
        Main.CL_OPTION_ALTERNATE_STYLESHEET_DESCRIPTION = Messages.get("Main.cl.option.alternate.stylesheet.description", "No description");
        Main.CL_OPTION_VALIDATE = Messages.get("Main.cl.option.validate", "-validate");
        Main.CL_OPTION_VALIDATE_DESCRIPTION = Messages.get("Main.cl.option.validate.description", "No description");
        Main.CL_OPTION_ONLOAD = Messages.get("Main.cl.option.onload", "-onload");
        Main.CL_OPTION_ONLOAD_DESCRIPTION = Messages.get("Main.cl.option.onload.description", "No description");
        Main.CL_OPTION_SNAPSHOT_TIME = Messages.get("Main.cl.option.snapshot.time", "-snapshotTime");
        Main.CL_OPTION_SNAPSHOT_TIME_DESCRIPTION = Messages.get("Main.cl.option.snapshot.time.description", "No description");
        Main.CL_OPTION_LANGUAGE = Messages.get("Main.cl.option.language", "-lang");
        Main.CL_OPTION_LANGUAGE_DESCRIPTION = Messages.get("Main.cl.option.language.description", "No description");
        Main.CL_OPTION_USER_STYLESHEET = Messages.get("Main.cl.option.user.stylesheet", "-cssUser");
        Main.CL_OPTION_USER_STYLESHEET_DESCRIPTION = Messages.get("Main.cl.option.user.stylesheet.description", "No description");
        Main.CL_OPTION_DPI = Messages.get("Main.cl.option.dpi", "-dpi");
        Main.CL_OPTION_DPI_DESCRIPTION = Messages.get("Main.cl.option.dpi.description", "No description");
        Main.CL_OPTION_QUALITY = Messages.get("Main.cl.option.quality", "-q");
        Main.CL_OPTION_QUALITY_DESCRIPTION = Messages.get("Main.cl.option.quality.description", "No description");
        Main.CL_OPTION_INDEXED = Messages.get("Main.cl.option.indexed", "-indexed");
        Main.CL_OPTION_INDEXED_DESCRIPTION = Messages.get("Main.cl.option.indexed.description", "No description");
        Main.CL_OPTION_ALLOWED_SCRIPTS = Messages.get("Main.cl.option.allowed.scripts", "-scripts");
        Main.CL_OPTION_ALLOWED_SCRIPTS_DESCRIPTION = Messages.get("Main.cl.option.allowed.scripts.description", "No description");
        Main.CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN = Messages.get("Main.cl.option.constrain.script.origin", "-anyScriptOrigin");
        Main.CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN_DESCRIPTION = Messages.get("Main.cl.option.constrain.script.origin.description", "No description");
        Main.CL_OPTION_SECURITY_OFF = Messages.get("Main.cl.option.security.off", "-scriptSecurityOff");
        Main.CL_OPTION_SECURITY_OFF_DESCRIPTION = Messages.get("Main.cl.option.security.off.description", "No description");
        Main.optionMap = new HashMap();
        (Main.mimeTypeMap = new HashMap()).put("image/jpg", DestinationType.JPEG);
        Main.mimeTypeMap.put("image/jpeg", DestinationType.JPEG);
        Main.mimeTypeMap.put("image/jpe", DestinationType.JPEG);
        Main.mimeTypeMap.put("image/png", DestinationType.PNG);
        Main.mimeTypeMap.put("application/pdf", DestinationType.PDF);
        Main.mimeTypeMap.put("image/tiff", DestinationType.TIFF);
        Main.optionMap.put(Main.CL_OPTION_OUTPUT, new SingleValueOptionHandler() {
            public void handleOption(final String pathname, final SVGConverter svgConverter) {
                svgConverter.setDst(new File(pathname));
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_OUTPUT_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_MIME_TYPE, new SingleValueOptionHandler() {
            public void handleOption(final String s, final SVGConverter svgConverter) {
                final DestinationType destinationType = Main.mimeTypeMap.get(s);
                if (destinationType == null) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setDestinationType(destinationType);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_MIME_TYPE_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_WIDTH, new FloatOptionHandler() {
            public void handleOption(final float width, final SVGConverter svgConverter) {
                if (width <= 0.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setWidth(width);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_WIDTH_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_HEIGHT, new FloatOptionHandler() {
            public void handleOption(final float height, final SVGConverter svgConverter) {
                if (height <= 0.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setHeight(height);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_HEIGHT_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_MAX_WIDTH, new FloatOptionHandler() {
            public void handleOption(final float maxWidth, final SVGConverter svgConverter) {
                if (maxWidth <= 0.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setMaxWidth(maxWidth);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_MAX_WIDTH_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_MAX_HEIGHT, new FloatOptionHandler() {
            public void handleOption(final float maxHeight, final SVGConverter svgConverter) {
                if (maxHeight <= 0.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setMaxHeight(maxHeight);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_MAX_HEIGHT_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_AOI, new RectangleOptionHandler() {
            public void handleOption(final Rectangle2D area, final SVGConverter svgConverter) {
                svgConverter.setArea(area);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_AOI_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_BACKGROUND_COLOR, new ColorOptionHandler() {
            public void handleOption(final Color backgroundColor, final SVGConverter svgConverter) {
                svgConverter.setBackgroundColor(backgroundColor);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_BACKGROUND_COLOR_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_MEDIA_TYPE, new SingleValueOptionHandler() {
            public void handleOption(final String mediaType, final SVGConverter svgConverter) {
                svgConverter.setMediaType(mediaType);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_MEDIA_TYPE_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_DEFAULT_FONT_FAMILY, new SingleValueOptionHandler() {
            public void handleOption(final String defaultFontFamily, final SVGConverter svgConverter) {
                svgConverter.setDefaultFontFamily(defaultFontFamily);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_DEFAULT_FONT_FAMILY_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_ALTERNATE_STYLESHEET, new SingleValueOptionHandler() {
            public void handleOption(final String alternateStylesheet, final SVGConverter svgConverter) {
                svgConverter.setAlternateStylesheet(alternateStylesheet);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_ALTERNATE_STYLESHEET_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_USER_STYLESHEET, new SingleValueOptionHandler() {
            public void handleOption(final String userStylesheet, final SVGConverter svgConverter) {
                svgConverter.setUserStylesheet(userStylesheet);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_USER_STYLESHEET_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_LANGUAGE, new SingleValueOptionHandler() {
            public void handleOption(final String language, final SVGConverter svgConverter) {
                svgConverter.setLanguage(language);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_LANGUAGE_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_DPI, new FloatOptionHandler() {
            public void handleOption(final float n, final SVGConverter svgConverter) {
                if (n <= 0.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setPixelUnitToMillimeter(2.54f / n * 10.0f);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_DPI_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_QUALITY, new FloatOptionHandler() {
            public void handleOption(final float quality, final SVGConverter svgConverter) {
                if (quality <= 0.0f || quality >= 1.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setQuality(quality);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_QUALITY_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_INDEXED, new FloatOptionHandler() {
            public void handleOption(final float n, final SVGConverter svgConverter) {
                if (n != 1.0f && n != 2.0f && n != 4.0f && n != 8.0f) {
                    throw new IllegalArgumentException();
                }
                svgConverter.setIndexed((int)n);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_INDEXED_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_VALIDATE, new NoValueOptionHandler() {
            public void handleOption(final SVGConverter svgConverter) {
                svgConverter.setValidate(true);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_VALIDATE_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_ONLOAD, new NoValueOptionHandler() {
            public void handleOption(final SVGConverter svgConverter) {
                svgConverter.setExecuteOnload(true);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_ONLOAD_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_SNAPSHOT_TIME, new TimeOptionHandler() {
            public void handleOption(final float snapshotTime, final SVGConverter svgConverter) {
                svgConverter.setExecuteOnload(true);
                svgConverter.setSnapshotTime(snapshotTime);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_SNAPSHOT_TIME_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_ALLOWED_SCRIPTS, new SingleValueOptionHandler() {
            public void handleOption(final String allowedScriptTypes, final SVGConverter svgConverter) {
                svgConverter.setAllowedScriptTypes(allowedScriptTypes);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_ALLOWED_SCRIPTS_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN, new NoValueOptionHandler() {
            public void handleOption(final SVGConverter svgConverter) {
                svgConverter.setConstrainScriptOrigin(false);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_CONSTRAIN_SCRIPT_ORIGIN_DESCRIPTION;
            }
        });
        Main.optionMap.put(Main.CL_OPTION_SECURITY_OFF, new NoValueOptionHandler() {
            public void handleOption(final SVGConverter svgConverter) {
                svgConverter.setSecurityOff(true);
            }
            
            public String getOptionDescription() {
                return Main.CL_OPTION_SECURITY_OFF_DESCRIPTION;
            }
        });
    }
    
    public abstract static class ColorOptionHandler extends SingleValueOptionHandler
    {
        public void handleOption(final String s, final SVGConverter svgConverter) {
            final Color argb = this.parseARGB(s);
            if (argb == null) {
                throw new IllegalArgumentException();
            }
            this.handleOption(argb, svgConverter);
        }
        
        public abstract void handleOption(final Color p0, final SVGConverter p1);
        
        public Color parseARGB(final String str) {
            Color color = null;
            if (str != null) {
                final StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
                if (stringTokenizer.countTokens() == 4) {
                    final String nextToken = stringTokenizer.nextToken();
                    final String nextToken2 = stringTokenizer.nextToken();
                    final String nextToken3 = stringTokenizer.nextToken();
                    final String nextToken4 = stringTokenizer.nextToken();
                    int int1 = -1;
                    int int2 = -1;
                    int int3 = -1;
                    int int4 = -1;
                    try {
                        int1 = Integer.parseInt(nextToken);
                        int2 = Integer.parseInt(nextToken2);
                        int3 = Integer.parseInt(nextToken3);
                        int4 = Integer.parseInt(nextToken4);
                    }
                    catch (NumberFormatException ex) {}
                    if (int1 >= 0 && int1 <= 255 && int2 >= 0 && int2 <= 255 && int3 >= 0 && int3 <= 255 && int4 >= 0 && int4 <= 255) {
                        color = new Color(int2, int3, int4, int1);
                    }
                }
            }
            return color;
        }
    }
    
    public abstract static class SingleValueOptionHandler extends AbstractOptionHandler
    {
        public void safeHandleOption(final String[] array, final SVGConverter svgConverter) {
            this.handleOption(array[0], svgConverter);
        }
        
        public int getOptionValuesLength() {
            return 1;
        }
        
        public abstract void handleOption(final String p0, final SVGConverter p1);
    }
    
    public abstract static class AbstractOptionHandler implements OptionHandler
    {
        public void handleOption(final String[] array, final SVGConverter svgConverter) {
            if (((array != null) ? array.length : 0) != this.getOptionValuesLength()) {
                throw new IllegalArgumentException();
            }
            this.safeHandleOption(array, svgConverter);
        }
        
        public abstract void safeHandleOption(final String[] p0, final SVGConverter p1);
    }
    
    public interface OptionHandler
    {
        void handleOption(final String[] p0, final SVGConverter p1);
        
        int getOptionValuesLength();
        
        String getOptionDescription();
    }
    
    public abstract static class RectangleOptionHandler extends SingleValueOptionHandler
    {
        public void handleOption(final String s, final SVGConverter svgConverter) {
            final Rectangle2D.Float rect = this.parseRect(s);
            if (rect == null) {
                throw new IllegalArgumentException();
            }
            this.handleOption(rect, svgConverter);
        }
        
        public abstract void handleOption(final Rectangle2D p0, final SVGConverter p1);
        
        public Rectangle2D.Float parseRect(String string) {
            Rectangle2D.Float float1 = null;
            if (string != null) {
                if (!string.toLowerCase().endsWith("f")) {
                    string += "f";
                }
                final StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
                if (stringTokenizer.countTokens() == 4) {
                    final String nextToken = stringTokenizer.nextToken();
                    final String nextToken2 = stringTokenizer.nextToken();
                    final String nextToken3 = stringTokenizer.nextToken();
                    final String nextToken4 = stringTokenizer.nextToken();
                    float float2 = Float.NaN;
                    float float3 = Float.NaN;
                    float float4 = Float.NaN;
                    float float5 = Float.NaN;
                    try {
                        float2 = Float.parseFloat(nextToken);
                        float3 = Float.parseFloat(nextToken2);
                        float4 = Float.parseFloat(nextToken3);
                        float5 = Float.parseFloat(nextToken4);
                    }
                    catch (NumberFormatException ex) {}
                    if (!Float.isNaN(float2) && !Float.isNaN(float3) && !Float.isNaN(float4) && float4 > 0.0f && !Float.isNaN(float5) && float5 > 0.0f) {
                        float1 = new Rectangle2D.Float(float2, float3, float4, float5);
                    }
                }
            }
            return float1;
        }
    }
    
    public abstract static class TimeOptionHandler extends FloatOptionHandler
    {
        public void handleOption(final String s, final SVGConverter svgConverter) {
            try {
                final ClockParser clockParser = new ClockParser(false);
                clockParser.setClockHandler(new ClockHandler() {
                    private final /* synthetic */ TimeOptionHandler this$0 = this$0;
                    
                    public void clockValue(final float n) {
                        this.this$0.handleOption(n, svgConverter);
                    }
                });
                clockParser.parse(s);
            }
            catch (ParseException ex) {
                throw new IllegalArgumentException();
            }
        }
        
        public abstract void handleOption(final float p0, final SVGConverter p1);
    }
    
    public abstract static class FloatOptionHandler extends SingleValueOptionHandler
    {
        public void handleOption(final String s, final SVGConverter svgConverter) {
            try {
                this.handleOption(Float.parseFloat(s), svgConverter);
            }
            catch (NumberFormatException ex) {
                throw new IllegalArgumentException();
            }
        }
        
        public abstract void handleOption(final float p0, final SVGConverter p1);
    }
    
    public abstract static class NoValueOptionHandler extends AbstractOptionHandler
    {
        public void safeHandleOption(final String[] array, final SVGConverter svgConverter) {
            this.handleOption(svgConverter);
        }
        
        public int getOptionValuesLength() {
            return 0;
        }
        
        public abstract void handleOption(final SVGConverter p0);
    }
}
