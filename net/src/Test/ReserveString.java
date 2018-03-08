package Test;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kanyuxia on 2017/5/4.
 */
public class ReserveString extends FilterInputStream {

    protected ReserveString(InputStream in) {
        super(in);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        byte tmp;
        int i = off;
        int j = off + result;
        while (i < j) {
            tmp = b[i];
            b[i] = b[j - 1];
            b[j - 1] = tmp;
            i++;
            j--;
        }
        return result;
    }
}
