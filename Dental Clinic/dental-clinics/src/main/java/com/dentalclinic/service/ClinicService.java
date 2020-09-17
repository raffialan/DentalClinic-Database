package com.dentalclinic.service;

import com.dentalclinic.dto.AppointmentDto;
import com.dentalclinic.dto.StaffDto;
import com.dentalclinic.exception.RecordNotFoundException;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.model.Clinic;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Staff;
import com.dentalclinic.model.Treatment;
import com.dentalclinic.repository.AppointmentRepository;
import com.dentalclinic.repository.BillRepository;
import com.dentalclinic.repository.ClinicRepository;
import com.dentalclinic.repository.PatientRepository;
import com.dentalclinic.repository.StaffRepository;
import com.dentalclinic.repository.TreatmentRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class ClinicService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    TreatmentRepository treatmentRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Format formatter = new SimpleDateFormat("yyyy-MM-dd");

    public List<Patient> getAllPatient() {


        List<Patient> result = (List<Patient>) patientRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Patient>();
        }


    }

    public Patient getPatientById(Long id) throws RecordNotFoundException {
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {
            return patient.get();
        } else {
            throw new RecordNotFoundException("No Patient record exist for given id");
        }
    }

    public Patient createOrUpdatePatient(Patient entity) {
        if (entity.getPid() == null) {
            entity = patientRepository.save(entity);

            return entity;
        } else {
            Optional<Patient> patient = patientRepository.findById(entity.getPid());

            if (patient.isPresent()) {
                Patient newEntity = patient.get();
                newEntity.setAddress(entity.getAddress());
                newEntity.setAge(entity.getAge());
                newEntity.setContactno(entity.getContactno());
                newEntity.setEmail(entity.getEmail());
                newEntity.setName(entity.getName());
                newEntity.setSex(entity.getSex());

                newEntity = patientRepository.save(newEntity);

                return newEntity;
            } else {
                entity = patientRepository.save(entity);

                return entity;
            }
        }
    }

    public void deletePatientById(Long id) throws RecordNotFoundException {
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {

            String deleteTreatment = "delete from treatment where aid in (select aid from appointment where pid = ?)";
            jdbcTemplate.update(deleteTreatment, id);

            String deleteBill = "delete from bill where aid in (select aid from appointment where pid = ?)";
            jdbcTemplate.update(deleteBill, id);

            String deleteAppointmentForPatient = "delete from appointment where pid=" + id;
            jdbcTemplate.update(deleteAppointmentForPatient);

            String deletePatient = "delete from patient where pid=" + id;
            jdbcTemplate.update(deletePatient);


        } else {
            throw new RecordNotFoundException("No patient record exist for given id");
        }
    }

    public List<Clinic> getAllClinic() {

        List<Clinic> result = (List<Clinic>) clinicRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Clinic>();
        }

    }

    public List<Staff> getAllStaff() {

        List<Staff> result = (List<Staff>) staffRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Staff>();
        }

    }

    public List<Treatment> getAllTreatment() {

        List<Treatment> result = (List<Treatment>) treatmentRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Treatment>();
        }

    }

    public List<Bill> getAllBill() {

        List<Bill> result = (List<Bill>) billRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Bill>();
        }

    }

    public List<Staff> getStaffByClinicId() {

        String query = "SELECT s.sid,s.name,c.name FROM STAFF s, clinic c where role = 'Dentist' and s.cid=c.cid order by c.name";
        return jdbcTemplate.query(query, new RowMapper<Staff>() {
            public Staff mapRow(ResultSet rs, int row) throws SQLException {
                Staff staff = new Staff();
                staff.setSid(rs.getLong(1));
                staff.setName(rs.getString(2) + " (" + rs.getString(3) + ")");
                return staff;
            }
        });

    }

    public List<Appointment> getAllAppointment() {

        List<Appointment> result = (List<Appointment>) appointmentRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Appointment>();
        }
    }

    public Appointment getAppointmentById(Long id) throws RecordNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            return appointment.get();
        } else {
            throw new RecordNotFoundException("No Appointment record exist for given id");
        }
    }

    public AppointmentDto getAptById(Long id) {

        String query = "SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name as patientName,s.name as dentistName,c.name as clinicName FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid and a.aid=?";

        AppointmentDto appointmentDto = jdbcTemplate
            .queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(AppointmentDto.class));
        return appointmentDto;
    }


    public Appointment createOrUpdateAppointment(Appointment entity) {
        if (entity.getAid() == null) {
            entity = appointmentRepository.save(entity);
            return entity;
        } else {
            Optional<Appointment> appointment = appointmentRepository.findById(entity.getAid());

            if (appointment.isPresent()) {
                Appointment appointment1 = appointment.get();

                appointment1.setAppointmentdate(entity.getAppointmentdate());
                appointment1.setCid(entity.getCid());
                appointment1.setDescription(entity.getDescription());
                appointment1.setPid(entity.getPid());
                appointment1.setSid(entity.getSid());
                appointment1.setIsvisit(entity.getIsvisit());

                appointment1 = appointmentRepository.save(appointment1);

                return appointment1;
            } else {
                entity = appointmentRepository.save(entity);

                return entity;
            }
        }
    }

    public void deleteAppointmentById(Long id) throws RecordNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {

            String deleteTreatment = "delete from treatment where aid=" + id;
            jdbcTemplate.update(deleteTreatment);

            String deleteBill = "delete from bill where aid=" + id;
            jdbcTemplate.update(deleteBill);

            String deleteAppointment = "delete from appointment where aid=" + id;
            jdbcTemplate.update(deleteAppointment);


        } else {
            throw new RecordNotFoundException("No appointment record exist for given id");
        }
    }

    public List<AppointmentDto> getAppointments() {

        String query = "SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid order by aid";
        return jdbcTemplate.query(query, new RowMapper<AppointmentDto>() {
            public AppointmentDto mapRow(ResultSet rs, int row) throws SQLException {
                AppointmentDto appointmentDto = new AppointmentDto();

                appointmentDto.setAid(rs.getLong(1));
                appointmentDto.setDescription(rs.getString(2));
                String date = formatter.format(rs.getDate(3));
                appointmentDto.setAppointmentdate(date);
                appointmentDto.setPid(rs.getLong(4));
                appointmentDto.setSid(rs.getLong(5));
                appointmentDto.setCid(rs.getLong(6));
                appointmentDto.setIsvisit(rs.getString(7));
                appointmentDto.setPatientName(rs.getString(8));
                appointmentDto.setDentistName(rs.getString(9));
                appointmentDto.setClinicName(rs.getString(10));

                return appointmentDto;
            }
        });

    }

    public List<AppointmentDto> getAppointmentsForDentist(String dentist, String startDate, String endDate) {

        String query =
            "SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid and \n"
                + "a.sid in (select sid from staff where name = '" + dentist + "') and appointmentdate between '"
                + startDate + "' and '" + endDate + "' order by a.aid";

        System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<AppointmentDto>() {
            public AppointmentDto mapRow(ResultSet rs, int row) throws SQLException {
                AppointmentDto appointmentDto = new AppointmentDto();

                appointmentDto.setAid(rs.getLong(1));
                appointmentDto.setDescription(rs.getString(2));
                String date = formatter.format(rs.getDate(3));
                appointmentDto.setAppointmentdate(date);
                appointmentDto.setPid(rs.getLong(4));
                appointmentDto.setSid(rs.getLong(5));
                appointmentDto.setCid(rs.getLong(6));
                appointmentDto.setIsvisit(rs.getString(7));
                appointmentDto.setPatientName(rs.getString(8));
                appointmentDto.setDentistName(rs.getString(9));
                appointmentDto.setClinicName(rs.getString(10));

                return appointmentDto;
            }
        });

    }

    public List<AppointmentDto> getAppointmentsForClinic(String clinic, String date) {

        String query =
            "SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid and \n"
                + " a.cid in (select cid from clinic where name = '" + clinic + "') and appointmentdate= '" + date
                + "'  order by a.aid";

        System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<AppointmentDto>() {
            public AppointmentDto mapRow(ResultSet rs, int row) throws SQLException {
                AppointmentDto appointmentDto = new AppointmentDto();

                appointmentDto.setAid(rs.getLong(1));
                appointmentDto.setDescription(rs.getString(2));
                String date = formatter.format(rs.getDate(3));
                appointmentDto.setAppointmentdate(date);
                appointmentDto.setPid(rs.getLong(4));
                appointmentDto.setSid(rs.getLong(5));
                appointmentDto.setCid(rs.getLong(6));
                appointmentDto.setIsvisit(rs.getString(7));
                appointmentDto.setPatientName(rs.getString(8));
                appointmentDto.setDentistName(rs.getString(9));
                appointmentDto.setClinicName(rs.getString(10));

                return appointmentDto;
            }
        });

    }

    public List<AppointmentDto> getAppointmentsForPatient(String patient) {

        String query =
            "SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid";

        System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<AppointmentDto>() {
            public AppointmentDto mapRow(ResultSet rs, int row) throws SQLException {
                AppointmentDto appointmentDto = new AppointmentDto();

                appointmentDto.setAid(rs.getLong(1));
                appointmentDto.setDescription(rs.getString(2));
                String date = formatter.format(rs.getDate(3));
                appointmentDto.setAppointmentdate(date);
                appointmentDto.setPid(rs.getLong(4));
                appointmentDto.setSid(rs.getLong(5));
                appointmentDto.setCid(rs.getLong(6));
                appointmentDto.setIsvisit(rs.getString(7));
                appointmentDto.setPatientName(rs.getString(8));
                appointmentDto.setDentistName(rs.getString(9));
                appointmentDto.setClinicName(rs.getString(10));

                return appointmentDto;
            }
        });

    }

    public List<AppointmentDto> getMissedAppointment() {

        String query =
            "select a.pid,count(*) as count, p.name from appointment a, patient p where a.isvisit='No' and a.pid=p.pid group by a.pid having count >=1";

       System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<AppointmentDto>() {
            public AppointmentDto mapRow(ResultSet rs, int row) throws SQLException {
                AppointmentDto appointmentDto = new AppointmentDto();

                appointmentDto.setPid(rs.getLong(1));
                appointmentDto.setCount(rs.getLong(2));
                appointmentDto.setPatientName(rs.getString(3));

                return appointmentDto;
            }
        });

    }


    public List<StaffDto> getAllDentist() {

        String query = "SELECT s.sid, s.name, s.role,c.name,s.sex,s.age,s.email,s.contactno FROM STAFF s, clinic c where role = 'Dentist' and s.cid=c.cid order by c.name";

        System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<StaffDto>() {
            public StaffDto mapRow(ResultSet rs, int row) throws SQLException {
                StaffDto staff = new StaffDto();

                staff.setSid(rs.getLong(1));
                staff.setName(rs.getString(2));
                staff.setRole(rs.getString(3));
                staff.setClinicName(rs.getString(4));
                staff.setSex(rs.getString(5));
                staff.setAge(rs.getInt(6));
                staff.setEmail(rs.getString(7));
                staff.setContactno(rs.getLong(8));

                return staff;
            }
        });

    }

    public List<Treatment> getTreatmentByAppointMentId(Long appointmentId) {

        String query = "SELECT * FROM treatment where aid =" + appointmentId;
        System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<Treatment>() {
            public Treatment mapRow(ResultSet rs, int row) throws SQLException {
                Treatment treatment = new Treatment();

                treatment.setTid(rs.getLong(1));
                treatment.setName(rs.getString(2));
                treatment.setAid(rs.getLong(3));
                treatment.setCost(rs.getDouble(4));

                return treatment;
            }
        });

    }

    public List<Bill> getunpaidBill() {

        String query = "SELECT * FROM BILL where ispaid = 'No'";
        System.out.println(query);

        return jdbcTemplate.query(query, new RowMapper<Bill>() {
            public Bill mapRow(ResultSet rs, int row) throws SQLException {
                Bill bill = new Bill();

                bill.setBid(rs.getLong(1));
                bill.setAid(rs.getLong(2));
                bill.setTotalamount(rs.getDouble(3));
                bill.setIspaid(rs.getString(4));

                return bill;
            }
        });

    }

    public List<Map<String, Object>> fireQuery(String query) {

        List<Map<String, Object>> result = null;

        if (query.toLowerCase().contains("select")) {

            result = jdbcTemplate.queryForList(query);

         //   System.out.println(result);


        } else {
            jdbcTemplate.update(query);
        }

        return result;
    }

}