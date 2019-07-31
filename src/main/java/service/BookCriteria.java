package service;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class BookCriteria implements Serializable {

    private static final long serialVersionUID = 6326267368520380132L;
    private Boolean sortByAsc;
    private String sortBy;
    private String title;
    private String author;
    private String summary;
    private Double price;
    private Date startDatePublication;
    private Date endDatePublication;

    public Boolean isSortByAsc() {
        return sortByAsc;
    }

    public void setSortByAsc(Boolean sortByAsc) {
        this.sortByAsc = sortByAsc;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getStartDatePublication() {
        return startDatePublication;
    }

    public void setStartDatePublication(Date startDatePublication) {
        this.startDatePublication = startDatePublication;
    }

    public Date getEndDatePublication() {
        return endDatePublication;
    }

    public void setEndDatePublication(Date endDatePublication) {
        this.endDatePublication = endDatePublication;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
