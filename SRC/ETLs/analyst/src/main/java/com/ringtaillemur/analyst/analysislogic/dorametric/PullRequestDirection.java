package com.ringtaillemur.analyst.analysislogic.dorametric;

import java.io.IOException;

import com.ringtaillemur.analyst.restapi.LogModule;
import org.json.simple.parser.ParseException;

import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;

public class PullRequestDirection {
    private static final PullRequestDirection pullRequestDirection = new PullRequestDirection();
    private final QueryRunner queryRunner = QueryRunner.getQueryRunner();
    public PullRequestDirection() {
    }

    public static PullRequestDirection getPullRequestDirection () {
        return pullRequestDirection;
    }

    public void MakePullRequestDirection() throws IOException, ParseException {
        try{
        queryRunner.runMakePullRequestDirection(OlapQuery.MAKE_PULL_REQUEST_DIRECTION);
        }catch (Exception e){
            LogModule logModule = LogModule.getLogModule();
            logModule.sendLog(e, "MakePullRequestDirection");
        }
    }

}
