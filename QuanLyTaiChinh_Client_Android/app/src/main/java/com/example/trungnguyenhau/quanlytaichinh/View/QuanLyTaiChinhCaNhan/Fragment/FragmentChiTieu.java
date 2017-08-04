package com.example.trungnguyenhau.quanlytaichinh.View.QuanLyTaiChinhCaNhan.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trungnguyenhau.quanlytaichinh.Adapter.FinancialAdapter;
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

public class FragmentChiTieu extends Fragment implements View.OnClickListener{
    private ImageButton imgBtnAdd;
    private ListView lstTypeFinancial;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList;

    private JSONArray jsonArray = null;
    private String typeFinancial = null;

    Dialog dialogAdd, dialogUpdate;
    EditText edtTypeFinancialDialog, edtTypeFinancialDialogUpdate;
    Button btnAdd, btnCancelAdd, btnUpdate, btnCancelUpdate;

    private ProgressDialog progressDialog;
    private Socket _socket;
    String typeListView = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        getDataCustomer();


        View view = inflater.inflate(R.layout.layout_chitieu, container, false);
        lstTypeFinancial = (ListView) view.findViewById(R.id.listview_type) ;

        arrayList = new ArrayList<>();

        imgBtnAdd = (ImageButton) view.findViewById(R.id.imagebutton_add);
        imgBtnAdd.setOnClickListener(this);


        //financialTypeAdapter
        //        = new FinancialAdapter(getActivity(),
        //        android.R.layout.simple_list_item_1, listFinancial);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        lstTypeFinancial.setAdapter(arrayAdapter);

        addControls();
        addEvents();
        return view;
    }

    private void addControls() {
        Toast.makeText(getActivity(), "Click Vào Item Để Cập Nhật Hoặc Xóa Dữ Liệu", Toast.LENGTH_SHORT).show();

        dialogAdd = new Dialog(getActivity());
        dialogAdd.setContentView(R.layout.layout_dialog_add_financial_type);
        dialogAdd.setTitle("Thêm Loại Tài Chính");

        dialogUpdate = new Dialog(getActivity());
        dialogUpdate.setContentView(R.layout.layout_dialog_update_financial_type);
        dialogUpdate.setTitle("Cập Nhật Thể Loại Tài Chính");

        edtTypeFinancialDialog = (EditText) dialogAdd.findViewById(R.id.edittext_dialog_them_tc);
        btnAdd = (Button) dialogAdd.findViewById(R.id.button_add);
        btnAdd.setOnClickListener(this);
        btnCancelAdd = (Button) dialogAdd.findViewById(R.id.button_cancel);
        btnCancelAdd.setOnClickListener(this);

        edtTypeFinancialDialogUpdate = (EditText) dialogUpdate.findViewById(R.id.edittext_dialog_capnhat_tc);
        btnUpdate = (Button) dialogUpdate.findViewById(R.id.button_update);
        btnUpdate.setOnClickListener(this);
        btnCancelUpdate = (Button) dialogUpdate.findViewById(R.id.button_cancel_update);
        btnCancelUpdate.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        try {
            _socket = IO.socket("http://192.168.0.101:3000");
            _socket.connect();
            _socket.on("addTypeFinancial", onAddTypeFinancial);
            _socket.on("deleteTypeFinancial", onDeleteTypeFinancial);
            _socket.on("updateTypeFinancial", onUpdateTypeFinancial);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    Emitter.Listener onAddTypeFinancial = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {
            }
            else
            {
            }

            hideDialog();
        }
    };
    Emitter.Listener onDeleteTypeFinancial = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {
            }
            else
            {
            }

            hideDialog();
        }
    };
    Emitter.Listener onUpdateTypeFinancial = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            if (data == "true")
            {
            }
            else
            {
            }

            hideDialog();
        }
    };

    /*----- Socket.IO -----*/
    private void checkAddTypeFinancial(final String type) {
        _socket.emit("addTypeFinancial",type);
    }
    private void checkDeleteTypeFinancial(final String type) {
        _socket.emit("deleteTypeFinancial",type);
    }
    private void checkUpdateTypeFinancial(final String Oldtype, final String newType) {
        _socket.emit("updateTypeFinancial",Oldtype, newType);
    }

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Delete Financial Type");
        menu.add("Update Financial Type");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle() == "Delete Financial Type"){
            checkDeleteTypeFinancial(typeListView);
            Toast.makeText(getActivity(), "Đã Xóa Thể Loại.", Toast.LENGTH_LONG).show();

            arrayAdapter.clear();
            getDataCustomer();
        }

        if (item.getTitle() == "Update Financial Type"){
            edtTypeFinancialDialogUpdate.setText(typeListView);
            dialogUpdate.show();
        }
        return super.onContextItemSelected(item);
    }

    private void addEvents() {
        lstTypeFinancial.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(lstTypeFinancial);
                typeListView = arrayList.get(position).toString();
                return false;
            }
        });
    }

    private void getDataCustomer(){
        // Thực thi những yêu cầu mà chúng ta gửi đi
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.0.101:3000/financial";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonArray = new JSONArray(response);
                            for (int i = 0;i < jsonArray.length(); ++i)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                typeFinancial = jsonObject.getString("typename");

                                //listFinancial.add(new Financial(typeFinancial));
                                arrayList.add(typeFinancial);
                                arrayAdapter.notifyDataSetChanged();
                                lstTypeFinancial.setAdapter(arrayAdapter);
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

        requestQueue.add(stringRequest);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imagebutton_add:

                dialogAdd.show();
                break;
            case R.id.imagebutton_edit:
                Toast.makeText(getActivity(), "Sửa Chi Tiêu Cá Nhân", Toast.LENGTH_LONG).show();
                break;
            case R.id.imagebutton_delete:
                Toast.makeText(getActivity(), "Xóa Chi Tiêu Cá Nhân", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_cancel:
                dialogAdd.dismiss();
                break;
            case R.id.button_add:
                String type = edtTypeFinancialDialog.getText().toString();
                if (!type.trim().isEmpty())
                {
                    checkAddTypeFinancial(type);
                    dialogAdd.dismiss();
                    Toast.makeText(getActivity(), "Thêm Thể Loại Thành Công", Toast.LENGTH_LONG).show();

                    arrayAdapter.clear();
                    getDataCustomer();
                }
                else
                {
                    Toast.makeText(getActivity(), "Vui Lòng Nhập Tên Thể Loại", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.button_update:
                String oldType = typeListView;
                String newType = edtTypeFinancialDialogUpdate.getText().toString().trim();
                if (!newType.isEmpty())
                {

                    checkUpdateTypeFinancial(oldType, newType);
                    dialogUpdate.dismiss();

                    arrayList.clear();
                    getDataCustomer();
                    Toast.makeText(getActivity(), "Đã Cập Nhật Thể Loại.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Cập Nhật Bị Lỗi.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_cancel_update:
                dialogUpdate.dismiss();
                break;
        }
    }
}
