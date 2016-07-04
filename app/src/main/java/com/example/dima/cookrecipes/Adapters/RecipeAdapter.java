package com.example.dima.cookrecipes.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dima.cookrecipes.Database.CategoryItem;
import com.example.dima.cookrecipes.Database.RecipeItem;
import com.example.dima.cookrecipes.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by dima on 29.06.16.
 */
public class RecipeAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater lInflater;
    ArrayList<RecipeItem> objects;
    String tag = "MyLogs";

    public RecipeAdapter(Context context, ArrayList<RecipeItem> categories) {
        mContext = context;
        objects = categories;

        lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false));
            vh.root.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        RecipeItem recipeItem = getRecipe(position);

        vh.recipeNameTextView.setText(recipeItem.recipeName);
        String imageUrl = "recipes/images/" + recipeItem.recipeImageLink;
        Bitmap bitmap = getBitmapFromAsset(imageUrl);
        vh.recipeIconImageView.setImageBitmap(bitmap);

        return vh.root;
    }

    private Bitmap getBitmapFromAsset(String imageUrl) {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    RecipeItem getRecipe(int position) {
        return ((RecipeItem) getItem(position));
    }

    private static class ViewHolder {
        TextView recipeNameTextView;
        ImageView recipeIconImageView;

        View root;

        public ViewHolder(View rootView) {
            root = rootView;
            recipeNameTextView = (TextView) rootView.findViewById(R.id.recipe_item_textView_recipeName);
            recipeIconImageView = (ImageView) rootView.findViewById(R.id.recipe_item_imageView_icon);
        }

    }
}