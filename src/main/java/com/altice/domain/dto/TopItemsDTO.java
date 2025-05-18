package com.altice.domain.dto;

import java.util.List;

public class TopItemsDTO {

    private List<ItemDTO> topItems;
    private Integer totalItemsAnalyzed;
    private String analysisDate;

    public List<ItemDTO> getTopItems() {
        return topItems;
    }

    public void setTopItems(List<ItemDTO> topItems) {
        this.topItems = topItems;
    }

    public Integer getTotalItemsAnalyzed() {
        return totalItemsAnalyzed;
    }

    public void setTotalItemsAnalyzed(Integer totalItemsAnalyzed) {
        this.totalItemsAnalyzed = totalItemsAnalyzed;
    }

    public String getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(String analysisDate) {
        this.analysisDate = analysisDate;
    }
}
