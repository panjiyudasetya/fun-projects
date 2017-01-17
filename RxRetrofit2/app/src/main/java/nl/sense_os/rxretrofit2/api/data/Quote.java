package nl.sense_os.rxretrofit2.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by panjiyudasetya on 1/16/17.
 */

public class Quote {
    @SerializedName("quote")
    private String quote;
    @SerializedName("author")
    private String author;
    @SerializedName("category")
    private String category;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
