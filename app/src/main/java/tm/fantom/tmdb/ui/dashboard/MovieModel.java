package tm.fantom.tmdb.ui.dashboard;

import tm.fantom.tmdb.api.netmodel.MovieListObject;

public final class MovieModel {

    private int id;
    private boolean isSkeleton = false;
    private String poster;
    private String title;
    private String release;
    private String budget;
    private String category;
    private String tag;
    private String description;

    public MovieModel(int id) {
        this.id = id;
    }

    public boolean isSkeleton() {
        return isSkeleton;
    }

    public MovieModel setSkeleton(boolean skeleton) {
        isSkeleton = skeleton;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public MovieModel setPoster(String poster) {
        this.poster = poster;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public static  MovieModel fromNetwork(MovieListObject mlo) {
        return new MovieModel(mlo.getId())
//                .setPoster(mlo.getPosterPath())
                .setTitle(mlo.getTitle());
    }
}
