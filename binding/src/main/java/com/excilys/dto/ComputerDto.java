package com.excilys.dto;

import com.excilys.dto.validation.Date;
import com.excilys.dto.validation.DateConsistency;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO representation of the model class Computer
 * 
 * @author B. Herbaut
 * @see com.excilys.model.Computer
 */
@DateConsistency
public class ComputerDto implements Serializable {

  private static final long serialVersionUID = -2761601670550288031L;

  @Min(0)
  private long              computerId;

  @NotNull
  @Size(min = 1, max = 30)
  private String            computerName;

  @Date
  private String            introducedDate;
  @Date
  private String            discontinuedDate;

  private long              companyId;
  private String            companyName;

  public ComputerDto() {
    super();
  }

  /**
   * Instantiates a new computer dto.
   *
   * @param computerId the computer id
   * @param computerName the computer name
   * @param introducedDate the introduced date
   * @param discontinuedDate the discontinued date
   * @param companyId the company id
   * @param companyName the company name
   */
  public ComputerDto(long computerId, String computerName, String introducedDate,
      String discontinuedDate, long companyId, String companyName) {
    this.computerId = computerId;
    this.computerName = computerName;
    this.introducedDate = introducedDate;
    this.discontinuedDate = discontinuedDate;
    this.companyId = companyId;
    this.companyName = companyName;
  }

  public long getComputerId() {
    return computerId;
  }

  public void setComputerId(long computerId) {
    this.computerId = computerId;
  }

  public String getComputerName() {
    return computerName;
  }

  public void setComputerName(String computerName) {
    this.computerName = computerName;
  }

  public String getIntroducedDate() {
    return introducedDate;
  }

  /**
   * Sets the introduced date.
   *
   * @param introducedDate the new introduced date
   */
  public void setIntroducedDate(String introducedDate) {
    if (introducedDate != null) {
      this.introducedDate = introducedDate.replace('/', '-');
    } else {
      this.introducedDate = null;
    }
  }

  public String getDiscontinuedDate() {
    return discontinuedDate;
  }

  /**
   * Sets the discontinued date.
   *
   * @param discontinuedDate the new discontinued date
   */
  public void setDiscontinuedDate(String discontinuedDate) {
    if (discontinuedDate != null) {
      this.discontinuedDate = discontinuedDate.replace('/', '-');
    } else {
      this.discontinuedDate = null;
    }
  }

  public long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public String toString() {
    return "ComputerDto [computerId=" + computerId + ", computerName=" + computerName
        + ", introducedDate=" + introducedDate + ", discontinuedDate=" + discontinuedDate
        + ", companyId=" + companyId + ", companyName=" + companyName + "]";
  }
}
