package me.kulam.pagefetch;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Sharu on 14/03/2016.
 */
public class Page implements java.io.Serializable{
    private String title;
    private String description;
    private Image image;
    private String url;
    private String category;

    /*Intentional*/
    public Page(String title, String category, String url,String description)
    {
        this.title = title;
        this.description = description;
        this.url = "http://"+url;
        this.category = category;

        //TODO: Set image resource when creating page.
    }

    /*Optional*/
    public Page(String title, String category, String url)
    {
        this.title = StringUsage.stdFormat(title);
        this.category = category;
        this.url = "http://www."+url;
        //TODO: Set image resource when creating page.

        /**
         * tes
         * test
         * st
         * es
         * tes
         * tset
         * s
         */
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return description;
    }

    public String getUrl(){
        return url;
    }

    public String getCategory(){
        return category;
    }
}
