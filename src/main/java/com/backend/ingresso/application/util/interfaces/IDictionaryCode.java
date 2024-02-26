package com.backend.ingresso.application.util.interfaces;

import java.util.Hashtable;

public interface IDictionaryCode {
    public Integer getKeyDictionary(String key);

    public Integer removeKeyDictionary(String key);

    public Integer putKeyValueDictionary(String key, Integer value);
}
