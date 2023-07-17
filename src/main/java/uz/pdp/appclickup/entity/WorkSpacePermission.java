package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkSpacePermission extends   AbsUUIDEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpaceRole workSpaceRole;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;//add member, remove
}
