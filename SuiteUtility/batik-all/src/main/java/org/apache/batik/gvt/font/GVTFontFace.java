// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import org.apache.batik.util.SVGConstants;

public class GVTFontFace implements SVGConstants
{
    protected String familyName;
    protected float unitsPerEm;
    protected String fontWeight;
    protected String fontStyle;
    protected String fontVariant;
    protected String fontStretch;
    protected float slope;
    protected String panose1;
    protected float ascent;
    protected float descent;
    protected float strikethroughPosition;
    protected float strikethroughThickness;
    protected float underlinePosition;
    protected float underlineThickness;
    protected float overlinePosition;
    protected float overlineThickness;
    
    public GVTFontFace(final String familyName, final float unitsPerEm, final String fontWeight, final String fontStyle, final String fontVariant, final String fontStretch, final float slope, final String panose1, final float ascent, final float descent, final float strikethroughPosition, final float strikethroughThickness, final float underlinePosition, final float underlineThickness, final float overlinePosition, final float overlineThickness) {
        this.familyName = familyName;
        this.unitsPerEm = unitsPerEm;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.fontVariant = fontVariant;
        this.fontStretch = fontStretch;
        this.slope = slope;
        this.panose1 = panose1;
        this.ascent = ascent;
        this.descent = descent;
        this.strikethroughPosition = strikethroughPosition;
        this.strikethroughThickness = strikethroughThickness;
        this.underlinePosition = underlinePosition;
        this.underlineThickness = underlineThickness;
        this.overlinePosition = overlinePosition;
        this.overlineThickness = overlineThickness;
    }
    
    public GVTFontFace(final String s) {
        this(s, 1000.0f, "all", "all", "normal", "normal", 0.0f, "0 0 0 0 0 0 0 0 0 0", 800.0f, 200.0f, 300.0f, 50.0f, -75.0f, 50.0f, 800.0f, 50.0f);
    }
    
    public String getFamilyName() {
        return this.familyName;
    }
    
    public boolean hasFamilyName(final String s) {
        final String familyName = this.familyName;
        if (familyName.length() < s.length()) {
            return false;
        }
        final String lowerCase = familyName.toLowerCase();
        final int index = lowerCase.indexOf(s.toLowerCase());
        if (index == -1) {
            return false;
        }
        Label_0337: {
            if (lowerCase.length() > s.length()) {
                boolean b = false;
                Label_0184: {
                    if (index > 0) {
                        switch (lowerCase.charAt(index - 1)) {
                            default: {
                                return false;
                            }
                            case ',': {
                                break;
                            }
                            case ' ': {
                                int i = index - 2;
                                while (i >= 0) {
                                    switch (lowerCase.charAt(i)) {
                                        default: {
                                            return false;
                                        }
                                        case ' ': {
                                            --i;
                                            continue;
                                        }
                                        case '\"':
                                        case '\'': {
                                            b = true;
                                            break Label_0184;
                                        }
                                    }
                                }
                                break;
                            }
                            case '\"':
                            case '\'': {
                                b = true;
                                break;
                            }
                        }
                    }
                }
                if (index + s.length() < lowerCase.length()) {
                    switch (lowerCase.charAt(index + s.length())) {
                        default: {
                            return false;
                        }
                        case '\"':
                        case '\'': {
                            if (!b) {
                                return false;
                            }
                            break;
                        }
                        case ',': {
                            break;
                        }
                        case ' ': {
                            int j = index + s.length() + 1;
                            while (j < lowerCase.length()) {
                                switch (lowerCase.charAt(j)) {
                                    default: {
                                        return false;
                                    }
                                    case ' ': {
                                        ++j;
                                        continue;
                                    }
                                    case '\"':
                                    case '\'': {
                                        if (!b) {
                                            return false;
                                        }
                                        break Label_0337;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public String getFontWeight() {
        return this.fontWeight;
    }
    
    public String getFontStyle() {
        return this.fontStyle;
    }
    
    public float getUnitsPerEm() {
        return this.unitsPerEm;
    }
    
    public float getAscent() {
        return this.ascent;
    }
    
    public float getDescent() {
        return this.descent;
    }
    
    public float getStrikethroughPosition() {
        return this.strikethroughPosition;
    }
    
    public float getStrikethroughThickness() {
        return this.strikethroughThickness;
    }
    
    public float getUnderlinePosition() {
        return this.underlinePosition;
    }
    
    public float getUnderlineThickness() {
        return this.underlineThickness;
    }
    
    public float getOverlinePosition() {
        return this.overlinePosition;
    }
    
    public float getOverlineThickness() {
        return this.overlineThickness;
    }
}
