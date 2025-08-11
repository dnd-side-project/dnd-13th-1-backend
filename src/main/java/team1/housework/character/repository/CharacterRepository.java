package team1.housework.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.character.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
