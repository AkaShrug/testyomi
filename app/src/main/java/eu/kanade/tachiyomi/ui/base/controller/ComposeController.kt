package eu.kanade.tachiyomi.ui.base.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import eu.kanade.presentation.util.LocalRouter
import eu.kanade.tachiyomi.databinding.ComposeControllerBinding
import eu.kanade.tachiyomi.util.view.setComposeContent

/**
 * Basic Compose controller without a presenter.
 */
abstract class BasicFullComposeController(bundle: Bundle? = null) :
    BaseController<ComposeControllerBinding>(bundle),
    ComposeContentController {

    override fun createBinding(inflater: LayoutInflater) =
        ComposeControllerBinding.inflate(inflater)

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        binding.root.apply {
            setComposeContent {
                CompositionLocalProvider(LocalRouter provides router) {
                    ComposeContent()
                }
            }
        }
    }

    // Let Compose view handle this
    override fun handleBack(): Boolean {
        val dispatcher = (activity as? OnBackPressedDispatcherOwner)?.onBackPressedDispatcher ?: return false
        return if (dispatcher.hasEnabledCallbacks()) {
            dispatcher.onBackPressed()
            true
        } else {
            false
        }
    }
}

interface ComposeContentController {
    @Composable fun ComposeContent()
}
