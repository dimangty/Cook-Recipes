package com.example.dima.cookrecipes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.dima.cookrecipes.Adapters.RecipeAdapter;

import com.example.dima.cookrecipes.Database.DataBaseAdapter;
import com.example.dima.cookrecipes.Database.RecipeItem;
import com.example.dima.cookrecipes.R;

import com.example.dima.cookrecipes.utils.FragmentsStack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dima on 02.07.16.
 */
public class RecipesFragment extends Fragment {
    ArrayList<RecipeItem> mItems;
    Context mContext;
    @BindView(R.id.recipes_fragment_listView_recipes)
    ListView mRecipesList;
    FragmentManager mFragmentManager;
    final static String TAG_1 = "Recipe_Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipes_fragment, null);
        ButterKnife.bind(this,view);

        mContext = container.getContext();
        mFragmentManager = getFragmentManager();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String link = bundle.getString("Link");
            if (link != null) {
                mItems = DataBaseAdapter.getAdapter(mContext).getRecipes(link);
                loadTable();
            }
        }

        return view;

    }

    void loadTable() {
        // находим список


        // создаем адаптер
        RecipeAdapter adapter = new RecipeAdapter(mContext, mItems);
        mRecipesList.setAdapter(adapter);


        mRecipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                RecipeItem item = mItems.get(position);
                showRecipe(item);
            }
        });

    }

    void showRecipe(RecipeItem recipeItem) {

        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Image", recipeItem.recipeImageLink);
        bundle.putString("Description", recipeItem.recipeDescription);
        bundle.putString("Name", recipeItem.recipeName);

        recipeFragment.setArguments(bundle);
        FragmentsStack.getStack().pushFragment(recipeFragment);


        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, recipeFragment, TAG_1);
        fragmentTransaction.addToBackStack("myStack");
        fragmentTransaction.commit();

    }


}
