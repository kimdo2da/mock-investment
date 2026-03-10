package project;

public class NewsDTO {
    private String title;
    private String summary;
    private String url;
    private String image;
    private String date;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}