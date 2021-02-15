package com.avatye.example.crk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashroulette.CashRouletteSDK
import com.avatye.cashroulette.ITicketCount
import com.avatye.cashroulette.business.model.GenderType
import com.avatye.cashroulette.business.model.user.Profile
import com.avatye.example.crk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewProfile()

        /** set profile */
        binding.buttonAuth.setOnClickListener {
            if (binding.userId.text.isNullOrEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                setProfile()
            }
        }

        /** start cash roulette */
        binding.buttonEnter.setOnClickListener {
            CashRouletteSDK.start(this)
        }

        /** refresh ticket balance */
        binding.ticketBalance.setOnClickListener {
            ticketBalance()
        }

        /** refresh ticket condition */
        binding.ticketCondition.setOnClickListener {
            ticketCondition()
        }
    }


    private fun viewProfile() {
        val profile = CashRouletteSDK.getProfile()
        if (profile.userID.isNotEmpty()) {
            binding.wrapAuth.visibility = View.GONE
            binding.wrapProfile.visibility = View.VISIBLE
            binding.profileId.text = "ID : ${profile.userID}"
            ticketBalance()
            ticketCondition()
        } else {
            binding.wrapAuth.visibility = View.VISIBLE
            binding.wrapProfile.visibility = View.GONE
        }
    }


    private fun ticketBalance() {
        CashRouletteSDK.getTicketBalance(listener = object : ITicketCount {
            override fun callback(count: Int) {
                binding.ticketBalance.text = "총 티켓 : $count"
                Toast.makeText(
                    this@MainActivity,
                    "TicketBalance:$count",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun ticketCondition() {
        CashRouletteSDK.getTicketCondition(listener = object : ITicketCount {
            override fun callback(count: Int) {
                binding.ticketCondition.text = "받을 수 있는 티켓 : $count"
                Toast.makeText(
                    this@MainActivity,
                    "TicketCondition:$count",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun setProfile() {
        val userID = binding.userId.text.toString()
        val profile = Profile(userID = userID, birthYear = 2000, gender = GenderType.MALE)
        CashRouletteSDK.setProfile(profile)
        viewProfile()
    }
}