package Circular_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.Property;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class View_Circular_animation_Manager {
    public static final View_Circular_animation_Manager.ClipRadiusProperty REVEAL = new View_Circular_animation_Manager.ClipRadiusProperty();

    private Map<View, View_Circular_animation_Manager.RevealValues> targets = new HashMap<>();

    public View_Circular_animation_Manager() {

    }

    protected ObjectAnimator createAnimator(View_Circular_animation_Manager.RevealValues data) {
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(data, REVEAL, data.startRadius, data.endRadius);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                View_Circular_animation_Manager.RevealValues values = getValues(animation);
                values.clip(true);
            }

            @Override public void onAnimationEnd(Animator animation) {
                View_Circular_animation_Manager.RevealValues values = getValues(animation);
                values.clip(false);
                targets.remove(values.target());
            }
        });

        targets.put(data.target(), data);
        return animator;
    }

    private static View_Circular_animation_Manager.RevealValues getValues(Animator animator) {
        return (View_Circular_animation_Manager.RevealValues) ((ObjectAnimator) animator).getTarget();
    }

    public final Map<View, View_Circular_animation_Manager.RevealValues> getTargets() {
        return targets;
    }

    protected boolean hasCustomerRevealAnimator() {
        return false;
    }

    public boolean isClipped(View child) {
        View_Circular_animation_Manager.RevealValues data = targets.get(child);
        return data != null && data.isClipping();
    }

    public boolean transform(Canvas canvas, View child) {
        final View_Circular_animation_Manager.RevealValues revealData = targets.get(child);
        return revealData != null && revealData.applyTransformation(canvas, child);
    }

    public static final class RevealValues {
        private static final Paint debugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        static {
            debugPaint.setColor(Color.GREEN);
            debugPaint.setStyle(Paint.Style.FILL);
            debugPaint.setStrokeWidth(2);
        }

        final int centerX;
        final int centerY;

        final float startRadius;
        final float endRadius;

        boolean clipping;

        float radius;

        View target;

        Path path = new Path();

        Region.Op op = Region.Op.REPLACE;

        public RevealValues(View target, int centerX, int centerY, float startRadius, float endRadius) {
            this.target = target;
            this.centerX = centerX;
            this.centerY = centerY;
            this.startRadius = startRadius;
            this.endRadius = endRadius;
        }

        public void radius(float radius) {
            this.radius = radius;
        }

        public float radius() {
            return radius;
        }

        public View target() {
            return target;
        }

        public void clip(boolean clipping) {
            this.clipping = clipping;
        }

        public boolean isClipping() {
            return clipping;
        }

        public Region.Op op() {
            return op;
        }

        public void op(Region.Op op) {
            this.op = op;
        }

        boolean applyTransformation(Canvas canvas, View child) {
            if (child != target || !clipping) {
                return false;
            }

            path.reset();
            path.addCircle(child.getX() + centerX, child.getY() + centerY, radius, Path.Direction.CW);

            canvas.clipPath(path, op);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                child.invalidateOutline();
            }
            return true;
        }
    }

    private static final class ClipRadiusProperty extends Property<View_Circular_animation_Manager.RevealValues, Float> {

        ClipRadiusProperty() {
            super(Float.class, "supportCircularReveal");
        }

        @Override public void set(View_Circular_animation_Manager.RevealValues data, Float value) {
            data.radius(value);
            data.target().invalidate();
        }

        @Override public Float get(View_Circular_animation_Manager.RevealValues v) {
            return v.radius();
        }
    }

     static class ChangeViewLayerTypeAdapter extends AnimatorListenerAdapter {
        private View_Circular_animation_Manager.RevealValues viewData;
        private int featuredLayerType;
        private int originalLayerType;

        ChangeViewLayerTypeAdapter(View_Circular_animation_Manager.RevealValues viewData, int layerType) {
            this.viewData = viewData;
            this.featuredLayerType = layerType;
            this.originalLayerType = viewData.target.getLayerType();
        }

        @Override public void onAnimationStart(Animator animation) {
            viewData.target().setLayerType(featuredLayerType, null);
        }

        @Override public void onAnimationCancel(Animator animation) {
            viewData.target().setLayerType(originalLayerType, null);
        }

        @Override public void onAnimationEnd(Animator animation) {
            viewData.target().setLayerType(originalLayerType, null);
        }
    }
}