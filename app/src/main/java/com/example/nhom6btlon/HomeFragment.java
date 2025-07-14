package com.example.nhom6btlon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //nút liên kết đến trang đặt lịch
        ImageButton imageButton4 = view.findViewById(R.id.imageButton4);

        imageButton4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AppointmentActivity.class);
            startActivity(intent);
        });

        return view;
    }}


