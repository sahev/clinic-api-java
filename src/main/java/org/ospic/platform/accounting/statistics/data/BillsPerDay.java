package org.ospic.platform.accounting.statistics.data;

import lombok.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This file was created by eli on 26/03/2021 for org.ospic.platform.accounting.statistics.data
 * --
 * --
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
public class BillsPerDay {
    private String createdDate;
    private BigDecimal totalBills;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalAmount;

    public static BillsPerDay fromResultSet(ResultSet rs) throws SQLException{
        final String createdDate = rs.getString("createdDate");
        final BigDecimal totalBills = rs.getBigDecimal("totalBills");
        final BigDecimal totalPaidAmount = rs.getBigDecimal("totalPaidAmount");
        final BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
        return new BillsPerDay(createdDate,totalBills, totalPaidAmount,totalAmount);
    }
}
