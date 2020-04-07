package tm.fantom.tmdb.ui;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class VHOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private @RecyclerView.Orientation
    int orientation;
    private int halfOffset;
    private int offset;
    private int vOffset;
    private int vHalfOffset;
    private boolean omitFirst = false;
    private int lastOffset;

    public VHOffsetItemDecoration(int orientation, int offset, int vOffset, boolean omitFirst) {
        this.orientation = orientation;
        this.offset = offset;
        this.vOffset = vOffset;
        this.halfOffset = offset / 2;
        this.vHalfOffset = vOffset / 2;
        this.omitFirst = omitFirst;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);

        switch (orientation) {
            case RecyclerView.HORIZONTAL:
                if (parent.getChildAdapterPosition(view) == 0) {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = offset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = halfOffset;
                } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = halfOffset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = offset;
                } else {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = halfOffset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = halfOffset;
                }
                ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = vHalfOffset;
                ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = vHalfOffset;
                break;
            case RecyclerView.VERTICAL:
                if (parent.getChildAdapterPosition(view) == (omitFirst ? 1 : 0)) {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = vOffset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = vHalfOffset;
                } else if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = vHalfOffset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = lastOffset + vOffset;
                } else {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = vHalfOffset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = vHalfOffset;
                }
                if (omitFirst && parent.getChildAdapterPosition(view) == 0) {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = vOffset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = 0;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = 0;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = 0;
                } else {
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = offset;
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = offset;
                }
                break;
        }
    }
}