# SDCPermission
- 1、不依赖第三方库，原生实现Android6.0运行时权限的方法
- 2、加入第三方权限申请库的使用（推荐严振杰大神的）
### 使用方式：
下载工程导入Android Studio即可。
### 系统权限分类
#### 普通权限（Normal Permissions）

这类权限一般不涉及用户隐私，也不需要用户进行授权，如网络访问，手机震动等，这些权限如下所示：

```
ACCESS_LOCATION_EXTRA_COMMANDS
ACCESS_NETWORK_STATE
ACCESS_NOTIFICATION_POLICY
ACCESS_WIFI_STATE
BLUETOOTH
BLUETOOTH_ADMIN
BROADCAST_STICKY
CHANGE_NETWORK_STATE
CHANGE_WIFI_MULTICAST_STATE
CHANGE_WIFI_STATE
DISABLE_KEYGUARD
EXPAND_STATUS_BAR
GET_PACKAGE_SIZE
INSTALL_SHORTCUT
INTERNET
KILL_BACKGROUND_PROCESSES
MODIFY_AUDIO_SETTINGS
NFC
READ_SYNC_SETTINGS
READ_SYNC_STATS
RECEIVE_BOOT_COMPLETED
REORDER_TASKS
REQUEST_INSTALL_PACKAGES
SET_ALARM
SET_TIME_ZONE
SET_WALLPAPER
SET_WALLPAPER_HINTS
TRANSMIT_IR
UNINSTALL_SHORTCUT
USE_FINGERPRINT
VIBRATE
WAKE_LOCK
WRITE_SYNC_SETTINGS
```
#### 危险权限（Dangerous Permission）

涉及用户隐私，需要用户授权，如对sd卡读取、访问用户手机通讯录等。如下所示：
```
android.permission-group.CALENDAR(日历数据) 
android.permission.READ_CALENDAR
android.permission.WRITE_CALENDAR

android.permission-group.CAMERA(相机) 
android.permission.CAMERA

android.permission-group.CONTACTS(联系人)  
android.permission.READ_CONTACTS
android.permission.WRITE_CONTACTS
android.permission.GET_ACCOUNTS

android.permission-group.LOCATION(位置)   
android.permission.ACCESS_FINE_LOCATION
android.permission.ACCESS_COARSE_LOCATION

android.permission-group.MICROPHONE(麦克风)    
android.permission.RECORD_AUDIO

android.permission-group.PHONE(电话)  
android.permission.READ_PHONE_STATE
android.permission.CALL_PHONE
android.permission.READ_CALL_LOG
android.permission.WRITE_CALL_LOG
com.android.voicemail.permission.ADD_VOICEMAIL
android.permission.USE_SIP
android.permission.PROCESS_OUTGOING_CALLS

android.permission-group.SENSORS(传感器)   
android.permission.BODY_SENSORS

android.permission-group.SMS(短信)    
android.permission.SEND_SMS
android.permission.RECEIVE_SMS
android.permission.READ_SMS
android.permission.RECEIVE_WAP_PUSH
android.permission.RECEIVE_MMS
android.permission.READ_CELL_BROADCASTS

android.permission-group.STORAGE(存储)    
android.permission.READ_EXTERNAL_STORAGE
android.permission.WRITE_EXTERNAL_STORAGE
```
查看Dangerous Permission可以发现权限是分组的，这个和Android 6.0的授权机制有关。如果你申请某个危险的权限，假设你的app早已被用户授权了同一组的某个危险权限，那么系统会立即授权，而不需要用户去点击授权。比如你的app对READ_CONTACTS已经授权了，当你的app申请WRITE_CONTACTS时，系统会直接授权通过。此外，申请时弹出的dialog上面的文本说明也是对整个权限组的说明，而不是单个权限（ps:这个dialog是不能进行定制的）。

注：不要对权限组过多的依赖，尽可能对每个危险权限都进行正常流程的申请，因为在后期的版本中这个权限组可能会产生变化。

### 原生方式申请权限
#### 1、检查权限，如果没有获取则申请权限
```
//READ_CONTACTS是读取联系人
if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
        PackageManager.PERMISSION_GRANTED){//拒绝了权限，或者没有获得权限
    //如果没有权限则申请权限
    showTipsDialog("读取联系人", Manifest.permission.READ_CONTACTS, 101);
} else {
    showToast("已经获得‘读取联系人’权限");
}
```
```
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
        builder.setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                permission},code));
        builder.create().show();
    } else {
        showToast("我们需要"+name+"权限，给我吧！");
        //MIUI在此处，除了日历、录音、相机、存储卡会弹窗，你可以选择拒绝或者允许
        //其他权限不弹窗，直接返回0，也就是直接允许了，此时权限状态仍然为询问状态
        //MIUI上拒绝后不会再弹窗，相当于选中了不再询问，权限状态由询问变为拒绝，需要手动给予权限
        //MIUI上允许后，权限状态由询问变为允许
        //也就是说，MIUI上不会走上一步：shouldShowRequestPermissionRationale(this, permission)
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                permission},code);
    }
}
```
#### 2、处理权限申请结果回调
```
/**
 * ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)
 * 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
 * 如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don’t ask again 选项，此方法将返回 false。
 * 如果设备规范禁止应用具有该权限，此方法也会返回 false。
 * @param requestCode
 * @param permissions
 * @param grantResults
 */
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
      }
}
```
```
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
```

- 具体使用详情请参考Demo。

### 第三方权限处理库使用
主要介绍严振杰大神作品。
- 1、首先，引入依赖。
```
//（api>=11） https://github.com/yanzhenjie/AndPermission
compile 'com.yanzhenjie:permission:1.0.5'
```
- 2、申请权限
```
AndPermission.with(this)
    .requestCode(101)
    .permission(Manifest.permission.READ_CONTACTS)
    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
    .rationale((requestCode, rationale) ->
            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
            AndPermission.rationaleDialog(ThirdActivity.this, rationale).show()
    )
    .send();
```
- 3、处理回调
```
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
    AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```
- 4、实现监听事件
```
private PermissionListener listener = new PermissionListener() {

    @Override
    public void onSucceed(int requestCode, List<String> grantedPermissions) {
        // 权限申请成功回调。

        switch (requestCode){
            case 101:
                showToast("‘读取联系人’权限获取成功");
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
```

### MIUI下默认危险权限状态
![MIUI下默认危险权限状态](http://ww1.sinaimg.cn/mw690/6c6cd56dly1febw277e38j20u02a3tes.jpg)
### DEMO界面
![DEMO界面](http://ww1.sinaimg.cn/mw690/6c6cd56dly1febxde3n7cj20u01hctb5.jpg)
### 拒绝后的提示对话框
![拒绝后的提示对话框](http://ww1.sinaimg.cn/mw690/6c6cd56dly1febxf3xbgej20u01hcn00.jpg)
### 申请权限提示对话框
![申请权限提示对话框](http://ww1.sinaimg.cn/mw690/6c6cd56dly1febxfnzctkj20u01hcmzp.jpg)
