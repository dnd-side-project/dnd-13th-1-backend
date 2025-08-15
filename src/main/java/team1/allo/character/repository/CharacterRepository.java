package team1.allo.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.character.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
