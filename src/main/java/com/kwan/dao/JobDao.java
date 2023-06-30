package com.kwan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.bean.Job;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JobDao extends BaseMapper<Job> {
    /**
     * 实现模糊查询（职位/公司），并统计符合条件的记录
     *
     * 1. 匹配 `name` 参数或者 `company.name` 字段中包含 `name` 参数的记录。
     * 2. 如果 `max` 和 `min` 都为空，或者 `max` 为空而 `min` 有值，或者 `min` 为空而 `max` 有值，或者 `max` 和 `min` 都有值，才会对 `job.salary` 进行过滤。
     *    - 如果 `max` 为空而 `min` 有值，则筛选出 `job.salary` 大于等于 `min` 的记录。
     *    - 如果 `min` 为空而 `max` 有值，则筛选出 `job.salary` 小于等于 `max` 的记录。
     *    - 如果 `max` 和 `min` 都有值，则筛选出 `job.salary` 在 `min` 和 `max` 之间的记录。
     * 3. 如果 `province` 不为空，则筛选出 `job.province` 字段值等于 `province` 的记录。
     * 4. 如果 `city` 不为空，则筛选出 `job.city` 字段值等于 `city` 的记录。
     * 5. 如果 `area` 不为空，则筛选出 `job.area` 字段值等于 `area` 的记录。
     * 6. 返回从 `start` 开始的 `size` 条记录。
     *
     * @param name
     * @param start
     * @param size
     * @param max
     * @param min
     * @param province
     * @param city
     * @param area
     * @return
     */
    @Select("SELECT job.*,company.username AS receiver, company.name AS companyName, " +
            "(SELECT COUNT(*) FROM job, company WHERE job.company = company.regist " +
            "AND (job.name LIKE CONCAT('%', #{name}, '%') " +
            "OR company.name LIKE CONCAT('%', #{name}, '%'))) " +
            "AS count FROM job, company WHERE job.company = company.regist " +
            "AND (job.name LIKE CONCAT('%', #{name}, '%') " +
            "OR company.name LIKE CONCAT('%', #{name}, '%')) " +
            "AND (((#{max} IS NULL OR #{max} = '') AND (#{min} IS NULL OR #{min} = '')) " +
            "OR ((#{max} IS NULL OR #{max} = '') AND job.salary >= #{min}) " +
            "OR ((#{min} IS NULL OR #{min} = '') AND job.salary <= #{max}) " +
            "OR (job.salary BETWEEN #{min} AND #{max})) " +
            "AND (#{province} IS NULL OR job.province LIKE CONCAT('%', #{province}, '%')) " +
            "AND (#{city} IS NULL OR job.city LIKE CONCAT('%', #{city}, '%')) " +
            "AND (#{area} IS NULL OR job.area LIKE CONCAT('%', #{area}, '%')) " +
            "LIMIT #{start}, #{size}")
    List<Job> getAllJobs(String name, Integer start, Integer size, String max, String min, String province, String city, String area);


    //  获取已投简历信息（职位/公司模糊查询，并统计符合条件的记录）
    @Select("SELECT job.*, company.name AS companyName,deliver.time, deliver.id AS deliver, " +
            "(SELECT COUNT(*) " +
            "FROM job, company, deliver " +
            "WHERE deliver.job = job.id " +
            "AND job.company = company.regist " +
            "AND deliver.username = #{username} " +
            "AND (job.name LIKE CONCAT('%', #{name}, '%') " +
            "OR company.name LIKE CONCAT('%', #{name}, '%'))) AS count " +
            "FROM job, company, deliver " +
            "WHERE deliver.job = job.id " +
            "AND job.company = company.regist " +
            "AND deliver.username = #{username} " +
            "AND (job.name LIKE CONCAT('%', #{name}, '%') " +
            "OR company.name LIKE CONCAT('%', #{name}, '%')) " +
            "ORDER BY time " +
            "LIMIT #{start}, #{size}")
    List<Job> getDeliverJob(String username,String name,Integer start,Integer size);

    //  根据公司注册码查找职位（支持按照名称模糊查询）
    @Select("SELECT job.*, company.name AS companyName, " +
            "(SELECT COUNT(*) " +
            " FROM job " +
            " INNER JOIN company ON job.company = company.regist " +
            " WHERE company.regist = #{regist} " +
            "   AND job.name LIKE CONCAT('%', #{name}, '%')) AS count " +
            "FROM job " +
            "INNER JOIN company ON job.company = company.regist " +
            "WHERE company.regist = #{regist} " +
            "  AND job.name LIKE CONCAT('%', #{name}, '%') " +
            "LIMIT #{start}, #{size}")
    List<Job> getJobByRegist(String name, String regist, Integer start, Integer size);
}
