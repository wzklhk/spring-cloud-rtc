package com.example.service.common.api.controller;

import com.example.service.common.api.PageQuery;
import com.example.service.common.api.ResultInfo;
import com.example.service.common.api.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 通用 Controller
 *
 * @param <V>  实体类Vo
 * @param <E>  实体类
 * @param <ID> id主键类型
 */
public class CommonController<V, E, ID> {

    @Autowired
    private CommonService<V, E, ID> commonService;

    @GetMapping("/get")
    public ResultInfo getById(ID id) {
        return ResultInfo.ok(commonService.getById(id));
    }

    @GetMapping("/list")
    public ResultInfo list(V entityVo) {
        return ResultInfo.ok(commonService.list(entityVo));
    }

    @GetMapping("/page")
    public ResultInfo listByPage(PageQuery query) {
        return ResultInfo.ok(commonService.listByPage(query));
    }

    @PostMapping("/save")
    public ResultInfo save(@RequestBody V entityVo) {
        return ResultInfo.ok(commonService.saveOrUpdate(entityVo));
    }

    @PutMapping("/update")
    public ResultInfo update(@RequestBody V entityVo) {
        return ResultInfo.ok(commonService.saveOrUpdate(entityVo));
    }

    @DeleteMapping("/delete")
    public ResultInfo delete(ID id) {
        return ResultInfo.status(commonService.deleteById(id), null);
    }


}
