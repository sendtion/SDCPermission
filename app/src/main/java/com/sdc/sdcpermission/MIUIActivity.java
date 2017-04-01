package com.sdc.sdcpermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 小米MIUI系统下Android6.0运行时权限的处理
 * 已知：如果全部手动设置为询问，则日历、录音、相机、存储卡四个权限会弹窗提示，其他的不会弹窗提示
 */
public class MIUIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MIUIActivity";//快捷键logt

    private TextView tv_sdcard;
    private TextView tv_sms;
    private TextView tv_phone;
    private TextView tv_contacts;
    private TextView tv_location;
    private TextView tv_record;
    private TextView tv_camera;
    private TextView tv_calender;
    private TextView tv_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_contacts = (TextView) findViewById(R.id.tv_contacts);
        tv_sms = (TextView) findViewById(R.id.tv_sms);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_sdcard = (TextView) findViewById(R.id.tv_sdcard);
        tv_record = (TextView) findViewById(R.id.tv_record);
        tv_calender = (TextView) findViewById(R.id.tv_calender);
        tv_camera = (TextView) findViewById(R.id.tv_camera);
        tv_sensor = (TextView) findViewById(R.id.tv_sensor);

        tv_contacts.setOnClickListener(this);
        tv_sms.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_sdcard.setOnClickListener(this);
        tv_record.setOnClickListener(this);
        tv_calender.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        tv_sensor.setOnClickListener(this);

    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //android 国产手机6.0适配（小米） - hunanqi的博客 - 博客频道 - CSDN.NET
    //http://blog.csdn.net/hunanqi/article/details/52330614

    //（1）当在miui设置成允许时：android自动允许权限，即checkSelfPermission返回0.
    //（2）当在miui设置成拒绝时：此时，checkSelfPermission，返回-1，需要开发者手动申请权限，
    // miui会默认申请成功，并不会弹出android系统的权限申请框，
    //（3）设置成询问时：checkSelfPermission，返回-1，需要开发者手动申请权限，miui会默认申请成功，
    // 并不会弹出android系统的权限申请框，
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_contacts:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //手动改为拒绝权限，返回-1，但是不会弹出对话框，需要自己实现
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("读取联系人", Manifest.permission.READ_CONTACTS, 101);
                } else {
                    showToast("已经获得‘读取联系人’权限");
                }
                break;
            case R.id.tv_location:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //ACCESS_FINE_LOCATION是GPS定位，ACCESS_COARSE_LOCATION是网络定位
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},102);
                    showTipsDialog("GPS定位", Manifest.permission.ACCESS_FINE_LOCATION, 102);
                } else {
                    showToast("已经获得‘GPS定位’权限");
                }
                break;
            case R.id.tv_sdcard:
                //默认允许，读写存储卡权限，不需要申请，一直返回0
                //READ_EXTERNAL_STORAGE是读取存储卡，
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("读存储卡", Manifest.permission.READ_EXTERNAL_STORAGE, 103);
                } else {
                    showToast("已经获得‘读存储卡’权限");
                }
                break;
            case R.id.tv_sms:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //READ_SMS是读取短信，SEND_SMS是发送短信
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("读取短信", Manifest.permission.READ_SMS, 104);
                } else {
                    showToast("已经获得‘读取短信’权限");
                }
                break;
            case R.id.tv_phone:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //CALL_PHONE是拨打电话
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("拨打电话", Manifest.permission.CALL_PHONE, 105);
                } else {
                    showToast("已经获得‘拨打电话’权限");
                }
                break;
            case R.id.tv_record:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //RECORD_AUDIO是录音
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("录音", Manifest.permission.RECORD_AUDIO, 106);
                } else {
                    showToast("已经获得‘录音’权限");
                }
                break;
            case R.id.tv_camera:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //CAMERA是调用相机
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("调用相机", Manifest.permission.CAMERA, 107);
                } else {
                    showToast("已经获得‘调用相机’权限");
                }
                break;
            case R.id.tv_calender:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //WRITE_CALENDAR是写日历权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("写日历", Manifest.permission.WRITE_CALENDAR, 108);
                } else {
                    showToast("已经获得‘写日历’权限");
                }
                break;
            case R.id.tv_sensor:
                //默认询问，第一次返回-1，请求权限后miui自动给予权限，不会弹出对话框，此后一直返回0
                //BODY_SENSORS是传感器权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) !=
                        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
                    showTipsDialog("传感器", Manifest.permission.BODY_SENSORS, 109);
                } else {
                    showToast("已经获得‘传感器’权限");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showToast("已经获得‘读取联系人’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘读取联系人’权限");
                    getAppDetailSettingIntent("读取联系人");
                }
                break;
            case 102:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘GPS定位’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘GPS定位’权限");
                    getAppDetailSettingIntent("GPS定位");
                }
                break;
            case 103:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘读存储卡’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘读存储卡’权限");
                    getAppDetailSettingIntent("读存储卡");
                }
                break;
            case 104:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘读取短信’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘读取短信’权限");
                    getAppDetailSettingIntent("读取短信");
                }
                break;
            case 105:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘拨打电话’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘拨打电话’权限");
                    getAppDetailSettingIntent("拨打电话");
                }
                break;
            case 106:
                //录音默认询问，请求权限会弹窗
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘录音’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘录音’权限");
                    getAppDetailSettingIntent("录音");
                }
                break;
            case 107:
                //相机默认询问，请求权限会弹窗
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘调用相机’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘调用相机’权限");
                    getAppDetailSettingIntent("调用相机");
                }
                break;
            case 108:
                //日历默认询问，请求权限会弹窗
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘写日历’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘写日历’权限");
                    getAppDetailSettingIntent("写日历");
                }
                break;
            case 109:
                //传感器默认允许，不会弹窗
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //获取了权限
                    showToast("已经获得‘传感器’权限？？？");
                } else {
                    //拒绝了权限
                    showToast("拒绝获得‘传感器’权限");
                    getAppDetailSettingIntent("传感器");
                }
                break;
        }
    }

    /**
     * 显示对话框，提示用户允许权限
     * @param name
     */
    public void showTipsDialog(String name, final String permission, final int code){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("权限申请提示");
            builder.setMessage("当前应用缺少"+name+"权限。是否立即申请权限？");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MIUIActivity.this, new String[]{
                            permission}, code);
                }
            });
            builder.create().show();
        } else {
            showToast("我们需要"+name+"权限，给我吧！");
            //MIUI在此处，除了日历、录音、相机、存储卡会弹窗，你可以选择拒绝或者允许
            //其他权限不弹窗，直接返回0，也就是直接允许了，此时权限状态仍然为询问状态
            //MIUI上拒绝后不会再弹窗，相当于选中了不再询问，权限状态由询问变为拒绝，需要手动给予权限
            //MIUI上允许后，权限状态由询问变为允许
            //也就是说，MIUI上不会走上一步：shouldShowRequestPermissionRationale(this, permission)
            ActivityCompat.requestPermissions(MIUIActivity.this, new String[]{
                    permission},code);
        }
    }

    /**
     * 跳转到App详情页
     */
    public void getAppDetailSettingIntent(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限申请提示");
        builder.setMessage("当前应用缺少"+name+"权限。请到应用信息——>权限管理中手动给予权限。");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                }
                startActivity(localIntent);
            }
        });
        builder.create().show();
    }
}
