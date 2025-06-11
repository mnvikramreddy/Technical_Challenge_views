package com.tps.challenge.features.storefeed

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.tps.challenge.MainActivityViewModel
import com.tps.challenge.R
import com.tps.challenge.TCApplication
import com.tps.challenge.ViewModelFactory
import com.tps.challenge.features.storefeed.viewmodels.StoreFeedViewModel
import javax.inject.Inject

/**
 * Displays the list of Stores with its title, description and the cover image to the user.
 */
class StoreFeedFragment : Fragment() {
    companion object {
        const val TAG = "StoreFeedFragment"
    }

    private lateinit var storeFeedAdapter: StoreFeedAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    /*    */
    /**
     * To use DaggerViewModel Factory for all viewModel with viewModel Key
     * *//*
    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory


    */
    /**
     * To get viewmodel when using DaggerViewModelFactory
     * Because we added lazy this not throw injection error and can accessed after injection
     * the below method of accessing is standard method without ktx fragment library
     *
     * if ktx is used we can do as
     *  lateinit val storeFeedViewModel by viewModels<StoreFeedViewModel>{viewModelFactory}
     * *//*
    private val storeFeedViewModel: StoreFeedViewModel by lazy {
        ViewModelProvider(requireActivity(), daggerViewModelFactory)[StoreFeedViewModel::class.java]
    }*/


    @Inject
    lateinit var viewModelFactory: ViewModelFactory<StoreFeedViewModel>

    private val storeFeedViewModel: StoreFeedViewModel by lazy {
        viewModelFactory.get<StoreFeedViewModel>(this)
    }

    @Inject
    lateinit var mainViewModelFactory: ViewModelFactory<MainActivityViewModel>

    private val activityViewModel: MainActivityViewModel by lazy {
        mainViewModelFactory.get<MainActivityViewModel>(requireActivity())
    }

    override fun onAttach(context: Context) {
        TCApplication.getAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_feed, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipe_container)
        // Enable if Swipe-To-Refresh functionality will be needed
        swipeRefreshLayout.isEnabled = true

        storeFeedAdapter = StoreFeedAdapter {
            detailScreen(it)
        }
        setUpRecyclerView(view)
        setupSearch(view)
        subscribeUi()
    }

    private fun setUpRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.stores_view)
        /** This will add swipe delete and drag to arrange
         * */
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            (itemTouchHelperCallback)
            // TODO uncomment the line below whe Adapter is implemented
            adapter = storeFeedAdapter
        }
    }

    private fun detailScreen(string: String) {

        /**
         * legacy way fragment with shared viewmodel to detail screen
         * */
        //  activityViewModel.setStoreId(string)

        /**
         * Using Navigation by passing bundle
         * the StoreFeedFragmentDirection is generated when we use safe args
         * */
        val action =
            StoreFeedFragmentDirections.actionStoreFeedFragmentToStoreDetailsFragment(string)
        findNavController().navigate(action)
    }

    private fun setupSearch(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                storeFeedViewModel.onSearchQueryChanged(newText ?: "")
                return true
            }

        })
    }

    private fun subscribeUi() {

        swipeRefreshLayout.setOnRefreshListener {
            storeFeedViewModel.getStoreFeed()
        }

        storeFeedViewModel.storeList.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false
            storeFeedAdapter.submitList(it) //for List Adapter need to submit
            // storeFeedAdapter.updateList(it) //for Recycler view we need to add in Adapter manually and update list
        }

//        storeFeedViewModel.storeFeedUiState.observe(viewLifecycleOwner) {
//            storeFeedAdapter.updateList(it.storeList)
//
//        }
    }


    /**
     * This is a ItemTouch call back used for
     * Drag Drop items and swipe the items
     * */
    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            storeFeedViewModel.moveItem(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            direction: Int
        ) {
            val position = viewHolder.adapterPosition
            val deletedItem = storeFeedViewModel.deleteItemAt(position)
            Snackbar.make(recyclerView, "Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    deletedItem?.let {
                        storeFeedViewModel.restoreItemAt(deletedItem, position)
                    }
                }
                .show()
        }

        /**
         * This help in adding a simple Paint with red color for swipe
         * */
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                paint.color = Color.RED
                c.drawRect(
                    itemView.left.toFloat(),
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat(),
                    paint
                )

            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}
