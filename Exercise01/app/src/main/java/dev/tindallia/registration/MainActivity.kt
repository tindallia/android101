package dev.tindallia.registration

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import registration.R

import registration.databinding.ActivityMainBinding
import java.util.*

private var selected : String? = null

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var spinner : AutoCompleteTextView

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
            resources.getStringArray(R.array.genderList), spinner
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
                Data.setData(binding.content.etUsername.text.toString(),
                    binding.content.actvGender.text.toString(),
                    binding.content.etDocId.text.toString(),
                    binding.content.etDateOfBirth.text.toString(), this)
                val intent = Intent(this,VerifyDataActivity::class.java)
                startActivity(intent)
                finish()
            }}
    }

    private fun clickDatePicker() {
        val myCal = Calendar.getInstance()
        val year = myCal.get(Calendar.YEAR)
        val month = myCal.get(Calendar.MONTH)
        val day = myCal.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            {
                    _, selectedYear, selectedMonth, selectedDayOfMonth ->
                //Toast.makeText(this,"DatePicker works. Selected: $day, $month, $year",Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDayOfMonth ${months[selectedMonth]} $selectedYear"

                binding.content.etDateOfBirth.setText(selectedDate)

            }, year,month,day)

        dpd.datePicker.maxDate = (Date().time - 86400000)
        dpd.show()
    }

    object ArrayAdapterFactory{
        fun create(context: Context, id: Int, list: Array<String>, _spinner: AutoCompleteTextView):
                ArrayAdapter<String> = object: ArrayAdapter<String>(context,id,list){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)


                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }
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
}