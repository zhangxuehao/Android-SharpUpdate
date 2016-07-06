package com.sharpandroid.update;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sharpandroid.R;

import java.io.File;

public class UtilsDialog {

    public static MaterialDialog.Builder showDownloadingDialog(Context context, String version) {
        Boolean showMinMax = false; // Show a max/min ratio to the left of the seek bar

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .progress(false, 100, showMinMax)
                .cancelable(false)
                .negativeText(context.getResources().getString(android.R.string.cancel));

                builder.title(String.format(context.getResources().getString(R.string.downloading), context.getResources().getString(R.string.app_name), version));

        return builder;
    }

    public static MaterialDialog showSaveAPKDialog(final Context context, final File file, final String version) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(context.getResources().getString(R.string.delete))
                .content(context.getResources().getString(R.string.delete_description))
                .cancelable(false)
                .positiveText(context.getResources().getString(R.string.button_save))
                .negativeText(context.getResources().getString(R.string.button_delete))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        showSnackbarSavedAPK(context, file, version);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        file.delete();
                    }
                }).show();

        return dialog;
    }

    public static MaterialDialog showUpdateAvailableDialog(final Context context, final String version) {

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(String.format(context.getResources().getString(R.string.app_update), version))
                .content(context.getResources().getString(R.string.app_update_description))
                .positiveText(context.getResources().getString(R.string.button_update))
                .negativeText(context.getResources().getString(android.R.string.cancel))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        new UtilsAsync.DownloadFile(context, version).execute();
                    }
                })
                .show();

        return dialog;
    }

    public static MaterialDialog.Builder showNoWifiWarningDialog(final Context context) {

        MaterialDialog.Builder dialog = new MaterialDialog.Builder(context);
	    dialog.title(context.getResources().getString(R.string.download_after))
                .content(context.getResources().getString(R.string.no_wifi))
                .positiveText(context.getResources().getString(R.string.download_sure))
                .negativeText(context.getResources().getString(R.string.download_after))
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                    }
                });

        return dialog;
    }


    public static void showSnackbar(Context context, String title) {
        Activity activity = (Activity) context;
//        Snackbar.make(activity.findViewById(R.id.coordinatorLayout), title, Snackbar.LENGTH_LONG).show();
        Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbarSavedAPK(final Context context, final File file, final String version) {
//        Activity activity = (Activity) context;
//        Snackbar.make(activity.findViewById(R.id.coordinatorLayout), String.format(context.getResources().getString(R.string.snackbar_saved), file.getName()), Snackbar.LENGTH_LONG)
//                .setAction(context.getResources().getString(R.string.button_share), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String shareText = String.format(context.getResources().getString(R.string.snackbar_share), version, context.getResources().getString(R.string.app_name));
//                        context.startActivity(UtilsIntent.getShareAPKIntent(file, shareText));
//                    }
//                })
//                .show();
        Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
    }

}
