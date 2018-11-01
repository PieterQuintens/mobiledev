package mobiledev.pxl.be.triviaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobiledev.pxl.be.triviaking.fragmentsupport.Quiz;
import mobiledev.pxl.be.triviaking.fragmentsupport.QuizContent;
import mobiledev.pxl.be.triviaking.fragmentsupport.SimpleItemRecyclerViewAdapter;

public class QuizListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.quiz_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.quiz_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, QuizContent.ITEMS, mTwoPane));
    }
}
