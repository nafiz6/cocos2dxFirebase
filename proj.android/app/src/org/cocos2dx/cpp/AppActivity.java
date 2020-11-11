/****************************************************************************
Copyright (c) 2015-2016 Chukong Technologies Inc.
Copyright (c) 2017-2018 Xiamen Yaji Software Co., Ltd.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import android.os.Bundle;
import org.cocos2dx.lib.Cocos2dxActivity;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class AppActivity extends Cocos2dxActivity  {

    private static final String DEBUG_TAG = "3";
    static private FirebaseAnalytics mFirebaseAnalytics;
    static private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setEnableVirtualButton(false);
        super.onCreate(savedInstanceState);
        // Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        if (!isTaskRoot()) {
            // Android launched another instance of the root activity into an existing task
            //  so just quietly finish and go away, dropping the user back into the activity
            //  at the top of the stack (ie: the last state of this task)
            // Don't need to finish it again since it's finished in super.onCreate .
            return;
        }
        // Make sure we're running on Pie or higher to change cutout mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Enable rendering into the cutout area
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        // DO OTHER INITIALIZATION BELOW
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        int gravity= Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        layout.setGravity(gravity);

        this.addContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        // Initialize admob
        MobileAds.initialize(this);
        mAdView = new AdView(this);

        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        layout.addView(mAdView);
        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());

        layout.bringToFront();



        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logEvent();

        //RemoteConfig
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(20)
                .build();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        Map<String, Object> initialMessage = new HashMap<>();
        initialMessage.put("topMessage", "INITIAL MESSAGE");
        mFirebaseRemoteConfig.setDefaultsAsync(initialMessage);
        fetchUpdate();


    }

    public static String getTopMessage(){
        System.out.println("getting top message");
        return mFirebaseRemoteConfig.getString("topMessage");
    }

    public static void fetchUpdate() {
        System.out.println("fetching update");
        // [START fetch_config_with_callback]
        mFirebaseRemoteConfig.fetchAndActivate();
    }


    public static void logEvent(){
        String id = "123";
        String name = "TESTING_LOG";

        System.out.println("LOGGING");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent("LOG_TESTING_1", bundle);

    }

    public static void sendLogs(){
        System.out.println("SENDING LOGS VIA BUTTON");
        String name = "NAME";
        String text = "TEST";
        Bundle params = new Bundle();
        params.putString("image_name", name);
        params.putString("full_text", text);
        mFirebaseAnalytics.logEvent("BUTTON_LOG", params);
    }

     @Override
     public void onResume() {
         super.onResume();

         // Resume the AdView.
         mAdView.resume();
     }

     @Override
     public void onPause() {
         // Pause the AdView.
         mAdView.pause();

         super.onPause();
     }

     @Override
     public void onDestroy() {
         // Destroy the AdView.
         mAdView.destroy();

         super.onDestroy();
     }

}
