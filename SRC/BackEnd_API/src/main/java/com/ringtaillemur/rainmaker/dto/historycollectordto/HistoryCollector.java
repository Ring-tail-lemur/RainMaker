package com.ringtaillemur.rainmaker.dto.historycollectordto;

import lombok.Builder;
import lombok.Data;

@Data
public class HistoryCollector {
    private String ownerName;
    private String repoName;
    private String token;

    @Builder
    public HistoryCollector(String ownerName, String repoName, String token) {
        this.ownerName = ownerName;
        this.repoName = repoName;
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" + "ownerName=" + ownerName + ", repoName=" + repoName + ", token=" + token + "}";
    }
}
