<template>
  <div class="card" v-if="!waiting">
    <div class="card-header">
      <h5 class="card-title">추적할 리포지토리 등록</h5>
    </div>
    <div class="card-body row table-full-width">
      <div class="col-sm-12">
        <el-table class="table-striped"
                  header-row-class-name="text-primary"
                  fit="true"
                  :data="tableData">
          <el-table-column type="index">

          </el-table-column>
          <el-table-column prop="organization"
                           label="그룹명">
          </el-table-column>
          <el-table-column prop="repository"
                           label="리포지토리명">
          </el-table-column>
          <el-table-column prop="pushedAt"
                           align="center"
                           label="마지막사용">
          </el-table-column>
          <el-table-column
            align="right"
            label="Active">
            <template slot-scope="props">
              <p-switch v-model="props.row.checked"></p-switch>
            </template>
          </el-table-column>
        </el-table>
        <p-button class="float-right" style="margin: 1em;" v-on:click="registerRepository()">저장하기</p-button>
      </div>
    </div>
  </div>

  <loading-main-panel v-else="waiting"></loading-main-panel>
</template>

<script>
import Vue from 'vue'
import {Table, TableColumn} from 'element-ui'
import PSwitch from 'src/components/UIComponents/Switch.vue'
import {Button} from "@/components/UIComponents";
import setHeaderJWT from "@/api/setHeaderJWT";
import axios from "axios";
import tab from "@/components/UIComponents/Tabs/Tab";
import pageCheckAndChange from "@/util/pageCheckAndChange";
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";

Vue.use(Table)
Vue.use(TableColumn)
export default {
  components: {
    LoadingMainPanel,
    PSwitch,
    [Button.name]: Button
  },
  data () {
    return {
      name: "RepositorySelect",
      tableData: [],
      waiting : false
    }
  },
  created() {
    this.getList();
  },
  methods : {
    async getList () {
      let RepositoryInfo;

      try {
        RepositoryInfo = await axios({
          headers: setHeaderJWT(),
          method: "get",
          url: this.custom.defaultURL + "/api/user/remote/repositories",
        });
        console.log("inheyok  === =  " , RepositoryInfo);
      } catch (e) {
        // pageCheckAndChange(e, this);
      }

      this.tableData = RepositoryInfo.data;
    },
    async registerRepository() {

      let tableData = this.tableData;
      let repoIds = [];

      for(let i=0; i< tableData.length; i++) {
        if(tableData[i].checked)
          repoIds.push((tableData[i].id + "," + tableData[i].organization + "," + tableData[i].repository).toString());
      }

      if ( repoIds.length === 0 || tableData.length === 0) {
        alert("리포지토리를 선택해주세요");
        return;
      }

      this.waiting = true;

      await axios({
        headers: setHeaderJWT(),
        method: "post",
        url: this.custom.defaultURL + "/api/user/repositories",
        data : {
          repoIds: repoIds
        }
      });
      this.$router.push("/RepositorySelect")
      window.location.replace(this.custom.myURL + "/dashboard");
    }

  }
}
</script>

<style>

</style>
