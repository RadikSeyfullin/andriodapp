package com.example.todolist;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import static android.R.attr.checked;

class CustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private static final int STRIKE_LINE = Paint.STRIKE_THRU_TEXT_FLAG;
    private boolean[] checkOfBooleanVar;
    private int counter = 0;

    private LayoutInflater mInflater;

    CustomAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    boolean[] itemCount(int count){
        return checkOfBooleanVar = new boolean[count];
    }

    public void addItem(final String item, boolean isCompleted, int index) {
        checkOfBooleanVar[index] = isCompleted;
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    private void lineStriking(CompoundButton view, boolean isChecked){
        if(isChecked){
            view.setPaintFlags(view.getPaintFlags() | STRIKE_LINE);
        }
        else{
            view.setPaintFlags(view.getPaintFlags() ^ STRIKE_LINE);
        }
    }

    private void lineStriking(ViewHolder vh, boolean ic){
        if(ic){
            vh.checkBox.setPaintFlags(vh.checkBox.getPaintFlags() | STRIKE_LINE);
        }
        else{
            vh.checkBox.setPaintFlags(vh.checkBox.getPaintFlags() & STRIKE_LINE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.snippet_item1, null);
                    holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
                    holder.checkBox.setText(mData.get(position));
                    holder.checkBox.setChecked(checkOfBooleanVar[counter++]);
                    lineStriking(holder, holder.checkBox.isChecked());
                    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            Todo todoInFocus = (Todo) buttonView.getTag();
                            lineStriking(buttonView, isChecked);
                        }
                    });
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.snippet_item2, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    holder.textView.setText(mData.get(position));
                    break;
            }

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }

}