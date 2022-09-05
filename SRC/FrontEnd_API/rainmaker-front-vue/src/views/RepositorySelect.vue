<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Dongle:wght@400;700&family=Jua&display=swap" rel="stylesheet">

  <div class="container grid px-6 mx-auto " id="titleFont">

    <div class="login">
      <div>
        <div class="loginBox">
          <div style="margin-bottom: 7px">
            리포지토리 선택
          </div>
          <div>
            <form method="post" action="http://localhost:8080/RepositorySelect">
              <ul class="list-group">
                <li class="list-group-item">
                  <div>
                    <div style="float: left; width: 5%; ">
                      <p></p>
                    </div>
                    <div style="float: left; width: 30%; height: 100%">
                      그룹명
                    </div>
                    <div style="float: left; width: 40%; height: 100%">
                      리포지토리명
                    </div>

                    <div style="float: left; width: 20%; height: 100%">
                      마지막 사용
                    </div>
                  </div>

                </li>
                <li v-for="item in list" class="list-group-item">
                  <div>
                    <div style="float: left; width: 5%; ">
                      <input class="form-check-input me-1" type="checkbox" aria-label="..." name="repo_id" v-bind:value="[item.id, item.organization, item.repository, item.pushed_at]" >
                    </div>
                    <div style="float: left; width: 30%; height: 100%">
                      {{item.organization}}
                    </div>
                    <div style="float: left; width: 40%; height: 100%">
                      {{item.repository}}
                    </div>

                    <div style="float: left; width: 20%; height: 100%">
                      {{item.pushed_at}}
                    </div>
                  </div>

                </li>
              </ul>

              <p><input type="submit" value="Submit"> <input type="reset" value="Reset"></p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "RepositorySelect",
  data() {
    return {
      list : [
        // {id:"123141121111", organization: "인혁", repository: "RainMaker", pushed_at : "2022-06-08T10:06:50Z"},
        // {id:"123141132222", organization: "종현", repository: "test-for-fake-project", pushed_at : "2022-06-08T10:06:50Z"},
        // {id:"123141143333", organization: "동인", repository: "gugudan", pushed_at : "2022-06-08T10:06:50Z"},
      ]
    }
  },
  created() {
    this.getList();
  },
  methods : {
    async getList () {
      const RepositoryInfo = await axios({
        method: "get",
        url: this.defaultURL + "/RepositorySelect",
      });
      this.list = RepositoryInfo.data;
    }
  }
}
</script>

<style>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login {
  width: 450px;
}

.login {
  min-height: 0;
}

.login {
  -webkit-border-radius: 8px;
  border-radius: 12px;
  border: 1px solid #dadce0;
  display: block;
  -webkit-flex-shrink: 0;
  flex-shrink: 0;
  margin: 0 auto;
  min-height: 0;
  -webkit-transition: .2s;
  transition: .2s;
  width: 700px;
}

.login {
  background: #fff;
  display: -webkit-box;
  display: -webkit-flex;
  display: flex;
  -webkit-flex-direction: column;
  flex-direction: column;
  max-width: 100%;
  position: relative;
  z-index: 2;
}

.login {
  background: #fff;
  direction: ltr;
  line-height: 1.4286;
  margin: 0;
  padding: 0;
}

.loginBox {
  height: auto;
  min-height: 500px;
}

.loginBox {
  box-sizing: inherit;
  padding: 48px 40px 36px;
}

#titleFont {
  font-family: 'Jua', sans-serif;
  font-size: 20px;
}

.h-screen {
  min-height: 100vh;
  height: auto;
}
</style>