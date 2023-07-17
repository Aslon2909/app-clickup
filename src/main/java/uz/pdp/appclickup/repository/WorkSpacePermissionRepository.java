package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.WorkSpacePermission;

import java.util.UUID;

public interface WorkSpacePermissionRepository extends JpaRepository<WorkSpacePermission, UUID> {
}
