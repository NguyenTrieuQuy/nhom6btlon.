package com.example.nhom6btlon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        // Hiển thị cảm ơn
        TextView tvMessage = findViewById(R.id.tvThankYouMessage);
        tvMessage.setText("Cảm ơn bạn đã đặt hàng!\nChúng tôi sẽ liên hệ sớm nhất.");

        // quay về trang chủ
        ImageButton btnBackToHome = findViewById(R.id.imageButton3);
        btnBackToHome.setOnClickListener(v -> {
            // Xử lý khi nhấn nút
            Intent intent = new Intent(ThankYouActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }
}
