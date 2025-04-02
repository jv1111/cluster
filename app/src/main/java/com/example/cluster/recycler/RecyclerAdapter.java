package com.example.cluster.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cluster.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {


    private Cursor cursor;
    private Context context;
    private RecyclerCallback cb;

    public interface RecyclerCallback{
        void onTap(int id ,String name);
        void onDelete(int id);
    }

    public RecyclerAdapter(Cursor cursor, Context context, RecyclerCallback cb){
        this.cursor = cursor;
        this.context = context;
        this.cb = cb;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if(cursor.moveToPosition(position)){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

            holder.tvName.setText("Name: " + name);

            holder.itemView.setOnClickListener(v->{
                cb.onTap(id, name);
            });
            holder.btnDelete.setOnClickListener(v->{
                cb.onDelete(id);
            });
        }
        preventScrollOnTouch(holder);
    }

    private void preventScrollOnTouch(RecyclerViewHolder holder) {
        holder.mainMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                holder.itemView.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {}

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {}
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
