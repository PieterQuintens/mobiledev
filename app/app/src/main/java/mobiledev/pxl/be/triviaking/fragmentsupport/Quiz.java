package mobiledev.pxl.be.triviaking.fragmentsupport;

import java.sql.Timestamp;

public class Quiz {
    public final String id;
    public final String category;
    public final String data;
    public final String difficulty;
    public final int questions;
    public final Timestamp created;

    public Quiz(String id, String category, String data, int questions, Timestamp created, String difficulty) {
        this.id = id;
        this.category = category;
        this.data = data;
        this.questions = questions;
        this.created = created;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return id + " " +created.toString();
    }
}
