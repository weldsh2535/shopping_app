package com.sartor.ui.register

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.StateSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.sartor.R
import com.sartor.data.api.request.SignUpRequest
import com.sartor.data.db.SharedPreference
import com.sartor.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : Fragment() {

    var binding: FragmentSetupBinding? = null
    private val registerViewModel : RegisterViewModel by viewModels()
    private lateinit var sharedPreference: SharedPreference
    private lateinit var gender : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetupBinding.inflate(inflater)

        sharedPreference = SharedPreference(requireContext())


        binding?.btnDone?.setOnClickListener {

            if (binding?.etAddress?.text?.isEmpty() == true || binding?.etFullName?.text?.isEmpty() == true
                || binding?.etMobile?.text?.isEmpty() == true || gender.isEmpty() == true) {
                Toast.makeText(this.requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                registerViewModel.registerUser(
                    SignUpRequest(
                        sharedPreference.getRegisteredUserData().userName,
                        sharedPreference.getRegisteredUserData().password,
                        binding?.etFullName?.text?.toString()!!,
                        binding?.etMobile?.text?.toString()!!,
                        gender,
                        sharedPreference.getRegisteredUserData().userType
                    )

                )

            }

        }

        binding?.male?.setOnClickListener {
            gender = "male"
            binding?.male?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_select_blue, 0, 0, 0)
            binding?.female?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_pink_selector, 0, 0, 0)
            Log.e("GENDER::",gender)
        }

        binding?.female?.setOnClickListener {
            gender = "female"
            binding?.male?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_blue_selector, 0, 0, 0)
            binding?.female?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_select_pink, 0, 0, 0)
            Log.e("GENDER::",gender)
        }


      setUpViewModel()

        return binding?.root
    }




    private fun setUpViewModel() {
      registerViewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
      registerViewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
      registerViewModel.isRegisterSuccessful.observe(viewLifecycleOwner,isRegistrationSuccessful)
  }



    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v("LOGIN", "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        binding?.progressBar?.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v("LOGIN", "onMessageError $it")
        Toast.makeText(context, "Error $it", Toast.LENGTH_SHORT).show()
    }

    private val isRegistrationSuccessful = Observer<Boolean> {
        Log.v("Register", "isViewLoading $it")
        if(it){
            Navigation.findNavController(this.requireActivity(), R.id.host).navigate(
                R.id.action_setupFragment_to_loginFragment
            )

        }else{

            Toast.makeText(context, "Something happened ", Toast.LENGTH_SHORT).show()
        }


    }
    
}