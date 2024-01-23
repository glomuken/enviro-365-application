package com.enviro.assessment.grad001.KamwanyaMukendi.model;


import com.enviro.assessment.grad001.KamwanyaMukendi.WithdrawalNotice;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String name;

    private Double currentBalance;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WithdrawalNotice> withdrawalNotices = new ArrayList<>();


    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }



    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }
    public void addWithdrawalNotice(WithdrawalNotice withdrawalNotice) {
        this.withdrawalNotices.add(withdrawalNotice);
    }

    public List<WithdrawalNotice> getWithdrawalNotices() {
        return withdrawalNotices;
    }
}
