package com.kwan.controller;

import com.kwan.bean.Job;
import com.kwan.common.Result;
import com.kwan.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/getJobs")
    public Result getJobs(String name, Integer start, Integer size, String max, String min, String province, String city, String area) {
        int s = (start - 1) * size;
        List<Job> jobs = jobService.getJobs(name, s, size, max, min, province, city, area);
        if (jobs.isEmpty()) {
            return Result.error("查询不到数据");
        }
        return Result.scuess(jobs);
    }

    @PostMapping("/getDeliver")
    public Result getDeliver(String username, String name, Integer start, Integer size) {
        int s = (start - 1) * size;
        List<Job> jobs = jobService.getDeliverJob(username, name, s, size);
        if (jobs.isEmpty()) {
            return Result.error("查询不到数据");
        }
        return Result.scuess(jobs);
    }

    @PostMapping("/getJobByRegist")
    public Result getJobByRegist(String name, String regist, Integer start, Integer size) {
        int s = (start - 1) * size;
        List<Job> jobs = jobService.getJobByRegist(name, regist, s, size);
        if (jobs.isEmpty()) {
            return Result.error("查询不到数据");
        }
        return Result.scuess(jobs);
    }

    @PostMapping("/deleteJob")
    public Result deleteJob(int id) {
        return jobService.deleteJob(id) ? Result.scuess("职位删除成功") : Result.error("职位删除失败");
    }

    @PostMapping("/getJobById")
    public Result getJobById(int id) {
        Job job = jobService.getJobById(id);
        return job == null ? Result.error("找不到对应的职位信息") : Result.scuess(job);
    }

    @PostMapping("/updateJobById")
    public Result updateJobById(@RequestBody Job job) {
        return jobService.updateJobById(job) ? Result.scuess("职位信息更新成功") : Result.error("职位信息更新失败");
    }

    @PostMapping("/insertJob")
    public Result insertJob(@RequestBody Job job) {
        System.out.println(job);
        return jobService.insertJob(job) ? Result.scuess("职位发布成功") : Result.error("职位发布失败");
    }
}
