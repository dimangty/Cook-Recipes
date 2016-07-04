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
import com.example.dima.cookrecipes.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by dima on 05.06.16.
 */
public class CategoriesAdapter  extends BaseAdapter {

    Context mContext;
    LayoutInflater lInflater;
    ArrayList<CategoryItem> objects;
    String tag="MyLogs";

    public CategoriesAdapter(Context context, ArrayList<CategoryItem> categories) {
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
        if(convertView == null)
        {
            vh = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false));
            vh.root.setTag(vh);

        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        CategoryItem categoryItem = getCategory(position);

        vh.categoryNameTextView.setText(categoryItem.categoryName);
        String imageUrl = "recipes/images/" + categoryItem.categoryImageLink;
        Bitmap bitmap = getBitmapFromAsset(imageUrl);
        vh.categoryImageView.setImageBitmap(bitmap);

        return vh.root;
    }

    private Bitmap getBitmapFromAsset(String imageUrl)
    {
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

    CategoryItem getCategory(int position) {
        return ((CategoryItem) getItem(position));
    }

    private   static  class ViewHolder
    {
        TextView categoryNameTextView;
        ImageView categoryImageView;
        View root;

        public ViewHolder(View rootView)
        {
            root=rootView;
            categoryNameTextView = (TextView) rootView.findViewById(R.id.category_item_textView_name);
            categoryImageView = (ImageView) rootView.findViewById(R.id.category_item_imageView_icon);

        }

    }
}
