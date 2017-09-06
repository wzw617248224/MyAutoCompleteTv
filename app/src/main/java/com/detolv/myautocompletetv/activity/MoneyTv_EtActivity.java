package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.detolv.myautocompletetv.widget.MoneyTextView;
import com.detolv.myautocompletetv.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 是否正则通过
     */
    public static boolean isRegexMatchs(String strTemp, String regex) {
        if (TextUtils.isEmpty(strTemp) || TextUtils.isEmpty(regex)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strTemp);
        return matcher.matches();
    }

    public String insertString(String a, CharSequence b, int t){
        if (t == a.length()){
            return a + b;
        }else if (t == 0){
            return b + a;
        }else {
            return a.substring(0, t) + b + a.substring(t, a.length());
        }
    }

    private class MoneyInputFilter implements android.text.InputFilter {

        private int MAX_DIGIT_LENGTH = 6;

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //当只输入一个小数点时，自动补全前面的0
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }

            String destAfter = insertString(dest.toString(), source, dstart);
            boolean hasFind = isRegexMatchs(destAfter,"\\d{1,6}(.\\d{0,2})?");
            if (!hasFind){
                return "";
            }

            if (!source.equals(".") && dest.toString().length() >= MAX_DIGIT_LENGTH && !destAfter.contains(".")){
                return "";
            }

            return null;
        }
    }

}
