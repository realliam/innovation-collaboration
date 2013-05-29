package com.hp.it.innovation.collaboration.dto;


public class UploadFileDTO {

    private String name;
    private Integer size;
    private String url;
    private String thumbnailURL;
    private String deleteURL;
    private String deleteType;

    public UploadFileDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getDeleteURL() {
        return deleteURL;
    }

    public void setDeleteURL(String deleteURL) {
        this.deleteURL = deleteURL;
    }

    public String getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    public UploadFileDTO(String name, Integer size, String url) {
        super();
        this.name = name;
        this.size = size;
        this.url = url;
    }

    public UploadFileDTO(String name,
                         Integer size,
                         String url,
                         String thumbnail_url,
                         String delete_url,
                         String delete_type) {
        super();
        this.name = name;
        this.size = size;
        this.url = url;
        this.thumbnailURL = thumbnail_url;
        this.deleteURL = delete_url;
        this.deleteType = delete_type;
    }

    @Override
    public String toString() {
        return "UploadedFile [name="
               + name
               + ", size="
               + size
               + ", url="
               + url
               + ", thumbnail_url="
               + thumbnailURL
               + ", delete_url="
               + deleteURL
               + ", delete_type="
               + deleteType
               + "]";
    }
}
