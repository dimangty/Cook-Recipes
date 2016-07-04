package com.example.dima.cookrecipes.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import android.widget.TextView;


import com.example.dima.cookrecipes.R;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dima on 02.07.16.
 */
public class RecipeFragment extends Fragment {

    @BindView(R.id.recipe_fragment_textView_recipeName)
    TextView mRecipeNameTextView;

    @BindView(R.id.recipe_fragment_imageView_icon)
    ImageView mRecipeIconImageView;

    @BindView(R.id.recipe_fragment_webView)
    WebView mRecipeDescriptionWebView;

    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, null);
        ButterKnife.bind(this,view);

        mContext = container.getContext();
         Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("Name");
            String imageLink = bundle.getString("Image");
            String description = bundle.getString("Description");


            mRecipeNameTextView.setText(name);


            String imageUrl = "recipes/images/" + imageLink;
            Bitmap bitmap = getBitmapFromAsset(imageUrl);
            mRecipeIconImageView.setImageBitmap(bitmap);

            String html = "<html>" +
                    "<style type=\"text/css\">" +
                    "body { background-color:transparent; font-family:Marker Felt; font-size:15;}" +
                    "</style>" +
                    "<body>" +
                    "<p>" + description + "</p>" +
                    "</body></html>";

            String mime = "text/html";
            String encoding = "utf-8";

            mRecipeDescriptionWebView.getSettings().setJavaScriptEnabled(true);
            mRecipeDescriptionWebView.loadDataWithBaseURL(null, html, mime, encoding, null);
        }




        return view;

    }

    private Bitmap getBitmapFromAsset(String imageUrl) {
        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
