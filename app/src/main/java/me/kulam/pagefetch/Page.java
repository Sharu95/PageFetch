package me.kulam.pagefetch;

import android.media.Image;

/**
 * Created by Sharu on 14/03/2016.
 */
public class Page
{
    private String title;
    private String description;
    private Image image;

    public Page(String title, String description)
    {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
}
