package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.detolv.myautocompletetv.R;
import com.detolv.myautocompletetv.TextViewAdaptor;

/**
 * Created by detolv on 8/12/17.
 */
public class AutoTextViewActivity extends Activity {
    private AutoCompleteTextView autoCompleteTextView;
    private static final String[] COUNTRIES = {"China", "Russia", "Germany",
            "Ukraine", "Belarus", "USA", "China1", "China2", "USA1"};
    private static final String[] _COUNTRIES = {"China", "China1", "China2",  "Germany"};

    public static void navigateTo(Activity activity){
        Intent intent = new Intent(activity, AutoTextViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_textview_layout);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_tv);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.auto_tv_item_layout, COUNTRIES);
//        autoCompleteTextView.setAdapter(arrayAdapter);
        TextViewAdaptor<String> adaptor = new TextViewAdaptor<>(this, R.layout.auto_tv_item_layout, _COUNTRIES);
        autoCompleteTextView.setAdapter(adaptor);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {// 设置焦点改变事件
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                view.showDropDown();
            }
        });
    }
}
