package com.enviro.assessment.grad001.KamwanyaMukendi;

import com.enviro.assessment.grad001.KamwanyaMukendi.model.Product;
import jakarta.persistence.*;

@Entity
public class WithdrawalNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DOUBLE")
    private Double withdrawalAmount;

    @Column(columnDefinition = "VARCHAR")
    private String withdrawalDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // ... getter and setter methods ...

    public String getWithdrawalDate() {
        return withdrawalDate;
    }

    public Double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setWithdrawalDate(String withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }
}