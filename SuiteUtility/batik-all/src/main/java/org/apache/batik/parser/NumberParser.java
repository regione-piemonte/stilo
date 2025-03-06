// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import java.io.IOException;

public abstract class NumberParser extends AbstractParser
{
    private static final double[] pow10;
    
    protected float parseFloat() throws ParseException, IOException {
        int n = 0;
        int n2 = 0;
        boolean b = true;
        boolean b2 = false;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        boolean b3 = true;
        switch (this.current) {
            case 45: {
                b = false;
            }
            case 43: {
                this.current = this.reader.read();
                break;
            }
        }
        Label_0287: {
            switch (this.current) {
                default: {
                    this.reportUnexpectedCharacterError(this.current);
                    return 0.0f;
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
                                break Label_0287;
                            }
                            case 46:
                            case 69:
                            case 101: {
                                break Label_0287;
                            }
                            default: {
                                return 0.0f;
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
                                break Label_0287;
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
        Label_0730: {
            if (this.current == 46) {
                Label_0629: {
                    switch (this.current = this.reader.read()) {
                        default: {
                            if (!b2) {
                                this.reportUnexpectedCharacterError(this.current);
                                return 0.0f;
                            }
                            break;
                        }
                        case 48: {
                            if (n2 != 0) {
                                break Label_0629;
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
                                        break Label_0629;
                                    }
                                    default: {
                                        if (!b2) {
                                            return 0.0f;
                                        }
                                        break Label_0730;
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
                                        break Label_0730;
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
        Label_1178: {
            switch (this.current) {
                case 69:
                case 101: {
                    Label_0942: {
                        switch (this.current = this.reader.read()) {
                            default: {
                                this.reportUnexpectedCharacterError(this.current);
                                return 0.0f;
                            }
                            case 45: {
                                b3 = false;
                            }
                            case 43: {
                                switch (this.current = this.reader.read()) {
                                    default: {
                                        this.reportUnexpectedCharacterError(this.current);
                                        return 0.0f;
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
                                        break Label_0942;
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
                                Label_1077: {
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
                                                        break Label_1077;
                                                    }
                                                    default: {
                                                        break Label_1178;
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
                                                        break Label_1178;
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
        final int n6 = n3 + n5;
        if (!b) {
            n = -n;
        }
        return buildFloat(n, n6);
    }
    
    public static float buildFloat(int n, final int n2) {
        if (n2 < -125 || n == 0) {
            return 0.0f;
        }
        if (n2 >= 128) {
            return (n > 0) ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
        }
        if (n2 == 0) {
            return (float)n;
        }
        if (n >= 67108864) {
            ++n;
        }
        return (float)((n2 > 0) ? (n * NumberParser.pow10[n2]) : (n / NumberParser.pow10[-n2]));
    }
    
    static {
        pow10 = new double[128];
        for (int i = 0; i < NumberParser.pow10.length; ++i) {
            NumberParser.pow10[i] = Math.pow(10.0, i);
        }
    }
}
