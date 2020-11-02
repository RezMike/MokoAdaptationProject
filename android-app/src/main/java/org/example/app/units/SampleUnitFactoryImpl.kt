package org.example.app.units

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.units.TableUnitItem
import org.example.app.ItemLoading
import org.example.app.ItemTextBlock
import org.example.app.ItemTitle
import org.example.library.feature.sample.di.SampleUnitFactory

class SampleUnitFactoryImpl : SampleUnitFactory {

    override fun createTitle(text: StringDesc): TableUnitItem {
        return ItemTitle().apply {
            itemId = text.hashCode().toLong()
            title = text
        }
    }

    override fun createTextBlock(text: StringDesc): TableUnitItem {
        return ItemTextBlock().apply {
            itemId = text.hashCode().toLong()
            this.text = text
        }
    }

    override fun createLoadingItem(): TableUnitItem {
        return ItemLoading().apply {
            itemId = -1
        }
    }
}