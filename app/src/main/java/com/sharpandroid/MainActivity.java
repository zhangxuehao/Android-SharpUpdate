package com.sharpandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sharpandroid.update.UtilsAsync;
import com.sharpandroid.update.UtilsCheckApp;
import com.sharpandroid.update.UtilsDialog;
import com.sharpandroid.update.UtilsNetwork;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在首页获取到服务器接口中的内容,
        LastAppInfo lastAppInfo = new LastAppInfo();
        //这里设定代码版本号为2.0.1
        lastAppInfo.setLastAppVersion("2.0.1");
        downloading(lastAppInfo);
    }

    private void downloading(final LastAppInfo lastAppInfo) {
        //更新版本提示
        if (UtilsCheckApp.isUpdateAvailable(this, lastAppInfo.getLastAppVersion())) {
            MaterialDialog updateDialog = UtilsDialog.showUpdateAvailableDialog(this, lastAppInfo.getLastAppVersion());
            updateDialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog d, DialogAction w) {
                    //网路情况的判断,一般来说是没有wifi是不会直接让用户直接下载的
                    if (!UtilsNetwork.isWifiConnected(MainActivity.this)){
                        MaterialDialog.Builder noWifiWarnDialog = UtilsDialog.showNoWifiWarningDialog(MainActivity.this);
                        noWifiWarnDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                new UtilsAsync.DownloadFile(MainActivity.this, lastAppInfo.getLastAppVersion()).execute();
                            }
                        });
                        noWifiWarnDialog.show();
                    }else{
                        new UtilsAsync.DownloadFile(MainActivity.this,  lastAppInfo.getLastAppVersion()).execute();
                    }

                }
            });
        }
    }
}
