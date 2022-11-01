<template>
  <div class="col-md-4">
    <chart-card :chart-data="cycleTime"
                chart-type="Pie"
                title="CycleTime"
                description="Last Campaign Performance"
                :key="testData"
                :chart-options="cycleTime.options"
                >
      <span slot="title">싸이클 타임</span>
      <template slot="footer">
        <div class="legend">

          <i class="fa fa-circle" style="color:rgba(239,129,86,0.7)"></i> 코딩 시간
          <i class="fa fa-circle" style="color:rgba(125,217,245,0.7)"></i> 인지 시간
          <i class="fa fa-circle" style="color:rgba(55,152,109,0.7)"></i> 리뷰 시간
          <i class="fa fa-circle" style="color:rgba(203,104,252,0.7)"></i> 배포 시간
        </div>
        <hr>
        <div class="stats">
          <i class="fa fa-check"></i> 14일 동안의 지표입니다.
        </div>
      </template>
    </chart-card>
  </div>
</template>
<script>
import { Card } from 'src/components/UIComponents'
import ChartCard from 'src/components/UIComponents/Cards/ChartCard'
import PieChart from 'src/components/UIComponents/Charts/PieChart'
import DoughnutChart from "@/components/UIComponents/Charts/DoughnutChart";
import axios from "axios";
import setHeaderJWT from "@/util/setHeaderJWT";
import Loading from "@/components/Dashboard/Layout/LoadingMainPanel";

const tooltipOptions = {
  tooltipFillColor: "rgba(0,0,0,0.5)",
  tooltipFontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
  tooltipFontSize: 14,
  tooltipFontStyle: "normal",
  tooltipFontColor: "#fff",
  tooltipTitleFontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
  tooltipTitleFontSize: 14,
  tooltipTitleFontStyle: "bold",
  tooltipTitleFontColor: "#fff",
  tooltipYPadding: 6,
  tooltipXPadding: 6,
  tooltipCaretSize: 8,
  tooltipCornerRadius: 6,
  tooltipXOffset: 10,

};
export default {
  components: {
    Card,
    ChartCard,
    PieChart,
    DoughnutChart,
  },
  data() {
    return {
      testData : 0,
      cycleTime: {
        labels: ['코딩 시간', '인지 시간', '리뷰 시간', '배포 시간'],
        datasets: [{
          label: "Emails",
          pointRadius: 0,
          pointHoverRadius: 0,
          backgroundColor: [
            'rgba(239,129,86,0.7)',
            'rgba(125,217,245,0.7)',
            'rgba(55,152,109,0.7)',
            'rgba(203,104,252,0.7)'
          ],
          borderWidth: 0,
          // data: [1]
        }],
        options: {
          tooltips: tooltipOptions,
          cutoutPercentage: 70
        }
      },
    }
  },
  methods : {
    async createdMethod(showDate) {
      let Today = new Date();
      const FormatToday = this.dateFormat(Today);
      Today.setDate(Today.getDate()- showDate);
      const FormatLastMonth = this.dateFormat(Today);
      const repositories = await this.getRepositoryInfo();
      const repositoryArr = [];
      repositories.forEach((repository) => {
        repositoryArr.push(repository['repositoryId']);
      });
      await this.getCycleTime(FormatLastMonth, FormatToday, repositoryArr, "/lead-time-for-change/cycle-time-detail");
    },
    async getRepositoryInfo() {
      let axiosResponse;
      axiosResponse = await axios.get(this.custom.defaultURL + "/api/user/repositories", {
        headers: setHeaderJWT()
      });
      return axiosResponse.data;
    },
    async getCycleTime(start_time, end_time, repo_ids, MetricName) {
      console.log(start_time, end_time, repo_ids, MetricName, "이 들어왔네")
      const Message = await axios.get(this.custom.defaultURL + "/api/dorametric" + MetricName, {
        headers: setHeaderJWT(),
        params: {
          start_time: start_time,
          end_time: end_time,
          repo_id: repo_ids.toString()
        }
      })
      let responseData = Message.data;
      console.log("after : ", responseData);
      if(!responseData.codingAverageTime && !responseData.pickupAverageTime && !responseData.reviewAverageTime && !responseData.deployAverageTime){
        this.cycleTime.datasets[0].data = [0.00000000000000001];
        this.cycleTime.labels = ["데이터가 없습니다"];
        this.cycleTime.datasets[0].backgroundColor = ["#838383"];
        this.testData = 1;
        return;
      }
      this.cycleTime.datasets[0].data = [responseData.codingAverageTime, responseData.pickupAverageTime, responseData.reviewAverageTime, responseData.deployAverageTime];
      this.cycleTime.labels = [responseData.codingTimeLevel, responseData.pickupTimeLevel, responseData.reviewTimeLevel, responseData.deployTimeLevel];

      this.testData = 1;
    },
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;

      return date.getFullYear() + '-' + month + '-' + day;
    },
  },
  async created() {
    await this.createdMethod(14);
  }
}
</script>
