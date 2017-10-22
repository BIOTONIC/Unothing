package ca.wlu.tianran.unothing.cards;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

// http://www.jianshu.com/p/4eff036360da
public class CardListDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private boolean isLandscape;

    public static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public CardListDecoration(Context context, boolean isLandscape) {
        final TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.divider = ta.getDrawable(0);
        ta.recycle();
        setOrientation(isLandscape);
    }

    public void setOrientation(boolean isLandscape) {
        this.isLandscape = isLandscape;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (isLandscape) {
            drawVerticalLine(c, parent, state);
        } else {
            drawHorizontalLine(c, parent, state);
        }
    }

    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            // get layout parameters of child view
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingTop();
        int right = parent.getWidth() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            // get layout parameters of child view
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicWidth();
            // definition of the four variables changed with the orientation
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isLandscape) {
            // offset is the width of the divider
            outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
        } else {
            // offset is the height of the divider
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());

        }
    }
}
