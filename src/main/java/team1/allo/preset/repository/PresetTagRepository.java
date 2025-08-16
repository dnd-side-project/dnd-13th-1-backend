package team1.allo.preset.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.preset.entity.PresetTag;

public interface PresetTagRepository extends JpaRepository<PresetTag, Long> {
}
