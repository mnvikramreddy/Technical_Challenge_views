package com.tps.challenge.dagger

import com.tps.challenge.MainActivity
import com.tps.challenge.TCApplication
import com.tps.challenge.features.storefeed.StoreDetailsFragment
import com.tps.challenge.features.storefeed.StoreFeedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }

    /**
     *To access sub-component expose from here
     **/
    //fun activityComponent(): ActivityComponent.Factory

    fun inject(app: TCApplication)
    fun inject(activity: MainActivity)
    fun inject(storeFeedFragment: StoreFeedFragment)
    fun inject(storeDetailsFragment: StoreDetailsFragment)
}
