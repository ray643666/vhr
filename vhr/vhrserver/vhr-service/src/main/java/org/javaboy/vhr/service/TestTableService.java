package org.javaboy.vhr.service;

import com.alibaba.druid.util.StringUtils;
import org.javaboy.vhr.mapper.TestTableMapper;
import org.javaboy.vhr.model.TestTable;
import org.javaboy.vhr.model.TestTableExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestTableService {

    @Autowired
    TestTableMapper testTableMapper;

    /**
     * 查询测试表列表信息
     * @param testTable     入参
     * @return              返回列表
     */
    public List<TestTable> queryTestTable(TestTable testTable) {

        // Example查询器
        TestTableExample example = new TestTableExample();
        TestTableExample.Criteria criteria = example.createCriteria();
        // 测试姓名不为空
        if (!StringUtils.isEmpty(testTable.getTestName())) {
            criteria.andTestNameLike("%" + testTable.getTestName() + "%");
        }
        // 测试年龄不为空
        if (testTable.getTestAge() != null && testTable.getTestAge() != 0) {
            criteria.andTestAgeEqualTo(testTable.getTestAge());
        }
        // 测试身份证号不为空
        if (!StringUtils.isEmpty(testTable.getTestIdCard())) {
            criteria.andTestIdCardLike("%" + testTable.getTestIdCard() + "%");
        }
        return testTableMapper.selectByExample(example);
    }

    /**
     * 添加测试表数据
     * @param testTable
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addTestTable(TestTable testTable) {
        return testTableMapper.insert(testTable);
    }

    /**
     * 删除测试表数据
     * @param id       自增长主键
     * @return         删除条数
     */
    @Transactional
    public int deleteTestTable(int id) {
        return testTableMapper.deleteByPrimaryKey(id);
    }
}
