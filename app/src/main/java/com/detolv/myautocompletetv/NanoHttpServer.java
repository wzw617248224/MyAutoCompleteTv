package com.detolv.myautocompletetv;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by detolv on 8/13/17.
 */

public class NanoHttpServer extends NanoHTTPD {
    public static final int DEFAULT_SERVER_PORT = 9000;
    private IInsertSmsListener iInsertSmsListener;

    public void setiInsertSmsListener(IInsertSmsListener iInsertSmsListener) {
        this.iInsertSmsListener = iInsertSmsListener;
    }
    public NanoHttpServer()  {
        super(DEFAULT_SERVER_PORT);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><head><meta charset=\"utf-8\"></head><body><h1>短信模拟发送工具</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("phoneNum") == null && parms.get("content") == null) {
            msg += "<form action='?' method='get'>\n  <p>Phone: <input type='text' name='phoneNum'></p>\n";
            msg += "<p>Content: <input type='text' name='content'></p>\n";
            msg += " <input type=\"submit\" value=\"提交\" />"+ "</form>\n";
        } else {
            msg += "<form action='?' method='get'>\n  <p>Phone: <input type='text' name='phoneNum'></p>\n";
            msg += "<p>Content: <input type='text' name='content'></p>\n";
            msg += " <input type=\"submit\" value=\"提交\" />"+ "</form>\n";
            iInsertSmsListener.onSendSms(parms.get("phoneNum"), parms.get("content"));
        }
        return newFixedLengthResponse(msg + "</body></html>\n");
    }


}
