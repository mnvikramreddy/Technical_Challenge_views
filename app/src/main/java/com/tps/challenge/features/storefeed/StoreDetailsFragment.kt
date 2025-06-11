package com.tps.challenge.features.storefeed

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tps.challenge.R
import com.tps.challenge.TCApplication
import com.tps.challenge.ViewModelFactory
import com.tps.challenge.features.storefeed.viewmodels.StoreDetailsViewModel
import javax.inject.Inject

private const val STORE_ID = "storeId"

/**
 * A simple [Fragment] subclass.
 * Use the [StoreDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreDetailsFragment : Fragment() {

    /**
     * used for arguments passed by fragment transaction
     * */
  //  private var storeId: String? = null

    /**
     * Used for Navigation arguments passed with route
     *  */
    private val navArgs: StoreDetailsFragmentArgs by navArgs()

    private lateinit var view: View

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<StoreDetailsViewModel>

    private val storeDetailsViewModel by lazy {
        viewModelFactory.get<StoreDetailsViewModel>(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        /**
         * used for arguments passed by fragment transaction
         * */
      /*  arguments?.let {
            storeId = it.getString(STORE_ID)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_store_details, container, false)

        subscribeToUI()
        /**
         * used for arguments passed by fragment transaction
         * */
       // storeId?.let { storeDetailsViewModel.getStoreDetails(it) }

        /**
         * Used for Navigation arguments passed with route
         *  */
        storeDetailsViewModel.getStoreDetails(navArgs.storeId)
        return view
    }

    private fun subscribeToUI() {
        storeDetailsViewModel.storeDetails.observe(viewLifecycleOwner) {
            it.run {
                view.findViewById<ImageView>(R.id.store_image).apply {
                    Glide.with(this).load(coverImgUrl).into(this)
                }
                view.findViewById<TextView>(R.id.storeId).text = id
                view.findViewById<TextView>(R.id.name).text = name
                view.findViewById<TextView>(R.id.description).text = description
                view.findViewById<TextView>(R.id.phone).text = phoneNumber
                view.findViewById<TextView>(R.id.status).text = deliveryEta
                view.findViewById<TextView>(R.id.status_type).text = status
                view.findViewById<TextView>(R.id.delivery_fee).text = deliveryFeeCents.toString()
                view.findViewById<TextView>(R.id.tags).text = tags.joinToString()
                view.findViewById<TextView>(R.id.address).text = address.printableAddress
            }
        }
    }

    companion object {

        const val TAG = "StoreDetails"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment StoreDetailsFragment.
         */
/*        @JvmStatic
        fun newInstance(param1: String) =
            StoreDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(STORE_ID, param1)
                }
            }*/
    }
}