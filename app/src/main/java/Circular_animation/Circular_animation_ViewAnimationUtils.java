package Circular_animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import Circular_animation.View_Circular_animation_Manager.ChangeViewLayerTypeAdapter;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static Circular_animation.View_Circular_animation_Manager.RevealValues;


public final class Circular_animation_ViewAnimationUtils {

    private final static boolean LOLLIPOP_PLUS = SDK_INT >= LOLLIPOP;

    public static Animator createCircularReveal(View view, int centerX, int centerY,
                                                float startRadius, float endRadius) {

        return createCircularReveal(view, centerX, centerY, startRadius, endRadius,
                View.LAYER_TYPE_SOFTWARE);
    }

    public static Animator createCircularReveal(View view, int centerX, int centerY,
                                                float startRadius, float endRadius, int layerType) {

        if (!(view.getParent() instanceof Circular_animation_ViewGroup)) {
            throw new IllegalArgumentException("Parent must be instance of Circular_animation_ViewGroup");
        }

        Circular_animation_ViewGroup viewGroup = (Circular_animation_ViewGroup) view.getParent();
        View_Circular_animation_Manager rm = viewGroup.getViewRevealManager();

        if (!rm.hasCustomerRevealAnimator() && LOLLIPOP_PLUS) {
            return android.view.ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                    startRadius, endRadius);
        }

        RevealValues viewData = new RevealValues(view, centerX, centerY, startRadius, endRadius);
        ObjectAnimator animator = rm.createAnimator(viewData);

        if (layerType != view.getLayerType()) {
            animator.addListener(new ChangeViewLayerTypeAdapter(viewData, layerType));
        }
        return animator;
    }
}