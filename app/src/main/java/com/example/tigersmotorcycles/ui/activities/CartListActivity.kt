package com.example.tigersmotorcycles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tigersmotorcycles.R
import com.example.tigersmotorcycles.firestore.FirestoreClass
import com.example.tigersmotorcycles.models.CartItem
import com.example.tigersmotorcycles.models.Product
import com.example.tigersmotorcycles.ui.adapters.CartItemListAdapter
import com.example.tigersmotorcycles.utils.Constants
import kotlinx.android.synthetic.main.activity_cart_list.*

class CartListActivity : BaseActivity() {
    //a global variable for the product list.

    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<CartItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        setupActionBar()

        btn_checkout.setOnClickListener {
            val intent = Intent(this@CartListActivity, AddressListActivity::class.java)
            intent.putExtra(Constants.EXTRA_SELECT_ADDRESS, true)
            startActivity(intent)
        }
    }
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_cart_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_cart_list_activity.setNavigationOnClickListener { onBackPressed() }
    }
    //  a function to get the list of cart items in the activity.

    private fun getCartItemsList() {

        // Show the progress dialog.
        //showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getCartList(this@CartListActivity)
    }

    /**
     * A function to notify the success result of the cart items list from cloud firestore.
     */
    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()
        for (product in mProductsList) {
            for (cartItem in cartList) {
                if (product.product_id == cartItem.product_id) {

                    cartItem.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0){
                        cartItem.cart_quantity = product.stock_quantity
                    }
                }
            }
        }
        mCartListItems = cartList

        if (mCartListItems.size > 0) {

            rv_cart_items_list.visibility = View.VISIBLE
            ll_checkout.visibility = View.VISIBLE
            tv_no_cart_item_found.visibility = View.GONE

            rv_cart_items_list.layoutManager = LinearLayoutManager(this@CartListActivity)
            rv_cart_items_list.setHasFixedSize(true)

            val cartListAdapter = CartItemListAdapter(this@CartListActivity, mCartListItems, true)
            rv_cart_items_list.adapter = cartListAdapter

            var subTotal: Double = 0.0

            for (item in mCartListItems) {
                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }

            }

            tv_sub_total.text = "Ksh$subTotal"
            // Here we have kept Shipping Charge is fixed as $10 but in your case it may cary. Also, it depends on the location and total amount.
            tv_shipping_charge.text = "Ksh200.0"

            if (subTotal > 0) {
                ll_checkout.visibility = View.VISIBLE

                val total = subTotal + 10
                tv_total_amount.text = "Ksh$total"
            } else {
                ll_checkout.visibility = View.GONE
            }

        } else {
            rv_cart_items_list.visibility = View.GONE
            ll_checkout.visibility = View.GONE
            tv_no_cart_item_found.visibility = View.VISIBLE
        }
    }
    /**
     * A function to get product list to compare the current stock with the cart items.
     */
    private fun getProductList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAllProductsList(this@CartListActivity)
    }
    /**
     * A function to get the success result of product list.
     *
     * @param productsList
     */
    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        mProductsList = productsList
        getCartItemsList()

    }
    override fun onResume() {
        super.onResume()

        //getCartItemsList()
        getProductList()
    }
    /**
     * A function to notify the user about the item removed from the cart list.
     */
    fun itemRemovedSuccess() {

        hideProgressDialog()

        Toast.makeText(
                this@CartListActivity,
                resources.getString(R.string.msg_item_removed_successfully),
                Toast.LENGTH_SHORT
        ).show()

        getCartItemsList()
    }
    /**
     * A function to notify the user about the item quantity updated in the cart list.
     */
    fun itemUpdateSuccess() {

        hideProgressDialog()

        getCartItemsList()
    }
}