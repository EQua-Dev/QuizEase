package com.androidstrike.quizease.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.R;

public class RulesViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    TextView tvRules;

    public RulesViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        tvRules = itemView.findViewById(R.id.text_rule);
    }
}
