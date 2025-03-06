// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class TransformListParser extends NumberParser
{
    protected TransformListHandler transformListHandler;
    
    public TransformListParser() {
        this.transformListHandler = DefaultTransformListHandler.INSTANCE;
    }
    
    public void setTransformListHandler(final TransformListHandler transformListHandler) {
        this.transformListHandler = transformListHandler;
    }
    
    public TransformListHandler getTransformListHandler() {
        return this.transformListHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.transformListHandler.startTransformList();
    Label_0009_Outer:
        while (true) {
            while (true) {
                try {
                Label_0209:
                    while (true) {
                        switch (this.current = this.reader.read()) {
                            case 9:
                            case 10:
                            case 13:
                            case 32:
                            case 44: {
                                continue Label_0009_Outer;
                            }
                            case 109: {
                                this.parseMatrix();
                                continue Label_0009_Outer;
                            }
                            case 114: {
                                this.parseRotate();
                                continue Label_0009_Outer;
                            }
                            case 116: {
                                this.parseTranslate();
                                continue Label_0009_Outer;
                            }
                            case 115: {
                                switch (this.current = this.reader.read()) {
                                    case 99: {
                                        this.parseScale();
                                        continue Label_0009_Outer;
                                    }
                                    case 107: {
                                        this.parseSkew();
                                        continue Label_0009_Outer;
                                    }
                                    default: {
                                        this.reportUnexpectedCharacterError(this.current);
                                        this.skipTransform();
                                        continue Label_0009_Outer;
                                    }
                                }
                                break;
                            }
                            case -1: {
                                break Label_0209;
                            }
                            default: {
                                this.reportUnexpectedCharacterError(this.current);
                                this.skipTransform();
                                continue Label_0009_Outer;
                            }
                        }
                    }
                    break;
                }
                catch (ParseException ex) {
                    this.errorHandler.error(ex);
                    this.skipTransform();
                    continue Label_0009_Outer;
                }
                continue;
            }
        }
        this.skipSpaces();
        if (this.current != -1) {
            this.reportError("end.of.stream.expected", new Object[] { new Integer(this.current) });
        }
        this.transformListHandler.endTransformList();
    }
    
    protected void parseMatrix() throws ParseException, IOException {
        this.current = this.reader.read();
        if (this.current != 97) {
            this.reportCharacterExpectedError('a', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 116) {
            this.reportCharacterExpectedError('t', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 114) {
            this.reportCharacterExpectedError('r', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 105) {
            this.reportCharacterExpectedError('i', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 120) {
            this.reportCharacterExpectedError('x', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        if (this.current != 40) {
            this.reportCharacterExpectedError('(', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        final float float1 = this.parseFloat();
        this.skipCommaSpaces();
        final float float2 = this.parseFloat();
        this.skipCommaSpaces();
        final float float3 = this.parseFloat();
        this.skipCommaSpaces();
        final float float4 = this.parseFloat();
        this.skipCommaSpaces();
        final float float5 = this.parseFloat();
        this.skipCommaSpaces();
        final float float6 = this.parseFloat();
        this.skipSpaces();
        if (this.current != 41) {
            this.reportCharacterExpectedError(')', this.current);
            this.skipTransform();
            return;
        }
        this.transformListHandler.matrix(float1, float2, float3, float4, float5, float6);
    }
    
    protected void parseRotate() throws ParseException, IOException {
        this.current = this.reader.read();
        if (this.current != 111) {
            this.reportCharacterExpectedError('o', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 116) {
            this.reportCharacterExpectedError('t', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 97) {
            this.reportCharacterExpectedError('a', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 116) {
            this.reportCharacterExpectedError('t', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 101) {
            this.reportCharacterExpectedError('e', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        if (this.current != 40) {
            this.reportCharacterExpectedError('(', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        final float float1 = this.parseFloat();
        this.skipSpaces();
        switch (this.current) {
            case 41: {
                this.transformListHandler.rotate(float1);
                return;
            }
            case 44: {
                this.current = this.reader.read();
                this.skipSpaces();
                break;
            }
        }
        final float float2 = this.parseFloat();
        this.skipCommaSpaces();
        final float float3 = this.parseFloat();
        this.skipSpaces();
        if (this.current != 41) {
            this.reportCharacterExpectedError(')', this.current);
            this.skipTransform();
            return;
        }
        this.transformListHandler.rotate(float1, float2, float3);
    }
    
    protected void parseTranslate() throws ParseException, IOException {
        this.current = this.reader.read();
        if (this.current != 114) {
            this.reportCharacterExpectedError('r', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 97) {
            this.reportCharacterExpectedError('a', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 110) {
            this.reportCharacterExpectedError('n', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 115) {
            this.reportCharacterExpectedError('s', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 108) {
            this.reportCharacterExpectedError('l', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 97) {
            this.reportCharacterExpectedError('a', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 116) {
            this.reportCharacterExpectedError('t', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 101) {
            this.reportCharacterExpectedError('e', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        if (this.current != 40) {
            this.reportCharacterExpectedError('(', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        final float float1 = this.parseFloat();
        this.skipSpaces();
        switch (this.current) {
            case 41: {
                this.transformListHandler.translate(float1);
                return;
            }
            case 44: {
                this.current = this.reader.read();
                this.skipSpaces();
                break;
            }
        }
        final float float2 = this.parseFloat();
        this.skipSpaces();
        if (this.current != 41) {
            this.reportCharacterExpectedError(')', this.current);
            this.skipTransform();
            return;
        }
        this.transformListHandler.translate(float1, float2);
    }
    
    protected void parseScale() throws ParseException, IOException {
        this.current = this.reader.read();
        if (this.current != 97) {
            this.reportCharacterExpectedError('a', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 108) {
            this.reportCharacterExpectedError('l', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 101) {
            this.reportCharacterExpectedError('e', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        if (this.current != 40) {
            this.reportCharacterExpectedError('(', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        this.skipSpaces();
        final float float1 = this.parseFloat();
        this.skipSpaces();
        switch (this.current) {
            case 41: {
                this.transformListHandler.scale(float1);
                return;
            }
            case 44: {
                this.current = this.reader.read();
                this.skipSpaces();
                break;
            }
        }
        final float float2 = this.parseFloat();
        this.skipSpaces();
        if (this.current != 41) {
            this.reportCharacterExpectedError(')', this.current);
            this.skipTransform();
            return;
        }
        this.transformListHandler.scale(float1, float2);
    }
    
    protected void parseSkew() throws ParseException, IOException {
        this.current = this.reader.read();
        if (this.current != 101) {
            this.reportCharacterExpectedError('e', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        if (this.current != 119) {
            this.reportCharacterExpectedError('w', this.current);
            this.skipTransform();
            return;
        }
        this.current = this.reader.read();
        boolean b = false;
        switch (this.current) {
            case 88: {
                b = true;
            }
            case 89: {
                this.current = this.reader.read();
                this.skipSpaces();
                if (this.current != 40) {
                    this.reportCharacterExpectedError('(', this.current);
                    this.skipTransform();
                    return;
                }
                this.current = this.reader.read();
                this.skipSpaces();
                final float float1 = this.parseFloat();
                this.skipSpaces();
                if (this.current != 41) {
                    this.reportCharacterExpectedError(')', this.current);
                    this.skipTransform();
                    return;
                }
                if (b) {
                    this.transformListHandler.skewX(float1);
                }
                else {
                    this.transformListHandler.skewY(float1);
                }
            }
            default: {
                this.reportCharacterExpectedError('X', this.current);
                this.skipTransform();
            }
        }
    }
    
    protected void skipTransform() throws IOException {
    Label_0046:
        do {
            switch (this.current = this.reader.read()) {
                case 41: {
                    break Label_0046;
                }
                default: {
                    continue;
                }
            }
        } while (this.current != -1);
    }
}
