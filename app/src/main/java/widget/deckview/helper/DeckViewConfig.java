package widget.deckview.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.wz.myapp.R;

/**
 * Created by dell on 2016/2/15.
 */
public class DeckViewConfig {
    static DeckViewConfig sInstance;
    static int sPrevConfigurationHashCode;
    /**
     * Interpolators
     */
    public Interpolator fastOutSlowInInterpolator;
    public Interpolator fastOutLinearInInterpolator;
    public Interpolator linearOutSlowInInterpolator;
    public Interpolator quintOutInterpolator;

    /**
     * Filtering
     */
    public int filteringCurrentViewsAnimDuration;
    public int filteringNewViewsAnimDuration;

    /**
     * Insets
     */
    public Rect systemInsets = new Rect();
    public Rect displayRect = new Rect();

    /**
     * Layout
     */
    boolean isLandscape;

    /**
     * Task stack
     */
    public int taskStackScrollDuration;
    public int taskStackMaxDim;
    public int taskStackTopPaddingPx;
    public float taskStackWidthPaddingPct;
    public float taskStackOverscrollPct;

    /**
     * Transitions
     */
    public int transitionEnterFromAppDelay;
    public int transitionEnterFromHomeDelay;

    /**
     * Task view animation and styles
     */
    public int taskViewEnterFromAppDuration;
    public int taskViewEnterFromHomeDuration;
    public int taskViewEnterFromHomeStaggerDelay;
    public int taskViewExitToAppDuration;
    public int taskViewExitToHomeDuration;
    public int taskViewRemoveAnimDuration;
    public int taskViewRemoveAnimTranslationXPx;
    public int taskViewTranslationZMinPx;
    public int taskViewTranslationZMaxPx;
    public int taskViewRoundedCornerRadiusPx;
    public int taskViewHighlightPx;
    public int taskViewAffiliateGroupEnterOffsetPx;
    public float taskViewThumbnailAlpha;

    /**
     * Task bar colors
     */
    public int taskBarViewDefaultBackgroundColor;
    public int taskBarViewLightTextColor;
    public int taskBarViewDarkTextColor;
    public int taskBarViewHighlightColor;
    public float taskBarViewAffiliationColorMinAlpha;

    /**
     * Task bar size & animations
     */
    public int taskBarHeight;
    public int taskBarDismissDozeDelaySeconds;

    /**
     * Nav bar scrim
     */
    public int navBarScrimEnterDuration;

    /**
     * Launch states
     */
    public boolean launchedWithAltTab;
    public boolean launchedWithNoRecentTasks;
    public boolean launchedFromAppWithThumbnail;
    public boolean launchedFromHome;
    public boolean launchedFromSearchHome;
    public boolean launchedReuseTaskStackViews;
    public boolean launchedHasConfigurationChanged;
    public int launchedToTaskId;
    public int launchedNumVisibleTasks;
    public int launchedNumVisibleThumbnails;

    /**
     * Misc *
     */
    public boolean useHardwareLayers;
    public int altTabKeyDelay;
    public boolean fakeShadows;

    /**
     * Dev options and global settings
     */
    public boolean debugModeEnabled;
    public int svelteLevel;

    /**
     * Animations
     */
    public float animationPxMovementPerSecond;


    /**
     * Private constructor
     */
    private DeckViewConfig(Context context) {
        // Properties that don't have to be reloaded with each configuration change can be loaded
        // here.

        // Interpolators
        fastOutSlowInInterpolator = new AccelerateDecelerateInterpolator();
        fastOutLinearInInterpolator = new LinearInterpolator();
        linearOutSlowInInterpolator = new AccelerateDecelerateInterpolator();
        quintOutInterpolator = new AccelerateDecelerateInterpolator();
    }

