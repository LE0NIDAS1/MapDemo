package com.example.root.mapdemo.entity;

/**
 * Created by leoeg on 18/12/2016.
 */

    public class BookingModel {
    private Integer idModel;
    private String startDate;
    private String endDate;
    private Integer originBranchOfficeId;
    private Integer endBranchOfficeId;
    private boolean withInsurance;
    private boolean withFullTank;



    public Integer getIdModel() {
        return idModel;
    }

    public void setIdModel(Integer idModel) {
        this.idModel = idModel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getOriginBranchOfficeId() {
        return originBranchOfficeId;
    }

    public void setOriginBranchOfficeId(Integer originBranchOfficeId) {
        this.originBranchOfficeId = originBranchOfficeId;
    }

    public Integer getEndBranchOfficeId() {
        return endBranchOfficeId;
    }

    public void setEndBranchOfficeId(Integer endBranchOfficeId) {
        this.endBranchOfficeId = endBranchOfficeId;
    }

    public boolean isWithInsurance() {
        return withInsurance;
    }

    public void setWithInsurance(boolean withInsurance) {
        this.withInsurance = withInsurance;
    }

    public boolean isWithFullTank() {
        return withFullTank;
    }

    public void setWithFullTank(boolean withFullTank) {
        this.withFullTank = withFullTank;
    }
}
