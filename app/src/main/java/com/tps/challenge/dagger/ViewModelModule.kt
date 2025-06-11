package com.tps.challenge.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tps.challenge.DaggerViewModelFactory
import com.tps.challenge.ViewModelKey
import com.tps.challenge.features.storefeed.viewmodels.StoreFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    /**
     * when using viewmodel factory we need to use this bind
     * */
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory


    /**
     * For every viewmodel we are adding we need to do this to inject in to dagger tree
     * */
    @Binds
    @IntoMap
    @ViewModelKey(StoreFeedViewModel::class)
    abstract fun bindStoreFeedViewModel(storeFeedViewModel: StoreFeedViewModel): ViewModel
}