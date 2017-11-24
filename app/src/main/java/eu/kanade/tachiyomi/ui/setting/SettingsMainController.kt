package eu.kanade.tachiyomi.ui.setting

import android.support.v7.preference.PreferenceScreen
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.util.getResourceColor

class SettingsMainController : SettingsController() {
    override fun setupPreferenceScreen(screen: PreferenceScreen) = with(screen) {
        titleRes = R.string.label_settings

        val tintColor = context.getResourceColor(R.attr.colorAccent)

        preference {
            iconRes = R.drawable.ic_tune_black_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_general
            onClick { navigateTo(SettingsGeneralController()) }
        }
        preference {
            iconRes = R.drawable.ic_chrome_reader_mode_black_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_reader
            onClick { navigateTo(SettingsReaderController()) }
        }
        preference {
            iconRes = R.drawable.ic_file_download_black_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_downloads
            onClick { navigateTo(SettingsDownloadController()) }
        }
        preference {
            iconRes = R.drawable.ic_sync_black_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_tracking
            onClick { navigateTo(SettingsTrackingController()) }
        }
        preference {
            iconRes = R.drawable.ic_backup_black_24dp
            iconTint = tintColor
            titleRes = R.string.backup
            onClick { navigateTo(SettingsBackupController()) }
        }
        preference {
            iconRes = R.drawable.eh_ic_ehlogo_red_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_eh
            onClick { navigateTo(SettingsEhController()) }
        }
        preference {
            iconRes = R.drawable.ic_code_black_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_advanced
            onClick { navigateTo(SettingsAdvancedController()) }
        }
        preference {
            iconRes = R.drawable.ic_help_black_24dp
            iconTint = tintColor
            titleRes = R.string.pref_category_about
            onClick { navigateTo(SettingsAboutController()) }
        }
    }

    private fun navigateTo(controller: SettingsController) {
        router.pushController(RouterTransaction.with(controller)
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }
}