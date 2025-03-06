// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.rasterizer;

import java.io.FileFilter;
import java.io.OutputStream;
import org.apache.batik.transcoder.TranscoderOutput;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.batik.transcoder.TranscoderInput;
import java.io.IOException;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.TranscodingHints;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.transcoder.Transcoder;
import java.util.ArrayList;
import java.awt.Color;
import java.io.File;
import java.util.List;
import java.awt.geom.Rectangle2D;

public class SVGConverter
{
    public static final String ERROR_NO_SOURCES_SPECIFIED = "SVGConverter.error.no.sources.specified";
    public static final String ERROR_CANNOT_COMPUTE_DESTINATION = "SVGConverter.error.cannot.compute.destination";
    public static final String ERROR_CANNOT_USE_DST_FILE = "SVGConverter.error.cannot.use.dst.file";
    public static final String ERROR_CANNOT_ACCESS_TRANSCODER = "SVGConverter.error.cannot.access.transcoder";
    public static final String ERROR_SOURCE_SAME_AS_DESTINATION = "SVGConverter.error.source.same.as.destination";
    public static final String ERROR_CANNOT_READ_SOURCE = "SVGConverter.error.cannot.read.source";
    public static final String ERROR_CANNOT_OPEN_SOURCE = "SVGConverter.error.cannot.open.source";
    public static final String ERROR_OUTPUT_NOT_WRITEABLE = "SVGConverter.error.output.not.writeable";
    public static final String ERROR_CANNOT_OPEN_OUTPUT_FILE = "SVGConverter.error.cannot.open.output.file";
    public static final String ERROR_UNABLE_TO_CREATE_OUTPUT_DIR = "SVGConverter.error.unable.to.create.output.dir";
    public static final String ERROR_WHILE_RASTERIZING_FILE = "SVGConverter.error.while.rasterizing.file";
    protected static final String SVG_EXTENSION = ".svg";
    protected static final float DEFAULT_QUALITY = -1.0f;
    protected static final float MAXIMUM_QUALITY = 0.99f;
    protected static final DestinationType DEFAULT_RESULT_TYPE;
    protected static final float DEFAULT_WIDTH = -1.0f;
    protected static final float DEFAULT_HEIGHT = -1.0f;
    protected DestinationType destinationType;
    protected float height;
    protected float width;
    protected float maxHeight;
    protected float maxWidth;
    protected float quality;
    protected int indexed;
    protected Rectangle2D area;
    protected String language;
    protected String userStylesheet;
    protected float pixelUnitToMillimeter;
    protected boolean validate;
    protected boolean executeOnload;
    protected float snapshotTime;
    protected String allowedScriptTypes;
    protected boolean constrainScriptOrigin;
    protected boolean securityOff;
    protected List sources;
    protected File dst;
    protected Color backgroundColor;
    protected String mediaType;
    protected String defaultFontFamily;
    protected String alternateStylesheet;
    protected List files;
    protected SVGConverterController controller;
    
    public SVGConverter() {
        this(new DefaultSVGConverterController());
    }
    
    public SVGConverter(final SVGConverterController controller) {
        this.destinationType = SVGConverter.DEFAULT_RESULT_TYPE;
        this.height = -1.0f;
        this.width = -1.0f;
        this.maxHeight = -1.0f;
        this.maxWidth = -1.0f;
        this.quality = -1.0f;
        this.indexed = -1;
        this.area = null;
        this.language = null;
        this.userStylesheet = null;
        this.pixelUnitToMillimeter = -1.0f;
        this.validate = false;
        this.executeOnload = false;
        this.snapshotTime = Float.NaN;
        this.allowedScriptTypes = null;
        this.constrainScriptOrigin = true;
        this.securityOff = false;
        this.sources = null;
        this.backgroundColor = null;
        this.mediaType = null;
        this.defaultFontFamily = null;
        this.alternateStylesheet = null;
        this.files = new ArrayList();
        if (controller == null) {
            throw new IllegalArgumentException();
        }
        this.controller = controller;
    }
    
    public void setDestinationType(final DestinationType destinationType) {
        if (destinationType == null) {
            throw new IllegalArgumentException();
        }
        this.destinationType = destinationType;
    }
    
