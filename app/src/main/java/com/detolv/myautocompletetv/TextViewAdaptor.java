package com.detolv.myautocompletetv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by detolv on 8/8/17.
 */
public class TextViewAdaptor<T> extends BaseAdapter implements Filterable {
    private Context mContext;
    private int mResId;
    private List<T> mList;
    private LayoutInflater mInflater;

    public TextViewAdaptor(Context context, int resId, T[] objects) {
        this.mContext = context;
        this.mResId = resId;
        this.mList = Arrays.asList(objects);
        mInflater = LayoutInflater.from(context);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = createViewFromResource(mInflater, position, convertView, parent, mResId);
        if (position == 0){
            convertView.setBackgroundResource(R.drawable.drop_down_selector_top);
        }else if (position < getCount() - 1){
            convertView.setBackgroundResource(R.drawable.drop_down_selector_mid);
        }else {
            convertView.setBackgroundResource(R.drawable.drop_down_selector_bottom);
        }
        return convertView;
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView,
                                        ViewGroup parent, int resource) {
        View view;
        TextView text;

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            text = (TextView) view;
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        T item = getItem(position);
        if (item instanceof CharSequence) {
            text.setText((CharSequence)item);
        } else {
            text.setText(item.toString());
        }

        return view;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mList != null){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                final List<T> newValues = mList;
                results.values = newValues;
                results.count = newValues.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
