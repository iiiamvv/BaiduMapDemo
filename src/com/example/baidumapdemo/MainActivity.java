package com.example.baidumapdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity{
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private SDKReceiver mReceiver;
	private Button mButton;
	private myBDListener myLocationListener;
	private LocationClient mLocationClient;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
//		mMapView = (MapView) findViewById(R.id.bmapView);
//		mBaiduMap = mMapView.getMap();
//		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		
//		Intent intent = getIntent();
//		if (intent.hasExtra("x") && intent.hasExtra("y")) {
//			// ����intent����ʱ���������ĵ�Ϊָ����
//			Bundle b = intent.getExtras();
//			LatLng p = new LatLng(b.getDouble("y"), b.getDouble("x"));
//			mMapView = new MapView(this,
//					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
//							.target(p).build()));
//		} else {
//			mMapView = new MapView(this, new BaiduMapOptions());
//		}
//		setContentView(mMapView);
//		mBaiduMap = mMapView.getMap();
//		Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
		
		// ע�� SDK �㲥������
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
		
		mButton = (Button) findViewById(R.id.btn_1);
		myLocationListener = new myBDListener();
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.start();
		mLocationClient.requestLocation();
		Log.i("jxb", "��λ");
		//���ö�λ��ʽ
		LocationClientOption option = new LocationClientOption();
		
		
		
	}
	
	@Override  
    protected void onResume() {  
        super.onResume();  
//        mMapView.onResume();  
    }  
    @Override  
    protected void onPause() {  
        super.onPause();  
//        mMapView.onPause();  
    }  
    @Override  
    protected void onDestroy() {  
//        mMapView.onDestroy();  
//        mMapView = null;  
        super.onDestroy();  
        unregisterReceiver(mReceiver);
    } 
	
	
	
	
	public class myBDListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			int a = location.getLocType();
			Log.i("jxb", "LocType = "+a);
			mButton.setText(a);

		}
	}
	
	/**
	 * ����㲥�����࣬���� SDK key ��֤�Լ������쳣�㲥
	 */
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d("jxb", "action: " + s);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(getApplicationContext(),
						"key ��֤����! ���� AndroidManifest.xml �ļ��м�� key ����",
						Toast.LENGTH_LONG).show();
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(getApplicationContext(), "�������",
						Toast.LENGTH_LONG).show();
			}
		}
	}
	
}