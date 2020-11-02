package org.example.library.feature.sample.di

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.units.TableUnitItem

interface SampleUnitFactory {
    fun createTitle(text: StringDesc): TableUnitItem
    fun createTextBlock(text: StringDesc): TableUnitItem
}