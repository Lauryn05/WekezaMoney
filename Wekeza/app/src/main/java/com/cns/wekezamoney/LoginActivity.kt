import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cns.wekezamoney.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DBHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnDone.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (dbHelper.checkUser(username, password)) {
                // Login successful, proceed to main activity or dashboard
                finish() // Close login activity
            } else {
                // Handle incorrect username/password scenario
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = "Invalid credentials"
            }
        }
    }
}
