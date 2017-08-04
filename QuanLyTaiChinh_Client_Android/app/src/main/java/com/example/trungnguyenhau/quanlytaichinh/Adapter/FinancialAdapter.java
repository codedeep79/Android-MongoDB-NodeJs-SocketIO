package com.example.trungnguyenhau.quanlytaichinh.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trungnguyenhau.quanlytaichinh.Model.Financial;
import com.example.trungnguyenhau.quanlytaichinh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRUNGNGUYENHAU on 6/15/2017.
 */

public  class FinancialAdapter extends RecyclerView.Adapter<FinancialAdapter.ViewHolder>
{

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button btnDelete, btnUpdate;
        private Dialog dialogDeleteUpdate;

        private TextView txtMoneyHolder;
        private TextView txtCategoryHolder;
        private TextView txtTitleHolder;
        private TextView txtNoteHolder;
        private Context context;
        private List<Financial> financials;

        public ViewHolder(View itemView, Context context, List<Financial> financials) {
            super(itemView);
            this.context = context;
            this.financials = financials;

            itemView.setOnClickListener((View.OnClickListener)this);
            txtMoneyHolder = (TextView) itemView.findViewById(R.id.textview_money);
            txtCategoryHolder = (TextView) itemView.findViewById(R.id.textview_category);
            txtTitleHolder = (TextView) itemView.findViewById(R.id.textview_title);
            txtNoteHolder = (TextView) itemView.findViewById(R.id.textview_note);

        }


        @Override
        public void onClick(View view) {

        }



    }

    private Context context;
    private List<Financial> financials;
    private View view;
    public FinancialAdapter(Context context, List<Financial> financials) {
        this.context = context;
        this.financials = financials;
    }

    @Override
    public FinancialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.layout_adapter_chitieu, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, context, financials);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FinancialAdapter.ViewHolder holder, final int position) {
        final Financial financial = financials.get(position);

        holder.txtMoneyHolder.setText("Số Tiền: " + financial.getMoney());
        holder.txtCategoryHolder.setText("Loại Chi Tiêu: " + financial.getType());
        holder.txtTitleHolder.setText("Tên Loại Chi Tiêu: " + financial.getTitle());
        holder.txtNoteHolder.setText("Ghi Chú: " + financial.getNote());


    }

    @Override
    public int getItemCount() {
        return financials.size();
    }
}
/*public class FinancialAdapter extends ArrayAdapter<Financial> {

    Activity context = null;
    int layout;
    ArrayList<Financial> financials = null;

    class ViewHolder{
        private TextView txtMoneyHolder;
        private TextView txtCategoryHolder;
        private TextView txtTitleHolder;
        private TextView txtNoteHolder;

        public ViewHolder(View view) {
            this.txtMoneyHolder = (TextView) view.findViewById(R.id.textview_money);
            this.txtCategoryHolder = (TextView) view.findViewById(R.id.textview_category);
            this.txtTitleHolder = (TextView) view.findViewById(R.id.textview_title);
            this.txtNoteHolder = (TextView) view.findViewById(R.id.textview_note);
        }
    }

    public FinancialAdapter(Activity context, @LayoutRes int resource, ArrayList<Financial> financials) {
        super(context, resource);
        this.context = context;
        this.layout = resource;
        this.financials = financials;
    }

    // Mỗi 1 dòng trong listview nó sẽ thục hiện getView một lần
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        Financial financial = financials.get(position);
        if (financial != null)
        {
            viewHolder.txtMoneyHolder.setText(financial.getMoney().toString());
            viewHolder.txtCategoryHolder.setText(financial.getType().toString());
            viewHolder.txtTitleHolder.setText(financial.getTitle().toString());
            viewHolder.txtNoteHolder.setText(financial.getNote().toString());
        }
        return view;
    }
}*/
