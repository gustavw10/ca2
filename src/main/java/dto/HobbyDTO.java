package dto;

import entities.Hobby;

public class HobbyDTO {

    private String name;
    private String wikiLink;
    private String category;
    private String type;
    
    public HobbyDTO(Hobby h){
        this.category = h.getCategory();
        this.name = h.getName();
        this.type = h.getType();
        this.wikiLink = h.getWikiLink();
    }

    public HobbyDTO() {
    }

    public HobbyDTO(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
