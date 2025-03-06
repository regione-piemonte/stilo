// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.spi;

import java.util.Iterator;
import org.apache.batik.util.Service;
import java.util.HashMap;
import java.util.Map;

public class ImageWriterRegistry
{
    private static volatile ImageWriterRegistry instance;
    private final Map imageWriterMap;
    static /* synthetic */ Class class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry;
    
    private ImageWriterRegistry() {
        this.imageWriterMap = new HashMap();
        this.setup();
    }
    
    public static ImageWriterRegistry getInstance() {
        if (ImageWriterRegistry.instance == null) {
            Class class$;
            Class class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry;
            if (ImageWriterRegistry.class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry == null) {
                class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry = (ImageWriterRegistry.class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry = (class$ = class$("org.apache.batik.ext.awt.image.spi.ImageWriterRegistry")));
            }
            else {
                class$ = (class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry = ImageWriterRegistry.class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry);
            }
            final Class clazz = class$org$apache$batik$ext$awt$image$spi$ImageWriterRegistry;
            synchronized (class$) {
                if (ImageWriterRegistry.instance == null) {
                    ImageWriterRegistry.instance = new ImageWriterRegistry();
                }
            }
        }
        return ImageWriterRegistry.instance;
    }
    
    private void setup() {
        final Iterator providers = Service.providers(ImageWriter.class);
        while (providers.hasNext()) {
            this.register(providers.next());
        }
    }
    
    public void register(final ImageWriter imageWriter) {
        this.imageWriterMap.put(imageWriter.getMIMEType(), imageWriter);
    }
    
    public ImageWriter getWriterFor(final String s) {
        return this.imageWriterMap.get(s);
    }
    
    static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
}
