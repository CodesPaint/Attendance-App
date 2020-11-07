package com.e.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<Student>{

        private LayoutInflater mInflater;
        private ArrayList<Student> students;
        private int mViewResourceId;

        public ItemListAdapter(Context context, int textViewResourceId, ArrayList<Student> students){
            super(context,textViewResourceId,students);
            this.students= students;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mViewResourceId = textViewResourceId;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(mViewResourceId, null);

            Student student = students.get(position);
        if (student != null) {
            TextView txtscholar = (TextView) convertView.findViewById(R.id.tvscholar);
            TextView txtname = (TextView) convertView.findViewById(R.id.tvname);
            TextView txtdepartment = (TextView) convertView.findViewById(R.id.tvdepartment);
            if(txtscholar!=null)
                txtscholar.setText(student.getScholar_number()+"");
            if(txtname!=null)
                txtname.setText(student.getName());
            if(txtdepartment!=null)
                txtdepartment.setText(student.getDepartment());
        }
        return convertView;
        }
    }

