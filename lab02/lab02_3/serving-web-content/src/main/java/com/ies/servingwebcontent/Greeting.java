package com.ies.servingwebcontent;

public class Greeting{

    private final long id;
    private final String name;

    public Greeting(long id, String name){

        this.id = id;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }


    public String getName() {
        return this.name;
    }
}