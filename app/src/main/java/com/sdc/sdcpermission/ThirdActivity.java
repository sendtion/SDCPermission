package com.sdc.sdcpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * 第三方库处理Android6.0运行时权限
 * 主要介绍严振杰大神的库AndPermission和另外一个支持RxJava的库rxpermissions
 */
public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ThirdActivity";//快捷键logt

    private TextView tv_sdcard;
    private TextView tv_sms;
    private TextView tv_phone;
    private TextView tv_contacts;
    private TextView tv_location;
    private TextView tv_record;
    private TextView tv_camera;
    private TextView tv_calender;
    private TextView tv_sensor;

    private RxPermissions rxPermissions;

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

        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_contacts:
//                rxPermissions
//                .request(Manifest.permission.READ_CONTACTS)
//                .subscribe(granted -> {
//                    if (granted) { // Always true pre-M
//                        Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                        //dialogOfScan();
//                    } else {
//                        // Oups permission denied
//                        Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                        builder.setTitle("权限申请提示");
//                        builder.setMessage("当前应用缺少读取联系人权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                        builder.setNegativeButton("取消", null);
//                        builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                        builder.create().show();
//                    }
//                });

                AndPermission.with(this)
                        .requestCode(101)
                        .permission(Manifest.permission.READ_CONTACTS)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_location:
//                rxPermissions
//                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少定位权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(102)
                        .permission(Manifest.permission.ACCESS_FINE_LOCATION)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_sdcard:
//                rxPermissions
//                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少读写存储卡权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(103)
                        .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_sms:
//                rxPermissions
//                        .request(Manifest.permission.READ_SMS)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少读取短信权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(104)
                        .permission(Manifest.permission.READ_SMS)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_phone:
//                rxPermissions
//                        .request(Manifest.permission.CALL_PHONE)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少拨打电话权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(105)
                        .permission(Manifest.permission.CALL_PHONE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_record:
//                rxPermissions
//                        .request(Manifest.permission.RECORD_AUDIO)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少录音权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(106)
                        .permission(Manifest.permission.RECORD_AUDIO)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_camera:
//                rxPermissions
//                        .request(Manifest.permission.CAMERA)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少调用相机权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(107)
                        .permission(Manifest.permission.CAMERA)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_calender:
//                rxPermissions
//                        .request(Manifest.permission.WRITE_CALENDAR)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少写日历权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(108)
                        .permission(Manifest.permission.WRITE_CALENDAR)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
            case R.id.tv_sensor:
//                rxPermissions
//                        .request(Manifest.permission.BODY_SENSORS)
//                        .subscribe(granted -> {
//                            if (granted) { // Always true pre-M
//                                Toast.makeText(ThirdActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                //dialogOfScan();
//                            } else {
//                                // Oups permission denied
//                                Toast.makeText(ThirdActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
//                                builder.setTitle("权限申请提示");
//                                builder.setMessage("当前应用缺少传感器权限。请点击设置-权限-打开所需要的权限。最后点击两次返回键回到本界面即可。");
//                                builder.setNegativeButton("取消", null);
//                                builder.setPositiveButton("设置", (dialog, which) -> getAppDetailSettingIntent(""));
//                                builder.create().show();
//                            }
//                        });

                AndPermission.with(this)
                        .requestCode(109)
                        .permission(Manifest.permission.BODY_SENSORS)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale((requestCode, rationale) ->
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
                        )
                        .send();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private PermissionListener listener = new PermissionListener() {

        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            switch (requestCode){
                case 101:
                    showToast("‘读取联系人’权限获取成功");
                    break;
                case 102:
                    showToast("‘GPS定位’权限获取成功");
                    break;
                case 103:
                    showToast("‘读取存储卡’权限获取成功");
                    break;
                case 104:
                    showToast("‘读取短信’权限获取成功");
                    break;
                case 105:
                    showToast("‘拨打电话’权限获取成功");
                    break;
                case 106:
                    showToast("‘录音’权限获取成功");
                    break;
                case 107:
                    showToast("‘调用相机’权限获取成功");
                    break;
                case 108:
                    showToast("‘写日历’权限获取成功");
                    break;
                case 109:
                    showToast("‘传感器’权限获取成功");
                    break;
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(ThirdActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(ThirdActivity.this, 110).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    };

}
