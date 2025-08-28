package team1.allo.group.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.allo.auth.annotation.Auth;
import team1.allo.group.entity.Group;
import team1.allo.group.entity.GroupMember;
import team1.allo.group.entity.Place;
import team1.allo.group.entity.Tag;
import team1.allo.group.repository.GroupMemberRepository;
import team1.allo.group.repository.GroupRepository;
import team1.allo.group.repository.PlaceRepository;
import team1.allo.group.repository.tag.TagRepository;
import team1.allo.group.service.dto.EnterRequest;
import team1.allo.group.service.dto.EnterResponse;
import team1.allo.group.service.dto.GroupRequest;
import team1.allo.group.service.dto.GroupResponse;
import team1.allo.group.service.dto.MemberResponse;
import team1.allo.group.service.dto.MyGroupResponse;
import team1.allo.group.service.dto.PlaceResponse;
import team1.allo.group.service.dto.PlaceSaveRequest;
import team1.allo.group.service.dto.TagResponse;
import team1.allo.group.service.dto.TagSaveRequest;
import team1.allo.group.service.generator.InviteCodeGenerator;
import team1.allo.member.entity.Member;
import team1.allo.member.service.MemberService;
import team1.allo.preset.service.PresetService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository groupRepository;
	private final PlaceRepository placeRepository;
	private final TagRepository tagRepository;
	private final GroupMemberRepository groupMemberRepository;

	private final InviteCodeGenerator inviteCodeGenerator;

	private final PresetService presetService;
	private final MemberService memberService;

	@Transactional
	public GroupResponse save(@Auth Member member, GroupRequest groupRequest) {
		//초대코드 생성
		String inviteCode = inviteCodeGenerator.generateInviteCode();
		//그룹생성
		Group group = new Group(groupRequest.backGroundTypeNum(), inviteCode);
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

		return new GroupResponse(group.getId(), group.getInviteCode(), group.getBackGroundType().toString());
	}

	@Transactional
	public EnterResponse enter(Member member, EnterRequest enterRequest) {
		Group group = groupRepository.findByInviteCode(enterRequest.inviteCode())
			.orElseThrow(() -> new NoSuchElementException("Invite code does not exist"));

		Long groupId = group.getId();
		if (groupMemberRepository.existsByGroupIdAndMemberId(groupId, member.getId())) {
			throw new RuntimeException("Already a member of this group");
		}

		//멤버추가
		GroupMember groupMember = new GroupMember(group, member.getId());
		groupMemberRepository.save(groupMember);
		return new EnterResponse(groupId);
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
				Member member = memberService.findById(it.getMemberId());
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

	public MyGroupResponse getMyGroup(Member member) {
		GroupMember groupMember = groupMemberRepository.findFirstByMemberId(member.getId())
			.orElseThrow(() -> new NoSuchElementException("Group Member does not exist"));
		return new MyGroupResponse(groupMember.getGroup().getId(), groupMember.getGroup().getInviteCode());
	}

	public Group findGroupById(Long groupId) {
		return groupRepository.findById(groupId)
			.orElseThrow(() -> new NoSuchElementException("Group does not exist"));
	}

	public Place findPlaceById(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new NoSuchElementException("Place does not exist"));
	}

	public Tag findTagById(Long tagId) {
		return tagRepository.findById(tagId)
			.orElseThrow(() -> new NoSuchElementException("Tag does not exist"));
	}

	public List<Tag> findAllByTagId(List<Long> tagIds) {
		return tagRepository.findAllById(tagIds);
	}

	@Transactional
	public TagResponse saveTag(Long groupId, TagSaveRequest request) {
		Group group = groupRepository.findById(groupId)
			.orElseThrow(() -> new NoSuchElementException("group not found"));
		Tag tag = new Tag(request.tagName(), group);
		Tag saved = tagRepository.save(tag);
		return new TagResponse(saved.getId(), saved.getName());
	}

	@Transactional
	public void savePlace(Long groupId, PlaceSaveRequest request) {
		Group group = groupRepository.findById(groupId)
			.orElseThrow(() -> new NoSuchElementException("group not found"));
		Place place = new Place(request.name(), group);
		placeRepository.save(place);
	}
}
