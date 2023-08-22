package com.the9world.sqlite3.model;

import java.io.Serializable;


// 이게뭐냐고 시리얼라이저블 데이터를 일렬도 보내준다는데 뭔소리여
public class Contact implements Serializable {

    // 5-1. 퍼블릭으로 변경하여 접근가능하게 한.
    
    // Database에서 사용할 id 값
    public int id;

    public String name;
    public String phone;

    // 2-1. 생성자 생성
    public Contact(String name, String phone){
        this.name = name;
        this.phone =phone;
    }

    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

}
