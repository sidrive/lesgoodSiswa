package com.lesgood.app.ui.search;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Category;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.CategoryService;
import com.lesgood.app.data.remote.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agus on 5/11/17.
 */

public class SearchPresenter implements BasePresenter {
    SearchActivity activity;
    UserService userService;
    CategoryService categoryService;
    User user;

    public SearchPresenter(SearchActivity activity, UserService userService, CategoryService categoryService, User user){
        this.activity = activity;
        this.userService = userService;
        this.categoryService = categoryService;
        this.user = user;
    }

    @Override
    public void subscribe() {
        if (user != null){
            getCategories();
        }
    }

    @Override
    public void unsubscribe() {

    }

    public void getCategories(){
        categoryService.getCategories().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initCategories(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSubCategories(String id){
        categoryService.getSubCategories(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initSubcategories(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getLevels(String id){
        categoryService.getLevels(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initLevel(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
