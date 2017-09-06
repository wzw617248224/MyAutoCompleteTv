package com.detolv.myautocompletetv;

import android.text.TextUtils;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private static String[] destAfters = new String[]{
      "321.11",
      "11111111321.5555",
      "3132313.444",
      "32441.11",
      "32431.14321",
      "3243431.11",
      "321323",
      "0",
      "111111111111",
      "123456789",
      "1234567",
      "111111111111.323",
      "1234567.12"
    };
    /**
     * 是否正则通过
     */
    public static boolean isRegexMatchs(String strTemp, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strTemp);
        return matcher.matches();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        for (String s:destAfters){
            boolean hasFind = isRegexMatchs(s,"\\d{1,6}(^(.\\d{0,2})$)?");
            System.out.println(s + " :" + hasFind);
        }
    }
}