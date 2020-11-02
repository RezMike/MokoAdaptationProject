package org.example.library.feature.sample.presentation

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.Composition
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.format
import org.example.library.feature.sample.di.Strings

class SampleViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val strings: Strings
) : ViewModel(),
    EventsDispatcherOwner<SampleViewModel.EventsListener> {

    val numberField: FormField<String, StringDesc> = FormField("1", { ld ->
        ld.map {
            val number = it.toIntOrNull()
            if (number == null || number < 0) "Некорректное число!".desc() else null
        }
    })

    private val number: LiveData<Int?> = numberField.data.map {
        it.toIntOrNull()?.let { number ->
            if (number < 0) null else number
        }
    }
    val isFirstButtonEnabled: LiveData<Boolean> = number.map { it != null }

    val firstTitle: LiveData<StringDesc> = MutableLiveData(
        StringDesc.Composition(listOf(strings.firstTitle.desc(), "❓".desc()))
    )
    val secondTitle: LiveData<StringDesc> = number.map { strings.secondTitle.format(it.toString()) }

    init {
        numberField.validate()
    }

    fun onShowClick() {
        eventsDispatcher.dispatchEvent {
            showTextFields()
        }
    }

    interface EventsListener {
        fun showTextFields()
    }
}
