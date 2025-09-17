package com.example.ufo_fi.v2.interestedpost.domain;

import com.example.ufo_fi.v2.plan.domain.Carrier;

import java.util.ArrayList;
import java.util.List;

/**
 * 관심 통신사 Bit값 변경을 위한 ENUM
 */
public enum InterestedCarriers {
    SKT(1), KT(2), LGU(4);

    private final int bit;

    InterestedCarriers(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public static int encode(List<InterestedCarriers> carriers) {
        int bitmask = 0;
        for (InterestedCarriers carrier : carriers) {
            bitmask |= carrier.getBit();
        }
        return bitmask;
    }

    public static List<InterestedCarriers> decode(int bitmask) {
        List<InterestedCarriers> result = new ArrayList<>();
        for (InterestedCarriers carrier : InterestedCarriers.values()) {
            if ((bitmask & carrier.getBit()) != 0) {
                result.add(carrier);
            }
        }
        return result;
    }

    public static InterestedCarriers fromCarrier(Carrier carrier) {
        return switch (carrier) {
            case LGU -> LGU;
            case KT -> KT;
            case SKT -> SKT;
        };
    }
}
