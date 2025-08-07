package team1.housework.preset.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.preset.entity.PresetTag;

public interface PresetTagRepository extends JpaRepository<PresetTag, Long> {
}
