package com.pyapps.practice.Models;

/**
 * Created by Yallanki on 8/17/2017.
 */

public class Post {
    String imageViewUrl;
    String imageButtonUrl;

    public Post(String imageViewUrl, String imageButtonUrl) {
        this.imageViewUrl = imageViewUrl;
        this.imageButtonUrl = imageButtonUrl;
    }

    public String getImageViewUrl() {
        return imageViewUrl;
    }

    public String getImageButtonUrl() {
        return imageButtonUrl;
    }
}
