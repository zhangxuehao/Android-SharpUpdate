package com.sharpandroid.update;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sharpandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilsAsync {


    public static class DownloadFile extends AsyncTask<Void, Integer, Integer> {
        private Context context;
        private MaterialDialog dialog;
        private String version, path, filename, downloadUrl;

        public DownloadFile(Context context, String version) {
            this.context = context;
            this.version = version;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
            showDialogDownloading();
        }

        protected void showDialogDownloading() {
            MaterialDialog.Builder builder = UtilsDialog.showDownloadingDialog(context, version);
            builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
                    cancel(true);
                }
            });
            this.dialog = builder.show();

            filename = context.getPackageName() + "_" + version + ".apk";
            downloadUrl = Configure.UPDATE_URL;
            // Create download directory if doesn't exist
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            Integer lengthOfFile = 0;

            try {
                URL url = new URL(downloadUrl);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // Getting file lenght
                lengthOfFile = connection.getContentLength();
                // Read file
                input = connection.getInputStream();
                // Where to write file
                output = new FileOutputStream(new File(path, filename));

                byte data[] = new byte[4096];
                long total = 0;
                int count;

                while ((count = input.read(data)) != -1) {
                    // Close input if download has been cancelled
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // Updating download progress
                    if (lengthOfFile > 0) {
                        publishProgress((int) ((total * 100) / lengthOfFile));
                    }
                    output.write(data, 0, count);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ignored) {
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }

            return lengthOfFile;
        }

        protected void onProgressUpdate(Integer... progress) {
            dialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer file_length) {
            UtilsHelper.dismissDialog(dialog);
            File file = new File(path, filename);
            if (file_length != null && file.length() == file_length) {
                // File download: OK
                context.startActivity(UtilsIntent.getOpenAPKIntent(file));
                UtilsDialog.showSaveAPKDialog(context, file, version);
            } else {
                // File download: FAILED
                onCancelled();
                UtilsDialog.showSnackbar(context, context.getResources().getString(R.string.snackbar_failed));
            }
        }

        @Override
        protected void onCancelled() {
            // Delete uncompleted file
            File file = new File(path, filename);
            if (file.exists()) {
                file.delete();
            }
        }

    }


}
