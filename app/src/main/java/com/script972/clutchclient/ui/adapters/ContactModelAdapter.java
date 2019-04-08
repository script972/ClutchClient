package com.script972.clutchclient.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.domain.api.model.ContactModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactModelAdapter extends RecyclerView.Adapter<ContactModelAdapter.ViewHolder>{

    /**
     * Company list for display of adapter
     */
    List<ContactModel> items;

    public ContactModelAdapter(List<ContactModel> lists) {
        this.items=lists;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView phoneName;
        TextView phoneNumber;
        ImageView phoneClient;
        ImageView phonePhoto;
        CheckBox phoneChecker;
        ViewHolder(View v) {
            super(v);
            phoneName = v.findViewById(R.id.phone_name);
            phoneNumber = v.findViewById(R.id.phone_number);
            phoneClient = v.findViewById(R.id.phone_client);
            phonePhoto=v.findViewById(R.id.phone_photo);
            phoneChecker=v.findViewById(R.id.phone_checker);
        }
    }

    /**
     * Called when RecyclerView needs a new {@link GeoListAdapter.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @NonNull
    @Override
    public ContactModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_user, parent, false);
        return new ContactModelAdapter.ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel company = items.get(position);
        holder.phoneNumber.setText(company.getPhone());
        holder.phoneName.setText(company.getName());
        Picasso.get().load(company.getPhoto()).placeholder(R.drawable.photo_not_found).into(holder.phonePhoto);
        //holder.phoneClient.setText(company.getTitle());
        //holder.companyDescribe.setText(company.getNotice());
        //Picasso.get().load(company.getLogo()).into(holder.companyImg);

    }



    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

}