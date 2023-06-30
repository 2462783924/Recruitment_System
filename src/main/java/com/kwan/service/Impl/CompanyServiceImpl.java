package com.kwan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.bean.Company;
import com.kwan.dao.CompanyDao;
import com.kwan.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public IPage getAllCompanies(String name, String username, Integer start, Integer size) {
        IPage page = new Page(start, size);
        QueryWrapper<Company> qw = new QueryWrapper();
        qw.like("name", name).eq("username", username);
        return companyDao.selectPage(page, qw);
    }

    @Override
    public boolean registCompany(Company company) {
        return companyDao.insert(company) == 1 ? true : false;
    }

    @Override
    public boolean updateCompany(Company company) {
        LambdaQueryWrapper<Company> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Company::getRegist, company.getRegist());
        return companyDao.update(company, lqw) == 1;
    }

    @Override
    public boolean deleteCompany(String regist) {
        LambdaQueryWrapper<Company> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Company::getRegist, regist);
        return companyDao.delete(lqw) == 1;
    }

    @Override
    public Company getCompanyByRegist(String regist) {
        LambdaQueryWrapper<Company> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Company::getRegist, regist);
        return companyDao.selectOne(lqw);
    }
}
