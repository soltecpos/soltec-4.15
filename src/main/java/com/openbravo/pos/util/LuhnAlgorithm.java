/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.util;

import com.openbravo.pos.util.StringUtils;

public class LuhnAlgorithm {
    private LuhnAlgorithm() {
    }

    public static boolean checkCC(String cardNumber) {
        int sum = 0;
        int flip = 0;
        if (!StringUtils.isNumber(cardNumber)) {
            return false;
        }
        for (int i = cardNumber.length() - 1; i >= 0; --i) {
            int k = Character.digit(cardNumber.charAt(i), 10);
            if (++flip % 2 == 0 && (k *= 2) > 9) {
                k -= 9;
            }
            sum += k;
        }
        return sum % 10 == 0;
    }
}

