package com.excilys.dto;

/**
 * DTO representation of the model class Computer
 * 
 * @author B. Herbaut
 * @see com.excilys.model.Computer
 */
public class ComputerDto {

  private long   computerId;
  private String computerName;
  private String introducedDate;
  private String discontinuedDate;

  private long   companyId;
  private String companyName;

  /**
   * Instantiates a new computer dto.
   *
   * @param computerId
   *          the computer id
   * @param computerName
   *          the computer name
   * @param introducedDate
   *          the introduced date
   * @param discontinuedDate
   *          the discontinued date
   * @param companyId
   *          the company id
   * @param companyName
   *          the company name
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

  public String getComputerName() {
    return computerName;
  }

  public String getIntroducedDate() {
    return introducedDate;
  }

  public String getDiscontinuedDate() {
    return discontinuedDate;
  }

  public long getCompanyId() {
    return companyId;
  }

  public String getCompanyName() {
    return companyName;
  }
}
