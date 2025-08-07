package team1.housework.preset.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.preset.entity.PresetHouseWorkCategory;
import team1.housework.preset.entity.PresetPlace;
import team1.housework.preset.entity.PresetTag;
import team1.housework.preset.repository.PresetHouseWorkCategoryRepository;
import team1.housework.preset.repository.PresetHouseWorkRepository;
import team1.housework.preset.repository.PresetPlaceRepository;
import team1.housework.preset.repository.PresetTagRepository;
import team1.housework.preset.service.dto.PresetHouseWorkResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PresetService {

	private final PresetPlaceRepository presetPlaceRepository;
	private final PresetTagRepository presetTagRepository;
	private final PresetHouseWorkRepository presetHouseWorkRepository;
	private final PresetHouseWorkCategoryRepository presetHouseWorkCategoryRepository;

	public List<PresetPlace> getAllPlaces() {
		return presetPlaceRepository.findAll();
	}

	public List<PresetTag> getAllTags() {
		return presetTagRepository.findAll();
	}

	public List<PresetHouseWorkResponse> getPresetHouseWorks() {
		List<PresetHouseWorkCategory> categories = presetHouseWorkCategoryRepository.findAll();
		List<PresetHouseWorkResponse> results = new ArrayList<>();
		for (PresetHouseWorkCategory category : categories) {
			List<PresetHouseWorkResponse.HouseWork> houseWorks = presetHouseWorkRepository.findByPresetHouseWorkCategoryId(
					category.getId())
				.stream()
				.map(it -> new PresetHouseWorkResponse.HouseWork(it.getId(), it.getName()))
				.toList();
			results.add(new PresetHouseWorkResponse(
				category.getId(), category.getName(), houseWorks
			));
		}
		return results;
	}
}
