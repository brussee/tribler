package org.tribler.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okio.BufferedSource;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;

public class MyUtils {

    /**
     * Static class
     */
    private MyUtils() {
    }

    private static Random rand = new Random();

    private static RefWatcher _refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        if (_refWatcher == null) {
            Application app = (Application) context.getApplicationContext();
            _refWatcher = LeakCanary.install(app);
        }
        return _refWatcher;
    }

    public static String getPackageName() {
        return MyUtils.class.getPackage().getName();
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    public static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static String getMimeType(Uri uri) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static Intent viewIntent(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent editChannelIntent(String dispersyCid, String name, String description) {
        Intent intent = new Intent(EditChannelActivity.ACTION_EDIT_CHANNEL);
        intent.setClassName(getPackageName(), EditChannelActivity.class.getName());
        intent.putExtra(ChannelActivity.EXTRA_DISPERSY_CID, dispersyCid);
        intent.putExtra(ChannelActivity.EXTRA_NAME, name);
        intent.putExtra(ChannelActivity.EXTRA_DESCRIPTION, description);
        return intent;
    }

    public static Intent createChannelIntent() {
        Intent intent = new Intent(EditChannelActivity.ACTION_CREATE_CHANNEL);
        intent.setClassName(getPackageName(), EditChannelActivity.class.getName());
        return intent;
    }

    public static Intent beamIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName(getPackageName(), BeamActivity.class.getName());
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType(getMimeType(uri));
        return intent;
    }

    public static Intent sendIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType(getMimeType(uri));
        return intent;
    }

    public static Intent videoCaptureIntent(Uri output) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // 0: low 1: high
        return intent;
    }

    /**
     * @return The file created for saving a video
     */
    public static File getOutputVideoFile(Context context) throws IOException {
        File videoDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            videoDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_MOVIES).getAbsolutePath());
        } else {
            videoDir = new File(context.getFilesDir(), Environment.DIRECTORY_MOVIES);
        }
        // Create the storage directory if it does not exist
        if (!videoDir.exists() && !videoDir.mkdirs()) {
            throw new IOException(String.format("Failed to create directory: %s", videoDir));
        } else if (!videoDir.isDirectory()) {
            throw new IOException(String.format("Not a directory: %s", videoDir));
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
        return new File(videoDir, "VID_" + timeStamp + ".mp4");
    }

    public static boolean isNetworkConnected(ConnectivityManager connectivityManager) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            // No connection
            return false;
        }
        switch (networkInfo.getType()) {
            case ConnectivityManager.TYPE_ETHERNET:
            case ConnectivityManager.TYPE_WIFI:
                return true;
            case ConnectivityManager.TYPE_BLUETOOTH:
            case ConnectivityManager.TYPE_DUMMY:
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_VPN:
            case ConnectivityManager.TYPE_WIMAX:
            default:
                return false;
        }
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static int getColor(int hashCode) {
        int r = (hashCode & 0xFF0000) >> 16;
        int g = (hashCode & 0x00FF00) >> 8;
        int b = hashCode & 0x0000FF;
        return Color.rgb(r, g, b);
    }

    public static void setCicleBackground(ImageView view, int color) {
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(color);
        circle.setBounds(0, 0, view.getWidth(), view.getHeight());
        view.setBackground(circle);
    }

    public static int randInt() {
        return randInt(0, Integer.MAX_VALUE);
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

    public static String getCapitals(CharSequence sequence, int amount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, l = sequence.length(); i < l && builder.length() < amount; i++) {
            char c = sequence.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static Observable<String> readUtf8LineByLine(BufferedSource source) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            public void call(Subscriber<? super String> subscriber) {
                try {
                    while (!source.exhausted()) {
                        subscriber.onNext(source.readUtf8Line());
                    }
                } catch (IOException ex) {
                    subscriber.onError(ex);
                }
                subscriber.onCompleted();
            }
        });
    }

    public static void onError(Throwable e, Context context) {
        if (e instanceof HttpException && ((HttpException) e).code() == 500) {
            Toast.makeText(context, context.getText(R.string.exception_http_500), Toast.LENGTH_SHORT).show();
        }
    }

}
