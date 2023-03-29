package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String commid;

    private String commname;

    private String commdesc;

    private Date soldtime;

    private Integer collstatus;

    private String cmuserid;

    private String username;

    private String school;

    private String couserid;

    private Integer colloperate;
}
