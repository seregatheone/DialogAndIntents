package com.example.a16

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.a16.databinding.FragmentCrimeBinding
import java.time.LocalDateTime
import java.util.*

class CrimeFragment : Fragment(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private var _binding : FragmentCrimeBinding? = null
    private val binding get() = _binding!!

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    private val viewModel : CrimeDetailedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getTime()
        viewModel.crimeDetailedLiveData.observe(viewLifecycleOwner) {
            val crime = it
            binding.textViewType.text = crime.title
            binding.textViewTimeHours.text = crime.timeHours.toString()
            binding.textViewTimeMins.text = crime.timeMins.toString()
            binding.textViewDate.text = crime.date.toString()
            binding.textViewIsSolved.text = crime.isSolved.toString()
        }

        binding.changeTime.setOnClickListener {
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getTime() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)

    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month : Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day
        binding.textViewDate.text = "$year $month $day"
        TimePickerDialog(requireContext(),this,hour,minute,true).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTimeSet(view: TimePicker?, hour: Int, min: Int) {
        this.hour = hour
        this.minute = min
        viewModel.crimeDetailedLiveData.value = ModelCrime(1,"Murder", "${this.year} ${this.month} ${this.day}",
            this.hour,
            this.minute,false)
    }
}