package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.entity.WorkSpaceUser;

import java.util.UUID;

public interface WorkSpaceUserRepository extends JpaRepository<WorkSpaceUser, UUID> {
}
