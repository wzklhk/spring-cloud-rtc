package com.example.common.api.controller;

import com.example.common.api.ResultInfo;
import com.example.common.api.service.CommonService;
import com.example.common.pojo.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通用 Controller
 *
 * @param <VO> 实体类VO
 * @param <DO> 实体类DO，DO中不要使用基本数据类型，否则会导致Example中传入默认值
 * @param <ID> id主键类型
 * @author wzklhk
 */
public class CommonController<VO, DO, ID> {

    @Autowired
    private CommonService<VO, DO, ID> commonService;

    /**
     * 查询主键id相同的VO
     *
     * @param id 要匹配的主键id
     * @return 通用返回VO对象
     */
    @GetMapping("/id")
    public ResultInfo<VO> getById(@RequestParam ID id) {
        return ResultInfo.ok(commonService.getById(id));
    }

    /**
     * 查询匹配的VO
     *
     * @param queryMap 要匹配的query属性
     * @return 通用返回：VO列表
     */
    @GetMapping("/all")
    public ResultInfo<List<VO>> getAll(Map<String, Object> queryMap) {
        return ResultInfo.ok(commonService.getAll(queryMap));
    }

    /**
     * 查询匹配的VO，按页返回
     * 默认GET方法接口
     *
     * @param queryMap 要匹配的页面属性
     * @return 通用返回：页对象包含VO列表
     */
    @GetMapping
    public ResultInfo<CommonPage<VO>> getByPage(@RequestParam Map<String, Object> queryMap) {
        return ResultInfo.ok(commonService.getByPage(queryMap));
    }

    /**
     * 新增对象
     * POST方法接口进行新增对象
     *
     * @param queryMap 新增VO对象
     * @return 通用返回：新增的VO对象
     */
    @PostMapping
    public ResultInfo<VO> save(@RequestBody Map<String, Object> queryMap) {
        return ResultInfo.ok(commonService.saveOrUpdate(queryMap));
    }

    /**
     * 修改对象
     * PUT方法接口进行修改对象
     *
     * @param queryMap 修改VO对象
     * @return 通用返回：修改的VO对象
     */
    @PutMapping
    public ResultInfo<VO> update(@RequestBody Map<String, Object> queryMap) {
        return ResultInfo.ok(commonService.saveOrUpdate(queryMap));
    }

    /**
     * 删除对象
     *
     * @param id 要删除对象的id
     * @return 通用返回：status
     */
    @DeleteMapping
    public ResultInfo<Object> deleteById(@RequestParam ID id) {
        return ResultInfo.status(commonService.deleteById(id), null);
    }

    @DeleteMapping("/batch")
    public ResultInfo<Object> deleteAllByIdInBatch(@RequestParam List<ID> ids) {
        return ResultInfo.status(commonService.deleteAllByIdInBatch(ids), null);
    }
}
