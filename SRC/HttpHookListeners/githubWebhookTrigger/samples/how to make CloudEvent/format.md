1. 공통
    1. 행위(PR open, comment, codeReview, PR close(not merge, merge 등…)
    2. 행위자의 이름
    3. 행위의 식별자(id)
    4. 행위자(actor)의 식별자(user.id)
    5. 발생 시각
    6. 발생 장소(organization의 repo)의 팀 이름
    7. 발생 장소(repo)
2. PR(action : opened / pull_request)
    1. commit들을 얻을 수 있는 url
    2. Commit 수
3. codeReview(action : submitted / “review”)
    1. comment가 달린 PR의 위치
    2. comment가 달린 commit의 id
4. Comment(action : created / “comment”)
    1. comment가 달린 PR의 위치
5. PR closed(with not merged, action : “closed” / )
    1. Merge 여부 (pull_request/merged_at)
    2. merge한 branch의 이름
    3. 브랜치 이름(“head/ref”)