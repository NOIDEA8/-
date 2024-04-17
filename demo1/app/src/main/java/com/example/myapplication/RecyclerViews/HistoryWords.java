package com.example.myapplication.RecyclerViews;

public class HistoryWords {
    private String origin;
    private String translated;

    public HistoryWords() {
    }

    public HistoryWords(String origin, String translated) {
        this.origin = origin;
        this.translated = translated;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }
}
