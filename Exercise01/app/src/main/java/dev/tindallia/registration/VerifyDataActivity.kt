package dev.tindallia.registration

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import dev.tindallia.registration.api.ApiClient
import dev.tindallia.registration.model.UserData
import dev.tindallia.registration.model.UserModel
import registration.databinding.ActivityVerifyDataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyDataBinding
    //private val userData = Data.getData()
    private lateinit var userData: UserData
    private var userId = Data.getUserId().userId
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private var idToken = Data.getToken().token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityVerifyDataBinding.inflate(layoutInflater).also { binding = it }
        val px: Int = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10.0f,resources.displayMetrics)).toInt()
        binding.content.mainView.setPadding(px,px,px,px)

        getData(userId, idToken)

        /*binding.content.tvUsername.text = userData.username
        binding.content.tvGender.text = userData.gender
        binding.content.tvDocId.text = userData.docId
        binding.content.tvDob.text = userData.dateOfBirth*/

        binding.content.btnSubmit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(binding.root)
    }

    private fun getData(id: String, token: String){
        try{
            binding.content.pbProgress.visibility = View.VISIBLE
            val call = ApiClient.getClient.getUser(id, token)

            call!!.enqueue(object: Callback<UserModel?> {
                override fun onResponse(call: Call<UserModel?>, response: Response<UserModel?>) {
                    Toast.makeText(this@VerifyDataActivity,"Fetched from API", Toast.LENGTH_SHORT).show()

                    binding.content.tvUsername.text = response.body()?.username ?: ""
                    binding.content.tvGender.text = response.body()?.gender ?: ""
                    binding.content.tvDocId.text = response.body()?.documentId ?: ""
                    binding.content.tvDob.text = response.body()?.dateOfBirth ?: ""

                    binding.content.pbProgress.visibility = View.GONE
                }

                override fun onFailure(call: Call<UserModel?>, t: Throwable) {
                    Toast.makeText(this@VerifyDataActivity,t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }catch(e: Exception){
            Toast.makeText(this@VerifyDataActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }
}