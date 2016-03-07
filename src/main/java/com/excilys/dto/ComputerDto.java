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

  public void setIntroducedDate(String introducedDate) {
    this.introducedDate = introducedDate;
  }

  public String getDiscontinuedDate() {
    return discontinuedDate;
  }

  public void setDiscontinuedDate(String discontinuedDate) {
    this.discontinuedDate = discontinuedDate;
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
