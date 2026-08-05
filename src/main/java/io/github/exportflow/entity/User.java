package io.github.exportflow.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String gender;
    private Integer age;
    private String email;
    private String address;
    private String phone;

}
