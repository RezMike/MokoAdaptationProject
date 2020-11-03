package org.example.app.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.icerock.moko.mvvm.MvvmFragment
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.livedata.data
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.units.adapter.UnitsRecyclerViewAdapter
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.FragmentSecondSampleBinding
import org.example.app.units.SampleUnitFactoryImpl
import org.example.library.feature.sample.presentation.SampleViewModel

class SecondSampleFragment : MvvmFragment<FragmentSecondSampleBinding, SampleViewModel>() {

    override val layoutId: Int = R.layout.fragment_second_sample
    override val viewModelClass: Class<SampleViewModel> = SampleViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelStoreOwner() = requireActivity()

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.sampleFactory.createSampleViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            permissionsController = PermissionsController(
                applicationContext = requireContext().applicationContext
            ),
            unitFactory = SampleUnitFactoryImpl()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val unitsAdapter = UnitsRecyclerViewAdapter(this)
        val swipeRefreshLayout = binding.swipeRefresh
        val recyclerView = binding.itemsRv

        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = unitsAdapter
        }

        viewModel.isRefreshing.ld().observe(this) { swipeRefreshLayout.isRefreshing = it }
        viewModel.state.data().ld().observe(this) { unitsAdapter.units = it.orEmpty() }

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        recyclerView.addOnChildAttachStateChangeListener(
            object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewDetachedFromWindow(view: View) {}

                override fun onChildViewAttachedToWindow(view: View) {
                    val count = unitsAdapter.itemCount
                    val position = recyclerView.getChildAdapterPosition(view)
                    if (position != count - 1) return

                    viewModel.onLoadNextPage()
                }
            }
        )
    }
}