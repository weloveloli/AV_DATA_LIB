package com.weloveloli.avlib.model;

import java.io.Serializable;
import java.util.List;

public class AvData implements Serializable {

    private String title;
    private String year;
    private String number;
    private String source;
    private List<String> actorList;
    private String release;
    private List<String> labels;
    private List<String> tags;
    private List<String> covers;
    private String mainCover;
    private String outline;
    private String director;
    private List<String> magnets;

    @Override
    public String toString() {
        return "AvData{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", number='" + number + '\'' +
                ", source='" + source + '\'' +
                ", actorList=" + actorList +
                ", release='" + release + '\'' +
                ", labels=" + labels +
                ", tags=" + tags +
                ", covers=" + covers +
                ", mainCover='" + mainCover + '\'' +
                ", outline='" + outline + '\'' +
                ", director='" + director + '\'' +
                ", magnets=" + magnets +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public AvData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getYear() {
        return year;
    }

    public AvData setYear(String year) {
        this.year = year;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public AvData setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getSource() {
        return source;
    }

    public AvData setSource(String source) {
        this.source = source;
        return this;
    }

    public List<String> getActorList() {
        return actorList;
    }

    public AvData setActorList(List<String> actorList) {
        this.actorList = actorList;
        return this;
    }

    public String getRelease() {
        return release;
    }

    public AvData setRelease(String release) {
        this.release = release;
        return this;
    }

    public List<String> getLabels() {
        return labels;
    }

    public AvData setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public AvData setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public List<String> getCovers() {
        return covers;
    }

    public AvData setCovers(List<String> covers) {
        this.covers = covers;
        return this;
    }

    public String getMainCover() {
        return mainCover;
    }

    public AvData setMainCover(String mainCover) {
        this.mainCover = mainCover;
        return this;
    }

    public String getOutline() {
        return outline;
    }

    public AvData setOutline(String outline) {
        this.outline = outline;
        return this;
    }

    public String getDirector() {
        return director;
    }

    public AvData setDirector(String director) {
        this.director = director;
        return this;
    }

    public List<String> getMagnets() {
        return magnets;
    }

    public AvData setMagnets(List<String> magnets) {
        this.magnets = magnets;
        return this;
    }
}
