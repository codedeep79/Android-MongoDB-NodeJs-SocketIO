package com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trungnguyenhau.quanlytaichinh.Adapter.FinancialAdapter;
import com.example.trungnguyenhau.quanlytaichinh.Events.RecyclerItemClickListener;
import com.example.trungnguyenhau.quanlytaichinh.Model.Financial;
import com.example.trungnguyenhau.quanlytaichinh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by TRUNGNGUYENHAU on 5/28/2017.
 */

public class FragmentHienTai extends Fragment implements View.OnClickListener{
    private ImageButton imgBtnAdd, imgBtnEdit, imgBtnDelete;
    private  EditText edtMoneyAdd, edtCategoryAdd, edtTitleAdd, edtNoteAdd;
    private  EditText edtMoneyUpdate, edtCategoryUpdate, edtTitleUpdate, edtNoteUpdate;

    private Button btnAddTransaction, btnCancelTransaction,btnDelete, btnUpdate;
    private Button btnUpdateTransaction,btnCancelUpdateTransaction;
    private String money, category, title, note;

    private JSONArray jsonArray = null;

    private RecyclerView recyclerView;
    private FinancialAdapter financialAdapter;
    private ArrayList<Financial> listFiancial;

    private Dialog dialogAddTransaction;
    private Socket _socket;

    private Dialog dialogDeleteUpdate, dialogUpdate;
    private String _id, dataMoney, dataCategory, dataTitle, dataNote;

    private View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_taichinhhientai, container, false);
        addControls();
        addEvents();

        return view;
    }

    private void addControls() {
        Toast.makeText(getActivity(), "Click Vào Item Để Cập Nhật Hoặc Xóa Dữ Liệu", Toast.LENGTH_SHORT).show();
        listFiancial = new ArrayList<>();
        imgBtnAdd = (ImageButton) view.findViewById(R.id.imagebutton_add);
        imgBtnAdd.setOnClickListener(this);


        dialogAddTransaction = new Dialog(getActivity());
        dialogAddTransaction.setTitle("Add Transaction");
        dialogAddTransaction.setContentView(R.layout.layout_dialog_add_financial);

        edtMoneyAdd = (EditText) dialogAddTransaction.findViewById(R.id.edittext_money);
        edtCategoryAdd = (EditText) dialogAddTransaction.findViewById(R.id.edittext_category);
        edtTitleAdd = (EditText) dialogAddTransaction.findViewById(R.id.edittext_title);
        edtNoteAdd = (EditText) dialogAddTransaction.findViewById(R.id.edittext_note);
        btnAddTransaction = (Button) dialogAddTransaction.findViewById(R.id.button_addtransaction);
        btnAddTransaction.setOnClickListener(this);
        btnCancelTransaction = (Button) dialogAddTransaction.findViewById(R.id.button_cancel_addtransaction);
        btnCancelTransaction.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_taichinhhientai);

        listFiancial.clear();
        getDataCustomer();

        try {
            _socket = IO.socket("http://192.168.0.101:3000");
            _socket.connect();
            _socket.on("addTransaction", addTransaction);
            _socket.on("deleteTransaction", deleteTransaction);
            _socket.on("updateTransaction", updateTransaction);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEvents() {

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        _id = listFiancial.get(position).getId();
                        dataMoney = listFiancial.get(position).getMoney();
                        dataCategory = listFiancial.get(position).getType();
                        dataTitle = listFiancial.get(position).getTitle();
                        dataNote = listFiancial.get(position).getNote();

                        dialogDeleteUpdate = new Dialog(getActivity());
                        dialogDeleteUpdate.setTitle("UPDATE OR DELETE PERSONAL FINANCIL");
                        dialogDeleteUpdate.setContentView(R.layout.layout_delete_update_taichinhcanhan);
                        dialogDeleteUpdate.show();

                        addControlDialog();
                        addEventDialog();
                    }
                })
        );
    }

    private void addControlDialog(){
        btnDelete = (Button) dialogDeleteUpdate.findViewById(R.id.button_delete_taichinhcanhan);
        btnUpdate = (Button) dialogDeleteUpdate.findViewById(R.id.button_update_taichinhcanhan);
    }

    private void addEventDialog(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useDeleteTransaction(_id);
                Toast.makeText(getActivity(), "Delete Transaction Success" , Toast.LENGTH_SHORT).show();
                dialogDeleteUpdate.dismiss();

                listFiancial.clear();
                getDataCustomer();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeleteUpdate.dismiss();

                dialogUpdate = new Dialog(getActivity());
                dialogUpdate.setTitle("UPDATE PERSONAL FINANCIL");
                dialogUpdate.setContentView(R.layout.layout_dialog_update_financial);
                dialogUpdate.show();

                addControlDialogUpdate();
                addEventDialogUpdate();

            }
        });
    }

    private void addControlDialogUpdate(){
        edtMoneyUpdate = (EditText) dialogUpdate.findViewById(R.id.edittext_money);
        edtMoneyUpdate.setText(dataMoney);
        edtCategoryUpdate = (EditText) dialogUpdate.findViewById(R.id.edittext_category);
        edtCategoryUpdate.setText(dataCategory);
        edtTitleUpdate = (EditText) dialogUpdate.findViewById(R.id.edittext_title);
        edtTitleUpdate.setText(dataTitle);
        edtNoteUpdate = (EditText) dialogUpdate.findViewById(R.id.edittext_note);
        edtNoteUpdate.setText(dataNote);

        btnUpdateTransaction = (Button) dialogUpdate.findViewById(R.id.button_update_transaction);
        btnCancelUpdateTransaction = (Button) dialogUpdate.findViewById(R.id.button_cancel_transaction);
    }

    private void addEventDialogUpdate(){
        btnUpdateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money = edtMoneyUpdate.getText().toString();
                category = edtCategoryUpdate.getText().toString();
                title = edtTitleUpdate.getText().toString();
                note = edtNoteUpdate.getText().toString();

                useUpdateTransaction(_id, money, category, title, note);
                Toast.makeText(getActivity(), "Update Transaction Success", Toast.LENGTH_SHORT).show();
                dialogUpdate.dismiss();

                listFiancial.clear();
                getDataCustomer();
            }
        });

        btnCancelUpdateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate.dismiss();
            }
        });
    }

