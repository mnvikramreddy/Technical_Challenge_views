package com.tps.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tps.challenge.features.storefeed.StoreDetailsFragment
import com.tps.challenge.features.storefeed.StoreFeedFragment
import javax.inject.Inject

/**
 * The initial Activity for the app.
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MainActivityViewModel>

    private val viewModel by lazy {
        viewModelFactory.get<MainActivityViewModel>(this)
    }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // legacyFragmentTransaction()

        navigationSetup()
        //subscribeUi()
    }

    /**
     * Navigation way to set up NavGraph along with action bar
     * */
    private fun navigationSetup() {
        navController = findNavController(R.id.nav_host_fragment_container)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /** Navigation related code end here */

    /**
     * Legacy way using Fragment Transaction
     * */

    /*    private fun subscribeUi() {
            viewModel.storeId.observe(this) {
                val storeDetail = StoreDetailsFragment.newInstance(it)
                replaceFragment(storeDetail, StoreDetailsFragment.TAG)
            }
        }

        private fun legacyFragmentTransaction() {
            val storeFeedFragment = StoreFeedFragment()
            replaceFragment(storeFeedFragment, StoreFeedFragment.TAG)
        }

        private fun replaceFragment(newFragment: Fragment, tag: String) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container, newFragment,
                    tag
                )
                .addToBackStack(tag)
                .commit()
        }*/

    /** Legacy code End Here
     * */
}
