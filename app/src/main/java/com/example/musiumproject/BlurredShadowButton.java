package com.example.musiumproject;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

public class BlurredShadowButton extends AppCompatButton {
    private Paint shadowPaint;
    private int shadowColor = 0xFF06A0B5; // Black color for the shadow
    private float shadowRadius = 10f; // Adjust this for the blur radius
    private float dx = 5f; // Adjust this for shadow X offset
    private float dy = 5f; // Adjust this for shadow Y offset

    public BlurredShadowButton(Context context) {
        super(context);
        init();
    }

    public BlurredShadowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlurredShadowButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(shadowColor);
        shadowPaint.setMaskFilter(new BlurMaskFilter(shadowRadius, BlurMaskFilter.Blur.NORMAL));
        setLayerType(LAYER_TYPE_SOFTWARE, shadowPaint); // Disable hardware acceleration for shadow layer to work
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw shadow
        canvas.save();
        canvas.translate(dx, dy);
        super.onDraw(canvas);
        canvas.restore();
        // Draw button content
        super.onDraw(canvas);
    }
}
