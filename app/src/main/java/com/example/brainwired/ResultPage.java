package com.example.brainwired;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ResultPage extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView marks;
    Intent intent;
    ArrayList<QuestionChoiceVo> players = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        players = (ArrayList<QuestionChoiceVo>) args.getSerializable("ARRAYLIST");
        linearLayout = (LinearLayout) findViewById(R.id.linear1);
        prepareQuestionAnswerLayout(players);

        marks = findViewById(R.id.marksTV);
        marks.setText("Marks Scored: " + calcMarks());

        Button btn = (Button) findViewById(R.id.homebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iintent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(iintent);
            }
        });
    }

    public String calcMarks() {
        int scoredmarks = 0, totmarks;
        totmarks = players.size();
        for (QuestionChoiceVo mQuestionChoiceVo : players) {
            if (Objects.equals(mQuestionChoiceVo.getAns(), mQuestionChoiceVo.getAns_selected())) {
                scoredmarks++;
            }
        }
        return (Integer.toString(scoredmarks) + "/" + Integer.toString(totmarks));
    }

    private void prepareQuestionAnswerLayout(ArrayList<QuestionChoiceVo> questionChoiceVoArrayList) {
        for (QuestionChoiceVo mQuestionChoiceVo : questionChoiceVoArrayList) {
            LinearLayout mSingleQuestionLinearLayout = new LinearLayout(this);
            mSingleQuestionLinearLayout.setOrientation(LinearLayout.VERTICAL);
            mSingleQuestionLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mSingleQuestionLinearLayout.setPadding(0, 5, 0, 5);

            TextView mTextView = new TextView(this);
            TextView ansselected = new TextView(this);
            TextView ans = new TextView(this);

            mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mTextView.setText(mQuestionChoiceVo.getQuestion());
            mTextView.setTextSize(20f);
            mSingleQuestionLinearLayout.addView(mTextView);

            ansselected.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ansselected.setText("Answer Selected : " + mQuestionChoiceVo.getAns_selected());
            ansselected.setTextSize(15f);
            mSingleQuestionLinearLayout.addView(ansselected);

            ans.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ans.setText("Correct Answer : " + mQuestionChoiceVo.getAns());
            ans.setTextSize(15f);
            mSingleQuestionLinearLayout.addView(ans);
            mSingleQuestionLinearLayout.setPadding(12, 18, 12, 18);
            linearLayout.addView(mSingleQuestionLinearLayout);
        }


    }
}
