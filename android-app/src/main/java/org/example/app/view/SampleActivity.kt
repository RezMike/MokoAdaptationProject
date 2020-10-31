package org.example.app.view

import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.ActivitySampleBinding
import org.example.library.feature.sample.presentation.SampleViewModel

class SampleActivity :
    MvvmEventsActivity<ActivitySampleBinding, SampleViewModel, SampleViewModel.EventsListener>(),
    SampleViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_sample
    override val viewModelClass: Class<SampleViewModel> = SampleViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.sampleFactory.createSampleViewModel(
            eventsDispatcher = eventsDispatcherOnMain()
        )
    }
}
