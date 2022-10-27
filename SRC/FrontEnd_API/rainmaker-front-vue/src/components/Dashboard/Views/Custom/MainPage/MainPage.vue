<template>
  <div class="desktop-1 screen">
    <div class="top-bar">
      <div class="logo">
        <a href="/">
          <img class="rain-maker-1" src="./rainmaker-logo.png" alt="RainMaker 1"/>
        </a>
      </div>

      <a class="frame-3" href="#">
        <div class="text inter-normal-gray-20px">제품 설명</div>
      </a>
      <a class="frame-4" href="https://inhyeok-blog.tistory.com/">
        <div class="text inter-normal-gray-20px">관련 블로그</div>
      </a>
      <a class="frame-5" href="https://inhyeok-blog.tistory.com/">
        <div class="text inter-normal-gray-20px">문의하기</div>
      </a>
      <div class="frame-null">
      </div>
      <a class="frame-6" href="/register">
        <div class="text inter-normal-gray-20px">회원가입</div>
      </a>
      <a class="frame-7" href="/login">
        <div class="text inter-normal-gray-20px">로그인</div>
      </a>
    </div>
    <img class="title" src="./title.png" alt="Title"/>
    <div class="middle-title">
      <h1 class="rain-maker-2">RainMaker 제공 인사이트</h1>
    </div>
    <div class="middle">
      <div class="middle1-left">
        <div class="frame-1">
          <img class="deployment-frequency-image" src="./ddl.png" alt="Deplot"/>
        </div>
      </div>
      <div class="middle1">
        <div class="frame">
          <div class="text-1 inter-bold-black-35px">생산성 지표를 확인하세요</div>
          <p class="github inter-light-black-25px">
            Github의 데이터들을 기반으로 팀 생산성 관련 지표들을 뽑아내고 있습니다. 이는 Google의 Dora Metric(2016)의 팀 생산성 관련 보고서를
            기반으로 지표들을 뽑아내고 있습니다.
          </p>
        </div>
      </div>
    </div>
    <div class="middle">
      <div class="middle1">
        <div class="frame">
          <div class="text-1 inter-bold-black-35px">위험 감지 알람을 받아보세요</div>
          <p class="x inter-light-black-25px">
            팀이 성장함에 따라, 팀원들의 상태들을 파악하기 어려워지고 있습니다. RainMaker 서비스는 이를 미리 파악하고, 번아웃을 예상해주는
            서비스를 제공해주고 있습니다.
          </p>
        </div>
      </div>
      <div class="middle2-right">
        <div class="frame-2">
          <img
            class="x2560px-slack_-technologies_-logo-1"
            src="./2560px-slack-technologies-logo-1.png"
            alt="2560px-Slack_Technologies_Logo 1"
          />
          <p class="slack">
            Slack은 사람들을 한데 모아 통합된 하나의 팀을 운영하여 팀원의 커뮤니케이션을 혁신적으로 개선할 수 있는 앱입니다.
          </p>
        </div>
      </div>
    </div>
    <div class="buttom">
      <p class="x2022-ring-tail-lemur inter-normal-quill-gray-20px">© 저작권은 2022 Ring-tail-lemur 팀에 있습니다.</p>
      <p class="x010-2181-0 inter-normal-quill-gray-20px">
        회사 전화번호 : 010-2181-0467<br/>메일 문의 : jhbaik1501@gmail.com
      </p>
    </div>
  </div>
</template>
<script>
</script>
<style>
@import url("https://fonts.googleapis.com/css?family=Inter:400,300,700,600");

:root {
  --black: rgba(0, 0, 0, 1);
  --gray: rgba(128, 128, 128, 1);
  --quill-gray: rgba(214, 214, 214, 1);
  --bon-jour: rgba(226, 226, 226, 1);

  --font-size-s: 1.25rem;
  --font-size-m: 1.5625rem;
  --font-size-l: 2.1875rem;
  --font-size-xl: 3.125rem;

  --font-family-inter: "Inter";
}

.inter-light-black-25px {
  color: var(--black);
  /*font-family: var(--font-family-inter);*/
  font-size: var(--font-size-m);
  font-weight: 300;
  font-style: normal;
}

.inter-normal-gray-20px {
  color: var(--gray);
  /*font-family: var(--font-family-inter);*/
  font-size: var(--font-size-s);
  font-weight: 400;
  font-style: normal;
}

.inter-bold-black-35px {
  color: var(--black);
  /*font-family: var(--font-family-inter);*/
  font-size: var(--font-size-l);
  font-weight: 700;
  font-style: normal;
}

.inter-normal-quill-gray-20px {
  color: var(--quill-gray);
  /*font-family: var(--font-family-inter);*/
  font-size: var(--font-size-s);
  font-weight: 400;
  font-style: normal;
}

.desktop-1 {
  align-items: flex-start;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  height: 300vh;
  width: 100%;
}

.hidden,
.hidden * {
  pointer-events: none;
  visibility: hidden;
}

