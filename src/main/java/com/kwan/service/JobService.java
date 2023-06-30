package com.kwan.service;

import com.kwan.bean.Job;

import java.util.List;

public interface JobService {
    //  查询全部职位
    List<Job> getJobs(String name, Integer start, Integer size, String max, String min, String province, String city, String area);
    //  查询已投简历信息
    List<Job> getDeliverJob(String username, String name, Integer start, Integer size);
    //  根据公司注册号查找职位（职位名模糊查询）
    List<Job> getJobByRegist(String name, String regist, Integer start, Integer size);
    //  删除职位
    boolean deleteJob(int id);
    //  根据id查找职位
    Job getJobById(int id);
    //  更新职位By Id
    boolean updateJobById(Job job);
    //  新增职位
    boolean insertJob(Job job);
}
