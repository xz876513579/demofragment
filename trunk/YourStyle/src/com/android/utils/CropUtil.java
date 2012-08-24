package com.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class CropUtil {
    private static boolean sRestrictNextCrop = false;

    public static Intent constructCropIntent(Activity paramActivity, Uri paramUri)
    {
      /*Intent localIntent1 = new Intent(paramActivity, CropImage.class);
      Intent localIntent2 = localIntent1.setType("image/*");
      Uri localUri = Uri.fromFile(FileUtil.generateTempFile(paramActivity));
      Intent localIntent3 = localIntent1.setData(paramUri);
      Intent localIntent4 = localIntent1.putExtra("scale", false);
      Intent localIntent5 = localIntent1.putExtra("scaleUpIfNeeded", false);
      Intent localIntent6 = localIntent1.putExtra("largestDimension", 2048);
      if (takeRestrictNextCrop())
        Intent localIntent7 = localIntent1.putExtra("smallestDimension", 200);
      Intent localIntent8 = localIntent1.putExtra("aspectX", 1);
      Intent localIntent9 = localIntent1.putExtra("aspectY", 1);
      Intent localIntent10 = localIntent1.putExtra("noFaceDetection", true);
      Intent localIntent11 = localIntent1.putExtra("output", localUri);
      String str = Bitmap.CompressFormat.JPEG.toString();
      Intent localIntent12 = localIntent1.putExtra("outputFormat", str);
      CameraUsageReportingUtil.didOpenCropScreen();*/
      return /*localIntent1*/null;
    }

    public static void setRestrictNextCrop() {
        sRestrictNextCrop = true;
    }

    public static void show(Activity paramActivity, int paramInt, Uri paramUri) {
        Intent localIntent = constructCropIntent(paramActivity, paramUri);
        paramActivity.startActivityForResult(localIntent, paramInt);
    }

    static boolean takeRestrictNextCrop() {
        boolean bool = sRestrictNextCrop;
        sRestrictNextCrop = false;
        return bool;
    }
}
