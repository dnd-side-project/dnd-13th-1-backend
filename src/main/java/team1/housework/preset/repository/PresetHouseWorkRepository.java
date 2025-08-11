package team1.housework.preset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.preset.entity.PresetHouseWork;

public interface PresetHouseWorkRepository extends JpaRepository<PresetHouseWork, Long> {

	List<PresetHouseWork> findByPresetHouseWorkCategoryId(Long presetHouseWorkCategoryId);
}
