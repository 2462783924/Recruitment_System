package com.kwan.service.Impl;

import com.kwan.bean.Deliver;
import com.kwan.dao.DeliverDao;
import com.kwan.service.DeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DeliverServiceImpl implements DeliverService {
    @Autowired
    private DeliverDao deliverDao;

    @Override
    public boolean saveDeliver(Deliver deliver) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        deliver.setTime(currentTimestamp);
        return deliverDao.insert(deliver) == 1;
    }

    @Override
    public boolean deleteDeliver(int id) {
        return deliverDao.deleteById(id) == 1;
    }


}
