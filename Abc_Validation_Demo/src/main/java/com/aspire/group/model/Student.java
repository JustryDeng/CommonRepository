package com.aspire.group.model;

import com.aspire.group.anno.Create;
import com.aspire.group.anno.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;

/**
 * (non-javadoc)
 *
 * @author JustryDeng
 * @date 2020/4/22 22:22:32
 */
@Data
public class Student {

    @NotEmpty(groups = Create.class)
    private String name;

    @NotEmpty(groups = Update.class)
    private String motto;

    @NotEmpty(groups = {Create.class, Update.class})
    private String hobby ;

    @NotEmpty(groups = Default.class)
    private String idNo;

    @NotEmpty
    private String address;
}
