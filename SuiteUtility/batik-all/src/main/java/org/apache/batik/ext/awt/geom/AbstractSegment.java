// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.awt.geom.Point2D;
import java.util.Arrays;

public abstract class AbstractSegment implements Segment
{
    static final double eps = 3.552713678800501E-15;
    static final double tol = 1.4210854715202004E-14;
    
    protected abstract int findRoots(final double p0, final double[] p1);
    
    public SplitResults split(final double n) {
        final double[] a = { 0.0, 0.0, 0.0 };
        final int roots = this.findRoots(n, a);
        if (roots == 0) {
            return null;
        }
        Arrays.sort(a, 0, roots);
        final double[] array = new double[roots + 2];
        int n2 = 0;
        array[n2++] = 0.0;
        for (final double n3 : a) {
            if (n3 > 0.0) {
                if (n3 >= 1.0) {
                    break;
                }
                if (array[n2 - 1] != n3) {
                    array[n2++] = n3;
                }
            }
        }
        array[n2++] = 1.0;
        if (n2 == 2) {
            return null;
        }
        final Segment[] array2 = new Segment[n2];
        double n4 = 0.0;
        int n5 = 0;
        boolean b = false;
        int n6 = 0;
        for (int j = 1; j < n2; ++j) {
            array2[n5] = this.getSegment(array[j - 1], array[j]);
            final Point2D.Double eval = array2[n5].eval(0.5);
            if (n5 == 0) {
                ++n5;
                n6 = ((b = (eval.y < n)) ? 1 : 0);
            }
            else {
                final boolean b2 = eval.y < n;
                if (n6 == (b2 ? 1 : 0)) {
                    array2[n5 - 1] = this.getSegment(n4, array[j]);
                }
                else {
                    ++n5;
                    n4 = array[j - 1];
                    n6 = (b2 ? 1 : 0);
                }
            }
        }
        if (n5 == 1) {
            return null;
        }
        Segment[] array3;
        Segment[] array4;
        if (b) {
            array3 = new Segment[(n5 + 1) / 2];
            array4 = new Segment[n5 / 2];
        }
        else {
            array3 = new Segment[n5 / 2];
            array4 = new Segment[(n5 + 1) / 2];
        }
        int n7 = 0;
        int n8 = 0;
        for (int k = 0; k < n5; ++k) {
            if (b) {
                array3[n7++] = array2[k];
            }
            else {
                array4[n8++] = array2[k];
            }
            b = !b;
        }
        return new SplitResults(array4, array3);
    }
    
    public Segment splitBefore(final double n) {
        return this.getSegment(0.0, n);
    }
    
    public Segment splitAfter(final double n) {
        return this.getSegment(n, 1.0);
    }
    
    public static int solveLine(final double n, final double n2, final double[] array) {
        if (n != 0.0) {
            array[0] = -n2 / n;
            return 1;
        }
        if (n2 != 0.0) {
            return 0;
        }
        array[0] = 0.0;
        return 1;
    }
    
    public static int solveQuad(final double n, final double n2, final double n3, final double[] array) {
        if (n == 0.0) {
            return solveLine(n2, n3, array);
        }
        final double n4 = n2 * n2 - 4.0 * n * n3;
        if (Math.abs(n4) <= 1.4210854715202004E-14 * n2 * n2) {
            array[0] = -n2 / (2.0 * n);
            return 1;
        }
        if (n4 < 0.0) {
            return 0;
        }
        final double n5 = -(n2 + matchSign(Math.sqrt(n4), n2));
        array[0] = 2.0 * n3 / n5;
        array[1] = n5 / (2.0 * n);
        return 2;
    }
    
    public static double matchSign(final double n, final double n2) {
        if (n2 < 0.0) {
            return (n < 0.0) ? n : (-n);
        }
        return (n > 0.0) ? n : (-n);
    }
    
    public static int solveCubic(final double n, final double n2, final double n3, final double n4, final double[] array) {
        final double[] array2 = { 0.0, 0.0 };
        final int solveQuad = solveQuad(3.0 * n, 2.0 * n2, n3, array2);
        final double[] array3 = { 0.0, 0.0, 0.0, 0.0 };
        final double[] array4 = { 0.0, 0.0, 0.0, 0.0 };
        int n5 = 0;
        array3[n5] = n4;
        array4[n5++] = 0.0;
        switch (solveQuad) {
            case 1: {
                final double n6 = array2[0];
                if (n6 > 0.0 && n6 < 1.0) {
                    array3[n5] = ((n * n6 + n2) * n6 + n3) * n6 + n4;
                    array4[n5++] = n6;
                    break;
                }
                break;
            }
            case 2: {
                if (array2[0] > array2[1]) {
                    final double n7 = array2[0];
                    array2[0] = array2[1];
                    array2[1] = n7;
                }
                final double n8 = array2[0];
                if (n8 > 0.0 && n8 < 1.0) {
                    array3[n5] = ((n * n8 + n2) * n8 + n3) * n8 + n4;
                    array4[n5++] = n8;
                }
                final double n9 = array2[1];
                if (n9 > 0.0 && n9 < 1.0) {
                    array3[n5] = ((n * n9 + n2) * n9 + n3) * n9 + n4;
                    array4[n5++] = n9;
                    break;
                }
                break;
            }
        }
        array3[n5] = n + n2 + n3 + n4;
        array4[n5++] = 1.0;
        int n10 = 0;
        for (int i = 0; i < n5 - 1; ++i) {
            double n11 = array3[i];
            double n12 = array4[i];
            double n13 = array3[i + 1];
            double n14 = array4[i + 1];
            if (n11 >= 0.0 || n13 >= 0.0) {
                if (n11 <= 0.0 || n13 <= 0.0) {
                    if (n11 > n13) {
                        final double n15 = n11;
                        n11 = n13;
                        n13 = n15;
                        final double n16 = n12;
                        n12 = n14;
                        n14 = n16;
                    }
                    if (-n11 < 1.4210854715202004E-14 * n13) {
                        array[n10++] = n12;
                    }
                    else if (n13 < -1.4210854715202004E-14 * n11) {
                        array[n10++] = n14;
                        ++i;
                    }
                    else {
                        final double n17 = 1.4210854715202004E-14 * (n13 - n11);
                        int j;
                        for (j = 0; j < 20; ++j) {
                            final double n18 = n12 + (Math.abs(n11 / (n13 - n11)) * 99.0 + 0.5) * (n14 - n12) / 100.0;
                            final double a = ((n * n18 + n2) * n18 + n3) * n18 + n4;
                            if (Math.abs(a) < n17) {
                                array[n10++] = n18;
                                break;
                            }
                            if (a < 0.0) {
                                n12 = n18;
                                n11 = a;
                            }
                            else {
                                n14 = n18;
                                n13 = a;
                            }
                        }
                        if (j == 20) {
                            array[n10++] = (n12 + n14) / 2.0;
                        }
                    }
                }
            }
        }
        return n10;
    }
}
