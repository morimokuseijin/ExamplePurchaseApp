package com.morimoku.examplepurchaseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity  {
    CardView cardViewPurchase,cardViewSubscribe;
    Button button;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        MobileAds.initialize(this);
        final AdView adView = findViewById(R.id.admob);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");




        cardViewPurchase = (CardView)findViewById(R.id.cardview_item_purchase);
        cardViewSubscribe = (CardView)findViewById(R.id.card_subscribe);

        cardViewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,PurchaseActivity.class));
            }
        });
        cardViewSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SubscribeActivity.class));
            }
        });
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardedAd.loadAd(new AdRequest.Builder().build(),new RewardedAdLoadCallback(){
                    @Override
                    public void onRewardedAdLoaded() {
                        super.onRewardedAdLoaded();
                        if (rewardedAd.isLoaded()){
                            Activity activity = MainActivity.this;
                            rewardedAd.show(activity,new RewardedAdCallback() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    Log.d("show","show the add");
                                }
                            });

                        }
                    }
                    @Override
                    public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                        super.onRewardedAdFailedToLoad(loadAdError);
                    }
                });
            }
        });



    }


}