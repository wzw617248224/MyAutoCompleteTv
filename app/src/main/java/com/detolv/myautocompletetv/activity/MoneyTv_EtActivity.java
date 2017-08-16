package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;

import com.detolv.myautocompletetv.widget.MoneyTextView;
import com.detolv.myautocompletetv.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyTv_EtActivity extends Activity {
    private MoneyInputFilter lengthFilter;
    @BindView(R.id.money_tv)
    MoneyTextView moneyTv;
    @BindView(R.id.money_et)
    EditText moneyEt;

    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, MoneyTv_EtActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_tv__et);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        lengthFilter = new MoneyInputFilter();
        moneyEt.setFilters(new MoneyInputFilter[]{lengthFilter});
        moneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                moneyTv.setText(s.toString());
            }
        });
    }

    private class MoneyInputFilter implements android.text.InputFilter {
        int MAX_DIGIT_LENGTH = 2;

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //当只输入一个小数点时，自动补全前面的0
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            //限制小数点后最多输入两位
            String[] splitArray = dest.toString().split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                if (dotValue.length() == MAX_DIGIT_LENGTH) {
                    return "";
                }
            }
            //最多只能输入一个小数点
            int count = 0;
            for (char c : dest.toString().toCharArray()) {
                if (c == '.') {
                    count++;
                }
            }
            if (count > 0 && source.equals(".")) {
                return "";
            }
            return null;
        }
    }

}
