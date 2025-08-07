package team1.housework.group.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.character.service.CharacterService;
import team1.housework.group.entity.Group;
import team1.housework.group.entity.GroupMember;
import team1.housework.group.entity.Place;
import team1.housework.group.entity.Tag;
import team1.housework.group.repository.GroupMemberRepository;
import team1.housework.group.repository.GroupRepository;
import team1.housework.group.repository.PlaceRepository;
import team1.housework.group.repository.TagRepository;
import team1.housework.group.service.dto.EnterRequest;
import team1.housework.group.service.dto.EnterResponse;
import team1.housework.group.service.dto.GroupRequest;
import team1.housework.group.service.dto.GroupResponse;
import team1.housework.group.service.dto.MemberResponse;
import team1.housework.group.service.dto.PlaceResponse;
import team1.housework.group.service.dto.TagResponse;
import team1.housework.group.service.generator.InviteCodeGenerator;
import team1.housework.member.entity.Member;
import team1.housework.member.service.MemberService;
import team1.housework.preset.service.PresetService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository groupRepository;
	private final PlaceRepository placeRepository;
	private final TagRepository tagRepository;
	private final GroupMemberRepository groupMemberRepository;

	private final InviteCodeGenerator inviteCodeGenerator;

	private final CharacterService characterService;
	private final PresetService presetService;
	private final MemberService memberService;

	@Transactional
	public GroupResponse save(Member member, GroupRequest groupRequest) {
		Boolean existsByCharacterId = characterService.existsById(groupRequest.characterId());
		if (!existsByCharacterId) {
			throw new NoSuchElementException("Character does not exist");
		}
		//초대코드 생성
		String inviteCode = inviteCodeGenerator.generateInviteCode();
		//그룹생성
		Group group = new Group(groupRequest.characterId(), inviteCode);
		groupRepository.save(group);
		//요청자 그룹 멤버에 추가
		GroupMember groupMember = new GroupMember(group, member.getId());
		groupMemberRepository.save(groupMember);
		//장소, 태그 프리셋 복사해 생성
		List<Place> places = presetService.getAllPlaces()
			.stream()
			.map(it -> new Place(it.getName(), group))
			.toList();
		placeRepository.saveAll(places);

		List<Tag> tags = presetService.getAllTags()
			.stream()
			.map(it -> new Tag(it.getName(), group))
			.toList();
		tagRepository.saveAll(tags);

		return new GroupResponse(group.getId(), group.getInviteCode());
	}

	@Transactional
	public EnterResponse enter(Member member, EnterRequest enterRequest) {
		Group group = groupRepository.findByInviteCode(enterRequest.inviteCode())
			.orElseThrow(() -> new NoSuchElementException("Invite code does not exist"));
		//멤버추가
		GroupMember groupMember = new GroupMember(group, member.getId());
		groupMemberRepository.save(groupMember);
		return new EnterResponse(group.getId());
	}

	public List<PlaceResponse> getPlaces(Long groupId) {
		validateGroupExists(groupId);
		return placeRepository.findByGroupId(groupId)
			.stream()
			.map(it -> new PlaceResponse(it.getId(), it.getName()))
			.toList();
	}

	public List<TagResponse> getTags(Long groupId) {
		validateGroupExists(groupId);
		return tagRepository.findByGroupId(groupId)
			.stream()
			.map(it -> new TagResponse(it.getId(), it.getName()))
			.toList();
	}

	public List<MemberResponse> getMembers(Long groupId) {
		validateGroupExists(groupId);
		return groupMemberRepository.findByGroupId(groupId)
			.stream()
			.map(it -> {
				Member member = memberService.findById(it.getId());
				return new MemberResponse(
					member.getId(),
					member.getName() == null ? null : member.getName(),
					member.getProfileImageUrl() == null ? null : member.getProfileImageUrl()
				);
			})
			.toList();

	}

	private void validateGroupExists(Long groupId) {
		boolean exists = groupRepository.existsById(groupId);
		if (!exists) {
			throw new NoSuchElementException("Group does not exist");
		}
	}

}
