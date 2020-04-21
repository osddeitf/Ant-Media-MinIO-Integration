package com.amazonaws.regions;

public class Regions {

    private final String endpoint;

    public Regions(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public static Regions fromName(String name) {
        String url = name;
        return new Regions(url);
    }
}
