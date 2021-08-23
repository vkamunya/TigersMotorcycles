package com.example.tigersmotorcycles.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

/**
 * A custom object to declare all the constant values in a single file. The constant values declared here is can be used in whole application.
 */
object Constants {
    // Firebase Constants
    // This is used for the collection name for USERS.
    const val USERS: String = "users"
    const val PRODUCTS: String = "products"

    const val TIGERMOTORCYCLES_PREFERENCES: String = "TigerMotorcyclePrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    // a constant for Sold Products collections.
    const val SOLD_PRODUCTS: String = "sold_products"
    //A constant field for passing the sold product details to detail screen through intent.
    const val EXTRA_SOLD_PRODUCT_DETAILS: String = "extra_sold_product_details"

    //A constant for Orders collection.
    const val ORDERS: String = "orders"
    //  A constant for Stock Quantity.
    const val STOCK_QUANTITY: String = "stock_quantity"
    //a constant for passing the order details through intent.
    const val EXTRA_MY_ORDER_DETAILS: String = "extra_MY_ORDER_DETAILS"

    // Intent extra constants.
    const val EXTRA_USER_DETAILS: String = "extra_user_details"

    //the constant variable to pass the address details to the checkout screen through intent.

    const val EXTRA_SELECTED_ADDRESS: String = "extra_selected_address"

    //a global constant variable to notify the add address.
    const val ADD_ADDRESS_REQUEST_CODE: Int = 121

    //a constant to pass the value through intent in the address listing screen which will help to select the address to proceed with checkout.
    const val EXTRA_SELECT_ADDRESS: String = "extra_select_address"

    // a constant variable for passing the product id to product details screen through intent.
    const val EXTRA_PRODUCT_ID: String = "extra_product_id"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
    const val READ_STORAGE_PERMISSION_CODE = 2

    // a constant variable for CART ITEMS collection.
    const val CART_ITEMS: String = "cart_items"

    //a constant variable for product owner id.
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"

    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

   // a constant field for product id.
    const val PRODUCT_ID: String = "product_id"

    // Constant variables for Gender
    const val MALE: String = "Male"
    const val FEMALE: String = "Female"

    // the constant variable for default cart quantity.

    const val DEFAULT_CART_QUANTITY: String = "1"
    const val CART_QUANTITY: String = "cart_quantity"

    // Firebase database field names
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val COMPLETE_PROFILE: String = "profileCompleted"
    const val IMAGE: String = "image"

    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"

    const val HOME: String = "Home"
    const val OFFICE: String = "Office"
    const val OTHER: String = "Other"
    const val ADDRESSES: String = "addresses"

    const val EXTRA_ADDRESS_DETAILS: String = "AddressDetails"

    const val USER_ID: String = "user_id"

    const val PRODUCT_IMAGE: String = "Product_Image"
    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"

    /**
     * A function for user profile image selection from phone storage.
     */
    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
    /**
     * A function to get the image file extension of the selected image.
     *
     * @param activity Activity reference.
     * @param uri Image file uri.
     */
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}