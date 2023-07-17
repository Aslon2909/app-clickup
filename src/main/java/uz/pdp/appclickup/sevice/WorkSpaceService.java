package uz.pdp.appclickup.sevice;

import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.MemberDto;
import uz.pdp.appclickup.payload.WorkSpaceDto;

import java.util.UUID;


public interface WorkSpaceService {

    ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user);

    ApiResponse editWorkSpace(WorkSpaceDto workSpaceDto);

    ApiResponse changeOwnerWorkSpace(Long id, UUID ownerId);

    ApiResponse deleteWorkSpace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDto memberDto);

    ApiResponse joinToWorkSpace(Long id, User user);
}
