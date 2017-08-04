package com.example.trungnguyenhau.quanlytaichinh.Model;

import java.io.Serializable;

/**
 * Created by TRUNGNGUYENHAU on 6/15/2017.
 */

public class Financial implements Serializable {
    String money, type, title, note, id ;

    public Financial(String id, String money, String type, String title, String note) {
        this.id = id;
        this.money = money;
        this.type = type;
        this.title = title;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
