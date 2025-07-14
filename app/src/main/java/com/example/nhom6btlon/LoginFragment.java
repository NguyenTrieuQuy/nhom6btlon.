package com.example.nhom6btlon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    EditText nameInput, passwordInput;
    Button btnLogin;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        nameInput = view.findViewById(R.id.editTextTextEmailAddress4);
        passwordInput = view.findViewById(R.id.editTextTextPassword4);
        btnLogin = view.findViewById(R.id.btn_dangnhap);

        db = new DatabaseHelper(getActivity());

        btnLogin.setOnClickListener(v -> {
            //lấy dữ liệu
            String name = nameInput.getText().toString();
            String password = passwordInput.getText().toString();
        //kiểm tra thông tin
            boolean isValid = db.checkUser(name, password);

            if (isValid) {
                Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // Chuyển sang trang chủ
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();

            } else {
                Toast.makeText(getActivity(), "Sai tên hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        // Quên mk
        view.findViewById(R.id.btn_quenmk).setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SigninFragment())
                    .commit();
        });

        return view;
    }
}
