package com.payment_microservice.paymentService.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Currency extends  BaseModel{

    public String code;
    public String country;
    public String name;
}
