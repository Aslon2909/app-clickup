package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.appclickup.entity.WorkSpaceUser;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface WorkSpaceUserRepository extends JpaRepository<WorkSpaceUser, UUID> {

    Optional <WorkSpaceUser> findByWorkSpaceIdAndUserId(Long workSpace_id, UUID user_id);

    @Transactional
    @Modifying
    void deleteByWorkSpaceIdAndUserId(Long workSpace_id, UUID user_id);
}
