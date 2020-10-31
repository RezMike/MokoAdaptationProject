package org.example.library.feature.sample.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.example.library.feature.sample.di.Strings

class SampleViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val strings: Strings
) : ViewModel(),
    EventsDispatcherOwner<SampleViewModel.EventsListener> {

    interface EventsListener {
    }
}
