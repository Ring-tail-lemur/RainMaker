from . import repository_main
from . import pull_request_main
from datetime import datetime
import datetime as dt
import pandas as pd
import logging
class PrExtractor():
    pr = pull_request_main.PullRequest()
    now = datetime.now()
    week_ago = now+dt.timedelta(weeks=-1)
    month_ago = now+dt.timedelta(weeks=-5)
    now = now.strftime("%Y-%m-%d")
    week_ago = week_ago.strftime("%Y-%m-%d")
    month_ago = month_ago.strftime("%Y-%m-%d")

    def get_user_above_average(self):
        rp = repository_main.Repository()
        result_df = self.get_repository_pr_cnt()
        burn_out_repository = result_df['repository_id'].values.tolist()
        # print(burn_out_repository)
        burn_out_user_list = rp.get_repo_and_user_info_list(burn_out_repository)
        return burn_out_user_list
        # print(burn_out_user_list)


    def get_repository_pr_cnt(self):
        # 레포 리스트
        repository_list = self.pr.get_repository_id_list()
        # 풀리케 리스트
        pull_request_df = self.pr.get_pull_request()
        # 결과 DataFrame
        result_df = pd.DataFrame(columns=['repository_id','now','last'])
        # 각 레포 별로 최근 일주일의 PR 개수, 한달간의 PR 개수 가져옴 
        for repository in repository_list:
            # 해당 Repository의 PR을 탐색
            cond_repo_pr = pull_request_df.repository_id == repository
            repo_pr_df = pull_request_df[cond_repo_pr]
            # 현재 주의 PR DF
            self.this_week_pr(repo_pr_df)
            # 지난 한 달의 PR DF
            self.month_ago_pr(repo_pr_df)
            # TODO: 현재는 그냥 현재/7 > 지난 한 달 / 28 이면 True 반환중. 개선 필요
            repo_pr_len_list = self.make_repository_cnt()
            if(repo_pr_len_list[1]/7 > repo_pr_len_list[2]/28):
                repo_pr_len_list[0] = repository
                new_data = {
                    'repository_id' : [repo_pr_len_list[0]],
                    'now' : [repo_pr_len_list[1]],
                    'last' : [repo_pr_len_list[2]]
                }
                new_df = pd.DataFrame(new_data)
                result_df = pd.concat([result_df, new_df])
            logging.info(result_df)
        return result_df

    def this_week_pr(self, repo_pr_df):
        cond_this_week_end = repo_pr_df.created_date <= self.now
        cond_this_week_start = repo_pr_df.created_date >= self.week_ago
        self.last_week_pr_list = repo_pr_df[(cond_this_week_end) & (cond_this_week_start)]


    def month_ago_pr(self,repo_pr_df):
        cond_week_end = repo_pr_df.created_date < self.week_ago
        cond_week_start = repo_pr_df.created_date >= self.month_ago
        self.last_month_pr_list = repo_pr_df[(cond_week_end) & (cond_week_start)]

    def make_repository_cnt(self):
        return [0, len(self.last_week_pr_list), len(self.last_month_pr_list)]

# test = PrExtractor()
# test.get_user_above_average()
# print(result_list)

        