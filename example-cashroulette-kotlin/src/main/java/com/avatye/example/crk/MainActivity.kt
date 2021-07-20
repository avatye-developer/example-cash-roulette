package com.avatye.example.crk

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashroulette.CashRouletteSDK
import com.avatye.cashroulette.ITicketCount
import com.avatye.cashroulette.business.model.GenderType
import com.avatye.cashroulette.business.model.user.Profile
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewProfile()

        /** set profile */
        button_auth.setOnClickListener {
            if (user_id.text.isNullOrEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                setProfile()
            }
        }

        /** start cash roulette */
        button_enter.setOnClickListener {
            CashRouletteSDK.start(this)
        }

        /** refresh ticket balance */
        ticket_balance.setOnClickListener {
            ticketCondition()
        }

        /** refresh ticket condition */
        ticket_condition.setOnClickListener {
            ticketCondition()
        }
    }


    private fun viewProfile() {
        val profile = CashRouletteSDK.getProfile()
        if (profile.userID.isNotEmpty()) {
            wrap_auth.visibility = View.GONE
            wrap_profile.visibility = View.VISIBLE
            profile_id.text = "ID : ${profile.userID}"
            ticketCondition()
        } else {
            wrap_auth.visibility = View.VISIBLE
            wrap_profile.visibility = View.GONE
        }
    }


    private fun ticketCondition() {
        CashRouletteSDK.getTicketCondition(listener = object : ITicketCount {
            override fun callback(balance: Int, condition: Int) {
                ticket_balance.text = "총 티켓 : $balance"
                ticket_condition.text = "받을 수 있는 티켓 : $condition"
            }
        })
    }


    private fun setProfile() {
        val userID = user_id.text.toString()
        val profile = Profile(userID = userID, birthYear = 2000, gender = GenderType.MALE)
        CashRouletteSDK.setProfile(profile)
        viewProfile()
    }
}