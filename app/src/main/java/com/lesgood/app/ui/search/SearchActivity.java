package com.lesgood.app.ui.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Category;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.list.ListActivity;
import com.lesgood.app.ui.skill.SkillActivityModule;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 5/11/17.
 */

public class SearchActivity extends BaseActivity {

    @BindString(R.string.error_field_required)
    String errRequired;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.txt_category)
    TextView txtCategory;

    @Bind(R.id.txt_skill)
    TextView txtSkill;

    @Bind(R.id.txt_level)
    TextView txtLevel;

    @Inject
    User user;

    @Inject
    SearchPresenter presenter;

    CharSequence[] categories;
    CharSequence[] subcategories;
    CharSequence[] levels;

    List<Category> listCategories;
    List<Category> listSubcategories;
    List<Category> listLevels;

    int categoryVal;
    int subcategoryVal;
    int levelVal;

    String skillID;
    String levelID;
    String seletedID;

    public static void startWithData(BaseActivity activity, String id){
        Intent intent = new Intent(activity, SearchActivity.class);
        if (id != null){
            intent.putExtra("id", id);
        }
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            seletedID = extras.getString("id");
        }

        toolbar.setTitle("Cari Pengajar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void init(String id){
        for (int i=0;i<listCategories.size();i++){
            String catId = listCategories.get(i).getId();
            if (id.equals(catId)){
                handleSelectCategory(i);
            }
        }
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new SearchActivityModule((this)))
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);}

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }


    public void initCategories(List<Category> listCategories){
        this.listCategories = listCategories;
        categories = new CharSequence[listCategories.size()];
        for (int i=0;i<listCategories.size();i++){
            categories[i] = listCategories.get(i).getName();
        }

        if (seletedID != null){
            init(seletedID);
        }

    }

    public void initSubcategories(List<Category> listSubcategories){
        this.listSubcategories = listSubcategories;
        subcategories = new CharSequence[listSubcategories.size()];
        for (int i=0;i<listSubcategories.size();i++){
            subcategories[i] = listSubcategories.get(i).getName();
        }

    }

    public void initLevel(List<Category> listLevels){
        this.listLevels = listLevels;
        levels = new CharSequence[listLevels.size()];
        for (int i=0;i<listLevels.size();i++){
            levels[i] = listLevels.get(i).getName();
        }

    }


    public void showLoading(boolean show){
        if(show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.lin_category)
    void showCategories(){
        showDialogSelectCategory();
    }

    @OnClick(R.id.lin_subcategory)
    void showSubCategories(){
        showDialogSelectSubCategory();
    }

    @OnClick(R.id.lin_level)
    void showLevel(){
        showDialogSelectLevel();
    }

    private void showDialogSelectCategory() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pilih Kategori");
        alert.setSingleChoiceItems(categories, categoryVal, (dialog, which) -> {
            handleSelectCategory(which);
            dialog.dismiss();

        });
        alert.show();
    }

    private void handleSelectCategory(int pos){
        categoryVal = pos;
        String category = categories[pos].toString();
        txtCategory.setText(category);
        presenter.getSubCategories(listCategories.get(pos).getId());
    }


    private void showDialogSelectSubCategory() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pilih Materi");
        alert.setSingleChoiceItems(subcategories, subcategoryVal, (dialog, which) -> {
            handleSelectSubCategory(which);
            dialog.dismiss();

        });
        alert.show();
    }

    private void handleSelectSubCategory(int pos){
        subcategoryVal = pos;
        String subcategory = subcategories[pos].toString();
        txtSkill.setText(subcategory);

        skillID = listSubcategories.get(pos).getId();
        presenter.getLevels(listSubcategories.get(pos).getLevel());
    }

    private void showDialogSelectLevel() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pilih Tingkat");
        alert.setSingleChoiceItems(levels, levelVal, (dialog, which) -> {
            handleSelectLevel(which);
            dialog.dismiss();

        });
        alert.show();
    }

    private void handleSelectLevel(int pos){
        levelVal = pos;
        String level = levels[pos].toString();
        txtLevel.setText(level);
        levelID = listLevels.get(pos).getId();
    }

    @OnClick(R.id.btn_search)
    void searchTeachers(){
        boolean cancel = false;

        if (skillID == null){
            Toast.makeText(this, "Pilih Pelajaran", Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        if (levelID == null){
            Toast.makeText(this, "Pilih Tingkat", Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        if (cancel){

        }else{

            String code = skillID+levelID;
            String pelajaran = txtSkill.getText().toString()+" "+txtLevel.getText().toString();
            ListActivity.startWithData(this, code, pelajaran);
            finish();
        }
    }
}
