package com.example.brainwired;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class examViewHolder extends RecyclerView.ViewHolder {
    TextView examName;
    TextView examMessage;
    TextView examDate;
    View view;

    examViewHolder(View itemView) {
        super(itemView);
        examName = (TextView) itemView.findViewById(R.id.examName);
        examDate = (TextView) itemView.findViewById(R.id.examDate);
        examMessage = (TextView) itemView.findViewById(R.id.examMessage);
        view = itemView;
    }


}
