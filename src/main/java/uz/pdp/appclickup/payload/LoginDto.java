package uz.pdp.appclickup.payload;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
