package com.kwan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kwan.bean.Company;
import com.kwan.common.Result;
import com.kwan.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/getCompany")
    public Result getCompany(String name, String username, Integer start, Integer size) {
        IPage page = companyService.getAllCompanies(name, username, start, size);
        if (page.getTotal() == 0) {
            return Result.error("查询不到数据");
        }
        return Result.scuess(page);
    }

    @PostMapping("/registCompany")
    public Result registCompany(@RequestBody Company company) {
        try {
            companyService.registCompany(company);
            return Result.scuess("公司注册成功");
        } catch (Exception e) {
            if (e.getClass().toString().equals("class org.springframework.dao.DuplicateKeyException")) {
                return Result.error("该公司注册码已被注册");
            } else if (e.getClass().toString().equals("class org.springframework.dao.DataIntegrityViolationException")) {
                return Result.error("注册用户不存在");
            } else {
                return Result.error("注册失败，请尝试填入其他信息");
            }
        }
    }

    @PostMapping("/updateCompany")
    public Result updateCompany(@RequestBody Company company) {
        try {
            companyService.updateCompany(company);
            return Result.scuess("公司信息更新成功");
        } catch (Exception e) {
            if (e.getClass().toString().equals("class org.springframework.dao.DuplicateKeyException")) {
                return Result.error("该公司注册码已被注册");
            } else if (e.getClass().toString().equals("class org.springframework.dao.DataIntegrityViolationException")) {
                return Result.error("用户不存在");
            } else {
                return Result.error("公司信息更新失败，请尝试填入其他信息");
            }
        }
    }

    @PostMapping("/deleteCompany")
    public Result deleteCompany(String regist) {
        return companyService.deleteCompany(regist) ? Result.scuess("公司注销成功") : Result.error("公司注销失败");
    }

    @PostMapping("/getCompanyByRegist")
    public Result getCompanyByRegist(String regist) {
        Company company = companyService.getCompanyByRegist(regist);
        if (company == null) {
            return Result.error("查询不到数据");
        }
        return Result.scuess(company);
    }


}
