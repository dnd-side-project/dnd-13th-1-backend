package team1.allo.preset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.preset.entity.PresetHouseWork;

public interface PresetHouseWorkRepository extends JpaRepository<PresetHouseWork, Long> {

	List<PresetHouseWork> findByPresetHouseWorkCategoryId(Long presetHouseWorkCategoryId);
}
