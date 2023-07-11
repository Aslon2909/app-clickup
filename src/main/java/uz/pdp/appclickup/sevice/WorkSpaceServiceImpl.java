package uz.pdp.appclickup.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceRole;
import uz.pdp.appclickup.entity.WorkSpaceUser;
import uz.pdp.appclickup.entity.enums.WorkSpaceRoleName;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.repository.AttachmentRepository;
import uz.pdp.appclickup.repository.WorkSpaceRepository;
import uz.pdp.appclickup.repository.WorkSpaceRoleRepository;
import uz.pdp.appclickup.repository.WorkSpaceUserRepository;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Autowired
    WorkSpaceRepository workSpaceRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkSpaceUserRepository workSpaceUserRepository;
    @Autowired
    WorkSpaceRoleRepository workSpaceRoleRepository;

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
        workSpaceRoleRepository.save(new WorkSpaceRole(
                workSpace,
                WorkSpaceRoleName.ROLE_OWNER.name(),
                null
        ));


        //workspace user
        workSpaceUserRepository.save(new WorkSpaceUser(
                workSpace,
                user,
                null,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));
        return new ApiResponse("Ishxona saqlandi", true);

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
}
