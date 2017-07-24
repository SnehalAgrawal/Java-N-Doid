package agrawal.snehal.allwidgetexample.listexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agrawal.snehal.allwidgetexample.R;


public class ListViewExpandable extends AppCompatActivity {
    SparseArray<Group> groups = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expandable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.elv);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
        listView.setAdapter(adapter);
    }

    public void createData() {
        for (int j = 0; j < 5; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++)
                group.children.add("Sub Item" + i);
            groups.append(j, group);
        }
    }

    private class MyExpandableListAdapter extends BaseExpandableListAdapter {

        private final SparseArray<Group> groups;
        LayoutInflater inflater;
        Activity activity;

        MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
            activity = act;
            this.groups = groups;
            inflater = act.getLayoutInflater();
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groups.get(groupPosition).children.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String children = (String) getChild(groupPosition, childPosition);
            TextView text;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listrow_details, null);
            }
            text = (TextView) convertView.findViewById(R.id.textView1);
            text.setText(children);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, children, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return groups.get(groupPosition).children.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listrow_group, null);
            }
            Group group = (Group) getGroup(groupPosition);
            ((CheckedTextView) convertView).setText(group.str);
            ((CheckedTextView) convertView).setChecked(isExpanded);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    private class Group {
        String str;
        final List<String> children = new ArrayList<>();

        Group(String string) {
            this.str = string;
        }
    }
}