package com.henry.task41p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    Activity activity;
    ArrayList taskId, taskTitle, taskUnit, taskDescription, taskDate;

    int position;

    MyAdapter(Activity activity, Context context, ArrayList taskId, ArrayList taskTitle, ArrayList taskUnit, ArrayList taskDescription, ArrayList taskDate) {
        this.activity = activity;
        this.context = context;
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskUnit = taskUnit;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
        holder.taskId_txt.setText(String.valueOf(taskId.get(position)));
        holder.taskTitle_txt.setText(String.valueOf(taskTitle.get(position)));
        holder.taskUnit_txt.setText(String.valueOf(taskUnit.get(position)));
        holder.taskDescription_txt.setText(String.valueOf(taskDescription.get(position)));
        holder.taskDate_txt.setText(String.valueOf(taskDate.get(position)));

        holder.listLayout.setOnClickListener(v -> {
            Intent intent = new Intent (context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(taskId.get(position)));
            intent.putExtra("title", String.valueOf(taskTitle.get(position)));
            intent.putExtra("unit", String.valueOf(taskUnit.get(position)));
            intent.putExtra("description", String.valueOf(taskDescription.get(position)));
            intent.putExtra("date", String.valueOf(taskDate.get(position)));
            intent.putExtra("position", position);
            activity.startActivityForResult(intent, 1);

        });
    }

    @Override
    public int getItemCount() {
        return taskId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskId_txt, taskTitle_txt, taskUnit_txt, taskDescription_txt, taskDate_txt;
        LinearLayout listLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskId_txt = itemView.findViewById(R.id.taskNumber);
            taskTitle_txt = itemView.findViewById(R.id.taskName);
            taskUnit_txt = itemView.findViewById(R.id.taskUnit);
            taskDescription_txt = itemView.findViewById(R.id.textDescription);
            taskDate_txt = itemView.findViewById(R.id.textDate);
            listLayout = itemView.findViewById(R.id.listLayout);
        }
    }
}
