package com.example.brainwired;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView exampic, exampic2;
    TextView tv1, tv2;
    Button signout;
    ArrayList<QuestionChoiceVo> quiz = new ArrayList<QuestionChoiceVo>();
    ArrayList<QuestionChoiceVo> quiz1 = new ArrayList<QuestionChoiceVo>();
    ArrayList<QuestionChoiceVo> quiz2 = new ArrayList<QuestionChoiceVo>();
    ArrayList<yt_data> yt = new ArrayList<yt_data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        signout = findViewById(R.id.SignOutB);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signoutt();
            }
        });

        //exampic = findViewById(R.id.examPic);

        List<examData> list = new ArrayList<>();
        list = getData();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listiner = new ClickListiner() {
            @Override
            public void click(int index) {
                Toast.makeText(getApplicationContext(), "clicked item index is " + index, Toast.LENGTH_LONG).show();
                if (index % 2 == 0) {
                    openYT(index / 2);
                } else {
                    int i = (index - 1) / 2;
                    openQuiz(i);
                }
            }
        };
        adapter = new ImageGallaryAdapter2(list, getApplication(), listiner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));

    }

    private void signoutt() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(HomePage.this, "Signed Out!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomePage.this, LoginPage.class);
        startActivity(intent);
    }

    ImageGallaryAdapter2 adapter;
    RecyclerView recyclerView;
    ClickListiner listiner;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Sample data for RecyclerView
    private List<examData> getData() {
        List<examData> list = new ArrayList<>();
        list.add(new examData("First Video", "May 23, 2015", "Best Of Luck"));
        list.add(new examData("First Quiz", "June 09, 2015", "b of l"));
        list.add(new examData("Second Video", "April 27, 2017", "This is testing exam .."));
        list.add(new examData("Second Quiz", "April 27, 2017", "This is testing exam .."));
        list.add(new examData("Third Video", "April 27, 2017", "This is testing exam .."));
        list.add(new examData("Third Quiz", "April 27, 2017", "This is testing exam .."));

        return list;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void openYT(int index) {

        yt_data ytData = yt.get(index);
        String dec = ytData.getDesc();
        String id = ytData.getId();
        Intent intent = new Intent(HomePage.this, Youtubepage.class);
        Bundle bundle = new Bundle();
        bundle.putString("vidid", id);
        bundle.putString("desc", dec);
        intent.putExtra("yt_details", bundle);
        startActivity(intent);
    }

    private void openQuiz(int index) {
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis() + 1 * 60 * 1000;

        Intent intent = new Intent(HomePage.this, TopicsPage.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("Questions", Quess);
        if (index == 0) {
            bundle.putSerializable("Questions", quiz);
        } else if (index == 1){
            bundle.putSerializable("Questions", quiz1);
        } else if (index == 2) {
            bundle.putSerializable("Questions", quiz2);
        }

        bundle.putLong("time", timeInSecs);
        intent.putExtra("Quiz_details", bundle);
        startActivity(intent);

    }

    private void loadData() throws IOException {
        ArrayList<QuestionChoiceVo> questionChoiceVoArrayList = new ArrayList<>();
        InputStream inputStream = getAssets().open("video.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String str;
        String id, desc;
        try {
            while ((str = br.readLine()) != null) {
                String[] s = str.split("::");
                id = s[0];
                desc = s[1];
                yt.add(new yt_data(id, desc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream1 = getAssets().open("mcq.txt");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream1, StandardCharsets.UTF_8));

        String ques, o1, o2, o3, o4;
        int co;
        try {
//            while ((str = br1.readLine()) != null || str == "ssss") {
//                if (str.equals("ssss")){
//                    quiz.add(questionChoiceVoArrayList);
//                    questionChoiceVoArrayList.clear();
//                    continue;
//                }
//                String[] s = str.split("::");
//                ques = s[0];
//                o1 = s[1];
//                o2 = s[2];
//                o3 = s[3];
//                o4 = s[4];
//                co = Integer.parseInt(s[5]);
//                ArrayList<String> list1 = new ArrayList<String>();
//                list1.add(o1);
//                list1.add(o2);
//                list1.add(o3);
//                list1.add(o4);
//                questionChoiceVoArrayList.add(new QuestionChoiceVo(ques, list1, co));
//
//            }

            for(int i = 0;i<15;i++ ){
                str = br1.readLine();
                String[] s = str.split("::");
                ques = s[0];
                o1 = s[1];
                o2 = s[2];
                o3 = s[3];
                o4 = s[4];
                co = Integer.parseInt(s[5]);
                ArrayList<String> list1 = new ArrayList<String>();
                list1.add(o1);
                list1.add(o2);
                list1.add(o3);
                list1.add(o4);
                if(i<5){
                    quiz.add(new QuestionChoiceVo(ques, list1, co));
                }else if(i<10){
                    quiz1.add(new QuestionChoiceVo(ques, list1, co));
                } else {
                    quiz2.add(new QuestionChoiceVo(ques, list1, co));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        QuestionChoiceVo mQuestionChoiceVoOne = new QuestionChoiceVo("Question One", new ArrayList<String>() {{
//            add("Choice One");
//            add("Choice Two");
//            add("Choice Three");
//        }});
//        QuestionChoiceVo mQuestionChoiceVoTwo = new QuestionChoiceVo("Question Two", new ArrayList<String>() {{
//            add("Choice One");
//            add("Choice Two");
//            add("Choice Three");
//        }});
//        QuestionChoiceVo mQuestionChoiceVoThree = new QuestionChoiceVo("Question Three", new ArrayList<String>() {{
//            add("Choice One");
//            add("Choice Two");
//            add("Choice Three");
//        }});
//
//        questionChoiceVoArrayList.add(mQuestionChoiceVoOne);
//        questionChoiceVoArrayList.add(mQuestionChoiceVoTwo);
//        questionChoiceVoArrayList.add(mQuestionChoiceVoThree);
//        return questionChoiceVoArrayList;
    }

    private class yt_data {
        String id, desc;

        yt_data(String id, String dec) {
            this.id = id;
            desc = dec;
        }

        public String getDesc() {
            return desc;
        }

        public String getId() {
            return id;
        }
    }
}