package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserPerm implements Serializable {

    private static final long serialVersionUID = 1L;

	private Integer roleid;

	private String perms;

	private String mean;


}
