package com.example.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface PageHelperUtils {
    ThreadLocal<Page> THREAD_LOCAL_PAGE_HELPER_PAGE_SIZE = new ThreadLocal<>();

    static void skip() {
        Page page = PageHelper.getLocalPage();
        Page clonePage = new Page();
        BeanUtils.copyProperties(page, clonePage);
        // cache pageSize to thread local
        THREAD_LOCAL_PAGE_HELPER_PAGE_SIZE.set(clonePage);
//        clonePage.setPageSize(0);
        PageHelper.clearPage();
    }

    static void restore() {
        Page page = THREAD_LOCAL_PAGE_HELPER_PAGE_SIZE.get();
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
    }
}
