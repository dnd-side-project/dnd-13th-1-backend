package team1.allo.preset.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import team1.allo.preset.service.PresetService;
import team1.allo.preset.service.dto.PresetHouseWorkResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/preset")
public class PresetController {

	private final PresetService presetService;

	@Operation(summary = "프리셋 집안일 목록 조회")
	@GetMapping("/house-work")
	public List<PresetHouseWorkResponse> getPresetHouseWorks() {
		return presetService.getPresetHouseWorks();
	}
}
