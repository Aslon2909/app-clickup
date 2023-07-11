package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.WorkSpace;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.WorkSpaceDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.sevice.WorkSpaceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkSpaceController  {
    @Autowired
    WorkSpaceService workSpaceService;
    @PostMapping
    public HttpEntity<?>addWorkSpace(@RequestBody WorkSpaceDto workSpaceDto, @CurrentUser User user){
        ApiResponse apiResponse = workSpaceService.addWorkSpace(workSpaceDto , user);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    /**
     * NAME,COLOR,AVATAR uzgarishi mumkin
     * @param id
     * @param workSpaceDto
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?>editWorkSpace(@PathVariable Long id, @RequestBody WorkSpaceDto workSpaceDto){
        ApiResponse apiResponse = workSpaceService.editWorkSpace(workSpaceDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?>editWorkSpace(@PathVariable Long id,
                                      @RequestParam UUID ownerId){
        ApiResponse apiResponse = workSpaceService.changeOwnerWorkSpace(id,ownerId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    /**
     * Uchirish ishxonano
     * @param id
     * @param ownerId
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteWorkSpace(@PathVariable Long id){
        ApiResponse apiResponse = workSpaceService.deleteWorkSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
