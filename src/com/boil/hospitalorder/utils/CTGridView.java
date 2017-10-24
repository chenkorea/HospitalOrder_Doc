package com.boil.hospitalorder.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CTGridView extends GridView {  
    public CTGridView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public CTGridView(Context context) {  
        super(context);  
    }  
  
    public CTGridView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
      
      
}  