package com.dentalclinic.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "bill")
public class Bill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private Long aid;
    private Double totalamount;
    private String ispaid;


}
