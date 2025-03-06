package org.jpedal.color;

import java.awt.image.BufferedImage;

import org.jpedal.io.ObjectStore;
import org.jpedal.objects.GraphicsState;
import org.jpedal.render.DynamicVectorRenderer;
import org.jpedal.render.T3Display;
import org.jpedal.render.T3Renderer;

public class PatternDisplay extends T3Display implements T3Renderer 
{


    private BufferedImage lastImg;
    
    private int imageCount=0;

    public PatternDisplay(int i, boolean b, int j, ObjectStore localStore)
    {
        super(i,b,j,localStore);
        
        type = DynamicVectorRenderer.CREATE_PATTERN;
        
    }

    /* save image in array to draw */
    public int drawImage(int pageNumber,BufferedImage image,
                               GraphicsState currentGraphicsState,
                               boolean alreadyCached,String name, int optionsApplied, int previousUse) {

        lastImg=image;

        imageCount++;

        return super.drawImage(pageNumber, image, currentGraphicsState, alreadyCached, name,  optionsApplied, previousUse);
    }


    public BufferedImage getSingleImagePattern(){
        if(imageCount!=1)
            return null;
        else
            return lastImg;
    }

}
