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

package org.openlmis.referencedata.dto;

import org.openlmis.referencedata.domain.ProcessingSchedule;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ProcessingScheduleDto extends BaseDto
    implements ProcessingSchedule.Exporter, ProcessingSchedule.Importer {

  private String code;
  private String description;
  private String name;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ProcessingScheduleDto)) {
      return false;
    }
    ProcessingScheduleDto that = (ProcessingScheduleDto) obj;
    return Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }
}
