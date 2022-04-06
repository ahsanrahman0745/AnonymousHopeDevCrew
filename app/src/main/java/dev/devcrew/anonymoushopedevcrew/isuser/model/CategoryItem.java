package dev.devcrew.anonymoushopedevcrew.isuser.model;

public class CategoryItem {
    String images;
    String name;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryItem(String images, String name) {
        this.images = images;
        this.name = name;
    }
}
