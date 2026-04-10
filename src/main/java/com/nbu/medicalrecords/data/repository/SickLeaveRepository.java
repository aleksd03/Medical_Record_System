package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
    @Query("SELECT MONTH(s.startDate), COUNT(s) FROM SickLeave s GROUP BY MONTH(s.startDate) ORDER BY COUNT(s) DESC")
    List<Object[]> findMonthWithMostSickLeaves();

    @Query("SELECT a.doctor, COUNT(s) FROM SickLeave s JOIN s.appointment a GROUP BY a.doctor ORDER BY COUNT(s) DESC")
    List<Object[]> findDoctorsWithMostSickLeaves();
}
