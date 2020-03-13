package com.example.dbmarch11;

//import statements
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dbmarch11.UserDatabaseContract.UserDatabase;
import java.util.List;


//CLASS         : UserDetailsAdapter
//PURPOSE       : Handles the cardview/recyclerview.  Updates and deletes popups for
//                each card and ensure each view has the correct data from the DB.
public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserViewHolder>
{

    //variable declarations
    List<UserDetails> userDetailsList;
    Context context;
    UserDatabaseHelper dbHelper;
    SQLiteDatabase db;

    //NAME          : UserDetailsAdapter
    //PARAMETERS    : List<UserDetails> userDetailsList
    //RETURNS       : none
    //DESCRIPTION   : Sets the user detail list
    public UserDetailsAdapter(List<UserDetails> userDetailsList)
    {
        this.userDetailsList = userDetailsList;
    }


    //NAME          : onCreateViewHolder
    //PARAMETERS    : ViewGroup parent, int viewType
    //RETURNS       : UserViewHolder
    //DESCRIPTION   : 
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(itemView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position)
    {
        UserDetails userDetails = userDetailsList.get(position);
        holder.tvName.setText(userDetails.getName());
        holder.tvAddress.setText(userDetails.getAddress());
        holder.tvPhone.setText(userDetails.getMobileNo());
        holder.tvProfession.setText(userDetails.getProfession());

        //updated fields
        holder.tvGender.setText(userDetails.getGender());
        holder.tvCorona.setText(userDetails.getCorona());
        holder.tvAgeRange.setText(userDetails.getAgeRange());


        holder.ivMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final UserDetails userDetails = userDetailsList.get(position);
                final int userId = userDetails.getUserId();
                dbHelper = new UserDatabaseHelper(context);
                db = dbHelper.getWritableDatabase();
                PopupMenu menu = new PopupMenu(context, holder.ivMenu);


                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.delete:
                                db.delete(UserDatabase.TABLE_NAME,UserDatabase._ID + " = " + userId,null);
                                notifyItemRangeChanged(position,userDetailsList.size());
                                userDetailsList.remove(position);
                                notifyItemRemoved(position);
                                db.close();
                                break;
                            case R.id.update:
                                Intent intent = new Intent(context, UpdateActivity.class);
                                intent.putExtra("USERID", userId);
                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return userDetailsList.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvAddress, tvPhone, tvProfession;

        //updated fields
        TextView tvGender, tvCorona, tvAgeRange;

        ImageView ivMenu;

        public UserViewHolder(View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvProfession = (TextView) itemView.findViewById(R.id.tv_profession);

            //updated fields
            tvGender = (TextView) itemView.findViewById(R.id.tv_gender);
            tvCorona = (TextView) itemView.findViewById(R.id.tv_corona);
            tvAgeRange = (TextView) itemView.findViewById(R.id.tv_ageRange);

            ivMenu = (ImageView) itemView.findViewById(R.id.iv_menu);
        }
    }
}