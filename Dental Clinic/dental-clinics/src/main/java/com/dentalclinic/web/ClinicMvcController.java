package com.dentalclinic.web;

import com.dentalclinic.dto.AppointmentDto;
import com.dentalclinic.dto.StaffDto;
import com.dentalclinic.exception.RecordNotFoundException;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.model.Clinic;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Staff;
import com.dentalclinic.model.Treatment;
import com.dentalclinic.service.ClinicService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ClinicMvcController {

    @Autowired
    ClinicService service;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping
    public String getAllPatient(Model model) {
        List<Patient> list = service.getAllPatient();
        List<AppointmentDto> appointmentDtoList = service.getAppointments();

        model.addAttribute("patients", list);
        model.addAttribute("appointments", appointmentDtoList);
        return "list-patient";
    }

    @RequestMapping(path = {"/editPatient", "/editPatient/{id}"})
    public String editPatientById(Model model, @PathVariable("id") Optional<Long> id)
        throws RecordNotFoundException {
        if (id.isPresent()) {
            Patient entity = service.getPatientById(id.get());
            model.addAttribute("patient", entity);
        } else {
            model.addAttribute("patient", new Patient());
        }
        return "add-edit-patient";
    }

    @RequestMapping(path = "/deletePatient/{id}")
    public String deletePatientById(Model model, @PathVariable("id") Long id)
        throws RecordNotFoundException {
        service.deletePatientById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createPatient", method = RequestMethod.POST)
    public String createOrUpdatePatient(Patient patient) {
        service.createOrUpdatePatient(patient);
        return "redirect:/";
    }

    @RequestMapping(path = {"/editAppointment", "/editAppointment/{id}"})
    public String editAppointmentById(Model model, @PathVariable("id") Optional<Long> id) {
        List<Patient> patients = service.getAllPatient();
        model.addAttribute("patients", patients);

        List<Clinic> clinicList = service.getAllClinic();
        model.addAttribute("clinics", clinicList);

        List<Staff> staff = service.getStaffByClinicId();
        model.addAttribute("staffs", staff);

        if (id.isPresent()) {
            AppointmentDto entity = service.getAptById(id.get());
            model.addAttribute("appointment", entity);
        } else {
            model.addAttribute("appointment", new AppointmentDto());

        }
        return "add-edit-appointment";
    }

    @RequestMapping(path = "/deleteAppointment/{id}")
    public String deleteAppointmentById(Model model, @PathVariable("id") Long id)
        throws RecordNotFoundException {
        service.deleteAppointmentById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createAppointment", method = RequestMethod.POST)
    public String createOrUpdateAppointment(AppointmentDto appointmentDto) throws ParseException {

        Appointment appointment = new Appointment();

        appointment.setAid(appointmentDto.getAid());
        appointment.setIsvisit(appointmentDto.getIsvisit());
        appointment.setSid(appointmentDto.getSid());
        appointment.setPid(appointmentDto.getPid());
        appointment.setCid(appointmentDto.getCid());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setAppointmentdate(formatter.parse(appointmentDto.getAppointmentdate()));

        service.createOrUpdateAppointment(appointment);
        return "redirect:/";
    }

    @RequestMapping(path = "/sql")
    public String sql(Model model) {
        List<StaffDto> staffDtos = service.getAllDentist();
        model.addAttribute("staffDtos", staffDtos);

        List<AppointmentDto> appointmentDentist = service
            .getAppointmentsForDentist("William", "2020-04-01", "2020-04-03");
        //System.out.println(appointmentDentist);
        model.addAttribute("appointmentsDentist", appointmentDentist);
        model.addAttribute("appInputDentist", new AppointmentDto());

        List<AppointmentDto> appointmentClinic = service.getAppointmentsForClinic("AMS Clinic", "2020-04-01");
        model.addAttribute("appointmentsClinic", appointmentClinic);
        model.addAttribute("appInputClinic", new AppointmentDto());

        List<AppointmentDto> appointmentPatient = service.getAppointmentsForPatient("Marc");
        model.addAttribute("appointmentsPatient", appointmentPatient);
        model.addAttribute("appInputPatient", new AppointmentDto());
        model.addAttribute("searchPatientName","Marc");

        List<AppointmentDto> missedAppointment = service.getMissedAppointment();
        model.addAttribute("missedAppointment", missedAppointment);

        List<Treatment> treatmentList = service.getTreatmentByAppointMentId(1l);
        model.addAttribute("treatments", treatmentList);
        model.addAttribute("treatmentInput", new Treatment());

        List<Bill> unpaidBills = service.getunpaidBill();
        model.addAttribute("unpaidBills", unpaidBills);


        return "sql";
    }

    @RequestMapping(path = "/searchAppointmentByDentist", method = RequestMethod.POST)
    public String searchAppointmentByDentist(AppointmentDto appointmentDto, Model model) {

        List<AppointmentDto> appointmentDtoList = service
            .getAppointmentsForDentist(appointmentDto.getDentistName(), appointmentDto.getStartDate(),
                appointmentDto.getEndDate());
        model.addAttribute("appointmentsDentist", appointmentDtoList);
        model.addAttribute("appInputDentist", new AppointmentDto());

        model.addAttribute("appInputClinic", new AppointmentDto());
        model.addAttribute("appInputPatient", new AppointmentDto());
        model.addAttribute("treatmentInput", new Treatment());

        return "sql";
    }

    @RequestMapping(path = "/searchAppointmentByClinic", method = RequestMethod.POST)
    public String searchAppointmentByClinic(AppointmentDto appointmentDto, Model model) {

        System.out.println(appointmentDto);
        List<AppointmentDto> appointmentDtoList = service
            .getAppointmentsForClinic(appointmentDto.getClinicName(), appointmentDto.getStartDate());
        System.out.println(appointmentDtoList);
        model.addAttribute("appointmentsClinic", appointmentDtoList);
        model.addAttribute("appInputClinic", new AppointmentDto());

        model.addAttribute("appInputDentist", new AppointmentDto());
        model.addAttribute("appInputPatient", new AppointmentDto());
        model.addAttribute("treatmentInput", new Treatment());

        return "sql";
    }

    @RequestMapping(path = "/searchAppointmentByPatient", method = RequestMethod.POST)
    public String searchAppointmentByPatient(AppointmentDto appointmentDto, Model model) {

        System.out.println(appointmentDto);
        List<AppointmentDto> appointmentDtoList = service.getAppointmentsForPatient(appointmentDto.getPatientName());
        System.out.println(appointmentDtoList);
        model.addAttribute("appointmentsPatient", appointmentDtoList);
        model.addAttribute("appInputPatient", new AppointmentDto());

        model.addAttribute("appInputDentist", new AppointmentDto());
        model.addAttribute("appInputClinic", new AppointmentDto());
        model.addAttribute("treatmentInput", new Treatment());

        model.addAttribute("searchPatientName",appointmentDto.getPatientName());

        return "sql";
    }

    @RequestMapping(path = "/searchTreatMentByAppId", method = RequestMethod.POST)
    public String searchTreatMentByAppId(Treatment treatment, Model model) {

        List<Treatment> treatmentList = service.getTreatmentByAppointMentId(treatment.getAid());
        model.addAttribute("treatments", treatmentList);
        model.addAttribute("treatmentInput", new Treatment());

        model.addAttribute("appInputDentist", new AppointmentDto());
        model.addAttribute("appInputClinic", new AppointmentDto());
        model.addAttribute("appInputPatient", new AppointmentDto());

        return "sql";
    }

    @RequestMapping(path = "/dba")
    public String dba(Model model) {

        List<Patient> patients = service.getAllPatient();
        model.addAttribute("patients", patients);

        List<Clinic> clinics = service.getAllClinic();
        model.addAttribute("clinics", clinics);

        List<Staff> staffs = service.getAllStaff();
        model.addAttribute("staffs", staffs);

        List<Appointment> appointments = service.getAllAppointment();
        model.addAttribute("appointments", appointments);

        List<Treatment> treatments = service.getAllTreatment();
        model.addAttribute("treatments", treatments);

        List<Bill> bills = service.getAllBill();
        model.addAttribute("bills", bills);

        model.addAttribute("input",new AppointmentDto());

        model.addAttribute("header", null);
        model.addAttribute("entry", null);

        return "dba";
    }

    @RequestMapping(path = "/fireQuery", method = RequestMethod.POST)
    public String fireQuery(AppointmentDto appointmentDto, Model model) {

        System.out.println(appointmentDto);

        List<Map<String, Object>> result = service.fireQuery(appointmentDto.getInputquery());


        List<Patient> patients = service.getAllPatient();
        model.addAttribute("patients", patients);

        List<Clinic> clinics = service.getAllClinic();
        model.addAttribute("clinics", clinics);

        List<Staff> staffs = service.getAllStaff();
        model.addAttribute("staffs", staffs);

        List<Appointment> appointments = service.getAllAppointment();
        model.addAttribute("appointments", appointments);

        List<Treatment> treatments = service.getAllTreatment();
        model.addAttribute("treatments", treatments);

        List<Bill> bills = service.getAllBill();
        model.addAttribute("bills", bills);

        model.addAttribute("input",new AppointmentDto());

        if(result != null && !result.isEmpty()){
            Map<String,Object> entry = result.get(0);
            List<String> headers = new ArrayList<String>(entry.keySet());

            model.addAttribute("headers", headers);
            model.addAttribute("rows", result);

        }



        return "dba";
    }

    @RequestMapping(path = "/erdiagram")
    public String erdiagram(Model model) {

        return "erDiagram";
    }

    @RequestMapping(path = "/home")
    public String home(Model model) {

        return "redirect:/";
    }

}
