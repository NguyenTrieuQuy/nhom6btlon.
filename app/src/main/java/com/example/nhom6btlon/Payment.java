package com.example.nhom6btlon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity {

    EditText etName, etPhone, etEmail, etProvince, etDistrict, etAddress;
    TextView tvTotalPayment;
    Button btnPlaceOrder;
    DatabaseHelper db;
    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Ánh xạ các view
        etName = findViewById(R.id.editTextTextEmailAddress2);
        etPhone = findViewById(R.id.editTextTextEmailAddress3);
        etEmail = findViewById(R.id.editTextTextEmailAddress7);
        etProvince = findViewById(R.id.editTextTextEmailAddress6);
        etDistrict = findViewById(R.id.editTextTextEmailAddress10);
        etAddress = findViewById(R.id.editTextTextEmailAddress8);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        btnPlaceOrder = findViewById(R.id.button3);

        // tổng tiền từ giỏ hàng
        totalAmount = getIntent().getIntExtra("total_amount", 0);
        tvTotalPayment.setText("Tổng thanh toán: " + totalAmount + "đ");

        db = new DatabaseHelper(this);

        //đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String province = etProvince.getText().toString().trim();
            String district = etDistrict.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            // kiểm  tra thông tin
            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập họ tên và số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.addPayment(name, phone, email, province, district, address, totalAmount);

            if (inserted) {
                Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();


                db.clearCart();

                // nhảy sang trang cảm ơn
                Intent intent = new Intent(Payment.this, ThankYouActivity.class);
                startActivity(intent);
                finish();
            }

        else {
                Toast.makeText(this, "Lỗi khi đặt hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
