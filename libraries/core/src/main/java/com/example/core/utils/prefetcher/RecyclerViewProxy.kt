@file:Suppress("PackageDirectoryMismatch")

package androidx.recyclerview.widget

import com.example.core.utils.prefetcher.PrefetchViewPool

internal fun RecyclerView.RecycledViewPool.attachToPreventFromClearing() {
    attach()
}

internal fun RecyclerView.ViewHolder.setItemViewType(viewType: Int) {
    mItemViewType = viewType
}

internal fun PrefetchViewPool.factorInCreateTime(viewType: Int, creationTimeNanos: Long) {
    (this as RecyclerView.RecycledViewPool).factorInCreateTime(viewType, creationTimeNanos)
}