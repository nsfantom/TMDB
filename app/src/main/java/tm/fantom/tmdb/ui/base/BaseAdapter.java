package tm.fantom.tmdb.ui.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    protected List<T> items = new ArrayList<>();

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (position != holder.getAdapterPosition()) return;
        if (items.size() <= position) return;

        T item = items.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> items) {
        setData(items);
        notifyDataSetChanged();
    }

    public void setData(List<T> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
    }

    public void appendItems(List<T> items) {
        int position = this.items.size();
        if (items != null) {
            this.items.addAll(items);
            notifyItemRangeInserted(position, items.size());
        }
    }

    public void addItem(T item) {
        items.add(item);
    }
}
