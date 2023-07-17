package uz.pdp.appclickup.sevice;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.entity.enums.AddType;
import uz.pdp.appclickup.entity.enums.WorkSpaceRoleName;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final AttachmentRepository attachmentRepository;
    private final WorkSpaceUserRepository workSpaceUserRepository;
    private final WorkSpaceRoleRepository workSpaceRoleRepository;
    private final WorkSpacePermissionRepository workSpacePermissionRepository;
    private final UserRepository userRepository;

    public WorkSpaceServiceImpl(WorkSpaceRepository workSpaceRepository,
                                AttachmentRepository attachmentRepository,
                                WorkSpaceUserRepository workSpaceUserRepository,
                                WorkSpaceRoleRepository workSpaceRoleRepository,
                                WorkSpacePermissionRepository workSpacePermissionRepository, UserRepository userRepository) {
        this.workSpaceRepository = workSpaceRepository;
        this.attachmentRepository = attachmentRepository;
        this.workSpaceUserRepository = workSpaceUserRepository;
        this.workSpaceRoleRepository = workSpaceRoleRepository;
        this.workSpacePermissionRepository = workSpacePermissionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user) {
//       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (workSpaceRepository.existsByOwnerIdAndName(user.getId(), workSpaceDto.getName()))
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        WorkSpace workSpace = new WorkSpace(
                workSpaceDto.getName(),
                workSpaceDto.getColor(), user,
                workSpaceDto.getAvatarId() == null ? null : attachmentRepository.findById(workSpaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workSpaceRepository.save(workSpace);
        //workspace role
        WorkSpaceRole ownerRole = workSpaceRoleRepository.save(new WorkSpaceRole(
                workSpace,
                WorkSpaceRoleName.ROLE_OWNER.name(),
                null
        ));

        WorkSpaceRole adminRole = workSpaceRoleRepository.save(new WorkSpaceRole(workSpace, WorkSpaceRoleName.ROLE_ADMIN.name(), null));
        WorkSpaceRole memberRole = workSpaceRoleRepository.save(new WorkSpaceRole(workSpace, WorkSpaceRoleName.ROLE_MEMBER.name(), null));
        WorkSpaceRole guestRole = workSpaceRoleRepository.save(new WorkSpaceRole(workSpace, WorkSpaceRoleName.ROLE_GUEST.name(), null));

        //ownerga huqular

        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkSpacePermission> workSpacePermissions = new ArrayList<>();
        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkSpacePermission workSpacePermission = new WorkSpacePermission(
                    ownerRole,
                    workspacePermissionName);
            workSpacePermissions.add(workSpacePermission);
            if (workspacePermissionName.getWorkSpaceRoleName().contains(WorkSpaceRoleName.ROLE_ADMIN)) {
                workSpacePermissions.add(new WorkSpacePermission(
                        adminRole,
                        workspacePermissionName));
            }

            if (workspacePermissionName.getWorkSpaceRoleName().contains(WorkSpaceRoleName.ROLE_MEMBER)) {
                workSpacePermissions.add(new WorkSpacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkSpaceRoleName().contains(WorkSpaceRoleName.ROLE_GUEST)) {
                workSpacePermissions.add(new WorkSpacePermission(
                        guestRole,
                        workspacePermissionName));
            }
        }
        workSpacePermissionRepository.saveAll(workSpacePermissions);


        //workspace user
        workSpaceUserRepository.save(new WorkSpaceUser(
                workSpace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));
        return new ApiResponse("Ishxona saqlandi", true);

    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDto memberDto) {
        if (memberDto.getAddType().equals(AddType.ADD)) {
            WorkSpaceUser workSpaceUser = new WorkSpaceUser(
                    workSpaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id1")),
                    userRepository.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("id2")),
                    workSpaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id3")),
                    new Timestamp(System.currentTimeMillis()), null);
            workSpaceUserRepository.save(workSpaceUser);

        } else if (memberDto.getAddType().equals(AddType.EDIT)) {
            WorkSpaceUser workSpaceUser = workSpaceUserRepository.findByWorkSpaceIdAndUserId(id, memberDto.getId()).orElseGet(WorkSpaceUser::new);
            workSpaceUser.setWorkSpaceRole(workSpaceRoleRepository.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("id1")));
            workSpaceUserRepository.save(workSpaceUser);

        } else if (memberDto.getAddType().equals(AddType.REMOVE)) {
            workSpaceUserRepository.deleteByWorkSpaceIdAndUserId(id, memberDto.getId());
        }
        return new ApiResponse("Muvaffaqiyatli", true);
    }

    @Override
    public ApiResponse editWorkSpace(WorkSpaceDto workSpaceDto) {
        return null;
    }

    @Override
    public ApiResponse changeOwnerWorkSpace(Long id, UUID ownerId) {
        return null;
    }

    @Override
    public ApiResponse deleteWorkSpace(Long id) {
        try {
            workSpaceRepository.deleteById(id);
            return new ApiResponse("Uchirildi", true);
        } catch (Exception e) {
            return new ApiResponse("xatolik", false);
        }
    }

    @Override
    public ApiResponse joinToWorkSpace(Long id, User user) {
        Optional<WorkSpaceUser> optionalWorkSpaceUser = workSpaceUserRepository.findByWorkSpaceIdAndUserId(id, user.getId());
        if (optionalWorkSpaceUser.isPresent()) {
            WorkSpaceUser workSpaceUser = optionalWorkSpaceUser.get();
            workSpaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workSpaceUserRepository.save(workSpaceUser);
            return new ApiResponse("success", true);
        }
        return new ApiResponse("error", false);

    }
}
