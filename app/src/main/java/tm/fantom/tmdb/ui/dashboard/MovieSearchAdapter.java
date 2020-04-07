package tm.fantom.tmdb.ui.dashboard;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.facebook.imagepipeline.request.ImageRequestBuilder;

import tm.fantom.tmdb.R;
import tm.fantom.tmdb.databinding.ItemMovieBinding;
import tm.fantom.tmdb.ui.base.BaseAdapter;
import tm.fantom.tmdb.ui.base.BaseViewHolder;

public class MovieSearchAdapter extends BaseAdapter<MovieModel, BaseViewHolder<MovieModel>> {

    private OnLastListener lastListener;
    private OnMovieClickListener clickListener;

    private static final int ITEM = 0;
    private static final int ITEM_SKELETON = 1;

    public interface OnLastListener {
        void loadMore(int count);
    }

    public interface OnMovieClickListener {
        void onMovieClick(int id, String name);
    }

    @NonNull
    @Override
    public BaseViewHolder<MovieModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case ITEM_SKELETON:
                return new SkeletonItemViewHolder(inflater.inflate(R.layout.item_skeleton_movie, null));
            case ITEM:
            default:
                ItemMovieBinding binding = ItemMovieBinding.inflate(inflater, parent, false);
                return new ItemViewHolder(binding.getRoot());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isSkeleton() ? ITEM_SKELETON : ITEM;
    }

    public void setLastListener(OnLastListener lastListener) {
        this.lastListener = lastListener;
    }

    public MovieSearchAdapter setClickListener(OnMovieClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    class ItemViewHolder extends BaseViewHolder<MovieModel> {

        ItemMovieBinding layout;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = ItemMovieBinding.bind(itemView);
        }

        @Override
        public void onBind(MovieModel item) {
            if (getAdapterPosition() == getItemCount() - 1 && lastListener != null) {
                lastListener.loadMore(getItemCount());
            }

            layout.sdvPreview.setImageRequest(
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(item.getPoster()))
                            .build()
            );
            layout.tvTitle.setText(item.getTitle());
            layout.getRoot().setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onMovieClick(item.getId(), item.getTitle());
            });
        }
    }

    static class SkeletonItemViewHolder extends BaseViewHolder<MovieModel> {

        SkeletonItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(MovieModel item) {
        }
    }
}
