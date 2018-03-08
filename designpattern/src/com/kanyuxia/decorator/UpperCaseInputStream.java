package com.kanyuxia.decorator;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kanyuxia on 2017/3/26.
 */
public class UpperCaseInputStream extends FilterInputStream {

    protected UpperCaseInputStream(InputStream in) {
        super(in);
    }

    public int readCharToUpperCase() throws IOException {
        int c = read();
        return c == -1 ? c : Character.toUpperCase(c);
    }
}
