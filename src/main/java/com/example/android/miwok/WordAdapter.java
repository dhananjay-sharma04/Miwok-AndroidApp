package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourcesId;
    private static final String LOG_TAG = WordAdapter.class.getSimpleName();

    public WordAdapter(Activity context, ArrayList<Word> words, int ColorResourcesId) {
        super(context, 0, words);
        mColorResourcesId = ColorResourcesId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView gujTextView = (TextView) listItemView.findViewById(R.id.guj_text_view);
        gujTextView.setText(currentWord.getGujaratiTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

//        set image for every list view
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageList);
        if (currentWord.hasImage()){
                imageView.setImageResource(currentWord.getImageResourceId());
        } else {
            imageView.setVisibility(View.GONE);
        }

//        Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        textContainer.setBackgroundResource(mColorResourcesId);;

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
