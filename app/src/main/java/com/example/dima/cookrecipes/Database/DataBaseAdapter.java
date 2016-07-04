package com.example.dima.cookrecipes.Database;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by dima on 19.06.16.
 */
public class DataBaseAdapter {
    private static DataBaseAdapter instance;
    private Context mContext;

    final String LOG_TAG = "Parse log";


    public static DataBaseAdapter getAdapter(Context context) {
        DataBaseAdapter localInstance = instance;
        if (localInstance == null) {
            synchronized (DataBaseAdapter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataBaseAdapter(context);
                }
            }
        }
        return localInstance;
    }

    public DataBaseAdapter(Context context) {
        mContext = context;
    }




    public ArrayList<CategoryItem> getCategories() {
        InputStream xmlInput;
        ArrayList<CategoryItem> result = new ArrayList<>();
        try {
            xmlInput = mContext.getAssets().open("recipes/category.xml");
            String xmlString = getStringFromInputStream(xmlInput);
            XmlPullParser parser = prepareXpp(xmlString);
            String tmp = "";
            CategoryItem categoryItem;

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + parser.getName()
                                + ", depth = " + parser.getDepth() + ", attrCount = "
                                + parser.getAttributeCount());
                        tmp = "";
                        categoryItem = new CategoryItem();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            tmp = tmp + parser.getAttributeName(i) + " = "
                                    + parser.getAttributeValue(i) + ", ";

                            String attributeName = parser.getAttributeName(i);
                            if (attributeName.equals("title"))
                                categoryItem.categoryName = parser.getAttributeValue(i);
                            else if (attributeName.equals("link"))
                                categoryItem.categoryLink = parser.getAttributeValue(i);
                            else if (attributeName.equals("image"))
                                categoryItem.categoryImageLink = parser.getAttributeValue(i);
                            else if (attributeName.equals("hasSubcategory"))
                                categoryItem.hasSubCategory = Integer.parseInt(parser.getAttributeValue(i));

                        }
                        if (!TextUtils.isEmpty(tmp)) {
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                            result.add(categoryItem);
                        }
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + parser.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + parser.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                parser.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public ArrayList<CategoryItem> getSubCategories(String link) {
        InputStream xmlInput;
        ArrayList<CategoryItem> result = new ArrayList<>();
        try {
            xmlInput = mContext.getAssets().open("recipes/"+link);
            String xmlString = getStringFromInputStream(xmlInput);
            XmlPullParser parser = prepareXpp(xmlString);
            String tmp = "";
            CategoryItem categoryItem;

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + parser.getName()
                                + ", depth = " + parser.getDepth() + ", attrCount = "
                                + parser.getAttributeCount());
                        tmp = "";
                        categoryItem = new CategoryItem();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            tmp = tmp + parser.getAttributeName(i) + " = "
                                    + parser.getAttributeValue(i) + ", ";

                            String attributeName = parser.getAttributeName(i);
                            if (attributeName.equals("title"))
                                categoryItem.categoryName = parser.getAttributeValue(i);
                            else if (attributeName.equals("link"))
                                categoryItem.categoryLink = parser.getAttributeValue(i);
                            else if (attributeName.equals("image"))
                                categoryItem.categoryImageLink = parser.getAttributeValue(i);
                        }
                        if (!TextUtils.isEmpty(tmp)) {
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                            result.add(categoryItem);
                        }
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + parser.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + parser.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                parser.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public ArrayList<RecipeItem> getRecipes(String link) {
        InputStream xmlInput;
        ArrayList<RecipeItem> result = new ArrayList<>();
        try {
            xmlInput = mContext.getAssets().open("recipes/"+link);
            String xmlString = getStringFromInputStream(xmlInput);
            XmlPullParser parser = prepareXpp(xmlString);
            String tmp = "";
            RecipeItem recipeItem;

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + parser.getName()
                                + ", depth = " + parser.getDepth() + ", attrCount = "
                                + parser.getAttributeCount());
                        tmp = "";
                        recipeItem = new RecipeItem();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            tmp = tmp + parser.getAttributeName(i) + " = "
                                    + parser.getAttributeValue(i) + ", ";

                            String attributeName = parser.getAttributeName(i);
                            if (attributeName.equals("title"))
                                recipeItem.recipeName = parser.getAttributeValue(i);
                            else if (attributeName.equals("description"))
                                recipeItem.recipeDescription = parser.getAttributeValue(i);
                            else if (attributeName.equals("image"))
                                recipeItem.recipeImageLink = parser.getAttributeValue(i);
                        }
                        if (!TextUtils.isEmpty(tmp)) {
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                            result.add(recipeItem);
                        }
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + parser.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + parser.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                parser.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    // convert InputStream to String
    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    XmlPullParser prepareXpp(String parseText) throws XmlPullParserException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(parseText));
        return xpp;
    }


}
