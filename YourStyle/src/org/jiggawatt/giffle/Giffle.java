package org.jiggawatt.giffle;

public class Giffle {
    static {
        System.loadLibrary("gifflen");
    }

    public native int Init(String gifName, int width, int height,
            int numColors, int quality, int frameDelay);

    public native int AddFrame(int[] inArray);

    public native void Close();

    private static Giffle sGiffle;

    public static Giffle getInstance() {
        if (sGiffle == null) {
            sGiffle = new Giffle();
        }
        return sGiffle;
    }
}
