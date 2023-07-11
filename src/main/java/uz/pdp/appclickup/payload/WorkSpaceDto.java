package uz.pdp.appclickup.payload;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkSpaceDto {
    @NotNull
    private String name;
    @NotNull
    private String color;

    private UUID avatarId;


}
