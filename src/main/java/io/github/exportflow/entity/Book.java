package io.github.exportflow.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    private String id;
    private String bookName;
    private String price;
    private String author;
    private Date publishTime;
    private Integer status;

}
