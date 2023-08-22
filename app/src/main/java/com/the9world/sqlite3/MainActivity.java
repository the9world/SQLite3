package com.the9world.sqlite3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.the9world.sqlite3.adapter.ContactAdapter;
import com.the9world.sqlite3.data.DatabaseHandler;
import com.the9world.sqlite3.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // (0714_검색보여주기) 기능용
    EditText editSearch;
    ImageView imgCancel;


    Button btnAdd;

    RecyclerView recyclerView;
    ContactAdapter adapter;

    // Cotact 클래스의 객체들을 ArrayList로 저장하기 위해 사용
    // contactArrayList는 변수명,
// ArrayList로<Contact>클래스의 객체들을 저장하기 위한 비어있는 ArrayList 변수(contactArrayList)를 만든다.
// 즉 contactArrayList는 Contact클래스 객체(변수)들을 내포한 ArrayList 변수
// 예 ) contactArrayList = id[], name[], phone[]
// contactArrayList는 멤버 변수로 선언 되었으니 아래 어떤 메서드에서도 값을 CRUD 할 수 있음
// Contact는 제네릭타입 매개변수(데이터 타입을 일반화(편의용으로 만듬))
    ArrayList<Contact> contactArrayList = new ArrayList<>();
    // ★ 검색 텍스트 보이기(↑ 주석처리, ↓ 검색용 코드)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        // 8. 리사이클러뷰 초기화 작업
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // 검색기능용 (2개)
        editSearch = findViewById(R.id.editSearch);
        imgCancel = findViewById(R.id.imgCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });


        // (0714_검색보여주기) 누를때마다 동작
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 유저가 타이핑할 때마다 동작
                String keyword = editSearch.getText().toString().toString();
//                // 키워드가 비어있으면 리턴(아무 동작하지 않는다.)
//                if (keyword.isEmpty()){
//                    return;
//                }

                // DB에서 검색 !
                DatabaseHandler db = new DatabaseHandler(MainActivity.this, "contact_db", null, 1);
//                contactArrayList = db.searchMemo(keyword);
                // ★ 검색 텍스트 보이기, 2번 (↑ 주석처리, ↓ 검색용 코드)
                contactArrayList.clear();
                contactArrayList.addAll(db.searchMemo(keyword));

                // DB에서 검색했으면! 결과를 화면에 보여준다.(방법 3개)
                // 1번은 새로 다 짠다.
                // ★ 바로 아래 2개는 검색용 잠시 주석
//                adapter = new ContactAdapter(MainActivity.this, contactArrayList);
//                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                // 2번 페이징 처리( 코드 수정 필요) ★입력 코드수정


            }
        });

        // (0714_검색보여주기) 누를때마다 동작
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "contact_db", null, 1);
                contactArrayList.clear();
                contactArrayList.addAll(handler.getAllContacts());
                adapter.notifyDataSetChanged();
                editSearch.setText("");

            }
        });

    }


    // 데이터베이스 핸들러 후에 하는거다
    // contactArrayList에 데이터 있어야 한다.
    // DB에서 가져오면 된다.
    // 메서드 생성
    @Override
    public void onResume() {
        super.onResume();

        // contactArrayList 에 데이터 있어야 한다.
        // DB에서 가져온다.
        DatabaseHandler handler = new DatabaseHandler(MainActivity.this, "contact_db", null, 1 );

//        contactArrayList = handler.getAllContacts();
        // ★ 검색 텍스트 보이기, 2번 (↑ 주석처리, ↓ 검색용 코드)
        contactArrayList.clear();
        contactArrayList.addAll(handler.getAllContacts());


        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);
    }
}
