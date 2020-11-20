package com.example.examplepurchaseapp;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyBillingImpl implements PurchasesUpdatedListener {
    private final BillingClient billingClient;
    private final Activity mActivity;
    private static final String TAG = "Billing";
    private List <SkuDetails> SKUS;


    public MyBillingImpl(Activity activity){
        List<String> skuDetails = new ArrayList<>();
        skuDetails.add("android.test.purchased");  // prepared by Google
        skuDetails.add("android.test.canceled");
        skuDetails.add("android.test.refunded");
        skuDetails.add("android.test.item_unavailable");
        mActivity = activity;
        billingClient = BillingClient.newBuilder(mActivity).setListener(this).build();
      billingClient.startConnection(new BillingClientStateListener() {
          @Override
          public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) Log.i("ok","It was authenticated"+ billingResult);
            else Log.w("ok","It was authenticated"+ billingResult);

          }

          @Override
          public void onBillingServiceDisconnected() {
              Log.w("not ok","onBillingServiceDisconnected()");

          }
      });

    }


    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        Log.i("ok",billingResult.getDebugMessage());

    }

    public void querySkuDetailsAsync(@BillingClient.SkuType final String itemType,
                                     final List<String> skuList, final SkuDetailsResponseListener listener) {
        final SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder().setSkusList(skuList)
                .setType(itemType).build();
        billingClient.querySkuDetailsAsync(skuDetailsParams,
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                        /*
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuList != null){
                            for (skuDetailsParams details : skuList)
                        }

                         */
                        listener.onSkuDetailsResponse(billingResult, list);
                    }
                });
    }

    public void startPurchase(String Id){
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(SKUS.get(Id))
                .build();
        billingClient.launchBillingFlow(mActivity, billingFlowParams);

    }
}
