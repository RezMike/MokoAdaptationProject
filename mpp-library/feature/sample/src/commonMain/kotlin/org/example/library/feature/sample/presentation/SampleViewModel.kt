package org.example.library.feature.sample.presentation

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import dev.icerock.moko.resources.desc.Composition
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.format
import dev.icerock.moko.units.TableUnitItem
import kotlinx.coroutines.delay
import org.example.library.feature.sample.di.SampleUnitFactory
import org.example.library.feature.sample.di.Strings

class SampleViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val unitFactory: SampleUnitFactory,
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

    val units: LiveData<List<TableUnitItem>> = secondTitle.mergeWith(number) { title, number ->
        updateUnits(title, number ?: 0)
    }

    private val pagination: Pagination<Int> = Pagination(
        parentScope = viewModelScope,
        dataSource = LambdaPagedListDataSource {
            delay(1000)
            it?.plus(generateList(it.size, number.value ?: 1)) ?: generateList()
        },
        comparator = { a, b -> a - b },
        nextPageListener = ::onNextPageResult,
        refreshListener = ::onRefreshResult,
        initValue = generateList()
    )

    val isRefreshing: LiveData<Boolean> = pagination.refreshLoading
    val state: LiveData<State<List<TableUnitItem>, String>> = pagination.state
        .dataTransform {
            map { list ->
                list.map { number ->
                    unitFactory.createTextBlock(strings.textBlockText.format(number))
                }
            }.mergeWith(pagination.nextPageLoading) { items, nextPageLoading ->
                if (nextPageLoading) {
                    items + unitFactory.createLoadingItem()
                } else {
                    items
                }
            }
        }
        .errorTransform {
            map { it.toString() }
        }


    init {
        numberField.validate()
    }

    fun onLoadNextPage() {
        pagination.loadNextPage()
    }

    fun onRefresh() {
        pagination.refresh()
    }

    private fun generateList(start: Int = 0, size: Int = 1): List<Int> {
        val list = mutableListOf<Int>()
        for (i in start..size) {
            list.add(i)
        }
        return list
    }

    private fun onNextPageResult(result: Result<List<Int>>) {
        if (result.isSuccess) {
            println("Next items loaded")
        } else {
            println("Next items loading failed with ${result.exceptionOrNull()}")
        }
    }

    private fun onRefreshResult(result: Result<List<Int>>) {
        if (result.isSuccess) {
            println("Refresh successful")
        } else {
            println("Refresh failed with ${result.exceptionOrNull()}")
        }
    }

    private fun updateUnits(title: StringDesc, number: Int): List<TableUnitItem> {
        val list = mutableListOf<TableUnitItem>()
        list.add(unitFactory.createTitle(title))
        for (i in 1..number) {
            list.add(unitFactory.createTextBlock(strings.textBlockText.format(i)))
        }
        return list
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
