package nl.hsleiden.vault.vault.Database;

import java.io.Serializable;

/**
 * Created by Perseus on 05-04-16.
 */
public class PairValue implements Serializable {           // WAAROM serializable ????

    private String key;
    private String value;

    public PairValue(String keyNew, String valueNew){
        this.key = keyNew;
        this.value = valueNew;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

