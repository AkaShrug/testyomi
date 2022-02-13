package eu.kanade.tachiyomi.ui.library

import androidx.core.view.isVisible
import coil.clear
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.kanade.tachiyomi.data.database.models.Manga
import eu.kanade.tachiyomi.databinding.SourceCompactGridItemBinding
import eu.kanade.tachiyomi.util.view.loadAnyAutoPause
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

/**
 * Class used to hold the displayed data of a manga in the library, like the cover or the title.
 * All the elements from the layout file "source_compact_grid_item" are available in this class.
 *
 * @param binding the inflated view for this holder.
 * @param adapter the adapter handling this holder.
 * @param coverOnly true if title should be hidden a.k.a cover only mode.
 * @constructor creates a new library holder.
 */
class LibraryCompactGridHolder(
    override val binding: SourceCompactGridItemBinding,
    adapter: FlexibleAdapter<*>,
    private val coverOnly: Boolean
) : LibraryHolder<SourceCompactGridItemBinding>(binding.root, adapter) {

    // SY -->
    var manga: Manga? = null

    init {
        binding.playLayout.clicks()
            .onEach {
                playButtonClicked()
            }
            .launchIn((adapter as LibraryCategoryAdapter).controller.viewScope)
    }
    // SY <--

    /**
     * Method called from [LibraryCategoryAdapter.onBindViewHolder]. It updates the data for this
     * holder with the given manga.
     *
     * @param item the manga item to bind.
     */
    override fun onSetValues(item: LibraryItem) {
        // SY -->
        manga = item.manga
        // SY <--
        // Update the title of the manga.
        binding.title.text = item.manga.title

        // For rounded corners
        binding.badges.leftBadges.clipToOutline = true
        binding.badges.rightBadges.clipToOutline = true

        // Update the unread count and its visibility.
        with(binding.badges.unreadText) {
            isVisible = item.unreadCount > 0
            text = item.unreadCount.toString()
        }
        // Update the download count and its visibility.
        with(binding.badges.downloadText) {
            isVisible = item.downloadCount > 0
            text = item.downloadCount.toString()
        }
        // Update the source language and its visibility
        with(binding.badges.languageText) {
            isVisible = item.sourceLanguage.isNotEmpty()
            text = item.sourceLanguage
        }
        // set local visibility if its local manga
        binding.badges.localText.isVisible = item.isLocal

        // SY -->
        binding.playLayout.isVisible = (item.manga.unreadCount > 0 && item.startReadingButton)
        // SY <--

        // Update the cover.
        binding.thumbnail.clear()
        if (coverOnly) {
            // Cover only mode: Hides title text unless thumbnail is unavailable
            if (!item.manga.thumbnail_url.isNullOrEmpty()) {
                binding.thumbnail.loadAnyAutoPause(item.manga)
                binding.title.isVisible = false
            } else {
                binding.title.text = item.manga.title
                binding.title.isVisible = true
            }
            binding.thumbnail.foreground = null
        } else {
            binding.thumbnail.loadAnyAutoPause(item.manga)
        }
    }

    // SY -->
    private fun playButtonClicked() {
        manga?.let { (adapter as LibraryCategoryAdapter).controller.startReading(it, (adapter as LibraryCategoryAdapter)) }
    }
    // SY <--
}
