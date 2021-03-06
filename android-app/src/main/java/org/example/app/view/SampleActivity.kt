package org.example.app.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.media.picker.MediaPickerController
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.permissions.PermissionsController
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.ActivitySampleBinding
import org.example.app.units.SampleUnitFactoryImpl
import org.example.library.feature.sample.presentation.SampleViewModel

class SampleActivity :
    MvvmEventsActivity<ActivitySampleBinding, SampleViewModel, SampleViewModel.EventsListener>(),
    SampleViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_sample
    override val viewModelClass: Class<SampleViewModel> = SampleViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        val permissionsController = PermissionsController(applicationContext = applicationContext)
        AppComponent.factory.sampleFactory.createSampleViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            permissionsController = permissionsController,
            mediaPickerController = MediaPickerController(permissionsController),
            locationTracker = LocationTracker(permissionsController),
            unitFactory = SampleUnitFactoryImpl()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FirstSampleFragment(), FIRST_SAMPLE_FRAGMENT_TAG)
                .commit()
        }
        viewModel.permissionsController.bind(lifecycle, supportFragmentManager)
        viewModel.locationTracker.bind(lifecycle, this, supportFragmentManager)
    }

    override fun showTextFields() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SecondSampleFragment(), SECOND_SAMPLE_FRAGMENT_TAG)
            .commit()
    }

    companion object {
        private const val FIRST_SAMPLE_FRAGMENT_TAG = "FIRST_SAMPLE_FRAGMENT_TAG"
        private const val SECOND_SAMPLE_FRAGMENT_TAG = "SECOND_SAMPLE_FRAGMENT_TAG"
    }
}
