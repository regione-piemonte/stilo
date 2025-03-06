// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.pdfbox.jbig2.image;

import org.apache.pdfbox.jbig2.util.Utils;

final class Weighttab
{
    final int[] weights;
    final int i0;
    final int i1;
    
    public Weighttab(final ParameterizedFilter parameterizedFilter, final int n, final double n2, final int b, final int b2, final boolean b3) {
        int max = Math.max(parameterizedFilter.minIndex(n2), b);
        int n3 = Math.min(parameterizedFilter.maxIndex(n2), b2);
        double n4 = 0.0;
        for (int i = max; i <= n3; ++i) {
            n4 += parameterizedFilter.eval(n2, i);
        }
        final double n5 = (n4 == 0.0) ? n : (n / n4);
        if (b3) {
            int n6 = b3 ? 1 : 0;
            int a = 0;
            for (int j = max; j <= n3; ++j) {
                final int n7 = (int)Math.floor(Utils.clamp(n5 * parameterizedFilter.eval(n2, j), -32768.0, 32767.0) + 0.5);
                if (n6 != 0 && n7 == 0) {
                    ++max;
                }
                else {
                    n6 = 0;
                    if (n7 != 0) {
                        a = j;
                    }
                }
            }
            n3 = Math.max(a, max);
        }
        this.weights = new int[n3 - max + 1];
        int n8 = 0;
        int n9 = 0;
        for (int k = max; k <= n3; ++k) {
            final int n10 = (int)Math.floor(Utils.clamp(n5 * parameterizedFilter.eval(n2, k), -32768.0, 32767.0) + 0.5);
            this.weights[n9++] = n10;
            n8 += n10;
        }
        if (n8 == 0) {
            n3 = max;
            this.weights[0] = n;
        }
        else if (n8 != n) {
            int n11 = (int)(n2 + 0.5);
            if (n11 >= n3) {
                n11 = n3 - 1;
            }
            if (n11 < max) {
                n11 = max;
            }
            final int n12 = n - n8;
            final int[] weights = this.weights;
            final int n13 = n11 - max;
            weights[n13] += n12;
        }
        this.i0 = max - b;
        this.i1 = n3 - b;
    }
}
