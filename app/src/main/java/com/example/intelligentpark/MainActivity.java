package com.example.intelligentpark;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.intelligentpark.zxing.activity.CaptureActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class MainActivity extends Activity{

    //高德地图
    private MapView mapView = null;
    private AMap aMap;
    private UiSettings mUiSettings;

    //权限相关
    final private static String[] permissions = {ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    final private static int PERMISSIONSCODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态申请权限
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions,
                    PERMISSIONSCODE);//自定义的code

        }else {
            //初始化地图
            initMap();
            //aMap.moveCamera(CameraUpdateFactory.zoomTo((float)15.5));
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSIONSCODE);//自定义的code
        }

        //界面按钮
        ImageButton mainMessage = (ImageButton) findViewById(R.id.mainMessage);
        Button mainMe = (Button) findViewById(R.id.mainMe);
        Button mainScan = (Button) findViewById(R.id.mainScan);
        Button mainPay = (Button) findViewById(R.id.mainPay);
        ImageButton mainMap = (ImageButton) findViewById(R.id.mainMap);
        Button mainNews = (Button) findViewById(R.id.mainNews);
        ImageButton mainSearch = (ImageButton) findViewById(R.id.mainSearch);
        ImageButton mainRefresh = (ImageButton) findViewById(R.id.mainRefresh);

        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        //注册监听事件
        //mainSearch
        mainSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        //MeActivity
        mainMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MeActivity.class);
                startActivity(intent);
            }
        });

        //mainScan
        mainScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化地图
    private void initMap(){
        try {
            if(aMap == null){
                aMap = mapView.getMap();
                aMap.moveCamera(CameraUpdateFactory.zoomTo((float)15.5));
            }
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
            aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化定位
        try{
            MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location));
            //myLocationStyle.strokeColor(R.color.strokeColor);// 设置圆形的边框颜色
            //myLocationStyle.radiusFillColor(R.color.radiusFillColor);// 设置圆形的填充颜色
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        initMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode){
            case PERMISSIONSCODE:
                if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //用户同意授权
                    initMap();
                }else{
                    //finish();//用户拒绝授权
                }
                break;
            default: break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
