package com.example.amrit.doordashapp.Pojos;

import java.util.List;

/**
 * Created by amrita on 1/5/2017.
 */

public class Restaurant {
    private int id;
    private String name;
    private String description;
    private String url;
    private String address;
    private List<Menu> menuList;

    public Restaurant(int id, String name, String description, String url, String address, List<Menu> menuList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.address = address;
        this.menuList = menuList;
    }
    @Override
    public String toString() {
        return "id="+id+","+"Name:"+name+","+"description"+","+"url:"+url+"address="+","+address+"menus"+menuList.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }
}
