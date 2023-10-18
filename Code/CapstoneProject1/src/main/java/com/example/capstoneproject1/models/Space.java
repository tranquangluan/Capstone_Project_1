package com.example.capstoneproject1.models;



import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Space {
    @Id
    @GeneratedValue
    @Column(name = "spaceId")
    private Integer id;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image imageId;
    @Column(name = "description")
    private String description;
    @Column(name = "bathroomNumbers")
    private Integer bathroomNumbers;
    @Column(name = "bedroomNumbers")
    private Integer bedroomNumbers;
    @Column(name = "area")
    private String area;
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location locationId;



    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategorySpace categoryId ;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId ;

    public Space() {
    }


    public Space(Integer id, Boolean status, BigDecimal price, Image imageId, String description, Integer bathroomNumbers, Integer bedroomNumbers, String area, Location locationId, CategorySpace categoryId, User userId) {
        this.id = id;
        this.status = status;
        this.price = price;
        this.imageId = imageId;
        this.description = description;
        this.bathroomNumbers = bathroomNumbers;
        this.bedroomNumbers = bedroomNumbers;
        this.area = area;
        this.locationId = locationId;
        this.categoryId = categoryId;
        this.userId = userId;
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

    public Image getImageId() {
        return imageId;
    }

    public void setImageId(Image imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBathroomNumbers() {
        return bathroomNumbers;
    }

    public void setBathroomNumbers(Integer bathroomNumbers) {
        this.bathroomNumbers = bathroomNumbers;
    }

    public Integer getBedroomNumbers() {
        return bedroomNumbers;
    }

    public void setBedroomNumbers(Integer bedroomNumbers) {
        this.bedroomNumbers = bedroomNumbers;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public CategorySpace getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategorySpace categoryId) {
        this.categoryId = categoryId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
