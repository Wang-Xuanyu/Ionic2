package com.example.athenal20;

import android.content.Context;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.io.InputStream;

public class LocalServer extends NanoHTTPD {

    private Context context;

    public LocalServer(Context context) throws IOException {
        super(8080);
        this.context = context;
        start(SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            InputStream input = context.getAssets().open("index.html");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            String html = new String(buffer);
            return newFixedLengthResponse(Response.Status.OK, "text/html", html);
        } catch (IOException e) {
            return newFixedLengthResponse("Error loading page");
        }
    }
}