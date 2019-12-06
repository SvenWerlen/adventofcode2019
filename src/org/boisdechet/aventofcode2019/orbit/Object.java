package org.boisdechet.aventofcode2019.orbit;

public class Object {

    private String key;
    private Object inOrbitWith;

    public Object(String key, Object inOrbitWith) {
        this.key = key;
        this.inOrbitWith = inOrbitWith;
    }

    public String getKey() {
        return key;
    }


    public Object getInOrbitWith() {
        return inOrbitWith;
    }

    public void setInOrbitWith(Object inOrbitWith) {
        this.inOrbitWith = inOrbitWith;
    }
}
