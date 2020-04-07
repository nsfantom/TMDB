package tm.fantom.tmdb.ui.detail;

public final class MovieDetails {

    private String poster;
    private String release;
    private String budget;
    private String category;
    private String tag;
    private String description;
    private String rating;

    public String getPoster() {
        return poster;
    }

    public MovieDetails setPoster(String poster) {
        this.poster = poster;
        return this;
    }

    public String getRelease() {
        return release;
    }

    public MovieDetails setRelease(String release) {
        this.release = release;
        return this;
    }

    public String getBudget() {
        return budget;
    }

    public MovieDetails setBudget(String budget) {
        this.budget = budget;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public MovieDetails setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public MovieDetails setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MovieDetails setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public MovieDetails setRating(String rating) {
        this.rating = rating;
        return this;
    }
}
