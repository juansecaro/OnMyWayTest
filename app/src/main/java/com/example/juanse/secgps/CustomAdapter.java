package com.example.juanse.secgps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Juanse on 03/05/2015.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, int layout, int resId,String[] items) {
        //Call through to ArrayAdapter implementation
        super(context, layout, resId, items);
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        View row = convertView;
        //Inflate a new row if one isn't recycled
        if(row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }
        String item = getItem(position);
        ImageView left = (ImageView)row.findViewById(R.id.leftimage);
        ImageView right = (ImageView)row.findViewById(R.id.rightimage);
        TextView text = (TextView)row.findViewById(R.id.line1);
        left.setImageResource(R.drawable.left_arrow);
        right.setImageResource(R.drawable.right_arrow);

        text.setText(item);
        return row;
    }
}