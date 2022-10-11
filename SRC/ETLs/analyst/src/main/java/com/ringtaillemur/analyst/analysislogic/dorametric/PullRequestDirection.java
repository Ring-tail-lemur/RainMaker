package com.ringtaillemur.analyst.analysislogic.dorametric;

import java.io.IOException;

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
        queryRunner.runMakePullRequestDirection(OlapQuery.MAKE_PULL_REQUEST_DIRECTION);
    }

}
