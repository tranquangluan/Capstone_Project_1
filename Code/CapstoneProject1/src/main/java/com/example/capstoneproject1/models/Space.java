package com.example.capstoneproject1.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceId", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "title", columnDefinition = "nvarchar(255)")
    private String title;
    @ManyToOne
    @JoinColumn(name = "statusId", columnDefinition = "int", nullable = false)
    private Status status;
    @Column(name = "price", columnDefinition = "DECIMAL(10,2)", nullable = false)
    @Min(1)
    private BigDecimal price;
    @OneToMany(mappedBy = "spaceId")
    private List<Image> images;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "bathroomNumbers", columnDefinition = "int default 0")
    private Integer bathroomNumbers;
    @Column(name = "bedroomNumbers", columnDefinition = "int default 0")
    private Integer bedroomNumbers;
    @Column(name = "peopleNumbers", columnDefinition = "int default 0")
    private Integer peopleNumbers;
    @Column(name = "area", columnDefinition = "float")
    @Min(1)
    private float area;
    @Column(name = "province", columnDefinition = "nvarchar(50)")
    private String province;
    @Column(name = "district", columnDefinition = "nvarchar(50)")
    private String district;
    @Column(name = "ward", columnDefinition = "nvarchar(50)")
    private String ward;
    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;
    @ManyToOne
    @JoinColumn(name = "categoryId", columnDefinition = "int", nullable = false)
    private CategorySpace categoryId;
    @ManyToOne
    @JoinColumn(name = "ownerId",referencedColumnName = "userId",foreignKey = @ForeignKey(name = "fk_space_user"))
    private User ownerId ;
    @Column(name = "createdAt", columnDefinition = "DATETIME", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @OneToMany(mappedBy = "space", orphanRemoval = true)
    @JsonIgnore
    private List<Favourite> favourites = new ArrayList<>();

    public Space() {
    }

    public Space(String title, Status status, BigDecimal price, String description, Integer bathroomNumbers, Integer bedroomNumbers, Integer peopleNumbers, float area, String province, String district, String ward, String address, CategorySpace categoryId, User ownerId) {
        this.title = title;
        this.status = status;
        this.price = price;
        this.description = description;
        this.bathroomNumbers = bathroomNumbers;
        this.bedroomNumbers = bedroomNumbers;
        this.peopleNumbers = peopleNumbers;
        this.area = area;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
    }

    public Space(Integer id, String title, Status status, BigDecimal price, List<Image> images, String description, Integer bathroomNumbers, Integer bedroomNumbers, Integer peopleNumbers, float area, String province, String district, String ward, String address, CategorySpace categoryId, User ownerId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.price = price;
        this.images = images;
        this.description = description;
        this.bathroomNumbers = bathroomNumbers;
        this.bedroomNumbers = bedroomNumbers;
        this.peopleNumbers = peopleNumbers;
        this.area = area;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
    }

    public Space(Integer id, String title, Status status, BigDecimal price, List<Image> images, String description, Integer bathroomNumbers, Integer bedroomNumbers, Integer peopleNumbers, float area, String province, String district, String ward, String address, CategorySpace categoryId, User ownerId, Date createdAt, List<Favourite> favourites) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.price = price;
        this.images = images;
        this.description = description;
        this.bathroomNumbers = bathroomNumbers;
        this.bedroomNumbers = bedroomNumbers;
        this.peopleNumbers = peopleNumbers;
        this.area = area;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.favourites = favourites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public Integer getPeopleNumbers() {
        return peopleNumbers;
    }

    public void setPeopleNumbers(Integer peopleNumbers) {
        this.peopleNumbers = peopleNumbers;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CategorySpace getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategorySpace categoryId) {
        this.categoryId = categoryId;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }
}
