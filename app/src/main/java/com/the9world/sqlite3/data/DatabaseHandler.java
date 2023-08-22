package com.the9world.sqlite3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.the9world.sqlite3.model.Contact;

import java.util.ArrayList;

// (ADD)3-1. SQLite 상속 및 오버라이딩
// todo 오버라이딩 2번(1.onCreate + onUpgrade, 2. DatabaseHandler 생성자(1번)) 해야 함,
public class DatabaseHandler extends SQLiteOpenHelper {

    
    // 생성자
    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 4. 테이블 생성문 작성
        // SQL(query)문 MySQL WorkBench 실행하여 table 생성하면 코드가 나옴.
        // ''빼고 만든다. int 대신 integer 사용해야 한다. /*UNSIGNED NOT NULL,*/ 빠짐
        // VARCHAR 대신 text
        String query = "create table contact ( id integer primary key autoincrement, name text, phone text ); ";
        sqLiteDatabase.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존의 테이블을 삭제하고, 새 테이블 만드는 코드 작성.
    }

        // 필요한 CRUD 관련된 메서드들을 만들어 준다.
    public void addContact(Contact contact){

        SQLiteDatabase db = getWritableDatabase();

        String query = "insert into contact " +
                " (name, phone)" +
                " values " +
                " ( ? ,  ? ); ";

        String[] record = { contact.name,  contact.phone }; // []표시! -> 배열

        // DB가 처리한다.
        db.execSQL(query, record);

        // 작업이 끝나면 DB를 닫는다.
        db.close();
    }

        // 저장된 연락처를 모두 가져오는 메소드.
    public ArrayList<Contact> getAllContacts(){
        SQLiteDatabase db = getWritableDatabase();

        String query = "select * from contact";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Contact> contactArrayList = new ArrayList<>();

            if(cursor.moveToFirst()){
                do {

                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);

                    Contact contact = new Contact(id, name, phone);

                    contactArrayList.add(contact);



                } while (cursor.moveToNext()); // 다음 데이터 가져와서 처리한다. 데이터가 없으면 이 조건문에 안옴.
            }
        return contactArrayList;

        }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        String query = "delete from contact where id = ?";
        String[] record = {contact.id+""};
        db.execSQL(query, record);
        db.close();
    }


    public void updateContact(Contact contact) {
        // 데이터 가져오고
        SQLiteDatabase db = getWritableDatabase();
        // 가져온 데이터를 처리할 query 입력
        String query = "update contact set name = ?, phone = ? where id = ?";

        // 위 ?에 들어갈 record (id는 int니까 String으로 넣기 위해 뒤에 ""를 넣어 String으로 변환)
        String[] record = {contact.name, contact.phone, contact.id+""};

        // query와 record를 적용한다.
        db.execSQL(query, record);

        db.close();

    }


    public ArrayList<Contact> searchMemo(String keyword) {

        SQLiteDatabase db = getWritableDatabase();

        String query = "select from contact where name like '%"+keyword+"%' or phone like '%"+keyword+"%';";

        // null ?로 한 것은 없다.
        Cursor cursor = db.rawQuery(query, null);

        // 비어있는 어레이리스트 만들고
        ArrayList<Contact> contactArrayList = new ArrayList<>();

        // 커서에 들어있는 데이터를 하나씩(행) 가져온다.
        if(cursor.moveToFirst()){
            do {

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                Contact contact = new Contact(id, name, phone);
                //
                contactArrayList.add(contact);



            } while (cursor.moveToNext()); // 다음 데이터 가져와서 처리한다. 데이터가 없으면 이 조건문에 안옴.
        } return contactArrayList;

    }
}