    public static DeckViewConfig getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DeckViewConfig(context);
            sInstance.update(context);
        }
        return sInstance;
    }

    /**
     * Updates the state, given the specified context
     */
    void update(Context context) {
        SharedPreferences settings = context.getSharedPreferences(context.getPackageName(), 0);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();

        // Debug mode
//        debugModeEnabled = settings.getBoolean(DVConstants.Values.App.Key_DebugModeEnabled, false);
        debugModeEnabled = true;

        // Layout
        isLandscape = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Insets
        displayRect.set(0, 0, dm.widthPixels, dm.heightPixels);

        // Animations
        animationPxMovementPerSecond =
                res.getDimensionPixelSize(R.dimen.animation_movement_in_dps_per_second);

        // Filtering
        filteringCurrentViewsAnimDuration =
                res.getInteger(R.integer.filter_animate_current_views_duration);
        filteringNewViewsAnimDuration =
                res.getInteger(R.integer.filter_animate_new_views_duration);

        // Task stack
        taskStackScrollDuration =
                res.getInteger(R.integer.animate_deck_scroll_duration);
        TypedValue widthPaddingPctValue = new TypedValue();
        res.getValue(R.dimen.deck_width_padding_percentage, widthPaddingPctValue, true);
        taskStackWidthPaddingPct = widthPaddingPctValue.getFloat();
        TypedValue stackOverscrollPctValue = new TypedValue();
        res.getValue(R.dimen.deck_overscroll_percentage, stackOverscrollPctValue, true);
        taskStackOverscrollPct = stackOverscrollPctValue.getFloat();
        taskStackMaxDim = res.getInteger(R.integer.max_deck_view_dim);
        taskStackTopPaddingPx = res.getDimensionPixelSize(R.dimen.deck_top_padding);

        // Transition
        transitionEnterFromAppDelay =
                res.getInteger(R.integer.enter_from_app_transition_duration);
        transitionEnterFromHomeDelay =
                res.getInteger(R.integer.enter_from_home_transition_duration);

        // Task view animation and styles
        taskViewEnterFromAppDuration =
                res.getInteger(R.integer.task_enter_from_app_duration);
        taskViewEnterFromHomeDuration =
                res.getInteger(R.integer.task_enter_from_home_duration);
        taskViewEnterFromHomeStaggerDelay =
                res.getInteger(R.integer.task_enter_from_home_stagger_delay);
        taskViewExitToAppDuration =
                res.getInteger(R.integer.task_exit_to_app_duration);
        taskViewExitToHomeDuration =
                res.getInteger(R.integer.task_exit_to_home_duration);
        taskViewRemoveAnimDuration =
                res.getInteger(R.integer.animate_task_view_remove_duration);
        taskViewRemoveAnimTranslationXPx =
                res.getDimensionPixelSize(R.dimen.task_view_remove_anim_translation_x);
        taskViewRoundedCornerRadiusPx =
                res.getDimensionPixelSize(R.dimen.task_view_rounded_corners_radius);
        taskViewHighlightPx = res.getDimensionPixelSize(R.dimen.task_view_highlight);
        taskViewTranslationZMinPx = res.getDimensionPixelSize(R.dimen.task_view_z_min);
        taskViewTranslationZMaxPx = res.getDimensionPixelSize(R.dimen.task_view_z_max);
        taskViewAffiliateGroupEnterOffsetPx =
                res.getDimensionPixelSize(R.dimen.task_view_affiliate_group_enter_offset);
        TypedValue thumbnailAlphaValue = new TypedValue();
        res.getValue(R.dimen.task_view_thumbnail_alpha, thumbnailAlphaValue, true);
        taskViewThumbnailAlpha = thumbnailAlphaValue.getFloat();

        // Task bar colors
        taskBarViewDefaultBackgroundColor =
                res.getColor(R.color.task_bar_default_background_color);
        taskBarViewLightTextColor =
                res.getColor(R.color.task_bar_light_text_color);
        taskBarViewDarkTextColor =
                res.getColor(R.color.task_bar_dark_text_color);
        taskBarViewHighlightColor =
                res.getColor(R.color.task_bar_highlight_color);
        TypedValue affMinAlphaPctValue = new TypedValue();
        res.getValue(R.dimen.task_affiliation_color_min_alpha_percentage, affMinAlphaPctValue, true);
        taskBarViewAffiliationColorMinAlpha = affMinAlphaPctValue.getFloat();

        // Task bar size & animations
        taskBarHeight = res.getDimensionPixelSize(R.dimen.deck_child_header_bar_height);
        taskBarDismissDozeDelaySeconds =
                res.getInteger(R.integer.task_bar_dismiss_delay_seconds);

        // Nav bar scrim
        navBarScrimEnterDuration =
                res.getInteger(R.integer.nav_bar_scrim_enter_duration);

        // Misc
        useHardwareLayers = res.getBoolean(R.bool.config_use_hardware_layers);
        altTabKeyDelay = res.getInteger(R.integer.deck_alt_tab_key_delay);
        fakeShadows = res.getBoolean(R.bool.config_fake_shadows);
        svelteLevel = res.getInteger(R.integer.deck_svelte_level);
    }



    /**
     * Returns the task stack bounds in the current orientation. These bounds do not account for
     * the system insets.
     */
    public void getTaskStackBounds(int windowWidth, int windowHeight, int topInset, int rightInset,
                                   Rect taskStackBounds) {
        taskStackBounds.set(0, 0, windowWidth, windowHeight);
    }

}
