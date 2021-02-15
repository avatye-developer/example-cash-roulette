package com.avatye.example.crj;

import androidx.multidex.MultiDexApplication;

import com.avatye.cashroulette.CashRouletteSDK;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * appID, appSecret -> AndroidManifest.xml 정의된 경우
         * CashRouletteSDK.initialize(application, log(Boolean));
         */
        CashRouletteSDK.initialize(this, true);

        /**
         * appID, appSecret -> Application에서 정의
         * CashRouletteSDK.initialize(application, appId(String), appSecret(String), log(Boolean));
         *
         CashRouletteSDK.initialize(
             this,
             "98d4d4c35d594451b21f54718e2bc986",
             "c395dbe200ad4493ade96fb92c988fcf1c8df2d3687d49a9ab6f31f7c05e2bf4",
             true
         );
         */
    }
}
