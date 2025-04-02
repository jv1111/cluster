package com.example.cluster.recycler;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cluster.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView tvName;
    Button btnDelete;
    MotionLayout mainMotionLayout;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        btnDelete = itemView.findViewById(R.id.btnDelete);
        mainMotionLayout = itemView.findViewById(R.id.mainMotionLayout);
    }
}
