package com.example.tigersmotorcycles.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import com.example.tigersmotorcycles.R
import com.example.tigersmotorcycles.firestore.FirestoreClass
import com.example.tigersmotorcycles.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            val textView:TextView = findViewById(R.id.tv_login)
            textView.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }


        }

        setupActionBar()
        btn_register.setOnClickListener {

            registerUser()
        }

        // START
        tv_login.setOnClickListener{
            // Here when the user click on login text we can either call the login activity or call the onBackPressed function.
            // We will call the onBackPressed function.
            onBackPressed()
        }

    }

    private fun setupActionBar() {

        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_register_activity)
        setSupportActionBar(toolbar)


        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean {
        val textView: TextView = findViewById(R.id.et_first_name)
        val textView2: TextView = findViewById(R.id.et_last_name)
        val textView3: TextView = findViewById(R.id.et_email)
        val textView4: TextView = findViewById(R.id.et_password)
        val textView5: TextView = findViewById(R.id.et_confirm_password)
        val checkBox: CheckBox = findViewById(R.id.cb_terms_and_condition)
        return when {

            TextUtils.isEmpty(textView.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(textView2.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(textView3.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(textView4.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(textView5.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            textView4.text.toString().trim { it <= ' ' } != textView5.text.toString()
                    .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !checkBox.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                //showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }
    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val editText:EditText =findViewById(R.id.et_email)
            val editText2:EditText=findViewById(R.id.et_password)

            val email: String = editText.text.toString().trim { it <= ' ' }
            val password: String = editText2.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                // If the registration is successfully done
                                if (task.isSuccessful) {

                                    // Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    val user = User(
                                            firebaseUser.uid,
                                            et_first_name.text.toString().trim { it <= ' ' },
                                            et_last_name.text.toString().trim { it <= ' ' },
                                            et_email.text.toString().trim { it <= ' ' }
                                    )

                                    FirestoreClass().registerUser(this@RegisterActivity, user)


                                    //FirebaseAuth.getInstance().signOut()
                                    //finish()
                                } else {
                                    hideProgressDialog()
                                    // If the registering is not successful then show error message.
                                    showErrorSnackBar(task.exception!!.message.toString(), true)
                                }
                            })
        }
    }

    fun userRegistrationSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        // TODO Step 5: Replace the success message to the Toast instead of Snackbar.
        Toast.makeText(
                this@RegisterActivity,
                resources.getString(R.string.register_success),
                Toast.LENGTH_SHORT
        ).show()


        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()
        // Finish the Register Screen
        finish()
    }



}