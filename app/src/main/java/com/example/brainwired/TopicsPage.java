package com.example.brainwired;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TopicsPage extends AppCompatActivity {

    Button btn;
    LinearLayout linearLayout;
    ArrayList selected = new ArrayList();
    ArrayList<QuestionChoiceVo> questionChoiceVoArrayList;
    TextView timer;
    private static final String FORMAT = "%02d:%02d:%02d";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcq_page);

        linearLayout = (LinearLayout) findViewById(R.id.questionsLinearLayout);
        timer = findViewById(R.id.timerr);
        btn = findViewById(R.id.button5);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Quiz_details");
        questionChoiceVoArrayList = (ArrayList<QuestionChoiceVo>) bundle.getSerializable("Questions");

        long timee = bundle.getLong("time");
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        new CountDownTimer(timee - timeInSecs, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                onClick(new View(getApplicationContext()));
            }
        }.start();


        btn.setOnClickListener(this::onClick);


        prepareQuestionAnswerLayout(questionChoiceVoArrayList);
    }


    private void prepareQuestionAnswerLayout(ArrayList<QuestionChoiceVo> questionChoiceVoArrayList) {
        for (QuestionChoiceVo mQuestionChoiceVo : questionChoiceVoArrayList) {
            LinearLayout mSingleQuestionLinearLayout = new LinearLayout(this);
            mSingleQuestionLinearLayout.setOrientation(LinearLayout.VERTICAL);
            mSingleQuestionLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mSingleQuestionLinearLayout.setPadding(0, 5, 0, 5);
            TextView mTextView = new TextView(this);
            mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mTextView.setText(mQuestionChoiceVo.getQuestion());
            mTextView.setTextSize(20f);
            mSingleQuestionLinearLayout.addView(mTextView);
            RadioGroup mChoiceRadioGroup = setUpChoices(mQuestionChoiceVo);

            RelativeLayout.LayoutParams radioGroupLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mChoiceRadioGroup.setLayoutParams(radioGroupLayoutParams);
            mChoiceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    mQuestionChoiceVo.setAns_selected(checkedId);
                }
            });
            mSingleQuestionLinearLayout.addView(mChoiceRadioGroup);
            linearLayout.addView(mSingleQuestionLinearLayout);
        }
    }

    private RadioGroup setUpChoices(QuestionChoiceVo mQuestionChoiceVo) {
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setId(View.generateViewId());

        for (int i = 0; i < mQuestionChoiceVo.getChoiceArrayList().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(mQuestionChoiceVo.getChoiceArrayList().get(i));
            radioButton.setTextSize(18f);
            RadioGroup.LayoutParams radioGroupLayoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroupLayoutParams.setMargins(10, 10, 10, 10);
            radioButton.setPadding(10, 10, 10, 10);
            radioButton.setLayoutParams(radioGroupLayoutParams);
            radioButton.setId(i + 1);
            radioGroup.addView(radioButton);
        }
        return radioGroup;
    }

    public void onClick(View v) {
        for (QuestionChoiceVo q : questionChoiceVoArrayList) {
            Log.d("Ans::::::", "onClick: " + q.getAns_selected());
        }
        Intent intent = new Intent(getApplicationContext(), ResultPage.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) questionChoiceVoArrayList);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "You can't go back, Please submit.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if (key_code == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return true;
        }
        return false;
    }
}
