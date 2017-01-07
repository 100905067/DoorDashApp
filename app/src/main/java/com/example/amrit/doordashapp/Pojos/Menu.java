package com.example.amrit.doordashapp.Pojos;

/**
 * Created by amrita on 1/5/2017.
 */

public class Menu {
    private int id;
    private String name;

    public Menu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id="+id+","+"name="+name;
    }
}
