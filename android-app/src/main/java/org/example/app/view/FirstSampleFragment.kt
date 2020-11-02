package org.example.app.view

import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmFragment
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.FragmentFirstSampleBinding
import org.example.library.feature.sample.presentation.SampleViewModel

class FirstSampleFragment : MvvmFragment<FragmentFirstSampleBinding, SampleViewModel>() {

    override val layoutId: Int = R.layout.fragment_first_sample
    override val viewModelClass: Class<SampleViewModel> = SampleViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelStoreOwner() = requireActivity()

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.sampleFactory.createSampleViewModel(
            eventsDispatcher = eventsDispatcherOnMain()
        )
    }
}