package mobiledev.pxl.be.triviaking.data;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract(){}

    public static class Quiz implements BaseColumns {
        public static final String TABLE_NAME = "quiz";
        public static final String CREATED =  "created";
        public static final String CATEGORY = "category";
        public static final String QUESTIONS = "questions";
        public static final String DATA = "data";
    }
}
