package com.example.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * <b>WARNING</b> if offset has value, offset = offset + (page -1) * size
 * <pre>
 *     offset = 5, page = 2, size 10
 *     offset = 5 + (2 -1) * 10 = 15
 *     return 16~25
 * </pre>
 * @author Kenny Fang
 * @since 0.0.2
 */
@Data
public class Pager implements Serializable {

    private static final long serialVersionUID = -3999746958123583061L;

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;
    public static final int UNPAGED_SIZE = 0;

    public static final Pager UNPAGED = new Pager(UNPAGED_SIZE);
    public static final Pager DEFAULT = new Pager(Pager.DEFAULT_PAGE, Pager.DEFAULT_SIZE);

    @ApiModelProperty("偏移")
    private Integer offset;
    @ApiModelProperty("第几页")
    private Integer page;
    @ApiModelProperty("单页数量")
    private Integer size;
    @ApiModelProperty("排序")
    private String order;

    public Pager() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE;
        this.setSize(size);
    }

    public Pager(Integer size) {
        this.page = DEFAULT_PAGE;
        this.size = size;
        this.setSize(size);
    }

    public Pager(Integer page, Integer size) {
        this.page = page;
        this.size = size;
        this.setSize(size);
    }

    public Pager(Integer offset, Integer page, Integer size) {
        this.offset = offset;
        this.page = page;
        this.size = size;
        this.setSize(size);
    }

    public void setSize(Integer size) {
        if (Objects.isNull(size)) {
            size = 0;
        }

        this.size = size;
        if (Objects.isNull(this.page)) {
            this.page = DEFAULT_PAGE;
        }

        int offset = (this.page - 1) * size;
        if (Objects.isNull(this.offset)) {
            this.offset = offset;
        } else {
            this.offset += offset;
        }
    }

    public static Pager UNPAGED() {
        return UNPAGED;
    }
}
