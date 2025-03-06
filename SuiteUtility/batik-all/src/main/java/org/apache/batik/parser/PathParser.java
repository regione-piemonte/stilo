// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class PathParser extends NumberParser
{
    protected PathHandler pathHandler;
    
    public PathParser() {
        this.pathHandler = DefaultPathHandler.INSTANCE;
    }
    
    public void setPathHandler(final PathHandler pathHandler) {
        this.pathHandler = pathHandler;
    }
    
    public PathHandler getPathHandler() {
        return this.pathHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.pathHandler.startPath();
        this.current = this.reader.read();
    Label_0020_Outer:
        while (true) {
            while (true) {
                try {
                Label_0399:
                    while (true) {
                        switch (this.current) {
                            case 9:
                            case 10:
                            case 13:
                            case 32: {
                                this.current = this.reader.read();
                                continue Label_0020_Outer;
                            }
                            case 90:
                            case 122: {
                                this.current = this.reader.read();
                                this.pathHandler.closePath();
                                continue Label_0020_Outer;
                            }
                            case 109: {
                                this.parsem();
                                continue Label_0020_Outer;
                            }
                            case 77: {
                                this.parseM();
                                continue Label_0020_Outer;
                            }
                            case 108: {
                                this.parsel();
                                continue Label_0020_Outer;
                            }
                            case 76: {
                                this.parseL();
                                continue Label_0020_Outer;
                            }
                            case 104: {
                                this.parseh();
                                continue Label_0020_Outer;
                            }
                            case 72: {
                                this.parseH();
                                continue Label_0020_Outer;
                            }
                            case 118: {
                                this.parsev();
                                continue Label_0020_Outer;
                            }
                            case 86: {
                                this.parseV();
                                continue Label_0020_Outer;
                            }
                            case 99: {
                                this.parsec();
                                continue Label_0020_Outer;
                            }
                            case 67: {
                                this.parseC();
                                continue Label_0020_Outer;
                            }
                            case 113: {
                                this.parseq();
                                continue Label_0020_Outer;
                            }
                            case 81: {
                                this.parseQ();
                                continue Label_0020_Outer;
                            }
                            case 115: {
                                this.parses();
                                continue Label_0020_Outer;
                            }
                            case 83: {
                                this.parseS();
                                continue Label_0020_Outer;
                            }
                            case 116: {
                                this.parset();
                                continue Label_0020_Outer;
                            }
                            case 84: {
                                this.parseT();
                                continue Label_0020_Outer;
                            }
                            case 97: {
                                this.parsea();
                                continue Label_0020_Outer;
                            }
                            case 65: {
                                this.parseA();
                                continue Label_0020_Outer;
                            }
                            case -1: {
                                break Label_0399;
                            }
                            default: {
                                this.reportUnexpected(this.current);
                                continue Label_0020_Outer;
                            }
                        }
                    }
                    break;
                }
                catch (ParseException ex) {
                    this.errorHandler.error(ex);
                    this.skipSubPath();
                    continue Label_0020_Outer;
                }
                continue;
            }
        }
        this.skipSpaces();
        if (this.current != -1) {
            this.reportError("end.of.stream.expected", new Object[] { new Integer(this.current) });
        }
        this.pathHandler.endPath();
    }
    
    protected void parsem() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        final float float1 = this.parseFloat();
        this.skipCommaSpaces();
        this.pathHandler.movetoRel(float1, this.parseFloat());
        this._parsel(this.skipCommaSpaces2());
    }
    
    protected void parseM() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        final float float1 = this.parseFloat();
        this.skipCommaSpaces();
        this.pathHandler.movetoAbs(float1, this.parseFloat());
        this._parseL(this.skipCommaSpaces2());
    }
    
    protected void parsel() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        this._parsel(true);
    }
    
    protected void _parsel(boolean skipCommaSpaces2) throws ParseException, IOException {
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.linetoRel(float1, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseL() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        this._parseL(true);
    }
    
    protected void _parseL(boolean skipCommaSpaces2) throws ParseException, IOException {
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.linetoAbs(float1, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseh() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    this.pathHandler.linetoHorizontalRel(this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseH() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    this.pathHandler.linetoHorizontalAbs(this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parsev() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    this.pathHandler.linetoVerticalRel(this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseV() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    this.pathHandler.linetoVerticalAbs(this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parsec() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
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
                    this.pathHandler.curvetoCubicRel(float1, float2, float3, float4, float5, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseC() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
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
                    this.pathHandler.curvetoCubicAbs(float1, float2, float3, float4, float5, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseq() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float2 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float3 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.curvetoQuadraticRel(float1, float2, float3, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseQ() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float2 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float3 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.curvetoQuadraticAbs(float1, float2, float3, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parses() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float2 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float3 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.curvetoCubicSmoothRel(float1, float2, float3, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseS() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float2 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float3 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.curvetoCubicSmoothAbs(float1, float2, float3, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parset() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.curvetoQuadraticSmoothRel(float1, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseT() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.curvetoQuadraticSmoothAbs(float1, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parsea() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float2 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float3 = this.parseFloat();
                    this.skipCommaSpaces();
                    boolean b = false;
                    switch (this.current) {
                        default: {
                            this.reportUnexpected(this.current);
                            return;
                        }
                        case 48: {
                            b = false;
                            break;
                        }
                        case 49: {
                            b = true;
                            break;
                        }
                    }
                    this.current = this.reader.read();
                    this.skipCommaSpaces();
                    boolean b2 = false;
                    switch (this.current) {
                        default: {
                            this.reportUnexpected(this.current);
                            return;
                        }
                        case 48: {
                            b2 = false;
                            break;
                        }
                        case 49: {
                            b2 = true;
                            break;
                        }
                    }
                    this.current = this.reader.read();
                    this.skipCommaSpaces();
                    final float float4 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.arcRel(float1, float2, float3, b, b2, float4, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void parseA() throws ParseException, IOException {
        this.current = this.reader.read();
        this.skipSpaces();
        boolean skipCommaSpaces2 = true;
        while (true) {
            switch (this.current) {
                default: {
                    if (skipCommaSpaces2) {
                        this.reportUnexpected(this.current);
                    }
                }
                case 43:
                case 45:
                case 46:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final float float1 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float2 = this.parseFloat();
                    this.skipCommaSpaces();
                    final float float3 = this.parseFloat();
                    this.skipCommaSpaces();
                    boolean b = false;
                    switch (this.current) {
                        default: {
                            this.reportUnexpected(this.current);
                            return;
                        }
                        case 48: {
                            b = false;
                            break;
                        }
                        case 49: {
                            b = true;
                            break;
                        }
                    }
                    this.current = this.reader.read();
                    this.skipCommaSpaces();
                    boolean b2 = false;
                    switch (this.current) {
                        default: {
                            this.reportUnexpected(this.current);
                            return;
                        }
                        case 48: {
                            b2 = false;
                            break;
                        }
                        case 49: {
                            b2 = true;
                            break;
                        }
                    }
                    this.current = this.reader.read();
                    this.skipCommaSpaces();
                    final float float4 = this.parseFloat();
                    this.skipCommaSpaces();
                    this.pathHandler.arcAbs(float1, float2, float3, b, b2, float4, this.parseFloat());
                    skipCommaSpaces2 = this.skipCommaSpaces2();
                    continue;
                }
            }
        }
    }
    
    protected void skipSubPath() throws ParseException, IOException {
    Label_0040:
        while (true) {
            switch (this.current) {
                case -1:
                case 77:
                case 109: {
                    break Label_0040;
                }
                default: {
                    this.current = this.reader.read();
                    continue;
                }
            }
        }
    }
    
    protected void reportUnexpected(final int n) throws ParseException, IOException {
        this.reportUnexpectedCharacterError(this.current);
        this.skipSubPath();
    }
    
    protected boolean skipCommaSpaces2() throws IOException {
        while (true) {
            switch (this.current) {
                default: {
                    if (this.current != 44) {
                        return false;
                    }
                    while (true) {
                        switch (this.current = this.reader.read()) {
                            default: {
                                return true;
                            }
                            case 9:
                            case 10:
                            case 13:
                            case 32: {
                                continue;
                            }
                        }
                    }
                    break;
                }
                case 9:
                case 10:
                case 13:
                case 32: {
                    this.current = this.reader.read();
                    continue;
                }
            }
        }
    }
}
