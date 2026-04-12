package org.javaboy.vhr.controller.test;

import org.javaboy.vhr.annotation.Log;
import org.javaboy.vhr.enums.ResultCode;
import org.javaboy.vhr.model.Result;
import org.javaboy.vhr.model.TestTable;
import org.javaboy.vhr.service.TestTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @作者 ray643666
 * @公众号 ray
 * @GitHub https://github.com/ray643666
 * @时间 2026-04-11
 */
@RestController
@RequestMapping("/test/table")
public class TestTableController {
    @Autowired
    TestTableService testTableService;

    /**
     * 查询测试表列表
     * @param testTable     查询入参
     * @return              统一返回对象
     */
    @Log("查询测试表列表")
    @PostMapping("/queryTestTable")
    public Result<List<TestTable>> queryTestTable(@RequestBody TestTable testTable) {
        // 查询测试表列表
        List<TestTable> testTableList = testTableService.queryTestTable(testTable);
        if (testTableList != null && !testTableList.isEmpty()) {
            return Result.success(testTableList);
        }
        return Result.success(Collections.emptyList());
    }

    /**
     * 添加测试表数据
     * @param testTable     查询入参
     * @return              统一返回对象
     */
    @Log("添加测试表数据")
    @PostMapping("/addTestTable")
    public Result<Void> addTestTable(@RequestBody TestTable testTable) {
        // 查询测试表列表
        int result = testTableService.addTestTable(testTable);
        if (result != 0) {
            return Result.success("添加成功");
        }
        return Result.fail(ResultCode.BAD_REQUEST, "添加失败");
    }

    /**
     * 删除测试表数据
     * @param id            删除ID
     * @return              统一返回对象
     */
    @Log("删除测试表数据")
    @GetMapping("/deleteTestTable")
    public Result<Void> deleteTestTable(@RequestParam String id) {
        // 查询测试表列表
        int result = testTableService.deleteTestTable(id);
        if (result != 0) {
            return Result.success("删除成功");
        }
        return Result.fail(ResultCode.BAD_REQUEST, "删除失败");
    }
}