/*------------------------------- Socket.IO ------------------------------*/
    Emitter.Listener addTransaction = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {

            }
            else
            {
            }
        }
    };

    private void useAddTransaction(final String money, final String category, final String title, final String note ) {
        _socket.emit("addTransaction",money, category, title, note);
    }

    Emitter.Listener deleteTransaction = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {

            }
            else
            {
            }
        }
    };

    private void useDeleteTransaction(final String id) {
        _socket.emit("deleteTransaction", id);
    }

    private void useUpdateTransaction(final String id, final String money, final String category, final String title, final String note) {
        _socket.emit("updateTransaction", id, money, category, title, note);
    }

    Emitter.Listener updateTransaction = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {

            }
            else
            {
            }
        }
    };


/*--------------------------------------------------------------------------------*/
    private void getDataCustomer(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.0.101:3000/personal_type";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonArray = new JSONArray(response);
                            for (int i = 0;i < jsonArray.length(); ++i)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                money = jsonObject.getString("money");
                                category = jsonObject.getString("category");
                                title = jsonObject.getString("title");
                                note = jsonObject.getString("note");
                                _id = jsonObject.getString("_id");

                                //listFinancial.add(new Financial(typeFinancial));

                                listFiancial.add(new Financial(_id, money, category, title, note));
                                financialAdapter = new FinancialAdapter(getActivity(), listFiancial);


                                RecyclerView.LayoutManager layoutManager
                                        = new GridLayoutManager(getActivity(), 1);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(financialAdapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity()
                                , "Lỗi Trong Quá Trình Thực Thi Trên Server"
                                , Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);// Thực thi những yêu cầu mà chúng ta gửi đi
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button_addtransaction:
                money = edtMoneyAdd.getText().toString();
                category = edtCategoryAdd.getText().toString();
                title = edtTitleAdd.getText().toString();
                note = edtNoteAdd.getText().toString();

                useAddTransaction(money, category, title, note);
                dialogAddTransaction.dismiss();

                Toast.makeText(getActivity(), "Thêm Chi Tiêu Thành Công", Toast.LENGTH_LONG).show();
                listFiancial.clear();
                getDataCustomer();
                break;
            case R.id.imagebutton_add:
                dialogAddTransaction.show();
                break;
            case R.id.button_cancel_addtransaction:
                dialogAddTransaction.dismiss();
                break;
        }
    }
}
