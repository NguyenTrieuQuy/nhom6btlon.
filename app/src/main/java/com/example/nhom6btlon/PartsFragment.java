package com.example.nhom6btlon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PartsFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseHelper db;
    List<Product> list;
    ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parts, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        db = new DatabaseHelper(getContext());

        // Thêm phụ tùng nếu chưa có
        if (db.getAllProducts().isEmpty()) {
            db.insertProduct("MÁ PHANH", "2.000.000đ", R.drawable.brakepad);
            db.insertProduct("LỌC GIÓ", "500.000đ", R.drawable.locgiomaylanh);
            db.insertProduct("CA LĂNG", "1.000.000đ", R.drawable.calang);
        }

        list = db.getAllProducts();
        adapter = new ProductAdapter(getContext(), list, db, false, true); // ẩn nút xóa, hiện thêm giỏ

        recyclerView.setAdapter(adapter);

        return view;
    }

    // Hàm filter này sẽ được gọi từ MainActivity khi người dùng nhập từ khóa
    public void filterProducts(String keyword) { //filterProducts là "cầu nối" giữa MainActivity và Adapter của RecyclerView, giúp cập nhật dữ liệu sản phẩm khi tìm kiếm.
        if (adapter != null) {
            adapter.filter(keyword);//Nếu adapter đã tồn tại, hàm sẽ gọi tiếp filter(keyword) của adapter.
        }//Hàm filter(keyword) bên ProductAdapter sẽ thực hiện việc lọc lại list sản phẩm theo keyword, sau đó cập nhật lại RecyclerView để chỉ hiện sản phẩm phù hợp.
    }
}//Người dùng gõ vào thanh tìm kiếm: "lọc gió"
//MainActivity gọi: partsFragment.filterProducts("lọc gió");
//Trong PartsFragment, hàm này được thực thi: gọi adapter.filter("lọc gió");
//Adapter lọc lại danh sách và RecyclerView chỉ hiển thị những sản phẩm chứa "lọc gió" trong tên.
