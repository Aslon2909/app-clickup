package uz.pdp.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum WorkspacePermissionName {
    CAN_ADD_MEMBER("CAN_ADD_MEMBER",
            "Gives the user the permission to add members to the Workspace",
            Arrays.asList(WorkSpaceRoleName.ROLE_OWNER, WorkSpaceRoleName.ROLE_ADMIN)),
    CAN_REMOVE_MEMBER("CAN_REMOVE_MEMBER",
            "Gives the user the permission to remove members To the worksoace",
            Arrays.asList(WorkSpaceRoleName.ROLE_OWNER, WorkSpaceRoleName.ROLE_ADMIN)),

    CAN_EDIT_WORKSPACE("CAN_EDIT_WORKSPACE",
            "Gives the usep the permission to remove members to the Workspace",
            Arrays.asList(WorkSpaceRoleName.ROLE_OWNER, WorkSpaceRoleName.ROLE_ADMIN)),
    CAN_ADD_GUEST("CAN_ADD_GUEST", "Gives the user the permission to remove members to the Workspace",
            Arrays.asList(WorkSpaceRoleName.ROLE_OWNER, WorkSpaceRoleName.ROLE_ADMIN));


    public String name;
    public String description;

    public List<WorkSpaceRoleName> workSpaceRoleName;
    WorkspacePermissionName(String name, String description, List<WorkSpaceRoleName> workSpaceRoleNames) {
        this.name = name;
        this.description = description;
        this.workSpaceRoleName = workSpaceRoleNames;
    }
}
