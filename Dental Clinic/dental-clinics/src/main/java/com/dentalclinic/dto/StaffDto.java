package com.dentalclinic.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StaffDto {

    private Long sid;
    private String name;
    private String role;
    private String clinicName;
    private String sex;
    private int age;
    private String email;
    private Long contactno;

}
