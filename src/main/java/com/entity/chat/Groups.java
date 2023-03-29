package com.entity.chat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Groups {
    private String id;
    private String groupname;
    private String avatar;
    private String userid;
    private Date intime;
    private String grpowner;
}
