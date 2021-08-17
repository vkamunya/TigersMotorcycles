package com.example.tigersmotorcycles.ui.activities

import android.os.Bundle
import android.util.Log
import com.example.tigersmotorcycles.R
import com.example.tigersmotorcycles.firestore.FirestoreClass
import com.example.tigersmotorcycles.models.Product
import com.example.tigersmotorcycles.utils.Constants
import com.example.tigersmotorcycles.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_details.*

/**
 * Product Details Screen.
 */
class ProductDetailsActivity : BaseActivity() {
    // A global variable for product id.
    private var mProductId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        //Get the product id through the intent extra and print it in the log.

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
            Log.i("Product Id", mProductId)
        }
        // Call the function to setup action bar.
        setupActionBar()
        //Call the function to get the product details when the activity is launched.
        getProductDetails()
    }
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }
    /**
     * A function to call the firestore class function that will get the product details from cloud firestore based on the product id.
     */
    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FirestoreClass to get the product details.
        FirestoreClass().getProductDetails(this@ProductDetailsActivity, mProductId)
    }
    /**
     * A function to notify the success result of the product details based on the product id.
     *
     * @param product A model class with product details.
     */
    fun productDetailsSuccess(product: Product) {

        // Hide Progress dialog.
        hideProgressDialog()

        // Populate the product details in the UI.
        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            iv_product_detail_image
        )

        tv_product_details_title.text = product.title

        tv_product_details_price.text = "Ksh${product.price}"
        tv_product_details_description.text = product.description
        tv_product_details_available_quantity.text = product.stock_quantity
    }
}