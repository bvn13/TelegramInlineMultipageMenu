package ru.bvn13.examples.bot.menu;

/**
 * Created by bvn13 on 21.02.2018.
 */
public class MenuItem {

    private String name;
    private String action;


    public MenuItem(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