.middle {
  align-items: flex-start;
  display: flex;
  height: 75vh;
  padding: 0 120px;
  width: 100%;
}

.middle1 {
  align-items: center;

  display: flex;
  height: 100%;
  padding: 0 50px;
  width: 45vw;
}

.frame {
  align-items: flex-start;

  display: flex;
  flex-direction: column;
  gap: 2px;
  height: 450px;
  padding: 79px 1px;
  width: 40vw;
}

.github {
  letter-spacing: 0;
  line-height: normal;
  min-height: 130px;
  text-align: center;
  width: 40vw;
}

.text-1 {
  letter-spacing: 0;
  line-height: normal;
  min-height: 59px;
  text-align: center;
  width: 40vw;
}

.middle1-left {
  align-items: flex-start;

  display: flex;
  height: 100%;
  padding: 73px 25px;
  width: 45vw;
}

.frame-1 {
  align-items: center;
  background-color: var(--bon-jour);

  border-radius: 10px;
  display: flex;
  height: 100%;
  margin-left: 1px;
  overflow: hidden;
  padding: 54px 28px;
  width: 43vw;
}

.deployment-frequency-image {
  height: 100%;
  object-fit: scale-down;
  width: 40vw;
}

.middle-title {
  align-items: flex-start;
  border-color: #eaeaea;
  display: flex;
  height: 30vh;
  padding: 89px 440px;
  width: 100%;
}

.rain-maker-2 {
  color: var(--black);
  font-family: var(--font-family-inter);
  font-size: var(--font-size-xl);
  font-weight: 700;
  letter-spacing: 0;
  line-height: normal;
  margin-left: 1px;
  min-height: 61px;
  min-width: 43vw;
  text-align: center;
}

.title {
  object-fit: scale-down;
  height: 85vh;
  width: 100%;
}

.buttom {
  align-items: center;
  background-color: #737373;
  display: flex;
  gap: 30vw;
  height: 20vh;
  width: 100%;
}

.x2022-ring-tail-lemur {
  align-self: center;
  letter-spacing: 0;
  line-height: normal;
  margin-left: 5vw;
  min-width: 35vw;
}

.x010-2181-0 {
  letter-spacing: 0;
  line-height: normal;
  margin-right: 5vw;
}

.top-bar {
  align-items: center;
  display: flex;
  overflow: hidden;
  height: 15vh;
  width: 100%;
}

.frame-6 {
  align-items: flex-start;

  display: flex;
  height: 150px;
  margin-bottom: -1px;
  padding: 62px 0px 0px 30px;
  width: 10vw;
}

.text {
  letter-spacing: 0;
  line-height: normal;
  min-height: 5vw;
}

.frame-7 {
  align-items: flex-start;

  display: flex;
  height: 150px;
  margin-bottom: -1px;
  padding: 62px 22px;
  width: 10vw;
}

.logo {
  justify-content: center;
  align-items: center;

  display: flex;
  gap: 10px;
  margin-bottom: -1px;
  width: 25vw;
}

.rain-maker {
  align-self: center;
  color: #1b3b79;
  font-family: var(--font-family-inter);
  font-size: var(--font-size-m);
  font-weight: 600;
  letter-spacing: 0;
  line-height: normal;
  height: 30%;
  width: 40%;
}

.rain-maker-1 {
  height: 30%;
  object-fit: scale-down;
  width: 100%;
}

.frame-3 {
  justify-content: center;
  align-items: flex-start;

  display: flex;
  height: 150px;
  padding: 62px 5px;
  width: 10vw;
}

.frame-null {
  width: 25vw;
}

.frame-5 {
  justify-content: center;
  align-items: flex-start;

  display: flex;
  height: 150px;
  padding: 62px 5px;
  width: 10vw;
}

.frame-4 {
  justify-content: center;
  align-items: flex-start;

  display: flex;
  height: 150px;
  margin-bottom: -1px;
  padding: 62px 5px;
  width: 10vw;
}

.x {
  letter-spacing: 0;
  line-height: normal;
  min-height: 130px;
  text-align: center;
  width: 40vw;
}

.middle2-right {
  align-items: center;

  display: flex;
  height: 700px;
  padding: 0 29px;
  width: 45vw;
}

.frame-2 {
  align-items: center;
  background-color: var(--bon-jour);

  border-radius: 10px;
  display: flex;
  flex-direction: column;
  gap: 22px;
  height: 322px;
  overflow: hidden;
  padding: 47px 14px;
  width: 43vw;
}

.slack {
  color: var(--black);
  font-family: var(--font-family-inter);
  font-size: var(--font-size-s);
  font-weight: 300;
  letter-spacing: 0;
  line-height: normal;
  min-height: 70px;
  text-align: center;
  width: 70%;
}

.x2560px-slack_-technologies_-logo-1 {
  align-self: center;
  height: 102px;
  margin-left: 1px;
  margin-top: 34px;
  object-fit: scale-down;
  width: 40vw;
}

</style>
