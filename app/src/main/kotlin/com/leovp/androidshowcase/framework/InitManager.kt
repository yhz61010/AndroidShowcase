package com.leovp.androidshowcase.framework

import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.leovp.android.exts.LeoToast
import com.leovp.android.ui.ForegroundComponent
import com.leovp.androidbase.exts.android.closeAndroidPDialog
import com.leovp.androidshowcase.R
import com.leovp.log.LogContext
import com.leovp.module.common.CrashHandler
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.log.MarsXLog
import com.leovp.pref.LPref
import com.leovp.pref.PrefContext
import io.karn.notify.Notify

/**
 * Author: Michael Leo
 * Date: 2021/10/11 11:02
 */
object InitManager {
    fun init(app: Application) {
        CrashHandler.initCrashHandler(app)

        LeoToast.getInstance(app).init(
            LeoToast.ToastConfig(GlobalConst.DEBUG, R.mipmap.app_ic_launcher_round)
        )

        // Log must be initialized first.
        LogContext.setLogImpl(MarsXLog("AOS").apply {
            init(app, com.leovp.androidshowcase.BuildConfig.CONSOLE_LOG_OPEN)
        })

        PrefContext.setPrefImpl(LPref(app))

        closeAndroidPDialog()

        // Ignore netty start error cause by log4j
        // InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE)

        ForegroundComponent.init(app)

        Notify.defaultConfig {
            header {
                icon = R.mipmap.app_ic_launcher_round
                color = ContextCompat.getColor(app, R.color.app_colorPrimary)
            }
            alerting("default-notification") {
                channelName = app.getString(R.string.app_notification_channel_name)
                channelDescription = app.getString(R.string.app_notification_channel_name_desc)
                lockScreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
                channelImportance = Notify.IMPORTANCE_LOW
            }
        }
    }
}
