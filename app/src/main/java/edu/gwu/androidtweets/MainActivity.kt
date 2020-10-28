package edu.gwu.androidtweets

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    /*
        Here, `lateinit var` acts as a "promise" to the compiler that we cannot initialize the variable right now,
        but we will later *and* when we do it'll be non-null. This is because we cannot use findViewById until
        our layout is specified in onCreate with setContentView.

        If you forget to initialize a `lateinit` and then try and use it, the app will crash.

        More info: https://docs.google.com/presentation/d/1QYQswyFurpJYMvYYuRl2czITf2E3OFMz8Ibi_bTDQcE/edit#slide=id.g615c45607e_0_156
    */

    private lateinit var username: EditText

    private lateinit var password: EditText

    private lateinit var login: Button

    private lateinit var signUp: Button

    private lateinit var progressBar: ProgressBar

    private lateinit var firebaseAuth: FirebaseAuth

    /**
     * onCreate is called the first time the Activity is to be shown to the user, so it a good spot
     * to put initialization logic.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs: SharedPreferences = getSharedPreferences("android-tweets", Context.MODE_PRIVATE)

        // Tells Android which layout file should be used for this screen.
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        // The IDs we are using here should match what was set in the "id" field for our views
        // in our XML layout (which was specified by setContentView).
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        signUp = findViewById(R.id.signUp)
        progressBar = findViewById(R.id.progressBar)

        // Kotlin shorthand for login.setEnabled(false).
        // If the getter / setter is unambiguous, Kotlin lets you use the property-style syntax
        login.isEnabled = false
        signUp.isEnabled = false

        // Using a lambda to implement a View.OnClickListener interface. We can do this because
        // an OnClickListener is an interface that only requires *one* function.
        login.setOnClickListener { view ->
            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()

            firebaseAuth
                .signInWithEmailAndPassword(inputtedUsername, inputtedPassword)
                .addOnCompleteListener { task ->
                    // The "task" object represents whether the Firebase action was successful for not
                    // (i.e. were we able to log the user in successfully)
                    if (task.isSuccessful) {
                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        val email = user.email
                        Toast.makeText(
                            this,
                            "Logged in as $email!",
                            Toast.LENGTH_LONG
                        ).show()

                        sharedPrefs
                            .edit()
                            .putString("SAVED_USERNAME", username.text.toString())
                            .apply()

                        // An Intent is used to start a new Activity
                        // 1st param == a "Context" which is a reference point into the Android system. All Activities are Contexts by inheritance.
                        // 2nd param == the Class-type of the Activity you want to navigate to.
                        val intent = Intent(this, MapsActivity::class.java)
                        startActivity(intent)
                    } else {
                        val exception = task.exception

                        // We could also check the specific subtype of the Exception to display more targeted error messages
                        Toast.makeText(
                            this,
                            "Failed to sign up: $exception",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        signUp.setOnClickListener {
            val inputtedUsername: String = username.text.toString()
            val inputtedPassword: String = password.text.toString()

            firebaseAuth
                .createUserWithEmailAndPassword(inputtedUsername, inputtedPassword)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    // The "task" object represents whether the Firebase action was successful for not
                    // (i.e. were we able to register the user successfully)
                    if (task.isSuccessful) {
                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        val email = user.email
                        Toast.makeText(
                            this,
                            "Signed up as $email!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val exception = task.exception

                        // We could also check the specific subtype of the Exception to display more targeted error messages
                        Toast.makeText(
                            this,
                            "Failed to sign up: $exception",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // Another example of explicitly implementing an interface (TextWatcher). We cannot use
        // a lambda in this case since there are multiple functions we need to implement.
        //
        // We're defining an "anonymous class" here using the `object` keyword (basically created
        // a new, dedicated object to implement a TextWatcher for this variable assignment).
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Kotlin shorthand for username.getText().toString()
                // .toString() is needed because getText() returns an Editable (basically a char array).
                val inputtedUsername: String = username.text.toString()
                val inputtedPassword: String = password.text.toString()
                val enableButton = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty()

                // Kotlin shorthand for login.setEnabled(enableButton)
                login.isEnabled = enableButton
                signUp.isEnabled = enableButton
            }
        }

        // Using the same TextWatcher instance for both EditTexts so the same block of code runs on each character.
        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)

        val savedUsername = sharedPrefs.getString("SAVED_USERNAME", "")
        username.setText(savedUsername)
    }
}