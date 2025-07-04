package com.tps.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.Lazy
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * A Factory class that reduces the boilerplate to initialize the ViewModel within the Activity or a Fragment.
 * Check for usage examples in this codebase and feel free to copy-paste if it will be necessary.
 *
 * Usage example:
 *
 *    @Inject
 *    lateinit var viewModelFactory: ViewModelFactory<StoreFeedViewModel>
 *
 *    private val viewModel: StoreFeedViewModel by lazy {
 *        viewModelFactory.get<StoreFeedViewModel>(
 *            requireActivity()
 *        )
 *    }
 */
class ViewModelFactory<T : ViewModel>
@Inject constructor(private val viewModel: Lazy<T>) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel.get() as T

    /**
     * Returns an instance of a defined ViewModel class.
     */
    inline fun <reified R : T> get(viewModelStoreOwner: ViewModelStoreOwner): T {
        return ViewModelProvider(viewModelStoreOwner, this)[R::class.java]
    }
}

class DaggerViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
            ?: creators.entries.firstOrNull {
                modelClass.isAssignableFrom(it.key)
            }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel Class: $modelClass")
        return try {
            @Suppress("UNCHECKED_CAST")
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
