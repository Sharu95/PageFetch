package me.kulam.pagefetch;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Sharu on 14/03/2016.
 */
public class Page{
    private String title;
    private String description;
    private Image image;
    private String url;
    private String category;

    private final String stdTech = " is a tech page. Keep updated on your tech knowledge!";
    private final String stdNews = " , a news page. Read about domestic and foreign news.";
    private final String stdSocial = " is a social page. Updates on events near you!";
    private final String stdEducation = ", a page to expand your knowledge base.";

    private final String defDesc = "No description. Add optional descriptions by editing card info";


    /*Intentional*/
    public Page(String title, String category, String url,String description)
    {
        this.title = title;
        this.category = category;

        if(description == null){
            System.out.println("Add desc");
            switch(category){
                case "Tech":
                    this.description = title + stdTech; break;
                case "Technology":
                    this.description = title + stdTech; break;
                case "News":
                    this.description = title + stdNews; break;
                case "Social":
                    this.description = title + stdSocial; break;
                case "Education":
                    this.description = title + stdEducation; break;
                case "Educational":
                    this.description = title + stdEducation; break;
                default:
                    this.description = defDesc;
            }
        }
        else{
            this.description = description;
        }

        char[] checkUrl = new char[5];
        url.getChars(0,4,checkUrl, 0);
        String t = String.copyValueOf(checkUrl);

        if(t.startsWith("http") || t.startsWith("https")){
            this.url = url;
        }//Check if url is saved before or new page
        else{
            this.url = "http://"+url;
        }

        //TODO: Set image resource when creating page.
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
