package com.itsu.threedays.repository;

import com.itsu.threedays.entity.HabitEntity;
import com.itsu.threedays.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<HabitEntity,Long> {
    List<HabitEntity> findAllByUserId(UserEntity userId);
    List<HabitEntity> findAllByUserIdAndDeleteYnAndStopDateIsNull(UserEntity userId, boolean deleteYn);

    List<HabitEntity> findAllByUserIdAndDeleteYn(boolean deleteYn);

    Optional<HabitEntity> findById(Long habitId);

}
