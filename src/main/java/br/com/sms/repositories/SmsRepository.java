package br.com.sms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sms.models.SmsCodes;

public interface SmsRepository extends JpaRepository<SmsCodes, Long> {
    @Query(value = "SELECT * FROM smscodes AS sms WHERE BINARY sms.phone = :phone", nativeQuery = true)
    public Optional<SmsCodes> findByPhone(String phone);
    public Boolean existsByPhone(String phone);

}
