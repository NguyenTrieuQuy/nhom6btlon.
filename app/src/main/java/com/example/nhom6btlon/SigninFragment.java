package com.example.nhom6btlon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SigninFragment extends Fragment {

    EditText nameInput, phoneInput, passwordInput;
    Button btnSignUp;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin, container, false);

        nameInput = view.findViewById(R.id.editTextText);
        phoneInput = view.findViewById(R.id.editTextTextEmailAddress5);
        passwordInput = view.findViewById(R.id.editTextTextPassword);
        btnSignUp = view.findViewById(R.id.btn_đk);

        db = new DatabaseHelper(getActivity());

        btnSignUp.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String password = passwordInput.getText().toString();

            boolean inserted = db.insertUser(name, phone, password);

            if (inserted) {
                Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                // nhảy qua trang login
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .commit();

            } else {
                Toast.makeText(getActivity(), "Tên đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
