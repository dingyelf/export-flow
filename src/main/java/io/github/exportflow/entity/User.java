package io.github.exportflow.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String sex;
    private Integer age;
    private String email;
    private String address;
    private String phone;

}
