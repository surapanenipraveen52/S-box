package com.idbms.s_box.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by praveen on 4/10/2015.
 */
public class OpenFile {
    public void openFile(File f, Context context){
        try {
            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(f).toString());
            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(f),mimetype);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // no Activity to handle this kind of files
        }

    }
}
