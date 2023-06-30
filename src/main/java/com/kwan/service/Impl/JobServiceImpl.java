package com.kwan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kwan.bean.Job;
import com.kwan.dao.JobDao;
import com.kwan.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;

    @Override
    public List<Job> getJobs(String name, Integer start, Integer size, String max, String min, String province, String city, String area) {
        LambdaQueryWrapper<Job> lqw = new LambdaQueryWrapper<>();
        lqw.like(Job::getName, name);
        return jobDao.getAllJobs(name, start, size, max, min, province, city, area);
    }

    @Override
    public List<Job> getDeliverJob(String username, String name, Integer start, Integer size) {
        List<Job> delivers = jobDao.getDeliverJob(username, name, start, size);
        return delivers;
    }

    @Override
    public List<Job> getJobByRegist(String name, String regist, Integer start, Integer size) {
        return jobDao.getJobByRegist(name, regist, start, size);
    }

    @Override
    public boolean deleteJob(int id) {
        return jobDao.deleteById(id) == 1;
    }

    @Override
    public Job getJobById(int id) {
        return jobDao.selectById(id);
    }

    @Override
    public boolean updateJobById(Job job) {
        return jobDao.updateById(job) == 1;
    }

    @Override
    public boolean insertJob(Job job) {
        return jobDao.insert(job) == 1;
    }
}
