package mobiledev.pxl.be.triviaking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mobiledev.pxl.be.triviaking.support.Remembrance;

public class QuestionActivity extends AppCompatActivity {
    private JSONArray quiz;
    private boolean multi;
    private int correctId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        quiz = Remembrance.quiz;

        if(quiz == null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        try {
            JSONObject question = quiz.getJSONObject(Remembrance.questionNumber);
            String type = question.getString("type");
            multi = type.equalsIgnoreCase("multiple");
            ((TextView) findViewById(R.id.question_label)).setText(Html.fromHtml(question.getString("question")).toString());
            if(multi) {
                correctId = (int)(Math.random()*3);
                Button[] buttons = { findViewById(R.id.answer_button_1), findViewById(R.id.answer_button_2),findViewById(R.id.answer_button_3),findViewById(R.id.answer_button_4) };
                String correctAnswer = question.getString("correct_answer");
                JSONArray array = question.getJSONArray("incorrect_answers");
                if(correctId<3){
                    String replacedAnswer = array.getString(correctId);
                    array.put(replacedAnswer);
                }
                array.put(correctId, correctAnswer);
                for(int i = 0; i < buttons.length; i++) {
                    buttons[i].setText(Html.fromHtml(array.getString(i)).toString());
                    if(i==correctId){
                        buttons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onCorrectAnswer();
                            }
                        });
                    } else {
                        buttons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onIncorrectAnswer();
                            }
                        });
                    }
                }
            } else {
                findViewById(R.id.answer_button_3).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer_button_4).setVisibility(View.INVISIBLE);
                Button[] buttons = { findViewById(R.id.answer_button_1), findViewById(R.id.answer_button_2)};
                JSONArray array = question.getJSONArray("incorrect_answers");
                correctId = (int)(Math.random());
                if(correctId==0){
                    String replacedAnswer = array.getString(correctId);
                    array.put(replacedAnswer);
                }
                array.put(correctId, question.getString("correct_answer"));
                for(int i = 0; i < buttons.length; i++) {
                    buttons[i].setText(array.getString(i));
                    if(i==correctId){
                        buttons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onCorrectAnswer();
                            }
                        });
                    } else {
                        buttons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onIncorrectAnswer();
                            }
                        });
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onCorrectAnswer(){
        Remembrance.score = Remembrance.score+1;
        Remembrance.questionNumber = Remembrance.questionNumber+1;
        checkEnd();
    }

    private void onIncorrectAnswer(){
        Remembrance.questionNumber = Remembrance.questionNumber+1;
        checkEnd();
    }

    private  void checkEnd(){
        if(Remembrance.questionNumber >= Remembrance.questions){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("QuizScore", "You scored " + Remembrance.score + " out of " +Remembrance.questions);
            startActivity(i);
        } else {
            Intent i = new Intent(this, QuestionActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "NO SECOND CHANCES!", Toast.LENGTH_SHORT).show();
    }
}
