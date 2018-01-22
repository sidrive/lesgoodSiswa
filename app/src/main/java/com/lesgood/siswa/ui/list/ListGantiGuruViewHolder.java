package com.lesgood.siswa.ui.list;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.NONE;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoQuery;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.model.Skill;
import com.lesgood.siswa.data.remote.FirebaseImageService;
import com.lesgood.siswa.data.remote.UserService;
import com.lesgood.siswa.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Agus on 3/6/17.
 */

public class ListGantiGuruViewHolder extends RecyclerView.ViewHolder {

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
    GeoQuery geoQuery;
    ListGantiGuruActivity activity;

    public ListGantiGuruViewHolder(View itemView, UserService userService,
        FirebaseImageService firebaseImageService, ListGantiGuruActivity activity) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
        this.activity = activity;

    }

    public void bind(String item) {
        getGuru(item);
        //getAvatar(item);

    }




    public void getGuru(final String uid) {
        getGuruTarif(uid);
        userService.getUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Guru userf = dataSnapshot.getValue(Guru.class);

                if (userf != null) {
                    user = userf;
                    txtName.setText(userf.getFull_name());
                    txtSkill.setText(userf.getTotalSkill() + " Kemampuan Mengajar");
                    Glide.with(itemView.getContext())
                        .load(userf.getPhoto_url())
                        .placeholder(R.drawable.bg_wave_primary)
                        .dontAnimate()
                        .diskCacheStrategy(NONE)
                        .skipMemoryCache(true)
                        .into(imgAvatar);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "ListViewHolder" + databaseError.getDetails());
            }
        });
    }

    public void getGuruTarif(String uid) {

        userService.getUserSkill(uid, activity.code)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Skill skill = dataSnapshot.getValue(Skill.class);
                    if (skill != null) {
                        int tarif = skill.getPrice1();
                        double fee = 0;

                        fee = tarif * 0.3;

                        int finaltarif = (int) ((tarif + fee) + 0.5d);

                        txtPrice.setText(Utils.getRupiah(finaltarif)+ " /per jam");
                        txtPrice.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("onCancelled", "ListViewHolder" + databaseError.getDetails());
                }
            });
    }

    public void getAvatar(String uid) {
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
