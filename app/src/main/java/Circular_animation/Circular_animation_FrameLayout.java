package Circular_animation;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class Circular_animation_FrameLayout extends FrameLayout implements Circular_animation_ViewGroup {
    private View_Circular_animation_Manager manager;

    public Circular_animation_FrameLayout(Context context) {
        this(context, null);
    }

    public Circular_animation_FrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Circular_animation_FrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        manager = new View_Circular_animation_Manager();
    }

    @Override protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        try {
            canvas.save();

            manager.transform(canvas, child);
            return super.drawChild(canvas, child, drawingTime);
        } finally {
            canvas.restore();
        }
    }

    @Override public View_Circular_animation_Manager getViewRevealManager() {
        return manager;
    }
}