package com.example.nhom6btlon;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {

    EditText etName, etPhone, etDate, etTime, etNote;
    Button btnSubmit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Ánh xạ
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etNote = findViewById(R.id.etNote);
        btnSubmit = findViewById(R.id.btnSubmitAppointment);

        db = new DatabaseHelper(this);

        // Chọn ngày bằng DatePicker
        etDate.setFocusable(false);
        etDate.setClickable(true);
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        etDate.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Chọn giờ bằng TimePicker
        etTime.setFocusable(false);
        etTime.setClickable(true);
        etTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute1) -> {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        etTime.setText(selectedTime);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        // Xử lý khi nhấn Đặt lịch
        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String note = etNote.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.addAppointment(name, phone, date, time, note);
            if (inserted) {
                Toast.makeText(this, "Đặt lịch hẹn thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ThankYouActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi đặt lịch!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
