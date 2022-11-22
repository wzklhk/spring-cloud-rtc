package com.example.common.api.controller;

import com.example.common.api.PageQuery;
import com.example.common.api.ResultInfo;
import com.example.common.api.service.CommonService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping
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

    @PostMapping
    public ResultInfo save(@RequestBody V entityVo) {
        return ResultInfo.ok(commonService.saveOrUpdate(entityVo));
    }

    @PutMapping
    public ResultInfo update(@RequestBody V entityVo) {
        return ResultInfo.ok(commonService.saveOrUpdate(entityVo));
    }

    @DeleteMapping
    public ResultInfo delete(ID id) {
        return ResultInfo.status(commonService.deleteById(id), null);
    }


}
