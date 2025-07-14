package com.example.nhom6btlon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SendMessageActivity extends AppCompatActivity {

    EditText phoneEditText, messageEditText;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        phoneEditText = findViewById(R.id.phoneEditText);
        messageEditText = findViewById(R.id.messageEditText);
        sendBtn = findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString().trim();
                String message = messageEditText.getText().toString().trim();

                if (!phone.isEmpty() && !message.isEmpty()) {
                    try {
                        // Sử dụng ACTION_SENDTO (chuẩn hơn ACTION_VIEW)
                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                        smsIntent.setData(Uri.parse("smsto:" + Uri.encode(phone)));
                        smsIntent.putExtra("sms_body", message);
                        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(smsIntent);
                    } catch (Exception e) {
                        Toast.makeText(SendMessageActivity.this, "Không tìm thấy ứng dụng SMS!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SendMessageActivity.this, "Vui lòng nhập số điện thoại và nội dung tin nhắn!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
