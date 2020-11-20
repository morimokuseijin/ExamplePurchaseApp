package com.example.examplepurchaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String GET_SKU_DETAILS_ITEM_LIST = "data";

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> intentService = this.getPackageManager().queryIntentServices(intent,0);
        if (intentService != null && ! intentService.isEmpty()){
            this.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        }

        //This is to query the skus to pattern to make a purchase
        String skutotell = "premium_upgrade";
        ArrayList<String> skus = new ArrayList<>();
        skus.add(skutotell);
        Bundle skusBundle = new Bundle();
        skusBundle.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST,skus);
        Bundle skuDetails =

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IabResult result;
        if (requestCode != resultCode || data == null){
            //handle error
        }
        int responseCode = getRes
    }
}