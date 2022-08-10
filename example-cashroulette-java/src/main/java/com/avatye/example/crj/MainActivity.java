package com.avatye.example.crj;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.avatye.cashroulette.CashRouletteSDK;
import com.avatye.cashroulette.INotificationStatus;
import com.avatye.example.crj.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** view binding */
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        /** view profile */
        viewProfile();

        /** set profile */
        binding.buttonAuth.setOnClickListener(v -> {
            if (binding.userId.getText() == null || TextUtils.isEmpty(binding.userId.getText())) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            } else {
                setProfile();
            }
        });

        /** start cash roulette */
        binding.buttonEnter.setOnClickListener(v -> {
            CashRouletteSDK.start(this);
        });

        /** refresh ticket balance */
        binding.ticketBalance.setOnClickListener(v -> {
            ticketCondition();
        });

        /** refresh ticket condition */
        binding.ticketCondition.setOnClickListener(v -> {
            ticketCondition();
        });

        CashRouletteSDK.setNotificationStatus(this, true, new INotificationStatus() {
            @Override
            public void onStatus(boolean isActive, int code, @NonNull String reason) {
                Log.d("Notification", "isActive:" + isActive + ", code:" + code + ", reason:" + reason);
            }
        });
    }


    void viewProfile() {
        String appUserID = CashRouletteSDK.getAppUserID();
        if (!TextUtils.isEmpty(appUserID)) {
            binding.wrapAuth.setVisibility(View.GONE);
            binding.wrapProfile.setVisibility(View.VISIBLE);
            binding.profileId.setText("ID : " + appUserID);
            ticketCondition();
        } else {
            binding.wrapAuth.setVisibility(View.VISIBLE);
            binding.wrapProfile.setVisibility(View.GONE);
        }
    }


    void ticketCondition() {
        CashRouletteSDK.getTicketCondition((balance, condition) -> {
            binding.ticketBalance.setText("총 티켓 : " + balance);
            binding.ticketCondition.setText("받을 수 있는 티켓 : " + condition);
        });
    }


    void setProfile() {
        String appUserID = binding.userId.getText().toString();
        CashRouletteSDK.setAppUserID(appUserID);
        viewProfile();
    }
}