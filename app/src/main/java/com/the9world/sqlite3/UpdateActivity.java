package com.the9world.sqlite3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.the9world.sqlite3.data.DatabaseHandler;
import com.the9world.sqlite3.model.Contact;

public class UpdateActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;

    Button btnSave;

    // 아이디 받아올 멤버변수
//    int contactId; // 다른 방법으로 받아본다고 주석

    // 이걸 아예 멤버변수로 해서 처음부터 id도 받는다함
    Contact contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // 데이터를 일렬(한 번에)로 받아옴 intent 하는 곳에도 코드입력하고
        // 컨택트에서도 임플레멘츠 해야함
        // Contact는 멤버변수를 선언했으니 맨 앞 Contact를 빼도 됨.
        contact = (Contact) getIntent().getSerializableExtra("contact");
        // Error 뜨면 우클릭 에러해결

        // ↑ getSerializebleExtra로 ↓대신 일렬로 받아올 수 있음.
        // intent로 보낸 데이터를 받아옴.
//        Integer id = getIntent().getIntExtra("id",0);
//        String name = getIntent().getStringExtra("name");
//        String phone = getIntent().getStringExtra("phone");

        // id도 받아옴 (멤버변수 선언)
//        contactId = contact.id; // 다른 방법으로 받기위해 주석

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        editName.setText(contact.name);
        editPhone.setText(contact.phone);

        // 버튼을 누를 경우 수정
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DB 수정, Text를 받아온다.
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(btnSave,
                            "필수 항목을 입력하세요",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                // 여기 contact는 위에서 만든 로컬변수??
                // id는 contact 받아올 때 안에 있음. 따로 꺼내야함
//                Contact contact = new Contact(contactId,name,phone); // 밑에 다른방법
                // 위와 같지만 다른 방법, 데이터를 바꿈
                contact.name = name;
                contact.phone = phone;

                // 수정 된 데이터를 데이터 베이스로 보낸다.
                DatabaseHandler handler = new DatabaseHandler(UpdateActivity.this, "contact_db", null,1);
                // 데이터를 보낼 메서드를 생성한다.(DB Handler 클래스에 메서드 생성)
                handler.updateContact(contact);

                // 저장되었다고 유저에게 Toast를 알려준다.
                Toast.makeText(UpdateActivity.this, "잘 저장되었습니다.",
                                Toast.LENGTH_SHORT).show();
                // 이제 종료한다.
                finish();
            }
        });

    }
}