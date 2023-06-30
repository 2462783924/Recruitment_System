package com.kwan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kwan.bean.Company;

public interface CompanyService {
    //  查询全部公司（公司名模糊搜索，分页）
    IPage getAllCompanies(String name, String username, Integer start, Integer size);
    //  注册公司（插入）
    boolean registCompany(Company company);
    //  更新公司信息
    boolean updateCompany(Company company);
    //  注销公司
    boolean deleteCompany(String regist);
    //  根据注册码查找公司
    Company getCompanyByRegist(String regist);
}
