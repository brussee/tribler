package org.tribler.android;

import android.content.Context;
import android.content.Intent;

import org.kivy.android.PythonService;

import java.io.File;

public class ExperimentService extends PythonService {

    public static void start(Context ctx) {
        String argument = ctx.getFilesDir().getAbsolutePath();
        Intent intent = new Intent(ctx, ExperimentService.class);
        intent.putExtra("androidPrivate", argument);
        intent.putExtra("androidArgument", argument);
        intent.putExtra("pythonName", "Gumby");
        intent.putExtra("pythonHome", argument);
        intent.putExtra("pythonPath", argument + ":" + argument + "/lib");
        // Clean output dir
        File dir = new File(argument, "../output");
        dir.mkdirs();
        String OUTPUT_DIR = dir.getAbsolutePath();
        intent.putExtra("pythonServiceArgument", OUTPUT_DIR);
        intent.putExtra("serviceEntrypoint", "process_guard.py");
        intent.putExtra("serviceTitle", "Gumby");
        intent.putExtra("serviceDescription", "");
        intent.putExtra("serviceIconId", R.mipmap.ic_service);
        ctx.startService(intent);
    }

    public static void stop(Context ctx) {
        Intent intent = new Intent(ctx, ExperimentService.class);
        ctx.stopService(intent);
    }

}
