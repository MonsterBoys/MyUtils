package com.lock.lifesensexu.myapputils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2014/10/14.
 */
public class StreamUtil {

    public static void close(InputStream in, OutputStream out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
        }
    }

}
