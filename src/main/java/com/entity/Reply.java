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
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

	private String rid;

	private String cid;

	private String commid;

	private String cuserid;

    private String cusername;

	private String spuserid;

	private String recontent;

	private String ruserid;

    private String rusername;

    private String ruimage;

	private Date replytime;

	private Integer repstatus;


}
