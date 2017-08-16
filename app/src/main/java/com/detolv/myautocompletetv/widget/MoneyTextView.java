package com.detolv.myautocompletetv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by detolv on 8/12/17.
 */
public class MoneyTextView extends TextView {
    static DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public MoneyTextView(Context context) {
        super(context);
    }

    public MoneyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoneyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text){
        try {
            boolean hasCurrencyCode = false;
            if (text.startsWith("￥")){
                hasCurrencyCode = true;
            }
            double money = Double.valueOf(text.replace("￥",""));
            text = decimalFormat.format(money);
            if (hasCurrencyCode){
                text = "￥" + text;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        setText(text, BufferType.NORMAL);
    }
}