    public DestinationType getDestinationType() {
        return this.destinationType;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public void setMaxHeight(final float maxHeight) {
        this.maxHeight = maxHeight;
    }
    
    public float getMaxHeight() {
        return this.maxHeight;
    }
    
    public void setMaxWidth(final float maxWidth) {
        this.maxWidth = maxWidth;
    }
    
    public float getMaxWidth() {
        return this.maxWidth;
    }
    
    public void setQuality(final float quality) throws IllegalArgumentException {
        if (quality >= 1.0f) {
            throw new IllegalArgumentException();
        }
        this.quality = quality;
    }
    
    public float getQuality() {
        return this.quality;
    }
    
    public void setIndexed(final int indexed) throws IllegalArgumentException {
        this.indexed = indexed;
    }
    
    public int getIndexed() {
        return this.indexed;
    }
    
    public void setLanguage(final String language) {
        this.language = language;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void setUserStylesheet(final String userStylesheet) {
        this.userStylesheet = userStylesheet;
    }
    
    public String getUserStylesheet() {
        return this.userStylesheet;
    }
    
    public void setPixelUnitToMillimeter(final float pixelUnitToMillimeter) {
        this.pixelUnitToMillimeter = pixelUnitToMillimeter;
    }
    
    public float getPixelUnitToMillimeter() {
        return this.pixelUnitToMillimeter;
    }
    
    public void setArea(final Rectangle2D area) {
        this.area = area;
    }
    
    public Rectangle2D getArea() {
        return this.area;
    }
    
    public void setSources(final String[] array) {
        if (array == null) {
            this.sources = null;
        }
        else {
            this.sources = new ArrayList();
            for (int i = 0; i < array.length; ++i) {
                if (array[i] != null) {
                    this.sources.add(array[i]);
                }
            }
            if (this.sources.size() == 0) {
                this.sources = null;
            }
        }
    }
    
    public List getSources() {
        return this.sources;
    }
    
    public void setDst(final File dst) {
        this.dst = dst;
    }
    
    public File getDst() {
        return this.dst;
    }
    
    public void setBackgroundColor(final Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }
    
    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getMediaType() {
        return this.mediaType;
    }
    
    public void setDefaultFontFamily(final String defaultFontFamily) {
        this.defaultFontFamily = defaultFontFamily;
    }
    
    public String getDefaultFontFamily() {
        return this.defaultFontFamily;
    }
    
    public void setAlternateStylesheet(final String alternateStylesheet) {
        this.alternateStylesheet = alternateStylesheet;
    }
    
    public String getAlternateStylesheet() {
        return this.alternateStylesheet;
    }
    
    public void setValidate(final boolean validate) {
        this.validate = validate;
    }
    
    public boolean getValidate() {
        return this.validate;
    }
    
    public void setExecuteOnload(final boolean executeOnload) {
        this.executeOnload = executeOnload;
    }
    
    public boolean getExecuteOnload() {
        return this.executeOnload;
    }
    
    public void setSnapshotTime(final float snapshotTime) {
        this.snapshotTime = snapshotTime;
    }
    
    public float getSnapshotTime() {
        return this.snapshotTime;
    }
    
    public void setAllowedScriptTypes(final String allowedScriptTypes) {
        this.allowedScriptTypes = allowedScriptTypes;
    }
    
    public String getAllowedScriptTypes() {
        return this.allowedScriptTypes;
    }
    
    public void setConstrainScriptOrigin(final boolean constrainScriptOrigin) {
        this.constrainScriptOrigin = constrainScriptOrigin;
    }
    
    public boolean getConstrainScriptOrigin() {
        return this.constrainScriptOrigin;
    }
    
    public void setSecurityOff(final boolean securityOff) {
        this.securityOff = securityOff;
    }
    
    public boolean getSecurityOff() {
        return this.securityOff;
    }
    
    protected boolean isFile(final File file) {
        if (file.exists()) {
            return file.isFile();
        }
        return file.toString().toLowerCase().endsWith(this.destinationType.getExtension());
    }
    
    public void execute() throws SVGConverterException {
        final List computeSources = this.computeSources();
        List<File> computeDstFiles;
        if (computeSources.size() == 1 && this.dst != null && this.isFile(this.dst)) {
            computeDstFiles = new ArrayList<File>();
            computeDstFiles.add(this.dst);
        }
        else {
            computeDstFiles = (List<File>)this.computeDstFiles(computeSources);
        }
        final Transcoder transcoder = this.destinationType.getTranscoder();
        if (transcoder == null) {
            throw new SVGConverterException("SVGConverter.error.cannot.access.transcoder", new Object[] { this.destinationType.toString() }, true);
        }
        final Map computeTranscodingHints = this.computeTranscodingHints();
        transcoder.setTranscodingHints(computeTranscodingHints);
        if (!this.controller.proceedWithComputedTask(transcoder, computeTranscodingHints, computeSources, computeDstFiles)) {
            return;
        }
        for (int i = 0; i < computeSources.size(); ++i) {
            final SVGConverterSource svgConverterSource = computeSources.get(i);
            final File file = computeDstFiles.get(i);
            this.createOutputDir(file);
            this.transcode(svgConverterSource, file, transcoder);
        }
    }
    
    protected List computeDstFiles(final List list) throws SVGConverterException {
        final ArrayList<File> list2 = new ArrayList<File>();
        if (this.dst != null) {
            if (this.dst.exists() && this.dst.isFile()) {
                throw new SVGConverterException("SVGConverter.error.cannot.use.dst.file");
            }
            for (int size = list.size(), i = 0; i < size; ++i) {
                list2.add(new File(this.dst.getPath(), this.getDestinationFile(list.get(i).getName())));
            }
        }
        else {
            for (int size2 = list.size(), j = 0; j < size2; ++j) {
                final SVGConverterSource svgConverterSource = list.get(j);
                if (!(svgConverterSource instanceof SVGConverterFileSource)) {
                    throw new SVGConverterException("SVGConverter.error.cannot.compute.destination", new Object[] { svgConverterSource });
                }
                list2.add(new File(((SVGConverterFileSource)svgConverterSource).getFile().getParent(), this.getDestinationFile(svgConverterSource.getName())));
            }
        }
        return list2;
    }
    
    protected List computeSources() throws SVGConverterException {
        final ArrayList<SVGConverterFileSource> list = new ArrayList<SVGConverterFileSource>();
        if (this.sources == null) {
            throw new SVGConverterException("SVGConverter.error.no.sources.specified");
        }
        for (int size = this.sources.size(), i = 0; i < size; ++i) {
            final String pathname = this.sources.get(i);
            final File file = new File(pathname);
            if (file.exists()) {
                list.add(new SVGConverterFileSource(file));
            }
            else {
                final String[] fileNRef = this.getFileNRef(pathname);
                final File file2 = new File(fileNRef[0]);
                if (file2.exists()) {
                    list.add(new SVGConverterFileSource(file2, fileNRef[1]));
                }
                else {
                    list.add((SVGConverterFileSource)new SVGConverterURLSource(pathname));
                }
            }
        }
        return list;
    }
    
    public String[] getFileNRef(final String s) {
        final int lastIndex = s.lastIndexOf(35);
        final String[] array = { s, "" };
        if (lastIndex > -1) {
            array[0] = s.substring(0, lastIndex);
            if (lastIndex + 1 < s.length()) {
                array[1] = s.substring(lastIndex + 1);
            }
        }
        return array;
    }
    
    protected Map computeTranscodingHints() {
        final HashMap<TranscodingHints.Key, Float> hashMap = new HashMap<TranscodingHints.Key, Float>();
        if (this.area != null) {
            hashMap.put(ImageTranscoder.KEY_AOI, this.area);
        }
        if (this.quality > 0.0f) {
            hashMap.put(JPEGTranscoder.KEY_QUALITY, (Rectangle2D)new Float(this.quality));
        }
        if (this.indexed != -1) {
            hashMap.put(PNGTranscoder.KEY_INDEXED, (Rectangle2D)new Integer(this.indexed));
        }
        if (this.backgroundColor != null) {
            hashMap.put(ImageTranscoder.KEY_BACKGROUND_COLOR, (Rectangle2D)this.backgroundColor);
        }
        if (this.height > 0.0f) {
            hashMap.put(ImageTranscoder.KEY_HEIGHT, (Rectangle2D)new Float(this.height));
        }
        if (this.width > 0.0f) {
            hashMap.put(ImageTranscoder.KEY_WIDTH, (Rectangle2D)new Float(this.width));
        }
        if (this.maxHeight > 0.0f) {
            hashMap.put(ImageTranscoder.KEY_MAX_HEIGHT, (Rectangle2D)new Float(this.maxHeight));
        }
        if (this.maxWidth > 0.0f) {
            hashMap.put(ImageTranscoder.KEY_MAX_WIDTH, (Rectangle2D)new Float(this.maxWidth));
        }
        if (this.mediaType != null) {
            hashMap.put(ImageTranscoder.KEY_MEDIA, (Rectangle2D)this.mediaType);
        }
        if (this.defaultFontFamily != null) {
            hashMap.put(ImageTranscoder.KEY_DEFAULT_FONT_FAMILY, (Rectangle2D)this.defaultFontFamily);
        }
        if (this.alternateStylesheet != null) {
            hashMap.put(ImageTranscoder.KEY_ALTERNATE_STYLESHEET, (Rectangle2D)this.alternateStylesheet);
        }
        if (this.userStylesheet != null) {
            String s;
            try {
                s = new ParsedURL(new File(System.getProperty("user.dir")).toURL(), this.userStylesheet).toString();
            }
            catch (Exception ex) {
                s = this.userStylesheet;
            }
            hashMap.put(ImageTranscoder.KEY_USER_STYLESHEET_URI, (Rectangle2D)s);
        }
        if (this.language != null) {
            hashMap.put(ImageTranscoder.KEY_LANGUAGE, (Rectangle2D)this.language);
        }
        if (this.pixelUnitToMillimeter > 0.0f) {
            hashMap.put(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, (Rectangle2D)new Float(this.pixelUnitToMillimeter));
        }
        if (this.validate) {
            hashMap.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, (Rectangle2D)Boolean.TRUE);
        }
        if (this.executeOnload) {
            hashMap.put(ImageTranscoder.KEY_EXECUTE_ONLOAD, (Rectangle2D)Boolean.TRUE);
        }
        if (!Float.isNaN(this.snapshotTime)) {
            hashMap.put(ImageTranscoder.KEY_SNAPSHOT_TIME, (Rectangle2D)new Float(this.snapshotTime));
        }
        if (this.allowedScriptTypes != null) {
            hashMap.put(ImageTranscoder.KEY_ALLOWED_SCRIPT_TYPES, (Rectangle2D)this.allowedScriptTypes);
        }
        if (!this.constrainScriptOrigin) {
            hashMap.put(ImageTranscoder.KEY_CONSTRAIN_SCRIPT_ORIGIN, (Rectangle2D)Boolean.FALSE);
        }
        return hashMap;
    }
    
    protected void transcode(final SVGConverterSource svgConverterSource, final File file, final Transcoder transcoder) throws SVGConverterException {
        if (!this.controller.proceedWithSourceTranscoding(svgConverterSource, file)) {
            return;
        }
        TranscoderInput transcoderInput;
        FileOutputStream fileOutputStream;
        TranscoderOutput transcoderOutput;
        try {
            if (svgConverterSource.isSameAs(file.getPath())) {
                throw new SVGConverterException("SVGConverter.error.source.same.as.destination", true);
            }
            if (!svgConverterSource.isReadable()) {
                throw new SVGConverterException("SVGConverter.error.cannot.read.source", new Object[] { svgConverterSource.getName() });
            }
            try {
                svgConverterSource.openStream().close();
            }
            catch (IOException ex) {
                throw new SVGConverterException("SVGConverter.error.cannot.open.source", new Object[] { svgConverterSource.getName(), ex.toString() });
            }
            transcoderInput = new TranscoderInput(svgConverterSource.getURI());
            if (!this.isWriteable(file)) {
                throw new SVGConverterException("SVGConverter.error.output.not.writeable", new Object[] { file.getName() });
            }
            try {
                fileOutputStream = new FileOutputStream(file);
            }
            catch (FileNotFoundException ex4) {
                throw new SVGConverterException("SVGConverter.error.cannot.open.output.file", new Object[] { file.getName() });
            }
            transcoderOutput = new TranscoderOutput(fileOutputStream);
        }
        catch (SVGConverterException ex2) {
            if (this.controller.proceedOnSourceTranscodingFailure(svgConverterSource, file, ex2.getErrorCode())) {
                return;
            }
            throw ex2;
        }
        boolean b = false;
        try {
            transcoder.transcode(transcoderInput, transcoderOutput);
            b = true;
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            catch (IOException ex5) {}
            if (!this.controller.proceedOnSourceTranscodingFailure(svgConverterSource, file, "SVGConverter.error.while.rasterizing.file")) {
                throw new SVGConverterException("SVGConverter.error.while.rasterizing.file", new Object[] { file.getName(), ex3.getMessage() });
            }
        }
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (IOException ex6) {
            return;
        }
        if (b) {
            this.controller.onSourceTranscodingSuccess(svgConverterSource, file);
        }
    }
    
    protected String getDestinationFile(final String str) {
        final String extension = this.destinationType.getExtension();
        final int lastIndex = str.lastIndexOf(46);
        String s;
        if (lastIndex != -1) {
            s = str.substring(0, lastIndex) + extension;
        }
        else {
            s = str + extension;
        }
        return s;
    }
    
    protected void createOutputDir(final File file) throws SVGConverterException {
        boolean b = true;
        if (file.getParent() != null) {
            final File file2 = new File(file.getParent());
            if (!file2.exists()) {
                b = file2.mkdirs();
            }
            else if (!file2.isDirectory()) {
                b = file2.mkdirs();
            }
        }
        if (!b) {
            throw new SVGConverterException("SVGConverter.error.unable.to.create.output.dir");
        }
    }
    
    protected boolean isWriteable(final File file) {
        if (file.exists()) {
            if (!file.canWrite()) {
                return false;
            }
        }
        else {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                return false;
            }
        }
        return true;
    }
    
    static {
        DEFAULT_RESULT_TYPE = DestinationType.PNG;
    }
    
    public static class SVGFileFilter implements FileFilter
    {
        public static final String SVG_EXTENSION = ".svg";
        
        public boolean accept(final File file) {
            return file != null && file.getName().toLowerCase().endsWith(".svg");
        }
    }
}
