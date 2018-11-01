package mobiledev.pxl.be.triviaking.dummy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizContent {

    public static final List<Quiz> ITEMS = new ArrayList<>();

    public static final Map<String, Quiz> ITEM_MAP = new HashMap<String, Quiz>();

    private static void addItem(Quiz item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Quiz {
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
}
