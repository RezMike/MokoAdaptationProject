package org.example.library.feature.sample.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import org.example.library.feature.sample.presentation.SampleViewModel

class SampleFactory(
    private val strings: Strings
) {
    fun createSampleViewModel(
        eventsDispatcher: EventsDispatcher<SampleViewModel.EventsListener>
    ): SampleViewModel {
        return SampleViewModel(
            eventsDispatcher = eventsDispatcher,
            strings = strings
        )
    }
}
