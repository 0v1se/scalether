package scalether.abi.util;

public class Bytes {
    public static final byte ZERO = 0x0;
    public static final byte ONE = 0x1;

    private Bytes() {}

    public static void fill(byte[] bytes, byte fill, int from, int to) {
        for (int i = from; i < to; i++) {
            bytes[i] = fill;
        }
    }

    public static byte[] filled(int size, byte fill) {
        final byte[] result = new byte[size];
        if (fill != ZERO) {
            fill(result, fill, 0, size);
        }
        return result;
    }
}
