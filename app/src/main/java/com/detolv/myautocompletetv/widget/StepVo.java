package com.detolv.myautocompletetv.widget;

/**
 * Created by detolv on 7/23/17.
 */
public class StepVo {
    String title;
    String time;

    public StepVo(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
