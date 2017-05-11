package com.lesgood.app.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.lesgood.app.R;
import com.lesgood.app.data.model.Category;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.FirebaseImageService;
import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.NONE;

/**
 * Created by Agus on 3/6/17.
 */

public class ListViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.txt_name)
    TextView txtName;

    @Bind(R.id.txt_skill)
    TextView txtSkill;

    @Bind(R.id.txt_distance)
    TextView txtDistance;

    @Bind(R.id.txt_price)
    TextView txtPrice;

    @Bind(R.id.img_avatar)
    CircleImageView imgAvatar;

    private View itemView;
    UserService userService;
    FirebaseImageService firebaseImageService;
    Guru user;

    public ListViewHolder(View itemView, UserService userService, FirebaseImageService firebaseImageService) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
    }

    public void bind(String item) {
        getGuru(item);
        getAvatar(item);

    }

    public void getGuru(String uid){
        userService.getUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Guru userf = dataSnapshot.getValue(Guru.class);
                if (userf != null){
                    user = userf;

                    txtName.setText(userf.getFull_name());
                    txtSkill.setText(user.getTotalSkill()+" Kemampuan Mengajar");
                    txtPrice.setText(Utils.getRupiah(userf.getStartFrom()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAvatar(String uid){
        StorageReference coverRef = firebaseImageService.getImageRefThumb(uid);
        Glide.with(itemView.getContext())
                .using(new FirebaseImageLoader())
                .load(coverRef)
                .placeholder(R.drawable.bg_wave_primary)
                .dontAnimate()
                .diskCacheStrategy(NONE)
                .skipMemoryCache(true)
                .into(imgAvatar);
    }

}
