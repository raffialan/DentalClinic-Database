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
@Table(name = "treatment")
public class Treatment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
    private String name;
    private Long aid;
    private Double cost;

}
