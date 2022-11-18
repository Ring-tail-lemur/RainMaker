<template>
  <div>
    <div>
      <div class="col-lg-12 col-md-12">
        <time-line type="simple" class="timeline-margin">

          <time-line-item badgeType="success" badgeIcon="nc-icon nc-share-66" :inverted="true"
                          v-for="releaseData in releaseDataSet"
                          :style="{ 'margin-bottom' : releaseData[0].margin + 'px'}">
            <div slot="Description" class="datetime-title">{{ releaseData[0].releaseDate }}</div>
            <div slot="content">
              <el-table :data="releaseData" header-row-class-name="text-primary">
                <el-table-column prop="releaseName" label="버전명"></el-table-column>
                <el-table-column prop="repositoryName" label="리포지토리명"></el-table-column>
                <el-table-column prop="commitSize" label="커밋 갯수"></el-table-column>
                <el-table-column prop="codeChangeSize" label="코드 변화량"></el-table-column>
                <el-table-column prop="publishedAt" label="배포 시간"></el-table-column>
              </el-table>
            </div>
          </time-line-item>

        </time-line>
      </div>
    </div>
  </div>
</template>
<script>
import TaskList from '@/components/Dashboard/Views/Dashboard/Widgets/TaskList'
import {TimeLine, TimeLineItem, Card, Dropdown, Badge} from 'src/components/UIComponents'
import {Table, TableColumn} from 'element-ui'
import axios from "axios";
import setHeaderJWT from "@/api/setHeaderJWT";

export default {
  components: {
    TaskList,
    TimeLine,
    TimeLineItem,
    Card,
    Dropdown,
    Badge,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {
      releaseDataSet: [
        [],
      ],
    }
  },
  methods: {
    async getRepositoryInfo() {
      let axiosResponse;
      axiosResponse = await axios.get(this.custom.defaultURL + "/api/user/repositories", {
        headers: setHeaderJWT()
      });
      return axiosResponse.data;
    },
    async getTableData(showDate) {
      let Today = new Date();
      const FormatToday = this.dateFormat(Today);
      Today.setDate(Today.getDate() - showDate);
      const FormatLastMonth = this.dateFormat(Today);
      const repositories = await this.getRepositoryInfo();
      const repositoryArr = [];
      repositories.forEach((repository) => {
        repositoryArr.push(repository['repositoryId']);
      });
      let deploymentFrequencyDetail = await this.getDeploymentFrequencyDetail(FormatLastMonth, FormatToday, repositoryArr);
      this.releaseDataSet = deploymentFrequencyDetail;
    },
    async getDeploymentFrequencyDetail(start_time, end_time, repo_ids) {
      let axiosResponse;
      axiosResponse = (await axios.get(this.custom.defaultURL + "/api/dorametric/deployment-frequency/deployment-frequency-detail", {
        headers: setHeaderJWT(),
        params: {
          start_time: start_time,
          end_time: end_time,
          repo_id: repo_ids.toString()
        }
      })).data;

      const releaseDateSet = []

      for (let i = 0; i < axiosResponse.length ; i++) {
        releaseDateSet.push([axiosResponse[i]]);
      }
      return releaseDateSet;
    },
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;

      return date.getFullYear() + '-' + month + '-' + day;
    },
  },
  created() {
    this.getTableData(14)
  }
}
</script>
<style>
.datetime-title {
  display: inline-block;
  transform: translateX(-100px);
  width: 100px;
  border: 1px solid #a8a8a8;
  border-radius: 10px;
  text-align: center;
  margin-top: 28px;
  background-color: #fff;
  font-size: 17px;
}

.timeline-margin {
  margin-left: 100px;
}
</style>
