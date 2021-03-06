/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.rsadapter.jdbc.v43;

import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;

import javax.resource.spi.ConnectionManager;
import javax.sql.DataSource;

import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;
import com.ibm.ws.rsadapter.AdapterUtil;
import com.ibm.ws.rsadapter.impl.WSConnectionRequestInfoImpl;
import com.ibm.ws.rsadapter.impl.WSManagedConnectionFactoryImpl;
import com.ibm.ws.rsadapter.jdbc.WSJdbcDataSource;

public class WSJdbc43DataSource extends WSJdbcDataSource implements DataSource {
    private static final TraceComponent tc = Tr.register(WSJdbc43DataSource.class, AdapterUtil.TRACE_GROUP, AdapterUtil.NLS_FILE);

    public WSJdbc43DataSource(WSManagedConnectionFactoryImpl mcf, ConnectionManager connMgr) {
        super(mcf, connMgr);
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return new WSJdbc43ConnectionBuilder(this);
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    Connection getConnection(WSJdbc43ConnectionBuilder builder) throws SQLException {
        WSConnectionRequestInfoImpl conRequest = new WSConnectionRequestInfoImpl(mcf, cm, builder.user, builder.password, builder.shardingKey, builder.superShardingKey);
        return super.getConnection(conRequest);
    }
}