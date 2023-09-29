package com.example.capstoneproject1.models;



import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Space {
    @Id
    @Column(name = "Space_Id")
    private Integer id;
    @Column(name = "Status")
    private Boolean status;
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "Image")
    private String image;
    @Column(name = "Description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "Category_id")
    private CategorySpace categorySpace ;

    public Space() {
    }

    public Space(Integer id, Boolean status, BigDecimal price, String image, String description, CategorySpace categorySpace) {
        this.id = id;
        this.status = status;
        this.price = price;
        this.image = image;
        this.description = description;
        this.categorySpace = categorySpace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategorySpace getCategorySpace() {
        return categorySpace;
    }

    public void setCategorySpace(CategorySpace categorySpace) {
        this.categorySpace = categorySpace;
    }
}
