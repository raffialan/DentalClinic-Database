package com.dentalclinic.repository;

import com.dentalclinic.model.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<Bill,Long> {

}
