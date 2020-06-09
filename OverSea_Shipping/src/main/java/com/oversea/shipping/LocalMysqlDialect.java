package com.oversea.shipping;

import org.hibernate.dialect.MySQL5Dialect;

public class LocalMysqlDialect extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
    	return " DEFAULT CHARSET=utf8";
    }
}