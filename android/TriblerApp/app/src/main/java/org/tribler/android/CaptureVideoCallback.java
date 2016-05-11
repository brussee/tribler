package org.tribler.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureVideoCallback implements MenuItem.OnMenuItemClickListener {

    private Activity mActivity;
    private Uri mVideoCaptureUri;

    public CaptureVideoCallback(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File file = getOutputVideoFile();
        if (file == null) { // || !file.canWrite()
            Log.e("Tribler", "failed to obtain output file");
            //TODO: advise user
            return false;
        }
        mVideoCaptureUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoCaptureUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // 0: low 1: high
        mActivity.startActivityForResult(intent, Home.CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
        return true;
    }

    public void onActivityResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //TODO: advise user
            Log.d("Tribler", "Video saved to:\n" + data.getData());
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("Tribler", "User cancelled the video capture");
        } else {
            Log.e("Tribler", "failed to capture video");
            //TODO: advise user
        }
    }

    /**
     * @return The file created for saving a video
     */
    private static File getOutputVideoFile() {
        //TODO: To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File videoDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES).getAbsolutePath());

        // Create the storage directory if it does not exist
        if (!videoDir.exists()) {
            if (!videoDir.mkdirs()) {
                Log.e("Tribler", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(videoDir, "VID_" + timeStamp + ".mp4");
    }
}