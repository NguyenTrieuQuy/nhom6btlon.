package com.example.nhom6btlon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tvTotal;
    DatabaseHelper db;
    List<Product> cartList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartListView);
        tvTotal = findViewById(R.id.tvTotal);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            // Tính lại tổng
            int total = 0;
            for (Product p : cartList) {
                try {
                    total += Integer.parseInt(p.price.replaceAll("[^\\d]", ""));
                } catch (Exception e) {
                }
            }

            // Liên kết đến trang thanh toán
            Intent intent = new Intent(CartActivity.this, Payment.class);
            intent.putExtra("total_amount", total);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new DatabaseHelper(this);
        cartList = db.getCartItems();

        adapter = new ProductAdapter(this, cartList, db, true, false); //  hiện nút xóa, ẩn nút thêm giỏ
        recyclerView.setAdapter(adapter);

        // Tính tổng tiền ban đầu
        int total = 0;
        for (Product p : cartList) {
            try {
                total += Integer.parseInt(p.price.replaceAll("[^\\d]", ""));
            } catch (Exception e) {
            }
        }
        tvTotal.setText("Tổng: " + total + "đ");
    }
}
