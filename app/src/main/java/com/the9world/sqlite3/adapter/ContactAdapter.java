package com.the9world.sqlite3.adapter;

import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.voice.VoiceInteractionSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.the9world.sqlite3.AddActivity;
import com.the9world.sqlite3.MainActivity;
import com.the9world.sqlite3.R;
import com.the9world.sqlite3.UpdateActivity;
import com.the9world.sqlite3.data.DatabaseHandler;
import com.the9world.sqlite3.model.Contact;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<Contact> contactArrayList;


    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 아래 코드의 대한 설명.
        // LayoutInflater 클래스의 정적 메서드인 from()을 호출하여 LayoutInflater 객체를 생성.
        // rom() 메서드는 주어진 Context를 기반으로 LayoutInflater 객체를 생성
        // parent.getContext()는 View 객체가 속한 컨텍스트를 반환
        // LayoutInflater 객체의 inflate() 메서드를 호출하여 XML 레이아웃 파일을 인플레이션(파싱)하고,
        // 해당 레이아웃을 View 객체로 반환,
        // R.layout.memo_row는 인플레이션할 XML 레이아웃 파일의 리소스 ID.
        // parent는 View 객체가 속한 부모 ViewGroup을 나타내며, 인플레이션된 뷰를 부모에게 추가할 때 사용
        // attachToRoot: 매개변수 false는 부모 ViewGroup에 바로 추가하지 않고, 인플레이션된 뷰를 반환하기 위해 사용
// 즉, R.layout.conteact_row라는 XML 레이아웃 파일을 인플레이션하여 View 객체로 변환하고,
// 해당 View 객체를 view 변수에 할당하는 것. 이렇게 생성된 view 객체는 나중에 화면에 표시되어 사용자에게 보여질 수 있다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        // 메서드의 리턴 타입을 내가 만든 뷰홀더로 바꾼다. (기존엔 그냥 뷰 홀더 였음)
        return new ContactAdapter.ViewHolder(view); // findViewById가 가능해진다.
    }

    // 화면과 데이터를 매칭시켜서, 실제로 ""데이터를 화면에 적용시키는 메서드""
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = contactArrayList.get(position); // 위 position이 (RecyclerView 행 위치)

        // 데이터를 화면에 적용 시킨다.
        holder.txtName.setText(contact.name);
        holder.txtPhone.setText(contact.phone);
    }

    // 데이터의 개수 : ( 행의 개수 )
    // ArrayList에 저장된 요소의 개수를 확인할 수 있으며,
    // 이를 통해 ArrayList의 크기나 비어 있는지 여부 등을 확인할 수 있다.
    @Override
    public int getItemCount() {
        // return 값을 내가 만든 ArrayList의 객체의 개수(크기)로 변경
        return contactArrayList.size(); // 기존의 0이다. 꼭 바꾸자.
    }

    // 1. inner class "뷰홀더" 만든다.
    // RecyclerView.ViewHolder 상속, 오버라이딩(Alt+Enter)
    // 이 클래스에는 행 화면에 있는 뷰 들을 여기서 연결시킨다.
    // 밖에서 따로 클래스를 만들어도 되지만 유지보수를 위해서 이너클래스로 생성한다.
    public class ViewHolder extends RecyclerView.ViewHolder {

        // contact_row(CardView) View를 해당 클래스 멤버변수 만든다.
        TextView txtName;
        TextView txtPhone;
        ImageView imageDelete;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // R 일단 입력하고 추후 알트 엔터로 첫번째꺼 임폴트ㄱ
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imageDelete = itemView.findViewById(R.id.imgDelete);

            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // UpdateActivity를 실행하는 코드를 여기에 작성
                    Intent intent = new Intent(context, UpdateActivity.class);

                    int index = getAdapterPosition();

                    Contact contact = contactArrayList.get(index);

                    intent.putExtra("id", contact.id);
                    intent.putExtra("name", contact.name);
                    intent.putExtra("phone", contact.phone);

                    // 이거 사용하려면 Contant에서 implements Serializable
                    intent.putExtra("contact", contact);

                    // startActivity는 AppCompatActivity의 메서드 인데
                    // MainActivity는 AppCompatActivity를 상속받아서 startActivity가 있지만,
                    // ContactAdapter 클래스는 AppCompatActivity를 상속 받지 않았으므로 해당 메서드가 없다.
                    context.startActivity(intent);

                }
            });

            // 삭제할 때 다이얼로그를 띄운다.
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 알러트다이얼로그 메서드를 호출하여 다이얼로그를 띄운다.
                    showAlertDiaLog();
                }
            });
        }

        private void showAlertDiaLog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("삭제").setMessage("정말 삭제하시겠습니까?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // 인덱스를 가져온다.
                    int index = getAdapterPosition();

                    // DB에서 삭제
                    DatabaseHandler handler = new DatabaseHandler(context, "contact db", null, 1);

                    // id가 필요한데, Contact 가 묶음처리하니까
                    // 객체를 먼저 가져온다. 그 안에 들어있으니까!!!
                    Contact contact = contactArrayList.get(index);

                    handler.deleteContact(contact);

                    contactArrayList.remove(index);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();

        }

    }
}



