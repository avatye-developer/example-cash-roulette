package com.avatye.example.crk

import androidx.multidex.MultiDexApplication
import com.avatye.cashroulette.CashRouletteSDK

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        /**
         * appID, appSecret -> AndroidManifest.xml 정의된 경우
         * CashRouletteSDK.initialize(application, log:Boolean)
         */
        CashRouletteSDK.initialize(application = this, log = true)

        /**
         * appID, appSecret -> Application에서 정의
         * CashRouletteSDK.initialize(application, appId:String, appSecret:String, log:Boolean)
         *
        CashRouletteSDK.initialize(
        application = this,
        appID = "98d4d4c35d594451b21f54718e2bc986",
        appSecret = "c395dbe200ad4493ade96fb92c988fcf1c8df2d3687d49a9ab6f31f7c05e2bf4",
        log = true
        )
         */
    }
}