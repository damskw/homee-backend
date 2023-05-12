package com.codecool.homee_backend.repository;

import com.codecool.homee_backend.entity.DeviceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, UUID> {
    @Query("SELECT DISTINCT d FROM DeviceActivity d WHERE d.device.id=:id ORDER BY d.date DESC")
    List<DeviceActivity> findAllByDeviceId(UUID id);

    @Query("SELECT a FROM DeviceActivity a JOIN a.device d JOIN d.space s JOIN s.homeeUsers u WHERE u.id =:id ORDER BY a.date DESC")
    List<DeviceActivity> findAllByUserId(UUID id);
}
