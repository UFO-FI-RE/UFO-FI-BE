package com.example.ufo_fi.v2.payment.domain.payment;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateMetaData {
    private final Map<MetaDataKey, Object> metaData = new HashMap<>();

    public <T> void put(MetaDataKey metaDataKey, T value) {
        metaData.put(metaDataKey, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(MetaDataKey metaDataKey, Class<T> clazz) {
        return (T) metaData.get(metaDataKey);
    }
}
