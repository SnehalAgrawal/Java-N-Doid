package com.example.admin.restapi.ListAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.restapi.AddTask;
import com.example.admin.restapi.R;
import com.example.admin.restapi.support.InternetAccess;

public class CustomTaskCardAdapter extends ArrayAdapter<TaskCard> {
    private Context context;

    public CustomTaskCardAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final TaskCard taskCard = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_single_row, parent, false);
        }
        TextView tvtaskid = (TextView) convertView.findViewById(R.id.tvtaskid);
        TextView tvtask = (TextView) convertView.findViewById(R.id.tvtask);
        TextView tvtaskcreated = (TextView) convertView.findViewById(R.id.tvtaskcreated);
        TextView tvstatus = (TextView) convertView.findViewById(R.id.tvstatus);
        if (taskCard != null) {
            tvtaskid.setText(taskCard.taskid);
            tvtask.setText(taskCard.task);
            tvtaskcreated.setText(taskCard.taskcreated);
            tvstatus.setText(taskCard.taskstatus);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] items = {"View", "Update", "Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Make your selection");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0:
                                    InternetAccess.gettask(context, InternetAccess.URL_TASK + "/" + taskCard.taskid);
                                    break;
                                case 1:
                                    Intent i = new Intent(context, AddTask.class);
                                    i.putExtra("task", taskCard.task);
                                    i.putExtra("status", taskCard.taskstatus);
                                    i.putExtra("id", taskCard.taskid);
                                    context.startActivity(i);
                                    break;
                                case 2:
                                    InternetAccess.deletetask(context, InternetAccess.URL_TASK + "/" + taskCard.taskid);
                                    break;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
        return convertView;
    }
}
