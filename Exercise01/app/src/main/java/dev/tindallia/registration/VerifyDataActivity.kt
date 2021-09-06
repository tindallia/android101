package dev.tindallia.registration

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import registration.databinding.ActivityVerifyDataBinding

class VerifyDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyDataBinding
    private val userData = Data.getData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityVerifyDataBinding.inflate(layoutInflater).also { binding = it }
        val px: Int = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10.0f,resources.displayMetrics)).toInt()
        binding.content.mainView.setPadding(px,px,px,px)

        binding.content.tvUsername.text = userData.username
        binding.content.tvGender.text = userData.gender
        binding.content.tvDocId.text = userData.docId
        binding.content.tvDob.text = userData.dateOfBirth

        binding.content.btnSubmit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(binding.root)
    }
}