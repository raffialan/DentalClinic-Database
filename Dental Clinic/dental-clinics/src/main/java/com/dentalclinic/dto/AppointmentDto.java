package com.dentalclinic.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppointmentDto {

    private Long aid;
    private String description;
    private String appointmentdate;
    private Long pid;
    private Long sid;
    private Long cid;
    private String isvisit;
    private String patientName;
    private String dentistName;
    private String clinicName;
    private String startDate;
    private String endDate;
    private Long count;
    private String inputquery;

}
