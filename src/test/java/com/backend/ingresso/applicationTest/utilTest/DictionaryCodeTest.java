package com.backend.ingresso.applicationTest.utilTest;

import com.backend.ingresso.application.util.DictionaryCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryCodeTest {
    private final DictionaryCode dictionaryCode;

    public DictionaryCodeTest() {
        this.dictionaryCode = new DictionaryCode();
    }

    //getKeyDictionary//
    @Test
    public void shouldGetKeyDictionaryReturnTheKeyValueNumber(){
        String key = "8891dbe5-fe80-48ad-a86e-65a98c2b57d7";

        dictionaryCode.putKeyValueDictionary("8891dbe5-fe80-48ad-a86e-65a98c2b57d7", 10);

        var resultGet = dictionaryCode.getKeyDictionary(key);

        assertEquals(resultGet, 10);
    }

    @Test
    public void shouldGetKeyDictionaryReturnTheKeyValueNull(){
        String key = "8891dbe5-fe80-48ad-a86e-65a98c2b57d7";

        var resultGet = dictionaryCode.getKeyDictionary(key);

        assertNull(resultGet);
    }

    //removeKeyDictionary//
    @Test
    public void shouldRemoveKeyDictionaryReturnValueDelete(){
        String key = "8891dbe5-fe80-48ad-a86e-65a98c2b57d7";

        dictionaryCode.putKeyValueDictionary("8891dbe5-fe80-48ad-a86e-65a98c2b57d7", 10);

        var result = dictionaryCode.removeKeyDictionary(key);

        assertEquals(result, 10);
    }

    @Test
    public void shouldRemoveKeyDictionaryReturnValueNullNotFound(){
        String key = "8891dbe5-fe80-48ad-a86e-65a98c2b57d7";

        var result = dictionaryCode.removeKeyDictionary(key);

        assertNull(result);
    }

    //putKeyValueDictionary//
    @Test
    public void shouldPutKeyValueDictionarySuccessfully(){
        String key = "8891dbe5-fe80-48ad-a86e-65a98c2b57d7";

        var result1 = dictionaryCode.putKeyValueDictionary(key, 10);


        assertNull(result1);
    }

    @Test
    public void shouldPutKeyValueDictionaryKeyNull(){
        assertThrows(NullPointerException.class, () -> {
            dictionaryCode.putKeyValueDictionary(null, 10);
        });
    }
    @Test
    public void shouldPutKeyValueDictionaryValueNull(){
        String key = "8891dbe5-fe80-48ad-a86e-65a98c2b57d7";
        assertThrows(NullPointerException.class, () -> {
            dictionaryCode.putKeyValueDictionary(key, null);
        });
    }

}
