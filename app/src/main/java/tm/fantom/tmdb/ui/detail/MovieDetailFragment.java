package tm.fantom.tmdb.ui.detail;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.jetbrains.annotations.NotNull;

import tm.fantom.tmdb.TmApp;
import tm.fantom.tmdb.databinding.FragmentMovieDetailBinding;
import tm.fantom.tmdb.ui.base.BaseFragment;

public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {
    private FragmentMovieDetailBinding layout;
    private MovieDetailPresenter presenter;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new MovieDetailPresenter();
        TmApp.get(context).getAppComponent().inject(presenter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        layout = FragmentMovieDetailBinding.inflate(inflater);
        return attachToBaseView(inflater, container, layout.getRoot());
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attach(this);

        initClickListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        presenter.unsubscribe();
        super.onPause();
    }

    private void initClickListeners() {
        layout.ivBack.setOnClickListener(v -> getParentActivity().onBackPressed());
    }

    @Override
    public void showTitle(String name) {
        layout.tvTitle.setText(name);
    }

    @Override
    public void showData(MovieDetails movieDetails) {
        hideProgress();
        layout.sdvPreview.setImageRequest(
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(movieDetails.getPoster()))
                        .build()
        );
        layout.tvCategory.setText(movieDetails.getCategory());
        layout.tvReleaseDate.setText(movieDetails.getRelease());
        layout.tvBudget.setText(movieDetails.getBudget());
        layout.tvRating.setText(movieDetails.getRating());
        layout.tvTag.setText(movieDetails.getTag());
        layout.tvOverview.setText(movieDetails.getDescription());
    }

    @Override
    public void showError(String message) {
        hideProgress();
        if (!TextUtils.isEmpty(message)) showToast(message, Toast.LENGTH_SHORT);
    }

    @Override
    public void showError(int text) {
        showError(getString(text));
    }

}
