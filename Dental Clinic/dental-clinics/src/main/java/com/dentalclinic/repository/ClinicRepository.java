package com.dentalclinic.repository;

import com.dentalclinic.model.Clinic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends CrudRepository<Clinic,Long> {

}
