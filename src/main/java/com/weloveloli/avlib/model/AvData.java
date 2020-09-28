/*
 * Copyright 2020-2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.weloveloli.avlib.model;

import java.io.Serializable;
import java.util.List;

/**
 * AV meta data
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class AvData implements Serializable {

    private String title;
    private String year;
    private String number;
    private String source;
    private String time;
    private String release;
    private String studio;
    private String mainCover;
    private String outline;
    private List<String> actors;
    private List<String> labels;
    private List<String> tags;
    private List<String> covers;
    private List<String> series;
    private List<String> directors;
    private List<String> magnets;
    private String webSiteUrl;
    private String previewVideo;


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

    public List<String> getActors() {
        return actors;
    }

    public AvData setActors(List<String> actors) {
        this.actors = actors;
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

    public List<String> getDirectors() {
        return directors;
    }

    public AvData setDirectors(List<String> directors) {
        this.directors = directors;
        return this;
    }

    public List<String> getMagnets() {
        return magnets;
    }

    public AvData setMagnets(List<String> magnets) {
        this.magnets = magnets;
        return this;
    }

    public String getTime() {
        return time;
    }

    public AvData setTime(String time) {
        this.time = time;
        return this;
    }

    public List<String> getSeries() {
        return series;
    }

    public AvData setSeries(List<String> series) {
        this.series = series;
        return this;
    }

    public String getStudio() {
        return studio;
    }

    public AvData setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public AvData setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
        return this;
    }

    public String getPreviewVideo() {
        return previewVideo;
    }

    public AvData setPreviewVideo(String previewVideo) {
        this.previewVideo = previewVideo;
        return this;
    }

    @Override
    public String toString() {
        return "AvData{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", number='" + number + '\'' +
                ", source='" + source + '\'' +
                ", time='" + time + '\'' +
                ", release='" + release + '\'' +
                ", studio='" + studio + '\'' +
                ", mainCover='" + mainCover + '\'' +
                ", outline='" + outline + '\'' +
                ", actors=" + actors +
                ", labels=" + labels +
                ", tags=" + tags +
                ", covers=" + covers +
                ", series=" + series +
                ", directors=" + directors +
                ", magnets=" + magnets +
                ", webSiteUrl='" + webSiteUrl + '\'' +
                ", previewVideo='" + previewVideo + '\'' +
                '}';
    }
}
