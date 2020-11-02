package org.example.library.feature.sample.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.StringResource
import org.example.library.feature.sample.MR
import org.example.library.feature.sample.presentation.SampleViewModel

class SampleFactory {
    fun createSampleViewModel(
        eventsDispatcher: EventsDispatcher<SampleViewModel.EventsListener>,
        unitFactory: SampleUnitFactory,
    ): SampleViewModel {
        return SampleViewModel(
            eventsDispatcher = eventsDispatcher,
            unitFactory = unitFactory,
            strings = object : Strings {
                override val firstTitle: StringResource = MR.strings.first_title
                override val secondTitle: StringResource = MR.strings.second_title
                override val textBlockText: StringResource = MR.strings.text_block_text
            }
        )
    }
}
