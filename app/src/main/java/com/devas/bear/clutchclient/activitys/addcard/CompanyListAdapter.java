package com.devas.bear.clutchclient.activitys.addcard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devas.bear.clutchclient.R;
import com.devas.bear.clutchclient.model.Company;

import java.util.List;

/**
 * Created by script972 on 27.08.2017.
 */

class CompanyListAdapter  extends RecyclerView.Adapter<CompanyListAdapter.MyViewHolder> {
    private List<Company> companys;
    private Context context;


    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView alphaSign;
        public ImageView logoCompany;
        public TextView titleCompany;

        public MyViewHolder(View view) {
            super(view);

            alphaSign = (TextView) view.findViewById(R.id.alpha_sign);
            logoCompany = (ImageView) view.findViewById(R.id.logo_company);

            titleCompany= (TextView) view.findViewById(R.id.title_company);

        }
    }

    public CompanyListAdapter(Context context, List<Company> countryList) {
        this.companys = countryList;
        this.context=context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Company c = companys.get(position);
        holder.titleCompany.setText(c.getTitle());

        //Заполняем первую букву если следующия буква алфавита
        holder.alphaSign.setText("");
        if ((position > 0) && (companys.get(position - 1) != null) && companys.get(position - 1).getTitle().charAt(0) != companys.get(position).getTitle().charAt(0)){
            holder.alphaSign.setText(c.getTitle().substring(0, 1));
            Log.i("search", "alpha sign");
        } else
            if(position==0){
                holder.alphaSign.setText(c.getTitle().substring(0, 1));

            }


    }

    @Override
    public int getItemCount() {
        return companys.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_card,parent, false);
        return new MyViewHolder(v);
    }

}