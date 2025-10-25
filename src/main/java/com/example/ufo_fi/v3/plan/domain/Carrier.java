package com.example.ufo_fi.v3.plan.domain;

public enum Carrier {
    SKT, KT, LGU;

    public boolean hasCarrierBy(String rawCarrier) {
        return this.name().equals(rawCarrier);
    }
}
