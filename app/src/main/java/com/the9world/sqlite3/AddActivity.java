package com.the9world.sqlite3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.the9world.sqlite3.data.DatabaseHandler;
import com.the9world.sqlite3.model.Contact;

public class AddActivity extends AppCompatActivity {

    // (ADD)1. View를 멤버변수로 지정한다.
    EditText editName;
    EditText editPhone;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(btnSave,
                            "필수항목을 입력하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // 2. 이름과 전화번호가 있으니까, 저장 (행 정보가 있어야함.)
                // 데이터를 묶음으로 처리할 클래스도 필요 (Contact 클래스 생성).
                // Contact 클래스 멤버변수(메모리)에 저장 됨.
                Contact contact = new Contact(name, phone); // Contact 생성자 필요

                // 5-1. todo-- DB 에 저장한다. (DB용 클래스 생성한다.) SQLite(경랑DB)
                // DatabaseHandler(SQLtie 클래스)
                // 5-2 db handler 를 만든다. (DatabaseHandler 메서드의 4개의 항목)
                // add액티비티를 담고, 스키마명 지정, 팩토리, 버전
                DatabaseHandler handler = new DatabaseHandler(AddActivity.this, "contact_db", null, 1 );
                handler.addContact(contact);

                Toast.makeText(AddActivity.this,
                        "잘 저장되었습니다.",
                        Toast.LENGTH_SHORT).show();

                finish();

                // MainActivity 가서 데이터를 저장한 데이터를 띄우는 ??거 암튼
                // onResume 메서드 작성
            }
        });


    }
}