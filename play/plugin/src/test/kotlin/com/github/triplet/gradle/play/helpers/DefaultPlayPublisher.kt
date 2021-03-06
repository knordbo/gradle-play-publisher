package com.github.triplet.gradle.play.helpers

import com.github.triplet.gradle.androidpublisher.PlayPublisher
import java.io.File

open class DefaultPlayPublisher : PlayPublisher {
    fun install() {
        PlayPublisher.setFactory(object : PlayPublisher.Factory {
            override fun create(credentials: File, email: String?, appId: String) =
                    this@DefaultPlayPublisher
        })
    }

    override fun uploadInternalSharingBundle(bundleFile: File) = ""
}
