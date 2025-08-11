package team1.housework.character.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.character.repository.CharacterRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacterService {

	private final CharacterRepository characterRepository;

	public Boolean existsById(Long id) {
		return characterRepository.existsById(id);
	}

}
