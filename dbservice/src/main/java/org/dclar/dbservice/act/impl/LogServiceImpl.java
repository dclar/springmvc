package org.dclar.dbservice.act.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.dclar.dbservice.act.LogService;
import org.dclar.dbservice.act.dao.SelectLogDao;
import org.dclar.mybatis.mapper.ActEvtLogMapper;
import org.dclar.mybatis.model.ActEvtLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    ActEvtLogMapper actEvtLogMapper;

    @Override
    public SelectLogDao getActEvtLog(Long logNr) {

        ActEvtLog actEvtLog = actEvtLogMapper.selectByPrimaryKey(logNr);

        SelectLogDao dao = new SelectLogDao();

        try {
            BeanUtils.copyProperties(actEvtLog, dao);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return dao;
    }

    @Override
    public int insert(ActEvtLog record) {

        ActEvtLog re = new ActEvtLog();

        //re.setData();
        re.setExecutionId("setExecutionId");
        //re.setIsProcessed();
        re.setLockOwner("setLockOwner");
        re.setLockTime(new Date());
        re.setLogNr(new Long("1"));
        re.setProcDefId("setProcDefId");
        re.setProcInstId("setProcInstId");
        re.setTaskId("setTaskId");
        re.setTimeStamp(new Date());
        re.setUserId("setUserId");

        return 1;//actEvtLogMapper.insert(re);
    }
}
