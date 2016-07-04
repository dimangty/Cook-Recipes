package com.example.dima.cookrecipes.ui;

import android.content.Context;
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

import com.example.dima.cookrecipes.Adapters.CategoriesAdapter;
import com.example.dima.cookrecipes.Database.CategoryItem;
import com.example.dima.cookrecipes.Database.DataBaseAdapter;
import com.example.dima.cookrecipes.R;
import com.example.dima.cookrecipes.utils.FragmentsStack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dima on 02.07.16.
 */
public class SubCategoryFragment extends Fragment {
    ArrayList<CategoryItem> mItems;
    Context mContext;

    @BindView(R.id.subcategory_fragment_listView_subcategories)
    ListView mCategoryList;

    FragmentManager mFragmentManager;
    final static String TAG_1 = "Recipes_Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subcategory_fragment, null);
        ButterKnife.bind(this,view);
        mContext = container.getContext();

        mFragmentManager = getFragmentManager();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String link = bundle.getString("Link");
            if (link != null) {
                mItems = DataBaseAdapter.getAdapter(container.getContext()).getSubCategories(link);
                loadTable();
            }
        }

        return view;

    }

    void loadTable() {
        // находим список


        // создаем адаптер
        CategoriesAdapter adapter = new CategoriesAdapter(mContext, mItems);
        mCategoryList.setAdapter(adapter);


        mCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                CategoryItem categoryItem = mItems.get(position);
                showRecipes(categoryItem.categoryLink);
            }
        });

    }


    void showRecipes(String link) {
        RecipesFragment recipesFragment = new RecipesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Link", link);
        recipesFragment.setArguments(bundle);
        FragmentsStack.getStack().pushFragment(recipesFragment);

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, recipesFragment, TAG_1);
        fragmentTransaction.addToBackStack("myStack");
        fragmentTransaction.commit();

    }


}
