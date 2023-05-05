package com.example.brainwired;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;


public class QuestionChoiceVo implements Serializable, Parcelable {

    String question;
    int ans = 0;
    int ans_selected = 0;
    ArrayList<String> choiceArrayList;

    public QuestionChoiceVo(String question, ArrayList<String> choiceArrayList, int ans) {
        this.question = question;
        this.choiceArrayList = choiceArrayList;
        this.ans = ans;
    }

    protected QuestionChoiceVo(Parcel in) {
        question = in.readString();
        ans = in.readInt();
        ans_selected = in.readInt();
        choiceArrayList = in.createStringArrayList();
    }

    public static final Creator<QuestionChoiceVo> CREATOR = new Creator<QuestionChoiceVo>() {
        @Override
        public QuestionChoiceVo createFromParcel(Parcel in) {
            return new QuestionChoiceVo(in);
        }

        @Override
        public QuestionChoiceVo[] newArray(int size) {
            return new QuestionChoiceVo[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getChoiceArrayList() {
        return choiceArrayList;
    }

    public void setChoiceArrayList(ArrayList<String> choiceArrayList) {
        this.choiceArrayList = choiceArrayList;
    }

    public void setAns_selected(int n) {
        this.ans_selected = n;
    }

    public String getAns_selected() {
        if (ans_selected == 0) {
            return "Nothing Selected";
        } else
            return choiceArrayList.get(ans_selected - 1);
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public String getAns() {
        if (ans == 0) {
            return "Answer not given";
        } else
            return choiceArrayList.get(ans - 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeInt(ans);
        dest.writeInt(ans_selected);
        dest.writeStringList(choiceArrayList);
    }
}
