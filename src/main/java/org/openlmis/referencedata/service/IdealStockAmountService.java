/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.referencedata.service;

import org.openlmis.referencedata.domain.IdealStockAmount;
import org.openlmis.referencedata.repository.IdealStockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IdealStockAmountService {

  @Autowired
  private IdealStockAmountRepository repository;

  public List<IdealStockAmount> search() {
    return repository.search(null);
  }

  public List<IdealStockAmount> search(List<IdealStockAmount> idealStockAmounts) {
    return repository.search(idealStockAmounts);
  }

  public Page<IdealStockAmount> search(IdealStockAmountSearchParams requestParams,
                                       Pageable pageable) {
    return repository.search(requestParams.getFacilityId(), requestParams.getCommodityTypeId(),
        requestParams.getProcessingPeriodId(), pageable);
  }
}
