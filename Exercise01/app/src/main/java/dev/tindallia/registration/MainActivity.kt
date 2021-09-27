package dev.tindallia.registration

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import dev.tindallia.registration.model.ApiClient
import dev.tindallia.registration.model.UserModel
import registration.R

import registration.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import dev.tindallia.registration.model.ApiId
import java.text.SimpleDateFormat


private var selected : String? = null

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var spinner : AutoCompleteTextView
    private var apiDate: String? = null

    private val months : Array<String> = arrayOf("January","February","March","April","May","June",
        "July","August","September","October","November","December")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val px: Int = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10.0f,resources.displayMetrics)).toInt()
        binding.content.mainView.setPadding(px,px,px,px)

        spinner = binding.content.actvGender

        val newAdapter = ArrayAdapterFactory.create(
            this,
            R.layout.spinner_item,
            resources.getStringArray(R.array.genderList)
        )
        spinner.setAdapter(newAdapter)

        spinner.onItemSelectedListener = OnItemSelectedListenerFactory.create()

        binding.content.etDateOfBirth.setOnClickListener { clickDatePicker() }

        binding.content.btnNext.setOnClickListener{
            if(binding.content.etUsername.text.toString().isEmpty() ||
                binding.content.actvGender.text.toString().isEmpty() ||
                binding.content.etDocId.text.toString().isEmpty() ||
                binding.content.etDateOfBirth.text.toString().isEmpty()) {
                Toast.makeText(this, "Please complete the form to continue", Toast.LENGTH_SHORT).show()
            }else{
                binding.content.pbProgress.visibility = View.VISIBLE
                /*Data.setData(binding.content.etUsername.text.toString(),
                    binding.content.actvGender.text.toString(),
                    binding.content.etDocId.text.toString(),
                    binding.content.etDateOfBirth.text.toString(), this)*/
                /*val intent = Intent(this,VerifyDataActivity::class.java)
                startActivity(intent)
                finish()*/

                postData(binding.content.etUsername.text.toString(),
                    binding.content.actvGender.text.toString().lowercase(),
                    binding.content.etDocId.text.toString(),
                    apiDate!!
                    //binding.content.etDateOfBirth.text.toString()
                )

            }}
    }

    private fun clickDatePicker() {
        val myCal = Calendar.getInstance()
        val year = myCal.get(Calendar.YEAR)
        val month = myCal.get(Calendar.MONTH)
        val day = myCal.get(Calendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val dpd = DatePickerDialog(this,
            {
                    _, selectedYear, selectedMonth, selectedDayOfMonth ->
                //Toast.makeText(this,"DatePicker works. Selected: $selectedDayOfMonth, $selectedMonth, $selectedYear",Toast.LENGTH_LONG).show()

                //get the date
                val strSelectedDate = "$selectedDayOfMonth ${months[selectedMonth]} $selectedYear"
                var strDatePattern = "$selectedDayOfMonth.${selectedMonth + 1}.$selectedYear"
                val selectedDate: Date = sdf!!.parse(strDatePattern)

                //convert the date
                sdf.applyPattern("ddMMyyyy")
                strDatePattern = sdf.format(selectedDate)

                apiDate = strDatePattern

                binding.content.etDateOfBirth.setText(strSelectedDate)

            }, year,month,day)

        dpd.datePicker.maxDate = (Date().time - 86400000)
        dpd.show()
    }

    object ArrayAdapterFactory{
        fun create(context: Context, id: Int, list: Array<String>):
                ArrayAdapter<String> = object: ArrayAdapter<String>(context,id,list){
            //nothing to do
        }
    }

    object OnItemSelectedListenerFactory{
        fun create() = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
                selected = null
            }
        }
    }

    private fun postData(username: String, gender: String, docId: String, dateOfBirth: String){
        binding.content.pbProgress.visibility = View.VISIBLE

        val context = this
        val user = UserModel(username, gender, docId, dateOfBirth)
        val call = ApiClient.getClient.postUser(user)

        call.enqueue(object: Callback<ApiId>{
            override fun onResponse(call: Call<ApiId>, response: Response<ApiId>) {
                Toast.makeText(this@MainActivity, "Pushed to API",Toast.LENGTH_LONG).show()

                val userId: String = response.body()?.id?:""

                binding.content.pbProgress.visibility = View.GONE

                Data.setUserId(userId, this@MainActivity)

                val intent = Intent(context,VerifyDataActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<ApiId>, t: Throwable) {
                binding.content.pbProgress.visibility = View.GONE
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}