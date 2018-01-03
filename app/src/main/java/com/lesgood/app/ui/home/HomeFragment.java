package com.lesgood.app.ui.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.base.BaseFragment;
import com.lesgood.app.base.config.DefaultConfig;
import com.lesgood.app.data.model.Category;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.FetchAddressIntentService;
import com.lesgood.app.data.remote.LocationService;
import com.lesgood.app.ui.main.MainActivity;

import com.lesgood.app.ui.search.SearchActivity;
import com.lesgood.app.util.GridSpacingItemDecoration;

import com.lesgood.app.util.Utils;
import com.lesgood.app.util.preference.UserPreferences;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 4/27/17.
 */

public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.rv_items)
    RecyclerView rvItems;

    @Bind(R.id.slider)
    SliderLayout sliderLayout;

    @Inject
    HomePresenter presenter;

    @Inject
    User user;

    @Inject
    MainActivity activity;

    @Inject
    CategoryAdapter adapter;

    UserPreferences preferences;

    private BroadcastReceiver broadcastReceiver;
    private AddressResultReceiver mResultReceiver;
    String area;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupFragmentComponent() {
        BaseApplication.get(getActivity())
                .getMainComponent()
                .plus(new HomeFragmentModule(this))
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getContext(), LocationService.class);
        if(intent != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
            getActivity().stopService(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
        Intent intent = new Intent(getContext(), LocationService.class);
        if(intent != null) {
            getActivity().stopService(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        preferences = new UserPreferences(getContext());
        area = preferences.read(DefaultConfig.KEY_USER_AREA,String.class);
        Log.e("onCreateView", "arrea" + area);
        Log.e("onCreateView", "arrea" + area.length());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MethodName(intent);
            }
        };
        mResultReceiver = new AddressResultReceiver(new Handler());
        if (area==null){
            getActivity().setTitle("Location not detect");
        }{
            getActivity().setTitle(area);
        }

        showItems();
        initCateogies();
        initSlider();
        return view;
    }
    public void MethodName(Intent intent){
        final double lat = intent.getDoubleExtra("Latitude", 0);
        final double lng = intent.getDoubleExtra("Longitude", 0);
        final String provider = intent.getStringExtra("Provider");

        Log.d("GETLOCATIONSERVICE", "provider = "+provider);
        Log.d("GETLOCATIONSERVICE", "Latitude = "+lat);
        Log.d("GETLOCATIONSERVICE", "Longitude = "+lng);
        Location bestLocation = new Location(provider);
        bestLocation.setLatitude(lat);
        bestLocation.setLongitude(lng);
        if (lat!=0 && lng !=0){
            startServiceAddress(bestLocation);
        }else {
            startService();
            /*preferences.write(DefaultConfig.KEY_USER_LNG,lng,Double.class);
            preferences.write(DefaultConfig.KEY_USER_LAT,lat,Double.class);*/

        }

    }

    private void startServiceAddress(Location bestLocation) {
        Intent intent = new Intent(getContext(), FetchAddressIntentService.class);
        intent.putExtra(DefaultConfig.RECEIVER, mResultReceiver);
        intent.putExtra(DefaultConfig.LOCATION_DATA_EXTRA, bestLocation);
        getActivity().startService(intent);
    }

    private void startService() {
        area = preferences.read(DefaultConfig.KEY_USER_AREA,String.class);
        if (area==null){
            startService();
            getActivity().setTitle("Location not detect");
        }{
            getActivity().setTitle(area);
        }

    }

    public void initSlider(){
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Lesgood", "http://lesgood.com");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Lesgood",R.drawable.promotion);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(activity);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);

    }



    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(activity,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_change_location){
            Intent intent = new Intent(getContext(), LocationService.class);
            getActivity().startService(intent);
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
        }

        return super.onOptionsItemSelected(item);
    }

    public void initCateogies(){
        showAddItem(new Category("BM", "Materi Sekolah", R.drawable.materi_sekolah));
        showAddItem(new Category("MS", "Musik", R.drawable.musik));
        showAddItem(new Category("BA", "Bahasa Asing", R.drawable.bahasa_inggris));
        showAddItem(new Category("BD", "Bela Diri", R.drawable.beladiri));
        showAddItem(new Category("KM", "Komputerisasi", R.drawable.komputer));
        showAddItem(new Category("MG", "Mengaji", R.drawable.mengaji));
        showAddItem(new Category("KT", "Keterampilan", R.drawable.ketrapilan));
        showAddItem(new Category("OL", "Olahraga", R.drawable.olah_raga));
    }

    private void showItems(){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 3);
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setAdapter(adapter);
    }

    public void showAddItem(Category item){
        adapter.onItemAdded(item);
    }

    public void showItemClicked(Category item){
        SearchActivity.startWithData(activity, item.getId());
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            // Show a toast message if an address was found.
            if (resultCode == DefaultConfig.SUCCESS_RESULT) {
                String adminArea = resultData.getString(DefaultConfig.ADMIN_AREA_DATA_EXTRA);
                String locality = resultData.getString(DefaultConfig.LOCALITY_DATA_EXTRA);
                String postalCode = resultData.getString(DefaultConfig.POSTAL_CODE_DATA_EXTRA);
                String countryCode = resultData.getString(DefaultConfig.COUNTRY_CODE_DATA_EXTRA);
                preferences.write(DefaultConfig.KEY_USER_AREA,adminArea,String.class);
                Log.d("onReceiveResult", "adminArea = "+adminArea);
                Log.d("onReceiveResult", "locality = "+locality);
                Log.d("onReceiveResult", "postalCode = "+postalCode);
                Log.d("onReceiveResult", "countryCode = "+countryCode);
            }
        }
    }

}
