package org.jiggawatt.giffle;



public class Giffle {
    static {
        System.loadLibrary("gifflen");
    }
    public native int Init(String gifName, int w, int h, int numColors, int quality, int frameDelay);
    public native void Close();
    public native int AddFrame(int[] inArray);

    private static Giffle sGiffle;
    
    public static Giffle sInstance() {
        if(sGiffle == null) {
            sGiffle = new Giffle();
        }
        return sGiffle;
    }
}
