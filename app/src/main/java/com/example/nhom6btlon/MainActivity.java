package com.example.nhom6btlon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    private PartsFragment partsFragment; // Giữ tham chiếu PartsFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Ánh xạ SearchView
        SearchView searchView = findViewById(R.id.searchView);// bắt sự kiện, liên kết với bên activity_main để kết nối
        //Ánh xạ SearchView từ layout để có thể bắt sự kiện và xử lý trong code




        // Load fragment mặc định
        loadFragment(new HomeFragment());

        // Ánh xạ các Button menu
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnParts = findViewById(R.id.btnParts);
        Button btnSignin = findViewById(R.id.btnSignup);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnService = findViewById(R.id.btnService);
        Button btnRescue = findViewById(R.id.btnRescue);

        btnSignin.setOnClickListener(v -> loadFragment(new SigninFragment()));
        btnHome.setOnClickListener(v -> loadFragment(new HomeFragment()));
        btnLogin.setOnClickListener(v -> loadFragment(new LoginFragment()));
        btnService.setOnClickListener(v -> loadFragment(new ServiceFragment()));
        btnRescue.setOnClickListener(v -> loadFragment(new RescueFragment()));

        ImageButton btnCart = findViewById(R.id.imageButton);
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        btnParts.setOnClickListener(v -> {
            partsFragment = new PartsFragment();
            loadFragment(partsFragment);
        });





        // Bắt sự kiện SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //// Đăng ký listener để nhận sự kiện khi người dùng nhập/chỉnh sửa/thực hiện tìm kiếm trên SearchView
            @Override
            public boolean onQueryTextSubmit(String query) {  //khi người dùng nhấn nút trên thanh tìm kiếm thì hàm này sẽ chạy
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {  // Mỗi lần người dùng gõ/xóa ký tự sẽ chạy vào đây// Dùng để filter danh sách theo newText
                //onQueryTextChange là hàm xử lý lọc dữ liệu động mỗi khi có sự thay đổi ký tự trong ô tìm kiếm.
                //Giúp giao diện tìm kiếm mượt mà và tiện lợi cho người dùng.

                if (partsFragment != null && partsFragment.isVisible()) { //Kiểm tra fragment phụ tùng đã được khởi tạo chưa. //Nếu chưa tạo thì không làm gì cả, tránh lỗi NullPointerException.

                    partsFragment.filterProducts(newText);//Kiểm tra fragment "Phụ tùng" có đang được hiển thị trên màn hình không.
                }//Nếu đang ở trang khác (ví dụ: Trang chủ, Dịch vụ,...) thì không lọc dữ liệu (vì filter lúc này là vô nghĩa).
                //Nếu đúng cả 2 điều kiện trên, gọi hàm filterProducts của fragment "Phụ tùng" với từ khóa mới người dùng vừa nhập (newText).
                //Hàm filterProducts sẽ tiếp tục gọi tới adapter.filter(keyword) để lọc danh sách sản phẩm phụ tùng và cập nhật lại RecyclerView.



                return true;//Báo với hệ thống là sự kiện đã được xử lý xong.


            }
        });
    }





    // Hàm riêng để load fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SendMessageActivity.class);
            startActivity(intent);
        });
    }
}
