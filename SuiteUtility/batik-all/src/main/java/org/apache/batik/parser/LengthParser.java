// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public class LengthParser extends AbstractParser
{
    protected LengthHandler lengthHandler;
    
    public LengthParser() {
        this.lengthHandler = DefaultLengthHandler.INSTANCE;
    }
    
    public void setLengthHandler(final LengthHandler lengthHandler) {
        this.lengthHandler = lengthHandler;
    }
    
    public LengthHandler getLengthHandler() {
        return this.lengthHandler;
    }
    
    protected void doParse() throws ParseException, IOException {
        this.lengthHandler.startLength();
        this.current = this.reader.read();
        this.skipSpaces();
        this.parseLength();
        this.skipSpaces();
        if (this.current != -1) {
            this.reportError("end.of.stream.expected", new Object[] { new Integer(this.current) });
        }
        this.lengthHandler.endLength();
    }
    
    protected void parseLength() throws ParseException, IOException {
        int n = 0;
        int n2 = 0;
        boolean b = true;
        boolean b2 = false;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        boolean b3 = true;
        int n6 = 0;
        switch (this.current) {
            case 45: {
                b = false;
            }
            case 43: {
                this.current = this.reader.read();
                break;
            }
        }
        Label_0229: {
            switch (this.current) {
                default: {
                    this.reportUnexpectedCharacterError(this.current);
                    return;
                }
                case 46: {
                    break;
                }
                case 48: {
                    b2 = true;
                    while (true) {
                        switch (this.current = this.reader.read()) {
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57: {
                                break Label_0229;
                            }
                            default: {
                                break Label_0229;
                            }
                            case 48: {
                                continue;
                            }
                        }
                    }
                    break;
                }
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    b2 = true;
                    while (true) {
                        if (n2 < 9) {
                            ++n2;
                            n = n * 10 + (this.current - 48);
                        }
                        else {
                            ++n5;
                        }
                        switch (this.current = this.reader.read()) {
                            default: {
                                break Label_0229;
                            }
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
                                continue;
                            }
                        }
                    }
                    break;
                }
            }
        }
        Label_0666: {
            if (this.current == 46) {
                Label_0565: {
                    switch (this.current = this.reader.read()) {
                        default: {
                            if (!b2) {
                                this.reportUnexpectedCharacterError(this.current);
                                return;
                            }
                            break;
                        }
                        case 48: {
                            if (n2 != 0) {
                                break Label_0565;
                            }
                            while (true) {
                                this.current = this.reader.read();
                                --n5;
                                switch (this.current) {
                                    case 49:
                                    case 50:
                                    case 51:
                                    case 52:
                                    case 53:
                                    case 54:
                                    case 55:
                                    case 56:
                                    case 57: {
                                        break Label_0565;
                                    }
                                    default: {
                                        break Label_0666;
                                    }
                                    case 48: {
                                        continue;
                                    }
                                }
                            }
                            break;
                        }
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57: {
                            while (true) {
                                if (n2 < 9) {
                                    ++n2;
                                    n = n * 10 + (this.current - 48);
                                    --n5;
                                }
                                switch (this.current = this.reader.read()) {
                                    default: {
                                        break Label_0666;
                                    }
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
                                        continue;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        boolean b4 = false;
        Label_1206: {
            switch (this.current) {
                case 101: {
                    b4 = true;
                }
                case 69: {
                    Label_0969: {
                        switch (this.current = this.reader.read()) {
                            default: {
                                this.reportUnexpectedCharacterError(this.current);
                                return;
                            }
                            case 109: {
                                if (!b4) {
                                    this.reportUnexpectedCharacterError(this.current);
                                    return;
                                }
                                n6 = 1;
                                break Label_1206;
                            }
                            case 120: {
                                if (!b4) {
                                    this.reportUnexpectedCharacterError(this.current);
                                    return;
                                }
                                n6 = 2;
                                break Label_1206;
                            }
                            case 45: {
                                b3 = false;
                            }
                            case 43: {
                                switch (this.current = this.reader.read()) {
                                    default: {
                                        this.reportUnexpectedCharacterError(this.current);
                                        return;
                                    }
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
                                        break Label_0969;
                                    }
                                }
                                break;
                            }
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
                                Label_1105: {
                                    switch (this.current) {
                                        case 48: {
                                            while (true) {
                                                switch (this.current = this.reader.read()) {
                                                    case 49:
                                                    case 50:
                                                    case 51:
                                                    case 52:
                                                    case 53:
                                                    case 54:
                                                    case 55:
                                                    case 56:
                                                    case 57: {
                                                        break Label_1105;
                                                    }
                                                    default: {
                                                        break Label_1206;
                                                    }
                                                    case 48: {
                                                        continue;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                        case 49:
                                        case 50:
                                        case 51:
                                        case 52:
                                        case 53:
                                        case 54:
                                        case 55:
                                        case 56:
                                        case 57: {
                                            while (true) {
                                                if (n4 < 3) {
                                                    ++n4;
                                                    n3 = n3 * 10 + (this.current - 48);
                                                }
                                                switch (this.current = this.reader.read()) {
                                                    default: {
                                                        break Label_1206;
                                                    }
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
                                                        continue;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        if (!b3) {
            n3 = -n3;
        }
        final int n7 = n3 + n5;
        if (!b) {
            n = -n;
        }
        this.lengthHandler.lengthValue(NumberParser.buildFloat(n, n7));
        switch (n6) {
            case 1: {
                this.lengthHandler.em();
                this.current = this.reader.read();
            }
            case 2: {
                this.lengthHandler.ex();
                this.current = this.reader.read();
            }
            default: {
                Label_1792: {
                    switch (this.current) {
                        case 101: {
                            switch (this.current = this.reader.read()) {
                                case 109: {
                                    this.lengthHandler.em();
                                    this.current = this.reader.read();
                                    break Label_1792;
                                }
                                case 120: {
                                    this.lengthHandler.ex();
                                    this.current = this.reader.read();
                                    break Label_1792;
                                }
                                default: {
                                    this.reportUnexpectedCharacterError(this.current);
                                    break Label_1792;
                                }
                            }
                            break;
                        }
                        case 112: {
                            switch (this.current = this.reader.read()) {
                                case 99: {
                                    this.lengthHandler.pc();
                                    this.current = this.reader.read();
                                    break Label_1792;
                                }
                                case 116: {
                                    this.lengthHandler.pt();
                                    this.current = this.reader.read();
                                    break Label_1792;
                                }
                                case 120: {
                                    this.lengthHandler.px();
                                    this.current = this.reader.read();
                                    break Label_1792;
                                }
                                default: {
                                    this.reportUnexpectedCharacterError(this.current);
                                    break Label_1792;
                                }
                            }
                            break;
                        }
                        case 105: {
                            this.current = this.reader.read();
                            if (this.current != 110) {
                                this.reportCharacterExpectedError('n', this.current);
                                break;
                            }
                            this.lengthHandler.in();
                            this.current = this.reader.read();
                            break;
                        }
                        case 99: {
                            this.current = this.reader.read();
                            if (this.current != 109) {
                                this.reportCharacterExpectedError('m', this.current);
                                break;
                            }
                            this.lengthHandler.cm();
                            this.current = this.reader.read();
                            break;
                        }
                        case 109: {
                            this.current = this.reader.read();
                            if (this.current != 109) {
                                this.reportCharacterExpectedError('m', this.current);
                                break;
                            }
                            this.lengthHandler.mm();
                            this.current = this.reader.read();
                            break;
                        }
                        case 37: {
                            this.lengthHandler.percentage();
                            this.current = this.reader.read();
                            break;
                        }
                    }
                }
            }
        }
    }
}
