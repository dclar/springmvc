package org.dclar.dbservice.act;

import org.dclar.dbservice.act.dao.SelectLogDao;
import org.dclar.mybatis.model.ActEvtLog;

public interface LogService {

    public SelectLogDao getActEvtLog(Long logNr);

    int insert(ActEvtLog record);
}
