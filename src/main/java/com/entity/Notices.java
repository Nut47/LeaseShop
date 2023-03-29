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
public class Notices implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;

	private String userid;

	private String whys;

	private Integer isread;

	private String tpname;

	private Date nttime;

    private Integer latest;

}
