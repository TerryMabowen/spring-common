package com.mbw.commons.dal.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-05-21 15:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -265071337073082065L;

    private Integer status = 1;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private String deletedBy;

    private Date deletedTime;
}
