package com.example.jeremy.ssltracker;

/**
 * Created by Jeremy on 4/11/2016.
 */
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WebTrackerView extends RelativeLayout
{
    Button button;
    TextView typeTextView;
    TextView nameTextView;
    TextView expiryTextView;
    ArrayAdapter adapter;

    public WebTrackerView(Context context, AttributeSet attributes)
    {
        super(context, attributes);

        adapter = ArrayAdapter.createFromResource(context, R.array.WebTrackerOptions, android.R.layout.simple_spinner_item);

        int index = 1;

        button = new Button(context);
        nameTextView = new TextView(context);
        nameTextView.setId(index+1);
        typeTextView = new TextView(context);
        typeTextView.setId(index+2);
        expiryTextView = new TextView(context);
        expiryTextView.setId(index+3);




        RelativeLayout.LayoutParams nameTextViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams typeTextViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams expiryTextViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);




        //nameTextView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        nameTextView.setBackground(null);
        nameTextView.setTextColor(Color.BLACK);
        nameTextView.setTextSize(25);
        nameTextView.setGravity(CENTER_IN_PARENT);
        nameTextViewParams.setMargins(0, 0, 15, 0);
        nameTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        nameTextView.setLayoutParams(nameTextViewParams);
        this.addView(nameTextView);

        //typeTextView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        typeTextView.setBackground(null);
        typeTextView.setTextColor(Color.GRAY);
        typeTextView.setTextSize(15);
        typeTextView.setGravity(CENTER_IN_PARENT);
        typeTextViewParams.setMargins(0, 0, 15, 0);
        typeTextViewParams.addRule(RelativeLayout.RIGHT_OF, 2);
        typeTextView.setLayoutParams(typeTextViewParams);
        this.addView(typeTextView);

        //expiryTextView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        expiryTextView.setBackground(null);
        expiryTextView.setTextColor(Color.BLACK);
        expiryTextView.setTextSize(25);
        expiryTextView.setGravity(CENTER_VERTICAL);
        expiryTextViewParams.addRule(RelativeLayout.RIGHT_OF, 3);
        expiryTextView.setLayoutParams(expiryTextViewParams);
        this.addView(expiryTextView);


        button.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        button.setBackground(null);
        button.setTextColor(Color.BLACK);

        this.addView(button);
        /*this.addView(nameTextView);
        this.addView(typeTextView);
        this.addView(expiryTextView);*/

        //ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //buttonParams.weight = 1.0f;
        //buttonParams.gravity = Gravity.TOP;

        //button.setLayoutParams(buttonParams);
    }

    public void updateViewData(WebTrackerData data)
    {
        GregorianCalendar currentDate = new GregorianCalendar();

        long diff = data.getRenewal().getTimeInMillis() - currentDate.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);


        /*expiryTextView.setText((diffDays > 99 ? "99+" : diffDays) + "d");
        nameTextView.setText(data.getName());
        typeTextView.setText(adapter.getItem(data.getType());*/

        nameTextView.setText(data.getName());




        if(diffDays > 99)
        {
            expiryTextView.setText("99+ days");
        }
        else if (diffDays <= 10 && diffDays >= 0)
        {
            expiryTextView.setText((diffDays) + " days");
            expiryTextView.setTextColor(Color.RED);
        }
        else if(diffDays < 0 )
        {
            expiryTextView.setText("EXPIRED");
            expiryTextView.setTextColor(Color.RED);
        }
        else
        expiryTextView.setText(diffDays + " days");


        typeTextView.setText(adapter.getItem(data.getType()).toString());
        button.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);
    }

    public Button getButton()
    {
        return button;
    }
}

